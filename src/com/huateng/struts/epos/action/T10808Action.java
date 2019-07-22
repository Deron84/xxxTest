package com.huateng.struts.epos.action;

import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.impl.epos.TblEposService;
import com.huateng.common.Constants;
import com.huateng.po.epos.TblCupBcMap;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.JSONBean;

public class T10808Action extends BaseSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7895182769280843180L;
	
	TblEposService tblEposService;
	
	public void setTblEposService(TblEposService tblEposService) {
		this.tblEposService = tblEposService;
	}
	private String cupIdNew;
	private String bcIdNew;
	private String bankNameNew;
	private String dataList;
	private String cupId;
	
	
	public String getCupId() {
		return cupId;
	}

	public void setCupId(String cupId) {
		this.cupId = cupId;
	}

	public String getCupIdNew() {
		return cupIdNew;
	}

	public void setCupIdNew(String cupIdNew) {
		this.cupIdNew = cupIdNew;
	}

	public String getBcIdNew() {
		return bcIdNew;
	}

	public void setBcIdNew(String bcIdNew) {
		this.bcIdNew = bcIdNew;
	}

	public String getBankNameNew() {
		return bankNameNew;
	}

	public void setBankNameNew(String bankNameNew) {
		this.bankNameNew = bankNameNew;
	}
	
	public String getDataList() {
		return dataList;
	}

	public void setDataList(String dataList) {
		this.dataList = dataList;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String add(){
		
		try {
			//判断是否已经存在
			TblCupBcMap tblCupBcMap = new TblCupBcMap();
			tblCupBcMap.setId(cupIdNew);
			tblCupBcMap.setBcId(bcIdNew);
			tblCupBcMap.setBankName(bankNameNew);
			tblCupBcMap.setCreateDate(CommonFunction.getCurrentDate());
			tblCupBcMap.setUpdateDate(CommonFunction.getCurrentDate());
			if (null != tblEposService.get(cupIdNew)) {
				return returnService(Constants.ERR_EXIST);
			}
			
			rspCode = tblEposService.save(tblCupBcMap);
			
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
			
			List<TblCupBcMap> list = new ArrayList<TblCupBcMap>();
			for(int i = 0; i < len; i++) {
				TblCupBcMap inf = tblEposService.getCupBcInfo(bean.getJSONDataAt(i).getString("cupId"));
				if (null != inf) {
					inf.setBankName(bean.getJSONDataAt(i).getString("bankName"));
					inf.setUpdateDate(CommonFunction.getCurrentDate());
					list.add(inf);
				}
			}
			rspCode = tblEposService.updateCupBcMap(list);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	
	
	public String del(){
		try {
			rspCode = tblEposService.delCupBcMap(cupId);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	
	@Override
	public boolean isSuccess() {
		return success;
	}
	
	@Override
	public String getMsg() {
		return msg;
	}
}
