package com.huateng.struts.mchnt.action;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.huateng.bo.mchnt.T21400BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.po.mchnt.TblMchntApply;
import com.huateng.po.mchnt.TblMchntUser;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T21400Action extends BaseAction {
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
		'e', 'f' };
	private static Logger logger = Logger.getLogger(T21400Action.class);
	T21400BO t21400BO = (T21400BO) ContextUtil.getBean("T21400BO");
	private String applyId;
	private String mchntId;
	private String applyName;
	private String applyStatus;
	private String applyType;
	private String refuseInfo;
	

	@Override
	protected String subExecute() throws Exception {
		if ("refuse".equals(method)) {
			rspCode = refuse();
		} else if ("pass".equals(method)) {
			rspCode = pass();
		} 
		return rspCode;
	}

	private String pass() {
		TblMchntApply bean = new TblMchntApply();
		bean = t21400BO.get(applyId);
		if (StringUtils.equals(bean.getApplyType(), "1")) {//增机
			bean.setApplyStatus("2");
		}
		if (StringUtils.equals(bean.getApplyType(), "2")) {//撤机
			bean.setApplyStatus("2");
		}

		if (StringUtils.equals(bean.getApplyType(), "3")) {//重置密码
			bean.setApplyStatus("2");			
			if(StringUtils.isNotBlank(mchntId)){
				//重置密码
				String pwdNew="000000";
				TblMchntUser mchntUser=	t21400BO.getMchntUserByMchntId(mchntId);
				if(mchntUser!=null){
					mchntUser.setLoginPwd(T21400Action.MD5(pwdNew));
					t21400BO.updateMchntUser(mchntUser);					
				}else{
					return "传入后台系统参数丢失，请联系管理员!";
				}				
			}
			
		}
		if (StringUtils.equals(bean.getApplyType(), "4")) {//故障报修
			bean.setApplyStatus("2");
		}
		if (StringUtils.isNotBlank(refuseInfo)) {
			bean.setRemark(refuseInfo);
		}
		// 更新时间和柜员
		bean.setAuditTime(CommonFunction.getCurrentDateTime());
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);
		bean.setAuditId(opr.getOprId());
		bean.setAuditPhone(opr.getOprPhone());

		t21400BO.update(bean);

		return Constants.SUCCESS_CODE;
	}

	private String refuse() {
		if (!StringUtil.isNull(applyId)) {
			TblMchntApply bean = new TblMchntApply();
			bean = t21400BO.get(applyId);
			if (StringUtils.equals(bean.getApplyType(), "1")) {//增机
				bean.setApplyStatus("3");
			}
			if (StringUtils.equals(bean.getApplyType(), "2")) {//撤机
				bean.setApplyStatus("3");
			}

			if (StringUtils.equals(bean.getApplyType(), "3")) {//重置密码
				bean.setApplyStatus("3");				
			}
			if (StringUtils.equals(bean.getApplyType(), "4")) {//故障报修
				bean.setApplyStatus("3");
			}
			if(StringUtils.isNotBlank(refuseInfo)){
			bean.setRemark(refuseInfo);
			}
			bean.setAuditTime(CommonFunction.getCurrentDateTime());
			Operator opr = (Operator) ServletActionContext.getRequest()
							.getSession().getAttribute(Constants.OPERATOR_INFO);
			bean.setAuditId(opr.getOprId());
			bean.setAuditPhone(opr.getOprPhone());
			t21400BO.update(bean);
			
			return Constants.SUCCESS_CODE;
		} else {
			return "传入后台系统参数丢失，请联系管理员!";
		}
	}
	
	public static String getCurrentTime(){
      Date date = new Date();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}
	public String getMchntId() {
		return mchntId;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
	public T21400BO getT21400BO() {
		return t21400BO;
	}
	public void setT21400BO(T21400BO t21400bo) {
		t21400BO = t21400bo;
	}
	


	/**
	 * 按位生成code
	 * @param count int 
	 * @return 验证码/邀请码
	 */
	static final String[] codeStr = {"2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","J","K","L","M",
			"N","P","Q","R","S","T","U","V","W","X","Y","Z"};
	public static String getComplexCode(int count){
		StringBuffer buf = new StringBuffer();
		Random ran = new Random();
		for(int i=0;i<count;i++){
			buf.append(codeStr[ran.nextInt(codeStr.length)]);
		}
		
		return buf.toString();
	}
	/**
	 * MD5加密
	 * 
	 * @param inStr
	 * @return
	 */
	public static String MD5(String inStr) {
		MessageDigest md = null;
		String outStr = null;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(inStr.getBytes());
			outStr = byteToString(digest);
		} catch (NoSuchAlgorithmException e) {
			logger.error("执行MD5加密失败！", e);
		}
		return outStr;
	}
	/**
	 * 转换字符串
	 * 
	 * @param digest
	 * @return
	 */
	public static String byteToString(byte[] digest) {
		int len = digest.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(digest[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[digest[j] & 0x0f]);
		}
		return buf.toString();
	}
	
}