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
package io.mifos.core.mariadb.domain;

import io.mifos.core.lang.ApplicationName;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class FlywayFactoryBean {

  private final ApplicationName applicationName;

  public FlywayFactoryBean(final ApplicationName applicationName) {
    super();
    this.applicationName = applicationName;
  }

  public Flyway create(final DataSource dataSource) {
    final Flyway flyway = new Flyway();
    flyway.setDataSource(dataSource);
    flyway.setLocations("db/migrations/mariadb");
    flyway.setTable(this.applicationName.getServiceName() + "_schema_version");
    flyway.setBaselineOnMigrate(true);
    flyway.setBaselineVersionAsString("0");
    return flyway;
  }
}
