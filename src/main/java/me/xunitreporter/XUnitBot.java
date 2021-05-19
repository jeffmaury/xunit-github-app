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

import io.quarkiverse.githubapp.ConfigFile;
import io.quarkiverse.githubapp.event.WorkflowRun;
import me.xunitreporter.config.XUnitReporterConfigFile;
import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;
import org.kohsuke.github.GHArtifact;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.PagedIterator;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class XUnitBot {

    private final static Logger LOGGER = Logger.getLogger(XUnitBot.class);

    private static final String CONFIG_FILE_NAME = "xunit-reporter.yml";

    @Inject
    ArtifactProcessor artifactProcessor;

    void onWorkflow(@WorkflowRun.Completed GHEventPayload.WorkflowRun workflowRunPayload, @ConfigFile(CONFIG_FILE_NAME) XUnitReporterConfigFile config) throws IOException {
        if (config == null) {
            config = XUnitReporterConfigFile.DEFAULT;
        }
        PagedIterator<GHArtifact> artifacts = workflowRunPayload.getWorkflowRun().listArtifacts().iterator();
        ReportContext context = new ReportContext();
        while (artifacts.hasNext()) {
            try {
                GHArtifact artifact = artifacts.next();
                artifactProcessor.processArtifact(context, artifact.download(s -> toPath(s)));
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }
        }
        if (!context.isEmpty()) {
            GHCommit commit = workflowRunPayload.getRepository().getCommit(workflowRunPayload.getWorkflowRun().getHeadSha());
            commit.createComment(Templates.report(config, context).render());
        }
    }

    private Path toPath(InputStream s) throws IOException {
        Path path = Files.createTempFile("artifact", "zip");
        Files.copy(s, path, StandardCopyOption.REPLACE_EXISTING);
        return path;
    }
}
