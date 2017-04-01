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
package mg.jerytodik.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author nabil andriantomanga
 */
@MappedSuperclass
public abstract class AbstractJeriTodikRefData implements Serializable {

	private static final long serialVersionUID = -1729932131089686804L;

	@Column(name = "code", nullable = false, length = 4)
	protected String code;

	@Column(name = "label", nullable = false, length = 100)
	protected String label;

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

}
