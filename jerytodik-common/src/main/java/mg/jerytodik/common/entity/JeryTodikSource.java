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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author nabil andriantomanga
 */
@Entity
@Table(name = "jt_source")
public class JeryTodikSource extends AbstractJeryTodikMutableData {

	private static final long	serialVersionUID	= -3125794748821301487L;

	/* traitement autorise sur la ressource ? */
	@Column(name = "jt_source_activated", nullable = false)
	private boolean				activated;

	@Column(name = "jt_source_description", length = 2000)
	private String				description;

	@Id
	@Column(name = "jt_source_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_jt_source")
	@SequenceGenerator(name = "seq_jt_source", sequenceName = "seq_jt_source", allocationSize = 1)
	private Long				id;

	@Column(name = "jt_source_name", nullable = false, length = 255)
	private String				name;

	@Column(name = "jt_source_start_date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date				startDate;

	/* url vers la ressource */
	@Column(name = "jt_source_url", nullable = false, length = 255)
	private String				url;

	public String getDescription() {
		return description;
	}

	@Override
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getUrl() {
		return url;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "JeryTodikSource [activated=" + activated + ", description=" + description + ", id=" + id + ", name="
				+ name + ", startDate=" + startDate + ", url=" + url + "]";
	}

}
