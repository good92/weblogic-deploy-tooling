+++
title = "Release Notes"
date = 2019-02-22T15:27:38-05:00
weight = 95
pre = "<b> </b>"
+++


### Changes in Release 3.0.0
- [Major New Features](#major-new-features)
- [Other Changes](#other-changes)
- [Bugs Fixes](#bug-fixes)
- [Known Issues](#known-issues)


#### Major New Features
- #1355: Added `-remote` option to the `deployApps` tool that supports deploying applications and shared libraries from a remote machine. (Issue #1312)
- #1355: Added `-remote` option to the `updateDomain` tool that allows configuration changes not requiring archive file changes
  (except for applications and shared libraries) to be made from a remote machine. (Issue #1167)
- #1365: Added new `verrazzano` section of the model that can be used to augment/override the YAML files generated by the Verrazzano-related `-target` options. (WDT-688)
- #1367: New `archiveHelper` tool for helping to create and update the archive file from the command line. (WDT-711)
- Cleaned up the aliases to clean up online discovery and put tests in place to ensure that they align properly with the
  various WebLogic Server releases and PSUs.  The resulting model from online discovery is now much closer to the one generated by offline discovery.

#### Other Changes
- #1255: Renamed model attributes to align with the offline naming strategy:

  - SystemComponent `MWHome` model attribute renamed to `MwHome`.
  - WebserviceSecurity `DefaultCredentialProviderSTSURI` model attribute renamed to `DefaultCredentialProviderStsuri`.

- #1299: Renamed model attributes to align with the offline naming strategy:

  - SecurityConfiguration `IdentityAssertionCacheTTL` model attribute renamed to `IdentityAssertionCacheTtl`.

- #1310: Resolved issue that was causing the PSU version of several 12.2.1.3 PSUs to be incorrect.
- #1314, #1342: Added knowledge to the aliases to understand how default values change when using `production mode` and `secure production mode`. (WDT-678)
- #1328: Changed tool exit code handling to exit with exit code 1 if warnings exist and exit code 2 if errors exist in the summary report. (WDT-692)
- #1331: Deprecated the old archive locations of the OPSS wallet and ATP wallet in the archive file.  The old locations will be
  honored but will result in one or more deprecation messages. (WDT-687)
- #1334: Removed support for storing the model files in the archive. (WDT-643)
- #1341: Remove deprecated `-domain_resource_file` argument from the `extractDomainResource` tool. (WDT-706)
- #1348: Renamed the old ATP wallet to `rcu` wallet. (WDT-709)
- #1348: Added support for adding wallets either as a ZIP file or an exploded directory with one or more files.  When the wallet is
  added as a ZIP file, the `createDomain` and `updateDomain` tools will expand the ZIP into the domain home, just as before.
- #1348: Added database wallet support for multiple wallets. Other database wallets can be added, as needed. (WDT-710)
- #1351: Removed support for the deprecated "named object list" format from the `kubernetes` section of the model. (WDT-688)
- #1366: Refactored ATP and SSL database support to be more unified in their modeling approach.
- #1369: Added a special log level for deprecation messages so that they can show up in the tools' summary reports without causing a non-zero exit status. (WDT-721)
- #1375: Revamped application installation directory discovery to capture the entire application installation directory. (WDT-715)
- #1379: Added the ability for discovery to collect the JDBC wallet file(s) in the archive.
- #1380: Updated the approach for generating the Verrazzano custom resource related to the Ingress Trait routing rules to add
  a destination host/port and allow the user to add Paths via the new `verrazzano` section of the model. (WDT-696)
- #1383: Removed the `DomainVersion` domain-level attribute from discovered models and replaced it with a comment describing the
  WDT and WebLogic Server version numbers used to discover the model and which WLST mode was used for discovery. (WDT-698)
- #1386: Changed attribute not valid in the current WebLogic version warnings to log `INFO` level messages instead. (WDT-724)
- #1389: Added additional model and WebLogic Kubernetes Operator/Verrazzano target validation around dynamic clusters to catch modeling issues prior to domain creation. (WDT-726)
- #1391: Revamped tool summary report and remote report generated when using remote discovery. (WDT-637)
- #1395: Added unclustered servers to the WKTUI integration output of `prepareModel` (WDT-730)

#### Bug Fixes
- #1261, #1266: Corrected a `discoverDomain` error message when a keystore was missing. (WDT-685)
- #1324: Resolved an issue causing secure mode not to be discovered. (WDT-694)
- #1327: Resolved an issue where WLST errors in WLS 12.2.1.0.0 were showing up in the tools' summary reports. (WDT-691)
- #1329: Changed the tools summary log handler to not accept any messages that did not originate in WDT proper. (WDT-701)
- #1330: Fixed a Windows issue with environment variable substitution where environment variable references in the model that were not all uppercase were not working properly.
- #1335: Removed unused and undocumented `-prev_model_file` argument from the `deployApps` and `updateDomain` tools. (WDT-707)
- #1344: Removed `-archive_file` argument from the `extractDomainResource` tool since the model can no longer be stored in the archive file. (WDT-708)
- #1367: Corrected the behavior of the archive class to rename directories when an existing directory of the same name already exists.
  This makes the behavior for files and directories consistent. (WDT-722)
- #1374: Corrected a problem discovering a domain using a Coherence cluster with a custom configuration file.
- #1376: Corrected an issue with `compareModel` pertaining to comparisons of the JVM arguments and whitespace (Issue #1370)
- #1382: Removed misleading log entries during startup that displayed the WebLogic Server version with no PSU information (WDT-727)
- #1392: Fixed an issue with online discovery of domains with dynamic clusters where the dynamic servers were erroneously being added as configured servers.
- #1393: Fixed an issue causing an empty `SecureMode` folder to show up in an online discovered model. (WDT-731)
- #1394: Fixed alias entries to prevent some `INFO` messages with errors related to alias entries during online discovery.

#### Known Issues
1. When running `discoverDomain` with the `-remote` flag, there are several MBeans that are not being properly handled that
   will result in `INFO` level messages that look similar to the example shown below.  These errors seem to happen only when the MBean is
   non-existent so the resulting model should still be accurate.  These issues are expected to be fixed in a future release.

```
####<Feb 16, 2023 1:40:00 PM> <INFO> <Discoverer> <_add_to_dictionary> <WLSDPLY-06106> <Unable to add ServerFailureTrigger
from location /Clusters/mycluster/OverloadProtection/mycluster to the model : Failed to convert the wlst attribute name and
value for the model at location (model_folders = ['Cluster', 'OverloadProtection'],  'name_tokens' = {'DOMAIN': 'tododomain',
'CLUSTER': 'mycluster','OVERLOADPROTECTION': 'mycluster'}) : The wlst attribute ServerFailureTrigger is not defined for the
model folder /Cluster/OverloadProtection>
```