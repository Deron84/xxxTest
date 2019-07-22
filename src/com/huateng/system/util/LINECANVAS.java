package com.huateng.system.util;

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;

public class LINECANVAS implements PdfPCellEvent {

	public void cellLayout(PdfPCell cell, Rectangle rect,
			PdfContentByte[] canvas) {

		PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
		cb.saveState();
		cb.setLineWidth(0.5f);
		cb.setLineDash(new float[] { 2f, 2f }, 0);
		cb.moveTo(rect.getLeft() + 2, rect.getBottom());
		cb.lineTo(rect.getRight() - 2, rect.getBottom());
		cb.stroke();
		cb.restoreState();
	}
}
