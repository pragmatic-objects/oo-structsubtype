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
import com.pragmaticobjects.oo.meta.model.FAMStandard;
import com.pragmaticobjects.oo.meta.model.Type;
import com.pragmaticobjects.oo.meta.model.TypeReferential;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;

/**
 *
 * @author skapral
 */
public class StructSubtypeArtifact extends FreemarkerArtifact {

    public StructSubtypeArtifact(String packageName, String className, List<Type> supertypes) {
        super(
            "structsubtype",
            new FAMStandard(
                new TypeReferential(packageName, className),
                HashMap.ofEntries(
                    new Tuple2<>("supertypes", supertypes)
                )
            )
        );
    }
    
}
