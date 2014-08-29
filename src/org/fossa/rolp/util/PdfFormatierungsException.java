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

public class PdfFormatierungsException extends Exception {

	private static final long serialVersionUID = 7268683503544870079L;
	
	public static final Integer TYPE_HURENKIND = 0;	
	public static final Integer TYPE_LONELY_HEADER_OR_FOOTER = 1;
	public Integer type;	
	
	public PdfFormatierungsException(Integer type) {
		super(PdfFormatierungsException.class + ": " + type);
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

}
