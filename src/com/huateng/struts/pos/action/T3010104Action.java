package com.huateng.struts.pos.action;

import com.huateng.bo.term.TermService;
import com.huateng.common.Constants;
import com.huateng.dao.common.HqlDao;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermInfTmpPK;
import com.huateng.struts.pos.TblTermInfConstants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;

/**
 * project JSBConsole date 2013-3-12
 * 
 * @author 樊东东
 */
public class T3010104Action extends BaseAction {

	/** 冻结终端 置为冻结待审核 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		if ("stop".equals(method)) {
			return stop();
		}
		if ("close".equals(method)) {
			return close();
		}
		 if ("delete".equals(method)) {
		 return delete();
		 }
		if ("recover".equals(method)) {
			return recover();
		}
		if ("revive".equals(method)) {
			return revive();
		}
		return "无效请求";

	}

	/**
	 * @return
	 */
	private String recover() {
		// TODO Auto-generated method stub

		if (!TblTermInfConstants.TERM_ST_STOP.equals(termSta)
				&& !TblTermInfConstants.TERM_ST_ADD_BACK.equals(termSta)) {
			return "不是可解冻状态";
		}
		TblTermInfTmpPK id = new TblTermInfTmpPK(CommonFunction.fillString(
				termId, ' ', 12, true), CommonFunction.fillString(mchtCd, ' ',
				15, true));
		TblTermInfTmp tmp = (TblTermInfTmp) dao.get(TblTermInfTmp.class, id);
		if (tmp == null) {
			return "不存在指定终端,请重新选择";
		}
		tmp.setRecUpdOpr(this.operator.getOprId());
		tmp.setRecUpdTs(CommonFunction.getCurrentDate());
		tmp.setTermSta(TblTermInfConstants.TERM_ST_RECOVER_UNCHECK);
		dao.update(tmp);

		return Constants.SUCCESS_CODE;

	}

	/**
	 * @return注销恢复
	 */
	private String revive() {

		if (!TblTermInfConstants.TERM_ST_CLOSE.equals(termSta)) {
			return "不是可恢复状态";
		}
		TblTermInfTmpPK id = new TblTermInfTmpPK(CommonFunction.fillString(
				termId, ' ', 12, true), CommonFunction.fillString(mchtCd, ' ',
				15, true));
		TblTermInfTmp tmp = (TblTermInfTmp) dao.get(TblTermInfTmp.class, id);
		if (tmp == null) {
			return "不存在指定终端,请重新选择";
		}
		tmp.setRecUpdOpr(this.operator.getOprId());
		tmp.setRecUpdTs(CommonFunction.getCurrentDate());
		tmp.setTermSta(TblTermInfConstants.TERM_ST_REVIVE);
		dao.update(tmp);

		return Constants.SUCCESS_CODE;

	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	private String delete() throws Exception {

		if (!TblTermInfConstants.TERM_ST_INIT.equals(termSta)
				&& !TblTermInfConstants.TERM_ST_ADD_BACK.equals(termSta)) {
			return "不是可删除状态";
		}
		TblTermInfTmpPK id = new TblTermInfTmpPK(CommonFunction.fillString(
				termId, ' ', 12, true), CommonFunction.fillString(mchtCd, ' ',
				15, true));
		TblTermInfTmp tmp = (TblTermInfTmp) dao.get(TblTermInfTmp.class, id);
		if (tmp == null) {
			return "不存在指定终端,请重新选择";
		}
		service.delte(id);

		return Constants.SUCCESS_CODE;

	}

	/**
	 * @return
	 * @throws Exception
	 */
	private String close() throws Exception {
		// TODO Auto-generated method stub

		if (!TblTermInfConstants.TERM_ST_INST_WAIT.equals(termSta)
				&& !TblTermInfConstants.TERM_ST_INST.equals(termSta)
				&& !TblTermInfConstants.TERM_ST_STOP.equals(termSta)) {
			return "不是可注销状态";
		}
		TblTermInfTmpPK id = new TblTermInfTmpPK(CommonFunction.fillString(
				termId, ' ', 12, true), CommonFunction.fillString(mchtCd, ' ',
				15, true));
		TblTermInfTmp tmp = (TblTermInfTmp) dao.get(TblTermInfTmp.class, id);
		if (tmp == null) {
			return "不存在指定终端,请重新选择";
		}
		tmp.setTermSta(TblTermInfConstants.TERM_ST_CLOSE_UNCHCK);
		tmp.setRecUpdOpr(this.operator.getOprId());
		tmp.setRecUpdTs(CommonFunction.getCurrentDate());
		dao.update(tmp);
		return Constants.SUCCESS_CODE;

	}

	/**
	 * @return
	 * @throws Exception
	 */
	private String stop() throws Exception {
		// TODO Auto-generated method stub

		if (!"2".equals(termSta)) {
			return "不是可冻结状态";
		}
		TblTermInfTmpPK id = new TblTermInfTmpPK(CommonFunction.fillString(
				termId, ' ', 12, true), CommonFunction.fillString(mchtCd, ' ',
				15, true));
		TblTermInfTmp tmp = (TblTermInfTmp) dao.get(TblTermInfTmp.class, id);
		if (tmp == null) {
			return "不存在指定终端,请重新选择";
		}
		tmp.setTermSta(TblTermInfConstants.TERM_ST_STOP_UNCHCK);
		tmp.setRecUpdOpr(this.operator.getOprId());
		tmp.setRecUpdTs(CommonFunction.getCurrentDate());
		dao.update(tmp);
		return Constants.SUCCESS_CODE;

	}

	private TermService service;// 事务的话放到service里面执行

	private String method;

	private String termId;

	private String mchtCd;

	private String termSta;

	public TermService getService() {
		return service;
	}

	public void setService(TermService service) {
		this.service = service;
	}

	public String getTermSta() {
		return termSta;
	}

	public void setTermSta(String termSta) {
		this.termSta = termSta;
	}

	private HqlDao dao;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public HqlDao getDao() {
		return dao;
	}

	public void setDao(HqlDao dao) {
		this.dao = dao;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getMchtCd() {
		return mchtCd;
	}

	public void setMchtCd(String mchtCd) {
		this.mchtCd = mchtCd;
	}

}
