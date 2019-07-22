package com.sdses.struts.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.grid.GridConfigMethod;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;

public class T50107Action extends BaseExcelQueryAction {
    // 支付方式
    private String payType;

    @Override
    protected void deal() {
        // TODO Auto-generated method stub  
        HttpServletRequest request = ServletActionContext.getRequest();
        GridConfigMethod gridCfg = new GridConfigMethod();
        Object[] payBillInfo = new Object[2];
        Object[] payBill2Info = new Object[2];
        String[] columnStr = new String[7];
        String midStr = ServletActionContext.getContext().getApplication().get("recordNum").toString();
        Constants.QUERY_RECORD_COUNT = Integer.parseInt(midStr);
        try {
            payBillInfo = gridCfg.getPayBillInfo(0, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Constants.QUERY_RECORD_COUNT = 15;
        columnStr[0] = "交易时间";
        columnStr[1] = "商户号";
        columnStr[2] = "商户订单号";
        columnStr[3] = "第三方订单号";
        columnStr[4] = "支付方式";
        columnStr[5] = "订单金额";
        columnStr[6] = "对账结果";

        List<Object[]> statInfoList = (List<Object[]>) payBillInfo[0];
        List<Object[]> statInfo2List = (List<Object[]>) payBill2Info[0];
        fillData2(statInfoList, statInfo2List, columnStr, "二维码不平账查询", "二维码不平账查询", 7, null);
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
