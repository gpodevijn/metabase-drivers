Metabase's Redshift Spectrum driver
===================================

Metabase is shipped with a Redshift driver. Unfortunately, this driver doesn't support external tables. It is therefore not possible to discover and query data stored in S3.

This repository contains a Redshift driver that supports external tables.


Compilation
------------

For a complete documentation, please refer to Metabase's documentation: https://github.com/metabase/metabase/wiki/Writing-a-Driver:-Packaging-a-Driver-&-Metabase-Plugin-Basics

You need to install `Leiningen` (https://leiningen.org) to compile both Metabase and the driver.

### Compile Metabase

Clone metabase from GitHub: https://github.com/metabase/metabase

#### Install metabase-core locally

The dependency on metabase-core makes all namespaces that are part of the core Metabase project (e.g. metabase.driver) available for use in the driver itself. By putting this dependency in a provided profile, lein uberjar won't include that dependency in the built driver. Run the following command from the root of the core Metabase repository:

`lein install-for-building-drivers`

### Compile the driver

From the source directory of the driver, run the following command to build the JAR:

`LEIN_SNAPSHOTS_IN_RELEASE=true lein uberjar`

It creates the driver JAR  `target/uberjar/redshiftscpectrum.metabase-driver.jar` 

You can copy the jar in the `/plugins/` directory of Metabase.


