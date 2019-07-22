package com.rail.bo.work;

import java.util.List;
import java.util.Map;

import com.rail.po.base.RailEmployee;


/**
 * Title: T110801BO
 * Description: 人员管理
 * @author Zhaozc 
 * @date 下午8:14:39 
 */
public interface T130400BO {
	
	/**
	 * 根据条件查询人员信息
	 * @param params
	 * @return
	 */
	public List<RailEmployee> getByParam(Map<String,String> params);

	
	/**
	 * 新增人员
	 * @param railEmployee
	 * @return
	 */
	public String add(RailEmployee railEmployee);
	/**
	 * 
	 * @Description:批量导入员工 
	 * @param @param railEmployee
	 * @param @return   
	 * @return String  
	 * @author syl
	 * @date 2019年5月4日
	 */
	public Map addListFromExcel(List<RailEmployee> railEmployees);
	
	
	/**
	 * 新增人员
	 * @param railEmployee
	 * @return
	 */
	public String update(RailEmployee railEmployee);
	
	/**
	 * 新增人员
	 * @param railEmployee
	 * @return
	 */
	public String delete(RailEmployee railEmployee);
	
	
	/**
	 * 通过ID查询
	 * @return
	 */
	public RailEmployee getById(String id);
	 RailEmployee getByEmpoyeeCode(String employeeCode);

	/**
	 * 根据仓库code获得仓库信息
	 * @Description: TODO
	 * @param @param accessCode
	 * @param @return   
	 * @return RailAccessInfo  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public RailEmployee getByCode(String id);
	 boolean checkExistOnly(String employeeCode,String imgs,List<RailEmployee> finds);
	 /**
	  * 查询员工是否在工单中（未结束的工单）
	  * @Description: TODO
	  * @param @param employeeCode
	  * @param @return   
	  * @return int  
	  * @throws
	  * @author liujihui
	  * @date 2019年6月18日
	  */

	public int getInWorkByCode(String employeeCode);
	}