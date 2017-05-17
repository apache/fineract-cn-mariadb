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
package io.mifos.core.mariadb.config;


import com.jolbox.bonecp.BoneCPDataSource;
import io.mifos.core.lang.ApplicationName;
import io.mifos.core.lang.config.EnableApplicationName;
import io.mifos.core.mariadb.domain.FlywayFactoryBean;
import io.mifos.core.mariadb.util.JdbcUrlBuilder;
import io.mifos.core.mariadb.util.MariaDBConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@SuppressWarnings("WeakerAccess")
@Configuration
@ConditionalOnProperty(prefix = "mariadb", name = "enabled", matchIfMissing = true)
@EnableTransactionManagement
@EnableApplicationName
public class MariaDBJavaConfiguration {

  private final Environment env;

  @Autowired
  public MariaDBJavaConfiguration(final Environment env) {
    super();
    this.env = env;
  }

  @Bean(name = MariaDBConstants.LOGGER_NAME)
  public Logger logger() {
    return LoggerFactory.getLogger(MariaDBConstants.LOGGER_NAME);
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource) {
    final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setPersistenceUnitName("metaPU");
    em.setDataSource(dataSource);
    em.setPackagesToScan("io.mifos.**.repository");

    final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaProperties(additionalProperties());

    return em;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    final JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);
    return transactionManager;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  @Bean
  public FlywayFactoryBean flywayFactoryBean(final ApplicationName applicationName) {
    return new FlywayFactoryBean(applicationName);
  }

  @Bean
  public MetaDataSourceWrapper metaDataSourceWrapper() {

    final BoneCPDataSource boneCPDataSource = new BoneCPDataSource();
    boneCPDataSource.setDriverClass(
            this.env.getProperty(MariaDBConstants.MARIADB_DRIVER_CLASS_PROP, MariaDBConstants.MARIADB_DRIVER_CLASS_DEFAULT));
    boneCPDataSource.setJdbcUrl(JdbcUrlBuilder
            .create(JdbcUrlBuilder.DatabaseType.MARIADB)
            .host(this.env.getProperty(MariaDBConstants.MARIADB_HOST_PROP, MariaDBConstants.MARIADB_HOST_DEFAULT))
            .port(this.env.getProperty(MariaDBConstants.MARIADB_PORT_PROP, MariaDBConstants.MARIADB_PORT_DEFAULT))
            .instanceName(this.env.getProperty(MariaDBConstants.MARIADB_DATABASE_NAME_PROP, MariaDBConstants.MARIADB_DATABASE_NAME_DEFAULT))
            .build());
    boneCPDataSource.setUsername(
            this.env.getProperty(MariaDBConstants.MARIADB_USER_PROP, MariaDBConstants.MARIADB_USER_DEFAULT));
    boneCPDataSource.setPassword(
            this.env.getProperty(MariaDBConstants.MARIADB_PASSWORD_PROP, MariaDBConstants.MARIADB_PASSWORD_DEFAULT));
    boneCPDataSource.setIdleConnectionTestPeriodInMinutes(
            Long.valueOf(this.env.getProperty(MariaDBConstants.BONECP_IDLE_CONNECTION_TEST_PROP, MariaDBConstants.BONECP_IDLE_CONNECTION_TEST_DEFAULT)));
    boneCPDataSource.setIdleMaxAgeInMinutes(
            Long.valueOf(this.env.getProperty(MariaDBConstants.BONECP_IDLE_MAX_AGE_PROP, MariaDBConstants.BONECP_IDLE_MAX_AGE_DEFAULT)));
    boneCPDataSource.setMaxConnectionsPerPartition(
            Integer.valueOf(this.env.getProperty(MariaDBConstants.BONECP_MAX_CONNECTION_PARTITION_PROP, MariaDBConstants.BONECP_MAX_CONNECTION_PARTITION_DEFAULT)));
    boneCPDataSource.setMinConnectionsPerPartition(
            Integer.valueOf(this.env.getProperty(MariaDBConstants.BONECP_MIN_CONNECTION_PARTITION_PROP, MariaDBConstants.BONECP_MIN_CONNECTION_PARTITION_DEFAULT)));
    boneCPDataSource.setPartitionCount(
            Integer.valueOf(this.env.getProperty(MariaDBConstants.BONECP_PARTITION_COUNT_PROP, MariaDBConstants.BONECP_PARTITION_COUNT_DEFAULT)));
    boneCPDataSource.setAcquireIncrement(
            Integer.valueOf(this.env.getProperty(MariaDBConstants.BONECP_ACQUIRE_INCREMENT_PROP, MariaDBConstants.BONECP_ACQUIRE_INCREMENT_DEFAULT)));
    boneCPDataSource.setStatementsCacheSize(
            Integer.valueOf(this.env.getProperty(MariaDBConstants.BONECP_STATEMENT_CACHE_PROP, MariaDBConstants.BONECP_STATEMENT_CACHE_DEFAULT)));

    final Properties driverProperties = new Properties();
    driverProperties.setProperty("useServerPrepStmts", "false");
    boneCPDataSource.setDriverProperties(driverProperties);
    return new MetaDataSourceWrapper(boneCPDataSource);
  }

  private Properties additionalProperties() {
    final Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    return properties;
  }
}
