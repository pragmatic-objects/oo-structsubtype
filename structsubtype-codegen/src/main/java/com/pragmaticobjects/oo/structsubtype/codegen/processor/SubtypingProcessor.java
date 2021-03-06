/*-
 * ===========================================================================
 * structsubtype-codegen
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Copyright (C) 2019 - 2020 Kapralov Sergey
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ============================================================================
 */
package com.pragmaticobjects.oo.structsubtype.codegen.processor;

import com.pragmaticobjects.oo.meta.freemarker.FreemarkerArtifact;
import com.pragmaticobjects.oo.meta.freemarker.FreemarkerArtifactModel;
import com.pragmaticobjects.oo.meta.model.FAMStandard;
import com.pragmaticobjects.oo.meta.model.Type;
import com.pragmaticobjects.oo.meta.model.TypeReferential;
import com.pragmaticobjects.oo.structsubtype.api.StructSubtype;
import com.pragmaticobjects.oo.structsubtype.supertypes.CombinedSupertypes;
import com.pragmaticobjects.oo.structsubtype.supertypes.CrossDeclarationsSupertypes;
import com.pragmaticobjects.oo.structsubtype.supertypes.SupertypesFromDeclaration;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 *
 * @author skapral
 */
@SupportedAnnotationTypes({
    "com.pragmaticobjects.oo.structsubtype.api.StructSubtype.List",
    "com.pragmaticobjects.oo.structsubtype.api.StructSubtype"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SubtypingProcessor extends AbstractProcessor {
    @Override
    public final boolean process(java.util.Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        HashSet<PackageElement> elements = HashSet.ofAll(annotations)
            .flatMap(a -> roundEnv.getElementsAnnotatedWith(a))
            .map(e -> (PackageElement) e);
        Map<String, Set<StructSubtype>> packageAnnotationsMap = elements.collect(
            HashMap.collector(
                e -> e.getQualifiedName().toString(),
                elem -> HashSet.of(elem.getAnnotationsByType(StructSubtype.class))
            )
        );
        for(Tuple2<String, Set<StructSubtype>> packageAnnotations : packageAnnotationsMap) {
            Set<StructSubtype> annos = packageAnnotations._2;
            String packageName = packageAnnotations._1;
            for(StructSubtype anno : annos) {
                FreemarkerArtifactModel model = new FAMStandard(
                    new TypeReferential(packageName, anno.name()),
                    HashMap.ofEntries(
                        new Tuple2<>(
                            "supertypes",
                            new CombinedSupertypes(
                                new SupertypesFromDeclaration(anno),
                                new CrossDeclarationsSupertypes(packageName, anno, annos)
                            ).set()
                        )
                    )
                );
                FreemarkerArtifact artifact = new FreemarkerArtifact(
                    "structsubtype",
                    model
                );
                try {
                    JavaFileObject newSrc = processingEnv.getFiler().createSourceFile(model.<Type>get("this").getFullName());
                    try(OutputStream os = newSrc.openOutputStream()) {
                        os.write(artifact.contents().getBytes());
                        os.flush();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(SubtypingProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
}
