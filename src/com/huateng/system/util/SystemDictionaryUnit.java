package com.huateng.system.util;

import java.io.InputStream;
import java.util.LinkedHashMap;

import javax.servlet.ServletContext;

import org.dom4j.DocumentException;

import com.huateng.common.Constants;
/**
 * 
 * Title:系统字典工具
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
public class SystemDictionaryUnit {
	//key : tbl_nm    value: HashMap(key : fld_nm value : HashMap(key : fld_val value : fld_desc ) )

	private static LinkedHashMap<String,Object> sysDicMap = new LinkedHashMap<String,Object>();
	
	/**
	 * 初始化SysDic.xml
	 * @param context
	 * @throws DocumentException 
	 */
	public static void initSysDic(ServletContext context) throws DocumentException {
		InputStream inputStream = context.getResourceAsStream(Constants.SYSDIC_CONTEXTPATH);
		XmlDBParser.parseSysDic(inputStream);
	}
	/**
	 * add system dictionary record
	 * @param tbl_nm
	 * @param fld_nm
	 * @param fld_val
	 * @param fld_desc
	 */
	@SuppressWarnings("unchecked")
	public static void addRecord(String tbl_nm,String fld_nm,String fld_val,String fld_desc){
		
		LinkedHashMap subMap,subSubMap;
		
		if(sysDicMap.containsKey(tbl_nm)){
			
			subMap = (LinkedHashMap)sysDicMap.get(tbl_nm);
			if(subMap.containsKey(fld_nm)){
				subSubMap = (LinkedHashMap)subMap.get(fld_nm);
				subSubMap.put(fld_val, fld_desc);
			}else{
				subSubMap = new LinkedHashMap();
				subSubMap.put(fld_val, fld_desc);
				subMap.put(fld_nm, subSubMap);
			}
		}else{
			subSubMap = new LinkedHashMap();
			subMap = new LinkedHashMap();
			subSubMap.put(fld_val, fld_desc);
			subMap.put(fld_nm, subSubMap);
			sysDicMap.put(tbl_nm, subMap);
		}
	}

	/**
	 * @param tbl_nm
	 * @param fld_nm
	 * @param fld_val
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getFieldDesc(String tbl_nm,String fld_nm,String fld_val){
		LinkedHashMap subMap;
		LinkedHashMap subsubMap;
		if(sysDicMap.containsKey(tbl_nm)){
			subMap = (LinkedHashMap)sysDicMap.get(tbl_nm);
			if(subMap.containsKey(fld_nm)){
				subsubMap = (LinkedHashMap)subMap.get(fld_nm);
				if(subsubMap.containsKey(fld_val)){
					return (String)subsubMap.get(fld_val);
				}else{
					return fld_val;
				}
			}else{
				return fld_val;
			}
		}else{
			return fld_val;
		}
	}

	/**
	 * @param tbl_nm
	 * @param fld_nm
	 * @param fld_val
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String chkFieldDesc(String tbl_nm,String fld_nm,String fld_val){
		LinkedHashMap subMap;
		LinkedHashMap subsubMap;
		if(sysDicMap.containsKey(tbl_nm)){
			subMap = (LinkedHashMap)sysDicMap.get(tbl_nm);
			if(subMap.containsKey(fld_nm)){
				subsubMap = (LinkedHashMap)subMap.get(fld_nm);
				if(subsubMap.containsKey(fld_val)){
					return (String)subsubMap.get(fld_val);
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	/**
	 *
	 * @param tbl_nm
	 * @param fld_nm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static  LinkedHashMap getAllFieldDesc(String tbl_nm,String fld_nm){
		LinkedHashMap subMap;
		LinkedHashMap subsubMap;
		if(sysDicMap.containsKey(tbl_nm)){
			subMap = (LinkedHashMap)sysDicMap.get(tbl_nm);
			if(subMap.containsKey(fld_nm)){
				subsubMap = (LinkedHashMap)subMap.get(fld_nm);
				return subsubMap;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
}