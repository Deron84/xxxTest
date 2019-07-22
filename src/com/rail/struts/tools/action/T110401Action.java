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
import com.rail.bo.enums.InfoSignEnums;
import com.rail.bo.pub.ResultUtils;
import com.rail.bo.tools.T110401BO;
import com.rail.bo.tools.T110601BO;
import com.rail.bo.tools.T110801BO;
import com.rail.bo.tools.TSendMsgSocketBO;
import com.rail.po.tool.RailToolMaintain;
import com.rail.po.tool.RailToolRepair;
import com.rail.po.tool.RailToolScrap;

/**
 * 
 * ClassName: ToolsAction 
 * @Description: TODO
 * @author syl
 * @date 2019年4月15日
 */
public class T110401Action extends  BaseAction {
	private Log log = LogFactory.getLog(this.getClass());
	
	private Map result;
	
	/**操作员对象*/
    protected Operator operator;
    private int start;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	private static final long serialVersionUID = 5283736418947840301L;

	private long id;
	private String toolCode;
	private String applyMsg;
	private String applyUser;
	private Date applyDate;
	private String infoSign;
	private String verifyUser;
	private Date verifyDate;
	private String verifyMsg;
	private String note1;//1 维修 2  维修完成applyType
	private String note2;
	private String note3;
	private String applyType;
	
	
	private T110401BO t110100BO = (T110401BO) ContextUtil.getBean("T110401BO");
	
	@Override
	protected String subExecute() throws Exception {
		System.out.println(getMethod()+"  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		try {
            if ("add".equals(getMethod())) {
                rspCode = add();
            }
        } catch (Exception e) {
        	//e.printStackTrace();
            log("操作员：" + operator.getOprId() + "，对新增工具维修：" + getMethod() + "，失败，失败原因为：" + e);
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
	public String add() throws Exception {
		
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		String format = sdf.format(new Date());
		log.info("------------------新增工具维修---------------");
		RailToolRepair railToolInfo = new RailToolRepair();
        railToolInfo.setToolCode(toolCode);
        railToolInfo.setApplyUser(applyUser);
        railToolInfo.setApplyMsg(applyMsg);
        railToolInfo.setApplyDate(new Date());
        railToolInfo.setInfoSign(String.valueOf(InfoSignEnums.NORMAL.getCode()));
        TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
		if(tSendMsgSocketBO.getWorkToolInfo(toolCode)==null){
			t110100BO.save(railToolInfo);
			log("添加工具维修信息成功。操作员编号：" + operator.getOprId());
			result =  ResultUtils.success(1);
			return SUCCESS;			
		}else {
			result =  ResultUtils.fail("当前工具在未结束的工单中，不能维修！");
			return SUCCESS;	
		}
	}
	public String update() throws Exception {
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		String format = sdf.format(new Date());
		log.info("------------------新增工具维修审核---------------");
		RailToolRepair railToolInfo = new RailToolRepair();
		railToolInfo.setId(id);
		railToolInfo.setVerifyUser(operator.getOprId());
		railToolInfo.setVerifyMsg(verifyMsg);
		railToolInfo.setInfoSign(infoSign);
		//railToolInfo.setNote1(applyType);
		railToolInfo.setVerifyDate(new Date());
		t110100BO.update(railToolInfo);
		log("修改工具维修信息成功。操作员编号：" + operator.getOprId());
		result =  ResultUtils.success(1);
		return SUCCESS;
	}
	public String updateComplete() throws Exception {
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		String format = sdf.format(new Date());
		log.info("------------------新增工具维修---------------");
		RailToolRepair railToolInfo = new RailToolRepair();
		railToolInfo.setId(id);
		railToolInfo.setApplyMsg(applyMsg);
		t110100BO.updateComplete(railToolInfo);
		log("修改工具维修信息成功。操作员编号：" + operator.getOprId());
		result =  ResultUtils.success(1);
		return SUCCESS;
	}
	
	public String get() throws Exception {
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		RailToolRepair railToolInfo = new RailToolRepair();
		railToolInfo.setId(id);
		result =  ResultUtils.success(t110100BO.get(railToolInfo));
		return SUCCESS;
	}
	public String getInfo() throws Exception {
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		RailToolRepair railToolInfo = new RailToolRepair();
		railToolInfo.setId(id);
		result =  ResultUtils.success(t110100BO.get(railToolInfo));
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
	
	public String getApplyMsg() {
		return applyMsg;
	}
	public void setApplyMsg(String applyMsg) {
		this.applyMsg = applyMsg;
	}
	public String getApplyUser() {
		return applyUser;
	}
	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getInfoSign() {
		return infoSign;
	}
	public void setInfoSign(String infoSign) {
		this.infoSign = infoSign;
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
	public static SimpleDateFormat getSdf() {
		return sdf;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	
	

}

