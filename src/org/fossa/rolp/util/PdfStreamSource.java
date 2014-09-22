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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.fossa.rolp.data.leb.LebData;
import org.fossa.rolp.data.leb.LebFacheinschaetzungData;
import org.fossa.vaadin.FossaApplication;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.terminal.StreamResource.StreamSource;

public class PdfStreamSource implements StreamSource {

	private static final long serialVersionUID = -3132301652943616825L;

	private static final Integer DUMMY_LINE_MAX = 99;
	private static final Integer HURENKIND_MAX = 99;
	
	private static final float FIXED_LEADING_TEXT = 1;
	private static final float SCHUSTERJUNGEN_DISTANCE_FACTOR_FOR_FONTSIZE = 3.2f;
	private static final float HURENKIND_DISTANCE_FACTOR_FOR_FONTSIZE = 3.2f;

	private static Font lernentwicklungsberichtUeberschriftFont;
	private static Font standardTextFont;
	private static Font standardTextBoldFont;
	private static Font headerFont;
	private static Font footerFont;
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private LebData lebData;
	private Document document;
	private float zeilenabstandsfaktor;
	private Font fusszeilenFont;
	private Integer bottomMargin = 100;
	private Integer topMargin = 150;
	private LebCreator lebCreator;
	private Integer sectionCount = 0;

	public PdfStreamSource(FossaApplication app, LebData lebData, LebCreator lebCreator) throws DocumentException, IOException, PdfFormatierungsException {
		this.lebData = lebData;
		this.lebCreator = lebCreator;
		BaseFont fontNormal = BaseFont.createFont(app.getContext().getBaseDirectory() + Config.getRelativeNormalFontPath(), BaseFont.CP1252, BaseFont.EMBEDDED);
		BaseFont fontBold = BaseFont.createFont(app.getContext().getBaseDirectory() + Config.getRelativeBoldFontPath(), BaseFont.CP1252, BaseFont.EMBEDDED);
		BaseFont fontThin = BaseFont.createFont(app.getContext().getBaseDirectory() + Config.getRelativeThinFontPath(), BaseFont.CP1252, BaseFont.EMBEDDED);

		lernentwicklungsberichtUeberschriftFont = new Font(fontThin, 22, Font.NORMAL);
		standardTextFont = new Font(fontNormal, KlassenstufenUtils.getLebFontSize(lebData.getKlassenname()), Font.NORMAL);
		standardTextBoldFont = new Font(fontBold, KlassenstufenUtils.getLebFontSize(lebData.getKlassenname()), Font.NORMAL);
		headerFont = new Font(fontNormal, 12, Font.NORMAL);
		footerFont = new Font(fontNormal, 10, Font.NORMAL);
		fusszeilenFont = new Font(fontNormal, 8, Font.NORMAL);
		zeilenabstandsfaktor = KlassenstufenUtils.getLebZeilenabstandAsFactor(lebData.getKlassenname());
		document = null;
		try {
			document = new Document(PageSize.A4, 100, 90, topMargin, bottomMargin);
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			LebPageHelper event = new LebPageHelper(lebData, Image.getInstance(app.getContext().getBaseDirectory() + Config.getRelativeLogoPath()), fusszeilenFont);
			writer.setPageEvent(event);
			document.open();
			addContent(writer);
			addFooter(lebData, writer);
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	private void addFooter(LebData lebData, PdfWriter writer) throws DocumentException, PdfFormatierungsException {
		int footerMargin = 0;
		if (lebData.getSchulhalbjahr().isErstesHalbjahr()) {
			footerMargin = bottomMargin + 85;
		} else {
			footerMargin = bottomMargin + 170;
		}
		alertLonelyFooter(writer, footerMargin);		
		while (writer.getVerticalPosition(false) > (footerMargin + 20)) {
			document.add(Chunk.NEWLINE);
		}
		float footerBegin = writer.getVerticalPosition(false);
		Paragraph footerTopParagraph = new Paragraph();
		footerTopParagraph.setLeading(1, 0.4f);
		footerTopParagraph.add(Chunk.NEWLINE);
		footerTopParagraph.add(Chunk.NEWLINE);
		
		Paragraph footerBottompParagraph = new Paragraph();
		footerBottompParagraph.setLeading(1, 0.4f);
		
		if (lebData.getSchulhalbjahr().isErstesHalbjahr()) {
			footerTopParagraph.add(PdfFormatHelper.buildFooterHalbjahrDatumLine(lebData.getDatumString(), footerFont));
			footerTopParagraph.add(PdfFormatHelper.buildFooterHalbjahrDatumKlassenleiterLine(footerFont));
		} else {
			footerTopParagraph.add(PdfFormatHelper.buildFooterVersetzungsvermerkLine(lebData.getVersetzungsvermerk(), footerFont));
			footerTopParagraph.add(Chunk.NEWLINE);
			footerTopParagraph.add(PdfFormatHelper.buildFooterDatumLine(lebData.getDatumString(), footerFont));
			document.add(footerTopParagraph);
			while (writer.getVerticalPosition(false) > (footerBegin - 82)) {
				document.add(Chunk.NEWLINE);
			}
			footerBottompParagraph.add(PdfFormatHelper.buildFooterDienstsiegelLine(footerFont));
			footerBottompParagraph.add(Chunk.NEWLINE);
			footerBottompParagraph.add(PdfFormatHelper.buildFooterUnterschriftenLine(footerFont));
		}
		footerBottompParagraph.add(Chunk.NEWLINE);
		footerBottompParagraph.add(Chunk.NEWLINE);
		footerBottompParagraph.add(Chunk.NEWLINE);
		footerBottompParagraph.add(PdfFormatHelper.buildFooterKenntnisLine(footerFont));
		document.add(footerBottompParagraph);
	}

	private void addContent(PdfWriter writer) throws DocumentException, PdfFormatierungsException {		
		Anchor anchor = new Anchor("Lernentwicklungsbericht", lernentwicklungsberichtUeberschriftFont);
		Chapter chapterLEB = new Chapter(new Paragraph(anchor), 1);
		chapterLEB.setNumberDepth(0);
		Paragraph paragraphHeader = new Paragraph();
		paragraphHeader.setLeading(FIXED_LEADING_TEXT, 1);
		sectionCount += 1;
		Section headerSection = chapterLEB.addSection(paragraphHeader);
		headerSection.setNumberDepth(0);

		paragraphHeader.add(Chunk.NEWLINE);
		paragraphHeader.add(PdfFormatHelper.buildHeaderNameLine(lebData.getSchuelername() , headerFont));
		paragraphHeader.add(Chunk.NEWLINE);
		paragraphHeader.add(PdfFormatHelper.buildHeaderKlassendatenLine(lebData, headerFont));
		headerSection.add(Chunk.NEWLINE);		
		headerSection.add(Chunk.NEWLINE);
		document.add(chapterLEB);
		insertDummyLineIfNecessary(writer);
		
		addKlassenbrief(chapterLEB, writer);
		addIndividuelleEinschaetzung(chapterLEB, writer);
		addFacheinschaetzungen(chapterLEB, writer);
	}

	private void addFacheinschaetzungen(Chapter chapterLEB, PdfWriter writer) throws DocumentException, PdfFormatierungsException {
		for (LebFacheinschaetzungData facheinschaetzungsdaten : lebData.getFacheinschaetzungsdaten()) {
			sectionCount += 1;
			breakHurenkind(writer);
			breakSchusterjunge(writer);
			Paragraph paragraphFacheinschaetzung = new Paragraph();
			Section facheinschaetzungsTextSection = chapterLEB.addSection(paragraphFacheinschaetzung);
			facheinschaetzungsTextSection.setNumberDepth(0);
			Collection<String> fachbezeichnungen = facheinschaetzungsdaten.getFachbezeichnungen();
			fachbezeichnungen.add(facheinschaetzungsdaten.getFachname());
			
			String facheinschaetzung = facheinschaetzungsdaten.getFacheinschaetzung();
			
			Integer firstIndex = null;
			String boldedWord = "";
			for (String fachbezeichnung : fachbezeichnungen) {
				int index = facheinschaetzung.toLowerCase().indexOf(fachbezeichnung.toLowerCase());
				if (index != -1 && (firstIndex == null || firstIndex > index)) {
					firstIndex = index;
					boldedWord = fachbezeichnung;
				}
			}
			Paragraph facheinschaetzungParapgraph = new Paragraph();
			facheinschaetzungParapgraph.setAlignment(Element.ALIGN_JUSTIFIED);	
			facheinschaetzungParapgraph.setLeading(FIXED_LEADING_TEXT, zeilenabstandsfaktor);
			if (firstIndex == null) {
				facheinschaetzungParapgraph.add(new Phrase(facheinschaetzungsdaten.getFacheinschaetzung().replace('\t', '\0'), standardTextFont));
			} else {
				String beforeBoldWord = facheinschaetzung.substring(0, firstIndex);
				facheinschaetzungParapgraph.add(new Phrase(beforeBoldWord.replace('\t', '\0'), standardTextFont));
				
				String boldWord = facheinschaetzung.substring(firstIndex, firstIndex + boldedWord.length());
				facheinschaetzungParapgraph.add(new Phrase(boldWord, standardTextBoldFont));
				
				String afterBoldWord = facheinschaetzung.substring(firstIndex + boldedWord.length());
				facheinschaetzungParapgraph.add(new Phrase(afterBoldWord.replace('\t', '\0'), standardTextFont));							
			}
			facheinschaetzungParapgraph.setFont(standardTextFont);
			facheinschaetzungsTextSection.add(facheinschaetzungParapgraph);
			Paragraph unterschriftParagraph = new Paragraph();
			document.add(facheinschaetzungsTextSection);
			Section facheinschaetzungsUnterschriftSection = chapterLEB.addSection(unterschriftParagraph);
			facheinschaetzungsUnterschriftSection.setNumberDepth(0);
			unterschriftParagraph.add(new Phrase(facheinschaetzungsdaten.getUnterschrift().replace('\t', '\0'), standardTextFont));
			unterschriftParagraph.setAlignment(Element.ALIGN_RIGHT);
			unterschriftParagraph.add(Chunk.NEWLINE);
			unterschriftParagraph.add(Chunk.NEWLINE);			
			document.add(facheinschaetzungsUnterschriftSection);
			alertHurenkind(writer);
			insertDummyLineIfNecessary(writer);
		}
	}



	private void addIndividuelleEinschaetzung(Chapter chapterLEB, PdfWriter writer) throws DocumentException, PdfFormatierungsException {
		if (!lebData.getIndividuelleEinschaetzung().isEmpty()) {
			sectionCount += 1;
			breakHurenkind(writer);
			breakSchusterjunge(writer);
			Paragraph paragraphIndividuelleEinschaetzung = new Paragraph();
			Section individuelleEinschaetzungsTextSection = chapterLEB.addSection(paragraphIndividuelleEinschaetzung);
			individuelleEinschaetzungsTextSection.setNumberDepth(0);
			Paragraph schuelereinschaetzungParapgraph = new Paragraph(lebData.getIndividuelleEinschaetzung().replace('\t', '\0'), standardTextFont);
			schuelereinschaetzungParapgraph.setAlignment(Element.ALIGN_JUSTIFIED);
			schuelereinschaetzungParapgraph.setLeading(FIXED_LEADING_TEXT, zeilenabstandsfaktor);
			individuelleEinschaetzungsTextSection.add(schuelereinschaetzungParapgraph);			
			document.add(individuelleEinschaetzungsTextSection);
			document.add(getKlassenlehrerunterschrift(chapterLEB));
			alertHurenkind(writer);
			insertDummyLineIfNecessary(writer);
		}
	}

	private void addKlassenbrief(Chapter chapterLEB, PdfWriter writer) throws DocumentException, PdfFormatierungsException {
		if (!lebData.getKlassenbrief().isEmpty()) {
			sectionCount += 1;
			breakSchusterjunge(writer);
			Paragraph paragraphKlassenbrief = new Paragraph();
			Section klassenbriefSection = chapterLEB.addSection(paragraphKlassenbrief);
			klassenbriefSection.setNumberDepth(0);
			Paragraph klasseneinschaetzungParapgraph = new Paragraph(lebData.getKlassenbrief().replace('\t', '\0'), standardTextFont);
			klasseneinschaetzungParapgraph.setAlignment(Element.ALIGN_JUSTIFIED);
			klasseneinschaetzungParapgraph.setLeading(FIXED_LEADING_TEXT, zeilenabstandsfaktor);
			klasseneinschaetzungParapgraph.add(Chunk.NEWLINE);
			if (lebData.getIndividuelleEinschaetzung().isEmpty()) {
				klassenbriefSection.add(klasseneinschaetzungParapgraph);
				document.add(klassenbriefSection);
				document.add(getKlassenlehrerunterschrift(chapterLEB));
			} else {
				klasseneinschaetzungParapgraph.add(Chunk.NEWLINE);
				klassenbriefSection.add(klasseneinschaetzungParapgraph);
				document.add(klassenbriefSection);
			}
			alertLonelyHeader(writer);
			insertDummyLineIfNecessary(writer);
		}
	}
	
	private Section getKlassenlehrerunterschrift(Section chapterLEB) {
		Paragraph unterschriftParagraph = new Paragraph();
		Section klassenbriefUnterschriftSection = chapterLEB.addSection(unterschriftParagraph);
		klassenbriefUnterschriftSection.setNumberDepth(0);
		unterschriftParagraph.add(new Phrase(lebData.getKlassenlehrerUnterschrift().replace('\t', '\0'), standardTextFont));
		unterschriftParagraph.setAlignment(Element.ALIGN_RIGHT);
		unterschriftParagraph.add(Chunk.NEWLINE);
		unterschriftParagraph.add(Chunk.NEWLINE);			
		return klassenbriefUnterschriftSection;
	}

	private void breakHurenkind(PdfWriter writer) {
		if (lebCreator.getHurenkinderMarker().containsKey(sectionCount)) {
			document.newPage();
		}		
	}

	private void breakSchusterjunge(PdfWriter writer) {
		if (writer.getVerticalPosition(false) < getSchusterjungenCriticalDistance()) {
			document.newPage();
		}		
	}
	
	private void alertHurenkind(PdfWriter writer) throws PdfFormatierungsException {
		if (writer.getVerticalPosition(false) > getHurenkindCriticalDistance() &&
				 lebCreator.getHurenkindCount() < HURENKIND_MAX) {
			lebCreator.getHurenkinderMarker().put(sectionCount, true);
			throw new PdfFormatierungsException(PdfFormatierungsException.TYPE_HURENKIND);
		}		
	}
	
	private void alertLonelyHeader(PdfWriter writer) throws PdfFormatierungsException {
		if (writer.getVerticalPosition(false) > getHurenkindCriticalDistance() && lebCreator.getHurenkindCount() < HURENKIND_MAX) {
			throw new PdfFormatierungsException(PdfFormatierungsException.TYPE_LONELY_HEADER_OR_FOOTER);
		}
	}
	
	private void alertLonelyFooter(PdfWriter writer, float footerMargin) throws PdfFormatierungsException {
		if ((writer.getVerticalPosition(false) < footerMargin || writer.getVerticalPosition(false) > getHurenkindCriticalDistance()) && 
				lebCreator.getDummyLineCount() < DUMMY_LINE_MAX) {
			throw new PdfFormatierungsException(PdfFormatierungsException.TYPE_LONELY_HEADER_OR_FOOTER);
		}		
	}

	private void insertDummyLineIfNecessary(PdfWriter writer) throws DocumentException {
		int countNewLines = 0;
		while (countNewLines < lebCreator.getDummyLineCount()) {
			countNewLines += 1;
			if (writer.getVerticalPosition(false) < getSchusterjungenCriticalDistance()) {
				break;
			}
			document.add(Chunk.NEWLINE);
		}		
	}

	private float getSchusterjungenCriticalDistance() {
		return (standardTextFont.getSize() * zeilenabstandsfaktor * SCHUSTERJUNGEN_DISTANCE_FACTOR_FOR_FONTSIZE) + bottomMargin;
	}
	
	private float getHurenkindCriticalDistance() {
		return 840 - (standardTextFont.getSize() * zeilenabstandsfaktor * HURENKIND_DISTANCE_FACTOR_FOR_FONTSIZE) - topMargin;
	}
	
	@Override
	public ByteArrayInputStream getStream() {
		return new ByteArrayInputStream(outputStream.toByteArray());
	}
	
	public ByteArrayOutputStream getByteArrayOutputStream() {
		return outputStream;
	}
	
}
