/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-10-18       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.system.util;

import java.awt.Color;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.huateng.interceptor.LogInter;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-10-18
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class PdfUtil {

	
	private static Logger logger = Logger.getLogger(PdfUtil.class);
	
	public static void create(String mchtId, String selMchtId, String path, List<Object[]> list, LinkedHashMap<String, List<Object[]>> map, Set<String> set) throws Exception{
		
		BaseFont bfChinese = BaseFont.createFont("STSong-Light",
			    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		
		//字体的定义
		Font font17 = new Font(bfChinese, 17, Font.BOLD); 
		Font font8 = new Font(bfChinese, 8, Font.NORMAL); 
		Font font9 = new Font(bfChinese, 9, Font.NORMAL); 
		Font font9Bold = new Font(bfChinese, 9, Font.BOLD);
		Font font8Red = new Font(bfChinese, 8, Font.NORMAL); 
		font8Red.setColor(Color.RED);
		Font font8Green = new Font(bfChinese, 8, Font.NORMAL); 
		font8Green.setColor(Color.GREEN);
		
		logger.info("Starting build document...");
		
		Document document = new Document(PageSize.A4, 36, 36, 36, 36);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
		document.open();
		LINECANVAS border = new LINECANVAS();
		
		//表格的定义
		float[] widths = {0.1f, 0.35f, 0.35f, 0.1f, 0.1f};
		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(100);
		table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		table.getDefaultCell().setFixedHeight(12);
		
		
		//CELL的定义
		PdfPCell cellMchntId = new PdfPCell(new Paragraph(mchtId, font8));
		cellMchntId.setBorder(PdfPCell.BOTTOM);
		cellMchntId.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		PdfPCell cellMchntName = new PdfPCell(new Paragraph(InformationUtil.getMchtName(mchtId), font8));
		cellMchntName.setBorder(PdfPCell.BOTTOM);
		cellMchntName.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		String point = InformationUtil.getCurPaperPoint(selMchtId);
		PdfPCell cellPoint;
		cellPoint = new PdfPCell(new Paragraph(point, font8Red));
		cellPoint.setBorder(PdfPCell.NO_BORDER);
		cellPoint.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		PdfPCell cellLevel;
		String level = InformationUtil.getCurPaperLevel(selMchtId);
		if (Integer.valueOf(point) >= 60) {
			cellLevel = new PdfPCell(new Paragraph(level, font8Green));
		} else {
			cellLevel = new PdfPCell(new Paragraph(level, font8Red));
		}
		cellLevel.setBorder(PdfPCell.NO_BORDER);
		cellLevel.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		
		
		//银行标志
		Image img = Image.getInstance(ServletActionContext.getServletContext().getResource("/ext/resources/images/Title_logo.gif"));
		img.scalePercent(70);
		float w = img.getScaledWidth();
		float h = img.getScaledHeight();
		writer.getDirectContent().addImage(img, w, 0, 0, h, 36, PageSize.A4.getHeight() - 36 - h);

		//标题部分
		PdfPCell cell = new PdfPCell(new Paragraph("商户问卷", font17));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(5);
		cell.setFixedHeight(h);
		cell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell);
		
		cell = new PdfPCell();
		cell.setFixedHeight(20);
		cell.setColspan(5);
		cell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell);
		
		table.addCell(new Paragraph("商户编号：", font8));
		table.addCell(cellMchntId);
		table.addCell(" ");
		table.addCell(new Paragraph("商户得分: ", font8));
		table.addCell(cellPoint);
		
		table.addCell(new Paragraph("商户名称：", font8));
		table.addCell(cellMchntName);
		table.addCell(" ");
		table.addCell(new Paragraph("商户级别: ", font8));
		table.addCell(cellLevel);
		
		document.add(table);
		document.add(new Paragraph("\n\n"));

		
		//主数据部分
		PdfPTable t = new PdfPTable(1);
		
		Iterator<Object[]> it0 = list.iterator();
		int i = 1;
		while (it0.hasNext()) {
			Object[] obj = it0.next();
			PdfPCell c = new PdfPCell();
			c.addElement(new Paragraph("Q" + String.valueOf(i++) + "：" + obj[1].toString(), font9Bold));
			List<Object[]> opts = map.get(obj[0].toString());
			String opt = "";
			Iterator<Object[]> it1 = opts.iterator();
			while (it1.hasNext()) {
				Object[] o = it1.next();
				if (set.contains(o[0])) {
					opt += "● ";
					opt += o[1].toString();
					opt += "         ";
				} else {
					opt += "◎ ";
					opt += o[1].toString();
					opt += "         ";
				}
			}
			c.addElement(new Paragraph(opt.trim(), font9));
			c.setBorder(PdfPCell.NO_BORDER);
			if (i - 1 != list.size()) {
				c.setCellEvent(border);
			}
			t.addCell(c);
		}
		
		PdfPTable oTable = new PdfPTable(1);
		oTable.setWidthPercentage(100);
		PdfPCell ce = new PdfPCell(t);
		ce.setBorderColor(Color.GRAY);
		oTable.addCell(ce);
		
		document.add(oTable);
		
		document.close();
		
		logger.info("Finish build document...");
		
		
		
	}
	
	
	
	public static void create(String path, List<Object[]> list, LinkedHashMap<String, List<Object[]>> map) throws Exception{
		
		BaseFont bfChinese = BaseFont.createFont("STSong-Light",
			    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		
		//字体的定义
		Font font17 = new Font(bfChinese, 17, Font.BOLD); 
		Font font8 = new Font(bfChinese, 8, Font.NORMAL); 
		Font font10 = new Font(bfChinese, 10, Font.NORMAL); 
		Font font10Bold = new Font(bfChinese, 10, Font.BOLD);
		Font font8Red = new Font(bfChinese, 8, Font.NORMAL); 
		font8Red.setColor(Color.RED);
		Font font8Green = new Font(bfChinese, 8, Font.NORMAL); 
		font8Green.setColor(Color.GREEN);
		
		logger.info("Starting build document...");
		
		Document document = new Document(PageSize.A4, 36, 36, 36, 36);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
		document.open();
		LINECANVAS border = new LINECANVAS();
		
		//表格的定义
		float[] widths = {0.1f, 0.35f, 0.55f};
		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(100);
		table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		table.getDefaultCell().setFixedHeight(12);
		
		
		//CELL的定义
		PdfPCell cellMchntId = new PdfPCell(new Paragraph("XXXXXXXXXXXXXXX", font8));
		cellMchntId.setBorder(PdfPCell.BOTTOM);
		cellMchntId.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		PdfPCell cellMchntName = new PdfPCell(new Paragraph("商户问卷预览商户", font8));
		cellMchntName.setBorder(PdfPCell.BOTTOM);
		cellMchntName.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		
		//银行标志
		Image img = Image.getInstance(ServletActionContext.getServletContext().getResource("/ext/resources/images/Title_logo.gif"));
		img.scalePercent(70);
		float w = img.getScaledWidth();
		float h = img.getScaledHeight();
		writer.getDirectContent().addImage(img, w, 0, 0, h, 36, PageSize.A4.getHeight() - 36 - h);

		//标题部分
		PdfPCell cell = new PdfPCell(new Paragraph("商户问卷", font17));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(3);
		cell.setFixedHeight(h);
		cell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell);
		
		cell = new PdfPCell();
		cell.setFixedHeight(20);
		cell.setColspan(3);
		cell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell);
		
		table.addCell(new Paragraph("商户编号：", font8));
		table.addCell(cellMchntId);
		table.addCell(" ");
		
		table.addCell(new Paragraph("商户名称：", font8));
		table.addCell(cellMchntName);
		table.addCell(" ");
		
		document.add(table);
		document.add(new Paragraph("\n\n"));

		
		//主数据部分
		PdfPTable t = new PdfPTable(1);
		
		Iterator<Object[]> it0 = list.iterator();
		int i = 1;
		while (it0.hasNext()) {
			Object[] obj = it0.next();
			PdfPCell c = new PdfPCell();
			c.addElement(new Paragraph("Q" + String.valueOf(i++) + "：" + obj[1].toString(), font10Bold));
			List<Object[]> opts = map.get(obj[0].toString());
			String opt = "";
			Iterator<Object[]> it1 = opts.iterator();
			while (it1.hasNext()) {
				Object[] o = it1.next();
				opt += "◎ ";
				opt += o[1].toString();
				opt += "         ";
			}
			c.addElement(new Paragraph(opt.trim(), font10));
			c.setBorder(PdfPCell.NO_BORDER);
			if (i - 1 != list.size()) {
				c.setCellEvent(border);
			}
			t.addCell(c);
		}
		
		PdfPTable oTable = new PdfPTable(1);
		oTable.setWidthPercentage(100);
		PdfPCell ce = new PdfPCell(t);
		ce.setBorderColor(Color.GRAY);
		oTable.addCell(ce);
		
		document.add(oTable);
		
		document.close();
		
		logger.info("Finish build document...");
		
	}

}
