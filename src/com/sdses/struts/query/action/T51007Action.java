package com.sdses.struts.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.grid.GridConfigMethod;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;

public class T51007Action extends BaseExcelQueryAction{

	@Override
	protected void deal() {
		HttpServletRequest request = ServletActionContext.getRequest();
        GridConfigMethod gridCfg = new GridConfigMethod();
        Object[] queryAlipayInfo = new Object[2];
        Object[] queryAlipayInfo2 = new Object[2];
        String[] columnStr = new String[28];
        String midStr2 = ServletActionContext.getContext().getApplication().get("recordNum").toString();
        String dateStart = request.getParameter("startPayTime");
        String dateEnd = request.getParameter("endPayTime");
        Constants.QUERY_RECORD_COUNT = Integer.parseInt(midStr2);
        try {
            queryAlipayInfo = gridCfg.getWeChatAccount(0, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Constants.QUERY_RECORD_COUNT = 15;
        columnStr[0] = "公众账号ID";
        columnStr[1] = "微信订单号";
        columnStr[2] = "商户订单号";
        columnStr[3] = "商户号";
        columnStr[4] = "子商户号";
        columnStr[5] = "用户标识";
        columnStr[6] = "交易类型";
        columnStr[7] = "交易状态";
        columnStr[8] = "付款银行";
        columnStr[9] = "货币种类";
        columnStr[10] = "总金额(分)";
        columnStr[11] = "企业红包金额(分)";
        columnStr[12] = "微信退款单号";
        columnStr[13] = "商户退款单号";
        columnStr[14] = "退款金额(分)";
        columnStr[15] = "企业红包退款金额(分)";
        columnStr[16] = "退款类型";
        columnStr[17] = "退款状态";
        columnStr[18] = "商品名称";
        columnStr[19] = "商户数据包";
        columnStr[20] = "手续费(分)";
        columnStr[21] = "费率";
        columnStr[22] = "设备号";
        columnStr[23] = "支付机构";
        columnStr[24] = "交易时间";
        columnStr[25] = "对账状态";
        columnStr[26] = "对账描述";
        columnStr[27] = "对账时间";
     
        List<Object[]> statInfoList = (List<Object[]>) queryAlipayInfo[0];
        List<Object[]> statInfoList2 = (List<Object[]>) queryAlipayInfo2[0];
        fillData2(statInfoList, statInfoList2, columnStr, "微信对账查询", dateStart+"-"+dateEnd+"微信对账查询", 28,null);
	}
	
	@Override
	protected String getFileKey() {
		return ExcelName.EN_51007;
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
