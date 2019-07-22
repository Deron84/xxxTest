package com.huateng.struts.settle.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.settle.T80208BO;
import com.huateng.common.Constants;
import com.huateng.po.TblBrhAcct;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T80208Action extends BaseAction {
	
	T80208BO t80208BO = (T80208BO) ContextUtil.getBean("T80208BO");

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

	private String add() {
		
		TblBrhAcct brhAcct = new TblBrhAcct();
		brhAcct.setId(brhId);
		brhAcct.setAcct1(acct1);
		brhAcct.setAcct2(acct2);
		brhAcct.setLastUpdOprId(operator.getOprId());
		brhAcct.setLastUpdTs(CommonFunction.getCurrentDateTime());
		brhAcct.setLastUpdTxnId(getTxnId());
		return t80208BO.add(brhAcct);
	}

	private String delete() {

		return t80208BO.delete(brhId);
	}

	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		jsonBean.parseJSONArrayData(getParameterList());
		int len = jsonBean.getArray().size();
		
		TblBrhAcct tblBrhAcct = null;		
		List<TblBrhAcct> tblBrhAcctList = new ArrayList<TblBrhAcct>(len);
		
		for(int i = 0; i < len; i++) {
			
			tblBrhAcct = new TblBrhAcct();

			jsonBean.setObject(jsonBean.getJSONDataAt(i));			

			BeanUtils.setObjectWithPropertiesValue(tblBrhAcct, jsonBean,false);			
			BeanUtils.setNullValueWithValue(tblBrhAcct, "-", 0);
			tblBrhAcctList.add(tblBrhAcct);
		}
		
		t80208BO.update(tblBrhAcctList);
		
		return Constants.SUCCESS_CODE;
	}

	private String parameterList;

	public String getParameterList() {
		return parameterList;
	}

	public void setParameterList(String parameterList) {
		this.parameterList = parameterList;
	}
	
	private String brhId;
	private String acct1;
	/**
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}

	/**
	 * @param brhId the brhId to set
	 */
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	/**
	 * @return the acct1
	 */
	public String getAcct1() {
		return acct1;
	}

	/**
	 * @param acct1 the acct1 to set
	 */
	public void setAcct1(String acct1) {
		this.acct1 = acct1;
	}

	/**
	 * @return the acct2
	 */
	public String getAcct2() {
		return acct2;
	}

	/**
	 * @param acct2 the acct2 to set
	 */
	public void setAcct2(String acct2) {
		this.acct2 = acct2;
	}

	private String acct2;
}
