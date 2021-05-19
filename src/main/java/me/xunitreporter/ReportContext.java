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

import java.util.ArrayList;
import java.util.List;

public class ReportContext {
    private int success;
    private int failed;
    private int skipped;
    private int errored;

    private List<String> failedNames = new ArrayList();
    private List<String> erroredNames = new ArrayList();
    private List<String> skippedNames = new ArrayList();

    public void reportTest(TestStatus status, String name, String extra) {
        switch (status) {
            case SUCCESS:
                success++;
                break;
            case FAILED:
                failed++;
                failedNames.add(name);
                break;
            case SKIPPED:
                skipped++;
                skippedNames.add(name);
                break;
            case ERRORED:
                errored++;
                erroredNames.add(name);
        }
    }

    public int getSuccess() {
        return success;
    }

    public int getFailed() {
        return failed;
    }

    public int getSkipped() {
        return skipped;
    }

    public int getErrored() {
        return errored;
    }

    public List<String> getFailedNames() {
        return failedNames;
    }

    public List<String> getErroredNames() {
        return erroredNames;
    }

    public List<String> getSkippedNames() {
        return skippedNames;
    }

    public boolean isEmpty() {
        return success + failed + skipped + errored == 0;
    }
}
