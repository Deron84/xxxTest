package com.sdses.struts.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.grid.GridConfigMethod;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ExcelUtil;



public class T51002Action extends BaseExcelQueryAction{
    // 支付方式
    private String statType;
    
    @Override
    protected void deal() {
        // TODO Auto-generated method stub  
        HttpServletRequest request = ServletActionContext.getRequest();
        GridConfigMethod gridCfg = new GridConfigMethod();
        Object[] queryRepInfo = new Object[2];
        Object[] queryRep2Info = new Object[2];
        String[] columnStr = new String[20];
        Constants.QUERY_RECORD_COUNT = 10000;
        queryRepInfo = gridCfg.getStatSumInfoDetail(0, request);
        Constants.QUERY_RECORD_COUNT = 15;
        if(statType.equals("M")){
            columnStr[0] = "日期";
            columnStr[1] = "银联商户号";
            columnStr[2] = "交易金额";
            columnStr[3] = "交易量";
            List<Object[]> statInfoList = (List<Object[]>)queryRepInfo[0];
            List<Object[]> statInfo2List = (List<Object[]>)queryRep2Info[0];
            Object [] arr = {3};
            fillData2(statInfoList,statInfo2List,columnStr,"交易统计详情","交易统计详情",4,arr);
        }else if(statType.equals("T")  ){
            columnStr[0] = "日期";
            columnStr[1] = "银联商户号";
            columnStr[2] = "银联终端号";
            columnStr[3] = "交易金额";
            columnStr[4] = "交易量";
            List<Object[]> statInfoList = (List<Object[]>)queryRepInfo[0];
            List<Object[]> statInfo2List = (List<Object[]>)queryRep2Info[0];
            fillData2(statInfoList,statInfo2List,columnStr,"交易统计详情","交易统计详情",5,null);
        }
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

    public String getStatType() {
        return statType;
    }

    public void setStatType(String statType) {
        this.statType = statType;
    }

}
