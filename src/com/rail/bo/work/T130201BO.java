package com.rail.bo.work;

import java.util.List;
import java.util.Map;

import com.rail.po.work.RailWorkImg;

public interface  T130201BO {
	/**
	 * 更新作业现场图片视频信息
	 * @Description: TODO
	 * @param @param railAccessInfo
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String updateAccess(RailWorkImg railWorkImg);
	
	/**
	 * 根据作业现场图片视频code获得作业现场图片视频信息
	 * @Description: TODO
	 * @param @param accessCode
	 * @param @return   
	 * @return RailAccessInfo  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public RailWorkImg getByCode(String railWorkImgId);
	/**
	 * 根据参数条件查询作业现场图片视频数据
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return List<RailAccessInfo>  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public List<RailWorkImg> getByParam(Map<String,String> paramMap);
	
	/**
	 * 新增作业现场图片视频
	 * @Description: TODO
	 * @param @param railWhseInfo   
	 * @return void  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月16日
	 */
	public String add(RailWorkImg railWorkImg);
}
