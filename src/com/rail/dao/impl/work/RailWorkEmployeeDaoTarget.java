package com.rail.dao.impl.work;

import java.util.List;
import java.util.Map;

import com.rail.dao.iface.work.RailWorkEmployeeDao;
import com.rail.po.warehouse.RailWhseInfo;
import com.rail.po.work.RailWorkEmployee;
import com.sdses.dao._RootDAO;

public class RailWorkEmployeeDaoTarget extends _RootDAO<RailWhseInfo> implements RailWorkEmployeeDao {

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return null;
	}

	
	}
