package com.huateng.struts.query.action;



import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.StringUtil;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;
import com.huateng.system.util.CommonFunction;


public class T50104Action extends BaseExcelQueryAction {

	@Override
	
	protected void deal() {
		
		String date = CommonFunction.getCurrentDate();

		c = sheet.getRow(1).getCell(1);
		if(!StringUtil.isNull(date)){
			if(c == null){
				sheet.getRow(1).createCell(1);
			}
			c.setCellValue(date);
		}
		List countList= (List)ServletActionContext.getRequest().getSession().getAttribute("transList");
		ServletActionContext.getRequest().getSession().removeAttribute("transList");
		int rowNum =3, cellNum = 0,columnNum = 13;
		fillData( countList, rowNum, cellNum, columnNum);

	}


	@Override
	protected String getFileKey() {
		return ExcelName.EN_5162;
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