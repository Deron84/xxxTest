package com.huateng.system.util;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.huateng.common.Constants;

/**
 * 
 * Title:解析系统字典XML
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-12
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class XmlDBParser {


	/** memeber variable: dom4j SAXReader. */
	private static SAXReader saxReader = new SAXReader();;

	/**
	 * 解析 SysDic.xml
	 *
	 * @param configFile
	 * @throws DocumentException 
	 * @throws AppException
	 */
	@SuppressWarnings("unchecked")
	public static void parseSysDic(InputStream inputStream) throws DocumentException {
		Document document = saxReader.read(inputStream);
		List fieldList = document.selectNodes("//" + Constants.TABLE_ROOT + "/" + Constants.TABLE_ROW);
		Element field;
		for (int i = 0; i < fieldList.size(); i++) {
			field = (Element) fieldList.get(i);
			Node tblNmNode = field.selectSingleNode(Constants.SYS_DIC_TBL_NM);
			Node fldNmNode = field.selectSingleNode(Constants.SYS_DIC_FLD_NM);
			Node fldValNode = field.selectSingleNode(Constants.SYS_DIC_FLD_VAL);
			Node fldDescNode = field.selectSingleNode(Constants.SYS_DIC_FLD_DESC);
			SystemDictionaryUnit.addRecord(tblNmNode.getText(), fldNmNode.getText(), fldValNode.getText(), fldDescNode.getText());
		}
	}
}