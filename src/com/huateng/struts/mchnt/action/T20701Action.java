package com.huateng.struts.mchnt.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.mchnt.T20701BO;
import com.huateng.common.StringUtil;
import com.huateng.po.mchnt.TblHisDiscAlgo;
import com.huateng.po.mchnt.TblHisDiscAlgoPK;
import com.huateng.po.mchnt.TblInfDiscCd;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.JSONBean;

/**
 * Title:商户计费算法维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-06-16
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20701Action extends BaseSupport {

	private T20701BO t20701BO = (T20701BO) ContextUtil.getBean("T20701BO");

	/**
	 * 新增计费算法
	 * 
	 * @return 2011-8-8下午04:26:41
	 */
	public String add() {
		try {
			TblInfDiscCd inf = new TblInfDiscCd();

			String oprBrhId = getOperator().getOprBrhId();
			int max = 1;

			// 判断是否存在序号为0001的ID
//			String sql = "select count(1) from TBL_INF_DISC_CD where trim(DISC_CD) = '"
//					+ "JF" + oprBrhId.substring(0, 2) + "0001" + "'";
//			BigDecimal c = (BigDecimal) commQueryDAO.findBySQLQuery(sql).get(0);
//			if (c.intValue() != 0) {
//				sql = "select nvl(MIN(SUBSTR(DISC_CD,5,4) + 1),1) from TBL_INF_DISC_CD "
//						+ "where (SUBSTR(DISC_CD,5,4) + 1) not in (select (SUBSTR(DISC_CD,5,4) + 0) "
//						+ "from TBL_INF_DISC_CD where substr(DISC_CD,3,2) = '"
//						+ oprBrhId.substring(0, 2)
//						+ "') "
//						+ "and substr(DISC_CD,3,2) = '"
//						+ oprBrhId.substring(0, 2) + "'";
//				BigDecimal bg = (BigDecimal) commQueryDAO.findBySQLQuery(sql)
//						.get(0);
//				max = bg.intValue();
//				if (max > 9999) {
//					return returnService("该机构的计费算法已满");
//				}
//			}

//			discCd = "JF"
//					+ oprBrhId.substring(0, 2)
//					+ CommonFunction.fillString(String.valueOf(max), '0', 4,
//							false);

			if(StringUtil.isEmpty(discCd)){
				return returnService("计费算法编号不能为空！");
			}else if(t20701BO.getTblInfDiscCd(discCd) != null){
				return returnService("此计费代码编号已被使用！");
			}
			
			inf.setDiscCd(discCd);
			inf.setDiscOrg(oprBrhId);
			inf.setDiscNm(discNm);
			inf.setLastOperIn(mchtFlag);
			inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
			inf.setRecCrtTs(CommonFunction.getCurrentDateTime());
			inf.setRecUpdUserId(getOperator().getOprId());

			List<TblHisDiscAlgo> listHis = new ArrayList<TblHisDiscAlgo>();

			buildDiscAlgo(listHis);

			rspCode = t20701BO.createArith(inf, listHis);

			return returnService(rspCode);

		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}

	/**
	 * 更新计费算法
	 * 
	 * @return 2011-8-8下午04:26:32
	 */
	public String update() {
		try {
			TblInfDiscCd inf = t20701BO.getTblInfDiscCd(discCd);
			if (null == inf) {
				return returnService("没有找到计费算法信息，请刷新后重试！");
			}
			inf.setDiscNm(discNm);
			inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
			inf.setRecUpdUserId(getOperator().getOprId());

			List<TblHisDiscAlgo> listHis = new ArrayList<TblHisDiscAlgo>();

			buildDiscAlgo(listHis);

			rspCode = t20701BO.updateArith(inf, listHis);

			return returnService(rspCode);

		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}

	/**
	 * 删除计费算法
	 * 
	 * @return 2011-8-8下午04:26:23
	 */
	public String delete() {
		try {
			rspCode = t20701BO.deleteArith(discCd);
			return returnService(rspCode);
		} catch (Exception e) {
			return returnService(rspCode, e);
		}

	}

	public void buildDiscAlgo(List<TblHisDiscAlgo> listHis) {

		JSONBean jsonBean = new JSONBean();
		jsonBean.parseJSONArrayData(data);
		int len = jsonBean.getArray().size();

		for (int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));

			// //最低交易金额
			// String minCapital =
			// jsonBean.getJSONDataAt(i).getString("floorMount");
			// 回佣值
			String rate = jsonBean.getJSONDataAt(i).getString("feeValue");
			// 回佣类型
			String flag = jsonBean.getJSONDataAt(i).getString("flag");
			// 最低手续费
			String minTax = "0";
			// 最高手续费
			String maxTax = "9999999999999.99";

			if (jsonBean.getJSONDataAt(i).containsKey("minFee")
					&& !StringUtil.isNull(jsonBean.getJSONDataAt(i).getString(
							"minFee"))) {
				minTax = jsonBean.getJSONDataAt(i).getString("minFee");
			}

			if (jsonBean.getJSONDataAt(i).containsKey("maxFee")
					&& !StringUtil.isNull(jsonBean.getJSONDataAt(i).getString(
							"maxFee"))) {
				maxTax = jsonBean.getJSONDataAt(i).getString("maxFee");
			}

//			 交易代码
			String txnNum = jsonBean.getJSONDataAt(i).getString("txnNum");
			String cardType = jsonBean.getJSONDataAt(i).getString("cardType");

			TblHisDiscAlgo temp = new TblHisDiscAlgo();
			TblHisDiscAlgoPK key = new TblHisDiscAlgoPK();
			key.setDiscId(discCd);
			key.setIndexNum(i);
			temp.setId(key);
			temp.setMinFee(new BigDecimal(minTax));
			temp.setMaxFee(new BigDecimal(maxTax));
			temp.setTxnNum(txnNum);
			temp.setCardType(cardType);
			temp.setFeeValue(new BigDecimal(rate));
			// temp.setFloorMount(BigDecimal.valueOf(CommonFunction.getDValue(minCapital,
			// _defaultDoutbl)));
			temp.setFloorMount(BigDecimal.valueOf(0));
			temp.setFlag(CommonFunction.getInt(flag, -1));
			temp.setUpperMount(new BigDecimal("0"));
			temp.setRecUpdUsrId(getOperator().getOprId());
			temp.setRecUpdTs(CommonFunction.getCurrentDateTime());
			temp.setRecCrtTs(CommonFunction.getCurrentDateTime());

			listHis.add(temp);
		}
	}

	private final static Double _defaultDoutbl = new Double(0);

	public TblHisDiscAlgoPK id;

	public java.math.BigDecimal minFee;

	public java.math.BigDecimal maxFee;

	public java.math.BigDecimal floorMount;

	public java.math.BigDecimal upperMount;

	public java.lang.Integer flag;

	public java.math.BigDecimal feeValue;

	public java.lang.String recUpdUsrId;

	public java.lang.String txnNum;

	public String data;

	// 手续费类型IDdisc_cd
	public java.lang.String discCd;

	// 手续费名称
	public java.lang.String discNm;

	// 手续费所属机构
	public java.lang.String discOrg;

	// 最后操作状态
	public java.lang.String recUpdUserId;
	
	//区分传统pos和Epos计费算法
	public java.lang.String mchtFlag;

	public TblHisDiscAlgoPK getId() {
		return id;
	}

	public void setId(TblHisDiscAlgoPK id) {
		this.id = id;
	}

	public java.math.BigDecimal getMinFee() {
		return minFee;
	}

	public void setMinFee(java.math.BigDecimal minFee) {
		this.minFee = minFee;
	}

	public java.math.BigDecimal getMaxFee() {
		return maxFee;
	}

	public void setMaxFee(java.math.BigDecimal maxFee) {
		this.maxFee = maxFee;
	}

	public java.math.BigDecimal getFloorMount() {
		return floorMount;
	}

	public void setFloorMount(java.math.BigDecimal floorMount) {
		this.floorMount = floorMount;
	}

	public java.math.BigDecimal getUpperMount() {
		return upperMount;
	}

	public void setUpperMount(java.math.BigDecimal upperMount) {
		this.upperMount = upperMount;
	}

	public java.lang.Integer getFlag() {
		return flag;
	}

	public void setFlag(java.lang.Integer flag) {
		this.flag = flag;
	}

	public java.math.BigDecimal getFeeValue() {
		return feeValue;
	}

	public void setFeeValue(java.math.BigDecimal feeValue) {
		this.feeValue = feeValue;
	}

	public java.lang.String getRecUpdUsrId() {
		return recUpdUsrId;
	}

	public void setRecUpdUsrId(java.lang.String recUpdUsrId) {
		this.recUpdUsrId = recUpdUsrId;
	}

	public java.lang.String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(java.lang.String txnNum) {
		this.txnNum = txnNum;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public java.lang.String getDiscCd() {
		return discCd;
	}

	public void setDiscCd(java.lang.String discCd) {
		this.discCd = discCd;
	}

	public java.lang.String getDiscNm() {
		return discNm;
	}

	public void setDiscNm(java.lang.String discNm) {
		this.discNm = discNm;
	}

	public java.lang.String getDiscOrg() {
		return discOrg;
	}

	public void setDiscOrg(java.lang.String discOrg) {
		this.discOrg = discOrg;
	}

	public java.lang.String getRecUpdUserId() {
		return recUpdUserId;
	}

	public void setRecUpdUserId(java.lang.String recUpdUserId) {
		this.recUpdUserId = recUpdUserId;
	}

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

	public java.lang.String getMchtFlag() {
		return mchtFlag;
	}

	public void setMchtFlag(java.lang.String mchtFlag) {
		this.mchtFlag = mchtFlag;
	}
	
	
}
