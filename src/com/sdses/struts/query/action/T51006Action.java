package com.sdses.struts.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.grid.GridConfigMethod;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;

public class T51006Action extends BaseExcelQueryAction{

	@Override
	protected void deal() {
		HttpServletRequest request = ServletActionContext.getRequest();
        GridConfigMethod gridCfg = new GridConfigMethod();
        Object[] queryAlipayInfo = new Object[2];
        Object[] queryAlipayInfo2 = new Object[2];
        String[] columnStr = new String[30];
        String midStr2 = ServletActionContext.getContext().getApplication().get("recordNum").toString();
        String dateStart = request.getParameter("startPayTime");
        String dateEnd = request.getParameter("endPayTime");
        Constants.QUERY_RECORD_COUNT = Integer.parseInt(midStr2);
        try {
            queryAlipayInfo = gridCfg.getAlipayAccount(0, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Constants.QUERY_RECORD_COUNT = 15;
        columnStr[0] = "应用ID";
        columnStr[1] = "支付宝交易号";
        columnStr[2] = "商户订单号";
        columnStr[3] = "业务类型";
        columnStr[4] = "商品名称";
        columnStr[5] = "门店编码";
        columnStr[6] = "门店名称";
        columnStr[7] = "操作员";
        columnStr[8] = "终端号";
        columnStr[9] = "对方账户";
        columnStr[10] = "订单金额(分)";
        columnStr[11] = "实收金额(分)";
        columnStr[12] = "支付宝红包(分)";
        columnStr[13] = "集分宝(分)";
        columnStr[14] = "支付宝优惠(分)";
        columnStr[15] = "商家优惠(分)";
        columnStr[16] = "券核销金额(分)";
        columnStr[17] = "券名称";
        columnStr[18] = "商家红包消费金额(分)";
        columnStr[19] = "卡消费金额(分)";
        columnStr[20] = "退款批次号/请求号";
        columnStr[21] = "服务费(分)";
        columnStr[22] = "利润(分)";
        columnStr[23] = "备注";
        columnStr[24] = "支付机构";
        columnStr[25] = "创建时间";
        columnStr[26] = "完成时间";
        columnStr[27] = "对账状态";
        columnStr[28] = "对账描述";
        columnStr[29] = "对账时间";
     
        List<Object[]> statInfoList = (List<Object[]>) queryAlipayInfo[0];
        List<Object[]> statInfoList2 = (List<Object[]>) queryAlipayInfo2[0];
        fillData2(statInfoList, statInfoList2, columnStr, "支付宝对账查询", dateStart+"-"+dateEnd+"支付宝对账查询", 30,null);
	}
	
	@Override
	protected String getFileKey() {
		return ExcelName.EN_51006;
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
