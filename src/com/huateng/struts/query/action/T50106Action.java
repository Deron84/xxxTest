package com.huateng.struts.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.grid.GridConfigMethod;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;

public class T50106Action extends BaseExcelQueryAction {
    // 支付方式
    private String payType;

    @Override
    protected void deal() {
        // TODO Auto-generated method stub  
        HttpServletRequest request = ServletActionContext.getRequest();
        GridConfigMethod gridCfg = new GridConfigMethod();
        Object[] vegeDetailInfo = new Object[2];
        Object[] vegeDetail2Info = new Object[2];
        String[] columnStr = new String[15];
        String midStr2 = ServletActionContext.getContext().getApplication().get("recordNum").toString();
        Constants.QUERY_RECORD_COUNT = Integer.parseInt(midStr2);
        vegeDetailInfo = gridCfg.getVegDetailInfo(0, request);
        Constants.QUERY_RECORD_COUNT = 15;
        columnStr[0] = "交易时间";
        columnStr[1] = "终端流水号";
        columnStr[2] = "商户名称";
        columnStr[3] = "商户号";
        columnStr[4] = "终端号";
        columnStr[5] = "菜品编码";
        columnStr[6] = "菜品名称";
        columnStr[7] = "重量（KG）";
        columnStr[8] = "价格（元）";
        columnStr[9] = "单价(元/KG)";
        columnStr[10] = "支付方式";
        columnStr[11] = "机构号";
        columnStr[12] = "归属机构";
        columnStr[13] = "是否重复上传";
        columnStr[14] = "上传时间";
        List<Object[]> statInfoList = (List<Object[]>) vegeDetailInfo[0];
        List<Object[]> statInfo2List = (List<Object[]>) vegeDetail2Info[0];
        fillData2(statInfoList, statInfo2List, columnStr, "菜品明细查询", "菜品明细查询", 15, null);
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
