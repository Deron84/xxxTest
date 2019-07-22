package com.huateng.po.epos;

import com.huateng.po.base.BaseTblPptMsg;



public class TblPptMsg extends BaseTblPptMsg {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblPptMsg () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TblPptMsg (com.huateng.po.epos.TblPptMsgPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TblPptMsg (
		com.huateng.po.epos.TblPptMsgPK id,
		java.lang.Integer tmpId,
		java.lang.String crtDate,
		java.lang.String updDate) {

		super (
			id,
			tmpId,
			crtDate,
			updDate);
	}

/*[CONSTRUCTOR MARKER END]*/


}