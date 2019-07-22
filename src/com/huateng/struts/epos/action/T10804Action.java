package com.huateng.struts.epos.action;

import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.impl.epos.TblEposService;
import com.huateng.common.Constants;
import com.huateng.po.epos.TblPrtMsg;
import com.huateng.po.epos.TblPrtMsgPK;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.JSONBean;

public class T10804Action extends BaseSupport{

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
			TblPrtMsgPK id = new TblPrtMsgPK();
			id.setPrtId(new Integer(prtId));
			id.setUsageKey("0");//默认
			
			if (null != service.get(id)) {
				return returnService(Constants.ERR_EXIST);
			}
			TblPrtMsg inf = new TblPrtMsg();
			inf.setId(id);
			inf.setPrtMsg(prtMsg);
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
			
			List<TblPrtMsg> list = new ArrayList<TblPrtMsg>();
			for(int i = 0; i < len; i++) {
				
				TblPrtMsgPK id = new TblPrtMsgPK();
				id.setPrtId(new Integer(bean.getJSONDataAt(i).getString("prtId")));
				id.setUsageKey("0");//默认
				TblPrtMsg inf = service.get(id);
				if (null != inf) {
					inf.setId(id);
					inf.setPrtMsg(bean.getJSONDataAt(i).getString("prtMsg"));
					inf.setUpdDate(CommonFunction.getCurrentDate());
					list.add(inf);
				}
			}
			rspCode = service.updatePrtMsg(list);
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	
	
	public String delete(){
		
		try {
			//判断是否已经存在
			TblPrtMsgPK id = new TblPrtMsgPK("0", new Integer(prtId));
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
	
	private String prtId;
	
	private String prtMsg;
	
	private String dataList;
	
	
	

	public String getPrtId() {
		return prtId;
	}

	public void setPrtId(String prtId) {
		this.prtId = prtId;
	}

	public String getPrtMsg() {
		return prtMsg;
	}

	public void setPrtMsg(String prtMsg) {
		this.prtMsg = prtMsg;
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
