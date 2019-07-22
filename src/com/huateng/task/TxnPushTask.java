package com.huateng.task;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.system.util.HttpHandler;
import com.huateng.system.util.SysParamUtil;


@Component
public class TxnPushTask {
	private static Logger logger = Logger.getLogger(TxnPushTask.class);
	
	private ICommQueryDAO commQueryDAO;
	//
	//@Scheduled(cron="0 */1 * * * ?")
	public void txnPush(){
		logger.info("=============实时交易推送开始=============");
		
		/**
		 * 未发送的已经完成的消费交易
		 */
		String sql = "select txn.card_accp_id,trim(txn.AMT_TRANS),txn.sys_seq_num,txn.inst_date,base.mcht_nm,txn.CARD_ACCP_TERM_ID,(txn.AMT_SETTLMT-txn.AMT_TRANS) from tbl_n_txn txn "
				+ "left join tbl_mcht_base_inf base on base.MAPPING_MCHNTCDONE = txn.card_accp_id OR base.MAPPING_MCHNTCDTWO = txn.card_accp_id "
		        + "where txn.is_send is null and txn.resp_code='00' and txn.trans_type='P' and txn.revsal_flag='0' order by txn.inst_date asc";
		/**
		 * 已发送的经过冲正的消费交易
		 */
		String sql2 = "select txn.card_accp_id,trim(txn.AMT_TRANS),txn.sys_seq_num,txn.inst_date,base.mcht_nm,txn.CARD_ACCP_TERM_ID,(txn.AMT_SETTLMT-txn.AMT_TRANS) from tbl_n_txn txn "
				+ "left join tbl_mcht_base_inf base on base.MAPPING_MCHNTCDONE = txn.card_accp_id OR base.MAPPING_MCHNTCDTWO = txn.card_accp_id "
				+ "where txn.is_send='S' and txn.resp_code='00' and txn.trans_type='P' and txn.revsal_flag='1'  order by txn.inst_date asc";
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		List<Object[]> dataList2 = commQueryDAO.findBySQLQuery(sql2);
		send(dataList,true);
		send(dataList2,false);

		logger.info("=============实时交易推送结束=============");
	}
	
	
	/**
	 * 
	 * @param dataList
	 * @param flag true:消费交易;false:冲正交易
	 */
	private void send(List<Object[]> dataList,boolean flag){
		if(dataList != null	&& dataList.size() > 0){
			for(Object[] arr : dataList){
				
				String queryWechatIdSql = "select wechat_id from tbl_mchnt_user u left join tbl_mcht_base_inf  b on u.mchnt_id = b.mcht_no where b.MAPPING_MCHNTCDONE = trim('"+arr[0]+"') OR b.MAPPING_MCHNTCDTWO = trim('"+arr[0]+"') ";
				List wechatIdList = commQueryDAO.findBySQLQuery(queryWechatIdSql);
				
				if(wechatIdList==null|| wechatIdList.size() == 0||wechatIdList.get(0)==null){
					continue;
				}
				
				if(wechatIdList.get(0)!=null && (StringUtils.isEmpty(wechatIdList.get(0).toString())||wechatIdList.get(0).toString().length()<6)){
					continue;
				}
				
				String amt = arr[1]==null?"0":arr[1].toString();	
				String amt2=arr[6]==null?"0":arr[6].toString();
				amt=transFenToYuan(amt);
				amt2=transFenToYuan(amt2);
				
				String timeStr = arr[3].toString();
				String str = timeStr.substring(0, 4)+"年"+timeStr.substring(4, 6)+"月"+timeStr.substring(6, 8)+"日"+timeStr.substring(8, 10)+"时"+timeStr.substring(10, 12)+"分"+timeStr.substring(12, 14)+"秒";
				
				String mchtName ="尊敬的用户（"+arr[0]+"-"+arr[5]+"）您好";
				
				String SUBMIT_URL = SysParamUtil.getParam("SUBMITURL")+"?s=/home/api/api.html";
				String httpgetUrl ="";
				if(flag){
//					httpgetUrl = SUBMIT_URL + "&apiName=sendmsg&openid="+wechatIdList.get(0)+"&title="+mchtName+"&amount=人民币"+amt+"元&trans_time="+str+"&pay_type=消费&remark=备注:本交易参与优惠活动，实际交易金额"+amt+"元，补贴金额"+amt2+"元";
					httpgetUrl = SUBMIT_URL + "&apiName=sendmsg&openid="+"omu-JwIEo8ENWnJ0HbHNXt2JZMZY"+"&title="+mchtName+"&amount=人民币"+amt+"元&trans_time="+str+"&pay_type=消费&remark=备注:本交易参与优惠活动，实际交易金额"+amt+"元，补贴金额"+amt2+"元";
				}else{
					httpgetUrl = SUBMIT_URL + "&apiName=sendmsg&openid="+wechatIdList.get(0)+"&title="+mchtName+"&amount=人民币"+amt+"元&trans_time="+str+"&pay_type=冲正&remark=备注:本交易参与优惠活动，实际交易金额"+amt+"元，补贴金额"+amt2+"元";
				}
				//发推送接口
				JSONObject result = HttpHandler.httpGet(httpgetUrl);
				if(result == null){
					logger.info("=============实时交易返回为null=============");
				}else{
					String code = result.get("code").toString();
					if("1000".equals(code)){
						if(flag){
						//消息推送接口发生成功，更新交易状态
						String updateSql = "update tbl_n_txn set is_send='S' where sys_seq_num="+arr[2]+" and inst_date='"+arr[3]+"'";
						commQueryDAO.excute(updateSql);
						logger.info("=============实时消费交易更新=============");
						}else{
							//消息推送接口发生成功，更新交易状态
							String updateSql = "update tbl_n_txn set is_send='Y' where sys_seq_num="+arr[2]+" and inst_date='"+arr[3]+"'";
							commQueryDAO.excute(updateSql);
							logger.info("=============实时冲正交易更新=============");
						}
					}else{
						logger.info("=============无效数据=============");
					}
				}
			}
		}
	}

	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}
	
	private String transFenToYuan(String str) {
		if(str == null || "".equals(str.trim()))
			return "";
		BigDecimal bigDecimal = new BigDecimal(str.trim());
		return bigDecimal.movePointLeft(2).toString();
	}
	
}
