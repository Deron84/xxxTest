package com.huateng.struts.epos.action;

import com.huateng.bo.impl.epos.TblEposService;
import com.huateng.po.epos.TblMenuMsg;
import com.huateng.po.epos.TblMenuMsgPK;
import com.huateng.struts.epos.EposConstants;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;

public class T10805Action extends BaseSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1723338675358791684L;
	private TblEposService tblEposService;
	String brhIdNew;
	String verIdNew;
	String menuLevelNew;
	String menuMsgNew;
	String txnCodeNew;
	String menuPreId1New;
	String menuPreId2New;
	String pptIdNew;
	String conFlagNew;
	String menuIdNew;

	String usageKey;
	String brhId;
	String verId;
	String menuLevel;
	String menuPreId1;
	String menuPreId2;
	String menuMsg;
	String txnCode;
	String pptId;
	String conFlag;
	String menuId;
	
	String oprIdUp;
	
	public void setTblEposService(TblEposService tblEposService) {
		this.tblEposService = tblEposService;
	}
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
	public String getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel;
	}
	public String getMenuMsg() {
		return menuMsg;
	}
	public void setMenuMsg(String menuMsg) {
		this.menuMsg = menuMsg;
	}
	public String getTxnCode() {
		return txnCode;
	}
	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}
	public String getPptId() {
		return pptId;
	}
	public void setPptId(String pptId) {
		this.pptId = pptId;
	}
	public String getConFlag() {
		return conFlag;
	}
	public void setConFlag(String conFlag) {
		this.conFlag = conFlag;
	}
	
	public String getBrhIdNew() {
		return brhIdNew;
	}
	public void setBrhIdNew(String brhIdNew) {
		this.brhIdNew = brhIdNew;
	}
	public String getVerIdNew() {
		return verIdNew;
	}
	public void setVerIdNew(String verIdNew) {
		this.verIdNew = verIdNew;
	}
	public String getMenuLevelNew() {
		return menuLevelNew;
	}
	public void setMenuLevelNew(String menuLevelNew) {
		this.menuLevelNew = menuLevelNew;
	}
	public String getMenuMsgNew() {
		return menuMsgNew;
	}
	public void setMenuMsgNew(String menuMsgNew) {
		this.menuMsgNew = menuMsgNew;
	}
	public String getTxnCodeNew() {
		return txnCodeNew;
	}
	public void setTxnCodeNew(String txnCodeNew) {
		this.txnCodeNew = txnCodeNew;
	}
	public String getPptIdNew() {
		return pptIdNew;
	}
	public void setPptIdNew(String pptIdNew) {
		this.pptIdNew = pptIdNew;
	}
	public String getConFlagNew() {
		return conFlagNew;
	}
	public void setConFlagNew(String conFlagNew) {
		this.conFlagNew = conFlagNew;
	}
	public String getMenuIdNew() {
		return menuIdNew;
	}
	public void setMenuIdNew(String menuIdNew) {
		this.menuIdNew = menuIdNew;
	}
	
	public String getMenuPreId1() {
		return menuPreId1;
	}
	public void setMenuPreId1(String menuPreId1) {
		this.menuPreId1 = menuPreId1;
	}
	public String getMenuPreId2() {
		return menuPreId2;
	}
	public void setMenuPreId2(String menuPreId2) {
		this.menuPreId2 = menuPreId2;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getUsageKey() {
		return usageKey;
	}
	public void setUsageKey(String usageKey) {
		this.usageKey = usageKey;
	}
	
	public String getMenuPreId1New() {
		return menuPreId1New;
	}
	public void setMenuPreId1New(String menuPreId1New) {
		this.menuPreId1New = menuPreId1New;
	}
	public String getMenuPreId2New() {
		return menuPreId2New;
	}
	public void setMenuPreId2New(String menuPreId2New) {
		this.menuPreId2New = menuPreId2New;
	}
	
	
	
	public String getOprIdUp() {
		return oprIdUp;
	}
	public void setOprIdUp(String oprIdUp) {
		this.oprIdUp = oprIdUp;
	}
	public String add(){
		try {
			TblMenuMsg tblMenuMsg = new TblMenuMsg();
			TblMenuMsgPK tblMenuMsgPK = new TblMenuMsgPK();
			tblMenuMsgPK.setBrhId(brhId);
			tblMenuMsgPK.setMenuLevel(menuLevel);
			
			String menuId = tblEposService.getMenuId(brhId,menuLevel,verId,menuPreId1,menuPreId2);
			if(menuId.length()>2)
				return returnService(menuId);
			tblMenuMsgPK.setMenuId(menuId);
			
			if((menuLevel.equals(EposConstants.MENU_LEVEL_2) || menuLevel.equals(EposConstants.MENU_LEVEL_3))
					&& !tblEposService.isPreMenuIdExist(brhIdNew, EposConstants.MENU_LEVEL_1, verIdNew, menuId)){
				return returnService(EposConstants.T30505_03);
			}
			
			if(menuLevel.equals(EposConstants.MENU_LEVEL_3) 
					&& !tblEposService.isPreMenuIdExist(brhIdNew, EposConstants.MENU_LEVEL_2, verIdNew, menuId)){
				return returnService(EposConstants.T30505_04);
			}
			
			tblMenuMsgPK.setMenuPreId1(menuPreId1);
			tblMenuMsgPK.setMenuPreId2(menuPreId2);
			
			tblMenuMsgPK.setUsageKey(EposConstants.DEFAULT_USAGE_KEY);
			
			tblMenuMsgPK.setVerId(verId);

			tblMenuMsg.setId(tblMenuMsgPK);
			
			tblMenuMsg.setMenuMsg(menuMsgNew);
			tblMenuMsg.setOprId(EposConstants.OK);
			tblMenuMsg.setCrtDate(CommonFunction.getCurrentDate());
			tblMenuMsg.setUpdDate(CommonFunction.getCurrentDate());
			if(pptIdNew != null && !pptIdNew.equals(""))
				tblMenuMsg.setPptId(Integer.valueOf(pptIdNew));
			else
				tblMenuMsg.setPptId(Integer.valueOf(0));
			tblMenuMsg.setTxnCode(txnCodeNew);
			tblMenuMsg.setConFlag(isChecked(conFlagNew));
			rspCode = tblEposService.save(tblMenuMsg);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	public String update(){
		try {
			TblMenuMsgPK tblMenuMsgPK = new TblMenuMsgPK();
			tblMenuMsgPK.setBrhId(brhId);
			tblMenuMsgPK.setMenuId(menuId);
			tblMenuMsgPK.setMenuLevel(menuLevel);
			tblMenuMsgPK.setMenuPreId1(menuPreId1);
			tblMenuMsgPK.setMenuPreId2(menuPreId2);
			tblMenuMsgPK.setUsageKey(EposConstants.DEFAULT_USAGE_KEY);
			tblMenuMsgPK.setVerId(verId);
			
			TblMenuMsg tblMenuMsg = tblEposService.get(tblMenuMsgPK);
			
			tblMenuMsg.setConFlag(isChecked(conFlag));
			tblMenuMsg.setMenuMsg(menuMsg);
			if(pptId != null && !pptId.equals(""))
				tblMenuMsg.setPptId(Integer.valueOf(pptId));
			else
				tblMenuMsg.setPptId(Integer.valueOf(0));
			tblMenuMsg.setTxnCode(txnCode);
			tblMenuMsg.setUpdDate(CommonFunction.getCurrentDate());
			tblMenuMsg.setOprId(oprIdUp);
			
			rspCode = tblEposService.update(tblMenuMsg);
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	public String del(){
		try {
			TblMenuMsgPK tblMenuMsgPK = new TblMenuMsgPK();
			tblMenuMsgPK.setBrhId(brhId);
			tblMenuMsgPK.setMenuId(menuId);
			tblMenuMsgPK.setMenuLevel(menuLevel);
			tblMenuMsgPK.setMenuPreId1(menuPreId1);
			tblMenuMsgPK.setMenuPreId2(menuPreId2);
			tblMenuMsgPK.setUsageKey(usageKey);
			tblMenuMsgPK.setVerId(verId);
			TblMenuMsg tblMenuMsg = tblEposService.get(tblMenuMsgPK);
			tblMenuMsg.setOprId(EposConstants.DISABLE);
			rspCode = tblEposService.update(tblMenuMsg);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	public String isChecked(String param)
	{
		if(param == null || param.trim().equals(""))
			return "0";
		else
			return "1";
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
