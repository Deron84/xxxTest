package com.rail.struts.warehouse.action;


import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.warehouse.T100101BO;
import com.rail.po.warehouse.RailWhseInfo;



public class T100101Action extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String whseCode;//仓库编码
	private String whseName;//仓库名称
	private String whseAddress;//仓库地址
	private String whseRank;//仓库等级
	private String whseCapa;//仓库容量
	private String whsePic;//仓库负责人
	private String whseTel;//仓库联系电话
	private String whseDept;//仓库所属部门
	private String delStatus;//是否删除
	private String enableStatus;//仓库状态，0开启；1停用
	private String parentWhseCode;//父级仓库标识
	//仓库信息维护
	private T100101BO t100101BO = (T100101BO) ContextUtil.getBean("T100101BO");
	public T100101BO getT100101BO() {
		return t100101BO;
	}
	public void setT100101BO(T100101BO t100101bo) {
		t100101BO = t100101bo;
	}
	@Override
	protected String subExecute() throws Exception {
		System.out.println(getMethod()+"  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		try {
            if ("open".equals(getMethod())) {
                rspCode = openWhse();
            }else if("close".equals(getMethod())){
            	rspCode = closeWhse();
            }else if("edit".equals(getMethod())){
            	rspCode = editWhse();
            }
        } catch (Exception e) {
        	//e.printStackTrace();
            log("操作员：" + operator.getOprId() + "，对仓库进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	/**
	 * 更新仓库信息
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月17日
	 */
	public String editWhse()throws Exception{
		RailWhseInfo railWhseInfo = t100101BO.getWhseInfoByCode(getWhseCode());
		railWhseInfo.setWhseName(getWhseName());
		railWhseInfo.setWhseAddress(getWhseAddress());
		railWhseInfo.setWhseRank(getWhseRank());
		railWhseInfo.setWhseCapa(getWhseCapa());
		railWhseInfo.setWhsePic(getWhsePic());
		railWhseInfo.setWhseTel(getWhseTel());
		railWhseInfo.setWhseDept(getWhseDept());
		railWhseInfo.setEnableStatus(getEnableStatus());
		railWhseInfo.setUpdUser(operator.getOprId());
		String whseCode = getParentWhseCode();
		if(StringUtil.isNotEmpty(whseCode)){
			railWhseInfo.setParentWhseCode(getParentWhseCode());
		}else{
			railWhseInfo.setParentWhseCode("0");
		}
		railWhseInfo.setUpdDate(new Date());
		
		String rcode = t100101BO.updateWhse(railWhseInfo);
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
        
		log("更新仓库信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	/**
	 * 开启仓库
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月17日
	 */
	private String openWhse() throws Exception {
		RailWhseInfo railWhseInfo = t100101BO.getWhseInfoByCode(getWhseCode());
		railWhseInfo.setEnableStatus("0");
//		System.out.println(railWhseInfo.getId()+"  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String rcode = t100101BO.updateWhse(railWhseInfo);
		
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write(rcode);
        printWriter.flush();
        printWriter.close();
        
        return Constants.SUCCESS_CODE;
	}
	/**
	 * 获得单个仓库信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月17日
	 */
	public String getData() {
        try {
        	RailWhseInfo railWhseInfo = t100101BO.getByCode(getWhseCode());
//            TblBrhInfo tblBrhInfo = t10101BO.get(getBrhId());
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railWhseInfo);
            String str = JSONObject.toJSONString(midMap);//.fromObject(midMap).toString();
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
	public static Map<String, Object> nullToEmpty(Map<String, Object> map) {
		Set<String> set = map.keySet();
		if(set != null && !set.isEmpty()) {
			for(String key : set) {
				if(map.get(key) == null) { 
					map.put(key, ""); 
				}
			}
		}
		return map;
	}
	/**
	 * 关闭仓库
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月17日
	 */
	private String closeWhse() throws Exception {
		RailWhseInfo railWhseInfo = t100101BO.getWhseInfoByCode(getWhseCode());
		railWhseInfo.setEnableStatus("1");
		
		String rcode = t100101BO.updateWhse(railWhseInfo);
		
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write(rcode);
        printWriter.flush();
        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	public String getWhseCode() {
		return whseCode;
	}
	public void setWhseCode(String whseCode) {
		this.whseCode = whseCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getWhseName() {
		return whseName;
	}
	public void setWhseName(String whseName) {
		this.whseName = whseName;
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
	public String getParentWhseCode() {
		return parentWhseCode;
	}
	public void setParentWhseCode(String parentWhseCode) {
		this.parentWhseCode = parentWhseCode;
	}
	
}