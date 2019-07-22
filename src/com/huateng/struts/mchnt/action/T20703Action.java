package com.huateng.struts.mchnt.action;

import org.springframework.beans.BeanUtils;

import com.huateng.bo.mchnt.T20703BO;
import com.huateng.po.TblDivMchnt;
import com.huateng.po.TblDivMchntPK;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * project JSBConsole date 2013-3-10
 * 
 * @author 樊东东
 */
public class T20703Action extends BaseSupport {

	private T20703BO t20703BO = (T20703BO) ContextUtil.getBean("T20703BO");

	public String add() {
		try {
			
			String oprId = getOperator().getOprId();
			String nowTime = CommonFunction.getCurrentDateTime();
			TblDivMchnt existObj = t20703BO.get(divMcht.getId());
			if (existObj != null) {
				return returnService("已存在相同商户号、分期期数、产品代码的记录，请重新输入");
			}
			divMcht.setInProductCode("-");
			
			divMcht.setInitOprId(oprId);
			divMcht.setModiOprId(oprId);
			divMcht.setInitTime(nowTime);
			divMcht.setModiTime(nowTime);
			rspCode = t20703BO.save(divMcht);

			return returnService(rspCode);

		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}

	public String delete() {
		try {
			TblDivMchntPK id = new TblDivMchntPK(mchtId, divNo, productCode);
			TblDivMchnt existObj = t20703BO.get(id);
			if (existObj == null) {
				return returnService("没有找到指定商户号、分期期数、产品代码的记录，请重新选择");
			}
			
			rspCode = t20703BO.delete(existObj);

			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}

	}
	
	public String update(){
		try{
			TblDivMchnt existObj = t20703BO.get(divMcht.getId());
			if (existObj == null) {
				return returnService("没有找到指定商户号、分期期数、产品代码的记录，请重新选择");
			}
			existObj.setFeeCode(divMcht.getFeeCode());
			existObj.setProductDivName(divMcht.getProductDivName());
			existObj.setModiOprId(this.getOperator().getOprId());
			existObj.setModiTime(CommonFunction.getCurrentDateTime());
			rspCode = t20703BO.update(existObj);

			return returnService(rspCode);
		}catch(Exception e){
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.system.action.BaseSupport#getMsg()
	 */
	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return msg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.system.action.BaseSupport#isSuccess()
	 */
	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return success;
	}

	private TblDivMchnt divMcht;
	
	private java.lang.String mchtId;
	private java.lang.String divNo;
	private String productCode;
	

	
	public java.lang.String getMchtId() {
		return mchtId;
	}

	
	public void setMchtId(java.lang.String mchtId) {
		this.mchtId = mchtId;
	}

	
	public java.lang.String getDivNo() {
		return divNo;
	}

	
	public void setDivNo(java.lang.String divNo) {
		this.divNo = divNo;
	}

	
	public String getProductCode() {
		return productCode;
	}

	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public TblDivMchnt getDivMcht() {
		return divMcht;
	}

	public void setDivMcht(TblDivMchnt divMcht) {
		this.divMcht = divMcht;
	}

}
