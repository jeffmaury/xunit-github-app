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
package me.xunitreporter.config;

import org.apache.commons.lang3.StringUtils;

public class XUnitReporterConfigFile {
    public static XUnitReporterConfigFile DEFAULT = new XUnitReporterConfigFile();

    String reports;
    boolean extendedReport = false;

    public String getReports() {
        if (StringUtils.isBlank(reports)) {
            return ".*test.*";
        }
        return reports;
    }

    public boolean isExtendedReport() {
        return extendedReport;
    }
}
