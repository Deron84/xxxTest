package com.huateng.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_PAPER_DEF_INF table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_PAPER_DEF_INF"
 */

public abstract class BaseTblPaperDefInf  implements Serializable {

	public static String REF = "TblPaperDefInf";
	public static String PROP_RESERVE1 = "reserve1";
	public static String PROP_UPD_USER = "updUser";
	public static String PROP_RESERVE2 = "reserve2";
	public static String PROP_CRT_TIME = "crtTime";
	public static String PROP_QUESTION = "question";
	public static String PROP_REMARKS = "remarks";
	public static String PROP_ID = "id";
	public static String PROP_QUES_INDEX = "quesIndex";
	public static String PROP_UPD_TIME = "updTime";
	public static String PROP_CRT_USER = "crtUser";
	public static String PROP_OPTIONS = "options";


	// constructors
	public BaseTblPaperDefInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblPaperDefInf (com.huateng.po.mchtSrv.TblPaperDefInfPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.mchtSrv.TblPaperDefInfPK id;

	// fields
	private java.lang.Integer quesIndex;
	private java.lang.String question;
	private java.lang.String options;
	private java.lang.String remarks;
	private java.lang.String reserve1;
	private java.lang.String reserve2;
	private java.lang.String crtUser;
	private java.lang.String crtTime;
	private java.lang.String updUser;
	private java.lang.String updTime;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.mchtSrv.TblPaperDefInfPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.mchtSrv.TblPaperDefInfPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: QUES_INDEX
	 */
	public java.lang.Integer getQuesIndex () {
		return quesIndex;
	}

	/**
	 * Set the value related to the column: QUES_INDEX
	 * @param quesIndex the QUES_INDEX value
	 */
	public void setQuesIndex (java.lang.Integer quesIndex) {
		this.quesIndex = quesIndex;
	}



	/**
	 * Return the value associated with the column: QUESTION
	 */
	public java.lang.String getQuestion () {
		return question;
	}

	/**
	 * Set the value related to the column: QUESTION
	 * @param question the QUESTION value
	 */
	public void setQuestion (java.lang.String question) {
		this.question = question;
	}



	/**
	 * Return the value associated with the column: OPTIONS
	 */
	public java.lang.String getOptions () {
		return options;
	}

	/**
	 * Set the value related to the column: OPTIONS
	 * @param options the OPTIONS value
	 */
	public void setOptions (java.lang.String options) {
		this.options = options;
	}



	/**
	 * Return the value associated with the column: REMARKS
	 */
	public java.lang.String getRemarks () {
		return remarks;
	}

	/**
	 * Set the value related to the column: REMARKS
	 * @param remarks the REMARKS value
	 */
	public void setRemarks (java.lang.String remarks) {
		this.remarks = remarks;
	}



	/**
	 * Return the value associated with the column: RESERVE1
	 */
	public java.lang.String getReserve1 () {
		return reserve1;
	}

	/**
	 * Set the value related to the column: RESERVE1
	 * @param reserve1 the RESERVE1 value
	 */
	public void setReserve1 (java.lang.String reserve1) {
		this.reserve1 = reserve1;
	}



	/**
	 * Return the value associated with the column: RESERVE2
	 */
	public java.lang.String getReserve2 () {
		return reserve2;
	}

	/**
	 * Set the value related to the column: RESERVE2
	 * @param reserve2 the RESERVE2 value
	 */
	public void setReserve2 (java.lang.String reserve2) {
		this.reserve2 = reserve2;
	}



	/**
	 * Return the value associated with the column: CRT_USER
	 */
	public java.lang.String getCrtUser () {
		return crtUser;
	}

	/**
	 * Set the value related to the column: CRT_USER
	 * @param crtUser the CRT_USER value
	 */
	public void setCrtUser (java.lang.String crtUser) {
		this.crtUser = crtUser;
	}



	/**
	 * Return the value associated with the column: CRT_TIME
	 */
	public java.lang.String getCrtTime () {
		return crtTime;
	}

	/**
	 * Set the value related to the column: CRT_TIME
	 * @param crtTime the CRT_TIME value
	 */
	public void setCrtTime (java.lang.String crtTime) {
		this.crtTime = crtTime;
	}



	/**
	 * Return the value associated with the column: UPD_USER
	 */
	public java.lang.String getUpdUser () {
		return updUser;
	}

	/**
	 * Set the value related to the column: UPD_USER
	 * @param updUser the UPD_USER value
	 */
	public void setUpdUser (java.lang.String updUser) {
		this.updUser = updUser;
	}



	/**
	 * Return the value associated with the column: UPD_TIME
	 */
	public java.lang.String getUpdTime () {
		return updTime;
	}

	/**
	 * Set the value related to the column: UPD_TIME
	 * @param updTime the UPD_TIME value
	 */
	public void setUpdTime (java.lang.String updTime) {
		this.updTime = updTime;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.mchtSrv.TblPaperDefInf)) return false;
		else {
			com.huateng.po.mchtSrv.TblPaperDefInf tblPaperDefInf = (com.huateng.po.mchtSrv.TblPaperDefInf) obj;
			if (null == this.getId() || null == tblPaperDefInf.getId()) return false;
			else return (this.getId().equals(tblPaperDefInf.getId()));
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