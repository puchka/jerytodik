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
package mg.jerytodik.scheduler.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.quartz.ee.servlet.QuartzInitializerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import mg.jerytodik.scheduler.config.JeryTodikSchedulerConfig;

/**
 * @author nabil andriantomanga
 */
@WebListener
public class JerytodikInitializerListener extends QuartzInitializerListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(JerytodikInitializerListener.class);

	@SuppressWarnings("resource")
	@Override
	public void contextInitialized(final ServletContextEvent contextEvent) {
		super.contextInitialized(contextEvent);

		final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				JeryTodikSchedulerConfig.class);

		final SchedulerFactoryBean schedulerFactoryBean = applicationContext.getBean(SchedulerFactoryBean.class);

		try {
			schedulerFactoryBean.start();
		} catch (Exception e) {
			LOGGER.error("Error occured.", e);
		}
	}
}
