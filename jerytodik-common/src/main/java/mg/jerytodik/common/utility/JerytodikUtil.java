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
package mg.jerytodik.common.utility;

/**
 * @author nabil andriantomanga
 */
public final class JerytodikUtil {

	/* regex for alphanumeric string validation */
	public static final String	ALPHANUMERIC_PATTERN	= "[^A-Za-z0-9]";

	public static final String	DASHE_SEPARATOR			= "-";

	public static final String	LINE					= createLine();

	private static final char	LINE_CHAR				= '-';

	// standard output
	private static final int	LINE_LENGTH				= 80;

	public static final String	UTF8_ENCODING			= "UTF-8";

	/**
	 * Cree la ligne utilisee dans les fichiers de logging
	 * 
	 * @return la ligne utilisee
	 */
	private static String createLine() {

		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < LINE_LENGTH; i++) {
			sb.append(LINE_CHAR);
		}
		return sb.toString();
	}

	private JerytodikUtil() {
		throw new AssertionError();
	}
}
