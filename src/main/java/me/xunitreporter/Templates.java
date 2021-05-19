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

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import me.xunitreporter.config.XUnitReporterConfigFile;

@CheckedTemplate
public class Templates {
    public static native TemplateInstance report(XUnitReporterConfigFile config, ReportContext context);
}
