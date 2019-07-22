package com.huateng.bo.impl.mchnt;

import com.huateng.bo.mchnt.T21200BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.dao.iface.mchnt.TblMchtSignAccInfDAO;
import com.huateng.po.mchnt.TblMchtSignAccInf;
import com.huateng.po.mchnt.TblMchtSignAccInfPK;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.JSONBean;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * 
 * 商户签约实现<br>
 * 〈功能详细描述〉 〈方法简述 - 方法描述〉
 * 
 * @author Administrator
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class T21200BOTarget implements T21200BO {

    private static Logger logger = Logger.getLogger(T21200BOTarget.class);
    private TblMchtSignAccInfDAO tblMchtSignAccInfTmpdao;

    public TblMchtSignAccInfDAO getTblMchtSignAccInfTmpdao() {
        return tblMchtSignAccInfTmpdao;
    }

    public void setTblMchtSignAccInfTmpdao(TblMchtSignAccInfDAO tblMchtSignAccInfTmpdao) {
        this.tblMchtSignAccInfTmpdao = tblMchtSignAccInfTmpdao;
    }

    public String add(TblMchtSignAccInf cstSysParam) {
        // TODO Auto-generated method stub
        tblMchtSignAccInfTmpdao.save(cstSysParam);
        return Constants.SUCCESS_CODE;
    }

    /**
     * 删除
     */
    public String delete(String mchntId, String signAcct) {
        List list = CommonFunction.getCommQueryDAO().findBySQLQuery(
                "select MCHT_NO from TBL_MCHT_SIGN_ACC_INF where MCHT_NO='" + mchntId + "' and SIGN_ACCT='" + signAcct
                        + "' ");
        if (list == null || list.isEmpty()) {
            return "您所要删除的参数信息已经不存在";
        } else {
            CommonFunction.getCommQueryDAO().excute(
                    "delete from TBL_MCHT_SIGN_ACC_INF where MCHT_NO='" + mchntId + "' and SIGN_ACCT='" + signAcct
                            + "' ");
            return Constants.SUCCESS_CODE;
        }

    }

    public TblMchtSignAccInf get(TblMchtSignAccInfPK id) {
        return tblMchtSignAccInfTmpdao.get(id);
    }

    public String update(JSONBean jsonBean, Operator operator) {
        int len = jsonBean.getArray().size();
        if (len > 0) {
            logger.info("开始修改商户签约账户信息...");
            for (int i = 0; i < len; i++) {
                // jsonBean.setObject(jsonBean.getJSONDataAt(i));
                String mchntId = jsonBean.getJSONDataAt(i).getString("mchntId");
                String signAcct = jsonBean.getJSONDataAt(i).getString("signAcct");
                String rcvBankNo = jsonBean.getJSONDataAt(i).getString("rcvBankNo");
                String signStatus = jsonBean.getJSONDataAt(i).getString("signStatus");
                String rcvBankId = jsonBean.getJSONDataAt(i).getString("rcvBankId");

                StringBuffer SQL = new StringBuffer();
                SQL.append("update TBL_MCHT_SIGN_ACC_INF set ");
                if (!StringUtil.isNull(rcvBankNo)) {
                    SQL.append("RCV_BANK_NO='" + rcvBankNo + "',");
                }
                if (!StringUtil.isNull(rcvBankId)) {
                    SQL.append("RCV_BANK_ID='" + rcvBankId + "',");
                }
                if (!StringUtil.isNull(signStatus)) {
                    SQL.append("SIGN_STATUS='" + signStatus + "',");
                }
                SQL.append("UPD_OPR_ID='" + operator.getOprId() + "'");
                SQL.append(",REC_UPD_TS='" + CommonFunction.getCurrentDateTime() + "'");
                SQL.append(" ,BANK_NO ='" + operator.getOprBrhId() + "'");
                SQL.append(" ,BANK_NAME='" + operator.getOprBrhName() + "'");
                SQL.append(" where MCHT_NO='" + mchntId + "' ");
                SQL.append(" and SIGN_ACCT='" + signAcct + "' ");
                logger.info(SQL.toString());
                CommonFunction.getCommQueryDAO().excute(SQL.toString());
            }
            logger.info("修改商户签约账户信息完成...");
        } else {
            return "修改数据不存在";
        }

        return Constants.SUCCESS_CODE;
    }

	public int countNum(TblMchtSignAccInfPK id) {
		String countSql="select count(*) from TBL_MCHT_SIGN_ACC_INF where MCHT_NO='" + id.getMchntId().trim() + "' and SIGN_ACCT='" + id.getSignAcct().trim()+"'";
		int countNum=Integer.parseInt(CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql));
		return countNum;
	}
    
}
