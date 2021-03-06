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
package mg.jerytodik.business.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mg.jerytodik.business.dao.JeryTodikHistoryRepository;
import mg.jerytodik.business.dao.JeryTodikSourceRepository;
import mg.jerytodik.business.service.JeryTodikSourceService;
import mg.jerytodik.common.entity.JeryTodikHistory;
import mg.jerytodik.common.entity.JeryTodikSource;
import mg.jerytodik.common.exceptions.JerytodikException;
import mg.jerytodik.common.utility.HistoryUtil;
import mg.jerytodik.common.utility.JerytodikUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author nabil andriantomanga
 */
@Service
public class JeryTodikSourceServiceImpl implements JeryTodikSourceService {

	private static final String			INDEX_FILE_NAME	= "index.html";

	private static final Logger			LOGGER			= LoggerFactory.getLogger(JeryTodikSourceServiceImpl.class);

	@Value("${jerytodik.archive.root.folder}")
	private String						archiveRootFolder;

	@Autowired
	private JeryTodikHistoryRepository	jeryTodikHistoryRepository;

	@Autowired
	private JeryTodikSourceRepository	jeryTodikSourceRepository;

	private void addHistory(final String historyContent, final JeryTodikSource jerytodikSource) {

		final JeryTodikHistory history = new JeryTodikHistory();

		history.setContent(historyContent);
		history.setDate(new Date());
		history.setSource(jerytodikSource);

		jeryTodikHistoryRepository.save(history);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void archiveActiveResources() {

		jeryTodikSourceRepository.findAllActivatedJeriTodikSource().stream().forEach((i) -> {
			try {
				archiveResource(i);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void archiveResource(final JeryTodikSource jerytodikSource) throws JerytodikException {

		validateSource(jerytodikSource);

		try {
			final String principalResourceContent = getPrincipalResourceContent(jerytodikSource);

			final String rootResourceFolderName = createRootResourceFolderName(jerytodikSource.getUrl());

			LOGGER.info("Archiving resources {} from {}", jerytodikSource.getName(), jerytodikSource.getUrl());
			writeInFile(rootResourceFolderName, principalResourceContent);

			addHistory(HistoryUtil.ARCHIVE_OK, jerytodikSource);

		} catch (IOException e) {

			addHistory(HistoryUtil.ARCHIVE_KO, jerytodikSource);
			throw new JerytodikException(e.getMessage());
		}

	}

	private String createRootResourceFolderName(String possibleMalformedName) {

		possibleMalformedName = possibleMalformedName.replaceAll(JerytodikUtil.ALPHANUMERIC_PATTERN,
				JerytodikUtil.DASHE_SEPARATOR);

		return archiveRootFolder + File.separator + possibleMalformedName.trim();
	}

	private String createSubFolderName() {
		final Date now = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);

		return new StringBuilder().append(calendar.get(Calendar.YEAR)).append(JerytodikUtil.DASHE_SEPARATOR)
				.append(calendar.get(Calendar.MONTH)).append(JerytodikUtil.DASHE_SEPARATOR)
				.append(calendar.get(Calendar.DAY_OF_MONTH)).append(JerytodikUtil.DASHE_SEPARATOR)
				.append(calendar.get(Calendar.HOUR)).append(JerytodikUtil.DASHE_SEPARATOR)
				.append(calendar.get(Calendar.MINUTE)).toString();
	}

	private String getPrincipalResourceContent(final JeryTodikSource jerytodikSource) throws IOException {

		final OkHttpClient client = new OkHttpClient();

		LOGGER.info(JerytodikUtil.LINE);
		LOGGER.info("Sending request to {} ...", jerytodikSource.getUrl());
		LOGGER.info(JerytodikUtil.LINE);
		Request request = new Request.Builder().url(jerytodikSource.getUrl()).build();

		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	private void validateSource(final JeryTodikSource jerytodikSource) throws JerytodikException {

		if (null == jerytodikSource) {
			throw new JerytodikException("The resource to be archived must be specified");
		}

		if (StringUtils.isEmpty(jerytodikSource.getUrl())) {
			throw new JerytodikException("The URI of the resource must be specified : " + jerytodikSource.getName());
		}

		// TODO : completer la validation ici si necessaire ...
	}

	private void writeInFile(String rootResourceFolderName, final String principalResourceContent)
			throws FileNotFoundException, UnsupportedEncodingException, JerytodikException {

		rootResourceFolderName += File.separator + createSubFolderName();

		if (!new File(rootResourceFolderName).exists()) {
			LOGGER.info("The directory named {} does not exist.The directory is being created", rootResourceFolderName);

			if (!new File(rootResourceFolderName).mkdirs()) {
				throw new JerytodikException(
						"Unable to create directory. Please check that Jerytodik has write permission and that there is enough space on the disk.");
			}
		}

		final String filePath = new StringBuilder(rootResourceFolderName).append(File.separator).append(INDEX_FILE_NAME)
				.toString();

		final PrintWriter writer = new PrintWriter(filePath, JerytodikUtil.UTF8_ENCODING);
		writer.println(principalResourceContent);
		writer.close();

		LOGGER.info("File successfully created : {}", filePath);
	}

}
