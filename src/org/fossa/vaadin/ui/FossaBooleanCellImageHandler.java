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

package org.fossa.vaadin.ui;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Embedded;

public class FossaBooleanCellImageHandler extends Embedded {

	private static final long serialVersionUID = 697717346053048617L;

	public FossaBooleanCellImageHandler(boolean datasource) {
		super(null);
		if (datasource) {
			setSource(new ThemeResource("images/check.gif"));
		} else {
			setSource(new ThemeResource("images/uncheck.gif"));
		}
		setType(Embedded.TYPE_IMAGE);
	}

}
