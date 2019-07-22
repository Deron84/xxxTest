package com.huateng.struts.query;

import java.util.ResourceBundle;

/**
 * project JSBConsole date 2013-4-1
 * 
 * @author 樊东东
 */
public class ExcelName {

	private static final String NAME_FILE_DIR = "dir";

	private static final String EXCEL_NAME_FILE = "jsbank_excel_name";

	private static ResourceBundle enr = ResourceBundle
			.getBundle(EXCEL_NAME_FILE);// 资源

	/** 5.1全辖各业务情况汇总日报表.xls */
	public static final String EN_51 = "RN1RN";

	/** 5.2全辖外包服务费用统计月报表.xls */
	public static final String EN_52 = "RN2RN";

	/** 5.3营销员业绩明细日报表.xls */
	public static final String EN_53 = "RN3RN";

	/** 5.4疑似风险处理结果月报表 */
	public static final String EN_54 = "RN4RN";

	/** 5.5不平账交易统计月报表.xls */
	public static final String EN_55 = "RN5RN";

	/** 5.6银行卡受理终端数量统计报表.xls */
	public static final String EN_56 = "RN6RN";

	/** 5.7银行卡收单业务量统计报表.xls */
	public static final String EN_57 = "RN7RN";

	/** 5.8营销活动统计月表.xls */
	public static final String EN_58 = "RN8RN";

	/** 5.9全辖本行开户客户统计月报表.xls */
	public static final String EN_59 = "RN9RN";

	/** 5.10全辖终端装机时效统计月报表 */
	public static final String EN_510 = "RN10RN";

	/** 5.11全辖自购终端使用情况月报表 */
	public static final String EN_511 = "RN11RN";

	/** 5.12全辖终端巡检执行情况统计月报表.xls */
	public static final String EN_512 = "RN12RN";

	/** 5.14收益终端明细月报表.xls */
	public static final String EN_514 = "RN14RN";

	/** 5.15商户正式表全部信息.xls */
	public static final String EN_515 = "RN15RN";

	/** 历史交易信息.xls */
	public static final String EN_516 = "RN16RN";
	// 商户交易报表
	public static final String EN_518 = "RN18RN";
	/** 交易信息.xls */
	public static final String EN_5161 = "RN161RN";
	/** 广东交易信息导出 */
	public static final String EN_5162 = "RN162RN";

	/** 直联商户统计表.xls */
	public static final String EN_517 = "RN17RN";

	/** 历史交易信息.xls */
	public static final String EN_520 = "RN20RN";
	/** 历史交易信息.xls */
	public static final String EN_521 = "RN21RN";
	/** 商户达标信息.xls */
	public static final String EN_50120 = "RN50120RN";

	/** 交易统计(商户).xls */
	public static final String EN_51004 = "RN51004RN";
	
	/** 交易统计(终端).xls */
	public static final String EN_51005 = "RN51005RN";
	
	/**支付宝对账.xls */
	public static final String EN_51006 = "RN51006RN";
	
	/** 微信对账.xls */
	public static final String EN_51007 = "RN51007RN";
	/** 工具更换标签.xls */
	public static final String Rl_110101 = "RL110101RL";
	
	
	/** 获取excel文件名 (带目录的相对路径) */
	public static String getFileName(String key) {
		return enr.getString(NAME_FILE_DIR) + enr.getString(key);
	}

	/** 获取值 */
	public static String getValue(String key) {
		return enr.getString(key);
	}

}
