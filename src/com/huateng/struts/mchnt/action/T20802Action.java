package com.huateng.struts.mchnt.action;

import com.huateng.bo.mchnt.T20801BO;
import com.huateng.bo.mchnt.T20802BO;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.dao.common.SqlDao;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;

/**
 * project JSBConsole date 2013-3-18
 * 
 * @author 樊东东
 */
public class T20802Action extends BaseAction {
	private T20802BO t20802BO = (T20802BO) ContextUtil.getBean("T20802BO");
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if ("accept".equals(method)) {
			return accept();
		}
		if ("refuse".equals(method)) {
			return refuse();
		} else {
			return "无效请求";
		}

	}

	/**
	 * @return
	 * @throws Exception 
	 */
	private String refuse() throws Exception {
		// TODO Auto-generated method stub
		try {
//			if(mchtArray!=null&&!"".equals(mchtArray)){
//				for(int i=0;i<mchtArray.length;i++){
//					String sql = "update tbl_mcht_base_inf set plan_check_date='' where mcht_no='"
//						+ mchtArray[i].trim() + "'";
//					sqlDao.execute(sql);
//				}
//			}else{
//				return ErrorCode.T20801_03;
//			}
//			String sql = "update tbl_mcht_base_inf set plan_check_date='' where mcht_no='"
//					+ mchtNo + "'";
//			System.out.println(sql);
//			sqlDao.execute(sql);
			t20802BO.checkMchtsRefuse(mchtArray);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId() + "，对商户巡检信息的审核拒绝操作"
					+ getMethod() + "失败，失败原因为：" + e.getMessage());
			throw e;
		}
	}

	/**
	 * @return
	 * @throws Exception 
	 */
	private String accept() throws Exception {
		// TODO Auto-generated method stub
		try {
			t20802BO.checkMchtAccept(mchtArray, planCheckDateArray);
//			String sql = "update tbl_mcht_base_inf set rct_check_date='"
//					+ planCheckDate + "',plan_check_date='' where mcht_no='" + mchtNo + "'";
//			sqlDao.execute(sql);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId() + "，对商户巡检信息的审核通过操作"
					+ getMethod() + "失败，失败原因为：" + e.getMessage());
			throw e;
		}
	}

	private String mchtNo;

	private String planCheckDate;

	private String[] planCheckDateArray;
	
	private String[] mchtArray;
	
	private SqlDao sqlDao = (SqlDao) ContextUtil.getBean("sqlDao");

	public String getPlanCheckDate() {
		return planCheckDate;
	}

	public void setPlanCheckDate(String planCheckDate) {
		this.planCheckDate = planCheckDate;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String[] getPlanCheckDateArray() {
		return planCheckDateArray;
	}

	public void setPlanCheckDateArray(String[] planCheckDateArray) {
		this.planCheckDateArray = planCheckDateArray;
	}

	public String[] getMchtArray() {
		return mchtArray;
	}

	public void setMchtArray(String[] mchtArray) {
		this.mchtArray = mchtArray;
	}

	
}
