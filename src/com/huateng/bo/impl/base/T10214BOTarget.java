package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T10214BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.AlipayShareParamDAO;
import com.huateng.po.AlipayShareParam;

/**
 * Title:支付宝分润参数BO
 */
public class T10214BOTarget implements T10214BO {
	
	private AlipayShareParamDAO alipayShareParamDAO;
	
	public AlipayShareParam get(String brhId){
		return alipayShareParamDAO.get(brhId);
	}

	public String add(AlipayShareParam alipayShareParam) {
		alipayShareParamDAO.save(alipayShareParam);
		return Constants.SUCCESS_CODE;
	}

	public String delete(AlipayShareParam alipayShareParam) {
		alipayShareParamDAO.delete(alipayShareParam);
		return Constants.SUCCESS_CODE;
	}

	public String update(List<AlipayShareParam> alipayShareParamList) {
		for(AlipayShareParam alipayShareParam : alipayShareParamList) {
			alipayShareParamDAO.update(alipayShareParam);
		}
		return Constants.SUCCESS_CODE;
	}

	public AlipayShareParamDAO getAlipayShareParamDAO() {
		return alipayShareParamDAO;
	}

	public void setAlipayShareParamDAO(AlipayShareParamDAO alipayShareParamDAO) {
		this.alipayShareParamDAO = alipayShareParamDAO;
	}
	
}
