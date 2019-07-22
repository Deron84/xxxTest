package com.huateng.bo.impl.accident;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.huateng.bo.accident.T90401BO;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.common.Operator;
import com.huateng.dao.iface.accident.TblCostInfDAO;
import com.huateng.po.accident.TblCostInf;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.GenerateNextId;

public class T90401BOTarget implements T90401BO {

	private TblCostInfDAO tblCostInfDAO;
	
	public String add(TblCostInf obj) {
		
		String id = GenerateNextId.getCostId();
		
		if(id.length() > 8) {
			
			return ErrorCode.T90401_03;
		} else {
			
			obj.setId(id);
			tblCostInfDAO.save(obj);
			return Constants.SUCCESS_CODE;
		}
	}

	public String upd(String infoList) {

		Operator operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		JSONArray array = JSONArray.fromObject(infoList);
		
		for(int i=0; i<array.size(); i++) {
			
			JSONObject object = (JSONObject) array.get(i);
			String no = object.getString("no");
			String amount = object.getString("amount");
			String cardId = object.getString("cardId");
			String mchtNo = object.getString("mchtNo");
			String remarkInf = object.getString("remarkInf");
			
			TblCostInf obj = tblCostInfDAO.get(no);
			
			if(obj != null) {
				
				obj.setMchtNo(mchtNo);
				obj.setAmount(amount);
				obj.setCardId(cardId);
				obj.setRemarkInf(remarkInf);
				obj.setUpdOprId(operator.getOprId());
				obj.setRecUpdTs(CommonFunction.getCurrentDateTime());
				
				tblCostInfDAO.update(obj);
			}
		}
		
		return Constants.SUCCESS_CODE;
	}

	public String del(String id) {

		if(tblCostInfDAO.get(id) == null) {
			return ErrorCode.T90401_02;
		} else {
			tblCostInfDAO.delete(id);
			return Constants.SUCCESS_CODE;
		}
	}

	public void setTblCostInfDAO(TblCostInfDAO obj) {
		this.tblCostInfDAO = obj;
	}
}
