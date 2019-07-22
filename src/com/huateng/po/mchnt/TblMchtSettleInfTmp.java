package com.huateng.po.mchnt;

import com.huateng.po.mchnt.base.BaseTblMchtSettleInfTmp;
public class TblMchtSettleInfTmp extends BaseTblMchtSettleInfTmp {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblMchtSettleInfTmp () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TblMchtSettleInfTmp (java.lang.String mchtNo) {
		super(mchtNo);
	}

	/**
	 * Constructor for required fields
	 */
	public TblMchtSettleInfTmp (
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