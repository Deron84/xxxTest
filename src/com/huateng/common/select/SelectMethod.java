package com.huateng.common.select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.Var;

import com.huateng.bo.mchnt.T21402BO;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.common.TxnInfo;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.po.TblTermManagement;
import com.huateng.po.mchnt.TblMchntDiscountRule;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.informix.util.stringUtil;

/**
 * 
 * Title: SelectOption接口方法
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2009-12-27
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class SelectMethod {

    static ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
    /**
     * 获得所有仓库下拉列表用
     * @Description: TODO
     * @param @param params
     * @param @return   
     * @return LinkedHashMap<String,String>  
     * @throws
     * @author liujihui
     * @date 2019年5月3日
     */
    public static LinkedHashMap<String, String> getAllWhse(Object[] params) {
    	Operator operator = (Operator) params[0];
    	LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
    	if (operator == null) {
    		return dataMap;
    	}
    	List<Object[]> dataList = new ArrayList<Object[]>();
    	String sql = "SELECT WHSE_CODE||'-'||WHSE_NAME as WHSE_NAME,WHSE_CODE FROM RAIL_WHSE_INFO WHERE 1=1 AND DEL_STATUS=0 AND ENABLE_STATUS=0 ";
    	//sql += " and AGR_BR in " + operator.getBrhBelowId();
    	dataList = commQueryDAO.findBySQLQuery(sql);
    	Iterator<Object[]> iterator2 = dataList.iterator();
    	while (iterator2.hasNext()) {
    		Object[] obj = iterator2.next();
    		dataMap.put(obj[1].toString().trim(), obj[0].toString().trim());
    	}
    	return dataMap;
    }
    /**
     * 根据仓库获得工具名称列表
     * @Description: TODO
     * @param @param params
     * @param @return   
     * @return LinkedHashMap<String,String>  
     * @throws
     * @author liujihui
     * @date 2019年5月3日
     */
    public static LinkedHashMap<String, String> getPeerings(Object[] params) {
    	Operator operator = (Operator) params[0];
    	LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
    	if (operator == null) {
    		return dataMap;
    	}
    	List<Object[]> dataList = new ArrayList<Object[]>();
    	String sql = "SELECT t2.WHSE_CODE||'-'||t2.WHSE_NAME as WHSE_NAME,t2.WHSE_CODE FROM RAIL_WHSE_INFO t2 "
				+ "WHERE t2.PARENT_WHSE_CODE= (select PARENT_WHSE_CODE from RAIL_WHSE_INFO t where t.WHSE_CODE ='"+params[1]+"')  "
						+ "AND t2.ENABLE_STATUS = 0 AND t2.DEL_STATUS = 0 AND t2.WHSE_CODE != '"+params[1]+"' ";
    	//sql += " and AGR_BR in " + operator.getBrhBelowId();
    	dataList = commQueryDAO.findBySQLQuery(sql);
    	Iterator<Object[]> iterator2 = dataList.iterator();
    	while (iterator2.hasNext()) {
    		Object[] obj = iterator2.next();
    		dataMap.put(obj[1].toString().trim(), obj[0].toString().trim());
    	}
    	return dataMap;
    }
    /**
     * 根据仓库获得工具名称列表
     * @Description: TODO
     * @param @param params
     * @param @return   
     * @return LinkedHashMap<String,String>  
     * @throws
     * @author liujihui
     * @date 2019年5月3日
     */
    public static LinkedHashMap<String, String> getToolNames(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        if (operator == null) {
            return dataMap;
        }
        List<Object[]> dataList = new ArrayList<Object[]>();
//        System.out.println(params[1]+"   >>>>>>>>>>>>>>>");
        String param = StringUtil.toString(params[1]);
        String[] strs = param.split("？");
        String sql2 = "";
        if(strs.length==2){//and t.TOOL_CODE not in ("++")
        	sql2=" and t.TOOL_CODE not in ("+strs[1]+") ";
        	 System.out.println(strs[1]+"   >>>>>>>>>>ooooooo>>>>");
        }
        System.out.println(strs[0]+"   >>>>>>>>>>ddddddd>>>>");
//        String sql = " select rtn.ID ||'-'||rtn.TOOL_NAME as toolName,rtn.ID as ID FROM RAIL_TOOL_NAME rtn,RAIL_TOOL_INFO rti WHERE 1= 1 AND rti.TOOL_NAME = rtn.ID AND rti.WHSE_CODE = '"+params[1]+"' GROUP BY rtn.ID,rtn.TOOL_NAME ";
        String sql = "SELECT  t1.TOOL_NAME||'-'||t.NOTE2,TOOL_CODE FROM RAIL_TOOL_INFO t,RAIL_TOOL_NAME t1 where t.WHSE_CODE = '"+strs[0]+"' and t.TOOL_STATUS = 0 and t.DEL_STATUS = 0 and t.IN_WHSE=1 AND t.TOOL_NAME = t1.ID "+sql2;
        //sql += " and AGR_BR in " + operator.getBrhBelowId();
        dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator2 = dataList.iterator();
        while (iterator2.hasNext()) {
            Object[] obj = iterator2.next();
            dataMap.put(obj[1].toString().trim(), obj[0].toString().trim());
        }
        return dataMap;
    }
    /**
     * 
     * //TODO 商户营销规则开启方式 
     * 
     * @param params
     * @return
     * @author hanyongqing
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getOpenType(Object[] params) {

        Operator operator = (Operator) params[0];
        //补贴类型
        String subsidyStr = (String) params[1];
        StringBuffer sql = new StringBuffer();
        StringBuffer wheresql = new StringBuffer();
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        if (operator == null) {
            return dataMap;
        }
        if (!StringUtil.isNull(subsidyStr)) {
            wheresql.append(" and RULE_ID =  '" + subsidyStr + "'");
        }
        List<String> dataList = new ArrayList<String>();
        sql.append(" select OPEN_TYPE from TBL_MCHT_MARKSUBSIDY where 1=1 ");
        sql.append(wheresql);
        dataList = commQueryDAO.findBySQLQuery(sql.toString());
        String string = dataList.get(0);
        dataMap.put(string, string);
        return dataMap;
    }

    /**
    * 
    * //TODO 商户营销规则补贴值
    *
    * @param params
    * @return
    * @author hanyongqing
    */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getRuleName(Object[] params) {

        Operator operator = (Operator) params[0];
        //补贴类型
        String subsidyStr = (String) params[1];
        StringBuffer sql = new StringBuffer();
        StringBuffer wheresql = new StringBuffer();
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        if (operator == null) {
            return dataMap;
        }
        if (!StringUtil.isNull(subsidyStr)) {
            wheresql.append(" and SUBSIDY_TYPE =  '" + subsidyStr + "'");
        }
        List<Object[]> dataList = new ArrayList<Object[]>();
        sql.append(" select RULE_ID,SUBSIDY_RULE,SUBSIDY_TYPE from TBL_MCHT_MARKSUBSIDY where 1=1 ");
        sql.append(wheresql);
        dataList = commQueryDAO.findBySQLQuery(sql.toString());
        Iterator<Object[]> iterator2 = dataList.iterator();
        for (Object[] objArr : dataList) {
            if (objArr[2] != null && !objArr[2].equals("")) {
                if (objArr[2].equals("00")) {
                    if (objArr[1] != null && !objArr[1].equals("")) {
                        String trFToY = CommonFunction.transFenToYuan(objArr[1].toString().substring(0, 12));
                        String trFToY2 = CommonFunction.transFenToYuan(objArr[1].toString().substring(12, 24));
                        objArr[1] = "每笔满" + trFToY + "元补" + trFToY2 + "元(" + objArr[0] + ")";
                    }
                } else if (objArr[2].equals("01")) {
                    if (objArr[1] != null && !objArr[1].equals("")) {
                        int parseInt = Integer.parseInt(objArr[1].toString().substring(0, 3));
                        String trFToY2 = CommonFunction.transFenToYuan(objArr[1].toString().substring(3, 15));
                        objArr[1] = "每笔按比例补" + parseInt + "%，最高补" + trFToY2 + "元(" + objArr[0] + ")";
                    }
                } else {
                    objArr[1] = "未知";
                }
            }
        }
        while (iterator2.hasNext()) {
            Object[] obj = iterator2.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 
     * //TODO 终端名称下拉框
     *     因终端名称为空，顾终端名称显示为终端编号
     *
     * @param params
     * @return
     * @author hanyongqing
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getTermName(Object[] params) {

        Operator operator = (Operator) params[0];
        //商户号
        String mchntStr = (String) params[1];
        StringBuffer sql = new StringBuffer();
        StringBuffer wheresql = new StringBuffer();
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        if (operator == null) {
            return dataMap;
        }
        if (!StringUtil.isNull(mchntStr)) {
            wheresql.append(" and MAPPING_MCHNTCDTWO =  '" + mchntStr + "'");
        }
        List<Object[]> dataList = new ArrayList<Object[]>();
        sql.append(" select MAPPING_TERMIDTWO,MAPPING_TERMIDTWO from tbl_term_inf where 1 = 1 and TERM_STA in (1,2) ");
        sql.append(wheresql);
        sql.append(" and TERM_BRANCH in " + operator.getBrhBelowId());
        dataList = commQueryDAO.findBySQLQuery(sql.toString());
        Iterator<Object[]> iterator2 = dataList.iterator();
        while (iterator2.hasNext()) {
            Object[] obj = iterator2.next();
            dataMap.put(obj[1].toString().trim(), obj[0].toString().trim());
        }
        return dataMap;
    }

    /**
     * 
     * //TODO 商户名称下拉框
     *
     * @param params
     * @return
     * @author hanyongqing
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getMchntName(Object[] params) {

        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        if (operator == null) {
            return dataMap;
        }
        List<Object[]> dataList = new ArrayList<Object[]>();
        String sql = " select MCHT_NM ||' - '||MAPPING_MCHNTCDTWO,MAPPING_MCHNTCDTWO from tbl_mcht_base_inf base where 1 = 1 and MCHT_STATUS = 0 ";
        sql += " and AGR_BR in " + operator.getBrhBelowId();
        dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator2 = dataList.iterator();
        while (iterator2.hasNext()) {
            Object[] obj = iterator2.next();
            dataMap.put(obj[1].toString().trim(), obj[0].toString().trim());
        }
        return dataMap;
    }

    /**
     * 
     * //TODO 支付方式下拉框
     *
     * @param params
     * @return
     * @author hanyongqing
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getPayType(Object[] params) {

        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        if (operator == null) {
            return dataMap;
        }
        List<Object[]> dataList = new ArrayList<Object[]>();
        String sql = " select pay_type,pay_id from t_pay_typedic group by pay_type,pay_id order by pay_id desc ";
        dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator2 = dataList.iterator();
        while (iterator2.hasNext()) {
            Object[] obj = iterator2.next();
            dataMap.put(obj[1].toString().trim(), obj[0].toString().trim());
        }
        return dataMap;
    }

    /**
     * 根据当前操作员级别获得机构级别信息，
     * 只有总行返回
     * @param params
     * @return
     */
    public static LinkedHashMap<String, String> getBrhLvlByOprInfo(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();

        Operator operator = (Operator) params[0];

        //总行
        if ("0".endsWith(operator.getOprBrhLvl())) {
            dataMap.put("1", "分行");
            dataMap.put("2", "支行");
            //			dataMap.put("3", "网点");
        } else {
            dataMap.put("2", "支行");
            //			dataMap.put("3", "网点");
        }
        return dataMap;
    }

    /**
     * 获得当前机构的下属机构操作员
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getOprBellow(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        Operator operator = (Operator) params[0];

        String sql = "select opr_id,opr_id||'-'||opr_name as opr from tbl_opr_info where 1=1 ";
        if (!"0000".equals(operator.getOprBrhId())) {
            sql += "and brh_id in " + operator.getBrhBelowId();
        }
        sql += " order by opr_id";
        List dataList = commQueryDAO.findBySQLQuery(sql);

        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }

        return dataMap;
    }

    /**
     * 根据操作员所在机构返回机构信息
     * @param params
     * @return
     */
    public static LinkedHashMap<String, String> getUpBrhIdByOprInfo(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        Operator operator = (Operator) params[0];
        Map<String, String> brhInfoMap = operator.getBrhBelowMap();
        Iterator<String> iterator = brhInfoMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            dataMap.put(key, key + " - " + brhInfoMap.get(key));
        }
        return dataMap;
    }

    /**
     * 根据上级菜单编号查询菜单信息
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getLevelMenu(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select func_id,func_name from tbl_func_inf where func_type<>'3' and func_parent_id = " + params[1].toString();
        sql += " order by func_id";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 根据角色编号查找角色菜单信息
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getRoleMenu(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select value_id,func_name from tbl_role_func_map,tbl_func_inf where value_id = func_id and key_id = " + params[1].toString();
        sql += " order by value_id";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 获得当前机构的下属机构
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getBrhInfoBelow(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        Operator operator = (Operator) params[0];

        dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();

        return dataMap;
    }

    /**
     * 获得当前机构的下属机构(分行)
     * @param params 
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getBrhInfoBelow1(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        Operator operator = (Operator) params[0];

        dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();

        Iterator<String> it = dataMap.keySet().iterator();
        StringBuffer sb = new StringBuffer("(");
        while (it.hasNext()) {
            sb.append("'").append(it.next().trim()).append("'").append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        dataMap = new LinkedHashMap<String, String>();
        String sql = "select BRH_ID,BRH_ID||'-'||BRH_NAME FROM TBL_BRH_INFO WHERE BRH_LEVEL = '1' AND trim(BRH_ID) IN " + sb.toString();
        sql += " ORDER BY BRH_ID";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 获得开户行名称
     * @param params 
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getBankNo(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();

        dataMap = new LinkedHashMap<String, String>();
        String sql = "select BANK_NM,BANK_NO||' - '||BANK_NM FROM TBL_NET_BANK_MAP ";
        sql += " ORDER BY BANK_NO";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getBrhInfoBelowNew(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        Operator operator = (Operator) params[0];

        dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();

        Iterator<String> it = dataMap.keySet().iterator();
        StringBuffer sb = new StringBuffer("(");
        while (it.hasNext()) {
            sb.append("'").append(it.next().trim()).append("'").append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        dataMap = new LinkedHashMap<String, String>();
        String sql = "select BRH_ID,BRH_ID||'-'||BRH_NAME FROM TBL_BRH_INFO WHERE BRH_LEVEL IN( '1','2') AND trim(BRH_ID) IN " + sb.toString();
        sql += " ORDER BY BRH_ID";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 获得当前机构的下属机构(总分行)
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getBrhInfoBelowBranch(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        Operator operator = (Operator) params[0];

        dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();

        Iterator<String> it = dataMap.keySet().iterator();
        StringBuffer sb = new StringBuffer("(");
        while (it.hasNext()) {
            sb.append("'").append(it.next().trim()).append("'").append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        dataMap = new LinkedHashMap<String, String>();
        String sql = "select BRH_ID,BRH_ID||'-'||BRH_NAME FROM TBL_BRH_INFO WHERE BRH_LEVEL IN ('0','1') ";
        if (!"0000".equals(operator.getOprBrhId())) {
            sql += "AND trim(BRH_ID) IN " + sb.toString();
        }
        sql += " ORDER BY BRH_LEVEL";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 获得当前机构的下属机构(总分行)
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getCupInfoBelowBranch(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        Operator operator = (Operator) params[0];

        dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();

        Iterator<String> it = dataMap.keySet().iterator();
        StringBuffer sb = new StringBuffer("(");
        while (it.hasNext()) {
            sb.append("'").append(it.next().trim()).append("'").append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        dataMap = new LinkedHashMap<String, String>();
        String sql = "select CUP_BRH_ID,BRH_NAME FROM TBL_BRH_INFO WHERE BRH_LEVEL IN ('0','1') AND trim(BRH_ID) IN " + sb.toString();
        sql += " ORDER BY BRH_LEVEL";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 获得当前机构的下属机构(-)
     * @param params
     * @return
     */
    public static LinkedHashMap<String, String> getBrhInfoBelowShowId(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        Operator operator = (Operator) params[0];

        Iterator<String> it = operator.getBrhBelowMap().keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            dataMap.put(key, key + "-" + operator.getBrhBelowMap().get(key));
        }

        return dataMap;
    }

    /**
     * 根据机构级别获得角色信息
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getRoleInfoByBrh(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select role_id,role_id||'-'||role_name from tbl_role_inf where ROLE_ID !='1'";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 根据商户选在POS终端信息
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getMchtByPos(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select term_id,term_id from tbl_term_inf where mcht_cd='" + params[1].toString().trim() + "'";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 根据商户组别获得商户编号
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getMchntTpByGrp(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select mchnt_tp,descr from tbl_inf_mchnt_tp where mchnt_tp_grp = '" + params[1].toString().trim() + "'" + " order by mchnt_tp";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[0].toString().trim() + "-" + obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 查询商户分店列表
     * @param params
     * @return
     * 2010-8-17下午03:13:55
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getMchtBranInf(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select  a.MCHT_CD,a.BRANCH_CD,a.BRANCH_NM,a.rec_upd_ts from TBL_MCHT_BRAN_INF a order by a.rec_upd_ts";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 查询商户限额查询
     * @param params
     * @return
     * 2010-8-17下午03:13:55
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getCstMchtFee(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select a.mcht_cd,a.txn_num, a.card_type, a.channel, a.day_num, a.day_amt, "
                + "a.day_single, a.mon_num, a.mon_amt from cst_mcht_fee_inf a";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 查询临时表商户编号
     * 
     * 按照业务要求，未经过审核的商户号也能出现在终端添加是的下拉框中
     * 
     * @param params
     * @return
     * 2010-8-17下午03:13:55
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getMchntNo(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();

        //		String sql = "select MCHT_NO, MCHT_NO ||' - '|| MCHT_NM as MCHT_NM from TBL_MCHT_BASE_INF_TMP where  SIGN_INST_ID in " +  operator.getBrhBelowId(); 
        String sql = "select MCHT_NO, MCHT_NO ||' - '|| MCHT_NM as MCHT_NM from TBL_MCHT_BASE_INF_TMP where  AGR_BR in " + operator.getBrhBelowId();
        //			+ " and MCHT_STATUS = '0'";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 
     * 商户服务表    商户营销活动新增
     * 查询临时表商户编号
     * 
     * 按照业务要求，除【注销、冻结商户】的商户号 能出现在终端添加是的下拉框中
     * 
     * @param params
     * @return
     * 2010-8-17下午03:13:55
     */
    public static LinkedHashMap<String, String> getMchntNo01(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();

        //		String sql = "select MCHT_NO, MCHT_NO ||' - '|| MCHT_NM as MCHT_NM from TBL_MCHT_BASE_INF_TMP where  SIGN_INST_ID in " +  operator.getBrhBelowId()
        String sql = "select MCHT_NO, MCHT_NO ||' - '|| MCHT_NM as MCHT_NM from TBL_MCHT_BASE_INF_TMP where  AGR_BR in " + operator.getBrhBelowId()
                + " and MCHT_STATUS NOT IN('6','9') ";
        System.out.println("ODBC:" + sql);
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    public static LinkedHashMap<String, String> getMchntNo02(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();

        //		String sql = "select MCHT_NO, MCHT_NO ||' - '|| MCHT_NM as MCHT_NM from TBL_MCHT_BASE_INF_TMP where  SIGN_INST_ID in " +  operator.getBrhBelowId()
        String sql = "select MCHT_NO, MCHT_NO ||' - '|| MCHT_NM as MCHT_NM from TBL_MCHT_BASE_INF_TMP where  AGR_BR in " + operator.getBrhBelowId()
                + " and MCHT_STATUS NOT IN('6','9') and mcht_flag1 != '7'";
        System.out.println("ODBC:" + sql);
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    public static LinkedHashMap<String, String> getMchntNo001(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();

        //		String sql = "select MCHT_NO, MCHT_NO ||' - '|| MCHT_NM as MCHT_NM from TBL_MCHT_BASE_INF_TMP where  SIGN_INST_ID in " +  operator.getBrhBelowId()
        String sql = "select MCHT_NO, MCHT_NO ||' - '|| MCHT_NM as MCHT_NM from TBL_MCHT_BASE_INF where  AGR_BR in " + operator.getBrhBelowId();
        //			+ " and MCHT_STATUS NOT IN('6','9') " ;
        System.out.println("ODBC:" + sql);
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 终端管理  == 基本消息 == 商户号
     * @param params
     * @return
     */
    public static LinkedHashMap<String, String> getMchntNo1(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();

        StringBuilder sql = new StringBuilder();
        sql.append("select MCHT_NO, MCHT_NM ||' - '|| MAPPING_MCHNTCDTWO as MCHT_NM from TBL_MCHT_BASE_INF_TMP ");
        sql.append("where 1=1 and");
        //		sql.append(" SIGN_INST_ID in ");
        sql.append(" AGR_BR in ");
        sql.append(operator.getBrhBelowId());
        sql.append(" and MCHT_STATUS != '9'");

        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    public static LinkedHashMap<String, String> getMchntNoNew(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select MCHT_NO, MCHT_NO ||' - '|| MCHT_NM as MCHT_NM from TBL_MCHT_BASE_INF_TMP where  MCHT_STATUS = '0' AND SIGN_INST_ID in "
                + operator.getBrhBelowId();
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 查询正式表中商户编号除去注销和冻结的
     * 
     * @param params
     * @return
     * 2010-8-17下午03:13:55
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getMchntNoBase(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select MCHT_NO, MCHT_NO ||' - '|| MCHT_NM as MCHT_NM from TBL_MCHT_BASE_INF where  SIGN_INST_ID in " + operator.getBrhBelowId();
        //			+ " and MCHT_STATUS = '0'";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 风险模型类型
     * @param params
     * @return
     * 2010-8-17下午03:13:55
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getKind(Object[] params) {

        LinkedHashMap dataMapTmp = new LinkedHashMap();

        dataMapTmp.put("C2", "3日内，同一卡号在同一受理行内交易限制");
        dataMapTmp.put("C3", "3日内，同一卡号交易限制");
        dataMapTmp.put("M3", "同一商户当日同一卡号交易限制");
        dataMapTmp.put("M5", "同一商户当日有超过一笔同金额的限制");
        dataMapTmp.put("R1", "同卡在几家商户交易间隔短于正常时间");

        return dataMapTmp;
    }

    /**
     * 获取终端厂商
     * @param params
     * @return
     * 2011-6-8下午02:46:17
     */
    public static LinkedHashMap<String, String> getManufacturer(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        StringBuffer sql = new StringBuffer("select key,value from CST_SYS_PARAM where owner = '").append(TblTermManagement.REF).append("'");
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 获取终端类型（出厂的终端类型）
     * @param params
     * @return
     * 2011-6-8下午02:46:17
     */
    public static LinkedHashMap<String, String> getTermType(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        StringBuffer sql = new StringBuffer("select owner||'-'||KEY,KEY||'-'||value from CST_SYS_PARAM where 1<>1 ");
        if (params != null && params.length >= 2 && params[1] != null) {
            String manufacturer = params[1].toString();
            sql.append("or owner = '").append(manufacturer).append("'");
        }
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 获取终端库存编号
     * @param params
     * @return
     * 2011-6-21下午03:14:37
     */
    public static LinkedHashMap<String, String> getTermIdId(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        StringBuffer sql = new StringBuffer("select term_no,product_cd from tbl_term_management where STATE ='4' ");
        //		if(params.length<2 || params[1] == null)
        //			return dataMap;
        //		String[] tmp = params[1].toString().split(",");
        //		String mechNo = tmp.length>=1?tmp[0]:"";
        //		String terminalType = tmp.length>=2?tmp[1]:"";
        //		String manufaturer = tmp.length>=3?tmp[2]:"";
        //		if(mechNo!=null && !mechNo.equals(""))
        //			sql.append("or MECH_NO = '").append(mechNo).append("' ");
        //		if(terminalType!=null && !terminalType.equals(""))
        //			sql.append("and TERMINAL_TYPE = '").append(terminalType).append("' ");
        //		if(manufaturer!=null && !manufaturer.equals(""))
        //			sql.append("and MANUFATURER = '").append(manufaturer).append("'");

        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 查询POS交易类型
     * @param params
     * @return
     * 2010-8-27上午11:21:12
     */
    public static LinkedHashMap<String, String> getPosTxnNum(Object[] params) {
        return (LinkedHashMap<String, String>) TxnInfo.txnNameMap;
    }

    /**
     * 查询商户地区代码
     * @param params
     * @return
     * 2010-8-27上午11:21:12
     */
    public static LinkedHashMap<String, String> getCityCode(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        //			ICommQueryDAO commonQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAOCopy");
        List dates = null;
        String sql = "select CITY_CODE_NEW,CITY_CODE_NEW ||' - '|| CITY_NAME AS CITY_NAME From CST_CITY_CODE";
        //			String sql = "select CITY_CODE_NEW, CITY_CODE_NEW ||'-' ||CITY_NAME AS CITY_NAME From CST_CITY_CODE";
        dates = commQueryDAO.findBySQLQuery(sql);
        Iterator itor = dates.iterator();
        Object[] obj;
        String docType, descTx;
        while (itor.hasNext()) {
            obj = (Object[]) itor.next();
            docType = obj[0].toString().trim();
            descTx = obj[1].toString().trim();
            hashMap.put(docType, descTx);
        }
        return hashMap;

    }

    /**
     * 查询机构地区代码
     * @param params
     * @return
     * 2010-8-27上午11:21:12
     */
    public static LinkedHashMap<String, String> getCityCodeOld(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        List dates = null;
        String sql = "select DISTINCT(CITY_CODE_OLD),CITY_CODE_OLD　 From CST_CITY_CODE";
        dates = commQueryDAO.findBySQLQuery(sql);
        Iterator itor = dates.iterator();
        Object[] obj;
        String docType, descTx;
        while (itor.hasNext()) {
            obj = (Object[]) itor.next();
            docType = obj[0].toString().trim();
            descTx = obj[1].toString().trim();
            hashMap.put(docType, descTx);
        }
        return hashMap;

    }

    /**
     * 根据商户编号查询该商户已经选择的交易权限码
     * @param params
     * @return
     * 2010-8-27上午11:21:12
     */
    public static LinkedHashMap<String, String> getMchtRoleFuncByMerId(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        if (args.length >= 2) {
            //查找所有交易码
            String sql = "select TXN_NUM,TXN_NAME from TBL_TXN_NAME where DC_FLAG = '0' order by TXN_NUM";
            ;
            List<Object[]> funcAll = commQueryDAO.findBySQLQuery(sql);

            Long merId = Long.valueOf(args[1].toString().toString());
            //查找选择商户所在 Mcc
            List<Object[]> roleIds = commQueryDAO.findBySQLQuery("select MCC,SIGN_INST_ID from TBL_MCHT_BASE_INF where MCHT_NO = " + merId);
            String roleId = roleIds.get(0)[0].toString();
            //相关二级商户具有的交易权限
            sql = "select VALUE_ID from TBL_MER_ROLE_FUNC where KEY_ID = " + roleId + " order by VALUE_ID";
            List<Object> funcRole = commQueryDAO.findBySQLQuery(sql);

            //该商户所在机构所拥有的权限集

            List<Object[]> rightFuncLIst = new ArrayList<Object[]>();
            //筛选权限信息
            for (int i = 0; i < funcAll.size(); i++) {
                Long funcId = Long.valueOf(funcAll.get(i)[0].toString().trim());
                for (int j = 0; j < funcRole.size(); j++) {
                    Long valueId = Long.valueOf(String.valueOf(funcRole.get(j)));
                    if (valueId.longValue() == funcId.longValue()) {
                        Object[] funcInfo = new Object[2];
                        funcInfo[0] = funcId;
                        funcInfo[1] = funcAll.get(i)[1].toString();
                        rightFuncLIst.add(funcInfo);
                        funcAll.remove(i--);
                        break;
                    }
                }

            }

            Iterator itor = rightFuncLIst.iterator();
            Object[] obj;
            String docType, descTx;
            while (itor.hasNext()) {
                obj = (Object[]) itor.next();
                docType = obj[0].toString().trim();
                descTx = obj[1].toString().trim();
                hashMap.put(docType, descTx);
            }
        }
        return hashMap;

    }

    /**
     * 根据商户类型编号查询商户组别已经选择的交易权限码
     * @param params
     * @return
     * 2010-8-27上午11:21:12
     */
    public static LinkedHashMap<String, String> getMerRoleFunc(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        if (args.length >= 2) {
            Long roleId = Long.valueOf(args[1].toString());
            String sql = "select TXN_NUM,TXN_NAME from TBL_TXN_NAME where DC_FLAG = '1' order by TXN_NUM";
            ;
            List<Object[]> funcAll = commQueryDAO.findBySQLQuery(sql);
            sql = "select VALUE_ID from TBL_MER_ROLE_FUNC where KEY_ID = " + roleId + " order by VALUE_ID";
            System.out.println(sql);
            List<Object> funcRole = commQueryDAO.findBySQLQuery(sql);
            //右侧权限集
            List<Object[]> rightFuncLIst = new ArrayList<Object[]>();
            //筛选权限信息
            for (int i = 0; i < funcAll.size(); i++) {
                //				Long funcId = Long.valueOf(funcAll.get(i)[0].toString());
                String funcId = funcAll.get(i)[0].toString().trim();
                for (int j = 0; j < funcRole.size(); j++) {
                    //					Long valueId = Long.valueOf(String.valueOf(funcRole.get(j)));
                    String valueId = String.valueOf(funcRole.get(j)).trim();
                    //					if(valueId.longValue() == funcId.longValue()) {
                    if (valueId.equals(funcId)) {
                        Object[] funcInfo = new Object[2];
                        funcInfo[0] = funcId;
                        funcInfo[1] = funcAll.get(i)[1].toString();
                        rightFuncLIst.add(funcInfo);
                        funcAll.remove(i--);
                        break;
                    }
                }

            }

            Iterator itor = rightFuncLIst.iterator();
            Object[] obj;
            String docType, descTx;
            while (itor.hasNext()) {
                obj = (Object[]) itor.next();
                docType = obj[0].toString().trim();
                descTx = obj[1].toString().trim();
                hashMap.put(docType, descTx);
            }
        }
        return hashMap;

    }

    /**
     * 根据商户类型编号查询商户组别已经选择的权限码
     * @param params
     * @return
     * 2010-8-27上午11:21:12
     */
    public static LinkedHashMap<String, String> getMerMerFuncAll(Object[] args) {
        //		String sql = "select FUNC_ID,FUNC_NAME from TBL_MER_FUNC_INF where FUNC_TYPE = '0' order by FUNC_ID";
        //		String sql = "select TXN_NUM,TXN_NAME from TBL_TXN_NAME where DC_FLAG = '0' order by TXN_NUM";
        String sql = "select TXN_NUM,TXN_NAME from TBL_TXN_NAME where DC_FLAG = '1' order by TXN_NUM";
        List<Object[]> funcAll = commQueryDAO.findBySQLQuery(sql);
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        Iterator itor = funcAll.iterator();
        Object[] obj;
        String docType, descTx;
        while (itor.hasNext()) {
            obj = (Object[]) itor.next();
            docType = obj[0].toString().trim();
            descTx = obj[1].toString().trim();
            hashMap.put(docType, descTx);
        }
        return hashMap;

    }

    /**
     * 获得商户服务等级
     * 
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getBranchSvrLvl(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("0", "VIP");
        hashMap.put("1", "重点");
        hashMap.put("2", "普通");
        return hashMap;
    }

    /**
     * 获得商户资质等级
     * 
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getMchtCreLvl(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("1", "一级");
        hashMap.put("2", "二级");
        hashMap.put("3", "三级");
        hashMap.put("9", "未分级");
        return hashMap;
    }

    /**
     * getYesOrNotSelect
     * 
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getYesOrNotSelect(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("0", "是");
        hashMap.put("1", "否");
        return hashMap;
    }

    /**
     * getAOrPSelect
     * 
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getAOrPSelect(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("0", "A");
        hashMap.put("1", "P");
        return hashMap;
    }

    /**
     * getTxnNum
     * 回佣类型
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getDiscAlgoFlag(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("0", "按比率");
        hashMap.put("1", "按金额");
        return hashMap;
    }

    /**
     * getTxnNum
     * 交易类型
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getTxnNum(Object[] args) {
        //		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        //		hashMap.put("1101", "信用卡消费");
        //		hashMap.put("1091", "信用卡预授权完成");
        //		hashMap.put("1701", "T+0收款");
        //		return hashMap;
        LinkedHashMap<String, String> hashMap = new

        LinkedHashMap<String, String>();
        List dates = null;
        String sql = "select TXN_NUM,TXN_NAME from TBL_TXN_NAME where DC_FLAG = '0' order by TXN_NUM";
        ;
        dates = commQueryDAO.findBySQLQuery(sql);
        Iterator itor = dates.iterator();
        Object[] obj;
        String docType, descTx;
        while (itor.hasNext()) {
            obj = (Object[]) itor.next();
            docType = obj[0].toString().trim();
            descTx = obj[1].toString().trim();
            hashMap.put(docType, descTx);
        }
        return hashMap;

    }

    /**
     * getCardType
     * 卡类型
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getCardType(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("01", "借记卡");
        hashMap.put("00", "贷记卡");
        hashMap.put("10", "准贷记卡");
        hashMap.put("11", "预付卡");
        return hashMap;
    }

    /**
     * getChannel
     * 交易渠道CHANNEL
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getChannel(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("0", "间联POS");
        hashMap.put("1", "否");
        return hashMap;
    }

    /**
     * 获得币种信息
     * 
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getCurType(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("0", "人民币");
        hashMap.put("1", "港币");
        hashMap.put("2", "美元");
        hashMap.put("3", "日元");
        hashMap.put("4", "马克");
        hashMap.put("5", "英镑");
        hashMap.put("6", "法郎");
        hashMap.put("7", "台币");
        return hashMap;
    }

    /**
     * 授权管理的一级菜单获取
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getAuthorMenu(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();

        List<Object[]> list = null;

        String sql = "select FUNC_ID,FUNC_NAME from tbl_func_inf where func_id in "
                + "(select distinct func_parent_id from TBL_FUNC_INF where func_id in "
                + "(select distinct func_parent_id from TBL_FUNC_INF where trim(misc1) in ('1','2')))";

        list = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = list.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 根据上级菜单编号查询菜单信息(授权管理)
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getAuthorNextMenu(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();

        if (params.length == 1) {
            return dataMap;
        }

        String sql = "select FUNC_TYPE from tbl_func_inf where func_id = '" + params[1].toString().trim() + "'";
        List type = commQueryDAO.findBySQLQuery(sql);
        if (null != type && !type.isEmpty()) {
            if (!StringUtil.isNull(type.get(0))) {
                if ("1".equals(type.get(0).toString())) {
                    sql = "select func_id,func_name from tbl_func_inf where func_parent_id = '" + params[1].toString().trim()
                            + "' and func_id in (select distinct func_parent_id from TBL_FUNC_INF where trim(misc1) in ('1','2')) ";
                } else {
                    sql = "select func_id,func_name from tbl_func_inf where func_parent_id = '" + params[1].toString().trim()
                            + "' and trim(misc1) in ('1','2')";
                }
            }
            List<Object[]> list = commQueryDAO.findBySQLQuery(sql);
            Iterator<Object[]> iterator = list.iterator();
            while (iterator.hasNext()) {
                Object[] obj = iterator.next();
                dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
            }
        }
        return dataMap;
    }

    /**
     * 授权管理的已列为需授权的交易获取
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getAuthorisedMenu(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();

        List<Object[]> list = null;

        String sql = "select FUNC_ID,FUNC_NAME from tbl_func_inf where trim(misc1) = '1' and FUNC_TYPE = '0'";

        list = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = list.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 当前机构和下属机构的32域
     * @param params
     * @return
     * 2010-8-17下午03:13:55
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getCupBrhInfoBelow(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select CUP_BRH_ID, CUP_BRH_ID ||' - '|| BRH_NAME from TBL_BRH_INFO where BRH_LEVEL='1' AND BRH_ID in "
                + operator.getBrhBelowId();
        sql += " ORDER BY BRH_ID";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 银联清算机构，对应各省份银联
     * @param params
     * @return
     * 2010-8-17下午03:13:55
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getCupBrhSett(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select CUP_BRH_ID, CUP_BRH_ID ||' - '|| BRH_NAME from tbl_brh_info a join cst_brh_acct_param b on(a.cup_brh_id =b.cup_brh_no and (a.brh_level='1' or a.brh_level='0') and b.comp_key='SYS_ID_Z')";
        sql += " ORDER BY a.BRH_ID";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     *  终端活动编号
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getActNo(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select ACT_NO, ACT_NO ||' - '|| ACT_NAME from TBL_MARKET_ACT where END_DATE <='" + CommonFunction.getCurrentDate()
                + "' AND STATE = '0' and BANK_NO in " + operator.getBrhBelowId();
        sql += " ORDER BY ACT_NO";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     *  终端活动编号
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getActNoNew(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select ACT_NO, ACT_NO ||' - '|| ACT_NAME from TBL_MARKET_ACT where END_DATE >='" + CommonFunction.getCurrentDate()
                + "' AND STATE = '0' and BANK_NO in " + operator.getBrhBelowId();
        sql += " ORDER BY ACT_NO";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 获取专业服务机构
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getOragn(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select ORG_ID,ORG_ID||'-'||ORG_NAME from TBL_PROFESSIONAL_ORGAN where 1=1 ";
        if (!"0000".equals(operator.getOprBrhId())) {
            sql += "and BRANCH in" + operator.getBrhBelowId();
        }
        sql += " ORDER BY ORG_ID";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 查询临时表证照号
     * @param params
     * @return
     * 2010-8-17下午03:13:55
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getLicenceTmpNo(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select LICENCE_NO, LICENCE_NO ||' - '|| ETPS_NM as ETPS_NM from TBL_MCHT_LICENCE_INF_TMP";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 获得商户性质1
     * @param params
     * @return
     * 2010-8-17下午03:13:55
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getMchtFlag1(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        dataMap.put("0", "收款商户");
        dataMap.put("1", "老板通商户");
        dataMap.put("2", "分期商户");
        dataMap.put("3", "财务转账商户");
        dataMap.put("4", "项目形式商户");
        dataMap.put("5", "积分业务");
        dataMap.put("6", "其他业务");
        dataMap.put("7", "固话POS商户");
        return dataMap;
    }

    /**
     * 交易路由信息
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getRouting(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select USAGE_KEY,LINE_DSP from  tbl_line_cfg where SRV_ID='1'";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 获得商户性质2
     * @param params
     * @return
     * 2010-8-17下午03:13:55
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getMchtFlag2(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        dataMap.put("00", "线上商户");
        dataMap.put("01", "线下商户");
        dataMap.put("10", "业主收款商户");
        dataMap.put("11", "业主付款商户");
        dataMap.put("20", "汽车分期商户");
        dataMap.put("21", "停车位分期商户");
        dataMap.put("22", "家装分期商户");
        dataMap.put("23", "POS分期商户");
        dataMap.put("24", "其他分期商户");
        dataMap.put("30", "本行财务转账商户");
        dataMap.put("31", "跨行财务转账商户");
        dataMap.put("40", "交通罚没项目");
        dataMap.put("41", "体彩购额项目");
        dataMap.put("42", "其他项目");

        return dataMap;
    }

    //   <!-- 商户信息查询- 商户性质   phj -->
    public static LinkedHashMap<String, String> getMchtFlag3(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        dataMap.put("00", "收款商户 - 线上商户");
        dataMap.put("01", "收款商户 - 线下商户");
        dataMap.put("10", "老板通 - 业主收款商户");
        dataMap.put("11", "老板通 - 业主付款商户");
        dataMap.put("20", "分期商户 - 汽车分期商户");
        dataMap.put("21", "分期商户 - 停车位分期商户");
        dataMap.put("22", "分期商户 - 家装分期商户");
        dataMap.put("23", "分期商户 - POS分期商户");
        dataMap.put("24", "分期商户 - 其他分期商户");
        dataMap.put("30", "财务转账商户 - 本行财务转账商户");
        dataMap.put("31", "财务转账商户 - 跨行财务转账商户");
        dataMap.put("40", "项目形式商户 - 交通罚没项目");
        dataMap.put("41", "项目形式商户 - 体彩购额项目");
        dataMap.put("42", "项目形式商户 - 其他项目");

        return dataMap;
    }

    /**
     * 直联商户用银联机构码
     * @param params
     * @return
     * 2010-8-17下午03:13:55
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getCupBrhS(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select CUP_BRH_ID,CUP_BRH_ID ||' - '|| BRH_NAME from TBL_BRH_INFO where brh_level in('0','1') and BRH_ID in "
                + operator.getBrhBelowId();
        sql += " ORDER BY BRH_ID";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put("08" + obj[0].toString().trim().substring(0, 8), "08" + obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 直联商户用MCC
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getMchntTpCup(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select mchnt_tp,descr from tbl_inf_mchnt_tp where rec_st = '1'" + " order by mchnt_tp";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[0].toString().trim() + "-" + obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * getMchntGrpCup
     * 直联商户组别
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getMchntGrpCup(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("00", "00-综合零售");
        hashMap.put("01", "01-专门零售");
        hashMap.put("02", "02-批发类");
        hashMap.put("03", "03-住宅餐饮");
        hashMap.put("04", "04-房地产业");
        hashMap.put("05", "05-房地产业*");
        hashMap.put("06", "06-金融业");
        hashMap.put("07", "07-政府服务");
        hashMap.put("08", "08-教育");
        hashMap.put("09", "09-卫生");
        hashMap.put("10", "10-公共事业");
        hashMap.put("11", "11-居民服务");
        hashMap.put("12", "12-商业服务");
        hashMap.put("13", "13-交通物流");
        hashMap.put("14", "14-直销类商户");
        hashMap.put("15", "15-租赁服务");
        hashMap.put("16", "16-维修服务");
        hashMap.put("17", "17-其他");
        return hashMap;
    }

    /**
     * getAppReason
     * 套用商户类型原因 
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getAppReason(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("00", "真实公益类");
        hashMap.put("01", "入网优惠期套用");
        hashMap.put("02", "公共事业类商户");
        hashMap.put("03", "批发市场类商户");
        hashMap.put("04", "资金归集类商户");
        hashMap.put("05", "历史遗留类套用");
        return hashMap;
    }

    /**
     * getMccMd
     * MCC套用依据
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getMccMd(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("00", "私自套用");
        hashMap.put("01", "经价格小组通过");
        hashMap.put("02", "当地协商");
        hashMap.put("03", "人民银行/银监会发文");
        hashMap.put("04", "同业公会发文");
        hashMap.put("05", "其他组织发文");
        hashMap.put("06", "其他");
        return hashMap;
    }

    /**
     * getStoreFlag
     * 连锁店标识
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getStoreFlag(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("0", "非连锁店");
        hashMap.put("1", "根总店");
        hashMap.put("2", "分店");
        return hashMap;
    }

    /**
     * getCertCup
     * 直联商户证件类型
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getCertCup(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("99", "其他证件");
        hashMap.put("01", "身份证");
        hashMap.put("02", "军官证");
        hashMap.put("03", "护照");
        hashMap.put("04", "港澳居民来往内地通行证（回乡证）");
        hashMap.put("05", "台湾同胞来往内地通行证（台胞证）");
        hashMap.put("06", "警官证");
        hashMap.put("07", "士兵证");
        hashMap.put("08", "户口薄");
        hashMap.put("09", "临时身份证");
        hashMap.put("10", "外国人居留证");
        return hashMap;
    }

    /**
     * getReasonCode
     * 登记原因码
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getReasonCode(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("9790", "特殊结算方式");
        hashMap.put("9791", "大型批发商店资金归集业务");
        hashMap.put("9792", "缴费类业务");
        hashMap.put("9793", "历史遗留低扣率商户套用");
        hashMap.put("9794", "分公司自定义套用原因码1");
        hashMap.put("9795", "分公司自定义套用原因码2");
        hashMap.put("9796", "分公司自定义套用原因码3");
        hashMap.put("9797", "分公司自定义套用原因码4");
        hashMap.put("9798", "分公司自定义套用原因码5");
        hashMap.put("9799", "真实商户登记");
        return hashMap;
    }

    /**
     * getMchntCupFate
     * 直联商户分润算法
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getMchntCupFate(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("0000", "0000 -公益类；不收费");
        hashMap.put("AN001", "AN001-餐娱类；发卡0.9%，银联0.13%");
        hashMap.put("AN002", "AN002-房车类；发卡0.9%单笔最高60元，银联0.13%单笔最高10元");
        hashMap.put("AN003", "AN003-批发类；发卡0.55%单笔最高20元，银联0.08%单笔最高2.5元");
        hashMap.put("AN004", "AN004-一般类；发卡0.55%，银联0.08%");
        hashMap.put("AN005", "AN005-民生类；发卡0.26%，银联0.04%");
        hashMap.put("AX100", "AX100-消费类境内宾馆娱乐类，发卡1.4%，银联0.2%");
        hashMap.put("AX210", "AX210-房地产汽车典当拍卖类；发卡0.7%，单笔最高40元，银联0.1%，单笔最高5元");
        hashMap.put("AX230", "AX230-普通批发类；发卡0.7%，单笔最高16元，银联0.1%，单笔最高2元");
        hashMap.put("AX240", "AX240-大型企业批发，县乡优惠批发类；发卡0.35%，单笔最高8元，银联0.05%，单笔最高1元");
        hashMap.put("AX250", "AX250-烟草配送类；发卡0.35%单笔最高4元，银联0.05%，单笔最高0.5元");
        hashMap.put("AX270", "AX270-县乡优惠房产汽车类；发卡0.35%，单笔最高20元，银联0.05%，单笔最高2.5元");
        hashMap.put("AX280", "AX280-县乡优惠三农商户类；发卡0.21%，单笔最高2.1元，银联0.03%，单笔最高0.3元");
        hashMap.put("AX300", "AX300-航空售票加油超市类；发卡0.35%，银联0.05%");
        hashMap.put("AX310", "AX310-县乡优惠超市加油类；发卡0.175%，银联0.025%");
        hashMap.put("AX500", "AX500-保险刷卡类；发卡0.21%，银联0.03%");
        hashMap.put("AX510", "AX510-保险批量代扣类；发卡1.4元/笔，银联0.2元/笔");
        hashMap.put("AX520", "AX520-公共事业类；发卡0.21元/笔，银联0.03元/笔");
        hashMap.put("AX600", "AX600-信用卡还款；发卡1.5元，银联0.3元");
        hashMap.put("AX900", "AX900-一般类，县乡优惠宾馆娱乐消费类；发卡0.7%，银联0.1%");
        hashMap.put("CI181", "CI181-江苏地区发卡方收取商户回佣的40%，银联的20%");
        hashMap.put("CI182", "CI182-江苏地区发卡方收取商户回佣的40%，银联的10%");
        hashMap.put("CI183", "CI183-江苏地区发卡方收取商户回佣的20%，银联的10%");
        hashMap.put("CI184", "CI184-江苏地区发卡方收取商户回佣的70%，银联的10%");
        hashMap.put("CI185", "CI185-发卡0.4元/笔，银联0元/比，收单0元/笔");
        hashMap.put("CI186", "CI186-发卡0.1%，银联0.03%，交易金额的0.1%和0.03%");
        hashMap.put("CI187", "CI187-发卡0.1元/笔，银联0.03元/笔");
        hashMap.put("CI500", "CI500-批量代收(除公缴)，单笔金额小于1000元发卡0.5元银联0.2元，1000-5000元发卡1元银联0.2元,超过5000元发卡1.5元银联0.2元");
        return hashMap;
    }

    /**
     * getMchntFeeAct
     * 直联商户费率
     * @param args
     * @return
     */
    public static LinkedHashMap<String, String> getMchntFeeAct(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("NK001", "NK001-全国南卡北用决定索引");
        hashMap.put("TP001", "TP001-全国通配决定索引");
        hashMap.put("JS001", "JS001-江苏手续费计费决定");
        hashMap.put("JS002", "JS002-江苏手续费积分消费");
        hashMap.put("JS133", "JS133-江苏贷记卡计费决定");
        hashMap.put("JS134", "JS134-江苏准贷记卡计费决定");
        hashMap.put("JS447", "JS447-手续费默认通配条件");
        hashMap.put("NJ001", "NJ001-NJ001");
        hashMap.put("NJ002", "NJ002-NJ002");
        hashMap.put("NK001", "NK001-江苏南卡北用手续费");
        return hashMap;
    }

    public static LinkedHashMap<String, String> getMchntFeeAct2(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("JS119", "JS119-江苏手续费分润决定");
        return hashMap;
    }

    /**
     * 直联商户费率1
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getCupFeeTp1(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select fee_id,fee_id||'-'||fee_nm from tbl_mcht_cup_fee where fee_type = '" + params[1].toString().trim() + "'"
                + " order by fee_id";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 根据商户性质1获得商户性质2
     * @param params
     * @return
     * 2010-8-17下午03:13:55
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getMchtFlag2ByFlag1(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap0 = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> dataMap1 = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> dataMap2 = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> dataMap3 = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> dataMap4 = new LinkedHashMap<String, String>();
        Map<String, LinkedHashMap<String, String>> allMap = new HashMap<String, LinkedHashMap<String, String>>();
        allMap.put("0", dataMap0);
        allMap.put("1", dataMap1);
        allMap.put("2", dataMap2);
        allMap.put("3", dataMap3);
        allMap.put("4", dataMap4);
        dataMap0.put("0", "线上商户");
        dataMap0.put("1", "线下商户");

        dataMap1.put("0", "业主收款商户");
        dataMap1.put("1", "业主付款商户");

        dataMap2.put("0", "汽车分期商户");
        dataMap2.put("1", "停车位分期商户");
        dataMap2.put("2", "家装分期商户");
        dataMap2.put("3", "POS分期商户");
        dataMap2.put("4", "其他分期商户");

        dataMap3.put("0", "本行财务转账商户");
        dataMap3.put("1", "跨行财务转账商户");

        dataMap4.put("0", "交通罚没项目");
        dataMap4.put("1", "体彩购额项目");
        dataMap4.put("2", "其他项目");
        if (allMap.containsKey(params[1])) {
            return allMap.get(params[1]);
        } else {
            return new LinkedHashMap<String, String>();
        }
    }

    /**
     * 获得当前机构及其下属机构
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getBrhInfo(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        Operator operator = (Operator) params[0];

        dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();

        Iterator<String> it = dataMap.keySet().iterator();
        StringBuffer sb = new StringBuffer("(");
        while (it.hasNext()) {
            sb.append("'").append(it.next().trim()).append("'").append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        dataMap = new LinkedHashMap<String, String>();
        String sql = "select BRH_ID,BRH_ID||'-'||BRH_NAME FROM TBL_BRH_INFO WHERE  trim(BRH_ID) IN " + sb.toString();
        sql += " ORDER BY BRH_LEVEL";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 
     * @虚拟柜员维护-添加
     * @return
     */
    public static LinkedHashMap<String, String> getBrhMainInfo(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        Operator operator = (Operator) params[0];

        dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();

        Iterator<String> it = dataMap.keySet().iterator();
        StringBuffer sb = new StringBuffer("(");
        while (it.hasNext()) {
            sb.append("'").append(it.next().trim()).append("'").append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        dataMap = new LinkedHashMap<String, String>();
        String sql = "select BRH_ID,BRH_ID||'-'||BRH_NAME FROM TBL_BRH_INFO WHERE  trim(BRH_ID) = '9901'";
        //			sb.toString();
        sql += " ORDER BY BRH_LEVEL";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 根据商户号获取终端号
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getTermIdByMchtId(Object[] params) {
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "SELECT TERM_ID,' ' FROM TBL_TERM_INF WHERE MCHT_CD = '" + params[1].toString().trim() + "'" + " ORDER BY TERM_ID";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[0].toString().trim() + " " + obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * EPOS版本号通过银联编号查询
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getEposVer(Object[] params) {
        Operator operator = (Operator) params[0];
        String brhId = params[1].toString();
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select distinct t1.VER_ID,t1.VER_ID||'-'||t1.MISC from TBL_VER_MNG t1 left join tbl_brh_info t2 on t1.bank_id = t2.cup_brh_id  where t2.BRH_ID in "
                + operator.getBrhBelowId();
        if (brhId != null && !brhId.equals("")) sql += "and t2.cup_brh_id ='" + brhId + "'";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * EPOS交易提示信息
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getPptMsg(Object[] params) {
        Operator operator = (Operator) params[0];
        String verId = params[1].toString();
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select distinct t1.ppt_id,t1.ppt_id||'-'||t1.ppt_msg1 from TBL_PPT_MSG t1 where usage_key = '0' and msg_type = '1' ";
        if (verId != null && !verId.equals("")) sql += "and t1.ver_id ='" + verId + "'";

        sql += " order by t1.ppt_id ";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        dataMap.put("0", "0-默认");
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * EPOS版本号
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getEposVersion(Object[] params) {
        Operator operator = (Operator) params[0];
        String brhId = params[1].toString();
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select distinct t1.VER_ID,t1.VER_ID||'-'||t1.MISC from TBL_VER_MNG t1 left join tbl_brh_info t2 on t1.bank_id = t2.cup_brh_id  where t2.BRH_ID in "
                + operator.getBrhBelowId();
        if (brhId != null && !brhId.equals("")) sql += "and t2.brh_id ='" + brhId + "'";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * EPOS版本号
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getEposVersionNew(Object[] params) {
        Operator operator = (Operator) params[0];
        String brhId = params[1].toString();
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select distinct t1.VER_ID,t1.VER_ID||'-'||t1.MISC from TBL_VER_MNG t1";
        //		if(brhId != null && !brhId.equals(""))
        //			sql += "and t2.brh_id ='"+brhId+"'";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
    * getSignStatus
    * 签约状态
    * @param args
    * @return
    */
    public static LinkedHashMap<String, String> getSignStatus(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("0", "正常");
        hashMap.put("1", "未启用");
        return hashMap;
    }

    /**
    * getSelectStatus
    * 查询类型
    * @param args
    * @return
    */
    public static LinkedHashMap<String, String> getSelectStatus(Object[] args) {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("0", "1-所在地区");
        hashMap.put("1", "2-归属机构");
        return hashMap;
    }

    /**
     * EPOS交易类型
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getTxnNumEpos(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select txn_num,txn_name from tbl_txn_name where dc_flag='2' or txn_num='1801'";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 优惠规则
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getDiscountRule(Object[] params) {
        Operator operator = (Operator) params[0];
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select DISCOUNT_ID,DISCOUNT_TYPE,DISCOUNT_VALUE from TBL_MCHNT_DISCOUNTRULE where STATUS=1";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            Object[] objtem = new Object[2];
            if ("0".equals(obj[1].toString().trim())) {
                objtem = obj[2].toString().trim().split("-", 2);
                dataMap.put(
                        obj[0].toString().trim(),
                        obj[0].toString().trim() + "-满" + Integer.parseInt(objtem[0].toString(), 10) + "-减"
                                + Integer.parseInt(objtem[1].toString(), 10));
            } else if ("1".equals(obj[1].toString().trim())) {
                objtem = obj[2].toString().trim().split("-", 2);
                dataMap.put(
                        obj[0].toString().trim(),
                        obj[0].toString().trim() + "-折扣" + Integer.parseInt(objtem[0].toString(), 10) + "-" + objtem[1].toString());
            } else if ("2".equals(obj[1].toString().trim())) {
                dataMap.put(obj[0].toString().trim(), obj[0].toString().trim() + "-任意" + obj[2].toString().trim());
            }

        }
        return dataMap;
    }

    /**
     * 终端编号
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getTermId(Object[] params) {
        Operator operator = (Operator) params[0];
        String mchtId = params[1].toString();
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select t1.TERM_ID,trim(t1.TERM_ID)||'-'||t1.TERM_NAME from TBL_TERM_INF_TMP t1";
        if (mchtId != null && !mchtId.equals("")) sql += " where t1.MCHT_CD ='" + mchtId + "'";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 已装机的终端编号
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getTermIdEff(Object[] params) {
        Operator operator = (Operator) params[0];
        String mchtId = params[1].toString();
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select t1.TERM_ID,trim(t1.TERM_ID)||'-'||t1.TERM_NAME from TBL_TERM_INF_TMP t1";
        if (mchtId != null && !mchtId.equals("")) sql += " where t1.MCHT_CD ='" + mchtId + "'";
        sql += " and t1.TERM_STA in (1, 2)";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
        }
        return dataMap;
    }

    /**
     * 根据类型查询优惠规则
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getDiscountRuleByType(Object[] params) {
        Operator operator = (Operator) params[0];
        String type = params[1].toString();
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        String sql = "select DISCOUNT_ID,DISCOUNT_TYPE,DISCOUNT_VALUE,DISCOUNT_CODE from TBL_MCHNT_DISCOUNTRULE where 1=1 AND STATUS=1";
        if (StringUtils.equals(type, "00")) {
            sql = sql + " and DISCOUNT_TYPE=0";//0是满减
        }
        if (StringUtils.equals(type, "01")) {
            sql = sql + " and DISCOUNT_TYPE=1";//1是折扣
        }
        if (StringUtils.equals(type, "02")) {
            sql = sql + " and DISCOUNT_TYPE=2";//2是任意
        }

        if (StringUtils.equals(type, "03")) {
            sql = sql + " and DISCOUNT_TYPE=3";//3是仅联机
        }
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        Iterator<Object[]> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object[] obj = iterator.next();
            Object[] objtem = new Object[2];
            if ("0".equals(obj[1].toString().trim())) {
                objtem = obj[2].toString().trim().split("-", 2);
                dataMap.put(
                        obj[0].toString().trim(),
                        "满" + Long.parseLong(objtem[0].toString(), 10) + "-减" + Long.parseLong(objtem[1].toString(), 10) + "("
                                + obj[0].toString().trim() + ")");
                //				dataMap.put(obj[0].toString().trim(), "("+obj[3].toString().trim()+")满"+ Long.parseLong(objtem[0].toString(),10)+"-减"+Long.parseLong(objtem[1].toString(),10));
                //				dataMap.put(obj[0].toString().trim(), "("+obj[3].toString().trim()+")满"+ Integer.parseInt(objtem[0].toString(),10)+"-减"+Integer.parseInt(objtem[1].toString(),10));
            } else if ("1".equals(obj[1].toString().trim())) {
                objtem = obj[2].toString().trim().split("-", 2);
                //				dataMap.put(obj[0].toString().trim(), "折扣"+Long.parseLong(objtem[0].toString(),10)+"-"+objtem[1].toString()+"("+obj[0].toString().trim()+")");
                dataMap.put(
                        obj[0].toString().trim(),
                        objtem[1].toString() + "折-限" + Long.parseLong(objtem[0].toString(), 10) + "(" + obj[0].toString().trim() + ")");
                //				dataMap.put(obj[0].toString().trim(), "("+obj[3].toString().trim()+")折扣"+Integer.parseInt(objtem[0].toString(),10)+"-"+objtem[1].toString());
            } else if ("2".equals(obj[1].toString().trim())) {
                dataMap.put(obj[0].toString().trim(), "任意" + obj[2].toString().trim() + "(" + obj[0].toString().trim() + ")");
            } else if ("3".equals(obj[1].toString().trim())) {
                dataMap.put(obj[0].toString().trim(), "(" + obj[0].toString().trim() + ")仅联机");
            }

        }
        return dataMap;
    }

    /**
     * 根据优惠规则查询对应的商户号
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getMchntByDiscountRuleId(Object[] params) {
        Operator operator = (Operator) params[0];
        String ruleId = params[1].toString();
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        T21402BO t21402BO = (T21402BO) ContextUtil.getBean("T21402BO");
        TblMchntDiscountRule rule = null;
        if (StringUtils.isNotBlank(ruleId)) {
            rule = t21402BO.get(ruleId);
        }
        List<Object[]> dataList = new ArrayList<Object[]>();
        String sql1 = "select MAPPING_MCHNTCDONE,MCHT_NM from TBL_MCHT_BASE_INF where MAPPING_MCHNTCDONE is not null";
        String sql2 = "select MAPPING_MCHNTCDTWO,MCHT_NM from TBL_MCHT_BASE_INF where MAPPING_MCHNTCDTWO is not null";

        if (rule != null) {

            if (StringUtils.isNotBlank(rule.getAcquirersId())) {
                if (StringUtils.equals(rule.getAcquirersId(), "00")) {
                    dataList = commQueryDAO.findBySQLQuery(sql1);
                    if (dataList != null && dataList.size() > 0) {
                        for (Object[] obj : dataList) {
                            dataMap.put(obj[0].toString().trim(), obj[0].toString().trim() + "-一卡通商户号-" + obj[1]);

                        }
                    }
                }
                if (StringUtils.equals(rule.getAcquirersId(), "01")) {

                    dataList = commQueryDAO.findBySQLQuery(sql2);
                    Iterator<Object[]> iterator = dataList.iterator();
                    while (iterator.hasNext()) {
                        Object[] obj = iterator.next();
                        dataMap.put(obj[0].toString().trim(), obj[0].toString().trim() + "-银联商户号-" + obj[1]);

                    }

                }
            } else {
                List<Object[]> dataList1 = commQueryDAO.findBySQLQuery(sql1);
                List<Object[]> dataList2 = commQueryDAO.findBySQLQuery(sql2);
                Iterator<Object[]> iterator = dataList1.iterator();
                while (iterator.hasNext()) {
                    Object[] obj = iterator.next();
                    dataMap.put(obj[0].toString().trim(), obj[0].toString().trim() + "-一卡通商户号-" + obj[1]);

                }
                iterator = dataList2.iterator();
                while (iterator.hasNext()) {
                    Object[] obj = iterator.next();
                    dataMap.put(obj[0].toString().trim(), obj[0].toString().trim() + "-银联商户号-" + obj[1]);
                }
            }
        }

        return dataMap;
    }

    /**
     * 根据带属性的商户号查询匹配的终端
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getTermIdByMchntcd(Object[] params) {
        Operator operator = (Operator) params[0];
        String mchntcd = params[1].toString();

        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        List<Object[]> dataList = new ArrayList<Object[]>();
        if (StringUtils.isNotBlank(mchntcd)) {
            String[] mchntcdArr = mchntcd.split(",");
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mchntcdArr.length; i++) {
                sb.append("'").append(mchntcdArr[i]).append("'").append(",");
                if (i == mchntcdArr.length - 1) {
                    sb.append("'").append(mchntcdArr[i]).append("'");
                }
            }
            String sql1 = "select MAPPING_TERMIDONE,MCHT_CD from TBL_TERM_INF where 1=1" + " and TERM_STA in (1,2)" + " and MAPPING_MCHNTCDONE in("
                    + sb.toString() + ")";
            dataList = commQueryDAO.findBySQLQuery(sql1);
            Iterator<Object[]> iterator = dataList.iterator();
            while (iterator.hasNext()) {
                Object[] obj = iterator.next();
                dataMap.put(obj[0].toString().trim(), "一卡通终端号：" + obj[0].toString().trim());
            }

            String sql2 = "select MAPPING_TERMIDTWO,MCHT_CD from TBL_TERM_INF where 1=1" + " and TERM_STA in (1,2)" + " and MAPPING_MCHNTCDTWO in("
                    + sb.toString() + ")";

            dataList = commQueryDAO.findBySQLQuery(sql2);
            Iterator<Object[]> iterator2 = dataList.iterator();
            while (iterator2.hasNext()) {
                Object[] obj = iterator2.next();
                dataMap.put(obj[0].toString().trim(), "银联终端号：" + obj[0].toString().trim());
            }
        }
        return dataMap;
    }

    /**
     * 
     * //TODO 城市名称下拉框
     *
     * @param params
     * @return
     * @author lixiaomin
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getCityName(Object[] params) {

        Operator operator = (Operator) params[0];
        //上级地址码
        String areaName = (String) params[1];
        StringBuffer sql = new StringBuffer();
        StringBuffer wheresql = new StringBuffer();
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        if (operator == null) {
            return dataMap;
        }
        if (!StringUtil.isNull(areaName)) {
            wheresql.append(" and ROOTAREACODE = (select AREACODE from T_AREACODE where AREANAME='" + areaName + "')  ");
        }
        List<Object[]> dataList = new ArrayList<Object[]>();
        sql.append(" select AREANAME||'-'||AREACODE,AREANAME from T_AREACODE where UPAREACODE = '-'  ");
        sql.append(wheresql);
        dataList = commQueryDAO.findBySQLQuery(sql.toString());
        Iterator<Object[]> iterator2 = dataList.iterator();
        while (iterator2.hasNext()) {
            Object[] obj = iterator2.next();
            dataMap.put(obj[1].toString().trim(), obj[0].toString().trim());
        }
        return dataMap;
    }

    /**
     * 
     * //TODO 地区编码下拉框
     *
     * @param params
     * @return
     * @author lixiaomin
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getAreaCode(Object[] params) {
        Operator operator = (Operator) params[0];
        //上级地址码
        String cityName = (String) params[1];
        String provinceName = (String) params[2];
        StringBuffer sql = new StringBuffer();
        StringBuffer wheresql = new StringBuffer();
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        if (operator == null) {
            return dataMap;
        }
        if (!StringUtil.isNull(cityName)) {
            wheresql.append(" and UPAREACODE = (select AREACODE from T_AREACODE where AREANAME='" + cityName + "' ");
        }
        if (!StringUtil.isNull(provinceName)) {
            wheresql.append(" and ROOTAREACODE = (select AREACODE from T_AREACODE where AREANAME='" + provinceName + "')) ");
        }
        List<Object[]> dataList = new ArrayList<Object[]>();
        sql.append(" select AREACODE||'-'||AREANAME, AREACODE from T_AREACODE where 1=1  ");
        sql.append(wheresql);
        dataList = commQueryDAO.findBySQLQuery(sql.toString());
        Iterator<Object[]> iterator2 = dataList.iterator();
        while (iterator2.hasNext()) {
            Object[] obj = iterator2.next();
            dataMap.put(obj[1].toString().trim(), obj[0].toString().trim());
        }
        return dataMap;
    }
    /**
     * 获得所有仓库列表
     * @Description: TODO
     * @param @return   
     * @return LinkedHashMap<String,String>  
     * @throws
     * @author liujihui
     * @date 2019年4月15日
     */
    public static LinkedHashMap<String,String> getRailWhse(){
    	StringBuffer sql = new StringBuffer();
    	LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
    	List<Object[]> dataList = new ArrayList<Object[]>();
        sql.append(" select WHSE_CODE, WHSE_NAME from RAIL_WHSE_INFO where 1=1  ");
        dataList = commQueryDAO.findBySQLQuery(sql.toString());
        Iterator<Object[]> iterator2 = dataList.iterator();
        while (iterator2.hasNext()) {
            Object[] obj = iterator2.next();
            dataMap.put(obj[1].toString().trim(), obj[0].toString().trim());
        }
    	return dataMap;
    }
}
