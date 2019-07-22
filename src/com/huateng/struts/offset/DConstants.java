package com.huateng.struts.offset;

/**
 * project JSBConsole date 2013-4-8
 * 
 * @author 樊东东
 */
public class DConstants {
	/**交易补登的 宏定义    由后台给出
	 * 交易码 手工退货 5171 商户资金转入补登 9101 商户手续费收取补登 9103 商户手续费退还补登 9205
	 */
	/**商户退货补登*/
	public static final String MCHT_PRD_RE = "5171";
	/**商户资金转入补登*/
	public static final String MCHT_AMT_AC = "9101";
	/**商户手续费收取补登*/
	public static final String MCHT_FEE_AC = "9103";
	/**商户手续费退还补登*/
	public static final String MCHT_FEE_RE = "9205";
}
