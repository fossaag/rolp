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

package org.fossa.rolp.data.fach.fachdefinition.leb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionPojo;
import org.fossa.vaadin.laso.FossaLaso;

import com.vaadin.data.util.BeanItemContainer;

public class FachbezeichnungLebContainer extends BeanItemContainer<FachbezeichnungLebLaso> implements Serializable {

	private static final long serialVersionUID = -1864367499011923691L;

	public static final Object[] NATURAL_FORM_ORDER = new Object[] {
		FachbezeichnungLebPojo.BEZEICHNUNG_COLUMN
	};

	public static final Object[] NATURAL_COL_ORDER = new Object[] {
		FachbezeichnungLebPojo.BEZEICHNUNG_COLUMN
	};
	
	public static final String[] COL_HEADERS = new String[] {
		FachbezeichnungLebPojo.BEZEICHNUNG_COLUMN
	};
	
	private static FachbezeichnungLebContainer fachbezeichnungenLebContainer;

	private FachbezeichnungLebContainer() {
		super(FachbezeichnungLebLaso.class);
	}

	public static FachbezeichnungLebContainer getInstance() {
		if (fachbezeichnungenLebContainer == null) {
			fachbezeichnungenLebContainer = new FachbezeichnungLebContainer();
			for (FachbezeichnungLebPojo fachbezeichnungenLebLebPojo: FachbezeichnungLebLaso.getAll()) {
				FachbezeichnungLebLaso fachbezeichnungenLebLebLaso = new FachbezeichnungLebLaso(fachbezeichnungenLebLebPojo);
				fachbezeichnungenLebContainer.addBean(fachbezeichnungenLebLebLaso);
			}
		}
		return fachbezeichnungenLebContainer;
	}
	
	public static Collection<String> getFachbezeichnungenFuerFachdefinition(FachdefinitionPojo fachdefinition) {
		Collection<String> fachbezeichnungenLeb = new ArrayList<String>();
		for (FachbezeichnungLebLaso fachbezeichnungLeb: getFachbezeichnungenLebOfFachdefinition(fachdefinition).getItemIds()) {
			fachbezeichnungenLeb.add(fachbezeichnungLeb.getBezeichnung());
		}
		return fachbezeichnungenLeb;
	}
	
	public static BeanItemContainer<FachbezeichnungLebLaso> getFachbezeichnungenLebOfFachdefinition(FachdefinitionPojo fachdefinition) {
		BeanItemContainer<FachbezeichnungLebLaso> fachbezeichnungenLeb = new BeanItemContainer<FachbezeichnungLebLaso>(FachbezeichnungLebLaso.class);
		for (FachbezeichnungLebLaso fachbezeichnungLeb: getInstance().getItemIds()) {
			if (fachdefinition != null && fachbezeichnungLeb.getFachdefinition() != null && fachbezeichnungLeb.getFachdefinition().getId().equals(fachdefinition.getId())) {
				fachbezeichnungenLeb.addBean(fachbezeichnungLeb);
			}
		}
		fachbezeichnungenLeb.sort(new Object[] {FachbezeichnungLebPojo.BEZEICHNUNG_COLUMN}, new boolean[] {true});
		return fachbezeichnungenLeb;
	}
	
	public void deleteFachbezeichnungLeb(FachbezeichnungLebLaso fachbezeichnungenLeb) {
		FossaLaso.deleteIfExists(fachbezeichnungenLeb.getPojo());
		for (FachbezeichnungLebLaso aFachbezeichnungenLeb : getInstance().getItemIds()) {
			if (fachbezeichnungenLeb.getId().equals(aFachbezeichnungenLeb.getId())) {
				fachbezeichnungenLebContainer.removeItem(aFachbezeichnungenLeb);
				return;
			}
		}
	}
}
