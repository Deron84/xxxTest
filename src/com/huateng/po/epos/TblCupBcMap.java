package com.huateng.po.epos;

import com.huateng.po.base.BaseTblCupBcMap;



public class TblCupBcMap extends BaseTblCupBcMap {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblCupBcMap () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TblCupBcMap (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TblCupBcMap (
		java.lang.String id,
		java.lang.String bcId) {

		super (
			id,
			bcId);
	}

/*[CONSTRUCTOR MARKER END]*/


}