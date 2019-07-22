package com.huateng.struts.base.action;

import com.huateng.bo.base.T10601BO;
import com.huateng.po.base.TblIPosInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;

public class T10601Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private T10601BO t10601BO = (T10601BO) ContextUtil.getBean("T10601BO");

	@Override
	protected String subExecute(){
		try {
			if("add".equals(getMethod())) {				//新增
				rspCode = add();			
			} else if("delete".equals(getMethod())) {	//删除，直接从数据库删除这条记录，只用到卡号
				rspCode = delete();
			} else if("update".equals(getMethod())) {	//修改，前台传来JOSN类型的字符串，在BO中处理
				rspCode = update();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对财务POS账户的维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}

	private String add() throws Exception {
		
		TblIPosInf tblIPosInf = new TblIPosInf();
		tblIPosInf.setFeeCode(feeCode);
		tblIPosInf.setMchNumber(mchNumber);
		tblIPosInf.setPosStage(posStage);
		tblIPosInf.setOutmchNumber(outmchNumber);
		tblIPosInf.setInmchNumber(inmchNumber);
		tblIPosInf.setId(posMch);
		return t10601BO.add(tblIPosInf);
	}

	private String delete() throws Exception {
		
		return t10601BO.delete(posMch);
	}

	private String update() throws Exception {
		
		
		return t10601BO.update(infoList);
	}
	
	private String posMch;
	private String mchNumber;
	private String outmchNumber;
	private String inmchNumber;
	private String feeCode;
	private String posStage;

	private String infoList;

	public void setInfoList(String infoList) {
		this.infoList = infoList;
	}

	public void setPosMch(String posMch) {
		this.posMch = posMch;
	}

	public void setMchNumber(String mchNumber) {
		this.mchNumber = mchNumber;
	}

	public void setOutmchNumber(String outmchNumber) {
		this.outmchNumber = outmchNumber;
	}
	
	public void setInmchNumber(String inmchNumber) {
		this.inmchNumber = inmchNumber;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public String getPosStage() {
		return posStage;
	}

	public void setPosStage(String posStage) {
		this.posStage = posStage;
	}

	public String getMchNumber() {
		return mchNumber;
	}
}
