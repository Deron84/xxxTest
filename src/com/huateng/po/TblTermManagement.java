package com.huateng.po;

public class TblTermManagement {

	public static String REF = "zdcs";

	public static String PROP_LAST_UPD_TS = "LastUpdTs";

	public static String PROP_STOR_DATE = "StorDate";

	public static String PROP_TERM_TYPE = "TermType";

	public static String PROP_INVALID_OPR_ID = "InvalidOprId";

	public static String PROP_MISC = "Misc";

	public static String PROP_LAST_UPD_OPR_ID = "LastUpdOprId";

	public static String PROP_BANK_DATE = "BankDate";

	public static String PROP_BACK_OPR_ID = "BackOprId";

	public static String PROP_MECH_NO = "MechNo";

	public static String PROP_PRODUCT_CD = "ProductCd";

	public static String PROP_PIN_PAD = "PinPad";

	public static String PROP_SIGN_DATE = "SignDate";

	public static String PROP_RECI_DATE = "ReciDate";

	public static String PROP_MANUFACTURER = "Manufacturer";

	public static String PROP_STATE = "State";

	public static String PROP_STOR_OPR_ID = "StorOprId";

	public static String PROP_TERMINAL_TYPE = "TerminalType";

	public static String PROP_SIGN_OPR_ID = "SignOprId";

	public static String PROP_INVALID_DATE = "InvalidDate";

	public static String PROP_ID = "Id";

	public static String PROP_RECI_OPR_ID = "ReciOprId";

	public static String PROP_BATCH_NO = "BatchNo";

	// constructors
	public TblTermManagement() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public TblTermManagement(java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public TblTermManagement(java.lang.String id, java.lang.String termType,
			java.lang.String state, java.lang.String manufacturer,
			java.lang.String productCd, java.lang.String terminalType) {

		this.setId(id);
		this.setTermType(termType);
		this.setState(state);
		this.setManufacturer(manufacturer);
		this.setProductCd(productCd);
		this.setTerminalType(terminalType);
		initialize();
	}

	protected void initialize() {
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String mechNo;

	private java.lang.String termType;

	private java.lang.String state;

	private java.lang.String manufacturer;

	private java.lang.String productCd;

	private java.lang.String terminalType;

	private java.lang.String batchNo;

	private java.lang.String storOprId;

	private java.lang.String storDate;

	private java.lang.String reciOprId;

	private java.lang.String reciDate;

	private java.lang.String backOprId;

	private java.lang.String bankDate;

	private java.lang.String invalidOprId;

	private java.lang.String invalidDate;

	private java.lang.String signOprId;

	private java.lang.String signDate;

	private java.lang.String pinPad;

	private java.lang.String misc;

	private java.lang.String lastUpdOprId;

	private java.util.Date lastUpdTs;

	/** 新增字段，为了报表统计 */
	/** 机构号，终端属于哪个机构 */
	private String brhId;

	/** 出库时间，绑定终端时候更新 */
	private String outStoreDate;

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getOutStoreDate() {
		return outStoreDate;
	}

	public void setOutStoreDate(String outStoreDate) {
		this.outStoreDate = outStoreDate;
	}

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="assigned" column="TERM_NO"
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param id
	 *            the new ID
	 */
	public void setId(java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: MECH_NO
	 */
	public java.lang.String getMechNo() {
		return mechNo;
	}

	/**
	 * Set the value related to the column: MECH_NO
	 * 
	 * @param mechNo
	 *            the MECH_NO value
	 */
	public void setMechNo(java.lang.String mechNo) {
		this.mechNo = mechNo;
	}

	/**
	 * Return the value associated with the column: TERM_TYPE
	 */
	public java.lang.String getTermType() {
		return termType;
	}

	/**
	 * Set the value related to the column: TERM_TYPE
	 * 
	 * @param termType
	 *            the TERM_TYPE value
	 */
	public void setTermType(java.lang.String termType) {
		this.termType = termType;
	}

	/**
	 * Return the value associated with the column: STATE
	 */
	public java.lang.String getState() {
		return state;
	}

	/**
	 * Set the value related to the column: STATE
	 * 
	 * @param state
	 *            the STATE value
	 */
	public void setState(java.lang.String state) {
		this.state = state;
	}

	/**
	 * Return the value associated with the column: MANUFATURER
	 */
	public java.lang.String getManufacturer() {
		return manufacturer;
	}

	/**
	 * Set the value related to the column: MANUFATURER
	 * 
	 * @param manufacturer
	 *            the MANUFATURER value
	 */
	public void setManufacturer(java.lang.String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * Return the value associated with the column: PRODUCT_CD
	 */
	public java.lang.String getProductCd() {
		return productCd;
	}

	/**
	 * Set the value related to the column: PRODUCT_CD
	 * 
	 * @param productCd
	 *            the PRODUCT_CD value
	 */
	public void setProductCd(java.lang.String productCd) {
		this.productCd = productCd;
	}

	/**
	 * Return the value associated with the column: TERMINAL_TYPE
	 */
	public java.lang.String getTerminalType() {
		return terminalType;
	}

	/**
	 * Set the value related to the column: TERMINAL_TYPE
	 * 
	 * @param terminalType
	 *            the TERMINAL_TYPE value
	 */
	public void setTerminalType(java.lang.String terminalType) {
		this.terminalType = terminalType;
	}

	/**
	 * Return the value associated with the column: BATCH_NO
	 */
	public java.lang.String getBatchNo() {
		return batchNo;
	}

	/**
	 * Set the value related to the column: BATCH_NO
	 * 
	 * @param batchNo
	 *            the BATCH_NO value
	 */
	public void setBatchNo(java.lang.String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * Return the value associated with the column: STOR_OPR_ID
	 */
	public java.lang.String getStorOprId() {
		return storOprId;
	}

	/**
	 * Set the value related to the column: STOR_OPR_ID
	 * 
	 * @param storOprId
	 *            the STOR_OPR_ID value
	 */
	public void setStorOprId(java.lang.String storOprId) {
		this.storOprId = storOprId;
	}

	/**
	 * Return the value associated with the column: STOR_DATE
	 */
	public java.lang.String getStorDate() {
		return storDate;
	}

	/**
	 * Set the value related to the column: STOR_DATE
	 * 
	 * @param storDate
	 *            the STOR_DATE value
	 */
	public void setStorDate(java.lang.String storDate) {
		this.storDate = storDate;
	}

	/**
	 * Return the value associated with the column: RECI_OPR_ID
	 */
	public java.lang.String getReciOprId() {
		return reciOprId;
	}

	/**
	 * Set the value related to the column: RECI_OPR_ID
	 * 
	 * @param reciOprId
	 *            the RECI_OPR_ID value
	 */
	public void setReciOprId(java.lang.String reciOprId) {
		this.reciOprId = reciOprId;
	}

	/**
	 * Return the value associated with the column: RECI_DATE
	 */
	public java.lang.String getReciDate() {
		return reciDate;
	}

	/**
	 * Set the value related to the column: RECI_DATE
	 * 
	 * @param reciDate
	 *            the RECI_DATE value
	 */
	public void setReciDate(java.lang.String reciDate) {
		this.reciDate = reciDate;
	}

	/**
	 * Return the value associated with the column: BACK_OPR_ID
	 */
	public java.lang.String getBackOprId() {
		return backOprId;
	}

	/**
	 * Set the value related to the column: BACK_OPR_ID
	 * 
	 * @param backOprId
	 *            the BACK_OPR_ID value
	 */
	public void setBackOprId(java.lang.String backOprId) {
		this.backOprId = backOprId;
	}

	/**
	 * Return the value associated with the column: BANK_DATE
	 */
	public java.lang.String getBankDate() {
		return bankDate;
	}

	/**
	 * Set the value related to the column: BANK_DATE
	 * 
	 * @param bankDate
	 *            the BANK_DATE value
	 */
	public void setBankDate(java.lang.String bankDate) {
		this.bankDate = bankDate;
	}

	/**
	 * Return the value associated with the column: INVALID_OPR_ID
	 */
	public java.lang.String getInvalidOprId() {
		return invalidOprId;
	}

	/**
	 * Set the value related to the column: INVALID_OPR_ID
	 * 
	 * @param invalidOprId
	 *            the INVALID_OPR_ID value
	 */
	public void setInvalidOprId(java.lang.String invalidOprId) {
		this.invalidOprId = invalidOprId;
	}

	/**
	 * Return the value associated with the column: INVALID_DATE
	 */
	public java.lang.String getInvalidDate() {
		return invalidDate;
	}

	/**
	 * Set the value related to the column: INVALID_DATE
	 * 
	 * @param invalidDate
	 *            the INVALID_DATE value
	 */
	public void setInvalidDate(java.lang.String invalidDate) {
		this.invalidDate = invalidDate;
	}

	/**
	 * Return the value associated with the column: SIGN_OPR_ID
	 */
	public java.lang.String getSignOprId() {
		return signOprId;
	}

	/**
	 * Set the value related to the column: SIGN_OPR_ID
	 * 
	 * @param signOprId
	 *            the SIGN_OPR_ID value
	 */
	public void setSignOprId(java.lang.String signOprId) {
		this.signOprId = signOprId;
	}

	/**
	 * Return the value associated with the column: SIGN_DATE
	 */
	public java.lang.String getSignDate() {
		return signDate;
	}

	/**
	 * Set the value related to the column: SIGN_DATE
	 * 
	 * @param signDate
	 *            the SIGN_DATE value
	 */
	public void setSignDate(java.lang.String signDate) {
		this.signDate = signDate;
	}

	/**
	 * Return the value associated with the column: PIN_PAD
	 */
	public java.lang.String getPinPad() {
		return pinPad;
	}

	/**
	 * Set the value related to the column: PIN_PAD
	 * 
	 * @param pinPad
	 *            the PIN_PAD value
	 */
	public void setPinPad(java.lang.String pinPad) {
		this.pinPad = pinPad;
	}

	/**
	 * Return the value associated with the column: MISC
	 */
	public java.lang.String getMisc() {
		return misc;
	}

	/**
	 * Set the value related to the column: MISC
	 * 
	 * @param misc
	 *            the MISC value
	 */
	public void setMisc(java.lang.String misc) {
		this.misc = misc;
	}

	/**
	 * Return the value associated with the column: LAST_UPD_OPR_ID
	 */
	public java.lang.String getLastUpdOprId() {
		return lastUpdOprId;
	}

	/**
	 * Set the value related to the column: LAST_UPD_OPR_ID
	 * 
	 * @param lastUpdOprId
	 *            the LAST_UPD_OPR_ID value
	 */
	public void setLastUpdOprId(java.lang.String lastUpdOprId) {
		this.lastUpdOprId = lastUpdOprId;
	}

	/**
	 * Return the value associated with the column: LAST_UPD_TS
	 */
	public java.util.Date getLastUpdTs() {
		return lastUpdTs;
	}

	/**
	 * Set the value related to the column: LAST_UPD_TS
	 * 
	 * @param lastUpdTs
	 *            the LAST_UPD_TS value
	 */
	public void setLastUpdTs(java.util.Date lastUpdTs) {
		this.lastUpdTs = lastUpdTs;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.huateng.po.TblTermManagement))
			return false;
		else {
			com.huateng.po.TblTermManagement tblTermManagement = (com.huateng.po.TblTermManagement) obj;
			if (null == this.getId() || null == tblTermManagement.getId())
				return false;
			else
				return (this.getId().equals(tblTermManagement.getId()));
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"
						+ this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString() {
		return super.toString();
	}

}