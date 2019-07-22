package com.rail.struts.warehouse.action;



import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.StringUtil;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;
import com.huateng.system.util.CommonFunction;


public class T100200Action extends BaseExcelQueryAction {

	@Override
	
	protected void deal() {
		
		String date = CommonFunction.getCurrentDate();

		c = sheet.getRow(1).getCell(1);
		/*if(!StringUtil.isNull(date)){
			if(c == null){
				sheet.getRow(1).createCell(1);
			}
			c.setCellValue(date);
		}*/
		List countList= new ArrayList();//(List)ServletActionContext.getRequest().getSession().getAttribute("termInfoList");
		//ServletActionContext.getRequest().getSession().removeAttribute("termInfoList");
		int rowNum =2, cellNum = 0,columnNum = 21;
		fillData( countList, rowNum, cellNum, columnNum);

	}


	@Override
	protected String getFileKey() {
		System.out.println(ExcelName.EN_520+"   en520");
		return ExcelName.EN_520;
	}

	@Override
	public String getMsg() {
		return msg;
	}


	@Override
	public boolean isSuccess() {
		return success;
	}

}