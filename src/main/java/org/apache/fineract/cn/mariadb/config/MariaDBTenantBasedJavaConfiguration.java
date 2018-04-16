/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.cn.mariadb.config;

import org.apache.fineract.cn.mariadb.domain.ContextAwareRoutingDataSource;
import org.apache.fineract.cn.mariadb.util.JdbcUrlBuilder;
import org.apache.fineract.cn.mariadb.util.MariaDBConstants;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;

@SuppressWarnings("WeakerAccess")
@Configuration
@ConditionalOnProperty(prefix = "mariadb", name = "enabled", matchIfMissing = true)
public class MariaDBTenantBasedJavaConfiguration {
  @Bean
  public DataSource dataSource(@Qualifier(MariaDBConstants.LOGGER_NAME) final Logger logger,
                               final MetaDataSourceWrapper metaDataSource) {

    final ContextAwareRoutingDataSource dataSource = new ContextAwareRoutingDataSource(logger, JdbcUrlBuilder.DatabaseType.MARIADB);
    dataSource.setMetaDataSource(metaDataSource.getMetaDataSource());
    final HashMap<Object, Object> targetDataSources = new HashMap<>();
    dataSource.setTargetDataSources(targetDataSources);
    return dataSource;
  }
}
