package com.huateng.bo.base;

import java.util.List;

import com.huateng.dao.iface.base.AlipayShareParamDAO;
import com.huateng.po.AlipayShareParam;

public interface T10214BO {
	public AlipayShareParam get(String brhId);
	public String add(AlipayShareParam alipayShareParam);
	public String update(List<AlipayShareParam> alipayShareParamList);
	public String delete(AlipayShareParam alipayShareParam);
}
