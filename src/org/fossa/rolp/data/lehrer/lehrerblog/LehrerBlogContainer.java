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

package org.fossa.rolp.data.lehrer.lehrerblog;

import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;

public class LehrerBlogContainer extends BeanItemContainer<LehrerBlogLaso> implements Serializable {
	
	private static final long serialVersionUID = 5819317748736050509L;
	
	public static final String[] NATURAL_COL_ORDER = new String[] {
		LehrerBlogPojo.LEHRER_COLUMN, 
		LehrerBlogPojo.EREIGNIS_COLUMN,
		LehrerBlogLaso.ZEITSTEMPEL_COLUMN,
	};
	
	public static final String[] COL_HEADERS = new String[] {
		LehrerBlogPojo.LEHRER_COLUMN, 
		LehrerBlogPojo.EREIGNIS_COLUMN,
		LehrerBlogLaso.ZEITSTEMPEL_COLUMN,
	};
	
	private static LehrerBlogContainer lehrerblogContainer;

	private LehrerBlogContainer() {
		super(LehrerBlogLaso.class);
	}
	
	public static LehrerBlogContainer getInstance() {
		if (lehrerblogContainer == null) {
			lehrerblogContainer = new LehrerBlogContainer();
			for (LehrerBlogPojo lehrerblogPojo: LehrerBlogLaso.getAll()) {
				LehrerBlogLaso lehrerblogLaso = new LehrerBlogLaso(lehrerblogPojo);
				lehrerblogContainer.addBean(lehrerblogLaso);
			}
		}
		return lehrerblogContainer;
	}
}