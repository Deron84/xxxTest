package com.huateng.po.mchnt;

import java.io.Serializable;

/**
 * 商户映射列表
 * @author Administrator
 *
 */
public class TblMchntMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2426793491868827922L;
	
	
	//主键
    private TblMchntMapPK id;
    
    private String mchntId;
    
    private String equipmentId;
    
    private String acquiresId;
    private String createTime; 
	private String createPerson;
	private String remark;
	private String acmchntId;
	private String acequipmentId;
	

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

	public TblMchntMapPK getId() {
		return id;
	}

	public void setId(TblMchntMapPK id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
