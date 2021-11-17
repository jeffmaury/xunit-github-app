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
package me.xunitreporter;

import me.xunitreporter.spi.LogScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@ApplicationScoped
public class LogProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogProcessor.class);

    @Inject
    Instance<LogScanner> scanners;

    public void processLogs(ReportContext context, Path path) {
        processLogs(context, path, scanners, true);
    }

    public static void processLogs(ReportContext context, Path path, Iterable<LogScanner> scanners, boolean delete) {
        ZipEntry entry;

        try {
            for (LogScanner scanner : scanners) {
                try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(path))) {
                    while ((entry = zis.getNextEntry()) != null) {
                        if (!entry.isDirectory() && entry.getName().indexOf('/') == (-1)) {
                            Reader r = new InputStreamReader(zis, StandardCharsets.UTF_8);
                            LOGGER.debug("Processing log " + path + " entry " + entry.getName() + " with scanner: " + scanner);
                            try {
                                scanner.scan(context, r);
                            } catch (Exception e) {
                                LOGGER.error(e.getLocalizedMessage(), e);
                            }
                        }
                    }
                }
            }
            if (delete) {
                Files.delete(path);
            }
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
    }
}
