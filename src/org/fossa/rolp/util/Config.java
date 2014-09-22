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

import org.fossa.rolp.data.config.ConfigPojo;
import org.fossa.rolp.data.config.ConfigPojoContainer;

public class Config {
	
	private static String getProp(String propkey) {
		for(ConfigPojo configProp: ConfigPojoContainer.getInstance().getItemIds()) {
			if (configProp.getKey().equals(propkey)) {
				return configProp.getValue();
			}
		}
		return null;
	}

	public static String getLocalTempPath() {
		return getProp("localTempPath");
	}
	
	public static String getRelativeNormalFontPath() {
		return getProp("relativeNormalFontPath");
	}
	
	public static String getRelativeThinFontPath() {
		return getProp("relativeThinFontPath");
	}
	
	public static String getRelativeBoldFontPath() {
		return getProp("relativeBoldFontPath");
	}

	public static String getRelativeLogoPath() {
		return getProp("relativeLogoPath");
	}
	
	public static String getAppSeverity() {
		return getProp("appSeverity");
	}
}