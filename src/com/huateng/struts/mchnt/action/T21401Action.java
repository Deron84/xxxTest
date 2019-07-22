package com.huateng.struts.mchnt.action;

import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.huateng.bo.mchnt.T21401BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.po.mchnt.TblMchntMap;
import com.huateng.po.mchnt.TblMchntMapPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.InformationUtil;

public class T21401Action extends BaseAction{
	 private static Logger logger = Logger.getLogger(T21401Action.class);
	T21401BO t21401BO = (T21401BO) ContextUtil.getBean("T21401BO");
	private String mappingId;
	private String mchntId;
    private String equipmentId;
    private String acquiresId;
    private String remark;
    private String acmchntId;
    private String acequipmentId;
    
    
	@Override
	protected String subExecute() throws Exception {
        if ("add".equals(method)) {
            rspCode = add();
        } else if ("delete".equals(method)) {
            rspCode = delete();
        } else if ("update".equals(method)) {
            rspCode = update();
        }
        return rspCode;
	}

	private String update() {
		TblMchntMapPK tblMchntMapPK = new TblMchntMapPK(mappingId);
		TblMchntMap bean = t21401BO.get(tblMchntMapPK);
		// 判断商户号、终端号、收单方三个作为逻辑主键，不能重复
		List<Object[]> maps = t21401BO.findMappingListByThreeId(mchntId, equipmentId, acquiresId);
		if (maps != null && maps.size() > 0) {
			if (!StringUtils.equals(mappingId, maps.get(0)[0].toString())) {
				return "该终端号、收单方已绑定！";
			}
		}

		bean.setAcquiresId(acquiresId);
		bean.setEquipmentId(equipmentId);
		bean.setMchntId(mchntId);
		if (StringUtils.isNotBlank(remark)) {
			bean.setRemark(remark);
		}
		bean.setAcmchntId(acmchntId);
		bean.setAcequipmentId(acequipmentId);
		t21401BO.update(bean);
		return Constants.SUCCESS_CODE;
	}

	private String delete() {
		return t21401BO.delete(mappingId);
	}

	private String add() {
		mappingId = InformationUtil.getMchtMappId();
		TblMchntMap bean = new TblMchntMap();
		TblMchntMapPK tblMchntMapPK = new TblMchntMapPK(mappingId);
		int countNum = t21401BO.countNum(tblMchntMapPK);
		if (countNum != 0) {
			return "该条数据已存在！";
		}
		// 判断商户号、终端号两个作为逻辑主键，不能重复
		List<Object[]> maps = t21401BO.findMappingListByThreeId(mchntId, equipmentId, acquiresId);
		if (maps != null && maps.size() > 0) {
			return "该商户号、终端号、收单方已存在！";
		}
		bean.setId(tblMchntMapPK);
		bean.setAcquiresId(acquiresId);
		bean.setEquipmentId(equipmentId);
		bean.setMchntId(mchntId);
		bean.setAcmchntId(acmchntId);
		bean.setAcequipmentId(acequipmentId);
		// 更新时间和柜员
		bean.setCreateTime(CommonFunction.getCurrentDateTime());
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);
		bean.setCreatePerson(opr.getOprId());
		if (StringUtils.isNotBlank(remark)) {
			bean.setRemark(remark);
		}
		t21401BO.add(bean);
		return Constants.SUCCESS_CODE;

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

	public String getAcquiresId() {
		return acquiresId;
	}

	public void setAcquiresId(String acquiresId) {
		this.acquiresId = acquiresId;
	}

	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}

	

	public T21401BO getT21401BO() {
		return t21401BO;
	}

	public void setT21401BO(T21401BO t21401bo) {
		t21401BO = t21401bo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAcmchntId() {
		return acmchntId;
	}

	public void setAcmchntId(String acmchntId) {
		this.acmchntId = acmchntId;
	}

	public String getAcequipmentId() {
		return acequipmentId;
	}

	public void setAcequipmentId(String acequipmentId) {
		this.acequipmentId = acequipmentId;
	}

	
}
