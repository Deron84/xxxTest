package com.rail.dao.iface.work;

import java.util.List;
import java.util.Map;

import com.rail.po.work.RailWorkImg;


public interface RailWorkImgDao {
	/**
	 * 根据参数条件查询作业现场图片视频数据
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return List<RailWorkImg>  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public List<RailWorkImg> getByParam(Map<String, String> paramMap);
	/**
	 * 新增作业现场图片视频
	 * @Description: TODO
	 * @param @param railWorkImg
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String add(RailWorkImg railWorkImg);
	/**
	 * 修改作业现场图片视频信息
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String updateAccess(RailWorkImg railWorkImg);
	/**
	 * 根据作业现场图片视频编码获得作业现场图片视频信息
	 * @Description: TODO
	 * @param @param AccessCode
	 * @param @return   
	 * @return RailAccessWarn  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public RailWorkImg getByCode(String railWorkImgId);
}
