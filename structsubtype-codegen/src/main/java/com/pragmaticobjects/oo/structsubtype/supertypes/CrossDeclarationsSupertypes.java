/*-
 * ===========================================================================
 * project-name
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Copyright (C) 2019 Kapralov Sergey
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
package com.pragmaticobjects.oo.structsubtype.supertypes;

import com.pragmaticobjects.oo.meta.model.Type;
import com.pragmaticobjects.oo.meta.model.TypeReferential;
import com.pragmaticobjects.oo.structsubtype.api.StructSubtype;
import com.pragmaticobjects.oo.structsubtype.codegen.processor.Hack;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import java.util.Comparator;

/**
 *
 * @author skapral
 */
public class CrossDeclarationsSupertypes implements Supertypes {
    private final String packageName;
    private final StructSubtype base;
    private final List<StructSubtype> neighbors;

    public CrossDeclarationsSupertypes(String packageName, StructSubtype base, List<StructSubtype> neighbors) {
        this.packageName = packageName;
        this.base = base;
        this.neighbors = neighbors;
    }

    @Override
    public final Set<Type> set() {
        Set<Type> baseTypes = typesByAnnotation(base);
        return neighbors
            .filter(d -> baseTypes.containsAll(typesByAnnotation(d)))
            .filter(d -> !d.equals(base))
            .<Type>map(d -> new TypeReferential(packageName, d.name()))
            .toSortedSet(Comparator.comparing(Type::getFullName));
    }
    
    private Set<Type> typesByAnnotation(StructSubtype annotation) {
        return HashSet.ofAll(Hack.extractTypes(annotation::inherits));
    }
    
}
