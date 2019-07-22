package com.huateng.po.mchnt;

import com.huateng.po.mchnt.base.BaseTblMchtSettleInf;





public class TblMchtSettleInf extends BaseTblMchtSettleInf {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblMchtSettleInf () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TblMchtSettleInf (java.lang.String mchtNo) {
		super(mchtNo);
	}

	/**
	 * Constructor for required fields
	 */
	public TblMchtSettleInf (
		java.lang.String mchtNo,
		java.lang.String settleType,
		java.lang.String feeType,
		java.lang.String feeMaxAmt,
		java.lang.String feeMinAmt,
		java.lang.String feeDiv1,
		java.lang.String feeDiv2,
		java.lang.String feeDiv3,
		java.lang.String recUpdTs,
		java.lang.String recCrtTs) {

		super (
			mchtNo,
			settleType,
			feeType,
			feeMaxAmt,
			feeMinAmt,
			feeDiv1,
			feeDiv2,
			feeDiv3,
			recUpdTs,
			recCrtTs);
	}

/*[CONSTRUCTOR MARKER END]*/


}