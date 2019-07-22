package com.huateng.struts.pos.action;

import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.term.T3010BO;
import com.huateng.common.Constants;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermTmkLog;
import com.huateng.po.TblTermTmkLogPK;
import com.huateng.struts.pos.TblTermInfConstants;
import com.huateng.struts.pos.TblTermTmkLogConstants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.SysCodeUtil;

public class T30202Action extends BaseAction {

	private static final long serialVersionUID = 4822110756831919797L;

	private String termId;

	private String termNo;

	private String mchntNo;

	private String propInsNm;

	private String termBranch;

	private T3010BO t3010BO;
/**密钥申请*/
	@Override
	protected String subExecute() throws Exception {
		String subTxnId = getSubTxnId();
		String batchNo = t3010BO.getBatchNo();

		if (subTxnId.equals("02"))// 批量
		{
			List list = t3010BO.qryTermInfo(termNo, termBranch, propInsNm,
					operator);
			if (list == null || list.isEmpty())
				return TblTermTmkLogConstants.T30202_03;
			List<TblTermTmkLog> result = new ArrayList<TblTermTmkLog>();
			String tmpTermId = null;
			int count = 0;
			for (int i = 0; i < list.size(); i++) {
				Object[] object = (Object[]) list.get(i);
				if (!object[0].toString().trim().equals(tmpTermId)) {
					TblTermTmkLog tmp = new TblTermTmkLog();
					tmp.setId(new TblTermTmkLogPK(object[0].toString().trim(),
							batchNo));
					tmp.setMchnNo(object[1].toString());
					tmp.setTermBranch(object[2].toString());
					tmp.setState(TblTermTmkLogConstants.STATE_INIT);
					tmp.setReqOpr(operator.getOprId());
					tmp.setReqDate(CommonFunction.getCurrentDate());
					tmp.setRecUpdOpr(operator.getOprId());
					tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
					result.add(tmp);
					count++;
					tmpTermId = object[0].toString().trim();
				}
				if (count > 200)
					break;
			}
			if (t3010BO.initTmkLog(result)) {
				if (count <= 200)
					return Constants.SUCCESS_CODE_CUSTOMIZE + "申请批次号："
							+ batchNo;
				else
					return Constants.SUCCESS_CODE_CUSTOMIZE
							+ "申请批次号："
							+ batchNo
							+ "  "
							+ SysCodeUtil
									.getErrCode(TblTermTmkLogConstants.T30202_04);
			}
		}
		if (subTxnId.equals("01")) {

			TblTermTmkLog tblTermTmkLog = new TblTermTmkLog();
			tblTermTmkLog.setId(new TblTermTmkLogPK(termId, batchNo));
			tblTermTmkLog.setMchnNo(mchntNo);
			tblTermTmkLog.setTermBranch(termBranch.substring(0, termBranch.lastIndexOf("-")));
			tblTermTmkLog.setState(TblTermTmkLogConstants.STATE_INIT);
			tblTermTmkLog.setReqOpr(operator.getOprId());
			tblTermTmkLog.setReqDate(CommonFunction.getCurrentDate());
			tblTermTmkLog.setRecUpdOpr(operator.getOprId());
			tblTermTmkLog.setRecUpdTs(CommonFunction.getCurrentDateTime());
			t3010BO.initTmkLog(tblTermTmkLog);
			return Constants.SUCCESS_CODE_CUSTOMIZE + "申请批次号：" + batchNo;

		}
		return Constants.FAILURE_CODE;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getPropInsNm() {
		return propInsNm;
	}

	public void setPropInsNm(String propInsNm) {
		this.propInsNm = propInsNm;
	}

	public String getTermBranch() {
		return termBranch;
	}

	public void setTermBranch(String termBranch) {
		this.termBranch = termBranch;
	}

	public T3010BO getT3010BO() {
		return t3010BO;
	}

	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}

	public String getMchntNo() {
		return mchntNo;
	}

	public void setMchntNo(String mchntNo) {
		this.mchntNo = mchntNo;
	}

}
