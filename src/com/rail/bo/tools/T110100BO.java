package com.rail.bo.tools;

import java.util.List;
import java.util.Map;

import com.rail.po.base.RailEmployee;
import com.rail.po.tool.RailToolInfo;

public interface T110100BO {

	/**
	 * @param params
	 * @return
	 */
	RailToolInfo get(RailToolInfo railToolInfo)throws Exception;
	RailToolInfo getById(Long id);
	boolean checkINOrder(String toolOcde);
	RailToolInfo getByToolCode(RailToolInfo railToolInfo)throws Exception;
	/**
	 * @param params
	 * @return
	 */
	/*List listMap(Map<String, String> params)throws Exception;
	List listH(RailToolInfo railToolInfo)throws Exception;*/
	Map save(RailToolInfo railToolInfo);
	void del(RailToolInfo railToolInfo)throws Exception;
	void update(RailToolInfo railToolInfo)throws Exception;
	Map updateWhse(RailToolInfo railToolInfo)throws Exception;
	void updateAll(RailToolInfo railToolInfo)throws Exception;
	boolean checkFridPresent(String rfid);
	RailEmployee getByEmpoyeeCode(String employeeCode);
	Map addListFromExcel(List<RailEmployee> railEmployees);

}