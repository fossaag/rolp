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

package org.fossa.rolp.ui.fach;

import java.util.ArrayList;
import java.util.Date;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.fach.FachContainer;
import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.fach.FachPojo;
import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionPojo;
import org.fossa.rolp.data.fach.fachtyp.FachtypPojo;
import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.rolp.data.lehrer.lehrerblog.LehrerBlogContainer;
import org.fossa.rolp.data.lehrer.lehrerblog.LehrerBlogLaso;
import org.fossa.rolp.data.schueler.SchuelerContainer;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerLaso;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaForm;
import org.fossa.vaadin.ui.exception.FossaFormInvalidException;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button.ClickListener;

public class FaecherAnlegenForm extends FossaForm implements ClickListener {

	private static final long serialVersionUID = 3788562470960128102L;
	private FachtypPojo fachtyp;

	public FaecherAnlegenForm(FachtypPojo fachtypPojo) {
		super();
		this.fachtyp = fachtypPojo;
		setFormFieldFactory(new FaecherAnlegenFormFields(fachtyp));
	}
	
	@Override
	public void setItemDataSource(Item newDataSource) {
		if(fachtyp.isPflichtfach()){
			super.setItemDataSource(newDataSource,FachContainer.NATURAL_FORM_PFLICHTFACH_ORDER );
		} else
		{
			super.setItemDataSource(newDataSource,FachContainer.NATURAL_FORM_KURS_ORDER );
		}
	}
	
	@Override
	public void saveFossaForm() throws FossaFormInvalidException {
		boolean neuesFach = (fossaLaso.getId() == null);
		FachLaso fach = (FachLaso) fossaLaso;
		FachdefinitionPojo fachdefinition = (FachdefinitionPojo) getField(FachPojo.FACHDEFINITION_COLUMN).getValue();
		KlasseLaso klasse = KlasseContainer.getKlasseByLehrer(((RolpApplication) getApplication()).getLoginLehrer());
		ArrayList<FachLaso> relevanteFaecher = new ArrayList<FachLaso>();
		relevanteFaecher.addAll(ZuordnungFachSchuelerContainer.getAllPflichtfaecherOfKlasse(klasse.getPojo()).getItemIds());
		relevanteFaecher.addAll(FachContainer.getAllKurse().getItemIds());
		for (FachLaso aFach : relevanteFaecher) {
			if (aFach.getFachdefinition().getId().equals(fachdefinition.getId())) {
				if (neuesFach || (!neuesFach && !fach.getId().equals(aFach.getId()))) {
					throw new FossaFormInvalidException("Das Fach ist bereits vorhanden.");
				}
			}
		}
		LehrerPojo exFachlehrer1 = fach.getFachlehrerEins();
		LehrerPojo exFachlehrer2 = fach.getFachlehrerZwei();
		LehrerPojo exFachlehrer3 = fach.getFachlehrerDrei();
		super.saveFossaForm();
		LehrerBlogContainer lehrerBlogContainer = LehrerBlogContainer.getInstance();
		fach = (FachLaso) fossaLaso;
		String klassenSuffix = "";
		if (fachtyp.isPflichtfach()) {
			klassenSuffix = klassenSuffix + "' der Klasse '" + klasse.getKlassenname();
		}
		if (neuesFach) {
			if (fachtyp.isPflichtfach()) {
				BeanItemContainer<SchuelerLaso> schuelerOfKlasse = SchuelerContainer.getAllSchuelerOfKlasse(klasse.getPojo());
				for (SchuelerLaso schueler : schuelerOfKlasse.getItemIds()) {
					ZuordnungFachSchuelerLaso zuordnungFS = new ZuordnungFachSchuelerLaso();
					zuordnungFS.setSchueler(schueler.getPojo());
					zuordnungFS.setFach(fach.getPojo());
					ZuordnungFachSchuelerContainer.getInstance().addBean(zuordnungFS);
				}
			}			
			LehrerPojo fachlehrer1 = fach.getFachlehrerEins();
			if (fachlehrer1 != null) {
				LehrerBlogLaso lehrerblog = new LehrerBlogLaso();
				lehrerblog.setLehrer(fachlehrer1);
				lehrerblog.setTimestamp(new Date());
				lehrerblog.setEreignis("Fach '" + fach.getFachbezeichnung() + klassenSuffix + "' wurde hinzugefügt.");	
				lehrerBlogContainer.addBean(lehrerblog);
			}
			LehrerPojo fachlehrer2 = fach.getFachlehrerZwei();
			if (fachlehrer2 != null) {
				LehrerBlogLaso lehrerblog = new LehrerBlogLaso();
				lehrerblog.setLehrer(fachlehrer2);
				lehrerblog.setTimestamp(new Date());
				lehrerblog.setEreignis("Fach '" + fach.getFachbezeichnung() + klassenSuffix + "' wurde hinzugefügt.");	
				lehrerBlogContainer.addBean(lehrerblog);
			}
			LehrerPojo fachlehrer3 = fach.getFachlehrerDrei();
			if (fachlehrer3 != null) {
				LehrerBlogLaso lehrerblog = new LehrerBlogLaso();
				lehrerblog.setLehrer(fachlehrer3);
				lehrerblog.setTimestamp(new Date());
				lehrerblog.setEreignis("Fach '" + fach.getFachbezeichnung() + klassenSuffix + "' wurde hinzugefügt.");	
				lehrerBlogContainer.addBean(lehrerblog);
			}
		} else {
			LehrerPojo fachlehrer1 = fach.getFachlehrerEins();
			if (fachlehrer1 != null && (exFachlehrer1 == null || !fachlehrer1.getId().equals(exFachlehrer1.getId()))) {
				LehrerBlogLaso lehrerblog = new LehrerBlogLaso();
				lehrerblog.setLehrer(fachlehrer1);
				lehrerblog.setTimestamp(new Date());
				lehrerblog.setEreignis("Fach '" + fach.getFachbezeichnung() + klassenSuffix + "' wird nun von Ihnen unterrichtet.");	
				lehrerBlogContainer.addBean(lehrerblog);
			}					
			if (exFachlehrer1 != null && (fachlehrer1 == null || !exFachlehrer1.getId().equals(fachlehrer1.getId()))) {
				LehrerBlogLaso exlehrerblog = new LehrerBlogLaso();
				exlehrerblog.setLehrer(exFachlehrer1);
				exlehrerblog.setTimestamp(new Date());
				exlehrerblog.setEreignis("Fach '" + fach.getFachbezeichnung() + klassenSuffix + "' wird nicht mehr von Ihnen unterrichtet.");	
				lehrerBlogContainer.addBean(exlehrerblog);
			}
			LehrerPojo fachlehrer2 = fach.getFachlehrerZwei();
			if (fachlehrer2 != null && (exFachlehrer2 == null || !fachlehrer2.getId().equals(exFachlehrer2.getId()))) {
				LehrerBlogLaso lehrerblog = new LehrerBlogLaso();
				lehrerblog.setLehrer(fachlehrer2);
				lehrerblog.setTimestamp(new Date());
				lehrerblog.setEreignis("Fach '" + fach.getFachbezeichnung() + klassenSuffix + "' wird nun von Ihnen unterrichtet.");
				lehrerBlogContainer.addBean(lehrerblog);
			}					
			if (exFachlehrer2 != null && (fachlehrer2 == null || !exFachlehrer2.getId().equals(fachlehrer2.getId()))) {
				LehrerBlogLaso exlehrerblog = new LehrerBlogLaso();
				exlehrerblog.setLehrer(exFachlehrer2);
				exlehrerblog.setTimestamp(new Date());
				exlehrerblog.setEreignis("Fach '" + fach.getFachbezeichnung() + klassenSuffix + "' wird nicht mehr von Ihnen unterrichtet.");	
				lehrerBlogContainer.addBean(exlehrerblog);
			}
			LehrerPojo fachlehrer3 = fach.getFachlehrerDrei();
			if (fachlehrer3 != null && (exFachlehrer3 == null || !fachlehrer3.getId().equals(exFachlehrer3.getId()))) {
				LehrerBlogLaso lehrerblog = new LehrerBlogLaso();
				lehrerblog.setLehrer(fachlehrer3);
				lehrerblog.setTimestamp(new Date());
				lehrerblog.setEreignis("Fach '" + fach.getFachbezeichnung() + klassenSuffix + "' wird nun von Ihnen unterrichtet.");
				lehrerBlogContainer.addBean(lehrerblog);
			}					
			if (exFachlehrer3 != null && (fachlehrer3 == null || !exFachlehrer3.getId().equals(fachlehrer3.getId()))) {
				LehrerBlogLaso exlehrerblog = new LehrerBlogLaso();
				exlehrerblog.setLehrer(exFachlehrer3);
				exlehrerblog.setTimestamp(new Date());
				exlehrerblog.setEreignis("Fach '" + fach.getFachbezeichnung() + klassenSuffix + "' wird nicht mehr von Ihnen unterrichtet.");	
				lehrerBlogContainer.addBean(exlehrerblog);
			}
		}			
		closeWindow();
	}		
	
	public void addTemporaryItem(FachLaso fachLaso) {
		super.addTemporaryItem(fachLaso);
	}
	
	public void setFaecherAnlegen(FachLaso fachLaso){
		super.setFossaLaso(fachLaso);
	}

	@Override
	public Item addItemToContainer(FossaLaso fossaLaso) {
		return FachContainer.getInstance().addItem(fossaLaso);
	}
	
	public void setFaecherFestlegen(FachLaso fachLaso){
		super.setFossaLaso(fachLaso);
	}
}