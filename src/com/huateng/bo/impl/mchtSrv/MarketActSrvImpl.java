package com.huateng.bo.impl.mchtSrv;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.mchtSrv.TblMarketActDAO;
import com.huateng.dao.iface.mchtSrv.TblMarketActReviewDAO;
import com.huateng.dao.iface.mchtSrv.TblMchntParticipatDAO;
import com.huateng.dao.iface.mchtSrv.TblMchntParticipatReviewDAO;
import com.huateng.po.mchtSrv.TblMarketAct;
import com.huateng.po.mchtSrv.TblMarketActReview;
import com.huateng.po.mchtSrv.TblMchntParticipat;
import com.huateng.po.mchtSrv.TblMchntParticipatPK;
import com.huateng.po.mchtSrv.TblMchntParticipatReview;
import com.huateng.po.mchtSrv.TblMchntParticipatReviewPK;
import com.huateng.struts.mchtSrv.MarketActConstants;
import com.huateng.system.util.CommonFunction;

public class MarketActSrvImpl implements MarketActSrv {
	TblMarketActDAO tblMarketActDAO;
	TblMarketActReviewDAO tblMarketActReviewDAO;
	TblMchntParticipatDAO tblMchntParticipatDAO;
	TblMchntParticipatReviewDAO tblMchntParticipatReviewDAO;
	ICommQueryDAO commQueryDAO;
	
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}
	public void setTblMarketActDAO(TblMarketActDAO tblMarketActDAO) {
		this.tblMarketActDAO = tblMarketActDAO;
	}
	public void setTblMarketActReviewDAO(TblMarketActReviewDAO tblMarketActReviewDAO) {
		this.tblMarketActReviewDAO = tblMarketActReviewDAO;
	}
	public void setTblMchntParticipatDAO(TblMchntParticipatDAO tblMchntParticipatDAO) {
		this.tblMchntParticipatDAO = tblMchntParticipatDAO;
	}
	public void setTblMchntParticipatReviewDAO(
			TblMchntParticipatReviewDAO tblMchntParticipatReviewDAO) {
		this.tblMchntParticipatReviewDAO = tblMchntParticipatReviewDAO;
	}
	
	
	public String getActNo(String oprBrhId) {
		StringBuffer actNo = new StringBuffer();
		actNo.append(oprBrhId).append(CommonFunction.getCurrDate("yyyy").substring(2, 4));
		String sql = "select max(substr(act_no,7)) from Tbl_Market_Act_Review where substr(act_no,0,4)='"+oprBrhId+"'";
		List list = commQueryDAO.findBySQLQuery(sql);
		if(list == null || list.isEmpty() || list.get(0) == null)
			return actNo.append("0001").toString();
		int max = Integer.parseInt(list.get(0).toString().trim())+1;
		if(max > 9999)
			max = 1;
		return actNo.append(CommonFunction.fillString(String.valueOf(max), '0', 4, false)).toString();
	}
	
	
	public String saveNew(TblMarketActReview tblMarketActReview,
			String[] selectedOptions) {
		
		tblMarketActReviewDAO.save(tblMarketActReview);
		
		if(selectedOptions !=null && !selectedOptions[0].equals("")){
			List<TblMchntParticipatReview> list = new ArrayList<TblMchntParticipatReview>();
			for(int i=0;i<selectedOptions.length;i++)
			{
				TblMchntParticipatReview tblMchntParticipatReview = new TblMchntParticipatReview();
				TblMchntParticipatReviewPK pk = new TblMchntParticipatReviewPK();
				
				pk.setActNo(tblMarketActReview.getActNo());
				tblMchntParticipatReview.setBankNo(tblMarketActReview.getBankNo());
				tblMchntParticipatReview.setActName(tblMarketActReview.getActName());
				tblMchntParticipatReview.setStartDate(tblMarketActReview.getStartDate());
				tblMchntParticipatReview.setState(MarketActConstants.STATE_UNCHECK);
				tblMchntParticipatReview.setEndDate(tblMarketActReview.getEndDate());
				tblMchntParticipatReview.setCrtOpr(tblMarketActReview.getCrtOpr());
				tblMchntParticipatReview.setCrtTs(tblMarketActReview.getCrtTs());
				tblMchntParticipatReview.setUpdDt(tblMarketActReview.getUpdDt());
				tblMchntParticipatReview.setUpdOpr(tblMarketActReview.getUpdOpr());
				tblMchntParticipatReview.setFlag01(MarketActConstants.OPR_ADD);
				pk.setMchntNo(selectedOptions[i]);
				tblMchntParticipatReview.setId(pk);
				list.add(tblMchntParticipatReview);
			}
			tblMchntParticipatReviewDAO.saveBatch(list);
		}
		
		return Constants.SUCCESS_CODE;
	}
	public String save(TblMarketActReview tblMarketActReview,String[] selectedOptions,String[] actFees) {
		tblMarketActReviewDAO.save(tblMarketActReview);
		if(selectedOptions !=null && !selectedOptions[0].equals("")){
			List<TblMchntParticipatReview> list = new ArrayList<TblMchntParticipatReview>();
			for(int i=0;i<selectedOptions.length && i<actFees.length;i++)
			{
				TblMchntParticipatReview tblMchntParticipatReview = new TblMchntParticipatReview();
				TblMchntParticipatReviewPK pk = new TblMchntParticipatReviewPK();
				
				pk.setActNo(tblMarketActReview.getActNo());
				tblMchntParticipatReview.setBankNo(tblMarketActReview.getBankNo());
				tblMchntParticipatReview.setActName(tblMarketActReview.getActName());
				tblMchntParticipatReview.setStartDate(tblMarketActReview.getStartDate());
				tblMchntParticipatReview.setState(MarketActConstants.STATE_UNCHECK);
				tblMchntParticipatReview.setEndDate(tblMarketActReview.getEndDate());
				tblMchntParticipatReview.setCrtOpr(tblMarketActReview.getCrtOpr());
				tblMchntParticipatReview.setCrtTs(tblMarketActReview.getCrtTs());
				tblMchntParticipatReview.setUpdDt(tblMarketActReview.getUpdDt());
				tblMchntParticipatReview.setUpdOpr(tblMarketActReview.getUpdOpr());
				tblMchntParticipatReview.setFlag01(MarketActConstants.OPR_ADD);
				pk.setMchntNo(selectedOptions[i]);
				tblMchntParticipatReview.setId(pk);
				try{
					tblMchntParticipatReview.setActFee(new BigDecimal(actFees[i]));
				}catch(NumberFormatException e){
					return MarketActConstants.T70202_03;
				}
				list.add(tblMchntParticipatReview);
			}
			tblMchntParticipatReviewDAO.saveBatch(list);
		}
		
		return Constants.SUCCESS_CODE;
	}
	
	public String update(TblMarketActReview tblMarketActReview) {
		tblMarketActReviewDAO.update(tblMarketActReview);
		return Constants.SUCCESS_CODE;
	}
	
	public String delMchntsNew(String[] actNo, String[] selectedOptions,
			String oprId) {
		StringBuffer selects = new StringBuffer();
		List<TblMchntParticipatReview> list=new ArrayList<TblMchntParticipatReview>();
		for(int i=0;i<selectedOptions.length;i++)
		{
			TblMchntParticipatReviewPK pk=new TblMchntParticipatReviewPK();
			pk.setActNo(actNo[i]);
			pk.setMchntNo(selectedOptions[i]);
			TblMchntParticipatReview tblMchntParticipatReview=(TblMchntParticipatReview)tblMchntParticipatReviewDAO.get(pk);
			tblMchntParticipatReview.setFlag01(MarketActConstants.OPR_DEL);
			tblMchntParticipatReview.setUpdOpr(oprId);
			tblMchntParticipatReview.setUpdDt(CommonFunction.getCurrentDate());
			tblMchntParticipatReview.setState(MarketActConstants.STATE_UNCHECK);
			list.add(tblMchntParticipatReview);
		}
		tblMchntParticipatReviewDAO.deleteBatch(list);
//		StringBuffer sql = new StringBuffer("update tbl_mchnt_participat_review")
//			.append(" set flag01 = '").append(MarketActConstants.OPR_DEL).append("',upd_opr='")
//			.append(oprId).append("',upd_dt='")
//			.append(CommonFunction.getCurrentDate()).append("',state='")
//			.append(MarketActConstants.STATE_UNCHECK).append("'")
//			.append(" where MCHNT_NO in (").append(selects.substring(0, selects.length()-1))
//			.append(") and ACT_NO='").append(actNo).append("'");
//		commQueryDAO.excute(sql.toString());
		return Constants.SUCCESS_CODE;
	}
	public String delMchnts(String actNo, String[] selectedOptions,String oprId) {
		StringBuffer selects = new StringBuffer();
		for(int i=0;i<selectedOptions.length;i++)
		{
			selects.append("'").append(selectedOptions[i]).append("',");
		}
		StringBuffer sql = new StringBuffer("update tbl_mchnt_participat_review")
			.append(" set flag01 = '").append(MarketActConstants.OPR_DEL).append("',upd_opr='")
			.append(oprId).append("',upd_dt='")
			.append(CommonFunction.getCurrentDate()).append("',state='")
			.append(MarketActConstants.STATE_UNCHECK).append("'")
			.append(" where MCHNT_NO in (").append(selects.substring(0, selects.length()-1))
			.append(") and ACT_NO='").append(actNo).append("'");
		commQueryDAO.excute(sql.toString());
		return Constants.SUCCESS_CODE;
	}
	
	public String addMchntNew(String actNo, String[] selectedOptions,
			String oprId) {
		
		TblMarketActReview tblMarketActReview = tblMarketActReviewDAO.get(actNo);
		
		if(tblMarketActReview == null)
			return MarketActConstants.T70202_04;
		
		String sql;
		int start = Integer.valueOf(tblMarketActReview.getStartDate());
		int end = Integer.valueOf(tblMarketActReview.getEndDate());
		int thisStart;
		int thisEnd;
		
		List<TblMchntParticipatReview> list = new ArrayList<TblMchntParticipatReview>();
		
		for(int i=0;i<selectedOptions.length;i++){
			sql = "select ACT_NO,MCHNT_NO,START_DATE,END_DATE " +
				  "from TBL_MCHNT_PARTICIPAT_REVIEW where MCHNT_NO ='"+ selectedOptions[i] +"'";
			
			List<Object[]> exist = commQueryDAO.findBySQLQuery(sql);
			
//			if(exist.size() != 0){
//				for(int j = 0;j < exist.size(); j++){
//					thisStart = Integer.valueOf(exist.get(j)[2].toString());
//					thisEnd = Integer.valueOf(exist.get(j)[3].toString());
//					
//					if(((start < thisStart && thisStart < end) || (start < thisEnd && thisEnd < end)) 
//							|| ((thisStart < start && start < thisEnd) || (thisStart < end && end < thisEnd))){
//						return "商户["+selectedOptions[i]+"]已参加同时段编号为["+exist.get(j)[0].toString()+"]的活动！";
//					}
//				}
//			}
			
			TblMchntParticipatReview tblMchntParticipatReview = new TblMchntParticipatReview();
			TblMchntParticipatReviewPK pk = new TblMchntParticipatReviewPK();
			
			pk.setActNo(tblMarketActReview.getActNo());
			pk.setMchntNo(selectedOptions[i]);
			
			
			tblMchntParticipatReview.setBankNo(tblMarketActReview.getBankNo());
			tblMchntParticipatReview.setActName(tblMarketActReview.getActName());
			tblMchntParticipatReview.setStartDate(tblMarketActReview.getStartDate());
			tblMchntParticipatReview.setState(MarketActConstants.STATE_UNCHECK);
			tblMchntParticipatReview.setEndDate(tblMarketActReview.getEndDate());
			tblMchntParticipatReview.setCrtOpr(oprId);
			tblMchntParticipatReview.setCrtTs(CommonFunction.getCurrentDate());
			tblMchntParticipatReview.setUpdDt(CommonFunction.getCurrentDate());
			tblMchntParticipatReview.setUpdOpr(oprId);
			tblMchntParticipatReview.setFlag01(MarketActConstants.OPR_MOD);
			
			tblMchntParticipatReview.setId(pk);
			list.add(tblMchntParticipatReview);
		}
		tblMchntParticipatReviewDAO.saveBatch(list);
		
		return Constants.SUCCESS_CODE;
	}
	public String addMchnt(String actNo, String[] selectedOptions,
			String[] actFees,String oprId) {
		TblMarketActReview tblMarketActReview = tblMarketActReviewDAO.get(actNo);
		if(tblMarketActReview == null)
			return MarketActConstants.T70202_04;
		List<TblMchntParticipatReview> list = new ArrayList<TblMchntParticipatReview>();
		for(int i=0;i<selectedOptions.length && i<actFees.length;i++)
		{
			TblMchntParticipatReview tblMchntParticipatReview = new TblMchntParticipatReview();
			TblMchntParticipatReviewPK pk = new TblMchntParticipatReviewPK();
			
			pk.setActNo(tblMarketActReview.getActNo());
			tblMchntParticipatReview.setBankNo(tblMarketActReview.getBankNo());
			tblMchntParticipatReview.setActName(tblMarketActReview.getActName());
			tblMchntParticipatReview.setStartDate(tblMarketActReview.getStartDate());
			tblMchntParticipatReview.setState(MarketActConstants.STATE_UNCHECK);
			tblMchntParticipatReview.setEndDate(tblMarketActReview.getEndDate());
			tblMchntParticipatReview.setCrtOpr(oprId);
			tblMchntParticipatReview.setCrtTs(CommonFunction.getCurrentDate());
			tblMchntParticipatReview.setUpdDt(CommonFunction.getCurrentDate());
			tblMchntParticipatReview.setUpdOpr(oprId);
			tblMchntParticipatReview.setFlag01(MarketActConstants.OPR_MOD);
			pk.setMchntNo(selectedOptions[i]);
			tblMchntParticipatReview.setId(pk);
			try{
				tblMchntParticipatReview.setActFee(new BigDecimal(actFees[i]));
			}catch(NumberFormatException e){
				return MarketActConstants.T70202_03;
			}
			list.add(tblMchntParticipatReview);
		}
		tblMchntParticipatReviewDAO.saveBatch(list);
		
		return Constants.SUCCESS_CODE;
	}
	
	public String check(String actNo, String oprId,List<TblMchntParticipatReview> list) {
		TblMarketActReview tblMarketActReview = tblMarketActReviewDAO.get(actNo);
		if(tblMarketActReview == null)
			return MarketActConstants.T70204_02;
//		if(oprId.equals(tblMarketActReview.getUpdOpr().trim()))
//			return MarketActConstants.T70204_03;
		
		if(tblMarketActReview.getFlag01()!=null
				&&tblMarketActReview.getFlag01().equals(MarketActConstants.OPR_DEL))
			tblMarketActReview.setState(MarketActConstants.STATE_CLOSE);
		else
			tblMarketActReview.setState(MarketActConstants.STATE_OK);

		if(!list.isEmpty()){
			//临时表
			StringBuffer sql1 = new StringBuffer("update tbl_mchnt_participat_review set end_date='")
				.append(tblMarketActReview.getEndDate()).append("' ,bank_no='").append(tblMarketActReview.getBankNo())
				.append("' ,act_name='").append(tblMarketActReview.getActName());
			//修改审核，关闭活动
			if(tblMarketActReview.getFlag01()!=null
					&&tblMarketActReview.getFlag01().equals(MarketActConstants.OPR_DEL))
				sql1.append("' ,state='").append(MarketActConstants.STATE_CLOSE);
			//新增审核
			if(tblMarketActReview.getFlag01()!=null
					&&tblMarketActReview.getFlag01().equals(MarketActConstants.OPR_ADD))
				sql1.append("' ,state='").append(MarketActConstants.STATE_OK)
					.append("' ,rec_opr='").append(oprId)
					.append("' ,flag01='").append("' where act_no='")
					.append(actNo).append("' and flag01='").append(MarketActConstants.OPR_ADD)
					.append("'");
			else
				sql1.append("' where act_no='").append(actNo).append("'");
			commQueryDAO.excute(sql1.toString());
			
			//正式表
			//新增审核
			if(tblMarketActReview.getFlag01()!=null
					&&tblMarketActReview.getFlag01().equals(MarketActConstants.OPR_ADD)){
				
				Iterator<TblMchntParticipatReview> it = list.iterator();
				List<TblMchntParticipat> mchntList = new ArrayList<TblMchntParticipat>();
				
				while(it.hasNext()){
					TblMchntParticipat tmp = new TblMchntParticipat();
					TblMchntParticipatReview tblMchntParticipatitReview = it.next();
					
					BeanUtils.copyProperties(tblMchntParticipatitReview, tmp,new String[]{"id"});
					tmp.setId(new TblMchntParticipatPK(tblMchntParticipatitReview.getId().getActNo()
							,tblMchntParticipatitReview.getId().getMchntNo()));
					tmp.setFlag01(null);
					tmp.setRecOpr(oprId);
					tmp.setMisc1(CommonFunction.getCurrentDate());
					tmp.setState(MarketActConstants.STATE_OK);
					mchntList.add(tmp);
				}
				
				tblMchntParticipatDAO.saveBatch(mchntList);
			}else {
				
				StringBuffer sql2 = new StringBuffer("update tbl_mchnt_participat set end_date='")
				.append(tblMarketActReview.getEndDate()).append("' ,bank_no='").append(tblMarketActReview.getBankNo())
				.append("' ,act_name='").append(tblMarketActReview.getActName());
				//修改审核，关闭活动
				if(tblMarketActReview.getFlag01()!=null
						&&tblMarketActReview.getFlag01().equals(MarketActConstants.OPR_DEL))
					sql2.append("' ,state='").append(MarketActConstants.STATE_CLOSE);
				sql2.append("' where act_no='").append(actNo).append("'");
				
				commQueryDAO.excute(sql2.toString());
			}
		}
		
		
		tblMarketActReview.setFlag01(null);
		tblMarketActReview.setRecOpr(oprId);
		tblMarketActReview.setMisc1(CommonFunction.getCurrentDate());
		tblMarketActReview.setRemarks(tblMarketActReview.getRemarks());
		tblMarketActReviewDAO.update(tblMarketActReview);
		
		TblMarketAct tblMarketAct = new TblMarketAct();
		BeanUtils.copyProperties(tblMarketActReview, tblMarketAct);
		tblMarketAct.setRemarks(tblMarketActReview.getRemarks());
		tblMarketActDAO.saveOrUpdate(tblMarketAct);

		return Constants.SUCCESS_CODE;
	}
	
	public String refuse(String actNo, String oprId) {
		TblMarketActReview tblMarketActReview = tblMarketActReviewDAO.get(actNo);
		TblMarketAct tblMarketAct = tblMarketActDAO.get(actNo);
		if(tblMarketActReview == null)
			return MarketActConstants.T70204_02;
		if(oprId.equals(tblMarketActReview.getUpdOpr().trim()))
			return MarketActConstants.T70204_03;
		if(tblMarketAct == null)
		{
			tblMarketActReviewDAO.delete(tblMarketActReview);
			StringBuffer sql = new StringBuffer("delete from tbl_mchnt_participat_review ")
				.append(" where act_no='").append(actNo).append("'");
			commQueryDAO.excute(sql.toString());
		}
		else
		{
			BeanUtils.copyProperties(tblMarketAct, tblMarketActReview);
			tblMarketActReviewDAO.update(tblMarketActReview);
		}
		return Constants.SUCCESS_CODE;
	}
	
	public String checkMchnt(String actNo, String mchntNo, String oprId) {
		TblMchntParticipatReview tblMchntParticipatReview = 
			tblMchntParticipatReviewDAO.get(new TblMchntParticipatReviewPK(actNo,mchntNo));
		if(tblMchntParticipatReview == null)
			return MarketActConstants.T70205_04;
		
		tblMchntParticipatReview.setState(MarketActConstants.STATE_OK);
		tblMchntParticipatReview.setUpdDt(CommonFunction.getCurrentDate());
		tblMchntParticipatReview.setUpdOpr(oprId);
		tblMchntParticipatReview.setRecOpr(oprId);
		
		TblMchntParticipat tblMchntParticipat = new TblMchntParticipat();
		BeanUtils.copyProperties(tblMchntParticipatReview, tblMchntParticipat,new String[]{"id"});
		tblMchntParticipat.setId(new TblMchntParticipatPK(actNo,mchntNo));
		
		if(tblMchntParticipatReview.getFlag01()!=null && 
				tblMchntParticipatReview.getFlag01().equals(MarketActConstants.OPR_DEL))
		{
			tblMchntParticipatReviewDAO.delete(tblMchntParticipatReview);
			tblMchntParticipatDAO.delete(tblMchntParticipat);
		}
		else
		{
//			String flag = isExistMchntNo(actNo,mchntNo);
//			if(flag != null)
//				return flag;
			tblMchntParticipatReview.setFlag01(null);
			tblMchntParticipatReviewDAO.update(tblMchntParticipatReview);
			tblMchntParticipatDAO.saveOrUpdate(tblMchntParticipat);
		}
			
		return Constants.SUCCESS_CODE;
	}
	
	public String refuseMchnt(String actNo, String mchntNo, String oprId) {
		TblMchntParticipatReview tblMchntParticipatReview = 
			tblMchntParticipatReviewDAO.get(new TblMchntParticipatReviewPK(actNo,mchntNo));
		TblMchntParticipat tblMchntParticipat = 
			tblMchntParticipatDAO.get(new TblMchntParticipatPK(actNo,mchntNo));
		if(tblMchntParticipatReview == null)
			return MarketActConstants.T70205_04;
		if(oprId.equals(tblMchntParticipatReview.getUpdOpr().trim()))
			return MarketActConstants.T70205_03;
		
		if(tblMchntParticipat == null)
		{
			if(tblMchntParticipatReview.getFlag01().equals(MarketActConstants.OPR_DEL))
			{
				tblMchntParticipatReview.setFlag01(null);
				tblMchntParticipatReviewDAO.update(tblMchntParticipatReview);
			}
			else
				tblMchntParticipatReviewDAO.delete(tblMchntParticipatReview);
		}
		else
		{
			tblMchntParticipatReview.setFlag01(null);
			BeanUtils.copyProperties(tblMchntParticipat, tblMchntParticipatReview,new String[]{"id"});
			tblMchntParticipatReview.setId(new TblMchntParticipatReviewPK(actNo,mchntNo));
			tblMchntParticipatReviewDAO.update(tblMchntParticipatReview);
		}
		return Constants.SUCCESS_CODE;
	}
	public TblMarketActReview getMarketAct(String actNo) {
		return tblMarketActReviewDAO.get(actNo);
	}
	public TblMchntParticipatReview getMchnt(String actNo, String mchntNo) {
		return tblMchntParticipatReviewDAO.get(new TblMchntParticipatReviewPK(actNo,mchntNo));
	}

	public String isExistMchntNo(String actNo,String mchntNo)
	{
		//修改结束日期也应该大于当前日期
		String sql = "select mchnt_no from tbl_mchnt_participat_review where mchnt_no in("
			+mchntNo+") and state in ('0','1') and end_date >= '"+CommonFunction.getCurrentDate()+"'";
		if(actNo != null && !actNo.equals(""))
			sql += " and  act_no !='"+actNo+"'";
		List list = commQueryDAO.findBySQLQuery(sql);
		if(list != null&&!list.isEmpty())
			return MarketActConstants.T70205_05;
		return null;
	}
	
	public TblMarketAct getActInfo(String actNo) {
		return tblMarketActDAO.get(actNo);
	}
	public String checkMchnt(String[] actNo, String[] mchntNo, String oprId) {
		for(int i=0;i<actNo.length && i<mchntNo.length;i++)
		{
			TblMchntParticipatReview tblMchntParticipatReview = 
				tblMchntParticipatReviewDAO.get(new TblMchntParticipatReviewPK(actNo[i],mchntNo[i]));
			if(tblMchntParticipatReview == null)
				return MarketActConstants.T70205_04;
			
			tblMchntParticipatReview.setState(MarketActConstants.STATE_OK);
			tblMchntParticipatReview.setRecOpr(oprId);
			tblMchntParticipatReview.setMisc1(CommonFunction.getCurrentDate());
			
			TblMchntParticipat tblMchntParticipat = new TblMchntParticipat();
			BeanUtils.copyProperties(tblMchntParticipatReview, tblMchntParticipat,new String[]{"id"});
			tblMchntParticipat.setId(new TblMchntParticipatPK(actNo[i],mchntNo[i]));
			
			if(tblMchntParticipatReview.getFlag01()!=null && 
					tblMchntParticipatReview.getFlag01().equals(MarketActConstants.OPR_DEL))
			{
				tblMchntParticipatReviewDAO.delete(tblMchntParticipatReview);
				tblMchntParticipatDAO.delete(tblMchntParticipat);
			}
			else
			{
				tblMchntParticipatReview.setFlag01(null);
				tblMchntParticipatReviewDAO.update(tblMchntParticipatReview);
				tblMchntParticipatDAO.saveOrUpdate(tblMchntParticipat);
			}
		}
		return Constants.SUCCESS_CODE;
	}
	
}
