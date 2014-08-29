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

import org.fossa.rolp.data.leb.LebData;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class LebPageHelper extends PdfPageEventHelper {

	private Image csmLogoImage;
	private LebData lebData;
	private Font fusszeilenFont;

	public LebPageHelper(LebData lebData, Image csmLogoImage, Font fusszeilenFont) {
		super();
		this.csmLogoImage = csmLogoImage;
		this.lebData = lebData;
		this.fusszeilenFont = fusszeilenFont;
	}

	@Override
	public void onOpenDocument(PdfWriter writer, Document document) {
		writer.getDirectContent().createTemplate(60, 16);
	}
	
	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		PdfPTable table = new PdfPTable(2);
		table.setTotalWidth(527);
		table.setWidthPercentage(100);
		table.setLockedWidth(true);
		table.getDefaultCell().setFixedHeight(105f);
		table.getDefaultCell().setBorderWidth(0);
		table.addCell("");			
		table.addCell(csmLogoImage);
		table.writeSelectedRows(0, -1, 100, 840, writer.getDirectContent());
		ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_LEFT, 
                    new Phrase(lebData.getSchuelername() + " " + lebData.getSchuljahr() + " " + lebData.getSchulhalbjahr().getId() + " Seite " + document.getPageNumber(), fusszeilenFont),
                    100, 75, 0);
	}
}
