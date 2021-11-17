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

public class MavenLogScannerTest {
    private ReportContext context;

    @BeforeEach
    void init() {
        context = new ReportContext();
    }
    
    @Test
    void checkMaven() {
        Path path = Paths.get("src/test/resources/logs_3.zip");
        LogProcessor.processLogs(context, path, Collections.singleton(new MavenLogScanner()), false);
        assertEquals(1, context.getSuccess());
        assertEquals(1, context.getFailed());
        assertEquals(0, context.getSkipped());
        assertEquals(1, context.getErrored());
    }

}
