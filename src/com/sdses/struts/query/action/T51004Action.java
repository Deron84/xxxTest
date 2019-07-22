package com.sdses.struts.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.grid.GridConfigMethod;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;

public class T51004Action extends BaseExcelQueryAction{
	@Override
    protected void deal() {
        // TODO Auto-generated method stub  
        HttpServletRequest request = ServletActionContext.getRequest();
        GridConfigMethod gridCfg = new GridConfigMethod();
        Object[] queryRepInfo = new Object[2];
        String[] columnStr = new String[15];
        String midStr2 = ServletActionContext.getContext().getApplication().get("recordNum").toString();
        String dateStart = request.getParameter("startDate");
        String dateEnd = request.getParameter("endDate");
        Constants.QUERY_RECORD_COUNT = Integer.parseInt(midStr2);
        try {
            queryRepInfo = gridCfg.getDealTxnInfo(0, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Constants.QUERY_RECORD_COUNT = 15;
        columnStr[0] = "地区";
        columnStr[1] = "机构名称";
        columnStr[2] = "商户名称";
        columnStr[3] = "商户号";
        columnStr[4] = "营销员";
        columnStr[5] = "总金额";
        columnStr[6] = "总笔数";
        columnStr[7] = "微信-A";
        columnStr[8] = "微信-C";
        columnStr[9] = "支付宝-A";
        columnStr[10] = "支付宝-C";
        columnStr[11] = "银联-A";
        columnStr[12] = "银联-C";
        columnStr[13] = "其他-A";
        columnStr[14] = "其他-C";
        List<Object[]> statInfoList = (List<Object[]>) queryRepInfo[0];
        fillData3(statInfoList, columnStr, "交易查询统计(商户)", dateStart+"-"+dateEnd+"交易统计(商户)", 15, null);
    }
	@Override
    protected String getFileKey() {
        return ExcelName.EN_51004;
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
