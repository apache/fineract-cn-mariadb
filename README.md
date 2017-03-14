# Mifos I/O Core MariaDB

[![Join the chat at https://gitter.im/mifos-initiative/mifos.io](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/mifos-initiative/mifos.io?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

This project is an umbrella for all Mifos I/O Core components.

## Abstract
Mifos I/O is an application framework for digital financial services, a system to support nationwide and cross-national financial transactions and help to level and speed the creation of an inclusive, interconnected digital economy for every nation in the world.

## Prerequisites
### Runtime
Install Java 8 as described at https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html.

### Installation
Install MariaDB as described at https://mariadb.com/kb/en/mariadb/binary-packages/.

After installation you need to create the meta database:

    mysql -u root -pmysql
    CREATE DATABASE IF NOT EXISTS system_console;
    
## Multi-tenancy
Multi-tenancy is reached by providing separate data storage on a per tenant basis.

For every tenant a new database instance is created internally. A tenant aware component provides transparent access to these resources.

## Versioning
The version numbers follow the [Semantic Versioning](http://semver.org/) scheme.

In addition to MAJOR.MINOR.PATCH the following postfixes are used to indicate the development state.

* BUILD-SNAPSHOT - A release currently in development. 
* RELEASE - _General availability_ indicates that this release is the best available version and is recommended for all usage.

The versioning layout is {MAJOR}.{MINOR}.{PATCH}-{INDICATOR}[.{PATCH}]. Only milestones and release candidates can  have patch versions. Some examples:

1.2.3-BUILD-SNAPSHOT  
1.3.5-RELEASE

## License
See [LICENSE](LICENSE) file.
