package com.huateng.common.select;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.directwebremoting.util.Logger;

import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SystemDictionaryUnit;
/**
 * 
 * Title:解析SelectOptions
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2009-12-27
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
public class SelectOption {

	private static Logger log = Logger.getLogger(SelectOption.class);
    public static LinkedHashMap<String, String> getSelectView(String txnId , Object[] args) throws Exception {
    	LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        SelectOptionExtractMethod extractMethod = SelectOptionUnit.getSelectMethod(txnId.trim());
        return parseExtractMethod(extractMethod, args, map);
    }

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> parseExtractMethod(SelectOptionExtractMethod extractMethod,
    		Object[] args, LinkedHashMap<String, String> map) throws Exception {
        String extractMode = extractMethod.getExtractMode().trim();

        // 静态XML
        if (Constants.SLT_MODE_STATIC_VALUE.equalsIgnoreCase(extractMode)) {
            SelectStaticMode selectStaticMode = extractMethod
                    .getSelectStaticMode();
            String tblNm = selectStaticMode.getTblNm().trim();
            String fldNm = selectStaticMode.getFldNm().trim();
            map = SystemDictionaryUnit.getAllFieldDesc(tblNm, fldNm);

        // SQL
        } else if (Constants.SLT_MODE_SQL_VALUE.equalsIgnoreCase(extractMode)) {
            SelectSqlMode selectSqlMode = extractMethod.getSelectSqlMode();
            String queryDao = selectSqlMode.getQueryDao();
            String sql = selectSqlMode.getSql().trim();
            ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil
                    .getBean(queryDao);
            
            List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
            Iterator<Object[]> iterator = dataList.iterator();
            while (iterator.hasNext()) {
                Object[] obj = iterator.next();
                String value = obj[0].toString().trim();
                String text = obj[1].toString().trim();
                map.put(value, text);
            }

            // 接口方法
        } else if (Constants.SLT_MODE_DYNAMIC_VALUE.equalsIgnoreCase(extractMode)) {
        	if(args == null) {
        		return null;
        	}
            SelectDynamicMode dynamicMode = extractMethod
                    .getSelectDynamicMode();
            String method = dynamicMode.getMethod().trim();
            map = (LinkedHashMap<String, String>) SelectMethod.class.getMethod(method,
                    new Class[] { Object[].class }).invoke(SelectMethod.class,
                    new Object[] { args });

            // 异常
        } else {
        	log.info("SelectOption方法没有找到");
            throw new Exception("SelectOption方法没有找到");
        }
        return map;
    }

}
