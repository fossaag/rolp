/**
 * Copyright (c) 2013, 2014 Frank Kaddereit, Anne Lachnitt, http://www.fossa.de/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.fossa.rolp.util;

import org.fossa.rolp.data.lehrer.lehrerblog.LehrerBlogLaso;


public class BlogUtils {
	
	
	public static final String LINE_SEPERATOR = "<br><hr><br>";
	public static final String BREAK = "<br>";
	
	
	public static String createBlogtext(LehrerBlogLaso blogLaso) {
		return "<b>" + blogLaso.getZeitstempel() + "</b>" + BREAK + blogLaso.getEreignis();
	}
	
	
}
