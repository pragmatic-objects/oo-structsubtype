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

import com.pragmaticobjects.oo.meta.model.Type;
import com.pragmaticobjects.oo.meta.model.TypeFromTypeMirror;
import io.vavr.collection.List;
import java.util.function.Supplier;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;

/**
 * In annotation processors, retrieving an annotation field with {@link Class} type
 * ends with exception. There is no known way of working this around, but this
 * hack at least provides means to obtaining {@link TypeMirror} instance instead.
 * 
 * See also <a href="http://hauchee.blogspot.com/2015/12/compile-time-annotation-processing-getting-class-value.html">this</a>
 * article.
 * 
 * @author skapral
 */
public class Hack {
    private Hack() {}
    
    /**
     * @param supplier Method reference to the annotation field of type {@link Class}
     * @return {@link Type} instance.
     */
    public static Type extractType(Supplier<Class> supplier) {
        try {
            Class type = supplier.get();
            return new TypeFromClass(type);
        } catch(MirroredTypeException ex) {
            return new TypeFromTypeMirror(ex.getTypeMirror());
        }
    }
    
    /**
     * @param supplier Method reference to the annotation field of type {@link Class}
     * @return {@link Type} instances.
     */
    public static List<Type> extractTypes(Supplier<Class[]> supplier) {
        try {
            Class[] types = supplier.get();
            return List.of(types).map(TypeFromClass::new);
        } catch(MirroredTypesException ex) {
            return List
                    .ofAll(ex.getTypeMirrors())
                    .map(TypeFromTypeMirror::new);
        }
    }
}
