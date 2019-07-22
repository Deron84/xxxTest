package com.huateng.po.epos;

import com.huateng.po.base.BaseTblTxnCfgPK;

public class TblTxnCfgPK extends BaseTblTxnCfgPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblTxnCfgPK () {}
	
	public TblTxnCfgPK (
		java.lang.String termTxnCode,
		java.lang.String usageKey,
		java.lang.Integer cmdIdx) {

		super (
			termTxnCode,
			usageKey,
			cmdIdx);
	}
/*[CONSTRUCTOR MARKER END]*/


}