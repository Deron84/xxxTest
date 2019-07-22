package com.huateng.common.accident;

public class TblEntrustTradeConstants {

	/**托收状态--正常状态，待处理*/
	public static final String WAIT = "1";
	
	/**托收状态--处理成功，已清算*/
	public static final String SUCCESS = "2";
	
	/**托收状态--注销*/
	public static final String DEL = "3";

	/**托收表字段无值时默认插入的值*/
	public static final String NO_VALUE = "-";
	
	/**托收交易累计天数的计数初始值*/
	public static final String COUNT_INIT = "0";
}
