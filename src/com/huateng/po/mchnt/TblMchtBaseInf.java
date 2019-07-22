package com.huateng.po.mchnt;

import com.huateng.po.mchnt.base.BaseTblMchtBaseInf;



public class TblMchtBaseInf extends BaseTblMchtBaseInf {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblMchtBaseInf () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TblMchtBaseInf (java.lang.String mchtNo) {
		super(mchtNo);
	}

	/**
	 * Constructor for required fields
	 */
	public TblMchtBaseInf (
		java.lang.String mchtNo,
		java.lang.String mchtNm,
		java.lang.String addr,
		java.lang.String mcc,
		java.lang.String licenceNo,
//		java.lang.String licenceEndDate,
//		java.lang.String bankLicenceNo,
//		java.lang.String faxNo,
		java.lang.String contact,
		java.lang.String commEmail,
		java.lang.String manager,
		java.lang.String identityNo,
//		java.lang.String managerTel,
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
//			licenceEndDate,
//			bankLicenceNo,
//			faxNo,
			contact,
			commEmail,
			manager,
			identityNo,
//			managerTel,
			openTime,
			closeTime,
			recUpdTs,
			recCrtTs);
	}

/*[CONSTRUCTOR MARKER END]*/


}