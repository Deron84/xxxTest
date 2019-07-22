package com.huateng.po.settle;

import com.huateng.po.base.BaseTblInfileOpt;



public class TblInfileOpt extends BaseTblInfileOpt {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblInfileOpt () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TblInfileOpt (com.huateng.po.settle.TblInfileOptPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TblInfileOpt (
		com.huateng.po.settle.TblInfileOptPK id,
		java.lang.String startDate,
		java.lang.String endDate,
		java.math.BigDecimal settleAmt,
		java.math.BigDecimal txnAmt,
		java.math.BigDecimal settleFee1,
		java.lang.String stlmFlag) {

		super (
			id,
			startDate,
			endDate,
			settleAmt,
			txnAmt,
			settleFee1,
			stlmFlag);
	}

/*[CONSTRUCTOR MARKER END]*/


}