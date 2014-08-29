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
import java.util.Collection;

import org.fossa.rolp.data.fach.FachPojo;
import org.fossa.vaadin.laso.FossaLaso;

import com.vaadin.data.util.BeanItemContainer;

public class PflichtfachtemplatesPojoContainer extends BeanItemContainer<PflichtfachtemplatesPojo> implements Serializable {

	private static final long serialVersionUID = 5139293471833923329L;
	
	private static PflichtfachtemplatesPojoContainer pflichtfachtemplatesContainer;

	@SuppressWarnings("unchecked")
	private PflichtfachtemplatesPojoContainer() {
		super(PflichtfachtemplatesPojo.class);
		addAll((Collection<? extends PflichtfachtemplatesPojo>) FossaLaso.getAll(PflichtfachtemplatesPojo.class));
	}

	public static PflichtfachtemplatesPojoContainer getInstance() {
		if (pflichtfachtemplatesContainer == null) {
			pflichtfachtemplatesContainer = new PflichtfachtemplatesPojoContainer();
		}
		return pflichtfachtemplatesContainer;
	}

	public static PflichtfachtemplatesPojo getTemplateForFach(FachPojo fach) {
		for(PflichtfachtemplatesPojo pflichtfachtemplate : getInstance().getItemIds()) {
			if (fach.getFachbezeichnung().equals(pflichtfachtemplate.getPflichtfachname())) {
				return pflichtfachtemplate;
			}
		}
		return null;
	}

}
