package com.huateng.struts.epos.action;

import com.huateng.bo.impl.epos.TblEposService;
import com.huateng.po.epos.TblPptMsg;
import com.huateng.po.epos.TblPptMsgPK;
import com.huateng.struts.epos.EposConstants;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;

public class T10803Action extends BaseSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1103162858783806615L;
	
	private TblEposService tblEposService;
	String pptIdNew;
	String msgTypeNew;
	String verIdNew;
	String tmpIdNew;
	String msgFmt1New;
	String pptMsg1New;
	String msgFmt2New;
  	String pptMsg2New;
	String msgFmt3New;
	String pptMsg3New;
	
	String pptId;
	String verId;
	String msgType;
	String usageKey;
	String tmpId;
	String msgFmt1;
	String pptMsg1;
	String msgFmt2; 
	String pptMsg2; 
	String msgFmt3; 
	String pptMsg3; 
	
	
	public String getTmpId() {
		return tmpId;
	}
	public void setTmpId(String tmpId) {
		this.tmpId = tmpId;
	}
	public String getMsgFmt1() {
		return msgFmt1;
	}
	public void setMsgFmt1(String msgFmt1) {
		this.msgFmt1 = msgFmt1;
	}
	public String getPptMsg1() {
		return pptMsg1;
	}
	public void setPptMsg1(String pptMsg1) {
		this.pptMsg1 = pptMsg1;
	}
	public String getMsgFmt2() {
		return msgFmt2;
	}
	public void setMsgFmt2(String msgFmt2) {
		this.msgFmt2 = msgFmt2;
	}
	public String getPptMsg2() {
		return pptMsg2;
	}
	public void setPptMsg2(String pptMsg2) {
		this.pptMsg2 = pptMsg2;
	}
	public String getMsgFmt3() {
		return msgFmt3;
	}
	public void setMsgFmt3(String msgFmt3) {
		this.msgFmt3 = msgFmt3;
	}
	public String getPptMsg3() {
		return pptMsg3;
	}
	public void setPptMsg3(String pptMsg3) {
		this.pptMsg3 = pptMsg3;
	}
	public String getPptId() {
		return pptId;
	}
	public void setPptId(String pptId) {
		this.pptId = pptId;
	}
	public String getVerId() {
		return verId;
	}
	public void setVerId(String verId) {
		this.verId = verId;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getUsageKey() {
		return usageKey;
	}
	public void setUsageKey(String usageKey) {
		this.usageKey = usageKey;
	}
	public String getPptIdNew() {
		return pptIdNew;
	}
	public void setPptIdNew(String pptIdNew) {
		this.pptIdNew = pptIdNew;
	}
	public String getMsgTypeNew() {
		return msgTypeNew;
	}
	public void setMsgTypeNew(String msgTypeNew) {
		this.msgTypeNew = msgTypeNew;
	}
	public String getVerIdNew() {
		return verIdNew;
	}
	public void setVerIdNew(String verIdNew) {
		this.verIdNew = verIdNew;
	}
	public String getTmpIdNew() {
		return tmpIdNew;
	}
	public void setTmpIdNew(String tmpIdNew) {
		this.tmpIdNew = tmpIdNew;
	}
	public String getMsgFmt1New() {
		return msgFmt1New;
	}
	public void setMsgFmt1New(String msgFmt1New) {
		this.msgFmt1New = msgFmt1New;
	}
	public String getPptMsg1New() {
		return pptMsg1New;
	}
	public void setPptMsg1New(String pptMsg1New) {
		this.pptMsg1New = pptMsg1New;
	}
	public String getMsgFmt2New() {
		return msgFmt2New;
	}
	public void setMsgFmt2New(String msgFmt2New) {
		this.msgFmt2New = msgFmt2New;
	}
	public String getPptMsg2New() {
		return pptMsg2New;
	}
	public void setPptMsg2New(String pptMsg2New) {
		this.pptMsg2New = pptMsg2New;
	}
	public String getMsgFmt3New() {
		return msgFmt3New;
	}
	public void setMsgFmt3New(String msgFmt3New) {
		this.msgFmt3New = msgFmt3New;
	}
	public String getPptMsg3New() {
		return pptMsg3New;
	}
	public void setPptMsg3New(String pptMsg3New) {
		this.pptMsg3New = pptMsg3New;
	}
	public void setTblEposService(TblEposService tblEposService) {
		this.tblEposService = tblEposService;
	}
	
	public String add(){
		try {
			TblPptMsg tblPptMsg = new TblPptMsg();
			TblPptMsgPK tblPptMsgPK = new TblPptMsgPK();
			tblPptMsgPK.setMsgType(msgTypeNew);
			tblPptMsgPK.setPptId(Integer.valueOf(pptIdNew));
			tblPptMsgPK.setUsageKey(EposConstants.DEFAULT_USAGE_KEY);
			tblPptMsgPK.setVerId(verIdNew);
			if(!tblEposService.isExist(tblPptMsgPK))
				return returnService(EposConstants.T30503_01);
			tblPptMsg.setId(tblPptMsgPK);
			tblPptMsg.setTmpId(tblEposService.getTmpId(Integer.valueOf(pptIdNew), msgTypeNew, EposConstants.DEFAULT_USAGE_KEY, verIdNew));
			tblPptMsg.setMsgFmt1(msgFmt1New);
			tblPptMsg.setPptMsg1(pptMsg1New);
			tblPptMsg.setMsgFmt2(msgFmt2New);
			tblPptMsg.setPptMsg2(pptMsg2New);
			tblPptMsg.setMsgFmt3(msgFmt3New);
			tblPptMsg.setPptMsg3(pptMsg3New);
			tblPptMsg.setCrtDate(CommonFunction.getCurrentDate());
			tblPptMsg.setUpdDate(CommonFunction.getCurrentDate());
			rspCode = tblEposService.save(tblPptMsg);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	public String update(){
		try {
			TblPptMsg tblPptMsg = new TblPptMsg();
			TblPptMsgPK tblPptMsgPK = new TblPptMsgPK();
			tblPptMsgPK.setMsgType(msgType);
			tblPptMsgPK.setPptId(Integer.valueOf(pptId));
			tblPptMsgPK.setUsageKey(EposConstants.DEFAULT_USAGE_KEY);
			tblPptMsgPK.setVerId(verId);
			tblPptMsg.setId(tblPptMsgPK);
			tblPptMsg.setTmpId(Integer.valueOf(tmpId));
			tblPptMsg.setMsgFmt1(msgFmt1);
			tblPptMsg.setPptMsg1(pptMsg1);
			tblPptMsg.setMsgFmt2(msgFmt2);
			tblPptMsg.setPptMsg2(pptMsg2);
			tblPptMsg.setMsgFmt3(msgFmt3);
			tblPptMsg.setPptMsg3(pptMsg3);
			tblPptMsg.setCrtDate(CommonFunction.getCurrentDate());
			tblPptMsg.setUpdDate(CommonFunction.getCurrentDate());
			rspCode = tblEposService.update(tblPptMsg);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	public String del(){
		try {
			TblPptMsgPK tblPptMsgPK = new TblPptMsgPK();
			tblPptMsgPK.setMsgType(msgType);
			tblPptMsgPK.setPptId(Integer.valueOf(pptId));
			tblPptMsgPK.setUsageKey(EposConstants.DEFAULT_USAGE_KEY);
			tblPptMsgPK.setVerId(verId);
			rspCode = tblEposService.delete(tblPptMsgPK);
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
