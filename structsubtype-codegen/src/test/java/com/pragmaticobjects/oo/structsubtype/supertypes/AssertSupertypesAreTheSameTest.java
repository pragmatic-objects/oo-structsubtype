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
import com.pragmaticobjects.oo.tests.AssertAssertionFails;
import com.pragmaticobjects.oo.tests.AssertAssertionPasses;
import com.pragmaticobjects.oo.tests.TestCase;
import com.pragmaticobjects.oo.tests.junit5.TestsSuite;

/**
 *
 * @author skapral
 */
public class AssertSupertypesAreTheSameTest extends TestsSuite {
    public AssertSupertypesAreTheSameTest() {
        super(
            new TestCase(
                "Pass scenario",
                new AssertAssertionPasses(
                    new AssertSupertypesAreTheSame(
                        new FixedSupertypes(
                            new TypeReferential("com.test", "A"),
                            new TypeReferential("com.test", "B")
                        ),
                        new FixedSupertypes(
                            new TypeReferential("com.test", "B"),
                            new TypeReferential("com.test", "A")
                        )
                    )
                )
            ),
            new TestCase(
                "Fail scenario - extra element",
                new AssertAssertionFails(
                    new AssertSupertypesAreTheSame(
                        new FixedSupertypes(
                            new TypeReferential("com.test", "A"),
                            new TypeReferential("com.test", "B")
                        ),
                        new FixedSupertypes(
                            new TypeReferential("com.test", "A"),
                            new TypeReferential("com.test", "B"),
                            new TypeReferential("com.test", "C")
                        )
                    )
                )
            ),
            new TestCase(
                "Fail scenario - missing element",
                new AssertAssertionFails(
                    new AssertSupertypesAreTheSame(
                        new FixedSupertypes(
                            new TypeReferential("com.test", "A"),
                            new TypeReferential("com.test", "B")
                        ),
                        new FixedSupertypes(
                            new TypeReferential("com.test", "A")
                        )
                    )
                )
            )
        );
    }
}
