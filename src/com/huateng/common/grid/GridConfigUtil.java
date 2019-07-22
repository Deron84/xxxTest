package com.huateng.common.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.huateng.common.Constants;
import com.huateng.common.GridConfigConstants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.JSONBean;

/**
 * Title:信息列表工具
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-6-5
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class GridConfigUtil {
	
	private static Map<String, GridModel> gridConfigMap = new HashMap<String, GridModel>(10);
	
	/**
	 * 初始化信息列表
	 * @param context
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void initGirdConfig(ServletContext context) throws Exception {
		
		SAXReader reader = new SAXReader();
		
		Document document = null;
		document = reader.read(context.getResourceAsStream(Constants.GRID_CONFIG_CONTEXTPATH));
		
		Element root = document.getRootElement();
		
		List<Element> gridInfoList = root.elements(GridConfigConstants.NODE_GRID);
		
		for(Element gridNode : gridInfoList) {
			String gridId = gridNode.attributeValue(GridConfigConstants.GRID_ID);
			String gridConfigType = gridNode.attributeValue(GridConfigConstants.GRID_TYPE);
			
			//信息组织模型
			GridModel gridModel = new GridModel();
			gridModel.setId(gridId);
			gridModel.setType(gridConfigType);
			gridModel.setColumns(gridNode.elementText(GridConfigConstants.COLUMNS).trim());
			
			if(GridConfigConstants.TYPE_SQL.equals(gridConfigType)) {
				
				SqlMode sqlMode = new SqlMode();
				//SQL模式
				Element sqlModeNode = gridNode.element(GridConfigConstants.TYPE_SQLMODE);
								
				sqlMode.setSql(sqlModeNode.elementText(GridConfigConstants.SQL).trim());
				sqlMode.setDao(sqlModeNode.elementText(GridConfigConstants.QUERY_DAO).trim());
				
				//查询条件
				Element wheresNode = sqlModeNode.element(GridConfigConstants.WHERES);
				List<Element> whereList = wheresNode.elements(GridConfigConstants.WHERE);

				//如果有查询条件
				if(whereList != null && whereList.size() > 0) {
					WheresModel wheresModel = new WheresModel();
					List<WhereModel> whereModelList = new ArrayList<WhereModel>();
					for(Element whereNode : whereList) {
						WhereModel whereModel = new WhereModel();
						whereModel.setType(whereNode.attributeValue(GridConfigConstants.WHERE_TYPE));
						whereModel.setOperator(whereNode.attributeValue(GridConfigConstants.WHERE_OPERATOR));
						whereModel.setLogic(whereNode.attributeValue(GridConfigConstants.WHERE_LOGIC));
						whereModel.setDataBaseColumn(whereNode.elementText(GridConfigConstants.WHERE_DATABASE_COLUMN).trim());
						whereModel.setQueryColumn(whereNode.elementText(GridConfigConstants.WHERE_QUERY_COLUMN).trim());
						whereModelList.add(whereModel);
					}
					wheresModel.setWhereModelList(whereModelList);
					sqlMode.setWheresModel(wheresModel);
				}
				
				
				// 判断是否需要排序
				if(sqlModeNode.element(GridConfigConstants.ORDERS) != null) {
					OrdersModel ordersModel = new OrdersModel();
					Element orderModels = sqlModeNode.element(GridConfigConstants.ORDERS);
					ordersModel.setSort(orderModels.attributeValue("sort"));
					List<Element> orderList = orderModels.elements(GridConfigConstants.ORDER);
					for(Element element : orderList) {
						ordersModel.getOrders().add(element.getText());
					}
					sqlMode.setOrdersModel(ordersModel);
				}
				
				gridModel.setSqlMode(sqlMode);
			} else if(GridConfigConstants.TYPE_SYNC.equals(gridConfigType)) {
				
				SyncMode syncMode = new SyncMode();
				
				Element syncModeNode = gridNode.element(GridConfigConstants.TYPE_SYNCMODE);
				
				Element methodNode = syncModeNode.element(GridConfigConstants.SYNC_METHOD);
				
				syncMode.setMethod(methodNode.attributeValue(GridConfigConstants.SYNC_METHOD_VALUE));
				
				gridModel.setSyncMode(syncMode);
			} else {
				throw new Exception("信息组织形式配置错误[ id:" + gridId + " ]");
			}
			
			gridConfigMap.put(gridId, gridModel);
		}
		/*//新增代码		
		for (String key : gridConfigMap.keySet()) {
			GridModel gridModel = gridConfigMap.get(key);
			if (GridConfigConstants.TYPE_SYNC.equals(gridModel.getType())) {
				System.out.println("方法对应：" + gridModel.getId() + ":"
						+ gridModel.getSyncMode().getMethod());
			}

		} */

		
	}
	
	/**
	 * 根据编号获得数据列表
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static String getGridData(String id,int begin,HttpServletRequest request) throws Exception {
		GridModel gridModel = gridConfigMap.get(id);
		
		//数据结果集
		List<Object[]> dataList = null;
		List<Object> jsonDataList = new ArrayList<Object>();
		String totalCount = "";
		if(GridConfigConstants.TYPE_SQL.equals(gridModel.getType())) {
			String sql = gridModel.getSqlMode().getSql();
			String dao = gridModel.getSqlMode().getDao();
			
			// 如果有查询条件
			if(gridModel.getSqlMode().getWheresModel() != null) {
				WheresModel wheresModel = gridModel.getSqlMode().getWheresModel();
				List<WhereModel> whereList = wheresModel.getWhereModelList();
				for(WhereModel whereModel : whereList) {
					//如果查询条件不为空
					String queryColumn = null;
					if(!"".equals(whereModel.getDataBaseColumn()) && !"".equals(whereModel.getQueryColumn())) {
						queryColumn = request.getParameter(whereModel.getQueryColumn());
						//如果输入查询条件
						if(!"".equals(queryColumn) && queryColumn != null) {
							sql += " " + whereModel.getLogic() + " ";
							if(GridConfigConstants.WHERE_TYPE_CHAR.equals(whereModel.getType())) {
								if(GridConfigConstants.WHERE_OPERATOR_EQ.equals(whereModel.getOperator())) {
									sql += whereModel.getDataBaseColumn() + " = '" + queryColumn + "'";
								} else if(GridConfigConstants.WHERE_OPERATOR_NE.equals(whereModel.getOperator())) {
									sql += whereModel.getDataBaseColumn() + " != '" + queryColumn + "'";
							    } else if(GridConfigConstants.WHERE_OPERATOR_GE.equals(whereModel.getOperator())) {
									sql += whereModel.getDataBaseColumn() + " >= '" + queryColumn + "'";
								} else if(GridConfigConstants.WHERE_OPERATOR_GT.equals(whereModel.getOperator())) {
									sql += whereModel.getDataBaseColumn() + " > '" + queryColumn + "'";
								} else if(GridConfigConstants.WHERE_OPERATOR_IN.equals(whereModel.getOperator())) {
									sql += whereModel.getDataBaseColumn() + " in (" + queryColumn + ")";
								} else if(GridConfigConstants.WHERE_OPERATOR_LE.equals(whereModel.getOperator())) {
									sql += whereModel.getDataBaseColumn() + " <= '" + queryColumn + "'";
								} else if(GridConfigConstants.WHERE_OPERATOR_LT.equals(whereModel.getOperator())) {
									sql += whereModel.getDataBaseColumn() + " < '" + queryColumn + "'";
								} else if(GridConfigConstants.WHERE_OPERATOR_LIKE.equals(whereModel.getOperator())) {
									sql += whereModel.getDataBaseColumn() + " like '%" + queryColumn + "%'";
								}
								
							} else if(GridConfigConstants.WHERE_TYPE_NUMBER.equals(whereModel.getType())) {
								if(GridConfigConstants.WHERE_OPERATOR_EQ.equals(whereModel.getOperator())) {
									sql += whereModel.getDataBaseColumn() + " = " + queryColumn;
								} else if(GridConfigConstants.WHERE_OPERATOR_GE.equals(whereModel.getOperator())) {
									sql += whereModel.getDataBaseColumn() + " >= " + queryColumn;
								} else if(GridConfigConstants.WHERE_OPERATOR_GT.equals(whereModel.getOperator())) {
									sql += whereModel.getDataBaseColumn() + " > " + queryColumn;
								} else if(GridConfigConstants.WHERE_OPERATOR_LE.equals(whereModel.getOperator())) {
									sql += whereModel.getDataBaseColumn() + " <= " + queryColumn;
								} else if(GridConfigConstants.WHERE_OPERATOR_LT.equals(whereModel.getOperator())) {
									sql += whereModel.getDataBaseColumn() + " < " + queryColumn;
								} else if(GridConfigConstants.WHERE_OPERATOR_NE.equals(whereModel.getOperator())) {
									sql += whereModel.getDataBaseColumn() + " != " + queryColumn;
								}
							}
						}
					}
				}
			}
			
			// 如果有排序条件
			if(gridModel.getSqlMode().getOrdersModel() != null) {
				OrdersModel ordersModel = gridModel.getSqlMode().getOrdersModel();
				for(int i = 0; i < ordersModel.getOrders().size(); i++) {
					sql += " order by " + ordersModel.getOrders().get(i);
					if((i + 1) < ordersModel.getOrders().size()) {
						sql += ",";
					}
				}
				sql += " " + ordersModel.getSort();
			}
			
			//查询记录总数
			String countSql = "select count(*)" + sql.substring(sql.toLowerCase().indexOf(" from "));
			if(countSql.toLowerCase().indexOf(" order ") != -1) {
				countSql = countSql.substring(0,countSql.indexOf(" order "));
			}
			
			ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean(dao);
			dataList = commQueryDAO.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
			totalCount = commQueryDAO.findCountBySQLQuery(countSql);
		} else if(GridConfigConstants.TYPE_SYNC.equals(gridModel.getType())) {
			SyncMode syncMode = gridModel.getSyncMode();
			Object[] result = (Object[]) GridConfigMethod.class.getMethod(syncMode.getMethod(), 
										new Class[]{int.class,HttpServletRequest.class}).
										invoke(GridConfigMethod.class, begin,request);
			dataList = (List<Object[]>) result[0];
			totalCount = result[1].toString();
		}
		
		//信息列表列名
		String[] columns = gridModel.getColumns().split(",");
		for(Object[] data : dataList) {
			int columnLen = data.length;
			Map<String, String> jsonMap = new HashMap<String, String>();
			for(int i = 0; i < columnLen; i++) {
				jsonMap.put(columns[i], transNull(data[i]));
			}
			jsonDataList.add(jsonMap);
		}
		JSONBean bean = new JSONBean();
		bean.addJSONElement(Constants.JSON_HEANDER_TOTALCOUNT, totalCount);
		bean.addChild(Constants.JSON_HEANDER_DATA, jsonDataList);
		
		return bean.toString();
	}
	
	
	/**
	 * 转化空指针
	 * @param object
	 * @return
	 */
	private static String transNull(Object object) {
		if(object == null) {
			return "";
		} else {
			return object.toString().trim();
		}
	}
}