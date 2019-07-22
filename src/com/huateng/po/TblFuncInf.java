package com.huateng.po;

import java.io.Serializable;



public class TblFuncInf implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblFuncInf";
	public static String PROP_FUNC_TYPE = "FuncType";
	public static String PROP_PAGE_NAME = "PageName";
	public static String PROP_PAGE_TARGET = "PageTarget";
	public static String PROP_FUNC_PARENT_ID = "FuncParentId";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_FUNC_NAME = "FuncName";
	public static String PROP_PAGE_URL = "PageUrl";
	public static String PROP_MISC2 = "Misc2";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_MISC1 = "Misc1";
	public static String PROP_REC_UPD_OPR = "RecUpdOpr";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_FUNC_ID = "FuncId";
	protected void initialize () {}	
	
	/**
	 * 
	 */
	public TblFuncInf() {
		super();
	}


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer funcId;

	// fields
	private java.lang.Integer funcParentId;
	private java.lang.String funcType;
	private java.lang.String funcName;
	private java.lang.String pageName;
	private java.lang.String pageUrl;
	private java.lang.String iconPath;
	private java.lang.String misc1;
	private java.lang.String misc2;
	private java.lang.String description;
	private java.lang.String recUpdOpr;
	private java.lang.String recCrtTs;
	private java.lang.String recUpdTs;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="FUNC_ID"
     */
	public java.lang.Integer getFuncId () {
		return funcId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param funcId the new ID
	 */
	public void setFuncId (java.lang.Integer funcId) {
		this.funcId = funcId;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: FUNC_PARENT_ID
	 */
	public java.lang.Integer getFuncParentId () {
		return funcParentId;
	}

	/**
	 * Set the value related to the column: FUNC_PARENT_ID
	 * @param funcParentId the FUNC_PARENT_ID value
	 */
	public void setFuncParentId (java.lang.Integer funcParentId) {
		this.funcParentId = funcParentId;
	}



	/**
	 * Return the value associated with the column: FUNC_TYPE
	 */
	public java.lang.String getFuncType () {
		return funcType;
	}

	/**
	 * Set the value related to the column: FUNC_TYPE
	 * @param funcType the FUNC_TYPE value
	 */
	public void setFuncType (java.lang.String funcType) {
		this.funcType = funcType;
	}



	/**
	 * Return the value associated with the column: FUNC_NAME
	 */
	public java.lang.String getFuncName () {
		return funcName;
	}

	/**
	 * Set the value related to the column: FUNC_NAME
	 * @param funcName the FUNC_NAME value
	 */
	public void setFuncName (java.lang.String funcName) {
		this.funcName = funcName;
	}



	/**
	 * Return the value associated with the column: PAGE_NAME
	 */
	public java.lang.String getPageName () {
		return pageName;
	}

	/**
	 * Set the value related to the column: PAGE_NAME
	 * @param pageName the PAGE_NAME value
	 */
	public void setPageName (java.lang.String pageName) {
		this.pageName = pageName;
	}



	/**
	 * Return the value associated with the column: PAGE_URL
	 */
	public java.lang.String getPageUrl () {
		return pageUrl;
	}

	/**
	 * Set the value related to the column: PAGE_URL
	 * @param pageUrl the PAGE_URL value
	 */
	public void setPageUrl (java.lang.String pageUrl) {
		this.pageUrl = pageUrl;
	}

	/**
	 * @return the iconPath
	 */
	public java.lang.String getIconPath() {
		return iconPath;
	}

	/**
	 * @param iconPath the iconPath to set
	 */
	public void setIconPath(java.lang.String iconPath) {
		this.iconPath = iconPath;
	}

	/**
	 * Return the value associated with the column: MISC1
	 */
	public java.lang.String getMisc1 () {
		return misc1;
	}

	/**
	 * Set the value related to the column: MISC1
	 * @param misc1 the MISC1 value
	 */
	public void setMisc1 (java.lang.String misc1) {
		this.misc1 = misc1;
	}



	/**
	 * Return the value associated with the column: MISC2
	 */
	public java.lang.String getMisc2 () {
		return misc2;
	}

	/**
	 * Set the value related to the column: MISC2
	 * @param misc2 the MISC2 value
	 */
	public void setMisc2 (java.lang.String misc2) {
		this.misc2 = misc2;
	}



	/**
	 * Return the value associated with the column: DESCRIPTION
	 */
	public java.lang.String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: DESCRIPTION
	 * @param description the DESCRIPTION value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
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
		if (!(obj instanceof com.huateng.po.TblFuncInf)) return false;
		else {
			com.huateng.po.TblFuncInf tblFuncInf = (com.huateng.po.TblFuncInf) obj;
			if (null == this.getFuncId() || null == tblFuncInf.getFuncId()) return false;
			else return (this.getFuncId().equals(tblFuncInf.getFuncId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getFuncId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getFuncId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}
}