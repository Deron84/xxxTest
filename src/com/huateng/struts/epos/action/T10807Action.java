package com.huateng.struts.epos.action;

import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.impl.epos.TblEposService;
import com.huateng.common.Constants;
import com.huateng.po.epos.TblRspMsg;
import com.huateng.po.epos.TblRspMsgPK;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.JSONBean;

public class T10807Action extends BaseSupport{

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
			TblRspMsgPK id = new TblRspMsgPK();
			id.setChgNo(chgNo);
			id.setRspNo(rspNo);
			
			if (null != service.get(id)) {
				return returnService(Constants.ERR_EXIST);
			}
			TblRspMsg inf = new TblRspMsg();
			inf.setId(id);
			inf.setErrNo(errNo);
			inf.setErrMsg(errMsg);
			inf.setCrtDate(CommonFunction.getCurrentDate());
			inf.setUpdDate(CommonFunction.getCurrentDate());
			
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
			
			List<TblRspMsg> list = new ArrayList<TblRspMsg>();
			for(int i = 0; i < len; i++) {
				
				TblRspMsgPK id = new TblRspMsgPK();
				id.setChgNo(bean.getJSONDataAt(i).getString("chgNo"));
				id.setRspNo(bean.getJSONDataAt(i).getString("rspNo"));
				
				TblRspMsg inf = service.get(id);
				if (null != inf) {
					inf.setId(id);
					inf.setErrNo(bean.getJSONDataAt(i).getString("errNo"));
					inf.setErrMsg(bean.getJSONDataAt(i).getString("errMsg"));
					inf.setUpdDate(CommonFunction.getCurrentDate());
					list.add(inf);
				}
			}
			rspCode = service.updateRspMsg(list);
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	
	
	public String delete(){
		
		try {
			//判断是否已经存在
			TblRspMsgPK id = new TblRspMsgPK(chgNo, rspNo);
			if (null == service.get(id)) {
				return returnService(Constants.ERR_NO_DATA);
			}
			rspCode = service.delete(id);
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	private String rspNo;
	
	private String chgNo;
	
	private String errNo;
	
	private String errMsg;
	
	private String dataList;
	
	public String getRspNo() {
		return rspNo;
	}

	public void setRspNo(String rspNo) {
		this.rspNo = rspNo;
	}

	public String getChgNo() {
		return chgNo;
	}

	public void setChgNo(String chgNo) {
		this.chgNo = chgNo;
	}

	public String getErrNo() {
		return errNo;
	}

	public void setErrNo(String errNo) {
		this.errNo = errNo;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
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
