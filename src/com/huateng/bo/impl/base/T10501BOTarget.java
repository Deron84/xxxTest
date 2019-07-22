package com.huateng.bo.impl.base;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huateng.bo.base.T10501BO;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.dao.iface.base.TblPosCardInfDAO;
import com.huateng.po.base.TblPosCardInf;

public class T10501BOTarget implements T10501BO {

	private TblPosCardInfDAO tblPosCardInfDAO;

	public void setTblPosCardInfDAO(TblPosCardInfDAO tblPosCardInfDAO) {
		this.tblPosCardInfDAO = tblPosCardInfDAO;
	}

	public String add(TblPosCardInf tblPosCardInf) {
		
		if(tblPosCardInfDAO.get(tblPosCardInf.getId()) != null) {
			
			return ErrorCode.T10501_01;
		}
		
		if(tblPosCardInf.getStartTime().equals("请选择")) tblPosCardInf.setStartTime(" ");
		if(tblPosCardInf.getStopTime().equals("请选择")) tblPosCardInf.setStopTime(" ");
		
		tblPosCardInfDAO.save(tblPosCardInf);
		return Constants.SUCCESS_CODE;
	}

	public String update(TblPosCardInf obj) {
		
		
		return Constants.SUCCESS_CODE;
	}

	public String delete(String id) {
		
		if(tblPosCardInfDAO.get(id) == null) {
			
			return ErrorCode.T10501_01;
		}
		
		tblPosCardInfDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public String update(String infoList) {

		JSONArray array = JSONArray.fromObject(infoList);
		for(int i=0; i<array.size(); i++) {
			
			JSONObject object = (JSONObject) array.get(i);
			String cardId = object.getString("cardId");
			TblPosCardInf tblPosCardInf = tblPosCardInfDAO.get(cardId);
			if(tblPosCardInf != null) {
				
				tblPosCardInf.setHolderName(object.getString("holderName"));
				tblPosCardInf.setHolderId(object.getString("holderId"));
				tblPosCardInf.setHolderTel(object.getString("holderTel"));
				tblPosCardInf.setSmsQuota(object.getString("smsQuota"));
				tblPosCardInf.setStartTime(object.getString("startTime"));
				tblPosCardInf.setStopTime(object.getString("stopTime"));
				
				tblPosCardInfDAO.update(tblPosCardInf);
			}
		}
		return Constants.SUCCESS_CODE;
	}
	
	public String addBatch(List<TblPosCardInf> list) {
		
		for(TblPosCardInf tblPosCardInf : list) {
		
			if(tblPosCardInfDAO.get(tblPosCardInf.getId()) == null) {
				
				if(tblPosCardInf.getStartTime().equals("请选择")) tblPosCardInf.setStartTime(" ");
				if(tblPosCardInf.getStopTime().equals("请选择")) tblPosCardInf.setStopTime(" ");
				
				tblPosCardInfDAO.save(tblPosCardInf);
			}
		}
		
		return Constants.SUCCESS_CODE;
	}
}
