package com.huateng.bo.impl.base;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huateng.bo.base.T10502BO;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.dao.iface.base.TblComCardInfDAO;
import com.huateng.po.base.TblComCardInf;

public class T10502BOTarget implements T10502BO {

	private TblComCardInfDAO tblComCardInfDAO;

	public void setTblComCardInfDAO(TblComCardInfDAO tblComCardInfDAO) {
		this.tblComCardInfDAO = tblComCardInfDAO;
	}

	public String add(TblComCardInf tblComCardInf) {
		
		if(tblComCardInfDAO.get(tblComCardInf.getId()) != null) {
			
			return ErrorCode.T10501_01;
		}
		
		if(tblComCardInf.getStartTime().equals("请选择")) tblComCardInf.setStartTime(" ");
		if(tblComCardInf.getStopTime().equals("请选择")) tblComCardInf.setStopTime(" ");
		
		tblComCardInfDAO.save(tblComCardInf);
		return Constants.SUCCESS_CODE;
	}

	public String update(TblComCardInf obj) {
		
		
		return Constants.SUCCESS_CODE;
	}

	public String delete(String id) {
		
		if(tblComCardInfDAO.get(id) == null) {
			
			return ErrorCode.T10501_01;
		}
		
		tblComCardInfDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public String update(String infoList) {

		JSONArray array = JSONArray.fromObject(infoList);
		for(int i=0; i<array.size(); i++) {
			
			JSONObject object = (JSONObject) array.get(i);
			String cardId = object.getString("cardId");
			TblComCardInf tblComCardInf = tblComCardInfDAO.get(cardId);
			if(tblComCardInf != null) {
				
				tblComCardInf.setHolderName(object.getString("holderName"));
				tblComCardInf.setHolderId(object.getString("holderId"));
				tblComCardInf.setHolderTel(object.getString("holderTel"));
				tblComCardInf.setSmsQuota(object.getString("smsQuota"));
				tblComCardInf.setStartTime(object.getString("startTime"));
				tblComCardInf.setStopTime(object.getString("stopTime"));
				
				tblComCardInfDAO.update(tblComCardInf);
			}
		}
		return Constants.SUCCESS_CODE;
	}
	
	public String addBatch(List<TblComCardInf> list) {
		
		for(TblComCardInf tblComCardInf : list) {
		
			if(tblComCardInfDAO.get(tblComCardInf.getId()) == null) {
				
				if(tblComCardInf.getStartTime().equals("请选择")) tblComCardInf.setStartTime(" ");
				if(tblComCardInf.getStopTime().equals("请选择")) tblComCardInf.setStopTime(" ");
				
				tblComCardInfDAO.save(tblComCardInf);
			}
		}
		
		return Constants.SUCCESS_CODE;
	}
}
