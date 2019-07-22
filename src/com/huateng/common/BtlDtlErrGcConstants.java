package com.huateng.common;


/**
 * project JSBConsole
 * date 2013-3-19
 * @author 樊东东
 */
public class BtlDtlErrGcConstants {
	/**差错处理状态
	 * 00 按照正常交易向商户清算
	 * 01 初始待处理状态
	 * 02 等待调查押款处理
	 * 03 次日资金扣回
	 * 04 行方托收
	 * 05 多收款项退回发卡行
	 * 06 已清算
	 * */
	/**0 按照正常交易向商户清算*/
	public static final String TXN_ACQ_TYPE_0 = "00";
	/**1 初始待处理状态*/
	public static final String TXN_ACQ_TYPE_1 = "01";
	/**2 等待调查押款处理*/
	public static final String TXN_ACQ_TYPE_2 = "02";
	/**3 次日资金扣回*/
	public static final String TXN_ACQ_TYPE_3 = "03";
	/**4 行方托收*/
	public static final String TXN_ACQ_TYPE_4 = "04";
	/**5 多收款项退回发卡行*/
	public static final String TXN_ACQ_TYPE_5 = "05";
	/**6 已清算*/
	public static final String TXN_ACQ_TYPE_6 = "06";
}
