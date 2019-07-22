package com.rail.bo.impl.api.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.rail.bo.tools.TSendMsgSocketBO;
import org.springframework.expression.spel.ast.OpNE;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.common.StringUtil;
import com.huateng.po.TblBrhInfo;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.access.T120101BO;
import com.rail.bo.api.app.ApiAppBO;
import com.rail.bo.enums.ApiSendMsgFlagEnums;
import com.rail.bo.enums.BrhLevelEnums;
import com.rail.bo.enums.DelStatusEnums;
import com.rail.bo.enums.EmployeeInfoSignEnums;
import com.rail.bo.enums.InOutEnums;
import com.rail.bo.enums.InWhseEnums;
import com.rail.bo.enums.InfoSignEnums;
import com.rail.bo.enums.InfoSignEnums2;
import com.rail.bo.enums.InfoSignInOutEnums;
import com.rail.bo.enums.InfoSignRailWorkEmployeeEnums;
import com.rail.bo.enums.InfoSignRailWorkToolEnums;
import com.rail.bo.enums.InfoSignToolTransferEnums;
import com.rail.bo.enums.MoreOrderLessSignEnums;
import com.rail.bo.enums.OpenSignEnums;
import com.rail.bo.enums.WorkStatusEnums;
import com.rail.bo.enums.WorkWarnTypeEnums;
import com.rail.bo.pub.PubSearch;
import com.rail.bo.pub.ResultUtils;
import com.rail.bo.tools.T110100BO;
import com.rail.po.access.RailAccessEmployee;
import com.rail.po.access.RailAccessInfo;
import com.rail.po.access.RailAccessOptlog;
import com.rail.po.access.RailAccessTool;
import com.rail.po.access.RailAccessWarn;
import com.rail.po.base.RailEmployee;
import com.rail.po.base.RailTeam;
import com.rail.po.org.RailConstOrg;
import com.rail.po.org.RailFormOrg;
import com.rail.po.tool.RailToolInfo;
import com.rail.po.tool.RailToolName;
import com.rail.po.tool.RailToolProp;
import com.rail.po.vo.ApiAllTollsVo;
import com.rail.po.vo.ApiRailWorkAccessOpenVo;
import com.rail.po.vo.RailEmployeeVo;
import com.rail.po.vo.RailEmployeeVo2;
import com.rail.po.vo.RailToolInfoVo;
import com.rail.po.vo.RailToolInfoVo2;
import com.rail.po.vo.RailWorkInfoVo;
import com.rail.po.warehouse.RailWhseEmployee;
import com.rail.po.warehouse.RailWhseTool;
import com.rail.po.work.RailWorkEmployee;
import com.rail.po.work.RailWorkInfo;
import com.rail.po.work.RailWorkTool;
import com.rail.po.work.RailWorkWarn;
import com.rail.zWebSocket.PubUtil;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.org.apache.xpath.internal.objects.XString;

/**  
* @author syl
* @version 创建时间：2019年4月15日 上午9:23:37  
* @desc
*/
public class ApiAppBOTarget extends HibernateDaoSupport implements ApiAppBO {

	@Override
	public List apiAppActionAllToolsImgList(String equipCode)  {
		String sql_depts = "select t.ACCESS_DEPT    from RAIL_ACCESS_INFO t  "
				+ "left join RAIL_ACCESS_EQUIP_INFO  t2 on t.ACCESS_CODE   = t2.ACCESS_CODE"
				+ " where t2.EQUIP_CODE ='"+equipCode+"'";
		List<String> list_depts= CommonFunction.getCommQueryDAO().findBySQLQuery(sql_depts);
		if(list_depts == null || list_depts.size()==0){
			return null;
		}
		String dept = list_depts.get(0);
//		list_depts  = getDeptList(list_depts,dept);
//		StringBuffer where = new StringBuffer();
//		where.append(" where t.dept in (");
//		for(String item :list_depts){
//			where.append(item+",");
//		}
//		where.append("-1)");
		String where = getWhere(list_depts,dept);

		List<ApiAllTollsVo> allTollsVos  = new ArrayList<ApiAllTollsVo>();
		//查询出所有名称 
		String hsql22 =" from  RailToolName t where  t.delStatus  = 0  ";
		List<RailToolName> listNames = this.getHibernateTemplate().find(hsql22);
		for(RailToolName item:listNames){
			ApiAllTollsVo allTollsVo = new ApiAllTollsVo();
			allTollsVo.setToolImg(item.getToolImg());
			allTollsVo.setToolName(item.getToolName());
			allTollsVo.setToolNameId(String.valueOf(item.getId()));
			//组织名称中的工具和标签号
			String sqltool = "select t.tool_code,t.rfid ,t.STAND||'架'||t.floor||'层'||t.position||'位' as location from rail_tool_info t   " +
					"where t.TOOL_STATUS=0 and t.DEL_STATUS = '"+DelStatusEnums.NO.getCode()+"' and  t.tool_name='"+item.getId()+"' " +
					"and t.dept in  "+where;
			List<Object[]> list_tool_info = CommonFunction.getCommQueryDAO().findBySQLQuery(sqltool);
			List<Object> rfids = new ArrayList<Object>() ;
			List<Object> toolCodes = new ArrayList<Object>() ;
			List<Object> locations = new ArrayList<Object>() ;
			for(Object[] o : list_tool_info){
				toolCodes.add(o[0]);
				rfids.add(o[1]);
				locations.add(o[2]);
			}
			allTollsVo.setRfids(rfids);
			allTollsVo.setToolCodes(toolCodes);
			allTollsVo.setLocations(locations);
			allTollsVos.add(allTollsVo);
		}
		return allTollsVos;
	}

	/**
	 *
	 * @param list_depts_all   空的部门列表 或者父级的 只有一个参数
	 * @param dept
	 * @return
	 */
	@Override
	public   String getWhere(List<String> list_depts_all,String dept){
		list_depts_all  = getDeptList(list_depts_all,dept);
		StringBuffer where = new StringBuffer();
//		where.append(" where t.dept in (");
		where.append("   (");
		for(String item :list_depts_all){
			where.append(item+",");
		}
		where.append("-1)");
		return where.toString();
	}
	public   List getDeptList(List list_depts_all,String detp){
		TblBrhInfo brhInfo = this.getHibernateTemplate().get(TblBrhInfo.class, detp);
		List<TblBrhInfo> listBrhInfos ;
		String hsql;
		if(Integer.valueOf(brhInfo.getBrhLevel()) == BrhLevelEnums.ZRO.getCode()){
			hsql = "from TblBrhInfo t where t.LevelOne = '"+brhInfo.getId()+"'";
		}else if(Integer.valueOf(brhInfo.getBrhLevel()) == BrhLevelEnums.ONE.getCode()){
			hsql = "from TblBrhInfo t where t.LevelTwo = '"+brhInfo.getId()+"'";
		}else if(Integer.valueOf(brhInfo.getBrhLevel()) == BrhLevelEnums.TWO.getCode()){
			hsql = "from TblBrhInfo t where t.LevelThr = '"+brhInfo.getId()+"'";
		}else{
			hsql = "from TblBrhInfo t where t.LevelFou = '"+brhInfo.getId()+"'";
		}
		listBrhInfos = this.getHibernateTemplate().find(hsql);
		for(TblBrhInfo item:listBrhInfos){
			list_depts_all.add(item.getId());
		}
		return list_depts_all;
	}
	
	@Override
	public List apiAppActionAllEmplayeeImgList(Map<String, String> params)   {
		String equipCode = params.get("equipCode");
		String  whereequipCode = "";
		if(!StringUtil.isEmpty(equipCode)){
			whereequipCode =" where t2.EQUIP_CODE ="+equipCode;
		}
		
		String sql_depts = "select t.ACCESS_DEPT    from RAIL_ACCESS_INFO t "
				+ " left join RAIL_ACCESS_EQUIP_INFO  t2 on t.ACCESS_CODE   = t2.ACCESS_CODE "+whereequipCode;
		List<String> list_depts= CommonFunction.getCommQueryDAO().findBySQLQuery(sql_depts);
		String deptCode = list_depts.get(0);

		StringBuffer where = new StringBuffer();
		where.append("where t.dept in (");
		if(null!=list_depts && list_depts.size()>0){
			list_depts = getDeptList(list_depts,deptCode);
			for(String id :list_depts){
				where.append(id+",");
			}
		}
		where.append("-1)");
		
		String hql = "select new com.rail.po.vo.RailEmployeeVo(t.id,t.employeeCode,t.employeeName,t.employeeImg) from RailEmployee t  "
				+where.toString()
				+" and t.note1 = 0 ";
		 List list= this.getHibernateTemplate().find(hql);
		 return list;
	}

	@Override
	public List apiAppActionAllWorkInfoList(Map<String, String> params) {
		String equipCode = params.get("equipCode");
		String  whereequipCode = "";
		if(!StringUtil.isEmpty(equipCode)){
			whereequipCode =" where t2.EQUIP_CODE ="+equipCode;
		}
		
		String sql_depts = "select t.ACCESS_DEPT    from RAIL_ACCESS_INFO t "
				+ " left join RAIL_ACCESS_EQUIP_INFO  t2 on t.ACCESS_CODE   = t2.ACCESS_CODE "+whereequipCode;
		List<String> list_depts= CommonFunction.getCommQueryDAO().findBySQLQuery(sql_depts);
		StringBuffer where = new StringBuffer();
		where.append(" where  t.dept in (");
		if(null!=list_depts && list_depts.size()>0){
			String deptCode = list_depts.get(0);
			list_depts = getDeptList(list_depts,deptCode);
			for(String id :list_depts){
				where.append(id+",");
			}
		}
		where.append("-1)");
		String hql =  " select new com.rail.po.vo.RailWorkInfoVo(t.id,t.workCode,t.workName,t.constOrg,t.dispatchCode,t.skylightStart,"
				+"t.skylightEnd,t.workTeam,t.employeeCount,t.formOrg,t.dept,t.targetingEmployeeCode,t.constOrg,t.workStatus)  from RailWorkInfo t " + where
				+" and t.delStatus = 0 and t.workStatus < 2 and t.auditStatus = 1";
		List<RailWorkInfoVo> listWork= this.getHibernateTemplate().find(hql);
		actionWorkList(listWork);
		return listWork;
	}
	private List<RailWorkInfoVo>  actionWorkList(List<RailWorkInfoVo> listWork){
		for(RailWorkInfoVo info :listWork){
			if(info.getFormOrg()!=null){
				RailFormOrg railFormOrg= this.getHibernateTemplate().get(RailFormOrg.class,info.getFormOrg());
				if(railFormOrg!=null){
					info.setFormOrgName(railFormOrg.getFormOrgName());;
				}
			}
			if(info.getWorkTeam()!=null){
				RailTeam RailTeam= this.getHibernateTemplate().get(RailTeam.class,info.getWorkTeam());
				if(RailTeam!=null){
					info.setWorkTeamName(RailTeam.getWorkTeamName());
				}
			}
			if(info.getConstOrg()!=null){
				RailConstOrg item = this.getHibernateTemplate().get(RailConstOrg.class,info.getConstOrg());
				if(null!=item){
					info.setCostOrgName(item.getCostOrgName());;
				}
			}
			if(info.getTargetingEmployeeCode()!=null){
				String itemSql = " select new com.rail.po.vo.RailEmployeeVo2(t.id,t.employeeCode,t.employeeName,t.employeeImg,t.employeeTel,t.note1,t.dept)  from RailEmployee t where t.employeeCode ='"+info.getTargetingEmployeeCode()+"'";
				List<RailEmployeeVo2> items = this.getHibernateTemplate().find(itemSql);
				if(null!=items && items.size()>0){
					info.setTargetingEmployeeName(items.get(0).getEmployeeName());
					info.setTargetingEmployeeInfo(items.get(0));
				}
				
			}
			//			放置员工记录信息
			String wemployee = "and  t2.workCode = '"+info.getWorkCode()+"'";
			String hqlEmployee = "select new com.rail.po.vo.RailEmployeeVo2(t.id,t.employeeCode,t.employeeName,t.employeeImg,t.employeeTel,t.note1,t.dept)"
					+ " from RailWorkEmployee t2, RailEmployee  t "
					+ " where t.employeeCode = t2.emplpoyeeCode  "+wemployee;  
			List<RailEmployeeVo2> listRailEmployee= this.getHibernateTemplate().find(hqlEmployee);
			for(RailEmployeeVo2 item : listRailEmployee){
				TblBrhInfo t3 = this.getHibernateTemplate().get(TblBrhInfo.class, item.getDept());
				if(null!=t3){
					item.setDetpName(t3.getBrhName());
				}
				
				String itemSql  = "from RailWhseEmployee t where t.employeeCode='"+item.getEmployeeCode()+"'";
				List<RailWhseEmployee> items = this.getHibernateTemplate().find(itemSql);
				if(null!=items && items.size()>0){
					item.setWorkStatus(items.get(0).getInfoSign());
				}
			}
			info.setEmployeeList(listRailEmployee);
			
			//放置工具记录信息

			String sqlWorkToolall = "from RailWorkTool t where t.workCode='"+info.getWorkCode()+"'";
			List<RailWorkTool> railWorkToolList = this.getHibernateTemplate().find(sqlWorkToolall);
			TreeSet<Integer> toolStatus = new TreeSet<Integer>();

			for(RailWorkTool ite :railWorkToolList ){
				toolStatus.add(Integer.valueOf(ite.getInfoSign()));
			}

			int Max =0;
			if(toolStatus!=null && toolStatus.size()>0){
				Max = toolStatus.last();
			}
			info.setWorkStatus(Max+"");
			System.out.println("公单工具信息············");
			String sql_work_tool =" select t1.id  as toolNameId, t1.tool_name,t1.tool_img,t2.tool_type_name,"// index 0 - 3
					+" t.stand,t.floor,t.position,t.tool_Code,t.rfid,tt.info_sign "//4-9
					+" from    rail_work_tool tt"
					+" left join  rail_tool_info t on tt.tool_code = t.tool_code"
					+" left join rail_tool_name t1 on t1.id = t.tool_name"
					+" left join rail_tool_type t2 on t1.tool_type =  t2.id"
					+" where tt.work_code='"+info.getWorkCode()+"'" ;
			if(Max == InfoSignRailWorkToolEnums.INACESS.getCode()){
				sql_work_tool = sql_work_tool +"  and  tt.info_sign = '"+InfoSignRailWorkToolEnums.INACESS.getCode()+"'"  ;
			}
			List<Object[]> list_tools = CommonFunction.getCommQueryDAO().findBySQLQuery(sql_work_tool);
			Map<String,RailToolInfoVo2> tem_toolNameinfo  = new HashMap<String, RailToolInfoVo2>();
			for(Object[] oar:list_tools ){
				if(oar[0]==null){
					continue;
				}
				RailToolInfoVo2 vo = tem_toolNameinfo.get(String.valueOf(oar[0]));
				if(vo!=null){
//					vo.setToolNameId(String.valueOf(oar[0]));
//					if(oar[1]!=null){
//						vo.setToolName(String.valueOf(oar[1]));
//					}
//					if(oar[2]!=null){
//						vo.setToolImg(String.valueOf(oar[2]));
//					}
//					if(oar[3]!=null){
//						vo.setToolTypeName(String.valueOf(oar[3]));
//					}
					vo.setCount(vo.getCount()+1);
					if(oar[4]!=null && oar[5]!=null && oar[6]!=null){
						String location = oar[4]+"架"+oar[5]+"层"+oar[6]+"位";
						vo.getLocations().add(location);
					}
					if(oar[7]!=null){
						vo.getTooCodes().add(String.valueOf(oar[7]));
					}
					if(oar[8]!=null){
						vo.getRfids().add(String.valueOf(oar[8]));
					}
					
					if(oar[9]!=null){
						vo.getInfoSigns().add(String.valueOf(oar[9]));
					}
				}else{
					RailToolInfoVo2 newvo = new RailToolInfoVo2();
					newvo.setCount(1l);
					newvo.setToolNameId(String.valueOf(oar[0]));
					if(oar[1]!=null){
						newvo.setToolName(String.valueOf(oar[1]));
					}
					if(oar[2]!=null){
						newvo.setToolImg(String.valueOf(oar[2]));
					}
					if(oar[3]!=null){
						newvo.setToolTypeName(String.valueOf(oar[3]));
					}
					
					if(oar[4]!=null && oar[5]!=null && oar[6]!=null){
						newvo.setLocations(new ArrayList<String>());
						String location = oar[4]+"架"+oar[5]+"层"+oar[6]+"位";
						newvo.getLocations().add(location);
					}
					if(oar[7]!=null){
						newvo.setTooCodes(new ArrayList<String>());
						newvo.getTooCodes().add(String.valueOf(oar[7]));
					}
					if(oar[8]!=null){
						newvo.setRfids(new ArrayList<String>());
						newvo.getRfids().add(String.valueOf(oar[8]));
					}
					
					if(oar[9]!=null){
						newvo.setInfoSigns(new ArrayList<String>());
						newvo.getInfoSigns().add(String.valueOf(oar[9]));
					}
					tem_toolNameinfo.put(String.valueOf(oar[0]), newvo);
					
				}
				
			}
			List<RailToolInfoVo2> listRailTools= new ArrayList<>();
			for(Map.Entry<String, RailToolInfoVo2> en:tem_toolNameinfo.entrySet()){
				listRailTools.add(en.getValue());
			}

			if(listRailTools!=null &&listRailTools.size()>0){
//				int Mix = toolStatus.first();
//				//业务中 0未出库、1已出库、2已入网、3已出网、4已入库
//				//最大为已出库 则给最落后的  最大小于已出库 则给最新的
//				if(Max == InfoSignRailWorkToolEnums.OUTWHERE.getCode() ){
//					finalStatus = Mix;
//				}else{
//				}
				//工具信息整合
				info.setToolsList(listRailTools);
				info.setToolsCount(list_tools.size());
//				info.setWorkStatus(String.valueOf(finalStatus));
			}
		
			//放置小组成员信息
			String wteam = " and t2.workTeam = '"+info.getWorkTeam()+"'";
			String hqlteam = "select new  com.rail.po.vo.RailEmployeeVo2(t.id,t.employeeCode,t.employeeName,t.employeeImg,t.employeeTel,t.note1,t.dept)"
					+"  from RailEmployee t,RailTeamEmployee t2 "
					+ "  where t.employeeCode = t2.employeeCode " 
					+ wteam;  
			List<RailEmployeeVo2> listRailteams = this.getHibernateTemplate().find(hqlteam);
			for(RailEmployeeVo2 item : listRailteams){
				TblBrhInfo t3 = this.getHibernateTemplate().get(TblBrhInfo.class, item.getDept());
				if(null!=t3){
					item.setDetpName(t3.getBrhName());
				}
				String itemSql  = " from RailWhseEmployee t where t.employeeCode='"+item.getEmployeeCode()+"'";
				List<RailWhseEmployee> items = this.getHibernateTemplate().find(itemSql);
				if(null!=items && items.size()>0){
					item.setWorkStatus(items.get(0).getInfoSign());
				}
			}
			info.setListRailteams(listRailteams);
		}
		return listWork;
	}
	
	@Override
	public Map openAccess(String employeeCode, String equipCode,Integer openSign)  {
		Map<String,Object> result = new HashMap<>();
		//1 通过终端(eqiutCode)获取门禁的部门
		RailAccessInfo accessInfo  = null;
		String hql = "select new com.rail.po.access.RailAccessInfo(t1.accessCode,t1.whseCode,t1.accessDept,t1.note1) from  RailAccessEquipInfo t ,RailAccessInfo t1 "
				+ "where  t.accessCode = t1.accessCode and t.equipCode='"+equipCode+"'";
		List<RailAccessInfo> list = this.getHibernateTemplate().find(hql);
		
		if(list==null || list.size()==0){
			return ResultUtils.fail("终端设备码"+equipCode+"不存在对应的门禁");
		}
		accessInfo =  list.get(0);
		String dept = accessInfo.getAccessDept();
		//2 获取有权限开的门员工和 对应部门下的未开始&进行中的工单 
		String hsql_open = "select new com.rail.po.vo.ApiRailWorkAccessOpenVo(t.workStart,t.workCode,t.workTeam,t3.workTeamName) "
				+ " from RailWorkInfo  t ,RailWorkEmployee t2,RailTeam t3 "
				+ " where   t.workCode = t2.workCode  and t.workTeam = t3.id "
				+ " and  t.dept = '"+dept+"'"
				+ " and t2.emplpoyeeCode = '"+employeeCode+"'"
				+ " and t.auditStatus = 1 and   t.workStatus in('"+WorkStatusEnums.UNDO.getCode()+"','"+WorkStatusEnums.ACTION.getCode()+"')"
				+ " and t2.openSign = '"+OpenSignEnums.YES.getCode()+"'";
		this.getHibernateTemplate().clear();
		List<ApiRailWorkAccessOpenVo> lisAccessOpenVos = this.getHibernateTemplate().find(hsql_open);//有开门权限的员工工单信息
		if(lisAccessOpenVos==null ||lisAccessOpenVos.size()==0){
			return ResultUtils.fail("员工没有开门权限");
		}
		result.put("accessInfo", lisAccessOpenVos.get(0));
		//3 员工基础信息
		String hsql_emp = "from RailEmployee t where t.employeeCode='"+employeeCode+"'"  ;
		this.getHibernateTemplate().clear();
		List<RailEmployee> railEmployees =  this.getHibernateTemplate().find(hsql_emp);
		RailEmployee railEmployee = null;
		if(railEmployees!=null && railEmployees.size()>0 ){
			railEmployee = railEmployees.get(0);
		}
		if(railEmployee!=null){
			result.put("employeeCode", employeeCode);
			result.put("employeeName", railEmployee.getEmployeeName());
		}
		// 4 工单工具的状态信息
		String hsql_tool_state = "from RailWorkTool t where t.workCode='"+lisAccessOpenVos.get(0).getWorkCode()+"'"  ;
		this.getHibernateTemplate().clear();
		List<RailWorkTool> railWorkTools =  this.getHibernateTemplate().find(hsql_tool_state);
		if(railWorkTools==null || railWorkTools.size()==0){
			return ResultUtils.fail("工单不存在工具");
		}
//		Integer finalStatus ;
		Integer Max ;//初始值
//		Integer Mix ;//初始值
		TreeSet<Integer> treeSet = new TreeSet<Integer>();//有序不重复
		for(RailWorkTool  item :railWorkTools){
			if(item.getInfoSign()!=null){
				treeSet.add(Integer.valueOf(item.getInfoSign()));
			}
		}
//		Mix = treeSet.first();
		Max = treeSet.last();
		//业务中 0未出库、1已出库、2已入网、3已出网、4已入库
		//最大为已出库 则给最落后的  最大小于已出库 则给最新的
//		if(Max == InfoSignRailWorkToolEnums.OUTWHERE.getCode() ){
//			finalStatus = Mix;
//		}else{
//			finalStatus = Max;
//		}
		result.put("workStatus", Max);
		
		//首先先判断当前设备是否连接的上
		if(openSign!=null && openSign == OpenSignEnums.YES.getCode()){
			if (PubUtil.wsChannelIsAvailable(accessInfo.getNote1())) {
				String accessOrder=PubUtil.wsGetAccessOrder(accessInfo.getNote1(), 0);
				int wirteNum= PubUtil.wsWriteDataFromChannel(accessInfo.getNote1(), accessOrder);
				if (wirteNum>0) {
				//	retMsg="发送指令成功！";
					//系统发送的开门指令
					PubUtil.ACCESS_OPEN.put(accessInfo.getNote1(), new Date().getTime());
					
					//TODO 增加关门记录
					RailAccessOptlog railAccessOptlog=new RailAccessOptlog();
					railAccessOptlog.setAccessCode(accessInfo.getAccessCode());
					railAccessOptlog.setEquipCode(equipCode);
					railAccessOptlog.setEmployeeCode(employeeCode);
					//TODO 增加工单标识
					railAccessOptlog.setWorkCode(lisAccessOpenVos.get(0).getWorkCode());
					//1出  2 入
					if(Max==InfoSignRailWorkToolEnums.ANDO.getCode()){
						railAccessOptlog.setInfoSign("2");
					}else if(Max==InfoSignRailWorkToolEnums.OUTWHERE.getCode()){
						railAccessOptlog.setInfoSign("2");
					}else if(Max==InfoSignRailWorkToolEnums.INACESS.getCode()){
						railAccessOptlog.setInfoSign("1");
					}else if(Max==InfoSignRailWorkToolEnums.OUTACESS.getCode()){
						railAccessOptlog.setInfoSign("2");
					}
					railAccessOptlog.setOpenSign("1");
					railAccessOptlog.setAddDate(new Date());
					T120101BO t120101BO=(T120101BO) ContextUtil.getBean("T120101BO");
					t120101BO.saveRailAccessOptlog(railAccessOptlog);
				}
			}else {
				return ResultUtils.fail("无法连接到门禁终端，请重试！");
				//retMsg="无法连接到门禁终端，请重试！";
			}
		}
		
		return  ResultUtils.success(result);
	}//1出  2 入
	private void openAccessRecord(String flag,RailAccessInfo accessInfo,String equipCode,String workCode){
		if (PubUtil.wsChannelIsAvailable(accessInfo.getNote1())) {
			String accessOrder=PubUtil.wsGetAccessOrder(accessInfo.getNote1(), 0);
			int wirteNum= PubUtil.wsWriteDataFromChannel(accessInfo.getNote1(), accessOrder);
			if (wirteNum>0) {
				//	retMsg="发送指令成功！";
				//系统发送的开门指令
				PubUtil.ACCESS_OPEN.put(accessInfo.getNote1(), new Date().getTime());

				//TODO 增加关门记录
				RailAccessOptlog railAccessOptlog=new RailAccessOptlog();
				railAccessOptlog.setAccessCode(accessInfo.getAccessCode());
				railAccessOptlog.setEquipCode(equipCode);
				railAccessOptlog.setEmployeeCode(null);
				//TODO 增加工单标识
				railAccessOptlog.setWorkCode(workCode);
				railAccessOptlog.setInfoSign(flag);
				railAccessOptlog.setOpenSign("1");
				railAccessOptlog.setAddDate(new Date());
				T120101BO t120101BO=(T120101BO) ContextUtil.getBean("T120101BO");
				t120101BO.saveRailAccessOptlog(railAccessOptlog);
			}
		}else {
			//return ResultUtils.fail("无法连接到门禁终端，请重试！");
			//retMsg="无法连接到门禁终端，请重试！";
		}
	}


	/**
	 * 出库
	 * 0 校验
	 * 1 更新工单中 工具的 名称和编码 根据编码
	 * 2 基础工具的 工具使用次数+1
	 * 
	 */
	@Override
	public Map addRailWhseOut(String toolCodes ,String workCode, String equipCode, String infoSign)  {
		logger.info("出库操作"+"参数toolCodes:"+toolCodes+"workCode:"+workCode+"equipCode:"+equipCode);
		//获取部门
		
//		String[] toolCodeAr = toolCodes.substring(1, toolCodes.length()-1).split(",");
//		if(railWorkTools.size()!=toolCodeAr.length){
//			return ResultUtils.fail("所拿工具与订单工具数量不对");
//		}
		String[] toolCodeAr = toolCodes.split(",");
		List<String> ls = Arrays.asList(toolCodeAr);
		String wwwsql  ="(";
		for(String s : ls){
			wwwsql =  wwwsql +"'"+s+"',";
		}
		wwwsql = wwwsql +"'0')";
		// 0 查询工单中 在工具库中的存在 ---以备 更新工单工具信息 信息用用  并校验  如果数量不相同 （库中工具和工单中的工具），即工单中的工具并不存在。 
		String hsql1 = "from RailToolInfo t where t.toolCode in "+ wwwsql;
		List<RailToolInfo> railToolInfos = this.getHibernateTemplate().find(hsql1);
		logger.info("入库工具的数目:"+railToolInfos.size());
		if(railToolInfos==null || railToolInfos.size()==0){
			return ResultUtils.fail("出库时工具不能为空");
		}
		
		String hsql ="from RailWorkTool t where t.workCode='"+workCode+"'"
				+ " and t.toolCode in "+ wwwsql;
//		List<RailWorkTool> railWorkTools = this.getHibernateTemplate().find(hsql);
		//删除原有工单工具
		//新增 已出库的工具
		String sqlDel = "from  RailWorkTool t where t.workCode = '"+workCode+"'";
		List<RailWorkTool> delListRailWorkTools = this.getHibernateTemplate().find(sqlDel);
		this.getHibernateTemplate().deleteAll(delListRailWorkTools);
		List<RailWorkTool> addListRailWorkTool = new ArrayList<>();
		for(RailToolInfo item  : railToolInfos ){
			RailWorkTool addRailWorkTool = new RailWorkTool();
			addRailWorkTool.setWorkCode(workCode);
			if(Integer.valueOf(infoSign) == InfoSignInOutEnums.IN.getCode()){
				addRailWorkTool.setInfoSign(String.valueOf(InfoSignRailWorkToolEnums.INWHES.getCode()));
			}else if(Integer.valueOf(infoSign) == InfoSignInOutEnums.OUT.getCode()){
				addRailWorkTool.setInfoSign(String.valueOf(InfoSignRailWorkToolEnums.OUTWHERE.getCode()));
			}
			addRailWorkTool.setToolCode(item.getToolCode());
			addRailWorkTool.setDelStatus(DelStatusEnums.NO.getCode()+"");
			addRailWorkTool.setAddDate(new Date());
			addRailWorkTool.setToolName(item.getToolName());
			addRailWorkTool.setInfoSign1("1");
			addListRailWorkTool.add(addRailWorkTool);
		}
		logger.info("添加工单工具的数目:"+addListRailWorkTool.size());
		//去重
		HashMap<String,Object> isExcet = new HashMap<>();
		for(RailWorkTool isec  : addListRailWorkTool){
			if(isExcet.get(isec.getToolCode())==null){
				isExcet.put(isec.getToolCode(),isec);
			}else{
				addListRailWorkTool.remove(isec);
			}
		}
		this.getHibernateTemplate().saveOrUpdateAll(addListRailWorkTool);

		//2 使用工具次数加一 更新  工具信息
		for(RailToolInfo i :railToolInfos){
			if(i.getNote1()==null){
				i.setNote1("1");
			}else{
				i.setNote1(String.valueOf(Integer.valueOf(i.getNote1())+1));
			}
			this.getHibernateTemplate().update(i);
		}
		//出入库公共操作部分
		addRailWhseSingle(workCode, equipCode, infoSign);

		return ResultUtils.success(1);
	}
	/**
	 * 入库   --------- 和出入库公共部分
	 *  1 删除这个工单中的工具和人员  
	 *  2 存入工具 和人员的出入库记录
	 *  3 更新基础人员的出入网 --- 正常
	 *  4 更新基础工具的在库和出库  1在库；2出库
	 *  5 更新工单工具的出入库状态 0未出库、1已出库、2已入网、3已出网、4已入库
	 *  6 更新工单的开始和结束状态 0未开始、1进行中、2已结束、3预警
	 */
	@Override
	public Map addRailWhseSingle(String workCode, String equipCode, String infoSign) {
		if(Integer.valueOf(infoSign) == InfoSignInOutEnums.IN.getCode()){
			logger.info("入库操作"+"参数infoSign:"+infoSign+"workCode:"+workCode+"equipCode:"+equipCode);
		}
		Map<String,Object> result = new HashMap<>();
		//1 通过终端(eqiutCode)获取门禁的部门
		RailAccessInfo accessInfo  = null;
		String hql = "select new com.rail.po.access.RailAccessInfo(t1.accessCode,t1.whseCode,t1.accessDept,t1.note1) from  RailAccessEquipInfo t ,RailAccessInfo t1 "
				+ "where  t.accessCode = t1.accessCode and t.equipCode='"+equipCode+"'";
		List<RailAccessInfo> list = this.getHibernateTemplate().find(hql);

		if(list==null || list.size()==0){
			return ResultUtils.fail("终端设备码"+equipCode+"不存在对应的门禁");
		}
		accessInfo =  list.get(0);
		
		// 1.1 清空  仓库 工单的人员记录
//		RailWhseEmployee del = new RailWhseEmployee();
//		del.setInfoSign(infoSign);
//		del.setWorkCode(workCode);
//		del.setWhseCode(accessInfo.getWhseCode());
//		this.getHibernateTemplate().delete(del);
		//2.1 存入人员的出入库记录
		String hsql_emp = " from RailWorkEmployee t where t.workCode='"+workCode+"' ";//and t.openSign='"+OpenSignEnums.YES.getCode()+"'
		List<RailWorkEmployee> listEmployees   = this.getHibernateTemplate().find(hsql_emp);
		for(RailWorkEmployee item : listEmployees){
			RailWhseEmployee railWhseEmployee = new RailWhseEmployee();
			railWhseEmployee.setWhseCode(accessInfo.getWhseCode());
			railWhseEmployee.setWorkCode(workCode);
			railWhseEmployee.setInfoSign(infoSign);
			railWhseEmployee.setEmployeeCode(item.getEmplpoyeeCode());
			railWhseEmployee.setAddDate(new Date());
			this.getHibernateTemplate().save(railWhseEmployee);
			
			if(Integer.valueOf(infoSign) == InfoSignInOutEnums.IN.getCode()){
				//3 更新 员工的出入网状态
				String sqsl_em = "from RailEmployee t where t.employeeCode='"+item.getEmplpoyeeCode()+"'";
				List<RailEmployee> employees = this.getHibernateTemplate().find(sqsl_em);
				if(employees!=null && employees.size()!=0){
					RailEmployee employee2 = employees.get(0);
					employee2.setInfoSign(String.valueOf(EmployeeInfoSignEnums.NORMAL.getCode()));
					employee2.setUpdDate(new Date());
					this.getHibernateTemplate().update(employee2);
				}
			}
		}
		// 查询已出库 的工具
		String hsql_tool = " from RailWorkTool t where t.workCode='"+workCode+"' and t.infoSign ='"+InfoSignRailWorkToolEnums.ANDO.getCode()+"'";
		
		//1.2    清空   这个工单下 仓库 下的  工具 免除脏数据
//		RailWhseTool dell = new RailWhseTool();
//		dell.setInfoSign(infoSign);
//		dell.setWorkCode(workCode);
//		dell.setWhseCode(accessInfo.getWhseCode());
//		this.getHibernateTemplate().delete(dell);
		//2.2   存入工具的出入库记录
		List<RailWorkTool> railWorkTools   = this.getHibernateTemplate().find(hsql_tool);
		for(RailWorkTool item : railWorkTools){
			RailWhseTool railWhseTool = new RailWhseTool();
			railWhseTool.setWhseCode(accessInfo.getWhseCode());
			railWhseTool.setWorkCode(workCode);
			railWhseTool.setInfoSign(infoSign);
			railWhseTool.setToolCode(item.getToolCode());
			railWhseTool.setAddDate(new Date());
			this.getHibernateTemplate().save(railWhseTool);
			//4 更新 工具的在库状态  
			//5 更新 工单工具的状态
			String queryString2 = "from RailToolInfo t where t.toolCode='"+item.getToolCode()+"'";
			List<RailToolInfo> railWorkInfos2 = this.getHibernateTemplate().find(queryString2);
			RailToolInfo railToolInfo2 = railWorkInfos2.get(0);
			if(Integer.valueOf(infoSign) == InfoSignInOutEnums.IN.getCode()){
				item.setInfoSign(String.valueOf(InfoSignRailWorkToolEnums.INWHES.getCode()));
				railToolInfo2.setInWhse(String.valueOf(InWhseEnums.IN.getCode()));
				item.setInfoSign4("1");
				item.setUpdDate(new Date());
				this.getHibernateTemplate().update(item);
			}
			this.getHibernateTemplate().update(railToolInfo2);

		}
		//6  更新工单 开始和结束状态
		String queryString = "from RailWorkInfo t where t.workCode='"+workCode+"'";
		List<RailWorkInfo> railWorkInfos = this.getHibernateTemplate().find(queryString);
		RailWorkInfo railWorkInfo = railWorkInfos.get(0);
		if(Integer.valueOf(infoSign) == InfoSignInOutEnums.IN.getCode()){
			railWorkInfo.setWorkStatus(String.valueOf(WorkStatusEnums.END.getCode()));
			railWorkInfo.setWorkEnd(new Date());
		}else if(Integer.valueOf(infoSign) == InfoSignInOutEnums.OUT.getCode()){
			railWorkInfo.setWorkStatus(String.valueOf(WorkStatusEnums.ACTION.getCode()));
			railWorkInfo.setWorkStart(new Date());
		}
		this.getHibernateTemplate().update(railWorkInfo);

		//发送工单信息 更新    发送信息 更新app人员列表
		TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
		tSendMsgSocketBO.sendSocketMsg(railWorkInfo.getDept(), railWorkInfo.getId(), ApiSendMsgFlagEnums.WORK_ORDER.getCode());

		return ResultUtils.success(1);
		
	}

	/**
	 * 入网
	 * 0      清空   这个工单下 门禁 下的  工具 和人员 记录 免除脏数据
	 * 1  根据工单新增 工具和人员与出入库记录 
	 * 2更新工单中 工具的状态   和 新工单中 人员的状态 
	 * 3 更新 基础人员的出入网状态 --- 出/入 
	 * 
	 */
	@Override
	public void addRailAccesssInRecord(String employeeCodes, String toolCodes,String infoSign ,String workCode,String equipCode)   {
		logger.info("入网操作"+"参数employeeCodes:"+employeeCodes+"toolCodes:"+toolCodes+"workCode:"+workCode+"equipCode:"+equipCode);
		String[] toolCodeAr = toolCodes.split(",");
		List<String> ls = Arrays.asList(toolCodeAr);
		String wwwsql  ="(";
		for(String s : ls){
			wwwsql =  wwwsql +"'"+s+"',";
		}
		wwwsql = wwwsql +"'0')";
		// 0 查询工单中 在工具库中的存在 ---以备 更新工单工具信息 信息用用  并校验  如果数量不相同 （库中工具和工单中的工具），即工单中的工具并不存在。
		String hsql1 = "from RailToolInfo t where t.toolCode in "+ wwwsql;


		//查询部门
		RailAccessInfo accessInfo  = null;
		String hql = "select new com.rail.po.access.RailAccessInfo(t1.accessCode,t1.whseCode) from  RailAccessEquipInfo t ,RailAccessInfo t1 "
				+ "where  t.accessCode = t1.accessCode and t.equipCode='"+equipCode+"'";
		List<RailAccessInfo> list = this.getHibernateTemplate().find(hql);
		if(list!=null && list.size()>0){
			accessInfo =  list.get(0);
		}
		//0.1
//		RailAccessTool dell = new RailAccessTool();
//		dell.setInfoSign(infoSign);
//		dell.setWorkCode(workCode);
//		dell.setAccessCode(accessInfo.getAccessCode());
//		 this.getHibernateTemplate().delete(dell);
		// 1 存入工具出入网记录    --- 查询出工具 与工单相关 插入门禁（出入网）记录  
		// 1.1 更新出 工单工具
//		String htools = "from RailWorkTool t where t.workCode ='"+workCode+"' and t.infoSign ='"+InfoSignRailWorkToolEnums.OUTWHERE.getCode()+"'";
		String htools = "from RailWorkTool t where t.workCode ='"+workCode+"' and t.toolCode in "+ wwwsql;
		List<RailWorkTool> listTools = this.getHibernateTemplate().find(htools);
		for(RailWorkTool item : listTools){
			RailAccessTool accessTool = new RailAccessTool();
			accessTool.setWorkCode(workCode);
			accessTool.setInfoSign(infoSign);
			accessTool.setToolCode(item.getToolCode());
			accessTool.setNote1(String.valueOf(new Date()));
			accessTool.setAddDate(new Date());
			if(accessInfo != null){
				accessTool.setAccessCode(accessInfo.getAccessCode());
			}
			this.getHibernateTemplate().save(accessTool);
			//2.1 更新工单的工具状态
			if(Integer.valueOf(infoSign) ==InfoSignInOutEnums.IN.getCode()){
				item.setInfoSign(String.valueOf(InfoSignRailWorkToolEnums.INACESS.getCode()));
				item.setInfoSign2("1");
			}else if(Integer.valueOf(infoSign) ==InfoSignInOutEnums.OUT.getCode()){
				item.setInfoSign(String.valueOf(InfoSignRailWorkToolEnums.OUTACESS.getCode()));
				item.setInfoSign3("1");
			}
			item.setUpdDate(new Date());
			this.getHibernateTemplate().update(item);
		}
		//0.2 清空记录
		RailAccessEmployee del = new RailAccessEmployee();
		del.setInfoSign(infoSign);
		del.setWorkCode(workCode);
		del.setAccessCode(accessInfo.getAccessCode());
		this.getHibernateTemplate().delete(del);
		
		// 1.2 存入人员出入网1记录    查询出人员 与工单相关 插入门禁（出入网）记录
		String[] employeeSs = employeeCodes.split(",");
		List<String> ls1 = Arrays.asList(employeeSs);
		String wwwsqle  ="(";
		for(String s : ls1){
			wwwsqle =  wwwsqle +"'"+s+"',";
		}
		wwwsqle = wwwsqle +"'0')";

		/* 产出为出网的工单 人员*/
		String delhemployees = "from RailWorkEmployee t where t.workCode ='"+workCode+"' and t.emplpoyeeCode not in "+wwwsqle;
		List<RailWorkEmployee> dellistEmployees = this.getHibernateTemplate().find(delhemployees);
		this.getHibernateTemplate().deleteAll(dellistEmployees);

		String hemployees = "from RailWorkEmployee t where t.workCode ='"+workCode+"' and t.emplpoyeeCode in "+wwwsqle;
		List<RailWorkEmployee> listEmployees = this.getHibernateTemplate().find(hemployees);
		for(RailWorkEmployee item : listEmployees){
			RailAccessEmployee accessEmployee = new RailAccessEmployee();
			accessEmployee.setWorkCode(workCode);
			accessEmployee.setInfoSign(infoSign);
			accessEmployee.setAddDate(new Date());
			accessEmployee.setNote1(String.valueOf(new Date()));
			accessEmployee.setEmployeeCode(item.getEmplpoyeeCode());
			if(accessInfo != null){
				accessEmployee.setAccessCode(accessInfo.getAccessCode());
			}
			this.getHibernateTemplate().save(accessEmployee);
			// 2.2更新工单人员的状态
			if(Integer.valueOf(infoSign) ==InfoSignInOutEnums.IN.getCode()){
				item.setInfoSign(String .valueOf(InfoSignRailWorkEmployeeEnums.INACESS.getCode()));
				item.setInfoSign2("1");
			}else if(Integer.valueOf(infoSign) ==InfoSignInOutEnums.OUT.getCode()){
				item.setInfoSign(String .valueOf(InfoSignRailWorkEmployeeEnums.OUTACESS.getCode()));
				item.setInfoSign3("1");
			}
			item.setUpdDate(new Date());
			this.getHibernateTemplate().update(item);
			
			// 3 更新 员工的出入网状态  
			String sqsl_em = "from RailEmployee t where t.employeeCode='"+item.getEmplpoyeeCode()+"'";
			List<RailEmployee> employees = this.getHibernateTemplate().find(sqsl_em);
			if(employees!=null && employees.size()!=0){
				RailEmployee employee2 = employees.get(0);
				employee2.setInfoSign(infoSign);
				employee2.setUpdDate(new Date());
				this.getHibernateTemplate().update(employee2);
			}
		}
		String queryString = "from RailWorkInfo t where t.workCode='"+workCode+"'";
		List<RailWorkInfo> railWorkInfos = this.getHibernateTemplate().find(queryString);
		RailWorkInfo railWorkInfo = railWorkInfos.get(0);
		//发送工单信息 更新    发送信息 更新app人员列表
		TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
		tSendMsgSocketBO.sendSocketMsg(railWorkInfo.getDept(), railWorkInfo.getId(), ApiSendMsgFlagEnums.WORK_ORDER.getCode());
	}
	@Override
	public void addRailAccesssOutRecord(String infoSign ,String workCode,String equipCode)   {
		logger.info("出网操作"+"参数employeeCodes:"+"workCode:"+workCode+"equipCode:"+equipCode);
		//查询部门
		RailAccessInfo accessInfo  = null;
		String hql = "select new com.rail.po.access.RailAccessInfo(t1.accessCode,t1.whseCode) from  RailAccessEquipInfo t ,RailAccessInfo t1 "
				+ "where  t.accessCode = t1.accessCode and t.equipCode='"+equipCode+"'";
		List<RailAccessInfo> list = this.getHibernateTemplate().find(hql);
		if(list!=null && list.size()>0){
			accessInfo =  list.get(0);
		}
		//0.1
		RailAccessTool dell = new RailAccessTool();
		dell.setInfoSign(infoSign);
		dell.setWorkCode(workCode);
		dell.setAccessCode(accessInfo.getAccessCode());
		 this.getHibernateTemplate().delete(dell);
		// 1 存入工具出入网记录    --- 查询出工具 与工单相关 插入门禁（出入网）记录
		// 1.1 更新出 工单工具
		String htools = "from RailWorkTool t where t.workCode ='"+workCode+"' and t.infoSign ='"+InfoSignRailWorkToolEnums.INACESS.getCode()+"'";
		List<RailWorkTool> listTools = this.getHibernateTemplate().find(htools);
		for(RailWorkTool item : listTools){
			RailAccessTool accessTool = new RailAccessTool();
			accessTool.setWorkCode(workCode);
			accessTool.setInfoSign(infoSign);
			accessTool.setToolCode(item.getToolCode());
			accessTool.setNote1(String.valueOf(new Date()));
			accessTool.setAddDate(new Date());
			if(accessInfo != null){
				accessTool.setAccessCode(accessInfo.getAccessCode());
			}
			this.getHibernateTemplate().save(accessTool);
			//2.1 更新工单的工具状态
			if(Integer.valueOf(infoSign) ==InfoSignInOutEnums.IN.getCode()){
				item.setInfoSign(String.valueOf(InfoSignRailWorkToolEnums.INACESS.getCode()));
				item.setInfoSign2("1");
			}else if(Integer.valueOf(infoSign) ==InfoSignInOutEnums.OUT.getCode()){
				item.setInfoSign(String.valueOf(InfoSignRailWorkToolEnums.OUTACESS.getCode()));
				item.setInfoSign3("1");
			}
			item.setUpdDate(new Date());
			this.getHibernateTemplate().update(item);
		}
		//0.2 清空记录
		RailAccessEmployee del = new RailAccessEmployee();
		del.setInfoSign(infoSign);
		del.setWorkCode(workCode);
		del.setAccessCode(accessInfo.getAccessCode());
		this.getHibernateTemplate().delete(del);

		// 1.2 存入人员出入网记录    查询出人员 与工单相关 插入门禁（出入网）记录
		String hemployees = "from RailWorkEmployee t where t.infoSign = 1  and  t.workCode ='"+workCode+"'";
		List<RailWorkEmployee> listEmployees = this.getHibernateTemplate().find(hemployees);
		for(RailWorkEmployee item : listEmployees){
			RailAccessEmployee accessEmployee = new RailAccessEmployee();
			accessEmployee.setWorkCode(workCode);
			accessEmployee.setInfoSign(infoSign);
			accessEmployee.setAddDate(new Date());
			accessEmployee.setNote1(String.valueOf(new Date()));
			accessEmployee.setEmployeeCode(item.getEmplpoyeeCode());
			if(accessInfo != null){
				accessEmployee.setAccessCode(accessInfo.getAccessCode());
			}
			this.getHibernateTemplate().save(accessEmployee);
			// 2.2更新工单人员的状态
			if(Integer.valueOf(infoSign) ==InfoSignInOutEnums.IN.getCode()){
				item.setInfoSign(String .valueOf(InfoSignRailWorkEmployeeEnums.INACESS.getCode()));
				item.setInfoSign2("1");
			}else if(Integer.valueOf(infoSign) ==InfoSignInOutEnums.OUT.getCode()){
				item.setInfoSign(String .valueOf(InfoSignRailWorkEmployeeEnums.OUTACESS.getCode()));
				item.setInfoSign3("1");
			}
			this.getHibernateTemplate().update(item);

			// 3 更新 员工的出入网状态
			String sqsl_em = "from RailEmployee t where t.employeeCode='"+item.getEmplpoyeeCode()+"'";
			List<RailEmployee> employees = this.getHibernateTemplate().find(sqsl_em);
			if(employees!=null && employees.size()!=0){
				RailEmployee employee2 = employees.get(0);
				employee2.setInfoSign(infoSign);
				employee2.setUpdDate(new Date());
				this.getHibernateTemplate().update(employee2);
			}
		}
		String queryString = "from RailWorkInfo t where t.workCode='"+workCode+"'";
		List<RailWorkInfo> railWorkInfos = this.getHibernateTemplate().find(queryString);
		RailWorkInfo railWorkInfo = railWorkInfos.get(0);
		//发送工单信息 更新    发送信息 更新app人员列表
		TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
		tSendMsgSocketBO.sendSocketMsg(railWorkInfo.getDept(), railWorkInfo.getId(), ApiSendMsgFlagEnums.WORK_ORDER.getCode());

	}

	
	@Override
	public Object getPresentBean(String id, String type) {
		if(Integer.valueOf(type) == ApiSendMsgFlagEnums.EMPLOYEE.getCode()){
			String hsql  ="select new com.rail.po.vo.RailEmployeeVo(t.id,t.employeeCode,t.employeeName,t.employeeImg) from RailEmployee t where t.id='"+id+"'"; 
			List<RailEmployeeVo> list = this.getHibernateTemplate().find(hsql);
			if(list==null ||list.size()==0){
				return null;
			}
			return list;
		}else if(Integer.valueOf(type) == ApiSendMsgFlagEnums.TOOL.getCode()){
			String sql = "select t.id,t.rfid,t.tool_code,t.tool_name,t2.tool_name as tool_name_id,t2.tool_img,"
					+" t3.tool_type_name,t2.tool_type"
					+" from rail_tool_info t "
					+" left join rail_tool_name t2 on t2.id = t.tool_name"
					+" left join rail_tool_type t3 on t2.tool_type  = t3.id"
					+" where t.id = '"+id+"'";
			List<Object[]> list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			if(null==list ||list.size()==0){
				return null;
			}
			List<ApiAllTollsVo> listAllTollsVos = new ArrayList<>();
			for(Object[] iem :list){
				ApiAllTollsVo allTollsVo = new ApiAllTollsVo();
				if(iem[1]!=null){
					allTollsVo.setRfid(String.valueOf(iem[1]));
					allTollsVo.setRfids(Arrays.asList(iem[1]));
				}
				if(iem[2]!=null){
					allTollsVo.setToolCode(String.valueOf(iem[2]));
					allTollsVo.setToolCodes(Arrays.asList(iem[2]));
				}
				if(iem[3]!=null){
					allTollsVo.setToolName(String.valueOf(iem[3]));
				}
				if(iem[4]!=null){
					allTollsVo.setToolNameId(String.valueOf(iem[4]));
				}
				if(iem[5]!=null){
					allTollsVo.setToolImg(String.valueOf(iem[5]));
				}
				listAllTollsVos.add(allTollsVo);
			}
			return listAllTollsVos;
		}else if(Integer.valueOf(type) == ApiSendMsgFlagEnums.WORK_ORDER.getCode()){
			String hql =  " select new com.rail.po.vo.RailWorkInfoVo(t.id,t.workCode,t.workName,t.constOrg,t.dispatchCode,t.skylightStart,"
					+"t.skylightEnd,t.workTeam,t.employeeCount,t.formOrg,t.dept,t.targetingEmployeeCode,t.constOrg,t.workStatus)  from RailWorkInfo t "
					+ " where t.id ='"+id+"'";
//			String hql1 =  " select new com.rail.po.vo.RailWorkInfoVo(t.id,t.workCode,t.workName,t.constOrg,t.dispatchCode,t.workStart,t.workEnd,t.workTeam,t.employeeCount,t.formOrg,t.dept,t.targetingEmployeeCode,t.constOrg) from RailWorkInfo t   "
//					+ " where t.id ='"+id+"'";
			List<RailWorkInfoVo> listWork= this.getHibernateTemplate().find(hql);
			actionWorkList(listWork);
			return listWork;
			
		}else if(Integer.valueOf(type) == ApiSendMsgFlagEnums.WARN.getCode()){
			return this.getHibernateTemplate().get(RailWorkWarn.class, Long.valueOf(id));
		}else if(Integer.valueOf(type) == ApiSendMsgFlagEnums.ACCESS_WARN.getCode()){
			return this.getHibernateTemplate().get(RailAccessWarn.class, Long.valueOf(id));
		}
		return null;
	}



}

