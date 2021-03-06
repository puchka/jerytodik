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
package mg.jerytodik.scheduler.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mg.jerytodik.business.service.JeryTodikSourceService;

/**
 * @author nabil andriantomanga
 */
@Component
public class JerytodikResourcesArchiverJob implements Job {

	private static final Logger		LOGGER	= LoggerFactory.getLogger(JerytodikResourcesArchiverJob.class);

	@Autowired
	private JeryTodikSourceService	jeryTodikSourceService;

	@Override
	public void execute(final JobExecutionContext jerytodikExecutionContext) throws JobExecutionException {

		LOGGER.info("Job ** {} ** executed @ {}", jerytodikExecutionContext.getJobDetail().getKey().getName(),
				jerytodikExecutionContext.getFireTime());

		jeryTodikSourceService.archiveActiveResources();

		LOGGER.info("Next execution of Job @ {}", jerytodikExecutionContext.getNextFireTime());
	}
}
