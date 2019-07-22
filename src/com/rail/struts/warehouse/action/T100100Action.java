package com.rail.struts.warehouse.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.common.StringUtil;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.warehouse.T100100BO;
import com.rail.po.warehouse.RailWhseInfo;

public class T100100Action extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String whseCode;//仓库编码
	private String whseName;//仓库名称
	private long whseAreaId;//仓库分区关联ID
	private String whseAddress;//仓库地址
	private String whseRank;//仓库等级
	private String whseCapa;//仓库容量
	private String whsePic;//仓库负责人
	private String whseTel;//仓库联系电话
	private String whseDept;//仓库所属部门
	private String accessCode;//门禁关联ID
	private String delStatus;//是否删除
	private String enableStatus;//仓库状态，0开启；1停用
	private String addUser;//创建人ID
	private Date addDate;//创建日期
	private String parentWhseCode;//上级仓库标识
	//仓库信息
	private T100100BO t100100BO = (T100100BO) ContextUtil.getBean("T100100BO");
	@Override
	protected String subExecute() throws Exception {
		System.out.println(getMethod()+"  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		try {
            if ("add".equals(getMethod())) {
                rspCode = add();
            }
        } catch (Exception e) {
        	//e.printStackTrace();
            log("操作员：" + operator.getOprId() + "，对仓库进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	/**
	 * 新增仓库操作
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月16日
	 */
	private String add() throws Exception {
		System.out.println("------------------新增仓库---------------");
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("WHSE_CODE", getWhseCode());
		List<RailWhseInfo> rwInfos = t100100BO.getByParam(paramMap);
		//判断机构是否存在getWhseCode()
        if (rwInfos!=null&&!rwInfos.isEmpty()) {
        	Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", false);
            midMap.put("msg", "该仓库编码已存在！");
            String str = JSONObject.toJSONString(midMap);
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();
            return ErrorCode.T100100_01;
        }
		RailWhseInfo railWhseInfo = new RailWhseInfo();
		railWhseInfo.setWhseCode(getWhseCode());
		railWhseInfo.setWhseName(getWhseName());
		railWhseInfo.setWhseAreaId(getWhseAreaId());
		railWhseInfo.setWhseAddress(getWhseAddress());
		railWhseInfo.setWhseRank(getWhseRank());
		railWhseInfo.setWhseCapa(getWhseCapa());
		railWhseInfo.setWhsePic(getWhsePic());
		railWhseInfo.setWhseTel(getWhseTel());
		railWhseInfo.setDelStatus("0");
		railWhseInfo.setWhseDept(getWhseDept());
		railWhseInfo.setEnableStatus("0");
		railWhseInfo.setAddUser(operator.getOprId());
		railWhseInfo.setAddDate(new Date());
		String whseCode = getParentWhseCode();
		if(StringUtil.isNotEmpty(whseCode)){
			railWhseInfo.setParentWhseCode(getParentWhseCode());
		}else{
			railWhseInfo.setParentWhseCode("0");
		}
//		System.out.println(getParentWhseCode()+"  >>>>>>>>>parent>>>>>>>>>>>");
		String rcode=t100100BO.add(railWhseInfo);
		String msg = "操作失败";
		boolean success = false;
		if(rcode.equals(Constants.SUCCESS_CODE)){
			success=true;
			msg = "操作成功";
		}else{
			msg = "操作失败";
			success = false;
		}
		Map<String, Object> midMap = new HashMap<String, Object>();
        midMap.put("success", success);
        midMap.put("msg", msg);
        String str = JSONObject.toJSONString(midMap);
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write(str);
        printWriter.flush();
        printWriter.close();
		log("添加仓库信息成功。操作员编号：" + operator.getOprId());

        return Constants.SUCCESS_CODE;
	}
	public String getWhseCode() {
		return whseCode;
	}
	public void setWhseCode(String whseCode) {
		this.whseCode = whseCode;
	}
	public String getWhseName() {
		return whseName;
	}
	public void setWhseName(String whseName) {
		this.whseName = whseName;
	}
	public long getWhseAreaId() {
		return whseAreaId;
	}
	public void setWhseAreaId(long whseAreaId) {
		this.whseAreaId = whseAreaId;
	}
	public String getWhseAddress() {
		return whseAddress;
	}
	public void setWhseAddress(String whseAddress) {
		this.whseAddress = whseAddress;
	}
	public String getWhseRank() {
		return whseRank;
	}
	public void setWhseRank(String whseRank) {
		this.whseRank = whseRank;
	}
	public String getWhseCapa() {
		return whseCapa;
	}
	public void setWhseCapa(String whseCapa) {
		this.whseCapa = whseCapa;
	}
	public String getWhsePic() {
		return whsePic;
	}
	public void setWhsePic(String whsePic) {
		this.whsePic = whsePic;
	}
	public String getWhseTel() {
		return whseTel;
	}
	public void setWhseTel(String whseTel) {
		this.whseTel = whseTel;
	}
	public String getWhseDept() {
		return whseDept;
	}
	public void setWhseDept(String whseDept) {
		this.whseDept = whseDept;
	}
	public String getAccessCode() {
		return accessCode;
	}
	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
	public String getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}
	public String getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getParentWhseCode() {
		return parentWhseCode;
	}
	public void setParentWhseCode(String parentWhseCode) {
		this.parentWhseCode = parentWhseCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public T100100BO getT100100BO() {
		return t100100BO;
	}
	public void setT100100BO(T100100BO t100100bo) {
		t100100BO = t100100bo;
	}
}