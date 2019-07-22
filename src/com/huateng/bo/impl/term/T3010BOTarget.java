package com.huateng.bo.impl.term;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.huateng.bo.term.T3010BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.term.TblTermInfDAO;
import com.huateng.dao.iface.term.TblTermInfTmpDAO;
import com.huateng.dao.iface.term.TblTermRefuseDAO;
import com.huateng.dao.iface.term.TblTermTmkLogDAO;
import com.huateng.po.TblTermInf;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermInfTmpPK;
import com.huateng.po.TblTermRefuse;
import com.huateng.po.TblTermRefusePK;
import com.huateng.po.TblTermTmkLog;
import com.huateng.po.TblTermTmkLogPK;
import com.huateng.struts.pos.TblTermTmkLogConstants;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.StatusUtil;

/**
 * Title:终端信息管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-16
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class T3010BOTarget implements T3010BO {

    private TblTermInfTmpDAO tblTermInfTmpDAO;

    private ICommQueryDAO commQueryDAO;

    private TblTermInfDAO tblTermInfDAO;

    private TblTermTmkLogDAO tblTermTmkLogDAO;

    private TblTermRefuseDAO tblTermRefuseDAO;

    /**
     * @param tblTermRefuseDAO
     *            the tblTermRefuseDAO to set
     */
    public void setTblTermRefuseDAO(TblTermRefuseDAO tblTermRefuseDAO) {
        this.tblTermRefuseDAO = tblTermRefuseDAO;
    }

    /**
     * @param tblTermTmkLogDAO
     *            the tblTermTmkLogDAO to set
     */
    public void setTblTermTmkLogDAO(TblTermTmkLogDAO tblTermTmkLogDAO) {
        this.tblTermTmkLogDAO = tblTermTmkLogDAO;
    }

    /**
     * @param commQueryDAO
     *            the commQueryDAO to set
     */
    public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
        this.commQueryDAO = commQueryDAO;
    }

    /**
     * @param tblTermInfDAO
     *            the tblTermInfDAO to set
     */
    public void setTblTermInfDAO(TblTermInfDAO tblTermInfDAO) {
        this.tblTermInfDAO = tblTermInfDAO;
    }

    /**
     * @param tblTermInfTmpDAO
     *            the tblTermInfTmpDAO to set
     */
    public void setTblTermInfTmpDAO(TblTermInfTmpDAO tblTermInfTmpDAO) {
        this.tblTermInfTmpDAO = tblTermInfTmpDAO;
    }

    @Override
    public String add(TblTermInfTmp tblTermInfTmp) {
        return tblTermInfTmpDAO.save(tblTermInfTmp).getTermId();
    }

    @Override
    public TblTermInfTmp get(String termId, String mchtCd) {
        TblTermInfTmpPK pk = new TblTermInfTmpPK(termId, mchtCd);
        return tblTermInfTmpDAO.get(pk);
    }

    @Override
    public boolean update(TblTermInfTmp tblTermInfTmp, String termSta) {
        if (termSta != null) tblTermInfTmp.setTermSta(termSta);
        tblTermInfTmpDAO.update(tblTermInfTmp);
        return true;
    }

    @Override
    public String getTermId(String brhId) {
        if (StringUtils.isEmpty(brhId)) return Constants.FAILURE_CODE;
        if (brhId.length() > 4) brhId = brhId.substring(0, 4);// 终端号在机构号后面递增
        String sql = "select max(substr(term_Id,5,4)) from TBL_TERM_INF_TMP where substr(term_id,0,4)='" + brhId + "'";
        List list = commQueryDAO.findBySQLQuery(sql);
        if (list == null || list.isEmpty() || list.get(0) == null) return brhId + CommonFunction.fillString("1", '0', 4, false);
        int max = Integer.parseInt(list.get(0).toString()) + 1;
        if (max > 9999) max = 1;
        return brhId + CommonFunction.fillString(String.valueOf(max), '0', 4, false);
    }

    @Override
    public boolean save(TblTermInfTmp tblTermInfTmp, String oprId, String termBatchNm, String termSignSta) {
        TblTermInf tblTermInf = new TblTermInf();
        if (termBatchNm != null) {
            tblTermInfTmp.setTermBatchNm(termBatchNm);
            String param = tblTermInfTmp.getTermPara();
            String[] array = param.split("\\|");
            array[8] = "31" + termBatchNm;
            param = "";
            for (String tmp : array) {
                param += tmp + "|";
            }
            tblTermInfTmp.setTermPara(param);
        }
        tblTermInf = (TblTermInf) tblTermInfTmp.clone();
        if (termSignSta != null) tblTermInf.setTermSignSta(termSignSta);
        tblTermInf.setTermPara(tblTermInfTmp.getTermPara().replaceAll("\\|", ""));
        tblTermInf.setRecUpdOpr(oprId);
        tblTermInf.setRecUpdTs(CommonFunction.getCurrentDate());
        tblTermInfDAO.saveOrUpdate(tblTermInf);
        return true;
    }

    @Override
    public List qryTermInfo(String termNo, String state, Operator operator, String mchtCd, String termBranch, String startDate, String endDate) {
        StringBuffer whereSql = new StringBuffer(" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where t3.state ='0')")
                .append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId());
        if (mchtCd != null && !mchtCd.trim().equals("")) {
            whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
        }
        if (termBranch != null && !termBranch.trim().equals("")) {
            whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch).append("'");
        }
        if (termNo != null && !termNo.trim().equals("")) {
            whereSql.append(" AND t1.TERM_ID='").append(CommonFunction.fillString(termNo, ' ', 12, true)).append("'");
        }
        if (state != null && !state.trim().equals("")) {
            if (state.equals("1"))
                whereSql.append(" AND t2.STATE='1'");
            else
                whereSql.append(" AND t2.STATE is null");
        }
        if (startDate != null && !startDate.trim().equals("")) {
            whereSql.append(" AND t1.REC_CRT_TS>").append(startDate);
        }
        if (endDate != null && !endDate.trim().equals("")) {
            whereSql.append(" AND t1.REC_CRT_TS<").append(endDate);
        }

        String sql = "SELECT t1.term_id,t1.mcht_cd,t1.term_branch FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0'"
                + whereSql.toString() + " ORDER BY t1.term_id";

        List list = commQueryDAO.findBySQLQuery(sql);
        return list;
    }

    @Override
    public boolean initTmkLog(TblTermTmkLog tblTermTmkLog) {
        tblTermTmkLogDAO.save(tblTermTmkLog);
        return true;
    }

    @Override
    public String getBatchNo() {
        String sql = "select max(substr(BATCH_NO,9,12)) from TBL_TERM_TMK_LOG where substr(BATCH_NO,0,8)='" + CommonFunction.getCurrentDate() + "'";
        List list = commQueryDAO.findBySQLQuery(sql);
        if (list == null || list.isEmpty() || list.get(0) == null)
            return CommonFunction.getCurrentDate() + CommonFunction.fillString("1", '0', 4, false);
        int max = Integer.parseInt(list.get(0).toString()) + 1;
        if (max > 999999) max = 1;
        return CommonFunction.getCurrentDate() + CommonFunction.fillString(String.valueOf(max), '0', 4, false);
    }

    @Override
    public String chkTmkLog(String termIdId, String batchNo, String oprId) {
        TblTermTmkLogPK pk = new TblTermTmkLogPK(termIdId, batchNo);
        TblTermTmkLog tblTermTmkLog = tblTermTmkLogDAO.get(pk);
        if (tblTermTmkLog == null) return TblTermTmkLogConstants.T30203_01;
        // if(tblTermTmkLog.getReqOpr().equals(oprId.trim()))
        // //这个地方、可以相同的人审核
        // return TblTermTmkLogConstants.T30203_02;
        if (tblTermTmkLog.getState().equals(TblTermTmkLogConstants.STATE_RUN)) return TblTermTmkLogConstants.T30203_04;
        tblTermTmkLog.setChkOpr(oprId);
        tblTermTmkLog.setChkDate(CommonFunction.getCurrentDate());
        tblTermTmkLog.setState(TblTermTmkLogConstants.STATE_RUN);
        tblTermTmkLog.setRecUpdOpr(oprId);
        tblTermTmkLog.setRecUpdTs(CommonFunction.getCurrentDateTime());
        tblTermTmkLogDAO.update(tblTermTmkLog);
        return Constants.SUCCESS_CODE;
    }

    @Override
    public String chkTmkLogNew(String[] termId, String[] batchNo, String oprId) {
        List<TblTermTmkLog> list = new ArrayList<TblTermTmkLog>();
        for (int i = 0; i < batchNo.length && i < termId.length; i++) {
            TblTermTmkLogPK pk = new TblTermTmkLogPK(termId[i], batchNo[i]);
            TblTermTmkLog tblTermTmkLog = tblTermTmkLogDAO.get(pk);
            tblTermTmkLog.setChkOpr(oprId);
            tblTermTmkLog.setChkDate(CommonFunction.getCurrentDate());
            tblTermTmkLog.setState(TblTermTmkLogConstants.STATE_RUN);
            tblTermTmkLog.setRecUpdOpr(oprId);
            tblTermTmkLog.setRecUpdTs(CommonFunction.getCurrentDateTime());
            list.add(tblTermTmkLog);
        }
        tblTermTmkLogDAO.batchCheck(list);
        return Constants.SUCCESS_CODE;
    }

    @Override
    public String delTmkLogNew(String[] termIdId, String[] batchNo, String oprId) {
        List<TblTermTmkLog> list = new ArrayList<TblTermTmkLog>();
        for (int i = 0; i < batchNo.length && i < termIdId.length; i++) {
            TblTermTmkLogPK pk = new TblTermTmkLogPK(termIdId[i], batchNo[i]);
            TblTermTmkLog tblTermTmkLog = tblTermTmkLogDAO.get(pk);
            list.add(tblTermTmkLog);
        }
        tblTermTmkLogDAO.batchRefuse(list);
        return Constants.SUCCESS_CODE;
    }

    @Override
    public String chkTmkLog(String batchNo, String oprId) {
        // List<TblTermTmkLog> list =
        // commQueryDAO.findBySQLQuery("select req_opr from TBL_TERM_TMK_LOG t where t.batch_no='"+batchNo+"' and t.req_opr!='"+oprId+"'");
        // 这个地方、可以相同的人审核
        List<TblTermTmkLog> list = commQueryDAO.findBySQLQuery("select req_opr from TBL_TERM_TMK_LOG t where t.batch_no='" + batchNo + "' ");
        if (list == null || list.isEmpty()) return TblTermTmkLogConstants.T30203_02;
        StringBuffer sql = new StringBuffer("update tbl_term_tmk_log set state=").append(TblTermTmkLogConstants.STATE_RUN).append(",chk_opr = '")
                .append(oprId).append("',chk_date = ").append(CommonFunction.getCurrentDate()).append(",rec_upd_opr = '").append(oprId)
                .append("',rec_upd_ts = ").append(CommonFunction.getCurrentDateTime()).append(" where batch_no='").append(batchNo)
                .append("' and state='").append(TblTermTmkLogConstants.STATE_INIT).append("'");
        commQueryDAO.excute(sql.toString());
        return Constants.SUCCESS_CODE;
    }

    @Override
    public boolean initTmkLog(List<TblTermTmkLog> list) {
        if (list != null && !list.isEmpty()) {
            tblTermTmkLogDAO.batchSave(list);
            return true;
        }
        return false;
    }

    @Override
    public TblTermInf getTermInfo(String termId) {
        if (termId != null && !"".equals(termId)) return tblTermInfDAO.get(CommonFunction.fillString(termId, ' ', 12, true));
        return null;
    }

    @Override
    public String delTmkLog(String termIdId, String batchNo, String oprId) {
        TblTermTmkLog tblTermTmkLog = tblTermTmkLogDAO.get(new TblTermTmkLogPK(termIdId, batchNo));
        // if(tblTermTmkLog.getReqOpr().equals(oprId))
        // {
        // return TblTermTmkLogConstants.T30203_05;
        // }
        // 可以同一个人审核
        tblTermTmkLogDAO.delete(tblTermTmkLog);
        return Constants.SUCCESS_CODE;
    }

    @Override
    public String refuseLog(String termId, String refuseInfo, String refuseType) {
        TblTermRefusePK tblTermRefusePK = new TblTermRefusePK(CommonFunction.getCurrentDateTime(), termId);
        TblTermRefuse tblTermRefuse = new TblTermRefuse();
        Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
        tblTermRefuse.setId(tblTermRefusePK);
        tblTermRefuse.setBrhId(opr.getOprBrhId());
        tblTermRefuse.setOprId(opr.getOprId());
        tblTermRefuse.setRefuseInfo(refuseInfo);
        tblTermRefuse.setRefuseType(StatusUtil.getNextStatus("TM." + refuseType));
        tblTermRefuseDAO.save(tblTermRefuse);
        return Constants.SUCCESS_CODE;
    }

    @Override
    public boolean chkTmkLog(String termId) {
        StringBuffer hql = new StringBuffer("from TblTermTmkLog a").append(" where a.State='").append(TblTermTmkLogConstants.STATE_INIT).append("'")
                .append(" and a.Id.TermIdId='").append(termId).append("'");
        List list = tblTermTmkLogDAO.findByHQLQuery(hql.toString());
        if (list != null && !list.isEmpty()) return false;
        return true;
    }

    @Override
    public String refuse(TblTermInfTmpPK pk, String state) {
        // if(state.equals(TblTermInfConstants.TERM_STA_ADD_REFUSE))
        // {
        // tblTermInfTmpDAO.delete(pk);
        // return Constants.SUCCESS_CODE;
        // }
        // if(state.equals(TblTermInfConstants.TERM_STA_MOD_REFUSE))
        // {
        // TblTermInf tblTermInf = tblTermInfDAO.get(pk.getTermId());
        // TblTermInfTmp tblTermInfTmp = tblTermInfTmpDAO.get(pk);
        //			
        // String param = tblTermInf.getTermPara();
        // String param1 = tblTermInfTmp.getTermPara();
        //			
        // tblTermInfTmp = (TblTermInfTmp)tblTermInf.clone(tblTermInfTmp);
        //			
        // String[] array = param1.split("\\|");
        // StringBuffer result = new StringBuffer("");
        // int current = 0;
        // for(int i=0;i<array.length;i++)
        // {
        // result.append(param.substring(current,
        // current+array[i].length())).append("|");
        // current += array[i].length();
        // }
        // tblTermInfTmp.setTermPara(result.toString());
        // tblTermInfTmpDAO.update(tblTermInfTmp);
        // return Constants.SUCCESS_CODE;
        // }
        return Constants.FAILURE_CODE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.huateng.bo.term.T3010BO#qryTermInfo(java.lang.String,
     * java.lang.String, java.lang.String, com.huateng.common.Operator)
     */
    @Override
    public List qryTermInfo(String termNo, String termBranch, String propInsNm, Operator operator) {
        StringBuffer whereSql = new StringBuffer(
                " WHERE term_sta = '1' and trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where t3.state ='0')")
                        .append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId());

        if (termBranch != null && !termBranch.trim().equals("")) {
            whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch).append("'");
        }
        if (termNo != null && !termNo.trim().equals("")) {
            whereSql.append(" AND t1.TERM_ID='").append(CommonFunction.fillString(termNo, ' ', 12, true)).append("'");
        }
        if (StringUtils.isNotEmpty(propInsNm)) {
            whereSql.append(" and t1.prop_ins_nm='" + propInsNm + "'");
        }

        //		String sql = "SELECT t1.term_id,t1.mcht_cd,t1.term_branch FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0'"
        //				+ whereSql.toString() + " ORDER BY t1.term_id";
        //从终端正是表里查
        String sql = "SELECT t1.term_id,t1.mcht_cd,t1.term_branch FROM tbl_term_inf t1" + whereSql.toString() + " ORDER BY t1.term_id";
        List list = commQueryDAO.findBySQLQuery(sql);
        return list;
    }

    @Override
    public List qryTermInfosByOne(String mappingTermidOne, String mappingMchntcdTwo) {
        String sql = "SELECT t.mcht_cd,t.MAPPING_MCHNTCDONE,t.MAPPING_TERMIDONE FROM tbl_term_inf_tmp t where 1=1 ";

        if (StringUtils.isNotBlank(mappingTermidOne)) {
            sql += " and t.MAPPING_TERMIDONE ='" + mappingTermidOne + "'";
            sql += " and t.MAPPING_MCHNTCDONE ='" + mappingMchntcdTwo + "'";
            return commQueryDAO.findBySQLQuery(sql);
        }

        return null;
    }

    @Override
    public List queryMchntInfo(String mcht_no) {
        String sql = "select mcht_status from TBL_MCHT_BASE_INF_TMP where mcht_no='" + mcht_no + "'";
        return commQueryDAO.findBySQLQuery(sql);
    }

    @Override
    public List getTermCount() {
        String sql = "SELECT count(1) FROM tbl_term_inf_tmp";
        return commQueryDAO.findBySQLQuery(sql);
    }

    @Override
    public List qryTermInfosByTwo(String mappingTermidTwo, String mappingMchntcdTwo) {
        String sql = "SELECT t.mcht_cd,t.MAPPING_MCHNTCDTWO,t.MAPPING_TERMIDTWO FROM tbl_term_inf_tmp t where 1=1 ";

        if (StringUtils.isNotBlank(mappingTermidTwo)) {
            sql += " and t.MAPPING_TERMIDTWO ='" + mappingTermidTwo + "'";
            sql += " and t.MAPPING_MCHNTCDTWO ='" + mappingMchntcdTwo + "'";
            return commQueryDAO.findBySQLQuery(sql);
        }

        return null;
    }

}
