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
public class MavenLogScanner implements LogScanner {
    private static final Pattern PATTERN = Pattern.compile("(.*\\s)?Tests run: (\\d+), Failures: (\\d+), Errors: (\\d+), Skipped: (\\d+)$");
    @Override
    public void scan(ReportContext context, Reader r) {
        try {
            LineNumberReader lr = new LineNumberReader(r);
            String line;

            while ((line = lr.readLine()) != null) {
                Matcher m = PATTERN.matcher(line);
                if (m.matches()) {
                    int total = Integer.parseInt(m.group(2));
                    int failed = Integer.parseInt(m.group(3));
                    int errored = Integer.parseInt(m.group(4));
                    int skipped = Integer.parseInt(m.group(5));
                    context.report(total - failed - errored - skipped, failed, errored, skipped);
                }
            }
        } catch (IOException e) {
        }
    }
}
