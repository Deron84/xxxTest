package com.huateng.struts.base.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.base.T10207BO;
import com.huateng.common.Constants;
import com.huateng.po.TblBrhTlrInfo;
import com.huateng.po.TblBrhTlrInfoPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:虚拟柜员
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-17
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T10207Action extends BaseAction {
	
	
	T10207BO t10207BO = (T10207BO) ContextUtil.getBean("T10207BO");
	

	private String brhId;
	private String tlrId;
	private String tlrSta;
	private String resv1;
	
	private String tblBrhTlrInfoList;

	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
			if("add".equals(method)) {
				rspCode = add();			
			} else if("delete".equals(method)) {
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，虚拟柜员维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	/**
	 * add city code
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String add(){
		TblBrhTlrInfo  tblBrhTlrInfo = new TblBrhTlrInfo();		
		TblBrhTlrInfoPK pk = new TblBrhTlrInfoPK(tlrId,brhId);
		if(t10207BO.get(pk) != null) {
			return "该虚拟柜员号已经被使用";
		}
		tblBrhTlrInfo.setId(pk);
		tblBrhTlrInfo.setTlrSta("0");
		tblBrhTlrInfo.setLastUpdOprId(operator.getOprId());
		tblBrhTlrInfo.setRecCrtTs(CommonFunction.getCurrentDateTime());
		tblBrhTlrInfo.setRecUpdTs(CommonFunction.getCurrentDateTime());
		t10207BO.createTblBrhTlrInfo(tblBrhTlrInfo);
		log("添加虚拟柜员成功。操作员编号：" + operator.getOprId()+ "，机构编号：" + getBrhId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * delete city code
	 * @return
	 */
	private String delete() {
		TblBrhTlrInfoPK pk = new TblBrhTlrInfoPK(tlrId,brhId);
		t10207BO.delete(pk);
		log("删除虚拟柜员成功。操作员编号：" + operator.getOprId()+ "，机构编号：" + brhId+  "，虚拟柜员号：" + tlrId);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * update city code
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws ASException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		jsonBean.parseJSONArrayData(getTblBrhTlrInfoList());
//		
		int len = jsonBean.getArray().size();
//		
		TblBrhTlrInfo tblBrhTlrInfo = null;
//	
		List<TblBrhTlrInfo> tblBrhTlrInfoList = new ArrayList<TblBrhTlrInfo>(len);
////		
		for(int i = 0; i < len; i++) {
			tlrId = jsonBean.getJSONDataAt(i).getString("tlrId");
			brhId = jsonBean.getJSONDataAt(i).getString("brhId");
			tblBrhTlrInfo = t10207BO.get(new TblBrhTlrInfoPK(tlrId,brhId));		
			tblBrhTlrInfoList.add(tblBrhTlrInfo);
		}
			t10207BO.update(tblBrhTlrInfoList);
		log("修改虚拟柜员成功。操作员编号：" + operator.getOprId()+ "，机构编号：" + getBrhId());
		return Constants.SUCCESS_CODE;
	}

	

	public T10207BO getT10207BO() {
		return t10207BO;
	}

	public void setT10207BO(T10207BO t10207bo) {
		t10207BO = t10207bo;
	}

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getTlrId() {
		return tlrId;
	}

	public void setTlrId(String tlrId) {
		this.tlrId = tlrId;
	}

	public String getTlrSta() {
		return tlrSta;
	}

	public void setTlrSta(String tlrSta) {
		this.tlrSta = tlrSta;
	}

	public String getResv1() {
		return resv1;
	}

	public void setResv1(String resv1) {
		this.resv1 = resv1;
	}

	public String getTblBrhTlrInfoList() {
		return tblBrhTlrInfoList;
	}

	public void setTblBrhTlrInfoList(String tblBrhTlrInfoList) {
		this.tblBrhTlrInfoList = tblBrhTlrInfoList;
	}

	
	
	
	
}
