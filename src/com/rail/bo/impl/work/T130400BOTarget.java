package com.rail.bo.impl.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.pub.ResultUtils;
import com.rail.bo.work.T130400BO;
import com.rail.dao.iface.work.RailEmployeeDao;
import com.rail.po.base.RailEmployee;

/**  
* @author zhaozc
* @version 创建时间：2019年4月15日 上午9:23:37  
* @desc 班组管理
*/
public class T130400BOTarget implements T130400BO {

	private RailEmployeeDao railEmployeeDao;

	@Override
	public List<RailEmployee> getByParam(Map<String, String> params) {
		return railEmployeeDao.getByParam(params);
	}
	@Override
	public String add(RailEmployee railEmployee) {
		return railEmployeeDao.add(railEmployee);
	}
	@Override
	public Map addListFromExcel(List<RailEmployee> railEmployees) {
		if(railEmployees.size()==0){
			return ResultUtils.success("未导入人员信息");
		}
		HashMap<String, String> map = new HashMap<String, String>();
		//map.put("", value);
		List<RailEmployee> finds = getByParam(map);
		List<String> employeeCodes =  new  ArrayList<String>(); 
		List<String> imgs = new  ArrayList<String>();
		for(RailEmployee  im : railEmployees){
//			if(checkExist(employeeCodes, imgs, finds, im)){
//				continue;
//			}
			railEmployeeDao.add(im);
		}
		String msg = "导入成功。";
		if(employeeCodes.size()!=0){
			msg = "但：员工号为"+employeeCodes.toString().substring(1,employeeCodes.toString().length()-1)+"存在重复未导入。";
		}
		if(imgs.size()!=0){
			msg = "但：员头像为"+imgs.toString().substring(1,imgs.toString().length()-1)+"存在重复未导入。";
		}
		return ResultUtils.success(msg);
	}
	private boolean checkExist(List<String> employeeCodes,List<String> imgs,List<RailEmployee> finds,RailEmployee  im){
		for(RailEmployee f: finds){
			if(f.getEmployeeCode()==null || f.getEmployeeImg()==null){
				continue;
			}
			if(f.getEmployeeCode().equals(im.getEmployeeCode())){
				employeeCodes.add(im.getEmployeeCode());
				return true;
			}
			if(f.getEmployeeImg().equals(im.getEmployeeImg())){
				imgs.add(im.getEmployeeImg());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkExistOnly(String employeeCode, String img, List<RailEmployee> finds){
		for(RailEmployee f: finds){
			if(f==null){
				continue;
			}
			if(employeeCode!=null && f.getEmployeeCode()!=null && f.getEmployeeCode().equals(employeeCode)){
				return true;
			}
			if(img!=null && f.getEmployeeImg()!=null && f.getEmployeeImg().equals(img)){
				return true;
			}
		}
		return false;
	}

	
	
	
	@Override
	public String update(RailEmployee railEmployee) {
		return railEmployeeDao.update(railEmployee);
	}
	@Override
	public String delete(RailEmployee railEmployee) {
		return railEmployeeDao.delete(railEmployee);
	}
	@Override
	public RailEmployee getById(String id) {
		return railEmployeeDao.getById(id);
	}

	@Override
	public RailEmployee getByEmpoyeeCode(String employeeCode) {



		return null;
	}

	@Override
	public RailEmployee getByCode(String accessWarnId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("employee_Code", accessWarnId);
		List<RailEmployee> raws = railEmployeeDao.getByParam(paramMap);
		RailEmployee railEmployee = new RailEmployee();
		if(raws!=null&&!raws.isEmpty()){
			railEmployee = raws.get(0);
		}
		return railEmployee;
	}

	public RailEmployeeDao getRailEmployeeDao() {
		return railEmployeeDao;
	}

	public void setRailEmployeeDao(RailEmployeeDao railEmployeeDao) {
		this.railEmployeeDao = railEmployeeDao;
	}
	@Override
	public int getInWorkByCode(String employeeCode) {
		return railEmployeeDao.getInWorkByCode(employeeCode);
	}
	
}
