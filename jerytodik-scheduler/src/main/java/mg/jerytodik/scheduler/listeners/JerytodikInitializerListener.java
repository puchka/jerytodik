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

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

import mg.jerytodik.scheduler.jobs.JerytodikResourcesArchiverJob;

/**
 * @author nabil andriantomanga
 */
@WebListener
public class JerytodikInitializerListener extends QuartzInitializerListener {

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		super.contextInitialized(contextEvent);

		ServletContext ctx = contextEvent.getServletContext();
		StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QUARTZ_FACTORY_KEY);

		try {
			Scheduler scheduler = factory.getScheduler();
			JobDetail jobDetail = JobBuilder.newJob(JerytodikResourcesArchiverJob.class).build();

			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simple")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * 1/1 * ? *")).startNow().build();

			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();

		} catch (Exception e) {
			ctx.log("Une erreur est survenue lors de l'exécution du job.", e);
		}
	}

}