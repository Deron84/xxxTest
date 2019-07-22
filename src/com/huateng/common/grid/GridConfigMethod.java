package com.huateng.common.grid;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.common.CommonUtil;
import com.huateng.common.Constants;
import com.huateng.common.DataUtil;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.common.accident.TblManualReturnConstants;
import com.huateng.common.accident.TblRTxnConstants;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.DateUtil;
import com.huateng.system.util.FileFilter;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;
import com.rail.bo.api.app.ApiAppBO;
import com.rail.bo.enums.DelStatusEnums;
import com.rail.bo.pub.PubCstSysParam;
import com.rail.bo.staticfinalvalue.ContantValue;
import com.rail.bo.tools.TSendMsgSocketBO;
import com.rail.zWebSocket.PubUtil;

/**
 * Title:信息列表动态获取方法集合
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-6-6
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class GridConfigMethod  extends HibernateDaoSupport{
	/**
	 * 获得巡护中队列表
	 * @Description: TODO
	 * @param @param begin
	 * @param @param request
	 * @param @return
	 * @param @throws ParseException   
	 * @return Object[]  
	 * @throws UnsupportedEncodingException 
	 * @throws
	 * @author liujihui
	 * @date 2019年5月10日
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getPatrols(int begin, HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE 1=1 ");
		whereSql.append(" AND t.DEL_STATUS = 0 ");
		String patrolName=request.getParameter("patrolName");
		if (isNotEmpty(patrolName)) {
			if(begin<0)
				patrolName= new String(patrolName .getBytes("ISO-8859-1"), "UTF-8");
			whereSql.append(" AND t.PATROL_NAME like '%" + patrolName+"%' ");
		}
		String sql = " SELECT t.ID,t.PATROL_NAME,TO_CHAR(t.ADD_DATE,'YYYY-MM-DD hh24:mi:ss') as ADD_DATE FROM RAIL_PATROL t "
				+ whereSql.toString()+" ORDER BY t.ID ASC ";
		
		String countSql = "SELECT COUNT(*) FROM RAIL_PATROL t "+ whereSql.toString();
		List<Object[]> dataList = new ArrayList<Object[]>();
		if(begin < 0){
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 获得班组成员列表
	 * @Description: TODO
	 * @param @param begin
	 * @param @param request
	 * @param @return   
	 * @return Object[]  
	 * @throws ParseException 
	 * @throws
	 * @author liujihui
	 * @date 2019年4月18日
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTeamEmployees(int begin, HttpServletRequest request) throws ParseException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE 1=1 ");
		whereSql.append(" AND t.NOTE1=0 ");
		
		if (isNotEmpty(request.getParameter("workTeam"))) {
			whereSql.append(" AND t1.WORK_TEAM = " + request.getParameter("workTeam"));
		}
		
		String sql = "SELECT t.employee_Code,t.employee_Name,t.sex,TRUNC( months_between( SYSDATE, t.BIRTHDAY ) / 12 ) AS age,t.id_Number,t.employee_Img,t3.COST_ORG_NAME,t.job,t2.TYPE_NAME,t.employee_Tel,t4.BRH_NAME,t.const_Org,t.employee_Type,t.password,t.entry_Date,t.dept,t.birthday "
				+ "  FROM RAIL_TEAM_EMPLOYEE t1 LEFT JOIN RAIL_EMPLOYEE t ON t.EMPLOYEE_CODE = t1.EMPLOYEE_CODE "
				+ " LEFT JOIN RAIL_EMPLOYEE_TYPE t2 ON t2.ID = t.EMPLOYEE_TYPE "
				+ " LEFT JOIN RAIL_CONST_ORG t3 ON t3.ID = t.const_Org "
				+ " LEFT JOIN TBL_BRH_INFO t4 ON t4.BRH_ID = t.dept "
				 + whereSql.toString() + " order by t1.ADD_DATE desc";
		String countSql = "SELECT COUNT(1) FROM RAIL_TEAM_EMPLOYEE t1 LEFT JOIN RAIL_EMPLOYEE t ON t.EMPLOYEE_CODE = t1.EMPLOYEE_CODE LEFT JOIN RAIL_EMPLOYEE_TYPE t2 ON t2.ID = t.EMPLOYEE_TYPE LEFT JOIN RAIL_CONST_ORG t3 ON t3.ID = t.const_Org LEFT JOIN TBL_BRH_INFO t4 ON t4.BRH_ID = t.dept " + whereSql.toString();
		List<Object[]> dataList ;
		if(begin < 0){
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[17];
			for (Object[] obj : dataList) {
				tmpObj= new Object[17];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				if (obj[2] != null && !obj[2].equals("")) {
					//1男；2女
					if (obj[2].equals("1")) {
						tmpObj[2] = "男";
					} else if (obj[2].equals("2")) {
						tmpObj[2] = "女";
					} else {
						tmpObj[2] = "未知";
					}
				}
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				tmpObj[7]=obj[7];
				tmpObj[8]=obj[8];
				tmpObj[9]=obj[9];
				tmpObj[10]=obj[10];
				tmpObj[11]=obj[11];
				tmpObj[12]=obj[12];
				tmpObj[13]=obj[13];
				tmpObj[14]=obj[14];
				tmpObj[15]=obj[15];
				tmpObj[16]=obj[16];
				dataListRet.add(tmpObj);
			}
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	/**
	 * 获得工具移库列表
	 * @Description: TODO
	 * @param @param begin
	 * @param @param request
	 * @param @return   
	 * @return Object[]  
	 * @throws ParseException 
	 * @throws UnsupportedEncodingException 
	 * @throws
	 * @author liujihui
	 * @date 2019年4月18日
	 */
	@SuppressWarnings("unchecked")
    public static Object[] getRailToolTransfer(int begin, HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" WHERE 1=1 ");
        if (!StringUtil.isNull(request.getParameter("startDate"))&&!StringUtil.isNull(request.getParameter("endDate"))) {
        	Date endTime = DateUtil.add(DateUtil.strToDate(request.getParameter("endDate"), DateUtil.PATTERN_YMD), 1);
        	whereSql.append(" AND rtt.ADD_DATE between to_date('"+request.getParameter("startDate")+"','yyyy-MM-dd') AND to_date('"+DateUtil.dateToStr(endTime, DateUtil.PATTERN_YMD)+"','yyyy-MM-dd')");
        }
//      String toolName = request.getParameter("toolName");
//      if (isNotEmpty(toolName)) {
//      	if(begin<0)
//      		toolName= new String(toolName .getBytes("ISO-8859-1"), "UTF-8");
//			whereSql.append(" AND t3.tool_Name like '%" + request.getParameter("toolName") + "%' ");
//		}
        String toolCode = request.getParameter("toolCode");
        if (isNotEmpty(request.getParameter("toolCode"))) {
        	if(begin<0)
        		toolCode= new String(toolCode .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND rtt.TOOL_CODE like '%" + toolCode + "%' ");
        }
        String toolName = request.getParameter("toolName");
        if (isNotEmpty(toolName)) {
        	if(begin<0)
        		toolName= new String(toolName .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND t2.TOOL_NAME like '%" + toolName + "%' ");
        }
        
        
        String whseBeforeCode = request.getParameter("whseBeforeCode");
        if (isNotEmpty(whseBeforeCode)) {
        	if(begin<0)
        		whseBeforeCode= new String(whseBeforeCode .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND rtt.WHSE_BEFORE like '%" + whseBeforeCode + "%' ");
        }
        String whseBeforeName = request.getParameter("whseBeforeName");
        if (isNotEmpty(whseBeforeName)) {
        	if(begin<0)
        		whseBeforeName= new String(whseBeforeName .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND rwi1.WHSE_NAME like '%" + whseBeforeName + "%' ");
        }
        String whseAfterCode = request.getParameter("whseAfterCode");
        if (isNotEmpty(whseAfterCode)) {
        	if(begin<0)
        		whseAfterCode= new String(whseAfterCode .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND rtt.WHSE_AFTER like '%" + whseAfterCode + "%' ");
        }
        String whseAfterName = request.getParameter("whseAfterName");
        if (isNotEmpty(whseAfterName)) {
        	if(begin<0)
        		whseAfterName= new String(whseAfterName .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND rwi2.WHSE_NAME like '%" + whseAfterName + "%' ");
        }
        //toolCode,toolName,modelCode,toolExpiration,costOrgName,whseCode,whseName,workName,toolStatus,inWhse,lastExam,addDate
        String sql = "SELECT rtt.TOOL_CODE,t2.TOOL_NAME,rtt.WHSE_BEFORE as WHSE_BEFORE_CODE,rwi1.WHSE_NAME as WHSE_BEFORE_NAME ,rtt.WHSE_AFTER as WHSE_AFTER_CODE,rwi2.WHSE_NAME as WHSE_AFTER_NAME,rtt.TRANSFER_MSG,rtt.INFO_SIGN,toi.OPR_NAME,TO_CHAR(rtt.ADD_DATE,'YYYY-MM-DD hh24:mi:ss') as ADD_DATE,to_char(rti.TOOL_EXPIRATION,'YYYY-MM-DD') as TOOL_EXPIRATION,rti.TOOL_STATUS,rti.IN_WHSE,rti.LAST_EXAM "
                + " FROM RAIL_TOOL_TRANSFER rtt LEFT JOIN RAIL_TOOL_INFO rti ON rtt.TOOL_CODE = rti.TOOL_CODE LEFT JOIN RAIL_WHSE_INFO rwi1 ON rtt.WHSE_BEFORE=rwi1.WHSE_CODE  LEFT JOIN RAIL_WHSE_INFO rwi2 ON rtt.WHSE_AFTER=rwi2.WHSE_CODE "
                + " LEFT JOIN RAIL_TOOL_NAME t2 ON t2.id=rti.TOOL_NAME LEFT JOIN TBL_OPR_INFO toi ON toi.OPR_ID=rtt.ADD_USER " + whereSql.toString() + " order by rtt.ADD_DATE desc";
        String countSql = "SELECT COUNT(1) FROM RAIL_TOOL_TRANSFER rtt LEFT JOIN TBL_OPR_INFO toi ON toi.OPR_ID=rtt.ADD_USER LEFT JOIN RAIL_TOOL_INFO rti ON rtt.TOOL_CODE = rti.TOOL_CODE LEFT JOIN RAIL_WHSE_INFO rwi1 ON rtt.WHSE_BEFORE=rwi1.WHSE_CODE  LEFT JOIN RAIL_WHSE_INFO rwi2 ON rtt.WHSE_AFTER=rwi2.WHSE_CODE LEFT JOIN RAIL_TOOL_NAME t2 ON t2.id=rti.TOOL_NAME " + whereSql.toString();
        List<Object[]> dataList ;
        List<Object[]> dataListRet=new ArrayList<Object[]>();
		if(begin < 0){
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		Map<String,Object> PARAMS = PubCstSysParam.getSysParamMap(ContantValue.TRANSFER_TYPE);
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj;
			for (Object[] obj : dataList) {
				tmpObj= obj;
				if (obj[7] != null && !obj[7].equals("")) {
					if(PARAMS.get(String.valueOf(obj[7]))!=null){
						tmpObj[7]=PARAMS.get(String.valueOf(obj[7]));
					}else{
						tmpObj[7] = "未知";
					}
				}
				dataListRet.add(tmpObj);
			}
		}
		
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
    }
	/**
	 * 获得工具出入库记录列表
	 * @Description: TODO
	 * @param @param begin
	 * @param @param request
	 * @param @return   
	 * @return Object[]  
	 * @throws ParseException 
	 * @throws UnsupportedEncodingException 
	 * @throws
	 * @author liujihui
	 * @date 2019年4月18日
	 */
	@SuppressWarnings("unchecked")
    public static Object[] getRailWhseTool(int begin, HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" WHERE 1=1 ");
        if (!StringUtil.isNull(request.getParameter("startDate"))&&!StringUtil.isNull(request.getParameter("endDate"))) {
        	Date endTime = DateUtil.add(DateUtil.strToDate(request.getParameter("endDate"), DateUtil.PATTERN_YMD), 1);
        	whereSql.append(" AND rwt.ADD_DATE between to_date('"+request.getParameter("startDate")+"','yyyy-MM-dd') AND to_date('"+DateUtil.dateToStr(endTime, DateUtil.PATTERN_YMD)+"','yyyy-MM-dd')");
        }
        
        
        
        
        
//        String toolName = request.getParameter("toolName");
//        if (isNotEmpty(toolName)) {
//        	if(begin<0)
//        		toolName= new String(toolName .getBytes("ISO-8859-1"), "UTF-8");
//			whereSql.append(" AND t3.tool_Name like '%" + request.getParameter("toolName") + "%' ");
//		}
        
        
        String toolCode = request.getParameter("toolCode");
        if (isNotEmpty(toolCode)) {
        	if(begin<0)
        		toolCode= new String(toolCode .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND rwt.TOOL_CODE like '%" + toolCode + "%' ");
        }
        String toolName = request.getParameter("toolName");
        if (isNotEmpty(toolName)) {
        	if(begin<0)
        		toolName= new String(toolName .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND rtn.TOOL_NAME like '%" + toolName + "%' ");
        }
        String whseCode = request.getParameter("whseCode");
        if (isNotEmpty(whseCode)) {
        	if(begin<0)
        		whseCode= new String(whseCode .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND rwt.WHSE_CODE like '%" + whseCode + "%' ");
        }
        String whseName = request.getParameter("whseName");
        if (isNotEmpty(whseName)) {
        	if(begin<0)
        		whseName= new String(whseName .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND rwi.WHSE_NAME like '%" + whseName + "%' ");
        }
        //0toolCode,1toolName,2toolExpiration,3whseCode,4whseName,5workCode,6workName,7infoSign,8addDate,9toolStatus,10inWhse,11 lastExam
        String sql = "SELECT rwt.TOOL_CODE,rtn.TOOL_NAME,to_char(rti.TOOL_EXPIRATION,'YYYY-MM-DD') as TOOL_EXPIRATION,rwt.WHSE_CODE,"
        		+ "rwi.WHSE_NAME,rwri.WORK_CODE,rwri.WORK_NAME,rwt.INFO_SIGN,to_char(rwt.ADD_DATE,'YYYY-MM-DD hh24:mi:ss') as ADD_DATE,"
        		+ "rti.TOOL_STATUS,rti.IN_WHSE,to_char(rti.LAST_EXAM,'YYYY-MM-DD') as LAST_EXAM "
                + " FROM RAIL_WHSE_TOOL rwt LEFT JOIN RAIL_TOOL_INFO rti on rwt.TOOL_CODE = rti.TOOL_CODE LEFT JOIN RAIL_TOOL_NAME rtn ON rti.TOOL_NAME = rtn.ID LEFT JOIN RAIL_WHSE_INFO rwi ON rwt.WHSE_CODE = rwi.WHSE_CODE LEFT JOIN RAIL_WORK_INFO rwri ON rwt.WORK_CODE = rwri.WORK_CODE " + whereSql.toString() + " order by RWT.ADD_DATE desc";
        String countSql = "SELECT COUNT(1) FROM RAIL_WHSE_TOOL rwt LEFT JOIN RAIL_TOOL_INFO rti on rwt.TOOL_CODE = rti.TOOL_CODE LEFT JOIN RAIL_TOOL_NAME rtn ON rti.TOOL_NAME = rtn.ID LEFT JOIN RAIL_WHSE_INFO rwi ON rwt.WHSE_CODE = rwi.WHSE_CODE LEFT JOIN RAIL_WORK_INFO rwri ON rwt.WORK_CODE = rwri.WORK_CODE  " + whereSql.toString();
        List<Object[]> dataList ;
		if(begin < 0){
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        List<Object[]> dataListRet=new ArrayList<Object[]>();
        Map<String,Object> IOWhSE = PubCstSysParam.getSysParamMap(ContantValue.IOWhSE);
        if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[12];
			for (Object[] obj : dataList) {
				//0toolCode,1toolName,2toolExpiration,3whseCode,4whseName,5workCode,6workName,7infoSign,8addDate,9toolStatus,10inWhse,11 lastExam
				tmpObj= new Object[12];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				if (obj[7] != null && !obj[7].equals("")) {
					if(IOWhSE.get(String.valueOf(obj[7]))!=null){
						tmpObj[7] = IOWhSE.get(String.valueOf(obj[7]));
					}else{
						tmpObj[7] = "未知";
					}
				}
				tmpObj[8]=obj[8];
				tmpObj[9]=obj[9];
				
				tmpObj[10]=obj[10];
				tmpObj[11]=obj[11];
				dataListRet.add(tmpObj);
			}
		}
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
    }
	@SuppressWarnings("unchecked")
	public static Object[] getRailToolList(int begin, HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE 1=1 ");
		String whseCode = request.getParameter("whseCode");
		if (isNotEmpty(whseCode)) {
			if(begin<0)
				whseCode= new String(whseCode .getBytes("ISO-8859-1"), "UTF-8");
			whereSql.append(" AND t.WHSE_CODE  ='" + whseCode + "'");
		}
        String toolName = request.getParameter("toolName");
        if (isNotEmpty(toolName)) {
        	if(begin<0)
        		toolName= new String(toolName .getBytes("ISO-8859-1"), "UTF-8");
			whereSql.append(" AND t3.tool_Name like '%" + toolName + "%' ");
		}
		String sql = " select  t.id,t.tool_Code ,t3.tool_Name,t.model_Code,"
				+ " t.tool_Img,t.tool_Expiration,t.whse_Code,t.rfid,t.purchase_Dept,t.purchase_User,t.mfrs_Org,"
				+ " t.tool_Status,t.del_Status,t.in_Whse,t.exam_Period,t.last_Exam,"
				+ " t.access_Code,toi.OPR_NAME,t.add_Date,t.upd_User,t.upd_Date,t.note1,t.list_Price,t.init_Price,dept "
				+ " from RAIL_TOOL_INFO t LEFT JOIN TBL_OPR_INFO toi ON toi.OPR_ID=t.ADD_USER "
				+ " left join RAIL_TOOL_MODEL  t2 on t.MODEL_CODE = t2.MODEL_CODE left join RAIL_TOOL_NAME t3 on t2.TOOL_NAME = t3.ID"
				+ whereSql.toString() + " order by t.UPD_DATE desc";
		String countSql = "SELECT COUNT(1) "
				+ " from RAIL_TOOL_INFO t LEFT JOIN TBL_OPR_INFO toi ON toi.OPR_ID=t.ADD_USER "
				+ " left join RAIL_TOOL_MODEL  t2 on t.MODEL_CODE = t2.MODEL_CODE left join RAIL_TOOL_NAME t3 on t2.TOOL_NAME = t3.ID"
				+ whereSql.toString() ;
		List<Object[]> dataList ;
		if(begin < 0){
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	/**
	 * 获得人员出入库记录列表
	 * @Description: TODO
	 * @param @param begin
	 * @param @param request
	 * @param @return   
	 * @return Object[]  
	 * @throws ParseException 
	 * @throws UnsupportedEncodingException 
	 * @throws
	 * @author liujihui
	 * @date 2019年4月18日
	 */
	@SuppressWarnings("unchecked")
    public static Object[] getRailWhseEmployee(int begin, HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" WHERE 1=1 ");
        //employeeCode,employeeName,sex,employeeTel,costOrgName,workCode,workName,whseCode,whseName,infoSign,addDate
        if (!StringUtil.isNull(request.getParameter("startDate"))&&!StringUtil.isNull(request.getParameter("endDate"))) {
        	Date endTime = DateUtil.add(DateUtil.strToDate(request.getParameter("endDate"), DateUtil.PATTERN_YMD), 1);
        	whereSql.append(" AND rwe.ADD_DATE between to_date('"+request.getParameter("startDate")+"','yyyy-MM-dd') AND to_date('"+DateUtil.dateToStr(endTime, DateUtil.PATTERN_YMD)+"','yyyy-MM-dd')");
        }
        
        String whseCode = request.getParameter("whseCode");
        if (isNotEmpty(whseCode)) {
        	if(begin<0)
        		whseCode= new String(whseCode .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND RWI.WHSE_CODE like '%" + whseCode + "%' ");
        }
        String whseName = request.getParameter("whseName");
        if (isNotEmpty(whseName)) {
        	if(begin<0)
				whseName= new String(whseName .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND RWI.WHSE_NAME  like '%" + whseName + "%' ");
        }
        String employeeName = request.getParameter("employeeName");
        if (isNotEmpty(employeeName)) {
        	if(begin<0)
        		employeeName= new String(employeeName .getBytes("ISO-8859-1"), "UTF-8");
        	whereSql.append(" AND re.EMPLOYEE_NAME like '%" + employeeName + "%' ");
        }
        String sql = "SELECT rwe.EMPLOYEE_CODE,re.EMPLOYEE_NAME,re.SEX,re.EMPLOYEE_TEL,rco.COST_ORG_NAME,rwri.WORK_CODE,rwri.WORK_NAME ,rwe.WHSE_CODE,rwi.WHSE_NAME"
        		+ ",rwe.INFO_SIGN,to_char(rwe.ADD_DATE,'YYYY-MM-DD hh24:mi:ss') as ADD_DATE,rwe.INFO_SIGN "
                + "FROM RAIL_WHSE_EMPLOYEE rwe LEFT JOIN RAIL_EMPLOYEE re ON rwe.EMPLOYEE_CODE = re.EMPLOYEE_CODE LEFT JOIN RAIL_WHSE_INFO rwi ON rwi.WHSE_CODE = rwe.WHSE_CODE LEFT JOIN RAIL_CONST_ORG rco ON re.CONST_ORG = rco.ID LEFT JOIN RAIL_WORK_INFO rwri ON rwe.WORK_CODE = rwri.WORK_CODE " + whereSql.toString() + " order by RWE.ADD_DATE desc";
        String countSql = "SELECT COUNT(1) FROM RAIL_WHSE_EMPLOYEE rwe LEFT JOIN RAIL_EMPLOYEE re ON rwe.EMPLOYEE_CODE = re.EMPLOYEE_CODE LEFT JOIN RAIL_WHSE_INFO rwi ON rwi.WHSE_CODE = rwe.WHSE_CODE LEFT JOIN RAIL_CONST_ORG rco ON re.CONST_ORG = rco.ID LEFT JOIN RAIL_WORK_INFO rwri ON rwe.WORK_CODE = rwri.WORK_CODE " + whereSql.toString();
        List<Object[]> dataList ;
		if(begin < 0){
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		 String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		 List<Object[]> dataListRet=new ArrayList<Object[]>();
        Map<String,Object> IOWhSE = PubCstSysParam.getSysParamMap(ContantValue.IOWhSE);
        Map<String,Object> ParamSex = PubCstSysParam.getSysParamMap(ContantValue.SEX);
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[11];
			for (Object[] obj : dataList) {
				//0employeeCode,1employeeName,2sex,3employeeTel,4costOrgName,5workCode,6workName,7whseCode,8whseName,9infoSign,10addDate
				tmpObj= new Object[11];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				if (obj[2] != null && !obj[2].equals("")) {
					if(ParamSex.get(String.valueOf(obj[2]))!=null){
						tmpObj[2] = ParamSex.get(String.valueOf(obj[2]));
					}else{
						tmpObj[2] = "未知";
					}
				}
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				tmpObj[7]=obj[7];
				tmpObj[8]=obj[8];
				if (obj[9] != null && !obj[9].equals("")) {
					if(IOWhSE.get(String.valueOf(obj[9]))!=null){
						tmpObj[9] = IOWhSE.get(String.valueOf(obj[9]));
					}else{
						tmpObj[9] = "未知";
					}
				}
				tmpObj[10]=obj[10];
				dataListRet.add(tmpObj);
			}
		}
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
    }
	/**
	 * 获得仓库列表
	 * @Description: TODO
	 * @param @param begin
	 * @param @param request
	 * @param @return   
	 * @return Object[]  
	 * @throws UnsupportedEncodingException 
	 * @throws
	 * @author liujihui
	 * @date 2019年4月16日
	 */
	@SuppressWarnings("unchecked")
    public static Object[] getRailWhseInfo(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
		where.append("and RWI.WHSE_DEPT in (");
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
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" WHERE 1=1");
        if (isNotEmpty(request.getParameter("whseDept"))) {
            whereSql.append(" AND RWI.WHSE_DEPT =" + request.getParameter("whseDept"));
        }
        String whseCode = request.getParameter("whseCode");
        if (isNotEmpty(whseCode)) {
        	if(begin<0)
        		whseCode= new String(whseCode .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND RWI.WHSE_CODE like '%" + whseCode + "%' ");
        }
        String whseName = request.getParameter("whseName");
        if (isNotEmpty(whseName)) {
        	if(begin<0)
				whseName= new String(whseName .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND RWI.WHSE_NAME  like '%" + whseName + "%' ");
        }
        String whsePic = request.getParameter("whsePic");
        if (isNotEmpty(request.getParameter("whsePic"))) {
        	if(begin<0)
				whseName= new String(whseName .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND RWI.WHSE_PIC  like '%" + whsePic + "%' ");
        }
        if (isNotEmpty(request.getParameter("parentWhseCode"))) {
            whereSql.append(" AND RWI.PARENT_WHSE_CODE  = '" + request.getParameter("parentWhseCode")+"' ");
        }
        String sql = "SELECT RWI.WHSE_CODE,RWI.WHSE_NAME,RWI.WHSE_ADDRESS,RWI2.WHSE_NAME AS parentWhse,RWI.WHSE_CAPA,RWI.WHSE_PIC,RWI.WHSE_TEL,TBI.BRH_NAME,RWI.ENABLE_STATUS,RWI.WHSE_DEPT,RWI.PARENT_WHSE_CODE "
                + "FROM RAIL_WHSE_INFO RWI "
                + "LEFT JOIN TBL_BRH_INFO TBI ON TBI.BRH_ID = RWI.WHSE_DEPT "
                + "LEFT JOIN RAIL_WHSE_INFO RWI2 ON RWI2.WHSE_CODE = rwi.PARENT_WHSE_CODE  " + whereSql.toString()+where + " order by RWI.ADD_DATE desc";
        String countSql = "SELECT COUNT(1) FROM RAIL_WHSE_INFO RWI "
        		+ "LEFT JOIN TBL_BRH_INFO TBI ON TBI.BRH_ID = RWI.WHSE_DEPT "
        		+ "LEFT JOIN RAIL_WHSE_INFO RWI2 ON RWI2.WHSE_CODE = rwi.PARENT_WHSE_CODE " + whereSql.toString();
        List<Object[]> dataList ;
		if(begin < 0){
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
  
        List<Object[]> dataListRet=new ArrayList<Object[]>();
        Map<String,Object> ENABLE_STATUS = PubCstSysParam.getSysParamMap(ContantValue.ENABLE_STATUS);
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[12];
			for (Object[] obj : dataList) {
				tmpObj= new Object[12];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				tmpObj[7]=obj[7];
				
				if (obj[8] != null && !obj[8].equals("")) {
					if(ENABLE_STATUS.get(String.valueOf(obj[8]))!=null){
						tmpObj[8] = ENABLE_STATUS.get(String.valueOf(obj[8]));
					}else{
						tmpObj[8] = "未知";
					}
				}
				tmpObj[9]=obj[8];
				tmpObj[10]=obj[9];
				tmpObj[11]=obj[10];
				dataListRet.add(tmpObj);
			}
		}
		
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
    }
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: 仓库盘点查询列表 100103
	 * @param @param begin
	 * @param @param request  whseCode 仓库编码、仓库名称、工具名称
	 * @param @return 仓库编码、仓库名称、工具名称、正常数量、维护数量、报废数量、停用数量
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getWhseTools(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        
        String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
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
		
		String whseCode = request.getParameter("WHSE_CODE");
		if(!StringUtil.isEmpty(whseCode)){
			where.append(" and t1.WHSE_CODE like '%"+whseCode+"%'");
		}
		
		String whseName = request.getParameter("WHSE_NAME");
		if(!StringUtil.isEmpty(whseName)){
			if(begin<0)
				whseName= new String(whseName .getBytes("ISO-8859-1"), "UTF-8");
			where.append(" and t1.WHSE_NAME like'%"+whseName+"%'");
		}
		
		String toolName = request.getParameter("TOOL_NAME");
		if(!StringUtil.isEmpty(toolName)){
			if(begin<0)
				toolName= new String(toolName .getBytes("ISO-8859-1"), "UTF-8");
			where.append(" and t2.TOOL_NAME like'%"+toolName+"%'");
		}
		
		where.append(" AND t1.DEL_STATUS = 0 AND t.DEL_STATUS = 0 ");
		
		String whereFinal=" FROM RAIL_TOOL_INFO t "
				+ " LEFT JOIN RAIL_WHSE_INFO t1 ON t.WHSE_CODE=t1.WHSE_CODE "
				+ " LEFT JOIN RAIL_TOOL_NAME t2 ON t.TOOL_NAME=t2.ID "
				+ " where 1=1 "+ where ;
		//0仓库编码、1仓库名称、2工具名称、3正常数量、4维护数量、5报废数量、6停用数量、7使用次数
		String sql = " SELECT t1.WHSE_CODE,t1.WHSE_NAME,t2.TOOL_NAME,count(case when t.TOOL_STATUS='0' then 1 else null end) as num0,"
				+ "count(case when t.TOOL_STATUS='1' then 1 else null end) as num1,"
				+ "count(case when t.TOOL_STATUS='3' then 1 else null end) as num3,"
				+ "count(case when t.TOOL_STATUS='4' then 1 else null end) as num4,"
				+ "NVL(SUM(t.NOTE1),0) as useNum "
				+ whereFinal;
		//按照响应的结果进行分类统计
		sql=sql+" GROUP BY t1.WHSE_CODE,t1.WHSE_NAME,t2.ID,t2.TOOL_NAME ";
		List<Object[]> dataList ;
		if(begin < 0){
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
        
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
	}
	
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: 仓库盘统计查询表 100105
	 * @param @param begin
	 * @param @param request  whseCode 仓库编码、仓库名称、工具名称
	 * @param @return 仓库编码、仓库名称、工具名称、存放位置（架-层-位）、在库数量、出库数量、数量
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getWhseToolsStatic(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        
        String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
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
		
		String whseCode = request.getParameter("whseCode");
		if(!StringUtil.isEmpty(whseCode)){
			if(begin<0)
				whseCode= new String(whseCode .getBytes("ISO-8859-1"), "UTF-8");
			where.append(" and t1.WHSE_CODE like '%"+whseCode+"%'");
		}
		
		String whseName = request.getParameter("whseName");
		if(!StringUtil.isEmpty(whseName)){
			if(begin<0)
				whseName= new String(whseName .getBytes("ISO-8859-1"), "UTF-8");
			where.append(" and t1.WHSE_NAME like'%"+whseName+"%'");
		}
		
		String toolName = request.getParameter("toolName");
		if(!StringUtil.isEmpty(toolName)){
			if(begin<0)
				toolName= new String(toolName .getBytes("ISO-8859-1"), "UTF-8");
			where.append(" and t2.TOOL_NAME like'%"+toolName+"%'");
		}
		
		where.append(" AND t1.DEL_STATUS = 0 AND t.DEL_STATUS = 0 ");
		
		String whereFinal=" FROM RAIL_TOOL_INFO t "
				+ " LEFT JOIN RAIL_WHSE_INFO t1 ON t.WHSE_CODE=t1.WHSE_CODE "
				+ " LEFT JOIN RAIL_TOOL_NAME t2 ON t.TOOL_NAME=t2.ID "
				+ " where 1=1 "+ where ;
		//仓库编码、仓库名称、工具名称、存放位置（架-层-位）、数量
		String sql = " SELECT t1.WHSE_CODE,t1.WHSE_NAME,t2.TOOL_NAME,t.STAND,t.FLOOR,t.POSITION,count(case when t.IN_WHSE='1' then 1 else null end) as num0,count(case when t.IN_WHSE='2' then 1 else null end) as num1 "
				+ whereFinal;
		//按照响应的结果进行分类统计
		sql=sql+" GROUP BY t1.WHSE_CODE,t1.WHSE_NAME,t2.ID,t2.TOOL_NAME,t.STAND,t.FLOOR,t.POSITION ";
		List<Object[]> dataList ;
		if(begin < 0){
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        //用于返回的列表
        //返回数据列表 仓库编码、仓库名称、工具名称、存放位置（架-层-位）、数量
        List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[7];
			for (Object[] obj : dataList) {
				tmpObj= new Object[7];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				if (obj[3]==null&&obj[4]==null&&obj[5]==null) {
					tmpObj[3]="";
				}else {
					if(obj[3]==null){
						obj[3]=" ";
					}
					if(obj[4]==null){
						obj[4]=" ";
					}
					if(obj[5]==null){
						obj[5]=" ";
					}
					tmpObj[3]=obj[3]+"架-"+obj[4]+"层-"+obj[5]+"位";
				}
				tmpObj[4]=obj[6];
				tmpObj[5]=obj[7];
				tmpObj[6]=Integer.valueOf(obj[6].toString())+Integer.valueOf(obj[7].toString());  
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
	}
	//==================================工具相关分页查询开始=====================================
	/**
	 * @Description: 得到工具检修保养列表 110802
	 * @param @param begin
	 * @param @param request  whseCode 仓库编码 dateStart开始时间 dateEnd结束时间
	 * @param @return 仓库名称、工具名称、标签号、存放位置（架层位）、入库时间、保养周期、保养人、保养时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getToolMaintains(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        
        String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
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
		
		String toolCode = request.getParameter("toolCode");
		if(!StringUtil.isEmpty(toolCode)){
			where.append(" and t1.TOOL_CODE = '"+toolCode+"'");
		}
		
		String whseCode = request.getParameter("whseCode");
		if(!StringUtil.isEmpty(whseCode)){
			where.append(" and  t.WHSE_CODE='"+whseCode+"'");
		}
		
		String dateStartR = request.getParameter("dateStart");
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//to_date('2006-04-08 10:00:01','yyyy-MM-dd HH:mm:ss')
		if(!StringUtil.isEmpty(dateStartR)){
			//Date dateStart;
			where.append(" and t1.ADD_DATE>= to_date('"+dateStartR.replace("T", " ")+"','yyyy-mm-dd hh24:mi:ss')");
		}
		String dateEndR = request.getParameter("dateEnd");
		if(!StringUtil.isEmpty(dateEndR)){
			Date endTime = DateUtil.add(DateUtil.strToDate(dateEndR, DateUtil.PATTERN_YMD), 1);
			String endTimeStr = DateUtil.dateToStr(endTime, DateUtil.PATTERN_YMD);
			where.append(" and t1.ADD_DATE<= to_date('"+endTimeStr+"','yyyy-mm-dd hh24:mi:ss')");
		}
		String whereFinal="from RAIL_TOOL_MAINTAIN t1 left join RAIL_TOOL_INFO t on t1.TOOL_CODE=t.TOOL_CODE "
				+ "left join RAIL_TOOL_NAME t2 on t.TOOL_NAME=t2.id left join RAIL_TOOL_TYPE t3 ON t2.TOOL_TYPE=t3.id "
				+ "left join RAIL_TOOL_UNIT t4 on t.TOOL_UNIT=t4.id left join RAIL_WHSE_INFO t5 on t.WHSE_CODE=t5.WHSE_CODE "
				+ "where 1=1 "+ where +" order by t1.ADD_DATE desc";
		//返回数据列表 仓库名称、工具名称、标签号、存放位置（架层位）、入库时间、保养周期、保养人、保养时间
		String sql = "select t5.WHSE_NAME,t2.TOOL_NAME,t.RFID,t.STAND,t.FLOOR,t.POSITION, "
				+ "  TO_CHAR(t.ADD_DATE,'yyyy-mm-dd hh24:mi:ss') as ADD_DATE1,t.EXAM_PERIOD,t1.MAINTAIN_USER, TO_CHAR(t1.ADD_DATE,'yyyy-mm-dd hh24:mi:ss') as ADD_DATE2,t1.id "
				+ whereFinal;
		List<Object[]> dataList ;
		if(begin<0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		//List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        //用于返回的列表
        //返回数据列表 仓库名称、工具名称、标签号、存放位置（架层位）、入库时间、保养周期、保养人、保养时间
        List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[9];
			for (Object[] obj : dataList) {
				tmpObj= new Object[9];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				if (obj[3] != null && !obj[3].equals("")) {
					tmpObj[3]=obj[3]+"架-"+obj[4]+"层-"+obj[5]+"位";
				}
				tmpObj[4]=obj[6];
				tmpObj[5]=obj[7];
				tmpObj[6]=obj[8];
				tmpObj[7]=obj[9];
				tmpObj[8]=obj[10];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
	}
	
	
	/**
	 * @Description: 得到工具预警列表 110701
	 * @param @param begin
	 * @param @param request  whseCode 仓库编码 dateStart开始时间 dateEnd结束时见   infoSign预警状态
	 * @param @return 仓库名称、工具名称、标签号、存放位置（架层位）、预警内容、是否确认、审核人、审核时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getToolMaintainWarnning(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        
        String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
//		where.append("and t.DEPT in (");
//		if(null!=list_depts && list_depts.size()>0){
//			String deptCode = list_depts.get(0);
//			ApiAppBO ApiAppBO =  (ApiAppBO) ContextUtil.getBean("ApiAppBO");
//			list_depts = ApiAppBO.getDeptList(list_depts,orgBrhId);
//			for(String id :list_depts){
//				where.append("'"+id+"',");
//			}
//		}
//		//查询当前部门下的所有下属部门
//		where.append("'-1')");
		
		String toolCode = request.getParameter("toolCode");
		if(!StringUtil.isEmpty(toolCode)){
			where.append(" and t1.TOOL_CODE ='"+toolCode+"'");
		}
		
		String warnType = request.getParameter("warnType");
		if(!StringUtil.isEmpty(warnType)&&!"-1".equals(warnType)){
			where.append(" and t1.NOTE1 ='"+warnType+"'");
		}
		
		String whseCode = request.getParameter("whseCode");
		if(!StringUtil.isEmpty(whseCode)){
			where.append(" and t.WHSE_CODE='"+whseCode+"'");
		}
		
		String dateStartR = request.getParameter("dateStart");
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//to_date('2006-04-08 10:00:01','yyyy-MM-dd HH:mm:ss')
		if(!StringUtil.isEmpty(dateStartR)){
			//Date dateStart;
			where.append(" and t1.ADD_DATE>= to_date('"+dateStartR.replace("T", " ")+"','yyyy-mm-dd hh24:mi:ss')");
		}
		String dateEndR = request.getParameter("dateEnd");
		if(!StringUtil.isEmpty(dateEndR)){
			Date endTime = DateUtil.add(DateUtil.strToDate(dateEndR, DateUtil.PATTERN_YMD), 1);
			String endTimeStr = DateUtil.dateToStr(endTime, DateUtil.PATTERN_YMD);
			where.append(" and t1.ADD_DATE<= to_date('"+endTimeStr+"','yyyy-mm-dd hh24:mi:ss')");;
		}

		String infoSign = request.getParameter("infoSign");
		if(!StringUtil.isEmpty(infoSign)&&!"-1".equals(infoSign)){
			where.append(" and t1.INFO_SIGN='"+infoSign+"'");
		}
		
		String whereFinal="from RAIL_TOOL_MAINTAIN_WARN t1 left join RAIL_TOOL_INFO t on t1.TOOL_CODE=t.TOOL_CODE "
				+ "left join TBL_OPR_INFO t2 on t1.VERIFY_USER=t2.OPR_ID "
				+ "left join RAIL_TOOL_NAME t3 on t.TOOL_NAME=t3.id "
				+ "left join RAIL_TOOL_TYPE t4 ON t3.TOOL_TYPE=t4.id "
				+ "left join RAIL_TOOL_UNIT t5 on t.TOOL_UNIT=t5.id "
				+ "left join RAIL_WHSE_INFO t6 on t.WHSE_CODE=t6.WHSE_CODE "
				+ "where  1=1 "+ where +" ORDER BY t1.ADD_DATE DESC ";
		//仓库名称、工具名称、标签号、存放位置（架层位）、预警内容、是否确认、审核人、审核时间
		String sql = "select t6.WHSE_NAME,t3.TOOL_NAME,t.RFID,t.STAND,t.FLOOR,t.POSITION "
				+ ",t1.WARN_MSG,t1.INFO_SIGN,t1.VERIFY_MSG,t2.OPR_NAME, TO_CHAR(t1.VERIFY_DATE,'yyyy-mm-dd hh24:mi:ss') as VERIFY_DATE,t1.note1,t1.id "
				+ whereFinal ;
		List<Object[]> dataList ;
		if(begin<0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		//List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Map<String,Object> PARAMS = PubCstSysParam.getSysParamMap(ContantValue.VERIFY_WARN);
        
        //用于返回的列表
      //仓库名称、工具名称、标签号、存放位置（架层位）、预警内容、是否确认、审核人、审核时间
        List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[11];
			for (Object[] obj : dataList) {
				tmpObj= new Object[11];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				if (obj[3] != null && !obj[3].equals("")) {
					tmpObj[3]=obj[3]+"架-"+obj[4]+"层-"+obj[5]+"位";
				}
				tmpObj[4]=obj[6];
				if (obj[7] != null && !obj[7].equals("")) {
					
					if(PARAMS.get(String.valueOf(obj[7]))!=null){
						tmpObj[5]=PARAMS.get(String.valueOf(obj[7]));
					}else{
						tmpObj[5] = "未知";
					}
					
				}
				
				tmpObj[6]=obj[8];
				tmpObj[7]=obj[9];
				tmpObj[8]=obj[10];
				
				if (obj[11] != null && !obj[11].equals("")) {
					if (obj[11].equals("1")) {
						tmpObj[9] = "报废预警";
					} else if (obj[11].equals("2")) {
						tmpObj[9] = "检修预警";
					} else {
						tmpObj[9] = "未知";
					}
				}
				tmpObj[10]=obj[12];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
	}
	
	/**
	 * @Description: 得到工具报废列表 110602/110603
	 * @param @param begin
	 * @param @param request  whseCode 仓库编码 dateStart报废申请开始时间 dateEnd报废申请结束时间   infoSign审核状态
	 * @param @return 仓库名称、工具名称、标签号、存放位置（架层位）、报废信息、申请人、申请时间、审核状态、审核人、审核时间、审核信息
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getToolScrap(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        
        String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
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
		
		String toolCode = request.getParameter("toolCode");
		if(!StringUtil.isEmpty(toolCode)){
			where.append(" and t1.TOOL_CODE = '"+toolCode+"'");
		}
		
		String whseCode = request.getParameter("whseCode");
		if(!StringUtil.isEmpty(whseCode)){
			where.append(" and t.WHSE_CODE = '"+whseCode+"'");
		}
		String dateStartR = request.getParameter("dateStart");
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//to_date('2006-04-08 10:00:01','yyyy-MM-dd HH:mm:ss')
		if(!StringUtil.isEmpty(dateStartR)){
			//Date dateStart;
			where.append(" and t1.APPLY_DATE>= to_date('"+dateStartR.replace("T", " ")+"','yyyy-mm-dd hh24:mi:ss')");
		}
		String dateEndR = request.getParameter("dateEnd");
		if(!StringUtil.isEmpty(dateEndR)){
			Date endTime = DateUtil.add(DateUtil.strToDate(dateEndR, DateUtil.PATTERN_YMD), 1);
			String endTimeStr = DateUtil.dateToStr(endTime, DateUtil.PATTERN_YMD);
			where.append(" and t1.APPLY_DATE<= to_date('"+endTimeStr+"','yyyy-mm-dd hh24:mi:ss')");
		}

		String infoSign = request.getParameter("infoSign");
		if(!StringUtil.isEmpty(infoSign)&&!"-1".equals(infoSign)){
			where.append(" and t1.INFO_SIGN = '"+infoSign+"'");
		}
		
		String whereFinal="from RAIL_TOOL_SCRAP t1 left join RAIL_TOOL_INFO t on t1.TOOL_CODE=t.TOOL_CODE "
				+ "left join TBL_OPR_INFO t2 on t1.VERIFY_USER=t2.OPR_ID "
				+ "left join RAIL_TOOL_NAME t3 on t.TOOL_NAME=t3.id "
				+ "left join RAIL_TOOL_TYPE t4 ON t3.TOOL_TYPE=t4.id "
				+ "left join RAIL_TOOL_UNIT t5 on t.TOOL_UNIT=t5.id "
				+ "left join RAIL_WHSE_INFO t6 on t.WHSE_CODE=t6.WHSE_CODE "
				+ "where  1=1  "+ where  ;
		//仓库名称、工具名称、标签号、存放位置（架层位）、报废信息、申请人、申请时间、审核状态、审核人、审核时间、审核信息
		String sql = "select t6.WHSE_NAME,t3.TOOL_NAME,t.RFID,t.STAND,t.FLOOR,t.POSITION "
				+ ",t1.APPLY_MSG,t1.APPLY_USER, TO_CHAR(t1.APPLY_DATE,'yyyy-mm-dd hh24:mi:ss') as APPLY_DATE,t1.VERIFY_MSG,t1.INFO_SIGN,t2.OPR_NAME, TO_CHAR(t1.VERIFY_DATE,'yyyy-mm-dd hh24:mi:ss') as VERIFY_DATE,t1.id "
				+ whereFinal +" order by t1.APPLY_DATE desc ";
		List<Object[]> dataList ;
		if(begin<0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        
        Map<String,Object> PARAMS = PubCstSysParam.getSysParamMap(ContantValue.EXAM_STATUS);
        
        //用于返回的列表
       //仓库名称、工具名称、标签号、3存放位置（架层位）、报废信息、申请人、申请时间、7审核状态、审核人、审核时间、审核信息
        List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[12];
			for (Object[] obj : dataList) {
				tmpObj= new Object[12];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				if (obj[3] != null && !obj[3].equals("")) {
					tmpObj[3]=obj[3]+"架-"+obj[4]+"层-"+obj[5]+"位";
				}
				tmpObj[4]=obj[6];
				tmpObj[5]=obj[7];
				tmpObj[6]=obj[8];
				
				tmpObj[7]=obj[9];
				if (obj[10] != null && !obj[10].equals("")) {
					if(PARAMS.get(String.valueOf(obj[10]))!=null){
						tmpObj[8]=PARAMS.get(String.valueOf(obj[10]));
					}else{
						tmpObj[8] = "未知";
					}
				}
				
				tmpObj[9]=obj[11];
				tmpObj[10]=obj[12];
				tmpObj[11]=obj[13];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
	}
	
	/**
	 * @Description: 得到调库列表 110501
	 * @param @param begin
	 * @param @param request  whseCode 仓库编码 dateStart开始时间 dateEnd结束时见   infoSign预警状态
	 * @param @return 仓库名称、工具名称、标签号、存放位置（架层位）、移库前仓库、移库事由、移库类型、移库人、移库时间
	 * @return Object[]
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static  Object[]  getToolTransfer(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        
        String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
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
		
		String toolCode = request.getParameter("toolCode");
		if(!StringUtil.isEmpty(toolCode)){
			where.append(" and t1.TOOL_CODE = '"+toolCode+"'");
		}
		
		String whseCode = request.getParameter("whseCode");
		if(!StringUtil.isEmpty(whseCode)){
			where.append(" and t.WHSE_CODE = '"+whseCode+"'");
		}
		
		String dateStartR = request.getParameter("dateStart");
		if(!StringUtil.isEmpty(dateStartR)){
			where.append(" and t1.ADD_DATE>= to_date('"+dateStartR.replace("T", " ")+"','yyyy-mm-dd hh24:mi:ss')");
		}
		
		String dateEndR = request.getParameter("dateEnd");
		if(!StringUtil.isEmpty(dateEndR)){
			Date endTime = DateUtil.add(DateUtil.strToDate(dateEndR, DateUtil.PATTERN_YMD), 1);
			String endTimeStr = DateUtil.dateToStr(endTime, DateUtil.PATTERN_YMD);
			where.append(" and t1.ADD_DATE<= to_date('"+endTimeStr.replace("T", " ")+"','yyyy-mm-dd hh24:mi:ss')");
		}


		String infoSign = request.getParameter("infoSign");
		if(!StringUtil.isEmpty(infoSign)&&!"-1".equals(infoSign)){
			where.append(" and t1.INFO_SIGN='"+infoSign+"'");
		}
		
		String whereFinal="from RAIL_TOOL_TRANSFER t1 left join RAIL_TOOL_INFO t on t1.TOOL_CODE=t.TOOL_CODE "
				+ "left join TBL_OPR_INFO t2 on t1.ADD_USER=t2.OPR_ID "
				+ "left join RAIL_TOOL_NAME t3 on t.TOOL_NAME=t3.id "
				+ "left join RAIL_TOOL_TYPE t4 ON t3.TOOL_TYPE=t4.id "
				+ "left join RAIL_TOOL_UNIT t5 on t.TOOL_UNIT=t5.id "
				+ "left join RAIL_WHSE_INFO t6 on t1.WHSE_AFTER=t6.WHSE_CODE "
				+ "left join RAIL_WHSE_INFO t7 on t7.WHSE_CODE=t1.WHSE_BEFORE "
				+ "where 1=1 "+ where ;
		//仓库名称、工具名称、标签号、3存放位置（架层位）、移库前仓库、5移库事由、6移库类型、移库人、移库时间
		String sql ="select t6.WHSE_NAME,t3.TOOL_NAME,t.RFID,t.STAND,t.FLOOR,t.POSITION"
				+ ",t7.WHSE_NAME as whseBefore,t1.TRANSFER_MSG,t1.INFO_SIGN,t2.OPR_NAME,  TO_CHAR(t1.ADD_DATE,'yyyy-mm-dd hh24:mi:ss') as ADD_DATE,t1.id "
				+ whereFinal + " order by t1.ADD_DATE desc ";
		List<Object[]> dataList ;
		if(begin<0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
        Map<String,Object> PARAMS = PubCstSysParam.getSysParamMap(ContantValue.TRANSFER_TYPE);
        
        //用于返回的列表
		//仓库名称、工具名称、标签号、3存放位置（架层位）、移库前仓库、5移库事由、6移库类型、移库人、移库时间
        List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[10];
			for (Object[] obj : dataList) {
				tmpObj= new Object[10];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				if (obj[3] != null && !obj[3].equals("")) {
					tmpObj[3]=obj[3]+"架-"+obj[4]+"层-"+obj[5]+"位";
				}
				tmpObj[4]=obj[6];
				tmpObj[5]=obj[7];
				if (obj[8] != null && !obj[8].equals("")) {
					if(PARAMS.get(String.valueOf(obj[8]))!=null){
						tmpObj[6]=PARAMS.get(String.valueOf(obj[8]));
					}else{
						tmpObj[6] = "未知";
					}
				}
				tmpObj[7]=obj[9];
				tmpObj[8]=obj[10];
				tmpObj[9]=obj[11];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
	}
	
	/**
	 * @Description: 得到工具维修列表 110402/110403
	 * @param @param begin
	 * @param @param request  whseCode 仓库编码 dateStart维修申请开始时间 dateEnd维修申请结束时间   infoSign审核状态
	 * @param @return 仓库名称、工具名称、标签号、存放位置（架层位）、维修信息、申请人、申请时间、审核状态、审核人、审核时间、审核信息
	 * @return Object[]
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static  Object[]  getToolRepair(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        
        String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
		where.append("and t.DEPT in (");
		if(null!=list_depts && list_depts.size()>0){
			@SuppressWarnings("unused")
			String deptCode = list_depts.get(0);
			ApiAppBO ApiAppBO =  (ApiAppBO) ContextUtil.getBean("ApiAppBO");
			list_depts = ApiAppBO.getDeptList(list_depts,orgBrhId);
			for(String id :list_depts){
				where.append("'"+id+"',");
			}
		}
		//查询当前部门下的所有下属部门
		where.append("'-1')");
		
		String toolCode = request.getParameter("toolCode");
		if(!StringUtil.isEmpty(toolCode)){
			where.append(" and t1.TOOL_CODE='"+toolCode+"'");
		}
		
		String whseCode = request.getParameter("whseCode");
		if(!StringUtil.isEmpty(whseCode)){
			where.append(" and t.WHSE_CODE='"+whseCode+"'");
		}
		
		String dateStartR = request.getParameter("dateStart");
		if(!StringUtil.isEmpty(dateStartR)){
			//Date dateStart;
			where.append(" and t1.APPLY_DATE>= to_date('"+dateStartR.replace("T", " ")+"','yyyy-mm-dd hh24:mi:ss')");
		}
		String dateEndR = request.getParameter("dateEnd");
		if(!StringUtil.isEmpty(dateEndR)){
			Date endTime = DateUtil.add(DateUtil.strToDate(dateEndR, DateUtil.PATTERN_YMD), 1);
			String endTimeStr = DateUtil.dateToStr(endTime, DateUtil.PATTERN_YMD);
			where.append(" and t1.APPLY_DATE<= to_date('"+endTimeStr.replace("T", " ")+"','yyyy-mm-dd hh24:mi:ss')");
		}

		String infoSign = request.getParameter("infoSign");
		if(!StringUtil.isEmpty(infoSign)&&!"-1".equals(infoSign)){
			where.append(" and t1.INFO_SIGN = '"+infoSign+"'");
		}
		
		String whereFinal="from RAIL_TOOL_REPAIR t1 left join RAIL_TOOL_INFO t on t1.TOOL_CODE=t.TOOL_CODE "
				+ "left join TBL_OPR_INFO t2 on t1.VERIFY_USER=t2.OPR_ID "
				+ "left join RAIL_TOOL_NAME t3 on t.TOOL_NAME=t3.id "
				+ "left join RAIL_TOOL_TYPE t4 ON t3.TOOL_TYPE=t4.id "
				+ "left join RAIL_TOOL_UNIT t5 on t.TOOL_UNIT=t5.id "
				+ "left join RAIL_WHSE_INFO t6 on t.WHSE_CODE=t6.WHSE_CODE "
				+ "where  1=1  "+ where ;
		//仓库名称、工具名称、标签号、3存放位置（架层位）、维修信息、申请人、申请时间、7审核状态、审核人、审核时间、审核信息
		String sql = "select t6.WHSE_NAME,t3.TOOL_NAME,t.RFID,t.STAND,t.FLOOR,t.POSITION "
				+ ",t1.APPLY_MSG,t1.APPLY_USER, TO_CHAR(t1.APPLY_DATE,'yyyy-mm-dd hh24:mi:ss') as APPLY_DATE,t1.VERIFY_MSG,t1.INFO_SIGN,t2.OPR_NAME, TO_CHAR(t1.VERIFY_DATE,'yyyy-mm-dd hh24:mi:ss') as VERIFY_DATE,t1.id "
				+ whereFinal +" order by  t1.APPLY_DATE desc ";
		List<Object[]> dataList ;
		if(begin<0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
        //用于返回的列表
       //仓库名称、工具名称、标签号、3存放位置（架层位）、维修信息、申请人、申请时间、7审核状态、审核人、审核时间、审核信息
        List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[12];
			for (Object[] obj : dataList) {
				tmpObj= new Object[12];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				if (obj[3] != null && !obj[3].equals("")) {
					tmpObj[3]=obj[3]+"架-"+obj[4]+"层-"+obj[5]+"位";
				}
				tmpObj[4]=obj[6];
				tmpObj[5]=obj[7];
				tmpObj[6]=obj[8];
				tmpObj[7]=obj[9];
				if (obj[10] != null && !obj[10].equals("")) {
					if (obj[10].equals("0")) {
						tmpObj[8] = "未审核";
					} else if (obj[10].equals("1")) {
						tmpObj[8] = "审核通过";
					}  else if (obj[10].equals("2")) {
						tmpObj[8] = "审核不通过";
					} else if (obj[10].equals("3")) {
						tmpObj[8] = "维修完成";
					}else {
						tmpObj[8] = "未知";
					}
				}
				tmpObj[9]=obj[11];
				tmpObj[10]=obj[12];
				
				tmpObj[11]=obj[13];
				
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
	}
	
	/**
	 * @Description: 工具出入库记录 110201/110301 菜单方法已经排除
	 * @param @param begin
	 * @param @param request  whseCode 仓库编码 dateStart入库开始时间 dateEnd入库结束时间   toolStatus工具状态
	 * @param @return 0工具编码、1标签号、2工具分类、3工具名称、4工具型号、
	 * 5材质、6入库时间、7工具状态、8、有效期、9、出库状态、10、最后一次检修时间
	 * @return Object[]
	 * @throws
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public static  Object[]  getToolInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        
        String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
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
		
		String toolCode = request.getParameter("toolCode");
		if(!StringUtil.isEmpty(toolCode)){
			where.append(" and t.TOOL_CODE='"+toolCode+"'");
		}
		
		String whseCode = request.getParameter("whseCode");
		if(!StringUtil.isEmpty(whseCode)){
			where.append(" and t.WHSE_CODE='"+whseCode+"'");
		}
		
		
		String dateStartR = request.getParameter("dateStart");
		if(!StringUtil.isEmpty(dateStartR)){
			//Date dateStart;
			where.append(" and t.ADD_DATE>= to_date('"+dateStartR.replace("T", " ")+"','yyyy-mm-dd hh24:mi:ss')");
		}
		
		String dateEndR = request.getParameter("dateEnd");
		if(!StringUtil.isEmpty(dateEndR)){
			Date endTime = DateUtil.add(DateUtil.strToDate(dateEndR, DateUtil.PATTERN_YMD), 1);
			String endTimeStr = DateUtil.dateToStr(endTime, DateUtil.PATTERN_YMD);
			where.append(" and t.ADD_DATE<= to_date('"+endTimeStr.replace("T", " ")+"','yyyy-mm-dd hh24:mi:ss')");
		}
		String toolStatus = request.getParameter("toolStatus");
		if(!StringUtil.isEmpty(toolStatus)){
			where.append(" and t.tool_Status='"+toolStatus+"'");
		}
		
		String whereFinal="from  RAIL_TOOL_INFO t   where  1=1 "+ where ;
		//0工具编码、1标签号、2工具分类、3工具名称、4工具型号、
		 //* 5材质、6入库时间、7工具状态、8、有效期、9、出库状态、10、最后一次检修时间
		String sql = " select t.TOOL_CODE,t.RFID,t.MODEL_CODE,t.ADD_DATE,t.TOOL_STATUS,t.TOOL_EXPIRATION,t.IN_WHSE,t.LAST_EXAM"
				+ " "
				+ whereFinal;
		List<Object[]> dataList ;
		if(begin<0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		//List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
//		PubSearch pubSearch= (PubSearch) ContextUtil.getBean("PubSearch");
//		Map<String, String> param =new HashMap<String, String>();
//		Map<String, RailToolProp> modelData= pubSearch.getToolModleInfo(param);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        
        Map<String,Object> PARAMS = PubCstSysParam.getSysParamMap(ContantValue.TOOL_STATUS);
        
        //用于返回的列表
      //0工具编码、1标签号、2工具分类、3工具名称、4工具型号、
		 //* 5材质、6入库时间、7工具状态、8、有效期、9、出库状态、10、最后一次检修时间
        List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[11];
			for (Object[] obj : dataList) {
				tmpObj= new Object[11];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				//获取工具分类，通过上面获取的map中进行获取
//				RailToolProp tmpProp=modelData.get(obj[2].toString());
//				tmpObj[2]=tmpProp.getToolTypeName();
//				tmpObj[3]=tmpProp.getToolName();
//				tmpObj[4]=tmpProp.getRailToolModel().getModelName();
//				tmpObj[5]=tmpProp.getRailToolModel().getToolMaterial();
				tmpObj[6]=obj[3];
				if (obj[4] != null && !obj[4].equals("")) {
					if(PARAMS.get(String.valueOf(obj[4]))!=null){
						tmpObj[7]=PARAMS.get(String.valueOf(obj[4]));
					}else{
						tmpObj[5] = "未知";
					}
				}
				tmpObj[8]=obj[5];
				if (obj[6] != null && !obj[6].equals("")) {
					if (obj[6].equals("0")) {
						tmpObj[9] = "在库";
					} else if (obj[6].equals("1")) {
						tmpObj[9] = "出库";
					} else {
						tmpObj[9] = "未知";
					}
				}
	            if (obj[7] != null && !obj[7].equals("")) {
	            	tmpObj[10]=obj[7];
	            }
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
	}	
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: 得到工具信息列表 110101/
	 * @param @param begin
	 * @param @param request  whseCode 仓库编码 dateStart入库开始时间 dateEnd入库结束时间   toolStatus工具状态
	 * @param @return 仓库名称、工具名称、标签号、工具类型、材质、单位、存放位置（架层位）、检修周期、最后检修时间、有效期、入库时间、工具状态、在库状态
	 * @return Object[]
	 * @throws
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public static  Object[]  getToolInfofix(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		
		String orgBrhId=operator.getOprBrhId();
		List<String> list_depts= new ArrayList<String>();
		list_depts.add(orgBrhId);
		//查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
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
		
		String toolCode = request.getParameter("toolCode");
		if(!StringUtil.isEmpty(toolCode)){
			where.append(" and t.TOOL_CODE='"+toolCode+"'");
		}
		String rfid = request.getParameter("rfid");
		if(!StringUtil.isEmpty(rfid)){
			where.append(" and t.rfid='"+rfid+"'");
		}
		
		String toolName = request.getParameter("toolName");
		if(!StringUtil.isEmpty(toolName)){
			if(begin<0)
				toolName= new String(toolName .getBytes("ISO-8859-1"), "UTF-8");

			where.append(" and t2.TOOL_NAME like'%"+toolName+"%'");
		}
		
		String whseCode = request.getParameter("whseCode");
		if(!StringUtil.isEmpty(whseCode)){
			where.append(" and t.WHSE_CODE='"+whseCode+"'");
		}
		
		
		String dateStartR = request.getParameter("dateStart");
		if(!StringUtil.isEmpty(dateStartR)){
			//Date dateStart;
			where.append(" and t.ADD_DATE>= to_date('"+dateStartR.replace("T", " ")+"','yyyy-mm-dd hh24:mi:ss')");
		}
		
		String dateEndR = request.getParameter("dateEnd");
		if(!StringUtil.isEmpty(dateEndR)){
			Date endTime = DateUtil.add(DateUtil.strToDate(dateEndR, DateUtil.PATTERN_YMD), 1);
			String endTimeStr = DateUtil.dateToStr(endTime, DateUtil.PATTERN_YMD);
			where.append(" and t.ADD_DATE<= to_date('"+endTimeStr.replace("T", " ")+"','yyyy-mm-dd hh24:mi:ss')");
		}
		String toolStatus = request.getParameter("toolStatus");
		if(!StringUtil.isEmpty(toolStatus)){
			where.append(" and t.tool_Status='"+toolStatus+"'");
		}
//		仓库名称、工具名称、标签号、工具类型、材质、单位、存放位置（架层位）、检修周期、最后检修时间、有效期、入库时间、工具状态、在库状态
		String whereFinal="from RAIL_TOOL_INFO t "
				+ "left join RAIL_WHSE_INFO t1 on t.WHSE_CODE = t1.WHSE_CODE "
				+ "left join RAIL_TOOL_NAME t2 on t.TOOL_NAME=t2.id "
				+ "left join RAIL_TOOL_TYPE t3 ON t2.TOOL_TYPE=t3.id "
				+ "left join RAIL_TOOL_UNIT t4 on t.TOOL_UNIT=t4.id "
				+ "where  1=1 and t.DEL_STATUS = '"+DelStatusEnums.NO.getCode()+"' "+ where ;
		//仓库名称、工具名称、标签号、工具类型、材质、单位、6存放位置（架层位）、检修周期、最后检修时间、有效期、入库时间、11工具状态、12在库状态
		//TO_CHAR(t.TOOL_EXPIRATION,'yyyy-mm-dd hh24:mi:ss')
		String sql ="select t1.WHSE_NAME,t2.TOOL_NAME,t.RFID,t3.TOOL_TYPE_NAME,t.TOOL_MATERIAL,t4.TOOL_UNIT_NAME,t.STAND,t.FLOOR,t.POSITION,"
				+ "t.EXAM_PERIOD,TO_CHAR(t.LAST_EXAM,'yyyy-mm-dd hh24:mi:ss') as LAST_EXAM, TO_CHAR(t.TOOL_EXPIRATION,'yyyy-mm-dd') as TOOL_EXPIRATION, "
				+ "TO_CHAR(t.ADD_DATE,'yyyy-mm-dd hh24:mi:ss') as ADD_DATE,t.TOOL_STATUS,t.in_whse,t.note2,"
				+ " (select aa.brh_name  from TBL_BRH_INFO aa where aa.brh_id=t.PURCHASE_DEPT)  as PURCHASE_DEPT,"
				+ "t.PURCHASE_USER,"
				+ " (select aa.MFRS_ORG_NAME  from RAIL_MFRS_ORG aa where aa.id=t.MFRS_ORG) as MFRS_ORG,t.id   "
				+ whereFinal+"  order by t.ADD_DATE desc";
		List<Object[]> dataList ;
		if(begin<0){
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList  =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//仓库名称、工具名称、标签号、工具类型、材质、单位、6存放位置（架层位）、检修周期、最后检修时间、有效期、入库时间、11工具状态、12在库状态
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		Map<String,Object> TOOL_STATUS = PubCstSysParam.getSysParamMap(ContantValue.TOOL_STATUS);
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[18];
			for (Object[] obj : dataList) {
				tmpObj= new Object[18];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				if (obj[6] != null && !obj[6].equals("")) {
					tmpObj[6]=obj[6]+"架-"+obj[7]+"层-"+obj[8]+"位";
				}
				tmpObj[7]=obj[9];
				tmpObj[8]=obj[10];
				tmpObj[9]=obj[11];
				tmpObj[10]=obj[12];
			
				if (obj[13] != null && !obj[13].equals("")) {
					if(TOOL_STATUS.get(String.valueOf(obj[13]))!=null){
						tmpObj[11] = TOOL_STATUS.get(String.valueOf(obj[13]));
					}else{
						tmpObj[11] = "未知";
					}
				}
//				if (obj[13] != null && !obj[13].equals("")) {
//					if (obj[13].equals("0")) {
//						tmpObj[11] = "正常";
//					} else if (obj[13].equals("1")) {
//						tmpObj[11] = "维护";
//					} else if (obj[13].equals("3")) {
//						tmpObj[11] = "报废";
//					} else if (obj[13].equals("4")) {
//						tmpObj[11] = "停用";
//					} else {
//						tmpObj[11] = "未知";
//					}
//				}
				if (obj[14] != null && !obj[14].equals("")) {
					if (obj[14].equals("1")) {
						tmpObj[12] = "在库";
					} else if (obj[14].equals("2")) {
						tmpObj[12] = "出库";
					} else {
						tmpObj[12] = "未知";
					}
				}
				tmpObj[13]=obj[15];
				tmpObj[14]=obj[16];
				tmpObj[15]=obj[17];
				tmpObj[16]=obj[18];
				tmpObj[17]=obj[19];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}	

	//==================================工具相关分页查询结束=====================================
	//=================================作业工单相关分页查询开始====================================
	/**
	 * @Description: 工单审核、维护、查询对应列表 130103/130104/130105
	 * @param @param begin
	 * @param @param request  workStatus工单状态 dateStart开始时间 dateEnd结束时间
	 * @param @return 0计划号、1调度号、2工单名称、3维修类型、4站/区段、5负责人、6开始时间、
   	 * 7结束时间、8工单状态、9审核状态、
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getWorkInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        
        String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
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
		

		String dateStartR = request.getParameter("dateStart");
		if(!StringUtil.isEmpty(dateStartR)){
			where.append(" and t.SKYLIGHT_START>= to_date('"+dateStartR.replace("T", " ")+"','yyyy-mm-dd hh24:mi:ss')");
		}
		
		String dateEndR = request.getParameter("dateEnd");
		if(!StringUtil.isEmpty(dateEndR)){
			Date endTime = DateUtil.add(DateUtil.strToDate(dateEndR, DateUtil.PATTERN_YMD), 1);
			String endTimeStr = DateUtil.dateToStr(endTime, DateUtil.PATTERN_YMD);
			where.append(" and t.SKYLIGHT_START<= to_date('"+endTimeStr.replace("T", " ")+"','yyyy-mm-dd hh24:mi:ss')");
		}
		String workStatus = request.getParameter("workStatus");
		if(!StringUtil.isEmpty(workStatus)){
			where.append(" and t.WORK_STATUS = '"+workStatus+"'");
		}
		where.append(" ORDER BY t.SKYLIGHT_START DESC");
		
		String whereFinal="from  RAIL_WORK_INFO t LEFT JOIN	TBL_OPR_INFO toi ON toi.OPR_ID = t.AUDIT_USER where  1=1 "+ where ;
        //0计划号、1调度号、2工单名称、3维修类型、4站/区段、5负责人、6开始时间、
   	 	//* 7结束时间、8工单状态、9入网时间，10出网时间，11审核状态、12审核意见，13审核时间，14审核人
		//workCode,dispatchCode,workName,maintenceType,station,workPic,skylightStart,skylightEnd,workStatus,auditStatus,auditMsg,auditDate,auditUser
		String sql = " select t.WORK_CODE,t.DISPATCH_CODE,t.WORK_NAME,t.MAINTENCE_TYPE,t.STATION,t.WORK_PIC,TO_CHAR(t.SKYLIGHT_START,'yyyy-mm-dd hh24:mi:ss') as SKYLIGHT_START"
				+ ",TO_CHAR(t.SKYLIGHT_END,'yyyy-mm-dd hh24:mi:ss') as SKYLIGHT_END,t.WORK_STATUS,TO_CHAR(t.WORK_START,'yyyy-mm-dd hh24:mi:ss') as WORK_START,TO_CHAR(t.WORK_END,'yyyy-mm-dd hh24:mi:ss') as WORK_END,t.AUDIT_STATUS,t.AUDIT_MSG,TO_CHAR(t.AUDIT_DATE,'yyyy-mm-dd hh24:mi:ss') as AUDIT_DATE,toi.OPR_NAME "
			
				+ whereFinal;
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        //用于返回的列表
        //0计划号、1调度号、2工单名称、3维修类型、4站/区段、5负责人、6开始时间、
   	 	//* 7结束时间、8工单状态、9入网时间，10出网时间，11审核状态、12审核意见，13审核时间，14审核人
        List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[15];
			for (Object[] obj : dataList) {
				tmpObj= new Object[15];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				if (obj[3] != null && !obj[3].equals("")) {
					//0施工、1维修
					if (obj[3].equals("0")) {
						tmpObj[3] = "施工";
					} else if (obj[3].equals("1")) {
						tmpObj[3] = "维修";
					} else {
						tmpObj[3] = "未知";
					}
				}
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				tmpObj[7]=obj[7];
				if (obj[8] != null && !obj[8].equals("")) {
					//0未开始、1进行中、2已结束、3预警
					if (obj[8].equals("0")) {
						tmpObj[8] = "未开始";
					} else if (obj[8].equals("1")) {
						tmpObj[8] = "进行中";
					} else if (obj[8].equals("2")) {
						tmpObj[8] = "已结束";
					} else if (obj[8].equals("3")) {
						tmpObj[8] = "预警";
					} else {
						tmpObj[8] = "未知";
					}
				}
				tmpObj[9]=obj[9];
				tmpObj[10]=obj[10];
				if (obj[11] != null && !obj[11].equals("")) {
					//0未审核;1审核通过；2审核不通过
					if (obj[11].equals("0")) {
						tmpObj[11] = "未审核";
					} else if (obj[11].equals("1")) {
						tmpObj[11] = "审核通过";
					} else if (obj[11].equals("2")) {
						tmpObj[11] = "未通过";
					}  else {
						tmpObj[11] = "未知";
					}
				}
				tmpObj[12]=obj[12];
				tmpObj[13]=obj[13];
				
				tmpObj[14]=obj[14];
				
				
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
	}
	/**
	 * @Description: 获得审核时展示的工单工具列表
	 * @param @param begin
	 * @param @param request  whseCode 仓库编码 dateStart入库开始时间 dateEnd入库结束时间   toolStatus工具状态
	 * @param @return 0工具编码、1标签号、2工具分类、3工具名称、4工具型号、
	 * 5材质、6入库时间、7工具状态、8、有效期、9、出库状态、10、最后一次检修时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getWorkToolInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }

        //0工具名称、1工具分类、2工具数量
        //toolName,toolTypeName,toolNum
        String whereFinal=" FROM RAIL_WORK_TOOL t1 "
        		+ "LEFT JOIN RAIL_TOOL_INFO t ON t1.TOOL_CODE = t.TOOL_CODE "
        		+ "LEFT JOIN RAIL_TOOL_NAME t2 ON t.TOOL_NAME = t2.ID "
        		+ "LEFT JOIN RAIL_TOOL_TYPE t3 ON t2.TOOL_TYPE = t3.ID "
        		+ " WHERE 1=1 AND t1.WORK_CODE='"+request.getParameter("workCode")+"'";
        String sql="SELECT t2.TOOL_NAME,t3.TOOL_TYPE_NAME,t.NOTE2,t.IN_WHSE "
        		+ whereFinal;
        
		List<Object[]> dataList ;
		if(begin<0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
        //用于返回的列表
		//0工具名称、1工具分类、2工具数量
		//t2.TOOL_NAME,t3.TOOL_TYPE_NAME,t.NOTE2,t.IN_WHSE
        List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[4];
			for (Object[] obj : dataList) {
				tmpObj= new Object[4];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				if (obj[3] != null && !obj[3].equals("")) {
					//1在库；2出库
					if (obj[3].equals("1")) {
						tmpObj[3] = "在库";
					} else if (obj[3].equals("2")) {
						tmpObj[3] = "出库";
					} else {
						tmpObj[3] = "未知";
					}
				}else{
					tmpObj[3] = "未知";
				}
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
	}
	/**
	 * 根据工单获得已选择的工单工具
	 * @Description: TODO
	 * @param @param begin
	 * @param @param request
	 * @param @return   
	 * @return Object[]  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月3日
	 */
	public static Object[] getWorkTool(int begin,HttpServletRequest request){
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		String whereFinal=" FROM RAIL_WORK_TOOL t,RAIL_TOOL_INFO t1,RAIL_TOOL_NAME t2 WHERE 1=1 AND t.TOOL_CODE = t1.TOOL_CODE AND t2.ID = t1.TOOL_NAME AND t.WORK_CODE = '"+request.getParameter("workCode")+"'" ;
		//SELECT t.TOOL_NAME,COUNT(t.ID) as toolNum FROM RAIL_TOOL_NAME t,RAIL_WORK_TOOL t1 WHERE 1=1 AND t.ID = t1.TOOL_NAME AND t1.WORK_CODE = '0001' GROUP BY t.ID,t.TOOL_NAME
		String sql = " SELECT t1.TOOL_CODE,t2.TOOL_NAME,t1.NOTE2   "
				+ whereFinal;
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, 100);

		
//		Object[] workEmployee= getWorkEmployee(begin,request);
//		Map<String , String> seledEmMap= new HashMap<String, String>();
//		if (workEmployee!=null) {
//			List<Object[]> dataListTmp =(List<Object[]>) workEmployee[0];
//			if (dataListTmp!=null && dataListTmp.size()>0) {
//				for (int i = 0; i < dataListTmp.size(); i++) {
//					seledEmMap.put(dataListTmp.get(i)[0].toString(), dataListTmp.get(i)[1].toString());
//				}
//			}
//		}
		//用于返回的列表
		//0员工号、1员工姓名、2性别、3生日、4身份证号、5职位、6人员类型、7是否在网、8联系电话
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[3];
			for (Object[] obj : dataList) {
				tmpObj= new Object[3];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
//				if (seledEmMap.get(tmpObj[0].toString())==null) {
					dataListRet.add(tmpObj);
//				}
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	/**
	 * 根据工单获得还在仓库中的工具列表
	 * @Description: TODO
	 * @param @param begin
	 * @param @param request
	 * @param @return   
	 * @return Object[]  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月3日
	 */
	public static Object[] getWhseTool(int begin,HttpServletRequest request){
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		if (request.getParameter("whseCode")==null) {
			return ret;
		}
		String whseCode=request.getParameter("whseCode").toString().trim();
		TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
		List<String> whseCodeList=tSendMsgSocketBO.getWhseCodeThreeLevel(whseCode);
		String whseSql="(";
		for (int i = 0; i < whseCodeList.size(); i++) {
			whseSql=whseSql+"'"+whseCodeList.get(i).toString()+"',";
		}
		whseSql=whseSql+"'"+"-1"+"')";
		
		String whereFinal=" FROM RAIL_TOOL_INFO t1, RAIL_TOOL_NAME t2 WHERE 1=1 AND t1.TOOL_NAME = t2.ID AND t1.IN_WHSE = 1 AND t1.TOOL_STATUS =0 AND t1.DEL_STATUS = 0 AND t1.TOOL_CODE NOT IN ("
				+"SELECT t4.TOOL_CODE FROM RAIL_WORK_INFO t3, RAIL_WORK_TOOL t4 WHERE 1 = 1 AND t3.AUDIT_STATUS  <2 AND t3.WORK_STATUS < 2 AND t4.WORK_CODE = t3.WORK_CODE) "
				+ "AND t1.WHSE_CODE in "+whseSql+"" ;
		String sql = " SELECT t1.TOOL_CODE, t2.TOOL_NAME, t1.NOTE2   "
				+ whereFinal;
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);//findBySQLQuery(sql, begin, 100);
		
		
		Object[] workTool= getWorkTool(begin,request);
		Map<String , String> seledEmMap= new HashMap<String, String>();
		if (workTool!=null) {
			List<Object[]> dataListTmp =(List<Object[]>) workTool[0];
			if (dataListTmp!=null && dataListTmp.size()>0) {
				for (int i = 0; i < dataListTmp.size(); i++) {
					seledEmMap.put(dataListTmp.get(i)[0].toString(), dataListTmp.get(i)[1].toString());
				}
			}
		}
		//用于返回的列表
		//0员工号、1员工姓名、2性别、3生日、4身份证号、5职位、6人员类型、7是否在网、8联系电话
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[3];
			for (Object[] obj : dataList) {
				tmpObj= new Object[3];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
//				if (seledEmMap.get(tmpObj[0].toString())==null) {
				dataListRet.add(tmpObj);
//				}
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	/**
	 * @Description: 获得审核时查看人员列表
	 * @param @param begin
	 * @param @param request  workCode工单编码
	 * @param @return 0计划号、1调度号、2工单名称、3小组名称、4姓名、5是否开门权限、6人员状态、7添加时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getTeamEmployee(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		
		String whereFinal="FROM RAIL_TEAM_EMPLOYEE rte LEFT JOIN RAIL_EMPLOYEE re ON rte.EMPLOYEE_CODE = re.EMPLOYEE_CODE WHERE 1=1 AND rte.WORK_TEAM = '"+request.getParameter("teamId")+"' AND re.INFO_SIGN != 1" ;
		
		String sql = " SELECT re.EMPLOYEE_CODE,re.EMPLOYEE_NAME,re.JOB "
				+ whereFinal;
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, 100);
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		Object[] workEmployee= getWorkEmployee(begin,request);
		Map<String , String> seledEmMap= new HashMap<String, String>();
		if (workEmployee!=null) {
			List<Object[]> dataListTmp =(List<Object[]>) workEmployee[0];
			if (dataListTmp!=null && dataListTmp.size()>0) {
				for (int i = 0; i < dataListTmp.size(); i++) {
					seledEmMap.put(dataListTmp.get(i)[0].toString(), dataListTmp.get(i)[1].toString());
				}
			}
		}
		//用于返回的列表
		//0员工号、1员工姓名、2性别、3生日、4身份证号、5职位、6人员类型、7是否在网、8联系电话
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[3];
			for (Object[] obj : dataList) {
				tmpObj= new Object[3];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				if (seledEmMap.get(tmpObj[0].toString())==null) {
					dataListRet.add(tmpObj);
				}
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	
	
	
	/**
	 * @Description: 班组人员关联列表
	 * @param @param begin
	 * @param @param request  workCode工单编码
	 * @param @return 0计划号、1调度号、2工单名称、3小组名称、4姓名、5是否开门权限、6人员状态、7添加时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getTeamEmployee2(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		
		String whereFinal="FROM RAIL_TEAM_EMPLOYEE t,RAIL_EMPLOYEE t1 WHERE t.EMPLOYEE_CODE=t1.EMPLOYEE_CODE "
				+ " AND t1.NOTE1='0' AND t.WORK_TEAM='"+request.getParameter("teamId")+"' "
						+ " AND t.EMPLOYEE_CODE NOT IN(SELECT DISTINCT(t3.EMPLPOYEE_CODE)"
						+ " FROM RAIL_WORK_INFO t2,RAIL_WORK_EMPLOYEE t3 WHERE 1= 1"
						+ " AND t2.AUDIT_STATUS <2 AND t2.WORK_STATUS  < 2 AND t2.WORK_CODE=t3.WORK_CODE)" ;
		
		String sql = " SELECT t.EMPLOYEE_CODE,t1.EMPLOYEE_NAME,t1.JOB  "
				+ whereFinal;
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, 100);
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		//用于返回的列表
		//0员工号、1员工姓名、2性别、3生日、4身份证号、5职位、6人员类型、7是否在网、8联系电话
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[3];
			for (Object[] obj : dataList) {
				tmpObj= new Object[3];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	
	
	/**
	 * @Description: 获得审核时查看不在班组的人员列表
	 * @param @param begin
	 * @param @param request  workCode工单编码
	 * @param @return 0计划号、1调度号、2工单名称、3小组名称、4姓名、5是否开门权限、6人员状态、7添加时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getnullTeamEmployee(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		
		//String whereFinal="FROM RAIL_TEAM_EMPLOYEE rte LEFT JOIN RAIL_EMPLOYEE re ON rte.EMPLOYEE_CODE = re.EMPLOYEE_CODE WHERE 1=1 AND rte.WORK_TEAM != '"+request.getParameter("teamId")+"' AND re.INFO_SIGN != 1" ;
		String whereFinal=" FROM RAIL_EMPLOYEE rte  where rte.NOTE1=0 AND rte.EMPLOYEE_CODE not in (SELECT re.EMPLOYEE_CODE  FROM RAIL_TEAM_EMPLOYEE re WHERE 1=1 AND re.WORK_TEAM = '"+request.getParameter("teamId")+"')   AND rte.dept = '"+request.getParameter("dept")+"'  " ;
		String sql = " SELECT rte.EMPLOYEE_CODE,rte.EMPLOYEE_NAME,rte.JOB "
				+ whereFinal;
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, 100);
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		Object[] workEmployee= getWorkEmployee(begin,request);
		Map<String , String> seledEmMap= new HashMap<String, String>();
		if (workEmployee!=null) {
			List<Object[]> dataListTmp =(List<Object[]>) workEmployee[0];
			if (dataListTmp!=null && dataListTmp.size()>0) {
				for (int i = 0; i < dataListTmp.size(); i++) {
					seledEmMap.put(dataListTmp.get(i)[0].toString(), dataListTmp.get(i)[1].toString());
				}
			}
		}
		//用于返回的列表
		//0员工号、1员工姓名、2性别、3生日、4身份证号、5职位、6人员类型、7是否在网、8联系电话
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[3];
			for (Object[] obj : dataList) {
				tmpObj= new Object[3];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				if (seledEmMap.get(tmpObj[0].toString())==null) {
					dataListRet.add(tmpObj);
				}
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	
	
	/**
	 * @Description: 获得审核时查看人员列表
	 * @param @param begin
	 * @param @param request  workCode工单编码
	 * @param @return 0员工号、1员工姓名、2性别、3生日、4身份证号、5职位、6人员类型、7是否在网、8联系电话、9类型名称
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getWorkEmployee(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }

		String whereFinal="FROM RAIL_WORK_EMPLOYEE rwe LEFT JOIN RAIL_EMPLOYEE re ON rwe.EMPLPOYEE_CODE = re.EMPLOYEE_CODE LEFT JOIN RAIL_EMPLOYEE_TYPE ret ON ret.ID = re.EMPLOYEE_TYPE WHERE rwe.WORK_CODE='"+request.getParameter("workCode")+"'" ;
		
		String sql = " SELECT re.EMPLOYEE_CODE,re.EMPLOYEE_NAME,re.SEX,TO_CHAR(re.BIRTHDAY,'YYYY-MM-DD hh24:mi:ss') as BIRTHDAY,re.ID_NUMBER,re.JOB,re.EMPLOYEE_TYPE,re.INFO_SIGN,re.EMPLOYEE_TEL,ret.TYPE_NAME  "
				+ whereFinal;
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        //用于返回的列表
		//0员工号、1员工姓名、2性别、3生日、4身份证号、5职位、6人员类型、7是否在网、8联系电话
        List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[10];
			for (Object[] obj : dataList) {
				tmpObj= new Object[10];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				if (obj[7] != null && !obj[7].equals("")) {
					//0未入网，1已入网、2已出网
					if (obj[7].equals("0")) {
						tmpObj[7] = "未入网";
					} else if (obj[7].equals("1")) {
						tmpObj[7] = "已入网";
					} else if (obj[7].equals("2")) {
						tmpObj[7] = "已出网";
					} else {
						tmpObj[7] = "未知";
					}
				}
				tmpObj[8]=obj[8];
				tmpObj[9]=obj[9];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
	}
	/**
	 * @Description: 得到作业单元管理 工单人员关联 130101
	 * @param @param begin
	 * @param @param request  workCode工单编码
	 * @param @return 0计划号、1调度号、2工单名称、3工单状态，4小组名称、5姓名、6是否开门权限、7人员状态、8添加时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getWorkEmployees(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        
        String orgBrhId=operator.getOprBrhId();
        List<String> list_depts= new ArrayList<String>();
        list_depts.add(orgBrhId);
        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
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
		
		String workCode = request.getParameter("workCode");
		if(!StringUtil.isEmpty(workCode)){
			where.append(" and t.WORK_CODE='"+workCode+"'");
		}
		String dispatchCode = request.getParameter("dispatchCode");
		if(!StringUtil.isEmpty(dispatchCode)){
			where.append(" and t.DISPATCH_CODE like '%"+dispatchCode+"%'");
		}
		String workStatus = request.getParameter("workStatus");
		if(!StringUtil.isEmpty(workStatus)){
			where.append(" and t.WORK_STATUS='"+workStatus+"'");
		}
		where.append(" ORDER BY t1.ADD_DATE DESC ");
		String whereFinal="from  RAIL_WORK_EMPLOYEE t1 left join RAIL_WORK_INFO t on t1.WORK_CODE=t.WORK_CODE "
				+ " left join RAIL_EMPLOYEE t2 on t1.EMPLPOYEE_CODE=t2.EMPLOYEE_CODE "
				+ " left join RAIL_TEAM t3 on t.WORK_TEAM=t3.ID where  1=1 "+ where ;
		//0计划号、1调度号、2工单名称、3工单状态，4小组名称、5姓名、6是否开门权限、7人员状态、8添加时间
		String sql = " select t.WORK_CODE,t.DISPATCH_CODE,t.WORK_NAME,t.WORK_STATUS,t3.WORK_TEAM_NAME,t2.EMPLOYEE_CODE,t2.EMPLOYEE_NAME,t1.OPEN_SIGN,t1.INFO_SIGN,TO_CHAR(t1.ADD_DATE,'YYYY-MM-DD hh24:mi:ss') as ADD_DATE "
				+ whereFinal;
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        //用于返回的列表
		//0计划号、1调度号、2工单名称、3工单状态，4小组名称、5姓名、6是否开门权限、7人员状态、8添加时间
        List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[10];
			for (Object[] obj : dataList) {
				tmpObj= new Object[10];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				if (obj[3] != null && !obj[3].equals("")) {
					//0未开始、1进行中、2已结束、3预警
					if (obj[3].equals("0")) {
						tmpObj[3] = "未开始";
					} else if (obj[3].equals("1")) {
						tmpObj[3] = "进行中";
					} else if (obj[3].equals("2")) {
						tmpObj[3] = "已结束";
					} else if (obj[3].equals("3")) {
						tmpObj[3] = "预警";
					} else {
						tmpObj[3] = "未知";
					}
				}
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				if (obj[7] != null && !obj[7].equals("")) {
					//0能开门；1不能开门
					if (obj[7].equals("0")) {
						tmpObj[7] = "有权限";
					} else if (obj[6].equals("1")) {
						tmpObj[7] = "无权限";
					} else {
						tmpObj[7] = "未知";
					}
				}
				if (obj[8] != null && !obj[8].equals("")) {
					//0未入网，1已入网、2已出网
					if (obj[8].equals("0")) {
						tmpObj[8] = "未入网";
					} else if (obj[8].equals("1")) {
						tmpObj[8] = "已入网";
					} else if (obj[8].equals("2")) {
						tmpObj[8] = "已出网";
					} else {
						tmpObj[8] = "未知";
					}
				}
				tmpObj[9]=obj[9];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
	}
	
	/**
	 * @throws ParseException 
	 * @Description: 得到作业单元管理 进出高铁人员清单
	 * @param @param begin
	 * @param @param request  workCode工单编码
	 * @param @return 姓名、性别、年龄、当前状态、点名人数、入网人数、出网人数
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getWorkEmployeesSeeList(int begin, HttpServletRequest request) throws ParseException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        
		StringBuffer where = new StringBuffer();
		where.append(" WHERE t.EMPLPOYEE_CODE=t1.EMPLOYEE_CODE ");
		String workCode = request.getParameter("workCode");
		if(!StringUtil.isEmpty(workCode)){
			where.append(" and t.WORK_CODE='"+workCode+"'");
		}
		
		String whereFinal=" FROM RAIL_WORK_EMPLOYEE t,RAIL_EMPLOYEE t1 "+ where ;
		//ArrayList
		String sql = " SELECT t1.EMPLOYEE_NAME,t1.SEX,Trunc(MONTHS_BETWEEN(SYSDATE,t1.BIRTHDAY)/12) AS age,t.INFO_SIGN,t.INFO_SIGN1,t.INFO_SIGN2,t.INFO_SIGN3 "
				+ whereFinal;
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        //用于返回的列表
		//姓名、性别、年龄、当前状态、点名人数、入网人数、出网人数
        List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[7];
			for (Object[] obj : dataList) {
				tmpObj= new Object[7];
				tmpObj[0]=obj[0];
                if (obj[1] != null && obj[1] != "") {
                	if (obj[1].equals("1")) {
						tmpObj[1] = "男";
					} else if (obj[1].equals("2")) {
						tmpObj[1] = "女";
					}
                }
                tmpObj[2]=obj[2];
                if (obj[3] != null && !obj[3].equals("")) {
                	//0未入网，1已入网、2已出网
                	if (obj[3].equals("0")) {
                		tmpObj[3] = "未入网";
                	} else if (obj[3].equals("1")) {
                		tmpObj[3] = "已入网";
                	} else if (obj[3].equals("2")) {
                		tmpObj[3] = "已出网";
                	} else {
                		tmpObj[3] = "未知";
                	}
                }
//                System.out.println(obj[5]+"  >>>>>>>>>>>>>>>>>>");
                if(obj[4]==null){
                	tmpObj[4]=0;
                }else{
                	tmpObj[4]=obj[4];
                }
                if(obj[5]==null){
                	tmpObj[5]=0;
                }else{
                	tmpObj[5]=obj[5];
                }
                if(obj[6]==null){
                	tmpObj[6]=0;
                }else{
                	tmpObj[6]=obj[6];
                }
//                tmpObj[5]=obj[5];
//                tmpObj[6]=obj[6];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
	}
	
	/**
	 * @Description: 得到作业工具管理 工单工具关联 130102
	 * @param @param begin
	 * @param @param request  workCode工单编码
	 * @param @return 0计划号、1调度号、2工单名称、3工单状态、4工具类型、5工具名称、6工具状态、7出入库状态、8添加时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getWorkTools(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		
		String orgBrhId=operator.getOprBrhId();
		List<String> list_depts= new ArrayList<String>();
		list_depts.add(orgBrhId);
		//查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
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
		
		String workCode = request.getParameter("workCode");
		if(!StringUtil.isEmpty(workCode)){
			where.append(" and t.WORK_CODE='"+workCode+"'");
		}
		String dispatchCode = request.getParameter("dispatchCode");
		if(!StringUtil.isEmpty(dispatchCode)){
			where.append(" and t.DISPATCH_CODE like '%"+dispatchCode+"%'");
		}
		where.append(" ORDER BY t1.ADD_DATE DESC");
		String whereFinal="from RAIL_WORK_TOOL t1 left join RAIL_WORK_INFO t on t1.WORK_CODE=t.WORK_CODE "
				+ "left join RAIL_TOOL_INFO t2 on t1.TOOL_CODE=t2.TOOL_CODE "
				+ "left join RAIL_TOOL_NAME t3 on t2.TOOL_NAME=t3.id "
				+ "left join RAIL_TOOL_TYPE t4 ON t3.TOOL_TYPE=t4.id "
				+ "left join RAIL_TOOL_UNIT t5 on t2.TOOL_UNIT=t5.id "
				+ "where 1=1 "+ where ;
		//0计划号、1调度号、2工单名称、3工单状态、4工具类型、5工具名称、6工具型号、7工具状态、8是否在库，9出入库状态、10添加时间
		String sql = " select t.WORK_CODE,t.DISPATCH_CODE,t.WORK_NAME,t.WORK_STATUS,t4.TOOL_TYPE_NAME,t3.TOOL_NAME,t2.NOTE2,t2.TOOL_STATUS,t2.IN_WHSE,t1.INFO_SIGN,TO_CHAR(t1.ADD_DATE,'YYYY-MM-DD hh24:mi:ss') as ADD_DATE "
				+ whereFinal;
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0计划号、1调度号、2工单名称、3工单状态、4工具类型、5工具名称、6工具型号、7工具状态、8是否在库，9出入库状态、10添加时间
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[11];
			for (Object[] obj : dataList) {
				tmpObj= new Object[11];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				if (obj[3] != null && !obj[3].equals("")) {
					//0未开始、1进行中、2已结束、3预警
					if (obj[3].equals("0")) {
						tmpObj[3] = "未开始";
					} else if (obj[3].equals("1")) {
						tmpObj[3] = "进行中";
					} else if (obj[3].equals("2")) {
						tmpObj[3] = "已结束";
					} else if (obj[3].equals("3")) {
						tmpObj[3] = "预警";
					} else {
						tmpObj[3] = "未知";
					}
				}
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				if (obj[7] != null && !obj[7].equals("")) {
					//0正常；1维护；3报废；4停用
					if (obj[7].equals("0")) {
						tmpObj[7] = "正常";
					} else if (obj[7].equals("1")) {
						tmpObj[7] = "维护";
					} else if (obj[7].equals("3")) {
						tmpObj[7] = "报废";
					} else if (obj[7].equals("4")) {
						tmpObj[7] = "停用";
					} else {
						tmpObj[7] = "未知";
					}
				}else{
					tmpObj[7] = "未知";
				}
				if (obj[8] != null && !obj[8].equals("")) {
					//1在库；2出库
					if (obj[8].equals("1")) {
						tmpObj[8] = "在库";
					} else if (obj[8].equals("2")) {
						tmpObj[8] = "出库";
					} else {
						tmpObj[8] = "未知";
					}
				}else{
					tmpObj[8] = "未知";
				}
				if (obj[9] != null && !obj[9].equals("")) {
					//0未出库、1已出库、2已入网、3已出网、4已入库
					if (obj[9].equals("0")) {
						tmpObj[9] = "未出库";
					} else if (obj[9].equals("1")) {
						tmpObj[9] = "已出库";
					} else if (obj[9].equals("2")) {
						tmpObj[9] = "已入网";
					} else if (obj[9].equals("3")) {
						tmpObj[9] = "已出网";
					} else if (obj[9].equals("4")) {
						tmpObj[9] = "已入库";
					} else {
						tmpObj[9] = "未知";
					}
				}else{
					tmpObj[9] = "未出库";
				}
				
				tmpObj[10]=obj[10];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * @Description: 得到作业工具管理 工单工具关联 机物料一览
	 * @param @param begin
	 * @param @param request  workCode工单编码
	 * @param @return 工具名称、工具单位、当前状态、出库房数量、入网数量、出网数量、入库数量
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getWorkToolsSeeList(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		
		StringBuffer where = new StringBuffer();
		
		String workCode = request.getParameter("workCode");
		if(!StringUtil.isEmpty(workCode)){
			where.append(" and t.WORK_CODE='"+workCode+"'");
		}
		
		String whereFinal=" FROM RAIL_WORK_TOOL t LEFT JOIN RAIL_TOOL_INFO t2 ON t.TOOL_CODE=t2.TOOL_CODE "
				+ "LEFT JOIN RAIL_TOOL_NAME t3 ON t2.TOOL_NAME=t3.ID "
				+ "LEFT JOIN RAIL_TOOL_UNIT t4 ON t2.TOOL_UNIT=t4.ID "
				+ "where 1=1 "+ where ;
		//工具名称、工具单位、当前状态、出库房数量、入网数量、出网数量、入库数量
		String sql = " SELECT t2.NOTE2,t3.TOOL_NAME,t4.TOOL_UNIT_NAME,t.INFO_SIGN,t.INFO_SIGN1,t.INFO_SIGN2,t.INFO_SIGN3,t.INFO_SIGN4 "
				+ whereFinal;
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//工具名称、工具单位、当前状态、出库房数量、入网数量、出网数量、入库数量
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[8];
			for (Object[] obj : dataList) {
				tmpObj= new Object[8];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				if(obj[2]==null){
					tmpObj[2]="未知";
				}else{
					tmpObj[2]=obj[2];
				}
				
				if (obj[3] != null && !obj[3].equals("")) {
					//0未出库、1已出库、2已入网、3已出网、4已入库
					if (obj[3].equals("0")) {
						tmpObj[3] = "未出库";
					} else if (obj[3].equals("1")) {
						tmpObj[3] = "已出库";
					} else if (obj[3].equals("2")) {
						tmpObj[3] = "已入网";
					} else if (obj[3].equals("3")) {
						tmpObj[3] = "已出网";
					} else if (obj[3].equals("4")) {
						tmpObj[3] = "已入库";
					} else {
						tmpObj[3] = "未知";
					}
				}else{
					tmpObj[3] = "未出库";
				}
				if(obj[4]==null){
					tmpObj[4]=0;
				}else{
					tmpObj[4]=obj[4];
				}
				if(obj[5]==null){
					tmpObj[5]=0;
				}else{
					tmpObj[5]=obj[4];
				}
				if(obj[6]==null){
					tmpObj[6]=0;
				}else{
					tmpObj[6]=obj[6];
				}
				if(obj[7]==null){
					tmpObj[7]=0;
				}else{
					tmpObj[7]=obj[7];
				}
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	
	
	/**
	 * @Description: 作业影像管理 图片/视频 130200/130201
	 * @param @param begin
	 * @param @param request  workCode工单编码、imgType影像类型(到时候按照菜单默认传递过来就行了)
	 * @param @return 0计划号、1调度号、2工单名称、3影像名称、4上传时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getWorkImgs(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		
		String orgBrhId=operator.getOprBrhId();
		List<String> list_depts= new ArrayList<String>();
		list_depts.add(orgBrhId);
		//查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
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
		
		String workCode = request.getParameter("workCode");
		if(!StringUtil.isEmpty(workCode)){
			where.append(" and t.WORK_CODE='"+workCode+"'");
		}
		String imgType = request.getParameter("imgType");
		if(!StringUtil.isEmpty(imgType)){
			where.append(" and t1.IMG_TYPE='"+imgType+"'");
		}
		
		String whereFinal=" from  RAIL_WORK_IMG t1 left join RAIL_WORK_INFO t on t1.WORK_CODE=t.WORK_CODE LEFT JOIN	TBL_OPR_INFO toi ON toi.OPR_ID = t1.add_user where  1=1 "+ where+" order by t1.ADD_DATE desc" ;
		//0计划号、1调度号、2工单名称、3影像名称、4上传时间
		String sql = " select  t.WORK_CODE,t.WORK_NAME,t1.IMG_TYPE,t1.note1,toi.OPR_NAME ,TO_CHAR(t1.ADD_DATE,'YYYY-MM-DD hh24:mi:ss') as ADD_DATE,t1.id,t1.IMG_PATH,t.DISPATCH_CODE  "
				+ whereFinal;
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0计划号、1调度号、2工单名称、3影像名称、4上传时间
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[8];
			for (Object[] obj : dataList) {
				tmpObj= new Object[8];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				if (obj[2] != null && !obj[2].equals("")) {
					//1男；2女
					if (obj[2].equals("0")) {
						tmpObj[2] = "图片";
					} else if (obj[2].equals("1")) {
						tmpObj[2] = "视频";
					} else {
						tmpObj[2] = "未知";
					}
				}
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				tmpObj[7]=obj[7];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	
	
	/**
	 * @Description: 进出门信息查询 130300/130301
	 * @param @param begin
	 * @param @param request  workCode工单编码、accessCode通道门编码、infoSign进门/出门(通过菜单传递过来)
	 * @param @return 0作业门编码、1作业门名称、2进门时间、3、计划号、4调度号、5工单名称、6、负责人、7、负责人电话、8当前状态
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getAccessEmployees(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		
		String orgBrhId=operator.getOprBrhId();
		List<String> list_depts= new ArrayList<String>();
		list_depts.add(orgBrhId);
		//查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
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
		
		String accessCode = request.getParameter("accessCode");
		if(!StringUtil.isEmpty(accessCode)){
			where.append(" and t1.ACCESS_CODE='"+accessCode+"'");
		}
		
		String  workCode = request.getParameter("workCode");
		if(!StringUtil.isEmpty(workCode)){
			where.append(" and t1.WORK_CODE='"+workCode+"'");
		} 
		
		String infoSign = request.getParameter("infoSign");
		if(!StringUtil.isEmpty(infoSign)){
			where.append(" and t1.INFO_SIGN = '"+infoSign+"'");
		}
		
		String whereFinal=" from RAIL_ACCESS_EMPLOYEE t1 left join RAIL_WORK_INFO t on t1.WORK_CODE=t.WORK_CODE "
				+ " left join RAIL_ACCESS_INFO t2 on t1.ACCESS_CODE=t2.ACCESS_CODE where 1=1 "+ where +" order by t1.ADD_DATE desc" ;
		//0作业门编码、1作业门名称、2进门或出门时间、3、计划号、4调度号、5工单名称、
		//6、负责人、7、负责人电话、8当前状态
		String sql = " select t2.ACCESS_CODE,t2.ACCESS_NAME,t1.ADD_DATE,t.WORK_CODE,t.DISPATCH_CODE,t.WORK_NAME,"
				+ "t.WORK_PIC,t.WORK_TEL, t2.OPEN_STATUS,t1.id"
				+ whereFinal;
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0作业门编码、1作业门名称、2进门时间、3、计划号、4调度号、5工单名称、6、负责人、7、负责人电话、8当前状态
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[9];
			for (Object[] obj : dataList) {
				tmpObj= new Object[10];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				tmpObj[7]=obj[7];
				tmpObj[8]=obj[8];
				if (obj[8] != null && !obj[8].equals("")) {
					//1男；2女
					if (obj[8].equals("0")) {
						tmpObj[8] = "启用";
					} else if (obj[8].equals("1")) {
						tmpObj[8] = "停用";
					} else {
						tmpObj[8] = "未知";
					}
				}
				tmpObj[9]=obj[9];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: 人员信息查询维护列表 130401/130402
	 * @param @param begin
	 * @param @param request  employeeType人员类型、idNumber身份证号
	 * @param @return 0人员编码、1姓名、2性别、3、年龄、4出生日期、5人员类型、6入职日期、7、身份证号
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getEmployees(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		
		String orgBrhId=operator.getOprBrhId();
		List<String> list_depts= new ArrayList<String>();
		list_depts.add(orgBrhId);
		//查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
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
		
		String employeeCode = request.getParameter("employeeCode");
		if(!StringUtil.isEmpty(employeeCode)){
			if(begin<0)
				employeeCode= new String(employeeCode .getBytes("ISO-8859-1"), "UTF-8");
			where.append(" and t.EMPLOYEE_code like '%"+employeeCode+"%'");
		}
		
		String employeeName = request.getParameter("employeeName");
		if(!StringUtil.isEmpty(employeeName)){
			if(begin<0)
				employeeName= new String(employeeName .getBytes("ISO-8859-1"), "UTF-8");
			where.append(" and t.EMPLOYEE_Name like '%"+employeeName+"%'");
		}
		
		String employeeType = request.getParameter("employeeType");
		if(!StringUtil.isEmpty(employeeType)){
			where.append(" and t.EMPLOYEE_TYPE='"+employeeType+"'");
		}
		
		
		String idNumber = request.getParameter("idNumber");
		if(!StringUtil.isEmpty(idNumber)){
			where.append(" and t.EMPLOYEE_TYPE = '"+idNumber+"'");
		}
		
		String whereFinal="from RAIL_EMPLOYEE t left join RAIL_EMPLOYEE_TYPE t1 on t1.ID=t.EMPLOYEE_TYPE  left join RAIL_CONST_ORG t2 on t2.ID=t.const_Org    left join TBL_BRH_INFO t3 on t3.BRH_ID=t.dept  "
				+ " where 1=1 AND t.NOTE1=0 "+ where+" order by t.ADD_DATE desc"  ;
		//0人员编码、1姓名、2性别、3、年龄、4身份证号、5照片地址、6作业单位、7、职务、8人员类型、9联系方式、10所属机构
		String sql = "select  t.employee_Code,t.employee_Name,t.sex,TRUNC(months_between(sysdate,t.BIRTHDAY)/12) AS age,t.id_Number,t.employee_Img,t2.COST_ORG_NAME,t.job,t1.TYPE_NAME,t.employee_Tel,t3.BRH_NAME,t.const_Org,"
					+ "t.employee_Type,t.password,t.entry_Date,t.dept ,t.birthday "
				+ whereFinal;
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0人员编码、1姓名、2性别、3、年龄、4身份证号、5照片地址、6作业单位、7、职务、8人员类型、9联系方式、10所属机构
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[17];
			for (Object[] obj : dataList) {
				tmpObj= new Object[17];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				if (obj[2] != null && !obj[2].equals("")) {
					//1男；2女
					if (obj[2].equals("1")) {
						tmpObj[2] = "男";
					} else if (obj[2].equals("2")) {
						tmpObj[2] = "女";
					} else {
						tmpObj[2] = "未知";
					}
				}
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				tmpObj[7]=obj[7];
				tmpObj[8]=obj[8];
				tmpObj[9]=obj[9];
				tmpObj[10]=obj[10];
				tmpObj[11]=obj[11];
				tmpObj[12]=obj[12];
				tmpObj[13]=obj[13];
				tmpObj[14]=obj[14];
				tmpObj[15]=obj[15];
				tmpObj[16]=obj[16];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: 班组管理维护列表 130403
	 * @param @param begin
	 * @param @param request
	 * @param @return 0班组名称、1人数、2添加日期
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getTeams(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		
		StringBuffer where = new StringBuffer();
		where.append(" where 1=1 and t.DEL_STATUS='0' ");
		String workTeamName = request.getParameter("workTeamName");
		if (isNotEmpty(workTeamName)) {
			if(begin<0)
				workTeamName=new String(workTeamName .getBytes("ISO-8859-1"), "UTF-8");
			where.append("and t.WORK_TEAM_NAME like '%"+workTeamName+"%'");
		}
		String dept = request.getParameter("dept");
		if(!StringUtil.isEmpty(dept)){
			where.append("and t.dept ='"+dept+"'");
		}
		//0班组名称，1班组状态，2班组人数，3创建人，4创建时间,5id   t.ADD_DATE,'yyyy-mm-dd hh24:mi:ss') as ADD_DATE
		String sql = " select t.WORK_TEAM_NAME,"
				+ "(select a.brh_name from TBL_BRH_INFO a where a.brh_id=t.dept) as deptName, "
				+ "(select count(*) from RAIL_TEAM_EMPLOYEE t1 where  t1.WORK_TEAM=t.ID) as count,t.enable_Status,"
				+ "toi.OPR_NAME, TO_CHAR(t.add_date,'yyyy-mm-dd hh24:mi:ss') as ADD_DATE ,t.id,t.dept from RAIL_TEAM t LEFT JOIN TBL_OPR_INFO toi ON toi.OPR_ID = t.add_user "+where+" order by t.ADD_DATE desc" ;
				
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0班组名称、1人数、2添加日期
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		Map<String,Object> ENABLE_STATUS = PubCstSysParam.getSysParamMap(ContantValue.TEAM_STATUS);
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[8];
			for (Object[] obj : dataList) {
				tmpObj= new Object[8];
				tmpObj[0]=obj[0];
				
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				if (obj[3] != null && !obj[3].equals("")) {
					if(ENABLE_STATUS.get(String.valueOf(obj[3]))!=null){
						tmpObj[3] = ENABLE_STATUS.get(String.valueOf(obj[3]));
					}else{
						tmpObj[3] = "未知";
					}
				}
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				tmpObj[7]=obj[7];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	

	/**
	 * @Description: 作业天窗预警 130500
	 * @param @param begin
	 * @param @param request  workCode工单编码、warnType预警类型
	 * @param @return 0计划号、1调度号、2工单名称、3开始时间、4结束时间、5预警类型、
	 * 6预警信息、7预警时间、8是否确认、9确认时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getRailWorkWarn(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        
//        String orgBrhId=operator.getOprBrhId();
//        List<String> list_depts= new ArrayList<String>();
//        list_depts.add(orgBrhId);
//        //查询当前部门下的所有下属部门
		StringBuffer where = new StringBuffer();
//		where.append("and t.DEPT in (");
//		if(null!=list_depts && list_depts.size()>0){
//			String deptCode = list_depts.get(0);
//			ApiAppBO ApiAppBO =  (ApiAppBO) ContextUtil.getBean("ApiAppBO");
//			list_depts = ApiAppBO.getDeptList(list_depts,orgBrhId);
//			for(String id :list_depts){
//				where.append("'"+id+"',");
//			}
//		}
//		//查询当前部门下的所有下属部门
//		where.append("'-1')");
		
		String workCode = request.getParameter("workCode");
		if(!StringUtil.isEmpty(workCode)){
			where.append(" and t1.WORK_CODE='"+workCode+"'");
		}
		String warnType = request.getParameter("warnType");
		if(!StringUtil.isEmpty(warnType)){
			where.append(" and t1.WARN_TYPE='"+warnType+"'");
		}
		
		String whereFinal="from RAIL_WORK_WARN t1 left join RAIL_WORK_INFO t on t1.WORK_CODE=t.WORK_CODE LEFT JOIN	TBL_OPR_INFO toi ON toi.OPR_ID = t1.verify_User "
				+ "where 1=1 "+ where +" order by t1.ADD_DATE desc";
		//0计划号、1调度号、2工单名称、3开始时间、4结束时间、5预警类型、 6预警信息、7预警时间、8是否确认、9确认时间
		
		//0工单编码。1工单名称、2预警类型、3预警内容、4是否确认、5预警时间、6操作备注、7操作员、8操作时间,9调度号，10工单开始时间，11工单结束时间,12标识
		String sql = " select t.WORK_CODE,t.WORK_NAME,t1.WARN_TYPE,t1.WARN_MSG,t1.INFO_SIGN,"
				+ " TO_CHAR(t1.ADD_DATE,'yyyy-mm-dd hh24:mi:ss') as ADD_DATE,t1.verify_Msg ,toi.OPR_NAME, "
				+ "TO_CHAR(t1.VERIFY_DATE,'yyyy-mm-dd hh24:mi:ss') as VERIFY_DATE,t.DISPATCH_CODE, t.WORK_START,t.WORK_END,t1.id "
				+ " "
				+ whereFinal;
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        //用于返回的列表
		//0计划号、1调度号、2工单名称、3开始时间、4结束时间、5预警类型、
//		 * 6预警信息、7预警时间、8是否确认、9确认时间
        List<Object[]> dataListRet=new ArrayList<Object[]>();
        Map<String,Object> ENABLE_STATUS = PubCstSysParam.getSysParamMap(ContantValue.VERIFY_WARN);
        Map<String,Object> WARN_TYPE = PubCstSysParam.getSysParamMap(ContantValue.WORK_WARNING_TYPE);
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[13];
			for (Object[] obj : dataList) {
				tmpObj= new Object[13];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				if (obj[2] != null && !obj[2].equals("")) {
					if(WARN_TYPE.get(String.valueOf(obj[2]))!=null){
						tmpObj[2] = WARN_TYPE.get(String.valueOf(obj[2]));
					}else{
						tmpObj[2] = "未知";
					}
				}
//				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				
				if (obj[4] != null && !obj[4].equals("")) {
					if(ENABLE_STATUS.get(String.valueOf(obj[4]))!=null){
						tmpObj[4] = ENABLE_STATUS.get(String.valueOf(obj[4]));
					}else{
						tmpObj[4] = "未知";
					}
				}
				
				//0工单编码。1工单名称、2预警类型、3预警内容、4是否确认、5预警时间、6操作备注、7操作员、8操作时间,
				//9调度号，10工单开始时间，11工单结束时间
				
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
//				if (obj[6] != null && !obj[6].equals("")) {
//					//1工单过期人未全出、2工单结束工具未全出
//					if (obj[6].equals("1")) {
//						tmpObj[6] = "人未全出";
//					} else if (obj[6].equals("2")) {
//						tmpObj[6] = "工具未全出";
//					} else {
//						tmpObj[6] = "未知";
//					}
//				}else{
//					tmpObj[6] = "未知";
//				}
				tmpObj[7]=obj[7];
				tmpObj[8]=obj[8];
				tmpObj[9]=obj[9];
				tmpObj[10]=obj[10];
				tmpObj[11]=obj[11];
				tmpObj[12]=obj[12];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
	}
	//=================================作业工单相关分页查询结束====================================
	//=================================系统管理分页查询开始====================================
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: 施工单位维护列表 140100
	 * @param @param begin
	 * @param @param request
	 * @param @return 0类型名称、1添加日期
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getConstOrg(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		System.out.println("costOrgName=="+request.getParameter("costOrgName"));
		String whereFinal="from RAIL_CONST_ORG t LEFT JOIN	TBL_OPR_INFO toi ON toi.OPR_ID = t.add_user LEFT JOIN TBL_OPR_INFO toi2 ON toi2.OPR_ID = t.upd_user Where t.DEL_STATUS='0'";
		String  costOrgName = request.getParameter("costOrgName");
		if (isNotEmpty(costOrgName)) {
			if(begin<0)
				costOrgName=new String(costOrgName .getBytes("ISO-8859-1"), "UTF-8");
			 whereFinal+="  AND t.cost_org_name  like '%" + costOrgName + "%' ";
	        }
		//0类型名称、1添加日期
		String sql = "select t.cost_org_name,t.enable_status,"
				+ "toi.OPR_NAME,TO_CHAR(t.add_date,'yyyy-mm-dd hh24:mi:ss') as add_date,(select a.FORM_ORG_NAME from RAIL_FORM_ORG a where a.id=t.form_org) as FORM_ORG_NAME,toi2.OPR_NAME,"
				+ "t.upd_date,t.form_org,t.id  "
				+ whereFinal+" order by t.ADD_DATE desc";
		List<Object[]> dataList;
		if(begin < 0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0类型名称、1添加日期
		
		
		
		//t.cost_org_name,t.enable_status,add_user,add_date,FORM_ORG_NAME,upd_user,upd_date,form_org,id
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[6];
			for (Object[] obj : dataList) {
				tmpObj= new Object[10];
				tmpObj[0]=obj[0];
				if (obj[1].toString().equals("0")) {
					tmpObj[1] = "启用";
		        } else if (obj[1].toString().equals("1")) {
		        	tmpObj[1] = "停用";
		        }
				tmpObj[9]=obj[1];
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				
				tmpObj[7]=obj[7];
				tmpObj[8]=obj[8];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	/**
	 * @Description: 组织单位维护列表 添加
	 * @param @param begin
	 * @param @param request
	 * @param @return 0类型名称、1添加日期
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getFormOrg(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		String whereFinal="from RAIL_FORM_ORG t Where t.DEL_STATUS='0'";
		//0类型名称、1添加日期
		String sql = "select t.FORM_ORG_NAME,t.ADD_DATE "
				+ whereFinal+" order by t.ADD_DATE desc";
		List<Object[]> dataList;
		if(begin < 0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0类型名称、1添加日期
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[2];
			for (Object[] obj : dataList) {
				tmpObj= new Object[2];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: 供应商维护列表 140101
	 * @param @param begin
	 * @param @param request
	 * @param @return 0供应商名称、1地址、2联系人、3联系电话、4传真、5添加时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getMfrsOrg(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		String whereFinal="from RAIL_MFRS_ORG t Where t.DEL_STATUS='0'";
		String mfrsOrgName = request.getParameter("mfrsOrgName");
	    if (isNotEmpty(mfrsOrgName)) {
	        if(begin<0)
	        	mfrsOrgName= new String(mfrsOrgName .getBytes("ISO-8859-1"), "UTF-8");
	        whereFinal+="  AND t.MFRS_ORG_NAME  like '%" + mfrsOrgName + "%' ";
	    }
		//0供应商名称、1地址、2联系人、3联系电话、4传真、5添加时间
		String sql = "select t.MFRS_ORG_NAME,t.ENABLE_STATUS,t.MFRS_ADDRESS,t.MFRS_PIC,t.MFRS_TEL,t.MFRS_FAX,t.ADD_USER,TO_CHAR(t.add_date,'yyyy-mm-dd hh24:mi:ss') as add_date, t.id "
				+ whereFinal+" order by t.ADD_DATE desc";
		List<Object[]> dataList;
		if(begin < 0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0类型名称、1添加日期
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[9];
			for (Object[] obj : dataList) {
				tmpObj= new Object[9];
				tmpObj[0]=obj[0];
				if (obj[1].toString().equals("0")) {
					tmpObj[1] = "启用";
		        } else if (obj[1].toString().equals("1")) {
		        	tmpObj[1] = "停用";
		        }
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				tmpObj[7]=obj[7];
				tmpObj[8]=obj[8];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: 仓库区维护列表 140102
	 * @param @param begin
	 * @param @param request
	 * @param @return 0仓库区名称、1添加时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getArea(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		String whereFinal="from RAIL_AREA t Where t.DEL_STATUS='0'";

		String whseAreaName = request.getParameter("whseAreaName");
		 if (isNotEmpty(whseAreaName)) {
			 if(begin<0)
				 whseAreaName= new String(whseAreaName .getBytes("ISO-8859-1"), "UTF-8");
			 whereFinal+="  AND t.WHSE_AREA_NAME  like '%" + whseAreaName + "%' ";
	        }
		//0仓库区名称、1添加时间
		String sql = "select t.id,t.WHSE_AREA_NAME,t.add_user,t.add_date,t.upd_user,t.upd_date,t.enable_status,t.const_org,(select a.COST_ORG_NAME from RAIL_CONST_ORG a where a.id=t.const_org) as COST_ORG_NAME   "
				+ whereFinal+" order by t.ADD_DATE desc";
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0仓库区名称、1添加时间
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[8];
			for (Object[] obj : dataList) {
				tmpObj= new Object[9];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				tmpObj[7]=obj[7];
				tmpObj[8]=obj[8];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: 工具名称管理管理维护列表 140103
	 * @param @param begin
	 * @param @param request
	 * @param @return 0工具名称、1、工具分类、2添加时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getToolName(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		String whereFinal="from RAIL_TOOL_NAME t left join RAIL_TOOL_TYPE t1 on t.TOOL_TYPE=t1.ID "
				+ "Where t.DEL_STATUS='0'";

		String toolName = request.getParameter("toolName");
		 if (isNotEmpty(toolName)) {
			 if(begin<0)
				 toolName= new String(toolName .getBytes("ISO-8859-1"), "UTF-8");
			 whereFinal+="  AND t.TOOL_NAME  like '%" + toolName + "%' ";
	     }
		 
		 if (isNotEmpty(request.getParameter("toolType"))) {
			 whereFinal+="  AND t.TOOL_TYPE ='" + request.getParameter("toolType") + "' ";
	        }
		//0工具名称、1、工具分类、2添加时间
		String sql = "select t.TOOL_NAME,t.enable_status,"
				+ "(select t1.TOOL_TYPE_NAME from RAIL_TOOL_TYPE t1 where t1.id=t.TOOL_TYPE) as TOOL_TYPE_NAME,"
				+ "t.TOOL_IMG,t.add_user,"
				+ "TO_CHAR(t.add_date,'yyyy-mm-dd hh24:mi:ss') as add_date,t.id,t.TOOL_TYPE,t.enable_status "
				+ whereFinal+" order by t.ADD_DATE desc";
		List<Object[]> dataList;
		if(begin < 0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0工具名称、1、工具分类、2添加时间
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		Map<String,Object> ENABLE_STATUS = PubCstSysParam.getSysParamMap(ContantValue.ENABLE_STATUS);
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[9];
			for (Object[] obj : dataList) {
				tmpObj= new Object[9];
				tmpObj[0]=obj[0];
				if (obj[1] != null && !obj[1].equals("")) {
					if(ENABLE_STATUS.get(String.valueOf(obj[1]))!=null){
						tmpObj[1] = ENABLE_STATUS.get(String.valueOf(obj[1]));
					}else{
						tmpObj[1] = "未知";
					}
				}
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				tmpObj[7]=obj[7];
				tmpObj[8]=obj[8];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: 工具分类管理管理维护列表 140104
	 * @param @param begin
	 * @param @param request
	 * @param @return 0分类名称、1添加时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getToolType(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		String whereFinal="from RAIL_TOOL_TYPE t Where t.DEL_STATUS='0'";

		String toolTypeName = request.getParameter("toolTypeName");
		 if (isNotEmpty(toolTypeName)) {
			 if(begin<0)
				 toolTypeName= new String(toolTypeName .getBytes("ISO-8859-1"), "UTF-8"); 
			 whereFinal+="  AND t.TOOL_TYPE_NAME  like '%" + toolTypeName + "%' ";
	        }
		//0分类名称、1添加时间
		 //toolTypeName,enableStatus,addUser,addDate,updUser,updDate,enableStatus,id
		String sql = "select t.TOOL_TYPE_NAME,t.enable_status,t.add_user,TO_CHAR(t.add_date,'yyyy-mm-dd hh24:mi:ss') as add_date,t.upd_user,t.upd_date,t.enable_status,t.id "
				+ whereFinal+" order by t.ADD_DATE desc";
		List<Object[]> dataList;
		if(begin < 0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0分类名称、1添加时间
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[8];
			for (Object[] obj : dataList) {
				tmpObj= new Object[8];
				tmpObj[0]=obj[0];
				Map<String,Object> ENABLE_STATUS = PubCstSysParam.getSysParamMap(ContantValue.ENABLE_STATUS);
				if (obj[1] != null && !obj[1].equals("")) {
					if(ENABLE_STATUS.get(String.valueOf(obj[1]))!=null){
						tmpObj[1] = ENABLE_STATUS.get(String.valueOf(obj[1]));
					}else{
						tmpObj[1] = "未知";
					}
				}
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				if (obj[6] != null && !obj[6].equals("")) {
					if(ENABLE_STATUS.get(String.valueOf(obj[6]))!=null){
						tmpObj[6] = ENABLE_STATUS.get(String.valueOf(obj[6]));
					}else{
						tmpObj[6] = "未知";
					}
				}
				tmpObj[7]=obj[7];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: 工具单位管理管理维护列表 140105
	 * @param @param begin
	 * @param @param request
	 * @param @return 0单位名称、1添加时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getToolUnit(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		String whereFinal="from RAIL_TOOL_UNIT t Where t.DEL_STATUS='0'";

		String toolUnitName = request.getParameter("toolUnitName");
		 if (isNotEmpty(toolUnitName)) {
			 if(begin<0)
				 toolUnitName= new String(toolUnitName .getBytes("ISO-8859-1"), "UTF-8");
			 whereFinal+="  AND t.TOOL_UNIT_NAME  like '%" + toolUnitName + "%' ";
	        }
		//0单位名称、1添加时间
		 //<![CDATA[toolUnitName,enableStatusDesc,addUser,addDate,updUser,updDate,enableStatus,id]]>
		String sql = "select t.TOOL_UNIT_NAME,t.enable_status,t.add_user,TO_CHAR(t.add_date,'yyyy-mm-dd hh24:mi:ss') as add_date,t.upd_user,t.upd_date,t.enable_status,t.id "
				+ whereFinal+" order by t.ADD_DATE desc";
		List<Object[]> dataList;
		if(begin < 0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0单位名称、1添加时间
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		Map<String,Object> ENABLE_STATUS = PubCstSysParam.getSysParamMap(ContantValue.ENABLE_STATUS);
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[8];
			for (Object[] obj : dataList) {
				tmpObj= new Object[8];
				tmpObj[0]=obj[0];
				if (obj[1] != null && !obj[1].equals("")) {
					if(ENABLE_STATUS.get(String.valueOf(obj[1]))!=null){
						tmpObj[1] = ENABLE_STATUS.get(String.valueOf(obj[1]));
					}else{
						tmpObj[1] = "未知";
					}
				}
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				if (obj[6] != null && !obj[6].equals("")) {
					if(ENABLE_STATUS.get(String.valueOf(obj[6]))!=null){
						tmpObj[6] = ENABLE_STATUS.get(String.valueOf(obj[6]));
					}else{
						tmpObj[6] = "未知";
					}
				}
				tmpObj[7]=obj[7];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: 工具型号管理管理维护列表 140106
	 * @param @param begin
	 * @param @param request
	 * @param @return 0型号编码、1型号名称、2工具名称、3工具单位、4材质、5库、6架、7层、8位、9添加时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getToolModel(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		String whereFinal="from RAIL_TOOL_MODEL t left join RAIL_TOOL_NAME t1 on t.TOOL_NAME=t1.ID "
				+ "left join RAIL_TOOL_UNIT t2 on t.TOOL_UNIT=t2.id "
				+ "Where t.DEL_STATUS='0'";

		String modelName = request.getParameter("modelName");
		 if (isNotEmpty(modelName)) {
			 if(begin<0)
				 modelName= new String(modelName .getBytes("ISO-8859-1"), "UTF-8");
			 whereFinal+="  AND t.MODEL_NAME  like '%" + modelName + "%' ";
	        }
		//0型号编码、1型号名称、2工具名称、3工具单位、4材质、5库、6架、7层、8位、9添加时间
		String sql = "select t.id,t.MODEL_CODE,t.MODEL_NAME,t1.TOOL_NAME,t2.TOOL_UNIT_NAME,t.TOOL_MATERIAL,t.WHSE,t.STAND,t.FLOOR,t.POSITION,t.ADD_DATE "
				+ whereFinal+" order by t.ADD_DATE desc";
		List<Object[]> dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0型号编码、1型号名称、2工具名称、3工具单位、4材质、5库、6架、7层、8位、9添加时间
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[10];
			for (Object[] obj : dataList) {
				tmpObj= new Object[10];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				tmpObj[7]=obj[7];
				tmpObj[8]=obj[8];
				tmpObj[9]=obj[9];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: 门禁终端管理类型维护列表 140107
	 * @param @param begin
	 * @param @param request
	 * @param @return 0类型名称、1添加时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getAccessEquipType(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		String whereFinal="from RAIL_ACCESS_EQUIP_TYPE t Where t.DEL_STATUS='0'";

		String equipTypeName = request.getParameter("equipTypeName");
		 if (isNotEmpty(equipTypeName)) {
			  if(begin<0)
			    	equipTypeName= new String(equipTypeName .getBytes("ISO-8859-1"), "UTF-8");
			 whereFinal+="  AND t.EQUIP_TYPE_NAME  like '%" + equipTypeName + "%' ";
	        }
		//0类型名称、1添加时间
		String sql = "select t.EQUIP_TYPE_NAME,t.enable_status,t.add_user, TO_CHAR(t.add_date,'yyyy-mm-dd hh24:mi:ss') as add_date,t.id  "
				+ whereFinal+" order by t.ADD_DATE desc";
		List<Object[]> dataList;
		if(begin < 0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0类型名称、1添加时间
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[5];
			for (Object[] obj : dataList) {
				tmpObj= new Object[5];
				tmpObj[0]=obj[0];
				if (obj[1].toString().equals("0")) {
					tmpObj[1] = "启用";
		        } else if (obj[1].toString().equals("1")) {
		        	tmpObj[1] = "停用";
		        }
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}	
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: 人员类型 130404
	 * @param @param begin
	 * @param @param request
	 * @param @return 0类型名称、1添加时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getEmployeeType(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		String whereFinal="from RAIL_EMPLOYEE_TYPE t,TBL_OPR_INFO t1 Where t.DEL_STATUS='0'";

		String typeName = request.getParameter("typeName");
		 if (isNotEmpty(typeName)) {
			 if(begin<0)
				 typeName= new String(typeName .getBytes("ISO-8859-1"), "UTF-8");
			 whereFinal+="  AND t.TYPE_NAME  like '%" + typeName + "%' ";
	    }
		 whereFinal+=" AND t1.OPR_ID=t.ADD_USER";
		//0类型名称、1添加时间
		String sql = "select t.TYPE_NAME,t.enable_status,t1.OPR_NAME, TO_CHAR(t.add_date,'yyyy-mm-dd hh24:mi:ss') as add_date,t.id  "
				+ whereFinal+" order by t.ADD_DATE desc";
		List<Object[]> dataList;
		if(begin < 0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0类型名称、1添加时间
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[5];
			for (Object[] obj : dataList) {
				tmpObj= new Object[5];
				tmpObj[0]=obj[0];
				if (obj[1].toString().equals("0")) {
					tmpObj[1] = "启用";
		        } else if (obj[1].toString().equals("1")) {
		        	tmpObj[1] = "停用";
		        }
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}	
	
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: 仓库工具预警阈值查询
	 * @param @param begin
	 * @param @param request
	 * @param @return 0类型名称、1添加时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getRailWhseToolWarn(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		String whereFinal="from RAIL_Whse_Tool_Warn t   "
				+ "LEFT JOIN RAIL_TOOL_NAME rtn ON t.TOOL_NAME = rtn.ID LEFT JOIN RAIL_WHSE_INFO rwi ON t.WHSE_CODE = rwi.WHSE_CODE LEFT JOIN TBL_OPR_INFO toi ON toi.OPR_ID=t.ADD_USER "
				+ "  Where t.DEL_STATUS='0'";
		 if (isNotEmpty(request.getParameter("whseCode"))) {
			 whereFinal+="  AND t.whse_Code ='" + request.getParameter("whseCode") + "' ";
	        }

		 String toolName = request.getParameter("toolName");
		 if (isNotEmpty(toolName)) {
			 if(begin<0)
				 toolName= new String(toolName .getBytes("ISO-8859-1"), "UTF-8");
			 whereFinal+="  AND t.tool_Name ='" + toolName + "' ";
	        }
		//0类型名称、1添加时间
		String sql = "select t.whse_Code,rwi.WHSE_NAME,t.tool_Name,rtn.TOOL_NAME as toolnamename ,t.lower_Threshold, toi.OPR_NAME,TO_CHAR(t.add_date,'yyyy-mm-dd hh24:mi:ss') as add_date,t.id  "
				+ "  "
				+ whereFinal+" order by t.ADD_DATE desc";
		List<Object[]> dataList;
		if(begin < 0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0类型名称、1添加时间
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[8];
			for (Object[] obj : dataList) {
				tmpObj= new Object[8];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				tmpObj[7]=obj[7];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}	
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: 仓库工具下限预警
	 * @param @param begin
	 * @param @param request
	 * @param @return 0类型名称、1添加时间
	 * @return Object[]
	 * @throws
	 */
	public static  Object[]  getRailWhseToolWarntongji(int begin, HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (operator == null) {
			return ret;
		}
		
		 String orgBrhId=operator.getOprBrhId();
	        List<String> list_depts= new ArrayList<String>();
	        list_depts.add(orgBrhId);
	        //查询当前部门下的所有下属部门
			StringBuffer where = new StringBuffer();
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
		
		
		
		
		String whereFinal=" from  Rail_Whse_Tool_Warn tt LEFT JOIN (SELECT t1.WHSE_CODE,t1.WHSE_NAME,t2.ID, t2.TOOL_NAME,COUNT(*) num,t.IN_WHSE FROM RAIL_TOOL_INFO t  LEFT JOIN RAIL_WHSE_INFO t1 ON t.WHSE_CODE=t1.WHSE_CODE LEFT JOIN RAIL_TOOL_NAME t2 ON t.TOOL_NAME=t2.ID "
				+ " where 1=1   "+where+ " AND  t1.DEL_STATUS = 0 AND"
				+ " t.DEL_STATUS = 0  AND t.TOOL_STATUS != 3 GROUP BY t1.WHSE_CODE,t1.WHSE_NAME,t2.ID,t2.TOOL_NAME,t.IN_WHSE ) aa  on  aa.whse_code=tt.whse_code and aa.id=tt.tool_name  where tt.del_Status='0' and (tt.lower_threshold>aa.num  or aa.num is null)";
		 if (isNotEmpty(request.getParameter("whseCode"))) {
			 whereFinal+="  AND tt.whse_Code ='" + request.getParameter("whseCode") + "' ";
	        }

		 String toolName = request.getParameter("toolName");
		 if (isNotEmpty(toolName)) {
			 if(begin<0)
				 toolName= new String(toolName .getBytes("ISO-8859-1"), "UTF-8");
			 whereFinal+="  AND tt.tool_name ='" + toolName + "' ";
	        }
		//0类型名称、1添加时间
		String sql = "select tt.whse_code, (select  WHSE_NAME from RAIL_WHSE_INFO aa where tt.whse_code=aa.WHSE_CODE) as  whse_name,  (select  TOOL_NAME from RAIL_TOOL_NAME aa where tt.tool_name=aa.id) as  tool_name, (CASE WHEN aa.num IS NULL THEN 0 ELSE aa.num END) AS num,tt.lower_Threshold,aa.id  "
				+ whereFinal;
		List<Object[]> dataList;
		if(begin < 0){
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		}else{
			dataList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//用于返回的列表
		//0类型名称、1添加时间
		List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[6];
			for (Object[] obj : dataList) {
				tmpObj= new Object[6];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				dataListRet.add(tmpObj);
			}
		}
		
		String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataListRet;
		ret[1] = count;
		return ret;
	}	
	//=================================系统管理分页查询开始====================================
	
	
	//==================================施工单位相关分页查询结束==================================
	
	
    /**
     * 
     * //TODO 获取门禁列表信息
     *
     * @param begin
     * @param request
     * @return
     * @author qiufulong
     * @throws UnsupportedEncodingException 
     */
	@SuppressWarnings("unchecked")
    public static Object[] getRailAccessInfo(int begin, HttpServletRequest request) throws UnsupportedEncodingException {

        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" WHERE 1=1 ");

        String accessCode = request.getParameter("accessCode");
        if (isNotEmpty(accessCode)) {
        	if(begin<0)
        		accessCode= new String(accessCode .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND rai.access_code like '%" + accessCode+"%' ");
        }
        String accessName = request.getParameter("accessName");
        if (isNotEmpty(request.getParameter("accessName"))) {
        	if(begin<0)
        		accessName= new String(accessName .getBytes("ISO-8859-1"), "UTF-8");
            whereSql.append(" AND rai.access_name like '%" + accessName + "%' ");
        }
        whereSql.append(" AND toi.OPR_ID=rai.add_user");
        String sql = "select rai.access_code ,rai.access_name,rai.access_type,rai.note1,rai.access_route,"
        		+ "rai.access_address ,"
        		+ "(select a.brh_name from TBL_BRH_INFO a where a.brh_id=rai.access_dept) as brh_name,"
        		+ "rai.access_pic,rai.access_tel ,rai.police_office ,"
        		+ "rai.exam_period , TO_CHAR(rai.last_exam,'yyyy-mm-dd hh24:mi:ss') as last_exam ,"
        		+ "rai.access_status,rai.note5,rai.open_status ,rai.mileage,"
        		+ "rai.mileage_previous,rai.mileage_next, TO_CHAR(rai.install_date,'yyyy-mm-dd hh24:mi:ss') as install_date,"
        		+ "rai.longitude,rai.latitude,rai.whse_code ,toi.OPR_NAME,rai.access_dept,"
        		+ "rai.access_type,rai.access_status,rai.open_status"
        		+ " from RAIL_ACCESS_INFO rai,TBL_OPR_INFO toi " + whereSql.toString() + " order by rai.ADD_DATE desc";
        //<![CDATA[accessCode,accessName,2 accessType,note1,accessRoute,accessAddress,brhName,
        //accessPic,accessTel,policeOffice,examPeriod,lastExam, 12 accessStatus,13 openStatus,warnWeixin,mileage,mileagePrevious,mileageNext,
        //installDate,longitude,latitude,whseCode,addUser,accessDept,accessType,accessStatus,openStatus]]>
        String countSql = "SELECT COUNT(1) FROM RAIL_ACCESS_INFO RAI,TBL_OPR_INFO toi " + whereSql.toString();
        
        List<Object[]> dataList ;
        if(begin < 0){
    	   dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
        }else{
    	   dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        }
        List<Object[]> dataListRet=new ArrayList<Object[]>();
        Map<String,Object> ENABLE_STATUS = PubCstSysParam.getSysParamMap(ContantValue.ENABLE_STATUS);
        Map<String,Object> ACCESS_TYPE = PubCstSysParam.getSysParamMap(ContantValue.ACCESS_TYPE);
        if (dataList!=null && dataList.size()>0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] tmpObjects=dataList.get(i);
				String equipCodee=(String) tmpObjects[3];
				
				if (PubUtil.EQUIP_IN_CON.get(equipCodee)!=null) {
					tmpObjects[13]="正常";
				}else {
					tmpObjects[13]="故障";
				}
				
				if (PubUtil.wsChannelIsAvailable(equipCodee)) {
					tmpObjects[14]="已连接";
				}else {
					tmpObjects[14]="未连接";
				}
				
				Object[] obj=dataList.get(i);
				if (obj[2] != null && !obj[2].equals("")) {
					if(ACCESS_TYPE.get(String.valueOf(obj[2]))!=null){
						tmpObjects[2] = ACCESS_TYPE.get(String.valueOf(obj[2]));
					}else{
						tmpObjects[2] = "未知";
					}
				}
				
				if (obj[12] != null && !obj[12].equals("")) {
					if(ENABLE_STATUS.get(String.valueOf(obj[12]))!=null){
						tmpObjects[12] = ENABLE_STATUS.get(String.valueOf(obj[12]));
					}else{
						tmpObjects[12] = "未知";
					}
				}
				
				dataListRet.add(tmpObjects);
			}
		}
        
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
    }
	 /**
     * 
     * //TODO 获取门禁终端设备列表信息
     *
     * @param begin
     * @param request
     * @return
     * @author qiufulong
     */
	@SuppressWarnings("unchecked")
    public static Object[] getRailAccessEquipInfo(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" WHERE 1=1 and raei.equip_status='0' ");
        if (isNotEmpty(request.getParameter("accessCode"))) {
            whereSql.append("  AND raei.access_code ='" + request.getParameter("accessCode")+"'");
        }
        if (isNotEmpty(request.getParameter("equipCode"))) {
            whereSql.append("  AND raei.equip_code ='" + request.getParameter("equipCode")+"'");
        }
        if (isNotEmpty(request.getParameter("equipType"))) {
        	whereSql.append("  AND raei.equip_type ='" + request.getParameter("equipType")+"'");
        }
        whereSql.append(" AND toi.OPR_ID=raei.add_user ");
        String sql = "select  raei.access_code,raei.equip_code,raei.equip_name,"
        		+ " (select a.EQUIP_TYPE_NAME from RAIL_ACCESS_EQUIP_TYPE a where a.id=raei.equip_type) as EQUIP_TYPE_NAME, "
        		+ "raei.equip_ip,raei.note1,raei.note2,TO_CHAR(raei.install_date,'yyyy-mm-dd hh24:mi:ss') as install_date ,"
        		+ "raei.equip_status,toi.OPR_NAME, TO_CHAR(raei.add_date,'yyyy-mm-dd hh24:mi:ss') as add_date,"
        		+ "raei.equip_type,raei.DEFENCE,raei.equip_status as equip_status2,raei.id "
        		+ "from RAIL_ACCESS_EQUIP_INFO raei,TBL_OPR_INFO toi " + whereSql.toString() + " order by raei.ADD_DATE desc";
        String countSql = "SELECT COUNT(1) FROM RAIL_ACCESS_EQUIP_INFO RAEI,TBL_OPR_INFO toi " + whereSql.toString();
        List<Object[]> dataList ;
        if(begin<0){
        	dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
        }else{
        	dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        }
        List<Object[]> dataListRet=new ArrayList<Object[]>();
        if (dataList!=null && dataList.size()>0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] tmpObjects=dataList.get(i);
				String equipCodee=(String) tmpObjects[1];
				int equipType=Integer.parseInt(String.valueOf(tmpObjects[11]));
				int defence=Integer.parseInt(String.valueOf(tmpObjects[12]));
				
				if (equipType==1&&PubUtil.EQUIP_IN_CON.containsKey(equipCodee)) {
					tmpObjects[5]=PubUtil.EQUIP_IN_CON.get(equipCodee).getDefenceState()[defence-1];
					if (String.valueOf(tmpObjects[5]).contains("0")) {
						tmpObjects[8]="正常";
					}else {
						tmpObjects[8]="故障";
					}
				}else {
					tmpObjects[8]="未连接";
				}
				
				if (equipType!=1) {
					tmpObjects[8]="正常";
				}
				dataListRet.add(tmpObjects);
			}
		}
        
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
    }
	/**
     * 
     * //TODO 获取门禁预警列表信息
     *
     * @param begin
     * @param request
     * @return
     * @author qiufulong
     */
	@SuppressWarnings("unchecked")
    public static Object[] getRailAccessWarn(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" WHERE 1=1 ");
        if (isNotEmpty(request.getParameter("accessCode"))) {
            whereSql.append(" AND raww.access_code ='" + request.getParameter("accessCode")+"'");
        }
        //accessCode,accessName,2 warnTypeDesc,warnMsg, 4 infoSignDesc,addDate,verifyMsg,verifyUser,verifyDate,id,warnType,infoSign]]>
        String sql = "select raww.access_Code,(select a.ACCESS_NAME from RAIL_ACCESS_INFO a where a.ACCESS_CODE=raww.access_Code) as ACCESS_NAME ,"
        		+ "raww.warn_Type,raww.warn_Msg,raww.info_Sign,"
        		+ " TO_CHAR(raww.add_Date,'yyyy-mm-dd hh24:mi:ss') as add_Date,raww.verify_Msg ,toi.OPR_NAME, "
        		+ "TO_CHAR(raww.verify_Date,'yyyy-mm-dd hh24:mi:ss') as verify_Date,"
        		+ "raww.id,raww.warn_Type,raww.info_Sign"
        		+ " from RAIL_ACCESS_WARN raww LEFT JOIN TBL_OPR_INFO toi ON toi.OPR_ID = raww.verify_User " + whereSql.toString() + " order by raww.ADD_DATE desc";
        String countSql = "SELECT COUNT(1) FROM RAIL_ACCESS_WARN RAWW LEFT JOIN TBL_OPR_INFO toi ON toi.OPR_ID = raww.verify_User " + whereSql.toString();
        List<Object[]> dataList ;
        if(begin < 0 ){
        	dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
        }else{
        	dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        }
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        
        Map<String,Object> ACCESS_WRAN_TYPE = PubCstSysParam.getSysParamMap(ContantValue.ACCESS_WRAN_TYPE);
        Map<String,Object> VERIFY_WARN = PubCstSysParam.getSysParamMap(ContantValue.VERIFY_WARN);
    	List<Object[]> dataListRet=new ArrayList<Object[]>();
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[12];
			for (Object[] obj : dataList) {
				tmpObj= new Object[12];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				if (obj[2] != null && !obj[2].equals("")) {
					if(ACCESS_WRAN_TYPE.get(String.valueOf(obj[2]))!=null){
						tmpObj[2] = ACCESS_WRAN_TYPE.get(String.valueOf(obj[2]));
					}else{
						tmpObj[2] = "未知";
					}
				}
				
				tmpObj[3]=obj[3];
				if (obj[4] != null && !obj[4].equals("")) {
					if(VERIFY_WARN.get(String.valueOf(obj[4]))!=null){
						tmpObj[4] = VERIFY_WARN.get(String.valueOf(obj[4]));
					}else{
						tmpObj[4] = "未知";
					}
				}
				
				tmpObj[5]=obj[5];
				tmpObj[6]=obj[6];
				tmpObj[7]=obj[7];
				tmpObj[8]=obj[8];
				tmpObj[9]=obj[9];
				tmpObj[10]=obj[10];
				tmpObj[11]=obj[11];
				dataListRet.add(tmpObj);
			}
		}
		
        
        
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
    }
	
	/**
     * 
     * //TODO 获取门禁检修保养预警列表信息
     *
     * @param begin
     * @param request
     * @return
     * @author qiufulong
     */
	@SuppressWarnings("unchecked")
    public static Object[] getRailAccessMaintainWarn(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" WHERE 1=1 ");
        if (isNotEmpty(request.getParameter("accessCode"))) {
            whereSql.append(" AND raww.access_code ='" + request.getParameter("accessCode")+"'");
        }
        if (isNotEmpty(request.getParameter("equipCode"))) {
            whereSql.append("  AND raww.equip_code ='" + request.getParameter("equipCode")+"'");
        }
        //accessCode,accessName,warnMsg, 3 infoSignDesc,addDate,verifyMsg,verifyUser,verifyDate,id,infoSign]]>
        String sql = "select raww.access_Code,(select a.ACCESS_NAME from RAIL_ACCESS_INFO a where a.ACCESS_CODE=raww.access_Code) as ACCESS_NAME,"
        		+ "raww.warn_Msg,raww.info_Sign,"
        		+ "TO_CHAR(raww.add_Date,'yyyy-mm-dd hh24:mi:ss') as add_Date,raww.verify_Msg,"
        		+ "toi.OPR_NAME, TO_CHAR(raww.verify_Date,'yyyy-mm-dd hh24:mi:ss') as verify_Date,"
        		+ "raww.id,raww.info_Sign  "
        		+ " from RAIL_ACCESS_MAINTAIN_WARN raww LEFT JOIN	TBL_OPR_INFO toi ON toi.OPR_ID = raww.verify_User " + whereSql.toString() + " order by raww.ADD_DATE desc";
        String countSql = "SELECT COUNT(1) FROM RAIL_ACCESS_MAINTAIN_WARN RAWW LEFT JOIN	TBL_OPR_INFO toi ON toi.OPR_ID = raww.verify_User " + whereSql.toString();
        List<Object[]> dataList ;
        if(begin < 0 ){
        	dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
        }else{
        	dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        }
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        
    	List<Object[]> dataListRet=new ArrayList<Object[]>();
    	Map<String,Object> VERIFY_WARN = PubCstSysParam.getSysParamMap(ContantValue.VERIFY_WARN);
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[10];
			for (Object[] obj : dataList) {
				tmpObj= new Object[10];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				int i;
				i = 3;
				if (obj[i] != null && !obj[i].equals("")) {
					if(VERIFY_WARN.get(String.valueOf(obj[i]))!=null){
						tmpObj[i] = VERIFY_WARN.get(String.valueOf(obj[i]));
					}else{
						tmpObj[i] = "未知";
					}
				}
				tmpObj[5]=obj[5];
				tmpObj[4]=obj[4];
				tmpObj[6]=obj[6];
				tmpObj[7]=obj[7];
				tmpObj[8]=obj[8];
				tmpObj[9]=obj[9];
				dataListRet.add(tmpObj);
			}
		}
		
        
        
        
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
    }
	
	/**
     * 
     * //TODO 获取门禁检修保养列表信息
     *
     * @param begin
     * @param request
     * @return
     * @author qiufulong
     */
	@SuppressWarnings("unchecked")
    public static Object[] getRailAccessMaintain(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" where 1=1 ");
        if (!StringUtil.isNull(request.getParameter("startDate"))&&!StringUtil.isNull(request.getParameter("endDate"))) {
        	Date endTime = DateUtil.add(DateUtil.strToDate(request.getParameter("endDate"), DateUtil.PATTERN_YMD), 1);
        	whereSql.append(" AND raww.add_Date between to_date('"+request.getParameter("startDate")+"','yyyy-MM-dd') AND to_date('"+DateUtil.dateToStr(endTime, DateUtil.PATTERN_YMD)+"','yyyy-MM-dd')");
        }
        if (isNotEmpty(request.getParameter("accessCode"))) {
            whereSql.append(" AND raww.access_code ='" + request.getParameter("accessCode")+"'");
        }
        if (isNotEmpty(request.getParameter("equipCode"))) {
            whereSql.append("  AND raww.equip_code ='" + request.getParameter("equipCode")+"'");
        }
        String sql = "select raww.access_Code,a.ACCESS_NAME, "
        		+ " a.ACCESS_ROUTE,a.ACCESS_ADDRESS,a.ACCESS_PIC,a.ACCESS_TEL,TO_CHAR(raww.add_Date,'yyyy-mm-dd hh24:mi:ss') as add_Date,toi.OPR_NAME,raww.id "
        		+ "from RAIL_ACCESS_MAINTAIN raww LEFT JOIN RAIL_ACCESS_INFO a ON raww.access_code=a.access_code LEFT JOIN	TBL_OPR_INFO toi ON toi.OPR_ID = raww.MAINTAIN_USER "
        		+ whereSql.toString() + " and raww.access_code=a.access_code  order by raww.ADD_DATE desc";
        String countSql = "SELECT COUNT(1) FROM RAIL_ACCESS_MAINTAIN RAWW LEFT JOIN RAIL_ACCESS_INFO a ON raww.access_code=a.access_code LEFT JOIN TBL_OPR_INFO toi ON toi.OPR_ID = raww.MAINTAIN_USER " + whereSql.toString();
        List<Object[]> dataList ;
        if(begin < 0 ){
        	dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
        }else{
        	dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        }
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }
	
	/**
     * 
     * //TODO 获取门禁出入记录信息
     *
     * @param begin
     * @param request
     * @return
     * @author qiufulong
     */
	@SuppressWarnings("unchecked")
    public static Object[] getRailAccessOptlog(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" WHERE 1=1 ");
        if (isNotEmpty(request.getParameter("accessCode"))) {
            whereSql.append(" AND raww.access_code ='" + request.getParameter("accessCode")+"'");
        }
        if (isNotEmpty(request.getParameter("equipCode"))) {
            whereSql.append("  AND raww.equip_code ='" + request.getParameter("equipCode")+"'");
        }
        if (isNotEmpty(request.getParameter("infoSign"))) {
            whereSql.append("  AND raww.INFO_SIGN ='" + request.getParameter("infoSign")+"'");
        }
        if (isNotEmpty(request.getParameter("openSign"))) {
            whereSql.append("  AND raww.OPEN_SIGN ='" + request.getParameter("openSign")+"'");
        }
        //accessCode,accessName,employeeCode,employeeName,workCode,workName,
        // 6 infoSign, 7 openSign,addDate,addUser,id]]>
        String sql = "select raww.access_Code,(select a.ACCESS_NAME from RAIL_ACCESS_INFO a where a.ACCESS_CODE=raww.access_Code) as ACCESS_NAME,"
        		+ "raww.EMPLOYEE_CODE,(select a.EMPLOYEE_NAME from RAIL_EMPLOYEE a where a.EMPLOYEE_CODE=raww.EMPLOYEE_CODE) as EMPLOYEE_NAME, "
        		+ "raww.WORK_CODE,(select a.WORK_NAME from RAIL_WORK_INFO a where a.WORK_CODE=raww.WORK_CODE) as WORK_NAME ,"
        		+ "raww.INFO_SIGN,raww.OPEN_SIGN,TO_CHAR(raww.add_Date,'yyyy-mm-dd hh24:mi:ss') as add_Date,toi.OPR_NAME, "
        		+ "raww.id  "
        		+ "  from RAIL_ACCESS_OPTLOG raww LEFT JOIN	TBL_OPR_INFO toi ON toi.OPR_ID = raww.ADD_USER " + whereSql.toString() + " order by raww.ADD_DATE desc";
        String countSql = "SELECT COUNT(1) FROM RAIL_ACCESS_OPTLOG  RAWW LEFT JOIN TBL_OPR_INFO toi ON toi.OPR_ID = raww.ADD_USER " + whereSql.toString();
        List<Object[]> dataList ;
        if(begin < 0 ){
        	dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
        }else{
        	dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        }
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        List<Object[]> dataListRet= new ArrayList<Object[]>();
        Map<String,Object> ACCESS_IO_TYPE = PubCstSysParam.getSysParamMap(ContantValue.ACCESS_IO_TYPE);
        Map<String,Object> ACCESS_OPEN_SIGN = PubCstSysParam.getSysParamMap(ContantValue.ACCESS_OPEN_SIGN);
		if (dataList!=null && dataList.size()>0) {
			Object[] tmpObj= new Object[11];
			for (Object[] obj : dataList) {
				tmpObj= new Object[11];
				tmpObj[0]=obj[0];
				tmpObj[1]=obj[1];
				tmpObj[2]=obj[2];
				tmpObj[3]=obj[3];
				tmpObj[4]=obj[4];
				tmpObj[5]=obj[5];
				int i ;
				i = 6;
				if (obj[i] != null && !obj[i].equals("")) {
					if(ACCESS_IO_TYPE.get(String.valueOf(obj[i]))!=null){
						tmpObj[i] = ACCESS_IO_TYPE.get(String.valueOf(obj[i]));
					}else{
						tmpObj[i] = "未知";
					}
				}
				i = 7;
				if (obj[i] != null && !obj[i].equals("")) {
					if(ACCESS_OPEN_SIGN.get(String.valueOf(obj[i]))!=null){
						tmpObj[i] = ACCESS_OPEN_SIGN.get(String.valueOf(obj[i]));
					}else{
						tmpObj[i] = "未知";
					}
				}
				tmpObj[8]=obj[8];
				tmpObj[9]=obj[9];
				tmpObj[10]=obj[10];
				dataListRet.add(tmpObj);
			}
		}
        ret[0] = dataListRet;
        ret[1] = count;
        return ret;
    }
	
    /**
     * 
     * //TODO 商户营销补贴规则绑定
     *
     * @param begin
     * @param request
     * @return
     * @author hanyongqing
     */
    public static Object[] getMchtMarkSubsidyBind(int begin, HttpServletRequest request) throws Exception {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String _ruleId = request.getParameter("ruleID");
        String _mchntNO = request.getParameter("mchntNO");
        String _termNO = request.getParameter("termNO");
        StringBuffer sb = new StringBuffer();
        StringBuffer whereSql = new StringBuffer();
        // 规则ID
        if (StringUtils.isNotEmpty(_ruleId)) {
            whereSql.append(" and trim(bind.RULE_ID) like '%" + _ruleId + "%' ");
        }
        // 商户号
        if (StringUtils.isNotEmpty(_mchntNO)) {
            whereSql.append(" and trim(bind.MCHNT_NO) like '%" + _mchntNO + "%' ");
        }
        // 终端号
        if (StringUtils.isNotEmpty(_termNO)) {
            whereSql.append(" and trim(bind.TERM_NO) like '%" + _termNO + "%' ");
        }
        whereSql.append(" and mcht.AGR_BR in " + orgArrStr);
        sb.append(
                " select bind.RECORD_ID,bind.RULE_ID,bind.MCHNT_NO,bind.TERM_NO,bind.OPEN_FLAG,mcht.MCHT_NM,brh.BRH_NAME,bind.BIND_TIME,bind.MODIFY_TIME,bind.REMARK "
                        + " from tbl_Mcht_MarkSubsidy_Bind bind left join tbl_mcht_base_inf mcht on bind.MCHNT_NO = mcht.MAPPING_MCHNTCDTWO "
                        + " left join tbl_brh_info brh on mcht.AGR_BR = brh.BRH_ID where 1=1 ");
        sb.append(whereSql);
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String countSql = "SELECT COUNT(1) FROM (" + sb + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        for (Object[] obj : dataList) {
            if (obj[4] != null && !obj[4].equals("")) {
                if (obj[4].equals("0")) {
                    obj[4] = "关闭";
                } else if (obj[4].equals("1")) {
                    obj[4] = "开启";
                } else {
                    obj[4] = "未知";
                }
            }
            if (obj[7] != null && !obj[7].equals("")) {
                Date parse = sdf.parse(obj[7].toString());
                obj[7] = sdf2.format(parse);
            }
            if (obj[8] != null && !obj[8].equals("")) {
                Date parse = sdf.parse(obj[8].toString());
                obj[8] = sdf2.format(parse);
            }
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 
     * //TODO 商户营销补贴规则
     *
     * @param begin
     * @param request
     * @return
     * @author hanyongqing
     */
    public static Object[] getMchntSubsidyRuleInfo(int begin, HttpServletRequest request) throws Exception {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        //      String orgArrStr = operator.getBrhBelowId();
        String _ruleId = request.getParameter("ruleId");
        String _subsidyType = request.getParameter("subsidyType");
        String _openType = request.getParameter("openType");
        String _ruleName = request.getParameter("ruleName");
        String _startTime = request.getParameter("startTime");
        String _endTime = request.getParameter("endTime");
        SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        StringBuffer sb = new StringBuffer();
        StringBuffer whereSql = new StringBuffer();
        // 补贴类型
        if (StringUtils.isNotEmpty(_subsidyType)) {
            whereSql.append(" and trim(SUBSIDY_TYPE) = '" + _subsidyType + "' ");
        }
        // 开启方式
        if (StringUtils.isNotEmpty(_openType)) {
            whereSql.append(" and trim(OPEN_TYPE) = '" + _openType + "' ");
        }
        // 规则ID
        if (StringUtils.isNotEmpty(_ruleId)) {
            whereSql.append(" and trim(RULE_ID) = '" + _ruleId + "' ");
        }
        // 规则名称
        if (StringUtils.isNotEmpty(_ruleName)) {
            whereSql.append(" and trim(RULE_NAME) like '%" + _ruleName + "%' ");
        }
        //开始时间
        if (StringUtils.isNotEmpty(_startTime)) {
            Date parse = sdf3.parse(_startTime);
            String format = sdf4.format(parse);
            whereSql.append(" and trim(START_TIME) >= '" + format + "'");
        }
        // 结束时间
        if (StringUtils.isNotEmpty(_endTime)) {
            Date parse = sdf3.parse(_endTime);
            String format = sdf4.format(parse);
            whereSql.append(" and trim(END_TIME) <= '" + format + "'");
        }
        //        whereSql.append(" and mcht.AGR_BR in " + orgArrStr);
        sb.append(
                " select RULE_ID,RULE_NAME,SUBSIDY_TYPE,SUBSIDY_RULE,OPEN_TYPE,CARD_BIN,START_TIME,END_TIME,CARD_LIMIT,TERM_LIMIT,MCHT_LIMIT,ADD_TIME,MODIFY_TIME,REMARK  "
                        + " from tbl_Mcht_MarkSubsidy where 1 = 1 ");
        sb.append(whereSql);
        sb.append(" order by RULE_ID  desc ");
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String countSql = "SELECT COUNT(1) FROM (" + sb + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        for (Object[] obj : dataList) {
            if (obj[2] != null && !obj[2].equals("")) {
                if (obj[2].equals("00")) {
                    obj[2] = "满额补";
                    if (obj[3] != null && !obj[3].equals("")) {
                        String trFToY = CommonFunction.transFenToYuan(obj[3].toString().substring(0, 12));
                        String trFToY2 = CommonFunction.transFenToYuan(obj[3].toString().substring(12, 24));
                        obj[3] = "每笔满" + trFToY + "元补" + trFToY2 + "元";
                    }
                } else if (obj[2].equals("01")) {
                    obj[2] = "按比例补";
                    if (obj[3] != null && !obj[3].equals("")) {
                        int parseInt = Integer.parseInt(obj[3].toString().substring(0, 3));
                        String trFToY2 = CommonFunction.transFenToYuan(obj[3].toString().substring(3, 15));
                        obj[3] = "每笔按比例补" + parseInt + "%，最高补" + trFToY2 + "元";
                    }
                } else {
                    obj[2] = "未知";
                }
            }
            if (obj[4] != null && !obj[4].equals("")) {
                if (obj[4].equals("00")) {
                    obj[4] = "依据商户开启";
                } else if (obj[4].equals("01")) {
                    obj[4] = "依据终端开启";
                } else if (obj[4].equals("02")) {
                    obj[4] = "依据卡BIN开启";
                } else {
                    obj[4] = "未知";
                }
            }
            if (obj[6] != null && !obj[6].equals("")) {
                Date tm = sdf4.parse(obj[6].toString());
                obj[6] = sdf3.format(tm);
            }
            if (obj[7] != null && !obj[7].equals("")) {
                Date tm = sdf4.parse(obj[7].toString());
                obj[7] = sdf3.format(tm);
            }
            if (obj[8] != null && !obj[8].equals("")) {
                obj[8] = Integer.parseInt(obj[8].toString());
            }
            if (obj[9] != null && !obj[9].equals("")) {
                obj[9] = Integer.parseInt(obj[9].toString());
            }
            if (obj[10] != null && !obj[10].equals("")) {
                obj[10] = Integer.parseInt(obj[10].toString());
            }
            if (obj[11] != null && !obj[11].equals("")) {
                Date tm = sdf.parse(obj[11].toString());
                obj[11] = sdf2.format(tm);
            }
            if (obj[12] != null && !obj[12].equals("")) {
                Date tm = sdf.parse(obj[12].toString());
                obj[12] = sdf2.format(tm);
            }
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 
     * //TODO 对账查询
     *
     * @param begin
     * @param request
     * @return
     * @throws Exception
     * @author hanyongqing
     */
    public static Object[] getComparePayBill(int begin, HttpServletRequest request) throws Exception {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();

        String dateStart = request.getParameter("startDate");
        String dateEnd = request.getParameter("endDate");
        String localTimeStart = StringUtils.trim(request.getParameter("localTimeStart"));
        String dateTimeS = dateStart + localTimeStart;
        String localTimeEnd = StringUtils.trim(request.getParameter("localTimeEnd"));
        String dateTimeE = dateEnd + localTimeEnd;

        StringBuffer sb = new StringBuffer();
        StringBuffer whereSql = new StringBuffer();

        //  开始时间
        if (StringUtils.isNotEmpty(dateStart)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                whereSql.append(" AND  TRIM(ACC_DATE) >= '" + dateTimeS + "'");
                //           whereSql2.append(" AND TRIM(chk.TRADE_TIME) >= '" + dateTimeS + "'");
            } else {
                whereSql.append(" AND substr(ACC_DATE,0,8) >= '" + dateStart + "'");
                //         whereSql2.append(" AND substr(chk.TRADE_TIME,0,8) >= '" + dateStart + "'");
            }
        }
        // 结束时间
        if (StringUtils.isNotEmpty(dateEnd)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                whereSql.append(" AND TRIM(ACC_DATE) <= '" + dateTimeE + "'");
                //               whereSql2.append(" AND TRIM(chk.TRADE_TIME) <= '" + dateTimeE + "'");
            } else {
                whereSql.append(" AND substr(ACC_DATE,0,8) <= '" + dateEnd + "'");
                //              whereSql2.append(" AND  substr(chk.TRADE_TIME,0,8) <= '" + dateEnd + "'");
            }
        }

        //    whereSql.append(" and  tmp.Agr_Br in " + orgArrStr);
        sb.append(" select ACC_DATE,FILE_NAME,STEP||'.'||DESCRIPTION as STEPS,LAST_TIME from checkacc_file where 1 = 1 ");
        sb.append(whereSql);
        sb.append(" order by ACC_DATE desc,LAST_TIME desc ");
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String countSql = "SELECT COUNT(1) FROM (" + sb + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf2Cp = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfCp = new SimpleDateFormat("yyyyMMdd");
        for (Object[] objArr : dataList) {
            if (objArr[0] != null && !objArr[0].equals("")) {
                Date tm = sdfCp.parse(objArr[0].toString());
                objArr[0] = sdf2Cp.format(tm);
            }
            if (objArr[3] != null && !objArr[3].equals("")) {
                Date tm = sdf.parse(objArr[3].toString());
                objArr[3] = sdf1.format(tm);
            }
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 
     * //TODO 二维码不平账查询
     *
     * @param begin
     * @param request
     * @return
     * @author hanyongqing
     */
    public static Object[] getPayBillInfo(int begin, HttpServletRequest request) throws Exception {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();

        String dateStart = request.getParameter("startDate");
        String dateEnd = request.getParameter("endDate");
        String localTimeStart = StringUtils.trim(request.getParameter("localTimeStart"));
        String dateTimeS = dateStart + localTimeStart;
        String localTimeEnd = StringUtils.trim(request.getParameter("localTimeEnd"));
        String dateTimeE = dateEnd + localTimeEnd;

        String _ylMchntNo = request.getParameter("ylMchntNo");
        String _cMchntNo = request.getParameter("cMchntNo");
        String _orderInfo = request.getParameter("orderInfo");
        StringBuffer sb = new StringBuffer();
        //     StringBuffer sb2 = new StringBuffer();
        StringBuffer whereSql = new StringBuffer(" where 1 = 1 ");
        //    StringBuffer whereSql2 = new StringBuffer(" INFO_ID is null ");
        // 商户号
        if (StringUtils.isNotEmpty(_ylMchntNo)) {
            whereSql.append(" and trim(MCHNT_ID) like '%" + _ylMchntNo + "%' ");
        }
        // 商户订单号
        if (StringUtils.isNotEmpty(_cMchntNo)) {
            whereSql.append(" and trim(MCHNT_ORDER_NO) like '%" + _cMchntNo + "%' ");
            //    whereSql2.append(" and trim(chk.INFO_ID) like '%" + _orderInfo + "%' ");
        }
        // 扫码付平台订单号
        if (StringUtils.isNotEmpty(_orderInfo)) {
            whereSql.append(" and trim(YS_ORDER_NO) like '%" + _orderInfo + "%' ");
        }

        //  开始时间
        if (StringUtils.isNotEmpty(dateStart)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                whereSql.append(" AND  TRIM(TRADE_TIME) >= '" + dateTimeS + "'");
                //           whereSql2.append(" AND TRIM(chk.TRADE_TIME) >= '" + dateTimeS + "'");
            } else {
                whereSql.append(" AND substr(TRADE_TIME,0,8) >= '" + dateStart + "'");
                //         whereSql2.append(" AND substr(chk.TRADE_TIME,0,8) >= '" + dateStart + "'");
            }
        }
        // 结束时间
        if (StringUtils.isNotEmpty(dateEnd)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                whereSql.append(" AND TRIM(TRADE_TIME) <= '" + dateTimeE + "'");
                //               whereSql2.append(" AND TRIM(chk.TRADE_TIME) <= '" + dateTimeE + "'");
            } else {
                whereSql.append(" AND substr(TRADE_TIME,0,8) <= '" + dateEnd + "'");
                //              whereSql2.append(" AND  substr(chk.TRADE_TIME,0,8) <= '" + dateEnd + "'");
            }
        }

        //       whereSql.append(" and  tmp.Agr_Br in " + orgArrStr);
        sb.append(" select TRADE_TIME,MCHNT_ID,MCHNT_ORDER_NO,YS_ORDER_NO,PAY_TYPE,ORDER_FEE,CHECK_RESULT  from checkacc_result ");
        //                        + "join ( select C_MCHNT_NO from ( select C_MCHNT_NO from tbl_mcht_base_inf  join t_Cmchnt_Map on MAPPING_MCHNTCDTWO = YL_MCHNT_NO  where AGR_BR in "
        //                        + orgArrStr + " ) tbl group by C_MCHNT_NO ) on C_MCHNT_NO = MCHNT_ID ");

        //  + "chk left join tbl_mcht_base_inf tmp on chk.MCHNT_ID = tmp.MAPPING_MCHNTCDTWO  ");
        sb.append(whereSql);
        sb.append(" order by TRADE_TIME desc ");
        //        sb.append(
        //                " select INFO_ID,PAYTYPE,ORDER_TIME,ORDER_FEE1,PAY_TYPE,TRADE_TIME,ORDER_FEE2,MCHNT_ORDER_NO,CHECK_RESULT,YL_MCHNT_NO,MCHT_NM,YL_TERM_NO,C_MCHNT_NO,BRH_NAME  "
        //                        + " from  ( select chk.INFO_ID,case when chk.NOTICE_PAY_TYPE = '0' then '微信' when chk.NOTICE_PAY_TYPE = '1' then '支付宝' ELSE '' END as PAYTYPE , chk.ORDER_TIME,chk.ORDER_FEE1,chk.PAY_TYPE,chk.TRADE_TIME,chk.ORDER_FEE2,chk.MCHNT_ORDER_NO,chk.CHECK_RESULT,chk.YL_MCHNT_NO,mcht.MCHT_NM,chk.YL_TERM_NO,chk.C_MCHNT_NO,brh.BRH_NAME "
        //                        + " from CHECKACC_RESULT chk left join tbl_mcht_base_inf mcht on mcht.MAPPING_MCHNTCDTWO = chk.YL_MCHNT_NO left join tbl_brh_info brh on mcht.AGR_BR = brh.BRH_ID where  ");
        //        sb.append(" (( " + whereSql + " ) or ( " + whereSql2 + " )) ");
        //        sb.append("   ) tbl   order by case when tbl.ORDER_TIME is not null then tbl.ORDER_TIME else tbl.TRADE_TIME end  desc   ");

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String countSql = "SELECT COUNT(1) FROM (" + sb + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        //      String count2 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql2);
        //        for (Object[] obj : dataList2) {
        //            dataList.add(obj);
        //        }
        SimpleDateFormat sdf2Cp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfCp = new SimpleDateFormat("yyyyMMddHHmmss");
        for (Object[] objArr : dataList) {
            if (objArr[0] != null && !objArr[0].equals("")) {
                Date tm = sdfCp.parse(objArr[0].toString());
                objArr[0] = sdf2Cp.format(tm);
            }
            if (objArr[5] != null && !objArr[5].equals("")) {
                objArr[5] = CommonFunction.transFenToYuan(objArr[5].toString());
            } else {
                objArr[5] = CommonFunction.transFenToYuan("");
            }
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 
     * //TODO 扫码支付信息查询
     *
     * @param begin
     * @param request
     * @return
     * @author hanyongqing
     */
    public static Object[] getPosCodeInfo(int begin, HttpServletRequest request) throws Exception {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String _ylMchntNo = request.getParameter("ylMchntNo");
        String _ylTermNo = request.getParameter("ylTermNo");
        String _termFixNo = request.getParameter("termFixNo");

        StringBuffer sb = new StringBuffer();
        StringBuffer whereSql = new StringBuffer();

        // 商户号
        if (StringUtils.isNotEmpty(_ylMchntNo)) {
            whereSql.append(" and trim(map.Yl_MCHNT_NO) like '%" + _ylMchntNo + "%' ");
        }
        // 终端号
        if (StringUtils.isNotEmpty(_ylTermNo)) {
            whereSql.append(" and trim(map.Yl_TERM_NO) like '%" + _ylTermNo + "%' ");
        }
        // 硬件编号
        if (StringUtils.isNotEmpty(_termFixNo)) {
            whereSql.append(" and trim(map.TERM_FIX_NO) like '%" + _termFixNo + "%' ");
        }
        whereSql.append(" and base.AGR_BR in " + orgArrStr);
        // case when map.FLAG ='0' then '停用' when map.FLAG = '1' then '启用'  else '停用' end as FLAG
        sb.append(
                " select base.MCHT_NM,map.Yl_MCHNT_NO,map.Yl_TERM_NO,map.C_MCHNT_NO,map.TERM_FIX_NO,map.SCANCODEFLAG,brh.C_BRH_NAME,"
                        + "map.C_KEY,map.CIPHER,map.BUSS_ID,map.RESV1,map.CERTPATH,map.BRANCHCODE,map.FLAG,map.VALID_START_DATE,map.VALID_END_DATE,map.ID "
                        + "from t_cmchnt_map map left join tbl_mcht_base_inf base on map.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO "
                        + "left join T_BRH_DATADIC brh on map.C_MCHNT_BRH=brh.C_BRH_ID where 1 = 1  ");
        sb.append(whereSql);
        sb.append("  order by map.Yl_MCHNT_NO,map.Yl_TERM_NO ");

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String countSql = "SELECT COUNT(1) FROM (" + sb + ")";
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        for (Object[] objArr : dataList) {
            if ("0".equals(objArr[5].toString())) {
                objArr[5] = "扫码(主被扫)";
            } else if ("1".equals(objArr[5].toString())) {
                objArr[5] = "主扫方式";
            } else if ("2".equals(objArr[5].toString())) {
                objArr[5] = "被扫方式";
            }
            if ("0".equals(objArr[13].toString())) {
                objArr[13] = "停用";
            } else if ("1".equals(objArr[13].toString())) {
                objArr[13] = "启用";
            }
            if (objArr[14] != "" && objArr[14] != null) {
                Date tm = sdf.parse(objArr[14].toString());
                objArr[14] = sdf2.format(tm);
            }
            if (objArr[15] != "" && objArr[15] != null) {
                Date tm = sdf.parse(objArr[15].toString());
                objArr[15] = sdf2.format(tm);
            }
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 
     * //TODO 交易统计功能，银联卡的交易统计信息和
     *
     * @param begin
     * @param request
     * @return
     * @author hanyongqing
     */
    public static Object[] getStatRepInfo2(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String _startDate = request.getParameter("startDate");
        String _endDate = request.getParameter("endDate");
        String _merchantNo = request.getParameter("merchantNo");
        String _brhId = request.getParameter("brhId");

        StringBuffer sb = new StringBuffer();
        StringBuffer whereSql = new StringBuffer();
        StringBuffer whereSql2 = new StringBuffer();
        // 开始时间
        if (StringUtils.isNotEmpty(_startDate)) {
            whereSql.append(" and trim(txn.INST_DATE) >= '" + _startDate + "000000' ");
        }
        // 结束时间
        if (StringUtils.isNotEmpty(_endDate)) {
            whereSql.append(" and trim(txn.INST_DATE) <= '" + _endDate + "235959' ");
        }
        // 
        if (StringUtils.isNotEmpty(_merchantNo)) {
            whereSql2.append(" and trim(tbl.MCHT_NO) like '%" + _merchantNo + "%' ");
        }
        // 结束时间
        if (StringUtils.isNotEmpty(_brhId)) {
            whereSql2.append(" and trim(tbl.BRH_ID) like '%" + _brhId + "%' ");
        }
        sb.append(
                "  select '' as BRH_ID,'' as BRH_NAME,'' as MCHT_NO,'' as MCHT_NM,count(1) as NUM,sum(AMT_TRANS) as AMT_TRANS,sum(AMT_SETTLMT) as AMT_SETTLMT,'' as PREFEE,'' as SETTLE_ACCT_SND, '' as SETTLE_BANK_NO_SND,'' as SETTLE_BANK_NM_SND,'' as SETTLE_ACCT_NM_SND from "
                        + " ( select tbl.BRH_ID,tbl.BRH_NAME,to_number(tbl.AMT_TRANS) as AMT_TRANS ,to_number(tbl.AMT_SETTLMT) as AMT_SETTLMT,tbl.MCHT_NO,tbl.MCHT_NM from ( "
                        + "   select txn.INST_DATE,txn.TRANS_TYPE,txn.REVSAL_FLAG,txn.RESP_CODE,(case when txn.AMT_TRANS is null then 0 else to_number(txn.AMT_TRANS) end) as AMT_TRANS, (case when txn.AMT_SETTLMT is null then 0 else to_number(txn.AMT_SETTLMT) end) as AMT_SETTLMT ,txn.CARD_ACCP_TERM_ID,txn.CARD_ACCP_ID, "
                        + "  term.MAPPING_MCHNTCDTWO,term.MAPPING_TERMIDTWO,base.MCHT_NO,base.MCHT_NM,brh.BRH_ID,brh.BRH_NAME  "
                        + "  from tbl_n_txn txn left join tbl_term_inf term on txn.CARD_ACCP_TERM_ID = term.MAPPING_TERMIDTWO  "
                        + "  left join tbl_mcht_base_inf base on term.MAPPING_MCHNTCDTWO = base.mapping_mchntcdtwo  "
                        + "  left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID  " + "  where brh.BRH_ID in " + orgArrStr
                        + " and txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
        sb.append(whereSql);
        sb.append(" ) tbl where 1 = 1 ");
        sb.append(whereSql2);
        sb.append(" ) tb   ");
        //        String countSql = "SELECT COUNT(1) FROM (" + sb + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        //        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
        //                countSql);
        for (Object[] objArr : dataList) {
            //     objArr[7] = (Integer.parseInt(objArr[6].toString()) - Integer.parseInt(objArr[5].toString())) / 100.0;
            objArr[7] = CommonFunction.transFenToYuan((Integer.parseInt(objArr[6].toString()) - Integer.parseInt(objArr[5].toString())) + "");
            //      objArr[5] = Integer.parseInt(objArr[5].toString()) / 100.0;
            objArr[5] = CommonFunction.transFenToYuan(objArr[5].toString());
            objArr[6] = CommonFunction.transFenToYuan(objArr[6].toString());
            //        objArr[6] = Integer.parseInt(objArr[6].toString()) / 100.0;
        }
        ret[0] = dataList;
        ret[1] = 1;
        return ret;
    }

    /**
     * 
     * //TODO 交易统计功能，统计银联卡相关信息
     *
     * @param begin
     * @param request
     * @return
     * @author hanyongqing
     */
    public static Object[] getStatRepInfo(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String _startDate = request.getParameter("startDate");
        String _endDate = request.getParameter("endDate");
        String _merchantNo = request.getParameter("merchantNo");
        String _brhId = request.getParameter("brhId");

        StringBuffer sb = new StringBuffer();
        StringBuffer whereSql = new StringBuffer();
        StringBuffer whereSql2 = new StringBuffer();
        // 开始时间
        if (StringUtils.isNotEmpty(_startDate)) {
            whereSql.append(" and trim(txn.INST_DATE) >= '" + _startDate + "000000' ");
        }
        // 结束时间
        if (StringUtils.isNotEmpty(_endDate)) {
            whereSql.append(" and trim(txn.INST_DATE) <= '" + _endDate + "235959' ");
        }
        // 
        if (StringUtils.isNotEmpty(_merchantNo)) {
            whereSql2.append(" and trim(tbl.MCHT_NO) like '%" + _merchantNo + "%' ");
        }
        // 结束时间
        if (StringUtils.isNotEmpty(_brhId)) {
            whereSql2.append(" and trim(tbl.BRH_ID) like '%" + _brhId + "%' ");
        }
        sb.append(
                "select BRH_ID,BRH_NAME,MCHT_NO,MCHT_NM,count(1) as NUM,sum(AMT_TRANS) as AMT_TRANS,sum(AMT_SETTLMT) as AMT_SETTLMT,'' as PREFEE,SETTLE_ACCT_SND,SETTLE_BANK_NO_SND,SETTLE_BANK_NM_SND,SETTLE_ACCT_NM_SND from "
                        + " ( select tbl.BRH_ID,tbl.BRH_NAME,to_number(tbl.AMT_TRANS) as AMT_TRANS ,to_number(tbl.AMT_SETTLMT) as AMT_SETTLMT,tbl.MCHT_NO,tbl.MCHT_NM,inf.SETTLE_ACCT_SND,inf.SETTLE_BANK_NO_SND,inf.SETTLE_BANK_NM_SND,inf.SETTLE_ACCT_NM_SND from ( "
                        + "   select txn.INST_DATE,txn.TRANS_TYPE,txn.REVSAL_FLAG,txn.RESP_CODE,(case when txn.AMT_TRANS is null then 0 else to_number(txn.AMT_TRANS) end) as AMT_TRANS, (case when txn.AMT_SETTLMT is null then 0 else to_number(txn.AMT_SETTLMT) end) as AMT_SETTLMT ,txn.CARD_ACCP_TERM_ID,txn.CARD_ACCP_ID, "
                        + "  term.MAPPING_MCHNTCDTWO,term.MAPPING_TERMIDTWO,base.MCHT_NO,base.MCHT_NM,brh.BRH_ID,brh.BRH_NAME  "
                        + "  from tbl_n_txn txn left join tbl_term_inf term on txn.CARD_ACCP_TERM_ID = term.MAPPING_TERMIDTWO  "
                        + "  left join tbl_mcht_base_inf base on term.MAPPING_MCHNTCDTWO = base.mapping_mchntcdtwo  "
                        + "  left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID  " + "  where brh.BRH_ID in " + orgArrStr
                        + " and txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
        sb.append(whereSql);
        sb.append(" ) tbl left join tbl_mcht_settle_inf inf on tbl.MCHT_NO = inf.MCHT_NO where 1 = 1 ");
        sb.append(whereSql2);
        sb.append(
                " ) tb group by BRH_ID,BRH_NAME,MCHT_NO,MCHT_NM,SETTLE_ACCT_SND,SETTLE_BANK_NO_SND,SETTLE_BANK_NM_SND,SETTLE_ACCT_NM_SND order by BRH_ID ");
        String countSql = "SELECT COUNT(1) FROM (" + sb + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        for (Object[] objArr : dataList) {
            //     objArr[7] = (Integer.parseInt(objArr[6].toString()) - Integer.parseInt(objArr[5].toString())) / 100.0;
            objArr[7] = CommonFunction.transFenToYuan((Integer.parseInt(objArr[5].toString()) - Integer.parseInt(objArr[6].toString())) + "");
            //      objArr[5] = Integer.parseInt(objArr[5].toString()) / 100.0;
            objArr[5] = CommonFunction.transFenToYuan(objArr[5].toString());
            objArr[6] = CommonFunction.transFenToYuan(objArr[6].toString());
            //        objArr[6] = Integer.parseInt(objArr[6].toString()) / 100.0;
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 
     * //TODO 用于查询菜品明细信息
     *
     * @param begin
     * @param request
     * @return
     * @author hanyongqing
     */
    public static Object[] getVegDetailInfo(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return null;
        }
        Object[] ret = new Object[2];
        String dateStart = request.getParameter("startDate");
        String dateEnd = request.getParameter("endDate");
        String localTimeStart = StringUtils.trim(request.getParameter("localTimeStart"));
        String dateTimeS = dateStart + localTimeStart;
        String localTimeEnd = StringUtils.trim(request.getParameter("localTimeEnd"));
        String dateTimeE = dateEnd + localTimeEnd;

        String _mchntNo = request.getParameter("mchntNo");
        String _termNo = request.getParameter("termNo");
        String _repNo = request.getParameter("repNo");
        StringBuffer sb = new StringBuffer();
        StringBuffer whereSql = new StringBuffer();
        StringBuffer whereSql2 = new StringBuffer();
        // 商户号
        if (StringUtils.isNotEmpty(_mchntNo)) {
            whereSql.append(" and trim(vege.MCHNT_NO) like '%" + _mchntNo + "%'");
        }
        // 终端号
        if (StringUtils.isNotEmpty(_termNo)) {
            whereSql.append(" and trim(vege.TERM_NO) like '%" + _termNo + "%'");
        }
        // 系统流水号
        if (StringUtils.isNotEmpty(_repNo)) {
            whereSql2.append(" and trim(tb.REPNUM) like '%" + _repNo + "%'");
        }
        //开始时间
        if (StringUtils.isNotEmpty(dateStart)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                whereSql.append(" AND  TRIM(vege.TRADE_TIME) >= '" + dateTimeS + "'");
            } else {
                whereSql.append(" AND substr(vege.TRADE_TIME,0,8) >= '" + dateStart + "'");
            }
        }
        // 结束时间
        if (StringUtils.isNotEmpty(dateEnd)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                whereSql.append(" AND TRIM(vege.TRADE_TIME) <= '" + dateTimeE + "'");
            } else {
                whereSql.append(" AND substr(vege.TRADE_TIME,0,8) <= '" + dateEnd + "'");
            }
        }
        whereSql.append(" and base.AGR_BR in " + operator.getBrhBelowId());

        sb.append(
                " select TRADE_TIME,REPNUM,MCHT_NM,MCHNT_NO,TERM_NO,VEGE_CODE,VEGE_NAME,WEIGHT,AMOUNT,PRICE,PAYTYPE,AGR_BR,BRH_NAME,REPEAT_FLAG,UPLOAD_TIME "
                        + "  from ( select tbl.TRADE_TIME,case when tbl.CUP_SSN is not null then tbl.CUP_SSN when tbl.TERM_ORDER_ID is not null then tbl.TERM_ORDER_ID else '' end as REPNUM,case when tbl.CUP_SSN is not null then '银联卡支付' when tbl.TERM_ORDER_ID is not null then '扫码支付' else '' end as PAYTYPE,tbl.MCHT_NM,tbl.MCHNT_NO,tbl.TERM_NO,tbl.VEGE_CODE,tbl.VEGE_NAME,tbl.WEIGHT,tbl.AMOUNT,tbl.PRICE,tbl.AGR_BR,brh.BRH_NAME,tbl.REPEAT_FLAG,tbl.UPLOAD_TIME "
                        + " from ( select vege.MCHNT_NO,vege.TERM_NO,vege.TRADE_TIME,vege.VEGE_CODE,vege.VEGE_NAME,vege.WEIGHT,vege.PRICE,vege.AMOUNT,vege.UPLOAD_TIME,vege.REPEAT_FLAG,base.MCHT_NM,base.AGR_BR,(select CUP_SSN from tbl_n_txn where trim(KEY_RSP)= vege.KEY_RSP ) as CUP_SSN, (select TERM_ORDER_ID from t_corderinfo  where INFO_ID = vege.KEY_RSP ) as TERM_ORDER_ID from vege_detail vege "
                        + " left join tbl_mcht_base_inf base on vege.MCHNT_NO = base.MAPPING_MCHNTCDTWO where 1=1 ");
        sb.append(whereSql);
        sb.append("  ) tbl left join tbl_brh_info brh on tbl.AGR_BR = brh.BRH_ID ) tb where 1=1 ");
        sb.append(whereSql2);
        sb.append(" order by tb.TRADE_TIME desc ");
        String countSql = "SELECT COUNT(1) FROM (" + sb + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        for (int i = 0; i < dataList.size(); i++) {
            Object[] obj = dataList.get(i);
            try {
                if (obj[0] != null && !obj[0].equals("")) {
                    Date tm = sdf.parse(obj[0].toString());
                    obj[0] = sdf2.format(tm);
                }
                if (obj[7] != null && !obj[7].equals("")) {
                    obj[7] = CommonFunction.transGToKg(obj[7].toString());
                } else {
                    obj[7] = CommonFunction.transGToKg("");
                }
                if (obj[8] != null && !obj[8].equals("")) {
                    obj[8] = CommonFunction.transFenToYuan(obj[8].toString());
                } else {
                    obj[8] = CommonFunction.transFenToYuan("");
                }
                //
                if (obj[9] != null || !obj[9].equals("")) {
                    obj[9] = CommonFunction.transFkgToYkg(obj[9].toString());
                } else {
                    obj[9] = CommonFunction.transFkgToYkg("");
                }
                if (obj[13] != null && obj[13] != "") {
                    String string = obj[13].toString();
                    if (string.equals("1")) {
                        obj[13] = "重复上传";
                    } else if (string.equals("0")) {
                        obj[13] = "上传";
                    } else {
                        obj[13] = "未知";
                    }
                }
                if (obj[14] != null && obj[14] != "") {
                    Date tm = sdf.parse(obj[14].toString());
                    obj[14] = sdf2.format(tm);
                }
                dataList.set(i, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 
     * //TODO 菜品编码表查询信息，不分页查询，最多显示10000条
     *
     * @param begin
     * @param request
     * @return
     * @author hanyongqing
     */
    public static Object[] getVegCodeInfo2(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        String _vegCode = request.getParameter("vegCode");
        String _vegName = request.getParameter("vegName");
        StringBuffer sb = new StringBuffer();
        StringBuffer whereSql = new StringBuffer();
        //菜品编码
        if (StringUtils.isNotEmpty(_vegCode)) {
            whereSql.append(" and trim(VEGE_CODE) like '%" + _vegCode + "%'");
        }
        // 菜品名称
        if (StringUtils.isNotEmpty(_vegName)) {
            whereSql.append(" and trim(VEGE_NAME) like '%" + _vegName + "%'");
        }
        sb.append("select VEGE_CODE,VEGE_NAME from vege_code_base where 1=1 ");
        sb.append(whereSql);
        sb.append(" order by VEGE_CODE");
        String countSql = "SELECT COUNT(1) FROM (" + sb + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, 10000);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 
     * //TODO 显示终端菜品映射表信息
     *
     * @param begin
     * @param request
     * @return
     * @author hanyongqing
     */
    public static Object[] getVegMappingInfo(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        String _mchntNo = request.getParameter("mchntNo");
        String _termNo = request.getParameter("termNo");
        String _vegeCode = request.getParameter("vegeCode");

        StringBuffer whereSql = new StringBuffer();
        //商户号
        if (StringUtils.isNotEmpty(_mchntNo)) {
            whereSql.append(" and  trim(MCHNT_NO) like '%" + _mchntNo + "%'");
        }
        // 终端号
        if (StringUtils.isNotEmpty(_termNo)) {
            whereSql.append("  and trim(TERM_NO) like '%" + _termNo + "%'");
        }
        // 菜品编码
        if (StringUtils.isNotEmpty(_vegeCode)) {
            whereSql.append(" and trim(mcht.VEGE_CODE) like '%" + _vegeCode + "%'");
        }

        StringBuffer sb = new StringBuffer();

        sb.append(
                " select MCHNT_NO,TERM_NO,KEY_CODE,mcht.VEGE_CODE,base.VEGE_NAME from VEGE_CODE_FOR_MCHT mcht left join VEGE_CODE_BASE base on mcht.VEGE_CODE = base.VEGE_CODE where 1 = 1 ");
        sb.append(whereSql);
        sb.append(" order by MCHNT_NO,TERM_NO ");
        String countSql = "SELECT COUNT(1) FROM (" + sb + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 菜品编码表查询信息
     * //TODO 添加方法功能描述
     *
     * @param begin
     * @param request
     * @return
     * @author hanyongqing
     */
    public static Object[] getVegCodeInfo(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        String _vegCode = request.getParameter("vegCode");
        String _vegName = request.getParameter("vegName");
        StringBuffer sb = new StringBuffer();
        StringBuffer whereSql = new StringBuffer();
        //菜品编码
        if (StringUtils.isNotEmpty(_vegCode)) {
            whereSql.append(" and trim(VEGE_CODE) like '%" + _vegCode + "%'");
        }
        // 菜品名称
        if (StringUtils.isNotEmpty(_vegName)) {
            whereSql.append(" and trim(VEGE_NAME) like '%" + _vegName + "%'");
        }
        sb.append("select VEGE_CODE,VEGE_NAME from vege_code_base where 1=1 ");
        sb.append(whereSql);
        sb.append(" order by VEGE_CODE");
        String countSql = "SELECT COUNT(1) FROM (" + sb + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询机构信息
     */
    @SuppressWarnings("unchecked")
    public static Object[] getBrhInfoBelow(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" WHERE 1=1 ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and BRH_ID IN " + operator.getBrhBelowId());
        }
        if (isNotEmpty(request.getParameter("brhId"))) {
            whereSql.append(" AND BRH_ID = '" + request.getParameter("brhId") + "' ");
        }
        if (isNotEmpty(request.getParameter("brhName"))) {
            whereSql.append(" AND BRH_NAME  like '%" + request.getParameter("brhName") + "%' ");
        }
        String sql = "SELECT BRH_ID,BRH_LEVEL,UP_BRH_ID,BRH_NAME,BRH_ADDR,BRH_TEL_NO,POST_CD," + "BRH_CONT_NAME,CUP_BRH_ID,RESV1 "
                + "FROM TBL_BRH_INFO " + whereSql.toString() + " order by BRH_LEVEL,BRH_ID";
        String countSql = "SELECT COUNT(1) FROM TBL_BRH_INFO " + whereSql.toString();
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询财务POS账户信息
     */
    @SuppressWarnings("unchecked")
    public static Object[] getPosCardInfo(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        String sql = "select card_no,mcht_no,term_id,brh_id,holder_name,holder_id,holder_tel,stat,sms_quota,start_time,stop_time from tbl_pos_card_inf";
        String countSql = "select count(*) from tbl_pos_card_inf";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询财务POS账户信息2
     */
    @SuppressWarnings("unchecked")
    public static Object[] getComCardInfo(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        String sql = "select card_no,mcht_no,term_id,brh_id,holder_name,holder_id,holder_tel,stat,sms_quota,start_time,stop_time from tbl_com_card_inf";
        String countSql = "select count(*) from tbl_pos_card_inf";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 云SHOP
     */
    @SuppressWarnings("unchecked")
    public static Object[] getiPosInf(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        // String sql =
        // "select pos_mch,mch_number,outmch_number,inmch_number,fee_code from tbl_i_pos_inf";
        String sql = "select pos_mch,mch_number,pos_stage,outmch_number,inmch_number,fee_code from tbl_i_pos_inf";
        String countSql = "select count(*) from tbl_i_pos_inf";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 分期信息
     */
    @SuppressWarnings("unchecked")
    public static Object[] getDivInfo(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        // String sql =
        // "select pos_mch,mch_number,outmch_number,inmch_number,fee_code from tbl_i_pos_inf";
        StringBuffer whereSql = new StringBuffer();
        if (isNotEmpty(request.getParameter("feeCode"))) {
            whereSql.append(" where fee_code='" + request.getParameter("feeCode") + "' ");
        }
        String sql = "select mcht_id,div_no,product_code,inproduct_code,fee_code from tbl_div_mcht" + whereSql;
        String countSql = "select count(*) from tbl_div_mcht" + whereSql;
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询操作员信息
     */
    @SuppressWarnings("unchecked")
    public static Object[] getOprInfoWithBrh(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        String sql = "SELECT OPR_ID,BRH_ID,OPR_NAME,OPR_GENDER,REGISTER_DT,OPR_TEL,OPR_MOBILE FROM TBL_OPR_INFO WHERE BRH_ID = '"
                + request.getParameter("brhId") + "'";
        String countSql = "SELECT COUNT(*) FROM TBL_OPR_INFO WHERE BRH_ID = '" + request.getParameter("brhId") + "'";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询当前操作员下属操作员信息
     */
    @SuppressWarnings("unchecked")
    public static Object[] getOprInfo(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" WHERE 1=1 ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and BRH_ID IN " + operator.getBrhBelowId());
        }
        if (isNotEmpty(request.getParameter("oprId"))) {
            whereSql.append(" AND OPR_ID like '%" + request.getParameter("oprId") + "%' ");
        }
        if (isNotEmpty(request.getParameter("brhId"))) {
            whereSql.append(" AND BRH_ID = '" + request.getParameter("brhId") + "' ");
        }
        if (isNotEmpty(request.getParameter("startDate"))) {
            whereSql.append(" AND REGISTER_DT >= '" + request.getParameter("startDate") + "' ");
        }
        if (isNotEmpty(request.getParameter("endDate"))) {
            whereSql.append(" AND REGISTER_DT <= '" + request.getParameter("endDate") + "' ");
        }

        String sql = "SELECT OPR_ID,BRH_ID,OPR_DEGREE,OPR_NAME,OPR_GENDER,REGISTER_DT,OPR_TEL," + "OPR_MOBILE,PWD_OUT_DATE,OPR_STA FROM TBL_OPR_INFO "
                + whereSql;

        String countSql = "SELECT COUNT(*) FROM TBL_OPR_INFO " + whereSql;

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询交易日志信息
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTxnInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        StringBuffer whereSql = new StringBuffer();

        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and b.BRH_ID IN " + operator.getBrhBelowId());
        }

        if (!StringUtil.isNull(request.getParameter("oprNo"))) {
            whereSql.append(" and a.OPR_ID = '" + request.getParameter("oprNo") + "' ");
        }
        if (!StringUtil.isNull(request.getParameter("startDate"))) {
            whereSql.append(" and TXN_DATE >= '" + request.getParameter("startDate") + "' ");
        }
        if (!StringUtil.isNull(request.getParameter("endDate"))) {
            whereSql.append(" and TXN_DATE <= '" + request.getParameter("endDate") + "' ");
        }
        if (!StringUtil.isNull(request.getParameter("brhId"))) {
            whereSql.append(" and b.BRH_ID = '" + request.getParameter("brhId") + "' ");
        }
        if (!StringUtil.isNull(request.getParameter("conTxn"))) {
            whereSql.append(" and TXN_CODE = '" + request.getParameter("conTxn") + "' ");
        }

        String sql = "SELECT a.OPR_ID,TXN_DATE,TXN_TIME,TXN_NAME,TXN_STA,ERR_MSG " + "FROM TBL_TXN_INFO a , TBL_OPR_INFO b WHERE a.OPR_ID = b.OPR_ID "
                + whereSql.toString() + " ORDER BY TXN_DATE DESC,TXN_TIME DESC";

        String countSql = "SELECT COUNT(1) " + "FROM TBL_TXN_INFO a , TBL_OPR_INFO b WHERE a.OPR_ID = b.OPR_ID " + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询集团商户信息
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getGroupMchtInf(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer();

        whereSql.append(" WHERE 1=1 ");
        if (isNotEmpty(request.getParameter("groupMchtCd"))) {
            whereSql.append(" AND group_mcht_cd = '" + request.getParameter("groupMchtCd") + "' ");
        }
        if (isNotEmpty(request.getParameter("grouName"))) {
            whereSql.append(" AND group_name = '" + request.getParameter("grouName") + "' ");
        }

        String order = "ORDER BY a.GROUP_MCHT_CD";
        String sql = "SELECT a.GROUP_MCHT_CD,a.GROUP_NAME,a.GROUP_TYPE,a.REG_FUND,"
                + "a.BUS_RANGE,a.MCHT_PERSON,a.CONTACT_ADDR FROM TBL_GROUP_MCHT_INF a" + whereSql.toString() + order;
        String countSql = "SELECT COUNT(*) FROM TBL_GROUP_MCHT_INF a" + whereSql.toString();
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询商户入网信息
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntNet(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" WHERE a.brh_id = b.brh_id ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and a.last_opr_id IN " + operator.getBrhBelowId());
        }
        String operate = request.getParameter("operate");
        if ("check".equals(operate)) {
            whereSql.append(" AND a.status IN ('1','3','5','7')");
        } else {
            whereSql.append(" AND a.status IN ('0','2','4','6')");
        }
        if (isNotEmpty(request.getParameter("id"))) {
            whereSql.append(" AND id = '" + request.getParameter("id") + "' ");
        }
        if (isNotEmpty(request.getParameter("mchtNm"))) {
            whereSql.append(" AND legal_nm = '" + request.getParameter("mchtNm") + "' ");
        }
        String order = "ORDER BY create_time";
        String sql = "SELECT a.ID,b.BRH_NAME,a.STATUS,a.MCHT_NM,a.LEGAL_NM,create_time FROM TBL_MCHT_NET_TMP a,TBL_BRH_INFO b " + whereSql.toString()
                + order;
        String countSql = "SELECT COUNT(*) FROM TBL_MCHT_NET_TMP a,TBL_BRH_INFO b " + whereSql.toString();
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户限额维护信息查询
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchtFeeInf(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        StringBuffer whereSql = new StringBuffer();
        StringBuffer whereSql1 = new StringBuffer();
        whereSql.append("AND a.mcht_cd=b.MCHT_NO  ");

        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and sign_Inst_Id IN  " + operator.getBrhBelowId());
        }
        if (isNotEmpty(request.getParameter("mchntId"))) {
            whereSql.append(" AND MCHT_CD = '" + request.getParameter("mchntId") + "'");
        }

        String sql = "select a.mcht_cd, b.MCHT_NM,a.txn_num, a.card_type, a.day_num,a.day_amt, "
                + "a.day_single,a.mon_num,a.mon_amt from cst_mcht_fee_inf a,TBL_MCHT_BASE_INF_TMP b WHERE b.MCHT_STATUS='0' ";
        // System.out.println(sql);
        String countSql = "SELECT COUNT(*) FROM (" + sql + whereSql.toString() + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql + whereSql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户分店查询
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getrecUpdTs(int begin, HttpServletRequest request) {
        String whereSql = " WHERE 1=1 ";
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (isNotEmpty(request.getParameter("mchtCd"))) {
            whereSql += " AND MCHT_CD = '" + request.getParameter("mchtCd") + "' ";
        }
        if (isNotEmpty(request.getParameter("branchCd"))) {
            whereSql += " AND BRANCH_CD = '" + request.getParameter("branchCd") + "' ";
        }
        if (isNotEmpty(request.getParameter("branchNm"))) {
            whereSql += " AND BRANCH_NM like '" + request.getParameter("branchNm") + "' ";
        }
        Object[] ret = new Object[2];
        String sql = "select  a.MCHT_CD,a.BRANCH_CD,a.BRANCH_NM,a.BRANCH_AREA,a.BRANCH_SVR_LVL,a.BRANCH_CONT_MAN,"
                + "a.BRANCH_TEL,a.BRANCH_Fax,a.BRANCH_NM_EN,a.BRANCH_ADDR,a.CUST_MNGER,a.CUST_MOBILE,a.CUST_TEL,a.OPR_NM,"
                + "a.SIGN_DATE from TBL_MCHT_BRAN_INF a" + whereSql + " order by a.rec_upd_ts";
        String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BRAN_INF " + whereSql;

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户分店查询
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntMccInfo(int begin, HttpServletRequest request) {
        String whereSql = " WHERE 1=1 ";
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (isNotEmpty(request.getParameter("mchtGrp"))) {
            whereSql += " AND MCHNT_TP_GRP = '" + request.getParameter("mchtGrp") + "' ";
        }
        if (isNotEmpty(request.getParameter("mcc"))) {
            whereSql += " AND MCHNT_TP = '" + request.getParameter("mcc") + "' ";
        }

        Object[] ret = new Object[2];
        String sql = "SELECT MCHNT_TP,MCHNT_TP_GRP,DESCR,REC_ST FROM TBL_INF_MCHNT_TP" + whereSql + " order by MCHNT_TP";
        String countSql = "SELECT COUNT(*) FROM TBL_INF_MCHNT_TP " + whereSql;

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 到真实表中查询正常商户的信息
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntInfoTrue(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        String whereSql = " where 1=1 ";

        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " and sign_Inst_Id IN  " + operator.getBrhBelowId();
        }

        String sql = "SELECT MCHT_NO,MCHT_CN_ABBR,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
                + "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,MCHT_STATUS FROM TBL_MCHT_BASE_INF " + whereSql + " AND MCHT_STATUS ='0' ORDER BY  MCHT_NO";
        String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF " + whereSql + " AND MCHT_STATUS ='0'";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询商户信息
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntInfo(int begin, HttpServletRequest request) {

        String whereSql = " WHERE 1=1 ";
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (isNotEmpty(request.getParameter("mchntId"))) {
            whereSql += " AND MCHT_NO = '" + request.getParameter("mchntId") + "' ";
        }

        String acmchntId = request.getParameter("acmchntId");
        // 收单方商户号
        if (acmchntId != null && !acmchntId.trim().equals("")) {
            whereSql += " AND (MAPPING_MCHNTCDONE like '%" + acmchntId + "%' ";
            whereSql += " or MAPPING_MCHNTCDTWO like '%" + acmchntId + "%') ";
        }
        if (isNotEmpty(request.getParameter("startDate"))) {
            whereSql += " AND substr(REC_CRT_TS,0,8) >= '" + request.getParameter("startDate") + "' ";
        }
        if (isNotEmpty(request.getParameter("endDate"))) {
            whereSql += " AND substr(REC_CRT_TS,0,8) <= '" + request.getParameter("endDate") + "' ";
        }
        if (isNotEmpty(request.getParameter("brhId"))) {
            whereSql += " AND sign_Inst_Id = '" + request.getParameter("brhId") + "' ";
        } else {
            if (!"0000".equals(operator.getOprBrhId())) {
                whereSql += " AND sign_Inst_Id IN " + operator.getBrhBelowId() + " ";
            }

        }
        whereSql += "AND MCHT_STATUS IN ('0','2','4','6') ";
        Object[] ret = new Object[2];

        String sql = "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
                + "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,substr(REC_CRT_TS,0,8),MCHT_STATUS,nvl(B.TERM_COUNT,0),MAPPING_MCHNTTYPEONE,MAPPING_MCHNTCDONE,MAPPING_MCHNTTYPETWO,MAPPING_MCHNTCDTWO  FROM "
                + "(SELECT * FROM TBL_MCHT_BASE_INF " + whereSql + ") A " + "left outer join "
                + "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B " + "ON (A.MCHT_NO = B.MCHT_CD) ORDER BY  MCHT_NO";
        String countSql = "SELECT COUNT(1) FROM (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询商户信息TMP
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntInfoTmp(int begin, HttpServletRequest request) {

        String whereSql = " WHERE 1=1 ";
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (isNotEmpty(request.getParameter("mchntId"))) {
            whereSql += " AND MCHT_NO = '" + request.getParameter("mchntId") + "' ";
        }

        String acmchntId = request.getParameter("acmchntId");
        // 收单方商户号
        if (acmchntId != null && !acmchntId.trim().equals("")) {
            whereSql += " AND (MAPPING_MCHNTCDONE like '%" + acmchntId + "%' ";
            whereSql += " or MAPPING_MCHNTCDTWO like '%" + acmchntId + "%') ";
        }

        if (isNotEmpty(request.getParameter("mchtStatus"))) {
            whereSql += " AND MCHT_STATUS = '" + request.getParameter("mchtStatus") + "' ";
        }
        if (isNotEmpty(request.getParameter("mchtGrp"))) {
            whereSql += " AND MCHT_GRP = '" + request.getParameter("mchtGrp") + "' ";
        }
        if (isNotEmpty(request.getParameter("startDate"))) {
            whereSql += " AND substr(REC_CRT_TS,0,8) >= '" + request.getParameter("startDate") + "' ";
        }
        if (isNotEmpty(request.getParameter("endDate"))) {
            whereSql += " AND substr(REC_CRT_TS,0,8) <= '" + request.getParameter("endDate") + "' ";
        }

        // 换成签约网点
        if (isNotEmpty(request.getParameter("brhId")) && !"0000".equals(request.getParameter("brhId"))) {
            whereSql += " AND AGR_BR IN " + InformationUtil.getBrhGroupString(request.getParameter("brhId")) + " ";
        } else if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " AND AGR_BR IN " + InformationUtil.getBrhGroupString(operator.getOprBrhId()) + " ";
        }
        if (isNotEmpty(request.getParameter("mchtFlag1"))) {
            whereSql += (" AND MCHT_FLAG1 = '" + request.getParameter("mchtFlag1") + "' ");
        }
        if (isNotEmpty(request.getParameter("connType"))) {
            whereSql += (" AND CONN_TYPE = '" + request.getParameter("connType") + "' ");
        }
        if (isNotEmpty(request.getParameter("mchtFlag2"))) {
            whereSql += (" AND MCHT_FLAG1 = '" + request.getParameter("mchtFlag2").substring(0, 1) + "' ");
            whereSql += (" AND MCHT_FLAG2 = '" + request.getParameter("mchtFlag2").substring(1) + "' ");
        }

        if (isNotEmpty(request.getParameter("licenceNo"))) {
            whereSql += (" AND LICENCE_NO like '%" + request.getParameter("licenceNo") + "%' ");
        }
        if (isNotEmpty(request.getParameter("mchtNmShort"))) {
            whereSql += (" AND MCHT_CN_ABBR like '%" + request.getParameter("mchtNmShort") + "%' ");
        }
        whereSql += " order by REC_CRT_TS desc ";// 查看TblMchntInfoConstants
        Object[] ret = new Object[2];

        String sql = "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,LICENCE_NO,ADDR,"
                + "COMM_EMAIL,CONTACT,COMM_TEL,substr(REC_CRT_TS,0,8),MCHT_STATUS,nvl(B.TERM_COUNT,0),CRT_OPR_ID,UPD_OPR_ID,"
                + "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2),MAPPING_MCHNTTYPEONE,MAPPING_MCHNTCDONE,MAPPING_MCHNTTYPETWO,MAPPING_MCHNTCDTWO FROM "
                + "(SELECT * FROM TBL_MCHT_BASE_INF_TMP " + whereSql + ") A " + "left outer join "
                + "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
                + "ON (A.MCHT_NO = B.MCHT_CD) ORDER BY SUBSTR (REC_CRT_TS, 0, 8) desc ";
        String countSql = "SELECT COUNT(1) FROM (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询全部商户信息
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntInfoAll(int begin, HttpServletRequest request) {

        String whereSql = " WHERE 1=1 ";
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (isNotEmpty(request.getParameter("mchtFlag1"))) {
            whereSql += (" AND MCHT_FLAG1 = '" + request.getParameter("mchtFlag1") + "' ");
        }
        if (isNotEmpty(request.getParameter("mchtFlag2"))) {
            whereSql += (" AND MCHT_FLAG1 = '" + request.getParameter("mchtFlag2").substring(0, 1) + "' ");
            whereSql += (" AND MCHT_FLAG2 = '" + request.getParameter("mchtFlag2").substring(1) + "' ");
        }
        if (isNotEmpty(request.getParameter("mchntId"))) {
            whereSql += (" AND MCHT_NO = '" + request.getParameter("mchntId") + "' ");
        }
        String acmchntId = request.getParameter("acmchntId");
        // 收单方商户号
        if (acmchntId != null && !acmchntId.trim().equals("")) {
            whereSql += " AND (MAPPING_MCHNTCDONE like '%" + acmchntId + "%' ";
            whereSql += " or MAPPING_MCHNTCDTWO like '%" + acmchntId + "%') ";
        }
        if (isNotEmpty(request.getParameter("mchtStatus"))) {
            whereSql += (" AND MCHT_STATUS = '" + request.getParameter("mchtStatus") + "' ");
        }
        if (isNotEmpty(request.getParameter("mchtGrp"))) {
            whereSql += (" AND MCHT_GRP = '" + request.getParameter("mchtGrp") + "' ");
        }
        if (isNotEmpty(request.getParameter("startDate"))) {
            whereSql += (" AND substr(REC_CRT_TS,0,8) >= '" + request.getParameter("startDate") + "' ");
        }
        if (isNotEmpty(request.getParameter("endDate"))) {
            whereSql += (" AND substr(REC_CRT_TS,0,8) <= '" + request.getParameter("endDate") + "' ");
        }
        if (isNotEmpty(request.getParameter("mchtNmShort"))) {
            whereSql += (" AND MCHT_CN_ABBR like '%" + request.getParameter("mchtNmShort") + "%' ");
        }
        if (isNotEmpty(request.getParameter("licenceNo"))) {
            whereSql += (" AND LICENCE_NO like '%" + request.getParameter("licenceNo") + "%' ");
        }

        if (isNotEmpty(request.getParameter("brhId")) && !"0000".equals(request.getParameter("brhId"))) {
            whereSql += " AND AGR_BR IN " + InformationUtil.getBrhGroupString(request.getParameter("brhId")) + " ";
        } else if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " AND AGR_BR IN " + InformationUtil.getBrhGroupString(operator.getOprBrhId()) + " ";
        }
        if (isNotEmpty(request.getParameter("connType"))) {
            whereSql += " AND CONN_TYPE = '" + request.getParameter("connType") + "' ";
        }
        whereSql += " order by REC_CRT_TS desc ";
        Object[] ret = new Object[2];

        String sql = "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCHT_GRP,MCC,LICENCE_NO,ADDR,CONN_TYPE,"
                + "COMM_EMAIL,CONTACT,COMM_TEL,substr(REC_CRT_TS,0,8),MCHT_STATUS,nvl(B.TERM_COUNT,0),CRT_OPR_ID,UPD_OPR_ID,"
                + "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2),"
                + "MCHT_FLAG1,MCHT_FLAG1||MCHT_FLAG2,MAPPING_MCHNTTYPEONE,MAPPING_MCHNTCDONE,MAPPING_MCHNTTYPETWO,MAPPING_MCHNTCDTWO "
                + "FROM (SELECT * FROM TBL_MCHT_BASE_INF_TMP " + whereSql + ") A " + "left outer join "
                + "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
                + "ON (A.MCHT_NO = B.MCHT_CD) ORDER BY SUBSTR (REC_CRT_TS, 0, 8) desc";
        String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询待审核商户信息
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntCheckInfo(int begin, HttpServletRequest request) {
        StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" AND AGR_BR IN " + operator.getBrhBelowId());
        }
        whereSql.append(" AND MCHT_STATUS IN ('1','3','5','7','8','R') ");

        String mchntId = request.getParameter("mchntId");
        String acmchntId = request.getParameter("acmchntId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String brhId = request.getParameter("brhId");
        if (StringUtils.isNotEmpty(mchntId)) {
            whereSql.append(" AND MCHT_NO LIKE '%" + mchntId + "%' ");
        }
        // 收单方商户号
        if (acmchntId != null && !acmchntId.trim().equals("")) {
            whereSql.append(" AND (MAPPING_MCHNTCDONE like '%" + acmchntId + "%' ");
            whereSql.append(" or MAPPING_MCHNTCDTWO like '%" + acmchntId + "%') ");
        }

        if (StringUtils.isNotEmpty(startDate)) {
            whereSql.append(" AND substr(REC_CRT_TS,0,8) >= '" + startDate + "' ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            whereSql.append(" AND substr(REC_CRT_TS,0,8) <= '" + endDate + "' ");
        }
        if (StringUtils.isNotEmpty(brhId)) {
            whereSql.append(" AND AGR_BR IN " + InformationUtil.getBrhGroupString(request.getParameter("brhId")) + " ");
        }

        Object[] ret = new Object[2];

        String sql = "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,LICENCE_NO,ADDR,"
                + "COMM_EMAIL,CONTACT,COMM_TEL,substr(REC_CRT_TS,0,8),MCHT_STATUS,nvl(B.TERM_COUNT,0),CRT_OPR_ID,PART_NUM,"
                + "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2),MAPPING_MCHNTTYPEONE,MAPPING_MCHNTCDONE,MAPPING_MCHNTTYPETWO,MAPPING_MCHNTCDTWO FROM "
                + "(SELECT * FROM TBL_MCHT_BASE_INF_TMP " + whereSql + ") A " + "left outer join "
                + "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B " + "ON (A.MCHT_NO = B.MCHT_CD) "
                + "ORDER BY REC_CRT_TS desc ";

        String countSql = "SELECT COUNT(1) FROM (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 终端库存查询
     * 
     * @param begin
     * @param request
     * @return 2011-6-9下午01:43:32
     */
    public static Object[] getTermManagementInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        String termNo = request.getParameter("termNo");
        String mchnNo = request.getParameter("mchnNo");
        String brhId = request.getParameter("brhId");
        String batchNo = request.getParameter("batchNo");
        String state = request.getParameter("state");
        String termIdId = request.getParameter("termIdId");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        String branchId = CommonFunction.getBranchIdByOprId(operator.getOprId());
        StringBuffer sql = new StringBuffer("select TERM_NO,STATE,MANUFATURER,PRODUCT_CD,TERMINAL_TYPE,TERM_TYPE,MECH_NO,BATCH_NO,STOR_OPR_ID,")
                .append(
                        "STOR_DATE,RECI_OPR_ID,RECI_DATE,BACK_OPR_ID,BANK_DATE,INVALID_OPR_ID,"
                                + "INVALID_DATE,SIGN_OPR_ID,SIGN_DATE,MISC,PIN_PAD,BRH_ID " + "from TBL_TERM_MANAGEMENT");
        StringBuffer where = new StringBuffer(" where 1=1 ");

        if (brhId == null || brhId.trim().equals("")) {
            brhId = branchId;
        }
        // 如果是总行可以看所有
        if (!"0000".equals(brhId)) {
            where.append(" and brh_id in" + InformationUtil.getBrhGroupString(brhId) + " ");
        }

        if (state != null && !state.trim().equals("")) {
            if (state.length() == 1) where.append("and STATE ='").append(state).append("'");
            if (state.length() >= 2) where.append("and STATE in (").append(state).append(")");
        }
        if (termNo != null && !termNo.trim().equals("")) {
            where.append("and TERM_NO='").append(termNo).append("'");
        }
        if (mchnNo != null && !mchnNo.trim().equals("")) {
            where.append("and MECH_NO='").append(mchnNo).append("'");
        }
        if (batchNo != null && !batchNo.trim().equals("")) {
            where.append("and BATCH_NO='").append(batchNo).append("'");
        }
        if (termIdId != null && !termIdId.trim().equals("")) {
            where.append("and PRODUCT_CD='").append(termIdId).append("'");
        }
        if (startTime != null && !startTime.trim().equals("")) {
            where.append("and LAST_UPD_TS >=").append("to_date('").append(startTime.substring(0, 10)).append("','yyyy-mm-dd").append("')");
        }
        if (endTime != null && !endTime.trim().equals("")) {
            where.append("and LAST_UPD_TS <=").append("to_date('").append(endTime.substring(0, 10).concat(" 23:59:59"))
                    .append("','yyyy-mm-dd hh24:mi:ss").append("')");
        }

        sql.append(where);
        sql.append(" order by BATCH_NO desc,BRH_ID,TERM_NO,MANUFATURER,PRODUCT_CD");

        String countSql = "SELECT COUNT(1) FROM TBL_TERM_MANAGEMENT " + where.toString();
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 获得终端审核信息列表
     * 
     * @param begin
     * @param request
     * @return 2010-8-18上午09:38:26
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTermCheckInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String whereSql = " where 1=1 ";
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " AND BRH_ID IN " + operator.getBrhBelowId();
        }
        String sql = "SELECT TERM_ID,MCHNT_NO,TERM_STA,IS_SUPP_IC,IS_SUPP_CREDIT,TERM_TYPE,CALL_TYPE,"
                + "CALL_NO,BRH_ID,SEQUENCE_NO,KB_SEQUENCE_NO,V_TELLER,ENC_TYPE,BIND_NO " + "FROM TBL_TERM_INF_TMP " + whereSql
                + " AND TERM_STA IN ('1','3','5','7') ORDER BY MCHNT_NO,TERM_ID";
        String countSql = "SELECT COUNT(*) FROM TBL_TERM_INF_TMP " + whereSql + " AND TERM_STA IN ('1','3','5','7')";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户退回/拒绝原因查询
     * 
     * @param begin
     * @param request
     * @return 2010-8-19上午09:50:00
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntBackRefuseInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String whereSql = " where 1=1 ";
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " AND BRH_ID IN " + operator.getBrhBelowId();
        }

        String sql = "SELECT TXN_TIME,MCHNT_ID,a.BRH_ID,"
                + "(select b.OPR_ID||'-'||OPR_NAME from TBL_OPR_INFO b where a.OPR_ID=b.OPR_ID),REFUSE_TYPE," + "REFUSE_INFO FROM TBL_MCHNT_REFUSE a "
                + whereSql + " ORDER BY TXN_TIME DESC";
        String countSql = "SELECT COUNT(*) FROM TBL_MCHNT_REFUSE " + whereSql;

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 终端退回/拒绝原因查询
     * 
     * @param begin
     * @param request
     * @return 2010-8-19上午09:56:42
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTermBackRefuseInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        String whereSql = " where 1=1 ";
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " AND BRH_ID IN " + operator.getBrhBelowId();
        }
        String sql = "SELECT TXN_TIME,TERM_ID,MCHT_CD,a.BRH_ID," + "(select b.OPR_ID||'-'||OPR_NAME from TBL_OPR_INFO b where a.OPR_ID=b.OPR_ID),"
                + "REFUSE_TYPE,REFUSE_INFO FROM TBL_TERM_REFUSE a " + whereSql + " ORDER BY TXN_TIME DESC";
        String countSql = "SELECT COUNT(*) FROM TBL_TERM_REFUSE " + whereSql;

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 获取终端密钥申请信息
     * 
     * @param begin
     * @param request
     * @return 2011-7-6上午10:52:10
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTermTmkInfo(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String termNo = request.getParameter("termId");

        String termBranch = request.getParameter("termBranch");

        String propInsNm = request.getParameter("propInsNm");

        // 待装机和已装机的1 2只有待装机才能申请
        StringBuffer whereSql = new StringBuffer(" ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId());
        }
        if (termBranch != null && !termBranch.trim().equals("")) {
            whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch).append("'");
        }
        if (StringUtils.isNotEmpty(termNo)) {
            whereSql.append(" AND t1.TERM_ID like '%").append(termNo).append("%'");
        }
        if (StringUtils.isNotEmpty(propInsNm)) {
            whereSql.append(" AND t1.prop_ins_nm='").append(propInsNm).append("'");
        }

        String sql = "SELECT t1.MCHT_CD,t1.TERM_ID,t3.BRH_ID||'-'||t3.BRH_NAME,t1.TERM_STA,t2.ORG_ID||'-'||t2.ORG_NAME " + " from "
                + " ( select * from tbl_term_inf t3 where rowid in (select max(rowid) from tbl_term_inf t2 group by t2.term_id)) t1 "
                + " left join TBL_PROFESSIONAL_ORGAN t2 on t1.prop_ins_nm=t2.org_id " + "left join tbl_brh_info t3 on t1.term_branch=t3.brh_id "
                + " where trim(t1.term_id) not in (select term_id_id from tbl_term_tmk_log)  " + whereSql.toString() + "  ORDER BY t1.term_id";
        String countSql = "select count(1) from (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 终端密钥下发申请 phj 只有待装机的终端才能申请密钥 一机一密
     * 
     * @param begin
     * @param request
     * @return
     */
    public static Object[] getTermTmkInfo01(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String termNo = request.getParameter("termId");

        String termBranch = request.getParameter("termBranch");

        String propInsNm = request.getParameter("propInsNm");

        // 待装机和已装机的1 2只有待装机才能申请
        StringBuffer whereSql = new StringBuffer(" ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId());
        }
        if (termBranch != null && !termBranch.trim().equals("")) {
            whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch).append("'");
        }
        if (StringUtils.isNotEmpty(termNo)) {
            whereSql.append(" AND t1.TERM_ID like '%").append(termNo).append("%'");
        }
        if (StringUtils.isNotEmpty(propInsNm)) {
            whereSql.append(" AND t1.prop_ins_nm='").append(propInsNm).append("'");
        }

        String sql = "SELECT t1.MCHT_CD,t1.TERM_ID,t3.BRH_ID||'-'||t3.BRH_NAME,t1.TERM_STA,t2.ORG_ID||'-'||t2.ORG_NAME " + " from "
                + " ( select * from tbl_term_inf t3 where rowid in (select max(rowid) from tbl_term_inf t2 group by t2.term_id)"
                + " and t3.term_sta = '1' ) t1 " + " left join TBL_PROFESSIONAL_ORGAN t2 on t1.prop_ins_nm=t2.org_id "
                + "left join tbl_brh_info t3 on t1.term_branch=t3.brh_id "
                + " where trim(t1.term_id) not in (select term_id_id from tbl_term_tmk_log)  " + whereSql.toString() + "  ORDER BY t1.term_id";
        String countSql = "select count(1) from (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 获取终端密钥待审核信息
     * 
     * @param begin
     * @param request
     * @return 2011-7-6上午10:52:10
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTermTmkInfoAll(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer(" WHERE t1.prt_count is null and t1.STATE = '0' ");

        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId());
        }
        Object[] ret = new Object[2];
        String batchNo = request.getParameter("batchNo");
        String termIdId = request.getParameter("termIdId");
        String reqOpr = request.getParameter("reqOpr");
        String propInsNm = request.getParameter("propInsNm");
        String state = request.getParameter("state");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if (!StringUtil.isEmpty(propInsNm)) whereSql.append(" AND T3.ORG_ID = '").append(propInsNm).append("'");
        if (!StringUtil.isEmpty(reqOpr)) whereSql.append(" AND t1.REQ_OPR like '%").append(reqOpr).append("%'");
        if (!StringUtil.isEmpty(termIdId)) whereSql.append(" AND t1.TERM_ID_ID like '%").append(termIdId).append("%'");
        if (!StringUtil.isEmpty(batchNo)) whereSql.append(" AND t1.BATCH_NO like '%").append(batchNo).append("%'");
        if (!StringUtil.isEmpty(startDate)) whereSql.append(" AND t1.REQ_DATE>=").append(startDate);
        if (!StringUtil.isEmpty(endDate)) whereSql.append(" AND t1.REQ_DATE<=").append(endDate);
        if (!StringUtil.isEmpty(state)) whereSql.append(" AND t1.STATE='").append(state).append("'");
        String sql = "select t1.BATCH_NO,t1.TERM_ID_ID,t1.MCHN_NO,t1.TERM_BRANCH||'-'||T4.BRH_NAME AS TERM_BRANCH,"
                + "t1.STATE,T2.prop_ins_nm||'-'||T3.ORG_NAME AS THIRD_ORG,t1.REQ_OPR,t1.REQ_DATE,"
                + "t1.CHK_OPR,t1.CHK_DATE,t1.MISC,t1.REC_UPD_OPR,t1.REC_UPD_TS,t1.PRT_OPR,t1.PRT_DATE,t1.PRT_COUNT " + "from TBL_TERM_TMK_LOG t1"
                + " left join (select * from tbl_term_inf t3 where rowid in (select max(rowid) from tbl_term_inf t2 group by t2.term_id)) t2"
                + " on t2.MCHT_CD=t1.MCHN_NO AND trim(T2.TERM_ID)=T1.TERM_ID_ID " + "LEFT JOIN TBL_PROFESSIONAL_ORGAN T3 ON T2.prop_ins_nm=T3.ORG_ID "
                + "LEFT JOIN TBL_BRH_INFO T4 ON T1.TERM_BRANCH=T4.BRH_ID " + whereSql.toString() + " ORDER BY t1.REQ_DATE DESC,t1.BATCH_NO";

        String countSql = "select count(1) from (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 获取终端密钥未打印
     * 
     * @param begin
     * @param request
     * @return 2011-7-6上午10:52:10
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTermTmkInfoUn(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer(" WHERE t1.prt_count is null and t1.STATE = '1' ");

        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId());
        }
        Object[] ret = new Object[2];
        String batchNo = request.getParameter("batchNo");
        String termIdId = request.getParameter("termIdId");
        String reqOpr = request.getParameter("reqOpr");
        String propInsNm = request.getParameter("propInsNm");
        String state = request.getParameter("state");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if (!StringUtil.isEmpty(propInsNm)) whereSql.append(" AND T3.ORG_ID = '").append(propInsNm).append("'");
        if (!StringUtil.isEmpty(reqOpr)) whereSql.append(" AND t1.REQ_OPR like '%").append(reqOpr).append("%'");
        if (!StringUtil.isEmpty(termIdId)) whereSql.append(" AND t1.TERM_ID_ID like '%").append(termIdId).append("%'");
        if (!StringUtil.isEmpty(batchNo)) whereSql.append(" AND t1.BATCH_NO like '%").append(batchNo).append("%'");
        if (!StringUtil.isEmpty(startDate)) whereSql.append(" AND t1.REQ_DATE>=").append(startDate);
        if (!StringUtil.isEmpty(endDate)) whereSql.append(" AND t1.REQ_DATE<=").append(endDate);
        if (!StringUtil.isEmpty(state)) whereSql.append(" AND t1.STATE='").append(state).append("'");
        String sql = "select t1.BATCH_NO,t1.TERM_ID_ID,t1.MCHN_NO,t1.TERM_BRANCH||'-'||T4.BRH_NAME AS TERM_BRANCH,"
                + "t1.STATE,T2.prop_ins_nm||'-'||T3.ORG_NAME AS THIRD_ORG,t1.REQ_OPR,t1.REQ_DATE,"
                + "t1.CHK_OPR,t1.CHK_DATE,t1.MISC,t1.REC_UPD_OPR,t1.REC_UPD_TS,t1.PRT_OPR,t1.PRT_DATE," + "t1.PRT_COUNT " + "from TBL_TERM_TMK_LOG t1"
                + " left join TBL_TERM_INF t2 on t2.MCHT_CD=t1.MCHN_NO AND trim(T2.TERM_ID)=T1.TERM_ID_ID "
                + "LEFT JOIN TBL_PROFESSIONAL_ORGAN T3 ON T2.prop_ins_nm=T3.ORG_ID " + "LEFT JOIN TBL_BRH_INFO T4 ON T1.TERM_BRANCH=T4.BRH_ID "
                + whereSql.toString() + " ORDER BY t1.REQ_DATE DESC,t1.BATCH_NO";
        String countSql = "select count(1) from (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询终端密钥打印信息
     * 
     * @param begin
     * @param request
     * @return 2011-7-6上午10:52:10
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTermTmkInfoPrint(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer(" WHERE t1.prt_count ='1' ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId());
        }
        Object[] ret = new Object[2];
        String batchNo = request.getParameter("batchNo");
        String termIdId = request.getParameter("termIdId");
        String propInsNm = request.getParameter("propInsNm");

        if (!StringUtil.isEmpty(propInsNm)) whereSql.append(" AND T3.ORG_ID = '").append(propInsNm).append("'");
        if (!StringUtil.isEmpty(termIdId)) whereSql.append(" AND t1.TERM_ID_ID like '%").append(termIdId).append("%'");
        if (!StringUtil.isEmpty(batchNo)) whereSql.append(" AND t1.BATCH_NO like '%").append(batchNo).append("%'");

        String sql = "select t1.BATCH_NO,t1.TERM_ID_ID,t1.MCHN_NO,t1.TERM_BRANCH||'-'||T4.BRH_NAME AS TERM_BRANCH,"
                + "t1.STATE,T2.prop_ins_nm||'-'||T3.ORG_NAME AS THIRD_ORG,t1.REQ_OPR,t1.REQ_DATE,"
                + "t1.CHK_OPR,t1.CHK_DATE,t1.MISC,t1.REC_UPD_OPR,t1.REC_UPD_TS,t1.PRT_OPR,t1.PRT_DATE," + "t1.PRT_COUNT "
                + "from TBL_TERM_TMK_LOG t1 " + "left join TBL_TERM_INF t2 on t2.MCHT_CD=t1.MCHN_NO AND trim(T2.TERM_ID)=T1.TERM_ID_ID "
                + "LEFT JOIN TBL_PROFESSIONAL_ORGAN T3 ON T2.prop_ins_nm=T3.ORG_ID " + "LEFT JOIN TBL_BRH_INFO T4 ON T1.TERM_BRANCH=T4.BRH_ID "
                + whereSql.toString() + " ORDER BY t1.REQ_DATE DESC,t1.BATCH_NO";
        String countSql = "select count(1) from (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询机构下所有终端信息
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTermInfoAll(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String orgId = request.getParameter("orgId");
        String termNameLike = request.getParameter("termName");
        String mchtFlag1 = request.getParameter("mchtFlag1");
        String mchtFlag2 = request.getParameter("mchtFlag2");
        String connTp = request.getParameter("connType");
        String brhId = request.getParameter("brhId");
        String termSta = request.getParameter("termSta");
        String startTimeUse = request.getParameter("startTimeUse"); // 装机时间
        String endTimeUse = request.getParameter("endTimeUse");
        String startTime = request.getParameter("startTime"); // 入网时间
        String endTime = request.getParameter("endTime");
        String termId = request.getParameter("termId");
        String actermId = request.getParameter("acequipmentId");
        String mchtCd = request.getParameter("mchtCd");
        String acmchtCd = request.getParameter("acmchntId");
        String termTp = request.getParameter("termTp");
        String mchtGrp = request.getParameter("mchtGrp");
        String agrBr = request.getParameter("agrBr");// 归属机构
        String propInsNmNew = request.getParameter("propInsNmNew");// 第三方服务机构

        String termBranch = request.getParameter("termBranch");
        StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
        if (brhId == null || brhId.trim().equals("")) {
            brhId = operator.getOprBrhId();
        }
        if (!"0000".equals(brhId)) {
            whereSql.append(" and d.TERM_BRANCH in " + InformationUtil.getBrhGroupString(brhId));
        }
        if (termNameLike != null && !termNameLike.trim().equals("")) {
            whereSql.append(" AND d.TERM_NAME like '%" + termNameLike + "%'");
        }
        if (mchtFlag1 != null && !mchtFlag1.trim().equals("")) {
            whereSql.append(" AND d.MCHT_FLAG1 = '" + mchtFlag1 + "'");
        }
        if (mchtFlag2 != null && !mchtFlag2.trim().equals("")) {
            whereSql.append(" AND d.MCHT_FLAG1 = '" + mchtFlag2.substring(0, 1) + "'");
            whereSql.append(" AND d.MCHT_FLAG2 = '" + mchtFlag2.substring(1, 2) + "'");
        }
        if (connTp != null && !connTp.trim().equals("")) {
            whereSql.append(" AND d.CONNECT_MODE = '" + connTp + "'");
        }
        if (StringUtils.isNotBlank(agrBr)) {
            if (!"0000".equals(agrBr)) {
                whereSql.append(" and d.TERM_BRANCH in " + InformationUtil.getBrhGroupString(agrBr));
            }
        }

        if (propInsNmNew != null && !propInsNmNew.trim().equals("")) {
            whereSql.append(" AND c.ORG_ID = '" + propInsNmNew + "'");
        }
        if (orgId != null && !orgId.trim().equals("")) {
            whereSql.append(" AND d.PROP_INS_NM = '" + orgId + "'");
        }
        if (termTp != null && !termTp.trim().equals("")) {
            whereSql.append(" AND d.TERM_TP = '" + termTp + "'");
        }
        if (mchtGrp != null && !mchtGrp.trim().equals("")) {
            whereSql.append(" AND d.MCHT_GRP = '" + mchtGrp + "'");
        }

        if (startTimeUse != null && !startTimeUse.trim().equals("")) {
            whereSql.append(" AND d.USING_DATE>=");
            whereSql.append(startTimeUse.split("T")[0].replaceAll("-", ""));
        }
        if (endTimeUse != null && !endTimeUse.trim().equals("")) {
            whereSql.append(" AND d.USING_DATE<=");
            whereSql.append(endTimeUse.split("T")[0].replaceAll("-", ""));
        }
        if (startTime != null && !startTime.trim().equals("")) {
            whereSql.append(" AND d.REC_UPD_TS>=");
            whereSql.append(startTime.split("T")[0].replaceAll("-", ""));
        }
        if (endTime != null && !endTime.trim().equals("")) {
            whereSql.append(" AND d.REC_UPD_TS<=");
            whereSql.append(endTime.split("T")[0].replaceAll("-", ""));
        }
        if (mchtCd != null && !mchtCd.trim().equals("")) {
            whereSql.append(" AND d.MCHT_CD='");
            whereSql.append(mchtCd);
            whereSql.append("'");
        }
        // 收单方商户号
        if (acmchtCd != null && !acmchtCd.trim().equals("")) {
            whereSql.append(" AND (d.MAPPING_MCHNTCDONE like '%" + acmchtCd + "%' ");
            whereSql.append(" or d.MAPPING_MCHNTCDTWO like '%" + acmchtCd + "%') ");
        }
        // 收单方终端号
        if (actermId != null && !actermId.trim().equals("")) {
            whereSql.append(" AND (d.MAPPING_TERMIDONE like '%" + actermId + "%' ");
            whereSql.append(" or d.MAPPING_TERMIDTWO like '%" + actermId + "%') ");
        }
        if (termBranch != null && !termBranch.trim().equals("")) {
            whereSql.append(" AND d.TERM_BRANCH='");
            whereSql.append(termBranch);
            whereSql.append("'");
        }
        if (termId != null && !termId.trim().equals("")) {
            whereSql.append(" AND d.TERM_ID LIKE '%");
            whereSql.append(termId);
            whereSql.append("%' ");
        }
        if (termSta != null && !termSta.trim().equals("")) {
            if (termSta.length() == 1)
                whereSql.append(" AND d.TERM_STA='").append(termSta).append("'");
            else {
                termSta = termSta.replaceAll("1", "','");
                whereSql.append(" AND d.TERM_STA in ('").append(termSta).append("')");
            }
        }
        // String sql =
        // "SELECT a.TERM_ID,a.PROP_INS_NM,a.MCHT_CD,b.MCHT_FLAG1,b.MCHT_FLAG1||b.MCHT_FLAG2,"
        // +
        // "a.CONNECT_MODE,a.TERM_STA,a.TERM_SIGN_STA,a.TERM_ID_ID,a.TERM_FACTORY,a.TERM_MACH_TP,"
        // +
        // "a.TERM_VER,a.TERM_TP,b.MCHT_GRP,a.TERM_BRANCH,a.TERM_INS,a.REC_CRT_TS,a.PROP_INS_RATE,a.LEASE_FEE,a.RENT_FEE,a.USING_DATE "
        // +
        // "FROM TBL_TERM_INF_TMP a left outer join TBL_MCHT_BASE_INF b on(a.mcht_cd=b.mcht_no) "
        String querySQLValue = "select a.TERM_ID,a.MCHT_CD,b.MCHT_FLAG1,b.MCHT_FLAG2,b.MCHT_FLAG1||b.MCHT_FLAG2 flag, "
                + "a.CONNECT_MODE,a.TERM_STA,a.TERM_SIGN_STA,a.TERM_ID_ID,a.TERM_FACTORY,a.TERM_MACH_TP, "
                + "a.TERM_VER,a.TERM_TP,b.MCHT_GRP,a.TERM_BRANCH,a.TERM_INS,a.PROP_INS_RATE,a.LEASE_FEE,"
                + "a.RENT_FEE,a.USING_DATE,a.REC_CRT_TS,a.REC_UPD_TS,a.prop_ins_nm,a.misc_2,a.TERM_NAME,"
                + "a.MAPPING_MCHNTTYPEONE,a.MAPPING_MCHNTCDONE,a.MAPPING_MCHNTTYPETWO,a.MAPPING_MCHNTCDTWO,a.MAPPING_TERMIDONE,a.MAPPING_TERMIDTWO "
                + "from TBL_TERM_INF_TMP a left outer join TBL_MCHT_BASE_INF b on a.MCHT_CD = b.MCHT_NO ";
        String sql = "select d.TERM_ID,c.ORG_ID || '-' || c.ORG_NAME orgValue,d.MCHT_CD,d.MCHT_FLAG1,d.flag,"
                + "d.CONNECT_MODE,d.TERM_STA,d.TERM_SIGN_STA,d.TERM_ID_ID,d.TERM_FACTORY,d.TERM_MACH_TP, "
                + "d.TERM_VER,d.TERM_TP,d.MCHT_GRP,d.TERM_BRANCH,d.TERM_INS,d.PROP_INS_RATE,d.LEASE_FEE,d.RENT_FEE,d.USING_DATE,d.misc_2,d.TERM_NAME "
                + ",d.MAPPING_MCHNTTYPEONE,d.MAPPING_MCHNTCDONE,d.MAPPING_MCHNTTYPETWO,d.MAPPING_MCHNTCDTWO,d.MAPPING_TERMIDONE,d.MAPPING_TERMIDTWO "
                + "from (" + querySQLValue + ") d " + " left outer join TBL_PROFESSIONAL_ORGAN c on d.prop_ins_nm = c.org_id " + whereSql.toString()
                + " ORDER BY d.REC_CRT_TS DESC";
        String countSql = "SELECT COUNT(*) FROM (" + querySQLValue + ") d " + " left outer join TBL_PROFESSIONAL_ORGAN c on d.prop_ins_nm = c.org_id "
                + whereSql.toString();
        String sql2 = "select d.TERM_ID,d.MCHT_CD,d.MAPPING_MCHNTCDONE,d.MAPPING_TERMIDONE,d.MAPPING_MCHNTCDTWO,d.MAPPING_TERMIDTWO,(case d.MCHT_FLAG1 when '0' then '收款商户'  "
                + "when '1' then '老板通商户'  " + "when '2' then '分期商户' " + "when '3' then '财务转账商户' " + "when '4' then '项目形式商户' "
                + "when '5' then '积分业务'  " + "when '6' then '其他业务' " + "when '7' then 'Epos商户' else '未知' end), "
                + " (case d.flag when '00' then '线上商户' " + "when '01' then '线下商户' " + "when '10' then '业主收款商户' " + "when '11' then '业主付款商户' "
                + "when '20' then '汽车分期商户' " + "when '21' then '停车位分期商户' " + "when '22' then '家装分期商户' " + "when '23' then 'POS分期商户' "
                + " when '24' then '其他分期商户' " + "when '30' then '本行财务转账商户' " + " when '31' then '跨行财务转账商户' " + "when '40' then '交通罚没项目' "
                + "when '41' then '体彩购额项目' " + "when '42' then '其他项目' else '未知' end), " + "(case ti.descr when null then '未知' "
                + " when ' ' then '未知' else ti.descr end )," + "(case d.CONNECT_MODE when 'J' then '间联' when 'Z' then '直联' else '未知' end),"
                + "tt.descr," + "(case d.TERM_STA " + "when '0' then '新增待审核' " + "when '1' then '待装机' " + "when '2' then '已装机' "
                + " when '3' then '修改待审核' " + " when '4' then '冻结' " + " when '5' then '冻结待审核' " + "when '6' then '解冻待审核' " + "when '7' then '注销' "
                + "when '8' then '注销待审核' " + " when 'A' then '新增退回' " + "when 'R' then '注销恢复审核' " + " else '未知' end ),"
                + "d.USING_DATE,tb.brh_name,d.TERM_ID_ID,d.TERM_FACTORY," + "d.TERM_MACH_TP,c.ORG_ID || '-' || c.ORG_NAME orgValue,d.PROP_INS_RATE,"
                + "d.LEASE_FEE,d.RENT_FEE " + "from (" + querySQLValue + ") d "
                + "left outer join TBL_PROFESSIONAL_ORGAN c on d.prop_ins_nm = c.org_id "
                + "left outer join tbl_brh_info tb on tb.brh_id = d.TERM_BRANCH " + "left outer join tbl_term_tp tt on tt.term_tp = d.TERM_TP "
                + "left outer join TBL_INF_MCHNT_TP_GRP ti on ti.MCHNT_TP_GRP = d.MCHT_GRP " + whereSql.toString() + " ORDER BY d.REC_CRT_TS DESC";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

        List<Object[]> list1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sql2.toString());
        /*
         * Object[] dataNew; for (int i = 0; i < list1.size(); i++) { dataNew =
         * list1.get(i); dataNew[15] =
         * CommonFunction.transFenToYuan(dataNew[15].toString()); dataNew[16] =
         * CommonFunction.transFenToYuan(dataNew[16].toString()); list1.set(i,
         * dataNew); }
         */
        request.getSession().removeAttribute("termInfoList");
        request.getSession().setAttribute("termInfoList", list1);
        ret[0] = dataList;
        ret[1] = count;
        return ret;

    }

    /**
     * 查询机构下所有终端信息，除了Epos商户下的终端
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTermInfoNew(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String orgId = request.getParameter("orgId");
        String mchtFlag1 = request.getParameter("mchtFlag1");
        String mchtFlag2 = request.getParameter("mchtFlag2");
        String connTp = request.getParameter("connType");
        String brhId = request.getParameter("brhId");
        String termSta = request.getParameter("termSta");
        String startTimeUse = request.getParameter("startTimeUse"); // 装机时间
        String endTimeUse = request.getParameter("endTimeUse");
        String startTime = request.getParameter("startTime"); // 入网时间
        String endTime = request.getParameter("endTime");
        String termId = request.getParameter("termId");
        String actermId = request.getParameter("acequipmentId");
        String mchtCd = request.getParameter("mchtCd");
        String acmchntId = request.getParameter("acmchntId");
        String termTp = request.getParameter("termTp");
        String mchtGrp = request.getParameter("mchtGrp");
        String agrBr = request.getParameter("agrBr");// 归属机构
        String propInsNmNew = request.getParameter("propInsNmNew");// 第三方服务机构

        String termBranch = request.getParameter("termBranch");
        StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
        if (brhId == null || brhId.trim().equals("")) {
            brhId = operator.getOprBrhId();
        }
        if (!"0000".equals(brhId)) {
            whereSql.append(" and d.TERM_BRANCH in " + InformationUtil.getBrhGroupString(brhId));
        }
        if (mchtFlag1 != null && !mchtFlag1.trim().equals("")) {
            whereSql.append(" AND d.MCHT_FLAG1 = '" + mchtFlag1 + "'");
        }
        if (mchtFlag2 != null && !mchtFlag2.trim().equals("")) {
            whereSql.append(" AND d.MCHT_FLAG1 = '" + mchtFlag2.substring(0, 1) + "'");
            whereSql.append(" AND d.MCHT_FLAG2 = '" + mchtFlag2.substring(1, 2) + "'");
        }
        if (connTp != null && !connTp.trim().equals("")) {
            whereSql.append(" AND d.CONNECT_MODE = '" + connTp + "'");
        }
        if (agrBr != null && !agrBr.trim().equals("")) {
            whereSql.append(" AND d.term_branch = '" + agrBr + "'");
        }
        if (propInsNmNew != null && !propInsNmNew.trim().equals("")) {
            whereSql.append(" AND c.ORG_ID = '" + propInsNmNew + "'");
        }
        if (orgId != null && !orgId.trim().equals("")) {
            whereSql.append(" AND d.PROP_INS_NM = '" + orgId + "'");
        }
        if (termTp != null && !termTp.trim().equals("")) {
            whereSql.append(" AND d.TERM_TP = '" + termTp + "'");
        }
        if (mchtGrp != null && !mchtGrp.trim().equals("")) {
            whereSql.append(" AND d.MCHT_GRP = '" + mchtGrp + "'");
        }

        if (startTimeUse != null && !startTimeUse.trim().equals("")) {
            whereSql.append(" AND d.USING_DATE>=");
            whereSql.append(startTimeUse.split("T")[0].replaceAll("-", ""));
        }
        if (endTimeUse != null && !endTimeUse.trim().equals("")) {
            whereSql.append(" AND d.USING_DATE<=");
            whereSql.append(endTimeUse.split("T")[0].replaceAll("-", ""));
        }
        if (startTime != null && !startTime.trim().equals("")) {
            whereSql.append(" AND d.REC_UPD_TS>=");
            whereSql.append(startTime.split("T")[0].replaceAll("-", ""));
        }
        if (endTime != null && !endTime.trim().equals("")) {
            whereSql.append(" AND d.REC_UPD_TS<=");
            whereSql.append(endTime.split("T")[0].replaceAll("-", ""));
        }
        if (mchtCd != null && !mchtCd.trim().equals("")) {
            whereSql.append(" AND d.MCHT_CD='");
            whereSql.append(mchtCd);
            whereSql.append("'");
        }
        // 收单方商户号
        if (acmchntId != null && !acmchntId.trim().equals("")) {
            whereSql.append(" AND (d.MAPPING_MCHNTCDONE like '%" + acmchntId + "%' ");
            whereSql.append(" or d.MAPPING_MCHNTCDTWO like '%" + acmchntId + "%') ");
        }

        // 收单方终端号
        if (actermId != null && !actermId.trim().equals("")) {
            whereSql.append(" AND (d.MAPPING_TERMIDONE like '%" + actermId + "%' ");
            whereSql.append(" or d.MAPPING_TERMIDTWO like '%" + actermId + "%') ");
        }
        if (termBranch != null && !termBranch.trim().equals("")) {
            whereSql.append(" AND d.TERM_BRANCH='");
            whereSql.append(termBranch);
            whereSql.append("'");
        }
        if (termId != null && !termId.trim().equals("")) {
            whereSql.append(" AND d.TERM_ID LIKE '%");
            whereSql.append(termId);
            whereSql.append("%' ");
        }
        if (termSta != null && !termSta.trim().equals("")) {
            if (termSta.length() == 1)
                whereSql.append(" AND d.TERM_STA='").append(termSta).append("'");
            else {
                termSta = termSta.replaceAll("1", "','");
                whereSql.append(" AND d.TERM_STA in ('").append(termSta).append("')");
            }
        }
        // String sql =
        // "SELECT a.TERM_ID,a.PROP_INS_NM,a.MCHT_CD,b.MCHT_FLAG1,b.MCHT_FLAG1||b.MCHT_FLAG2,"
        // +
        // "a.CONNECT_MODE,a.TERM_STA,a.TERM_SIGN_STA,a.TERM_ID_ID,a.TERM_FACTORY,a.TERM_MACH_TP,"
        // +
        // "a.TERM_VER,a.TERM_TP,b.MCHT_GRP,a.TERM_BRANCH,a.TERM_INS,a.REC_CRT_TS,a.PROP_INS_RATE,a.LEASE_FEE,a.RENT_FEE,a.USING_DATE "
        // +
        // "FROM TBL_TERM_INF_TMP a left outer join TBL_MCHT_BASE_INF b on(a.mcht_cd=b.mcht_no) "
        String querySQLValue = "select a.TERM_ID,a.MCHT_CD,b.MCHT_FLAG1,b.MCHT_FLAG2,b.MCHT_FLAG1||b.MCHT_FLAG2 flag, "
                + "a.CONNECT_MODE,a.TERM_STA,a.TERM_SIGN_STA,a.TERM_ID_ID,a.TERM_FACTORY,a.TERM_MACH_TP, "
                + "a.TERM_VER,a.TERM_TP,b.MCHT_GRP,a.TERM_BRANCH,a.TERM_INS,a.PROP_INS_RATE,a.LEASE_FEE,"
                + "a.RENT_FEE,a.USING_DATE,a.REC_CRT_TS,a.REC_UPD_TS,a.prop_ins_nm,a.misc_2 "
                + ",a.MAPPING_MCHNTTYPEONE,a.MAPPING_MCHNTCDONE,a.MAPPING_MCHNTTYPETWO,a.MAPPING_MCHNTCDTWO,a.MAPPING_TERMIDONE,a.MAPPING_TERMIDTWO "
                + "from TBL_TERM_INF_TMP a  left outer join TBL_MCHT_BASE_INF b on a.MCHT_CD = b.MCHT_NO where a.mcht_cd not in (select mcht_no from tbl_mcht_base_inf_tmp where mcht_flag1='7' ) ";
        String sql = "select d.TERM_ID,c.ORG_ID || '-' || c.ORG_NAME orgValue,d.MCHT_CD,d.MCHT_FLAG1,d.flag,"
                + "d.CONNECT_MODE,d.TERM_STA,d.TERM_SIGN_STA,d.TERM_ID_ID,d.TERM_FACTORY,d.TERM_MACH_TP, "
                + "d.TERM_VER,d.TERM_TP,d.MCHT_GRP,d.TERM_BRANCH,d.TERM_INS,d.PROP_INS_RATE,d.LEASE_FEE,d.RENT_FEE,d.USING_DATE,d.misc_2 "
                + ",d.MAPPING_MCHNTTYPEONE,d.MAPPING_MCHNTCDONE,d.MAPPING_MCHNTTYPETWO,d.MAPPING_MCHNTCDTWO,d.MAPPING_TERMIDONE,d.MAPPING_TERMIDTWO "
                + "from (" + querySQLValue + ") d " + " left outer join TBL_PROFESSIONAL_ORGAN c on d.prop_ins_nm = c.org_id " + whereSql.toString()
                + " ORDER BY d.REC_CRT_TS DESC";
        String countSql = "SELECT COUNT(*) FROM (" + querySQLValue + ") d " + " left outer join TBL_PROFESSIONAL_ORGAN c on d.prop_ins_nm = c.org_id "
                + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询机构下所有终端信息，只有Epos商户下的终端
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTermInfoNewEpos(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String orgId = request.getParameter("orgId");
        String mchtFlag1 = request.getParameter("mchtFlag1");
        String mchtFlag2 = request.getParameter("mchtFlag2");
        String connTp = request.getParameter("idconnType");
        String brhId = request.getParameter("agrBrId");
        String termSta = request.getParameter("termSta");
        String startTimeUse = request.getParameter("startTimeUse"); // 装机时间
        String endTimeUse = request.getParameter("endTimeUse");
        String startTime = request.getParameter("startTime"); // 入网时间
        String endTime = request.getParameter("endTime");
        String termId = request.getParameter("termId");
        String actermId = request.getParameter("acequipmentId");
        String mchtCd = request.getParameter("mchtCd");
        String acmchntId = request.getParameter("acmchntId");
        String termTp = request.getParameter("termTp");
        String mchtGrp = request.getParameter("mchtGrp");
        String propInsNmN = request.getParameter("propInsNmN");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + propInsNmN);
        String termBranch = request.getParameter("termBranch");
        StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
        if (brhId == null || brhId.trim().equals("")) {
            brhId = operator.getOprBrhId();
        }
        if (!"0000".equals(brhId)) {
            whereSql.append(" and d.TERM_BRANCH in " + InformationUtil.getBrhGroupString(brhId));
        }
        if (mchtFlag1 != null && !mchtFlag1.trim().equals("")) {
            whereSql.append(" AND d.MCHT_FLAG1 = '" + mchtFlag1 + "'");
        }
        if (mchtFlag2 != null && !mchtFlag2.trim().equals("")) {
            whereSql.append(" AND d.MCHT_FLAG1 = '" + mchtFlag2.substring(0, 1) + "'");
            whereSql.append(" AND d.MCHT_FLAG2 = '" + mchtFlag2.substring(1, 2) + "'");
        }
        if (propInsNmN != null && !propInsNmN.trim().equals("")) {
            whereSql.append(" AND c.ORG_ID = '" + propInsNmN + "'");
        }
        if (connTp != null && !connTp.trim().equals("")) {
            whereSql.append(" AND d.CONNECT_MODE = '" + connTp + "'");
        }
        if (orgId != null && !orgId.trim().equals("")) {
            whereSql.append(" AND d.PROP_INS_NM = '" + orgId + "'");
        }
        if (termTp != null && !termTp.trim().equals("")) {
            whereSql.append(" AND d.TERM_TP = '" + termTp + "'");
        }
        if (mchtGrp != null && !mchtGrp.trim().equals("")) {
            whereSql.append(" AND d.MCHT_GRP = '" + mchtGrp + "'");
        }

        if (startTimeUse != null && !startTimeUse.trim().equals("")) {
            whereSql.append(" AND d.USING_DATE>=");
            whereSql.append(startTimeUse.split("T")[0].replaceAll("-", ""));
        }
        if (endTimeUse != null && !endTimeUse.trim().equals("")) {
            whereSql.append(" AND d.USING_DATE<=");
            whereSql.append(endTimeUse.split("T")[0].replaceAll("-", ""));
        }
        if (startTime != null && !startTime.trim().equals("")) {
            whereSql.append(" AND d.REC_UPD_TS>=");
            whereSql.append(startTime.split("T")[0].replaceAll("-", ""));
        }
        if (endTime != null && !endTime.trim().equals("")) {
            whereSql.append(" AND d.REC_UPD_TS<=");
            whereSql.append(endTime.split("T")[0].replaceAll("-", ""));
        }
        if (mchtCd != null && !mchtCd.trim().equals("")) {
            whereSql.append(" AND d.MCHT_CD='");
            whereSql.append(mchtCd);
            whereSql.append("'");
        }
        // 收单方商户号
        if (acmchntId != null && !acmchntId.trim().equals("")) {
            whereSql.append(" AND (d.MAPPING_MCHNTCDONE like '%" + acmchntId + "%' ");
            whereSql.append(" or d.MAPPING_MCHNTCDTWO like '%" + acmchntId + "%') ");

        }
        // 收单方终端号
        if (actermId != null && !actermId.trim().equals("")) {
            whereSql.append(" AND (d.MAPPING_TERMIDONE like '%" + actermId + "%' ");
            whereSql.append(" or d.MAPPING_TERMIDTWO like '%" + actermId + "%') ");
        }

        if (termBranch != null && !termBranch.trim().equals("")) {
            whereSql.append(" AND d.TERM_BRANCH='");
            whereSql.append(termBranch);
            whereSql.append("'");
        }
        if (termId != null && !termId.trim().equals("")) {
            whereSql.append(" AND d.TERM_ID LIKE '%");
            whereSql.append(termId);
            whereSql.append("%' ");
        }
        if (termSta != null && !termSta.trim().equals("")) {
            if (termSta.length() == 1)
                whereSql.append(" AND d.TERM_STA='").append(termSta).append("'");
            else {
                termSta = termSta.replaceAll("1", "','");
                whereSql.append(" AND d.TERM_STA in ('").append(termSta).append("')");
            }
        }
        // String sql =
        // "SELECT a.TERM_ID,a.PROP_INS_NM,a.MCHT_CD,b.MCHT_FLAG1,b.MCHT_FLAG1||b.MCHT_FLAG2,"
        // +
        // "a.CONNECT_MODE,a.TERM_STA,a.TERM_SIGN_STA,a.TERM_ID_ID,a.TERM_FACTORY,a.TERM_MACH_TP,"
        // +
        // "a.TERM_VER,a.TERM_TP,b.MCHT_GRP,a.TERM_BRANCH,a.TERM_INS,a.REC_CRT_TS,a.PROP_INS_RATE,a.LEASE_FEE,a.RENT_FEE,a.USING_DATE "
        // +
        // "FROM TBL_TERM_INF_TMP a left outer join TBL_MCHT_BASE_INF b on(a.mcht_cd=b.mcht_no) "
        String querySQLValue = "select a.TERM_ID,a.MCHT_CD,b.MCHT_FLAG1,b.MCHT_FLAG2,b.MCHT_FLAG1||b.MCHT_FLAG2 flag, "
                + "a.CONNECT_MODE,a.TERM_STA,a.TERM_SIGN_STA,a.TERM_ID_ID,a.TERM_FACTORY,a.TERM_MACH_TP, "
                + "a.TERM_VER,a.TERM_TP,b.MCHT_GRP,a.TERM_BRANCH,a.TERM_INS,a.PROP_INS_RATE,a.LEASE_FEE,"
                + "a.RENT_FEE,a.USING_DATE,a.REC_CRT_TS,a.REC_UPD_TS,a.prop_ins_nm,a.misc_2 "
                + ",a.MAPPING_MCHNTTYPEONE,a.MAPPING_MCHNTCDONE,a.MAPPING_MCHNTTYPETWO,a.MAPPING_MCHNTCDTWO,a.MAPPING_TERMIDONE,a.MAPPING_TERMIDTWO "
                + "from TBL_TERM_INF_TMP a  left outer join TBL_MCHT_BASE_INF b on a.MCHT_CD = b.MCHT_NO where a.mcht_cd in (select mcht_no from tbl_mcht_base_inf_tmp where mcht_flag1='7' ) ";
        String sql = "select d.TERM_ID,c.ORG_ID || '-' || c.ORG_NAME orgValue,d.MCHT_CD,d.MCHT_FLAG1,d.flag,"
                + "d.CONNECT_MODE,d.TERM_STA,d.TERM_SIGN_STA,d.TERM_ID_ID,d.TERM_FACTORY,d.TERM_MACH_TP, "
                + "d.TERM_VER,d.TERM_TP,d.MCHT_GRP,d.TERM_BRANCH,d.TERM_INS,d.PROP_INS_RATE,d.LEASE_FEE,d.RENT_FEE,d.USING_DATE,d.misc_2 "
                + ",d.MAPPING_MCHNTTYPEONE,d.MAPPING_MCHNTCDONE,d.MAPPING_MCHNTTYPETWO,d.MAPPING_MCHNTCDTWO,d.MAPPING_TERMIDONE,d.MAPPING_TERMIDTWO "
                + "from (" + querySQLValue + ") d " + " left outer join TBL_PROFESSIONAL_ORGAN c on d.prop_ins_nm = c.org_id " + whereSql.toString()
                + " ORDER BY d.REC_CRT_TS DESC";
        String countSql = "SELECT COUNT(*) FROM (" + querySQLValue + ") d " + " left outer join TBL_PROFESSIONAL_ORGAN c on d.prop_ins_nm = c.org_id "
                + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询机构下所有待审核的终端信息
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTermInfoForAudit(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String termSta = request.getParameter("termSta");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String termId = request.getParameter("termId");
        String actermId = request.getParameter("acequipmentId");
        String mchtCd = request.getParameter("mchtCd");
        String acmchntId = request.getParameter("acmchntId");
        String termBranch = request.getParameter("termBranch");
        StringBuffer whereSql = new StringBuffer(" WHERE TERM_STA IN ('0','3','5','6','8','R') ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and TERM_BRANCH in " + operator.getBrhBelowId());
        }
        if (startTime != null && !startTime.trim().equals("")) {
            whereSql.append(" AND REC_UPD_TS>=");
            whereSql.append(startTime.split("T")[0].replaceAll("-", ""));
        }
        if (endTime != null && !endTime.trim().equals("")) {
            whereSql.append(" AND REC_UPD_TS<=");
            whereSql.append(endTime.split("T")[0].replaceAll("-", ""));
        }
        if (mchtCd != null && !mchtCd.trim().equals("")) {
            whereSql.append(" AND MCHT_CD='");
            whereSql.append(mchtCd);
            whereSql.append("'");
        }
        // 收单方商户号
        if (acmchntId != null && !acmchntId.trim().equals("")) {
            whereSql.append(" AND (MAPPING_MCHNTCDONE like '%" + acmchntId + "%' ");
            whereSql.append(" or MAPPING_MCHNTCDTWO like '%" + acmchntId + "%') ");
        }

        // 收单方终端号
        if (actermId != null && !actermId.trim().equals("")) {
            whereSql.append(" AND (MAPPING_TERMIDONE like '%" + actermId + "%' ");
            whereSql.append(" or MAPPING_TERMIDTWO like '%" + actermId + "%') ");
        }

        if (termBranch != null && !termBranch.trim().equals("")) {
            whereSql.append(" AND TERM_BRANCH='");
            whereSql.append(termBranch);
            whereSql.append("'");
        }
        if (termId != null && !termId.trim().equals("")) {
            whereSql.append(" AND TERM_ID LIKE '%");
            whereSql.append(termId);
            whereSql.append("%' ");
        }
        if (termSta != null && !termSta.trim().equals("")) {
            if (termSta.length() == 1)
                whereSql.append(" AND TERM_STA='").append(termSta).append("'");
            else {
                termSta = termSta.replaceAll("1", "','");
                whereSql.append(" AND TERM_STA in ('").append(termSta).append("')");
            }
        }
        String sql = "SELECT TERM_ID,MCHT_CD,TERM_STA,TERM_SIGN_STA,TERM_ID_ID,TERM_FACTORY,TERM_MACH_TP,"
                + "TERM_VER,TERM_TP,TERM_BRANCH,TERM_INS,REC_CRT_TS,"
                + "MAPPING_MCHNTTYPEONE,MAPPING_MCHNTCDONE,MAPPING_MCHNTTYPETWO,MAPPING_MCHNTCDTWO,MAPPING_TERMIDONE,MAPPING_TERMIDTWO FROM TBL_TERM_INF_TMP  "
                + whereSql.toString() + " ORDER BY REC_CRT_TS DESC";
        String countSql = "SELECT COUNT(1) FROM TBL_TERM_INF_TMP " + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 终端信息查询（正式表）
     * 
     * @param begin
     * @param request
     * @return 2011-8-3下午04:15:43
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTermInfo(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String termNo = request.getParameter("termId");
        String termName = request.getParameter("termNameU");
        String mchtCd = request.getParameter("mchnNo");
        String termBranch = request.getParameter("termBranch");
        StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and TERM_BRANCH in " + operator.getBrhBelowId());
        }
        if (startTime != null && !startTime.trim().equals("")) {
            whereSql.append(" AND REC_UPD_TS>").append(startTime.split("T")[0].replaceAll("-", ""));
        }
        if (endTime != null && !endTime.trim().equals("")) {
            whereSql.append(" AND REC_UPD_TS<").append(endTime.split("T")[0].replaceAll("-", ""));
        }
        if (mchtCd != null && !mchtCd.trim().equals("")) {
            whereSql.append(" AND MCHT_CD='").append(mchtCd).append("'");
        }
        if (termBranch != null && !termBranch.trim().equals("")) {
            whereSql.append(" AND TERM_BRANCH='").append(termBranch).append("'");
        }
        if (termNo != null && !termNo.trim().equals("")) {
            whereSql.append(" AND TERM_ID='").append(CommonFunction.fillString(termNo, ' ', 12, true)).append("'");
        }

        String sql = "SELECT TERM_ID,MCHT_CD,TERM_STA,TERM_SIGN_STA,TERM_ID_ID,TERM_FACTORY,TERM_MACH_TP,"
                + "TERM_VER,TERM_TP,TERM_BRANCH,TERM_INS,TERM_NAME FROM TBL_TERM_INF " + whereSql.toString() + " ORDER BY MCHT_CD,TERM_ID";
        String countSql = "SELECT COUNT(1) FROM TBL_TERM_INF " + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询终端密钥信息
     * 
     * @param begin
     * @param request
     * @return 2010-8-19上午11:20:41
     */
    @SuppressWarnings("unchecked")
    public static Object[] getPosKeyInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String whereSql = "";
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " AND b.BRH_ID IN " + operator.getBrhBelowId();
        }
        String sql = "SELECT a.MCHNT_ID,a.TERM_ID,b.BRH_ID,POS_TMK,TMK_ST FROM TBL_TERM_KEY a,TBL_TERM_INF b "
                + "WHERE a.TERM_ID  = b.TERM_ID AND b.TERM_ST = '0' " + whereSql + " ORDER BY a.MCHNT_ID,a.TERM_ID";
        String countSql = "SELECT COUNT(*) FROM TBL_TERM_KEY a,TBL_TERM_INF b " + "WHERE a.TERM_ID  = b.TERM_ID AND b.TERM_ST = '0' " + whereSql;

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询卡黑名单信息
     * 
     * @param begin
     * @param request
     * @return 2010-8-24下午04:47:34
     */
    @SuppressWarnings("unchecked")
    public static Object[] getCardRiskInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        // Operator operator = (Operator)
        // request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String sql = "SELECT SA_CARD_NO,SA_LIMIT_AMT,SA_ACTION,SA_INIT_ZONE_NO,SA_INIT_OPR_ID,SA_INIT_TIME,"
                + "SA_MODI_ZONE_NO,SA_MODI_OPR_ID,SA_MODI_TIME FROM TBL_CTL_CARD_INF" + " WHERE 1=1";
        String countSql = "SELECT COUNT(*) FROM TBL_CTL_CARD_INF WHERE 1=1";

        String whereSql = "";
        String date = null;
        if (isNotEmpty(request.getParameter("srCardNo"))) {
            date = request.getParameter("srCardNo");
            whereSql += " and SA_CARD_NO = '" + date + "'";
        }
        if (isNotEmpty(request.getParameter("srBrhNo"))) {
            date = request.getParameter("srBrhNo");
            whereSql += " and SA_INIT_ZONE_NO = '" + date + "'";
        }
        if (isNotEmpty(request.getParameter("srAction"))) {
            date = request.getParameter("srAction");
            whereSql += " and SA_ACTION = '" + date + "'";
        }

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql + whereSql, begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            // data[1] = CommonFunction.transFenToYuan(data[1].toString());
            dataList.set(i, data);
        }
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql + whereSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询商户黑名单信息
     * 
     * @param begin
     * @param request
     * @return 2010-8-26下午09:40:21
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntRiskInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        String sql = "SELECT SA_MER_NO,SA_MER_CH_NAME,SA_MER_EN_NAME,SA_LIMIT_AMT,SA_ACTION,SA_ZONE_NO,SA_ADMI_BRAN_NO,"
                + "SA_CONN_OR,SA_CONN_TEL,SA_INIT_ZONE_NO,SA_INIT_OPR_ID,SA_INIT_TIME,SA_MODI_ZONE_NO,SA_MODI_OPR_ID,"
                + "SA_MODI_TIME FROM TBL_CTL_MCHT_INF " + "WHERE 1=1 ";
        String countSql = "SELECT COUNT(*) FROM TBL_CTL_MCHT_INF WHERE 1=1 ";

        String whereSql = "";
        String date = null;
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " AND SA_ZONE_NO IN " + operator.getBrhBelowId();
        }
        if (isNotEmpty(request.getParameter("srMerNo"))) {
            date = request.getParameter("srMerNo");
            whereSql += " and SA_MER_NO = '" + date + "'";
        }
        if (isNotEmpty(request.getParameter("srBrhNo"))) {
            date = request.getParameter("srBrhNo");
            whereSql += " and SA_ZONE_NO = '" + date + "'";
        }
        if (isNotEmpty(request.getParameter("srAction"))) {
            date = request.getParameter("srAction");
            whereSql += " and SA_ACTION = '" + date + "'";
        }
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql + whereSql, begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            // data[3] = CommonFunction.transFenToYuan(data[3].toString());
            dataList.set(i, data);
        }
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql + whereSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 交易流水查询（支付宝、微信）
     * //TODO 添加方法功能描述
     *
     * @param begin
     * @param request
     * @return
     * @author hanyongqing
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTxnInfoAir(int begin, HttpServletRequest request) {
        List<Object[]> findBySQLQuery = null;
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String payType = request.getParameter("payType");
        String dateStart = request.getParameter("startDate");
        String dateEnd = request.getParameter("endDate");
        String localTimeStart = StringUtils.trim(request.getParameter("localTimeStart"));
        String dateTimeS = dateStart + localTimeStart;
        String localTimeEnd = StringUtils.trim(request.getParameter("localTimeEnd"));
        String dateTimeE = dateEnd + localTimeEnd;
        String merchantNo = request.getParameter("merchantNo");
        String brhId = request.getParameter("brhId");
        String transCode = request.getParameter("transCode");
        String repCode = request.getParameter("repCode");
        String orderNum = request.getParameter("orderNum");
        String payStatus = request.getParameter("payStatus");
        String merchantName = request.getParameter("merchantName");
        // 查询条件组串
        StringBuffer whereSql = new StringBuffer();

        if (StringUtils.isNotEmpty(dateStart)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                whereSql.append(" AND TRIM(tcord.ORDER_TIME) >= '" + dateTimeS + "' ");
            } else {
                whereSql.append(" AND substr(tcord.ORDER_TIME,0,8) >= '" + dateStart + "'");
            }
        }

        if (StringUtils.isNotEmpty(dateEnd)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                whereSql.append(" AND TRIM(tcord.ORDER_TIME) <= '" + dateTimeE + "' ");
            } else {
                whereSql.append(" AND substr(tcord.ORDER_TIME,0,8) <= '" + dateEnd + "'");
            }
        }
        //支付方式
        if (StringUtils.isNotEmpty(payType)) {
            if ("99".equals(payType)) {
                whereSql.append("");
            } else {
                findBySQLQuery = CommonFunction.getCommQueryDAO()
                        .findBySQLQuery(" select t.INFO_ID from t_pay_typedic t where t.pay_id = '" + payType + "' ");
                if (findBySQLQuery.size() > 0) {
                    whereSql.append(" and tcord.NOTICE_PAY_TYPE in (");
                    for (Object obj : findBySQLQuery) {
                        whereSql.append(" '" + obj + "',");
                    }
                    whereSql.deleteCharAt(whereSql.length() - 1);
                    whereSql.append(")");
                }
            }

            //            if (payType.equals("aliPay")) {
            //                payType = "1";
            //                whereSql.append(" and TRIM(tcord.NOTICE_PAY_TYPE) = '" + payType + "'");
            //            } else if (payType.equals("weChat")) {
            //                payType = "0";
            //                whereSql.append(" and TRIM(tcord.NOTICE_PAY_TYPE) = '" + payType + "'");
            //            } else if (payType.equals("qqPay")) {
            //                payType = "3";
            //                whereSql.append(" and TRIM(tcord.NOTICE_PAY_TYPE) = '" + payType + "'");
            //            } else if (payType.equals("scan")) {
            //                payType = "2";
            //                whereSql.append(" and TRIM(tcord.NOTICE_PAY_TYPE) = '" + payType + "'");
            //            } else {
            //                whereSql.append("");
            //            }
        }
        // 商户名称
        if (StringUtils.isNotEmpty(merchantName)) {
            whereSql.append(" and TRIM( tmcht.MCHT_NM) like '%" + merchantName + "%'");
        }
        //银联的商户号
        if (StringUtils.isNotEmpty(merchantNo)) {
            whereSql.append(" and TRIM(tcord.Yl_MCHNT_NO) like '%" + merchantNo + "%'");
        }
        //银联的终端号
        if (StringUtils.isNotEmpty(transCode)) {
            whereSql.append(" AND TRIM(tcord.YL_TERM_NO) like '%" + transCode + "%' ");
        }
        //归属机构
        if (StringUtils.isNotEmpty(brhId)) {
            whereSql.append(" AND TRIM(tmcht.AGR_BR)='" + brhId + "'");
        }
        //商户订单号
        if (StringUtils.isNotEmpty(repCode)) {
            whereSql.append(" AND TRIM(tcord.INFO_ID) like '%" + repCode + "%' ");
        }
        //终端订单号
        if (StringUtils.isNotEmpty(orderNum)) {
            whereSql.append(" AND TRIM(tcord.TERM_ORDER_ID) like '%" + orderNum + "%' ");
        }
        //支付状态
        if (StringUtils.isNotEmpty(payStatus)) {
            whereSql.append(" AND TRIM(tcord.NOTICE_STATUS) = '" + payStatus + "' ");
        }

        StringBuffer sb = new StringBuffer();
        // tcord.C_MCHNT_NO = tmcht.MAPPING_MCHNTCDONE or
        //   case when sa_txn_amt is null then 0 when sa_txn_amt  = ' ' then 0 else sa_txn_amt*1/100 end)
        sb.append(
                " select tbl.ORDER_TIME,tbl.MCHT_NM,tbl.AGR_BR as SETTLE_AREA_NO,tbl.Yl_MCHNT_NO,tbl.YL_TERM_NO,tbl.TERM_ORDER_ID,tbl.INFO_ID,tbl.ORDER_FEE,tbl.ORG_FEE, tbl.FEE,tpt.PAY_TYPE,tbl.NOTICE_STATUS,tbl.C_MCHNT_NO,NOTICE_ORDER_ID,brh.C_BRH_NAME,tbl.ORDER_RESP_TIME,tbl.AGR_BR,tbr.BRH_NAME,tbl.SELLERID,tbl.BUYERID,tbl.SELLER_EMAIL,tbl.BUYER_LOGON_ID "
                        + " from ( select tcord.INFO_ID,tcord.Yl_MCHNT_NO,tcord.YL_TERM_NO,tcord.C_MCHNT_NO,tcord.TERM_ORDER_ID,tcord.ORDER_TIME,case when tcord.ORDER_FEE ='0' then tcord.NOTICE_FEE else tcord.ORDER_FEE end as ORDER_FEE,tcord.ORDER_FEE as ORG_FEE,tcord.ORDER_RESP_TIME,tcord.NOTICE_PAY_TYPE,tcord.C_MCHNT_BRH,tcord.NOTICE_ORDER_ID,tmcht.AGR_BR,tmcht.MCHT_NM,tcord.NOTICE_STATUS,tcord.SELLERID,tcord.BUYERID,tcord.SELLER_EMAIL,tcord.BUYER_LOGON_ID, " 
                        + "tcord.share_ruleid, case when tcord.NOTICE_STATUS = '1' and tmsi.ACCT_SETTLE_LIMIT is not null then (tmsi.ACCT_SETTLE_LIMIT/100) * (tcord.ORDER_FEE/100) else null  end AS FEE from T_CORDERINFO tcord left join TBL_MCHT_BASE_INF tmcht  on ( tcord.YL_MCHNT_NO = tmcht.MAPPING_MCHNTCDTWO ) "
                        + " LEFT JOIN (	select tal.share_ruleid as share_ruleid,tal.sharetype as SETTLE_ACCT_NM,tal.mcht_shareval as settle_bank_nm_snd, "
                        +"tal.orgshareval_first as settle_bank_no_snd,tal.orgshareval_second as settle_acct_nm_snd,"  
                        +"tal.orgshareval_three as settle_acct_snd,tal.freeratio as acct_settle_limit "   
                        +"from TBL_ALIPAY_SHARE_PARAM tal ) tmsi ON tmsi.share_ruleid = tcord.share_ruleid "
                        + " where 1= 1 ");
        sb.append(whereSql);
        sb.append(
                " ) tbl left join TBL_BRH_INFO tbr  on tbl.AGR_BR = tbr.BRH_ID left join T_PAY_TYPEDIC tpt on tbl.NOTICE_PAY_TYPE=tpt.INFO_ID  "
                        + "left join T_BRH_DATADIC brh on tbl.C_MCHNT_BRH=brh.CORD_BRH_ID where tbr.BRH_ID in " + orgArrStr
                        + " order by tbl.ORDER_TIME desc");

        String countSql = "SELECT COUNT(1) FROM (" + sb.toString() + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        DecimalFormat df = new DecimalFormat("0.00");
        for (int i = 0; i < dataList.size(); i++) {
            Object[] obj = dataList.get(i);
            try {
                if (obj[0] != null && !obj[0].equals("")) {
                    Date tm = sdf.parse(obj[0].toString());
                    obj[0] = sdf2.format(tm);
                }
                if (obj[7] != null && !obj[7].equals("")) {
                    obj[7] = CommonFunction.transFenToYuan(obj[7].toString());
                } else {
                    obj[7] = CommonFunction.transFenToYuan("");
                }
                if (obj[8] != null && !obj[8].equals("")) {
                    obj[8] = CommonFunction.transFenToYuan(obj[8].toString());
                } else {
                    obj[8] = CommonFunction.transFenToYuan("");
                }
                if (obj[9] != null && !obj[9].equals("")) {
                	if("0.00".equals(df.format(obj[9]))){
                		obj[9]="";
                	}else{
                		obj[9] = df.format(obj[9]);
                	}
                }
                if (obj[11] == null || obj[11].equals("")) {
                    obj[11] = "";
                } else if (obj[11].equals("1")) {
                    obj[11] = "成功";
                } else if (obj[11].equals("0")) {
                    obj[11] = "失败";
                } else if (obj[11].equals("2")) {
                    if (obj[7] != null && !obj[7].equals("") && !obj[7].equals("0")) {
                        obj[11] = "暂未支付";
                    } else {
                        obj[11] = "退款";
                    }
                } else if (obj[11].equals("3")) {
                    obj[11] = "退款成功";
                } else {
                    obj[11] = "";
                }
                if (obj[15] != null && !obj[15].equals("")) {
                    Date tm = sdf.parse(obj[15].toString());
                    obj[15] = sdf2.format(tm);
                }
                dataList.set(i, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 交易流水查询（银联）
     * //TODO 添加方法功能描述
     *
     * @param begin
     * @param request
     * @return
     * @author hanyongqing
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTxnInfoTxn(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String payType = request.getParameter("payType");
        String dateStart = request.getParameter("startDate");
        String dateEnd = request.getParameter("endDate");
        String localTimeStart = StringUtils.trim(request.getParameter("localTimeStart"));
        String dateTimeS = dateStart + localTimeStart;
        String localTimeEnd = StringUtils.trim(request.getParameter("localTimeEnd"));
        String dateTimeE = dateEnd + localTimeEnd;
        String merchantNo = request.getParameter("merchantNo");
        String transCode = request.getParameter("transCode");
        String repCode = request.getParameter("repCode");
        String merchantName = request.getParameter("merchantName");
        String brhId = request.getParameter("brhId");
        // 查询条件组串
        StringBuffer whereSql = new StringBuffer();

        if (StringUtils.isNotEmpty(dateStart)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                whereSql.append(" AND TRIM(txn.INST_DATE) >= '" + dateTimeS + "' ");
            } else {
                whereSql.append(" AND substr(txn.INST_DATE,0,8) >= '" + dateStart + "'");
            }
        }

        if (StringUtils.isNotEmpty(dateEnd)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                whereSql.append(" AND TRIM(txn.INST_DATE) <= '" + dateTimeE + "' ");
            } else {
                whereSql.append(" AND substr(txn.INST_DATE,0,8) <= '" + dateEnd + "'");
            }
        }
        //商户名称
        if (StringUtils.isNotEmpty(merchantName)) {
            whereSql.append(" and TRIM(base.MCHT_NM) like '%" + merchantName + "%'");
        }
        //归属机构
        if (StringUtils.isNotEmpty(brhId)) {
            whereSql.append(" AND TRIM(tbl.AGR_BR)='" + brhId + "'");
        }
        //卡号
        if (StringUtils.isNotEmpty(merchantNo)) {
            whereSql.append(" and TRIM(term.MAPPING_MCHNTCDTWO) like '%" + merchantNo + "%'");
        }
        //终端号
        if (StringUtils.isNotEmpty(transCode)) {
            whereSql.append(" AND TRIM(txn.CARD_ACCP_TERM_ID) like '%" + transCode + "%' ");
        }

        //银联卡号
        if (StringUtils.isNotEmpty(repCode)) {
            whereSql.append(
                    " AND ( TRIM(txn.PAN) like '%" + repCode.replace(" ", "")
                            + "%' or TRIM((substr( trim(txn.PAN), 1, 6) || lpad('*', length(trim(txn.PAN))-10, '*') || substr(trim(txn.PAN), -4))) like '%"
                            + repCode.replace(" ", "") + "%')  ");
        }

        StringBuffer sb = new StringBuffer();
        sb.append(
                "select tbl.INST_DATE,tbl.MAPPING_MCHNTCDTWO,tbl.CARD_ACCP_TERM_ID,tbl.SYS_SEQ_NUM,tbl.AMT_TRANS,tbl.AMT_SETTLMT,tbl.PAN,tbl.TRANS_TYPE,tbl.REVSAL_FLAG,tbl.RESP_CODE,tbl.TRANS_STATE,tbl.MARKSUBSIDY_AMOUNT,tbl.SUBSIDY_RULE,tbl.AGR_BR,tbl.MCHT_NM,tbl.AGR_BR as SETTLE_AREA_NO,brh.BRH_NAME from  "
                        + " ( select txn.INST_DATE,txn.SYS_SEQ_NUM,txn.TRANS_TYPE,txn.TRANS_STATE,txn.MARKSUBSIDY_AMOUNT,mark.SUBSIDY_RULE,mark.SUBSIDY_TYPE,txn.AMT_TRANS,txn.AMT_SETTLMT,(substr( trim(txn.PAN), 1, 6) || lpad('*', length(trim(txn.PAN))-10, '*') || substr(trim(txn.PAN), -4)) as PAN,txn.REVSAL_FLAG,txn.RESP_CODE,txn.CARD_ACCP_TERM_ID,term.MAPPING_MCHNTCDTWO,base.AGR_BR,base.MCHT_NM from TBL_N_TXN txn  "
                        + " left join TBL_TERM_INF term on txn.CARD_ACCP_TERM_ID = term.MAPPING_TERMIDTWO left join TBL_MCHT_BASE_INF base on term.MAPPING_MCHNTCDTWO = base.MAPPING_MCHNTCDTWO left join Tbl_MCHT_MARKSUBSIDY mark on txn.RULE_ID = mark.RULE_ID  where 1=1 ");
        sb.append(whereSql);
        sb.append(" ) tbl left join TBL_BRH_INFO brh on tbl.AGR_BR = brh.BRH_ID where brh.BRH_ID in " + orgArrStr + " order by tbl.INST_DATE desc ");

        String countSql = "SELECT COUNT(1) FROM (" + sb.toString() + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        for (int i = 0; i < dataList.size(); i++) {
            Object[] obj = dataList.get(i);
            try {
                if (obj[0] != null && !obj[0].equals("")) {
                    Date tm = sdf.parse(obj[0].toString());
                    obj[0] = sdf2.format(tm);
                }
                if (obj[4] != null && !obj[4].equals("")) {
                    obj[4] = CommonFunction.transFenToYuan(obj[4].toString());
                } else {
                    obj[4] = CommonFunction.transFenToYuan("");
                }
                if (obj[5] != null && !obj[5].equals("")) {
                    obj[5] = CommonFunction.transFenToYuan(obj[5].toString());
                } else {
                    obj[5] = CommonFunction.transFenToYuan("");
                }
                if (obj[7] != null && !obj[7].equals("")) {
                    String midStr = obj[7].toString();
                    if (midStr.equals("P")) {
                        obj[7] = "消费";
                    } else if (midStr.equals("PP")) {
                        obj[7] = "消费(已冲正)";
                    } else if (midStr.equals("Y")) {
                        obj[7] = "预授权";
                    } else if (midStr.equals("X")) {
                        obj[7] = "撤销";
                    } else if (midStr.equals("W")) {
                        obj[7] = "预授权完成";
                    } else if (midStr.equals("R")) {
                        obj[7] = "冲正";
                    } else {
                        obj[7] = "未知";
                    }
                } else {
                    obj[7] = "未知";
                }
                //                交易状态
                //                0：请求中,1：成功，2：银联/贷记卡/借记卡/外卡拒绝，3：超时，4：主机拒绝/中间业务平台，5：PIN/MAC错，6：前置拒绝，7：记账中/发送中间业务平台中，8：记账超时/中间业务平台应答超时
                //                A：EPOS终端拒绝 P:权利系统拒绝
                if (obj[10] != null && !obj[10].equals("")) {
                    String midStr = obj[10].toString();
                    if (midStr.equals("0")) {
                        obj[10] = "请求中";
                    } else if (midStr.equals("1")) {
                        obj[10] = "成功";
                    } else if (midStr.equals("2")) {
                        obj[10] = "银联卡拒绝";
                    } else if (midStr.equals("3")) {
                        obj[10] = "超时";
                    } else if (midStr.equals("4")) {
                        obj[10] = "主机拒绝";
                    } else if (midStr.equals("5")) {
                        obj[10] = "PIN/MAC错";
                    } else if (midStr.equals("6")) {
                        obj[10] = "前置拒绝";
                    } else if (midStr.equals("7")) {
                        obj[10] = "记账中";
                    } else if (midStr.equals("8")) {
                        obj[10] = "记账超时";
                    } else if (midStr.equals("A")) {
                        obj[10] = "EPOS终端拒绝 ";
                    } else if (midStr.equals("P")) {
                        obj[10] = "权利系统拒绝 ";
                    }
                }
                if (obj[11] != null && !obj[11].equals("")) {
                    obj[11] = CommonFunction.transFenToYuan(obj[11].toString());
                } else {
                    obj[11] = CommonFunction.transFenToYuan("");
                }
                if (obj[16] != null && !obj[16].equals("")) {
                    if (obj[16].equals("00")) {
                        obj[16] = "满额补";
                        if (obj[12] != null && !obj[12].equals("")) {
                            String trFToY = CommonFunction.transFenToYuan(obj[17].toString().substring(0, 12));
                            String trFToY2 = CommonFunction.transFenToYuan(obj[17].toString().substring(12, 24));
                            obj[12] = "每笔满" + trFToY + "元补" + trFToY2 + "元";
                        }
                    } else if (obj[16].equals("01")) {
                        obj[16] = "按比例补";
                        if (obj[12] != null && !obj[12].equals("")) {
                            int parseInt = Integer.parseInt(obj[12].toString().substring(0, 3));
                            String trFToY2 = CommonFunction.transFenToYuan(obj[12].toString().substring(3, 15));
                            obj[12] = "每笔按比例补" + parseInt + "%，最高补" + trFToY2 + "元";
                        }
                    } else {
                        obj[12] = "未知";
                    }
                }
                dataList.set(i, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 交易流水查询（所有：包括支付宝、微信、银联）
     * //TODO 添加方法功能描述
     *
     * @param begin
     * @param request
     * @return
     * @author hanyongqing
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTxnInfoAll(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String payType = request.getParameter("payType");
        String dateStart = request.getParameter("startDate");
        String dateEnd = request.getParameter("endDate");
        String localTimeStart = StringUtils.trim(request.getParameter("localTimeStart"));
        String dateTimeS = dateStart + localTimeStart;
        String localTimeEnd = StringUtils.trim(request.getParameter("localTimeEnd"));
        String dateTimeE = dateEnd + localTimeEnd;
        String merchantNo = request.getParameter("merchantNo");
        String transCode = request.getParameter("transCode");
        String repCode = request.getParameter("repCode");

        // 查询条件组串
        StringBuffer whereSql = new StringBuffer();

        if (StringUtils.isNotEmpty(dateStart)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                whereSql.append(" AND TRIM(txn.INST_DATE) >= '" + dateTimeS + "' ");
            } else {
                whereSql.append(" AND substr(txn.INST_DATE,0,8) >= '" + dateStart + "'");
            }
        }

        if (StringUtils.isNotEmpty(dateEnd)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                whereSql.append(" AND TRIM(txn.INST_DATE) <= '" + dateTimeE + "' ");
            } else {
                whereSql.append(" AND substr(txn.INST_DATE,0,8) <= '" + dateEnd + "'");
            }
        }
        //银联商户号
        if (StringUtils.isNotEmpty(merchantNo)) {
            whereSql.append(" and TRIM(term.MAPPING_MCHNTCDTWO) like '%" + merchantNo + "%'");
        }
        //银联终端号
        if (StringUtils.isNotEmpty(transCode)) {
            whereSql.append(" AND TRIM(txn.CARD_ACCP_TERM_ID) like '%" + transCode + "%' ");
        }

        StringBuffer sb = new StringBuffer();
        sb.append(
                " (  select tbl.INST_DATE as TRADE_TIME,tbl.MAPPING_MCHNTCDTWO as YL_MCHNT_NO,tbl.CARD_ACCP_TERM_ID as YL_TERM_NO,tbl.PAN as ACCOUNT_NUM,'' as MCHNT_NUM,tbl.SYS_SEQ_NUM as REPLEN_NUM,tbl.AMT_TRANS as TRADE_MON,'银联' as PAY_TYPE,base.AGR_BR as AGR_BR,base.MCHT_NM as MCHT_NM,brh.BRH_NAME as BRH_NAME "
                        + " from  ( select txn.INST_DATE,(substr( trim(txn.PAN), 1, 6) || lpad('*', length(trim(txn.PAN))-10, '*') || substr(trim(txn.PAN), -4)) as PAN,txn.SYS_SEQ_NUM,txn.AMT_TRANS,txn.CARD_ACCP_TERM_ID,term.MAPPING_MCHNTCDTWO from TBL_N_TXN txn left join TBL_TERM_INF term on txn.CARD_ACCP_TERM_ID = term.MAPPING_TERMIDTWO "
                        + " where 1=1 ");
        sb.append(whereSql);
        sb.append(
                ") tbl left join TBL_MCHT_BASE_INF base on tbl.MAPPING_MCHNTCDTWO = base.MAPPING_MCHNTCDTWO left join TBL_BRH_INFO brh on base.AGR_BR = brh.BRH_ID where brh.BRH_ID in "
                        + orgArrStr + " ) ");
        sb.append("union all ");

        // 查询条件组串
        StringBuffer whereSql2 = new StringBuffer();

        if (StringUtils.isNotEmpty(dateStart)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                whereSql2.append(" AND TRIM(tco.ORDER_TIME) >= '" + dateTimeS + "' ");
            } else {
                whereSql2.append(" AND substr(tco.ORDER_TIME,0,8) >= '" + dateStart + "'");
            }
        }

        if (StringUtils.isNotEmpty(dateEnd)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                whereSql2.append(" AND TRIM(tco.ORDER_TIME) <= '" + dateTimeE + "' ");
            } else {
                whereSql2.append(" AND substr(tco.ORDER_TIME,0,8) <= '" + dateEnd + "'");
            }
        }

        //银联商户号
        if (StringUtils.isNotEmpty(merchantNo)) {
            whereSql2.append(" and TRIM(tco.YL_MCHNT_NO) like '%" + merchantNo + "%'");
        }
        //银联终端号
        if (StringUtils.isNotEmpty(transCode)) {
            whereSql2.append(" AND TRIM(tco.YL_TERM_NO) like '%" + transCode + "%' ");
        }

        StringBuffer sb2 = new StringBuffer();
        sb2.append(
                " (   select tbl.ORDER_TIME as TRADE_TIME,tbl.YL_MCHNT_NO as YL_MCHNT_NO,tbl.YL_TERM_NO as YL_TERM_NO,'' as ACCOUNT_NUM,tbl.INFO_ID as MCHNT_NUM,tbl.TERM_ORDER_ID as REPLEN_NUM,tbl.ORDER_FEE as TRADE_MON,tbl.NOTICE_PAY_TYPE as PAY_TYPE,tbl.AGR_BR as AGR_BR,tbl.MCHT_NM as MCHT_NM,brh.BRH_NAME as BRH_NAME  "
                        + " from  ( select tco.ORDER_TIME,tco.YL_MCHNT_NO,tco.YL_TERM_NO,tco.C_MCHNT_NO,tco.INFO_ID,tco.TERM_ORDER_ID,tco.ORDER_FEE,tco.NOTICE_PAY_TYPE,mcht.AGR_BR,mcht.MCHT_NM from T_CORDERINFO tco left join TBL_MCHT_BASE_INF mcht on  tco.YL_MCHNT_NO = mcht.MAPPING_MCHNTCDTWO "
                        + " where 1 = 1 ");
        sb2.append(whereSql2);
        sb2.append(" ) tbl left join TBL_BRH_INFO brh on tbl.AGR_BR = brh.BRH_ID where brh.BRH_ID in " + orgArrStr + " ) ");
        sb.append(sb2);
        String countSql = "SELECT COUNT(1) FROM ( " + sb.toString() + ") tbl";
        sb.append(" order by TRADE_TIME desc ");

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        for (int i = 0; i < dataList.size(); i++) {
            Object[] obj = dataList.get(i);
            try {
                // 时间转换
                if (obj[0] != null && obj[0] != "") {
                    Date tm = sdf.parse(obj[0].toString());
                    obj[0] = sdf2.format(tm);
                }
                // 金额转换
                if (obj[6] != null && obj[6] != "") {
                    int _parseInt = Integer.parseInt(obj[6].toString());
                    obj[6] = _parseInt / 100.0;
                }
                // 支付方式
                if (obj[7] != null && obj[7] != "") {
                    if (obj[7].equals("0")) {
                        obj[7] = "微信";
                    } else if (obj[7].equals("1")) {
                        obj[7] = "支付宝";
                    }
                } else {
                    obj[7] = "支付宝或微信(未支付)";
                }
                dataList.set(i, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 交易流水信息小计
     * //TODO 添加方法功能描述
     *
     * @param begin
     * @param request
     * @return
     * @author hanyongqing
     */
    @SuppressWarnings("unchecked")
    public static Object[] getStatSumInfo(int begin, HttpServletRequest request) {
        List<Object[]> findBySQLQuery = null;
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String staType = request.getParameter("staType");
        String payType = request.getParameter("payType");

        String dateStart = request.getParameter("startDate");
        String dateEnd = request.getParameter("endDate");
        String dateTimeS = dateStart + StringUtils.trim(request.getParameter("localTimeStart"));
        String dateTimeE = dateEnd + StringUtils.trim(request.getParameter("localTimeEnd"));

        String ylMerchantNo = request.getParameter("ylMerchantNo");
        String ylTermNo = request.getParameter("ylTermNo");
        // 查询条件组串
        StringBuffer sb = new StringBuffer();
        StringBuffer sbL = new StringBuffer();
        String countSql = "";

        if (payType.equals("5")) {
            sbL.delete(0, sb.length());
            StringBuffer whereSql = new StringBuffer();
            if (StringUtils.isNotEmpty(dateStart)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                    whereSql.append(" AND TRIM(txn.INST_DATE) >= '" + dateTimeS + "' ");
                } else {
                    whereSql.append(" AND substr(txn.INST_DATE,0,8) >= '" + dateStart + "'");
                }
            }

            if (StringUtils.isNotEmpty(dateEnd)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                    whereSql.append(" AND TRIM(txn.INST_DATE) <= '" + dateTimeE + "' ");
                } else {
                    whereSql.append(" AND substr(txn.INST_DATE,0,8) <= '" + dateEnd + "'");
                }
            }
            //银联商户号
            if (StringUtils.isNotEmpty(ylMerchantNo)) {
                whereSql.append(" and TRIM(term.MAPPING_MCHNTCDTWO) like '%" + ylMerchantNo + "%'");
            }
            //银联终端号
            if (StringUtils.isNotEmpty(ylTermNo)) {
                whereSql.append(" AND TRIM(txn.CARD_ACCP_TERM_ID) like '%" + ylTermNo + "%' ");
            }
            if (staType.equals("M")) {
                sbL.append(
                        " select MAPPING_MCHNTCDTWO as MCHNTNO,'' as TERMNO,sum(AMT_TRANS) as TOTALMON,count(1) as TOTALNUM  from ( select txn.INST_DATE,to_number(txn.AMT_TRANS) as AMT_TRANS,txn.CARD_ACCP_TERM_ID,base.MAPPING_MCHNTCDTWO from tbl_n_txn txn "
                                + " left join tbl_mcht_base_inf base on txn.CARD_ACCP_ID = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and "
                                + "  txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
                sbL.append(whereSql);
                sbL.append(" ) tbl  group by MAPPING_MCHNTCDTWO ");
            } else if (staType.equals("T")) {
                sbL.append(
                        " select MAPPING_MCHNTCDTWO as MCHNTNO,CARD_ACCP_TERM_ID as TERMNO,sum(AMT_TRANS) as TOTALMON,count(1) as TOTALNUM  from ( select txn.INST_DATE,to_number(txn.AMT_TRANS) as AMT_TRANS,txn.CARD_ACCP_TERM_ID,base.MAPPING_MCHNTCDTWO from tbl_n_txn txn left join tbl_mcht_base_inf base on txn.CARD_ACCP_ID = base.MAPPING_MCHNTCDTWO  "
                                + "left join tbl_term_inf term on txn.CARD_ACCP_ID=term.MAPPING_MCHNTCDTWO and txn.CARD_ACCP_TERM_ID = term.MAPPING_TERMIDTWO  left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and "
                                + " txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
                sbL.append(whereSql);
                sbL.append(" ) tbl  group by MAPPING_MCHNTCDTWO,CARD_ACCP_TERM_ID ");
            } else {
                sbL.append(
                        " select MAPPING_MCHNTCDTWO as MCHNTNO,'' as TERMNO,sum(AMT_TRANS) as TOTALMON,count(1) as TOTALNUM  from ( select txn.INST_DATE,to_number(txn.AMT_TRANS) as AMT_TRANS,txn.CARD_ACCP_TERM_ID,base.MAPPING_MCHNTCDTWO from tbl_n_txn txn "
                                + " left join tbl_mcht_base_inf base on txn.CARD_ACCP_ID = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and "
                                + " txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
                sbL.append(whereSql);
                sbL.append(" ) tbl  group by MAPPING_MCHNTCDTWO ");
            }

            StringBuffer sb2 = new StringBuffer();
            StringBuffer whereSql2 = new StringBuffer();
            if (StringUtils.isNotEmpty(dateStart)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                    whereSql2.append(" AND TRIM(cord.ORDER_TIME) >= '" + dateTimeS + "' ");
                } else {
                    whereSql2.append(" AND substr(cord.ORDER_TIME,0,8) >= '" + dateStart + "'");
                }
            }

            if (StringUtils.isNotEmpty(dateEnd)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                    whereSql2.append(" AND TRIM(cord.ORDER_TIME) <= '" + dateTimeE + "' ");
                } else {
                    whereSql2.append(" AND substr(cord.ORDER_TIME,0,8) <= '" + dateEnd + "'");
                }
            }
            //银联商户号
            if (StringUtils.isNotEmpty(ylMerchantNo)) {
                whereSql2.append(" and TRIM(cord.YL_MCHNT_NO) like '%" + ylMerchantNo + "%'");
            }
            //银联终端号
            if (StringUtils.isNotEmpty(ylTermNo)) {
                whereSql2.append(" AND TRIM(cord.YL_TERM_NO) like '%" + ylTermNo + "%' ");
            }
            whereSql2.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '7' ");
            if (staType.equals("M")) {
                sb2.append(
                        " select YL_MCHNT_NO as MCHNTNO,'' as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select cord.ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb2.append(whereSql2);
                sb2.append(" ) tbl  group by YL_MCHNT_NO ");
            } else if (staType.equals("T")) {
                sb2.append(
                        " select YL_MCHNT_NO as MCHNTNO,YL_TERM_NO as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select cord.ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb2.append(whereSql2);
                sb2.append(" ) tbl  group by YL_MCHNT_NO,YL_TERM_NO ");
            } else {
                sb2.append(
                        " select YL_MCHNT_NO as MCHNTNO,'' as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select cord.ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb2.append(whereSql2);
                sb2.append(" ) tbl  group by YL_MCHNT_NO ");
            }

            sb.append("  select MCHNTNO,TERMNO,sum(TOTALMON) as TOTALMON ,sum(TOTALNUM) as TOTALNUM from ( (");
            sb.append(sbL);
            sb.append(" ) union all ( ");
            sb.append(sb2 + " ) ) tbl group by MCHNTNO,TERMNO ");

            countSql = "SELECT COUNT(1) FROM ( " + sb.toString() + ") tb";
        } else if ("99".equals(payType)) {
            sbL.delete(0, sb.length());
            StringBuffer whereSql = new StringBuffer();
            if (StringUtils.isNotEmpty(dateStart)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                    whereSql.append(" AND TRIM(txn.INST_DATE) >= '" + dateTimeS + "' ");
                } else {
                    whereSql.append(" AND substr(txn.INST_DATE,0,8) >= '" + dateStart + "'");
                }
            }

            if (StringUtils.isNotEmpty(dateEnd)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                    whereSql.append(" AND TRIM(txn.INST_DATE) <= '" + dateTimeE + "' ");
                } else {
                    whereSql.append(" AND substr(txn.INST_DATE,0,8) <= '" + dateEnd + "'");
                }
            }
            //银联商户号
            if (StringUtils.isNotEmpty(ylMerchantNo)) {
                whereSql.append(" and TRIM(term.MAPPING_MCHNTCDTWO) like '%" + ylMerchantNo + "%'");
            }
            //银联终端号
            if (StringUtils.isNotEmpty(ylTermNo)) {
                whereSql.append(" AND TRIM(txn.CARD_ACCP_TERM_ID) like '%" + ylTermNo + "%' ");
            }
            if (staType.equals("M")) {
                sbL.append(
                        " select MAPPING_MCHNTCDTWO as MCHNTNO,'' as TERMNO,sum(AMT_TRANS) as TOTALMON,count(1) as TOTALNUM  from ( select txn.INST_DATE,to_number(txn.AMT_TRANS) as AMT_TRANS,txn.CARD_ACCP_TERM_ID,base.MAPPING_MCHNTCDTWO from tbl_n_txn txn "
                                + " left join tbl_mcht_base_inf base on txn.CARD_ACCP_ID = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and "
                                + "  txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
                sbL.append(whereSql);
                sbL.append(" ) tbl  group by MAPPING_MCHNTCDTWO ");
            } else if (staType.equals("T")) {
                sbL.append(
                        " select MAPPING_MCHNTCDTWO as MCHNTNO,CARD_ACCP_TERM_ID as TERMNO,sum(AMT_TRANS) as TOTALMON,count(1) as TOTALNUM  from ( select txn.INST_DATE,to_number(txn.AMT_TRANS) as AMT_TRANS,txn.CARD_ACCP_TERM_ID,base.MAPPING_MCHNTCDTWO from tbl_n_txn txn left join tbl_mcht_base_inf base on txn.CARD_ACCP_ID = base.MAPPING_MCHNTCDTWO  "
                                + "left join tbl_term_inf term on txn.CARD_ACCP_ID=term.MAPPING_MCHNTCDTWO and txn.CARD_ACCP_TERM_ID = term.MAPPING_TERMIDTWO  left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and "
                                + " txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
                sbL.append(whereSql);
                sbL.append(" ) tbl  group by MAPPING_MCHNTCDTWO,CARD_ACCP_TERM_ID ");
            } else {
                sbL.append(
                        " select MAPPING_MCHNTCDTWO as MCHNTNO,'' as TERMNO,sum(AMT_TRANS) as TOTALMON,count(1) as TOTALNUM  from ( select txn.INST_DATE,to_number(txn.AMT_TRANS) as AMT_TRANS,txn.CARD_ACCP_TERM_ID,base.MAPPING_MCHNTCDTWO from tbl_n_txn txn "
                                + " left join tbl_mcht_base_inf base on txn.CARD_ACCP_ID = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and "
                                + " txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
                sbL.append(whereSql);
                sbL.append(" ) tbl  group by MAPPING_MCHNTCDTWO ");
            }

            StringBuffer sb2 = new StringBuffer();
            StringBuffer whereSql2 = new StringBuffer();
            if (StringUtils.isNotEmpty(dateStart)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                    whereSql2.append(" AND TRIM(cord.ORDER_TIME) >= '" + dateTimeS + "' ");
                } else {
                    whereSql2.append(" AND substr(cord.ORDER_TIME,0,8) >= '" + dateStart + "'");
                }
            }

            if (StringUtils.isNotEmpty(dateEnd)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                    whereSql2.append(" AND TRIM(cord.ORDER_TIME) <= '" + dateTimeE + "' ");
                } else {
                    whereSql2.append(" AND substr(cord.ORDER_TIME,0,8) <= '" + dateEnd + "'");
                }
            }
            //银联商户号
            if (StringUtils.isNotEmpty(ylMerchantNo)) {
                whereSql2.append(" and TRIM(cord.YL_MCHNT_NO) like '%" + ylMerchantNo + "%'");
            }
            //银联终端号
            if (StringUtils.isNotEmpty(ylTermNo)) {
                whereSql2.append(" AND TRIM(cord.YL_TERM_NO) like '%" + ylTermNo + "%' ");
            }
            //            if (payType.equals("weChat")) {
            //                whereSql2.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '0' ");
            //            } else if (payType.equals("aliPay")) {
            //                whereSql2.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '1' ");
            //            } else if (payType.equals("qqPay")) {
            //                whereSql2.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '3' ");
            //            }
            if (staType.equals("M")) {
                sb2.append(
                        " select YL_MCHNT_NO as MCHNTNO,'' as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select cord.ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb2.append(whereSql2);
                sb2.append(" ) tbl  group by YL_MCHNT_NO ");
            } else if (staType.equals("T")) {
                sb2.append(
                        " select YL_MCHNT_NO as MCHNTNO,YL_TERM_NO as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select cord.ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb2.append(whereSql2);
                sb2.append(" ) tbl  group by YL_MCHNT_NO,YL_TERM_NO ");
            } else {
                sb2.append(
                        " select YL_MCHNT_NO as MCHNTNO,'' as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select cord.ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb2.append(whereSql2);
                sb2.append(" ) tbl  group by YL_MCHNT_NO ");
            }

            sb.append("  select MCHNTNO,TERMNO,sum(TOTALMON) as TOTALMON ,sum(TOTALNUM) as TOTALNUM from ( (");
            sb.append(sbL);
            sb.append(" ) union all ( ");
            sb.append(sb2 + " ) ) tbl group by MCHNTNO,TERMNO ");

            countSql = "SELECT COUNT(1) FROM ( " + sb.toString() + ") tb";
        } else {
            sb.delete(0, sb.length());
            StringBuffer whereSql = new StringBuffer();

            if (StringUtils.isNotEmpty(dateStart)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                    whereSql.append(" AND TRIM(cord.ORDER_TIME) >= '" + dateTimeS + "' ");
                } else {
                    whereSql.append(" AND substr(cord.ORDER_TIME,0,8) >= '" + dateStart + "'");
                }
            }

            if (StringUtils.isNotEmpty(dateEnd)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                    whereSql.append(" AND TRIM(cord.ORDER_TIME) <= '" + dateTimeE + "' ");
                } else {
                    whereSql.append(" AND substr(cord.ORDER_TIME,0,8) <= '" + dateEnd + "'");
                }
            }

            //银联商户号
            if (StringUtils.isNotEmpty(ylMerchantNo)) {
                whereSql.append(" and TRIM(cord.YL_MCHNT_NO) like '%" + ylMerchantNo + "%'");
            }
            //银联终端号
            if (StringUtils.isNotEmpty(ylTermNo)) {
                whereSql.append(" AND TRIM(cord.YL_TERM_NO) like '%" + ylTermNo + "%' ");
            }
            //            if (payType.equals("weChat")) {
            //                whereSql.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '0' ");
            //            } else if (payType.equals("aliPay")) {
            //                whereSql.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '1' ");
            //            } else if (payType.equals("qqPay")) {
            //                whereSql.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '3' ");
            //            } else if (payType.equals("scanpay")) {
            //                whereSql.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '2' ");
            //            }
            if (StringUtils.isNotEmpty(payType)) {
                findBySQLQuery = CommonFunction.getCommQueryDAO()
                        .findBySQLQuery(" select t.INFO_ID from t_pay_typedic t where t.pay_id = '" + payType + "' ");
                if (findBySQLQuery.size() > 0) {
                    whereSql.append(" and cord.NOTICE_PAY_TYPE in (");
                    for (Object obj : findBySQLQuery) {
                        whereSql.append(" '" + obj + "',");
                    }
                    whereSql.deleteCharAt(whereSql.length() - 1);
                    whereSql.append(")");
                }
            }
            if (staType.equals("M")) {
                sb.append(
                        " select YL_MCHNT_NO as MCHNTNO,'' as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select cord.ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb.append(whereSql);
                sb.append(" ) tbl  group by YL_MCHNT_NO ");
                countSql = "SELECT COUNT(1) FROM ( " + sb.toString() + ") tb";
            } else if (staType.equals("T")) {
                sb.append(
                        " select YL_MCHNT_NO as MCHNTNO,YL_TERM_NO as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select cord.ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb.append(whereSql);
                sb.append(" ) tbl  group by YL_MCHNT_NO,YL_TERM_NO ");
                countSql = "SELECT COUNT(1) FROM ( " + sb.toString() + ") tb";
            } else {
                sb.append(
                        " select YL_MCHNT_NO as MCHNTNO,'' as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select cord.ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb.append(whereSql);
                sb.append(" ) tbl  group by YL_MCHNT_NO ");
                countSql = "SELECT COUNT(1) FROM ( " + sb.toString() + ") tb";
            }
        }
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        for (int i = 0; i < dataList.size(); i++) {
            Object[] obj = dataList.get(i);
            try {
                // 金额转换
                if (obj[2] != null && !obj[2].equals("")) {
                    obj[2] = CommonFunction.transFenToYuan(obj[2].toString());
                } else {
                    obj[2] = CommonFunction.transFenToYuan("");
                }
                dataList.set(i, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 交易流水信息总金额
     * //TODO 添加方法功能描述
     *
     * @param begin
     * @param request
     * @return
     * @author hanyongqing
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTotalSumInfo(int begin, HttpServletRequest request) {
        List<Object[]> findBySQLQuery = null;
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String staType = request.getParameter("staType");
        String payType = request.getParameter("payType");
        String dateStart = request.getParameter("startDate");
        String dateEnd = request.getParameter("endDate");
        String dateTimeS = dateStart + StringUtils.trim(request.getParameter("localTimeStart"));
        String dateTimeE = dateEnd + StringUtils.trim(request.getParameter("localTimeEnd"));
        String ylMerchantNo = request.getParameter("ylMerchantNo");
        String ylTermNo = request.getParameter("ylTermNo");
        // 查询条件组串
        StringBuffer sb = new StringBuffer();
        StringBuffer sbL = new StringBuffer();
        //      String countSql = "";
        if (payType.equals("5")) {
            sbL.delete(0, sb.length());
            StringBuffer whereSql = new StringBuffer();
            if (StringUtils.isNotEmpty(dateStart)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                    whereSql.append(" AND TRIM(txn.INST_DATE) >= '" + dateTimeS + "' ");
                } else {
                    whereSql.append(" AND substr(txn.INST_DATE,0,8) >= '" + dateStart + "'");
                }
            }

            if (StringUtils.isNotEmpty(dateEnd)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                    whereSql.append(" AND TRIM(txn.INST_DATE) <= '" + dateTimeE + "' ");
                } else {
                    whereSql.append(" AND substr(txn.INST_DATE,0,8) <= '" + dateEnd + "'");
                }
            }
            //银联商户号
            if (StringUtils.isNotEmpty(ylMerchantNo)) {
                whereSql.append(" and TRIM(base.MAPPING_MCHNTCDTWO) like '%" + ylMerchantNo + "%'");
            }
            //银联终端号
            if (StringUtils.isNotEmpty(ylTermNo)) {
                whereSql.append(" AND TRIM(txn.CARD_ACCP_TERM_ID) like '%" + ylTermNo + "%' ");
            }
            if (staType.equals("M")) {
                sbL.append(
                        " select MAPPING_MCHNTCDTWO as MCHNTNO,'' as TERMNO,sum(AMT_TRANS) as TOTALMON,count(1) as TOTALNUM  from ( select txn.INST_DATE,to_number(txn.AMT_TRANS) as AMT_TRANS,txn.CARD_ACCP_TERM_ID,base.MAPPING_MCHNTCDTWO from tbl_n_txn txn   "
                                + " left join tbl_mcht_base_inf base on txn.CARD_ACCP_ID = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and "
                                + " txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
                sbL.append(whereSql);
                sbL.append(" ) tbl  group by MAPPING_MCHNTCDTWO ");
            } else if (staType.equals("T")) {
                sbL.append(
                        " select MAPPING_MCHNTCDTWO as MCHNTNO,CARD_ACCP_TERM_ID as TERMNO,sum(AMT_TRANS) as TOTALMON,count(1) as TOTALNUM  from ( select txn.INST_DATE,to_number(txn.AMT_TRANS) as AMT_TRANS,txn.CARD_ACCP_TERM_ID,base.MAPPING_MCHNTCDTWO from tbl_n_txn txn "
                                + " left join tbl_term_inf term on txn.CARD_ACCP_TERM_ID = term.MAPPING_TERMIDTWO  and txn.CARD_ACCP_ID=term.MAPPING_MCHNTCDTWO"
                                + " left join tbl_mcht_base_inf base on txn.CARD_ACCP_ID = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and "
                                + " txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
                sbL.append(whereSql);
                sbL.append(" ) tbl  group by MAPPING_MCHNTCDTWO,CARD_ACCP_TERM_ID ");
            } else {
                sbL.append(
                        " select MAPPING_MCHNTCDTWO as MCHNTNO,'' as TERMNO,sum(AMT_TRANS) as TOTALMON,count(1) as TOTALNUM  from ( select txn.INST_DATE,to_number(txn.AMT_TRANS) as AMT_TRANS,txn.CARD_ACCP_TERM_ID,base.MAPPING_MCHNTCDTWO from tbl_n_txn txn  "
                                + " left join tbl_mcht_base_inf base on txn.CARD_ACCP_ID = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and "
                                + " txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
                sbL.append(whereSql);
                sbL.append(" ) tbl  group by MAPPING_MCHNTCDTWO ");
            }

            StringBuffer sb2 = new StringBuffer();
            StringBuffer whereSql2 = new StringBuffer();
            if (StringUtils.isNotEmpty(dateStart)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                    whereSql2.append(" AND TRIM(cord.ORDER_TIME) >= '" + dateTimeS + "' ");
                } else {
                    whereSql2.append(" AND substr(cord.ORDER_TIME,0,8) >= '" + dateStart + "'");
                }
            }

            if (StringUtils.isNotEmpty(dateEnd)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                    whereSql2.append(" AND TRIM(cord.ORDER_TIME) <= '" + dateTimeE + "' ");
                } else {
                    whereSql2.append(" AND substr(cord.ORDER_TIME,0,8) <= '" + dateEnd + "'");
                }
            }
            //银联商户号
            if (StringUtils.isNotEmpty(ylMerchantNo)) {
                whereSql2.append(" and TRIM(cord.YL_MCHNT_NO) like '%" + ylMerchantNo + "%'");
            }
            //银联终端号
            if (StringUtils.isNotEmpty(ylTermNo)) {
                whereSql2.append(" AND TRIM(cord.YL_TERM_NO) like '%" + ylTermNo + "%' ");
            }
            whereSql2.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '7' ");
            if (staType.equals("M")) {
                sb2.append(
                        " select YL_MCHNT_NO as MCHNTNO,'' as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select cord.ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb2.append(whereSql2);
                sb2.append(" ) tbl  group by YL_MCHNT_NO ");
            } else if (staType.equals("T")) {
                sb2.append(
                        " select YL_MCHNT_NO as MCHNTNO,YL_TERM_NO as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select cord.ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb2.append(whereSql2);
                sb2.append(" ) tbl  group by YL_MCHNT_NO,YL_TERM_NO ");
            } else {
                sb2.append(
                        " select YL_MCHNT_NO as MCHNTNO,'' as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select cord.ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb2.append(whereSql2);
                sb2.append(" ) tbl  group by YL_MCHNT_NO ");
            }

            sb.append("  select sum(TOTALMON) as TOTALMON,sum(TOTALNUM) as TOTALNUM from (  (");
            sb.append(sbL);
            sb.append(" ) union all ( ");
            sb.append(sb2 + " ) ) ");
        } else if ("99".equals(payType)) {
            sbL.delete(0, sb.length());
            StringBuffer whereSql = new StringBuffer();
            if (StringUtils.isNotEmpty(dateStart)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                    whereSql.append(" AND TRIM(txn.INST_DATE) >= '" + dateTimeS + "' ");
                } else {
                    whereSql.append(" AND substr(txn.INST_DATE,0,8) >= '" + dateStart + "'");
                }
            }

            if (StringUtils.isNotEmpty(dateEnd)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                    whereSql.append(" AND TRIM(txn.INST_DATE) <= '" + dateTimeE + "' ");
                } else {
                    whereSql.append(" AND substr(txn.INST_DATE,0,8) <= '" + dateEnd + "'");
                }
            }
            //银联商户号
            if (StringUtils.isNotEmpty(ylMerchantNo)) {
                whereSql.append(" and TRIM(base.MAPPING_MCHNTCDTWO) like '%" + ylMerchantNo + "%'");
            }
            //银联终端号
            if (StringUtils.isNotEmpty(ylTermNo)) {
                whereSql.append(" AND TRIM(txn.CARD_ACCP_TERM_ID) like '%" + ylTermNo + "%' ");
            }
            if (staType.equals("M")) {
                sbL.append(
                        " select MAPPING_MCHNTCDTWO as MCHNTNO,'' as TERMNO,sum(AMT_TRANS) as TOTALMON,count(1) as TOTALNUM  from ( select txn.INST_DATE,to_number(txn.AMT_TRANS) as AMT_TRANS,txn.CARD_ACCP_TERM_ID,base.MAPPING_MCHNTCDTWO from tbl_n_txn txn   "
                                + " left join tbl_mcht_base_inf base on txn.CARD_ACCP_ID = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and "
                                + " txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
                sbL.append(whereSql);
                sbL.append(" ) tbl  group by MAPPING_MCHNTCDTWO ");
            } else if (staType.equals("T")) {
                sbL.append(
                        " select MAPPING_MCHNTCDTWO as MCHNTNO,CARD_ACCP_TERM_ID as TERMNO,sum(AMT_TRANS) as TOTALMON,count(1) as TOTALNUM  from ( select txn.INST_DATE,to_number(txn.AMT_TRANS) as AMT_TRANS,txn.CARD_ACCP_TERM_ID,base.MAPPING_MCHNTCDTWO from tbl_n_txn txn "
                                + " left join tbl_term_inf term on txn.CARD_ACCP_TERM_ID = term.MAPPING_TERMIDTWO  and txn.CARD_ACCP_ID=term.MAPPING_MCHNTCDTWO"
                                + " left join tbl_mcht_base_inf base on txn.CARD_ACCP_ID = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and "
                                + " txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
                sbL.append(whereSql);
                sbL.append(" ) tbl  group by MAPPING_MCHNTCDTWO,CARD_ACCP_TERM_ID ");
            } else {
                sbL.append(
                        " select MAPPING_MCHNTCDTWO as MCHNTNO,'' as TERMNO,sum(AMT_TRANS) as TOTALMON,count(1) as TOTALNUM  from ( select txn.INST_DATE,to_number(txn.AMT_TRANS) as AMT_TRANS,txn.CARD_ACCP_TERM_ID,base.MAPPING_MCHNTCDTWO from tbl_n_txn txn  "
                                + " left join tbl_mcht_base_inf base on txn.CARD_ACCP_ID = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and "
                                + " txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
                sbL.append(whereSql);
                sbL.append(" ) tbl  group by MAPPING_MCHNTCDTWO ");
            }

            StringBuffer sb2 = new StringBuffer();
            StringBuffer whereSql2 = new StringBuffer();
            if (StringUtils.isNotEmpty(dateStart)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                    whereSql2.append(" AND TRIM(cord.ORDER_TIME) >= '" + dateTimeS + "' ");
                } else {
                    whereSql2.append(" AND substr(cord.ORDER_TIME,0,8) >= '" + dateStart + "'");
                }
            }

            if (StringUtils.isNotEmpty(dateEnd)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                    whereSql2.append(" AND TRIM(cord.ORDER_TIME) <= '" + dateTimeE + "' ");
                } else {
                    whereSql2.append(" AND substr(cord.ORDER_TIME,0,8) <= '" + dateEnd + "'");
                }
            }
            //银联商户号
            if (StringUtils.isNotEmpty(ylMerchantNo)) {
                whereSql2.append(" and TRIM(cord.YL_MCHNT_NO) like '%" + ylMerchantNo + "%'");
            }
            //银联终端号
            if (StringUtils.isNotEmpty(ylTermNo)) {
                whereSql2.append(" AND TRIM(cord.YL_TERM_NO) like '%" + ylTermNo + "%' ");
            }
            //            if (payType.equals("weChat")) {
            //                whereSql2.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '0' ");
            //            } else if (payType.equals("aliPay")) {
            //                whereSql2.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '1' ");
            //            } else if (payType.equals("qqPay")) {
            //                whereSql2.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '3' ");
            //            } else if (payType.equals("scanpay")) {
            //                whereSql2.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '2' ");
            //            }
            if (staType.equals("M")) {
                sb2.append(
                        " select YL_MCHNT_NO as MCHNTNO,'' as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select cord.ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb2.append(whereSql2);
                sb2.append(" ) tbl  group by YL_MCHNT_NO ");
            } else if (staType.equals("T")) {
                sb2.append(
                        " select YL_MCHNT_NO as MCHNTNO,YL_TERM_NO as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select cord.ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb2.append(whereSql2);
                sb2.append(" ) tbl  group by YL_MCHNT_NO,YL_TERM_NO ");
            } else {
                sb2.append(
                        " select YL_MCHNT_NO as MCHNTNO,'' as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select cord.ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb2.append(whereSql2);
                sb2.append(" ) tbl  group by YL_MCHNT_NO ");
            }

            sb.append("  select sum(TOTALMON) as TOTALMON,sum(TOTALNUM) as TOTALNUM from (  (");
            sb.append(sbL);
            sb.append(" ) union all ( ");
            sb.append(sb2 + " ) ) ");
        } else {
            sb.delete(0, sb.length());
            StringBuffer whereSql = new StringBuffer();
            if (StringUtils.isNotEmpty(dateStart)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                    whereSql.append(" AND TRIM(cord.ORDER_TIME) >= '" + dateTimeS + "' ");
                } else {
                    whereSql.append(" AND substr(cord.ORDER_TIME,0,8) >= '" + dateStart + "'");
                }
            }

            if (StringUtils.isNotEmpty(dateEnd)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                    whereSql.append(" AND TRIM(cord.ORDER_TIME) <= '" + dateTimeE + "' ");
                } else {
                    whereSql.append(" AND substr(cord.ORDER_TIME,0,8) <= '" + dateEnd + "'");
                }
            }
            //银联商户号
            if (StringUtils.isNotEmpty(ylMerchantNo)) {
                whereSql.append(" and TRIM(cord.YL_MCHNT_NO) like '%" + ylMerchantNo + "%'");
            }
            //银联终端号
            if (StringUtils.isNotEmpty(ylTermNo)) {
                whereSql.append(" AND TRIM(cord.YL_TERM_NO) like '%" + ylTermNo + "%' ");
            }
            //            if (payType.equals("weChat")) {
            //                whereSql.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '0' ");
            //            } else if (payType.equals("aliPay")) {
            //                whereSql.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '1' ");
            //            } else if (payType.equals("qqPay")) {
            //                whereSql.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '3' ");
            //            } else if (payType.equals("scanpay")) {
            //                whereSql.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '2' ");
            //            }
            if (StringUtils.isNotEmpty(payType)) {
                findBySQLQuery = CommonFunction.getCommQueryDAO()
                        .findBySQLQuery(" select t.INFO_ID from t_pay_typedic t where t.pay_id = '" + payType + "' ");
                if (findBySQLQuery.size() > 0) {
                    whereSql.append(" and cord.NOTICE_PAY_TYPE in (");
                    for (Object obj : findBySQLQuery) {
                        whereSql.append(" '" + obj + "',");
                    }
                    whereSql.deleteCharAt(whereSql.length() - 1);
                    whereSql.append(")");
                }
            }
            sb.append(
                    " select sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select cord.ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                            + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                            + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
            sb.append(whereSql);
            sb.append(" ) tbl ");
        }
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        for (int i = 0; i < dataList.size(); i++) {
            Object[] obj = dataList.get(i);
            try {
                // 金额转换
                if (obj[0] != null && !obj[0].equals("")) {
                    obj[0] = CommonFunction.transFenToYuan(obj[0].toString());
                } else {
                    obj[0] = CommonFunction.transFenToYuan("");
                }
                dataList.set(i, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ret[0] = dataList;
        ret[1] = 1;
        return ret;
    }

    /**
     * 交易统计详情
     * //TODO 添加方法功能描述
     *
     * @param begin
     * @param request
     * @return
     * @author hanyongqing
     */
    @SuppressWarnings("unchecked")
    public static Object[] getStatSumInfoDetail(int begin, HttpServletRequest request) {
        List<Object[]> findBySQLQuery = null;
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String staType = request.getParameter("statType");
        String payType = request.getParameter("payType");
        String dateStart = request.getParameter("startDate");
        String dateEnd = request.getParameter("endDate");
        String dateTimeS = dateStart + StringUtils.trim(request.getParameter("localTimeStart"));
        String dateTimeE = dateEnd + StringUtils.trim(request.getParameter("localTimeEnd"));
        String ylMerchantNo = request.getParameter("merchantCode");
        String ylTermNo = request.getParameter("terminalCode");
        // 查询条件组串
        StringBuffer sb = new StringBuffer();
        String countSql = "";
        if (payType.equals("5")) {
            //      sb.delete(0, sb.length());
            StringBuffer whereSql = new StringBuffer();
            if (StringUtils.isNotEmpty(dateStart)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                    whereSql.append(" AND TRIM(txn.INST_DATE) >= '" + dateTimeS + "' ");
                } else {
                    whereSql.append(" AND substr(txn.INST_DATE,0,8) >= '" + dateStart + "'");
                }
            }

            if (StringUtils.isNotEmpty(dateEnd)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                    whereSql.append(" AND TRIM(txn.INST_DATE) <= '" + dateTimeE + "' ");
                } else {
                    whereSql.append(" AND substr(txn.INST_DATE,0,8) <= '" + dateEnd + "'");
                }
            }
            //银联商户号
            if (StringUtils.isNotEmpty(ylMerchantNo)) {
                whereSql.append(" and TRIM(base.MAPPING_MCHNTCDTWO) ='" + ylMerchantNo + "'");
            }
            //银联终端号
            if (StringUtils.isNotEmpty(ylTermNo)) {
                whereSql.append(" AND TRIM(txn.CARD_ACCP_TERM_ID) = '" + ylTermNo + "' ");
            }
            StringBuffer sbL = new StringBuffer("");
            if (staType.equals("M")) {
                sbL.append(
                        " select INST_DATE as TOTALTIME,MAPPING_MCHNTCDTWO as MCHNTNO,'' as TERMNO,sum(AMT_TRANS) as TOTALMON,count(1) as TOTALNUM from ( select substr(txn.INST_DATE,0,8) as INST_DATE,to_number(txn.AMT_TRANS) as AMT_TRANS,txn.CARD_ACCP_TERM_ID,base.MAPPING_MCHNTCDTWO from tbl_n_txn txn   "
                                + " left join tbl_mcht_base_inf base on txn.CARD_ACCP_ID = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and "
                                + "  txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
                sbL.append(whereSql);
                sbL.append(" ) tbl group by INST_DATE,MAPPING_MCHNTCDTWO   "); //order by INST_DATE desc
            } else if (staType.equals("T")) {
                sbL.append(
                        " select INST_DATE as TOTALTIME,MAPPING_MCHNTCDTWO as MCHNTNO,CARD_ACCP_TERM_ID as TERMNO,sum(AMT_TRANS) as TOTALMON,count(1) as TOTALNUM from ( select substr(txn.INST_DATE,0,8) as INST_DATE,to_number(txn.AMT_TRANS) as AMT_TRANS,txn.CARD_ACCP_TERM_ID,base.MAPPING_MCHNTCDTWO from tbl_n_txn txn "
                                + " left join tbl_term_inf term on txn.CARD_ACCP_TERM_ID = term.MAPPING_TERMIDTWO  and txn.CARD_ACCP_ID=term.MAPPING_MCHNTCDTWO"
                                + " left join tbl_mcht_base_inf base on txn.CARD_ACCP_ID = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and "
                                + " txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
                sbL.append(whereSql);
                sbL.append(" ) tbl group by INST_DATE,CARD_ACCP_TERM_ID,MAPPING_MCHNTCDTWO  "); //order by INST_DATE desc 
            }
            //            countSql = "SELECT COUNT(1) FROM ( " + sb.toString() + ") tb";
            //            String cot = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
            sb.delete(0, sb.length());
            StringBuffer whereSql2 = new StringBuffer();
            if (StringUtils.isNotEmpty(dateStart)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                    whereSql2.append(" AND TRIM(cord.ORDER_TIME) >= '" + dateTimeS + "' ");
                } else {
                    whereSql2.append(" AND substr(cord.ORDER_TIME,0,8) >= '" + dateStart + "'");
                }
            }

            if (StringUtils.isNotEmpty(dateEnd)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                    whereSql2.append(" AND TRIM(cord.ORDER_TIME) <= '" + dateTimeE + "' ");
                } else {
                    whereSql2.append(" AND substr(cord.ORDER_TIME,0,8) <= '" + dateEnd + "'");
                }
            }
            //银联商户号
            if (StringUtils.isNotEmpty(ylMerchantNo)) {
                whereSql2.append(" and TRIM(cord.YL_MCHNT_NO) = '" + ylMerchantNo + "'");
            }
            //银联终端号
            if (StringUtils.isNotEmpty(ylTermNo)) {
                whereSql2.append(" AND TRIM(cord.YL_TERM_NO) = '" + ylTermNo + "' ");
            }
            whereSql2.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '7' ");
            if (staType.equals("M")) {
                sb.append(" select TOTALTIME,MCHNTNO,TERMNO,sum(TOTALMON) as TOTALMON,sum(TOTALNUM) as TOTALNUM from ( (");
                sb.append(sbL);
                sb.append(" ) union all ( ");
                sb.append(
                        " select ORDER_TIME as TOTALTIME,YL_MCHNT_NO as MCHNTNO,'' as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select substr(cord.ORDER_TIME,0,8) as ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb.append(whereSql2);
                sb.append(" ) tbl group by ORDER_TIME,YL_MCHNT_NO "); //order by ORDER_TIME desc 
                sb.append(" ) ) tbl group by MCHNTNO,TERMNO,TOTALTIME ");

            } else if (staType.equals("T")) {
                sb.append(" select TOTALTIME,MCHNTNO,TERMNO,sum(TOTALMON) as TOTALMON,sum(TOTALNUM) as TOTALNUM from ( (");
                sb.append(sbL);
                sb.append(" ) union all ( ");
                sb.append(
                        " select ORDER_TIME as TOTALTIME,YL_MCHNT_NO as MCHNTNO,YL_TERM_NO as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select substr(cord.ORDER_TIME,0,8) as ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb.append(whereSql2);
                sb.append(" ) tbl group by ORDER_TIME,YL_MCHNT_NO,YL_TERM_NO "); // order by ORDER_TIME desc
                sb.append(" ) ) tbl group by MCHNTNO,TERMNO,TOTALTIME order by TOTALTIME desc");
            }
            countSql = "SELECT COUNT(1) FROM ( " + sb.toString() + ") tb";

        } else if ("99".equals(payType)) {
            //      sb.delete(0, sb.length());
            StringBuffer whereSql = new StringBuffer();
            if (StringUtils.isNotEmpty(dateStart)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                    whereSql.append(" AND TRIM(txn.INST_DATE) >= '" + dateTimeS + "' ");
                } else {
                    whereSql.append(" AND substr(txn.INST_DATE,0,8) >= '" + dateStart + "'");
                }
            }

            if (StringUtils.isNotEmpty(dateEnd)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                    whereSql.append(" AND TRIM(txn.INST_DATE) <= '" + dateTimeE + "' ");
                } else {
                    whereSql.append(" AND substr(txn.INST_DATE,0,8) <= '" + dateEnd + "'");
                }
            }
            //银联商户号
            if (StringUtils.isNotEmpty(ylMerchantNo)) {
                whereSql.append(" and TRIM(base.MAPPING_MCHNTCDTWO) ='" + ylMerchantNo + "'");
            }
            //银联终端号
            if (StringUtils.isNotEmpty(ylTermNo)) {
                whereSql.append(" AND TRIM(txn.CARD_ACCP_TERM_ID) = '" + ylTermNo + "' ");
            }
            StringBuffer sbL = new StringBuffer("");
            if (staType.equals("M")) {
                sbL.append(
                        " select INST_DATE as TOTALTIME,MAPPING_MCHNTCDTWO as MCHNTNO,'' as TERMNO,sum(AMT_TRANS) as TOTALMON,count(1) as TOTALNUM from ( select substr(txn.INST_DATE,0,8) as INST_DATE,to_number(txn.AMT_TRANS) as AMT_TRANS,txn.CARD_ACCP_TERM_ID,base.MAPPING_MCHNTCDTWO from tbl_n_txn txn   "
                                + " left join tbl_mcht_base_inf base on txn.CARD_ACCP_ID = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and "
                                + "  txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
                sbL.append(whereSql);
                sbL.append(" ) tbl group by INST_DATE,MAPPING_MCHNTCDTWO   "); //order by INST_DATE desc
            } else if (staType.equals("T")) {
                sbL.append(
                        " select INST_DATE as TOTALTIME,MAPPING_MCHNTCDTWO as MCHNTNO,CARD_ACCP_TERM_ID as TERMNO,sum(AMT_TRANS) as TOTALMON,count(1) as TOTALNUM from ( select substr(txn.INST_DATE,0,8) as INST_DATE,to_number(txn.AMT_TRANS) as AMT_TRANS,txn.CARD_ACCP_TERM_ID,base.MAPPING_MCHNTCDTWO from tbl_n_txn txn "
                                + " left join tbl_term_inf term on txn.CARD_ACCP_TERM_ID = term.MAPPING_TERMIDTWO  and txn.CARD_ACCP_ID=term.MAPPING_MCHNTCDTWO"
                                + " left join tbl_mcht_base_inf base on txn.CARD_ACCP_ID = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and "
                                + " txn.trans_type='P' and txn.revsal_flag='0' and txn.resp_code='00' ");
                sbL.append(whereSql);
                sbL.append(" ) tbl group by INST_DATE,CARD_ACCP_TERM_ID,MAPPING_MCHNTCDTWO  "); //order by INST_DATE desc 
            }
            //            countSql = "SELECT COUNT(1) FROM ( " + sb.toString() + ") tb";
            //            String cot = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
            sb.delete(0, sb.length());
            StringBuffer whereSql2 = new StringBuffer();
            if (StringUtils.isNotEmpty(dateStart)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                    whereSql2.append(" AND TRIM(cord.ORDER_TIME) >= '" + dateTimeS + "' ");
                } else {
                    whereSql2.append(" AND substr(cord.ORDER_TIME,0,8) >= '" + dateStart + "'");
                }
            }

            if (StringUtils.isNotEmpty(dateEnd)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                    whereSql2.append(" AND TRIM(cord.ORDER_TIME) <= '" + dateTimeE + "' ");
                } else {
                    whereSql2.append(" AND substr(cord.ORDER_TIME,0,8) <= '" + dateEnd + "'");
                }
            }
            //银联商户号
            if (StringUtils.isNotEmpty(ylMerchantNo)) {
                whereSql2.append(" and TRIM(cord.YL_MCHNT_NO) = '" + ylMerchantNo + "'");
            }
            //银联终端号
            if (StringUtils.isNotEmpty(ylTermNo)) {
                whereSql2.append(" AND TRIM(cord.YL_TERM_NO) = '" + ylTermNo + "' ");
            }
            //                if (payType.equals("weChat")) {
            //                    whereSql2.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '0' ");
            //                } else if (payType.equals("aliPay")) {
            //                    whereSql2.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '1' ");
            //                } else if (payType.equals("qqPay")) {
            //                    whereSql2.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '3' ");
            //                }
            if (staType.equals("M")) {
                sb.append(" select TOTALTIME,MCHNTNO,TERMNO,sum(TOTALMON) as TOTALMON,sum(TOTALNUM) as TOTALNUM from ( (");
                sb.append(sbL);
                sb.append(" ) union all ( ");
                sb.append(
                        " select ORDER_TIME as TOTALTIME,YL_MCHNT_NO as MCHNTNO,'' as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select substr(cord.ORDER_TIME,0,8) as ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb.append(whereSql2);
                sb.append(" ) tbl group by ORDER_TIME,YL_MCHNT_NO "); //order by ORDER_TIME desc 
                sb.append(" ) ) tbl group by MCHNTNO,TERMNO,TOTALTIME ");

            } else if (staType.equals("T")) {
                sb.append(" select TOTALTIME,MCHNTNO,TERMNO,sum(TOTALMON) as TOTALMON,sum(TOTALNUM) as TOTALNUM from ( (");
                sb.append(sbL);
                sb.append(" ) union all ( ");
                sb.append(
                        " select ORDER_TIME as TOTALTIME,YL_MCHNT_NO as MCHNTNO,YL_TERM_NO as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select substr(cord.ORDER_TIME,0,8) as ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb.append(whereSql2);
                sb.append(" ) tbl group by ORDER_TIME,YL_MCHNT_NO,YL_TERM_NO "); // order by ORDER_TIME desc
                sb.append(" ) ) tbl group by MCHNTNO,TERMNO,TOTALTIME order by TOTALTIME desc");
            }
            countSql = "SELECT COUNT(1) FROM ( " + sb.toString() + ") tb";

        } else {
            sb.delete(0, sb.length());
            StringBuffer whereSql = new StringBuffer();
            if (StringUtils.isNotEmpty(dateStart)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                    whereSql.append(" AND TRIM(cord.ORDER_TIME) >= '" + dateTimeS + "' ");
                } else {
                    whereSql.append(" AND substr(cord.ORDER_TIME,0,8) >= '" + dateStart + "'");
                }
            }

            if (StringUtils.isNotEmpty(dateEnd)) {
                if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                    whereSql.append(" AND TRIM(cord.ORDER_TIME) <= '" + dateTimeE + "' ");
                } else {
                    whereSql.append(" AND substr(cord.ORDER_TIME,0,8) <= '" + dateEnd + "'");
                }
            }
            //银联商户号
            if (StringUtils.isNotEmpty(ylMerchantNo)) {
                whereSql.append(" and TRIM(cord.YL_MCHNT_NO) = '" + ylMerchantNo + "'");
            }
            //银联终端号
            if (StringUtils.isNotEmpty(ylTermNo)) {
                whereSql.append(" AND TRIM(cord.YL_TERM_NO) = '" + ylTermNo + "' ");
            }
            //            if (payType.equals("weChat")) {
            //                whereSql.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '0' ");
            //            } else if (payType.equals("aliPay")) {
            //                whereSql.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '1' ");
            //            } else if (payType.equals("qqPay")) {
            //                whereSql.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '3' ");
            //            } else if (payType.equals("scanpay")) {
            //                whereSql.append(" AND TRIM(cord.NOTICE_PAY_TYPE) = '2' ");
            //            }
            if (StringUtils.isNotEmpty(payType)) {
                findBySQLQuery = CommonFunction.getCommQueryDAO()
                        .findBySQLQuery(" select t.INFO_ID from t_pay_typedic t where t.pay_id = '" + payType + "' ");
                if (findBySQLQuery.size() > 0) {
                    whereSql.append(" and cord.NOTICE_PAY_TYPE in (");
                    for (Object obj : findBySQLQuery) {
                        whereSql.append(" '" + obj + "',");
                    }
                    whereSql.deleteCharAt(whereSql.length() - 1);
                    whereSql.append(")");
                }
            }
            if (staType.equals("M")) {
                sb.append(
                        " select ORDER_TIME as TOTALTIME,YL_MCHNT_NO as MCHNTNO,'' as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select substr(cord.ORDER_TIME,0,8) as ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb.append(whereSql);
                sb.append(" ) tbl group by ORDER_TIME,YL_MCHNT_NO order by ORDER_TIME desc ");
            } else if (staType.equals("T")) {
                sb.append(
                        " select ORDER_TIME as TOTALTIME,YL_MCHNT_NO as MCHNTNO,YL_TERM_NO as TERMNO,sum(OTDER_FEE) as TOTALMON,count(1) as TOTALNUM from ( select substr(cord.ORDER_TIME,0,8) as ORDER_TIME,cord.YL_MCHNT_NO,cord.YL_TERM_NO,to_number(cord.ORDER_FEE) as OTDER_FEE  from t_corderinfo cord "
                                + " left join tbl_mcht_base_inf base on cord.YL_MCHNT_NO = base.MAPPING_MCHNTCDTWO left join tbl_brh_info brh on base.AGR_BR = brh.BRH_ID "
                                + " where brh.BRH_ID in " + orgArrStr + " and " + " cord.NOTICE_STATUS = '1' ");
                sb.append(whereSql);
                sb.append(" ) tbl group by ORDER_TIME,YL_MCHNT_NO,YL_TERM_NO order by ORDER_TIME desc ");
            }
            countSql = "SELECT COUNT(1) FROM ( " + sb.toString() + ") tb";
        }

        countSql = "SELECT COUNT(1) FROM ( " + sb.toString() + ") tb";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        for (int i = 0; i < dataList.size(); i++) {
            Object[] obj = dataList.get(i);
            try {
                if (obj[0] != null && obj[0] != "") {
                    Date tm = sdf.parse(obj[0].toString());
                    obj[0] = sdf2.format(tm);
                }
                // 金额转换
                if (obj[3] != null && !obj[3].equals("")) {
                    obj[3] = CommonFunction.transFenToYuan(obj[3].toString());
                } else {
                    obj[3] = CommonFunction.transFenToYuan("");
                }
                dataList.set(i, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询联机交易信息
     * 
     * @param begin
     * @param request
     * @return 2010-8-27上午10:47:28
     */
    @SuppressWarnings("unchecked")
    public static Object[] getPosTxnInfo(int begin, HttpServletRequest request) throws Exception {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        StringBuffer whereSql = new StringBuffer(" ");
        String brhBelowId = operator.getBrhBelowId();
        String mchtFlag1 = request.getParameter("mchtFlag1");
        String txnNum = request.getParameter("txnNum");
        String brhId = request.getParameter("brhId");
        String mchtCd = request.getParameter("mchtCd");
        String termId = request.getParameter("termId");
        String sysSeqNum = request.getParameter("sysSeqNum");
        String cupSsn = request.getParameter("cupSsn");
        String pan = request.getParameter("pan");
        String dateStart = request.getParameter("startDate");
        String dateEnd = request.getParameter("endDate");
        String dateTimeS = dateStart + StringUtils.trim(request.getParameter("localTimeStart"));
        String dateTimeE = dateEnd + StringUtils.trim(request.getParameter("localTimeEnd"));

        String transCode = request.getParameter("transCode");
        String track1Data = request.getParameter("track1Data");
        /*
         * if (!"0000".equals(operator.getOprBrhId())) {
         * whereSql.append(" AND TRIM(TERM.TERM_BRANCH) IN " +
         * operator.getBrhBelowId()); }
         */
        if (StringUtils.isNotEmpty(txnNum)) {
            whereSql.append(" AND TRIM(t.TXN_NUM) = '" + txnNum + "' ");
        }

        if (StringUtils.isNotEmpty(dateStart)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                whereSql.append(" AND TRIM(INST_DATE) >= '" + dateTimeS + "' ");
            } else {
                whereSql.append(" AND substr(INST_DATE,0,8) >= '" + dateStart + "'");
            }
        }

        if (StringUtils.isNotEmpty(dateEnd)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                whereSql.append(" AND TRIM(T.INST_DATE) <= '" + dateTimeE + "' ");
            } else {
                whereSql.append(" AND substr(T.INST_DATE,0,8) <= '" + dateEnd + "'");
            }
        }

        if (StringUtils.isNotEmpty(brhId)) {
            whereSql.append(" AND TRIM(M.AGR_BR)='" + brhId + "'");
        }
        if (StringUtils.isNotEmpty(mchtCd)) {
            whereSql.append(" AND TRIM(T.CARD_ACCP_ID) like '%" + mchtCd + "%'");
        }
        if (StringUtils.isNotEmpty(termId)) {
            whereSql.append(" AND TRIM(T.CARD_ACCP_TERM_ID) like '%" + termId + "%'");
        }
        if (StringUtils.isNotEmpty(sysSeqNum)) {
            whereSql.append(" AND TRIM(T.SYS_SEQ_NUM)='" + sysSeqNum + "'");
        }
        if (StringUtils.isNotEmpty(cupSsn)) {
            whereSql.append(" AND TRIM(T.CUP_SSN)='" + cupSsn + "'");
        }
        if (StringUtils.isNotEmpty(pan)) {
            whereSql.append(
                    " AND ( TRIM(T.PAN) like '%" + pan.replace(" ", "")
                            + "%' or TRIM((substr( trim(T.PAN), 1, 6) || lpad('*', length(trim(T.PAN))-10, '*') || substr(trim(T.PAN), -4))) like '%"
                            + pan.replace(" ", "") + "%')  ");
        }
        if (StringUtils.isNotEmpty(track1Data)) {
            whereSql.append(" AND TRIM(T.TRACK_1_DATA) like '%" + track1Data + "%'");
        }
        if (StringUtils.isNotEmpty(mchtFlag1)) {
            whereSql.append(" AND T.MCHT_FLAG1='" + mchtFlag1 + "'");
        }
        if (StringUtils.isNotEmpty(transCode)) {
            whereSql.append(" AND T.TRANS_CODE='" + transCode + "'");
        }
        whereSql.append(" AND  TRIM(M.AGR_BR) in " + brhBelowId);
        StringBuffer sb = new StringBuffer();
        sb.append(
                " select T.INST_DATE,"
                        + " T.SYS_SEQ_NUM,(substr( trim(T.PAN), 1, 6) || lpad('*', length(trim(T.PAN))-10, '*') || substr(trim(T.PAN), -4)) as PAN,T.CARD_ACCP_ID,M.MCHT_NM,M.AGR_BR as SETTLE_AREA_NO,T.CARD_ACCP_TERM_ID,T.RETRIVL_REF,T.AMT_TRANS, "
                        + " T.ACQ_INST_ID_CODE,T.RESP_CODE,T.AMT_SETTLMT as AMT_CDHLDR_BIL,T.CUP_SSN, T.DATE_LOCAL_TRANS,T.TIME_LOCAL_TRANS,T.TRANS_TYPE,T.MARKSUBSIDY_AMOUNT,subdy.SUBSIDY_RULE,trim(T.TRACK_1_DATA) AS TRACK_1_DATA,T.TRANS_CODE,T.REVSAL_FLAG,subdy.SUBSIDY_TYPE "
                        + " from TBL_N_TXN T "
                        + " LEFT OUTER JOIN TBL_MCHT_BASE_INF M ON (M.MAPPING_MCHNTCDONE = trim(T.CARD_ACCP_ID) OR M.MAPPING_MCHNTCDTWO = trim(T.CARD_ACCP_ID)) "
                        + " LEFT JOIN TBL_MCHT_MARKSUBSIDY subdy on T.RULE_ID = subdy.RULE_ID where 1 = 1 ");
        // 交易查询（十日内）       + "where substr(T.INST_DATE,1,8) >= TO_CHAR(sysdate - interval '10' day, 'YYYYMMDD') and (RESP_CODE = '00' or (TRANS_TYPE='R' and (RESP_CODE='12' or RESP_CODE='25')) )");

        sb.append(whereSql);
        sb.append(" ORDER BY INST_DATE DESC");

        String countSql = "SELECT COUNT(1) FROM (" + sb.toString() + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf2Date = new SimpleDateFormat("MM-dd");
        SimpleDateFormat sdfDate = new SimpleDateFormat("MMdd");
        SimpleDateFormat sdf2Time = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HHmmss");
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            if (data[0] != null && !data[0].equals("")) {
                try {
                    Date parse = sdf.parse(data[0].toString());
                    data[0] = sdf2.format(parse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //            if (data[1] != null) {
            //                data[8] = CommonFunction.transFenToYuan(data[8].toString());
            //            } else {
            //                data[8] = CommonFunction.transFenToYuan("");
            //            }
            if (data[8] != null) {
                data[8] = CommonFunction.transFenToYuan(data[8].toString());
            } else {
                data[8] = CommonFunction.transFenToYuan("");
            }
            if (data[11] != null) {
                data[11] = CommonFunction.transFenToYuan(data[11].toString());
            } else {
                data[11] = CommonFunction.transFenToYuan("");
            }
            //  日期
            if (data[13] != null && !data[13].equals("")) {
                Date parse = sdfDate.parse(data[13].toString());
                data[13] = sdf2Date.format(parse);
            }
            //  时间
            if (data[14] != null && !data[14].equals("")) {
                Date parse = sdfTime.parse(data[14].toString());
                data[14] = sdf2Time.format(parse);
            }
            if (data[15] != null && data[15].equals("P")) {
                if (data[20] != null && data[20].equals("1")) {
                    data[15] = "PP";
                }
            }
            if (data[16] != null && !data[16].equals("")) {
                data[16] = CommonFunction.transFenToYuan(data[16].toString());
            } else {
                data[16] = CommonFunction.transFenToYuan("");
            }
            if (data[21] != null && !data[21].equals("")) {
                if (data[21].equals("00")) {
                    data[21] = "满额补";
                    if (data[17] != null && !data[17].equals("")) {
                        String trFToY = CommonFunction.transFenToYuan(data[17].toString().substring(0, 12));
                        String trFToY2 = CommonFunction.transFenToYuan(data[17].toString().substring(12, 24));
                        data[17] = "每笔满" + trFToY + "元补" + trFToY2 + "元";
                    }
                } else if (data[21].equals("01")) {
                    data[21] = "按比例补";
                    if (data[17] != null && !data[17].equals("")) {
                        int parseInt = Integer.parseInt(data[17].toString().substring(0, 3));
                        String trFToY2 = CommonFunction.transFenToYuan(data[17].toString().substring(3, 15));
                        data[17] = "每笔按比例补" + parseInt + "%，最高补" + trFToY2 + "元";
                    }
                } else {
                    data[17] = "未知";
                }
            }
            dataList.set(i, data);
        }
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

        List<Object[]> list1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString());
        Object[] dataNew;
        for (int i = 0; i < list1.size(); i++) {
            dataNew = list1.get(i);

            if (dataNew[9] != null) {
                dataNew[9] = CommonFunction.transFenToYuan(dataNew[9].toString());
            } else {
                dataNew[9] = CommonFunction.transFenToYuan("");
            }
            if (dataNew[12] != null) {
                dataNew[12] = CommonFunction.transFenToYuan(dataNew[12].toString());
            } else {
                dataNew[12] = CommonFunction.transFenToYuan("");
            }
            if (dataNew[16] != null) {
                if (StringUtils.equals(dataNew[16].toString(), "P")) {
                    dataNew[16] = "消费";
                    if (dataNew[19] != null && dataNew[19].equals("1")) {
                        dataNew[16] = "消费(已冲正)";
                    }
                }
                if (StringUtils.equals(dataNew[16].toString(), "Y")) {
                    dataNew[16] = "预授权";
                }
                if (StringUtils.equals(dataNew[16].toString(), "X")) {
                    dataNew[16] = "撤销";
                }
                if (StringUtils.equals(dataNew[16].toString(), "W")) {
                    dataNew[16] = "预授权完成";
                }
                if (StringUtils.equals(dataNew[16].toString(), "R")) {
                    dataNew[16] = "冲正";
                }
            } else {
                dataNew[16] = "未知";
            }
            if (dataNew[19] != null) {
                if (StringUtils.equals(dataNew[19].toString(), "022")) {
                    dataNew[19] = "联机消费";
                } else if (StringUtils.equals(dataNew[19].toString(), "036")) {
                    dataNew[19] = "脱机消费";
                } else {
                    dataNew[19] = "未知";
                }

            } else {
                dataNew[19] = "未知";
            }
            list1.set(i, dataNew);
        }
        request.getSession().removeAttribute("transList");
        request.getSession().setAttribute("transList", list1);

        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 广东建行
     * 
     * @param begin
     * @param request
     * @return
     */
    public static Object[] getPosTxnInfogd(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer(" ");

        String mchtFlag1 = request.getParameter("mchtFlag1");
        String txnNum = request.getParameter("txnNum");
        String brhId = request.getParameter("brhId");
        String mchtCd = request.getParameter("mchtCd");
        String termId = request.getParameter("termId");
        String sysSeqNum = request.getParameter("sysSeqNum");
        String pan = request.getParameter("pan");
        String dateStart = request.getParameter("startDate");
        String dateEnd = request.getParameter("endDate");
        String dateTimeS = dateStart + StringUtils.trim(request.getParameter("localTimeStart"));
        String dateTimeE = dateEnd + StringUtils.trim(request.getParameter("localTimeEnd"));

        String transCode = request.getParameter("transCode");
        String track1Data = request.getParameter("track1Data");
        /*
         * if (!"0000".equals(operator.getOprBrhId())) {
         * whereSql.append(" AND TRIM(TERM.TERM_BRANCH) IN " +
         * operator.getBrhBelowId()); }
         */
        if (StringUtils.isNotEmpty(txnNum)) {
            whereSql.append(" AND TRIM(t.TXN_NUM) = '" + txnNum + "' ");
        }

        if (StringUtils.isNotEmpty(dateStart)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeStart"))) {
                whereSql.append(" AND TRIM(INST_DATE) >= '" + dateTimeS + "' ");
            } else {
                whereSql.append(" AND substr(INST_DATE,0,8) >= '" + dateStart + "'");
            }
        }

        if (StringUtils.isNotEmpty(dateEnd)) {
            if (StringUtils.isNotEmpty(request.getParameter("localTimeEnd"))) {
                whereSql.append(" AND TRIM(INST_DATE) <= '" + dateTimeE + "' ");
            } else {
                whereSql.append(" AND substr(INST_DATE,0,8) <= '" + dateEnd + "'");
            }
        }

        if (StringUtils.isNotEmpty(brhId)) {
            whereSql.append(" AND TRIM(M.AGR_BR)='" + brhId + "'");
        }
        if (StringUtils.isNotEmpty(mchtCd)) {
            whereSql.append(" AND TRIM(CARD_ACCP_ID) like '%" + mchtCd + "%'");
        }
        if (StringUtils.isNotEmpty(termId)) {
            whereSql.append(" AND TRIM(CARD_ACCP_TERM_ID) like '%" + termId + "%'");
        }
        if (StringUtils.isNotEmpty(sysSeqNum)) {
            whereSql.append(" AND TRIM(SYS_SEQ_NUM)='" + sysSeqNum + "'");
        }
        if (StringUtils.isNotEmpty(pan)) {
            whereSql.append(" AND TRIM(PAN)='" + pan + "'");
        }
        if (StringUtils.isNotEmpty(track1Data)) {
            whereSql.append(" AND TRIM(TRACK_1_DATA) like '%" + track1Data + "%'");
        }
        if (StringUtils.isNotEmpty(mchtFlag1)) {
            whereSql.append(" AND MCHT_FLAG1='" + mchtFlag1 + "'");
        }
        if (StringUtils.isNotEmpty(transCode)) {
            whereSql.append(" AND TRANS_CODE='" + transCode + "'");
        }

        StringBuffer sb = new StringBuffer();
        // 日期、时间
        sb.append("select substr(T.INST_DATE,1,8) as inst_date," +
        // 商户编号、商户名称、归属行、市场、终端号、结算账号名称、结算账号
                "T.CARD_ACCP_ID,M.MCHT_NM,D.BRH_NAME,M.ADDR,T.CARD_ACCP_TERM_ID,C.SETTLE_ACCT_NM,substr(C.SETTLE_ACCT,2,length(C.SETTLE_ACCT)-1) as settle_acct,"
                + "substr(T.INST_DATE,1,4)||'/'||substr(T.INST_DATE,5,2)||'/'||substr(T.INST_DATE,7,2)||' '||substr(T.INST_DATE,9,2)||':'||substr(T.INST_DATE,11,2)||':'||substr(T.INST_DATE,13,2) as inst_time,"
                +
                // 卡号、交易流水号、折前金额、交易金额
                "T.PAN,T.TERM_SSN,T.AMT_SETTLMT,(T.AMT_SETTLMT-T.AMT_TRANS) as BT_SETTLMT,T.TRANS_TYPE,trim(T.TRACK_1_DATA),T.TRANS_CODE"
                + " from TBL_N_TXN T, tbl_mcht_base_inf M,TBL_MCHT_SETTLE_INF C ,tbl_brh_info D "
                + "where T.CARD_ACCP_ID=M.MAPPING_MCHNTCDTWO and C.MCHT_NO= M.MCHT_NO and D.BRH_ID=M.BANK_NO" + " and t.trans_state='1'");

        sb.append(whereSql);
        sb.append(" ORDER BY INST_DATE DESC,UPDT_DATE DESC");
        String countSql = "SELECT COUNT(1) FROM (" + sb.toString() + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            if (data[11] != null) {
                data[11] = CommonFunction.transFenToYuan(data[11].toString());
            } else {
                data[11] = CommonFunction.transFenToYuan("");
            }
            if (data[12] != null) {
                data[12] = CommonFunction.transFenToYuan(data[12].toString());
            } else {
                data[12] = CommonFunction.transFenToYuan("");
            }

            dataList.set(i, data);
        }
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

        List<Object[]> list1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString());
        Object[] dataNew;
        for (int i = 0; i < list1.size(); i++) {
            dataNew = list1.get(i);
            if (dataNew[11] != null) {
                dataNew[11] = CommonFunction.transFenToYuan(dataNew[11].toString());
            } else {
                dataNew[11] = CommonFunction.transFenToYuan("");
            }
            if (dataNew[12] != null) {
                dataNew[12] = CommonFunction.transFenToYuan(dataNew[12].toString());
            } else {
                dataNew[12] = CommonFunction.transFenToYuan("");
            }
            if (dataNew[13] != null) {
                if (StringUtils.equals(dataNew[13].toString(), "P")) {
                    dataNew[13] = "消费";
                }
                if (StringUtils.equals(dataNew[13].toString(), "Y")) {
                    dataNew[13] = "预授权";
                }
                if (StringUtils.equals(dataNew[13].toString(), "X")) {
                    dataNew[13] = "撤销";
                }
                if (StringUtils.equals(dataNew[13].toString(), "W")) {
                    dataNew[13] = "预授权完成";
                }
                if (StringUtils.equals(dataNew[13].toString(), "R")) {
                    dataNew[13] = "冲正";
                }
            } else {
                dataNew[13] = "未知";
            }
            if (dataNew[15] != null) {
                if (StringUtils.equals(dataNew[15].toString(), "022")) {
                    dataNew[15] = "联机消费";
                } else if (StringUtils.equals(dataNew[15].toString(), "036")) {
                    dataNew[15] = "脱机消费";
                } else {
                    dataNew[15] = "未知";
                }

            } else {
                dataNew[15] = "未知";
            }

            list1.set(i, dataNew);
        }
        request.getSession().setAttribute("transList", list1);

        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询历史交易信息
     * 
     * @param begin
     * @param request
     * @return 2010-8-27上午10:47:28
     */
    @SuppressWarnings("unchecked")
    public static Object[] getPosTxnInfoHis(int begin, HttpServletRequest request) throws Exception {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer(" ");
        /*
         * if (!"0000".equals(operator.getOprBrhId())) {
         * whereSql.append(" AND TRIM(TERM.TERM_BRANCH) IN " +
         * operator.getBrhBelowId()); }
         */
        // 交易类型
        String txnNum = request.getParameter("txnNum");
        if (StringUtils.isNotEmpty(txnNum)) {
            whereSql.append(" AND TRIM(T.TXN_NUM) = '" + txnNum + "' ");
        }
        // 开始日期
        String startDate = request.getParameter("startDate");
        if (StringUtils.isNotEmpty(startDate)) {
            whereSql.append(" AND substr(T.INST_DATE,1,8)>='" + startDate + "'");
        } else {
            whereSql.append(" AND substr(T.INST_DATE,1,8)>='" + CommonFunction.get180DaysAgo() + "'");
        }
        // 结束日期
        String endDate = request.getParameter("endDate");
        if (StringUtils.isNotEmpty(endDate)) {
            whereSql.append(" AND substr(T.INST_DATE,1,8)<='" + endDate + "'");
        }

        // 机构号
        String brhId = request.getParameter("brhId");
        if (StringUtils.isNotEmpty(brhId)) {
            whereSql.append(" AND TRIM(M.AGR_BR)='" + brhId + "'");
        }
        // 商户号
        String mchtCd = request.getParameter("mchtCd");
        if (StringUtils.isNotEmpty(mchtCd)) {
            whereSql.append(" AND TRIM(T.CARD_ACCP_ID) like '%" + mchtCd + "%'");
        }
        // 系统流水号
        String sysSeqNum = request.getParameter("sysSeqNum");
        if (StringUtils.isNotEmpty(sysSeqNum)) {
            whereSql.append(" AND TRIM(T.SYS_SEQ_NUM)='" + sysSeqNum + "'");
        }
        String pan = request.getParameter("pan");
        if (StringUtils.isNotEmpty(pan)) {
            whereSql.append(
                    " AND ( TRIM(T.PAN) like '%" + pan.replace(" ", "")
                            + "%' or TRIM((substr( trim(T.PAN), 1, 6) || lpad('*', length(trim(T.PAN))-10, '*') || substr(trim(T.PAN), -4))) like '%"
                            + pan.replace(" ", "") + "%')  ");
        }
        String transCode = request.getParameter("transCode");
        if (StringUtils.isNotEmpty(transCode)) {
            whereSql.append(" AND TRIM(T.TRANS_CODE)='" + transCode + "'");
        }

        String track1Data = request.getParameter("track1Data");
        if (StringUtils.isNotEmpty(track1Data)) {
            whereSql.append(" AND TRIM(T.TRACK_1_DATA) like '%" + track1Data + "%'");
        }
        StringBuffer sb = new StringBuffer();

        sb.append(
                " select T.INST_DATE,"
                        + " T.SYS_SEQ_NUM,(substr( trim(T.PAN), 1, 6) || lpad('*', length(trim(T.PAN))-10, '*') || substr(trim(T.PAN), -4)) as PAN,T.CARD_ACCP_ID,M.MCHT_NM,M.AGR_BR as SETTLE_AREA_NO,T.CARD_ACCP_TERM_ID,T.RETRIVL_REF,T.AMT_TRANS, "
                        + " T.ACQ_INST_ID_CODE,T.RESP_CODE,T.AMT_SETTLMT as AMT_CDHLDR_BIL,T.CUP_SSN, T.DATE_LOCAL_TRANS,T.TIME_LOCAL_TRANS,T.TRANS_TYPE,T.MARKSUBSIDY_AMOUNT,subdy.SUBSIDY_RULE,trim(T.TRACK_1_DATA) AS TRACK_1_DATA,T.TRANS_CODE,T.REVSAL_FLAG,subdy.SUBSIDY_TYPE "
                        + " from TBL_N_TXN T "
                        + " LEFT OUTER JOIN TBL_MCHT_BASE_INF M ON (M.MAPPING_MCHNTCDONE = trim(T.CARD_ACCP_ID) OR M.MAPPING_MCHNTCDTWO = trim(T.CARD_ACCP_ID)) "
                        + " LEFT JOIN TBL_MCHT_MARKSUBSIDY subdy on T.RULE_ID = subdy.RULE_ID "

                        + "where substr(T.INST_DATE,1,8) < TO_CHAR(sysdate - interval '10' day, 'YYYYMMDD') and (RESP_CODE = '00' or (TRANS_TYPE='R' and (RESP_CODE='12' or RESP_CODE='25')) ) ");

        sb.append(whereSql);
        sb.append(" ORDER BY INST_DATE DESC");

        String countSql = "SELECT COUNT(1) FROM (" + sb.toString() + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf2Date = new SimpleDateFormat("MM-dd");
        SimpleDateFormat sdfDate = new SimpleDateFormat("MMdd");
        SimpleDateFormat sdf2Time = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HHmmss");
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            if (data[0] != null && !data[0].equals("")) {
                Date parse = sdf.parse(data[0].toString());
                data[0] = sdf2.format(parse);
            }
            if (data[8] != null) {
                data[8] = CommonFunction.transFenToYuan(data[8].toString());
            } else {
                data[8] = CommonFunction.transFenToYuan("");
            }
            if (data[11] != null) {
                data[11] = CommonFunction.transFenToYuan(data[11].toString());
            } else {
                data[11] = CommonFunction.transFenToYuan("");
            }
            //  日期
            if (data[13] != null && !data[13].equals("")) {
                Date parse = sdfDate.parse(data[13].toString());
                data[13] = sdf2Date.format(parse);
            }
            //  时间
            if (data[14] != null && !data[14].equals("")) {
                Date parse = sdfTime.parse(data[14].toString());
                data[14] = sdf2Time.format(parse);
            }

            if (data[15] != null && data[15].equals("P")) {
                if (data[20] != null && data[20].equals("1")) {
                    data[15] = "PP";
                }
            }
            if (data[16] != null && !data[16].equals("")) {
                data[16] = CommonFunction.transFenToYuan(data[16].toString());
            } else {
                data[16] = CommonFunction.transFenToYuan("");
            }
            if (data[21] != null && !data[21].equals("")) {
                if (data[21].equals("00")) {
                    data[21] = "满额补";
                    if (data[17] != null && !data[17].equals("")) {
                        String trFToY = CommonFunction.transFenToYuan(data[17].toString().substring(0, 12));
                        String trFToY2 = CommonFunction.transFenToYuan(data[17].toString().substring(12, 24));
                        data[17] = "每笔满" + trFToY + "元补" + trFToY2 + "元";
                    }
                } else if (data[21].equals("01")) {
                    data[21] = "按比例补";
                    if (data[17] != null && !data[17].equals("")) {
                        int parseInt = Integer.parseInt(data[17].toString().substring(0, 3));
                        String trFToY2 = CommonFunction.transFenToYuan(data[17].toString().substring(3, 15));
                        data[17] = "每笔按比例补" + parseInt + "%，最高补" + trFToY2 + "元";
                    }
                } else {
                    data[17] = "未知";
                }
            }
        }

        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static Object[] getPosTxnInfo2(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String whereSql = "";
        if (!StringUtil.isNull(request.getParameter("txnNum")) && !"0000".equals(request.getParameter("txnNum"))) {
            whereSql += "AND t.TXN_NUM = '" + request.getParameter("txnNum") + "' ";
        }
        if (!StringUtil.isNull(request.getParameter("startDate"))) {
            whereSql += "AND substr(UPDT_DATE,1,8) >= '" + request.getParameter("startDate") + "' ";
        }
        if (!StringUtil.isNull(request.getParameter("endDate"))) {
            whereSql += "AND substr(UPDT_DATE,1,8) <= '" + request.getParameter("endDate") + "' ";
        }
        if (!StringUtil.isNull(request.getParameter("pan"))) {
            whereSql += " AND rtrim(PAN) like '%" + request.getParameter("pan").trim() + "%' ";
        }
        if (!StringUtil.isNull(request.getParameter("keyRsp"))) {
            whereSql += " AND KEY_RSP like '%" + request.getParameter("keyRsp").trim() + "%' ";
        }
        if (!StringUtil.isNull(request.getParameter("mchtNo"))) {
            whereSql += " AND CARD_ACCP_ID = '" + request.getParameter("mchtNo").trim() + "' ";
        }
        if (!StringUtil.isNull(request.getParameter("amt"))) {
            BigDecimal bigDecimal = new BigDecimal(request.getParameter("amt").trim());
            whereSql += " AND AMT_TRANS = " + bigDecimal.movePointRight(2);
        }
        StringBuffer sb = new StringBuffer();
        sb.append(
                "select substr(UPDT_DATE,1,8) as inst_date,substr(UPDT_DATE,9,6) as inst_time,"
                        + "SYS_SEQ_NUM,PAN,CARD_ACCP_ID,CARD_ACCP_NAME,CARD_ACCP_TERM_ID,RETRIVL_REF,AMT_TRANS,"
                        // +
                        // "ACQ_INST_ID_CODE,name.TXN_NAME,RESP_CODE,key_rsp from tbl_n_txn_his t left outer join "
                        + "ACQ_INST_ID_CODE,name.TXN_NAME,RESP_CODE,key_rsp from tbl_n_txn t left outer join "
                        + "tbl_txn_name name on (t.txn_num = name.txn_num) where 1 = 1 ");

        // 根据终端号前两位定义受理机构,总行不受控制
        String brhId = "";
        if (!StringUtil.isNull(request.getParameter("brhId"))) {
            brhId = request.getParameter("brhId").trim();
        } else {
            brhId = operator.getOprBrhId();
        }
        if (!"9900".equals(brhId)) {
            sb.append(" and substr(CARD_ACCP_TERM_ID,1,2) = '" + brhId.substring(0, 2) + "'");
        }

        sb.append(whereSql);
        sb.append(" ORDER BY UPDT_DATE DESC");

        String countSql = "SELECT COUNT(*) FROM (" + sb.toString() + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            data[8] = CommonFunction.transFenToYuan(data[8].toString());
            dataList.set(i, data);
        }
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    public static Object[] getPosTxnInfoOutline(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String whereSql = "";
        if (!StringUtil.isNull(request.getParameter("startDate"))) {
            whereSql += "AND substr(UPDT_DATE,1,8) >= '" + request.getParameter("startDate") + "' ";
        }
        if (!StringUtil.isNull(request.getParameter("endDate"))) {
            whereSql += "AND substr(UPDT_DATE,1,8) <= '" + request.getParameter("endDate") + "' ";
        }
        if (!StringUtil.isNull(request.getParameter("txnNum")) && !"0000".equals(request.getParameter("txnNum"))) {
            whereSql += "AND t.TXN_NUM = '" + request.getParameter("txnNum") + "' ";
        }
        if (!StringUtil.isNull(request.getParameter("mchntNo"))) {
            whereSql += "AND CARD_ACCP_ID = '" + request.getParameter("mchntNo") + "' ";
        }
        // 终端编号
        if (!StringUtil.isNull(request.getParameter("termId"))) {
            whereSql += " AND CARD_ACCP_TERM_ID = '" + request.getParameter("termId") + "' ";
        }
        // 卡号
        if (!StringUtil.isNull(request.getParameter("pan"))) {
            whereSql += " AND rtrim(PAN) like '" + request.getParameter("pan").trim() + "%' ";
        }
        // 检索参考号
        if (!StringUtil.isNull(request.getParameter("retrivlRef"))) {
            whereSql += " AND rtrim(RETRIVL_REF) like '" + request.getParameter("retrivlRef").trim() + "%' ";
        }
        // 应答码
        if (!StringUtil.isNull(request.getParameter("respCode"))) {
            whereSql += " AND RESP_CODE = '" + request.getParameter("respCode") + "' ";
        }

        StringBuffer sb = new StringBuffer();
        sb.append(
                "select substr(UPDT_DATE,1,8) as inst_date,substr(UPDT_DATE,9,6) as inst_time,"
                        + "SYS_SEQ_NUM,PAN,CARD_ACCP_ID,CARD_ACCP_NAME,CARD_ACCP_TERM_ID,RETRIVL_REF,AMT_TRANS,"
                        + "ACQ_INST_ID_CODE,name.TXN_NAME,RESP_CODE from tbl_t_txn t left outer join "
                        + "tbl_txn_name name on (t.txn_num = name.txn_num) where 1 = 1 ");

        // 根据终端号前两位定义受理机构,总行不受控制
        String brhId = "";
        if (!StringUtil.isNull(request.getParameter("brhId"))) {
            brhId = request.getParameter("brhId").trim();
        } else {
            brhId = operator.getOprBrhId();
        }
        if (!"9900".equals(brhId)) {
            sb.append(" and substr(CARD_ACCP_TERM_ID,1,2) = '" + brhId.substring(0, 2) + "'");
        }

        sb.append(whereSql);
        sb.append(" ORDER BY UPDT_DATE DESC");

        String countSql = "SELECT COUNT(*) FROM (" + sb.toString() + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            data[8] = CommonFunction.transFenToYuan(data[8].toString());
            dataList.set(i, data);
        }
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;

        // Object[] ret = new Object[2];
        // Operator operator = (Operator) request.getSession().getAttribute(
        // Constants.OPERATOR_INFO);
        //
        // String whereSql = "";
        // if (!StringUtil.isNull(request.getParameter("startDate"))) {
        // whereSql += "AND substr(UPDT_DATE,1,8) >= '"
        // + request.getParameter("startDate") + "' ";
        // }
        // if (!StringUtil.isNull(request.getParameter("endDate"))) {
        // whereSql += "AND substr(UPDT_DATE,1,8) <= '"
        // + request.getParameter("endDate") + "' ";
        // }
        // if (!StringUtil.isNull(request.getParameter("txnNum"))
        // && !"0000".equals(request.getParameter("txnNum"))) {
        // whereSql += "AND t.TXN_NUM = '" + request.getParameter("txnNum")
        // + "' ";
        // }
        // if (!StringUtil.isNull(request.getParameter("mchntNo"))) {
        // whereSql += "AND CARD_ACCP_ID = '"
        // + request.getParameter("mchntNo") + "' ";
        // }
        // if (!StringUtil.isNull(request.getParameter("brhId"))) {
        // whereSql += "AND ACQ_INST_ID_CODE in "
        // + InformationUtil.getCupBrhGroupString(request
        // .getParameter("brhId")) + " ";
        // }
        //
        // StringBuffer sb = new StringBuffer();
        // sb
        // .append("select substr(UPDT_DATE,1,8) as inst_date,substr(UPDT_DATE,9,6) as inst_time,"
        // +
        // "SYS_SEQ_NUM,PAN,CARD_ACCP_ID,CARD_ACCP_TERM_ID,RETRIVL_REF,AMT_TRANS,"
        // +
        // "ACQ_INST_ID_CODE,name.TXN_NAME,RESP_CODE from tbl_t_txn t left outer join "
        // + "tbl_txn_name name on (t.txn_num = name.txn_num) where 1 = 1 ");
        // sb.append(whereSql);
        // sb.append(" ORDER BY UPDT_DATE DESC");
        //
        // String countSql = "SELECT COUNT(*) FROM (" + sb.toString() + ")";
        //
        // List<Object[]> dataList = CommonFunction.getCommQueryDAO()
        // .findBySQLQuery(sb.toString(), begin,
        // Constants.QUERY_RECORD_COUNT);
        // Object[] data;
        // for (int i = 0; i < dataList.size(); i++) {
        // data = dataList.get(i);
        // data[7] = CommonFunction.transFenToYuan(data[7].toString());
        // dataList.set(i, data);
        // }
        // String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
        // countSql);
        // ret[0] = dataList;
        // ret[1] = count;
        // return ret;
    }

    /**
     * 查询历史风险记录
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getHistoryRiskRecords(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];

        String sql = "SELECT SA_TXN_CARD,SA_MCHT_NO,SA_TERM_NO,SA_TXN_NUM,"
                + "(case SA_TXN_AMT when null then 0 when ' ' then 0 else trim(SA_TXN_AMT)*1/100 end),SA_TXN_TIME"
                + " FROM TBL_CLC_MON WHERE (SA_TXN_CARD IN (SELECT SA_CARD_NO FROM TBL_CTL_CARD_INF) OR"
                + " SA_MCHT_NO IN (SELECT SA_MER_NO FROM TBL_CTL_MCHT_INF))";
        String countSql = "SELECT COUNT(*) FROM TBL_CLC_MON WHERE (SA_TXN_CARD IN (SELECT SA_CARD_NO FROM TBL_CTL_CARD_INF) OR"
                + " SA_MCHT_NO IN (SELECT SA_MER_NO FROM TBL_CTL_MCHT_INF))";

        String whereSql = "";
        String date = null;
        if (isNotEmpty(request.getParameter("startDate"))) {
            date = request.getParameter("startDate") + "000000";
            whereSql += " and SA_TXN_DATE >= '" + date.substring(4, date.length()) + "'";
        }
        if (isNotEmpty(request.getParameter("endDate"))) {
            date = request.getParameter("endDate") + "2359559";
            whereSql += " and SA_TXN_DATE <= '" + date.substring(4, date.length()) + "'";
        }
        if (isNotEmpty(request.getParameter("srBrhNo"))) {
            date = request.getParameter("srBrhNo");
            whereSql += " and SA_STLM_INST = '" + date + "' ";
        }

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql + whereSql, begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            // data[4] = CommonFunction.transFenToYuan(data[4].toString());
            dataList.set(i, data);
        }
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql + whereSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询卡黑名单历史交易
     * 
     * @param begin
     * @param request
     * @return 2010-8-24下午04:47:34
     */
    @SuppressWarnings("unchecked")
    public static Object[] getCardRiskHistory(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];

        StringBuffer whereSql = new StringBuffer();

        if (isNotEmpty(request.getParameter("srCardNo"))) {
            whereSql.append(" AND trim(sa_txn_card) = '" + request.getParameter("srCardNo").trim() + "' ");
        }
        if (isNotEmpty(request.getParameter("srBrhNo"))) {
            whereSql.append(" AND trim(SA_OPEN_INST) = '" + request.getParameter("srBrhNo").trim() + "' ");
        }
        if (isNotEmpty(request.getParameter("startDate"))) {
            whereSql.append(" AND trim(sa_txn_date) >= '" + request.getParameter("startDate").trim() + "' ");
        }
        if (isNotEmpty(request.getParameter("endDate"))) {
            whereSql.append(" AND trim(sa_txn_date) <= '" + request.getParameter("endDate").trim() + "' ");
        }

        String sql = "select sa_txn_card,sa_mcht_no,sa_term_no,txn_name,"
                + "(case when sa_txn_amt is null then 0 when sa_txn_amt  = ' ' then 0 else sa_txn_amt*1/100 end),"
                + "sa_txn_date,SA_TXN_TIME,SA_CLC_FLAG " + "from tbl_clc_mon,tbl_txn_name "
                + "where sa_txn_num = txn_num and trim(SA_CLC_RSN1) = '受控卡单笔交易额超限' ";

        sql += whereSql.toString();

        sql += "order by sa_txn_date desc,SA_TXN_TIME desc";

        String countSql = "select count(*) from (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 直联商户信息info all ctz
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntCupInfo(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        // String whereSql = " WHERE b.bank_no in " + operator.getBrhBelowId();
        String whereSql = " WHERE 1=1 ";

        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " and b.AGR_BR in " + operator.getBrhBelowId() + " ";
        }

        if (isNotEmpty(request.getParameter("brhId")) && !"0805083000".equals(request.getParameter("brhId"))) {
            whereSql += " AND INNER_ACQ_INST_ID = '" + request.getParameter("brhId") + "' ";
        }

        if (isNotEmpty(request.getParameter("mchtNo"))) {
            whereSql += " AND a.MCHT_NO = '" + request.getParameter("mchtNo") + "' ";
        }
        String acmchntId = request.getParameter("acmchntId");

        // 收单方商户号
        if (acmchntId != null && !acmchntId.trim().equals("")) {
            whereSql += " AND (a.MAPPING_MCHNTCDONE like '%" + acmchntId + "%' ";
            whereSql += " or a.MAPPING_MCHNTCDTWO like '%" + acmchntId + "%') ";
        }

        if (isNotEmpty(request.getParameter("mchtStatus"))) {
            whereSql += " AND a.mcht_status = '" + request.getParameter("mchtStatus") + "' ";
        }
        if (isNotEmpty(request.getParameter("mchtType"))) {
            whereSql += " AND a.mchnt_srv_tp = '" + request.getParameter("mchtType") + "' ";
        }
        if (isNotEmpty(request.getParameter("startDate"))) {
            whereSql += " AND substr(a.crt_ts,0,8) >= '" + request.getParameter("startDate") + "' ";
        }
        if (isNotEmpty(request.getParameter("endDate"))) {
            whereSql += " AND substr(a.crt_ts,0,8) <= '" + request.getParameter("endDate") + "' ";
        }
        if (isNotEmpty(request.getParameter("startDateU"))) {
            whereSql += " AND substr(a.upd_ts,0,8) >= '" + request.getParameter("startDateU") + "' ";
        }
        if (isNotEmpty(request.getParameter("endDateU"))) {
            whereSql += " AND substr(a.upd_ts,0,8) <= '" + request.getParameter("endDateU") + "' ";
        }
        Object[] ret = new Object[2];

        String sql = "SELECT a.mcht_no,a.mcht_nm,a.mcht_status,a.mchnt_srv_tp,a.crt_ts,a.upd_ts,"
                + "(case when a.mcht_status != 0 AND a.crt_ts = a.upd_ts then 'I' " + "when a.mcht_status != 0 AND a.crt_ts < a.upd_ts then 'U' "
                + "when  a.mcht_status = 0 then 'D' end) AS insetFlag,MAPPING_MCHNTTYPEONE,MAPPING_MCHNTCDONE,MAPPING_MCHNTTYPETWO,MAPPING_MCHNTCDTWO "
                + " FROM tbl_mcht_cup_info a left outer join TBL_MCHT_BASE_INF b on(a.mcht_no=b.MCHT_NO) " + whereSql.toString()
                + " ORDER BY a.MCHT_NO,a.mchnt_srv_tp,a.mcht_status,a.crt_ts ";
        String countSql = "SELECT COUNT(*) FROM tbl_mcht_cup_info a left outer join TBL_MCHT_BASE_INF b on(a.mcht_no=b.MCHT_NO) "
                + whereSql.toString();
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询商户黑名单历史交易
     * 
     * @param begin
     * @param request
     * @return 2010-8-24下午04:47:34
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchtRiskHistory(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];

        StringBuffer whereSql = new StringBuffer();

        if (isNotEmpty(request.getParameter("srMerNo"))) {
            whereSql.append(" AND trim(sa_mcht_no) = '" + request.getParameter("srMerNo").trim() + "' ");
        }
        if (isNotEmpty(request.getParameter("srBrhNo"))) {
            whereSql.append(" AND trim(SA_OPEN_INST) = '" + request.getParameter("srBrhNo").trim() + "' ");
        }
        if (isNotEmpty(request.getParameter("startDate"))) {
            whereSql.append(" AND trim(sa_txn_date) >= '" + request.getParameter("startDate").trim() + "' ");
        }
        if (isNotEmpty(request.getParameter("endDate"))) {
            whereSql.append(" AND trim(sa_txn_date) <= '" + request.getParameter("endDate").trim() + "' ");
        }

        String sql = "select sa_txn_card,sa_mcht_no,sa_term_no,txn_name,"
                + "(case when sa_txn_amt is null then 0 when sa_txn_amt  = ' ' then 0 else sa_txn_amt*1/100 end),"
                + "sa_txn_date,SA_TXN_TIME,SA_CLC_FLAG " + "from tbl_clc_mon,tbl_txn_name "
                + "where sa_txn_num = txn_num and trim(SA_CLC_RSN1) = '商户单笔交易额超限' ";

        sql += whereSql.toString();

        sql += "order by sa_txn_date desc,SA_TXN_TIME desc";

        String countSql = "select count(*) from (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 获得监控模型
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getRiskModelInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String sql = "SELECT SA_MODEL_KIND,SA_LIMIT_NUM,SA_LIMIT_AMOUNT_SINGLE,SA_LIMIT_AMOUNT_TOTLE,SA_BE_USE,SA_ACTION, MODI_ZONE_NO,MODI_OPR_ID,MODI_TIME "
                + "FROM TBL_RISK_INF " + "WHERE SA_BRANCH_CODE in" + operator.getBrhBelowId()
                + "AND SUBSTR(SA_MODEL_KIND,1,1) != 'R' order by SA_MODEL_KIND";
        String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            // data[2] = CommonFunction.transFenToYuan(data[2].toString());
            dataList.set(i, data);
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 获得监控模型
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getRRiskModelInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String sql = "SELECT SA_MODEL_KIND,SA_LIMIT_AMOUNT,SA_BE_USE,SA_ACTION, MODI_ZONE_NO,MODI_OPR_ID,MODI_TIME " + "FROM TBL_RISK_INF "
                + "WHERE SA_BRANCH_CODE in'" + operator.getBrhBelowId() + "AND SUBSTR(SA_MODEL_KIND,1,1) = 'R' order by SA_MODEL_KIND";
        String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            // data[2] = CommonFunction.transFenToYuan(data[2].toString());
            dataList.set(i, data);
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 获得监控模型修改记录
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getRiskModelInfoUpdLog(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        // Operator operator = (Operator) request.getSession().getAttribute(
        // Constants.OPERATOR_INFO);
        StringBuffer sbSql = new StringBuffer();
        sbSql.append("SELECT b.SA_MODEL_KIND,a.SA_BRANCH_CODE,b.SA_FIELD_NAME,b.SA_FIELD_VALUE_BF,b.SA_FIELD_VALUE,b.MODI_ZONE_NO,b.MODI_TIME");
        sbSql.append(" FROM TBL_RISK_INF_UPD_LOG b,TBL_RISK_INF a WHERE 1=1 AND a.SA_MODEL_KIND = b.SA_MODEL_KIND ");
        String countSql = "SELECT COUNT(*) FROM TBL_RISK_INF_UPD_LOG b,TBL_RISK_INF a WHERE a.SA_MODEL_KIND = b.SA_MODEL_KIND";

        StringBuffer whereSql = new StringBuffer();

        String date = null;
        if (isNotEmpty(request.getParameter("startDate"))) {
            date = request.getParameter("startDate") + "000000";
            whereSql.append("  AND b.MODI_TIME >= '");
            whereSql.append(date + "'");
        }
        if (isNotEmpty(request.getParameter("endDate"))) {
            date = request.getParameter("endDate") + "2359559";
            whereSql.append("  AND b.MODI_TIME <= '");
            whereSql.append(date + "'");
        }
        if (isNotEmpty(request.getParameter("srBrhNo"))) {
            date = request.getParameter("srBrhNo");
            whereSql.append("  AND a.SA_BRANCH_CODE = '");
            whereSql.append(date + "'");
        }
        if (isNotEmpty(request.getParameter("saModelKind"))) {
            date = request.getParameter("saModelKind");
            whereSql.append(" AND a.SA_MODEL_KIND like '%");
            whereSql.append(date + "%' ");
        }

        whereSql.append(" ORDER BY b.MODI_TIME DESC ");
        List<Object[]> dataList = CommonFunction.getCommQueryDAO()
                .findBySQLQuery(sbSql.toString() + whereSql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql + whereSql.toString());

        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询银联卡信息
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getBankBinInf(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];

        StringBuffer whereSql = new StringBuffer();

        if (!StringUtil.isNull(request.getParameter("insIdCd"))) {
            String insIdcd = request.getParameter("insIdCd").trim();
            insIdcd = insIdcd.substring(0, insIdcd.indexOf("-"));
            whereSql.append(" and INS_ID_CD = '" + insIdcd + "'");
        }

        if (!StringUtil.isNull(request.getParameter("cardType"))) {
            whereSql.append(" and CARD_TP = '" + request.getParameter("cardType").trim() + "'");
        }

        if (!StringUtil.isNull(request.getParameter("binStartNo"))) {
            whereSql.append(" and BIN_STA_NO like '%");
            whereSql.append(request.getParameter("binStartNo").trim());
            whereSql.append("%'");
        }

        String sql = "SELECT IND,INS_ID_CD,CARD_DIS,CARD_TP,ACC1_OFFSET,ACC1_LEN,ACC1_TNUM,"
                + "BIN_OFFSET,BIN_LEN,BIN_STA_NO,BIN_END_NO,BIN_TNUM FROM TBL_BANK_BIN_INF where 1 = 1 ";
        sql += whereSql.toString();
        sql += " ORDER BY IND,INS_ID_CD";

        String countSql = "select count(1) from (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询银联CA公钥
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getEmvPara(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];

        String sql = "SELECT USAGE_KEY,PARA_IDX,PARA_ORG,PARA_VAL FROM TBL_EMV_PARA WHERE trim(USAGE_KEY) = '1' order by PARA_IDX";
        String countSql = "SELECT COUNT(*) FROM TBL_EMV_PARA WHERE trim(USAGE_KEY) = '1'";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

        List<Object[]> newDataList = new ArrayList();

        for (int i = 0; i < dataList.size(); i++) {

            Object[] obj = new Object[11];

            String USAGE_KEY = (String) dataList.get(i)[0];
            String PARA_IDX = (String) dataList.get(i)[1];
            String PARA_ORG = (String) dataList.get(i)[2];
            String PARA_VAL = (String) dataList.get(i)[3];

            String paraIdx = PARA_IDX; // 索引
            String usageKey = USAGE_KEY; // 参数种类
            String paraOrg = PARA_ORG; // 参数名称

            String a9F06 = PARA_VAL.substring(6, 16); // 注册应用提供商标识
            PARA_VAL = PARA_VAL.substring(16);

            String a9F22 = PARA_VAL.substring(6, 8); // 根CA公钥索引
            PARA_VAL = PARA_VAL.substring(8);

            String DF05 = PARA_VAL.substring(6, 22); // 证书失效日期
            char[] DF05Char = DF05.toCharArray();
            StringBuffer DF05StringBuffer = new StringBuffer();
            for (int j = 1; j < DF05Char.length; j = j + 2)
                DF05StringBuffer.append(DF05Char[j]);
            DF05 = DF05StringBuffer.toString();
            PARA_VAL = PARA_VAL.substring(22);

            String DF06 = PARA_VAL.substring(6, 8); // 哈希算法标识
            PARA_VAL = PARA_VAL.substring(8);

            String DF07 = PARA_VAL.substring(6, 8); // 数字签名算法长度
            PARA_VAL = PARA_VAL.substring(8);

            String DF02 = null; // 根CA公钥模
            PARA_VAL = PARA_VAL.substring(4);
            String lengthSi = PARA_VAL.substring(0, 2);
            if (lengthSi.equals("81")) {

                String lengthString = PARA_VAL.substring(2, 4);
                int length = Integer.parseInt(lengthString, 16);
                DF02 = PARA_VAL.substring(4, length * 2 + 4);
                PARA_VAL = PARA_VAL.substring(4 + length * 2);
            } else if (lengthSi.equals("82")) {

                String lengthString = PARA_VAL.substring(3, 6);
                int length = Integer.parseInt(lengthString, 16);
                DF02 = PARA_VAL.substring(6, length * 2 + 6);
                PARA_VAL = PARA_VAL.substring(6 + length * 2);
            } else {

                String lengthString = PARA_VAL.substring(0, 2);
                int length = Integer.parseInt(lengthString, 16);
                DF02 = PARA_VAL.substring(2, length * 2 + 2);
                PARA_VAL = PARA_VAL.substring(2 + length * 2);
            }

            String DF04 = PARA_VAL.substring(6, 8); // 公钥指数算法长度
            PARA_VAL = PARA_VAL.substring(8);

            String DF03 = PARA_VAL.substring(6); // 哈希结果

            obj[0] = paraIdx;
            obj[1] = usageKey;
            obj[2] = paraOrg;
            obj[3] = a9F06;
            obj[4] = a9F22;
            obj[5] = DF05;
            obj[6] = DF06;
            obj[7] = DF07;
            obj[8] = DF02;
            obj[9] = DF04;
            obj[10] = DF03;

            newDataList.add(obj);
        }

        ret[0] = newDataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询虚拟柜员
     */
    @SuppressWarnings("unchecked")
    public static Object[] getBrhTlrInfo(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" WHERE " + "BRH_ID IN " + operator.getBrhBelowId());
        if (isNotEmpty(request.getParameter("brhId"))) {
            whereSql.append(" AND BRH_ID = '" + request.getParameter("brhId") + "' ");
        }
        if (isNotEmpty(request.getParameter("tlrId"))) {
            whereSql.append(" AND TLR_ID  = '" + request.getParameter("tlrId") + "' ");
        }
        String sql = "SELECT BRH_ID,TLR_ID,TLR_STA,RESV1,LAST_UPD_OPR_ID,REC_UPD_TS,REC_CRT_TS FROM TBL_BRH_TLR_INFO " + whereSql.toString();
        String countSql = "SELECT COUNT(*) FROM TBL_BRH_TLR_INFO " + whereSql.toString();
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    public static Object[] getBrhTlrInfo01(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        String sql = "SELECT BRH_ID,TLR_ID,TLR_STA,RESV1,LAST_UPD_OPR_ID,REC_UPD_TS,REC_CRT_TS "
                + "FROM TBL_BRH_TLR_INFO WHERE TRIM(BRH_ID) = '9901' ";
        String countSql = "SELECT COUNT(*) FROM TBL_BRH_TLR_INFO WHERE TRIM(BRH_ID) = '9901' ";
        // + whereSql.toString();
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询计费算法
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTblInfDiscCd(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        StringBuffer whereSql = new StringBuffer();
        // whereSql.append(" WHERE " + "DISC_ORG IN " +
        // operator.getBrhBelowId());
        whereSql.append(" WHERE 1 = 1 ");
        if (isNotEmpty(request.getParameter("discOrg"))) {
            whereSql.append(" AND DISC_ORG = '" + request.getParameter("discOrg") + "' ");
        }
        if (isNotEmpty(request.getParameter("discCd"))) {
            whereSql.append(" AND DISC_CD like '%" + request.getParameter("discCd") + "%' ");
        }
        if (isNotEmpty(request.getParameter("discNm"))) {
            whereSql.append(" AND DISC_NM like '%" + request.getParameter("discNm") + "%' ");
        }
        String sql = "SELECT DISC_CD,DISC_NM,DISC_ORG,LAST_OPER_IN,REC_UPD_USER_ID,substr(REC_UPD_TS,1,8),substr(REC_CRT_TS,1,8) FROM TBL_INF_DISC_CD "
                + whereSql.toString();
        sql += " order by DISC_CD";
        // System.out.println("Print:" + sql);
        String countSql = "SELECT COUNT(*) FROM TBL_INF_DISC_CD " + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询计费算法
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTblInfDiscCd2(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        StringBuffer whereSql = new StringBuffer();
        // whereSql.append(" WHERE " + "DISC_ORG IN " +
        // operator.getBrhBelowId());
        whereSql.append(" WHERE 1 = 1 AND  LAST_OPER_IN ='1' ");
        if (isNotEmpty(request.getParameter("discOrg"))) {
            whereSql.append(" AND DISC_ORG = '" + request.getParameter("discOrg") + "' ");
        }
        if (isNotEmpty(request.getParameter("discCd"))) {
            whereSql.append(" AND DISC_CD like '%" + request.getParameter("discCd") + "%' ");
        }
        if (isNotEmpty(request.getParameter("discNm"))) {
            whereSql.append(" AND DISC_NM like '%" + request.getParameter("discNm") + "%' ");
        }
        String sql = "SELECT DISC_CD,DISC_NM,DISC_ORG,LAST_OPER_IN,REC_UPD_USER_ID,substr(REC_UPD_TS,1,8),substr(REC_CRT_TS,1,8) FROM TBL_INF_DISC_CD "
                + whereSql.toString();
        sql += " order by DISC_CD";
        // System.out.println("Print:" + sql);
        String countSql = "SELECT COUNT(*) FROM TBL_INF_DISC_CD " + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询计费算法
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTblInfDiscCd1(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        StringBuffer whereSql = new StringBuffer();
        // whereSql.append(" WHERE " + "DISC_ORG IN " +
        // operator.getBrhBelowId());
        whereSql.append(" WHERE 1 = 1 AND  LAST_OPER_IN ='0' ");
        if (isNotEmpty(request.getParameter("discOrg"))) {
            whereSql.append(" AND DISC_ORG = '" + request.getParameter("discOrg") + "' ");
        }
        if (isNotEmpty(request.getParameter("discCd"))) {
            whereSql.append(" AND DISC_CD like '%" + request.getParameter("discCd") + "%' ");
        }
        if (isNotEmpty(request.getParameter("discNm"))) {
            whereSql.append(" AND DISC_NM like '%" + request.getParameter("discNm") + "%' ");
        }
        String sql = "SELECT DISC_CD,DISC_NM,DISC_ORG,LAST_OPER_IN,REC_UPD_USER_ID,substr(REC_UPD_TS,1,8),substr(REC_CRT_TS,1,8) FROM TBL_INF_DISC_CD "
                + whereSql.toString();
        sql += " order by DISC_CD";
        // System.out.println("Print:" + sql);
        String countSql = "SELECT COUNT(*) FROM TBL_INF_DISC_CD " + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 清算凭证
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getSettleDocInfo(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String sql = "select BRH_ID,EXCHANGE_NO,PAYER_NAME,PAYER_ACCOUNT_NO,PAY_BANK_NO,IN_BANK_SETTLE_NO,SETTLE_BUS_NO,INNER_ACCT,INNER_ACCT1,INNER_ACCT2 from SETTLE_DOC_INFO";
        sql = sql + " WHERE " + "BRH_ID IN " + operator.getBrhBelowId();
        String countSql = "select count(*) from SETTLE_DOC_INFO";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询计费算法
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getDiscInf(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        String discId = request.getParameter("discCd");

        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" WHERE " + "trim(DISC_ID) = '" + discId.trim() + "'");

        String sql = "SELECT TXN_NUM,CARD_TYPE," + "(case when MIN_FEE = to_number('9999999999999.99') then null else MIN_FEE end),"
                + "(case when MAX_FEE = to_number('9999999999999.99') then null else MAX_FEE end)," + "FLAG,FEE_VALUE FROM TBL_HIS_DISC_ALGO "
                + whereSql.toString();

        sql += " order by CARD_TYPE";

        String countSql = "SELECT COUNT(1) FROM TBL_HIS_DISC_ALGO " + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询计费算法
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getDiscInf1(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        String discId = request.getParameter("discCd");

        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" WHERE " + "trim(DISC_ID) = '" + discId.trim() + "'");

        String sql = "SELECT TXN_NUM,CARD_TYPE," + "(case when MIN_FEE = to_number('9999999999999.99') then null else MIN_FEE end),"
                + "(case when MAX_FEE = to_number('9999999999999.99') then null else MAX_FEE end)," + "FLAG,FEE_VALUE FROM TBL_HIS_DISC_ALGO "
                + whereSql.toString();

        sql += " order by CARD_TYPE";

        String countSql = "SELECT COUNT(1) FROM TBL_HIS_DISC_ALGO " + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询计费算法
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getDiscInf2(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        String discId = request.getParameter("discCd");

        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" WHERE " + "trim(DISC_ID) = '" + discId.trim() + "'");

        String sql = "SELECT TXN_NUM,CARD_TYPE," + "(case when MIN_FEE = to_number('9999999999999.99') then null else MIN_FEE end),"
                + "(case when MAX_FEE = to_number('9999999999999.99') then null else MAX_FEE end)," + "FLAG,FEE_VALUE FROM TBL_HIS_DISC_ALGO "
                + whereSql.toString();

        sql += " order by CARD_TYPE";

        String countSql = "SELECT COUNT(1) FROM TBL_HIS_DISC_ALGO " + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 证书影像
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getImgInf(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        String imagesId = request.getParameter("imagesId");

        List<Object[]> dataList = new ArrayList<Object[]>();
        String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);

        basePath = basePath.replace("\\", "/");
        File fl = new File(basePath);
        FileFilter filter = new FileFilter(imagesId);
        // 启用过滤
        File[] files = fl.listFiles(filter);

        // File[] files = fl.listFiles();

        if (null == files) {
            ret[0] = dataList;
            ret[1] = dataList.size();
            return ret;
        }
        for (File file : files) {
            Object[] obj = new Object[6];
            obj[0] = "imageSelf" + dataList.size();
            obj[1] = "btBig" + dataList.size();
            obj[2] = "btDel" + dataList.size();
            try {
                BufferedImage bi = ImageIO.read(file);
                double width = bi.getWidth();
                double height = bi.getHeight();
                double rate = 0;
                // 等比例缩放
                if (width > 400 || height > 400) {
                    if (width > height) {
                        rate = 400 / width;
                        width = 400;
                        height = height * rate;
                    } else {
                        rate = 400 / height;
                        height = 400;
                        width = width * rate;
                    }
                }
                obj[3] = (int) width;
                obj[4] = (int) height;
            } catch (Exception e) {
                obj[3] = 400;
                obj[4] = 400;
                e.printStackTrace();
            }
            obj[5] = file.getName();
            dataList.add(obj);
        }
        ret[0] = dataList;
        ret[1] = dataList.size();
        return ret;
    }

    /**
     * 对账任务处理
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getBatchMission(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];

        String sql = "select a.BAT_ID,a.BAT_DSP,NVL(b.BAT_STATE,0),a.BAT_NOTE,NVL(b.BAT_STATE,0) from "
                + "((SELECT BAT_ID,BAT_DSP,BAT_STATE,BAT_NOTE,BAT_CLS FROM TBL_BAT_MAIN_CTL "
                + "where substr(BAT_ID,1,1) = 'B' and TASK_START_FLG = '0') a " + "left outer join "
                + "(select DATE_SETTLMT,BAT_ID,BAT_STATE from TBL_BAT_MAIN_CTL_DTL " + "where trim(DATE_SETTLMT) = '" + request.getParameter("date")
                + "') b " + "on (a.BAT_ID = b.BAT_ID)) ORDER BY a.BAT_ID";

        String countSql = "SELECT COUNT(1) FROM ( " + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, 100);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户清算
     */
    @SuppressWarnings("unchecked")
    public static Object[] getBatchMissionMcht(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];

        String sql = "select a.BAT_ID,a.BAT_DSP,NVL(b.BAT_STATE,0),a.BAT_NOTE,NVL(b.BAT_STATE,0) from "
                + "((SELECT BAT_ID,BAT_DSP,BAT_STATE,BAT_NOTE,BAT_CLS FROM TBL_BAT_MAIN_CTL "
                + "where substr(BAT_ID,1,1) = 'S' and TASK_START_FLG = '0') a " + "left outer join "
                + "(select DATE_SETTLMT,BAT_ID,BAT_STATE from TBL_BAT_MAIN_CTL_DTL " + "where trim(DATE_SETTLMT) = '" + request.getParameter("date")
                + "') b " + "on (a.BAT_ID = b.BAT_ID)) ORDER BY a.BAT_ID";

        String countSql = "SELECT COUNT(1) FROM ( " + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, 100);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /*
     * 脱机对账任务处理
     * 
     * @param begin
     * 
     * @param request
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] batchMissionOutline(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];

        String sql = "select a.BAT_ID,a.BAT_DSP,NVL(b.BAT_STATE,0),a.BAT_NOTE,NVL(b.BAT_STATE,0) from "
                + "((SELECT BAT_ID,BAT_DSP,BAT_STATE,BAT_NOTE,BAT_CLS FROM TBL_BAT_MAIN_CTL "
                + "where substr(BAT_ID,1,1) = 'C' and TASK_START_FLG = '0') a " + "left outer join "
                + "(select DATE_SETTLMT,BAT_ID,BAT_STATE from TBL_BAT_MAIN_CTL_DTL " + "where trim(DATE_SETTLMT) = '" + request.getParameter("date")
                + "') b " + "on (a.BAT_ID = b.BAT_ID)) ORDER BY a.BAT_ID";

        String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, 100);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

        // 读取文件信息
        // String s =
        // "select BAT_ID,FILE_NAME,trim(FILE_STA),MISC_1,trim(FILE_STA) as STA from TBL_ON_LINE_CTL "
        // +
        // "WHERE trim(DATE_SETTLMT) = '" + request.getParameter("date").trim()
        // + "'";
        // List<Object[]> list =
        // CommonFunction.getCommQueryDAO().findBySQLQuery(s);
        // if (null != list && !list.isEmpty()) {
        // Iterator<Object[]> it = list.iterator();
        // int index = 1;
        // while (it.hasNext()) {
        // Object[] obj = it.next();
        // obj[0] = "F000" + String.valueOf(index++);
        // obj[1] = "获取文件[" + obj[1] + "]";
        // if (null != obj[1] && obj[1].toString().indexOf("ICONF") != -1) {
        // obj[2] = "I" + obj[2];
        // obj[4] = "I" + obj[4];
        // } else {
        // obj[2] = "F" + obj[2];
        // obj[4] = "F" + obj[4];
        // }
        // dataList.add(index - 2, obj);
        // }
        // }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 获取清算信息
     * 
     * @param begin
     * @param request
     * @return 2011-7-29下午05:51:01
     */
    @SuppressWarnings("unchecked")
    public static Object[] getBatchDtl(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        String date = request.getParameter("date");
        StringBuffer whereSql = new StringBuffer(" WHERE t1.BRH_CODE = t2.BRH_ID and t1.BRH_CODE != '9900'");
        if (!StringUtil.isEmpty(date))
            whereSql.append("and t1.DATE_SETTLMT='").append(date.split("T")[0].replaceAll("-", "")).append("'");
        else
            whereSql.append("and t1.DATE_SETTLMT='").append(CommonFunction.getCurrentDate()).append("'");

        String sql = "SELECT DATE_SETTLMT,BRH_CODE,SETTLE_AMT_1,SETTLE_AMT_2,SETTLE_AMT_3,t2.BRH_NAME,"
                + "(SETTLE_AMT_1-SETTLE_AMT_2-SETTLE_AMT_3) as SETTLE_AMT,SEND_NUM,SETTLE_FLAG as flag,FAIL_RESN,SETTLE_FLAG"
                + " FROM TBL_BRH_INFILE_DTL t1,TBL_BRH_INFO t2" + whereSql.toString() + " order by BRH_CODE";
        String countSql = "SELECT COUNT(*) FROM TBL_BRH_INFILE_DTL t1,TBL_BRH_INFO t2" + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, 100);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 获取总行资金清算信息
     * 
     * @param begin
     * @param request
     * @return 2011-8-2上午11:08:55
     */
    @SuppressWarnings("unchecked")
    public static Object[] getBatchBankInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        String date = request.getParameter("date");
        StringBuffer whereSql = new StringBuffer(" WHERE t1.BRH_CODE = '9900' ");
        if (!StringUtil.isEmpty(date))
            whereSql.append("and t1.DATE_SETTLMT='").append(date.split("T")[0].replaceAll("-", "")).append("'");
        else
            whereSql.append("and t1.DATE_SETTLMT='").append(CommonFunction.getCurrentDate()).append("'");
        String sql = "SELECT DATE_SETTLMT,BRH_CODE,SETTLE_AMT_1,SETTLE_AMT_2,SETTLE_AMT_3,(SETTLE_AMT_1-SETTLE_AMT_2-SETTLE_AMT_3) as SETTLE_AMT"
                + " FROM TBL_BRH_INFILE_DTL t1" + whereSql.toString() + " order by BRH_CODE";
        String countSql = "SELECT COUNT(*) FROM TBL_BRH_INFILE_DTL t1" + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, 1);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 获取商户下挂终端
     * 
     * @param begin
     * @param request
     * @return 2011-7-29下午05:51:01
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntTermInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        String mchntNo = request.getParameter("mchntNo");
        if (mchntNo == null || "".equals(mchntNo)) {
            mchntNo = " ";
        } else {
            mchntNo = mchntNo.trim();
        }
        String sql = "select TERM_ID,TERM_STA,TERM_SIGN_STA,MCHT_CD FROM TBL_TERM_INF WHERE MCHT_CD = '" + mchntNo + "' ";
        String countSql = "SELECT COUNT(1) FROM (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 判断字符串是否为空
     * 
     * @param str
     * @return
     */
    private static boolean isNotEmpty(String str) {
        if (str != null && !"".equals(str.trim()))
            return true;
        else
            return false;
    }

    /**
     * 查询商户入账结果
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntSettleInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        StringBuffer whereSql = new StringBuffer();

        whereSql.append(" WHERE trim(BRH_CODE) IN " + operator.getBrhBelowId());

        if (isNotEmpty(request.getParameter("brhId"))) {
            whereSql.append(" AND trim(BRH_CODE) in " + InformationUtil.getBrhGroupString(request.getParameter("brhId")));
        }
        if (isNotEmpty(request.getParameter("date"))) {
            whereSql.append(" AND START_DATE = '" + request.getParameter("date") + "' ");
        }
        if (isNotEmpty(request.getParameter("mchntId"))) {
            whereSql.append(" AND trim(MCHT_NO) = '" + request.getParameter("mchntId") + "' ");
        }
        if (isNotEmpty(request.getParameter("mchntAcct"))) {
            whereSql.append(" AND MCHT_ACCT = '" + request.getParameter("mchntAcct") + "' ");
        }
        if (isNotEmpty(request.getParameter("settleFlag"))) {
            whereSql.append(" AND SETTLE_FLAG = '" + request.getParameter("settleFlag") + "' ");
        }

        String sql = "SELECT BRH_CODE,MCHT_NO,MCHT_NM," + "(case ACCT_TYPE when 'A' then '本行对公账户' when 'P' then '本行对私账户或单位卡' "
                + "when 'O' then '他行对公账户' when 'S' then '他行对私账户' else '' end)," + "MCHT_ACCT,ACCT_NM,"
                + "DATE_SETTLMT,SETTLE_AMT,TXN_AMT,SETTLE_FEE1," + "TXN_NUM,SETTLE_FLAG,FAIL_RESN,SETTLE_FLAG AS FLAG FROM "
                + "TBL_MCHNT_INFILE_DTL ";

        sql += whereSql.toString();

        sql += "order by BRH_CODE,MCHT_NO,DATE_SETTLMT ";

        String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询商户入账结果
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntSettleInfoNew(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        StringBuffer whereSql = new StringBuffer();

        whereSql.append(" WHERE trim(BRH_CODE) IN " + operator.getBrhBelowId());

        if (isNotEmpty(request.getParameter("brhId"))) {
            whereSql.append(" AND trim(BRH_CODE) in " + InformationUtil.getBrhGroupString(request.getParameter("brhId")));
        }
        if (isNotEmpty(request.getParameter("date"))) {
            whereSql.append(" AND START_DATE = '" + request.getParameter("date") + "' ");
        }
        if (isNotEmpty(request.getParameter("mchntId"))) {
            whereSql.append(" AND trim(MCHT_NO) = '" + request.getParameter("mchntId") + "' ");
        }
        if (isNotEmpty(request.getParameter("mchntAcct"))) {
            whereSql.append(" AND MCHT_ACCT = '" + request.getParameter("mchntAcct") + "' ");
        }
        // if (isNotEmpty(request.getParameter("settleFlag"))) {
        // whereSql.append(" AND SETTLE_FLAG = '"
        // + request.getParameter("settleFlag") + "' ");
        // }

        String sql = "SELECT BRH_CODE,MCHT_NO,MCHT_NM,"
                // +
                // "(case ACCT_TYPE when 'A' then '本行对公账户' when 'P' then '本行对私账户或单位卡' "
                // +
                // "when 'O' then '他行对公账户' when 'S' then '他行对私账户' else '' end),"
                // + "MCHT_ACCT,"
                + "DATE_SETTLMT,SETTLE_AMT,TXN_AMT,SETTLE_FEE1," + "TXN_NUM,SETTLE_FLAG,FAIL_RESN,SETTLE_FLAG AS FLAG FROM "
                + "TBL_MCHNT_INFILE_DTL ";

        sql += whereSql.toString();

        sql += "order by BRH_CODE,MCHT_NO,DATE_SETTLMT ";

        String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    public static Object[] riskTxnInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];

        StringBuffer whereSql = new StringBuffer();

        if (isNotEmpty(request.getParameter("saTxnCard"))) {
            whereSql.append(" AND trim(sa_txn_card) = '" + request.getParameter("saTxnCard").trim() + "' ");
        }
        if (isNotEmpty(request.getParameter("saMchtNo"))) {
            whereSql.append(" AND trim(sa_mcht_no) = '" + request.getParameter("saMchtNo").trim() + "' ");
        }
        if (isNotEmpty(request.getParameter("startDate"))) {
            whereSql.append(" AND trim(sa_txn_date) >= '" + request.getParameter("startDate").trim() + "' ");
        }
        if (isNotEmpty(request.getParameter("endDate"))) {
            whereSql.append(" AND trim(sa_txn_date) <= '" + request.getParameter("endDate").trim() + "' ");
        }

        String sql = "select sa_txn_card,sa_mcht_no,sa_term_no,txn_name,"
                + "(case when sa_txn_amt is null then 0 when sa_txn_amt  = ' ' then 0 else sa_txn_amt*1/100 end),"
                + "sa_txn_date,SA_TXN_TIME,SA_CLC_FLAG " + "from tbl_clc_mon,tbl_txn_name "
                + "where sa_txn_num = txn_num and trim(SA_CLC_RSN1) in (select SA_MODEL_KIND from TBL_RISK_INF) ";

        sql += whereSql.toString();

        sql += "order by sa_txn_date desc,SA_TXN_TIME desc";

        String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 代发记录查询
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getSettleLog(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        StringBuffer whereSql = new StringBuffer();

        whereSql.append(" WHERE trim(BRH_ID) IN " + operator.getBrhBelowId());
        // 代发机构
        if (isNotEmpty(request.getParameter("brhId"))) {
            whereSql.append(" AND trim(BRH_ID) in " + InformationUtil.getBrhGroupString(request.getParameter("brhId")));
        }
        // 代发日期
        if (isNotEmpty(request.getParameter("date"))) {
            whereSql.append(" AND INST_DATE = '" + request.getParameter("date") + "' ");
        }

        String sql = "SELECT BRH_ID,INST_DATE,START_DATE,END_DATE,SETTLE_AMT,TXN_AMT,SETTLE_FEE1,"
                + "MCHNT_COUNT,SETTLE_AMT_DIS,TXN_AMT_DIS,SETTLE_FEE1_DIS,MCHNT_COUNT_DIS," + "(SETTLE_AMT + SETTLE_AMT_DIS) AS SETTLE_AMT_TAL,"
                + "(TXN_AMT + TXN_AMT_DIS) AS TXN_AMT_TAL," + "(SETTLE_FEE1 + SETTLE_FEE1_DIS) AS SETTLE_FEE1_TAL,"
                + "(MCHNT_COUNT + MCHNT_COUNT_DIS) AS MCHNT_COUNT_TAL,"
                + "SUCC_FILE,FAIL_FILE,STLM_FLAG,STLM_FLAG2,OPR_ID1,OPR_ID2,STLM_DSP,SUBSTR(REC_CRE_TIME,9),SUBSTR(REC_UPD_TIME,9) "
                + "FROM TBL_INFILE_OPT ";

        sql += whereSql.toString();

        sql += "ORDER BY BRH_ID,INST_DATE";

        String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询专业服务机构信息
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getOrganInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String orgId = request.getParameter("orgId");
        String branch = request.getParameter("branch");
        StringBuffer whereSql = new StringBuffer("WHERE branch in").append(operator.getBrhBelowId());
        if (orgId != null && !orgId.equals("")) whereSql.append(" and org_Id like'%").append(orgId).append("%'");
        if (branch != null && !branch.equals("")) whereSql.append(" and branch='").append(branch).append("'");

        whereSql.append(" order by ORG_ID");
        StringBuffer sql = new StringBuffer("SELECT ORG_ID,(select brh_name from tbl_brh_info where brh_id=t.branch),ORG_NAME,REMARKS,"
                + "MISC,CRT_OPR,CRT_TS,LAST_UPD_OPR,LAST_UPD_TS," + "(select city_name from cst_city_code where city_code_new=t.area_code),"
                + "PRINCIPAL,LINKMAN,LINK_TEL " + "FROM TBL_PROFESSIONAL_ORGAN t ");

        sql.append(whereSql);

        String countSql = "SELECT COUNT(1) FROM TBL_PROFESSIONAL_ORGAN " + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 问卷查询
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] PaperRecInf(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];

        String curPaperId = InformationUtil.getCurPaperId();

        // 现使用RESERVE1来保存是否为当前试卷，为当前试卷RESERVE1=1，否则为0

        String sql = "select r.PAPER_ID,r.RESERVE1,r.RESERVE2,r.CRT_USER,substr(r.CRT_TIME,1,8),"
                + "(case when p.QUESTION = r.PAPER_ID then '1' else '0' end) as STA "
                + "from TBL_PAPER_REC_INF r,TBL_PAPER_DEF_INF p where p.PAPER_ID = 'PAPER_STATUS' order by r.CRT_TIME DESC";

        String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 问卷历史查询
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] PaperHisInf(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];

        StringBuffer whereSql = new StringBuffer();

        // 问卷编号
        if (isNotEmpty(request.getParameter("PAPER_ID"))) {
            whereSql.append(" WHERE PAPER_ID = '" + request.getParameter("PAPER_ID") + "' ");
        }

        String sql = "SELECT QUES_ID,QUES_INDEX,QUESTION,OPTIONS,REMARKS FROM TBL_PAPER_HIS_INF ";

        sql += whereSql.toString();

        sql += "ORDER BY QUES_INDEX";

        String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 实时问卷查询
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] PaperDefInf(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];

        String sql = "SELECT PAPER_ID,QUES_ID,QUES_INDEX,QUESTION,OPTIONS,REMARKS FROM TBL_PAPER_DEF_INF ";

        sql += "where PAPER_ID != 'PAPER_STATUS' ";

        sql += "ORDER BY QUES_INDEX";

        String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户评级查询 sign_Inst_Id ACQ_INST_ID 字段意义替换了、若有问题看着改下 商户评级查询现在页面貌似没用到、
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] PaperLelInf(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        StringBuffer sb = new StringBuffer(" where trim(MCHT_ID) = trim(b.MCHT_NO) ");
        sb.append("and b.ACQ_INST_ID in " + operator.getBrhBelowId());

        if (!StringUtil.isNull(request.getParameter("ACQ_INST_ID"))) {
            sb.append(" and b.ACQ_INST_ID in " + InformationUtil.getBrhGroupString(request.getParameter("ACQ_INST_ID")));
        }

        if (!StringUtil.isNull(request.getParameter("MCHT_NO"))) {
            sb.append(" and MCHT_ID = '" + request.getParameter("MCHT_NO") + "' ");
        }

        if (!StringUtil.isNull(request.getParameter("MCHT_LEVEL"))) {
            sb.append(" and MCHT_LEVEL = '" + request.getParameter("MCHT_LEVEL") + "' ");
        }

        String sql = "SELECT SEL_MCHT_ID, PAPER_ID, MCHT_ID,b.MCHT_NM, MCHT_POINT, MCHT_LEVEL, RESERVE1, RESERVE2, CRT_USER, substr(CRT_TIME,1,8) "
                + "FROM TBL_PAPER_LEL_INF,TBL_MCHT_BASE_INF b ";

        sql += sb.toString();

        sql += " order by MCHT_ID,CRT_TIME desc";

        String countSql = "select count(*) from (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 获取选项信息
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getQuestionInf(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];

        StringBuffer whereSql = new StringBuffer();

        String sql = "SELECT OPT_ID,OPT,POINT FROM TBL_PAPER_OPT_INF " + "where trim(QUES_ID) = '" + request.getParameter("quesId").trim() + "' ";
        sql += "order by POINT DESC";

        String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, 100);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 获取营销活动信息
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMarketAct(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String actNo = request.getParameter("actNo");
        String actName = request.getParameter("actName");
        String actCycle = request.getParameter("actCycle");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String state = request.getParameter("state");
        StringBuffer whereSql = new StringBuffer("WHERE BANK_NO in ").append(operator.getBrhBelowId());

        if (isNotEmpty(state)) {
            whereSql.append(" AND STATE ='").append(state).append("'");
        }
        if (isNotEmpty(actNo)) {
            whereSql.append(" AND ACT_NO ='").append(actNo).append("'");
        }
        if (isNotEmpty(actName)) {
            whereSql.append(" AND ACT_NAME like '%").append(actName).append("%'");
        }
        if (isNotEmpty(actCycle)) {
            whereSql.append(" AND ACT_CYCLE ='").append(actCycle).append("'");
        }
        if (isNotEmpty(startDate)) {
            if (startDate.length() >= 10) {
                String startDate00001 = startDate.substring(0, 10);
                whereSql.append(" AND START_DATE >=").append(startDate00001.replaceAll("-", ""));
            } else {
                whereSql.append(" AND START_DATE >=").append(startDate);
            }

        }
        if (isNotEmpty(endDate)) {
            if (endDate.length() >= 10) {
                String endDate00001 = endDate.substring(0, 10);
                whereSql.append(" AND END_DATE <=").append(endDate00001.replaceAll("-", ""));
            } else {
                whereSql.append(" AND END_DATE <=").append(endDate);
            }
        }

        String sql = "SELECT BANK_NO,ACT_NO,ACT_NAME,STATE,START_DATE,END_DATE,ACT_CYCLE,ORGAN_NO,ORGAN_TYPE,ACT_CONTENT,ACT_FEE,REMARKS,FLAG01,CRT_DT,CRT_OPR,UPD_DT,UPD_OPR,REC_OPR,MISC1 FROM TBL_MARKET_ACT_REVIEW "
                + whereSql.append(" ORDER BY ACT_NO").toString();

        String countSql = "SELECT COUNT(*) FROM TBL_MARKET_ACT_REVIEW " + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, 100);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static Object[] getMarketActNew(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String actNo = request.getParameter("actNo");
        String actName = request.getParameter("actName");
        String actCycle = request.getParameter("actCycle");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String state = request.getParameter("state");
        StringBuffer whereSql = new StringBuffer("WHERE END_DATE <='" + CommonFunction.getCurrentDate() + "' AND BANK_NO in ")
                .append(operator.getBrhBelowId());

        if (isNotEmpty(state)) {
            whereSql.append(" AND STATE ='").append(state).append("'");
        }
        if (isNotEmpty(actNo)) {
            whereSql.append(" AND ACT_NO ='").append(actNo).append("'");
        }
        if (isNotEmpty(actName)) {
            whereSql.append(" AND ACT_NAME ='").append(actName).append("'");
        }
        if (isNotEmpty(actCycle)) {
            whereSql.append(" AND ACT_CYCLE ='").append(actCycle).append("'");
        }
        if (isNotEmpty(startDate)) {
            whereSql.append(" AND START_DATE >=").append(startDate);
        }
        if (isNotEmpty(endDate)) {
            whereSql.append(" AND END_DATE <=").append(endDate);
        }

        String sql = "SELECT BANK_NO,ACT_NO,ACT_NAME,STATE,START_DATE,END_DATE,ACT_CYCLE,ORGAN_NO,ORGAN_TYPE,ACT_CONTENT,ACT_FEE,REMARKS,FLAG01,CRT_DT,CRT_OPR,UPD_DT,UPD_OPR,REC_OPR,MISC1 FROM TBL_MARKET_ACT_REVIEW "
                + whereSql.append(" ORDER BY ACT_NO").toString();

        String countSql = "SELECT COUNT(*) FROM TBL_MARKET_ACT_REVIEW " + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, 100);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 获取营销活动商户参与信息
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntParticipat(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String actNo = request.getParameter("actNo");
        String actNm = request.getParameter("actNm");
        String mchtNo = request.getParameter("mchtNo");
        String state = request.getParameter("state");
        String flag = request.getParameter("flag");
        String noFlag = request.getParameter("noFlag");
        StringBuffer whereSql = new StringBuffer(" WHERE t1.STATE!='2'");
        if ("0000".equals(operator.getBrhBelowId())) {
            whereSql.append(" AND t1.BANK_NO in" + operator.getBrhBelowId());
        }
        if (!StringUtil.isNull(flag)) whereSql.append(" AND t1.FLAG01='").append(flag).append("'");
        if (!StringUtil.isNull(noFlag)) whereSql.append(" AND t1.FLAG01!='").append(noFlag).append("'");
        if (!StringUtil.isNull(actNo)) whereSql.append(" AND t1.ACT_NO='").append(actNo).append("'");
        if (!StringUtil.isNull(state)) whereSql.append(" AND t1.STATE='").append(state).append("'");
        if (!StringUtil.isNull(actNm)) {
            whereSql.append(" AND t1.ACT_NAME like '%").append(actNm).append("%'");
        }
        if (!StringUtil.isNull(mchtNo)) {
            whereSql.append(" AND t1.MCHNT_NO = '").append(mchtNo).append("'");
        }

        StringBuffer sql = new StringBuffer("SELECT t1.ACT_NO,t1.ACT_NAME,t1.BANK_NO,")
                .append("t1.STATE,t1.START_DATE,t1.END_DATE,t1.MCHNT_NO,t2.MCHT_NM,")
                .append("t1.ACT_CONTENT,t1.ACT_FEE,t1.FLAG01 FROM TBL_MCHNT_PARTICIPAT_REVIEW t1 ")
                .append("left join tbl_mcht_base_inf t2 on t1.MCHNT_NO=t2.MCHT_NO").append(whereSql).append(" ORDER BY t1.ACT_NO");
        String countSql = "SELECT COUNT(*) FROM TBL_MCHNT_PARTICIPAT_REVIEW t1" + whereSql.toString();
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString(), begin, 200);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询商户信息(营销活动)
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntInfoForActivityNew(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String bankNo = request.getParameter("bankNo");
        String mchtFlag1 = request.getParameter("mchtFlag1");
        String mchtGrp = request.getParameter("mchtGrp");
        String mchnNo = request.getParameter("mchnNo");
        String connType = request.getParameter("connType");
        StringBuffer whereSql = new StringBuffer(" WHERE t1.MCHT_NO= t2.MCHT_NO and t2.FEE_RATE = t3.DISC_CD ");
        // .append("and  t1.MCHT_STATUS in ('0','3','5','8') ").append(
        // "and t1.sign_Inst_Id in ").append(operator.getBrhBelowId());
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and t1.AGR_BR in ").append(operator.getBrhBelowId());
        }

        if (isNotEmpty(mchnNo)) {
            whereSql.append(" AND t1.MCHT_NO = '").append(mchnNo).append("' ");
        }
        if (isNotEmpty(mchtFlag1)) {
            whereSql.append(" AND t1.mcht_flag1 = '").append(mchtFlag1).append("' ");
        }
        if (isNotEmpty(mchtGrp)) {
            whereSql.append(" AND t1.MCC = '").append(mchtGrp).append("' ");
        }
        if (isNotEmpty(bankNo)) {
            whereSql.append(" AND t1.sign_Inst_Id = '").append(bankNo).append("' ");
        }
        if (isNotEmpty(connType)) {
            whereSql.append(" AND t1.CONN_TYPE = '").append(connType).append("' ");
        }
        Object[] ret = new Object[2];

        StringBuffer sql = new StringBuffer("SELECT t1.MCHT_NO,t1.MCHT_NM,substr(t4.DESCR,1,4),t1.MCC,t1.sign_Inst_Id,")
                .append("case CONN_TYPE when 'J' then '间联' when 'Z' then '直联' else CONN_TYPE end CONN_TYPE")
                .append(",t3.DISC_NM,' ' FROM　TBL_MCHT_BASE_INF t1 left join TBL_INF_MCHNT_TP_GRP t4 on t1.MCHT_GRP=t4.MCHNT_TP_GRP,")
                .append("TBL_MCHT_SETTLE_INF t2,TBL_INF_DISC_CD t3 ").append(whereSql).append(" ORDER BY t1.MCHT_NO");
        String countSql = "SELECT COUNT(1) FROM TBL_MCHT_BASE_INF t1,TBL_MCHT_SETTLE_INF t2,TBL_INF_DISC_CD t3 " + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString(), begin, 200);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * IC卡其他参数维护
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] icParam(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];

        StringBuffer whereSql = new StringBuffer();

        String sql = "SELECT USAGE_KEY,PARA_IDX,PARA_ID,PARA_LEN,PARA_VAL,REC_UPD_OPR,REC_CRT_TS from TBL_EMV_PARA where trim(USAGE_KEY) = '0' order by PARA_IDX";

        String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 手工退货信息(审核)
     */
    @SuppressWarnings("unchecked")
    public static Object[] getManualReturnForCheck(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];

        String whereSql = " where manual.status='" + TblManualReturnConstants.ADD_UNCHECK + "' ";
        if (!StringUtil.isNull(request.getParameter("key"))) {
            whereSql += "and manual.key_rsp like '%" + request.getParameter("key") + "%' ";
        }
        if (!StringUtil.isNull(request.getParameter("pan"))) {
            whereSql += "and manual.pan like '%" + request.getParameter("pan") + "%' ";
        }
        if (!StringUtil.isNull(request.getParameter("mno"))) {
            whereSql += "and manual.card_accp_id like '%" + request.getParameter("mno") + "%' ";
        }

        String sql = "select manual.key_rsp,manual.pan,manual.card_accp_id,manual.amt_trans,manual.status,manual.count,txn.inst_date,txn.inst_time,txn.SYS_SEQ_NUM,txn.CARD_ACCP_NAME,txn.CARD_ACCP_TERM_ID,txn.RETRIVL_REF,txn.ACQ_INST_ID_CODE,txn.txn_name,txn.RESP_CODE from tbl_manual_return manual left outer join (select substr(UPDT_DATE,1,8) as inst_date,substr(UPDT_DATE,9,6) as inst_time,SYS_SEQ_NUM,CARD_ACCP_NAME,CARD_ACCP_TERM_ID,RETRIVL_REF,ACQ_INST_ID_CODE,name.TXN_NAME as txn_name,RESP_CODE,key_rsp from tbl_n_txn_his t left outer join tbl_txn_name name on (t.txn_num = name.txn_num)) txn on (trim(manual.key_rsp)=trim(txn.key_rsp))"
                + whereSql;
        String countSql = "select count(*) from tbl_manual_return manual" + whereSql;
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 手工退货信息(审核) new
     */
    @SuppressWarnings("unchecked")
    public static Object[] getReturnForCheck(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];

        String whereSql = " where R.opr_sta='" + TblRTxnConstants.OPR_ADD_UNCHECK + "' ";
        if (!StringUtil.isNull(request.getParameter("key"))) {
            whereSql += "and R.key_rsp like '%" + request.getParameter("key") + "%' ";
        }
        if (!StringUtil.isNull(request.getParameter("pan"))) {
            whereSql += "and R.pan like '%" + request.getParameter("pan") + "%' ";
        }
        if (!StringUtil.isNull(request.getParameter("mno"))) {
            whereSql += "and R.mcht_no like '%" + request.getParameter("mno") + "%' ";
        }
        String sql = "select R.inst_date,R.key_rsp,R.pan,R.mcht_no,R.orig_amt,R.return_amt,R.opr_sta,txn.inst_date,"
                + "txn.SYS_SEQ_NUM,txn.CARD_ACCP_NAME,txn.CARD_ACCP_TERM_ID,txn.RETRIVL_REF,txn.ACQ_INST_ID_CODE,"
                + "txn.RESP_CODE from tbl_r_txn R left outer join tbl_n_txn txn on R.key_rsp=txn.key_rsp " + whereSql;

        String countSql = "select count(*) from tbl_r_txn R " + whereSql;
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            data[4] = CommonFunction.transFenToYuan(data[4].toString());
            data[5] = CommonFunction.transFenToYuan(data[5].toString());
            dataList.set(i, data);
        }
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 手工退货信息(管理) old
     */
    @SuppressWarnings("unchecked")
    public static Object[] getManualReturnForManage(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];

        String whereSql = " where 1=1 ";
        if (!StringUtil.isNull(request.getParameter("key"))) {
            whereSql += "and manual.key_rsp like '%" + request.getParameter("key") + "%' ";
        }
        if (!StringUtil.isNull(request.getParameter("pan"))) {
            whereSql += "and manual.pan like '%" + request.getParameter("pan") + "%' ";
        }
        if (!StringUtil.isNull(request.getParameter("mno"))) {
            whereSql += "and manual.card_accp_id like '%" + request.getParameter("mno") + "%' ";
        }

        String sql = "select manual.key_rsp,manual.pan,manual.card_accp_id,manual.amt_trans,manual.status,manual.count,txn.inst_date,txn.inst_time,txn.SYS_SEQ_NUM,txn.CARD_ACCP_NAME,txn.CARD_ACCP_TERM_ID,txn.RETRIVL_REF,txn.ACQ_INST_ID_CODE,txn.txn_name,txn.RESP_CODE from tbl_manual_return manual left outer join (select substr(UPDT_DATE,1,8) as inst_date,substr(UPDT_DATE,9,6) as inst_time,SYS_SEQ_NUM,CARD_ACCP_NAME,CARD_ACCP_TERM_ID,RETRIVL_REF,ACQ_INST_ID_CODE,name.TXN_NAME as txn_name,RESP_CODE,key_rsp from tbl_n_txn_his t left outer join tbl_txn_name name on (t.txn_num = name.txn_num)) txn on (trim(manual.key_rsp)=trim(txn.key_rsp))"
                + whereSql;

        String countSql = "select count(*) from tbl_manual_return manual" + whereSql;
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 手工退货信息(管理) new
     */
    @SuppressWarnings("unchecked")
    public static Object[] getReturnForManage(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];

        String whereSql = " where 1=1 ";
        if (!StringUtil.isNull(request.getParameter("key"))) {
            whereSql += "and R.key_rsp like '%" + request.getParameter("key") + "%' ";
        }
        if (!StringUtil.isNull(request.getParameter("pan"))) {
            whereSql += "and R.pan like '%" + request.getParameter("pan") + "%' ";
        }
        if (!StringUtil.isNull(request.getParameter("mno"))) {
            whereSql += "and R.mcht_no like '%" + request.getParameter("mno") + "%' ";
        }
        String sql = "select R.key_rsp,R.pan,R.mcht_no,R.orig_amt,R.return_amt,R.match_sta,R.opr_sta,txn.inst_date,"
                + "txn.SYS_SEQ_NUM,txn.CARD_ACCP_NAME,txn.CARD_ACCP_TERM_ID,txn.RETRIVL_REF,txn.ACQ_INST_ID_CODE,"
                + "txn.RESP_CODE from tbl_r_txn R left outer join tbl_n_txn txn on R.key_rsp=txn.key_rsp " + whereSql;

        String countSql = "select count(*) from tbl_r_txn R " + whereSql;
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            data[3] = CommonFunction.transFenToYuan(data[3].toString());
            data[4] = CommonFunction.transFenToYuan(data[4].toString());
            dataList.set(i, data);
        }
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 托收交易
     */
    @SuppressWarnings("unchecked")
    public static Object[] getEntrustInfo(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];

        String whereSql = " where 1=1 ";
        if (!StringUtil.isNull(request.getParameter("key"))) {
            whereSql += "and trade_id like '%" + request.getParameter("key") + "%' ";
        }
        if (!StringUtil.isNull(request.getParameter("pan"))) {
            whereSql += "and pan like '%" + request.getParameter("pan") + "%' ";
        }
        if (!StringUtil.isNull(request.getParameter("mno"))) {
            whereSql += "and card_accp_id like '%" + request.getParameter("mno") + "%' ";
        }

        String sql = "select trade_id,status,pan,card_accp_id,amt_trans from tbl_entrust_trade" + whereSql;
        String countSql = "select count(*) from tbl_entrust_trade" + whereSql;
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 差错未处理
     */
    @SuppressWarnings("unchecked")
    public static Object[] getBthDtlErrGc(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer(" where err_proc_cd in('01','02') ");
        String pan = StringUtils.trim(request.getParameter("pan"));
        String mchtNo = StringUtils.trim(request.getParameter("mchtNo"));
        String errProcCd = StringUtils.trim(request.getParameter("errProcCd"));// err_proc_cd
        if (StringUtils.isNotEmpty(pan)) {
            whereSql.append(" and pan like '%" + pan + "%' ");
        }
        if (StringUtils.isNotEmpty(mchtNo)) {
            whereSql.append(" and ACQ_MCHT_CD = '" + mchtNo + "' ");
        }
        if (StringUtils.isNotEmpty(errProcCd)) {
            whereSql.append(" and err_proc_cd = '" + errProcCd + "' ");
        }
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and b.SIGN_INST_ID IN" + operator.getBrhBelowId());
        }

        // 前置流水号显示acq_txn_ssn
        String sql = "select b.SIGN_INST_ID," + "DATE_SETTLMT,OUT_SSN,TERM_SSN,acq_txn_ssn,ACQ_PAN,nvl(ACQ_AMT_TRANS,0),err_proc_cd,trans_date_time,"
        // +
        // "(select TXN_NAME from TBL_TXN_NAME b where a.txn_num=b.TXN_NUM and DC_FLAG ='0') txnNm,a.txn_num,"
                + "(select TXN_NAME from TBL_TXN_NAME b where a.txn_num=b.TXN_NUM ) txnNm,a.txn_num," + "STLM_FLAG,ACQ_MCHT_CD,b.MCHT_NM "
                + "from bth_dtl_err_gc a left outer join TBL_MCHT_BASE_INF b on(a.ACQ_MCHT_CD=b.MCHT_NO) " + whereSql
                + " and a.txn_num != '1301' order by DATE_SETTLMT desc,trans_date_time desc";

        String countSql = "select count(1) from bth_dtl_err_gc" + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            data[6] = CommonFunction.transFenToYuan(data[6].toString());
            dataList.set(i, data);
        }

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 差错已处理
     */
    @SuppressWarnings("unchecked")
    public static Object[] getBthDtlErrGcFinish(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer whereSql = new StringBuffer(" where err_proc_cd not in('01','02') ");
        String pan = StringUtils.trim(request.getParameter("pan"));
        String mchtNo = StringUtils.trim(request.getParameter("mchtNo"));
        String errProcCd = StringUtils.trim(request.getParameter("errProcCd"));// err_proc_cd
        if (StringUtils.isNotEmpty(pan)) {
            whereSql.append(" and pan like '%" + pan + "%' ");
        }
        if (StringUtils.isNotEmpty(mchtNo)) {
            whereSql.append(" and ACQ_MCHT_CD = '" + mchtNo + "' ");
        }
        if (StringUtils.isNotEmpty(errProcCd)) {
            whereSql.append(" and err_proc_cd = '" + errProcCd + "' ");
        }
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and b.SIGN_INST_ID IN" + operator.getBrhBelowId());
        }

        // 前置流水号显示acq_txn_ssn
        String sql = "select b.SIGN_INST_ID," + "DATE_SETTLMT,OUT_SSN,TERM_SSN,acq_txn_ssn,ACQ_PAN,nvl(ACQ_AMT_TRANS,0),err_proc_cd,trans_date_time,"
        // +
        // "(select TXN_NAME from TBL_TXN_NAME b where a.txn_num=b.TXN_NUM and DC_FLAG ='0') txnNm,"
                + "(select TXN_NAME from TBL_TXN_NAME b where a.txn_num=b.TXN_NUM ) txnNm," + "STLM_FLAG,ACQ_MCHT_CD,b.MCHT_NM "
                + "from bth_dtl_err_gc a left outer join TBL_MCHT_BASE_INF b on(a.ACQ_MCHT_CD=b.MCHT_NO) " + whereSql
                + " order by DATE_SETTLMT desc,trans_date_time desc";
        String countSql = "select count(1) from bth_dtl_err_gc" + whereSql.toString();
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            data[6] = CommonFunction.transFenToYuan(data[6].toString());
            dataList.set(i, data);
        }

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 差错处理（流水勾兑不平处理）
     */
    @SuppressWarnings("unchecked")
    public static Object[] getBthGcTxnSucc(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];

        StringBuffer whereSql = new StringBuffer(" where stlm_flag not in ('0','A','7','8','9','D')");
        String pan = StringUtils.trim(request.getParameter("pan"));
        String errProcCd = StringUtils.trim(request.getParameter("errProcCd"));// err_proc_cd
        if (StringUtils.isNotEmpty(pan)) {
            whereSql.append(" and pan like '%" + pan + "%' ");
        }
        if (StringUtils.isNotEmpty(errProcCd)) {
            whereSql.append(" and err_proc_cd = '" + errProcCd + "' ");
        }
        // 前置流水号显示acq_txn_ssn
        String sql = "select ACQ_REGION_CD,DATE_SETTLMT,HOST_SSN,TERM_SSN,SYS_SEQ_NUM,PAN,to_number(AMT_TRANS)/100 ATM_TRANS,err_proc_cd,trans_date_time,txn_num from bth_gc_txn_succ"
                + whereSql;
        String countSql = "select count(1) from bth_gc_txn_succ" + whereSql.toString();
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户巡检
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchtCheckInf(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];

        String whereSql = " where 1=1 ";

        String sql = "select ch.mcht_no,ch.check_date,ch.check_name,ch.check_inf,ba.mcht_nm,ba.addr,ba.contact,ba.comm_tel from tbl_mcht_check_inf ch left outer join tbl_mcht_base_inf ba on ch.mcht_no=ba.mcht_no"
                + whereSql;
        String countSql = "select count(*) from tbl_mcht_check_inf" + whereSql;
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户待巡检
     * 
     * 跟业务定的查出三十天内（不是一个月，这样更合理些）巡检到期的商户
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchtAwaitCheckInf(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String agrBrId = request.getParameter("agrBrId");
        String thirdId = request.getParameter("thirdId");
        String mchtNo = request.getParameter("mchtNo");

        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 30);
        Date dateAfter = c.getTime();
        String dateString = new SimpleDateFormat("yyyyMMdd").format(dateAfter);

        Object[] ret = new Object[2];
        StringBuffer where = new StringBuffer(
                " where (to_date(nvl(rct_check_date,'19700101'),'yyyymmdd')+to_number(trim(check_frqc))-30) <= to_date('" + dateString
                        + "','yyyymmdd') and mcht_status ='0' ");
        if (!"0000".equals(operator.getOprBrhId())) {
            where.append(" AND agr_br IN " + operator.getBrhBelowId() + " ");
        }
        // 状态参考TblMchntInfoConstants
        // 19700101没多大意义、只要比当前日期早很多就行了，因为最近巡检日期是c插入、防止空值导致错误
        if (StringUtils.isNotEmpty(agrBrId)) {
            where.append(" and agr_br = '" + agrBrId.trim() + "' ");
        }
        if (StringUtils.isNotEmpty(mchtNo)) {
            where.append(" and mcht_no = '" + mchtNo.trim() + "' ");
        }
        if (StringUtils.isNotEmpty(thirdId)) {
            where.append(
                    " and mcht_no in (select mcht_cd from tbl_term_inf where term_sta not in('4','7','9') and prop_tp='1' and prop_ins_nm='"
                            + thirdId.trim() + "')");
        }
        where.append(" order by rct_check_date");
        String sql = "select mcht_no,mcht_nm,rct_check_date,check_frqc,plan_check_date,addr,contact,comm_tel,"
                + "(select brh_name from TBL_BRH_INFO b where a.agr_br=b.BRH_ID) brhNm " + "from tbl_mcht_base_inf a" + where.toString();
        String countSql = "select count(1) from (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户手工巡检待审核
     * 
     * 跟业务定的查出三十天内（不是一个月，这样更合理些）巡检到期的商户
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchtAwaitAuditInf(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 30);
        Date dateAfter = c.getTime();
        String dateString = new SimpleDateFormat("yyyyMMdd").format(dateAfter);
        Object[] ret = new Object[2];
        String agrBrId = request.getParameter("agrBrId");
        String thirdId = request.getParameter("thirdId");
        String mchtNo = request.getParameter("mchtNo");

        StringBuffer where = new StringBuffer("where length(plan_check_date)=8 ");

        if (!"0000".equals(operator.getOprBrhId())) {
            where.append(" AND agr_br IN " + operator.getBrhBelowId() + " ");
        }
        // 状态参考TblMchntInfoConstants
        // 19700101没多大意义、只要比当前日期早很多就行了，因为最近巡检日期是c插入、防止空值导致错误
        if (StringUtils.isNotEmpty(agrBrId)) {
            where.append(" and agr_br = '" + agrBrId.trim() + "' ");
        }
        if (StringUtils.isNotEmpty(mchtNo)) {
            where.append(" and mcht_no = '" + mchtNo.trim() + "' ");
        }
        if (StringUtils.isNotEmpty(thirdId)) {
            where.append(
                    " and mcht_no in (select mcht_cd from tbl_term_inf where term_sta not in('4','7','9') and prop_tp='1' and prop_ins_nm='"
                            + thirdId.trim() + "')");
        }
        where.append(" order by rct_check_date");
        String sql = "select mcht_no,mcht_nm,rct_check_date,check_frqc,plan_check_date,addr,contact,comm_tel,"
                + "(select brh_name from TBL_BRH_INFO b where a.agr_br=b.BRH_ID) brhNm " + "from tbl_mcht_base_inf a " + where.toString();
        String countSql = "select count(1) from (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /** 城市地区码 */
    public static Object[] getAreaInf(int begin, HttpServletRequest request) {
        String cityCode = request.getParameter("cityCode");
        String cityName = request.getParameter("cityName");
        StringBuffer sb = new StringBuffer("SELECT CITY_CODE_NEW,CITY_NAME,MODI_OPR_ID,MODI_TIME,INIT_TIME FROM CST_CITY_CODE WHERE 1=1");
        if (StringUtils.isNotEmpty(cityCode)) {
            sb.append(" AND CITY_CODE_NEW LIKE " + CommonFunction.getStringWithBFHTwoSide(cityCode));
        }
        if (StringUtils.isNotEmpty(cityName)) {
            sb.append(" AND CITY_NAME LIKE " + CommonFunction.getStringWithBFHTwoSide(cityName));
        }

        sb.append(" ORDER BY CITY_CODE_NEW");

        return CommonFunction.getSqlQueryObjectArray(sb.toString(), begin);
    }

    /** 终端类型参数*/
    public static Object[] getTermTypeInf(int begin, HttpServletRequest request) {
        String termTp = request.getParameter("termTp");
        String descr = request.getParameter("descr");
        String termNum = request.getParameter("termNum");
        String termType = request.getParameter("termType");
        StringBuffer sb = new StringBuffer("SELECT TERM_TP,DESCR,TERM_NUM,TERM_TYPE,PRO_DESCR FROM TBL_TERM_TP WHERE 1=1");
        if (StringUtils.isNotEmpty(termTp)) {
            sb.append(" AND TERM_TP LIKE " + CommonFunction.getStringWithBFHTwoSide(termTp));
        }
        if (StringUtils.isNotEmpty(descr)) {
            sb.append(" AND DESCR LIKE " + CommonFunction.getStringWithBFHTwoSide(descr));
        }
        if (StringUtils.isNotEmpty(termNum)) {
            sb.append(" AND DESCR LIKE " + CommonFunction.getStringWithBFHTwoSide(termNum));
        }
        if (StringUtils.isNotEmpty(termType)) {
            sb.append(" AND DESCR LIKE " + CommonFunction.getStringWithBFHTwoSide(termType));
        }

        sb.append(" ORDER BY TERM_TP");

        return CommonFunction.getSqlQueryObjectArray(sb.toString(), begin);
    }

    /** 商户分期信息 */
    public static Object[] getDivMchtInf(int begin, HttpServletRequest request) {
        String mchtId = request.getParameter("mchtId");
        String divNo = request.getParameter("divNo");
        String productCode = request.getParameter("productCode");
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        StringBuffer sb = new StringBuffer("SELECT mcht_id,(select mcht_nm from tbl_mcht_base_inf_tmp m where m.mcht_no=t.mcht_id) as mcht_name,"
                + "div_no,product_code,Fee_code,(select disc_nm from tbl_inf_disc_cd d where d.disc_cd=t.Fee_code)  as fee_name,"
                + "product_div_name,modi_opr_id,"
                + "init_time,modi_time FROM tbl_div_mcht t WHERE INIT_OPR_ID in (select opr.OPR_ID from TBL_OPR_INFO opr where opr.BRH_ID in "
                + operator.getBrhBelowId() + ")");
        if (StringUtils.isNotEmpty(mchtId)) {
            sb.append(" AND mcht_id = '" + mchtId + "' ");
        }
        if (StringUtils.isNotEmpty(divNo)) {
            sb.append(" AND div_no = '" + divNo + "' ");
        }
        if (StringUtils.isNotEmpty(productCode)) {
            sb.append(" AND product_code like '%" + productCode + "%'");
        }

        sb.append(" ORDER BY mcht_id,div_no,product_code");

        return CommonFunction.getSqlQueryObjectArray(sb.toString(), begin);
    }

    /** 商户分期信息 */
    public static Object[] getDivMchtInfNew(int begin, HttpServletRequest request) {
        String mchtId = request.getParameter("mchtId");
        String divNo = request.getParameter("divNo");
        String productCode = request.getParameter("productCode");
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        StringBuffer sb = new StringBuffer("SELECT mcht_id,(select mcht_nm from tbl_mcht_base_inf_tmp m where m.mcht_no=t.mcht_id) as mcht_name,"
                + "div_no,product_code,Fee_code,(select disc_nm from tbl_inf_disc_cd d where d.disc_cd=t.Fee_code)  as fee_name,"
                + "product_div_name,modi_opr_id,"
                + "init_time,modi_time FROM tbl_div_mcht t WHERE INIT_OPR_ID in (select opr.OPR_ID from TBL_OPR_INFO opr where 1=1");
        if (StringUtils.isNotEmpty(mchtId)) {
            sb.append(" AND mcht_id = '" + mchtId + "' ");
        }
        if (StringUtils.isNotEmpty(divNo)) {
            sb.append(" AND div_no = '" + divNo + "' ");
        }
        if (StringUtils.isNotEmpty(productCode)) {
            sb.append(" AND product_code like '%" + productCode + "%'");
        }

        sb.append(" ) ORDER BY mcht_id,div_no,product_code");

        return CommonFunction.getSqlQueryObjectArray(sb.toString(), begin);
    }

    /** 财务pos商户单位卡个人卡签约信息 by樊东东 2013-3-11 */
    public static Object[] getMchtUnitPersonBindInfo(int begin, HttpServletRequest request) {
        String mchtNo = request.getParameter("mchtNo");
        String cardNo = request.getParameter("cardNo");
        String cardFlag = request.getParameter("cardFlag");
        // System.out.println(mchtNo+"-"+unitNo+"-"+personNo);
        StringBuffer sb = new StringBuffer("SELECT mcht_no,(select m.mcht_nm from tbl_mcht_base_inf_tmp m where m.mcht_no=t.mcht_no) as mcht_name,"
                + "card_no,card_flag,modi_opr_id," + "init_time,modi_time FROM TBL_UNIT_PERSON_BIND t WHERE 1=1");
        if (StringUtils.isNotEmpty(mchtNo)) {
            sb.append(" AND mcht_no = '" + mchtNo + "' ");
        }
        if (StringUtils.isNotEmpty(cardNo)) {
            sb.append(" AND card_no like '%" + cardNo + "%' ");
        }
        if (StringUtils.isNotEmpty(cardFlag)) {
            sb.append(" AND card_flag = '" + cardFlag + "'");
        }

        sb.append(" ORDER BY mcht_no,card_no,card_flag");

        return CommonFunction.getSqlQueryObjectArray(sb.toString(), begin);
    }

    /**
     * 费用收取
     */
    @SuppressWarnings("unchecked")
    public static Object[] getCostInf(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];

        String whereSql = " where 1=1 ";

        String sql = "select no,state,amount,card_id,mcht_no,remark_inf from tbl_cost_inf" + whereSql;
        String countSql = "select count(*) from tbl_cost_inf" + whereSql;
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 证照号信息查询
     */
    @SuppressWarnings("unchecked")
    public static Object[] getLicenceInf(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];

        String whereSql = " where 1=1 ";
        String licenceNo = request.getParameter("licenceNo");
        String etpsState = request.getParameter("etpsState");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        // System.out.println(licenceNo + "---" + etpsState + "---" + startDate
        // + "----" + endDate);
        if (isNotEmpty(licenceNo)) {
            whereSql += " AND LICENCE_NO = '" + licenceNo + "' ";
        }
        if (isNotEmpty(etpsState)) {
            whereSql += " AND ETPS_STATE = '" + etpsState + "' ";
        }
        if (isNotEmpty(startDate)) {
            whereSql += " AND APPLY_DATE >= '" + startDate + "' ";
        }
        if (isNotEmpty(endDate)) {
            whereSql += " AND APPLY_DATE <= '" + endDate + "' ";
        }

        String sql = "select licence_No as licenceNo,etps_Nm as etpsNm,licence_End_Date as licenceEndDate,bank_Licence_No as bankLicenceNo,"
                + "etps_Type as etpsType,fax_No as faxNo,bus_Amt as busAmt,mcht_Cre_Lvl as mchtCreLvl,manager as manager,"
                + "artif_Certif_Tp as artifCertifTp,identity_No as identityNo,manager_Tel as managerTel,fax as fax,reg_Addr as regAddr,"
                + "apply_Date as applyDate,enable_Date as enableDate,pre_Aud_Nm as preAudNm,confirm_Nm as confirmNm,etps_State as etpsState,"
                + "reserved,refuse_msg2 as refuseMsg2,"
                + "upd_Opr_Id as updOprId,crt_Opr_Id as crtOprId,SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2) as recUpdTs,"
                + "rec_Crt_Ts as recCrtTs from TBL_MCHT_LICENCE_INF_TMP" + whereSql;
        String countSql = "select count(*) from TBL_MCHT_LICENCE_INF_TMP" + whereSql;
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 证照号信息查询(审核)
     */
    @SuppressWarnings("unchecked")
    public static Object[] getLicenceInf2(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];

        String whereSql = " ";
        String licenceNo = request.getParameter("licenceNo");
        String etpsState = request.getParameter("etpsState");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        // System.out.println(licenceNo + "---" + etpsState + "---" + startDate
        // + "----" + endDate);
        if (isNotEmpty(licenceNo)) {
            whereSql += " AND LICENCE_NO = '" + licenceNo + "' ";
        }
        if (isNotEmpty(etpsState)) {
            whereSql += " AND ETPS_STATE = '" + etpsState + "' ";
        }
        if (isNotEmpty(startDate)) {
            whereSql += " AND APPLY_DATE >= '" + startDate + "' ";
        }
        if (isNotEmpty(endDate)) {
            whereSql += " AND APPLY_DATE <= '" + endDate + "' ";
        }
        // reserved is not like '%refuse%'
        String sql = "select licence_No as licenceNo,etps_Nm as etpsNm,licence_End_Date as licenceEndDate,bank_Licence_No as bankLicenceNo,"
                + "etps_Type as etpsType,fax_No as faxNo,bus_Amt as busAmt,mcht_Cre_Lvl as mchtCreLvl,manager as manager,"
                + "artif_Certif_Tp as artifCertifTp,identity_No as identityNo,manager_Tel as managerTel,fax as fax,reg_Addr as regAddr,"
                + "apply_Date as applyDate,enable_Date as enableDate,pre_Aud_Nm as preAudNm,confirm_Nm as confirmNm,etps_State as etpsState,"
                + "upd_Opr_Id as updOprId,crt_Opr_Id as crtOprId,SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2) as recUpdTs,"
                + "rec_Crt_Ts as recCrtTs from TBL_MCHT_LICENCE_INF_TMP where licence_no not in (select licence_no from tbl_mcht_licence_inf_tmp where reserved like '%refuse%') "
                + " and etps_State IN ('1','3','5','7') " + whereSql;
        String countSql = "select count(*) from TBL_MCHT_LICENCE_INF_TMP where licence_no not in (select licence_no from tbl_mcht_licence_inf_tmp where reserved like '%refuse%') "
                + " and etps_State IN ('1','3','5','7') " + whereSql;
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户对账信息
     * 
     * @param begin
     * @param request
     * @return
     */

    public static Object[] getAccountSettle(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String mchntId = request.getParameter("mchntId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        StringBuffer whereSql = new StringBuffer("where 1=1 ");
        StringBuffer whereSql1 = new StringBuffer(" ");
        StringBuffer whereSql2 = new StringBuffer(" ");

        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and b.AGR_BR in " + operator.getBrhBelowId());
        }

        if (mchntId != null && !mchntId.trim().equals("")) {
            whereSql.append(" AND MCHT_CD like '%");
            whereSql.append(mchntId);
            whereSql.append("%'");
        }

        if (startDate != null && !startDate.trim().equals("")) {
            whereSql1.append(" AND DATE_SETTLMT>= '" + startDate + "' ");
            whereSql2.append(" AND SUBSTR(REC_UPD_TS,1,8)>= '" + startDate + "' ");
        }
        if (endDate != null && !endDate.trim().equals("")) {
            whereSql1.append(" AND DATE_SETTLMT<= '" + endDate + "'");
            whereSql2.append(" AND SUBSTR(REC_UPD_TS,1,8)<= '" + endDate + "' ");
        }

        String sql = "SELECT PAN,MCHT_CD,TXN_NUM,DATE_SETTLMT,TRANS_DATE,TRANS_DATE_TIME,TERM_ID,"
                + "MCHT_AT_C-MCHT_AT_D amt,MCHT_FEE_D-MCHT_FEE_C fee,(MCHT_AT_C-MCHT_AT_D)-(MCHT_FEE_D-MCHT_FEE_C) settleAmt,"
                + "TXN_SSN, OLD_TXN_FLG " + "FROM TBL_ALGO_DTL "
                + "where txn_num not in('5171', '9103') and C_D_FLG <> '1'  and SUBSTR(TXN_NUM,1,1) <> '3' " + whereSql1 + "union all "
                + "SELECT PAN,MCHT_CD,TXN_NUM,DATE_SETTLMT,TRANS_DATE,TRANS_DATE_TIME,TERM_ID,"
                + "MCHT_AT_C-MCHT_AT_D amt,MCHT_FEE_D-MCHT_FEE_C fee,(MCHT_AT_C-MCHT_AT_D)-(MCHT_FEE_D-MCHT_FEE_C) settleAmt,"
                + "TXN_SSN, OLD_TXN_FLG " + "FROM TBL_ALGO_DTL " + "where TXN_NUM in ('5171', '9103') and OLD_TXN_FLG = '1' " + whereSql2;

        sql = "select PAN,(select BRH_NAME from TBL_BRH_INFO c where b.AGR_BR=c.BRH_ID) bankNm,"
                + "MCHT_CD,b.MCHT_NM,TXN_NUM,DATE_SETTLMT,TRANS_DATE,TRANS_DATE_TIME,TERM_ID,amt," + "fee,settleAmt,TXN_SSN,OLD_TXN_FLG " + "from ("
                + sql + ") a left outer join TBL_MCHT_BASE_INF b on(a.MCHT_CD=b.MCHT_NO) " + whereSql
                + " order by DATE_SETTLMT,TRANS_DATE,TRANS_DATE_TIME,bankNm,MCHT_CD";

        String countSql = "SELECT COUNT(1) FROM (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /** 商户清算信息明细 */
    @SuppressWarnings("unchecked")
    public static Object[] getMchtAlgoInfo(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        Object[] ret = new Object[2];
        String mchtCd = request.getParameter("mchtCd");
        String termId = request.getParameter("termId");
        String mchtGrp = request.getParameter("mchtGrp");
        String mchtFlag1 = request.getParameter("mchtFlag1");
        String cardType = request.getParameter("cardType");

        String date = request.getParameter("date");
        String date1 = request.getParameter("date1");

        String whereSql = " ";

        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " and b.AGR_BR in " + operator.getBrhBelowId();
        }
        if (isNotEmpty(mchtCd)) {
            whereSql += " AND trim(mcht_cd)='" + mchtCd + "'";
        }
        if (isNotEmpty(termId)) {
            whereSql += " AND trim(term_id)='" + termId + "'";
        }
        if (isNotEmpty(mchtGrp)) {
            whereSql += " AND trim(MCHT_GRP)='" + mchtGrp + "'";
        }
        if (isNotEmpty(mchtFlag1)) {
            whereSql += " AND trim(MCHT_FLAG1)='" + mchtFlag1 + "'";
        }
        if (isNotEmpty(cardType)) {
            whereSql += " AND trim(CARD_TP)='" + cardType + "'";
        }

        if (isNotEmpty(date)) {
            whereSql += " and trim(DATE_SETTLMT)>='" + date + "'";
        }
        if (isNotEmpty(date1)) {
            whereSql += " and trim(DATE_SETTLMT)<='" + date1 + "'";
        }

        String sql = "select trans_date,trans_date_time," + "date_settlmt,txn_ssn,pan,CARD_TP," + "(MCHT_AT_C - MCHT_AT_D)*100 mchtAmt,"
                + "(MCHT_FEE_C-MCHT_FEE_D)*100 mchtFee," + "(FEE_D_OUT-FEE_C_OUT)*100 cupAmt,SVC_FEE*100 svcFee,"
                + "((MCHT_AT_C - MCHT_AT_D)-(MCHT_FEE_D-MCHT_FEE_C))*100 pureAmt," + "mcht_cd,term_id,"
                // +
                // "(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM and DC_FLAG ='0') txnNm,"
                + "(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM ) txnNm,"
                + "acq_ins_id_cd,OLD_TXN_FLG,substr(misc_1,21,12) refFlag " + "from TBL_ALGO_DTL a "
                + "where txn_num not in('5171', '9103') and C_D_FLG <> '1'  and SUBSTR(TXN_NUM,1,1) <> '3' " + " union all "
                + "select trans_date,trans_date_time," + "trim(REC_UPD_TS) date_settlmt,txn_ssn,pan,CARD_TP," + "(MCHT_AT_C - MCHT_AT_D)*100 mchtAmt,"
                + "(MCHT_FEE_C-MCHT_FEE_D)*100 mchtFee," + "(FEE_D_OUT-FEE_C_OUT)*100 cupAmt,SVC_FEE*100 svcFee,"
                + "((MCHT_AT_C - MCHT_AT_D)-(MCHT_FEE_D-MCHT_FEE_C))*100 pureAmt," + "mcht_cd,term_id,"
                // +
                // "(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM and DC_FLAG ='0') txnNm,"
                + "(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM ) txnNm,"
                + "acq_ins_id_cd,OLD_TXN_FLG,substr(misc_1,21,12) refFlag " + "from TBL_ALGO_DTL a "
                + "where TXN_NUM in ('5171', '9103') and OLD_TXN_FLG = '1' ";

        sql = "select trans_date,trans_date_time,date_settlmt,txn_ssn,pan," + "(case a.CARD_TP when '01' then '本行借记卡' " + " when '02' then '本行信用卡' "
                + " when '03' then '他行借记卡' " + " when '04' then '他行信用卡' end) CARD_TYPE," + "mchtAmt,mchtFee,cupAmt,"
                + "svcFee,pureAmt,mcht_cd,MCHT_GRP,MCHT_FLAG1,term_id,txnNm,acq_ins_id_cd,OLD_TXN_FLG,refFlag " + "from (" + sql
                + ") a left outer join TBL_MCHT_BASE_INF b on(a.MCHT_CD=b.MCHT_NO) " + "where 1=1 " + whereSql
                + " order by DATE_SETTLMT desc,TRANS_DATE desc,TRANS_DATE_TIME desc,acq_ins_id_cd,MCHT_CD,term_id";

        String countSql = "select count(1) from (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            data[6] = CommonFunction.transFenToYuan(data[6].toString());
            data[7] = CommonFunction.transFenToYuan(data[7].toString());
            data[8] = CommonFunction.transFenToYuan(data[8].toString());
            data[9] = CommonFunction.transFenToYuan(data[9].toString());
            data[10] = CommonFunction.transFenToYuan(data[10].toString());
            dataList.set(i, data);
        }

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /** 间联商户清算汇总 */
    @SuppressWarnings("unchecked")
    public static Object[] getMchtAlgoInfoCount(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        Object[] ret = new Object[2];
        String mchtCd = request.getParameter("mchtCd");
        String mchtGrp = request.getParameter("mchtGrp");
        String mchtFlag1 = request.getParameter("mchtFlag1");
        String cardType = request.getParameter("cardType");

        String signInstId = request.getParameter("signInstId");
        String agrBr = request.getParameter("agrBr");
        String date = request.getParameter("date");
        String date1 = request.getParameter("date1");

        String sqlAlgo = "select date_settlmt,MCHT_AT_C,MCHT_AT_D,MCHT_FEE_C,MCHT_FEE_D,"
                + "FEE_D_OUT,FEE_C_OUT,mcht_cd,term_id,acq_ins_id_cd,txn_num,CARD_TP,"
                + "(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM and DC_FLAG ='0') txnNm " + "from TBL_ALGO_DTL a "
                + "where txn_num not in('5171', '9103') and C_D_FLG <> '1'  and SUBSTR(TXN_NUM,1,1) <> '3' " + " union all "
                + "select trim(REC_UPD_TS) date_settlmt,MCHT_AT_C,MCHT_AT_D,MCHT_FEE_C,MCHT_FEE_D,"
                + "FEE_D_OUT,FEE_C_OUT,mcht_cd,term_id,acq_ins_id_cd,txn_num,CARD_TP,"
                + "(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM and DC_FLAG ='0') txnNm " + "from TBL_ALGO_DTL a "
                + "where TXN_NUM in ('5171', '9103') and OLD_TXN_FLG = '1' ";

        String sql = "select trim(mcht_cd),b.MCHT_NM," + "(select DESCR from TBL_INF_MCHNT_TP_GRP g where b.MCHT_GRP=g.MCHNT_TP_GRP),b.MCHT_FLAG1,"
                + "b.MCHT_FLAG1||b.MCHT_FLAG2," + " d.brh_name," + "c.FEE_RATE," + "sum(MCHT_AT_C - MCHT_AT_D)*100,"
                + "sum(MCHT_FEE_C-MCHT_FEE_D)*100," + "(sum(MCHT_AT_C - MCHT_AT_D)-sum(MCHT_FEE_D-MCHT_FEE_C))*100," + "count(1),"
                + "sum(case when TXN_NUM not in('9103','9205') then NVL((a.MCHT_FEE_D - a.MCHT_FEE_C), 0)-NVL((FEE_D_OUT-FEE_C_OUT), 0) "
                + "when TXN_NUM = '9103' then nvl((MCHT_AT_D - MCHT_AT_C),0) " + "when TXN_NUM = '9205' then nvl((MCHT_AT_D - MCHT_AT_C),0) end)*100 "
                + " from (" + sqlAlgo + ") a " + "left outer join TBL_MCHT_BASE_INF b on (a.mcht_cd =b.MCHT_NO) "
                + "left outer join TBL_MCHT_SETTLE_INF c on(a.mcht_cd =c.MCHT_NO) " + "left outer join tbl_brh_info d on (b.agr_br = d.brh_id) "
                + "where 1=1";

        if (isNotEmpty(mchtCd)) {
            sql += " AND trim(mcht_cd)='" + mchtCd + "'";
        }
        if (isNotEmpty(mchtGrp)) {
            sql += " AND MCHT_GRP='" + mchtGrp + "'";
        }
        if (isNotEmpty(mchtFlag1)) {
            sql += " AND MCHT_FLAG1='" + mchtFlag1 + "'";
        }
        if (isNotEmpty(cardType)) {
            sql += " AND a.CARD_TP='" + cardType + "'";
        }
        if (isNotEmpty(signInstId) && !"0000".equals(signInstId)) {
            sql += " AND trim(b.SIGN_INST_ID)='" + signInstId + "'";
        }

        if (!isNotEmpty(agrBr)) {
            agrBr = operator.getOprBrhId();
        }
        if (!"0000".equals(agrBr)) {
            sql += " AND trim(b.AGR_BR) in" + InformationUtil.getBrhGroupString(agrBr);
        }
        if (isNotEmpty(date)) {
            sql += " and trim(DATE_SETTLMT)>='" + date + "'";
        }
        if (isNotEmpty(date1)) {
            sql += " and trim(DATE_SETTLMT)<='" + date1 + "'";
        }
        sql += " group by mcht_cd,d.brh_name,b.MCHT_NM,MCHT_GRP,b.MCHT_FLAG1,b.MCHT_FLAG2,c.FEE_RATE";
        String countSql = "select count(1) from (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO()
                .findBySQLQuery(sql + " order by mcht_cd,b.MCHT_FLAG1,b.MCHT_FLAG2", begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);

            data[7] = CommonFunction.transFenToYuan(data[7].toString());
            data[8] = CommonFunction.transFenToYuan(data[8].toString());
            data[9] = CommonFunction.transFenToYuan(data[9].toString());
            data[11] = CommonFunction.transFenToYuan(data[11].toString());

            dataList.set(i, data);
        }

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /** 间联商户付款交易明细 */
    @SuppressWarnings("unchecked")
    public static Object[] getMchtPayInfo(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        Object[] ret = new Object[2];
        String mchtCd = request.getParameter("mchtCd");
        String termId = request.getParameter("termId");
        String mchtGrp = request.getParameter("mchtGrp");
        String mchtFlag1 = request.getParameter("mchtFlag1");

        String date = request.getParameter("date") + "000000";
        String date1 = request.getParameter("date1") + "999999";

        String whereSql = " ";

        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " and b.AGR_BR in " + operator.getBrhBelowId();
        }
        if (isNotEmpty(mchtCd)) {
            whereSql += " AND trim(a.card_accp_id)='" + mchtCd + "'";
        }
        if (isNotEmpty(termId)) {
            whereSql += " AND trim(a.card_accp_term_id)='" + termId + "'";
        }
        if (isNotEmpty(mchtGrp)) {
            whereSql += " AND trim(MCHT_GRP)='" + mchtGrp + "'";
        }
        if (isNotEmpty(mchtFlag1)) {
            whereSql += " AND trim(MCHT_FLAG1)='" + mchtFlag1 + "'";
        }

        if (isNotEmpty(date)) {
            whereSql += " and trim(a.INST_DATE)>='" + date + "'";
        }
        if (isNotEmpty(date1)) {
            whereSql += " and trim(a.INST_DATE)<='" + date1 + "'";
        }

        String sql2 = "select substr(a.inst_date,1,8)," + "a.trans_date_time," + "a.card_accp_id as MCHT_CD,"
                + "(select DESCR from TBL_INF_MCHNT_TP_GRP g where b.MCHT_GRP = g.MCHNT_TP_GRP) as MCHT_GRP," + "b.MCHT_FLAG1,"
                + "b.MCHT_FLAG1 || b.MCHT_FLAG2," + "a.card_accp_term_id," + "b.agr_br," + "d.prop_ins_nm," + "a.sys_seq_num," + "a.retrivl_ref,"
                + "a.fwd_inst_id_code," + "a.pan," + "a.acct_id2," + "'商户付款'," + "a.amt_trans "
                + " from tbl_n_txn a, tbl_mcht_base_inf b, tbl_mcht_settle_inf c, tbl_term_inf d " + "where a.card_accp_id = b.mcht_no "
                + "and b.mcht_no = c.mcht_no " + "and a.txn_num = '1301' " + "and a.card_accp_term_id = d.term_id " + whereSql;

        String sql3 = "select substr(a.inst_date,1,8)," + "a.trans_date_time," + "a.card_accp_id as MCHT_CD,"
                + "(select DESCR from TBL_INF_MCHNT_TP_GRP g where b.MCHT_GRP = g.MCHNT_TP_GRP) as MCHT_GRP," + "b.MCHT_FLAG1,"
                + "b.MCHT_FLAG1 || b.MCHT_FLAG2," + "a.card_accp_term_id," + "b.agr_br," + "d.prop_ins_nm," + "a.sys_seq_num," + "a.retrivl_ref,"
                + "a.fwd_inst_id_code," + "a.pan," + "a.acct_id2," + "'商户付款'," + "a.amt_trans "
                + " from tbl_n_txn_his a, tbl_mcht_base_inf b, tbl_mcht_settle_inf c, tbl_term_inf d " + "where a.card_accp_id = b.mcht_no "
                + "and b.mcht_no = c.mcht_no " + "and a.txn_num = '1301' " + "and a.card_accp_term_id = d.term_id " + whereSql;

        String sql = sql2 + " union all " + sql3 + " order by MCHT_CD";

        String countSql = "select count(1) from (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            data[15] = CommonFunction.transFenToYuan(data[15].toString());
            dataList.set(i, data);
        }

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /** 间联商户付款交易汇总 */
    @SuppressWarnings("unchecked")
    public static Object[] getMchtpayInfoCount(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        Object[] ret = new Object[2];
        String mchtCd = request.getParameter("mchtCd");
        String mchtGrp = request.getParameter("mchtGrp");
        String mchtFlag1 = request.getParameter("mchtFlag1");
        String signInstId = request.getParameter("signInstId");
        String agrBr = request.getParameter("agrBr");
        String date = request.getParameter("date") + "00000000";
        String date1 = request.getParameter("date1") + "99999999";
        String sql = "";
        String whereSql = "";

        if (isNotEmpty(mchtCd)) {
            whereSql += " AND trim(a.card_accp_id)='" + mchtCd + "'";
        }
        if (isNotEmpty(mchtGrp)) {
            whereSql += " AND b.MCHT_GRP='" + mchtGrp + "'";
        }
        if (isNotEmpty(mchtFlag1)) {
            whereSql += " AND b.MCHT_FLAG1='" + mchtFlag1 + "'";
        }
        if (isNotEmpty(signInstId) && !"0000".equals(signInstId)) {
            whereSql += " AND trim(b.SIGN_INST_ID)='" + signInstId + "'";
        }

        if (!isNotEmpty(agrBr)) {
            agrBr = operator.getOprBrhId();
        }
        if (!"0000".equals(agrBr)) {
            whereSql += " AND trim(b.AGR_BR) in" + InformationUtil.getBrhGroupString(agrBr);
        }
        if (isNotEmpty(date)) {
            whereSql += " and trim(a.INST_DATE)>='" + date + "'";
        }
        if (isNotEmpty(date1)) {
            whereSql += " and trim(a.INST_DATE)<='" + date1 + "'";
        }
        whereSql += " group by a.card_accp_id,a.card_accp_name,b.MCHT_FLAG1, b.MCHT_FLAG1 || b.MCHT_FLAG2,b.agr_br,b.sign_inst_id,c.settle_acct,c.settle_bank_nm,b.MCHT_GRP";

        String sql2 = "select a.card_accp_id as db_1," + "a.card_accp_name as db_2,"
                + "(select DESCR from TBL_INF_MCHNT_TP_GRP g where b.MCHT_GRP = g.MCHNT_TP_GRP) as db_3," + "b.MCHT_FLAG1 as db_4,"
                + "b.MCHT_FLAG1 || b.MCHT_FLAG2 as db_5," + "b.agr_br as db_6," + "b.sign_inst_id as db_7," + "substr(c.settle_acct,2) as db_8,"
                + "c.settle_bank_nm as db_9," + "count(a.sys_seq_num) as db_10," + "sum(to_number(a.amt_trans))/100 as db_11 "
                + " from tbl_n_txn a, tbl_mcht_base_inf b, tbl_mcht_settle_inf c " + " where a.card_accp_id = b.mcht_no and  b.mcht_no = c.mcht_no "
                + "and a.txn_num = '1301' " + whereSql;

        String sql3 = "select a.card_accp_id as db_1, " + "a.card_accp_name as db_2, "
                + "(select DESCR from TBL_INF_MCHNT_TP_GRP g where b.MCHT_GRP = g.MCHNT_TP_GRP) as db_3, " + "b.MCHT_FLAG1 as db_4, "
                + "b.MCHT_FLAG1 || b.MCHT_FLAG2 as db_5, " + "b.agr_br as db_6, " + "b.sign_inst_id as db_7, " + "substr(c.settle_acct,2) as db_8, "
                + "c.settle_bank_nm as db_9," + "count(a.sys_seq_num) as db_10, " + "sum(to_number(a.amt_trans))/100  as db_11 "
                + " from tbl_n_txn_his a, tbl_mcht_base_inf b, tbl_mcht_settle_inf c "
                + " where a.card_accp_id = b.mcht_no and  b.mcht_no = c.mcht_no " + "and a.txn_num = '1301' " + whereSql;
        sql = "select db_1,db_2,db_3,db_4,db_5,db_6,db_7,db_8,db_9,sum(db_10),sum(db_11) from " + "(" + sql2 + " union all " + sql3 + ") "
                + "group by db_1,db_2,db_3,db_4,db_5,db_6,db_7,db_8,db_9";
        String countSql = "select count(1) from (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql + " order by db_1 desc", begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            dataList.set(i, data);
        }

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /** 直联商户清算明细 */
    @SuppressWarnings("unchecked")
    public static Object[] getMchtAlgoCupInfo(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        Object[] ret = new Object[2];
        String mchtCd = request.getParameter("mchtCd");
        String date = request.getParameter("date");
        String date1 = request.getParameter("date1");

        String whereSql = " ";

        if (isNotEmpty(mchtCd)) {
            whereSql += " AND trim(CARD_ACCP_ID)='" + mchtCd + "'";
        }

        if (isNotEmpty(date)) {
            whereSql += " and trim(INST_DATE)>='" + date + "'";
        }
        if (isNotEmpty(date1)) {
            whereSql += " and trim(INST_DATE)<='" + date1 + "'";
        }

        String sql = "select INST_DATE,TRANS_DATE_TIME,ACQ_INST_ID_CODE,CARD_ACCP_ID,trim(MCHT_NAME)," + "CARD_ACCP_TERM_ID,CUP_SSN,PAN,"
                + "(select TXN_NAME from TBL_TXN_NAME b where a.TXN_NUM=b.TXN_NUM)," + "AMT_TRANS,FEE_MCHT,FEE_CREDIT-FEE_DEBIT,AMT_TRANS+FEE_MCHT "
                + "from BTH_CUP_COMA a " + "where CANCEL_FLAG not in('R','C')  and substr(a.TXN_NUM,0,1) !='3' " + whereSql;
        // +
        // " order by INST_DATE desc,TRANS_DATE_TIME desc,ACQ_INST_ID_CODE,CARD_ACCP_ID,CARD_ACCP_TERM_ID,TXN_NUM";

        String countSql = "select count(1) from (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            data[9] = CommonFunction.transFenToYuan(data[9].toString());
            data[10] = CommonFunction.transFenToYuan(data[10].toString());
            data[11] = CommonFunction.transFenToYuan(data[11].toString());
            data[12] = CommonFunction.transFenToYuan(data[12].toString());
            dataList.set(i, data);
        }

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /** 直联商户清算汇总 */
    @SuppressWarnings("unchecked")
    public static Object[] getMchtAlgoInfoCupCount(int begin, HttpServletRequest request) {
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        Object[] ret = new Object[2];
        String mchtCd = request.getParameter("mchtCd");
        String signInstId = request.getParameter("signInstId");
        String agrBr = request.getParameter("agrBr");
        String date = request.getParameter("date");
        String date1 = request.getParameter("date1");
        String sql = "select a.MCHT_NO,trim(a.MCHT_NAME),b.MCHT_FLAG1,b.MCHT_FLAG1||b.MCHT_FLAG2,d.brh_name,sum(TRANS_NUM_COM+0),sum(TRANS_AMT_COM),sum(TRANS_FEE_COM),"
                + "sum(TRANS_NUM_ERR+0),sum(TRANS_AMT_ERR),sum(TRANS_BAK_AMT),sum(OTHER_AMT),"
                + "sum(TRANS_AMT_COM+TRANS_FEE_COM+TRANS_AMT_ERR+TRANS_BAK_AMT+OTHER_AMT),"
                + "sum((TRANS_AMT_COM+TRANS_FEE_COM+TRANS_AMT_ERR+TRANS_BAK_AMT+OTHER_AMT) "
                + "+ (IN_EX_AMT+AUR_RELIEF_AMT+AUR_CHARGE_AMT+AUR_BAK_AMT)),sum(bc.FEE_CREDIT - bc.FEE_DEBIT) "
                + "from BTH_CUP_ZPSUM a left outer join TBL_MCHT_BASE_INF b on (a.MCHT_NO =b.MCHT_NO) "
                + "left outer join BTH_CUP_COMA bc on (bc.CARD_ACCP_ID = a.MCHT_NO and bc.INST_DATE = a.inst_date) "
                + "left outer join tbl_brh_info d on (b.agr_br = d.brh_id)  " + " where 1=1 ";

        if (isNotEmpty(mchtCd)) {
            sql += " AND trim(a.MCHT_NO)='" + mchtCd + "'";
        }
        if (isNotEmpty(signInstId) && !"0000".equals(signInstId)) {
            sql += " AND trim(b.SIGN_INST_ID)='" + signInstId + "'";
        }
        if (isNotEmpty(date)) {
            sql += " and trim(a.INST_DATE)>='" + date + "'";
        }
        if (isNotEmpty(date1)) {
            sql += " and trim(a.INST_DATE)<='" + date1 + "'";
        }

        String countSql = "select count(1) from (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(
                sql + " group by a.MCHT_NO,a.MCHT_NAME,b.MCHT_FLAG1,b.MCHT_FLAG1||b.MCHT_FLAG2,d.brh_name ",
                begin,
                Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);

            data[6] = CommonFunction.transFenToYuan(data[6].toString());
            data[7] = CommonFunction.transFenToYuan(data[7].toString());
            data[9] = CommonFunction.transFenToYuan(data[9].toString());
            data[10] = CommonFunction.transFenToYuan(data[10].toString());
            data[11] = CommonFunction.transFenToYuan(data[11].toString());
            data[12] = CommonFunction.transFenToYuan(data[12].toString());
            data[13] = CommonFunction.transFenToYuan(data[13].toString());
            data[14] = CommonFunction.transFenToYuan(data[14].toString());
            dataList.set(i, data);
        }

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 风险交易查询(套现)(月)
     * */
    public static Object[] getRiskTxnInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        String brhId = request.getParameter("brhId");
        String mchtCd = request.getParameter("mchtCd");
        String mon = request.getParameter("mon");
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer sql = new StringBuffer("SELECT a.ABS_MON,a.BRH_NO,a.MCHT_NO,a.MCHT_NM,a.SETTLE_ACCT,a.SINGLE_COUNT,a.SINGLE_AMT,"
                + "a.CREDIT_COUNT,a.CREDIT_AMT,a.RET_COUNT,a.RET_AMT,a.INTE_COUNT,a.INTE_AMT,a.BIG_COUNT,a.BIG_AMT,a.REF_COUNT,a.REF_AMT,a.MISC,b.WHITELIST_FLAG,a.FLAG"
                + " FROM ABS_MCHT_TRADE_MON a LEFT OUTER JOIN TBL_MCHT_SETTLE_INF b ON(a.MCHT_NO=b.MCHT_NO) " + " WHERE FLAG<>'5' ");
        if (!"0000".equals(operator.getOprBrhId())) {
            sql.append(" AND a.BRH_NO IN " + operator.getBrhBelowId());
        }
        if (StringUtils.isNotEmpty(brhId)) {
            sql.append(" AND a.BRH_NO='" + brhId + "'");
        }
        if (StringUtils.isNotEmpty(mchtCd)) {
            sql.append(" AND a.MCHT_NO='" + mchtCd + "'");
        }
        if (StringUtils.isNotEmpty(mon)) {
            sql.append(" AND a.ABS_MON='" + mon + "'");
        }
        sql.append(" ORDER BY a.ABS_MON");
        String countSql = "SELECT COUNT(1) FROM(" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            data[6] = CommonFunction.transFenToYuan(data[6].toString());
            data[8] = CommonFunction.transFenToYuan(data[8].toString());
            data[10] = CommonFunction.transFenToYuan(data[10].toString());
            data[12] = CommonFunction.transFenToYuan(data[12].toString());
            data[14] = CommonFunction.transFenToYuan(data[14].toString());
            data[16] = CommonFunction.transFenToYuan(data[16].toString());
            dataList.set(i, data);
        }

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 风控处理
     * */
    public static Object[] getShiftTermAct(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        String mchtCd = request.getParameter("mchtCd");
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        // 表里存的原本就是元，这里先乘100，下面data里面转分，就能保留到小数点后面2位
        String sql = "select * from " + "(select a.DATE_SETTLMT,a.MCHT_NO,a.MCHT_NM,a.LAST_REMAIN_AMT*100,a.INFILE_AMT*100,a.RETURN_AMT*100,"
                + "a.SETTLE_AMT*100,a.LOAM_AMT*100,a.SETTLE_FLAG,b.WHITELIST_FLAG,"
                + "dense_rank() over(partition by a.MCHT_NO order by DATE_SETTLMT desc) drank,"
                + "(select count(*) from ABS_SHIFT_TERM_DAY c where a.MCHT_NO=c.MCHT_NO and a.SETTLE_FLAG ='2') "
                + "from TBL_MCHNT_INFILE_DTL a left outer join TBL_MCHT_SETTLE_INF b on(a.MCHT_NO=b.MCHT_NO)) ";
        StringBuffer where = new StringBuffer("where drank = 1 and SETTLE_FLAG ='2' ");

        if (StringUtils.isNotEmpty(mchtCd)) {
            where.append("and MCHT_NO='" + mchtCd + "' ");
        }
        sql = sql + where;

        String countSql = "select count(1) from(" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            data[3] = CommonFunction.transFenToYuan(data[3].toString());
            data[4] = CommonFunction.transFenToYuan(data[4].toString());
            data[5] = CommonFunction.transFenToYuan(data[5].toString());
            data[6] = CommonFunction.transFenToYuan(data[6].toString());
            data[7] = CommonFunction.transFenToYuan(data[7].toString());
            dataList.set(i, data);
        }
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 风控详细
     * */
    public static Object[] getShiftTermInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        String mchtCd = request.getParameter("mchtCd");
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        // 表里存的原本就是元，这里先乘100，下面data里面转分，就能保留到小数点后面2位
        String sql = "select DATE_SETTLMT,a.MCHT_NO,a.MCHT_NM,LAST_REMAIN_AMT*100,INFILE_AMT*100,"
                + "RETURN_AMT*100,SETTLE_AMT*100,LOAM_AMT*100,SETTLE_FLAG,WHITELIST_FLAG,"
                + "(select count(*) from ABS_SHIFT_TERM_DAY c where a.MCHT_NO=c.MCHT_NO and a.SETTLE_FLAG ='2') "
                + "from TBL_MCHNT_INFILE_DTL a left outer join TBL_MCHT_SETTLE_INF b on(a.MCHT_NO=b.MCHT_NO) ";
        StringBuffer where = new StringBuffer("where SETTLE_FLAG in('2','4','5','7') ");

        if (StringUtils.isNotEmpty(mchtCd)) {
            where.append("and a.MCHT_NO='" + mchtCd + "' ");
        }
        sql = sql + where + " order by DATE_SETTLMT desc";

        String countSql = "select count(1) from(" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        Object[] data;
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            data[3] = CommonFunction.transFenToYuan(data[3].toString());
            data[4] = CommonFunction.transFenToYuan(data[4].toString());
            data[5] = CommonFunction.transFenToYuan(data[5].toString());
            data[6] = CommonFunction.transFenToYuan(data[6].toString());
            data[7] = CommonFunction.transFenToYuan(data[7].toString());
            dataList.set(i, data);
        }
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 移机监测信息(日)详细信息
     * */
    public static Object[] getshiftTermDayInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        String brhId = request.getParameter("brhId");
        String mchtCd = request.getParameter("mchtCd");
        String dateStart = request.getParameter("dateStart");
        String dateEnd = request.getParameter("dateEnd");
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        StringBuffer sql = new StringBuffer("select * from (select ABS_DATE,BRH_NO,a.MCHT_NO,a.MCHT_NM,TERM_NO,a.SETTLE_ACCT,"
                + "AMT_TRANS,FLAG,WHITELIST_FLAG,ACQ_INST_ID,REAL_TEL,BIND_TEL1,BIND_TEL2,BIND_TEL3,TXN_NUM,INST_TIME,"
                + "PAN,TERM_SSN,CERTI_ID,AUTHR_ID_R,RETRIVL_REF,"
                + "dense_rank() over(partition by a.MCHT_NO,TERM_NO order by ABS_DATE,INST_TIME desc) drank "
                + " from ABS_SHIFT_TERM_DAY a left outer join TBL_MCHT_SETTLE_INF b on(a.MCHT_NO=b.MCHT_NO)) " + "where drank = '1' ");
        if (!"0000".equals(operator.getOprBrhId())) {
            sql.append(" and BRH_NO IN " + operator.getBrhBelowId());
        }
        if (StringUtils.isNotEmpty(brhId)) {
            sql.append(" and BRH_NO='" + brhId + "'");
        }
        if (StringUtils.isNotEmpty(mchtCd)) {
            sql.append(" and a.MCHT_NO='" + mchtCd + "'");
        }
        if (StringUtils.isNotEmpty(dateStart)) {
            sql.append(" and ABS_DATE>='" + dateStart + "'");
        }
        if (StringUtils.isNotEmpty(dateEnd)) {
            sql.append(" and ABS_DATE<='" + dateEnd + "'");
        }

        sql.append(" order by ABS_DATE,INST_TIME");

        String countSql = "select count(1) from(" + sql + ")";

        // System.out.println("Print:" + sql);

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 补登信息
     * */
    public static Object[] getTblDTxnInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String txnDate = request.getParameter("txnDate");
        String txnAmt = request.getParameter("txnAmt");
        String mchtNo = request.getParameter("mchtNo");
        String record = request.getParameter("record");
        String txnNum = request.getParameter("txnNum");
        String oprSta = request.getParameter("oprSta");
        String settleStatus = request.getParameter("settleStatus");
        StringBuffer sql = new StringBuffer("select t1.INST_DATE ,t1.INST_TIME,t1.SETTLE_DATE,t1.SYS_SEQ_NUM,"
                + "t1.MCHT_NO,t1.PAN,t1.TRANS_AMT,t1.RETURN_FEE,t1.CREATE_OPR,t1.UPDATE_DATE,t1.UPDATE_OPR,"
                + "t1.OPR_STA,t1.SETTLE_STATUS,t1.RECORD,t1.RESERVED" + " from TBL_D_TXN t1 inner join TBL_OPR_INFO t2 on t1.CREATE_OPR=t2.opr_id "
                + "where t1.TXN_NUM='" + txnNum + "' ");
        if (StringUtils.isNotEmpty(txnDate)) {
            sql.append(" and t1.INST_DATE='" + txnDate + "'");
        }
        if (StringUtils.isNotEmpty(txnAmt)) {
            sql.append(" and t1.TRANS_AMT='" + txnAmt + "'");
        }
        if (StringUtils.isNotEmpty(mchtNo)) {
            sql.append(" and t1.MCHT_NO='" + mchtNo + "'");
        }
        if (StringUtils.isNotEmpty(record)) {
            sql.append(" and t1.RECORD like '%" + record + "%'");
        }
        if (StringUtils.isNotEmpty(oprSta)) {
            sql.append(" and t1.OPR_STA = '" + oprSta + "'");
        }
        if (StringUtils.isNotEmpty(settleStatus)) {
            sql.append(" and t1.SETTLE_STATUS = '" + settleStatus + "'");
        }
        if (!"0000".equals(operator.getOprBrhId())) {
            sql.append(" and t2.brh_id IN " + operator.getBrhBelowId());
        }
        sql.append(" order by t1.INST_DATE desc,t1.INST_TIME desc");

        String countSql = "select count(1) from(" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户退货信息
     * */
    public static Object[] getTblRTxnInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String orgTxnDate = request.getParameter("orgTxnDate");
        String txnDate = request.getParameter("txnDate");
        String txnAmt = request.getParameter("txnAmt");
        String retAmt = request.getParameter("retAmt");
        String record = request.getParameter("record");
        String txnSsn = request.getParameter("txnSsn");
        String oprSta = request.getParameter("oprSta");
        String settleStatus = request.getParameter("settleStatus");
        String msgSrcId = request.getParameter("msgSrcId");
        String auditFlag = request.getParameter("auditFlag");// 是否是待审核的查询
        // 手工退货审核的时候用到
        StringBuffer sql = new StringBuffer(
                "select t1.INST_DATE,t1.INST_TIME,t1.SETTLE_DATE,t1.MSG_SRC_ID,t1.TERM_SSN,t1.SYS_SEQ_NUM,t1.KEY_RSP,t1.PAN,t1.CARD_TYPE,t1.LICENCE_NO,t1.MCHT_NO,to_number(t1.RETURN_AMT)/100,t1.ORIG_TXN_NUM,to_number(t1.ORIG_AMT)/100,t1.ORIG_DIV_NO,t1.ORIG_PRODUCT_CODE,t1.ORIG_DATE,t1.RETRIVL_REF,t1.ORIG_FEE_ALGO_ID,t1.ORIG_FEE_MD,t1.ORIG_FEE_PARAM,t1.ORIG_FEE_PCT_MIN,t1.ORIG_FEE_PCT_MAX,to_number(t1.ORIG_FEE)/100,to_number(RETURN_FEE)/100,t1.TERM_NO,t1.CREATE_OPR,t1.UPDATE_OPR,t1.UPDATE_DATE,t1.MATCH_STA,t1.OPR_STA,t1.SETTLE_STATUS,t1.RECORD,RESERVED,AUDIT_STA"
                        + " from TBL_R_TXN t1 inner join TBL_OPR_INFO t2 on trim(t1.CREATE_OPR)=t2.opr_id   where 1=1 ");
        if (StringUtils.isNotEmpty(orgTxnDate)) {
            sql.append(" and t1.ORIG_DATE='" + orgTxnDate + "'");
        }
        if (StringUtils.isNotEmpty(txnDate)) {
            sql.append(" and t1.INST_DATE='" + txnDate + "'");
        }
        if (StringUtils.isNotEmpty(txnAmt)) {
            sql.append(" and t1.ORIG_AMT='" + txnAmt + "'");
        }
        if (StringUtils.isNotEmpty(retAmt)) {
            sql.append(" and t1.RETURN_AMT='" + retAmt + "'");
        }
        if (StringUtils.isNotEmpty(txnSsn)) {
            sql.append(" and t1.SYS_SEQ_NUM='" + txnSsn + "'");
        }
        if (StringUtils.isNotEmpty(record)) {
            sql.append(" and t1.RECORD like '%" + record + "%'");
        }
        if (StringUtils.isNotEmpty(oprSta)) {
            sql.append(" and t1.OPR_STA = '" + oprSta + "'");
        }
        if (StringUtils.isNotEmpty(settleStatus)) {
            sql.append(" and t1.SETTLE_STATUS = '" + settleStatus + "'");
        }
        if ("1901".equals(msgSrcId)) {
            sql.append(" and t1.msg_src_id='1901'");// 交易补登里面的手工退货只查询管理平台发起的
        }
        if ("auditFlag".equals(auditFlag)) {
            sql.append(" and t1.audit_sta in ('1','2') ");// 待审核状态
        }
        if (!"0000".equals(operator.getOprBrhId())) {
            sql.append(" and t2.brh_id IN " + operator.getBrhBelowId());
        }
        sql.append(" order by t1.INST_DATE,t1.INST_TIME");

        String countSql = "select count(1) from(" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询全部商户正式表信息
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntBaseInfoAllTmp(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String mchtStatus = request.getParameter("mchtStatus");
        String agrBr = request.getParameter("brhId");
        String mchtFlag1 = request.getParameter("MchtFlag1");
        String connType = request.getParameter("connType");
        String mchtId = request.getParameter("mchntId");
        String ylMchntNo = request.getParameter("acmchntId");
        String mchtGrp = request.getParameter("mchtGrp");
        // 查询条件组串
        StringBuffer whereSql = new StringBuffer();

        whereSql.append("WHERE CONN_TYPE IN ('J','Z') ");
        // 注册开始日期
        if (StringUtils.isNotEmpty(startDate)) {
            whereSql.append(" AND substr(REC_CRT_TS,0,8) >= '" + startDate + "'");
        }
        // 注册结束日期
        if (StringUtils.isNotEmpty(endDate)) {
            whereSql.append(" AND substr(REC_CRT_TS,0,8) <= '" + endDate + "'");
        }
        // 商户状态
        if (StringUtils.isNotEmpty(mchtStatus)) {
            whereSql.append(" AND MCHT_STATUS = '" + mchtStatus + "'");
        }
        // 归属机构
        if (StringUtils.isNotEmpty(agrBr)) {
            whereSql.append(" AND AGR_BR in " + InformationUtil.getBrhGroupString(agrBr) + "");
        } else {
            if (!"0000".equals(operator.getOprBrhId())) {
                whereSql.append(" AND AGR_BR in " + operator.getBrhBelowId() + "");
            }
        }
        // 商户性质1
        if (StringUtils.isNotEmpty(mchtFlag1)) {
            whereSql.append(" AND MCHT_FLAG1 = '" + mchtFlag1 + "'");
        }
        // 商户接入方式
        if (StringUtils.isNotEmpty(connType)) {
            whereSql.append(" AND CONN_TYPE = '" + connType + "'");
        }
        // 商户ID
        if (StringUtils.isNotEmpty(mchtId)) {
            whereSql.append(" AND MCHT_NO = '" + mchtId + "'");
        }

        // 商户号
        if (StringUtils.isNotEmpty(ylMchntNo)) {
            whereSql.append(" AND MAPPING_MCHNTCDONE like '%" + ylMchntNo + "%'");
            whereSql.append(" or MAPPING_MCHNTCDTWO like '%" + ylMchntNo + "%'");
        }
        // MCC类别
        if (StringUtils.isNotEmpty(mchtGrp)) {
            whereSql.append(" AND MCHT_GRP = '" + mchtGrp + "'");
        }
        whereSql.append("  order by REC_CRT_TS desc ");

        StringBuffer sb = new StringBuffer();
        sb.append(
                " SELECT MCHT_NO, MCHT_NM,MAPPING_MCHNTCDONE,MAPPING_MCHNTCDTWO, "
                        + " LICENCE_NO,substr(REC_CRT_TS,0,8),MCHT_STATUS,nvl(B.TERM_COUNT,0),ENG_NAME,MCC,ADDR,COMM_EMAIL,CONTACT,COMM_TEL,CRT_OPR_ID,UPD_OPR_ID, "
                        + " SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2), "
                        + " MAPPING_MCHNTTYPEONE,MAPPING_MCHNTTYPETWO  FROM (SELECT * FROM TBL_MCHT_BASE_INF_TMP " + whereSql + ") a "
                        + "left outer join (select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) b "
                        + "ON (a.MCHT_NO = b.MCHT_CD) ORDER BY SUBSTR (REC_CRT_TS, 0, 8) desc");
        String countSql = "SELECT COUNT(*) FROM (" + sb.toString() + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询全部商户正式表信息
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntBaseInfoAll(int begin, HttpServletRequest request) {

        String whereSql = " WHERE CONN_TYPE IN ('J','Z') ";
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (isNotEmpty(request.getParameter("mchntId"))) {
            whereSql += (" AND MCHT_NO = '" + request.getParameter("mchntId") + "' ");
        }

        String acmchntId = request.getParameter("acmchntId");
        // 收单方商户号
        if (acmchntId != null && !acmchntId.trim().equals("")) {
            whereSql += " AND (MAPPING_MCHNTCDONE like '%" + acmchntId + "%' ";
            whereSql += " or MAPPING_MCHNTCDTWO like '%" + acmchntId + "%') ";
        }
        if (isNotEmpty(request.getParameter("mchtStatus"))) {
            whereSql += (" AND MCHT_STATUS = '" + request.getParameter("mchtStatus") + "' ");
        }
        if (isNotEmpty(request.getParameter("mchtGrp"))) {
            whereSql += (" AND MCHT_GRP = '" + request.getParameter("mchtGrp") + "' ");
        }
        if (isNotEmpty(request.getParameter("startDate"))) {
            whereSql += (" AND substr(REC_CRT_TS,0,8) >= '" + request.getParameter("startDate") + "' ");
        }
        if (isNotEmpty(request.getParameter("endDate"))) {
            whereSql += (" AND substr(REC_CRT_TS,0,8) <= '" + request.getParameter("endDate") + "' ");
        }

        if (isNotEmpty(request.getParameter("MchtFlag1"))) {
            whereSql += (" AND MCHT_FLAG1 = '" + request.getParameter("MchtFlag1") + "' ");
        }
        if (isNotEmpty(request.getParameter("connType"))) {
            whereSql += (" AND CONN_TYPE = '" + request.getParameter("connType") + "' ");
        }
        if (isNotEmpty(request.getParameter("brhId"))) {
            whereSql += " AND AGR_BR in " + InformationUtil.getBrhGroupString(request.getParameter("brhId")) + " ";
        } else {
            if (!"0000".equals(operator.getOprBrhId())) {
                whereSql += (" AND AGR_BR IN " + operator.getBrhBelowId() + " ");
            }
        }
        whereSql += " order by REC_CRT_TS desc ";
        String and = " WHERE CONN_TYPE IN ('J','Z')";
        if (isNotEmpty(request.getParameter("mchntId"))) {
            and += (" AND a.MCHT_NO = '" + request.getParameter("mchntId") + "' ");
        }
        if (isNotEmpty(request.getParameter("mchtStatus"))) {
            and += (" AND a.MCHT_STATUS = '" + request.getParameter("mchtStatus") + "' ");
        }
        if (isNotEmpty(request.getParameter("mchtGrp"))) {
            and += (" AND a.MCHT_GRP = '" + request.getParameter("mchtGrp") + "' ");
        }
        if (isNotEmpty(request.getParameter("startDate"))) {
            and += (" AND substr(a.REC_CRT_TS,0,8) >= '" + request.getParameter("startDate") + "' ");
        }
        if (isNotEmpty(request.getParameter("endDate"))) {
            and += (" AND substr(a.REC_CRT_TS,0,8) <= '" + request.getParameter("endDate") + "' ");
        }
        if (isNotEmpty(request.getParameter("MchtFlag1"))) {
            and += (" AND MCHT_FLAG1 = '" + request.getParameter("MchtFlag1") + "' ");
        }
        if (isNotEmpty(request.getParameter("connType"))) {
            and += (" AND CONN_TYPE = '" + request.getParameter("connType") + "' ");
        }
        if (isNotEmpty(request.getParameter("brhId"))) {
            and += (" AND a.AGR_BR = '" + request.getParameter("brhId") + "' ");
        } else {
            if (!"0000".equals(operator.getOprBrhId())) {
                and += (" AND a.AGR_BR IN " + operator.getBrhBelowId() + " ");
            }
        }
        Object[] ret = new Object[2];

        String sql = "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,LICENCE_NO,ADDR,"
                + "COMM_EMAIL,CONTACT,COMM_TEL,substr(REC_CRT_TS,0,8),MCHT_STATUS,nvl(B.TERM_COUNT,0),CRT_OPR_ID,UPD_OPR_ID,"
                + "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2),MAPPING_MCHNTTYPEONE,MAPPING_MCHNTCDONE,MAPPING_MCHNTTYPETWO,MAPPING_MCHNTCDTWO FROM "
                + "(SELECT * FROM TBL_MCHT_BASE_INF " + whereSql + ") A " + "left outer join "
                + "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
                + "ON (A.MCHT_NO = B.MCHT_CD) ORDER BY SUBSTR (REC_CRT_TS, 0, 8) desc";
        StringBuilder sqlList = new StringBuilder();
        sqlList.append("select ");
        sqlList.append(
                "c.mcht_no,c.mcht_nm,c.MAPPING_MCHNTCDONE,c.MAPPING_MCHNTCDTWO,c.risl_lvl,c.mcht_lvl,c.mcht_status,c.manu_auth_flag,c.part_num,");
        sqlList.append("c.disc_cons_flg,c.disc_cons_rebate,c.pass_flag,c.open_days,c.sleep_days,c.mcht_cn_abbr,c.spell_name,c.eng_name,"); // 9
        // 个字段
        sqlList.append("c.mcht_en_abbr,c.area_no,c.settle_area_no,c.addr,c.home_page,c.mcc,c.tcc,");
        sqlList.append("c.etps_attr,c.mng_mcht_id,c.mcht_grp,c.mcht_attr,c.mcht_group_flag,c.mcht_group_id,c.mcht_eng_nm,");
        sqlList.append("c.mcht_eng_addr,c.mcht_eng_city_name,c.otsc_name,c.otsc_addr,c.otsc_phone,c.otsc_div_rate,c.marketer_name,");
        sqlList.append("c.marketer_contact,c.check_frqc,c.mcht_post_email,c.sa_limit_amt,c.sa_action,c.psam_num,c.cd_mac_num,");
        sqlList.append("c.pos_num,c.conn_type,c.mcht_mng_mode,c.mcht_function,c.licence_no,c.licence_end_date,c.bank_licence_no,");
        sqlList.append("c.bus_type,c.bus_amt,c.mcht_cre_lvl,c.contact,c.post_code,c.comm_email,c.comm_mobil,");
        sqlList.append("c.comm_tel,c.contact_snd,c.comm_email_snd,c.comm_mobil_snd,c.comm_tel_snd,c.manager,c.artif_certif_tp,");
        sqlList.append("c.identity_no,c.manager_tel,c.fax,c.electrofax,c.reg_addr,c.apply_date,c.enable_date,");
        sqlList.append("c.pre_aud_nm,c.confirm_nm,c.protocal_id,c.sign_inst_id,c.net_nm,c.agr_br,c.net_tel,");
        sqlList.append("c.prol_date,c.prol_tlr,c.close_date,c.close_tlr,c.main_tlr,c.check_tlr,c.oper_no,");
        sqlList.append("c.oper_nm,c.proc_flag,c.set_cur,c.print_inst_id,c.acq_inst_id,c.acq_bk_name,c.bank_no,");
        sqlList.append("c.orgn_no,c.subbrh_no,c.subbrh_nm,c.open_time,c.close_time,c.vis_act_flg,c.vis_mcht_id,");
        sqlList.append("c.mst_act_flg,c.mst_mcht_id,c.amx_act_flg,c.amx_mcht_id,c.dnr_act_flg,c.dnr_mcht_id,c.jcb_act_flg,");
        sqlList.append("c.jcb_mcht_id,c.cup_mcht_flg,c.deb_mcht_flg,c.cre_mcht_flg,c.cdc_mcht_flg,c.reserved,c.upd_opr_id,");
        sqlList.append("c.crt_opr_id,c.rec_upd_ts,c.rec_crt_ts,c.rct_check_date,c.plan_check_date,c.mcht_flag1,c.mcht_flag2,");
        sqlList.append("b.settle_type,b.rate_flag,b.settle_chn,b.bat_time,b.auto_stl_flg,b.part_num,b.fee_type,");
        sqlList.append("b.fee_fixed,b.fee_max_amt,b.fee_min_amt,b.fee_rate,b.fee_div_1,b.fee_div_2,b.fee_div_3,");
        sqlList.append("b.settle_mode,b.fee_cycle,b.settle_rpt,b.whitelist_flag,b.acct_settle_type,b.acct_settle_rate,b.acct_settle_limit,");
        sqlList.append(
                "b.settle_bank_no,b.settle_bank_nm,b.settle_acct_nm,b.settle_acct,b.settle_bank_no_snd,b.settle_bank_nm_snd,b.settle_acct_nm_snd,");
        sqlList.append("b.settle_acct_snd,b.fee_acct_nm,b.fee_acct,b.group_flag,b.open_stlno,b.change_stlno,b.fee_back_flg,");
        sqlList.append("b.reserved,b.rec_upd_ts,b.rec_crt_ts,b.person_settle_flg ");
        sqlList.append("from ( select a.mcht_no,a.mcht_nm,a.risl_lvl,a.mcht_lvl,a.mcht_status,a.manu_auth_flag,a.part_num,");
        sqlList.append("a.disc_cons_flg,a.disc_cons_rebate,a.pass_flag,a.open_days,a.sleep_days,a.mcht_cn_abbr,a.spell_name,a.eng_name,"); // 9
        // 个字段
        sqlList.append("a.mcht_en_abbr,a.area_no,a.settle_area_no,a.addr,a.home_page,a.mcc,a.tcc,");
        sqlList.append("a.etps_attr,a.mng_mcht_id,a.mcht_grp,a.mcht_attr,a.mcht_group_flag,a.mcht_group_id,a.mcht_eng_nm,");
        sqlList.append("a.mcht_eng_addr,a.mcht_eng_city_name,a.otsc_name,a.otsc_addr,a.otsc_phone,a.otsc_div_rate,a.marketer_name,");
        sqlList.append("a.marketer_contact,a.check_frqc,a.mcht_post_email,a.sa_limit_amt,a.sa_action,a.psam_num,a.cd_mac_num,");
        sqlList.append("a.pos_num,a.conn_type,a.mcht_mng_mode,a.mcht_function,a.licence_no,a.licence_end_date,a.bank_licence_no,");
        sqlList.append("a.bus_type,a.bus_amt,a.mcht_cre_lvl,a.contact,a.post_code,a.comm_email,a.comm_mobil,");
        sqlList.append("a.comm_tel,a.contact_snd,a.comm_email_snd,a.comm_mobil_snd,a.comm_tel_snd,a.manager,a.artif_certif_tp,");
        sqlList.append("a.identity_no,a.manager_tel,a.fax,a.electrofax,a.reg_addr,a.apply_date,a.enable_date,");
        sqlList.append("a.pre_aud_nm,a.confirm_nm,a.protocal_id,a.sign_inst_id,a.net_nm,a.agr_br,a.net_tel,");
        sqlList.append("a.prol_date,a.prol_tlr,a.close_date,a.close_tlr,a.main_tlr,a.check_tlr,a.oper_no,");
        sqlList.append("a.oper_nm,a.proc_flag,a.set_cur,a.print_inst_id,a.acq_inst_id,a.acq_bk_name,a.bank_no,");
        sqlList.append("a.orgn_no,a.subbrh_no,a.subbrh_nm,a.open_time,a.close_time,a.vis_act_flg,a.vis_mcht_id,");
        sqlList.append("a.mst_act_flg,a.mst_mcht_id,a.amx_act_flg,a.amx_mcht_id,a.dnr_act_flg,a.dnr_mcht_id,a.jcb_act_flg,");
        sqlList.append("a.jcb_mcht_id,a.cup_mcht_flg,a.deb_mcht_flg,a.cre_mcht_flg,a.cdc_mcht_flg,a.reserved,a.upd_opr_id,");
        sqlList.append("a.crt_opr_id,a.rec_upd_ts,a.rec_crt_ts,a.rct_check_date,a.plan_check_date,a.mcht_flag1,a.mcht_flag2");
        sqlList.append(",a.MAPPING_MCHNTCDONE,a.MAPPING_MCHNTCDTWO");
        // sqlList.append("b.fee_fixed,b.fee_max_amt,b.fee_min_amt,b.fee_rate,b.fee_div_1,b.fee_div_2,b.fee_div_3,");
        // sqlList.append("b.settle_mode,b.fee_cycle,b.settle_rpt,b.whitelist_flag,b.acct_settle_type,b.acct_settle_rate,b.acct_settle_limit,");
        // sqlList.append("b.settle_bank_no,b.settle_bank_nm,b.settle_acct_nm,b.settle_acct,b.settle_bank_no_snd,b.settle_bank_nm_snd,b.settle_acct_nm_snd,");
        // sqlList.append("b.settle_acct_snd,b.fee_acct_nm,b.fee_acct,b.group_flag,b.open_stlno,b.change_stlno,b.fee_back_flg,");
        // sqlList.append("b.reserved,b.rec_upd_ts,b.rec_crt_ts,b.person_settle_flg FROM TBL_MCHT_BASE_INF a");
        sqlList.append(" FROM TBL_MCHT_BASE_INF a  ");
        sqlList.append(and);
        sqlList.append(" ) c left outer join TBL_MCHT_SETTLE_INF b on c.mcht_no = b.mcht_no");
        List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlList.toString());
        request.getSession().removeAttribute("queryList");
        request.getSession().setAttribute("queryList", list);
        String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询终端信息查询（商户状态不正常，应该在终端信息查询中不能显示。）
     */
    public static Object[] getTermInfoByConditon(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String termSta = request.getParameter("termSta");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String termId = request.getParameter("termId");
        String mchtCd = request.getParameter("mchtCd");
        String termBranch = request.getParameter("termBranch");
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" on a.MCHT_CD = b.mcht_no ");
        whereSql.append(" WHERE 1=1 ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and a.TERM_BRANCH in " + operator.getBrhBelowId());
        }
        if (startTime != null && !startTime.trim().equals("")) {
            whereSql.append(" AND a.REC_UPD_TS>=");
            whereSql.append(startTime.split("T")[0].replaceAll("-", ""));
        }
        if (endTime != null && !endTime.trim().equals("")) {
            whereSql.append(" AND a.REC_UPD_TS<=");
            whereSql.append(endTime.split("T")[0].replaceAll("-", ""));
        }
        if (mchtCd != null && !mchtCd.trim().equals("")) {
            whereSql.append(" AND a.MCHT_CD='");
            whereSql.append(mchtCd);
            whereSql.append("'");
        }
        if (termBranch != null && !termBranch.trim().equals("")) {
            whereSql.append(" AND a.TERM_BRANCH='");
            whereSql.append(termBranch);
            whereSql.append("'");
        }
        if (termId != null && !termId.trim().equals("")) {
            whereSql.append(" AND a.TERM_ID LIKE '%");
            whereSql.append(termId);
            whereSql.append("%' ");
        }
        if (termSta != null && !termSta.trim().equals("")) {
            if (termSta.length() == 1) {
                whereSql.append(" AND a.TERM_STA='").append(termSta).append("'");
            } else {
                termSta = termSta.replaceAll("1", "','");
                whereSql.append(" AND a.TERM_STA in ('").append(termSta).append("')");
            }
        }
        whereSql.append(" AND b.mcht_status != '9' ");
        String sql = "SELECT a.TERM_ID,a.MCHT_CD,a.TERM_STA,a.TERM_SIGN_STA,a.TERM_ID_ID,a.TERM_FACTORY,a.TERM_MACH_TP,"
                + "a.TERM_VER,a.TERM_TP,a.TERM_BRANCH,a.TERM_INS,a.REC_CRT_TS,a.RENT_FEE FROM TBL_TERM_INF_TMP a "
                + " right outer join TBL_MCHT_BASE_INF_TMP b  " + whereSql.toString() + " ORDER BY a.TERM_ID";
        String countSql = "SELECT COUNT(1) FROM TBL_TERM_INF_TMP a left outer join TBL_MCHT_BASE_INF_TMP b " + whereSql.toString();
        // System.out.println("Print:"+sql);
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户正式表导出查询终端
     * 
     * @param begin
     * @param request
     * @return
     */
    public static Object[] getBaseTermInfoByConditon(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String termSta = request.getParameter("termSta");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String termId = request.getParameter("termId");
        String mchtCd = request.getParameter("mchtCd");
        String termBranch = request.getParameter("termBranch");
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" on a.MCHT_CD = b.mcht_no ");
        whereSql.append(" WHERE 1=1 ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and a.TERM_BRANCH in " + operator.getBrhBelowId());
        }
        if (startTime != null && !startTime.trim().equals("")) {
            whereSql.append(" AND a.REC_UPD_TS>=");
            whereSql.append(startTime.split("T")[0].replaceAll("-", ""));
        }
        if (endTime != null && !endTime.trim().equals("")) {
            whereSql.append(" AND a.REC_UPD_TS<=");
            whereSql.append(endTime.split("T")[0].replaceAll("-", ""));
        }
        if (mchtCd != null && !mchtCd.trim().equals("")) {
            whereSql.append(" AND a.MCHT_CD='");
            whereSql.append(mchtCd);
            whereSql.append("'");
        }
        if (termBranch != null && !termBranch.trim().equals("")) {
            whereSql.append(" AND a.TERM_BRANCH='");
            whereSql.append(termBranch);
            whereSql.append("'");
        }
        if (termId != null && !termId.trim().equals("")) {
            whereSql.append(" AND a.TERM_ID LIKE '%");
            whereSql.append(termId);
            whereSql.append("%' ");
        }
        if (termSta != null && !termSta.trim().equals("")) {
            if (termSta.length() == 1) {
                whereSql.append(" AND a.TERM_STA='").append(termSta).append("'");
            } else {
                termSta = termSta.replaceAll("1", "','");
                whereSql.append(" AND a.TERM_STA in ('").append(termSta).append("')");
            }
        }
        whereSql.append(" AND b.mcht_status != '9' ");
        String sql = "SELECT a.TERM_ID,a.MCHT_CD,a.TERM_STA,a.TERM_SIGN_STA,a.TERM_ID_ID,a.TERM_FACTORY,a.TERM_MACH_TP,"
                + "a.TERM_VER,a.TERM_TP,a.TERM_BRANCH,a.TERM_INS,a.REC_CRT_TS,a.RENT_FEE FROM TBL_TERM_INF a "
                + " right outer join TBL_MCHT_BASE_INF b  " + whereSql.toString() + " ORDER BY a.TERM_ID";
        String countSql = "SELECT COUNT(1) FROM TBL_TERM_INF a left outer join TBL_MCHT_BASE_INF b " + whereSql.toString();
        // System.out.println("Print:"+sql);
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户正式表查询与导出==终端正式信息表
     * 
     * @param begin
     * @param request
     * @return
     */
    public static Object[] getTermInfoBaseAll(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String termSta = request.getParameter("termSta");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String termId = request.getParameter("termId");
        String mchtCd = request.getParameter("mchtCd");
        String termBranch = request.getParameter("termBranch");
        StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and TERM_BRANCH in " + operator.getBrhBelowId());
        }
        if (startTime != null && !startTime.trim().equals("")) {
            whereSql.append(" AND REC_UPD_TS>=");
            whereSql.append(startTime.split("T")[0].replaceAll("-", ""));
        }
        if (endTime != null && !endTime.trim().equals("")) {
            whereSql.append(" AND REC_UPD_TS<=");
            whereSql.append(endTime.split("T")[0].replaceAll("-", ""));
        }
        if (mchtCd != null && !mchtCd.trim().equals("")) {
            whereSql.append(" AND MCHT_CD='");
            whereSql.append(mchtCd);
            whereSql.append("'");
        }
        if (termBranch != null && !termBranch.trim().equals("")) {
            whereSql.append(" AND TERM_BRANCH='");
            whereSql.append(termBranch);
            whereSql.append("'");
        }
        if (termId != null && !termId.trim().equals("")) {
            whereSql.append(" AND TERM_ID LIKE '%");
            whereSql.append(termId);
            whereSql.append("%' ");
        }
        if (termSta != null && !termSta.trim().equals("")) {
            if (termSta.length() == 1)
                whereSql.append(" AND TERM_STA='").append(termSta).append("'");
            else {
                termSta = termSta.replaceAll("1", "','");
                whereSql.append(" AND TERM_STA in ('").append(termSta).append("')");
            }
        }
        String sql = "SELECT TERM_ID,MCHT_CD,TERM_STA,TERM_SIGN_STA,TERM_ID_ID,TERM_FACTORY,TERM_MACH_TP,"
                + "TERM_VER,TERM_TP,TERM_BRANCH,TERM_INS,REC_CRT_TS,RENT_FEE FROM TBL_TERM_INF " + whereSql.toString() + " ORDER BY TERM_ID";
        String countSql = "SELECT COUNT(1) FROM TBL_TERM_INF " + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询正常交易信息
     * 
     * @param begin
     * @param request
     * @return
     */

    public static Object[] getNormalTransInfo(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String mchntId = request.getParameter("mchntId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        StringBuffer whereSql = new StringBuffer(" WHERE a.STLM_FLG='1' ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and b.bank_no in " + operator.getBrhBelowId());
        }
        if (startDate != null && !startDate.trim().equals("")) {
            whereSql.append(" AND DATE_SETTLMT>= '" + startDate + "'");
        }
        if (endDate != null && !endDate.trim().equals("")) {
            whereSql.append(" AND DATE_SETTLMT<= '" + endDate + "'");
        }
        if (mchntId != null && !mchntId.trim().equals("")) {
            whereSql.append(" AND MCHT_CD like '%");
            whereSql.append(mchntId);
            whereSql.append("%'");
        }
        String sql = "SELECT DATE_SETTLMT,MCHT_CD," + "(select brh_name from tbl_brh_info d where b.bank_no=d.brh_id) brhNm,"
                + "c.SETTLE_ACCT,c.SETTLE_ACCT_NM," + "sum(MCHT_AT_C-MCHT_AT_D) transAmt," + "sum(MCHT_FEE_D-MCHT_FEE_C) fee,"
                + "sum(case TXN_NUM when '5171' then MCHT_AT_C-MCHT_AT_D else 0 end) errAmt,"
                + "sum(case TXN_NUM when '5171' then MCHT_FEE_D-MCHT_FEE_C else 0 end) errFee,"
                + "sum(MCHT_AT_C-MCHT_AT_D)-sum(MCHT_FEE_D-MCHT_FEE_C)-sum(case TXN_NUM when '5171' then MCHT_AT_C-MCHT_AT_D else 0 end)+sum(case TXN_NUM when '5171' then MCHT_FEE_D-MCHT_FEE_C else 0 end) back "
                + "FROM TBL_ALGO_DTL a " + "left outer join TBL_MCHT_BASE_INF b on(a.MCHT_CD=b.MCHT_NO) "
                + "left outer join TBL_MCHT_SETTLE_INF c on(a.MCHT_CD=c.MCHT_NO)" + whereSql.toString()
                + " group by DATE_SETTLMT,MCHT_CD,b.bank_no,c.SETTLE_ACCT,c.SETTLE_ACCT_NM " + "ORDER BY DATE_SETTLMT,MCHT_CD";
        String countSql = "SELECT COUNT(1) FROM (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询退货交易信息
     * 
     * @param begin
     * @param request
     * @return
     */

    public static Object[] getBackTransInfo(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String mchntId = request.getParameter("mchntId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and b.bank_no in " + operator.getBrhBelowId());
        }
        if (startDate != null && !startDate.trim().equals("")) {
            whereSql.append(" AND SETTLE_DATE>= '" + startDate + "'");
        }
        if (endDate != null && !endDate.trim().equals("")) {
            whereSql.append(" AND SETTLE_DATE<= '" + endDate + "'");
        }
        if (mchntId != null && !mchntId.trim().equals("")) {
            whereSql.append(" AND a.MCHT_NO like '%");
            whereSql.append(mchntId);
            whereSql.append("%'");
        }
        String sql = "SELECT SETTLE_DATE," + "a.MCHT_NO," + "c.SETTLE_ACCT_NM," + "PAN," + "INST_DATE," + "sum(nvl(ORIG_AMT/100,0)),"
                + "sum(nvl(RETURN_AMT/100,0))," + "sum(nvl(RETURN_FEE/100,0)) " + "FROM TBL_R_TXN a "
                + "left outer join TBL_MCHT_BASE_INF b on(a.MCHT_NO=b.MCHT_NO) " + "left outer join TBL_MCHT_SETTLE_INF c on(a.MCHT_NO=c.MCHT_NO)"
                + whereSql.toString() + " group by SETTLE_DATE,a.MCHT_NO,c.SETTLE_ACCT_NM,PAN,INST_DATE "
                + "ORDER BY SETTLE_DATE,a.MCHT_NO,pan,INST_DATE";
        String countSql = "SELECT COUNT(1) FROM (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询押款交易信息
     * 
     * @param begin
     * @param request
     * @return
     */

    public static Object[] getDeferTransInfo(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String mchntId = request.getParameter("mchntId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        StringBuffer whereSql = new StringBuffer(" WHERE a.STLM_FLG='4' ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and b.bank_no in " + operator.getBrhBelowId());
        }
        if (startDate != null && !startDate.trim().equals("")) {
            whereSql.append(" AND DATE_SETTLMT>= '" + startDate + "'");
        }
        if (endDate != null && !endDate.trim().equals("")) {
            whereSql.append(" AND DATE_SETTLMT<= '" + endDate + "'");
        }
        if (mchntId != null && !mchntId.trim().equals("")) {
            whereSql.append(" AND MCHT_CD like '%");
            whereSql.append(mchntId);
            whereSql.append("%'");
        }
        String sql = "SELECT DATE_SETTLMT,MCHT_CD," + "(select brh_name from tbl_brh_info d where b.bank_no=d.brh_id) brhNm,"
                + "c.SETTLE_ACCT,c.SETTLE_ACCT_NM," + "sum(MCHT_AT_C-MCHT_AT_D) transAmt," + "sum(MCHT_FEE_D-MCHT_FEE_C) fee,"
                + "sum(case TXN_NUM when '5171' then MCHT_AT_C-MCHT_AT_D else 0 end) errAmt,"
                + "sum(case TXN_NUM when '5171' then MCHT_FEE_D-MCHT_FEE_C else 0 end) errFee,"
                + "sum(MCHT_AT_C-MCHT_AT_D)-sum(MCHT_FEE_D-MCHT_FEE_C)-sum(case TXN_NUM when '5171' then MCHT_AT_C-MCHT_AT_D else 0 end)+sum(case TXN_NUM when '5171' then MCHT_FEE_D-MCHT_FEE_C else 0 end) back "
                + "FROM TBL_ALGO_DTL a " + "left outer join TBL_MCHT_BASE_INF b on(a.MCHT_CD=b.MCHT_NO) "
                + "left outer join TBL_MCHT_SETTLE_INF c on(a.MCHT_CD=c.MCHT_NO)" + whereSql.toString()
                + " group by DATE_SETTLMT,MCHT_CD,b.bank_no,c.SETTLE_ACCT,c.SETTLE_ACCT_NM " + "ORDER BY DATE_SETTLMT,MCHT_CD";
        String countSql = "SELECT COUNT(1) FROM (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询风险交易信息
     * 
     * @param begin
     * @param request
     * @return
     */

    public static Object[] getRiskTransInfo(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String mchntId = request.getParameter("mchntId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        StringBuffer whereSql = new StringBuffer(" WHERE a.STLM_FLG in('2','3') ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and b.bank_no in " + operator.getBrhBelowId());
        }
        if (startDate != null && !startDate.trim().equals("")) {
            whereSql.append(" AND DATE_SETTLMT>= '" + startDate + "'");
        }
        if (endDate != null && !endDate.trim().equals("")) {
            whereSql.append(" AND DATE_SETTLMT<= '" + endDate + "'");
        }
        if (mchntId != null && !mchntId.trim().equals("")) {
            whereSql.append(" AND MCHT_CD like '%");
            whereSql.append(mchntId);
            whereSql.append("%'");
        }
        String sql = "SELECT DATE_SETTLMT,MCHT_CD," + "(select brh_name from tbl_brh_info d where b.bank_no=d.brh_id) brhNm,"
                + "c.SETTLE_ACCT,c.SETTLE_ACCT_NM," + "sum(MCHT_AT_C-MCHT_AT_D) transAmt," + "sum(MCHT_FEE_D-MCHT_FEE_C) fee,"
                + "sum(case TXN_NUM when '5171' then MCHT_AT_C-MCHT_AT_D else 0 end) errAmt,"
                + "sum(case TXN_NUM when '5171' then MCHT_FEE_D-MCHT_FEE_C else 0 end) errFee,"
                + "sum(MCHT_AT_C-MCHT_AT_D)-sum(MCHT_FEE_D-MCHT_FEE_C)-sum(case TXN_NUM when '5171' then MCHT_AT_C-MCHT_AT_D else 0 end)+sum(case TXN_NUM when '5171' then MCHT_FEE_D-MCHT_FEE_C else 0 end) back,"
                + "(case a.STLM_FLG when '2' then '套现' when '3' then '移机' end) risk " + "FROM TBL_ALGO_DTL a "
                + "left outer join TBL_MCHT_BASE_INF b on(a.MCHT_CD=b.MCHT_NO) " + "left outer join TBL_MCHT_SETTLE_INF c on(a.MCHT_CD=c.MCHT_NO)"
                + whereSql.toString() + " group by DATE_SETTLMT,MCHT_CD,b.bank_no,c.SETTLE_ACCT,c.SETTLE_ACCT_NM,a.STLM_FLG "
                + "ORDER BY DATE_SETTLMT,MCHT_CD";
        String countSql = "SELECT COUNT(1) FROM (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询不平交易信息
     * 
     * @param begin
     * @param request
     * @return
     */

    public static Object[] getErrTransInfo(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String mchntId = request.getParameter("mchntId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        StringBuffer whereSql = new StringBuffer(" WHERE a.STLM_FLAG not in('0','7','8','9','A','D') ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and b.bank_no in " + operator.getBrhBelowId());
        }
        if (startDate != null && !startDate.trim().equals("")) {
            whereSql.append(" AND DATE_SETTLMT_8>= '" + startDate + "'");
        }
        if (endDate != null && !endDate.trim().equals("")) {
            whereSql.append(" AND DATE_SETTLMT_8<= '" + endDate + "'");
        }
        if (mchntId != null && !mchntId.trim().equals("")) {
            whereSql.append(" AND CARD_ACCP_ID like '%");
            whereSql.append(mchntId);
            whereSql.append("%'");
        }
        String sql = "SELECT DATE_SETTLMT_8,CARD_ACCP_ID," + "(select brh_name from tbl_brh_info d where b.bank_no=d.brh_id) brhNm,"
                + "c.SETTLE_ACCT,c.SETTLE_ACCT_NM," + "sum(AMT_TRANS/100) transAmt," + "sum(MCHT_FEE/100) fee," + "0 errAmt," + "0 errFee,"
                + "0 back " + "FROM BTH_GC_TXN_SUCC a " + "left outer join TBL_MCHT_BASE_INF b on(a.CARD_ACCP_ID=b.MCHT_NO) "
                + "left outer join TBL_MCHT_SETTLE_INF c on(a.CARD_ACCP_ID=c.MCHT_NO)" + whereSql.toString()
                + " group by DATE_SETTLMT_8,CARD_ACCP_ID,b.bank_no,c.SETTLE_ACCT,c.SETTLE_ACCT_NM " + "ORDER BY DATE_SETTLMT_8,CARD_ACCP_ID";
        String countSql = "SELECT COUNT(1) FROM (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询可疑交易信息
     * 
     * @param begin
     * @param request
     * @return
     */

    public static Object[] getDubiousTransInfo(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String mchntId = request.getParameter("mchntId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and bank_no in " + operator.getBrhBelowId());
        }
        if (startDate != null && !startDate.trim().equals("")) {
            whereSql.append(" AND dateSettle>= '" + startDate + "'");
        }
        if (endDate != null && !endDate.trim().equals("")) {
            whereSql.append(" AND dateSettle<= '" + endDate + "'");
        }
        if (mchntId != null && !mchntId.trim().equals("")) {
            whereSql.append(" AND mcht like '%");
            whereSql.append(mchntId);
            whereSql.append("%'");
        }
        String sql = "select dateSettle," + "mcht," + "(select brh_name from tbl_brh_info d where bank_no=d.brh_id) brhNm," + "SETTLE_ACCT,"
                + "SETTLE_ACCT_NM," + "sum(amt)," + "sum(fee)," + "sum(errAmt)," + "sum(errFee)," + "sum(back) "
                + "from (select DATE_SETTLMT dateSettle," + "a.MCHT_NO mcht," + "b.bank_no," + "c.SETTLE_ACCT," + "c.SETTLE_ACCT_NM,"
                + "TRANS_AMT/100 amt," + "TRANS_FEE/100 fee," + "0 errAmt," + "0 errFee," + "0 back " + "from BTH_HOST_TXN_SUSPS a "
                + "left outer join TBL_MCHT_BASE_INF b on(a.MCHT_NO=b.MCHT_NO) " + "left outer join TBL_MCHT_SETTLE_INF c on(a.MCHT_NO=c.MCHT_NO) "
                + "where STLM_FLAG ='7' " + "union all " + "select DATE_SETTLMT_8," + "CARD_ACCP_ID," + "b.bank_no," + "c.SETTLE_ACCT,"
                + "c.SETTLE_ACCT_NM," + "AMT_TRANS/100," + "MCHT_FEE/100," + "0 errAmt," + "0 errFee," + "0 back " + "from BTH_GC_TXN_SUCC_SUSPS a "
                + "left outer join TBL_MCHT_BASE_INF b on(a.CARD_ACCP_ID=b.MCHT_NO) "
                + "left outer join TBL_MCHT_SETTLE_INF c on(a.CARD_ACCP_ID=c.MCHT_NO) " + "where STLM_FLAG='7') " + whereSql.toString()
                + " group by dateSettle,mcht,bank_no,SETTLE_ACCT,SETTLE_ACCT_NM " + "ORDER BY dateSettle,mcht";
        String countSql = "SELECT COUNT(1) FROM (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 查询商品正式表信息
     * 
     * @param begin
     * @param request
     * @return
     */

    public static Object[] getTermInfoRealAll(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String termSta = request.getParameter("termSta");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String termId = request.getParameter("termId");
        String mchtCd = request.getParameter("mchtCd");
        String termBranch = request.getParameter("termBranch");
        StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" and TERM_BRANCH in " + operator.getBrhBelowId());
        }
        if (startTime != null && !startTime.trim().equals("")) {
            whereSql.append(" AND REC_UPD_TS>=");
            whereSql.append(startTime.split("T")[0].replaceAll("-", ""));
        }
        if (endTime != null && !endTime.trim().equals("")) {
            whereSql.append(" AND REC_UPD_TS<=");
            whereSql.append(endTime.split("T")[0].replaceAll("-", ""));
        }
        if (mchtCd != null && !mchtCd.trim().equals("")) {
            whereSql.append(" AND MCHT_CD='");
            whereSql.append(mchtCd);
            whereSql.append("'");
        }
        if (termBranch != null && !termBranch.trim().equals("")) {
            whereSql.append(" AND TERM_BRANCH='");
            whereSql.append(termBranch);
            whereSql.append("'");
        }
        if (termId != null && !termId.trim().equals("")) {
            whereSql.append(" AND TERM_ID LIKE '%");
            whereSql.append(termId);
            whereSql.append("%' ");
        }
        if (termSta != null && !termSta.trim().equals("")) {
            if (termSta.length() == 1)
                whereSql.append(" AND TERM_STA='").append(termSta).append("'");
            else {
                termSta = termSta.replaceAll("1", "','");
                whereSql.append(" AND TERM_STA in ('").append(termSta).append("')");
            }
        }
        String sql = "SELECT TERM_ID,MCHT_CD,TERM_STA,TERM_SIGN_STA,TERM_ID_ID,TERM_FACTORY,TERM_MACH_TP,"
                + "TERM_VER,TERM_TP,TERM_BRANCH,TERM_INS,REC_CRT_TS,RENT_FEE FROM TBL_TERM_INF  " + whereSql.toString() + " ORDER BY TERM_ID";
        String countSql = "SELECT COUNT(1) FROM TBL_TERM_INF " + whereSql.toString();

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户退货信息，控制台发起部分
     * */
    public static Object[] getTblRTxnInfoNew(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String orgTxnDate = request.getParameter("orgTxnDate");
        String txnDate = request.getParameter("txnDate");
        String txnAmt = request.getParameter("txnAmt");
        String retAmt = request.getParameter("retAmt");
        String record = request.getParameter("record");
        String mchtNo = request.getParameter("mchtNo");
        String retrivlRef = request.getParameter("retrivlRef");
        String oprSta = request.getParameter("oprSta");
        String settleStatus = request.getParameter("settleStatus");
        String msgSrcId = request.getParameter("msgSrcId");
        String auditFlag = request.getParameter("auditFlag");// 是否是待审核的查询
        // 手工退货审核的时候用到
        StringBuffer sql = new StringBuffer("select t1.INST_DATE,t1.INST_TIME,t1.SETTLE_DATE,t1.MSG_SRC_ID,"
                + "t1.TERM_SSN,t1.SYS_SEQ_NUM,t1.KEY_RSP,t1.PAN,t1.CARD_TYPE,t1.LICENCE_NO,t1.MCHT_NO,"
                + "to_number(t1.RETURN_AMT)/100,t1.ORIG_TXN_NUM,to_number(t1.ORIG_AMT)/100,t1.ORIG_DIV_NO,"
                + "t1.ORIG_PRODUCT_CODE,t1.ORIG_DATE,t1.RETRIVL_REF,t1.ORIG_FEE_ALGO_ID,t1.ORIG_FEE_MD,"
                + "t1.ORIG_FEE_PARAM,t1.ORIG_FEE_PCT_MIN,t1.ORIG_FEE_PCT_MAX,to_number(t1.ORIG_FEE)/100,"
                + "to_number(RETURN_FEE)/100,t1.TERM_NO,t1.CREATE_OPR,t1.UPDATE_OPR,t1.UPDATE_DATE,t1.MATCH_STA,"
                + "t1.OPR_STA,t1.SETTLE_STATUS,t1.RECORD,RESERVED,AUDIT_STA"
                + " from TBL_R_TXN t1 where MSG_SRC_ID = '1901' and substr(t1.MATCH_STA,2,1) != '2' ");
        if (StringUtils.isNotEmpty(orgTxnDate)) {
            sql.append(" and t1.ORIG_DATE='" + orgTxnDate + "'");
        }
        if (StringUtils.isNotEmpty(txnDate)) {
            sql.append(" and t1.INST_DATE='" + txnDate + "'");
        }
        if (StringUtils.isNotEmpty(txnAmt)) {
            sql.append(" and t1.ORIG_AMT='" + txnAmt + "'");
        }
        if (StringUtils.isNotEmpty(retAmt)) {
            sql.append(" and t1.RETURN_AMT='" + retAmt + "'");
        }
        if (StringUtils.isNotEmpty(mchtNo)) {
            sql.append(" and t1.MCHT_NO='" + mchtNo + "'");
        }
        if (StringUtils.isNotEmpty(retrivlRef)) {
            sql.append(" and t1.RETRIVL_REF='" + retrivlRef + "'");
        }
        if (StringUtils.isNotEmpty(record)) {
            sql.append(" and t1.RECORD like '%" + record + "%'");
        }
        if (StringUtils.isNotEmpty(oprSta)) {
            sql.append(" and t1.OPR_STA = '" + oprSta + "'");
        }
        if (StringUtils.isNotEmpty(settleStatus)) {
            sql.append(" and t1.SETTLE_STATUS = '" + settleStatus + "'");
        }
        // if("1901".equals(msgSrcId)){
        // sql.append(" and t1.msg_src_id='1901'");//交易补登里面的手工退货只查询管理平台发起的
        // }
        if ("auditFlag".equals(auditFlag)) {
            sql.append(" and t1.audit_sta in ('1','2') ");// 待审核状态
        }

        if (!"0000".equals(operator.getOprBrhId())) {
            sql.append(" and trim(subStr(t1.reserved,0,4)) in " + operator.getBrhBelowId());
        }
        sql.append(" order by t1.INST_DATE desc,t1.INST_TIME desc");

        String countSql = "select count(1) from(" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户退货信息all
     * */
    public static Object[] getTblRTxnInfoAll(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String orgTxnDate = request.getParameter("orgTxnDate");
        String txnDate = request.getParameter("txnDate");
        String cardType = request.getParameter("cardType");
        System.out.println("*****************************" + cardType + "************************************************");
        String txnAmt = request.getParameter("txnAmt");
        String retAmt = request.getParameter("retAmt");
        String record = request.getParameter("record");
        String retrivlRef = request.getParameter("retrivlRef");
        String oprSta = request.getParameter("oprSta");
        String settleStatus = request.getParameter("settleStatus");
        String msgSrcId = request.getParameter("msgSrcId");
        String mchtId = request.getParameter("mchtId");
        String auditFlag = request.getParameter("auditFlag");// 是否是待审核的查询
        // 手工退货审核的时候用到
        StringBuffer sql = new StringBuffer("select t1.INST_DATE,t1.INST_TIME,t1.SETTLE_DATE,t1.MSG_SRC_ID,"
                + "t1.TERM_SSN,t1.SYS_SEQ_NUM,t1.KEY_RSP,(select TXN_NAME from TBL_TXN_NAME b where t1.orig_txn_num = b.TXN_NUM) txnNm,t1.PAN,"
                + "(case t1.CARD_TYPE when '01' then '本行借记卡' " + " when '02' then '本行信用卡' " + " when '03' then '他行借记卡' "
                + " when '04' then '他行信用卡' end) CARD_TYPE," + "t1.LICENCE_NO,t1.MCHT_NO,"
                + "to_number(t1.RETURN_AMT)/100,t1.ORIG_TXN_NUM,to_number(t1.ORIG_AMT)/100,t1.ORIG_DIV_NO,"
                + "t1.ORIG_PRODUCT_CODE,t1.ORIG_DATE,t1.RETRIVL_REF,t1.ORIG_FEE_ALGO_ID,t1.ORIG_FEE_MD,"
                + "t1.ORIG_FEE_PARAM,t1.ORIG_FEE_PCT_MIN,t1.ORIG_FEE_PCT_MAX,to_number(t1.ORIG_FEE)/100,"
                + "to_number(RETURN_FEE)/100,t1.TERM_NO,t1.CREATE_OPR,t1.UPDATE_OPR,t1.UPDATE_DATE,"
                + "t1.MATCH_STA,t1.OPR_STA,t1.SETTLE_STATUS,t1.RECORD,RESERVED,AUDIT_STA" + " from TBL_R_TXN t1 where 1=1 ");
        if (StringUtils.isNotEmpty(orgTxnDate)) {
            sql.append(" and t1.ORIG_DATE='" + orgTxnDate + "'");
        }
        if (StringUtils.isNotEmpty(txnDate)) {
            sql.append(" and t1.INST_DATE='" + txnDate + "'");
        }
        if (StringUtils.isNotEmpty(cardType)) {
            sql.append(" and t1.CARD_TYPE='" + cardType + "'");
        }
        if (StringUtils.isNotEmpty(txnAmt)) {
            sql.append(" and t1.ORIG_AMT='" + txnAmt + "'");
        }
        if (StringUtils.isNotEmpty(retAmt)) {
            sql.append(" and t1.RETURN_AMT='" + retAmt + "'");
        }
        if (StringUtils.isNotEmpty(retrivlRef)) {
            sql.append(" and t1.RETRIVL_REF='" + retrivlRef + "'");
        }
        if (StringUtils.isNotEmpty(record)) {
            sql.append(" and t1.RECORD like '%" + record + "%'");
        }
        if (StringUtils.isNotEmpty(oprSta)) {
            sql.append(" and t1.OPR_STA = '" + oprSta + "'");
        }
        if (StringUtils.isNotEmpty(settleStatus)) {
            sql.append(" and t1.SETTLE_STATUS = '" + settleStatus + "'");
        }
        if (StringUtils.isNotEmpty(mchtId)) {
            sql.append(" and t1.MCHT_NO = '" + mchtId + "'");
        }
        // }
        if ("auditFlag".equals(auditFlag)) {
            sql.append(" and t1.audit_sta in ('1','2') ");// 待审核状态
        }

        if (!"0000".equals(operator.getOprBrhId())) {
            sql.append(" and trim(subStr(t1.reserved,0,4)) in " + operator.getBrhBelowId());
        }
        sql.append(" order by t1.INST_DATE desc,t1.INST_TIME desc");
        String countSql = "select count(1) from(" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户退货信息app
     * */
    public static Object[] getTblRTxnInfoApp(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String orgTxnDate = request.getParameter("orgTxnDate");
        String txnDate = request.getParameter("txnDate");
        String txnAmt = request.getParameter("txnAmt");
        String retAmt = request.getParameter("retAmt");
        String record = request.getParameter("record");
        String retrivlRef = request.getParameter("retrivlRef");
        String oprSta = request.getParameter("oprSta");
        String settleStatus = request.getParameter("settleStatus");
        String msgSrcId = request.getParameter("msgSrcId");
        String auditFlag = request.getParameter("auditFlag");// 是否是待审核的查询
        // 手工退货审核的时候用到
        StringBuffer sql = new StringBuffer("select t1.INST_DATE,t1.INST_TIME,t1.SETTLE_DATE,t1.MSG_SRC_ID,"
                + "t1.TERM_SSN,t1.SYS_SEQ_NUM,t1.KEY_RSP,t1.PAN,t1.CARD_TYPE,t1.LICENCE_NO,t1.MCHT_NO,"
                + "to_number(t1.RETURN_AMT)/100,t1.ORIG_TXN_NUM,to_number(t1.ORIG_AMT)/100,t1.ORIG_DIV_NO,"
                + "t1.ORIG_PRODUCT_CODE,t1.ORIG_DATE,t1.RETRIVL_REF,t1.ORIG_FEE_ALGO_ID,t1.ORIG_FEE_MD,"
                + "t1.ORIG_FEE_PARAM,t1.ORIG_FEE_PCT_MIN,t1.ORIG_FEE_PCT_MAX,to_number(t1.ORIG_FEE)/100,"
                + "to_number(RETURN_FEE)/100,t1.TERM_NO,t1.CREATE_OPR,t1.UPDATE_OPR,t1.UPDATE_DATE,"
                + "t1.MATCH_STA,t1.OPR_STA,t1.SETTLE_STATUS,t1.RECORD,RESERVED,AUDIT_STA" + " from TBL_R_TXN t1 " +
                // 单笔或者多笔重复 异常 待清算 初始化或者为null
                " where t1.MATCH_STA in('10','11') and t1.OPR_STA='1'"
                + " AND t1.SETTLE_STATUS ='1' AND (t1.AUDIT_STA IS NULL OR t1.AUDIT_STA = '0') ");
        // 无效作废 不清算 除去
        // " AND (OPR_STA != '2' AND SETTLE_STATUS != '2') ");
        if (StringUtils.isNotEmpty(orgTxnDate)) {
            sql.append(" and t1.ORIG_DATE='" + orgTxnDate + "'");
        }
        if (StringUtils.isNotEmpty(txnDate)) {
            sql.append(" and t1.INST_DATE='" + txnDate + "'");
        }
        if (StringUtils.isNotEmpty(txnAmt)) {
            sql.append(" and t1.ORIG_AMT='" + txnAmt + "'");
        }
        if (StringUtils.isNotEmpty(retAmt)) {
            sql.append(" and t1.RETURN_AMT='" + retAmt + "'");
        }
        if (StringUtils.isNotEmpty(retrivlRef)) {
            sql.append(" and t1.RETRIVL_REF='" + retrivlRef + "'");
        }
        if (StringUtils.isNotEmpty(record)) {
            sql.append(" and t1.RECORD like '%" + record + "%'");
        }
        if (StringUtils.isNotEmpty(oprSta)) {
            sql.append(" and t1.OPR_STA = '" + oprSta + "'");
        }
        if (StringUtils.isNotEmpty(settleStatus)) {
            sql.append(" and t1.SETTLE_STATUS = '" + settleStatus + "'");
        }

        // if("auditFlag".equals(auditFlag)){
        // sql.append(" and t1.audit_sta in ('1','2') ");//待审核状态
        // }

        if (!"0000".equals(operator.getOprBrhId())) {
            sql.append(" and trim(subStr(t1.reserved,0,4)) in " + operator.getBrhBelowId());
        }
        sql.append(" order by t1.INST_DATE desc,t1.INST_TIME desc");
        String countSql = "select count(1) from(" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户退货信息check
     * */
    public static Object[] getTblRTxnInfoCheck(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String orgTxnDate = request.getParameter("orgTxnDate");
        String txnDate = request.getParameter("txnDate");
        String txnAmt = request.getParameter("txnAmt");
        String retAmt = request.getParameter("retAmt");
        String record = request.getParameter("record");
        String retrivlRef = request.getParameter("retrivlRef");
        String oprSta = request.getParameter("oprSta");
        String settleStatus = request.getParameter("settleStatus");
        String msgSrcId = request.getParameter("msgSrcId");
        String auditFlag = request.getParameter("auditFlag");// 是否是待审核的查询
        // 手工退货审核的时候用到
        StringBuffer sql = new StringBuffer("select t1.INST_DATE,t1.INST_TIME,t1.SETTLE_DATE,t1.MSG_SRC_ID,"
                + "t1.TERM_SSN,t1.SYS_SEQ_NUM,t1.KEY_RSP,t1.PAN,t1.CARD_TYPE,t1.LICENCE_NO,t1.MCHT_NO,"
                + "to_number(t1.RETURN_AMT)/100,t1.ORIG_TXN_NUM,to_number(t1.ORIG_AMT)/100,t1.ORIG_DIV_NO,"
                + "t1.ORIG_PRODUCT_CODE,t1.ORIG_DATE,t1.RETRIVL_REF,t1.ORIG_FEE_ALGO_ID,t1.ORIG_FEE_MD,"
                + "t1.ORIG_FEE_PARAM,t1.ORIG_FEE_PCT_MIN,t1.ORIG_FEE_PCT_MAX,to_number(t1.ORIG_FEE)/100,"
                + "to_number(RETURN_FEE)/100,t1.TERM_NO,t1.CREATE_OPR,t1.UPDATE_OPR,t1.UPDATE_DATE,"
                + "t1.MATCH_STA,t1.OPR_STA,t1.SETTLE_STATUS,t1.RECORD,RESERVED,AUDIT_STA"
                + " from TBL_R_TXN t1 where t1.AUDIT_STA IN('1','2') and t1.MATCH_STA IN('10','11') ");
        if (StringUtils.isNotEmpty(orgTxnDate)) {
            sql.append(" and t1.ORIG_DATE='" + orgTxnDate + "'");
        }
        if (StringUtils.isNotEmpty(txnDate)) {
            sql.append(" and t1.INST_DATE='" + txnDate + "'");
        }
        if (StringUtils.isNotEmpty(txnAmt)) {
            sql.append(" and t1.ORIG_AMT='" + txnAmt + "'");
        }
        if (StringUtils.isNotEmpty(retAmt)) {
            sql.append(" and t1.RETURN_AMT='" + retAmt + "'");
        }
        if (StringUtils.isNotEmpty(retrivlRef)) {
            sql.append(" and t1.RETRIVL_REF='" + retrivlRef + "'");
        }
        if (StringUtils.isNotEmpty(record)) {
            sql.append(" and t1.RECORD like '%" + record + "%'");
        }
        if (StringUtils.isNotEmpty(oprSta)) {
            sql.append(" and t1.OPR_STA = '" + oprSta + "'");
        }
        if (StringUtils.isNotEmpty(settleStatus)) {
            sql.append(" and t1.SETTLE_STATUS = '" + settleStatus + "'");
        }
        // if("1901".equals(msgSrcId)){
        // sql.append(" and t1.msg_src_id='1901'");//交易补登里面的手工退货只查询管理平台发起的
        // }
        // if("auditFlag".equals(auditFlag)){
        // sql.append(" and t1.audit_sta in ('1','2') ");//待审核状态
        // }

        if (!"0000".equals(operator.getOprBrhId())) {
            sql.append(" and trim(subStr(t1.reserved,0,4)) in " + operator.getBrhBelowId());
        }
        sql.append(" order by t1.INST_DATE,t1.INST_TIME");

        String countSql = "select count(1) from(" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /*
     * 审核通过的信息
     */
    public static Object[] getTblRTxnInfoCheckReady(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String orgTxnDate = request.getParameter("orgTxnDate");
        String txnDate = request.getParameter("txnDate");
        String txnAmt = request.getParameter("txnAmt");
        String retAmt = request.getParameter("retAmt");
        String record = request.getParameter("record");
        String retrivlRef = request.getParameter("retrivlRef");
        String oprSta = request.getParameter("oprSta");
        String settleStatus = request.getParameter("settleStatus");
        String msgSrcId = request.getParameter("msgSrcId");
        String auditFlag = request.getParameter("auditFlag");// 是否是待审核的查询
        // 手工退货审核的时候用到
        StringBuffer sql = new StringBuffer("SELECT t1.INST_DATE,t1.INST_TIME,t1.SETTLE_DATE,t1.MSG_SRC_ID,"
                + "t1.TERM_SSN,t1.SYS_SEQ_NUM,t1.KEY_RSP,t1.PAN,t1.CARD_TYPE,t1.LICENCE_NO,t1.MCHT_NO,"
                + "TO_NUMBER(t1.RETURN_AMT)/100,t1.ORIG_TXN_NUM,TO_NUMBER(t1.ORIG_AMT)/100,t1.ORIG_DIV_NO,"
                + "t1.ORIG_PRODUCT_CODE,t1.ORIG_DATE,t1.RETRIVL_REF,t1.ORIG_FEE_ALGO_ID,t1.ORIG_FEE_MD,"
                + "t1.ORIG_FEE_PARAM,t1.ORIG_FEE_PCT_MIN,t1.ORIG_FEE_PCT_MAX,TO_NUMBER(t1.ORIG_FEE)/100,"
                + "TO_NUMBER(RETURN_FEE)/100,t1.TERM_NO,t1.CREATE_OPR,t1.UPDATE_OPR,t1.UPDATE_DATE,"
                + "t1.MATCH_STA,t1.OPR_STA,t1.SETTLE_STATUS,t1.RECORD,RESERVED,AUDIT_STA" + " FROM TBL_R_TXN t1 " +
                // 单笔或多笔审核通过
                " WHERE t1.AUDIT_STA = '3' AND t1.MATCH_STA IN('10','11')");
        if (StringUtils.isNotEmpty(orgTxnDate)) {
            sql.append(" and t1.ORIG_DATE='" + orgTxnDate + "'");
        }
        if (StringUtils.isNotEmpty(txnDate)) {
            sql.append(" and t1.INST_DATE='" + txnDate + "'");
        }
        if (StringUtils.isNotEmpty(txnAmt)) {
            sql.append(" and t1.ORIG_AMT='" + txnAmt + "'");
        }
        if (StringUtils.isNotEmpty(retAmt)) {
            sql.append(" and t1.RETURN_AMT='" + retAmt + "'");
        }
        if (StringUtils.isNotEmpty(retrivlRef)) {
            sql.append(" and t1.RETRIVL_REF='" + retrivlRef + "'");
        }
        if (StringUtils.isNotEmpty(record)) {
            sql.append(" and t1.RECORD like '%" + record + "%'");
        }
        if (StringUtils.isNotEmpty(oprSta)) {
            sql.append(" and t1.OPR_STA = '" + oprSta + "'");
        }
        if (StringUtils.isNotEmpty(settleStatus)) {
            sql.append(" and t1.SETTLE_STATUS = '" + settleStatus + "'");
        }
        // if("1901".equals(msgSrcId)){
        // sql.append(" and t1.msg_src_id='1901'");//交易补登里面的手工退货只查询管理平台发起的
        // }
        // if("auditFlag".equals(auditFlag)){
        // sql.append(" and t1.audit_sta in ('1','2') ");//待审核状态
        // }

        if (!"0000".equals(operator.getOprBrhId())) {
            sql.append(" and trim(subStr(t1.reserved,0,4)) in " + operator.getBrhBelowId());
        }
        sql.append(" order by t1.INST_DATE,t1.INST_TIME");

        String countSql = "select count(1) from(" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 角色信息查询
     * */
    public static Object[] getRoleInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        String sql = "select t1.ROLE_ID,t1.ROLE_NAME,t1.ROLE_TYPE,t1.DESCRIPTION,toi.OPR_NAME,t1.REC_CRT_TS,t1.REC_UPD_TS from tbl_role_inf t1 "
        		+ "LEFT JOIN TBL_OPR_INFO toi ON toi.OPR_ID = t1.REC_UPD_OPR where t1.ROLE_ID != 1 and ROLE_TYPE>='"
                + operator.getOprBrhLvl() + "' order by ROLE_ID";
        String countSql = "select count(1) from(" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /*********** 二期添加 ************/

    /**
     * 查询转入卡
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntInCard(int begin, HttpServletRequest request) {

        String whereSql = " WHERE 1=1 ";
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        if (isNotEmpty(request.getParameter("mchntNo"))) {
            whereSql += " AND A.MCHT_NO LIKE '%" + request.getParameter("mchntNo") + "%' ";
        }
        if (isNotEmpty(request.getParameter("termId"))) {
            whereSql += " AND A.TERM_ID LIKE '%" + request.getParameter("mchntNo") + "%' ";
        }
        if (isNotEmpty(request.getParameter("useFlag"))) {
            whereSql += " AND A.USE_FLAG LIKE '%" + request.getParameter("useFlag") + "%' ";
        }
        if (isNotEmpty(request.getParameter("inOutFlag"))) {
            whereSql += " AND A.IN_OUT_FLAG LIKE '%" + request.getParameter("inOutFlag") + "%' ";
        }
        if (isNotEmpty(request.getParameter("contactName"))) {
            whereSql += " AND A.CONTACT_NAME LIKE '%" + request.getParameter("contactName") + "%' ";
        }
        if (isNotEmpty(request.getParameter("contactIden"))) {
            whereSql += " AND A.CONTACT_IDENTIFY LIKE '%" + request.getParameter("contactIden") + "%' ";
        }
        if (isNotEmpty(request.getParameter("inCardNum"))) {
            whereSql += " AND A.IN_CARDNUM LIKE '%" + request.getParameter("inCardNum") + "%' ";
        }
        if (isNotEmpty(request.getParameter("startDate"))) {
            whereSql += " AND A.DEAL_BEGINTIME >= '" + request.getParameter("startDate") + "' ";
        }
        if (isNotEmpty(request.getParameter("endDate"))) {
            whereSql += " AND A.DEAL_ENDTIME >= '" + request.getParameter("endDate") + "' ";
        }
        Object[] ret = new Object[2];

        String sql = "SELECT A.TERM_ID,A.MCHT_NO,B.MCHT_NM,A.IN_OUT_FLAG,A.IN_CARDNUM,B.AGR_BR,B.BRH_NAME,"
                + "           A.CONTACT_NAME,A.CONTACT_TEL,A.CONTACT_IDENTIFY,A.IN_FLAG,A.USE_FLAG,"
                + "           A.MESSAGE_LIMIT,A.DEAL_BEGINTIME,A.DEAL_ENDTIME" + "    FROM TBL_IN_CARD_MANAGENT A " + "    LEFT OUTER JOIN "
                + "        (SELECT D.MCHT_NM,F.BRH_NAME,D.MCHT_NO,D.AGR_BR " + "          FROM TBL_MCHT_BASE_INF D "
                + "          LEFT OUTER JOIN TBL_BRH_INFO F" + "          ON D.AGR_BR = F.BRH_ID) B " + "    ON A.MCHT_NO = B.MCHT_NO ";

        String countSql = "SELECT COUNT(*) FROM (" + sql + whereSql.toString() + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql + whereSql.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;

        return ret;
    }

    /**
     * 终端版本管理
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] verMng(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String sql = "SELECT t1.VER_ID,t1.BANK_ID,t2.BRH_NAME,t1.MISC,t1.CRT_DATE,t1.UPD_DATE FROM TBL_VER_MNG t1 left join tbl_brh_info t2 on t1.bank_id = t2.cup_brh_id "
                + "WHERE t2.BRH_ID IN " + operator.getBrhBelowId() + "and t2.brh_level ='1' ORDER BY t1.BANK_ID,t1.VER_ID ";
        String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 配置首页提示信息
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] firstMsg(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        Object[] ret = new Object[2];

        String sql = "SELECT rtrim(f.BRH_ID),BRH_NAME,PPT_MSG,CRT_OPR,CRT_DATE,UPD_OPR,UPD_DATE from TBL_FIRST_PAGE f,TBL_BRH_INFO b "
                + "where rtrim(f.BRH_ID) = b.CUP_BRH_ID AND b.BRH_ID in " + operator.getBrhBelowId() + " and b.brh_level='1' order by f.BRH_ID desc";

        String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /*
     * 终端交易映射信息
     * 
     * @param begin
     * 
     * @param request
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] termTxnCode(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String sql = "SELECT TERM_TXN_CODE,INT_TXN_CODE,DSP FROM TBL_TERM_TXN order by TERM_TXN_CODE";
        String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 终端返回码说明
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] rspMsg(int begin, HttpServletRequest request) {

        Object[] ret = new Object[2];

        String sql = "SELECT RSP_NO,CHG_NO,ERR_NO,ERR_MSG,CRT_DATE,UPD_DATE FROM TBL_RSP_MSG order by CHG_NO,ERR_NO";

        String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 终端菜单管理
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getEposMenu(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        Object[] ret = new Object[2];
        String sql = "SELECT t1.BANK_ID,t2.BRH_NAME,t1.VER_ID,t1.MISC,nvl(v1.MENU_NUM1,0),nvl(v2.MENU_NUM2,0),nvl(v3.MENU_NUM3,0) FROM TBL_VER_MNG t1 "
                + "left join tbl_brh_info t2 on t1.bank_id = t2.cup_brh_id and t2.brh_level='1' "
                + "left join (select BRH_ID,VER_ID,count(*) as MENU_NUM1 from TBL_MENU_MSG  "
                + "WHERE MENU_LEVEL = '1' group by BRH_ID,VER_ID) v1 on (v1.BRH_ID = t1.BANK_ID and v1.VER_ID = t1.VER_ID) "
                + "left join (select BRH_ID,VER_ID,count(*) as MENU_NUM2 from TBL_MENU_MSG  "
                + "WHERE MENU_LEVEL = '2' group by BRH_ID,VER_ID) v2 on (v2.BRH_ID = t1.BANK_ID and v2.VER_ID = t1.VER_ID) "
                + "left join (select BRH_ID,VER_ID,count(*) as MENU_NUM3 from TBL_MENU_MSG  "
                + "WHERE MENU_LEVEL = '3' group by BRH_ID,VER_ID) v3 on (v3.BRH_ID = t1.BANK_ID and v3.VER_ID = t1.VER_ID) " + "WHERE t2.BRH_ID IN "
                + operator.getBrhBelowId();

        if (!StringUtil.isNull(request.getParameter("brhId"))) {
            sql += " and t1.BANK_ID = '" + request.getParameter("brhId") + "' ";
        }
        if (!StringUtil.isNull(request.getParameter("verId"))) {
            sql += " and t1.VER_ID = '" + request.getParameter("verId") + "' ";
        }

        sql += " ORDER BY t1.BANK_ID,t1.VER_ID ";

        String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户重置密码
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] gettblMchntApplyInf(int begin, HttpServletRequest request) {
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        String mchnNo = request.getParameter("mchnNo");

        Object[] ret = new Object[2];

        String whereSql = " where 1=1 ";
        /*
         * if (!"0000".equals(operator.getOprBrhId())) { whereSql +=
         * " AND BRH_ID IN " + operator.getBrhBelowId(); }
         */
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " AND AGR_BR IN " + InformationUtil.getBrhGroupString(operator.getOprBrhId()) + " ";
        }

        StringBuffer where = new StringBuffer();
        where.append(" and APPLY_TYPE = '3'");
        if (mchnNo != null && !mchnNo.equals("")) {
            where.append(" and MCHNT_ID = '").append(mchnNo).append("'");
        }
        if (isNotEmpty(request.getParameter("applyStatus"))) {// 申请状态
            whereSql += " AND APPLY_STATUS = '" + request.getParameter("applyStatus") + "' ";
        }
        String acmchntId = request.getParameter("acmchntId");
        // 收单方商户号
        if (acmchntId != null && !acmchntId.trim().equals("")) {
            whereSql += " AND (b.MAPPING_MCHNTCDONE like '%" + acmchntId + "%' ";
            whereSql += " or b.MAPPING_MCHNTCDTWO like '%" + acmchntId + "%') ";
        }
        if (isNotEmpty(startTime)) {// 时间
            whereSql += " AND CREATE_TIME >= '" + startTime.split("T")[0].replaceAll("-", "") + "000000" + "' ";
        }

        if (isNotEmpty(endTime)) {// 时间
            whereSql += " AND CREATE_TIME <= '" + endTime.split("T")[0].replaceAll("-", "") + "235959" + "' ";
        }
        where.append("  ORDER BY CREATE_TIME DESC");

        String sql = "SELECT a.APPLY_ID,a.MCHNT_ID," + "a.APPLY_TYPE,a.APPLY_STATUS,a.CREATE_TIME," + "a.CREATE_PERSON,a.USER_NAME,a.CREATE_PHONE,"
                + "a.AUDIT_TIME,a.AUDIT_ID,a.AUDIT_PHONE,"
                + "a.REMARK,b.MAPPING_MCHNTTYPEONE,b.MAPPING_MCHNTCDONE,b.MAPPING_MCHNTTYPETWO,b.MAPPING_MCHNTCDTWO  FROM TBL_MCHNT_APPLY a left join TBL_MCHT_BASE_INF b on a.MCHNT_ID = b.MCHT_NO"
                + whereSql;

        String countSql = "SELECT COUNT(*) FROM ( " + sql + where.toString() + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql + where.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 终端菜单配置
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] eposMenuMsg(int begin, HttpServletRequest request) {

        String menuLevel = request.getParameter("menuLevel");
        String brhId = request.getParameter("brhId");
        String verId = request.getParameter("verId");

        Object[] ret = new Object[2];
        StringBuffer where = new StringBuffer();
        if (brhId != null && !brhId.equals("")) {
            where.append(" and t1.BRH_ID='").append(brhId).append("'");
        }
        if (verId != null && !verId.equals("")) {
            where.append(" and t1.VER_ID='").append(verId).append("'");
        }
        if (menuLevel != null && !menuLevel.equals("")) {
            where.append(" and t1.MENU_LEVEL='").append(menuLevel).append("'");
        }

        if (menuLevel.equals("2")) {
            where.append(" and t1.MENU_PRE_ID1 = '" + request.getParameter("upMenuId1") + "'");
        } else if (menuLevel.equals("3")) {
            where.append(" and t1.MENU_PRE_ID1 = '" + request.getParameter("upMenuId1") + "'");
            where.append(" and t1.MENU_PRE_ID2 = '" + request.getParameter("upMenuId2") + "'");
        }

        where.append(" order by t1.MENU_ID");
        String sql = "SELECT t1.USAGE_KEY,t1.BRH_ID,t1.MENU_ID,t1.MENU_LEVEL,t1.MENU_MSG,"
                + "t1.MENU_PRE_ID1,t1.MENU_PRE_ID2,t1.TXN_CODE,t2.INT_TXN_CODE||'-'||t2.DSP as TXN_DSP,"
                + "t1.CON_FLAG,t1.PPT_ID,t3.ppt_id||'-'||t3.ppt_msg1 as PPT_MSG,"
                + "t1.OPR_ID,t1.VER_ID,t1.CRT_DATE,t1.UPD_DATE from tbl_menu_msg t1 "
                + "left join (select INT_TXN_CODE,DSP from TBL_TERM_TXN where mod(INT_TXN_CODE,2)=1) t2 on t1.TXN_CODE = t2.INT_TXN_CODE  "
                + "left join (select distinct VER_ID,usage_key,msg_type,ppt_id,ppt_msg1 from TBL_PPT_MSG where usage_key = '0' and msg_type = '1') t3 "
                + "on (t1.ppt_id = t3.ppt_id)  where 1 = 1 ";

        String countSql = "SELECT COUNT(1) FROM (" + sql + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql + where.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 
     * 功能描述: 商户签约账户信息查询<br>
     * 〈功能详细描述〉
     * 
     * @param begin
     * @param request
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Object[] getTblMchtSignAccInf(int begin, HttpServletRequest request) {
        StringBuffer SQL = new StringBuffer();
        SQL.append("SELECT ");
        SQL.append(
                "MCHT_NO,SIGN_ACCT,SIGN_TYPE,SIGN_STATUS,RCV_BANK_ID,RCV_BANK_NO,REC_UPD_TS,REC_CRT_TS,UPD_OPR_ID,CRT_OPR_ID,BANK_NO,BANK_NAME,BANK_ADDR,ACCT_NAME ");
        SQL.append(" FROM ");
        SQL.append("TBL_MCHT_SIGN_ACC_INF ");
        String sql = new String();
        if (!StringUtil.isNull(request.getParameter("mchtNo"))) {
            sql = "WHERE MCHT_NO='" + request.getParameter("mchtNo").trim() + "'";
        }
        SQL.append(sql);
        SQL.append(" order by REC_UPD_TS desc ");
        String countSql = "SELECT count(*) FROM TBL_MCHT_SIGN_ACC_INF " + sql;
        Object[] ret = new Object[2];
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(SQL.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 商户映射列表查询
     * 
     * @param begin
     * @param request
     * @return 2010-8-19上午09:50:00
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntMappingInfo(int begin, HttpServletRequest request) {

        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        String equipmentId = request.getParameter("equipmentId");
        String mchntId = request.getParameter("mchntId");

        String acquiresId = request.getParameter("acquiresId");
        String mappingId = request.getParameter("mappingId");
        String acmchntId = request.getParameter("acmchntId");
        String acequipmentId = request.getParameter("acequipmentId");

        Object[] ret = new Object[2];
        String whereSql = " where 1=1 ";

        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " AND BRH_ID IN " + operator.getBrhBelowId();
        }

        StringBuffer where = new StringBuffer();

        if (equipmentId != null && !equipmentId.equals("")) {
            where.append(" and EQUIPMENT_ID like '%").append(equipmentId).append("%'");
        }
        if (mappingId != null && !mappingId.equals("")) {
            where.append(" and MAPPING_ID = '").append(mappingId).append("'");
        }
        if (mchntId != null && !mchntId.equals("")) {
            where.append(" and MCHNT_ID like '%").append(mchntId).append("%'");
        }
        if (acquiresId != null && !acquiresId.equals("")) {
            where.append(" and ACQUIRERS_ID = '").append(acquiresId).append("'");
        }

        if (acmchntId != null && !acmchntId.equals("")) {
            where.append(" and ACMCHNT_ID like '%").append(acmchntId).append("%'");
        }

        if (acequipmentId != null && !acequipmentId.equals("")) {
            where.append(" and ACEQUIPMENT_ID like '%").append(acequipmentId).append("%'");
        }

        if (isNotEmpty(startTime)) {// 时间
            whereSql += " AND CREATE_TIME >= '" + startTime.split("T")[0].replaceAll("-", "") + "000000" + "' ";
        }

        if (isNotEmpty(endTime)) {// 时间
            whereSql += " AND CREATE_TIME <= '" + endTime.split("T")[0].replaceAll("-", "") + "235959" + "' ";
        }

        String sql = "SELECT a.*,c.term_name FROM TBL_MCHNT_MAP a left join TBL_OPR_INFO b on a.create_person = b.OPR_ID left join TBL_TERM_INF_TMP c on a.equipment_id = trim(c.term_id)"
                + whereSql;
        String countSql = "SELECT COUNT(*) FROM TBL_MCHNT_MAP " + whereSql;

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql + where.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql + where.toString());
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    public static void main(String[] args) {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 30);
        Date dateAfter = c.getTime();
        String dateString = CommonFunction.getOffSizeDate(CommonFunction.getCurrentDate(), "30");
        System.out.println(dateString);
        System.out.println(dateAfter);
    }

    /**
     * 查询商户申请列表信息
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntApplyInfoAll(int begin, HttpServletRequest request) {
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String whereSql = " WHERE 1=1 ";
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " AND AGR_BR IN " + InformationUtil.getBrhGroupString(operator.getOprBrhId()) + " ";
        }

        if (isNotEmpty(request.getParameter("mchntId"))) {// 商户号
            whereSql += " AND MCHNT_ID = '" + request.getParameter("mchntId") + "' ";
        }
        String acmchntId = request.getParameter("acmchntId");
        // 收单方商户号
        if (acmchntId != null && !acmchntId.trim().equals("")) {
            whereSql += " AND (b.MAPPING_MCHNTCDONE like '%" + acmchntId + "%' ";
            whereSql += " or b.MAPPING_MCHNTCDTWO like '%" + acmchntId + "%') ";
        }
        if (isNotEmpty(request.getParameter("applyName"))) {// 申请名称
            whereSql += " AND APPLY_NAME like '%" + request.getParameter("applyName") + "%' ";
        }
        if (isNotEmpty(request.getParameter("applyStatus"))) {// 申请状态
            whereSql += " AND APPLY_STATUS = '" + request.getParameter("applyStatus") + "' ";
        }
        if (isNotEmpty(request.getParameter("applyType"))) {// 申请类型
            whereSql += " AND APPLY_TYPE = '" + request.getParameter("applyType") + "' ";
        } else {
            whereSql += " AND APPLY_TYPE != '3' ";
        }

        if (isNotEmpty(startTime)) {// 时间
            whereSql += " AND CREATE_TIME >= '" + startTime.split("T")[0].replaceAll("-", "") + "000000" + "' ";
        }

        if (isNotEmpty(endTime)) {// 时间
            whereSql += " AND CREATE_TIME <= '" + endTime.split("T")[0].replaceAll("-", "") + "235959" + "' ";
        }
        Object[] ret = new Object[2];

        String sql = "SELECT a.APPLY_ID,a.MCHNT_ID,a.APPLY_TYPE,a.APPLY_STATUS,a.CREATE_TIME,a.CREATE_PERSON,a.USER_NAME,a.CREATE_PHONE,a.AUDIT_TIME,a.AUDIT_ID,a.AUDIT_PHONE,a.REMARK,b.MAPPING_MCHNTTYPEONE,b.MAPPING_MCHNTCDONE,b.MAPPING_MCHNTTYPETWO,b.MAPPING_MCHNTCDTWO  "

                + " FROM TBL_MCHNT_APPLY a left join TBL_MCHT_BASE_INF b on a.MCHNT_ID = b.MCHT_NO" + whereSql + " ORDER BY CREATE_TIME DESC";
        String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 优惠规则列表查询
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntDiscountRuleInfo(int begin, HttpServletRequest request) {
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String whereSql = " WHERE 1=1 AND STATUS=1";
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " AND BRH_ID IN " + operator.getBrhBelowId();
        }

        if (isNotEmpty(request.getParameter("discountId"))) {
            whereSql += " AND DISCOUNT_ID = '" + request.getParameter("discountId") + "' ";
        }
        if (isNotEmpty(request.getParameter("discountIdLike"))) {
            whereSql += " AND DISCOUNT_ID like '%" + request.getParameter("discountIdLike") + "%' ";
        }
        if (isNotEmpty(request.getParameter("discountCode"))) {
            whereSql += " AND DISCOUNT_CODE like '%" + request.getParameter("discountCode") + "%' ";
        }
        if (isNotEmpty(request.getParameter("discountType"))) {
            whereSql += " AND DISCOUNT_TYPE = '" + request.getParameter("discountType") + "' ";
        }

        if (isNotEmpty(request.getParameter("cardbin"))) {
            whereSql += " AND DISCOUNT_CARD like '%" + request.getParameter("cardbin") + "%' ";
        }
        if (isNotEmpty(startTime)) {// 时间
            whereSql += " AND CREATE_TIME >= '" + startTime.split("T")[0].replaceAll("-", "") + "000000" + "' ";
        }

        if (isNotEmpty(endTime)) {// 时间
            whereSql += " AND CREATE_TIME <= '" + endTime.split("T")[0].replaceAll("-", "") + "235959" + "' ";
        }
        Object[] ret = new Object[2];

        String sql = "SELECT DISCOUNT_ID,DISCOUNT_TYPE,DISCOUNT_CARD,DISCOUNT_VALUE,DISCOUNT_VALUE_INFO,CREATE_TIME,CREATE_PERSON,START_TIME,END_TIME,DISCOUNT_CODE"
                + ",OPEN_TYPE,IS_DOWNLOAD,OPEN_LIAN,ACQUIRERS_TYPE,REMARK,ISSUED_CONTENT,ACQUIRERS_ID,SUMCOUNT0,SUMCOUNT1,SUMCOUNT0l,SUMCOUNT1l "
                + " FROM TBL_MCHNT_DISCOUNTRULE a left join TBL_OPR_INFO b on a.create_person = b.OPR_ID" + whereSql + " ORDER BY DISCOUNT_ID DESC";
        String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 优惠规则绑定列表查询
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntDiscountRuleBindInfo(int begin, HttpServletRequest request) {

        String whereSql = " WHERE 1=1 ";
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " AND BRH_ID IN " + operator.getBrhBelowId();
        }

        if (isNotEmpty(request.getParameter("bindId"))) {
            whereSql += " AND a.BIND_ID = '" + request.getParameter("bindId") + "' ";
        }
        if (isNotEmpty(request.getParameter("mchntId"))) {
            whereSql += " AND a.MCHNT_ID like '%" + request.getParameter("mchntId") + "%' ";
        }

        if (isNotEmpty(request.getParameter("equipmentId"))) {
            whereSql += " AND a.EQUIPMENT_ID like '%" + request.getParameter("equipmentId") + "%' ";
        }
        // 商户地址
        if (isNotEmpty(request.getParameter("mchntAddress"))) {
            whereSql += " AND e.addr like '%" + request.getParameter("mchntAddress") + "%' ";
        }

        if (isNotEmpty(request.getParameter("acquirersId"))) {
            whereSql += " AND a.ACQUIRERS_ID = '" + request.getParameter("acquirersId") + "' ";
        }

        if (isNotEmpty(request.getParameter("discountId"))) {
            whereSql += " AND a.DISCOUNT_ID = '" + request.getParameter("discountId") + "' ";
        }

        if (isNotEmpty(request.getParameter("acquirersType"))) {
            whereSql += " AND a.ACQUIRERS_TYPE = '" + request.getParameter("acquirersType") + "' ";
        }

        if (isNotEmpty(request.getParameter("isOpen"))) {
            whereSql += " AND a.IS_OPEN = '" + request.getParameter("isOpen") + "' ";
        }

        Object[] ret = new Object[2];

        String sql = "SELECT a.BIND_ID,a.MCHNT_ID,a.EQUIPMENT_ID,a.ACQUIRERS_ID,a.DISCOUNT_ID,a.CREATE_TIME,a.MCHNT_NAME,a.DISCOUNT_VALUE_INFO,a.ACTIVITY_ID,a.ACTIVITY_DESC,a.IS_OPEN,a.CREATE_PERSON"
                + ",a.START_TIME,a.END_TIME,a.ACQUIRERS_TYPE,a.DISCOUNT_VALUE "

                + " FROM TBL_MCHNT_DISCOUNTRULE_BIND a left join TBL_OPR_INFO b on a.CREATE_PERSON = b.OPR_ID "
                + " left join tbl_mcht_base_inf e on (" + " (a.MCHNT_ID=e.MAPPING_MCHNTTYPEONE and A .ACQUIRERS_ID = E .MAPPING_MCHNTTYPEONE)"
                + " OR(A .MCHNT_ID = E .MAPPING_MCHNTCDTWO AND A .ACQUIRERS_ID = E .MAPPING_MCHNTTYPETWO)" + " )" + whereSql
                + " ORDER BY A.CREATE_TIME DESC";
        String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 手续费配置信息查询
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMchntFeeInfo(int begin, HttpServletRequest request) {

        String whereSql = " WHERE 1=1 ";
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " AND AGR_BR IN " + InformationUtil.getBrhGroupString(operator.getOprBrhId()) + " ";
        }

        if (isNotEmpty(request.getParameter("mchntId"))) {
            whereSql += " AND MCHNT_ID = '" + request.getParameter("mchntId") + "' ";
        }
        String acmchntId = request.getParameter("acmchntId");
        // 收单方商户号
        if (acmchntId != null && !acmchntId.trim().equals("")) {
            whereSql += " AND (MAPPING_MCHNTCDONE like '%" + acmchntId + "%' ";
            whereSql += " or MAPPING_MCHNTCDTWO like '%" + acmchntId + "%') ";
        }

        Object[] ret = new Object[2];

        String sql = "SELECT MCHNT_ID,FEE_TYPE,FEE_RATE,FEE_MIN,FEE_MAX,CREATE_TIME,UPDATE_TIME,OPERATOR "
                + ",MAPPING_MCHNTTYPEONE,MAPPING_MCHNTCDONE,MAPPING_MCHNTTYPETWO,MAPPING_MCHNTCDTWO "

                + " FROM TBL_MCHNT_FEE a left join TBL_MCHT_BASE_INF b on a.MCHNT_ID = b.MCHT_NO" + whereSql + " ORDER BY CREATE_TIME DESC";
        String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 交易统计信息查询
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTxnSummaryInfo(int begin, HttpServletRequest request) {

        String whereSql = " WHERE 1=1 ";
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql += " AND AGR_BR IN " + InformationUtil.getBrhGroupString(operator.getOprBrhId()) + " ";
        }

        if (isNotEmpty(request.getParameter("mchntId"))) {
            whereSql += " AND m.MERCHANT_CODE like '%" + request.getParameter("mchntId") + "%' ";
        }

        if (isNotEmpty(request.getParameter("termId"))) {
            whereSql += " AND m.TERMINAL_CODE like '%" + request.getParameter("termId") + "%' ";
        }

        String table_name = "";
        String selectValue = "";
        if (isNotEmpty(request.getParameter("timeType")) && isNotEmpty(request.getParameter("txnType"))) {

            String timeType = request.getParameter("timeType");
            String txnType = request.getParameter("txnType");

            if ("1".equals(timeType)) {// 按日 交易统计
                if ("M".equals(txnType)) {// 按商户
                    table_name = "TB_STL_ACOMA_M_SUMMARY_DAY";
                    selectValue = "m.settle_date," + "m.MERCHANT_CODE," + "'' as TERMINAL_CODE," + "m.TRANS_AMOUNT," + "m.CUPS_TRANS_FEE,"
                            + "m.LOCAL_TRANS_FEE ";
                } else if ("T".equals(txnType)) {// 按终端
                    table_name = "TB_STL_ACOMA_MT_SUMMARY_DAY";
                    selectValue = "m.settle_date," + "m.MERCHANT_CODE," + "m.TERMINAL_CODE," + "m.TRANS_AMOUNT," + "m.CUPS_TRANS_FEE,"
                            + "m.LOCAL_TRANS_FEE ";
                }

                if (isNotEmpty(request.getParameter("txnDate"))) {
                    String[] txnDateArr = request.getParameter("txnDate").split(",");
                    StringBuffer buf = new StringBuffer();
                    for (int i = 0; i < txnDateArr.length; i++) {
                        buf.append(txnDateArr[i]).append(",");
                        if (i == txnDateArr.length - 1) {
                            buf.append(txnDateArr[i]);
                        }
                    }
                    whereSql += " AND m.SETTLE_DATE in(" + buf.toString() + ")";
                }

            } else if ("2".equals(timeType)) {// 按月 交易统计
                if ("M".equals(txnType)) {// 按商户
                    table_name = "TB_STL_ACOMA_MT_SUMMARY_MONTH";
                    selectValue = "m.settle_month as settle_date," + "m.MERCHANT_CODE," + "'' as TERMINAL_CODE," + "m.TRANS_AMOUNT,"
                            + "m.CUPS_TRANS_FEE," + "m.LOCAL_TRANS_FEE ";
                } else if ("T".equals(txnType)) {// 按终端
                    table_name = "TB_STL_ACOMA_M_SUMMARY_MONTH";
                    selectValue = "m.settle_month as settle_date," + "m.MERCHANT_CODE," + "m.TERMINAL_CODE," + "m.TRANS_AMOUNT," + "m.CUPS_TRANS_FEE,"
                            + "m.LOCAL_TRANS_FEE ";
                }

                if (isNotEmpty(request.getParameter("txnDate"))) {
                    String[] txnDateArr = request.getParameter("txnDate").split(",");
                    StringBuffer buf = new StringBuffer();
                    for (int i = 0; i < txnDateArr.length; i++) {
                        buf.append(txnDateArr[i]).append(",");
                        if (i == txnDateArr.length - 1) {
                            buf.append(txnDateArr[i]);
                        }
                    }
                    whereSql += " AND m.settle_month in(" + buf.toString() + ")";
                }
            }
        }

        Object[] ret = new Object[2];

        String sql = "SELECT " + selectValue + " FROM " + table_name + " m" + " left join TBL_MCHT_BASE_INF b on m.MERCHANT_CODE=b.MCHT_NO" + whereSql
                + "order by SETTLE_DATE DESC";
        String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 交易统计详细信息查询
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTxnSummaryInfoDetail(int begin, HttpServletRequest request) {

        String whereSql = " WHERE 1=1 ";
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

        if (isNotEmpty(request.getParameter("terminalCode"))) {
            whereSql += " AND TERMINAL_CODE = '" + request.getParameter("terminalCode") + "' ";
            if (isNotEmpty(request.getParameter("settleDate"))) {
                String dateParam = request.getParameter("settleDate");
                if (dateParam.length() == 8) {
                    whereSql += " AND SETTLE_DATE = '" + request.getParameter("settleDate") + "' ";
                } else {
                    whereSql += " AND substr(SETTLE_DATE,1,6) = '" + request.getParameter("settleDate") + "' ";
                }
            }
        } else {
            whereSql += " AND MERCHANT_CODE = '" + request.getParameter("merchantCode") + "' ";
            if (isNotEmpty(request.getParameter("settleDate"))) {
                String dateParam = request.getParameter("settleDate");
                if (dateParam.length() == 8) {
                    whereSql += " AND SETTLE_DATE = '" + request.getParameter("settleDate") + "' ";
                } else {
                    whereSql += " AND substr(SETTLE_DATE,1,6) = '" + request.getParameter("settleDate") + "' ";
                }
            }
        }

        Object[] ret = new Object[2];

        String sql = "SELECT POS_SERIAL,MERCHANT_CODE,TERMINAL_CODE,TRANS_TIME,MASTER_ACCOUNT,TRANS_AMOUNT,CUPS_TRANS_FEE,SETTLE_DATE,MERCHANT_TYPE,RESP_CODE,CHECK_STATE"
                + " FROM TB_STL_ACOMA " + whereSql + "order by SETTLE_DATE DESC";
        String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        request.getSession().setAttribute("txnSummaryList", dataList);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static Object[] getBrhParamInfo(int begin, HttpServletRequest request) {

        StringBuilder whereSql = new StringBuilder();
        whereSql.append(" WHERE 1=1 ");
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (!"0000".equals(operator.getOprBrhId())) {
            whereSql.append(" AND a.BRH_ID IN " + InformationUtil.getBrhGroupString(operator.getOprBrhId()) + " ");
        }
        if (isNotEmpty(request.getParameter("brhId"))) {
            whereSql.append(" AND a.BRH_ID = '" + request.getParameter("brhId") + "' ");
        }
        if (isNotEmpty(request.getParameter("brhName"))) {
            whereSql.append(" AND b.BRH_NAME  like '%" + request.getParameter("brhName").trim() + "%' ");
        }
        Object[] ret = new Object[2];
        String sql = "select a.BRH_ID, b.BRH_NAME, a.STAT_DATE, a.STD_TOALCOUNTS, a.STD_TOALMONEY,a.STD_CONDITION"
                + " FROM TBL_BRH_PARAM a  left join TBL_BRH_INFO b on a.BRH_ID = b.BRH_ID " + whereSql + " order by b.brh_level asc,a.brh_id ";
        String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 获取达标统计信息
     * 
     * @param begin
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getStdCountInfo(int begin, HttpServletRequest request) throws Exception {
        List<Object[]> dataList = new ArrayList<Object[]>();
        String count = "0";
        StringBuilder sb = new StringBuilder();
        Object[] ret = new Object[2];

        String brhId, date, statDate;
        String startDate = "", endDate = "";
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        if (isNotEmpty(request.getParameter("brhId"))) {
            brhId = request.getParameter("brhId");
        } else {
            brhId = operator.getOprBrhId();
        }
        if (isNotEmpty(request.getParameter("date"))) {
            date = request.getParameter("date");
        } else {
            date = new SimpleDateFormat("yyyyMM").format(new Date());
        }
        // 1.根据机构号查询机构参数信息
        List<String> brhParamList = CommonFunction.getCommQueryDAO()
                .findBySQLQuery("select t.stat_date from tbl_brh_param t where t.brh_id = '" + brhId + "'");
        if (brhParamList == null || brhParamList.get(0) == null) {
            ret[0] = dataList;
            ret[1] = count;
            request.getSession().setAttribute("stdCountInfoList", dataList);
            return ret;
        }
        statDate = brhParamList.get(0).trim();
        statDate = (statDate.length() < 2) ? "0" + statDate : statDate;
        startDate = date + statDate;
        endDate = CommonFunction.addMonthToString(date, "yyyyMM", 1) + statDate;
        // 2.根据查询条件组织查询语句

        sb.append("select mcht_nm,                                                                           ");
        sb.append("       card_accp_id,                                                                      ");
        sb.append("       card_accp_term_id,                                                                 ");
        sb.append("       trans_cnt,                                                                         ");
        sb.append("       amt_trans,                                                                         ");
        sb.append("       amt_settlmt,                                                                       ");
        sb.append("       panNums,                                                                           ");
        sb.append("       case                                                                               ");
        sb.append("         when (amt_trans = 0 and Trans_Cnt = 0 ) then                                     ");
        sb.append("          '不达标'                                                                          ");
        sb.append("         when (std_condition = '01') and (amt_trans >= std_toalmoney) then                ");
        sb.append("          '达标'                                                                           ");
        sb.append("         when (std_condition = '02') and (Trans_Cnt >= std_toalcounts) then               ");
        sb.append("          '达标'                                                                           ");
        sb.append("         when (std_condition = '03') and                                                  ");
        sb.append("              (Trans_Cnt >= std_toalcounts or amt_trans >= std_toalmoney) then            ");
        sb.append("          '达标'                                                                           ");
        sb.append("         when (std_condition = '04') and (Trans_Cnt >= std_toalcounts) and                ");
        sb.append("              (amt_trans >= std_toalmoney) then                                           ");
        sb.append("          '达标'                                                                           ");
        sb.append("         else                                                                             ");
        sb.append("          '不达标'                                                                          ");
        sb.append("       end as reachsdt ,                                                                  ");
        sb.append("      '" + startDate + "'   as startDate ,         ");
        sb.append("      '" + endDate + "'     as endDate ,           ");
        sb.append("       addr,                                                                              ");
        sb.append("       brh_name                                                                           ");
        sb.append("  from (select t1.mcht_nm as mcht_nm,                                                     ");
        sb.append("               t4.card_accp_id as card_accp_id,                                           ");
        sb.append("               t3.brh_name as brh_name,                                                   ");
        sb.append("               t1.addr as addr,                                                           ");
        sb.append("               t4.card_accp_term_id as card_accp_term_id,                                 ");
        sb.append("               t5.stat_date as stat_date,                                                 ");
        sb.append("               t5.std_toalcounts as std_toalcounts,                                       ");
        sb.append("               to_number(t5.std_toalmoney) / 100 as std_toalmoney,                        ");
        sb.append("               count(inst_date) as Trans_Cnt,                                             ");
        sb.append("               sum(to_number(t4.amt_trans) / 100) as amt_trans,                           ");
        sb.append("               nvl(sum(to_number(t4.amt_settlmt) / 100),0) as amt_settlmt,                ");
        sb.append("               count(distinct(t4.pan)) as panNums,                                        ");
        sb.append("               t5.std_condition std_condition                                             ");
        sb.append("          from tbl_mcht_base_inf t1,                                                      ");
        sb.append("               tbl_term_inf      t2,                                                      ");
        sb.append("               tbl_brh_info      t3,                                                      ");
        sb.append("               tbl_n_txn         t4,                                                      ");
        sb.append("               tbl_brh_Param     t5                                                       ");
        sb.append("         where t1.agr_br = t3.brh_id                                                      ");
        sb.append("           and t5.brh_id = t3.brh_id                                                      ");
        sb.append("           and t2.mapping_mchntcdtwo = t1.mapping_mchntcdtwo                              ");
        sb.append("           and t2.mapping_termidtwo = t4.card_accp_term_id                                ");
        sb.append("           and t4.trans_type = 'P'                                                        ");
        sb.append("           and revsal_flag = '0'                                                          ");
        sb.append("           and t4.resp_code = '00'                                                        ");
        sb.append("           and t4.inst_date >=  '" + startDate + "'");
        sb.append("           and t4.inst_date < '" + endDate + "'");
        sb.append("           and t5.brh_id = '" + brhId + "' ");
        sb.append("         group by mcht_nm,                                                                ");
        sb.append("                  card_accp_id,                                                           ");
        sb.append("                  brh_name,                                                               ");
        sb.append("                  addr,                                                                   ");
        sb.append("                  card_accp_term_id,                                                      ");
        sb.append("                  stat_date,                                                              ");
        sb.append("                  std_toalcounts,                                                         ");
        sb.append("                  std_toalmoney,                                                          ");
        sb.append("                  std_condition                                                           ");
        sb.append("        union all                                                 ");
        sb.append("           select t1.mcht_nm as mcht_nm,                          ");
        sb.append("                  t2.mapping_mchntcdtwo as card_accp_id,          ");
        sb.append("                  t3.brh_name as brh_name,                        ");
        sb.append("                  t1.addr as addr,                                ");
        sb.append("                  t2.mapping_termidtwo as card_accp_term_id,      ");
        sb.append("                  t5.stat_date as stat_date,                      ");
        sb.append("                  t5.std_toalcounts as std_toalcounts,            ");
        sb.append("                  0 std_toalmoney,                                ");
        sb.append("                  0 Trans_Cnt,                                    ");
        sb.append("                  0 amt_trans,                                    ");
        sb.append("                  0 amt_settlmt,                                  ");
        sb.append("                  0 panNums,                                      ");
        sb.append("                  t5.std_condition std_condition                  ");
        sb.append("             from tbl_mcht_base_inf t1,                           ");
        sb.append("                  tbl_term_inf      t2,                           ");
        sb.append("                  tbl_brh_info      t3,                           ");
        sb.append("                  tbl_brh_Param     t5                            ");
        sb.append("            where t1.agr_br = t3.brh_id                           ");
        sb.append("              and t5.brh_id = t3.brh_id                           ");
        sb.append("              and t2.mapping_mchntcdtwo = t1.mapping_mchntcdtwo   ");
        sb.append("              and t5.brh_id = '" + brhId + "' ");
        sb.append("              and t2.mapping_termidtwo not in                     ");
        sb.append("                  (select card_accp_term_id                       ");
        sb.append("                     from tbl_n_txn t4                            ");
        sb.append("                    where t4.trans_type = 'P'                     ");
        sb.append("                      and t4.revsal_flag = '0'                    ");
        sb.append("                      and t4.resp_code = '00'                     ");
        sb.append("                      and t4.inst_date >=  '" + startDate + "'");
        sb.append("                      and t4.inst_date < '" + endDate + "'");
        sb.append("                    )                                             ");
        sb.append("            group by mcht_nm,                                     ");
        sb.append("                     t2.mapping_mchntcdtwo,                       ");
        sb.append("                     brh_name,                                    ");
        sb.append("                     addr,                                        ");
        sb.append("                     mapping_termidtwo,                           ");
        sb.append("                     stat_date,                                   ");
        sb.append("                     std_toalcounts,                              ");
        sb.append("                     std_toalmoney,                               ");
        sb.append("                     std_condition                                ");
        sb.append("         )                                                        ");
        sb.append("         order by brh_name                                        ");

        String countSql = "SELECT COUNT(*) FROM (" + sb.toString() + ")";
        dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        //        request.getSession().setAttribute("stdCountInfoList", CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString()));
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        for (Object[] objArr : dataList) {
            if (objArr[8] != null && !objArr[8].equals("")) {
                Date parse = sdf.parse(objArr[8].toString());
                objArr[8] = sdf2.format(parse);
            }
            if (objArr[9] != null && !objArr[9].equals("")) {
                Date parse = sdf.parse(objArr[9].toString());
                objArr[9] = sdf2.format(parse);
            }
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 交易查询（商户）
     * @author lixiaomin
     */
    @SuppressWarnings("unchecked")
    public static Object[] getDealTxnInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String mchtName = request.getParameter("mchtName");
        String brhName = StringUtils.trim(request.getParameter("agrBr"));
        String marketerName = request.getParameter("marketerName");
        String cityName = request.getParameter("cityName");
        String orderFee = request.getParameter("orderFee");
        String dateStart = StringUtils.trim(request.getParameter("startDate"));
        String dateEnd = StringUtils.trim(request.getParameter("endDate"));
        // 查询条件组串
        StringBuffer whereSql = new StringBuffer();

        if (StringUtils.isNotEmpty(dateStart)) {
            whereSql.append(" AND substr(V_TEST.ORDER_TIME,0,8) >= '" + dateStart + "'");
        }

        if (StringUtils.isNotEmpty(dateEnd)) {
            whereSql.append(" AND substr(V_TEST.ORDER_TIME,0,8) <= '" + dateEnd + "'");
        }

        // 商户名称
        if (StringUtils.isNotEmpty(mchtName)) {
            whereSql.append(" and trim(Yl_MCHNT_NO) like '%" + mchtName + "%' ");
        }
        // 所属机构
        if (StringUtils.isNotEmpty(brhName)) {
            whereSql.append(
                    " AND  trim(AGR_BR) in (SELECT DISTINCT agr_br FROM V_TEST START WITH UP_BRH_ID ='" + brhName
                            + "' CONNECT BY PRIOR agr_br = UP_BRH_ID" + "  union all " + "select DISTINCT agr_br from V_TEST where agr_br='" + brhName
                            + "')");
        } else {
            whereSql.append(" and AGR_BR in " + orgArrStr);
        }
        //营销员
        if (StringUtils.isNotEmpty(marketerName)) {
            whereSql.append(" AND  trim(MARKETER_NAME) ='" + marketerName + "'");
        }
        //地区名称
        if (StringUtils.isNotEmpty(cityName)) {
            whereSql.append(" AND  trim(settle_area_no) ='" + cityName + "'");
        }
        //金额
        if (StringUtils.isNotEmpty(orderFee)) {
            whereSql.append(" AND  ( AMT) >='" + orderFee + "'");
        }
        StringBuffer sb = new StringBuffer();
        sb.append(
                " select CITY_NAME,BRH_NAME,MCHT_NM,Yl_MCHNT_NO,MARKETER_NAME,COALESCE(sum(AMT),0) as TOTAL_FEE,count(*) as TOTAL_NUM,"
                        + "COALESCE(sum(case when PAY_TYPE=TRIM('0') then AMT end),0) as WECHAT_TOTAL_FEE,count( case when PAY_TYPE=TRIM('0') then 1 end) as WECHAT_TOTAL_NUM,"
                        + "COALESCE(sum(case when PAY_TYPE=TRIM('1') then AMT end),0) as ALIPAY_TOTAL_FEE,count( case when PAY_TYPE=TRIM('1') then 1 end) as ALIPAY_TOTAL_NUM,"
                        + "COALESCE(sum(case when PAY_TYPE=TRIM('P') then AMT end),0) as BANK_CARD_FEE,count( case when PAY_TYPE=TRIM('P') then 1 end) as BANK_CARD_NUM,"
                        + "COALESCE(sum(case when PAY_TYPE not in ('0','1','P') then AMT end),0) as OTHER_FEE,count(case when PAY_TYPE not in ('0','1','P') then 1 end) as OTHER_NUM"
                        + "  from V_TEST  where 1= 1 ");
        sb.append(whereSql);
        sb.append("   GROUP  by CITY_NAME,BRH_NAME,MCHT_NM,MARKETER_NAME,Yl_MCHNT_NO");
        sb.append(" order by CITY_NAME");
        String countSql = "SELECT COUNT(1) FROM (" + sb.toString() + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

        ret[0] = dataList;
        ret[1] = count;
        return ret;
        // return CommonFunction.getSqlQueryObjectArray(sb.toString(), begin);
    }

    /**
     * 交易查询（终端）
     * @author lixiaomin
     */
    @SuppressWarnings("unchecked")
    public static Object[] getDealMchtTxnInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String mchtName = request.getParameter("mchtName");
        String brhName = StringUtils.trim(request.getParameter("agrBr"));
        String marketerName = request.getParameter("marketerName");
        String cityName = request.getParameter("cityName");
        String orderFee = request.getParameter("orderFee");
        String dateStart = StringUtils.trim(request.getParameter("startDate"));
        String dateEnd = StringUtils.trim(request.getParameter("endDate"));
        // 查询条件组串
        StringBuffer whereSql = new StringBuffer();

        if (StringUtils.isNotEmpty(dateStart)) {
            whereSql.append(" AND substr(V_TEST.ORDER_TIME,0,8) >= '" + dateStart + "'");
        }

        if (StringUtils.isNotEmpty(dateEnd)) {
            whereSql.append(" AND substr(V_TEST.ORDER_TIME,0,8) <= '" + dateEnd + "'");
        }

        // 商户名称
        if (StringUtils.isNotEmpty(mchtName)) {
            whereSql.append(" AND trim(Yl_MCHNT_NO) like '%" + mchtName + "%' ");
        }
        // 所属机构
        if (StringUtils.isNotEmpty(brhName)) {
            whereSql.append(
                    " AND  trim(AGR_BR) in (SELECT DISTINCT agr_br FROM V_TEST START WITH UP_BRH_ID ='" + brhName
                            + "' CONNECT BY PRIOR agr_br = UP_BRH_ID" + "  union all " + "select DISTINCT agr_br from V_TEST where agr_br='" + brhName
                            + "')");
        } else {
            whereSql.append(" and AGR_BR in " + orgArrStr);
        }
        //营销员
        if (StringUtils.isNotEmpty(marketerName)) {
            whereSql.append(" AND  trim(MARKETER_NAME) ='" + marketerName + "'");
        }
        //地区名称
        if (StringUtils.isNotEmpty(cityName)) {
            whereSql.append(" AND  trim(settle_area_no) ='" + cityName + "'");
        }
        //金额
        if (StringUtils.isNotEmpty(orderFee)) {
            whereSql.append(" AND  ( AMT) >='" + orderFee + "'");
        }
        StringBuffer sb = new StringBuffer();

        sb.append(
                " select CITY_NAME,BRH_NAME,MCHT_NM,Yl_MCHNT_NO,Yl_TERM_NO,MARKETER_NAME,COALESCE(sum(AMT),0) as TOTAL_FEE,count(*) as TOTAL_NUM,"
                        + "COALESCE(sum(case when PAY_TYPE=TRIM('0') then AMT end),0) as WECHAT_TOTAL_FEE,count( case when PAY_TYPE=TRIM('0') then 1 end) as WECHAT_TOTAL_NUM,"
                        + "COALESCE(sum(case when PAY_TYPE=TRIM('1') then AMT end),0) as ALIPAY_TOTAL_FEE,count( case when PAY_TYPE=TRIM('1') then 1 end) as ALIPAY_TOTAL_NUM,"
                        + "COALESCE(sum(case when PAY_TYPE=TRIM('P') then AMT end),0) as BANK_CARD_FEE,count( case when PAY_TYPE=TRIM('P') then 1 end) as BANK_CARD_NUM,"
                        + "COALESCE(sum(case when PAY_TYPE not in ('0','1','P') then AMT end),0) as OTHER_FEE,count(case when PAY_TYPE not in ('0','1','P') then 1 end) as OTHER_NUM"
                        + "  from V_TEST  where 1= 1 ");
        sb.append(whereSql);
        sb.append("   GROUP  by CITY_NAME,BRH_NAME,MCHT_NM,MARKETER_NAME,Yl_MCHNT_NO,YL_TERM_NO");
        sb.append(" order by CITY_NAME");
        String countSql = "SELECT COUNT(1) FROM (" + sb.toString() + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

        ret[0] = dataList;
        ret[1] = count;
        return ret;
        //   return CommonFunction.getSqlQueryObjectArray(sb.toString(), begin);
    }

    /**
     * 支付宝对账查询
     * @author lixiaomin
     */
    @SuppressWarnings("unchecked")
    public static Object[] getAlipayAccount(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String appId = request.getParameter("appId");
        String outTradeNo = request.getParameter("outTradeNo");
        String tradeNo = request.getParameter("tradeNo");
        String cmchntBrh = request.getParameter("cmchntBrh");
        String status = request.getParameter("status");
        String startPayTime = request.getParameter("startPayTime");
        String endPayTime = request.getParameter("endPayTime");

        // 查询条件组串
        StringBuffer whereSql = new StringBuffer();

        // 应用ID
        if (StringUtils.isNotEmpty(appId)) {
            whereSql.append(" AND trim(APPID) like '%" + appId + "%'");
        }

        // 支付宝交易号
        if (StringUtils.isNotEmpty(outTradeNo)) {
            whereSql.append(" AND trim(OUTTRADENO) like '%" + outTradeNo + "%'");
        }

        // 商户订单号
        if (StringUtils.isNotEmpty(tradeNo)) {
            whereSql.append(" AND trim(TRADENO) like '%" + tradeNo + "%'");
        }

        // 第三方支付机构
        if (StringUtils.isNotEmpty(cmchntBrh)) {
            whereSql.append(" AND  trim(CMCHNTBRH) ='" + cmchntBrh + "'");
        }

        // 对账状态
        if (StringUtils.isNotEmpty(status)) {
            whereSql.append(" AND  trim(STATUS) ='" + status + "'");
        }

        // 支付完成开始时间
        if (StringUtils.isNotEmpty(startPayTime)) {
            whereSql.append(" AND  substr(PAYTIME,0,8)  >= '" + startPayTime + "'");
        }

        // 支付完成结束时间
        if (StringUtils.isNotEmpty(endPayTime)) {
            whereSql.append(" AND  substr(PAYTIME,0,8) <='" + endPayTime + "'");
        }

        StringBuffer sb = new StringBuffer();
        sb.append(
                " SELECT APPID,OUTTRADENO,TRADENO,TRADETYPE,GOODNAME,STORECODE,STORENAME,OPERATOR,TERMNO,PAYACCOUNT,TOTALFEE,REALFEE,"
                        + "ALIPAYBONUS,POINTBONUS,ALIPAYPREFFEE,MCHNTPREFEE,VOUCHFEE,VOUCHNAME,MCHTCOSTBONUS,CARDCOSTFEE,BATCHNO,SERVFEES,"
                        + "DISTRIPROFITS,REMARK,brh.C_BRH_NAME,CRETIME,PAYTIME,STATUS,CHKMARK,CHKTIME "
                        + "FROM T_ALIPAYACCOUNT left join T_BRH_DATADIC brh on CMCHNTBRH=brh.CORD_BRH_ID where 1= 1  ");
        sb.append(whereSql);
        sb.append(" order by CRETIME desc,PAYTIME desc");
        String countSql = "SELECT COUNT(1) FROM (" + sb.toString() + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        for (Object[] objArr : dataList) {
            if (objArr[27].toString().equals("0")) {
                objArr[27] = "未对账";
            } else if (objArr[27].toString().equals("1")) {
                objArr[27] = "对账成功";
            } else if (objArr[27].toString().equals("2")) {
                objArr[27] = "对账异常";
            } else if (objArr[27].toString().equals("3")) {
                objArr[27] = "对账不一致";
            }
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 微信对账查询
     * @author lixiaomin
     */
    @SuppressWarnings("unchecked")
    public static Object[] getWeChatAccount(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String appId = request.getParameter("appId");
        String outTradeNo = request.getParameter("outTradeNo");
        String tradeNo = request.getParameter("tradeNo");
        String cmchntBrh = request.getParameter("cmchntBrh");
        String status = request.getParameter("status");
        String startPayTime = request.getParameter("startPayTime");
        String endPayTime = request.getParameter("endPayTime");

        // 查询条件组串
        StringBuffer whereSql = new StringBuffer();

        // 公众账号ID
        if (StringUtils.isNotEmpty(appId)) {
            whereSql.append(" AND trim(APPID) like '%" + appId + "%'");
        }

        // 微信订单号
        if (StringUtils.isNotEmpty(outTradeNo)) {
            whereSql.append(" AND trim(OUTTRADENO) like '%" + outTradeNo + "%'");
        }

        // 商户订单号
        if (StringUtils.isNotEmpty(tradeNo)) {
            whereSql.append(" AND trim(TRADENO) like '%" + tradeNo + "%'");
        }

        // 第三方支付机构
        if (StringUtils.isNotEmpty(cmchntBrh)) {
            whereSql.append(" AND  trim(CMCHNTBRH) ='" + cmchntBrh + "'");
        }

        // 对账状态
        if (StringUtils.isNotEmpty(status)) {
            whereSql.append(" AND  trim(STATUS) ='" + status + "'");
        }

        // 交易开始时间
        if (StringUtils.isNotEmpty(startPayTime)) {
            whereSql.append(" AND  substr(PAYTIME,0,8)  >= '" + startPayTime + "'");
        }

        // 交易结束时间
        if (StringUtils.isNotEmpty(endPayTime)) {
            whereSql.append(" AND  substr(PAYTIME,0,8) <='" + endPayTime + "'");
        }

        StringBuffer sb = new StringBuffer();
        sb.append(
                " SELECT APPID,OUTTRADENO,TRADENO,MCHID,SUBMCHID,USERFLAG,TRADETYPE,TRADESTATUS,PAYBANK,CURRYTYPE,TOTALFEE,ENTERBONS,"
                        + "WECHATRFDNO,REFUNDNO,REFUNDFEE,ENTERRFDBONS,REFUNDTYPE,REFUNDSTATUS,GOODNAME,PACKGE,FEE,FEERATE,SN,brh.C_BRH_NAME,PAYTIME,"
                        + "STATUS,CHKMARK,CHKTIME FROM T_WECHATACCOUNT left join T_BRH_DATADIC brh on CMCHNTBRH=brh.CORD_BRH_ID  where 1= 1  ");
        sb.append(whereSql);
        sb.append(" order by PAYTIME desc");
        String countSql = "SELECT COUNT(1) FROM (" + sb.toString() + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        for (Object[] objArr : dataList) {
            if (objArr[25].toString().equals("0")) {
                objArr[25] = "未对账";
            } else if (objArr[25].toString().equals("1")) {
                objArr[25] = "对账成功";
            } else if (objArr[25].toString().equals("2")) {
                objArr[25] = "对账异常";
            } else if (objArr[25].toString().equals("3")) {
                objArr[25] = "对账不一致";
            }
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 民生商户信息维护
     * @author lixiaomin
     */
    @SuppressWarnings("unchecked")
    public static Object[] getmchntMSInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String outMchntId = request.getParameter("outMchntId");
        String mchntName = request.getParameter("mchntName");
        String mchntFullName = request.getParameter("mchntFullName");
        String cmbcMchntId = request.getParameter("cmbcMchntId");

        // 查询条件组串
        StringBuffer whereSql = new StringBuffer();
        // 外部商户号
        if (StringUtils.isNotEmpty(outMchntId)) {
            whereSql.append(" AND tms.OUTMCHNTID like '%" + outMchntId + "%'");
        }
        // 商户简称
        if (StringUtils.isNotEmpty(mchntName)) {
            whereSql.append(" AND tms.MCHNTNAME like '%" + mchntName + "%'");
        }
        // 商户全称
        if (StringUtils.isNotEmpty(mchntFullName)) {
            whereSql.append(" AND  tms.MCHNTFULLNAME like '%" + mchntFullName + "%'");
        }
        // 民生商户号
        if (StringUtils.isNotEmpty(cmbcMchntId)) {
            whereSql.append(" AND  tms.CMBCMCHNTID like '%" + cmbcMchntId + "%'");
        }

        //whereSql.append(" and AGR_BR in " + orgArrStr);
        StringBuffer sb = new StringBuffer();

        sb.append(
                " SELECT tms.OUTMCHNTID,tms.OPERID,tms.MCHNTNAME,tms.MCHNTFULLNAME,tms.DEVTYPE,tms.ACDCODE,tms.PROVINCE,tms.CITY,tms.ADDRESS,tms.ISCERT,"
                        + "tms.LICID,tms.IDTCARD,tms.CORPNAME,tms.TELEPHONE,tms.AUTOSETTLE,tms.STATUS,tms.CMBCMCHNTID,tms.MESSAGE FROM T_MS_MERCHNT tms where 1=1 ");
        sb.append(whereSql);
        sb.append(" order by tms.OUTMCHNTID");
        String countSql = "SELECT COUNT(1) FROM (" + sb.toString() + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        for (Object[] objArr : dataList) {
            if (objArr[4].toString().equals("1")) {
                objArr[4] = "神思电子";
            } else if (objArr[4].toString().equals("2")) {
                objArr[4] = "民生银行";
            }
            if (objArr[9].toString().equals("0")) {
                objArr[9] = "非持证商户";
            } else if (objArr[9].toString().equals("1")) {
                objArr[9] = "持证商户";
            }
            if ("1".equals(objArr[14].toString())) {
                objArr[14] = "自动结算";
            } else if ("2".equals(objArr[14].toString())) {
                objArr[14] = "手工提现";
            }
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 民生支付渠道维护
     * @author lixiaomin
     */
    @SuppressWarnings("unchecked")
    public static Object[] getpayChannelInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String outMchntId = request.getParameter("outMchntId");
        String cmbcMchntId = request.getParameter("cmbcMchntId");
        String apiCode = request.getParameter("apiCode");

        // 查询条件组串
        StringBuffer whereSql = new StringBuffer();

        // 外部商户号
        if (StringUtils.isNotEmpty(outMchntId)) {
            whereSql.append(" AND OUTMCHNTID like '%" + outMchntId + "%'");
        }

        // 民生商户号
        if (StringUtils.isNotEmpty(cmbcMchntId)) {
            whereSql.append(" AND CMBCMCHNTID like '%" + cmbcMchntId + "%'");
        }

        // 支付通道
        if (StringUtils.isNotEmpty(apiCode)) {
            whereSql.append(" AND  trim(APICODE) ='" + apiCode + "'");
        }

        StringBuffer sb = new StringBuffer();
        sb.append(
                " SELECT OUTMCHNTID,OPERID,CMBCMCHNTID,APICODE,INDUSTRYID,OPERATETYPE,DAYLIMIT,MONTHLIMIT,"
                        + "RATEFLAG,FEERATE,ACCOUNT,PBCBANKID,ACCTNAME,ACCTTYPE,CMBCSIGNID,CREATE_TIME,MODIFY_TIME,MESSAGE from T_MS_PAYCHANNEL where 1= 1 ");
        sb.append(whereSql);
        sb.append(" order by OUTMCHNTID");
        String countSql = "SELECT COUNT(1) FROM (" + sb.toString() + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        for (Object[] objArr : dataList) {
            if (objArr[3].toString().equals("0005")) {
                objArr[3] = "微信";
            } else if (objArr[3].toString().equals("0007")) {
                objArr[3] = "支付宝";
            } else if (objArr[3].toString().equals("0008")) {
                objArr[3] = "QQ钱包";
            }
            if (objArr[5].toString().equals("1")) {
                objArr[5] = "直联";
            } else if (objArr[5].toString().equals("2")) {
                objArr[5] = "间联";
            }
            if (objArr[8].toString().equals("0")) {
                objArr[8] = "比例费率";
            } else if (objArr[8].toString().equals("1")) {
                objArr[8] = "特殊费率";
            }
            if (objArr[13].toString().equals("1")) {
                objArr[13] = "对私";
            } else if (objArr[13].toString().equals("2")) {
                objArr[13] = "对公";
            }
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 微信、支付宝渠道维护
     * @author lixiaomin
     */
    @SuppressWarnings("unchecked")
    public static Object[] getAirpayMapInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String ylMchntNo = request.getParameter("YL_MCHNT_NO");
        String ylTermNo = request.getParameter("YL_TERM_NO");
        String payFlag = request.getParameter("PAY_FLAG");

        // 查询条件组串
        StringBuffer whereSql = new StringBuffer();

        // 银联商户号
        if (StringUtils.isNotEmpty(ylMchntNo)) {
            whereSql.append(" AND trim(YL_MCHNT_NO) like '%" + ylMchntNo + "%'");
        }

        // 银联终端号
        if (StringUtils.isNotEmpty(ylTermNo)) {
            whereSql.append(" AND trim(YL_TERM_NO) like '%" + ylTermNo + "%'");
        }

        // 支付方式
        if (StringUtils.isNotEmpty(payFlag)) {
            whereSql.append(" AND  trim(PAY_FLAG) ='" + payFlag + "'");
        }

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT YL_MCHNT_NO,YL_TERM_NO,APP_ID,PAY_FLAG,MCH_ID,C_KEY1,C_KEY2,SIGN_TYPE,FLAG,AUTHORITY_FLAG " + "from T_AIRPAY_MAP where 1= 1 ");
        sb.append(whereSql);
        sb.append(" order by YL_MCHNT_NO");
        String countSql = "SELECT COUNT(1) FROM (" + sb.toString() + ")";
        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        for (Object[] objArr : dataList) {
            if (objArr[3].toString().equals("0")) {
                objArr[3] = "微信";
            } else if (objArr[3].toString().equals("1")) {
                objArr[3] = "支付宝";
            }
            if (objArr[8].toString().equals("0")) {
                objArr[8] = "停用";
            } else if (objArr[8].toString().equals("1")) {
                objArr[8] = "启用";
            }
            if(objArr[9] != null){
            	if (objArr[9].toString().equals("0")) {
                    objArr[9] = "否";
                } else if (objArr[9].toString().equals("1")) {
                    objArr[9] = "是";
                }
            }
            
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }

    /**
     * 国密终端密钥维护
     * @author lixiaomin
     * @throws Exception 
     * @throws UnsupportedEncodingException 
     */
    @SuppressWarnings("unchecked")
    public static Object[] getTermKeyInf(int begin, HttpServletRequest request) throws UnsupportedEncodingException, Exception {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String ylMchntNo = request.getParameter("YL_MCHNT_NO");
        String ylTermNo = request.getParameter("YL_TERM_NO");

        // 查询条件组串
        StringBuffer whereSql = new StringBuffer();

        // 银联商户号
        if (StringUtils.isNotEmpty(ylMchntNo)) {
            whereSql.append(" AND trim(YL_MCHNT_NO) like '%" + ylMchntNo + "%'");
        }

        // 银联终端号
        if (StringUtils.isNotEmpty(ylTermNo)) {
            whereSql.append(" AND trim(YL_TERM_NO) like '%" + ylTermNo + "%'");
        }

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT YL_MCHNT_NO,YL_TERM_NO,C_KEY FROM T_TERM_KEY WHERE 1=1 ");
        sb.append(whereSql);
        sb.append(" order by YL_MCHNT_NO");
        String countSql = "SELECT COUNT(1) FROM (" + sb.toString() + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);
        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        StringBuffer sb1 = new StringBuffer();
        sb1.append(" select ID,PARAM from T_KEY_PARAM where ID='1' ");
        List<Object[]> dataList1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sb1.toString(), begin, Constants.QUERY_RECORD_COUNT);
        Object[] objArr1 = dataList1.get(0);
        String encryKey = objArr1[1].toString();
        System.out.println("encryKey:" + encryKey);
        for (Object[] objArr : dataList) {
            String key = CommonUtil.encryKey(objArr[1].toString(), encryKey);
            byte[] hexStringToByteArray = CommonUtil.hexStringToByteArray(objArr[2].toString());
            byte[] decrySm4 = CommonUtil.decrySm4(hexStringToByteArray, key.getBytes("UTF-8"));
            String strToHexStr = CommonUtil.strToHexStr(decrySm4);
            objArr[2] = strToHexStr;
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }
    //支付宝分润统计（明细）
    @SuppressWarnings("unchecked")
    public static Object[] getAlipayShareInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String merchantNo = request.getParameter("merchantNo");
        String brhId = request.getParameter("brhId");
        String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
        // 综合查询条件组串
        StringBuffer whereSql = new StringBuffer();

        //商户号
        if (StringUtils.isNotEmpty(merchantNo)) {
            whereSql.append(" and m.YL_MCHNT_NO like '%"+ merchantNo +"%' ");
        }
        //归属机构
        if (StringUtils.isNotEmpty(brhId)) {
            whereSql.append(" and t.BRH_ID = '"+ brhId +"' ");
        }
        //时间        
        if(StringUtils.isNotEmpty(startDate)){
        	whereSql.append(" and substr(ORDER_TIME,0,6) >= '"+ startDate +"' ");
        }
        if(StringUtils.isNotEmpty(endDate)){
        	whereSql.append(" and substr(ORDER_TIME,0,6) <= '"+ endDate +"' ");
        }

        StringBuffer sb = new StringBuffer();
        sb.append(" select tbl.ORDER_TIME,tmcht.MCHT_NM,tmcht.AGR_BR as SETTLE_AREA_NO,tbl.Yl_MCHNT_NO,tbl.YL_TERM_NO,tbl.TERM_ORDER_ID,tbl.INFO_ID,tbl.ORDER_FEE,tbl.ORDER_FEE as ORG_FEE, "
                + "tpt.PAY_TYPE,tbl.NOTICE_STATUS,tbl.C_MCHNT_NO,NOTICE_ORDER_ID,brh.C_BRH_NAME,tbl.ORDER_RESP_TIME,tmcht.AGR_BR,tbr.BRH_NAME,tbl.SELLERID,tbl.BUYERID,tbl.SELLER_EMAIL,tbl.BUYER_LOGON_ID, "
                + "case when tmsi.SETTLE_ACCT_NM = '1' then ((to_number(tbl.SINGLE_SHARE_MONEY))*(to_number(tmsi.SETTLE_BANK_NM_SND)/100)) when tmsi.SETTLE_ACCT_NM = '0' then to_number(tmsi.SETTLE_BANK_NM_SND)  else null  end as MCHT_SHARE, "
                + "case when tmsi.SETTLE_ACCT_NM = '1' then ((to_number(tbl.SINGLE_SHARE_MONEY))*(to_number(tmsi.SETTLE_BANK_NO_SND)/100)) when tmsi.SETTLE_ACCT_NM = '0' then to_number(tmsi.SETTLE_BANK_NO_SND) else null  end as ONEORG_SHARE, "
                + "case when tmsi.SETTLE_ACCT_NM = '1' then ((to_number(tbl.SINGLE_SHARE_MONEY))*(to_number(tmsi.SETTLE_ACCT_NM_SND)/100)) when tmsi.SETTLE_ACCT_NM = '0' then to_number(tmsi.SETTLE_ACCT_NM_SND) else null  end as TWOORG_SHARE, "
                + "case when tmsi.SETTLE_ACCT_NM = '1' then ((to_number(tbl.SINGLE_SHARE_MONEY))*(to_number(tmsi.SETTLE_ACCT_SND)/100)) when tmsi.SETTLE_ACCT_NM = '0' then to_number(tmsi.SETTLE_ACCT_SND) else null  end as THREEORG_SHARE "
                + " from (select h.* ,g.SINGLE_SHARE_MONEY from T_CORDERINFO h, "
                + "(select f.BRH_ID,f.SINGLE_SHARE_MONEY,e.YL_MCHNT_NO, e.YL_TERM_NO, min(e.ORDER_TIME) as ORDER_TIME, e.BUYERID,f.share_ruleid from T_CORDERINFO e,TBL_ALIPAY_SHARE_PARAM z,( "
                + "select a.* from(select substr(m.ORDER_TIME,0,6) as ORDER_TIME,t.BRH_ID,m.YL_MCHNT_NO, m.YL_TERM_NO,count(m.ORDER_FEE) as total, n.SINGLE_SHARE_MONEY,n.share_ruleid as share_ruleid "
                + "from T_CORDERINFO m, TBL_ALIPAY_SHARE_PARAM n, TBL_BRH_INFO t, TBL_MCHT_BASE_INF k "
                + "where m.YL_MCHNT_NO = k.MAPPING_MCHNTCDTWO "
                + "and k.AGR_BR = t.BRH_ID "
                + "and n.BRH_ID = t.BRH_ID "
                + "and m.NOTICE_STATUS = '1' "
                + "and m.NOTICE_PAY_TYPE = '1' "
                +"and m.share_ruleid=n.share_ruleid "
                + "and m.ORDER_FEE/100 >= n.SHARE_THRESHOLD ");
        		sb.append(whereSql);
        		sb.append(" group by substr(m.ORDER_TIME,0,6),t.BRH_ID,m.YL_MCHNT_NO, m.YL_TERM_NO, n.SINGLE_SHARE_MONEY,n.share_ruleid) a, TBL_ALIPAY_SHARE_PARAM b, TBL_BRH_INFO c, TBL_MCHT_BASE_INF d "
                + "where a.YL_MCHNT_NO = d.MAPPING_MCHNTCDTWO "
                + "and d.AGR_BR = c.BRH_ID "
                + "and b.BRH_ID = c.BRH_ID "
                + "and a.share_ruleid=b.share_ruleid "
                + "and a.total >= b.SHARE_STANDARD_TOTAL) f "
                + "where e.YL_MCHNT_NO = f.YL_MCHNT_NO "
                + "and e.NOTICE_STATUS = '1' "
                + "and e.NOTICE_PAY_TYPE = '1' "
                + "and e.ORDER_FEE/100 >= z.SHARE_THRESHOLD "
                + "and substr(e.ORDER_TIME,0,6) = f.ORDER_TIME"
                + " group by substr(e.ORDER_TIME,0,6),f.BRH_ID,e.YL_MCHNT_NO, e.YL_TERM_NO,  e.BUYERID, f.SINGLE_SHARE_MONEY,f.share_ruleid ) g "
                + " where h.YL_MCHNT_NO = g.YL_MCHNT_NO and h.YL_TERM_NO = g.YL_TERM_NO and h.ORDER_TIME = g.ORDER_TIME and h.BUYERID = g.BUYERID) tbl "
                + "left join TBL_MCHT_BASE_INF tmcht  on tbl.YL_MCHNT_NO = tmcht.MAPPING_MCHNTCDTWO "
                + "left join TBL_BRH_INFO tbr  on tmcht.AGR_BR = tbr.BRH_ID "
                + "left join T_PAY_TYPEDIC tpt on tbl.NOTICE_PAY_TYPE=tpt.INFO_ID "
                + "left join T_BRH_DATADIC brh on tbl.C_MCHNT_BRH=brh.CORD_BRH_ID "
                + "left join (select tal.share_ruleid as share_ruleid,tal.sharetype as SETTLE_ACCT_NM,tal.mcht_shareval as settle_bank_nm_snd," 
                +"tal.orgshareval_first as settle_bank_no_snd,tal.orgshareval_second as settle_acct_nm_snd,"  
                +"tal.orgshareval_three as settle_acct_snd,tal.freeratio as acct_settle_limit "  
                +"from TBL_ALIPAY_SHARE_PARAM tal) tmsi  on tmsi.share_ruleid = tbl.share_ruleid  where tbr.BRH_ID in " + orgArrStr
                + " and (tmsi.SETTLE_ACCT_NM = '1' or tmsi.SETTLE_ACCT_NM = '0')"
                + "order by tbl.ORDER_TIME desc ");

        String countSql = "SELECT COUNT(1) FROM (" + sb.toString() + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        DecimalFormat df = new DecimalFormat("0.00");
        for (int i = 0; i < dataList.size(); i++) {
            Object[] obj = dataList.get(i);
            try {
                if (obj[0] != null && !obj[0].equals("")) {
                    Date tm = sdf.parse(obj[0].toString());
                    obj[0] = sdf2.format(tm);
                }
                if (obj[7] != null && !obj[7].equals("")) {
                    obj[7] = CommonFunction.transFenToYuan(obj[7].toString());
                } else {
                    obj[7] = CommonFunction.transFenToYuan("");
                }
                if (obj[8] != null && !obj[8].equals("")) {
                    obj[8] = CommonFunction.transFenToYuan(obj[8].toString());
                } else {
                    obj[8] = CommonFunction.transFenToYuan("");
                }
                if (obj[10] == null || obj[10].equals("")) {
                    obj[10] = "";
                } else if (obj[10].equals("1")) {
                    obj[10] = "成功";
                } else if (obj[10].equals("0")) {
                    obj[10] = "失败";
                } else if (obj[10].equals("2")) {
                    if (obj[7] != null && !obj[7].equals("") && !obj[7].equals("0")) {
                        obj[10] = "暂未支付";
                    } else {
                        obj[10] = "退款";
                    }
                } else if (obj[10].equals("3")) {
                    obj[10] = "退款成功";
                } else {
                    obj[10] = "";
                }
                if (obj[14] != null && !obj[14].equals("")) {
                    Date tm = sdf.parse(obj[14].toString());
                    obj[14] = sdf2.format(tm);
                }
                if (obj[21] != null && !obj[21].equals("")) {
                	if("0.00".equals(df.format(obj[21]))){
                		obj[21]="";
                	}else{
                		obj[21] = df.format(obj[21]);
                	}
                }
                if (obj[22] != null && !obj[22].equals("")) {
                	if("0.00".equals(df.format(obj[22]))){
                		obj[22]="";
                	}else{
                		obj[22] = df.format(obj[22]);
                	}
                }
                if (obj[23] != null && !obj[23].equals("")) {
                	if("0.00".equals(df.format(obj[23]))){
                		obj[23]="";
                	}else{
                		obj[23] = df.format(obj[23]);
                	}
                }
                if (obj[24] != null && !obj[24].equals("")) {
                	if("0.00".equals(df.format(obj[24]))){
                		obj[24]="";
                	}else{
                		obj[24] = df.format(obj[24]);
                	}
                }
                dataList.set(i, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }
    
    //支付宝分润统计（汇总）
    @SuppressWarnings("unchecked")
    public static Object[] getAlipayShareInfo2(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String merchantNo = request.getParameter("merchantNo");
        String brhId = request.getParameter("brhId");
        String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		// 综合查询条件组串
        StringBuffer whereSql = new StringBuffer();

        //商户号
        if (StringUtils.isNotEmpty(merchantNo)) {
            whereSql.append(" and m.YL_MCHNT_NO like '%"+ merchantNo +"%' ");
        }
        //归属机构
        if (StringUtils.isNotEmpty(brhId)) {
            whereSql.append(" and t.BRH_ID = '"+ brhId +"' ");
        }
        //时间        
        if(StringUtils.isNotEmpty(startDate)){
        	whereSql.append(" and substr(ORDER_TIME,0,6) >= '"+ startDate +"' ");
        }
        if(StringUtils.isNotEmpty(endDate)){
        	whereSql.append(" and substr(ORDER_TIME,0,6) <= '"+ endDate +"' ");
        }

        StringBuffer sb = new StringBuffer();
        sb.append("select substr(tbl.ORDER_TIME,0,6), tmcht.MCHT_NM,tmcht.AGR_BR as SETTLE_AREA_NO,tbl.Yl_MCHNT_NO,tbl.YL_TERM_NO,sum(tbl.ORDER_FEE) as ORDER_FEE,sum(tbl.ORDER_FEE) as ORG_FEE,"
                + "case when tmsi.SETTLE_ACCT_NM = '1' then sum(round(((to_number(tbl.SINGLE_SHARE_MONEY))*(to_number(tmsi.SETTLE_BANK_NM_SND)/100)),2)) when tmsi.SETTLE_ACCT_NM = '0' then sum(to_number(tmsi.SETTLE_BANK_NM_SND))  else null  end as MCHT_SHARE,"
                + "case when tmsi.SETTLE_ACCT_NM = '1' then sum(round(((to_number(tbl.SINGLE_SHARE_MONEY))*(to_number(tmsi.SETTLE_BANK_NO_SND)/100)),2)) when tmsi.SETTLE_ACCT_NM = '0' then sum(to_number(tmsi.SETTLE_BANK_NO_SND)) else null  end as ONEORG_SHARE, "
                + "case when tmsi.SETTLE_ACCT_NM = '1' then sum(round(((to_number(tbl.SINGLE_SHARE_MONEY))*(to_number(tmsi.SETTLE_ACCT_NM_SND)/100)),2)) when tmsi.SETTLE_ACCT_NM = '0' then sum(to_number(tmsi.SETTLE_ACCT_NM_SND)) else null  end as TWOORG_SHARE, "
                + "case when tmsi.SETTLE_ACCT_NM = '1' then sum(round(((to_number(tbl.SINGLE_SHARE_MONEY))*(to_number(tmsi.SETTLE_ACCT_SND)/100)),2)) when tmsi.SETTLE_ACCT_NM = '0' then sum(to_number(tmsi.SETTLE_ACCT_SND)) else null  end as THREEORG_SHARE "
                + " from (select h.*, g.SINGLE_SHARE_MONEY from T_CORDERINFO h, "
                + "(select f.BRH_ID,f.SINGLE_SHARE_MONEY,e.YL_MCHNT_NO, e.YL_TERM_NO, min(e.ORDER_TIME) as ORDER_TIME, e.BUYERID,f.share_ruleid from T_CORDERINFO e,TBL_ALIPAY_SHARE_PARAM z,( "
                + "select a.* from(select substr(m.ORDER_TIME,0,6) as ORDER_TIME,t.BRH_ID,m.YL_MCHNT_NO, m.YL_TERM_NO,count(m.ORDER_FEE) as total, n.SINGLE_SHARE_MONEY,n.share_ruleid as share_ruleid "
                + "from T_CORDERINFO m, TBL_ALIPAY_SHARE_PARAM n, TBL_BRH_INFO t, TBL_MCHT_BASE_INF k "
                + "where m.YL_MCHNT_NO = k.MAPPING_MCHNTCDTWO "
                + "and k.AGR_BR = t.BRH_ID "
                //+ "and n.BRH_ID = t.BRH_ID "
                + "and m.NOTICE_STATUS = '1' "
                + "and m.NOTICE_PAY_TYPE = '1' "
                + "and m.ORDER_FEE/100 >= n.SHARE_THRESHOLD and m.share_ruleid=n.share_ruleid ");
        		sb.append(whereSql);
        		sb.append(" group by substr(m.ORDER_TIME,0,6),t.BRH_ID,m.YL_MCHNT_NO, m.YL_TERM_NO, n.SINGLE_SHARE_MONEY,n.share_ruleid) a, TBL_ALIPAY_SHARE_PARAM b, TBL_BRH_INFO c, TBL_MCHT_BASE_INF d "
                + "where a.YL_MCHNT_NO = d.MAPPING_MCHNTCDTWO "
                + "and d.AGR_BR = c.BRH_ID "
                + "and b.BRH_ID = c.BRH_ID "
                +"and a.share_ruleid=b.share_ruleid "
                + "and a.total >= b.SHARE_STANDARD_TOTAL) f "
                + "where e.YL_MCHNT_NO = f.YL_MCHNT_NO "
                + "and e.NOTICE_STATUS = '1' "
                + "and e.NOTICE_PAY_TYPE = '1' "
                
                + "and e.ORDER_FEE/100 >= z.SHARE_THRESHOLD "
                + "and substr(e.ORDER_TIME,0,6) = f.ORDER_TIME"
                + " group by substr(e.ORDER_TIME,0,6),f.BRH_ID,e.YL_MCHNT_NO, e.YL_TERM_NO,e.BUYERID,f.SINGLE_SHARE_MONEY,f.share_ruleid) g "
                + " where h.YL_MCHNT_NO = g.YL_MCHNT_NO and h.YL_TERM_NO = g.YL_TERM_NO and h.ORDER_TIME = g.ORDER_TIME) tbl "
                + "left join TBL_MCHT_BASE_INF tmcht  on tbl.YL_MCHNT_NO = tmcht.MAPPING_MCHNTCDTWO "
                + "left join TBL_BRH_INFO tbr  on tmcht.AGR_BR = tbr.BRH_ID "
//                + "left join T_PAY_TYPEDIC tpt on tbl.NOTICE_PAY_TYPE=tpt.INFO_ID "
//                + "left join T_BRH_DATADIC brh on tbl.C_MCHNT_BRH=brh.CORD_BRH_ID "
                + "left join (select tal.share_ruleid as share_ruleid,tal.sharetype as SETTLE_ACCT_NM,tal.mcht_shareval as settle_bank_nm_snd," 
                +"tal.orgshareval_first as settle_bank_no_snd,tal.orgshareval_second as settle_acct_nm_snd,"  
                +"tal.orgshareval_three as settle_acct_snd,tal.freeratio as acct_settle_limit "  
                +"from TBL_ALIPAY_SHARE_PARAM tal) tmsi  on tmsi.share_ruleid = tbl.share_ruleid where tbr.BRH_ID in " + orgArrStr
                + " and (tmsi.SETTLE_ACCT_NM = '1' or tmsi.SETTLE_ACCT_NM = '0')"
                + " group by substr(tbl.ORDER_TIME,0,6), tbl.YL_TERM_NO,tbl.Yl_MCHNT_NO,tmcht.AGR_BR,tmcht.MCHT_NM,tmsi.SETTLE_ACCT_NM order by substr(tbl.ORDER_TIME,0,6)"
                );

        String countSql = "SELECT COUNT(1) FROM (" + sb.toString() + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        DecimalFormat df = new DecimalFormat("0.00");
        for (int i = 0; i < dataList.size(); i++) {
            Object[] obj = dataList.get(i);
            try {
                if (obj[5] != null && !obj[5].equals("")) {
                    obj[5] = CommonFunction.transFenToYuan(obj[5].toString());
                    
                } else {
                    obj[5] = CommonFunction.transFenToYuan("");
                }
                if (obj[6] != null && !obj[6].equals("")) {
                    obj[6] = CommonFunction.transFenToYuan(obj[6].toString());
                } else {
                    obj[6] = CommonFunction.transFenToYuan("");
                }
                if (obj[7] != null && !obj[7].equals("")) {
                	if("0.00".equals(df.format(obj[7]))){
                		obj[7]="";
                	}else{
                		obj[7] = df.format(obj[7]);
                	}
                }
                if (obj[8] != null && !obj[8].equals("")) {
                	if("0.00".equals(df.format(obj[8]))){
                		obj[8]="";
                	}else{
                		obj[8] = df.format(obj[8]);
                	}
                }
                if (obj[9] != null && !obj[9].equals("")) {
                	if("0.00".equals(df.format(obj[9]))){
                		obj[9]="";
                	}else{
                		obj[9] = df.format(obj[9]);
                	}
                }
                if (obj[10] != null && !obj[10].equals("")) {
                	if("0.00".equals(df.format(obj[10]))){
                		obj[10]="";
                	}else{
                		obj[10] = df.format(obj[10]);
                	}
                }
                dataList.set(i, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }
    
    /**
     * 手续费统计
     
     */
    @SuppressWarnings("unchecked")
    public static Object[] getFeeStaInfo(int begin, HttpServletRequest request) {
        Object[] ret = new Object[2];
        Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
        if (operator == null) {
            return ret;
        }
        String orgArrStr = operator.getBrhBelowId();
        String merchantNo = request.getParameter("merchantNo");
        String brhId = request.getParameter("brhId");
        String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
        // 查询条件组串
        StringBuffer whereSql = new StringBuffer();

        //银联的商户号
        if (StringUtils.isNotEmpty(merchantNo)) {
            whereSql.append(" and TRIM(tcord.Yl_MCHNT_NO) like '%" + merchantNo + "%'");
        }
        //归属机构
        if (StringUtils.isNotEmpty(brhId)) {
            whereSql.append(" AND TRIM(tmcht.AGR_BR)='" + brhId + "'");
        }
        
        //时间        
        if(StringUtils.isNotEmpty(startDate)){
        	whereSql.append(" and substr(tcord.ORDER_TIME,0,6) >= '"+ startDate +"' ");
        }
        if(StringUtils.isNotEmpty(endDate)){
        	whereSql.append(" and substr(tcord.ORDER_TIME,0,6) <= '"+ endDate +"' ");
        }

        StringBuffer sb = new StringBuffer();
        sb.append(
                " select  substr(tbl.ORDER_TIME,0,6) as ORDER_TIME,tbl.MCHT_NM,tbl.AGR_BR as SETTLE_AREA_NO,tbl.Yl_MCHNT_NO,sum(tbl.FEE) as FEE "
                        + " from ( select tcord.Yl_MCHNT_NO,tcord.ORDER_TIME,tmcht.AGR_BR,tmcht.MCHT_NM,tcord.share_ruleid, " 
                        + " case when tcord.NOTICE_STATUS = '1' and tmsi.ACCT_SETTLE_LIMIT is not null then (tmsi.ACCT_SETTLE_LIMIT/100) * (tcord.ORDER_FEE/100) else null  end AS FEE from T_CORDERINFO tcord left join TBL_MCHT_BASE_INF tmcht  on ( tcord.YL_MCHNT_NO = tmcht.MAPPING_MCHNTCDTWO ) "
                        + " LEFT JOIN (	select tal.share_ruleid as share_ruleid,tal.sharetype as SETTLE_ACCT_NM,tal.mcht_shareval as settle_bank_nm_snd," 
                        +"tal.orgshareval_first as settle_bank_no_snd,tal.orgshareval_second as settle_acct_nm_snd,"  
                        +"tal.orgshareval_three as settle_acct_snd,tal.freeratio as acct_settle_limit "  
                        +"from TBL_ALIPAY_SHARE_PARAM tal ) tmsi ON tmsi.share_ruleid = tcord.share_ruleid "
                        + " where TRIM(tcord.NOTICE_STATUS) = '1'   and tcord.NOTICE_PAY_TYPE IN ( '1','5') and tmsi.ACCT_SETTLE_LIMIT IS NOT NULL  and tcord.ORDER_FEE is not null ");
        sb.append(whereSql);
        sb.append(
                " ) tbl left join TBL_BRH_INFO tbr  on tbl.AGR_BR = tbr.BRH_ID  "
                        + " where tbr.BRH_ID in " + orgArrStr
                        + " group by  substr(tbl.ORDER_TIME,0,6),tbl.MCHT_NM,tbl.AGR_BR,tbl.Yl_MCHNT_NO"
                        + " order by substr(tbl.ORDER_TIME,0,6) desc");

        String countSql = "SELECT COUNT(1) FROM (" + sb.toString() + ")";

        List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sb.toString(), begin, Constants.QUERY_RECORD_COUNT);

        String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
        DecimalFormat df = new DecimalFormat("0.00");
        for (int i = 0; i < dataList.size(); i++) {
            Object[] obj = dataList.get(i);
            try {
            	 if (obj[4] != null && !obj[4].equals("")) {
                 	if("0.00".equals(df.format(obj[4]))){
                 		obj[4]="";
                 	}else{
                 		obj[4] = df.format(obj[4]);
                 	}
                 }
            	 dataList.set(i, obj);
            }catch(Exception e){
            	e.printStackTrace();
            }
        }
        ret[0] = dataList;
        ret[1] = count;
        return ret;
    }
}
