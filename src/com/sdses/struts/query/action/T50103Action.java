package com.sdses.struts.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.grid.GridConfigMethod;
import com.huateng.struts.base.action.T10101Action;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;

public class T50103Action extends BaseExcelQueryAction {

    @Override
    protected void deal() {
        // TODO Auto-generated method stub  
        HttpServletRequest request = ServletActionContext.getRequest();
        GridConfigMethod gridCfg = new GridConfigMethod();
        Object[] queryRepInfo = new Object[2];
        Object[] queryRep2Info = new Object[2];
        String[] columnStr = new String[20];
        String midStr2 = ServletActionContext.getContext().getApplication().get("recordNum").toString();
        Constants.QUERY_RECORD_COUNT = Integer.parseInt(midStr2);
        try {
            queryRepInfo = gridCfg.getPosTxnInfoHis(0, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Constants.QUERY_RECORD_COUNT = 15;
        columnStr[0] = "交易时间";
        columnStr[1] = "系统流水";
        columnStr[2] = "卡号";
        columnStr[3] = "商户号";
        columnStr[4] = "商户名称";
        columnStr[5] = "归属机构";
        columnStr[6] = "终端号";
        columnStr[7] = "检索参考号";
        columnStr[8] = "交易金额(元)";
        columnStr[9] = "代理机构标识码";
        columnStr[10] = "应答码";
        columnStr[11] = "原始金额(元)";
        columnStr[12] = "POS流水号";
        columnStr[13] = "受卡方交易日期";
        columnStr[14] = "受卡方交易时间";
        columnStr[15] = "交易类型";
        columnStr[16] = "商户补贴金额";
        columnStr[17] = "商户补贴金额";
        columnStr[18] = "营销代码";
        columnStr[19] = "消费类别";
        T10101Action t10101Action = new T10101Action();
        List<Object[]> statInfoList = (List<Object[]>) queryRepInfo[0];
        for (Object[] objArr : statInfoList) {
            if (objArr[5] != null && !objArr[5].equals("")) {
                String brhId = objArr[5].toString();
                objArr[5] = brhId + "-" + t10101Action.getT10101BO().get(brhId).getBrhName();
            } else {
                objArr[5] = "";
            }
            if (objArr[15] != null && !objArr[15].equals("")) {
                String midStr = objArr[15].toString();
                if (midStr.equals("P")) {
                    objArr[15] = "消费";
                } else if (midStr.equals("PP")) {
                    objArr[15] = "消费(已冲正)";
                } else if (midStr.equals("Y")) {
                    objArr[15] = "预授权";
                } else if (midStr.equals("X")) {
                    objArr[15] = "撤销";
                } else if (midStr.equals("W")) {
                    objArr[15] = "预授权完成";
                } else if (midStr.equals("R")) {
                    objArr[15] = "冲正";
                } else {
                    objArr[15] = "未知";
                }
            } else {
                objArr[15] = "未知";
            }
            if (objArr[19] != null && !objArr[19].equals("")) {
                String midStr = objArr[19].toString();
                if (midStr.equals("022")) {
                    objArr[19] = "联机消费";
                } else if (midStr.equals("036")) {
                    objArr[19] = "脱机消费";
                } else {
                    objArr[19] = "未知";
                }
            } else {
                objArr[19] = "未知";
            }
        }
        List<Object[]> statInfo2List = (List<Object[]>) queryRep2Info[0];
        fillData2(statInfoList, statInfo2List, columnStr, "交易查询（十日前）", "交易查询（十日前）", 20, null);
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
