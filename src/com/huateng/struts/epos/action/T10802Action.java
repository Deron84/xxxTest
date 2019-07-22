package com.huateng.struts.epos.action;

import com.huateng.bo.impl.epos.TblEposService;
import com.huateng.po.epos.TblTermTxn;
import com.huateng.struts.system.action.BaseSupport;

public class T10802Action extends BaseSupport {


	private static final long serialVersionUID = 452104523564010468L;
	private TblEposService tblEposService;
	
	
	String termTxnCode;
	String intTxnCode;
	String dsp;
	
	String termTxnCodeNew;
	String intTxnCodeNew;
	String dspNew;
	
	
	public String getTermTxnCode() {
		return termTxnCode;
	}
	public void setTermTxnCode(String termTxnCode) {
		this.termTxnCode = termTxnCode;
	}
	public String getIntTxnCode() {
		return intTxnCode;
	}
	public void setIntTxnCode(String intTxnCode) {
		this.intTxnCode = intTxnCode;
	}
	public String getDsp() {
		return dsp;
	}
	public void setDsp(String dsp) {
		this.dsp = dsp;
	}
	public String getTermTxnCodeNew() {
		return termTxnCodeNew;
	}
	public void setTermTxnCodeNew(String termTxnCodeNew) {
		this.termTxnCodeNew = termTxnCodeNew;
	}
	public String getIntTxnCodeNew() {
		return intTxnCodeNew;
	}
	public void setIntTxnCodeNew(String intTxnCodeNew) {
		this.intTxnCodeNew = intTxnCodeNew;
	}
	public String getDspNew() {
		return dspNew;
	}
	public void setDspNew(String dspNew) {
		this.dspNew = dspNew;
	}
	public void setTblEposService(TblEposService tblEposService) {
		this.tblEposService = tblEposService;
	}
	public String add(){
		try {
			TblTermTxn tblTermTxn =  new TblTermTxn();
			tblTermTxn.setTermTxnCode(termTxnCodeNew);
			tblTermTxn.setIntTxnCode(intTxnCodeNew);
			tblTermTxn.setDsp(dspNew);
			rspCode = tblEposService.save(tblTermTxn);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	public String update(){
		try {
			TblTermTxn tblTermTxn = tblEposService.getTermTxn(termTxnCode);
			tblTermTxn.setDsp(dsp);
			tblTermTxn.setIntTxnCode(intTxnCode);
			rspCode = tblEposService.update(tblTermTxn);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	public String del(){
		try {
			rspCode = tblEposService.delTermTxn(termTxnCode);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	public String getMsg() {
		return this.msg;
	}
	@Override
	public boolean isSuccess() {
		return success;
	}
}
