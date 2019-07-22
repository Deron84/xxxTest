package com.rail.bo.impl.tools;

import java.text.SimpleDateFormat;
import java.util.*;

import com.huateng.commquery.dao.ICommQueryDAO;
import com.rail.po.base.RailEmployee;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.enums.ApiSendMsgFlagEnums;
import com.rail.bo.enums.DelStatusEnums;
import com.rail.bo.enums.InfoSignEnums2;
import com.rail.bo.enums.InfoSignToolTransferEnums;
import com.rail.bo.enums.ToolsStatusEnums;
import com.rail.bo.pub.PubSearch;
import com.rail.bo.pub.ResultUtils;
import com.rail.bo.tools.T110100BO;
import com.rail.bo.tools.TSendMsgSocketBO;
import com.rail.po.tool.RailToolInfo;
import com.rail.po.tool.RailToolProp;
import com.rail.po.tool.RailToolTransfer;
import com.rail.po.warehouse.RailWhseInfo;
import com.rail.po.warehouse.RailWhseTool;

/**  
* @author syl
* @version 创建时间：2019年4月15日 上午9:23:37  
* @desc
*/
public class T110100BOTarget extends HibernateDaoSupport implements T110100BO {
			
	
	@Override
	public void update(RailToolInfo railToolInfo) throws Exception {
		RailToolInfo railToolInfo2 = this.getHibernateTemplate().get(RailToolInfo.class, railToolInfo.getId());
		//绑定标签
		if(railToolInfo.getRfid()!=null){
			railToolInfo2.setRfid(railToolInfo.getRfid());
		}
		//删除
		if(railToolInfo.getDelStatus()!=null){
			railToolInfo2.setDelStatus(railToolInfo.getDelStatus());
		}
		//报废 更改状态
		if(railToolInfo.getToolStatus()!=null){
			railToolInfo2.setToolStatus(railToolInfo.getToolStatus());
		}
		if(railToolInfo.getWhseCode()!=null){
			//移库操作记录新增
			RailToolTransfer railToolTransfer = new RailToolTransfer();
			railToolTransfer.setAddDate(new Date());
			railToolTransfer.setAddUser(railToolInfo.getAddUser());
			railToolTransfer.setToolCode(railToolInfo2.getToolCode());
			railToolTransfer.setTransferMsg(railToolInfo.getTransferMsg());
			railToolTransfer.setWhseAfter(railToolInfo.getWhseCode());
			railToolTransfer.setWhseBefore(railToolInfo2.getWhseCode());
			railToolTransfer.setInfoSign(String.valueOf(InfoSignToolTransferEnums.HADEL.getCode()));
			this.getHibernateTemplate().save(railToolTransfer);  
		}
		railToolInfo2.setUpdUser(railToolInfo.getUpdUser());
		railToolInfo2.setUpdDate(new Date());
		this.getHibernateTemplate().update(railToolInfo2);   
	}
	@Override
	public Map updateWhse(RailToolInfo railToolInfo) throws Exception {
		RailToolInfo railToolInfo2 = this.getHibernateTemplate().get(RailToolInfo.class, railToolInfo.getId());
		//验证是否同级调库
//		if(!checkSameLevel(railToolInfo.getWhseCode(), railToolInfo2.getWhseCode())){
//			return ResultUtils.fail("请选择同级别仓库");
//		}
		//验证是否同一个仓库
		System.out.println("-----------------------------------------------------");
		System.out.println(railToolInfo.getId());
		System.out.println(railToolInfo.getId());
		System.out.println(railToolInfo.getWhseCode());
		System.out.println(railToolInfo2.getWhseCode());
		System.out.println("-----------------------------------------------------");
		if(railToolInfo.getWhseCode().equals(railToolInfo2.getWhseCode())){
			return ResultUtils.fail("请选择其他仓库");
		}
		if(railToolInfo.getWhseCode()!=null){
			//移库操作记录新增
			RailToolTransfer railToolTransfer = new RailToolTransfer();
			railToolTransfer.setAddDate(new Date());
			railToolTransfer.setAddUser(railToolInfo.getAddUser());
			railToolTransfer.setToolCode(railToolInfo2.getToolCode());
			railToolTransfer.setTransferMsg(railToolInfo.getTransferMsg());
			railToolTransfer.setWhseAfter(railToolInfo.getWhseCode());
			railToolTransfer.setWhseBefore(railToolInfo2.getWhseCode());
			railToolTransfer.setInfoSign(String.valueOf(InfoSignToolTransferEnums.HADEL.getCode()));
			this.getHibernateTemplate().save(railToolTransfer); 
			
			RailWhseTool railWhseTool = new RailWhseTool();
			railWhseTool.setWhseCode(railToolInfo.getWhseCode());
			railWhseTool.setWorkCode("");
			railWhseTool.setInfoSign("1");
			railWhseTool.setToolCode(railToolInfo2.getToolCode());
			railWhseTool.setAddDate(new Date());
			this.getHibernateTemplate().save(railWhseTool);
			
			
			RailWhseTool railWhseTool2 = new RailWhseTool();
			railWhseTool2.setWhseCode(railToolInfo2.getWhseCode());
			railWhseTool2.setWorkCode("");
			railWhseTool2.setInfoSign("2");
			railWhseTool2.setToolCode(railToolInfo2.getToolCode());
			railWhseTool2.setAddDate(new Date());
			this.getHibernateTemplate().save(railWhseTool2);
			
			
		}
		railToolInfo2.setWhseCode(railToolInfo.getWhseCode());
		railToolInfo2.setUpdUser(railToolInfo.getUpdUser());
		railToolInfo2.setUpdDate(new Date());
		//清空架层位
		railToolInfo2.setStand(null);
		railToolInfo2.setFloor(null);
		railToolInfo2.setPosition(null);
		railToolInfo2.setNote2(null);
		this.getHibernateTemplate().update(railToolInfo2);   
		return ResultUtils.success(1);
	}
	public boolean checkSameLevel(String whesCode,String whseCode2){
		String hsql = "from RailWhseInfo t where t.whseCode='"+whesCode+"'";
		List<RailWhseInfo> infos = this.getHibernateTemplate().find(hsql);
		if(infos==null || infos.size()==0){
			return false;
		}
		String hsql2 = "from RailWhseInfo t where t.whseCode='"+whseCode2+"'";
		List<RailWhseInfo> infos2 = this.getHibernateTemplate().find(hsql2);
		if(infos2==null || infos2.size()==0){
			return false;
		}
		if(infos.get(0).getWhseRank()!=null && infos2.get(0).getWhseRank()!=null && infos.get(0).getWhseRank().equals(infos2.get(0).getWhseRank())){
			return true;
		}
		return false;
	}
	public void updateAll(RailToolInfo railToolInfo) throws Exception {
		RailToolInfo railToolInfo2 = this.getHibernateTemplate().get(RailToolInfo.class, railToolInfo.getId());
		railToolInfo2.setId(railToolInfo.getId());
		if(railToolInfo.getUpdUser()!=null){
			railToolInfo2.setUpdUser(railToolInfo.getUpdUser());
		}
		if(railToolInfo.getToolCode()!=null){
			railToolInfo2.setToolCode(railToolInfo.getToolCode());
		}
		if(railToolInfo.getDept()!=null){
			railToolInfo2.setDept(railToolInfo.getDept());
		}
		if(railToolInfo.getToolExpiration()!=null){
			railToolInfo2.setToolExpiration(railToolInfo.getToolExpiration());
		}
		if(railToolInfo.getWhseCode()!=null){
			railToolInfo2.setWhseCode(railToolInfo.getWhseCode());
		}
		if(railToolInfo.getPurchaseDept()!=null){
			railToolInfo2.setPurchaseDept(railToolInfo.getPurchaseDept());
		}
		if(railToolInfo.getPurchaseUser()!=null){
			railToolInfo2.setPurchaseUser(railToolInfo.getPurchaseUser());
		}
		if(null!=railToolInfo.getToolStatus()){
			railToolInfo2.setToolStatus(railToolInfo.getToolStatus());//
		}
		if(null!=railToolInfo.getToolStatus()){
			railToolInfo2.setToolStatus(railToolInfo.getToolStatus());//
		}
		if(null!=railToolInfo.getStand()){
			railToolInfo2.setStand(railToolInfo.getStand());//
		}
		if(null!=railToolInfo.getNote2()){
			railToolInfo2.setNote2(railToolInfo.getNote2());//
		}
		if(null!=railToolInfo.getFloor()){
			railToolInfo2.setFloor(railToolInfo.getFloor());//
		}
		if(null!=railToolInfo.getPosition()){
			railToolInfo2.setPosition(railToolInfo.getPosition());//
		}
		if(null!=railToolInfo.getToolMaterial()){
			railToolInfo2.setToolMaterial(railToolInfo.getToolMaterial());//
		}
	    railToolInfo2.setExamPeriod(railToolInfo.getExamPeriod());
		
        railToolInfo2.setMfrsOrg(railToolInfo.getMfrsOrg());
        //railToolInfo2.setDelStatus(String.valueOf(DelStatusEnums.NO.getCode()));//
       // railToolInfo2.setAccessCode(railToolInfo.getAccessCode());//门禁
        railToolInfo2.setUpdDate(new Date());
//        railToolInfo2.setAddDate(new Date());
//        railToolInfo2.setAddUser(railToolInfo.getAddUser());
//        railToolInfo2.setRfid(railToolInfo.getRfid());
//        railToolInfo2.setInitPrice(railToolInfo.getInitPrice());
//        railToolInfo2.setListPrice(railToolInfo.getListPrice());
//		
		this.getHibernateTemplate().update(railToolInfo2);   
	}


	@Override
	public Map save(RailToolInfo railToolInfo)  {
		TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
		// toolName note2 whseCode 做唯一校验 whseCode toolName note2
		String sql_chek = " from RailToolInfo t where "
				+ " t.whseCode='"+railToolInfo.getWhseCode()+"'"
				+ " and t.toolName='"+railToolInfo.getToolName()+"'"
				+ " and t.note2='"+railToolInfo.getNote2()+"'";
		List<RailToolInfo> finds  = this.getHibernateTemplate().find(sql_chek);
		if(finds!=null && finds.size()>0){
			return ResultUtils.fail("该工具型号被占用，同一仓库中同种工具的工具型号不能重复");
		}
		
		RailWhseTool railWhseTool = new RailWhseTool();
		railWhseTool.setWhseCode(railToolInfo.getWhseCode());
		railWhseTool.setWorkCode("");
		railWhseTool.setInfoSign("1");
		railWhseTool.setToolCode(railToolInfo.getToolCode());
		railWhseTool.setAddDate(new Date());
		this.getHibernateTemplate().save(railWhseTool);
		
		
		this.getHibernateTemplate().save(railToolInfo);  
		System.out.println("id:"+railToolInfo.getId());
		int flag  = ApiSendMsgFlagEnums.TOOL.getCode();
		//发送信息 更新app工具列表
		tSendMsgSocketBO.sendSocketMsg(railToolInfo.getDept(), railToolInfo.getId(), ApiSendMsgFlagEnums.TOOL.getCode());
		return ResultUtils.success(1);
		//send(id，flag);发送 
	}

	@Override
	public void del(RailToolInfo railToolInfo) throws Exception {
		this.getHibernateTemplate().delete(railToolInfo);  
		
	}


	@Override
	public RailToolInfo get(RailToolInfo railToolInfo) throws Exception {
		//if(railToolInfo.getId()!= 0){
			return  this.getHibernateTemplate().get(RailToolInfo.class, railToolInfo.getId());
//		}else{
//			return null;
//		}
	}

	@Override
	public RailToolInfo getById(Long id) {
		return  this.getHibernateTemplate().get(RailToolInfo.class, id);
	}

	@Override
	public boolean checkINOrder(String toolCode) {
		String sql = " select t.tool_code from rail_work_tool t " +
				" left join rail_work_info t2  on t.work_code = t2.work_code " +
				"  where t2.work_status < 2 and t.work_code = '"+toolCode+"'";
		 ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		List<Object[]> list = commQueryDAO.findBySQLQuery(sql);
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public RailToolInfo getByToolCode(RailToolInfo railToolInfo) throws Exception {
		railToolInfo.setToolCode(railToolInfo.getToolCode());
		List<RailToolInfo> list = this.getHibernateTemplate().find("from  RailToolInfo t where t.toolCode ='"+railToolInfo.getToolCode()+"'");
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return  null;
	}
	@Override
	public boolean checkFridPresent(String rfid) {
		String queryString = " from RailToolInfo t where t.rfid='"+rfid+"' and t.delStatus='"+DelStatusEnums.NO.getCode()+"'";
		List<RailToolInfo> list = this.getHibernateTemplate().find(queryString);
		if(list!=null  &&  list.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public RailEmployee getByEmpoyeeCode(String employeeCode) {
		String sql = "from  RailEmployee t where t.employeeCode = '"+employeeCode+"'";
		List<RailEmployee> list = this.getHibernateTemplate().find(sql);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map addListFromExcel(List<RailEmployee> railEmployees) {

		if(railEmployees.size()==0){
			return ResultUtils.success("未导入人员信息");
		}
		this.getHibernateTemplate().saveOrUpdateAll(railEmployees);
		for(RailEmployee railEmployee : railEmployees){
			TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
			int flag  = ApiSendMsgFlagEnums.TOOL.getCode();
			//发送信息 更新app人员列表
			tSendMsgSocketBO.sendSocketMsg(railEmployee.getDept(), railEmployee.getId(), ApiSendMsgFlagEnums.EMPLOYEE.getCode());
		}



		List<String> employeeCodes =  new ArrayList<String>();
		List<String> imgs = new  ArrayList<String>();
		String msg = "导入成功。";
		if(employeeCodes.size()!=0){
			msg = "但：员工号为"+employeeCodes.toString().substring(1,employeeCodes.toString().length()-1)+"存在重复未导入。";
		}
		if(imgs.size()!=0){
			msg = "但：员头像为"+imgs.toString().substring(1,imgs.toString().length()-1)+"存在重复未导入。";
		}
		return ResultUtils.success(msg);

	}
}

