package com.huateng.po.mchnt;

import com.huateng.po.mchnt.base.BaseTblInfMchntTp;

public class TblInfMchntTp extends BaseTblInfMchntTp {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblInfMchntTp () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TblInfMchntTp (TblInfMchntTpPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TblInfMchntTp (
		TblInfMchntTpPK id,
		java.lang.String mchntTpGrp,
		java.lang.String descr,
		java.lang.String recSt,
		java.lang.String lastOperIn,
		java.lang.String recUpdUsrId,
		java.lang.String recUpdTs,
		java.lang.String recCrtTs) {

		super (
			id,
			mchntTpGrp,
			descr,
			recSt,
			lastOperIn,
			recUpdUsrId,
			recUpdTs,
			recCrtTs);
	}

/*[CONSTRUCTOR MARKER END]*/


}