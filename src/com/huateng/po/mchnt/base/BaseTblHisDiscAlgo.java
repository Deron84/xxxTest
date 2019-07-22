package com.huateng.po.mchnt.base;

import java.io.Serializable;

import com.huateng.po.mchnt.TblHisDiscAlgoPK;


/**
 * This is an object that contains data related to the TBL_HIS_DISC_ALGO table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_HIS_DISC_ALGO"
 */

@SuppressWarnings({ "unchecked", "serial" })
public abstract class BaseTblHisDiscAlgo  implements Comparable, Serializable {

	public static String REF = "TblHisDiscAlgo";
	public static String PROP_FEE_VALUE = "FeeValue";
	public static String PROP_FLAG = "Flag";
	public static String PROP_MIN_FEE = "MinFee";
	public static String PROP_UPPER_MOUNT = "UpperMount";
	public static String PROP_MAX_FEE = "MaxFee";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_ID = "Id";
	public static String PROP_REC_UPD_USR_ID = "RecUpdUsrId";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_FLOOR_MOUNT = "FloorMount";
	public static String PROP_CARD_TYPE = "CardType";


	// constructors
	public BaseTblHisDiscAlgo () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblHisDiscAlgo (TblHisDiscAlgoPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTblHisDiscAlgo (
		TblHisDiscAlgoPK id,
		java.math.BigDecimal minFee,
		java.math.BigDecimal maxFee,
		java.math.BigDecimal floorMount,
		java.math.BigDecimal upperMount,
		java.lang.Integer flag,
		java.math.BigDecimal feeValue,
		java.lang.String recUpdUsrId,
		java.lang.String recUpdTs,
		java.lang.String recCrtTs,
		java.lang.String txnNum) {

		this.setId(id);
		this.setMinFee(minFee);
		this.setMaxFee(maxFee);
		this.setFloorMount(floorMount);
		this.setUpperMount(upperMount);
		this.setFlag(flag);
		this.setFeeValue(feeValue);
		this.setRecUpdUsrId(recUpdUsrId);
		this.setRecUpdTs(recUpdTs);
		this.setRecCrtTs(recCrtTs);
		this.setTxnNum(txnNum);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private TblHisDiscAlgoPK id;

	// fields
	private java.math.BigDecimal minFee;
	private java.math.BigDecimal maxFee;
	private java.math.BigDecimal floorMount;
	private java.math.BigDecimal upperMount;
	private java.lang.Integer flag;
	private java.math.BigDecimal feeValue;
	private java.lang.String recUpdUsrId;
	private java.lang.String recUpdTs;
	private java.lang.String recCrtTs;
	private java.lang.String txnNum;
	private String cardType;



	public java.lang.String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(java.lang.String txnNum) {
		this.txnNum = txnNum;
	}

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public TblHisDiscAlgoPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (TblHisDiscAlgoPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: MIN_FEE
	 */
	public java.math.BigDecimal getMinFee () {
		return minFee;
	}

	/**
	 * Set the value related to the column: MIN_FEE
	 * @param minFee the MIN_FEE value
	 */
	public void setMinFee (java.math.BigDecimal minFee) {
		this.minFee = minFee;
	}



	/**
	 * Return the value associated with the column: MAX_FEE
	 */
	public java.math.BigDecimal getMaxFee () {
		return maxFee;
	}

	/**
	 * Set the value related to the column: MAX_FEE
	 * @param maxFee the MAX_FEE value
	 */
	public void setMaxFee (java.math.BigDecimal maxFee) {
		this.maxFee = maxFee;
	}



	/**
	 * Return the value associated with the column: FLOOR_MOUNT
	 */
	public java.math.BigDecimal getFloorMount () {
		return floorMount;
	}

	/**
	 * Set the value related to the column: FLOOR_MOUNT
	 * @param floorMount the FLOOR_MOUNT value
	 */
	public void setFloorMount (java.math.BigDecimal floorMount) {
		this.floorMount = floorMount;
	}



	/**
	 * Return the value associated with the column: UPPER_MOUNT
	 */
	public java.math.BigDecimal getUpperMount () {
		return upperMount;
	}

	/**
	 * Set the value related to the column: UPPER_MOUNT
	 * @param upperMount the UPPER_MOUNT value
	 */
	public void setUpperMount (java.math.BigDecimal upperMount) {
		this.upperMount = upperMount;
	}



	/**
	 * Return the value associated with the column: FLAG
	 */
	public java.lang.Integer getFlag () {
		return flag;
	}

	/**
	 * Set the value related to the column: FLAG
	 * @param flag the FLAG value
	 */
	public void setFlag (java.lang.Integer flag) {
		this.flag = flag;
	}



	/**
	 * Return the value associated with the column: FEE_VALUE
	 */
	public java.math.BigDecimal getFeeValue () {
		return feeValue;
	}

	/**
	 * Set the value related to the column: FEE_VALUE
	 * @param feeValue the FEE_VALUE value
	 */
	public void setFeeValue (java.math.BigDecimal feeValue) {
		this.feeValue = feeValue;
	}



	/**
	 * Return the value associated with the column: REC_UPD_USR_ID
	 */
	public java.lang.String getRecUpdUsrId () {
		return recUpdUsrId;
	}

	/**
	 * Set the value related to the column: REC_UPD_USR_ID
	 * @param recUpdUsrId the REC_UPD_USR_ID value
	 */
	public void setRecUpdUsrId (java.lang.String recUpdUsrId) {
		this.recUpdUsrId = recUpdUsrId;
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

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.mchnt.TblHisDiscAlgo)) return false;
		else {
			com.huateng.po.mchnt.TblHisDiscAlgo tblHisDiscAlgo = (com.huateng.po.mchnt.TblHisDiscAlgo) obj;
			if (null == this.getId() || null == tblHisDiscAlgo.getId()) return false;
			else return (this.getId().equals(tblHisDiscAlgo.getId()));
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

	public int compareTo (Object obj) {
		if (obj.hashCode() > hashCode()) return 1;
		else if (obj.hashCode() < hashCode()) return -1;
		else return 0;
	}

	public String toString () {
		return super.toString();
	}


}