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
package mg.jerytodik.scheduler.config;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import mg.jerytodik.scheduler.jobs.JerytodikResourcesArchiverJob;

/**
 * @author nabil andriantomanga
 */
@ComponentScan(basePackages = "mg.jerytodik")
@Configuration
@PropertySources({ @PropertySource("classpath:/mg/jerytodik/config/jerytodik-scheduler.properties"),
		/*
		 * Definir la variable d'environnement jerytodik_home pour ecraser les
		 * proprietes par defaut
		 */
		@PropertySource(value = "file:${jerytodik_home}/config/jerytodik-scheduler.properties", ignoreResourceNotFound = true) })
public class JeryTodikSchedulerConfig {

	@Autowired
	private ApplicationContext	applicationContext;

	@Autowired
	Environment					env;

	@Bean
	public JobDetailFactoryBean jobDetail() {

		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(JerytodikResourcesArchiverJob.class);
		jobDetailFactory.setDescription("Invocation du job d'archivage de ressources ...");
		jobDetailFactory.setDurability(true);

		return jobDetailFactory;
	}

	@Bean
	public SchedulerFactoryBean scheduler(final Trigger trigger, final JobDetail job) {

		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
		schedulerFactory.setJobFactory(springBeanJobFactory());
		schedulerFactory.setJobDetails(job);
		schedulerFactory.setTriggers(trigger);

		return schedulerFactory;
	}

	@Bean
	public SpringBeanJobFactory springBeanJobFactory() {

		AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);

		return jobFactory;
	}

	@Bean
	public SimpleTriggerFactoryBean trigger(final JobDetail job) {

		SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
		trigger.setJobDetail(job);
		trigger.setRepeatInterval(Long.parseLong(env.getProperty("jerytodik.scheduling.interval")));
		trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);

		return trigger;
	}
}
