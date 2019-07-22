package com.huateng.common.accident;

public class TblManualReturnConstants {

	/**手工退货状态--新增待审核*/
	public static final String ADD_UNCHECK = "1";
	
	/**手工退货状态--审核通过，待处理，待退货*/
	public static final String WAIT_FOR_RETURNS = "2";
	
	/**手工退货状态--退货成功*/
	public static final String RETURNS_OK = "3";
	
	/**手工退货状态--审核不通过*/
	public static final String CHECK_FALSE = "4";
	
	/**手工退货状态--需手工付款*/
	public static final String MANUAL_PAY = "5";
	
	/**手工退货计数--初始*/
	public static final String COUNT_INIT = "0";
	
	/**手工退货无值--默认插入*/
	public static final String NO_VALUE = "-";
}
