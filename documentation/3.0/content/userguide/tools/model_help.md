---
title: "Model Help Tool"
date: 2019-02-23T17:19:24-05:00
draft: false
weight: 11
description: "Provides information about the folders and attributes that are valid for sections and folders of a domain model."
---


The Model Help Tool provides information about the folders and attributes that are valid for sections and folders of a domain model. This is useful when creating a new domain model, or expanding an existing model, including discovered models.

{{% notice note %}} The Model Help Tool is new in WebLogic Deploy Tooling 1.8.
The `-model_sample` argument is deprecated starting with WebLogic Deploy Tooling 1.9.2, when model sample became the default output format.
{{% /notice %}}

Here is a simple example using the Model Help Tool:
```yaml
$ <wls-deploy-home>/bin/modelHelp.sh -oracle_home /tmp/oracle resources:/JDBCSystemResource
```
This will result in the following output:
```yaml
Attributes and sub-folders for resources:/JDBCSystemResource

resources:
    JDBCSystemResource:
        'JDBC-1':
            CompatibilityName:       # string            +
            DeploymentOrder:         # integer           (default=100) +
            DeploymentPrincipalName: # string            +
            DescriptorFileName:      # string            +
            ModuleType:              # string            +
            Notes:                   # string            +
            SourcePath:              # string            +
            Target:                  # delimited_string  +

            JdbcResource:
                # see /JDBCSystemResource/JdbcResource

            SubDeployment:
                'SubDeployment-1':
                    # see /JDBCSystemResource/SubDeployment


This bean defines a system-level JDBC resource.  It links a separate
descriptor that specifies the definition.
```
This output shows the eight attributes and two sub-folders available for the `JDBCSystemResource` folder in the `resources` section of the model. Each attribute includes a comment describing the type of the value to be added.
A plus sign (`+`) on an attribute indicates that appending the attribute to the model path will yield more information about the attribute.

Folders that support multiple instances, such as `JDBCSystemResource` in this example, are shown with a derived name, such as `'JDBC-1'`.

Each sub-folder includes a comment with a model path that can be used to display additional information about that sub-folder. For example, to determine the attributes and sub-folders for `'SubDeployment-1'`, the Model Help Tool could be re-invoked with the model path from the comment:
```yaml
$ <wls-deploy-home>/bin/modelHelp.sh -oracle_home /tmp/oracle -model_sample /JDBCSystemResource/SubDeployment
```
You can use the information above to construct this model section:
```yaml
resources:
    JDBCSystemResource:
        JDBC-1:
            # JDBCSystemResource attributes and folders
            CompatibilityName: myName
            DeploymentOrder: 5
            Target: ms1,ms2
            SubDeployment:
                deployment1:
                    # SubDeployment attributes and folders
                deployment2:
                    # SubDeployment attributes and folders
```
If you are copying elements from the sample model to create a full domain model, you should exclude any attributes or sub-folders that you do not intend to declare or override.

#### Path patterns
There are a number of ways to specify model location in the path argument. Here are some examples:

List all the top-level model sections, such as `topology`, `resources`, and such:
```yaml
top
```

List the attributes and folders within a section, such as `topology`, `resources`, and such:
```yaml
topology
```

List all the attributes and folders within a folder:
```yaml
resources:/JDBCSystemResource/JdbcResource
```

If the section is not provided for a folder, then it will be derived and included in the output text:
```yaml
/JDBCSystemResource/JdbcResource
```

#### Per attribute help
To show help for a particular attribute in a folder, simply add it to the model path.

For example:
```yaml
$ modelHelp.sh topology:/Server/Log/StdoutSeverity
```
The output is:
```yaml
Attributes and sub-folders for topology:/Server/Log/StdoutSeverity

topology:
    Server:
        'Server-1':
            Log:
                StdoutSeverity: # string

Default=Notice
Legal values:
   'Trace'
   'Debug'
   'Info'
   'Warning'
   'Error'
   'Notice'
   'Critical'
   'Alert'
   'Emergency'
   'Off'


The minimum severity of log messages going to the standard out.
Messages with a lower severity than the specified value will
not be published to standard out.
```


#### Output options
There are several command-line options that you can use to control the output text for the model path. Use only one of these options at a time. If no output options are specified, then the attributes and immediate sub-folders for the specified path are listed.

{{% notice note %}}
When the top sections are listed using the path ```top```, any output options are ignored.  
{{% /notice %}}

##### ```-attributes_only```
This option will list only the attributes for the specified path.

##### ```-folders_only```
This option will list only the immediate sub-folders for the specified path.

##### ```-recursive```
This option will recursively list all the sub-folders within the specified path. No attributes are listed.

Here is an example using the `-recursive` option:
```yaml
$ <wls-deploy-home>/bin/modelHelp.sh -oracle_home /tmp/oracle -recursive resources:/JDBCSystemResource
```
The output is:
```yaml
Recursive sub-folders only for resources:/JDBCSystemResource

resources:
    JDBCSystemResource:
        'JDBC-1':
            JdbcResource:
                JDBCConnectionPoolParams:
                JDBCDataSourceParams:
                JDBCDriverParams:
                    Properties:
                        'Properties-1':
                JDBCOracleParams:
                JDBCXAParams:
            SubDeployment:
                'SubDeployment-1':
```

#### Interactive option
To access an interactive command line for exploring model paths using a directory style syntax, use `modelHelp.sh -interactive <starting_model_path>`.

For example:
```yaml
$ modelHelp.sh -interactive top
```
The output is:
```yaml
In interactive mode! Type 'help' for help.
[top] --> help

Commands:

  ls                      - list contents of current location
  top, cd, cd /, cd top   - go to "top"
  cd x[/[...]]            - relative change (go to child location x...)
  cd section[:/[...]]     - absolute change (go to exact section and location)
  cd /folder[/...]        - find section that contains the folder and go there
  cd ..                   - go up
  history                 - history of visited locations
  exit                    - exit

Sections:

  domainInfo, topology, resources, appDeployments, kubernetes

Examples:

  cd topology
  cd topology:/Server/Log/StdoutSeverity
  cd /Server/Log/StdoutSeverity

[top] -->   
```

### Parameter table for `model_help`
| Parameter | Definition | Default |
| ---- | ---- | ---- |
| `-attributes_only` | List only the attributes for the specified model path. |    |
| `-folders_only` | List only the folders for the specified model path. |    |
| `-oracle_home` | Home directory of the Oracle WebLogic installation. Required if the `ORACLE_HOME` environment variable is not set. |    |
| `-recursive` | List only the folders for the specified model path, and recursively include the folders below that path. |    |
| `<model_path>` | The path to the model element to be examined. The format is `[^<section^>:][/^<folder^>]...` |    |
| `-interactive <starting_model_path>` | Interactive mode. |   |
