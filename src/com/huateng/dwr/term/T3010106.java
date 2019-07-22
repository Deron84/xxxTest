package com.huateng.dwr.term;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;

import com.huateng.bo.impl.mchnt.TblMchntService;
import com.huateng.common.TblMchntInfoConstants;
import com.huateng.dao.common.SqlDao;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-21
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class T3010106 {
	private static Logger log = Logger.getLogger(T3010106.class);
	public String getMchnt(String mchntId,HttpServletRequest request,
			HttpServletResponse response) {
		String jsonData = null;
		try {
			TblMchntService service = (TblMchntService) ContextUtil.getBean("TblMchntService");
			TblMchtBaseInfTmp tmpInfo = service.getBaseInfTmp(mchntId);
			String status = tmpInfo.getMchtStatus();
//			System.out.println(status);
			TblMchtBaseInf info = service.getMccByMchntId(mchntId);
			
//			TblMchtSettleInfTmp settleInfo = service.getSettleInfTmp(mchntId);
////            把清算信息存在临时域
//			info.setUpdOprId(settleInfo.getSettleAcct().substring(1));//这一段有点没看懂
			jsonData = JSONArray.fromObject(tmpInfo).toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}
	public String getTermParam(String termParam,HttpServletRequest request,
			HttpServletResponse response)
	{
		StringBuffer result = new StringBuffer();
		for (char c : termParam.toCharArray())
		{
			result.append(CommonFunction.fillString(Integer.toBinaryString(Integer.parseInt(String.valueOf(c),16)), '0', 4, false));
		}
		return result.toString();
	}
	
	/**获取商户所属mcc的所支持的交易*/
	public List getTxnSup(String mid){
		TblMchntService service = (TblMchntService) ContextUtil.getBean("TblMchntService");
		try {
			TblMchtBaseInfTmp tmpInfo = service.getBaseInfTmp(mid);
			String mcc = tmpInfo.getMcc();
			SqlDao dao = (SqlDao)ContextUtil.getBean("sqlDao");
			String sql = "SELECT TO_NUMBER(VALUE_ID) FROM TBL_MER_ROLE_FUNC WHERE TRIM(KEY_ID)='"+mcc+"'";
			List list = dao.queryForList(sql,String.class);
//			for(Object o:list){
//				System.out.println(o+""+o.getClass());
//			}
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		} 
		return null;
		
	}
	
	/**取商户连接类型*/
	public String getMchtConnType(String mid){
		try {
			TblMchntService service = (TblMchntService) ContextUtil.getBean("TblMchntService");
			TblMchtBaseInfTmp tmpInfo = service.getBaseInfTmp(mid);
			String connType = StringUtils.trim(tmpInfo.getConnType());
			
			return connType;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		} 
		return null;
	}
	
}