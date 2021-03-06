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
package mg.jerytodik.business.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mg.jerytodik.business.config.JeryTodikConfig;
import mg.jerytodik.common.entity.JeryTodikSource;

/**
 * @author nabil andriantomanga
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JeryTodikConfig.class)

public class JeryTodikSourceRepositoryTest {

	@Autowired
	private JeryTodikSourceRepository	jeriTodikSourceRepository;

	@Autowired
	private JeryTodikHistoryRepository	jeryTodikHistoryRepository;

	@Ignore
	@Test
	public void testFindAllActivatedJeriTodikSource() {

		JeryTodikSource s = new JeryTodikSource();
		s.setName("Affaires.mg");
		s.setDescription("Site de petites annonces");
		s.setUrl("http://www.affaires.mg");
		s.setActivated(true);
		s.setStartDate(new Date());
		jeriTodikSourceRepository.save(s);

		final List<JeryTodikSource> sources = jeriTodikSourceRepository.findAllActivatedJeriTodikSource();

		assertFalse(sources.isEmpty());
	}

}
