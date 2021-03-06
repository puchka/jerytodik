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

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.jerytodik.common.entity.JeryTodikHistory;

/**
 * @author nabil andriantomanga
 */
@Repository(value = "jeryTodikHistoryRepository")
public interface JeryTodikHistoryRepository extends JpaRepository<JeryTodikHistory, Long> {

	/**
	 * Permet d'avoir la liste des historiques relatifs a la source dont
	 * l'identifiant est specifie
	 * 
	 * @param sourceId
	 * @return la liste des historiques
	 */
	List<JeryTodikHistory> findBySource(final Long sourceId);
}
