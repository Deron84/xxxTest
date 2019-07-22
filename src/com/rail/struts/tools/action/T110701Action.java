package com.rail.struts.tools.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.pub.ResultUtils;
import com.rail.bo.tools.T110701BO;
import com.rail.bo.tools.T110801BO;
import com.rail.po.tool.RailToolMaintain;
import com.rail.po.tool.RailToolMaintainWarn;

/**
 * 
 * ClassName: ToolsAction 
 * @Description: TODO
 * @author syl
 * @date 2019年4月15日
 */
public class T110701Action extends  BaseAction {
	private Log log = LogFactory.getLog(this.getClass());
	
	private Map result;
	
	/**操作员对象*/
    protected Operator operator;
    private int start;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	private static final long serialVersionUID = 5283736418947840301L;

	private Long id;
	private String toolCode;
	private String warnMsg;
	private String infoSign;
	private Date addDate;
	private String verifyUser;
	private Date verifyDate;
	private String verifyMsg;
	private String note1;
	private String note2;
	private String note3;
	
	
	private T110701BO t110100BO = (T110701BO) ContextUtil.getBean("T110701BO");
	
	@Override
	protected String subExecute() throws Exception {
		System.out.println(getMethod()+"  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		try {
            if ("add".equals(getMethod())) {
                rspCode = check();
            }
        } catch (Exception e) {
        	//e.printStackTrace();
            log("操作员：" + operator.getOprId() + "，对新增工具预警审核：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return SUCCESS;
	}
	/**
	 * 新增工具操作
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author syl
	 * @date 2019年4月16日
	 */
	public String check() throws Exception {
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		String format = sdf.format(new Date());
		log.info("------------------确认工具预警审核---------------");
		RailToolMaintainWarn maintainWarn = new RailToolMaintainWarn();
		maintainWarn.setId(id);
		maintainWarn.setVerifyMsg(verifyMsg);
		maintainWarn.setVerifyUser(operator.getOprId());
        t110100BO.update(maintainWarn);
		log("确认预警成功。操作员编号：" + operator.getOprId());
		result =  ResultUtils.success(1);
		return SUCCESS;
	}
	public Log getLog() {
		return log;
	}
	public void setLog(Log log) {
		this.log = log;
	}
	public Map getResult() {
		return result;
	}
	public void setResult(Map result) {
		this.result = result;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getToolCode() {
		return toolCode;
	}
	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}
	public String getWarnMsg() {
		return warnMsg;
	}
	public void setWarnMsg(String warnMsg) {
		this.warnMsg = warnMsg;
	}
	public String getInfoSign() {
		return infoSign;
	}
	public void setInfoSign(String infoSign) {
		this.infoSign = infoSign;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getVerifyUser() {
		return verifyUser;
	}
	public void setVerifyUser(String verifyUser) {
		this.verifyUser = verifyUser;
	}
	public Date getVerifyDate() {
		return verifyDate;
	}
	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}
	public String getVerifyMsg() {
		return verifyMsg;
	}
	public void setVerifyMsg(String verifyMsg) {
		this.verifyMsg = verifyMsg;
	}
	public String getNote1() {
		return note1;
	}
	public void setNote1(String note1) {
		this.note1 = note1;
	}
	public String getNote2() {
		return note2;
	}
	public void setNote2(String note2) {
		this.note2 = note2;
	}
	public String getNote3() {
		return note3;
	}
	public void setNote3(String note3) {
		this.note3 = note3;
	}
	public T110701BO getT110100BO() {
		return t110100BO;
	}
	public void setT110100BO(T110701BO t110100bo) {
		t110100BO = t110100bo;
	}
	public static SimpleDateFormat getSdf() {
		return sdf;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}

