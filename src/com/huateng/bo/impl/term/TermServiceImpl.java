package com.huateng.bo.impl.term;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.BeanUtils;

import com.huateng.bo.term.TermService;
import com.huateng.common.Constants;
import com.huateng.dao.common.HqlDao;
import com.huateng.dao.common.SqlDao;
import com.huateng.po.TblTermInf;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermInfTmpPK;
import com.huateng.po.TblTermRefuse;
import com.huateng.po.TblTermRefusePK;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.struts.pos.TblTermInfConstants;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.GenerateNextId;

/**
 * project JSBConsole date 2013-3-13
 * 
 * @author 樊东东
 */
public class TermServiceImpl implements TermService {

	HqlDao dao;

	SqlDao sqlDao;

	public SqlDao getSqlDao() {
		return sqlDao;
	}

	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}

	public HqlDao getDao() {
		return dao;
	}

	public void setDao(HqlDao dao) {
		this.dao = dao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.bo.term.TermService#termAudit(java.util.Map)
	 */
	public String termAudit(Map<String, String> param) throws Exception {
		// TODO Auto-generated method stub
		String termId = param.get("termId");
		String mchtCd = param.get("mchtCd");
		String termSta = param.get("termSta");
		String refuseInfo = param.get("refuseInfo");
		String subTxnId = param.get("subTxnId");
		String oprId = param.get("oprId");
		String oprBrhId = param.get("oprBrhId");
		try {

			TblTermInfTmpPK id = new TblTermInfTmpPK(CommonFunction.fillString(
					termId, ' ', 12, true), CommonFunction.fillString(mchtCd,
					' ', 15, true));
			TblTermInfTmp tmp = (TblTermInfTmp) dao
					.get(TblTermInfTmp.class, id);
			TblTermInf inf = (TblTermInf) dao.get(TblTermInf.class, id);
			
			TblMchtBaseInf mchtInfo = (TblMchtBaseInf) dao.get(TblMchtBaseInf.class, mchtCd);
			TblMchtBaseInfTmp mchtTmp = (TblMchtBaseInfTmp) dao.get(TblMchtBaseInfTmp.class, mchtCd);
			
			String nowDate = CommonFunction.getCurrentDate();
			String nowDateTime = CommonFunction.getCurrentDateTime();
			
			
			//操作员审核判断再过一遍   终端的和商户的都要看
//			if(oprId.equals(StringUtils.trim(tmp.getRecUpdOpr()))){
//				return "同一操作员不能审核";
//			}
			if ("0".equals(subTxnId)) {// 通过
				if("3".equals(termSta)){//修改待审核
					if(inf == null){
						return "正式表找不到该终端信息！";
					}else{
						String stateInfo = inf.getTermSta();
						termSta = stateInfo;
					}
				}else if("0".equals(termSta)){//新增待审核
					
					if(mchtInfo == null){
						return "终端新增审核之前请先确保商户已审核通过！";
					}
					termSta = map.get(termSta);
					tmp.setRecCrtTs(nowDate);//记录新增入网日期
				}else{
					termSta = map.get(termSta);
				}
				

				tmp.setTermSta(termSta);
				tmp.setRecUpdTs(nowDate);
				tmp.setRecUpdOpr(oprId);
				if (inf == null) {
					inf = new TblTermInf();// 针对新增待审核 正式表没数据的情况
				}
				BeanUtils.copyProperties(tmp, inf);
				dao.update(tmp);
				dao.saveOrUpdate(inf);
			} else {// 拒绝或退回，需要往终端拒绝原因表插原因
				if(mchtTmp == null){
					return "找不到关联的商户信息！";
				}
				String mchtNm = mchtTmp.getMchtNm();
				
				TblTermRefuse refuse = new TblTermRefuse();
				TblTermRefusePK refuseId = new TblTermRefusePK(nowDateTime, termId,
						mchtNm);
				Object existRefuse = dao.get(TblTermRefuse.class, refuseId);
				if (existRefuse != null) {
					return "该终端当天已经退回或拒绝过一次，请于明天操作";
				}

				refuse.setId(refuseId);
				refuse.setOprId(oprId);
				refuse.setBrhId(tmp.getTermBranch());
				refuse.setRefuseInfo(refuseInfo);
				refuse.setRefuseType(this.UNKNOWN_REFUSE);
				if ("1".equals(subTxnId)) {// 拒绝

					if (TblTermInfConstants.TERM_ST_INIT.equals(termSta)) {// 如果是新增待审核、原纪录删除
						refuse.setRefuseType(this.ADD_REFUSE);
						dao.delete(tmp);
					} else {// 把正式表信息覆盖到临时表

						if (TblTermInfConstants.TERM_ST_UPD_UNCHECK
								.equals(termSta)) {
							refuse.setRefuseType(this.UPDATE_REFUSE);
						}
						if (TblTermInfConstants.TERM_ST_STOP_UNCHCK
								.equals(termSta)) {
							refuse.setRefuseType(this.STOP_REFUSE);
						}
						if (TblTermInfConstants.TERM_ST_RECOVER_UNCHECK
								.equals(termSta)) {
							refuse.setRefuseType(this.RECOVER_REFUSE);
						}
						if (TblTermInfConstants.TERM_ST_CLOSE_UNCHCK
								.equals(termSta)) {
							refuse.setRefuseType(this.CLOSE_REFUSE);
						}
						if (TblTermInfConstants.TERM_ST_REVIVE
								.equals(termSta)) {
							refuse.setRefuseType(this.REVIVE_REFUSE);
						}
						BeanUtils.copyProperties(inf, tmp);
						dao.update(tmp);
					}
				} else {// 退回
					if (TblTermInfConstants.TERM_ST_INIT.equals(termSta)) {// 新增退回
						refuse.setRefuseType(this.ADD_BACK);
						tmp.setTermSta(TblTermInfConstants.TERM_ST_ADD_BACK);
					}
				}

				dao.save(refuse);// 插入拒绝信息
			}
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	private static final HashMap<String, String> map = new HashMap<String, String>();
	static {
		/** 0：新增待审核 1：待装机 2 ：已装机 3：修改待审核4：冻结 5：冻结待审核 6:解冻待审核7：注销8：注销待审核 9：撤机 */
//		map.put("0", TblTermInfConstants.TERM_ST_INST_WAIT);
		//新增的，审核通过之后，直接设置为已装机
		map.put("0", TblTermInfConstants.TERM_ST_INST);
//		map.put("3", TblTermInfConstants.TERM_ST_INST_WAIT);
		map.put("5", TblTermInfConstants.TERM_ST_STOP);
		map.put("6", TblTermInfConstants.TERM_ST_INST);
		map.put("8", TblTermInfConstants.TERM_ST_CLOSE);
		map.put("R", TblTermInfConstants.TERM_ST_INST);
	}

	private static final String ADD_BACK = "新增退回";

	private static final String ADD_REFUSE = "新增拒绝";

	private static final String UPDATE_REFUSE = "修改拒绝";

	private static final String STOP_REFUSE = "冻结拒绝";

	private static final String CLOSE_REFUSE = "注销拒绝";

	private static final String RECOVER_REFUSE = "解冻拒绝";
	
	private static final String REVIVE_REFUSE = "注销恢复拒绝";

	private static final String UNKNOWN_REFUSE = "未知类型";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.bo.term.TermService#delte(com.huateng.po.TblTermInfTmpPK)
	 */
	public void delte(TblTermInfTmpPK id) {
		// TODO Auto-generated method stub
		TblTermInf inf = (TblTermInf) dao.get(TblTermInf.class, id);
		if (inf != null) {
			dao.delete(inf);
		}
		dao.delete(dao.get(TblTermInfTmp.class, id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.bo.term.TermService#batchSave(java.lang.String)
	 */
	public String batchSave(String termIdSave, String mchtCdSave, String count) {
		// TODO Auto-generated method stub
		TblTermInfTmpPK id = new TblTermInfTmpPK(termIdSave, mchtCdSave);
		TblTermInfTmp tmp = (TblTermInfTmp) dao.get(TblTermInfTmp.class, id);
		tmp.setTermSta(TblTermInfConstants.TERM_ST_INIT);
		String brhId = tmp.getTermBranch();
		int c = Integer.parseInt(count);
		// long termId = Long.parseLong(GenerateNextId.getTermId(brhId));

		for (int i = 1; i <= c; i++) {
			dao.beginTransaction();
			long termId = Long.parseLong(GenerateNextId.getTermId(brhId));
//			System.out.println(termId);
			id.setTermId("" + termId);
			TblTermInfTmp ntmp = new TblTermInfTmp();
			BeanUtils.copyProperties(tmp, ntmp);// 不是新的对象hibernate不会插入、它会update、下面膜还要把缓存清了
			dao.save(ntmp);
			// dao.flush();
			// dao.clear();
			dao.commit();
		}

		return Constants.SUCCESS_CODE;
	}

}
