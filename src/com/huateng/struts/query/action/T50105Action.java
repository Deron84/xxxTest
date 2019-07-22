package com.huateng.struts.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.grid.GridConfigMethod;
import com.huateng.struts.base.action.T10101Action;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;

public class T50105Action extends BaseExcelQueryAction {
    private static final long serialVersionUID = 1L;


    @Override
    protected void deal() {
        // TODO Auto-generated method stub  
        HttpServletRequest request = ServletActionContext.getRequest();
        //    GridConfigMethod gridCfg = new GridConfigMethod();
        Object[] queryRepInfo = new Object[2];
        Object[] queryRep2Info = new Object[2];
        String[] columnStr = new String[22];
        String payType = request.getParameter("payType");
        String midStr2 = ServletActionContext.getContext().getApplication().get("recordNum").toString();
        Constants.QUERY_RECORD_COUNT = Integer.parseInt(midStr2);
        if (payType.equals("unionPay")) {
            queryRepInfo = GridConfigMethod.getTxnInfoTxn(0, request);
            Constants.QUERY_RECORD_COUNT = 15;
            columnStr[0] = "交易时间";
            columnStr[1] = "银联商户号";
            columnStr[2] = "银联终端号";
            columnStr[3] = "流水号";
            columnStr[4] = "原始金额（元）";
            columnStr[5] = "交易金额（元）";
            columnStr[6] = "手续费（元）";
            columnStr[7] = "银联卡号";
            columnStr[8] = "交易类型";
            columnStr[9] = "冲正标识";
            columnStr[10] = "应答码";
            columnStr[11] = "交易状态";
            columnStr[12] = "商户补贴金额";
            columnStr[13] = "商户补贴规则";
            columnStr[14] = "签约网点";
            columnStr[15] = "商户名称";
            columnStr[16] = "归属机构";
            columnStr[17] = "网点名称";
            columnStr[18] = "支付宝卖家ID";
    		columnStr[19] = "支付宝买家ID";
    		columnStr[20] = "支付宝卖家账号";
    		columnStr[21] = "支付宝买家账号";
            @SuppressWarnings("unchecked")
            List<Object[]> statInfoList = (List<Object[]>) queryRepInfo[0];
            @SuppressWarnings("unchecked")
            List<Object[]> statInfo2List = (List<Object[]>) queryRep2Info[0];
            T10101Action t10101Action = new T10101Action();
            for (Object[] objArr : statInfoList) {
                if (objArr[16] != null && !objArr[16].equals("")) {
                    String brhId = objArr[16].toString();
                    objArr[16] = brhId + "-" + t10101Action.getT10101BO().get(brhId).getBrhName();
                } else {
                    objArr[16] = "";
                }
            }
            fillData2(statInfoList, statInfo2List, columnStr, "交易查询", "交易查询", 17, null);
        } else if (payType.equals("weChat") || payType.equals("aliPay") || payType.equals("scan") || payType.equals("qqPay")) {
            queryRepInfo = GridConfigMethod.getTxnInfoAir(0, request);
            Constants.QUERY_RECORD_COUNT = 15;
            columnStr[0] = "交易时间";
            columnStr[1] = "商户名称";
            columnStr[2] = "归属机构";
            columnStr[3] = "银联商户号";
            columnStr[4] = "银联终端号";
            columnStr[5] = "终端流水号";
            columnStr[6] = "商户订单号";
            columnStr[7] = "原始金额（元）";
            columnStr[8] = "交易金额（元）";
            columnStr[9] = "手续费（元）";
            columnStr[10] = "支付方式";
            columnStr[11] = "交易状态";
            columnStr[12] = "扫码支付商户号";
            columnStr[13] = "第三方订单号";
            columnStr[14] = "第三方机构";
            columnStr[15] = "异步通知时间";
            columnStr[16] = "签约网点";
            columnStr[17] = "网点名称";
            columnStr[18] = "支付宝卖家ID";
    		columnStr[19] = "支付宝买家ID";
    		columnStr[20] = "支付宝卖家账号";
    		columnStr[21] = "支付宝买家账号";
            @SuppressWarnings("unchecked")
            List<Object[]> statInfoList = (List<Object[]>) queryRepInfo[0];
            @SuppressWarnings("unchecked")
            List<Object[]> statInfo2List = (List<Object[]>) queryRep2Info[0];
            T10101Action t10101Action = new T10101Action();
            for (Object[] objArr : statInfoList) {
                if (objArr[2] != null && !objArr[2].equals("")) {
                    String brhId = objArr[2].toString();
                    objArr[2] = brhId + "-" + t10101Action.getT10101BO().get(brhId).getBrhName();
                } else {
                    objArr[2] = "";
                }
            }
            fillData2(statInfoList, statInfo2List, columnStr, "扫码支付交易查询", "扫码支付交易查询", 21, null);
        } else {
            //            queryRepInfo = gridCfg.getTxnInfoAll(0, request);
            //            Constants.QUERY_RECORD_COUNT = 15;
            //            columnStr[0] = "交易时间";
            //            columnStr[1] = "银联商户号";
            //            columnStr[2] = "银联终端号";
            //            columnStr[3] = "卡号";
            //            columnStr[4] = "商户订单号";
            //            columnStr[5] = "流水号";
            //            columnStr[6] = "交易金额";
            //            columnStr[7] = "支付方式";
            //            columnStr[8] = "签约网点";
            //            columnStr[9] = "商户名称";
            //            columnStr[10] = "网点名称";
            //            List<Object[]> statInfoList = (List<Object[]>) queryRepInfo[0];
            //            List<Object[]> statInfo2List = (List<Object[]>) queryRep2Info[0];
            //            fillData2(statInfoList, statInfo2List, columnStr, "交易查询", "交易查询", 11, null);
            //      @SuppressWarnings("unchecked")
            queryRepInfo = GridConfigMethod.getTxnInfoAir(0, request);
            Constants.QUERY_RECORD_COUNT = 15;
            columnStr[0] = "交易时间";
            columnStr[1] = "商户名称";
            columnStr[2] = "归属机构";
            columnStr[3] = "银联商户号";
            columnStr[4] = "银联终端号";
            columnStr[5] = "终端流水号";
            columnStr[6] = "商户订单号";
            columnStr[7] = "原始金额（元）";
            columnStr[8] = "交易金额（元）";
            columnStr[9] = "手续费（元）";
            columnStr[10] = "支付方式";
            columnStr[11] = "交易状态";
            columnStr[12] = "扫码支付商户号";
            columnStr[13] = "第三方订单号";
            columnStr[14] = "第三方机构";
            columnStr[15] = "异步通知时间";
            columnStr[16] = "签约网点";
            columnStr[17] = "网点名称";
            columnStr[18] = "支付宝卖家ID";
    		columnStr[19] = "支付宝买家ID";
    		columnStr[20] = "支付宝卖家账号";
    		columnStr[21] = "支付宝买家账号";
            @SuppressWarnings("unchecked")
            List<Object[]> statInfoList = (List<Object[]>) queryRepInfo[0];
            @SuppressWarnings("unchecked")
            List<Object[]> statInfo2List = (List<Object[]>) queryRep2Info[0];
            T10101Action t10101Action = new T10101Action();
            for (Object[] objArr : statInfoList) {
                if (objArr[2] != null && !objArr[2].equals("")) {
                    String brhId = objArr[2].toString();
                    objArr[2] = brhId + "-" + t10101Action.getT10101BO().get(brhId).getBrhName();
                } else {
                    objArr[2] = "";
                }
            }
            fillData2(statInfoList, statInfo2List, columnStr, "扫码支付交易查询", "扫码支付交易查询", 22, null);
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
}
