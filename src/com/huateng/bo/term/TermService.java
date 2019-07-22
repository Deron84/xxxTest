package com.huateng.bo.term;

import java.util.Map;

import com.huateng.po.TblTermInfTmpPK;


/**
 * project JSBConsole
 * date 2013-3-13
 * @author 樊东东
 */
public interface TermService {
	/**终端审核操作
	 * @throws Exception */
	String termAudit(Map<String,String> param) throws Exception;

	/**
	 * @param id
	 */
	void delte(TblTermInfTmpPK id);

	/**
	 * @param termIdSave
	 * @param mchtCdSave 
	 * @param count 
	 */
	String batchSave(String termIdSave, String mchtCdSave, String count);


}
