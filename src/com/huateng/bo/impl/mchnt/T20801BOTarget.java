package com.huateng.bo.impl.mchnt;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huateng.bo.mchnt.T20801BO;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.common.Operator;
import com.huateng.dao.iface.mchnt.ITblMchtBaseInfDAO;
import com.huateng.dao.iface.mchnt.TblMchtCheckInfDAO;
import com.huateng.dao.impl.mchnt.TblMchtBaseInfTmpDAO;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtCheckInf;
import com.huateng.po.mchnt.TblMchtCheckInfPK;
import com.huateng.system.util.CommonFunction;

public class T20801BOTarget implements T20801BO {
	
	private TblMchtCheckInfDAO tblMchtCheckInfDAO;
	private ITblMchtBaseInfDAO tblMchtBaseInfDAO;
	private TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;
//	public String add(TblMchtCheckInf obj) {
//		
//		if(tblMchtCheckInfDAO.get(obj.getId()) != null) {
//			
//			return ErrorCode.T20801_01;
//		}
//		
//		tblMchtCheckInfDAO.save(obj);
//		return Constants.SUCCESS_CODE;
//	}
//
//	public String update(String infoList) {
//
//		Operator operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
//		JSONArray array = JSONArray.fromObject(infoList);
//		
//		for(int i=0; i<array.size(); i++) {
//			
//			JSONObject object = (JSONObject) array.get(i);
//			String mchtNo = object.getString("mchtNo");
//			String checkDate = object.getString("checkDate");
//			String checkName = object.getString("checkName");
//			String checkInf = object.getString("checkInf");
//			
//			TblMchtCheckInfPK pk = new  TblMchtCheckInfPK();
//			pk.setMchtNo(mchtNo);
//			pk.setCheckDate(checkDate);
//			
//			TblMchtCheckInf tblMchtCheckInf = tblMchtCheckInfDAO.get(pk);
//			
//			if(tblMchtCheckInf != null) {
//				
//				tblMchtCheckInf.setCheckName(checkName);
//				tblMchtCheckInf.setCheckInf(checkInf);
//				tblMchtCheckInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
//				tblMchtCheckInf.setUpdOprId(operator.getOprId());
//				tblMchtCheckInfDAO.update(tblMchtCheckInf);
//			}
//		}
//		
//		return Constants.SUCCESS_CODE;
//	}
//
//	public String del(TblMchtCheckInfPK pk) {
//		
//		if(tblMchtCheckInfDAO.get(pk) == null) {
//			
//			return ErrorCode.T20801_02;
//		}
//
//		tblMchtCheckInfDAO.delete(pk);
//		return Constants.SUCCESS_CODE;
//	}
	
	public String checkMcht(String mchtNo){
		Operator operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		TblMchtBaseInf inf = tblMchtBaseInfDAO.get(mchtNo);
		inf.setPlanCheckDate(CommonFunction.getCurrentDate());
		inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
		inf.setUpdOprId(operator.getOprId());
		tblMchtBaseInfDAO.update(inf);
		return Constants.SUCCESS_CODE;
	}
	
	public String checkMchts(String[] mchtNos) {
		Operator operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		if(mchtNos!=null&&!"".equals(mchtNos[0])){
			List<TblMchtBaseInf> list=new ArrayList<TblMchtBaseInf>();
			List<TblMchtBaseInfTmp> listTmp=new ArrayList<TblMchtBaseInfTmp>();
			for(int i=0;i<mchtNos.length;i++){
			TblMchtBaseInf inf = tblMchtBaseInfDAO.get(mchtNos[i].trim());
			TblMchtBaseInfTmp infTmp=tblMchtBaseInfTmpDAO.get(mchtNos[i].trim());
			inf.setPlanCheckDate(CommonFunction.getCurrentDate());
			inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
			inf.setUpdOprId(operator.getOprId());
			infTmp.setPlanCheckDate(CommonFunction.getCurrentDate());
			infTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
			infTmp.setUpdOprId(operator.getOprId());
            list.add(inf);
            listTmp.add(infTmp);
			}
			tblMchtBaseInfDAO.saveBatch(list);
			tblMchtBaseInfTmpDAO.saveBatch(listTmp);
		}else{
			return ErrorCode.T20801_03;
		}
		return Constants.SUCCESS_CODE;
	}

	public void setTblMchtCheckInfDAO(TblMchtCheckInfDAO tblMchtCheckInfDAO) {
		this.tblMchtCheckInfDAO = tblMchtCheckInfDAO;
	}

	/**
	 * @return the tblMchtBaseInfDAO
	 */
	public ITblMchtBaseInfDAO getTblMchtBaseInfDAO() {
		return tblMchtBaseInfDAO;
	}

	/**
	 * @param tblMchtBaseInfDAO the tblMchtBaseInfDAO to set
	 */
	public void setTblMchtBaseInfDAO(ITblMchtBaseInfDAO tblMchtBaseInfDAO) {
		this.tblMchtBaseInfDAO = tblMchtBaseInfDAO;
	}

	public TblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	public void setTblMchtBaseInfTmpDAO(TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}

	
}
