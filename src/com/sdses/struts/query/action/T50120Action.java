package com.sdses.struts.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.grid.GridConfigMethod;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;

/**
 * 达标查询统计
 * 
 * @author zhangkai
 *
 */
public class T50120Action extends BaseExcelQueryAction {

    @Override
    protected void deal() {
        // TODO Auto-generated method stub  
        HttpServletRequest request = ServletActionContext.getRequest();
        GridConfigMethod gridCfg = new GridConfigMethod();
        Object[] vegeDetailInfo = new Object[2];
        Object[] vegeDetail2Info = new Object[2];
        String[] columnStr = new String[13];
        String midStr2 = ServletActionContext.getContext().getApplication().get("recordNum").toString();
        Constants.QUERY_RECORD_COUNT = Integer.parseInt(midStr2);
        try {
            vegeDetailInfo = gridCfg.getStdCountInfo(0, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Constants.QUERY_RECORD_COUNT = 15;
        columnStr[0] = "商户名称";
        columnStr[1] = "商户号";
        columnStr[2] = "终端号";
        columnStr[3] = "交易总笔数";
        columnStr[4] = "交易总金额（元）";
        columnStr[5] = "原交易总金额（元）";
        columnStr[6] = "银行卡总个数";
        columnStr[7] = "是否达标";
        columnStr[8] = "起始日期";
        columnStr[9] = "终止日期";
        columnStr[10] = "商户地址";
        columnStr[11] = "归属机构";
        List<Object[]> statInfoList = (List<Object[]>) vegeDetailInfo[0];
        List<Object[]> statInfo2List = (List<Object[]>) vegeDetail2Info[0];
        fillData2(statInfoList, statInfo2List, columnStr, "商户达标信息查询", "商户达标信息查询", 12, null);
    }

    @Override
    protected String getFileKey() {
        return ExcelName.EN_50120;
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