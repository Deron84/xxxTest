package com.huateng.bo.impl.mchnt;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huateng.bo.mchnt.T20801BO;
import com.huateng.bo.mchnt.T20802BO;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.common.Operator;
import com.huateng.dao.iface.mchnt.ITblMchtBaseInfDAO;
import com.huateng.dao.iface.mchnt.ITblMchtBaseInfTmpDAO;
import com.huateng.dao.iface.mchnt.TblMchtCheckInfDAO;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtCheckInf;
import com.huateng.po.mchnt.TblMchtCheckInfPK;
import com.huateng.system.util.CommonFunction;

public class T20802BOTarget implements T20802BO {
	
	private ITblMchtBaseInfDAO tblMchtBaseInfDAO;

	private ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;
	
	public ITblMchtBaseInfDAO getTblMchtBaseInfDAO() {
		return tblMchtBaseInfDAO;
	}

	public void setTblMchtBaseInfDAO(ITblMchtBaseInfDAO tblMchtBaseInfDAO) {
		this.tblMchtBaseInfDAO = tblMchtBaseInfDAO;
	}

	
	public ITblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	public void setTblMchtBaseInfTmpDAO(ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}

	public String checkMchtAccept(String[] mchtNos, String[] planCheckDateArray) {
		if(mchtNos!=null&&!"".equals(mchtNos)&&planCheckDateArray!=null&&!"".equals(planCheckDateArray)){
			List<TblMchtBaseInf> list=new ArrayList<TblMchtBaseInf>();
			List<TblMchtBaseInfTmp> listTmp=new ArrayList<TblMchtBaseInfTmp>();
			for(int i=0;i<mchtNos.length;i++){
			TblMchtBaseInf inf = tblMchtBaseInfDAO.get(mchtNos[i].trim());
			TblMchtBaseInfTmp infTmp=tblMchtBaseInfTmpDAO.get(mchtNos[i].trim());
			infTmp.setRctCheckDate(planCheckDateArray[i]);
			infTmp.setPlanCheckDate("");
			inf.setRctCheckDate(planCheckDateArray[i]);
			inf.setPlanCheckDate("");
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

	public String checkMchtsRefuse(String[] mchtNos) {
		if(mchtNos!=null&&!"".equals(mchtNos[0])){
			List<TblMchtBaseInf> list=new ArrayList<TblMchtBaseInf>();
			List<TblMchtBaseInfTmp> listTmp=new ArrayList<TblMchtBaseInfTmp>();
			for(int i=0;i<mchtNos.length;i++){
			TblMchtBaseInf inf = tblMchtBaseInfDAO.get(mchtNos[i].trim());
			TblMchtBaseInfTmp infTmp=tblMchtBaseInfTmpDAO.get(mchtNos[i].trim());
			infTmp.setPlanCheckDate("");
			inf.setPlanCheckDate("");
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

}
