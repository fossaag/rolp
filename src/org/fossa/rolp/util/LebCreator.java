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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import org.fossa.rolp.data.fach.FachPojo;
import org.fossa.rolp.data.fach.pflichtfach.PflichtfachsuchwortPojoContainer;
import org.fossa.rolp.data.fach.pflichtfach.PflichtfachtemplatesPojoContainer;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.leb.LebData;
import org.fossa.rolp.data.leb.LebFacheinschaetzungData;
import org.fossa.rolp.data.leb.LebSettingsContainer;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerLaso;
import org.fossa.vaadin.FossaApplication;

import com.itextpdf.text.DocumentException;
import com.vaadin.terminal.StreamResource;

public class LebCreator {

	private StreamResource resource;
	private PdfStreamSource lebPdf;
	private String filename;
	private Integer dummyLineCount = 0;
	private HashMap<Integer, Boolean> hurenkinderMarker = new HashMap<Integer, Boolean>();
	private Integer hurenkinderCount = 0;

	public LebCreator(FossaApplication app, SchuelerLaso schueler, KlasseLaso klasse, String dateiname) throws DocumentException, IOException {
		LebData lebData = collectLebData(schueler, klasse);
		boolean done = false;
		while (!done ) {
			try {
				lebPdf = new PdfStreamSource(app, lebData, this);
				done = true;
			} catch (PdfFormatierungsException pdfFE) {
				System.out.println(pdfFE.getMessage());
				if (pdfFE.getType() == PdfFormatierungsException.TYPE_HURENKIND) {
					hurenkinderCount = hurenkinderCount + 1;
				} else if (pdfFE.getType() == PdfFormatierungsException.TYPE_LONELY_HEADER_OR_FOOTER) {
					dummyLineCount = dummyLineCount + 1;
					hurenkinderMarker = new HashMap<Integer, Boolean>();
					hurenkinderCount = 0;
				}
			}
		}
		filename = dateiname;
		resource = new StreamResource(lebPdf, filename, app);
		resource.setMIMEType("application/pdf");
	}
	
	public StreamResource getLebResource() {
		return resource;
	}
	
	public ByteArrayOutputStream getPdfOutputStream() {
		return lebPdf.getByteArrayOutputStream();		
	}
	
	public String getLebFilename() {
		return filename;
	}
	
	private LebData collectLebData(SchuelerLaso schueler, KlasseLaso klasse) {
		LebData lebData = new LebData();
		lebData.setDatumString(DateUtil.showDateString(LebSettingsContainer.getLebSettings().getZeugnisausgabedatum()));
		if (schueler.getSchuelereinschaetzung() != null && schueler.getSchuelereinschaetzung().getErledigt()) {
			lebData.setIndividuelleEinschaetzung(schueler.getSchuelereinschaetzung().getEinschaetzungstext());
		}
		
		if (klasse.getKlasseneinschaetzung() != null && klasse.getKlasseneinschaetzung().getErledigt()) {
			lebData.setKlassenbrief(klasse.getKlasseneinschaetzung().getEinschaetzungstext());
		}
		
		lebData.setKlassenname(klasse.getKlassenname());
		lebData.setKlassenlehrerUnterschrift(klasse.getKlassenlehrer().getUser().getFirstname());
		lebData.setSchuelername(schueler.getVorname() + " " + schueler.getName());
		lebData.setSchulhalbjahr(LebSettingsContainer.getLebSettings().getHalbjahr());
		
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		lebData.setSchuljahr((currentYear-1) + "/" + currentYear);
		
		lebData.setVersetzungsvermerk(schueler.getVorname() + " " + schueler.getVersetzungsvermerk());
		
		Collection<LebFacheinschaetzungData> facheinschaetzungsdaten = new ArrayList<LebFacheinschaetzungData>();
		for (ZuordnungFachSchuelerLaso zuordnungFS : ZuordnungFachSchuelerContainer.getAllZuordnungenFachSchuelerOfSchueler(schueler.getPojo()).getItemIds()) {
			if (zuordnungFS.getFacheinschaetzung() != null && zuordnungFS.getFacheinschaetzung().getErledigt()) {
				FachPojo fach = zuordnungFS.getFach();
				LebFacheinschaetzungData lebFacheinschaetzungData = new LebFacheinschaetzungData();
				lebFacheinschaetzungData.setFacheinschaetzung(zuordnungFS.getFacheinschaetzung().getEinschaetzungstext());
				lebFacheinschaetzungData.setFachname(fach.getFachbezeichnung());
				lebFacheinschaetzungData.setSuchworte(PflichtfachsuchwortPojoContainer.getSuchworteFuerPflichtfach(PflichtfachtemplatesPojoContainer.getTemplateForFach(fach)));				
				lebFacheinschaetzungData.setUnterschrift(fach.getFachlehrerEins().getUser().getFirstname());
				if (fach.getFachlehrerZwei() != null) {
					lebFacheinschaetzungData.setUnterschrift(fach.getFachlehrerEins().getUser().getFirstname() + " und " + fach.getFachlehrerZwei().getUser().getFirstname());
				}
				facheinschaetzungsdaten.add(lebFacheinschaetzungData);
			}
		}		
		lebData.setFacheinschaetzungsdaten(facheinschaetzungsdaten);
		return lebData;
	}


	public HashMap<Integer, Boolean> getHurenkinderMarker() {
		return hurenkinderMarker;
	}

	public Integer getDummyLineCount() {
		return dummyLineCount;
	}

	public Integer getHurenkindCount() {
		return hurenkinderCount;
	}
}
