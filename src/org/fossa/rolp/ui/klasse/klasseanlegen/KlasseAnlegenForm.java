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

package org.fossa.rolp.ui.klasse.klasseanlegen;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.klasse.KlassePojo;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.rolp.util.KlassenstufenUtils;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaForm;
import org.fossa.vaadin.ui.exception.FossaFormInvalidException;

import com.vaadin.data.Item;
import com.vaadin.ui.Button.ClickListener;

public class KlasseAnlegenForm extends FossaForm implements ClickListener {

	private static final long serialVersionUID = 3788562470960128102L;

	public KlasseAnlegenForm() {
		super();
		setFormFieldFactory(new KlasseAnlegenFormFields());
	}

	@Override
	public void setItemDataSource(Item newDataSource) {
		super.setItemDataSource(newDataSource, KlasseContainer.NATURAL_COL_ORDER );
	}
	
	@Override
	public void saveFossaForm() throws FossaFormInvalidException {
		boolean neueKlasse = (fossaLaso.getId() == null);
		String klassenname = ((String) getField(KlassePojo.KLASSENNAME_COLUMN).getValue());		
		try {
			KlassenstufenUtils.getKlassenstufe(klassenname);
		} catch (NumberFormatException e) {
			throw new FossaFormInvalidException("1. Zeichen muss eine Zahl sein");
		}
		for(KlasseLaso klasse : KlasseContainer.getInstance().getItemIds()) {
			if (klasse.getKlassenname().equals(klassenname) && klasse.getId() != fossaLaso.getId()) {
				throw new FossaFormInvalidException("Es gibt bereits eine Klasse mit diesem Namen.");
			}
		}
		super.saveFossaForm();
		if (neueKlasse) {
			LehrerPojo klassenlehrer = (((RolpApplication) getApplication()).getLoginLehrer());
			KlasseLaso klasse = (KlasseLaso) fossaLaso;
			klasse.setKlassenlehrer(klassenlehrer);
			closeWindow();
		}
	}	

	public void addTemporaryItem(KlasseLaso klasseLaso) {
		super.addTemporaryItem(klasseLaso);
	}
	
	public void setKlasse(KlasseLaso klasseLaso){
		super.setFossaLaso(klasseLaso);
	}
	
	@Override
	public Item addItemToContainer(FossaLaso fossaLaso) {
		return KlasseContainer.getInstance().addItem(fossaLaso);
	}
}