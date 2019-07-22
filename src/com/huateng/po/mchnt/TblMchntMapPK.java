package com.huateng.po.mchnt;

import com.huateng.po.mchnt.base.BaseTblHisDiscAlgoPK;

public class TblMchntMapPK extends BaseTblHisDiscAlgoPK{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7943205445624559587L;
	
	
	private String mappingId;
	
	public TblMchntMapPK(){}
    public TblMchntMapPK(String mappingId) {
        this.mappingId = mappingId;
    }
	public String getMappingId() {
		return mappingId;
	}
	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}

    
}
