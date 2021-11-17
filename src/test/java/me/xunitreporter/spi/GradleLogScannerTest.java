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

import me.xunitreporter.LogProcessor;
import me.xunitreporter.ReportContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GradleLogScannerTest {
    private ReportContext context;

    @BeforeEach
    void init() {
        context = new ReportContext();
    }
    
    @Test
    void checkFailedAndSkipped() {
        Path path = Paths.get("src/test/resources/gradle-failed-skipped.zip");
        LogProcessor.processLogs(context, path, Collections.singleton(new GradleLogScanner()), false);
        assertEquals(1, context.getSuccess());
        assertEquals(1, context.getFailed());
        assertEquals(1, context.getSkipped());
        assertEquals(0, context.getErrored());
    }

    @Test
    void checkFailed() {
        Path path = Paths.get("src/test/resources/gradle-failed.zip");
        LogProcessor.processLogs(context, path, Collections.singleton(new GradleLogScanner()), false);
        assertEquals(1, context.getSuccess());
        assertEquals(1, context.getFailed());
        assertEquals(0, context.getSkipped());
        assertEquals(0, context.getErrored());
    }

    @Test
    void checkFailedFromGH() {
        Path path = Paths.get("src/test/resources/logs_2383.zip");
        LogProcessor.processLogs(context, path, Collections.singleton(new GradleLogScanner()), false);
        assertEquals(429, context.getSuccess());
        assertEquals(30, context.getFailed());
        assertEquals(3, context.getSkipped());
        assertEquals(0, context.getErrored());
    }
}
