/*******************************************************************************
 * Copyright (c) 2021 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package me.xunitreporter.spi;

import me.xunitreporter.ReportContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SurefireArtifactScannerTest {
    private static SurefireArtifactScanner scanner;
    private ReportContext context;

    @BeforeAll
    static void setup() {
        scanner = new SurefireArtifactScanner();
    }
    
    @BeforeEach
    void init() {
        context = new ReportContext();
    }
    
    @Test
    void checkEmpty() {
        InputStream stream = SurefireArtifactScannerTest.class.getResourceAsStream("/empty.zip");
        scanner.scan(context, stream);
        assertEquals(0, context.getSuccess());
        assertEquals(0, context.getFailed());
        assertEquals(0, context.getSkipped());
        assertEquals(0, context.getErrored());
    }

    @Test
    void checkGradle() {
        InputStream stream = SurefireArtifactScannerTest.class.getResourceAsStream("/surefire/gradle-test-results.zip");
        scanner.scan(context, stream);
        assertEquals(62, context.getSuccess());
        assertEquals(3, context.getFailed());
        assertEquals(0, context.getSkipped());
        assertEquals(0, context.getErrored());
    }
}
