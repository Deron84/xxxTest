package com.huateng.po.mchnt;

import com.huateng.po.mchnt.base.BaseTblInfMchntTpGrp;




public class TblInfMchntTpGrp extends BaseTblInfMchntTpGrp {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblInfMchntTpGrp () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TblInfMchntTpGrp (TblInfMchntTpGrpPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TblInfMchntTpGrp (
		TblInfMchntTpGrpPK id,
		java.lang.String descr,
		java.lang.String recSt,
		java.lang.String lastOperIn,
		java.lang.String recUpdUsrId,
		java.lang.String recUpdTs,
		java.lang.String recCrtTs) {

		super (
			id,
			descr,
			recSt,
			lastOperIn,
			recUpdUsrId,
			recUpdTs,
			recCrtTs);
	}

/*[CONSTRUCTOR MARKER END]*/


}