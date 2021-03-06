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

package org.fossa.rolp.data.klasse.klassentyp;

import java.io.Serializable;
import java.util.Collection;

import org.fossa.vaadin.laso.FossaLaso;

import com.vaadin.data.util.BeanItemContainer;

public class KlassentypPojoContainer extends BeanItemContainer<KlassentypPojo> implements Serializable {

	private static final long serialVersionUID = -450775208499292824L;
	
	private static KlassentypPojoContainer klassentypContainer;

	@SuppressWarnings("unchecked")
	private KlassentypPojoContainer() {
		super(KlassentypPojo.class);
		addAll((Collection<? extends KlassentypPojo>) FossaLaso.getAll(KlassentypPojo.class));
	}

	public static KlassentypPojoContainer getInstance() {
		if (klassentypContainer == null) {
			klassentypContainer = new KlassentypPojoContainer();
		}
		return klassentypContainer;
	}
	
	public KlassentypPojo getKlassenstufenorientiert() {
		for (KlassentypPojo klassentyp: klassentypContainer.getItemIds()) {			
			if (klassentyp.isKlassenstufenorientiert()) {
				return klassentyp;
			}
		}
		return null;
	}

	public KlassentypPojo getKlassenstufenuebergreifend() {
		for (KlassentypPojo klassentyp: klassentypContainer.getItemIds()) {			
			if (klassentyp.isKlassenstufenuebergreifend()) {
				return klassentyp;
			}
		}
		return null;
	}

}
