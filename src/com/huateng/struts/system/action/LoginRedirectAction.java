package com.huateng.struts.system.action;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.huateng.bo.base.T10101BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.dao.iface.base.TblOprInfoDAO;
import com.huateng.po.TblBrhInfo;
import com.huateng.po.TblOprInfo;
import com.huateng.startup.init.MenuInfoUtil;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Title:登录后跳转
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-6-3
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class LoginRedirectAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(LoginRedirectAction.class);
	
	public String execute() throws Exception {
		
		//验证操作员opr信息是否存在
		String oprId = (String) getSessionAttribute("oprId");
		log(oprId);
		if(oprId == null) {
			return LOGIN;
		}
		TblOprInfoDAO tblOprInfoDAO = (TblOprInfoDAO) ContextUtil.getBean("OprInfoDAO");
		TblOprInfo tblOprInfo = tblOprInfoDAO.get(oprId);
		if(tblOprInfo == null) {
			ServletActionContext.getRequest().getSession().removeAttribute("oprId");
			log("登录失败，操作员不存在。编号[ " + oprId + " ]");
			return LOGIN;
		}
		
		//组装操作员信息operator
		T10101BO t10101BO = (T10101BO) ContextUtil.getBean("T10101BO");
		TblBrhInfo tblBrhInfo = t10101BO.get(tblOprInfo.getBrhId());
		Operator operator = new Operator();
		operator.setOprId(oprId);
		operator.setOprName(StringUtils.trim(tblOprInfo.getOprName()));
		operator.setOprBrhId(tblBrhInfo.getId());
		operator.setOprBrhName(StringUtils.trim(tblBrhInfo.getBrhName()));
		operator.setOprDegree(tblOprInfo.getOprDegree());
		operator.setOprBrhLvl(tblBrhInfo.getBrhLevel());
		operator.setOprPhone(tblOprInfo.getOprMobile());
//		Map<String, String> brhMap = new LinkedHashMap<String, String>(); //本行及下属机构信息MAP
//		brhMap.put(operator.getOprBrhId(),operator.getOprBrhName());
//		operator.setBrhBelowMap(CommonFunction.getBelowBrhMap(brhMap));
		operator.setBrhBelowMap(CommonFunction.getBelowBrhMap(operator.getOprBrhId()));//本行及下属机构信息MAP  一个sql搞定
		operator.setBrhBelowId(CommonFunction.getBelowBrhInfo(operator.getBrhBelowMap()));
		log("本行及下属机构编号：" + operator.getBrhBelowId());

		//组装菜单信息
		LinkedHashMap<String,Object> menuMap = MenuInfoUtil.setOperatorMenuWithDegree(operator.getOprDegree(), ServletActionContext.getRequest().getContextPath());
		Iterator<String> iter = menuMap.keySet().iterator();
		LinkedList<Object> toolList = new LinkedList<Object>();
		while(iter.hasNext()) {
			toolList.add(menuMap.get(iter.next()));
		}
		String toolBarStr = JSONArray.fromObject(toolList).toString();
		toolBarStr = toolBarStr.replaceAll(Constants.MENU_LVL1_JSON_FUNC, Constants.MENU_LVL1_FUNC).replaceAll(Constants.MENU_LVL3_JSON_FUNC, Constants.MENU_LVL3_FUNC);
		System.out.println("toolBarStr : " + toolBarStr);
		removeTreeCls(menuMap); //去掉菜单树图标样式
		
		//将组装好的信息放入session
		setSessionAttribute(Constants.OPERATOR_INFO, operator); //操作员信息
		setSessionAttribute("CUP_BRH_ID", tblBrhInfo.getCupBrhId());//银联编号
		setSessionAttribute(Constants.TREE_MENU_MAP, menuMap); //菜单树信息，LinkedHashMap<String,Object>形式
		setSessionAttribute(Constants.TOOL_BAR_STR, toolBarStr); //菜单树信息，String形式
		setSessionAttribute("menu_list", toolList); //菜单树信息，String形式
		setSessionAttribute(Constants.USER_AUTH_SET, MenuInfoUtil.getAuthSet(operator.getOprDegree())); //操作员权限信息
		
		return SUCCESS;
	}
	
	/**
	 * 去掉菜单树图标样式
	 * @param menuMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map removeTreeCls(Map<String,Object> menuMap) {
		
		Iterator<String> iter = menuMap.keySet().iterator();
		String key;
		Map map;
		List list;
		
		while(iter.hasNext()) {
			
			key = iter.next();
			if(menuMap.get(key) instanceof String && Constants.TOOLBAR_ICON.equals(key)) {
				
				menuMap.remove(key);
				return menuMap;
			} else if(menuMap.get(key) instanceof Map) {
				
				map = (Map) menuMap.get(key);
				if(map.get(Constants.TOOLBAR_ICON) != null) {
					map.remove(Constants.TOOLBAR_ICON);
				}
				map = removeTreeCls(map);
				menuMap.put(key, map);
			}else if(menuMap.get(key) instanceof List) {
				
				list = (List)menuMap.get(key);
				for(int i = 0; i < list.size(); i++) {
					if(list.get(i) instanceof Map) {
						list.set(i, removeTreeCls((Map)list.get(i)));
					}
				}
				menuMap.put(key, list);
			}
		}
		return menuMap;
	}
	
	/**
	 * 记录系统日志
	 * @param info
	 */
	protected void log(String info) {
		log.info(info);
	}
	
	/**
	 * 设置session的attribute
	 * @param name
	 * @param value
	 */
	protected void setSessionAttribute(String name,Object value) {
		ServletActionContext.getRequest().getSession().setAttribute(name, value);
	}
	
	/**
	 * 获得session的attribute
	 * @param name
	 */
	protected Object getSessionAttribute(String name) {
		return ServletActionContext.getRequest().getSession().getAttribute(name);
	}
}
