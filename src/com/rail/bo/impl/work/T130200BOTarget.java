package com.rail.bo.impl.work;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.work.T130200BO;
import com.rail.dao.iface.work.RailWorkImgDao;
import com.rail.po.work.RailWorkImg;

public class T130200BOTarget  implements T130200BO{

private RailWorkImgDao railWorkImgDao;
	

	public RailWorkImgDao getRailWorkImgDao() {
	return railWorkImgDao;
}

public void setRailWorkImgDao(RailWorkImgDao railWorkImgDao) {
	this.railWorkImgDao = railWorkImgDao;
}

	@Override
	public String updateAccess(RailWorkImg railWorkImg) {
		// TODO Auto-generated method stub
		return railWorkImgDao.updateAccess(railWorkImg);
	}

	@Override
	public RailWorkImg getByCode(String railWorkImgId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", railWorkImgId);
		List<RailWorkImg> raws = railWorkImgDao.getByParam(paramMap);
		RailWorkImg railWorkImg = new RailWorkImg();
		if(raws!=null&&!raws.isEmpty()){
			railWorkImg = raws.get(0);
		}
		System.out.println("qiufulong======="+railWorkImg);
		return railWorkImg;
	}

	@Override
	public List<RailWorkImg> getByParam(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String add(RailWorkImg railWorkImg) {
		// TODO Auto-generated method stub
		return railWorkImgDao.add(railWorkImg);
	}

	@Override
	public String upload(RailWorkImg railWorkImg) {
		// TODO Auto-generated method stub
		return railWorkImgDao.add(railWorkImg);
	}

}

