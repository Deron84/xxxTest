package com.huateng.bo.impl.term;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.huateng.bo.term.TermManagementBO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.term.TblTermInfDAO;
import com.huateng.dao.iface.term.TblTermInfTmpDAO;
import com.huateng.dao.iface.term.TblTermManagementDAO;
import com.huateng.po.TblTermInf;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermManagement;
import com.huateng.struts.pos.TblTermManagementConstants;
import com.huateng.system.util.CommonFunction;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-10
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class TermManagementBOImpl implements TermManagementBO {

	private TblTermManagementDAO tblTermManagementDAO;
	private ICommQueryDAO commQueryDAO;
	private TblTermInfDAO tblTermInfDAO;
	private TblTermInfTmpDAO tblTermInfTmpDAO;
	
	/**
	 * @param tblTermInfTmpDAO the tblTermInfTmpDAO to set
	 */
	public void setTblTermInfTmpDAO(TblTermInfTmpDAO tblTermInfTmpDAO) {
		this.tblTermInfTmpDAO = tblTermInfTmpDAO;
	}

	/**
	 * @param tblTermInfDAO the tblTermInfDAO to set
	 */
	public void setTblTermInfDAO(TblTermInfDAO tblTermInfDAO) {
		this.tblTermInfDAO = tblTermInfDAO;
	}

	/**
	 * @param commQueryDAO the commQueryDAO to set
	 */
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	/**
	 * @param tblTermManagementDAO the tblTermManagementDAO to set
	 */
	public void setTblTermManagementDAO(TblTermManagementDAO tblTermManagementDAO) {
		this.tblTermManagementDAO = tblTermManagementDAO;
	}


	public String storeTerminal(HashMap map) {
		if(map == null)
			return Constants.FAILURE_CODE;
		Operator operator = (Operator)map.get("operator");
		String batchNo = getNextBatchNo();
		TblTermManagement tblTermManagement = new TblTermManagement();
		tblTermManagement.setId(getNextId(operator.getOprBrhId()));
		tblTermManagement.setBatchNo(batchNo);
		tblTermManagement.setState(TblTermManagementConstants.TERM_STATE_SIGNED);
		tblTermManagement.setManufacturer(map.get("manufacturer").toString());
		tblTermManagement.setProductCd(map.get("startProductCd").toString());
		tblTermManagement.setTermType(map.get("termType").toString());
		tblTermManagement.setTerminalType(map.get("terminalType").toString());
		if(map.get("misc") != null && !map.get("misc").toString().trim().equals(""))
			tblTermManagement.setMisc(map.get("misc").toString());
		tblTermManagement.setStorOprId(operator.getOprId());
		tblTermManagement.setStorDate(CommonFunction.getCurrentDate());
		tblTermManagement.setLastUpdOprId(operator.getOprId());
		tblTermManagement.setLastUpdTs(CommonFunction.getCurrentTs());
		/**所属分行*/
		tblTermManagement.setBrhId(map.get("brhId").toString());
		tblTermManagementDAO.save(tblTermManagement);
		return Constants.SUCCESS_CODE_CUSTOMIZE+"批次号："+batchNo;
	}
	
	public String storeTerminals(HashMap map) {
		if(map == null)
			return Constants.FAILURE_CODE;
		List<TblTermManagement> list = new ArrayList<TblTermManagement>();
		int num = Integer.parseInt(map.get("number").toString());
		Operator operator = (Operator)map.get("operator");
		Long startProductCd = Long.valueOf(map.get("startProductCd").toString());
		String batchNo = getNextBatchNo();
		for (int i=0;i<num;i++)
		{
			TblTermManagement tblTermManagement = new TblTermManagement();
			tblTermManagement.setId(getNextId(operator.getOprBrhId()));
			tblTermManagement.setBatchNo(batchNo);
			tblTermManagement.setState(TblTermManagementConstants.TERM_STATE_SIGNED);
			tblTermManagement.setManufacturer(map.get("manufacturer").toString());
			tblTermManagement.setProductCd(startProductCd.toString());
			tblTermManagement.setTermType(map.get("termType").toString());
			tblTermManagement.setTerminalType(map.get("terminalType").toString());
			if(map.get("misc") != null && !map.get("misc").toString().trim().equals(""))
				tblTermManagement.setMisc(map.get("misc").toString());
			tblTermManagement.setStorOprId(operator.getOprId());
			tblTermManagement.setStorDate(CommonFunction.getCurrentDate());
			tblTermManagement.setLastUpdOprId(operator.getOprId());
			tblTermManagement.setLastUpdTs(CommonFunction.getCurrentTs());
			/**所属机构*/
			tblTermManagement.setBrhId(map.get("brhId").toString());//取分行
			startProductCd++;
			list.add(tblTermManagement);
		}
		tblTermManagementDAO.saveBatch(list);
		return Constants.SUCCESS_CODE_CUSTOMIZE+"批次号："+batchNo;
	}
	
	public String getNextId(String brhId) {
		if(brhId == null || brhId.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(brhId.length()>2)
			brhId = brhId.substring(0, 2);
		String sql = "select SEQ_TERM_MANAGEMENT.nextval from dual";
		List list = commQueryDAO.findBySQLQuery(sql);
		String nextId = "0";
		if(list != null && !list.isEmpty())
		{
			String num = list.get(0).toString();
			nextId = CommonFunction.fillString(num, '0', 5, false);
		}
		return "K"+ brhId + nextId;
	}

	
	public String invalidTerminal(HashMap map) {
		if(map == null)
			return Constants.FAILURE_CODE;
		String termNo = map.get("termNo")==null?"":map.get("termNo").toString();
		String misc = map.get("misc")==null?"":map.get("misc").toString();
		if(termNo.equals(""))
			return Constants.FAILURE_CODE;
		Operator operator = (Operator)map.get("operator");
		TblTermManagement tblTermManagement = tblTermManagementDAO.get(termNo);
		List list0 = tblTermInfTmpDAO.findByHQLQuery("from TblTermInfTmp t where TRIM(t.termIdId)='"+termNo+"'");
		List list1 = tblTermInfDAO.findByHQLQuery("from TblTermInf t where TRIM(t.termIdId)='"+termNo+"'");
		if(list1!=null&&!list1.isEmpty()&&list0!=null&&!list0.isEmpty())
		{
			TblTermInfTmp tblTermInfTmp = (TblTermInfTmp) list0.get(0);
			TblTermInf tblTermInf = (TblTermInf) list1.get(0);
//			tblTermInf.setProductCd(Constants.DEFAULT);
			tblTermInf.setTermIdId(Constants.DEFAULT);
			tblTermInf.setTermFactory(Constants.DEFAULT);
			tblTermInf.setTermMachTp(Constants.DEFAULT);
//			tblTermInfTmp.setProductCd(Constants.DEFAULT);
			tblTermInfTmp.setTermIdId(Constants.DEFAULT);
			tblTermInfTmp.setTermFactory(Constants.DEFAULT);
			tblTermInfTmp.setTermMachTp(Constants.DEFAULT);
			tblTermInfDAO.update(tblTermInf);
			tblTermInfTmpDAO.update(tblTermInfTmp);
		}
		tblTermManagement.setState(TblTermManagementConstants.TERM_STATE_INVALID);
		tblTermManagement.setInvalidDate(CommonFunction.getCurrentDate());
		tblTermManagement.setInvalidOprId(operator.getOprId());
		tblTermManagement.setLastUpdOprId(operator.getOprId());
		tblTermManagement.setMisc(misc);
		tblTermManagement.setLastUpdTs(CommonFunction.getCurrentTs());
		tblTermManagementDAO.update(tblTermManagement);
		return Constants.SUCCESS_CODE;
	}

	public List<TblTermManagement> queryTerminal(String manufacturer, String terminalType,
			int number, String termType) {
		StringBuffer hql = new StringBuffer("from TblTermManagement t where t.TermType = '");
		hql.append(termType.substring(0, 1));
		hql.append("'");
		hql.append(" and t.State = '");
		hql.append(TblTermManagementConstants.TERM_STATE_NORMAL);
		hql.append("'");
		if(manufacturer != null && !"".equals(manufacturer))
		{
			hql.append(" and t.Manufacturer ='").append(manufacturer).append("'");
		}
		if(terminalType != null && !"".equals(terminalType))
		{
			hql.append(" and t.TerminalType ='").append(terminalType).append("'");
		}
		hql.append(" order by t.Id");
		List <TblTermManagement> list = tblTermManagementDAO.findByHQLQuery(hql.toString(), number);
		return list;
	}
	
	public boolean updTerminal(TblTermManagement terminal, String state,String oprId,String mech) {
		terminal.setState(state);
		terminal.setMechNo(mech);
		terminal.setLastUpdOprId(oprId);
		terminal.setLastUpdTs(CommonFunction.getCurrentTs());
		tblTermManagementDAO.update(terminal);
		return true;
	}


	public String getNextBatchNo() {
		String sql = "select SEQ_TERMINAL_BATCH.nextval from dual";
		List list = commQueryDAO.findBySQLQuery(sql);
		String nextId = "0000";
		if(list != null && !list.isEmpty())
		{
			String num = list.get(0).toString();
			nextId = CommonFunction.fillString(num, '0', 4, false);
		}
		return CommonFunction.getCurrentDate()+nextId;
	}

	public String reciTerminals(String batchNo, String termId, String mchnNo,
			String oprId) {
// 		update Tbl_Term_Management set MECH_NO='mchnNo',STATE='3',RECI_OPR_ID='oprId',
//		RECI_DATE='yyyymmdd',LAST_UPD_OPR_ID='oprId',LAST_UPD_TS=new TimeStamp()  
//		where BATCH_NO='batchNo' and STATE='2'
		if(batchNo == null||batchNo.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(mchnNo == null||mchnNo.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(batchNo == null||batchNo.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(oprId == null||oprId.trim().equals(""))
			return Constants.FAILURE_CODE;
		
		StringBuffer sql = new StringBuffer("update Tbl_Term_Management ");
		sql.append("set MECH_NO='").append(mchnNo).append("',");
		sql.append("STATE='").append(TblTermManagementConstants.TERM_STATE_RECI).append("',");
		sql.append("RECI_OPR_ID='").append(oprId).append("',");
		sql.append("RECI_DATE='").append(CommonFunction.getCurrentDate()).append("',");
		sql.append("LAST_UPD_OPR_ID='").append(oprId).append("',");
		sql.append("LAST_UPD_TS=to_timestamp('").append(CommonFunction.getCurrentDateTimeForShow()).append("','yyyy-mm-dd hh24:mi:ss')");
		sql.append(" where BATCH_NO='").append(batchNo).append("' ");
		sql.append("and STATE='").append(TblTermManagementConstants.TERM_STATE_RECI_UNCHECK).append("'");
		commQueryDAO.excute(sql.toString());
		return Constants.SUCCESS_CODE;
	}

	public String reciTerminal(String batchNo, String termId, String mechNo,
			String oprId) {
		TblTermManagement tblTermManagement = tblTermManagementDAO.get(termId);
		if(tblTermManagement == null)
			return Constants.FAILURE_CODE;
		
		tblTermManagement.setState(TblTermManagementConstants.TERM_STATE_RECI);
		tblTermManagement.setMechNo(mechNo);
		tblTermManagement.setReciOprId(oprId);
		tblTermManagement.setReciDate(CommonFunction.getCurrentDate());
		tblTermManagement.setLastUpdOprId(oprId);
		tblTermManagement.setLastUpdTs(CommonFunction.getCurrentTs());
		
		tblTermManagementDAO.update(tblTermManagement);
		return Constants.SUCCESS_CODE;
	}

	public String refuseTerminal(String batchNo, String termId, String mechNo,
			String oprId) {
		TblTermManagement tblTermManagement = tblTermManagementDAO.get(termId);
		if(tblTermManagement == null)
			return Constants.FAILURE_CODE;
		
		tblTermManagement.setState(TblTermManagementConstants.TERM_STATE_NORMAL);
		tblTermManagement.setMechNo(mechNo);
		tblTermManagement.setLastUpdOprId(oprId);
		tblTermManagement.setLastUpdTs(CommonFunction.getCurrentTs());
		
		tblTermManagementDAO.update(tblTermManagement);
		return Constants.SUCCESS_CODE;
	}

	public String refuseTerminals(String batchNo, String termId, String mchnNo,
			String oprId) {
		if(batchNo == null||batchNo.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(mchnNo == null||mchnNo.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(batchNo == null||batchNo.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(oprId == null||oprId.trim().equals(""))
			return Constants.FAILURE_CODE;
		
		StringBuffer sql = new StringBuffer("update Tbl_Term_Management ");
		sql.append("set MECH_NO='").append(mchnNo).append("',");
		sql.append("BATCH_NO='',");
		sql.append("STATE='").append(TblTermManagementConstants.TERM_STATE_NORMAL).append("',");
		sql.append("LAST_UPD_OPR_ID='").append(oprId).append("',");
		sql.append("LAST_UPD_TS=to_timestamp('").append(CommonFunction.getCurrentDateTimeForShow()).append("','yyyy-mm-dd hh24:mi:ss')");
		sql.append(" where BATCH_NO='").append(batchNo).append("' ");
		sql.append("and STATE='").append(TblTermManagementConstants.TERM_STATE_RECI_UNCHECK).append("'");
		commQueryDAO.excute(sql.toString());
		return Constants.SUCCESS_CODE;
	}

	public String signTerminal(String batchNo, String termId, String oprId) {
		TblTermManagement tblTermManagement = tblTermManagementDAO.get(termId);
		if(tblTermManagement == null)
			return Constants.FAILURE_CODE;
		
		tblTermManagement.setState(TblTermManagementConstants.TERM_STATE_SIGNED);
		tblTermManagement.setSignOprId(oprId);
		tblTermManagement.setSignDate(CommonFunction.getCurrentDate());
		tblTermManagement.setLastUpdOprId(oprId);
		tblTermManagement.setLastUpdTs(CommonFunction.getCurrentTs());
		
		tblTermManagementDAO.update(tblTermManagement);
		return Constants.SUCCESS_CODE;
	}

	public String signTerminals(String batchNo, String termId, String oprId) {
		if(batchNo == null||batchNo.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(batchNo == null||batchNo.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(oprId == null||oprId.trim().equals(""))
			return Constants.FAILURE_CODE;
		
		StringBuffer sql = new StringBuffer("update Tbl_Term_Management ");
		sql.append("set STATE='").append(TblTermManagementConstants.TERM_STATE_SIGNED).append("',");
		sql.append("SIGN_OPR_ID='").append(oprId).append("',");
		sql.append("SIGN_DATE='").append(CommonFunction.getCurrentDate()).append("',");
		sql.append("LAST_UPD_OPR_ID='").append(oprId).append("',");
		sql.append("LAST_UPD_TS=to_timestamp('").append(CommonFunction.getCurrentDateTimeForShow()).append("','yyyy-mm-dd hh24:mi:ss')");
		sql.append(" where BATCH_NO='").append(batchNo).append("' ");
		sql.append("and STATE='").append(TblTermManagementConstants.TERM_STATE_RECI).append("'");
		commQueryDAO.excute(sql.toString());
		return Constants.SUCCESS_CODE;
	}

	public String bankTermianl(int action, String termId,Operator operator) {
		TblTermManagement tblTermManagement = tblTermManagementDAO.get(termId);
		if(tblTermManagement == null)
			return Constants.FAILURE_CODE;
		tblTermManagement.setBackOprId(operator.getOprId());
		tblTermManagement.setBankDate(CommonFunction.getCurrentDate());
		if(action == 0)
		{
			updTerminal(tblTermManagement, TblTermManagementConstants.TERM_STATE_NORMAL,operator.getOprId(),"");
		}
		if(action == 1)
		{
			updTerminal(tblTermManagement, TblTermManagementConstants.TERM_STATE_INVALID,operator.getOprId(),"");
		}
		if(action == 2)
		{
			updTerminal(tblTermManagement, TblTermManagementConstants.TERM_STATE_RECI_UNCHECK,operator.getOprId(),tblTermManagement.getMechNo());
		}
		return Constants.SUCCESS_CODE;
	}

	public TblTermManagement getTerminal(String termId) {
		return tblTermManagementDAO.get(termId);
	}
	
	public boolean refuseTerm(String termId,String oprId) {
		TblTermManagement tblTermManagement = tblTermManagementDAO.get(termId);
		tblTermManagement.setState(TblTermManagementConstants.TERM_STATE_SIGNED);
		tblTermManagement.setLastUpdOprId(oprId);
		tblTermManagement.setLastUpdTs(CommonFunction.getCurrentTs());
		return true;
	}

	public boolean bindTermInfo(TblTermManagement tblTermManagement,
			String oprId, TblTermInf tblTermInf, TblTermInfTmp tblTermInfTmp) {
//		updTerminal(tblTermManagement, TblTermManagementConstants.TERM_STATE_END,oprId,tblTermInf.getMchtCd().trim());
//		tblTermInf.setProductCd(tblTermManagement.getProductCd());
		tblTermInf.setTermIdId(tblTermManagement.getId());
		tblTermInf.setTermFactory(tblTermManagement.getManufacturer());
		tblTermInf.setTermMachTp(tblTermManagement.getTerminalType());
//		tblTermInfTmp.setProductCd(tblTermManagement.getProductCd());
		tblTermInfTmp.setTermIdId(tblTermManagement.getId());
		tblTermInfTmp.setTermFactory(tblTermManagement.getManufacturer());
		tblTermInfTmp.setTermMachTp(tblTermManagement.getTerminalType());
		tblTermManagement.setMechNo(tblTermInf.getId().getMchtCd());
		/**库存状态为出库*/
		tblTermManagement.setState(TblTermManagementConstants.TERM_STATE_END);
		/**出库时间*/
		tblTermManagement.setOutStoreDate(CommonFunction.getCurrentDate());
		tblTermInfTmpDAO.update(tblTermInfTmp);
		tblTermInfDAO.update(tblTermInf);
		tblTermManagementDAO.update(tblTermManagement);
		return true;
	}

}
