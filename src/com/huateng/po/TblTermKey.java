package com.huateng.po;

import java.io.Serializable;



public class TblTermKey implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblTermKey";
	public static String PROP_TRK_KEY = "TrkKey";
	public static String PROP_PIN_KEY_LEN = "PinKeyLen";
	public static String PROP_MAC_KEY_LEN = "MacKeyLen";
	public static String PROP_PIN_KEY = "PinKey";
	public static String PROP_POS_BMK = "PosBmk";
	public static String PROP_KEY_INDEX = "KeyIndex";
	public static String PROP_POS_TMK = "PosTmk";
	public static String PROP_TRK_KEY_CHK = "TrkKeyChk";
	public static String PROP_TRK_KEY_LEN = "TrkKeyLen";
	public static String PROP_MAC_KEY = "MacKey";
	public static String PROP_PIN_KEY_CHK = "PinKeyChk";
	public static String PROP_TMK_ST = "TmkSt";
	public static String PROP_ID = "Id";
	public static String PROP_MAC_KEY_CHK = "MacKeyChk";

	/**
	 * @param tblTermKeyPK
	 */
	public TblTermKey(TblTermKeyPK tblTermKeyPK) {
		this.id = tblTermKeyPK;
	}

	protected void initialize () {}
	
	
	/**
	 * 
	 */
	public TblTermKey() {
		super();
		// TODO Auto-generated constructor stub
	}


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.TblTermKeyPK id;

	// fields
	private java.lang.String keyIndex;
	private java.lang.String macKeyLen;
	private java.lang.String macKey;
	private java.lang.String macKeyChk;
	private java.lang.String pinKeyLen;
	private java.lang.String pinKey;
	private java.lang.String pinKeyChk;
	private java.lang.String trkKeyLen;
	private java.lang.String trkKey;
	private java.lang.String trkKeyChk;
	private java.lang.String posBmk;
	private java.lang.String posTmk;
	private java.lang.String tmkSt;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.TblTermKeyPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.TblTermKeyPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: key_index
	 */
	public java.lang.String getKeyIndex () {
		return keyIndex;
	}

	/**
	 * Set the value related to the column: key_index
	 * @param keyIndex the key_index value
	 */
	public void setKeyIndex (java.lang.String keyIndex) {
		this.keyIndex = keyIndex;
	}



	/**
	 * Return the value associated with the column: mac_key_len
	 */
	public java.lang.String getMacKeyLen () {
		return macKeyLen;
	}

	/**
	 * Set the value related to the column: mac_key_len
	 * @param macKeyLen the mac_key_len value
	 */
	public void setMacKeyLen (java.lang.String macKeyLen) {
		this.macKeyLen = macKeyLen;
	}



	/**
	 * Return the value associated with the column: mac_key
	 */
	public java.lang.String getMacKey () {
		return macKey;
	}

	/**
	 * Set the value related to the column: mac_key
	 * @param macKey the mac_key value
	 */
	public void setMacKey (java.lang.String macKey) {
		this.macKey = macKey;
	}



	/**
	 * Return the value associated with the column: mac_key_chk
	 */
	public java.lang.String getMacKeyChk () {
		return macKeyChk;
	}

	/**
	 * Set the value related to the column: mac_key_chk
	 * @param macKeyChk the mac_key_chk value
	 */
	public void setMacKeyChk (java.lang.String macKeyChk) {
		this.macKeyChk = macKeyChk;
	}



	/**
	 * Return the value associated with the column: pin_key_len
	 */
	public java.lang.String getPinKeyLen () {
		return pinKeyLen;
	}

	/**
	 * Set the value related to the column: pin_key_len
	 * @param pinKeyLen the pin_key_len value
	 */
	public void setPinKeyLen (java.lang.String pinKeyLen) {
		this.pinKeyLen = pinKeyLen;
	}



	/**
	 * Return the value associated with the column: pin_key
	 */
	public java.lang.String getPinKey () {
		return pinKey;
	}

	/**
	 * Set the value related to the column: pin_key
	 * @param pinKey the pin_key value
	 */
	public void setPinKey (java.lang.String pinKey) {
		this.pinKey = pinKey;
	}



	/**
	 * Return the value associated with the column: pin_key_chk
	 */
	public java.lang.String getPinKeyChk () {
		return pinKeyChk;
	}

	/**
	 * Set the value related to the column: pin_key_chk
	 * @param pinKeyChk the pin_key_chk value
	 */
	public void setPinKeyChk (java.lang.String pinKeyChk) {
		this.pinKeyChk = pinKeyChk;
	}



	/**
	 * Return the value associated with the column: trk_key_len
	 */
	public java.lang.String getTrkKeyLen () {
		return trkKeyLen;
	}

	/**
	 * Set the value related to the column: trk_key_len
	 * @param trkKeyLen the trk_key_len value
	 */
	public void setTrkKeyLen (java.lang.String trkKeyLen) {
		this.trkKeyLen = trkKeyLen;
	}



	/**
	 * Return the value associated with the column: trk_key
	 */
	public java.lang.String getTrkKey () {
		return trkKey;
	}

	/**
	 * Set the value related to the column: trk_key
	 * @param trkKey the trk_key value
	 */
	public void setTrkKey (java.lang.String trkKey) {
		this.trkKey = trkKey;
	}



	/**
	 * Return the value associated with the column: trk_key_chk
	 */
	public java.lang.String getTrkKeyChk () {
		return trkKeyChk;
	}

	/**
	 * Set the value related to the column: trk_key_chk
	 * @param trkKeyChk the trk_key_chk value
	 */
	public void setTrkKeyChk (java.lang.String trkKeyChk) {
		this.trkKeyChk = trkKeyChk;
	}



	/**
	 * Return the value associated with the column: pos_bmk
	 */
	public java.lang.String getPosBmk () {
		return posBmk;
	}

	/**
	 * Set the value related to the column: pos_bmk
	 * @param posBmk the pos_bmk value
	 */
	public void setPosBmk (java.lang.String posBmk) {
		this.posBmk = posBmk;
	}



	/**
	 * Return the value associated with the column: pos_tmk
	 */
	public java.lang.String getPosTmk () {
		return posTmk;
	}

	/**
	 * Set the value related to the column: pos_tmk
	 * @param posTmk the pos_tmk value
	 */
	public void setPosTmk (java.lang.String posTmk) {
		this.posTmk = posTmk;
	}



	/**
	 * Return the value associated with the column: tmk_st
	 */
	public java.lang.String getTmkSt () {
		return tmkSt;
	}

	/**
	 * Set the value related to the column: tmk_st
	 * @param tmkSt the tmk_st value
	 */
	public void setTmkSt (java.lang.String tmkSt) {
		this.tmkSt = tmkSt;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblTermKey)) return false;
		else {
			com.huateng.po.TblTermKey tblTermKey = (com.huateng.po.TblTermKey) obj;
			if (null == this.getId() || null == tblTermKey.getId()) return false;
			else return (this.getId().equals(tblTermKey.getId()));
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