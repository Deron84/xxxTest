package com.huateng.dwr.query;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.xwork.StringUtils;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.dao.common.SqlDao;
import com.huateng.system.util.ContextUtil;


/**
 * project JSBConsole
 * date 2013-3-23
 * @author 樊东东
 */
public class T50601 {
	public Map<String, String> getCount(String brhId,String thirdId,String connType,HttpServletRequest request){
		Map<String,String> map=new HashMap<String,String>();
		try{
			SqlDao dao = (SqlDao) ContextUtil.getBean("sqlDao");
			StringBuffer sql = new StringBuffer("select count(1) as count,nvl(sum(t1.mcht_at_c)-sum(t1.mcht_at_d),0) as countAmt," +
					"nvl(sum(t1.mcht_fee_c)-sum(t1.mcht_fee_d),0) as countFee" +
					" from TBL_ALGO_DTL t1 left join tbl_mcht_base_inf t2 on trim(t1.mcht_cd)=trim(t2.mcht_no) " +
					" left join tbl_term_inf t3 on trim(t1.mcht_cd)=trim(t3.mcht_cd) and trim(t1.term_id)=trim(t3.term_id) where 1=1 ");
			Operator opr = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
			sql.append(" and t2.sign_inst_id in "+opr.getBrhBelowId());
			if(StringUtils.isNotEmpty(brhId)){
				sql.append(" and t2.sign_inst_id='"+brhId+"'");
			}
			if(StringUtils.isNotEmpty(thirdId)){
				sql.append(" and t3.prop_ins_nm='"+thirdId+"'");
			}
			if(StringUtils.isNotEmpty(connType)){
				sql.append(" and t2.conn_type='"+connType+"'");
			}
			map = dao.queryforStrMap(sql.toString());
			return map;
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
}
