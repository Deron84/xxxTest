package com.huateng.po;

import java.io.Serializable;



public class TblRoleInf implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblRoleInf";
	public static String PROP_MISC2 = "Misc2";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_ROLE_TYPE = "RoleType";
	public static String PROP_MISC1 = "Misc1";
	public static String PROP_REC_UPD_OPR = "RecUpdOpr";
	public static String PROP_ROLE_NAME = "RoleName";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_ROLE_ID = "RoleId";
	public static String PROP_REC_UPD_TS = "RecUpdTs";

	protected void initialize () {}
	
	
	/**
	 * 
	 */
	public TblRoleInf() {
		super();
		// TODO Auto-generated constructor stub
	}


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer roleId;

	// fields
	private java.lang.String roleName;
	private java.lang.String roleType;
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
     *  column="ROLE_ID"
     */
	public java.lang.Integer getRoleId () {
		return roleId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param roleId the new ID
	 */
	public void setRoleId (java.lang.Integer roleId) {
		this.roleId = roleId;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: ROLE_NAME
	 */
	public java.lang.String getRoleName () {
		return roleName;
	}

	/**
	 * Set the value related to the column: ROLE_NAME
	 * @param roleName the ROLE_NAME value
	 */
	public void setRoleName (java.lang.String roleName) {
		this.roleName = roleName;
	}



	/**
	 * Return the value associated with the column: ROLE_TYPE
	 */
	public java.lang.String getRoleType () {
		return roleType;
	}

	/**
	 * Set the value related to the column: ROLE_TYPE
	 * @param roleType the ROLE_TYPE value
	 */
	public void setRoleType (java.lang.String roleType) {
		this.roleType = roleType;
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
		if (!(obj instanceof com.huateng.po.TblRoleInf)) return false;
		else {
			com.huateng.po.TblRoleInf tblRoleInf = (com.huateng.po.TblRoleInf) obj;
			if (null == this.getRoleId() || null == tblRoleInf.getRoleId()) return false;
			else return (this.getRoleId().equals(tblRoleInf.getRoleId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getRoleId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getRoleId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}
}