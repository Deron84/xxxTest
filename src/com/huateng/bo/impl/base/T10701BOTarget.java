package com.huateng.bo.impl.base;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huateng.bo.base.T10701BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblIPosInfDAO;
import com.huateng.dao.iface.mchnt.TblDivMchntDAO;
import com.huateng.po.TblDivMchnt;
import com.huateng.po.TblDivMchntPK;
import com.huateng.po.base.TblIPosInf;

public class T10701BOTarget implements T10701BO {

	private TblDivMchntDAO tblDivMchntDAO;

	public TblDivMchntDAO getTblDivMchntDAO() {
		return tblDivMchntDAO;
	}

	public void setTblDivMchntDAO(TblDivMchntDAO tblDivMchntDAO) {
		this.tblDivMchntDAO = tblDivMchntDAO;
	}

	public String add(TblDivMchnt t) {
		
		tblDivMchntDAO.save(t);
		return Constants.SUCCESS_CODE;
	}

	public String delete(TblDivMchntPK id) {
		
		tblDivMchntDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

//	public String update(String infoList) {
//		
//		JSONArray array = JSONArray.fromObject(infoList);
//		
//		for(int i=0; i<array.size(); i++) {
//			
//			JSONObject object = (JSONObject) array.get(i);
//			String posMch = object.getString("posMch");
//			TblIPosInf t = tblIPosInfDAO.get(posMch);
//			if(t != null) {
//				
//				t.setFeeCode(object.getString("feeCode"));
//				t.setOutmchNumber(object.getString("outmchNumber"));
//				t.setInmchNumber(object.getString("inmchNumber"));
//				//t.setMchNumber(object.getString("mchNumber"));
//				t.setPosStage(object.getString("posStage"));
//				tblIPosInfDAO.update(t);
//			}
//		}
//		
//		return Constants.SUCCESS_CODE;
//	}
}
