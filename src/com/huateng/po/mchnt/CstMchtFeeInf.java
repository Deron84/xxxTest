package com.huateng.po.mchnt;

import java.io.Serializable;


/**
 * This is an object that contains data related to the cst_mcht_fee_inf table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="cst_mcht_fee_inf"
 */

@SuppressWarnings("serial")
public class CstMchtFeeInf  implements Serializable {

	public static String REF = "CstMchtFeeInf";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_CHANNEL = "Channel";
	public static String PROP_MON_NUM = "MonNum";
	public static String PROP_RESERVED = "Reserved";
	public static String PROP_ID = "Id";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_DAY_SINGLE = "DaySingle";
	public static String PROP_DAY_AMT = "DayAmt";
	public static String PROP_MON_AMT = "MonAmt";
	public static String PROP_DAY_NUM = "DayNum";


	// constructors
	public CstMchtFeeInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public CstMchtFeeInf (CstMchtFeeInfPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public CstMchtFeeInf (
		CstMchtFeeInfPK id,
		java.lang.String channel,
		java.lang.String dayNum,
		java.lang.Float dayAmt,
		java.lang.Float daySingle,
		java.lang.String monNum,
		java.lang.Float monAmt) {

		this.setId(id);
		this.setChannel(channel);
		this.setDayNum(dayNum);
		this.setDayAmt(dayAmt);
		this.setDaySingle(daySingle);
		this.setMonNum(monNum);
		this.setMonAmt(monAmt);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private CstMchtFeeInfPK id;

	// fields
	private java.lang.String channel;
	private java.lang.String dayNum;
	private java.lang.Float dayAmt;
	private java.lang.Float daySingle;
	private java.lang.String monNum;
	private java.lang.Float monAmt;
	private java.lang.String reserved;
	private java.lang.String recCrtTs;
	private java.lang.String recUpdTs;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public CstMchtFeeInfPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (CstMchtFeeInfPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: channel
	 */
	public java.lang.String getChannel () {
		return channel;
	}

	/**
	 * Set the value related to the column: channel
	 * @param channel the channel value
	 */
	public void setChannel (java.lang.String channel) {
		this.channel = channel;
	}



	/**
	 * Return the value associated with the column: day_num
	 */
	public java.lang.String getDayNum () {
		return dayNum;
	}

	/**
	 * Set the value related to the column: day_num
	 * @param dayNum the day_num value
	 */
	public void setDayNum (java.lang.String dayNum) {
		this.dayNum = dayNum;
	}



	/**
	 * Return the value associated with the column: day_amt
	 */
	public java.lang.Float getDayAmt () {
		return dayAmt;
	}

	/**
	 * Set the value related to the column: day_amt
	 * @param dayAmt the day_amt value
	 */
	public void setDayAmt (java.lang.Float dayAmt) {
		this.dayAmt = dayAmt;
	}



	/**
	 * Return the value associated with the column: day_single
	 */
	public java.lang.Float getDaySingle () {
		return daySingle;
	}

	/**
	 * Set the value related to the column: day_single
	 * @param daySingle the day_single value
	 */
	public void setDaySingle (java.lang.Float daySingle) {
		this.daySingle = daySingle;
	}



	/**
	 * Return the value associated with the column: mon_num
	 */
	public java.lang.String getMonNum () {
		return monNum;
	}

	/**
	 * Set the value related to the column: mon_num
	 * @param monNum the mon_num value
	 */
	public void setMonNum (java.lang.String monNum) {
		this.monNum = monNum;
	}



	/**
	 * Return the value associated with the column: mon_amt
	 */
	public java.lang.Float getMonAmt () {
		return monAmt;
	}

	/**
	 * Set the value related to the column: mon_amt
	 * @param monAmt the mon_amt value
	 */
	public void setMonAmt (java.lang.Float monAmt) {
		this.monAmt = monAmt;
	}



	/**
	 * Return the value associated with the column: reserved
	 */
	public java.lang.String getReserved () {
		return reserved;
	}

	/**
	 * Set the value related to the column: reserved
	 * @param reserved the reserved value
	 */
	public void setReserved (java.lang.String reserved) {
		this.reserved = reserved;
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
		if (!(obj instanceof CstMchtFeeInf)) return false;
		else {
			CstMchtFeeInf cstMchtFeeInf = (CstMchtFeeInf) obj;
			if (null == this.getId() || null == cstMchtFeeInf.getId()) return false;
			else return (this.getId().equals(cstMchtFeeInf.getId()));
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