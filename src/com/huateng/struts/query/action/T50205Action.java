package com.huateng.struts.query.action;

import java.io.FileOutputStream;

import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T50205Action  extends ReportBaseAction  {

	@Override
	protected String genSql() {
		
		return sql;
	}

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "XLS";
		
		String[] title = {"D01","D02","D03","D04","D11","D12","D13","D14","D21","D22","D23","D24","D31","D32","D33","D34","D41","D42","D43","D44","D51","D52","D53","D54","D61","D71","D81","D82","D83","D84","D91","D92","D93","D94",
		          "D101","D102","D103","D104","D111","D112","D113","D114","D121","D122","D123","D124","D131","D132","D133","D134","D141","D142","D143","D144","D151","D152","D153","D154","D161","D162","D163","D164",
		          "D171","D172","D173","D174","D181","D182","D183","D184","D191","D192","D193","D194","D201","D202","D203","D204","D211","D212","D213","D214","D221","D222","D223","D224","D231","D232","D233","D234",
		          "D241","D242","D243","D244","D251","D252","D253","D254","D261","D262","D263","D264","D271","D272","D273","D274","D281","D282","D283","D284","D291","D292","D293","D294","D301","D302","D303","D304",
		          "D311","D312","D313","D314","D321","D322","D323","D324"};
		
		Object[][] data = new Object[1][title.length];
		
		for(int i=0; i<title.length; i++) {
			
			data[0][i] = "1";
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		reportCreator.initReportData(getJasperInputSteam("T50205.jasper"), parameters, 
				                     reportModel.wrapReportDataSource(), getReportType());

		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + 
		           operator.getOprId() + "_singleUserReport_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, "商户及终端情况（月表）");
		
		callDownload(fileName,"singleUserReport.xls");
	}
	
	/** 
	 * 用于打印出title中的String，用main运行一下，然后粘贴给title
	 * **/
	private void createTitleString() {
		
		StringBuffer s = new StringBuffer();

		for(int i=0; i<33; i++) {
			
			if(6 == i) {
				
				s.append("\"D61\",");
			} else if(7 == i) {
				
				s.append("\"D71\",");
			} else {
				
				for(int j=1; j<5; j++) {
					
					s.append("\"D");
					s.append(i);
					s.append(j);
					s.append("\",");
				}
			}
		}
		
		System.out.println(s);
	}
}
