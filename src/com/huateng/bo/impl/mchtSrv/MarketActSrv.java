package com.huateng.bo.impl.mchtSrv;

import java.util.List;

import com.huateng.po.mchtSrv.TblMarketAct;
import com.huateng.po.mchtSrv.TblMarketActReview;
import com.huateng.po.mchtSrv.TblMchntParticipatReview;

public interface MarketActSrv {
	/**
	 * 获取参与商户
	 * @param actNo
	 * @param mchntNo
	 * @return
	 */
	public TblMchntParticipatReview getMchnt(String actNo,String mchntNo);
	/**
	 * 获取正式表信息
	 * @param actNo
	 * @param mchntNo
	 * @return
	 */
	public TblMarketAct getActInfo(String actNo);
	/**
	 * 获取营销活动
	 * @param actNo
	 * @return
	 */
	public TblMarketActReview getMarketAct(String actNo);
	/**
	 * 生成活动编号
	 * @param oprBrhId
	 * @return
	 */
	public String getActNo(String oprBrhId);
	/**
	 * 存储商业营销活动
	 * @param tblMarketAct
	 * @return
	 */
	public String save(TblMarketActReview tblMarketActReview,String[] selectedOptions,String[] actFees);
	public String saveNew(TblMarketActReview tblMarketActReview,String[] selectedOptions);
	/**
	 * 修改商业营销活动
	 * @param tblMarketActReview
	 * @return
	 */
	public String update(TblMarketActReview tblMarketActReview);
	/**
	 * 删除参与商户
	 * @param actNo
	 * @param selectedOptions
	 * @return
	 */
	public String delMchnts(String actNo,String[] selectedOptions,String oprId);
	public String delMchntsNew(String[] actNo,String[] selectedOptions,String oprId);
	/**
	 * 增加参与商户信息
	 * @param actNo
	 * @param selectedOptions
	 * @param actFees
	 * @return
	 */
	public String addMchnt(String actNo,String[] selectedOptions,String[] actFees,String oprId);
	public String addMchntNew(String actNo,String[] selectedOptions,String oprId);
	/**
	 * 商户营销活动审核
	 * @param actNo
	 * @param oprId
	 * @return
	 */
	public String check(String actNo,String oprId,List<TblMchntParticipatReview> list);
	/**
	 * 商户营销活动审核拒绝
	 * @param actNo
	 * @param oprId
	 * @return
	 */
	public String refuse(String actNo,String oprId);
	/**
	 * 拒绝商户变更
	 * @param actNo
	 * @param mchntNo
	 * @param oprId
	 * @return
	 */
	public String refuseMchnt(String actNo,String mchntNo,String oprId);
	/**
	 * 审核商户变更
	 * @param actNo
	 * @param mchntNo
	 * @param oprId
	 * @return
	 */
	public String checkMchnt(String actNo,String mchntNo,String oprId);
	/**
	 * 批量商户变更
	 * @param actNo
	 * @param mchntNo
	 * @param oprId
	 * @return
	 */
	public String checkMchnt(String[] actNo,String[] mchntNo,String oprId);
	/**
	 * 判断其他活动里面是否有该商户
	 * @param actNo
	 * @param mchntNo
	 * @return 
	 */
	public String isExistMchntNo(String actNo,String mchntNo);
}
