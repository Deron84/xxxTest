package com.huateng.common.accident;

public class TblRTxnConstants {

	/**手工退货状态--人工新增待审核*/
	public static final String OPR_ADD_UNCHECK = "01";
	
	/**手工退货状态--人工新增审核通过*/
	public static final String OPR_ADD_ACCEPT = "11";
	
	/**手工退货状态--人工新增审核拒绝*/
	public static final String OPR_ADD_REFUSE = "21";
	
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

	/**退货未重复-匹配单笔**/
	public static final String NO_REPEAT_SINGLE_MATCH = "00";
	
	/**退货重复-匹配单笔**/
	public static final String REPEAT_SINGLE_MATCH = "10";
	
	/**退货未重复-匹配多笔**/
	public static final String NO_REPEAT_MUL_MATCH = "01";
	
	/**退货重复-匹配多笔**/
	public static final String REPEAT_MUL_MATCH = "11";
	
	/**退货未重复-未匹配上**/
	public static final String NO_REPEAT_NO_MATCH = "02";
	
	/**退货重复-未匹配上**/
	public static final String REPEAT_NO_MATCH = "12";
}
