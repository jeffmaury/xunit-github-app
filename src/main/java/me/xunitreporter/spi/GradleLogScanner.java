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

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
public class GradleLogScanner implements LogScanner {
    private static final Pattern GRADLE_TEST_LOGGING_PATTERN = Pattern.compile(".*(\\d+) tests completed(, (\\d+) failed)?(, (\\d+) skipped)?$");
    @Override
    public void scan(ReportContext context, Reader r) {
        try {
            LineNumberReader lr = new LineNumberReader(r);
            String line;

            while ((line = lr.readLine()) != null) {
                Matcher m = GRADLE_TEST_LOGGING_PATTERN.matcher(line);
                if (m.matches()) {
                    int total = Integer.parseInt(m.group(1));
                    int failed = Integer.parseInt(m.group(3));
                    int skipped = m.start(4)!=(-1)?Integer.parseInt(m.group(5)):0;
                    context.report(total - failed - skipped, failed, 0, skipped);
                }
            }
        } catch (IOException e) {
        }
    }
}
