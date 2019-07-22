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
import com.rail.bo.tools.T110801BO;
import com.rail.po.tool.RailToolMaintain;

/**
 * 
 * ClassName: ToolsAction 
 * @Description: TODO
 * @author syl
 * @date 2019年4月15日
 */
public class T110801Action extends  BaseAction {
	private Log log = LogFactory.getLog(this.getClass());
	
	private Map result;
	
	/**操作员对象*/
    protected Operator operator;
    private int start;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	private static final long serialVersionUID = 5283736418947840301L;

	private long id;
	private String toolCode;
	private Date addDate;
	private String maintainUser;
	private String note1;
	private String note2;
	private String note3;
	
	
	private T110801BO t110100BO = (T110801BO) ContextUtil.getBean("T110801BO");
	
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
		RailToolMaintain railToolInfo = new RailToolMaintain();
        railToolInfo.setToolCode(toolCode);
        railToolInfo.setMaintainUser(maintainUser);
        railToolInfo.setAddDate(new Date());
        t110100BO.save(railToolInfo);
		log("添加工具信息成功。操作员编号：" + operator.getOprId());
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
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getMaintainUser() {
		return maintainUser;
	}
	public void setMaintainUser(String maintainUser) {
		this.maintainUser = maintainUser;
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
	
	

}

