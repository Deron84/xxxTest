package com.huateng.po;

import java.io.Serializable;


public class TblBrhTlrInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	public static String REF = "TblBrhTlrInfo";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_RESV1 = "Resv1";
	public static String PROP_TLR_STA = "TlrSta";
	public static String PROP_LAST_UPD_OPR_ID = "LastUpdOprId";
	public static String PROP_ID = "Id";
	public static String PROP_REC_UPD_TS = "RecUpdTs";

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblBrhTlrInfo () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public TblBrhTlrInfo (com.huateng.po.TblBrhTlrInfoPK id) {
		this.setId(id);
		initialize();
	}
	
	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.TblBrhTlrInfoPK id;

	// fields
	private java.lang.String tlrSta;
	private java.lang.String resv1;
	private java.lang.String lastUpdOprId;
	private java.lang.String recUpdTs;
	private java.lang.String recCrtTs;

	/**
	 * Constructor for required fields
	 */
	public TblBrhTlrInfo (
		com.huateng.po.TblBrhTlrInfoPK id,
		java.lang.String tlrSta) {
		this.setId(id);
		this.setTlrSta(tlrSta);
		initialize();
	}

	public com.huateng.po.TblBrhTlrInfoPK getId() {
		return id;
	}

	public void setId(com.huateng.po.TblBrhTlrInfoPK id) {
		this.id = id;
	}

	public java.lang.String getTlrSta() {
		return tlrSta;
	}

	public void setTlrSta(java.lang.String tlrSta) {
		this.tlrSta = tlrSta;
	}

	public java.lang.String getResv1() {
		return resv1;
	}

	public void setResv1(java.lang.String resv1) {
		this.resv1 = resv1;
	}

	public java.lang.String getLastUpdOprId() {
		return lastUpdOprId;
	}

	public void setLastUpdOprId(java.lang.String lastUpdOprId) {
		this.lastUpdOprId = lastUpdOprId;
	}

	public java.lang.String getRecUpdTs() {
		return recUpdTs;
	}

	public void setRecUpdTs(java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	public java.lang.String getRecCrtTs() {
		return recCrtTs;
	}

	public void setRecCrtTs(java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblBrhTlrInfo)) return false;
		else {
			com.huateng.po.TblBrhTlrInfo tblBrhTlrInfo = (com.huateng.po.TblBrhTlrInfo) obj;
			if (null == this.getId() || null == tblBrhTlrInfo.getId()) return false;
			else return (this.getId().equals(tblBrhTlrInfo.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}

	
/*[CONSTRUCTOR MARKER END]*/


}