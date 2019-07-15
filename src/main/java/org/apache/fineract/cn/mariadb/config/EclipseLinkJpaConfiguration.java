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

import org.eclipse.persistence.config.BatchWriting;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.config.TargetDatabase;
import org.eclipse.persistence.logging.SessionLog;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ebenezer Graham
 */

@Configuration
@EnableTransactionManagement
@EntityScan({
		"org.apache.fineract.cn.**.repository",
		"org.apache.fineract.cn.mariadb.util"
})
public class EclipseLinkJpaConfiguration extends JpaBaseConfiguration {
	
	
	protected EclipseLinkJpaConfiguration(DataSource dataSource, JpaProperties properties, ObjectProvider<JtaTransactionManager> jtaTransactionManagerProvider) {
		super(dataSource, properties, jtaTransactionManagerProvider);
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
	protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
		EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
		vendorAdapter.setShowSql(true); // Todo: remove sql log
		vendorAdapter.setDatabase(Database.MYSQL);
		vendorAdapter.setGenerateDdl(false);
		return vendorAdapter;
	}
	
	@Bean
	protected Map<String, Object> getVendorProperties() {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put(PersistenceUnitProperties.WEAVING, "static");
		properties.put(PersistenceUnitProperties.WEAVING_EAGER, "true");
		properties.put(PersistenceUnitProperties.TARGET_DATABASE, TargetDatabase.MySQL);
		properties.put(PersistenceUnitProperties.LOGGING_LEVEL, SessionLog.ALL_LABEL);// Todo: Reduce log level after test
		properties.put(PersistenceUnitProperties.LOGGING_PARAMETERS, "true");
		properties.put(PersistenceUnitProperties.DEPLOY_ON_STARTUP, "true");
		return properties;
	}
}
