package com.huateng.struts.system.action;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.huateng.bo.impl.mchnt.TblMchntService;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysCodeUtil;
import com.huateng.system.util.TxnInfoUtil;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseSupport extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(BaseSupport.class);

	public String returnService(String rspCode) {

		if (StringUtil.isNull(rspCode)) {
			return SUCCESS;
		}

		if (Constants.SUCCESS_CODE.equals(rspCode)) {
			if (!StringUtil.isNull(getTxnId()) && !StringUtil.isNull(getSubTxnId())) {
				msg = TxnInfoUtil.getTxnInfo(getTxnId() + "." + getSubTxnId())
					+ "成功";
			} else {
				msg = "交易成功";
			}
			success = true;
		} else if (rspCode.length() > 2
				&& Constants.SUCCESS_CODE_CUSTOMIZE.equalsIgnoreCase(rspCode
						.substring(0, 2))) {
			// 自定义返回信息，信息格式：Constants.SUCCESS_CODE_CUSTOMIZE + 信息
			msg = rspCode.substring(2);
			success = true;
		} else {
			msg = SysCodeUtil.getErrCode(rspCode);
			success = false;
		}
		return SUCCESS;
	}

	/**
	 * 异常的返回信息
	 * 
	 * @param rspCode
	 * @param e
	 * @return
	 */
	public String returnService(String rspCode, Exception e) {

		ServletActionContext.getRequest().setAttribute("exception", e);

		return ERROR;
	}

	/** 交易代码 */
	private String txnId;
	/** 交易子码 */
	private String subTxnId;
	/** 操作员对象 */
	private Operator operator;
	/** 交易返回码 */
	protected String rspCode = "运行时错误";
	/** 返回信息 */
	public String msg = "对不起，本次操作失败!";
	/** 返回状态 */
	public boolean success = false;

	public abstract String getMsg();

	public abstract boolean isSuccess();

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String execute() {
		return SUCCESS;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getSubTxnId() {
		return subTxnId;
	}

	public void setSubTxnId(String subTxnId) {
		this.subTxnId = subTxnId;
	}

	/**
	 * 记录系统日志
	 * 
	 * @param info
	 */
	protected void log(String info) {
		log.info(info);
	}

	/**
	 * 设置request的attribute
	 * 
	 * @param name
	 * @param value
	 */
	protected void setRequestAttribute(String name, Object value) {
		ServletActionContext.getRequest().setAttribute(name, value);
	}

	/**
	 * 设置session的attribute
	 * 
	 * @param name
	 * @param value
	 */
	protected void setSessionAttribute(String name, Object value) {
		ServletActionContext.getRequest().getSession()
				.setAttribute(name, value);
	}

	/**
	 * 删除session内attribute
	 * 
	 * @param name
	 *            2010-8-31下午10:11:37
	 */
	protected void removeSessionAttribute(String name) {
		ServletActionContext.getRequest().getSession().removeAttribute(name);
	}

	/**
	 * 获得request的attribute
	 * 
	 * @param name
	 */
	protected Object getRequestAttribute(String name) {
		return ServletActionContext.getRequest().getAttribute(name);
	}

	/**
	 * 获得session的attribute
	 * 
	 * @param name
	 */
	protected Object getSessionAttribute(String name) {
		return ServletActionContext.getRequest().getSession()
				.getAttribute(name);
	}
	
	

	public Operator getOperator() {
		if (null == operator) {
			operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		}
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	/** 通用查询DAO */
	protected ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil
			.getBean("CommQueryDAO");
	/** 商户业务 */
	protected TblMchntService service = (TblMchntService) ContextUtil
			.getBean("TblMchntService");
	
	/**
	 * 组装自定义信息
	 * 
	 * @param msg
	 * @return
	 * 2011-7-8上午10:20:00
	 */
	protected String getCustomizeMsg(String msg){
		return Constants.SUCCESS_CODE_CUSTOMIZE + msg;
	}
}
