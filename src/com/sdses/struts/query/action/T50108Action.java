package com.sdses.struts.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.grid.GridConfigMethod;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;

public class T50108Action extends BaseExcelQueryAction {
    // 支付方式
    private String payType;

    @Override
    protected void deal() {
        // TODO Auto-generated method stub  
        HttpServletRequest request = ServletActionContext.getRequest();
        GridConfigMethod gridCfg = new GridConfigMethod();
        Object[] payBillInfo = new Object[2];
        Object[] payBill2Info = new Object[2];
        String[] columnStr = new String[4];
        String midStr2 = ServletActionContext.getContext().getApplication().get("recordNum").toString();
        Constants.QUERY_RECORD_COUNT = Integer.parseInt(midStr2);
        try {
            payBillInfo = gridCfg.getComparePayBill(0, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Constants.QUERY_RECORD_COUNT = 15;
        columnStr[0] = "交易日期";
        columnStr[1] = "对账文件名";
        columnStr[2] = "对账进度";
        columnStr[3] = "处理时间";

        List<Object[]> statInfoList = (List<Object[]>) payBillInfo[0];
        List<Object[]> statInfo2List = (List<Object[]>) payBill2Info[0];
        fillData2(statInfoList, statInfo2List, columnStr, "对账查询", "对账查询", 4, null);
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
