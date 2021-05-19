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

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.ServiceLoader;

public interface ArtifactScanner {
    void scan(ReportContext context, InputStream artifact);

    static Iterator<ArtifactScanner> getProviders() {
        return ServiceLoader.load(ArtifactScanner.class).iterator();
    }
}
