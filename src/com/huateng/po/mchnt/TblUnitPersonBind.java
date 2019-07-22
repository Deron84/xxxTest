package com.huateng.po.mchnt;

import java.io.Serializable;


/**
 * project JSBConsole
 * date 2013-3-11
 * @author 樊东东
 * TBL_UNIT_PERSON_BIND
 */
public class TblUnitPersonBind implements Serializable{
	
	/**
	 * 
	 */
	public TblUnitPersonBind() {
		// TODO Auto-generated constructor stub
	}
	
	private TblUnitPersonBindPK id; 
	/**创建人柜员号 INIT_OPR_ID*/
	private String initOprId;
	/**最后修改人柜员号  MODI_OPR_ID*/
	private String modiOprId;
	/**创建时间  INIT_TIME*/
	private String initTime;
	/**最后修改时间  MODI_TIME*/
	private String modiTime;
	
	
	public TblUnitPersonBind(TblUnitPersonBindPK id) {
		super();
		this.id = id;
	}


	public TblUnitPersonBindPK getId() {
		return id;
	}

	
	public void setId(TblUnitPersonBindPK id) {
		this.id = id;
	}

	public String getInitOprId() {
		return initOprId;
	}
	
	public void setInitOprId(String initOprId) {
		this.initOprId = initOprId;
	}
	
	public String getModiOprId() {
		return modiOprId;
	}
	
	public void setModiOprId(String modiOprId) {
		this.modiOprId = modiOprId;
	}
	
	public String getInitTime() {
		return initTime;
	}
	
	public void setInitTime(String initTime) {
		this.initTime = initTime;
	}
	
	public String getModiTime() {
		return modiTime;
	}
	
	public void setModiTime(String modiTime) {
		this.modiTime = modiTime;
	}
	
	
	
}
