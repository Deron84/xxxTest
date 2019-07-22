package com.huateng.struts.mchnt.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.mchnt.T20601BO;
import com.huateng.common.Constants;
import com.huateng.po.mchnt.TblMchtBranInf;
import com.huateng.po.mchnt.TblMchtBranInfPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:商店分店信息维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-06-16
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20601Action extends BaseAction {
	//商户编号
	private String mchtCd;
	//分店代码
	private String branchCd;
	//分店名称（中文）
	private String branchNm;
	//分店名称（英文）
	private String branchNmEn;
	//分店所在区域
	private String branchArea;
	//分店营业地址
	private String branchAddr;
	//服务等级
	private String branchSvrLvl;
	//分店联系人
	private String branchContMan;
	//分店联系电话
	private String branchTel;
	//分店传真
	private String branchFax;
	//客户经理姓名
	private String custMnger;
	//客户经理手机
	private String custMobile;
	//客户经理电话
	private String custTel;
	//收银员数目
	private String oprNm;
	//签约日期
	private String signDate;
	
	private String mchtBranInfList;
	
	private T20601BO t20601BO = (T20601BO) ContextUtil.getBean("T20601BO");
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
			if("add".equals(method)) {
				rspCode = add();
			}else if("delete".equals(method)) {
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，商店分店信息维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		
		return rspCode;
	}
	
	
	
	/**
	 * 添加商店信息
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String add() throws IllegalAccessException, InvocationTargetException {
		
		TblMchtBranInf tblMchtBranInf = new TblMchtBranInf();
		
		TblMchtBranInfPK tblMchtBranInfPK = new TblMchtBranInfPK();
		
		tblMchtBranInfPK.setMchtCd(CommonFunction.fillString(mchtCd.trim(), ' ', 15, true));
		
		tblMchtBranInfPK.setBranchCd(CommonFunction.fillString(branchCd.trim(), ' ', 11, true));
		
		tblMchtBranInf.setId(tblMchtBranInfPK);
		BeanUtils.copyProperties(tblMchtBranInf, this);	
		
		tblMchtBranInf.setBusRange("-");
		
		tblMchtBranInf.setBranchSta("-");
		
		tblMchtBranInf.setProvCd("-");
		
		tblMchtBranInf.setCityCd("-");
		
		tblMchtBranInf.setRecOprId("I");
		
		tblMchtBranInf.setRecUpdOpr(operator.getOprId());
		
		tblMchtBranInf.setRecCrtTs(CommonFunction.getCurrentDateTime());
		
		tblMchtBranInf.setRecUpdTs(tblMchtBranInf.getRecCrtTs());
			if(t20601BO.getMchtBranchInfo(tblMchtBranInf.getId()) != null) {
				return "添加分店出错，错误信息为：商店分店编号重复";
			}
			t20601BO.addMchtBranchInfo(tblMchtBranInf);
			
			log("添加商店信息成功。操作员编号：" + operator.getOprId());
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除操作员
	 * @return
	 */
	private String delete() {
		t20601BO.delete(new TblMchtBranInfPK(mchtCd,branchCd));
		log("删除商店信息成功。操作员编号：" + operator.getOprId());		
		return Constants.SUCCESS_CODE;
	}
	
	
	/**
	 * 更新商店信息
	 * @return
	 * @throws Exception 
	 */
	private String update() throws Exception {
		jsonBean.parseJSONArrayData(getMchtBranInfList());
		
		int len = jsonBean.getArray().size();
//		
		TblMchtBranInf tblMchtBranInf = null;
//		
		List<TblMchtBranInf> tblMchtBranInfList = new ArrayList<TblMchtBranInf>(len);
		
		for(int i = 0; i < len; i++) {				
			mchtCd = jsonBean.getJSONDataAt(i).getString("mchtCd");
			branchCd = jsonBean.getJSONDataAt(i).getString("branchCd");
			tblMchtBranInf = t20601BO.getMchtBranchInfo(new TblMchtBranInfPK(mchtCd,branchCd));				
			BeanUtils.setObjectWithPropertiesValue(tblMchtBranInf, jsonBean, false);
			tblMchtBranInf.setRecUpdOpr(operator.getOprId());
			tblMchtBranInf.setRecUpdTs(CommonFunction.getCurrentDateTime());	
			tblMchtBranInfList.add(tblMchtBranInf);
		}

		t20601BO.update(tblMchtBranInfList);
		log("更新商店信息成功。操作员编号：" + operator.getOprId());		
		
		return Constants.SUCCESS_CODE;
	}

	public String getMchtCd() {
		return mchtCd;
	}

	public void setMchtCd(String mchtCd) {
		this.mchtCd = mchtCd;
	}

	public String getBranchCd() {
		return branchCd;
	}

	public void setBranchCd(String branchCd) {
		this.branchCd = branchCd;
	}

	public String getBranchNm() {
		return branchNm;
	}

	public void setBranchNm(String branchNm) {
		this.branchNm = branchNm;
	}

	public String getBranchNmEn() {
		return branchNmEn;
	}

	public void setBranchNmEn(String branchNmEn) {
		this.branchNmEn = branchNmEn;
	}

	public String getBranchArea() {
		return branchArea;
	}

	public void setBranchArea(String branchArea) {
		this.branchArea = branchArea;
	}

	public String getBranchAddr() {
		return branchAddr;
	}

	public void setBranchAddr(String branchAddr) {
		this.branchAddr = branchAddr;
	}

	public String getBranchContMan() {
		return branchContMan;
	}

	public void setBranchContMan(String branchContMan) {
		this.branchContMan = branchContMan;
	}

	public String getBranchTel() {
		return branchTel;
	}

	public void setBranchTel(String branchTel) {
		this.branchTel = branchTel;
	}

	public String getBranchFax() {
		return branchFax;
	}

	public void setBranchFax(String branchFax) {
		this.branchFax = branchFax;
	}

	public String getCustMnger() {
		return custMnger;
	}

	public void setCustMnger(String custMnger) {
		this.custMnger = custMnger;
	}

	public String getCustMobile() {
		return custMobile;
	}

	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}

	public String getCustTel() {
		return custTel;
	}

	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}

	public String getOprNm() {
		return oprNm;
	}

	public void setOprNm(String oprNm) {
		this.oprNm = oprNm;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}



	public String getBranchSvrLvl() {
		return branchSvrLvl;
	}



	public void setBranchSvrLvl(String branchSvrLvl) {
		this.branchSvrLvl = branchSvrLvl;
	}



	public String getMchtBranInfList() {
		return mchtBranInfList;
	}



	public void setMchtBranInfList(String mchtBranInfList) {
		this.mchtBranInfList = mchtBranInfList;
	}
	

}
