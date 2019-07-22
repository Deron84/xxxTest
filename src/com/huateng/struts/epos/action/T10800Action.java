package com.huateng.struts.epos.action;

import com.huateng.bo.impl.epos.TblEposService;
import com.huateng.common.Constants;
import com.huateng.po.epos.TblVerMng;
import com.huateng.po.epos.TblVerMngPK;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;

public class T10800Action extends BaseSupport {

	private static final long serialVersionUID = 3085229538534154345L;
	private TblEposService tblEposService;
	
	String brhId;
	String verId;
	String misc;

    public String getBrhId() {
		return brhId;
	}


	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}


	public String getVerId() {
		return verId;
	}


	public void setVerId(String verId) {
		this.verId = verId;
	}
	
	public String getMisc() {
		return misc;
	}


	public void setMisc(String misc) {
		this.misc = misc;
	}


	public void setTblEposService(TblEposService tblEposService) {
		this.tblEposService = tblEposService;
	}
	
	public String add(){
		try {
			TblVerMng inf = new TblVerMng();
			TblVerMngPK pk = new TblVerMngPK();
			pk.setBankId(brhId);
			pk.setVerId(verId);
			inf.setId(pk);
			inf.setMisc(misc);
			inf.setCrtDate(CommonFunction.getCurrentDate());
			inf.setUpdDate(CommonFunction.getCurrentDate());
			if(1==tblEposService.getVerInfoNew(pk)){
				return returnService(Constants.ERR_EXIST);
			}
			if (null != tblEposService.getVerInfo(pk)) {
				return returnService(Constants.ERR_EXIST);
			}
			rspCode = tblEposService.save(inf);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	public String del(){
		try {
			rspCode = tblEposService.delVerInfo(brhId, verId);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
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
