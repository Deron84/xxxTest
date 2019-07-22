/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-10-26       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.common.select;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.huateng.common.Operator;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.api.app.ApiAppBO;
import com.rail.bo.enums.DelStatusEnums;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-10-26
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class DynamicSQL extends DynamicSQLSupport{
	
	static ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	
	/**
	 * 查询同级仓库，移库下拉列表显示用
	 * @Description: TODO
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月20日
	 */
	public static DynamicSqlBean getPeerings(String inputValue, Operator operator, HttpServletRequest request){
		String orgBrhId=operator.getOprBrhId();
		List<String> list_depts= new ArrayList<String>();
		list_depts.add(orgBrhId);
		//查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
		where.append("and t2.WHSE_DEPT in (");
		if(null!=list_depts && list_depts.size()>0){
			String deptCode = list_depts.get(0);
			ApiAppBO ApiAppBO =  (ApiAppBO) ContextUtil.getBean("ApiAppBO");
			list_depts = ApiAppBO.getDeptList(list_depts,orgBrhId);
			for(String id :list_depts){
				where.append("'"+id+"',");
			}
		}
		 
		where.append("'-1')");
		String sql = "SELECT t2.WHSE_CODE,t2.WHSE_CODE||'-'||t2.WHSE_NAME as WHSE_NAME FROM RAIL_WHSE_INFO t2 "
				+ "WHERE t2.PARENT_WHSE_CODE= (select PARENT_WHSE_CODE from RAIL_WHSE_INFO t where t.WHSE_CODE ='"+request.getParameter("whseCode")+"')  "
						+ "AND t2.ENABLE_STATUS = 0 AND t2.DEL_STATUS = 0 AND t2.WHSE_CODE != '"+request.getParameter("whseCode")+"' "+where;
		
		sql += provideSqlDouLike(sql, "t2.WHSE_CODE||'-'||t2.WHSE_NAME", inputValue);
		sql += " order by t2.ID ASC ";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	/**
	 * 查询所有仓库，下拉列表显示用
	 * @Description: TODO
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月20日
	 */
	public static DynamicSqlBean getAllWhse(String inputValue, Operator operator, HttpServletRequest request){
		String orgBrhId=operator.getOprBrhId();
		List<String> list_depts= new ArrayList<String>();
		list_depts.add(orgBrhId);
		//查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
		where.append("and rwi.WHSE_DEPT in (");
		if(null!=list_depts && list_depts.size()>0){
			String deptCode = list_depts.get(0);
			ApiAppBO ApiAppBO =  (ApiAppBO) ContextUtil.getBean("ApiAppBO");
			list_depts = ApiAppBO.getDeptList(list_depts,orgBrhId);
			for(String id :list_depts){
				where.append("'"+id+"',");
			}
		}
		where.append("'-1')");
		String sql = "SELECT rwi.WHSE_CODE,rwi.WHSE_CODE||'-'||rwi.WHSE_NAME as WHSE_NAME FROM RAIL_WHSE_INFO rwi WHERE 1=1 AND rwi.ENABLE_STATUS=0 AND rwi.DEL_STATUS=0 "+where;
		
		sql += provideSqlDouLike(sql, "rwi.WHSE_CODE||'-'||rwi.WHSE_NAME", inputValue);
		sql += " order by WHSE_CODE";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	/**
	 * 查询所有施工单位，新增工单时下拉列表用
	 * @Description: TODO
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月20日
	 */
	public static DynamicSqlBean getAllRailConstOrg(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT ID,COST_ORG_NAME FROM RAIL_CONST_ORG rco WHERE 1=1 AND rco.ENABLE_STATUS=0 AND rco.DEL_STATUS=0 ";
		sql += " order by ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	/**
	 * 获得所有班组
	 * @Description: TODO
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月20日
	 */
	public static DynamicSqlBean getAllRailTeam(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT ID,WORK_TEAM_NAME FROM RAIL_TEAM rt WHERE 1=1 AND rt.ENABLE_STATUS=0 AND rt.DEL_STATUS=0";
		sql += " order by ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**
	 * 获得所有未开始工单
	 * @Description: TODO
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月20日
	 */
	public static DynamicSqlBean getAllRailWork(String inputValue, Operator operator, HttpServletRequest request){
		
        String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
		where.append("and rt.DEPT in (");
		if(null!=list_depts && list_depts.size()>0){
			String deptCode = list_depts.get(0);
			ApiAppBO ApiAppBO =  (ApiAppBO) ContextUtil.getBean("ApiAppBO");
			list_depts = ApiAppBO.getDeptList(list_depts,orgBrhId);
			for(String id :list_depts){
				where.append("'"+id+"',");
			}
		}
		//查询当前部门下的所有下属部门
		where.append("'-1')");
		String sql = "SELECT WORK_CODE,WORK_NAME FROM RAIL_WORK_INFO rt WHERE 1=1  "+where;
		sql += " order by ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}

	/**
	 * 获得所有未开始工单
	 * @Description: TODO
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月20日
	 */
	public static DynamicSqlBean getAllRailWork2(String inputValue, Operator operator, HttpServletRequest request){
		
        String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
		where.append("and rt.DEPT in (");
		if(null!=list_depts && list_depts.size()>0){
			String deptCode = list_depts.get(0);
			ApiAppBO ApiAppBO =  (ApiAppBO) ContextUtil.getBean("ApiAppBO");
			list_depts = ApiAppBO.getDeptList(list_depts,orgBrhId);
			for(String id :list_depts){
				where.append("'"+id+"',");
			}
		}
		//查询当前部门下的所有下属部门
		where.append("'-1')");
		String sql = "SELECT WORK_CODE,WORK_NAME FROM RAIL_WORK_INFO rt WHERE 1=1 AND rt.AUDIT_STATUS !=1  AND WORK_STATUS=0 "+where;
		sql += " order by ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	/**
	 * 获得所有的通道类型门禁
	 * @Description: TODO
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @throws  
	 * @author liujihui
	 * @date 2019年4月20日
	 */
	public static DynamicSqlBean getTdAccesses(String inputValue, Operator operator, HttpServletRequest request){
		String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
		where.append("and rai.ACCESS_DEPT in (");
		if(null!=list_depts && list_depts.size()>0){
			String deptCode = list_depts.get(0);
			ApiAppBO ApiAppBO =  (ApiAppBO) ContextUtil.getBean("ApiAppBO");
			list_depts = ApiAppBO.getDeptList(list_depts,orgBrhId);
			for(String id :list_depts){
				where.append("'"+id+"',");
			}
		}
		//查询当前部门下的所有下属部门
		where.append("'-1')");
		
		String sql = "SELECT ACCESS_CODE,trim(ACCESS_CODE) ||' - '|| trim(ACCESS_NAME) as ACCESS_NAME FROM RAIL_ACCESS_INFO rai WHERE 1=1 AND rai.ACCESS_TYPE=0 AND rai.ACCESS_STATUS=0 "+where;
		sql += provideSqlDouLike(sql, "trim(ACCESS_CODE) ||' - '|| trim(ACCESS_NAME)", inputValue);
		sql += " order by ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**
	 * 获得所有的通道类型门禁
	 * @Description: TODO
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @throws  
	 * @author liujihui
	 * @date 2019年4月20日
	 */
	public static DynamicSqlBean getTdAccesses1(String inputValue, Operator operator, HttpServletRequest request){
		String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
		where.append("and rai.ACCESS_TYPE='0' and rai.ACCESS_DEPT in (");
		if(null!=list_depts && list_depts.size()>0){
			String deptCode = list_depts.get(0);
			ApiAppBO ApiAppBO =  (ApiAppBO) ContextUtil.getBean("ApiAppBO");
			list_depts = ApiAppBO.getDeptList(list_depts,orgBrhId);
			for(String id :list_depts){
				where.append("'"+id+"',");
			}
		}
		//查询当前部门下的所有下属部门
		where.append("'-1')");
		
		String sql = "SELECT ACCESS_CODE,trim(ACCESS_CODE) ||' - '|| trim(ACCESS_NAME) as ACCESS_NAME FROM RAIL_ACCESS_INFO rai WHERE 1=1 AND rai.ACCESS_STATUS=0 "+where;
		sql += provideSqlDouLike(sql, "trim(ACCESS_CODE) ||' - '|| trim(ACCESS_NAME)", inputValue);
		sql += " order by ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**
	 * 获得所有的通道类型门禁
	 * @Description: TODO
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @throws  
	 * @author liujihui
	 * @date 2019年4月20日
	 */
	public static DynamicSqlBean getTdAccesses2(String inputValue, Operator operator, HttpServletRequest request){
		String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
		where.append("and rai.ACCESS_DEPT in (");
		if(null!=list_depts && list_depts.size()>0){
			String deptCode = list_depts.get(0);
			ApiAppBO ApiAppBO =  (ApiAppBO) ContextUtil.getBean("ApiAppBO");
			list_depts = ApiAppBO.getDeptList(list_depts,orgBrhId);
			for(String id :list_depts){
				where.append("'"+id+"',");
			}
		}
		//查询当前部门下的所有下属部门
		where.append("'-1')");
		
		String sql = "SELECT ACCESS_CODE,trim(ACCESS_CODE) ||' - '|| trim(ACCESS_NAME) as ACCESS_NAME FROM RAIL_ACCESS_INFO rai WHERE 1=1  AND rai.ACCESS_STATUS=0 "+where;
		sql += provideSqlDouLike(sql, "trim(ACCESS_CODE) ||' - '|| trim(ACCESS_NAME)", inputValue);
		sql += " order by ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	/**
	 * 获得所有的工单
	 * @Description: TODO
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月28日
	 */
	public static DynamicSqlBean getTdWork(String inputValue, Operator operator, HttpServletRequest request){
		String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
		where.append("and rai.DEPT in (");
		if(null!=list_depts && list_depts.size()>0){
			String deptCode = list_depts.get(0);
			ApiAppBO ApiAppBO =  (ApiAppBO) ContextUtil.getBean("ApiAppBO");
			list_depts = ApiAppBO.getDeptList(list_depts,orgBrhId);
			for(String id :list_depts){
				where.append("'"+id+"',");
			}
		}
		//查询当前部门下的所有下属部门
		where.append("'-1')");
		String sql = "SELECT WORK_CODE,trim(WORK_CODE) ||' - '|| trim(WORK_NAME) as WORK_NAME FROM RAIL_WORK_INFO rai WHERE 1=1 "+where;
		sql += provideSqlDouLike(sql, "trim(WORK_CODE) ||' - '|| trim(WORK_NAME)", inputValue);
		sql += " order by ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**
	 * 获得所有的设备
	 * @Description: TODO
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月20日
	 */
	public static DynamicSqlBean getTdAccessesEquip(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT EQUIP_CODE,trim(EQUIP_CODE) ||' - '|| trim(EQUIP_NAME) as EQUIP_NAME FROM RAIL_ACCESS_EQUIP_INFO rai WHERE 1=1  AND rai.EQUIP_STATUS=0 ";
		sql += provideSqlDouLike(sql, "trim(EQUIP_CODE) ||' - '|| trim(EQUIP_NAME)", inputValue);
		sql += " order by ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**
	 * 获得组织单位
	 * @Description: TODO
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月20日
	 */
	public static DynamicSqlBean getRailFormOrg(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "SELECT ID,FORM_ORG_NAME FROM RAIL_FORM_ORG rfo WHERE 1=1 AND rfo.ENABLE_STATUS=0 ";
		sql += " order by ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	/**
	 * 获得巡护中队列表
	 * @Description: TODO
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月20日
	 */
	public static DynamicSqlBean getRailpatrol(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "SELECT ID,PATROL_NAME FROM RAIL_PATROL rfo WHERE 1=1 AND rfo.ENABLE_STATUS=0 AND rfo.DEL_STATUS = 0 ";
		sql += " order by ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}	
	
	/**
	 * 获得盯控干部列表
	 * @Description: TODO
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月20日
	 */
	public static DynamicSqlBean getRailDkEmployee(String inputValue, Operator operator, HttpServletRequest request){
		String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
		where.append("and DEPT in (");
		if(null!=list_depts && list_depts.size()>0){
			String deptCode = list_depts.get(0);
			ApiAppBO ApiAppBO =  (ApiAppBO) ContextUtil.getBean("ApiAppBO");
			list_depts = ApiAppBO.getDeptList(list_depts,orgBrhId);
			for(String id :list_depts){
				where.append("'"+id+"',");
			}
		}
		//查询当前部门下的所有下属部门
		where.append("'-1')");
		String sql = "SELECT EMPLOYEE_CODE,trim(EMPLOYEE_CODE) ||' - '|| trim(EMPLOYEE_NAME) as EMPLOYEE_NAME FROM RAIL_EMPLOYEE WHERE 1=1 AND EMPLOYEE_TYPE=1 "+where;
		sql += provideSqlDouLike(sql, "trim(EMPLOYEE_CODE) ||' - '|| trim(EMPLOYEE_NAME)", inputValue);
		sql += " order by ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	
	/**
	 * 获得人员类型列表
	 * @Description: TODO
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月20日
	 */
	public static DynamicSqlBean getRailEmployeeType(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "SELECT ID,trim(ID) ||' - '|| trim(TYPE_NAME) as TYPE_NAME FROM RAIL_EMPLOYEE_TYPE WHERE 1=1 AND DEL_STATUS=0  AND ENABLE_STATUS=0 ";
		sql += provideSqlDouLike(sql, "trim(ID) ||' - '|| trim(TYPE_NAME)", inputValue);
		sql += " order by ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static DynamicSqlBean getMchntId(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where MCHT_STATUS = '0' ";
		
		sql += provideSqlDouLike(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "AGR_BR", operator.getBrhBelowId());
		}
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntIdfor10501(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where MCHT_STATUS = '0' and mcht_group_flag = '3'";
		
		sql += provideSqlDouLike(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "AGR_BR", operator.getBrhBelowId());
		}
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntIdfor20801(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where MCHT_STATUS = '0'";
		
		sql += provideSqlDouLike(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "AGR_BR", operator.getBrhBelowId());
		}
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}

	public static DynamicSqlBean getMchntIdfor90401(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where MCHT_STATUS = '0'";
		
		sql += provideSqlDouLike(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "AGR_BR", operator.getBrhBelowId());
		}
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntCupIdAllInfo(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from tbl_mcht_base_inf where conn_type='Z' ";
		
		sql += provideSqlDouLike(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "AGR_BR", operator.getBrhBelowId());
		}	
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}

	public static DynamicSqlBean getMchntNo(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where MCHT_STATUS = '0' ";
		
		sql += provideSql(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "AGR_BR", operator.getBrhBelowId());
		}
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	
	public static DynamicSqlBean getMchntIdJ(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF  ";
		sql += provideSqlLike(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);

		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "AGR_BR", operator.getBrhBelowId());
		}
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntIdInfoZ(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where CONN_TYPE='Z' ";
		sql += provideSqlLike(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);

		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "AGR_BR", operator.getBrhBelowId());
		}
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}

	public static DynamicSqlBean getMchntIdTmp(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF_TMP where 1=1 ";
		sql += provideSqlLike(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "AGR_BR", operator.getBrhBelowId());
		}
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**
	 * 终端管理  == 基本消息 == 商户号
	 * @param params
	 * @return
	 */
	
	public static DynamicSqlBean getMchtCdInTemp(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF_TMP where 1=1  and MCHT_STATUS != '9' ";
		
		sql+= provideSqlLike(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "AGR_BR", operator.getBrhBelowId());
		}
		
		sql += " order by MCHT_NO";
		
		return new DynamicSqlBean(sql.toString(), commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntIdInBase(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where 1=1 ";
		
		sql += provideSqlLike(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "AGR_BR", operator.getBrhBelowId());
		}
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntIdInCup(String inputValue, Operator operator, HttpServletRequest request){
			
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where 1=1 ";
		
		sql +=" and conn_type = 'J' " ;
		
		sql += provideSqlLike(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "AGR_BR", operator.getBrhBelowId());
		}
	
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**
	 * 退货交易查询
	 * @param inputValue
	 * @param operator
	 * @param request
	 * @return
	 */
	public static DynamicSqlBean getMchntNoInBase(String inputValue, Operator operator, HttpServletRequest request){
			
		String sql = "select MCHT_NO, trim(MCHT_NO) as MCHT_NMS from TBL_MCHT_BASE_INF_TMP where 1=1 ";
		
		sql += provideSqlLike(sql, "trim(MCHT_NO)", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "AGR_BR", operator.getBrhBelowId());
		}
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getAreaCode(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select CITY_CODE_NEW,CITY_CODE_NEW||' - '||CITY_NAME from CST_CITY_CODE ";
		
		sql += provideSqlDouLike(sql, "CITY_CODE_NEW||' - '||CITY_NAME", inputValue);
		
		sql += " order by CITY_CODE_NEW";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getAreaCode_as(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select CITY_CODE_NEW,CITY_CODE_NEW||' - '||CITY_NAME from CST_CITY_CODE ";
		
		sql += provideSqlDouLike(sql, "CITY_CODE_NEW||' - '||CITY_NAME", inputValue);
		
		sql += " order by CITY_CODE_NEW";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntCupBank(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select cnapsbankno, trim(cnapsbankno) ||' - '|| trim(bankname) as MCHT_NMS from tbl_mbf_bank_info where rownum=100 ";

		sql += provideSql(sql, "trim(cnapsbankno) ||' - '|| trim(bankname)", inputValue);
		
		sql += " order by cnapsbankno";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntCupBank_as(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select cnapsbankno, trim(cnapsbankno) ||' - '|| trim(bankname) as MCHT_NMS from tbl_mbf_bank_info where 1=1 ";

		sql += provideSql(sql, "trim(cnapsbankno) ||' - '|| trim(bankname)", inputValue);
		
		sql += " order by cnapsbankno";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**
	 * @param inputValue
	 * @param operator
	 * @param request
	 * @return
	 */
	public static DynamicSqlBean getBranchId(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT BRH_ID,BRH_ID||'-'||BRH_NAME as BRH_NAME FROM TBL_BRH_INFO where BRH_LEVEL in ('0','1') ";
		
		sql += provideSqlDouLike(sql, "BRH_ID||' - '||BRH_NAME", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "BRH_ID", operator.getBrhBelowId());
		}
		
		sql += " order by BRH_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getBranchIdAll(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "SELECT BRH_ID,trim(BRH_ID)||'-'||trim(BRH_NAME) as BRH_NAME FROM TBL_BRH_INFO where 1=1 ";
		
		sql += provideSqlDouLike(sql, "trim(BRH_ID)||' - '||trim(BRH_NAME)", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "BRH_ID", operator.getBrhBelowId());
		}
		
		sql += " order by BRH_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getBranchId12(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT BRH_ID,BRH_ID||'-'||BRH_NAME as BRH_NAME FROM TBL_BRH_INFO where BRH_LEVEL in ('3','2','1','0') ";
		
		sql += provideSqlDouLike(sql, "BRH_ID||' - '||BRH_NAME", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "BRH_ID", operator.getBrhBelowId());
		}
		
		sql += " order by BRH_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMccId(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = " select mchnt_tp,mchnt_tp||' - '||mchnt_tp_grp||' - '||descr from tbl_inf_mchnt_tp";
		sql += provideSqlDouLike(sql, "mchnt_tp_grp||' - '||descr", inputValue);
		
		sql += " order by mchnt_tp_grp";
		return new DynamicSqlBean(sql, commQueryDAO);
	}

	public static DynamicSqlBean getBranchId12_as(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT BRH_ID,BRH_ID||'-'||BRH_NAME as BRH_NAME FROM TBL_BRH_INFO where BRH_LEVEL in ('3','2','1') ";
		
		sql += provideSqlDouLike(sql, "BRH_ID||' - '||BRH_NAME", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "BRH_ID", operator.getBrhBelowId());
		}
		
		sql += " order by BRH_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getInsIdCd(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "select distinct trim(INS_ID_CD)||'- '||trim(CARD_DIS) as id,trim(INS_ID_CD)||' - '||trim(CARD_DIS) from TBL_BANK_BIN_INF ";
		
		sql += provideSqlDouLike(sql, "trim(INS_ID_CD)||' - '||trim(CARD_DIS)", inputValue);
		
		sql += " order by id";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	public static DynamicSqlBean getCityName(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = " select CITY_CODE_NEW,CITY_CODE_NEW||'-'||CITY_NAME as CITY_NAME from CST_CITY_CODE";
		sql += provideSqlDouLike(sql, "CITY_CODE_NEW||' - '||CITY_NAME", inputValue);
		
		sql += " order by CITY_CODE_NEW";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	public static DynamicSqlBean getProvince(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = " select AREANAME,AREANAME||'-'||AREACODE as AREACODE from T_AREACODE where ROOTAREACODE='-'";
		sql += provideSqlDouLike(sql, "AREANAME||' - '||AREACODE", inputValue);
		
		sql += " order by AREACODE";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	public static DynamicSqlBean getBrhName(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = " select C_BRH_ID,C_BRH_ID||'-'||C_BRH_NAME as C_BRH_NAME from T_BRH_DATADIC where C_BRH_ID not in ('14','15')";
		sql += provideSqlDouLike(sql, "C_BRH_ID||'-'||C_BRH_NAME", inputValue);
		
		sql += "  order by C_BRH_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	public static DynamicSqlBean getBrhName_as(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = " select C_BRH_ID,C_BRH_ID||'-'||C_BRH_NAME as C_BRH_NAME from T_BRH_DATADICselect C_BRH_ID,C_BRH_ID||'-'||C_BRH_NAME as C_BRH_NAME from T_BRH_DATADIC where C_BRH_ID not in ('14','15')";
		sql += provideSqlDouLike(sql, "C_BRH_ID||'-'||C_BRH_NAME", inputValue);
		
		sql += "  order by C_BRH_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	public static DynamicSqlBean getMchntNameNew(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MAPPING_MCHNTCDTWO, trim(MAPPING_MCHNTCDTWO) ||' - '|| trim(MCHT_NM) as MCHT_NM from TBL_MCHT_BASE_INF  ";
		sql += provideSqlLike(sql, "trim(MAPPING_MCHNTCDTWO) ||' - '|| trim(MCHT_NM)", inputValue);

		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "AGR_BR", operator.getBrhBelowId());
		}
		sql += " order by MAPPING_MCHNTCDTWO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getShareRole(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT SHARE_RULEID,SHARE_RULEID||'-'||SHARE_RULENAME as SHARE_RULENAME FROM TBL_ALIPAY_SHARE_PARAM ";
		
		sql += provideSqlDouLike(sql, "SHARE_RULENAME||' - '||SHARE_RULEID", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "BRH_ID", operator.getBrhBelowId());
		}
		
		sql += "where SHARE_RULESTATUS='1' order by SHARE_RULEID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
public static DynamicSqlBean getShareRole_as(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT SHARE_RULEID,SHARE_RULEID||'-'||SHARE_RULENAME as SHARE_RULENAME FROM TBL_ALIPAY_SHARE_PARAM  ";
		
		sql += provideSqlDouLike(sql, "SHARE_RULEID||' - '||SHARE_RULENAME", inputValue);
		if(!"0000".equals(operator.getOprBrhId())){
			sql += provideSqlIn(sql, "SHARE_RULEID", operator.getBrhBelowId());
		}
		
		sql += " order by SHARE_RULEID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
/**
 * 获得仓库列表
 * @Description: TODO
 * @param @param inputValue
 * @param @param operator
 * @param @param request
 * @param @return   
 * @return DynamicSqlBean  
 * @throws
 * @author liujihui
 * @date 2019年4月15日
 */
	public static DynamicSqlBean getRailWhse(String inputValue, Operator operator, HttpServletRequest request){
		
		System.out.println("--------------getRailWhse------------------");
		//System.out.println(operator.getOprName());
		String sql = "select t.WHSE_CODE, t.WHSE_CODE||' - '||t.WHSE_NAME from RAIL_WHSE_INFO t where 1=1 and ENABLE_STATUS='0' "+getDelDiff(1)+getDepts(operator, "WHSE_DEPT");
		
		return new DynamicSqlBean(sql, commQueryDAO);
	}	
	/**
	 * 
	 * @Description: 获取工具名称下拉
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @author syl
	 * @date 2019年4月17日
	 */
	public static DynamicSqlBean getToolName(String inputValue, Operator operator, HttpServletRequest request){
		System.out.println("--------------getToolName------------------");
		String sql = "select t.TOOL_NAME, t.TOOL_CODE||' - '||t.TOOL_NAME from RAIL_TOOL_INFO t where 1=1 "+getDelDiff(1)+getDepts(operator);
		
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	/**
	 * 
	 * @Description: 
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @author syl
	 * @date 2019年4月28日
	 */
	public static DynamicSqlBean getToolCode(String inputValue, Operator operator, HttpServletRequest request){
		System.out.println("--------------getToolName------------------");
		String sql = "select  t.TOOL_CODE, t.rfid||' - '||t1.TOOL_NAME from RAIL_TOOL_INFO  t "

				+" left join RAIL_TOOL_Name t1  on t.tool_name = t1.id where 1=1 "+getDelDiff(1)+getDepts(operator)
				+"  and t.TOOL_CODE not in (select t.tool_code from rail_work_tool t " +
				"  left join rail_work_info t2  on t.work_code = t2.work_code" +
				"  where t2.work_status < 2 )";
		
		return new DynamicSqlBean(sql, commQueryDAO);
	}	
	
	/**
	 * 
	 * @Description: 
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @author qiufulong
	 * @date 2019年4月28日
	 */
	public static DynamicSqlBean getToolCode1(String inputValue, Operator operator, HttpServletRequest request){
		System.out.println("--------------getToolName------------------");
		String sql = "select  t.TOOL_CODE, t.rfid||' - '||t1.TOOL_NAME from RAIL_TOOL_INFO  t "

				+" left join RAIL_TOOL_Name t1  on t.tool_name = t1.id where 1=1 and t.TOOL_STATUS='0' "+getDelDiff(1)+getDepts(operator)
				+"  and t.TOOL_CODE not in (select t.tool_code from rail_work_tool t " +
				" left join rail_work_info t2  on t.work_code = t2.work_code" +
				" where t2.work_status < 2 )";
		return new DynamicSqlBean(sql, commQueryDAO);
	}	
	/**
	 * 
	 * @Description: 
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @author syl
	 * @date 2019年4月28日
	 */
	public static DynamicSqlBean getToolList(String inputValue, Operator operator, HttpServletRequest request){
		System.out.println("--------------getToolName------------------");
		String sql = "select t.id,t.rfid||' - '||t1.TOOL_NAME from RAIL_TOOL_INFO  t "

				+" left join RAIL_TOOL_Name t1  on t.tool_name = t1.id where 1=1 "+getDelDiff(1)+getDepts(operator);
		//String sql = "select id, TOOL_CODE||' - '||TOOL_NAME from RAIL_TOOL_INFO";
		
		return new DynamicSqlBean(sql, commQueryDAO);
	}		
	/**
	 * 
	 * @Description: 获取工具模型下拉
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @author syl
	 * @date 2019年4月17日
	 */
//	public static DynamicSqlBean getToolModel(String inputValue, Operator operator, HttpServletRequest request){
//		System.out.println("--------------getToolModel------------------");
//		String sql = "select MODEL_CODE, MODEL_CODE||' - '||MODEL_NAME from RAIL_TOOL_MODEL where 1=1";
//		
//		return new DynamicSqlBean(sql, commQueryDAO);
//	}
	/**
	 * 
	 * @Description: 获取设备类型的列表
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @author qfl
	 * @date 2019年4月17日
	 */
	public static DynamicSqlBean getRailAccessEquipType(String inputValue, Operator operator, HttpServletRequest request){
		System.out.println("--------------getRailAccessEquipType------------------");
		String sql = "select ID, ID ||' - '||EQUIP_TYPE_NAME from   (select EQUIP_TYPE_NAME ,ID as ID from RAIL_ACCESS_EQUIP_TYPE where DEL_STATUS='0' and ENABLE_STATUS='0' order by ID  )t where 1=1 ";
		
		return new DynamicSqlBean(sql, commQueryDAO);
	}	
	/**
	 * 
	 * @Description: 获取供应商的列表
	 * @param @param inputValue
	 * @param @param operator
	 * @param @param request
	 * @param @return   
	 * @return DynamicSqlBean  
	 * @author syl
	 * @date 2019年4月17日
	 */
	public static DynamicSqlBean getOrgName(String inputValue, Operator operator, HttpServletRequest request){
		System.out.println("--------------getOrgName------------------");
		String sql = "select MFRS_ORG, MFRS_ORG ||' - '||MFRS_ORG_NAME from   (select MFRS_ORG_NAME ,ID as MFRS_ORG from RAIL_MFRS_ORG  WHERE 1=1 and DEL_STATUS='0' AND ENABLE_STATUS='0' )t where 1=1 ";
		
		return new DynamicSqlBean(sql, commQueryDAO);
	}																			
	public static DynamicSqlBean getToolNameLong(String inputValue, Operator operator, HttpServletRequest request){
		System.out.println("--------------getToolNameLong------------------");
		String sql = "select id, TOOL_NAME from   RAIL_TOOL_NAME where 1=1 AND ENABLE_STATUS='0'  AND ENABLE_STATUS='0'  "+getDelDiff(0);
		
		return new DynamicSqlBean(sql, commQueryDAO);
	}		
	
	
	public static DynamicSqlBean getToolType(String inputValue, Operator operator, HttpServletRequest request){
		System.out.println("--------------getOrgName------------------");
		String sql = "select MFRS_ORG, MFRS_ORG ||' - '||TOOL_TYPE_NAME from   (select TOOL_TYPE_NAME ,ID as MFRS_ORG from RAIL_TOOL_TYPE where DEL_STATUS='0' and ENABLE_STATUS='0')t where 1=1";
		
		return new DynamicSqlBean(sql, commQueryDAO);
	}	
	public static DynamicSqlBean getToolunit(String inputValue, Operator operator, HttpServletRequest request){
		System.out.println("--------------getToolunit------------------");
		String sql = "select id, TOOL_UNIT_NAME from   RAIL_TOOL_UNIT  where 1=1 AND ENABLE_STATUS='0' "+getDelDiff(0);;
		
		return new DynamicSqlBean(sql, commQueryDAO);
	}																			
	public static DynamicSqlBean getRailWorkInfolList(String inputValue, Operator operator, HttpServletRequest request){
		System.out.println("--------------getRailWorkInfolList------------------");
		String sql = "select work_code as workCode, work_code ||' - '||work_name from   rail_work_info where 1=1 "+getDelDiff(0);;
		
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static String getDelDiff(int asT){
		if(asT==1){
			return " and t.DEL_STATUS='"+DelStatusEnums.NO.getCode()+"'"; 
		}else{
			
			return "  and  DEL_STATUS='"+DelStatusEnums.NO.getCode()+"'"; 
		}
	}
		public static String getDepts(Operator operator){
		 String orgBrhId=operator.getOprBrhId();
		 StringBuffer where = new StringBuffer();
	        List<String> list_depts= new ArrayList<String>();
	        list_depts.add(orgBrhId);
	        //查询当前部门下的所有下属部门
			where.append("and t.DEPT in (");
			if(null!=list_depts && list_depts.size()>0){
				String deptCode = list_depts.get(0);
				ApiAppBO ApiAppBO =  (ApiAppBO) ContextUtil.getBean("ApiAppBO");
				list_depts = ApiAppBO.getDeptList(list_depts,orgBrhId);
				for(String id :list_depts){
					where.append("'"+id+"',");
				}
			}
			//查询当前部门下的所有下属部门
			where.append("'-1')");
			return where.toString();
	}
	
		public static String getDepts(Operator operator,String deptStr){
			String orgBrhId=operator.getOprBrhId();
			StringBuffer where = new StringBuffer();
			List<String> list_depts= new ArrayList<String>();
			list_depts.add(orgBrhId);
			//查询当前部门下的所有下属部门
			where.append("and t."+deptStr+" in (");
			if(null!=list_depts && list_depts.size()>0){
				String deptCode = list_depts.get(0);
				ApiAppBO ApiAppBO =  (ApiAppBO) ContextUtil.getBean("ApiAppBO");
				list_depts = ApiAppBO.getDeptList(list_depts,orgBrhId);
				for(String id :list_depts){
					where.append("'"+id+"',");
				}
			}
			//查询当前部门下的所有下属部门
			where.append("'-1')");
			return where.toString();
		}
		
	
}
