package com.rail.dao.impl.work;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.work.RailWorkImgDao;
import com.rail.po.work.RailWorkImg;
import com.sdses.dao._RootDAO;

public class RailWorkImgDaoTarget  extends _RootDAO<RailWorkImg> implements RailWorkImgDao{

	@Override
	public List<RailWorkImg> getByParam(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
				String querySql = "from RailWorkImg where 1=1 ";
				for (String key : paramMap.keySet()) {
					String value = paramMap.get(key);
					querySql += " and " + key + "='" + value + "'";
				}
				System.out.println("根据参数查询门禁querySql:" + querySql);
				return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailWorkImg railWorkImg) {
		// TODO Auto-generated method stub
		try {
			super.save(railWorkImg);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String updateAccess(RailWorkImg railWorkImg) {
		// TODO Auto-generated method stub
		try {
			super.update(railWorkImg);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailWorkImg getByCode(String railWorkImgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return null;
	}

	
}

