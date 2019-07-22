package com.huateng.struts.base.action;

import com.huateng.bo.base.T10601BO;
import com.huateng.bo.base.T10701BO;
import com.huateng.po.TblDivMchnt;
import com.huateng.po.TblDivMchntPK;
import com.huateng.po.base.TblIPosInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;

public class T10701Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private T10701BO t10701BO = (T10701BO) ContextUtil.getBean("T10701BO");

	@Override
	protected String subExecute(){
		try {
			if("add".equals(getMethod())) {				//新增
				rspCode = add();			
			} else if("delete".equals(getMethod())) {	//删除，直接从数据库删除这条记录，只用到卡号
				rspCode = delete();
			} 
//			else if("update".equals(getMethod())) {	//修改，前台传来JOSN类型的字符串，在BO中处理
//				rspCode = update();
//			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对财务POS账户的维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}

	private String add() throws Exception {
		
		TblDivMchnt divInf = new TblDivMchnt();
		TblDivMchntPK id = new TblDivMchntPK();
		id.setMchtId(mchtId);
		id.setDivNo(divNo);
		id.setProductCode(productCode);
		divInf.setId(id);
		divInf.setInProductCode(inproductCode);
		divInf.setFeeCode(feeCode);
		return t10701BO.add(divInf);
	}

	private String delete() throws Exception {
		TblDivMchntPK id = new TblDivMchntPK();
		id.setMchtId(mchtId);
		id.setDivNo(divNo);
		id.setProductCode(productCode);
		return t10701BO.delete(id);
	}

//	private String update() throws Exception {
//		
//		
//		return t10601BO.update(infoList);
//	}
//	
	private String mchtId;
	private String divNo;
	private String productCode;
	private String inproductCode;
	private String feeCode;

	private String infoList;

	public String getMchtId() {
		return mchtId;
	}

	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}

	public String getDivNo() {
		return divNo;
	}

	public void setDivNo(String divNo) {
		this.divNo = divNo;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getInproductCode() {
		return inproductCode;
	}

	public void setInproductCode(String inproductCode) {
		this.inproductCode = inproductCode;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public String getInfoList() {
		return infoList;
	}

	public void setInfoList(String infoList) {
		this.infoList = infoList;
	}

	public T10701BO getT10701BO() {
		return t10701BO;
	}

	public void setT10701BO(T10701BO t10701bo) {
		t10701BO = t10701bo;
	}
}
