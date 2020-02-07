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

import com.pragmaticobjects.oo.meta.anno.procesor.AssertAnnotationProcessorGeneratesFiles;
import com.pragmaticobjects.oo.tests.TestCase;
import com.pragmaticobjects.oo.tests.junit5.TestsSuite;
import io.vavr.collection.List;
import java.nio.file.Paths;

/**
 *
 * @author skapral
 */
public class SubtypingProcessorTest extends TestsSuite {
    public SubtypingProcessorTest() {
        super(
            /*new TestCase(
                "Test subtyping generation",
                new AssertAnnotationProcessorGeneratesFiles(
                    new SubtypingProcessor(),
                    Paths.get("com", "test", "package-info.java"),
                    String.join(
                        System.lineSeparator(),
                        "@StructSubtype(name = \"D\", inherits = {A.class, B.class, C.class})",
                        "package com.test;",
                        "import com.pragmaticobjects.oo.structsubtype.api.StructSubtype;"
                    ),
                    List.of(
                        new AssertAnnotationProcessorGeneratesFiles.File(
                            Paths.get("com", "test", "D.java"),
                            String.join(
                                System.lineSeparator(),
                                "package com.test;",
                                "",
                                "",
                                "public interface D extends A, B, C {",
                                "}",
                                ""
                            )
                        )
                    )
                )
            ),*/
            new TestCase(
                "Test subtyping generation -- complex scenario",
                new AssertAnnotationProcessorGeneratesFiles(
                    new SubtypingProcessor(),
                    Paths.get("com", "test", "package-info.java"),
                    String.join(
                        System.lineSeparator(),
                        "@StructSubtype(name = \"D\", inherits = {A.class, B.class, C.class})",
                        "@StructSubtype(name = \"E\", inherits = {A.class, B.class})",
                        "package com.test;",
                        "import com.pragmaticobjects.oo.structsubtype.api.StructSubtype;"
                    ),
                    List.of(
                        new AssertAnnotationProcessorGeneratesFiles.File(
                            Paths.get("com", "test", "D.java"),
                            String.join(
                                System.lineSeparator(),
                                "package com.test;",
                                "",
                                "",
                                "public interface D extends A, B, C, E {",
                                "}",
                                ""
                            )
                        ),
                        new AssertAnnotationProcessorGeneratesFiles.File(
                            Paths.get("com", "test", "E.java"),
                            String.join(
                                System.lineSeparator(),
                                "package com.test;",
                                "",
                                "",
                                "public interface E extends A, B {",
                                "}",
                                ""
                            )
                        )
                    )
                )
            )
        );
    }
}
