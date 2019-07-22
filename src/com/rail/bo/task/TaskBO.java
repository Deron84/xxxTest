package com.rail.bo.task;

/**  
* @author syl
* @version 创建时间：2019年4月15日 上午9:23:37  
* @desc
*/
public interface TaskBO  {
	// 报废定时任务
	public void railToolMaintainWarnTaskForFei();
	// 维修定时任务
	public void railToolMaintainWarnTaskForRepaire();
	//门禁维修
	public void railAccessWarnTaskForRepaire();
	//工单结束预警 未关门 未入网工单未结束
	public void railWorkWarn();
	
	//工单审核通过，到了天窗开始时间但是未开始具体的工单
	public void railWorkWarnBefore();
	
	//工单审核通过，到了天窗开始时间但是未入网具体的工单
	public void railWorkWarnInNet();
	
	//工单审核通过，到了天窗开始时间但是未出网具体的工单
	public void railWorkWarnOutNet();
}

