package com.huateng.po.mchnt.base;

import java.io.Serializable;

import com.huateng.po.TblRoleFuncMap;
import com.huateng.po.mchnt.TblMerRoleFuncMapId;



/**
 * This is an object that contains data related to the TBL_ROLE_FUNC_MAP table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_ROLE_FUNC_MAP"
 */

public abstract class BaseTblMerRoleFuncMap  implements Serializable {

	public static String REF = "TblMerRoleFuncMap";
	public static String PROP_ROW_CRT_TS = "RowCrtTs";
	public static String PROP_ID = "Id";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_REC_UPD_USR = "RecUpdUsr";


	// constructors
	public BaseTblMerRoleFuncMap () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblMerRoleFuncMap (TblMerRoleFuncMapId id2) {
		this.setId(id2);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private TblMerRoleFuncMapId id;

	// fields
	private java.lang.String recUpdUsr;
	private java.lang.String rowCrtTs;
	private java.lang.String recUpdTs;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public TblMerRoleFuncMapId getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (TblMerRoleFuncMapId id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: REC_UPD_OPR
	 */
	public java.lang.String getRecUpdUsr () {
		return recUpdUsr;
	}

	/**
	 * Set the value related to the column: REC_UPD_OPR
	 * @param recUpdUsr the REC_UPD_OPR value
	 */
	public void setRecUpdUsr (java.lang.String recUpdUsr) {
		this.recUpdUsr = recUpdUsr;
	}



	/**
	 * Return the value associated with the column: REC_CRT_TS
	 */
	public java.lang.String getRowCrtTs () {
		return rowCrtTs;
	}

	/**
	 * Set the value related to the column: REC_CRT_TS
	 * @param rowCrtTs the REC_CRT_TS value
	 */
	public void setRowCrtTs (java.lang.String rowCrtTs) {
		this.rowCrtTs = rowCrtTs;
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
		if (!(obj instanceof TblRoleFuncMap)) return false;
		else {
			TblRoleFuncMap tblRoleFuncMap = (TblRoleFuncMap) obj;
			if (null == this.getId() || null == tblRoleFuncMap.getId()) return false;
			else return (this.getId().equals(tblRoleFuncMap.getId()));
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