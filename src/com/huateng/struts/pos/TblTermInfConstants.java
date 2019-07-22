package com.huateng.struts.pos;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-20
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class TblTermInfConstants {

	/**
	 * 
	 * 0：新增待审核 1：待装机 2 ：已装机 3：修改待审核4：冻结 5：冻结待审核 6:解冻待审核7：注销8：注销待审核 
	 *A:新增退回 
	 */
	/** 0：新增待审核 */
	public static final String TERM_ST_INIT = "0";

	/** 1：待装机 */
	public static final String TERM_ST_INST_WAIT = "1";

	/** 2 ：已装机 */
	public static final String TERM_ST_INST = "2";
	
	/** 3：修改待审核 */
	public static final String TERM_ST_UPD_UNCHECK = "3";


	/** 4：冻结 */
	public static final String TERM_ST_STOP = "4";

	/** 5：冻结待审核 */
	public static final String TERM_ST_STOP_UNCHCK = "5";

	/** 6：解冻待审核 */
	public static final String TERM_ST_RECOVER_UNCHECK = "6";

	/** 7：注销 */
	public static final String TERM_ST_CLOSE = "7";

	/** 8：注销待审核 */
	public static final String TERM_ST_CLOSE_UNCHCK = "8";

	/** 9：撤机 */
//	public static final String TERM_ST_WEAN = "9";//注销就是撤机
	
	/**A:新增退回*/
	public static final String TERM_ST_ADD_BACK = "A";
	
	/**R:注销恢复*/
	public static final String TERM_ST_REVIVE = "R";
	 
	
	
	/**拒绝类型：新增拒绝*/

	public static final String DEFUALT_CHECKBOX = "1";

	/**
	 * 启用:0 不可用:1
	 */
	public static final String TERM_SIGN_STA_OK = "0";

	public static final String TERM_SIGN_STA_ERR = "1";

	/**
	 * 签到默认状态
	 */
	public static final String TERM_SIGN_DEFAULT = "0";
	
	/**未选中*/
	public static final String UN_CHECKED = "0";
	/**选中*/
	public static final String CHECKED = "1";

	/**
	 * 0-我行产权 1-我行租赁 2-第三方投入
	 */
	public static final String PROP_TP_OWN = "0";

	public static final String PROP_TP_RENT = "1";

	public static final String PROP_TP_OTHERS = "2";

	public static final String OK = "0";

	public static final String REFUSE = "1";

	public static final String T30101_01 = "30101.01";

	public static final String T30101_02 = "30101.02";

	public static final String T30101_03 = "30101.03";

	public static final String T30101_04 = "30101.04";

	public static final String T30101_05 = "30101.05";

	public static final String T30101_06 = "30101.06";

	public static final String T30201_01 = "30201.01";

	public static final String T30201_02 = "30201.02";
}
