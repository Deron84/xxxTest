/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-5       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
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
package com.huateng.struts.base.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.huateng.bo.base.T10204BO;
import com.huateng.bo.base.T10210BO;
import com.huateng.bo.impl.base.T10210BOTarget;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.po.CstSysParam;
import com.huateng.po.CstSysParamPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.JSONBean;

/**
 * Title:系统参数维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-5
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T10210Action extends BaseAction {
	
	T10210BO t10210BO = (T10210BO) ContextUtil.getBean("T10210BO");
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		// add method
		if("add".equals(method)) {
			rspCode = add();
		} else if("delete".equals(method)) {
			rspCode = delete();
		} else if("update".equals(method)) {
			rspCode = update();
		}
		return rspCode;
	}
	
	/**
	 * add system parameter information
	 * @return
	 */
	private String add() {
		Map<String, String> info=new HashMap<String, String>();
		info.put("usage_key", this.getUsage_key());
		info.put("line_index", this.getLine_index());
		info.put("local_addr", this.getLocal_addr());
		info.put("remote_addr",this.getRemote_addr() );
		info.put("in_sock_num",this.getIn_sock_num() );
		info.put("out_sock_num",this.getOut_sock_num() );
		info.put("line_dsp", this.getLine_dsp());
		System.out.println(info.toString());
		String sqlSelect="select * from tbl_line_cfg where usage_key='"+usage_key+"'";
		List list=t10210BO.get(sqlSelect);
		if(list!=null&&list.size()>0){//key已经存在
			return ErrorCode.T10210_01;
		}
		StringBuffer sbuf=new StringBuffer();
		sbuf.append("insert into tbl_line_cfg(usage_key,srv_id,line_index,local_addr,remote_addr,in_sock_num,out_sock_num,line_dsp) values ('");
		sbuf.append(usage_key+"','1','");
		sbuf.append(line_index+"','");
		sbuf.append(local_addr+"','");
		sbuf.append(remote_addr+"','");
		sbuf.append(in_sock_num+"','");
		sbuf.append(out_sock_num+"','");
		sbuf.append(line_dsp+"')");
		
		return t10210BO.add(sbuf.toString());
	}
	
	/**
	 * delete system parameter information
	 * @return
	 */
	private String delete() {
		if(usage_key.equals("9999")){
			return ErrorCode.T10210_04;
		}
		String delSql="delete from tbl_line_cfg where usage_key='"+usage_key+"'";
		return t10210BO.delete(delSql);
		
	}
	
	/**
	 * update the parameter information list
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String update() throws IllegalAccessException,InvocationTargetException, NoSuchMethodException {
		JSONBean jsonBean = new JSONBean();
		jsonBean.parseJSONArrayData(parameterList);
		int len = jsonBean.getArray().size();
		String[] sqls = new String[len];
		for (int i = 0; i < len; i++) {
			JSONObject jsonObj = jsonBean.getJSONDataAt(i);
			jsonObj.getString("usage_key");
			StringBuffer sbuf = new StringBuffer();
			sbuf.append("update tbl_line_cfg set line_index='"
					+ jsonObj.getString("line_index") + "',");
			sbuf.append("local_addr='" + jsonObj.getString("local_addr") + "',");
			sbuf.append("remote_addr='" + jsonObj.getString("remote_addr")
					+ "',");
			sbuf.append("in_sock_num='" + jsonObj.getString("in_sock_num")
					+ "',");
			sbuf.append("out_sock_num='" + jsonObj.getString("out_sock_num")
					+ "',");
			sbuf.append("line_dsp='" + jsonObj.getString("line_dsp") + "'");
			sbuf.append(" where usage_key='" + jsonObj.getString("usage_key")
					+ "'");
			sqls[i] = sbuf.toString();
			System.out.println(sbuf.toString());
		}
		t10210BO.update(sqls);
		return Constants.SUCCESS_CODE;
	}

	private String usage_key;
	private String line_index;
	private String local_addr;
	private String remote_addr;
	private String in_sock_num;
	private String out_sock_num;
	private String line_dsp;
	
	
	

	public String getUsage_key() {
		return usage_key;
	}

	public void setUsage_key(String usage_key) {
		this.usage_key = usage_key;
	}

	public String getLine_index() {
		return line_index;
	}

	public void setLine_index(String line_index) {
		this.line_index = line_index;
	}

	public String getLocal_addr() {
		return local_addr;
	}

	public void setLocal_addr(String local_addr) {
		this.local_addr = local_addr;
	}

	public String getRemote_addr() {
		return remote_addr;
	}

	public void setRemote_addr(String remote_addr) {
		this.remote_addr = remote_addr;
	}

	public String getIn_sock_num() {
		return in_sock_num;
	}

	public void setIn_sock_num(String in_sock_num) {
		this.in_sock_num = in_sock_num;
	}

	public String getOut_sock_num() {
		return out_sock_num;
	}

	public void setOut_sock_num(String out_sock_num) {
		this.out_sock_num = out_sock_num;
	}

	public String getLine_dsp() {
		return line_dsp;
	}

	public void setLine_dsp(String line_dsp) {
		this.line_dsp = line_dsp;
	}

	/**the list of parameter to update*/
	private String parameterList;
	
	/**
	 * @return the parameterList
	 */
	public String getParameterList() {
		return parameterList;
	}

	/**
	 * @param parameterList the parameterList to set
	 */
	public void setParameterList(String parameterList) {
		this.parameterList = parameterList;
	}
}
