package com.huateng.po.mchnt;

import com.huateng.po.mchnt.base.BaseTblMchtBaseInfTmp;

public class TblMchtBaseInfTmp extends BaseTblMchtBaseInfTmp {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblMchtBaseInfTmp () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TblMchtBaseInfTmp (java.lang.String mchtNo) {
		super(mchtNo);
	}

	/**
	 * Constructor for required fields
	 */
	public TblMchtBaseInfTmp (
			java.lang.String mchtNo,
			java.lang.String mchtNm,
			java.lang.String addr,
			java.lang.String mcc,
			java.lang.String licenceNo,
			java.lang.String contact,
			java.lang.String commEmail,
			java.lang.String openTime,
			java.lang.String closeTime,
			java.lang.String recUpdTs,
			java.lang.String recCrtTs) {

		super (
			mchtNo,
			mchtNm,
			addr,
			mcc,
			licenceNo,
			contact,
			commEmail,
			openTime,
			closeTime,
			recUpdTs,
			recCrtTs);
	}

/*[CONSTRUCTOR MARKER END]*/


}