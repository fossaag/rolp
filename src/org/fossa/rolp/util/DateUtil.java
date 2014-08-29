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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	
	public static Date cutTimeOffDate(Date date) {
		if (date==null) {
			return null;
		}
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND, 0);			
		date.setTime(gc.getTimeInMillis());
		return date;			
	}

	public static boolean dateIsInvalid(Date date) {
		if (date == null) {
			return false;
		}
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTime(date);
		if (gc.get(Calendar.YEAR) > 1900 && gc.get(Calendar.YEAR) < 3000) {
			return false;
		}
		return true;
	}

	public static String showDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		if (date != null) {
			return dateFormat.format(date);
		} else {
			return " - ";
		}		
	}

}
