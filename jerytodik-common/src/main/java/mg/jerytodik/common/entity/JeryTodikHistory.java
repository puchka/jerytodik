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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author nabil andriantomanga
 */
@Entity
@Table(name = "jt_history")
public class JeryTodikHistory extends AbstractJeryTodikMutableData {
	private static final long	serialVersionUID	= 6029965767959324902L;

	@Column(name = "jt_history_content", length = 100)
	private String				content;

	@Column(name = "jt_history_date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date				date;

	@Id
	@Column(name = "jt_history_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_jt_history")
	@SequenceGenerator(name = "seq_jt_history", sequenceName = "seq_jt_history", allocationSize = 1)
	private Long				id;

	@ManyToOne
	@JoinColumn(name = "js_history_source_id")
	private JeryTodikSource		source;

	public String getContent() {
		return content;
	}

	public Date getDate() {
		return date;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public Long getId() {
		return id;
	}

	public JeryTodikSource getSource() {
		return source;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSource(JeryTodikSource source) {
		this.source = source;
	}

}
