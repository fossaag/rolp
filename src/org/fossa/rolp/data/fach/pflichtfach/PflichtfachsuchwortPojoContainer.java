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

package org.fossa.rolp.data.fach.pflichtfach;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.fossa.vaadin.laso.FossaLaso;

import com.vaadin.data.util.BeanItemContainer;

public class PflichtfachsuchwortPojoContainer extends BeanItemContainer<PflichtfachsuchwortPojo> implements Serializable {

	private static final long serialVersionUID = 1634178597211010960L;
	
	private static PflichtfachsuchwortPojoContainer pflichtfachsuchwortContainer;

	@SuppressWarnings("unchecked")
	private PflichtfachsuchwortPojoContainer() {
		super(PflichtfachsuchwortPojo.class);
		addAll((Collection<? extends PflichtfachsuchwortPojo>) FossaLaso.getAll(PflichtfachsuchwortPojo.class));
	}

	public static PflichtfachsuchwortPojoContainer getInstance() {
		if (pflichtfachsuchwortContainer == null) {
			pflichtfachsuchwortContainer = new PflichtfachsuchwortPojoContainer();
		}
		return pflichtfachsuchwortContainer;
	}
	
	public static Collection<String> getSuchworteFuerPflichtfach(PflichtfachtemplatesPojo pflichtfach) {
		Collection<String> pflichtfachsuchworte = new ArrayList<String>();
		for (PflichtfachsuchwortPojo suchwort: getInstance().getItemIds()) {
			if ((pflichtfach == null) && (suchwort.getPflichtfachtemplate() == null) ||	(pflichtfach != null && suchwort.getPflichtfachtemplate() != null && suchwort.getPflichtfachtemplate().getId().equals(pflichtfach.getId()))) {
				pflichtfachsuchworte.add(suchwort.getSuchwort());
			}
		}
		return pflichtfachsuchworte;
	}
}
