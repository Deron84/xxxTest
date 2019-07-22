package com.huateng.po.base;

import java.io.Serializable;


public class TblEmvPara  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static String REF = "TblEmvPara";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_PARA_STA = "ParaSta";
	public static String PROP_PARA_VAL = "ParaVal";
	public static String PROP_REC_OPR_ID = "RecOprId";
	public static String PROP_REC_UPD_OPR = "RecUpdOpr";
	public static String PROP_PARA_ID = "ParaId";
	public static String PROP_PARA_LEN = "ParaLen";
	public static String PROP_GEN_FLAG = "GenFlag";
	public static String PROP_ID = "Id";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_PARA_ORG = "ParaOrg";


/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblEmvPara () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public TblEmvPara (com.huateng.po.base.TblEmvParaPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.base.TblEmvParaPK id;

	// fields
	private java.lang.String paraOrg;
	private java.lang.String paraId;
	private java.lang.String paraSta;
	private java.lang.String paraLen;
	private java.lang.String paraVal;
	private java.lang.String genFlag;
	private java.lang.String recOprId;
	private java.lang.String recUpdOpr;
	private java.lang.String recCrtTs;
	private java.lang.String recUpdTs;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.base.TblEmvParaPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.base.TblEmvParaPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: PARA_ORG
	 */
	public java.lang.String getParaOrg () {
		return paraOrg;
	}

	/**
	 * Set the value related to the column: PARA_ORG
	 * @param paraOrg the PARA_ORG value
	 */
	public void setParaOrg (java.lang.String paraOrg) {
		this.paraOrg = paraOrg;
	}



	/**
	 * Return the value associated with the column: PARA_ID
	 */
	public java.lang.String getParaId () {
		return paraId;
	}

	/**
	 * Set the value related to the column: PARA_ID
	 * @param paraId the PARA_ID value
	 */
	public void setParaId (java.lang.String paraId) {
		this.paraId = paraId;
	}



	/**
	 * Return the value associated with the column: PARA_STA
	 */
	public java.lang.String getParaSta () {
		return paraSta;
	}

	/**
	 * Set the value related to the column: PARA_STA
	 * @param paraSta the PARA_STA value
	 */
	public void setParaSta (java.lang.String paraSta) {
		this.paraSta = paraSta;
	}



	/**
	 * Return the value associated with the column: PARA_LEN
	 */
	public java.lang.String getParaLen () {
		return paraLen;
	}

	/**
	 * Set the value related to the column: PARA_LEN
	 * @param paraLen the PARA_LEN value
	 */
	public void setParaLen (java.lang.String paraLen) {
		this.paraLen = paraLen;
	}



	/**
	 * Return the value associated with the column: PARA_VAL
	 */
	public java.lang.String getParaVal () {
		return paraVal;
	}

	/**
	 * Set the value related to the column: PARA_VAL
	 * @param paraVal the PARA_VAL value
	 */
	public void setParaVal (java.lang.String paraVal) {
		this.paraVal = paraVal;
	}



	/**
	 * Return the value associated with the column: GEN_FLAG
	 */
	public java.lang.String getGenFlag () {
		return genFlag;
	}

	/**
	 * Set the value related to the column: GEN_FLAG
	 * @param genFlag the GEN_FLAG value
	 */
	public void setGenFlag (java.lang.String genFlag) {
		this.genFlag = genFlag;
	}



	/**
	 * Return the value associated with the column: REC_OPR_ID
	 */
	public java.lang.String getRecOprId () {
		return recOprId;
	}

	/**
	 * Set the value related to the column: REC_OPR_ID
	 * @param recOprId the REC_OPR_ID value
	 */
	public void setRecOprId (java.lang.String recOprId) {
		this.recOprId = recOprId;
	}



	/**
	 * Return the value associated with the column: REC_UPD_OPR
	 */
	public java.lang.String getRecUpdOpr () {
		return recUpdOpr;
	}

	/**
	 * Set the value related to the column: REC_UPD_OPR
	 * @param recUpdOpr the REC_UPD_OPR value
	 */
	public void setRecUpdOpr (java.lang.String recUpdOpr) {
		this.recUpdOpr = recUpdOpr;
	}



	/**
	 * Return the value associated with the column: REC_CRT_TS
	 */
	public java.lang.String getRecCrtTs () {
		return recCrtTs;
	}

	/**
	 * Set the value related to the column: REC_CRT_TS
	 * @param recCrtTs the REC_CRT_TS value
	 */
	public void setRecCrtTs (java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}



	/**
	 * Return the value associated with the column: REC_UPD_TS
	 */
	public java.lang.String getRecUpdTs () {
		return recUpdTs;
	}

	/**
	 * Set the value related to the column: REC_UPD_TS
	 * @param recUpdTs the REC_UPD_TS value
	 */
	public void setRecUpdTs (java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.base.TblEmvPara)) return false;
		else {
			com.huateng.po.base.TblEmvPara tblEmvPara = (com.huateng.po.base.TblEmvPara) obj;
			if (null == this.getId() || null == tblEmvPara.getId()) return false;
			else return (this.getId().equals(tblEmvPara.getId()));
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


}