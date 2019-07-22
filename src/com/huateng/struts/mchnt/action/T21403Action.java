package com.huateng.struts.mchnt.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.huateng.bo.mchnt.T21402BO;
import com.huateng.bo.mchnt.T21403BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.po.mchnt.TblMchntDiscountRule;
import com.huateng.po.mchnt.TblMchntDiscountRuleBind;
import com.huateng.po.mchnt.TblMchntDiscountRuleBindPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.InformationUtil;

/**
 * 优惠规则绑定
 * @author Administrator
 *
 */
public class T21403Action extends BaseAction{
	 private static Logger logger = Logger.getLogger(T21403Action.class);
	T21403BO t21403BO = (T21403BO) ContextUtil.getBean("T21403BO");
	private String bindId;
	private String mchntId;
	private String equipmentId;
	private String acquirersId;
	private String discountId;
	private String acquirersType;	
	private String isDownload;
	private String openLian;	
	private String activityId;
	private String activityDesc;
	private String isOpen;
	private String all;
	
	@Override
	protected String subExecute() throws Exception {
        if ("add".equals(method)) {
            rspCode = add();
        }else if ("delete".equals(method)) {
            rspCode = delete();
        } else if ("update".equals(method)) {
            rspCode = update();
        }
        return rspCode;
	}

	private String update() {
		if (StringUtils.isNotBlank(bindId)) {			
			TblMchntDiscountRuleBindPK idPK = new TblMchntDiscountRuleBindPK(
					bindId);
			TblMchntDiscountRuleBind bean = t21403BO.get(idPK);
			bean.setIsOpen(isOpen);
			t21403BO.update(bean);
			return Constants.SUCCESS_CODE;

		} else {
			return "传入后台系统参数丢失，请联系管理员!";
		}
	}

	private String delete() {
		if (StringUtils.isNotBlank(bindId)) {			
			TblMchntDiscountRuleBindPK idPK = new TblMchntDiscountRuleBindPK(
					bindId);
			return t21403BO.delete(idPK);

		} else {
			return "传入后台系统参数丢失，请联系管理员!";
		}

	}

	private String add() {		
		if (StringUtils.isNotBlank(all)) {
			T21402BO t21402BO = (T21402BO) ContextUtil.getBean("T21402BO");
			TblMchntDiscountRule rule=null;
			if(StringUtils.isNotBlank(discountId)){
				rule = t21402BO.get(discountId);
			}
			List<Object[]> dataList =new ArrayList<Object[]>();
			if(StringUtils.equals(all, "1")){
				dataList.addAll(t21403BO.findTermListAllOne());
				dataList.addAll(t21403BO.findTermListAllTwo());
				
			}else if(StringUtils.equals(all, "2")){
				dataList.addAll(t21403BO.findTermListAllOneMchntStr(mchntId));
				dataList.addAll(t21403BO.findTermListAllTwoMchntStr(mchntId));
			}else if(StringUtils.equals(all, "3")){
				dataList.addAll(t21403BO.findTermListAllOneTermStr(equipmentId));
				dataList.addAll(t21403BO.findTermListAllTwoTermStr(equipmentId));
			}
			
			for (Object[] obj : dataList) {			
				//校验该终端号是否已被绑定且所选规则的时间是否冲突
				List<Object[]> binds=t21403BO.findByTermIdTimes(obj[1].toString(),rule.getStartTimeEff(),rule.getEndTimeEff());
				if(binds!=null&&binds.size()>0){
					return "该终端号:"+obj[1]+"已绑定此时间段的优惠规则，不能再次绑定!";
				}
			}
			
			for (Object[] obj : dataList) {
				TblMchntDiscountRuleBind bean = new TblMchntDiscountRuleBind();
				TblMchntDiscountRuleBindPK idPK = new TblMchntDiscountRuleBindPK(InformationUtil.getDiscountRuleBindId());
				bean.setId(idPK);
				bean.setMchntId(obj[0].toString());
				bean.setEquipmentId(obj[1].toString());
				bean.setAcquirersId(obj[2].toString());
				bean.setCreateTime(CommonFunction.getCurrentDateTime());
				bean.setAcquirersType(acquirersType);
				//下发内容
								
				if(rule!=null){
					//规则的有效时间赋给规则绑定表
					bean.setStartTime(rule.getStartTimeEff());
					bean.setEndTime(rule.getEndTimeEff());
					bean.setDiscountValue(rule.getDiscountValueInfo());
					bean.setDiscountValueInfo(rule.getIssuedContent());//下发内容
				}
				bean.setDiscountId(discountId);
				//新增字段
				bean.setActivityId(activityId);
				bean.setActivityDesc(activityDesc);
				bean.setIsOpen(isOpen);
				//创建人
				Operator opr = (Operator) ServletActionContext.getRequest()
						.getSession().getAttribute(Constants.OPERATOR_INFO);
				bean.setCreatePerson(opr.getOprId());				
				t21403BO.add(bean);
			}
			return Constants.SUCCESS_CODE;
		} else {
			return "传入后台系统参数丢失，请联系管理员!";
		}
	}
	
	

	public T21403BO getT21403BO() {
		return t21403BO;
	}

	public void setT21403BO(T21403BO t21403bo) {
		t21403BO = t21403bo;
	}

	public String getDiscountId() {
		return discountId;
	}

	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}

	public String getMchntId() {
		return mchntId;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getAcquirersId() {
		return acquirersId;
	}

	public void setAcquirersId(String acquirersId) {
		this.acquirersId = acquirersId;
	}

	public String getAcquirersType() {
		return acquirersType;
	}

	public void setAcquirersType(String acquirersType) {
		this.acquirersType = acquirersType;
	}

	

	public String getIsDownload() {
		return isDownload;
	}

	public void setIsDownload(String isDownload) {
		this.isDownload = isDownload;
	}

	public String getOpenLian() {
		return openLian;
	}

	public void setOpenLian(String openLian) {
		this.openLian = openLian;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		this.all = all;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}
	
}
