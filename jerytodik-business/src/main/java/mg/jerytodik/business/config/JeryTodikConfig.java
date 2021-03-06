
/*
 * Licensed to JeryTodik under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. JeryTodik licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package mg.jerytodik.business.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import mg.jerytodik.common.utility.HibernateUtil;

/**
 * @author nabil andriantomanga
 */
@ComponentScan(basePackages = "mg.jerytodik.business")
@Configuration
@EnableJpaRepositories(basePackages = "mg.jerytodik.business.dao")
@EnableTransactionManagement
@PropertySources({ @PropertySource("classpath:/mg/jerytodik/config/database.properties"),
		@PropertySource("classpath:/mg/jerytodik/config/jerytodik.properties"),
		@PropertySource("classpath:/mg/jerytodik/config/hibernate.properties"),
		/* override propertie */
		@PropertySource(value = "file:${jerytodik_home}/config/database.properties", ignoreResourceNotFound = true),
		@PropertySource(value = "file:${jerytodik_home}/config/hibernate.properties", ignoreResourceNotFound = true),
		@PropertySource(value = "file:${jerytodik_home}/config/jerytodik.properties", ignoreResourceNotFound = true) })
public class JeryTodikConfig {

	@Autowired
	Environment env;

	@Bean
	public DataSource dataSource() {

		DriverManagerDataSource ds = new DriverManagerDataSource();

		ds.setDriverClassName(env.getProperty("db.driver"));
		ds.setUrl(env.getProperty("db.url"));
		ds.setUsername(env.getProperty("db.username"));
		ds.setPassword(env.getProperty("db.password"));

		return ds;
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan("mg.jerytodik.common.entity");
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);

		Properties properties = new Properties();

		properties.put(HibernateUtil.HIBERNATE_DIALECT, env.getProperty(HibernateUtil.HIBERNATE_DIALECT));
		properties.put(HibernateUtil.HIBERNATE_SHOW_SQL, env.getProperty(HibernateUtil.HIBERNATE_SHOW_SQL));
		properties.put(HibernateUtil.HIBERNATE_FORMAT_SQL, env.getProperty(HibernateUtil.HIBERNATE_FORMAT_SQL));
		properties.put(HibernateUtil.HIBERNATE_HBM2DDL_AUTO, env.getProperty(HibernateUtil.HIBERNATE_HBM2DDL_AUTO));

		entityManagerFactoryBean.setJpaProperties(properties);
		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();

		transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
		return transactionManager;
	}

}
