package com.huateng.common.select;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.huateng.common.Constants;
/**
 * 
 * Title:SelectOptions 工具类
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-12
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class SelectOptionUnit {
	
	private static HashMap<String, SelectOptionExtractMethod> selectMap = 
								new HashMap<String, SelectOptionExtractMethod>(10);
	public static void initSelectMethod(String txnId,SelectOptionExtractMethod extractMethod) {
		selectMap.put(txnId.trim(), extractMethod);
	}
	
	public static SelectOptionExtractMethod getSelectMethod(String txnId)
											throws Exception {
		if(!selectMap.containsKey(txnId.trim())) {
			throw new Exception("没有找到指定的编号->[ " + txnId + " ]");
		}
		return selectMap.get(txnId.trim());
	}
	
	/**
	 * 初始化下拉菜单
	 * @param context
	 * @throws DocumentException 
	 */
    @SuppressWarnings("unchecked")
	public static void initSelectOptions(ServletContext context) throws DocumentException {
        SAXReader reader = new SAXReader();
        org.dom4j.Document document = null;
        document = reader.read(context.getResourceAsStream(Constants.SELECTOPTION_CONTEXTPATH));
        Element root = document.getRootElement();
        List<Element> txnElements = root.elements(Constants.SLT_TXN_ID);
        for(int i = 0,n = txnElements.size(); i < n; i++) {
            //获得交易
            Element txnElement = txnElements.get(i);
            //获得交易编号
            String id = txnElement.attributeValue(Constants.SLT_XML_ID).trim();
            //数据抽取方式
            Element extractType = txnElement.element(Constants.SLT_EXTRACT_TYPE);
            String value = extractType.attributeValue(Constants.SLT_ATTR_VALUE);
            //静态方法
            Element staticMode = extractType.element(Constants.SLT_MODE_STATIC);
            Element tblNmElement = staticMode.element(Constants.SYS_DIC_TBL_NM);
            Element fldNmElement = staticMode.element(Constants.SYS_DIC_FLD_NM);
            //表名称
            String tblNm = tblNmElement.getText();
            String fldNm = fldNmElement.getText();
            //SQL方法
            Element sqlModeElement = extractType.element(Constants.SLT_MODE_SQL);
            Element sqlElement = sqlModeElement.element(Constants.SLT_MODE_SQL_SQL);
            Element queryDaoElement = sqlModeElement.element(Constants.SLT_MODE_QUERYDAO);
            //SQL语句
            String sql = sqlElement.getText();
            String queryDao = queryDaoElement.getText();
            //动态抽取方式
            Element dynamicModeElement = extractType.element(Constants.SLT_MODE_DYNAMIC);
            Element methodModeElement = dynamicModeElement.element(Constants.SLT_MODE_DYNAMIC_METHOD);
            String methodNm = methodModeElement.attributeValue(Constants.SLT_MODE_DYNAMIC_ATTR_NAME);

            //下拉框方法初始化
            SelectOptionExtractMethod extractMethod = new SelectOptionExtractMethod();
            //下拉框读静态XML配置模型初始化
            SelectStaticMode selectStaticMode = new SelectStaticMode();
            //下拉框读数据库配置模型初始化
            SelectSqlMode selectSqlMode = new SelectSqlMode();
            //下拉框动态配置模型初始化
            SelectDynamicMode selectDynamicMode = new SelectDynamicMode();

            selectStaticMode.setFldNm(fldNm);
            selectStaticMode.setTblNm(tblNm);
            selectSqlMode.setQueryDao(queryDao);
            selectSqlMode.setSql(sql);
            selectDynamicMode.setMethod(methodNm);

            extractMethod.setSelectStaticMode(selectStaticMode);
            extractMethod.setSelectSqlMode(selectSqlMode);
            extractMethod.setSelectDynamicMode(selectDynamicMode);
            extractMethod.setExtractMode(value);
            
            SelectOptionUnit.initSelectMethod(id, extractMethod);
        }
    }
}
