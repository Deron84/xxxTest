package com.huateng.struts.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.grid.GridConfigMethod;
import com.huateng.struts.base.action.T10101Action;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;

public class T51009Action extends BaseExcelQueryAction {
	private static final long serialVersionUID = 1L;


	@Override
	protected void deal() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Object[] queryRepInfo = new Object[2];
		Object[] queryRep2Info = new Object[2];
		String[] columnStr = new String[5];
		String midStr2 = ServletActionContext.getContext().getApplication().get("recordNum").toString();
		Constants.QUERY_RECORD_COUNT = Integer.parseInt(midStr2);
		queryRepInfo = GridConfigMethod.getFeeStaInfo(0, request);
		Constants.QUERY_RECORD_COUNT = 15;
		columnStr[0] = "统计月份";
		columnStr[1] = "商户名称";
		columnStr[2] = "归属机构";
		columnStr[3] = "商户号";
		columnStr[4] = "手续费(元)";
		@SuppressWarnings("unchecked")
		List<Object[]> statInfoList = (List<Object[]>) queryRepInfo[0];
		@SuppressWarnings("unchecked")
		List<Object[]> statInfo2List = (List<Object[]>) queryRep2Info[0];
		T10101Action t10101Action = new T10101Action();
		for (Object[] objArr : statInfoList) {
			if (objArr[2] != null && !objArr[1].equals("")) {
				String brhId = objArr[2].toString();
				objArr[2] = brhId + "-" + t10101Action.getT10101BO().get(brhId).getBrhName();
			} else {
				objArr[2] = "";
			}
		}
		
		float totalFee = 0;
        for (int i = 0; i < statInfoList.size(); i++) {
            Object[] obj = statInfoList.get(i);
            try {
                if (obj[4] != null && !obj[4].equals("")) {
                	totalFee += Float.parseFloat(obj[4].toString());
                } 
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        Object[] total = new Object[5];
        total[0] = "合计";
        if(totalFee != 0){
        	total[4] = totalFee;
        }
        statInfoList.add(total);
		
		fillData2(statInfoList, statInfo2List, columnStr, "手续费统计", "手续费统计", 5, null);
	}

	@Override
	protected String getFileKey() {
		return ExcelName.EN_521;
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
