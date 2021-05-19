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

import me.xunitreporter.spi.ArtifactScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

@ApplicationScoped
public class ArtifactProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArtifactProcessor.class);

    void processArtifact(ReportContext context, Path path) {
        try {
            Iterator<ArtifactScanner> scanners = ArtifactScanner.getProviders();
            while (scanners.hasNext()) {
                    scanners.next().scan(context, Files.newInputStream(path));
            }
            Files.delete(path);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
    }
}
