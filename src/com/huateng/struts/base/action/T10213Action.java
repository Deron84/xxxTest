package com.huateng.struts.base.action;

import com.huateng.bo.base.T10213BO;
import com.huateng.common.CommonUtil;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.po.TTermKey;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;

public class T10213Action extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private String addYlMchntNo; // 银联商户号
	private String addYlTermNo;  // 银联终端号
	
	private T10213BO t10213BO = (T10213BO) ContextUtil.getBean("T10213BO");
	
	@Override
	protected String subExecute() throws Exception {
		try {
            if ("add".equals(method)) {
                return add();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorCode.T10213_01;
        }
        return "无效请求";
	}

	private String add() throws Exception {
		TTermKey tTermKey = new TTermKey();
		tTermKey.setYlMchntNo(addYlMchntNo.trim());
		tTermKey.setYlTermNo(addYlTermNo.trim());
		int count = t10213BO.queryForInt(tTermKey);
		if (count != 0) {
			return "此商户下该终端已绑定密钥，请重新输入";
		}
		String param = t10213BO.queryForKey();
		String terminalKey = CommonUtil.getRandomCharAndNumr(32);
		System.out.println("terminalKey:"+terminalKey);
		String key = CommonUtil.encryKey(addYlTermNo.trim(), param);
        byte[] encryTermKey = CommonUtil.encrySm4(CommonUtil.hexStringToByteArray(terminalKey), key.getBytes("UTF-8"));
        String cKey=CommonUtil.strToHexStr(encryTermKey);       
        tTermKey.setcKey(cKey);
		int addDate = t10213BO.addDate(tTermKey);
		if (addDate > 0) {
            return Constants.SUCCESS_CODE;
        } else {
            return ErrorCode.T10213_01;
        }
	}

	public T10213BO getT10213BO() {
		return t10213BO;
	}

	public void setT10213BO(T10213BO t10213bo) {
		t10213BO = t10213bo;
	}

	public String getAddYlMchntNo() {
		return addYlMchntNo;
	}

	public void setAddYlMchntNo(String addYlMchntNo) {
		this.addYlMchntNo = addYlMchntNo;
	}

	public String getAddYlTermNo() {
		return addYlTermNo;
	}

	public void setAddYlTermNo(String addYlTermNo) {
		this.addYlTermNo = addYlTermNo;
	}

}
