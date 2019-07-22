package com.huateng.struts.query.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.grid.GridConfigMethod;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;



public class T20105Action extends BaseExcelQueryAction{
    // 支付方式
    private String payType;
    
    @Override
    protected void deal() {
        // TODO Auto-generated method stub  
        HttpServletRequest request = ServletActionContext.getRequest();
        GridConfigMethod gridCfg = new GridConfigMethod();
        Object[] mchntBaseInfoArr = new Object[2];
        Object[] mchntBaseInfoArr2 = new Object[2];
        String[] columnStr = new String[10];
        Constants.QUERY_RECORD_COUNT = 10000;
        mchntBaseInfoArr = gridCfg.getMchntBaseInfoAllTmp(0, request);
        Constants.QUERY_RECORD_COUNT = 15;
        columnStr[0] = "商户ID";
        columnStr[1] = "商户名称";
        columnStr[2] = "一卡通商户号";
        columnStr[3] = "银联商户号";
        columnStr[4] = "营业执照编号";
        columnStr[5] = "注册日期";
        columnStr[6] = "商户状态";
        columnStr[7] = "终端数量";
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        List<Object[]> statInfoList = (List<Object[]>)mchntBaseInfoArr[0];
        int i =0;
        for(Object[] objArr : statInfoList){
            if(objArr[5] != null && !objArr[5].equals("")){
                try{
                    Date tm = sdf.parse(objArr[5].toString());
                    objArr[5] = sdf2.format(tm);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(objArr[6] != null && !objArr[6].equals("")){
                String midStr = objArr[6].toString();
                if(midStr.equals("0")) {
                    objArr[6]="正常";
                } else if(midStr.equals("1")) {
                    objArr[6]="添加待审核";
                } else if(midStr.equals("2")) {
                    objArr[6]="添加审核退回";
                } else if(midStr.equals("3")) {
                    objArr[6]="修改待审核";
                } else if(midStr.equals("4")) {
                    objArr[6]="修改审核退回";
                } else if(midStr.equals("5")) {
                    objArr[6]="冻结待审核";
                } else if(midStr.equals("6")) {//把停用改为冻结、添加注销状态
                    objArr[6]="冻结";
                } else if(midStr.equals("7")) {
                    objArr[6]="解冻恢复待审核";
                } else if(midStr.equals("8")){
                    objArr[6]="注销待审核";
                }else if(midStr.equals("9")){
                    objArr[6]="注销";
                }else if(midStr.equals("R")){
                    objArr[6]="注销恢复审核";
                }else{
                    objArr[6]="未知";
                }
            }
            statInfoList.set(i++, objArr);
        }
        List<Object[]> statInfo2List = (List<Object[]>)mchntBaseInfoArr2[0];
        fillData2(statInfoList,statInfo2List,columnStr,"商户间联信息","商户间联信息",8,null);
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
