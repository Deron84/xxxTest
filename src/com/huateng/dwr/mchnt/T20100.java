/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-7-5       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.dwr.mchnt;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.huateng.bo.mchnt.T20701BO;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.dao.iface.base.TblMbfBankInfoDAO;
import com.huateng.po.TblMbfBankInfo;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;
import com.huateng.system.util.SocketConnect;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T20100.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-7-5
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T20100 {
	private static Logger log = Logger.getLogger(T20100.class);
	private T20701BO t20701BO=(T20701BO)ContextUtil.getBean("T20701BOTarget");
	public String getInfo(String cnapsbankno,HttpServletRequest request,
			HttpServletResponse response) {
		
		String jsonData = null;
		try {
			TblMbfBankInfoDAO MbfBrhInfoDAO = (TblMbfBankInfoDAO) ContextUtil.getBean("MbfBrhInfoDAO");
			TblMbfBankInfo info = MbfBrhInfoDAO.get(cnapsbankno);
			// 把清算信息存在临时域
			if(null==info){
				return "0";
			}else {
				jsonData = JSONArray.fromObject(info).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	public String deleteImgFile(String fileName){
		
		try {
			String basePath = SysParamUtil
					.getParam(SysParamConstants.FILE_UPLOAD_DISK);
			basePath = basePath.replace("\\", "/");
			File file = new File(basePath + fileName);
			if (file.exists()) {
				file.delete();
			}
			return "S";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "E";
	}
	
	
	/**
	 * 验证商户结算账号
	 * @param txnCode
	 * @param account
	 * @param type
	 * @return
	 * 2011-7-19上午11:29:27
	 * @throws IOException 
	 */
	public HashMap<String, String> verifyAccount(String flag,String account,String type) throws IOException {
		
		HashMap<String, String> map = new LinkedHashMap<String, String>();
		
		String txnCode = "";
		if ("A".equals(flag)) {
			txnCode = "1521062"; //本行对公账户
		} else {
			txnCode = "1511062"; //本行对私账户或单位卡
		}
		
		
		Random r = new Random();
//		交易码
		StringBuffer req = new StringBuffer(txnCode);
//		时间
		req.append(CommonFunction.getCurrentDateTime());
//		流水号
		int c = r.nextInt(9999);
		req.append(CommonFunction.fillString(String.valueOf(c), '0', 4, false));
		
//		账号类型
		if ("A".equals(flag)) {
			type = "01"; //本行对公账户'
		} else if ("P".equals(flag)) {
			type = "02"; //本行对私账户及单位卡
		}
		req.append(type);
		
//		账号长度
		if(!StringUtil.isEmpty(account))
			req.append(account.length());
//		账号
		req.append(CommonFunction.fillString(String.valueOf(account), ' ', 80, true));
		String len = Integer.toHexString(req.toString().length());
		len = new String(CommonFunction.hexStringToByte(len));
		len = new String(CommonFunction.hexStringToByte("00"))+len;
		SocketConnect socket = null;
		try {
			
			log.info("报文:["+len+req.toString()+"]");
			socket = new SocketConnect(len+req.toString());
			
			if (null != socket) {
				socket.run("GBK");
				String resp = socket.getRsp();
				
				if(resp.substring(4,6).equals("00")){
					map.put("result", "S");
					map.put("msg", "验证完成");
				} else {
					map.put("result", "F");
					map.put("msg", "无该账户信息");
				}
				if(txnCode.equals("1511062"))
				{
					map.put("accountNo", resp.substring(31, 70).trim());
					map.put("accountNm", resp.substring(71,83).trim());
					String certType = resp.substring(91,92).trim();
					if(certType.equals("2"))
					{
						map.put("licenceNo", resp.substring(92,107).trim());
						
					}
				}
				else{
					map.put("accountNm", resp.substring(71,102).trim());
				}
				
				
				
				return map;
			}
		} catch (UnknownHostException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		} finally {
			try {
				if (null != socket) {
					socket.close();
				}
			} catch (Exception e) {}
		}
		
		map.put("result", "E");
		map.put("msg", "请求账号验证服务异常");
		
		return map;
	}
	
	/**自动生成商户号*/
	public String getMchtNoByValue(String areaNo,String mcc){
		String idStr = "310" + areaNo + mcc;  //areaNo所在地区  商户mcc
		return GenerateNextId.getMchntId(idStr);
	}
	/**获取计费算法类型
	 * @throws Exception */
	public String getFeeFlag(String id) throws Exception{
		return t20701BO.getTblInfDiscCd(id).getLastOperIn();
	}

	
}
