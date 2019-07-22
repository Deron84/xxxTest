package com.huateng.struts.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.grid.GridConfigMethod;
import com.huateng.struts.base.action.T10101Action;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;

public class T51008Action extends BaseExcelQueryAction {
	private static final long serialVersionUID = 1L;


	@Override
	protected void deal() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		Object[] queryRepInfo = new Object[2];
		Object[] queryRep2Info = new Object[2];
		queryRepInfo = GridConfigMethod.getAlipayShareInfo(0, request);
		@SuppressWarnings("unchecked")
		List<Object[]> statInfoList = (List<Object[]>) queryRepInfo[0];
		@SuppressWarnings("unchecked")
		List<Object[]> statInfo2List = (List<Object[]>) queryRep2Info[0];
		String[] columnStr = null;
		if("0".equals(operator.getOprBrhLvl())){
			columnStr = new String[25];
			columnStr[0] = "交易时间";
			columnStr[1] = "商户名称";
			columnStr[2] = "归属机构";
			columnStr[3] = "商户号";
			columnStr[4] = "终端号";
			columnStr[5] = "终端流水号";
			columnStr[6] = "商户订单号";
			columnStr[7] = "原始金额（元）";
			columnStr[8] = "交易金额（元）";
			columnStr[9] = "支付方式";
			columnStr[10] = "交易状态";
			columnStr[11] = "扫码支付商户号";
			columnStr[12] = "第三方订单号";
			columnStr[13] = "第三方机构";
			columnStr[14] = "异步通知时间";
			columnStr[15] = "签约网点";
			columnStr[16] = "网点名称";
			columnStr[17] = "支付宝卖家ID";
			columnStr[18] = "支付宝买家ID";
			columnStr[19] = "支付宝卖家账号";
			columnStr[20] = "支付宝买家账号";
			columnStr[21] = "商户分润（元）";
			columnStr[22] = "1级机构分润（元）";
			columnStr[23] = "2级机构分润（元）";
			columnStr[24] = "3级机构分润（元）";
		}else if("1".equals(operator.getOprBrhLvl())){
			columnStr = new String[24];
			columnStr[0] = "交易时间";
			columnStr[1] = "商户名称";
			columnStr[2] = "归属机构";
			columnStr[3] = "商户号";
			columnStr[4] = "终端号";
			columnStr[5] = "终端流水号";
			columnStr[6] = "商户订单号";
			columnStr[7] = "原始金额（元）";
			columnStr[8] = "交易金额（元）";
			columnStr[9] = "支付方式";
			columnStr[10] = "交易状态";
			columnStr[11] = "扫码支付商户号";
			columnStr[12] = "第三方订单号";
			columnStr[13] = "第三方机构";
			columnStr[14] = "异步通知时间";
			columnStr[15] = "签约网点";
			columnStr[16] = "网点名称";
			columnStr[17] = "支付宝卖家ID";
			columnStr[18] = "支付宝买家ID";
			columnStr[19] = "支付宝卖家账号";
			columnStr[20] = "支付宝买家账号";
			columnStr[21] = "商户分润（元）";
			columnStr[22] = "2级机构分润（元）";
			columnStr[23] = "3级机构分润（元）";
		}else if("2".equals(operator.getOprBrhLvl())){
			columnStr = new String[23];
			columnStr[0] = "交易时间";
			columnStr[1] = "商户名称";
			columnStr[2] = "归属机构";
			columnStr[3] = "商户号";
			columnStr[4] = "终端号";
			columnStr[5] = "终端流水号";
			columnStr[6] = "商户订单号";
			columnStr[7] = "原始金额（元）";
			columnStr[8] = "交易金额（元）";
			columnStr[9] = "支付方式";
			columnStr[10] = "交易状态";
			columnStr[11] = "扫码支付商户号";
			columnStr[12] = "第三方订单号";
			columnStr[13] = "第三方机构";
			columnStr[14] = "异步通知时间";
			columnStr[15] = "签约网点";
			columnStr[16] = "网点名称";
			columnStr[17] = "支付宝卖家ID";
			columnStr[18] = "支付宝买家ID";
			columnStr[19] = "支付宝卖家账号";
			columnStr[20] = "支付宝买家账号";
			columnStr[21] = "商户分润（元）";
			columnStr[22] = "3级机构分润（元）";
		}
		
		String midStr2 = ServletActionContext.getContext().getApplication().get("recordNum").toString();
		Constants.QUERY_RECORD_COUNT = Integer.parseInt(midStr2);
		Constants.QUERY_RECORD_COUNT = 15;
		
		T10101Action t10101Action = new T10101Action();
		for (Object[] objArr : statInfoList) {
			if (objArr[2] != null && !objArr[2].equals("")) {
				String brhId = objArr[2].toString();
				objArr[2] = brhId + "-" + t10101Action.getT10101BO().get(brhId).getBrhName();
			} else {
				objArr[2] = "";
			}
			if("1".equals(operator.getOprBrhLvl())){
				objArr[22] = objArr[23];
				objArr[23] = objArr[24];
			}else if("2".equals(operator.getOprBrhLvl())){
				objArr[22] = objArr[24];
			}
		}
		fillData2(statInfoList, statInfo2List, columnStr, "支付宝分润按月统计(明细)", "支付宝分润按月统计(明细)", columnStr.length, null);
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
