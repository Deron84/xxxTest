package com.rail.bo.pub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.common.StringUtil;
import com.rail.po.tool.RailToolModel;
import com.rail.po.tool.RailToolProp;

/**
 * Title: PubSearch
 * Description:  通用的查询通用类
 * @author Zhaozc 
 * @date 上午10:49:47 
 */
public class PubSearch extends HibernateDaoSupport{
	
	/**
	 * @Description: TODO 
	 * @param @param param
	 * @param @return 返回对应的模型表
	 * @return Map<String,RailToolProp>
	 * @throws
	 */
	//对应表 RailToolType t4 RailToolName t3 RailToolUnit t2 RailToolModel  t1
	public Map<String, RailToolProp> getToolModleInfo(Map<String, String> param){
		StringBuffer sql=new StringBuffer();
		StringBuffer where=new StringBuffer();
		String toolType=param.get("toolType");
		if(!StringUtil.isEmpty(toolType)){
			where.append(" and t4.id ="+toolType);
		}
		String toolName=param.get("toolName");
		if(!StringUtil.isEmpty(toolName)){
			where.append(" and t3.id ="+toolName);
		}
		String toolUnit=param.get("toolUnit");
		if(!StringUtil.isEmpty(toolUnit)){
			where.append(" and t2.id ="+toolUnit);
		}
		String toolModel=param.get("toolModel");
		if(!StringUtil.isEmpty(toolModel)){
			where.append(" and t1.modelCode ='"+toolModel+"' ");
		}
		//long toolTypeCode, String toolTypeName, String toolName, RailToolModel railToolModel,String toolUnitName
		
		sql.append("select new com.rail.po.tool.RailToolProp(t4.id,t4.toolTypeName,t3.toolName,t1,t2.toolUnitName)");
		sql.append(" from RailToolType t4,RailToolName t3,RailToolModel  t1,RailToolUnit t2");
		sql.append(" where  t4.id = t3.toolType and  t3.id = t1.toolName   and t1.toolUnit=t2.id   ");
		sql.append(where);
		List<RailToolProp> list= this.getHibernateTemplate().find(sql.toString());
		Map<String, RailToolProp> ret=new HashMap<String, RailToolProp>();
		if (list!=null && list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				RailToolProp tmpProp=list.get(i);
				ret.put(tmpProp.getRailToolModel().getModelCode(), tmpProp);
			}
		}
		return ret;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
