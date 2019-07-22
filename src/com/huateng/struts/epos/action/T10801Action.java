package com.huateng.struts.epos.action;

import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.impl.epos.TblEposService;
import com.huateng.common.Constants;
import com.huateng.po.epos.TblFirstPage;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.JSONBean;

public class T10801Action extends BaseSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TblEposService service;
	
	
	
	public void setService(TblEposService service) {
		this.service = service;
	}

	/**
	 * 添加
	 * @return
	 */
	public String add(){
		
		try {
			//判断是否已经存在
			TblFirstPage inf = new TblFirstPage();
			inf.setBrhId(brhId);
			inf.setPptMsg(pptMsg);
			inf.setCrtDate(CommonFunction.getCurrentDate());
			inf.setCrtOpr(getOperator().getOprId());
			
			if (null != service.get(inf.getBrhId())) {
				return returnService(Constants.ERR_EXIST);
			}

			rspCode = service.save(inf);
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	
	/**
	 * 更新
	 * @return
	 */
	public String update(){
		
		try {
			JSONBean bean = new JSONBean();
			bean.parseJSONArrayData(dataList);
			int len = bean.getArray().size();
			
			List<TblFirstPage> list = new ArrayList<TblFirstPage>();
			for(int i = 0; i < len; i++) {
				TblFirstPage page = new TblFirstPage();
				page.setBrhId(CommonFunction.fillString(bean.getJSONDataAt(i).getString("brhId"), ' ', 10, true));
				page.setPptMsg(bean.getJSONDataAt(i).getString("pptMsg"));
				page.setUpdDate(CommonFunction.getCurrentDate());
				page.setUpdOpr(getOperator().getOprId());
				list.add(page);
			}
			rspCode = service.updateFirstPage(list);
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	
	
	public String delete(){
		
		try {
			//判断是否已经存在
			if (null == service.get(brhId)) {
				return returnService(Constants.ERR_NO_DATA);
			}
			rspCode = service.delete(brhId);
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	
	private String brhId;
	
	private String pptMsg;
	
	private String dataList;
	
	
	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getPptMsg() {
		return pptMsg;
	}

	public void setPptMsg(String pptMsg) {
		this.pptMsg = pptMsg;
	}

	public String getDataList() {
		return dataList;
	}

	public void setDataList(String dataList) {
		this.dataList = dataList;
	}

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

}
