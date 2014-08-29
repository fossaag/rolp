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

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PdfFormatHelper {

	private static PdfPTable prebuildHeaderTable() throws DocumentException {
		PdfPTable table = new PdfPTable(6);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] {0.12f, 0.2f, 0.15f, 0.2f, 0.2f, 0.08f});
		return table;
	}

	public static PdfPTable buildHeaderNameLine(String schuelername, Font headerFont) throws DocumentException {
		PdfPCell labelCell = new PdfPCell(new Phrase("Name", headerFont));
		labelCell.setBorder(Rectangle.BOTTOM);
		labelCell.setBorderWidth(1f);

		PdfPCell nameCell = new PdfPCell(new Phrase(schuelername, headerFont));
		nameCell.setBorder(Rectangle.BOTTOM);
		nameCell.setBorderWidth(1f);
		nameCell.setColspan(5);
		
		PdfPTable table = prebuildHeaderTable();
		table.addCell(labelCell);
		table.addCell(nameCell);
		
		return table;
	}
	
	public static PdfPTable buildHeaderKlassendatenLine(LebData lebData, Font headerFont) throws DocumentException {
		PdfPCell leftLabelCell = new PdfPCell(new Phrase("Klasse", headerFont));
		leftLabelCell.setBorder(Rectangle.BOTTOM);
		leftLabelCell.setBorderWidth(1f);

		PdfPCell leftValueCell = new PdfPCell(new Phrase(lebData.getKlassenname(), headerFont));
		leftValueCell.setBorder(Rectangle.BOTTOM);
		leftValueCell.setBorderWidth(1f);

		PdfPCell centerLabelCell = new PdfPCell(new Phrase("Schuljahr", headerFont));
		centerLabelCell.setBorder(Rectangle.BOTTOM);
		centerLabelCell.setBorderWidth(1f);

		PdfPCell centerValueCell = new PdfPCell(new Phrase(lebData.getSchuljahr(), headerFont));
		centerValueCell.setBorder(Rectangle.BOTTOM);
		centerValueCell.setBorderWidth(1f);

		PdfPCell rightLabelCell = new PdfPCell(new Phrase("Schulhalbjahr", headerFont));
		rightLabelCell.setBorder(Rectangle.BOTTOM);
		rightLabelCell.setBorderWidth(1f);

		PdfPCell rightValueCell = new PdfPCell(new Phrase(lebData.getSchulhalbjahr().getId().toString(), headerFont));
		rightValueCell.setBorder(Rectangle.BOTTOM);
		rightValueCell.setBorderWidth(1f);

		PdfPTable table = prebuildHeaderTable();
		table.addCell(leftLabelCell);
		table.addCell(leftValueCell);
		table.addCell(centerLabelCell);
		table.addCell(centerValueCell);
		table.addCell(rightLabelCell);
		table.addCell(rightValueCell);
		return table;
	}

	public static PdfPTable buildFooterVersetzungsvermerkLine(String versetzungsvermerk, Font footerFont) throws DocumentException {
		PdfPCell labelCell = new PdfPCell(new Phrase("Versetzungsvermerk", footerFont));
		labelCell.setBorder(Rectangle.BOTTOM);
		labelCell.setBorderWidth(1f);

		PdfPCell nameCell = new PdfPCell(new Phrase(versetzungsvermerk, footerFont));
		nameCell.setBorder(Rectangle.BOTTOM);
		nameCell.setBorderWidth(1f);
		
		PdfPTable table = new PdfPTable(2);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setWidthPercentage(100f);
		table.addCell(labelCell);
		table.addCell(nameCell);
		table.setWidths(new float[] {0.3f, 0.7f});
		return table;
	}

	public static PdfPTable buildFooterDatumLine(String datumString, Font footerFont) throws DocumentException {
		PdfPCell labelCell = new PdfPCell(new Phrase("Datum", footerFont));
		labelCell.setBorder(Rectangle.BOTTOM);
		labelCell.setBorderWidth(1f);

		PdfPCell nameCell = new PdfPCell(new Phrase(datumString, footerFont));
		nameCell.setBorder(Rectangle.BOTTOM);
		nameCell.setBorderWidth(1f);
		
		PdfPTable table = new PdfPTable(2);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setWidthPercentage(100f);
		table.addCell(labelCell);
		table.addCell(nameCell);
		table.setWidths(new float[] {0.3f, 0.7f});
		return table;
	}

	public static PdfPTable buildFooterDienstsiegelLine(Font footerFont) throws DocumentException {
		PdfPCell leftCell = new PdfPCell(new Phrase("", footerFont));
		leftCell.setBorder(Rectangle.NO_BORDER);
		leftCell.setBorderWidth(1f);
		
		PdfPCell centerCell = new PdfPCell(new Phrase("Dienstsiegel der Schule", footerFont));
		centerCell.setBorder(Rectangle.NO_BORDER);
		centerCell.setBorderWidth(1f);
		centerCell.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell rightCell = new PdfPCell(new Phrase("", footerFont));
		rightCell.setBorder(Rectangle.NO_BORDER);
		rightCell.setBorderWidth(1f);
		
		PdfPTable table = new PdfPTable(3);
		table.setHorizontalAlignment(Element.ALIGN_MIDDLE);
		table.setWidthPercentage(100f);
		table.addCell(leftCell);
		table.addCell(centerCell);
		table.addCell(rightCell);
		table.setWidths(new float[] {0.3f, 0.3f, 0.3f});
		return table;
	}

	public static PdfPTable buildFooterUnterschriftenLine(Font footerFont) throws DocumentException {
		PdfPCell leftCell = new PdfPCell(new Phrase("Schulleiter(in)", footerFont));
		leftCell.setBorder(Rectangle.TOP);
		leftCell.setBorderWidth(1f);
		leftCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		PdfPCell centerCell = new PdfPCell(new Phrase("", footerFont));
		centerCell.setBorder(Rectangle.TOP);
		centerCell.setBorderWidth(1f);
		centerCell.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell rightCell = new PdfPCell(new Phrase("Klassenleiter(in)", footerFont));
		rightCell.setBorder(Rectangle.TOP);
		rightCell.setBorderWidth(1f);
		rightCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100f);
		table.addCell(leftCell);
		table.addCell(centerCell);
		table.addCell(rightCell);
		table.setWidths(new float[] {0.3f, 0.3f, 0.3f});
		return table;
	}

	public static PdfPTable buildFooterKenntnisLine(Font footerFont) {
		PdfPCell cell = new PdfPCell(new Phrase("Kenntnis genommen: Erziehungsberechtigte", footerFont));
		cell.setBorder(Rectangle.TOP);
		cell.setBorderWidth(1f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPTable table = new PdfPTable(1);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setWidthPercentage(100f);
		table.addCell(cell);
		return table;
	}
	
	public static PdfPTable buildFooterHalbjahrDatumLine(String datumString, Font footerFont) throws DocumentException {
		PdfPCell leftCell = new PdfPCell(new Phrase(datumString, footerFont));
		leftCell.setBorder(Rectangle.NO_BORDER);
		leftCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		PdfPCell centerCell = new PdfPCell(new Phrase("", footerFont));
		centerCell.setBorder(Rectangle.NO_BORDER);
		centerCell.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell rightCell = new PdfPCell(new Phrase("", footerFont));
		rightCell.setBorder(Rectangle.NO_BORDER);
		rightCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100f);
		table.addCell(leftCell);
		table.addCell(centerCell);
		table.addCell(rightCell);
		table.setWidths(new float[] {0.3f, 0.3f, 0.3f});
		return table;
	}

	public static PdfPTable buildFooterHalbjahrDatumKlassenleiterLine(Font footerFont) throws DocumentException {
		PdfPCell leftCell = new PdfPCell(new Phrase("Datum", footerFont));
		leftCell.setBorder(Rectangle.TOP);
		leftCell.setBorderWidth(1f);
		leftCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		PdfPCell centerCell = new PdfPCell(new Phrase("", footerFont));
		centerCell.setBorder(Rectangle.TOP);
		centerCell.setBorderWidth(1f);
		centerCell.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell rightCell = new PdfPCell(new Phrase("Klassenleiter(in)", footerFont));
		rightCell.setBorder(Rectangle.TOP);
		rightCell.setBorderWidth(1f);
		rightCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100f);
		table.addCell(leftCell);
		table.addCell(centerCell);
		table.addCell(rightCell);
		table.setWidths(new float[] {0.3f, 0.3f, 0.3f});
		return table;
	}
}
