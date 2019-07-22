package com.huateng.po.mchnt;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tbl_group_mcht_inf table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tbl_group_mcht_inf"
 */

@SuppressWarnings("serial")
public class TblGroupMchtInf  implements Serializable {

	public static String REF = "TblGroupMchtInf";
	public static String PROP_CITY_CD = "CityCd";
	public static String PROP_BUS_RANGE = "BusRange";
	public static String PROP_GROUP_TYPE = "GroupType";
	public static String PROP_PROV_CD = "ProvCd";
	public static String PROP_REC_OPR_ID = "RecOprId";
	public static String PROP_GROUP_NAME = "GroupName";
	public static String PROP_MCHT_PERSON = "MchtPerson";
	public static String PROP_HEAD_ADDR = "HeadAddr";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_ZIP_CD = "ZipCd";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_REG_FUND = "RegFund";
	public static String PROP_REC_UPD_OPR = "RecUpdOpr";
	public static String PROP_GROUP_MCHT_CD = "GroupMchtCd";
	public static String PROP_CONTACT_ADDR = "ContactAddr";
	public static String PROP_BANK_NO = "BankNo";
	public static String PROP_INTERNAL_NO = "InternalNo";

	// constructors
	public TblGroupMchtInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public TblGroupMchtInf (java.lang.String groupMchtCd) {
		this.setGroupMchtCd(groupMchtCd);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public TblGroupMchtInf (
		java.lang.String groupMchtCd,
		java.lang.String provCd) {

		this.setGroupMchtCd(groupMchtCd);
		this.setProvCd(provCd);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String groupMchtCd;

	// fields
	private java.lang.String groupType;
	private java.lang.String groupName;
	private java.lang.String provCd;
	private java.lang.String cityCd;
	private java.lang.String headAddr;
	private java.lang.String regFund;
	private java.lang.String busRange;
	private java.lang.String mchtPerson;
	private java.lang.String contactAddr;
	private java.lang.String zipCd;
	private java.lang.String recOprId;
	private java.lang.String recUpdOpr;
	private java.lang.String recCrtTs;
	private java.lang.String recUpdTs;


	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="group_mcht_cd"
     */
	public java.lang.String getGroupMchtCd () {
		return groupMchtCd;
	}

	/**
	 * Set the unique identifier of this class
	 * @param groupMchtCd the new ID
	 */
	public void setGroupMchtCd (java.lang.String groupMchtCd) {
		this.groupMchtCd = groupMchtCd;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: group_type
	 */
	public java.lang.String getGroupType () {
		return groupType;
	}

	/**
	 * Set the value related to the column: group_type
	 * @param groupType the group_type value
	 */
	public void setGroupType (java.lang.String groupType) {
		this.groupType = groupType;
	}



	/**
	 * Return the value associated with the column: group_name
	 */
	public java.lang.String getGroupName () {
		return groupName;
	}

	/**
	 * Set the value related to the column: group_name
	 * @param groupName the group_name value
	 */
	public void setGroupName (java.lang.String groupName) {
		this.groupName = groupName;
	}



	/**
	 * Return the value associated with the column: prov_cd
	 */
	public java.lang.String getProvCd () {
		return provCd;
	}

	/**
	 * Set the value related to the column: prov_cd
	 * @param provCd the prov_cd value
	 */
	public void setProvCd (java.lang.String provCd) {
		this.provCd = provCd;
	}



	/**
	 * Return the value associated with the column: city_cd
	 */
	public java.lang.String getCityCd () {
		return cityCd;
	}

	/**
	 * Set the value related to the column: city_cd
	 * @param cityCd the city_cd value
	 */
	public void setCityCd (java.lang.String cityCd) {
		this.cityCd = cityCd;
	}



	/**
	 * Return the value associated with the column: head_addr
	 */
	public java.lang.String getHeadAddr () {
		return headAddr;
	}

	/**
	 * Set the value related to the column: head_addr
	 * @param headAddr the head_addr value
	 */
	public void setHeadAddr (java.lang.String headAddr) {
		this.headAddr = headAddr;
	}



	/**
	 * Return the value associated with the column: reg_fund
	 */
	public java.lang.String getRegFund () {
		return regFund;
	}

	/**
	 * Set the value related to the column: reg_fund
	 * @param regFund the reg_fund value
	 */
	public void setRegFund (java.lang.String regFund) {
		this.regFund = regFund;
	}



	/**
	 * Return the value associated with the column: bus_range
	 */
	public java.lang.String getBusRange () {
		return busRange;
	}

	/**
	 * Set the value related to the column: bus_range
	 * @param busRange the bus_range value
	 */
	public void setBusRange (java.lang.String busRange) {
		this.busRange = busRange;
	}



	/**
	 * Return the value associated with the column: mcht_person
	 */
	public java.lang.String getMchtPerson () {
		return mchtPerson;
	}

	/**
	 * Set the value related to the column: mcht_person
	 * @param mchtPerson the mcht_person value
	 */
	public void setMchtPerson (java.lang.String mchtPerson) {
		this.mchtPerson = mchtPerson;
	}



	/**
	 * Return the value associated with the column: contact_addr
	 */
	public java.lang.String getContactAddr () {
		return contactAddr;
	}

	/**
	 * Set the value related to the column: contact_addr
	 * @param contactAddr the contact_addr value
	 */
	public void setContactAddr (java.lang.String contactAddr) {
		this.contactAddr = contactAddr;
	}



	/**
	 * Return the value associated with the column: zip_cd
	 */
	public java.lang.String getZipCd () {
		return zipCd;
	}

	/**
	 * Set the value related to the column: zip_cd
	 * @param zipCd the zip_cd value
	 */
	public void setZipCd (java.lang.String zipCd) {
		this.zipCd = zipCd;
	}



	/**
	 * Return the value associated with the column: rec_opr_id
	 */
	public java.lang.String getRecOprId () {
		return recOprId;
	}

	/**
	 * Set the value related to the column: rec_opr_id
	 * @param recOprId the rec_opr_id value
	 */
	public void setRecOprId (java.lang.String recOprId) {
		this.recOprId = recOprId;
	}



	/**
	 * Return the value associated with the column: rec_upd_opr
	 */
	public java.lang.String getRecUpdOpr () {
		return recUpdOpr;
	}

	/**
	 * Set the value related to the column: rec_upd_opr
	 * @param recUpdOpr the rec_upd_opr value
	 */
	public void setRecUpdOpr (java.lang.String recUpdOpr) {
		this.recUpdOpr = recUpdOpr;
	}



	/**
	 * Return the value associated with the column: rec_crt_ts
	 */
	public java.lang.String getRecCrtTs () {
		return recCrtTs;
	}

	/**
	 * Set the value related to the column: rec_crt_ts
	 * @param recCrtTs the rec_crt_ts value
	 */
	public void setRecCrtTs (java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}



	/**
	 * Return the value associated with the column: rec_upd_ts
	 */
	public java.lang.String getRecUpdTs () {
		return recUpdTs;
	}

	/**
	 * Set the value related to the column: rec_upd_ts
	 * @param recUpdTs the rec_upd_ts value
	 */
	public void setRecUpdTs (java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblGroupMchtInf)) return false;
		else {
			TblGroupMchtInf tblGroupMchtInf = (TblGroupMchtInf) obj;
			if (null == this.getGroupMchtCd() || null == tblGroupMchtInf.getGroupMchtCd()) return false;
			else return (this.getGroupMchtCd().equals(tblGroupMchtInf.getGroupMchtCd()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getGroupMchtCd()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getGroupMchtCd().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}



	


}