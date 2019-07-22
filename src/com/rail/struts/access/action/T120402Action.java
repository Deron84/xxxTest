package com.rail.struts.access.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.access.T120101BO;
import com.rail.bo.access.T120402BO;
import com.rail.po.access.RailAccessInfo;
import com.rail.po.access.RailAccessMaintain;
import com.rail.po.warehouse.RailWhseInfo;

public class T120402Action extends BaseAction {
	private static final long serialVersionUID = 1L;
	private long id;
	private String accessCode;
	private String equipCode;
	private Date addDate;
	private String maintainUser;
	private String note1;
	private String note2;
	private String note3;
	private T120402BO t120402BO = (T120402BO) ContextUtil.getBean("T120402BO");
	public T120402BO getT120402BO() {
		return t120402BO;
	}

	public void setT120402BO(T120402BO t120402BO) {
		t120402BO = t120402BO;
	}
	private T120101BO t120101BO = (T120101BO) ContextUtil.getBean("T120101BO");
	
	public T120101BO getT120101BO() {
		return t120101BO;
	}

	public void setT120101BO(T120101BO t120101bo) {
		t120101BO = t120101bo;
	}

	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
				System.out.println(getMethod()+"  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				try {
		            if ("edit".equals(getMethod())) {
		            	rspCode = editAccess();
		            }else if ("add".equals(getMethod())) {
		                rspCode = add();
		            }
		        } catch (Exception e) {
		        	//e.printStackTrace();
		            log("操作员：" + operator.getOprId() + "，对门禁进行：" + getMethod() + "，失败，失败原因为：" + e);
		        }
		        return rspCode;
	}
	
	/**
	 * 新增门禁检修保养
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月16日
	 */
	private String add() throws Exception {
		System.out.println("------------------新增门禁检修保养---------------");
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ACCESS_CODE", getAccessCode());
		List<RailAccessMaintain> rwInfos = t120402BO.getByParam(paramMap);
		RailAccessMaintain railAccessMaintain = new RailAccessMaintain();
		railAccessMaintain.setAccessCode(getAccessCode());
		railAccessMaintain.setAddDate(new Date());
		railAccessMaintain.setMaintainUser(operator.getOprId());
		String rcode=t120402BO.add(railAccessMaintain);
		RailAccessInfo railAccessInfo = t120101BO.getByCode(getAccessCode());
		railAccessInfo.setLastExam(new Date());
		rcode = t120101BO.updateAccess(railAccessInfo);
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
	
	/**
	 * 更新门禁检修保养信息
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String editAccess()throws Exception{
		System.out.println("------------------更新门禁检修保养信息---------------");
		RailAccessMaintain railAccessMaintain  = t120402BO.getByCode(String.valueOf(getId()));
        if (railAccessMaintain==null) {
            return ErrorCode.T100100_01;
        }
		String rcode = t120402BO.updateAccess(railAccessMaintain);
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
        
		log("更新门禁信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 获得单个门禁检修保养信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("------------------获得单个门禁检修保养信息---------------");
        try {
        	RailAccessMaintain railAccessMaintain = t120402BO.getByCode(getAccessCode());
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railAccessMaintain);
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}


	public String getEquipCode() {
		return equipCode;
	}

	public void setEquipCode(String equipCode) {
		this.equipCode = equipCode;
	}
	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
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
}

