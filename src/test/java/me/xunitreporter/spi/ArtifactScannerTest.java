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

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class ArtifactScannerTest {

    @Inject
    Instance<ArtifactScanner> scanners;

    @Test
    void testProviders() {
        assertTrue(scanners.iterator().hasNext());
    }
}
