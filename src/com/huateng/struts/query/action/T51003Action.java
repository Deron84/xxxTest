package com.huateng.struts.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.grid.GridConfigMethod;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;

public class T51003Action extends BaseExcelQueryAction {
    @Override
    protected void deal() {
        // TODO Auto-generated method stub  
        HttpServletRequest request = ServletActionContext.getRequest();
        GridConfigMethod gridCfg = new GridConfigMethod();
        String midStr2 = ServletActionContext.getContext().getApplication().get("recordNum").toString();
        Constants.QUERY_RECORD_COUNT = Integer.parseInt(midStr2);
        Object[] statRepInfo = gridCfg.getStatRepInfo(0, request);
        Constants.QUERY_RECORD_COUNT = 15;
        Object[] statRepInfo2 = gridCfg.getStatRepInfo2(0, request);
        List<Object[]> statInfoList = (List<Object[]>) statRepInfo[0];
        List<Object[]> statInfo2List = (List<Object[]>) statRepInfo2[0];
        String[] columnStr = { "机构号", "机构名称", "商户号", "商户名称", "交易量", "交易金额", "原始金额", "优惠金额", "清算账户", "联行号", "开户行", "户名" };
        fillData2(statInfoList, statInfo2List, columnStr, "交易统计", "交易统计", 12, null);
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
