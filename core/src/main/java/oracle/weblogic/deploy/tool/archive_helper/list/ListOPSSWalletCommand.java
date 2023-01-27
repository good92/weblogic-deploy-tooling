/*
 * Copyright (c) 2023, Oracle and/or its affiliates.
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl.
 */
package oracle.weblogic.deploy.tool.archive_helper.list;

import oracle.weblogic.deploy.logging.PlatformLogger;
import oracle.weblogic.deploy.logging.WLSDeployLogFactory;
import oracle.weblogic.deploy.tool.archive_helper.CommandResponse;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import static oracle.weblogic.deploy.tool.ArchiveHelper.LOGGER_NAME;
import static oracle.weblogic.deploy.util.WLSDeployArchive.ArchiveEntryType.OPSS_WALLET;

@Command(
    name = "opssWallet",
    header = "List OPSS wallet entries in the archive file.",
    description = "%nCommand-line options:",
    sortOptions = false
)
public class ListOPSSWalletCommand extends ListTypeCommandBase {
    private static final String CLASS = ListOPSSWalletCommand.class.getName();
    private static final PlatformLogger LOGGER = WLSDeployLogFactory.getLogger(LOGGER_NAME);

    @Option(
        names = { "-help" },
        description = "Get help for the archiveHelper list opssWallet subcommand",
        usageHelp = true
    )
    private boolean helpRequested = false;

    @Override
    public CommandResponse call() throws Exception {
        final String METHOD = "call";
        LOGGER.entering(CLASS, METHOD);

        CommandResponse response = listType(OPSS_WALLET, "OPSS wallet", null);

        LOGGER.exiting(CLASS, METHOD, response);
        return response;
    }
}
