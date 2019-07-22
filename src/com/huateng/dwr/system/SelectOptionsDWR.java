package com.huateng.dwr.system;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.select.SelectOption;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.JSONBean;

/**
 * Title:异步读取下拉列表内容
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-12
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
public class SelectOptionsDWR {

    private static Logger log = Logger.getLogger(SelectOptionsDWR.class);

    /**
     * 
     * //TODO 终端名称
     *
     * @param txnId
     * @param request
     * @param response
     * @return
     * @author hanyongqing
     */
    public String getTermData(String txnId, String ylMchntNo, HttpServletRequest request, HttpServletResponse response) {

        String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";

        try {

            //获得操作员信息
            Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
            LinkedHashMap<String, String> dataMap = SelectOption.getSelectView(txnId, new Object[] { operator, ylMchntNo });
            Iterator<String> iter = dataMap.keySet().iterator();

            if (iter.hasNext()) {

                Map<String, Object> jsonDataMap = new HashMap<String, Object>();
                LinkedList<Object> jsonDataList = new LinkedList<Object>();
                Map<String, String> tmpMap = null;
                String key = null;

                while (iter.hasNext()) {

                    tmpMap = new LinkedHashMap<String, String>();
                    key = iter.next();
                    tmpMap.put("valueField", key);
                    tmpMap.put("displayField", dataMap.get(key));
                    jsonDataList.add(tmpMap);
                }
                jsonDataMap.put("data", jsonDataList);
                jsonData = JSONBean.genMapToJSON(jsonDataMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return jsonData;
    }

    /**
     * 
     * //TODO 商户名称下拉框
     *
     * @param txnId
     * @param request
     * @param response
     * @return
     * @author hanyongqing
     */
    public String getMchntData(String txnId, HttpServletRequest request, HttpServletResponse response) {

        String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";

        try {

            //获得操作员信息
            Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
            LinkedHashMap<String, String> dataMap = SelectOption.getSelectView(txnId, new Object[] { operator });
            Iterator<String> iter = dataMap.keySet().iterator();

            if (iter.hasNext()) {

                Map<String, Object> jsonDataMap = new HashMap<String, Object>();
                LinkedList<Object> jsonDataList = new LinkedList<Object>();
                Map<String, String> tmpMap = null;
                String key = null;

                while (iter.hasNext()) {

                    tmpMap = new LinkedHashMap<String, String>();
                    key = iter.next();
                    tmpMap.put("valueField", key);
                    tmpMap.put("displayField", dataMap.get(key));
                    jsonDataList.add(tmpMap);
                }
                jsonDataMap.put("data", jsonDataList);
                jsonData = JSONBean.genMapToJSON(jsonDataMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return jsonData;
    }

    /**
     * 
     * //TODO 支付方式下拉框
     *
     * @param txnId
     * @param request
     * @param response
     * @return
     * @author hanyongqing
     */
    public String getPayTypeData(String txnId, HttpServletRequest request, HttpServletResponse response) {

        String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";

        try {

            //获得操作员信息
            Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
            LinkedHashMap<String, String> dataMap = SelectOption.getSelectView(txnId, new Object[] { operator });
            Iterator<String> iter = dataMap.keySet().iterator();

            if (iter.hasNext()) {

                Map<String, Object> jsonDataMap = new HashMap<String, Object>();
                LinkedList<Object> jsonDataList = new LinkedList<Object>();
                Map<String, String> tmpMap = null;
                String key = null;

                while (iter.hasNext()) {

                    tmpMap = new LinkedHashMap<String, String>();
                    key = iter.next();
                    tmpMap.put("valueField", key);
                    tmpMap.put("displayField", dataMap.get(key));
                    jsonDataList.add(tmpMap);
                }
                jsonDataMap.put("data", jsonDataList);
                jsonData = JSONBean.genMapToJSON(jsonDataMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return jsonData;
    }

    /**
     * 根据下拉列表所显示数据类型显示内容
     * @param txnId
     * @return
     */
    public String getComboData(String txnId, HttpServletRequest request, HttpServletResponse response) {

        String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";

        try {

            //获得操作员信息
            Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
            LinkedHashMap<String, String> dataMap = SelectOption.getSelectView(txnId, new Object[] { operator });
            Iterator<String> iter = dataMap.keySet().iterator();

            if (iter.hasNext()) {

                Map<String, Object> jsonDataMap = new HashMap<String, Object>();
                LinkedList<Object> jsonDataList = new LinkedList<Object>();
                Map<String, String> tmpMap = null;
                String key = null;

                while (iter.hasNext()) {

                    tmpMap = new LinkedHashMap<String, String>();
                    key = iter.next();
                    tmpMap.put("valueField", key);
                    tmpMap.put("displayField", dataMap.get(key));
                    jsonDataList.add(tmpMap);
                }
                jsonDataMap.put("data", jsonDataList);
                jsonData = JSONBean.genMapToJSON(jsonDataMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return jsonData;
    }

    /**
     * 根据下拉列表所显示数据类型显示内容，可带参数
     * @param txnId
     * @return
     */
    public String getComboDataWithParameter(String txnId, String parameter, HttpServletRequest request, HttpServletResponse response) {

        String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";
        try {
            //获得操作员信息
            Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
            LinkedHashMap<String, String> dataMap = SelectOption.getSelectView(txnId, new Object[] { operator, parameter });
            Iterator<String> iter = dataMap.keySet().iterator();
            if (iter.hasNext()) {
                Map<String, Object> jsonDataMap = new HashMap<String, Object>();
                LinkedList<Object> jsonDataList = new LinkedList<Object>();
                Map<String, String> tmpMap = null;
                String key = null;
                while (iter.hasNext()) {
                    tmpMap = new LinkedHashMap<String, String>();
                    key = iter.next();
                    tmpMap.put("valueField", key);
                    tmpMap.put("displayField", dataMap.get(key));
                    jsonDataList.add(tmpMap);
                }
                jsonDataMap.put("data", jsonDataList);
                jsonData = JSONBean.genMapToJSON(jsonDataMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return jsonData;
    }

    /**
     * 获得数据集
     * @param txnId
     * @param request
     * @param response
     * @return
     * 2010-8-18上午11:36:58
     */
    public LinkedHashMap<String, String> getDataMap(String txnId, HttpServletRequest request, HttpServletResponse response) {
        try {
            //获得操作员信息
            Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
            LinkedHashMap<String, String> dataMap = SelectOption.getSelectView(txnId, new Object[] { operator });
            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 获得权限集
     * @param txnId
     * @param request
     * @param response
     * @return
     * 2010-8-18上午11:36:58
     */
    public String getFuncAllData(String txnId, HttpServletRequest request, HttpServletResponse response) {
        String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";
        try {
            //获得操作员信息
            Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
            LinkedHashMap<String, String> dataMap = SelectOption.getSelectView(txnId, new Object[] { operator });
            Iterator<String> iter = dataMap.keySet().iterator();
            if (iter.hasNext()) {
                Map<String, Object> jsonDataMap = new HashMap<String, Object>();
                LinkedList<Object> jsonDataList = new LinkedList<Object>();
                Map<String, String> tmpMap = null;
                String key = null;
                while (iter.hasNext()) {
                    tmpMap = new LinkedHashMap<String, String>();
                    key = iter.next();
                    tmpMap.put("valueField", key);
                    tmpMap.put("displayField", dataMap.get(key));
                    jsonDataList.add(tmpMap);
                }
                jsonDataMap.put("data", jsonDataList);
                jsonData = JSONBean.genMapToJSON(jsonDataMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return jsonData;
    }

    /**
    * 加载签约商户信息
    * 
    * @param txnId
    * @return
    */
    public String getMCHTSignAcc(HttpServletRequest request, HttpServletResponse response) {
        String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";
        try {
            String sql = "SELECT MCHT_NO,MCHT_NM FROM TBL_MCHT_BASE_INF WHERE MCHT_STATUS='0' AND MCHT_FLAG1='7'";

            ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");

            List<Object[]> list = commQueryDAO.findBySQLQuery(sql);

            if (list != null && !list.isEmpty()) {
                Map<String, Object> jsonDataMap = new HashMap<String, Object>();
                LinkedList<Object> jsonDataList = new LinkedList<Object>();
                Map<String, String> tmpMap = null;
                for (Object[] obj : list) {
                    tmpMap = new LinkedHashMap<String, String>();
                    tmpMap.put("valueField", String.valueOf(obj[0]));
                    tmpMap.put("displayField", String.valueOf(obj[0]).trim() + "-" + String.valueOf(obj[1]).trim());
                    jsonDataList.add(tmpMap);
                }
                jsonDataMap.put("data", jsonDataList);
                jsonData = JSONBean.genMapToJSON(jsonDataMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return jsonData;
    }

    /**
     * 
     * //TODO 区域名称
     *
     * @param txnId
     * @param request
     * @param response
     * @return
     * @author lixiaomin
     */
    public String getAreaCodeNew(String areacode, String city, String province, HttpServletRequest request, HttpServletResponse response) {

        String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";

        try {

            //获得操作员信息
            Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
            LinkedHashMap<String, String> dataMap = SelectOption.getSelectView(areacode, new Object[] { operator, city, province });
            Iterator<String> iter = dataMap.keySet().iterator();

            if (iter.hasNext()) {

                Map<String, Object> jsonDataMap = new HashMap<String, Object>();
                LinkedList<Object> jsonDataList = new LinkedList<Object>();
                Map<String, String> tmpMap = null;
                String key = null;

                while (iter.hasNext()) {

                    tmpMap = new LinkedHashMap<String, String>();
                    key = iter.next();
                    tmpMap.put("valueField", key);
                    tmpMap.put("displayField", dataMap.get(key));
                    jsonDataList.add(tmpMap);
                }
                jsonDataMap.put("data", jsonDataList);
                jsonData = JSONBean.genMapToJSON(jsonDataMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return jsonData;
    }
}