package com.huateng.struts.query.action;

import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.grid.GridConfigMethod;
import com.huateng.struts.base.action.T10101Action;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;
import com.huateng.system.util.CommonFunction;

public class T51008Action2 extends BaseExcelQueryAction {
	private static final long serialVersionUID = 1L;


	@Override
	protected void deal() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		Object[] queryRepInfo = new Object[2];
		Object[] queryRep2Info = new Object[2];
		queryRepInfo = GridConfigMethod.getAlipayShareInfo2(0, request);
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
		
		String midStr2 = ServletActionContext.getContext().getApplication().get("recordNum").toString();
		Constants.QUERY_RECORD_COUNT = Integer.parseInt(midStr2);
		Constants.QUERY_RECORD_COUNT = 15;
		
		String[] columnStr = null;
		if("0".equals(operator.getOprBrhLvl())){
			columnStr = new String[11];
			columnStr[0] = "统计月份";
			columnStr[1] = "商户名称";
			columnStr[2] = "归属机构";
			columnStr[3] = "商户号";
			columnStr[4] = "终端号";
			columnStr[5] = "原始金额（元）";
			columnStr[6] = "交易金额（元）";
			columnStr[7] = "商户分润（元）";
			columnStr[8] = "1级机构分润（元）";
			columnStr[9] = "2级机构分润（元）";
			columnStr[10] = "3级机构分润（元）";
		}else if("1".equals(operator.getOprBrhLvl())){
			columnStr = new String[10];
			columnStr[0] = "统计月份";
			columnStr[1] = "商户名称";
			columnStr[2] = "归属机构";
			columnStr[3] = "商户号";
			columnStr[4] = "终端号";
			columnStr[5] = "原始金额（元）";
			columnStr[6] = "交易金额（元）";
			columnStr[7] = "商户分润（元）";
			columnStr[8] = "2级机构分润（元）";
			columnStr[9] = "3级机构分润（元）";
		}else if("2".equals(operator.getOprBrhLvl())){
			columnStr = new String[9];
			columnStr[0] = "统计月份";
			columnStr[1] = "商户名称";
			columnStr[2] = "归属机构";
			columnStr[3] = "商户号";
			columnStr[4] = "终端号";
			columnStr[5] = "原始金额（元）";
			columnStr[6] = "交易金额（元）";
			columnStr[7] = "商户分润（元）";
			columnStr[8] = "3级机构分润（元）";
		}
		
		float totalOrderFee = 0;
        float totalOrgFee = 0;
        float totalMchtShare = 0;
        float totalOneOrgShare = 0;
        float totalTwoOrgShare = 0;
        float totalThreeOrgShare = 0;

        for (int i = 0; i < statInfoList.size(); i++) {
            Object[] obj = statInfoList.get(i);
            try {
                if (obj[5] != null && !obj[5].equals("")) {
                	totalOrderFee += Float.parseFloat(obj[5].toString());
                } 
                if (obj[6] != null && !obj[6].equals("")) {
                    totalOrgFee += Float.parseFloat(obj[6].toString());
                } 
                
                if (obj[7] != null && !obj[7].equals("")) {
                	totalMchtShare += Float.parseFloat(obj[7].toString());
                }
                if("1".equals(operator.getOprBrhLvl())){//2级机构
                	obj[8] = obj[9];
                	obj[9] = obj[10];
    			}else if("2".equals(operator.getOprBrhLvl())){//3级机构
    				obj[8] = obj[10];
    			}
                if (obj[8] != null && !obj[8].equals("")) {
                	totalOneOrgShare += Float.parseFloat(obj[8].toString());
                }
                if (obj[9] != null && !obj[9].equals("")) {
                	totalTwoOrgShare += Float.parseFloat(obj[9].toString());
                }
                if (obj[10] != null && !obj[10].equals("")) {
                	totalThreeOrgShare += Float.parseFloat(obj[10].toString());
                }
                
                statInfoList.set(i, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        DecimalFormat df = new DecimalFormat("0.00");
        Object[] total = new Object[11];
        total[0] = "合计";
        if(totalOrderFee != 0){
        	total[5] = df.format(totalOrderFee);
        }
        if(totalOrgFee != 0){
        	total[6] = df.format(totalOrgFee);
        }
        if(totalMchtShare != 0){
        	total[7] = df.format(totalMchtShare);
        }
        if(totalOneOrgShare != 0){
        	total[8] = df.format(totalOneOrgShare);
        }
        if(totalTwoOrgShare != 0){
        	total[9] = df.format(totalTwoOrgShare);
        }
        if(totalThreeOrgShare != 0){
        	total[10] = df.format(totalThreeOrgShare);
        }
        statInfoList.add(total);
		
		fillData2(statInfoList, statInfo2List, columnStr, "支付宝分润按月统计(汇总)", "支付宝分润按月统计(汇总)", columnStr.length, null);
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
