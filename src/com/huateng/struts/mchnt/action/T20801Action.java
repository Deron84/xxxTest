package com.huateng.struts.mchnt.action;

import com.huateng.bo.mchnt.T20801BO;
import com.huateng.po.mchnt.TblMchtCheckInf;
import com.huateng.po.mchnt.TblMchtCheckInfPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T20801Action extends BaseAction {

	private T20801BO t20801BO = (T20801BO) ContextUtil.getBean("T20801BO");
	
	@Override
	protected String subExecute(){
		try {
//			if("add".equals(getMethod())) {				//新增
//				rspCode = add();			
//			} else if("delete".equals(getMethod())) {	//删除
//				rspCode = delete();
//			} else if("update".equals(getMethod())) {	//修改
//				rspCode = update();
//			}
			if("check".equals(getMethod())){
				rspCode = check();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId()+ "，对商户巡检信息的维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
//	private String add() throws Exception {
//		
//		TblMchtCheckInf obj = new TblMchtCheckInf();
//		TblMchtCheckInfPK pk = new TblMchtCheckInfPK();
//		pk.setMchtNo(mchtNo);
//		pk.setCheckDate(checkDate);
//		obj.setId(pk);
//		obj.setCheckName(checkName);
//		obj.setCheckInf(checkInf);
//		obj.setRecCrtTs(CommonFunction.getCurrentDateTime());
//		obj.setCrtOprId(operator.getOprId());
//		
//		return t20801BO.add(obj);
//	}
//	
//	private String update() throws Exception {
//		
//		return t20801BO.update(infoList);
//	}
//	
//	private String delete() throws Exception {
//		
//		TblMchtCheckInfPK pk = new TblMchtCheckInfPK();
//		pk.setMchtNo(mchtNo);
//		pk.setCheckDate(checkDate);
//		return t20801BO.del(pk);
//	}

	private String check(){
//		return t20801BO.checkMcht(mchtNo);
		return t20801BO.checkMchts(mchtArray);
	}
	
	private String mchtNo;
	private String checkDate;
	private String checkName;
	private String checkInf;
    private String[] mchtArray;
	private String infoList;
	
	public void setInfoList(String infoList) {
		this.infoList = infoList;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}


	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public void setCheckInf(String checkInf) {
		this.checkInf = checkInf;
	}

	public String[] getMchtArray() {
		return mchtArray;
	}

	public void setMchtArray(String[] mchtArray) {
		this.mchtArray = mchtArray;
	}
	
}
