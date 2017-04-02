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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.jerytodik.business.dao.JeryTodikSourceRepository;
import mg.jerytodik.business.service.JeryTodikSourceService;
import mg.jerytodik.common.entity.JeryTodikSource;
import mg.jerytodik.common.exceptions.JerytodikException;
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

	@Autowired
	private JeryTodikSourceRepository	jeryTodikSourceRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void archiveActiveResources() {

		for (final JeryTodikSource jeryTodikSource : jeryTodikSourceRepository.findAllActivatedJeriTodikSource()) {
			try {
				archiveResource(jeryTodikSource);
			} catch (JerytodikException e) {
				LOGGER.error(e.getMessage());
			}
		}
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

			LOGGER.info("");
			writeInFile(rootResourceFolderName, principalResourceContent);

		} catch (IOException e) {
			throw new JerytodikException(e.getMessage());
		}

	}

	private String createRootResourceFolderName(final String possibleMalformedName) {

		possibleMalformedName.replaceAll(JerytodikUtil.ALPHANUMERIC_PATTERN, JerytodikUtil.DASHE_SEPARATOR);

		return possibleMalformedName.trim();
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

	private void validateSource(JeryTodikSource jerytodikSource) throws JerytodikException {

		if (null == jerytodikSource) {
			throw new JerytodikException("The resource to be archived must be specified");
		}

		if (StringUtils.isEmpty(jerytodikSource.getUrl())) {
			throw new JerytodikException("The URI of the resource must be specified : " + jerytodikSource.getName());
		}

		// TODO : completer la validation ici si necessaire ...
	}

	private void writeInFile(final String rootResourceFolderName, final String principalResourceContent)
			throws FileNotFoundException, UnsupportedEncodingException, JerytodikException {

		if (!new File(rootResourceFolderName).exists()) {
			LOGGER.info("The directory named {} does not exist.The directory is being created", rootResourceFolderName);

			if (!new File(rootResourceFolderName).mkdir()) {
				throw new JerytodikException(
						"Unable to create directory. Please check that J has write permission and that there is enough space on the disk.");
			}
		}

		final String filePath = new StringBuilder(rootResourceFolderName).append(File.separator).append(INDEX_FILE_NAME)
				.toString();

		final PrintWriter writer = new PrintWriter(filePath, JerytodikUtil.UTF8_ENCODING);
		writer.println(principalResourceContent);
		writer.close();

		// Si donnees binaires ...

		// byte data[] = ...
		// FileOutputStream out = new FileOutputStream("the-file-name");
		// out.write(data);
		// out.close();
	}

}