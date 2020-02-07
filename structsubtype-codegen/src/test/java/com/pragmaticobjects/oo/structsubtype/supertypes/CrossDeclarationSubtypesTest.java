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

import com.pragmaticobjects.oo.meta.model.TypeReferential;
import com.pragmaticobjects.oo.structsubtype.api.StructSubtype;
import com.pragmaticobjects.oo.tests.TestCase;
import com.pragmaticobjects.oo.tests.junit5.TestsSuite;
import com.test.*;
import io.vavr.collection.HashSet;
import java.lang.annotation.Annotation;

/**
 *
 * @author skapral
 */
public class CrossDeclarationSubtypesTest extends TestsSuite {
    public CrossDeclarationSubtypesTest() {
        super(
            new TestCase(
                "Simple subtyping: T derives T1 and T2, but not T3",
                new AssertSupertypesAreTheSame(
                    new CrossDeclarationsSupertypes(
                        "com.test",
                        new Sample("T", A.class, B.class),
                        HashSet.of(
                            new Sample("T1", A.class),
                            new Sample("T2", B.class),
                            new Sample("T3", C.class)                                
                        )
                    ),
                    new FixedSupertypes(
                        new TypeReferential("com.test", "T1"),
                        new TypeReferential("com.test", "T2")
                    )
                )
            ),
            new TestCase(
                "Complex subtyping: T derives everything",
                new AssertSupertypesAreTheSame(
                    new CrossDeclarationsSupertypes(
                        "com.test",
                        new Sample("T", A.class, B.class, C.class),
                        HashSet.of(
                            new Sample("T1", A.class, B.class),
                            new Sample("T2", B.class, C.class),
                            new Sample("T3", C.class, A.class)
                        )
                    ),
                    new FixedSupertypes(
                        new TypeReferential("com.test", "T1"),
                        new TypeReferential("com.test", "T2"),
                        new TypeReferential("com.test", "T3")
                    )
                )
            ),
            new TestCase(
                "Complex subtyping: T derives T1",
                new AssertSupertypesAreTheSame(
                    new CrossDeclarationsSupertypes(
                        "com.test",
                        new Sample("T", A.class, B.class),
                        HashSet.of(
                            new Sample("T1", A.class),
                            new Sample("T2", A.class, B.class, C.class)
                        )
                    ),
                    new FixedSupertypes(
                        new TypeReferential("com.test", "T1")
                    )
                )
            )
        );
    }
    
    private static class Sample implements StructSubtype {
        private final String name;
        private final Class[] inherits;

        public Sample(String name, Class... inherits) {
            this.name = name;
            this.inherits = inherits;
        }

        @Override
        public final String name() {
            return name;
        }

        @Override
        public final Class[] inherits() {
            return inherits;
        }

        @Override
        public final Class<? extends Annotation> annotationType() {
            return StructSubtype.class;
        }    
    }
}
