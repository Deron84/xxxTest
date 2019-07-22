package com.huateng.struts.pos.action;

import java.util.Map;

import com.huateng.bo.term.TermService;
import com.huateng.common.DataUtil;
import com.huateng.struts.system.action.BaseAction;

/**
 * Title:终端信息审核
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-18
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class T30201Action extends BaseAction {

	private static final long serialVersionUID = 991596026562130540L;

	private TermService service;

	public TermService getService() {
		return service;
	}

	public void setService(TermService service) {
		this.service = service;
	}

	@Override
	protected String subExecute() throws Exception {
		Map<String, String> param = DataUtil.getParamMapFromRequest(request);
		param.put("subTxnId", this.getSubTxnId());
		param.put("oprId", this.operator.getOprId());
		param.put("oprBrhId", this.operator.getOprBrhId());
		return service.termAudit(param);
	}

}
