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
import me.xunitreporter.TestStatus;
import org.apache.maven.plugin.surefire.log.api.NullConsoleLogger;
import org.apache.maven.plugins.surefire.report.TestSuiteXmlParser;
import org.xml.sax.SAXException;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@ApplicationScoped
public class SurefireArtifactScanner implements ArtifactScanner {
    @Override
    public void scan(ReportContext context, InputStream artifact) {
        ZipEntry entry;
        try (ZipInputStream stream = new ZipInputStream(artifact)) {
            while ((entry = stream.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    parseReport(context, stream);
                }
            }
        } catch (IOException e) {
        }
    }

    private void parseReport(ReportContext context, ZipInputStream artifact)  {
        TestSuiteXmlParser parser = new TestSuiteXmlParser(new NullConsoleLogger());
        try  {
            InputStreamReader reader = new InputStreamReader(artifact) {
                @Override
                public void close() throws IOException {
                }
            };
            parser.parse(reader).forEach(suite -> {
                suite.getTestCases().forEach(testCase -> {
                    if (testCase.hasError()) {
                        context.reportTest(TestStatus.ERRORED, testCase.getFullName(), null);
                    } else if (testCase.hasFailure()) {
                        context.reportTest(TestStatus.FAILED, testCase.getFullName(), null);
                    } else if (testCase.hasSkipped()) {
                        context.reportTest(TestStatus.SKIPPED, testCase.getFullName(), null);
                    } else  {
                        context.reportTest(TestStatus.SUCCESS, testCase.getFullName(), null);
                    }
                });
            });
        } catch (IOException | ParserConfigurationException | SAXException e) {
        }
    }
}
