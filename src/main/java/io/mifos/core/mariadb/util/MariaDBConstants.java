/*
 * Copyright 2017 The Mifos Initiative.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mifos.core.mariadb.util;

public interface MariaDBConstants {

  String LOGGER_NAME = "maridb-logger";

  String MARIADB_DRIVER_CLASS_PROP = "mariadb.driverClass";
  String MARIADB_DRIVER_CLASS_DEFAULT = "org.mariadb.jdbc.Driver";
  String MARIADB_DATABASE_NAME_PROP = "mariadb.database";
  String MARIADB_DATABASE_NAME_DEFAULT = "seshat";
  String MARIADB_HOST_PROP = "mariadb.host";
  String MARIADB_HOST_DEFAULT = "localhost";
  String MARIADB_PORT_PROP = "mariadb.port";
  String MARIADB_PORT_DEFAULT = "3306";
  String MARIADB_USER_PROP = "mariadb.user";
  String MARIADB_USER_DEFAULT = "root";
  String MARIADB_PASSWORD_PROP = "mariadb.password";
  String MARIADB_PASSWORD_DEFAULT = "mysql";

  String BONECP_IDLE_MAX_AGE_PROP = "bonecp.idleMaxAgeInMinutes";
  String BONECP_IDLE_MAX_AGE_DEFAULT = "240";
  String BONECP_IDLE_CONNECTION_TEST_PROP = "bonecp.idleConnectionTestPeriodInMinutes";
  String BONECP_IDLE_CONNECTION_TEST_DEFAULT = "60";
  String BONECP_MAX_CONNECTION_PARTITION_PROP = "bonecp.maxConnectionsPerPartition";
  String BONECP_MAX_CONNECTION_PARTITION_DEFAULT = "16";
  String BONECP_MIN_CONNECTION_PARTITION_PROP = "bonecp.minConnectionsPerPartition";
  String BONECP_MIN_CONNECTION_PARTITION_DEFAULT = "4";
  String BONECP_PARTITION_COUNT_PROP = "bonecp.partitionCount";
  String BONECP_PARTITION_COUNT_DEFAULT = "2";
  String BONECP_ACQUIRE_INCREMENT_PROP = "bonecp.acquireIncrement";
  String BONECP_ACQUIRE_INCREMENT_DEFAULT = "4";
  String BONECP_STATEMENT_CACHE_PROP = "bonecp.statementsCacheSize";
  String BONECP_STATEMENT_CACHE_DEFAULT = "128";
}
