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

package org.fossa.rolp.ui.schueler;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.klasse.klassentyp.KlassentypPojo;
import org.fossa.rolp.data.schueler.SchuelerContainer;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerLaso;
import org.fossa.rolp.util.KlassenstufenUtils;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaForm;
import org.fossa.vaadin.ui.exception.FossaFormInvalidException;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button.ClickListener;

public class SchuelerVerwaltenForm extends FossaForm implements ClickListener {

	private static final long serialVersionUID = 3788562470960128102L;

	public SchuelerVerwaltenForm() {
		super();
		setFormFieldFactory(new SchuelerVerwaltenFormFields());
	}
	
	@Override
	public void setItemDataSource(Item newDataSource) {
		super.setItemDataSource(newDataSource,SchuelerContainer.NATURAL_FORM_ORDER );
	}
	
	@Override
	public void saveFossaForm() throws FossaFormInvalidException {
		boolean neuerSchueler = (fossaLaso.getId() == null);	
		super.saveFossaForm();
		if (neuerSchueler) {
			SchuelerLaso schueler = (SchuelerLaso) fossaLaso;
			KlasseLaso klasse = KlasseContainer.getKlasseByLehrer(((RolpApplication) getApplication()).getLoginLehrer());
			schueler.setKlasse(klasse.getPojo());
			BeanItemContainer<FachLaso> pflichtfaecherOfKlasse = ZuordnungFachSchuelerContainer.getAllPflichtfaecherOfKlasse(klasse.getPojo());
			for (FachLaso fach : pflichtfaecherOfKlasse.getItemIds()) {
				ZuordnungFachSchuelerLaso zuordnungFS = new ZuordnungFachSchuelerLaso();
				zuordnungFS.setSchueler(schueler.getPojo());
				zuordnungFS.setFach(fach.getPojo());
				ZuordnungFachSchuelerContainer.getInstance().addBean(zuordnungFS);
			}
			
			KlassentypPojo klassentyp = klasse.getPojo().getKlassentyp();
			if (klassentyp.isKlassenstufenorientiert()){
				int nextKlasse = KlassenstufenUtils.getKlassenstufe(schueler.getKlasse().getKlassenname()) + 1;
				schueler.setVersetzungsvermerk("wird versetzt nach Klasse " + nextKlasse + ".");
			} else {
				schueler.setVersetzungsvermerk("wird versetzt.");
			}
			
		}
		closeWindow();
	}

	public void addTemporaryItem(SchuelerLaso schuelerLaso) {
		super.addTemporaryItem(schuelerLaso);
	}
	
	public void setSchuelerAnlegen(SchuelerLaso schuelerLaso){
		super.setFossaLaso(schuelerLaso);
	}

	@Override
	public Item addItemToContainer(FossaLaso fossaLaso) {
		return SchuelerContainer.getInstance().addItem(fossaLaso);
	}
}