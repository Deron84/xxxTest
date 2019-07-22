package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_VER_MNG table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_VER_MNG"
 */

public abstract class BaseTblVerMng  implements Serializable {

	public static String REF = "TblVerMng";
	public static String PROP_CRT_DATE = "CrtDate";
	public static String PROP_UPD_DATE = "UpdDate";
	public static String PROP_MISC = "Misc";
	public static String PROP_ID = "Id";


	// constructors
	public BaseTblVerMng () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblVerMng (com.huateng.po.epos.TblVerMngPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.epos.TblVerMngPK id;

	// fields
	private java.lang.String misc;
	private java.lang.String crtDate;
	private java.lang.String updDate;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.epos.TblVerMngPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.epos.TblVerMngPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: MISC
	 */
	public java.lang.String getMisc () {
		return misc;
	}

	/**
	 * Set the value related to the column: MISC
	 * @param misc the MISC value
	 */
	public void setMisc (java.lang.String misc) {
		this.misc = misc;
	}



	/**
	 * Return the value associated with the column: CRT_DATE
	 */
	public java.lang.String getCrtDate () {
		return crtDate;
	}

	/**
	 * Set the value related to the column: CRT_DATE
	 * @param crtDate the CRT_DATE value
	 */
	public void setCrtDate (java.lang.String crtDate) {
		this.crtDate = crtDate;
	}



	/**
	 * Return the value associated with the column: UPD_DATE
	 */
	public java.lang.String getUpdDate () {
		return updDate;
	}

	/**
	 * Set the value related to the column: UPD_DATE
	 * @param updDate the UPD_DATE value
	 */
	public void setUpdDate (java.lang.String updDate) {
		this.updDate = updDate;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.epos.TblVerMng)) return false;
		else {
			com.huateng.po.epos.TblVerMng tblVerMng = (com.huateng.po.epos.TblVerMng) obj;
			if (null == this.getId() || null == tblVerMng.getId()) return false;
			else return (this.getId().equals(tblVerMng.getId()));
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