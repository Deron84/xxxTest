package com.huateng.struts.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.huateng.commquery.dao.CommQueryDAO;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * project JSBConsole date 2013-4-2
 * 
 * @author 樊东东 报表的一些查询顺序和一些公共方法
 */
public class QueryExcelUtil {

	public static List<Integer[]> flagList;// 供报表5.1全辖各业务情况汇总日报表 用 商户性质的排序
	static {
		flagList = new ArrayList<Integer[]>();
		flagList.add(new Integer[] { null, null });// 线上商户
		flagList.add(new Integer[] { 0, 0 });// 线上商户
		flagList.add(new Integer[] { 0, 1 });// 线下商户

		flagList.add(new Integer[] { 1, 0 });// 业主收款商户
		flagList.add(new Integer[] { 1, 1 });// 业主付款商户

		flagList.add(new Integer[] { 2, 0 });// 汽车分期商户
		flagList.add(new Integer[] { 2, 1 });// 停车位分期商户
		flagList.add(new Integer[] { 2, 2 });// 家装分期商户
		flagList.add(new Integer[] { 2, 3 });// POS分期商户
		flagList.add(new Integer[] { 2, 4 });// 其他分期商户

		flagList.add(new Integer[] { 3, 0 });// 本行财务转账商户
		flagList.add(new Integer[] { 3, 1 });// 跨行财务转账商户

		flagList.add(new Integer[] { 4, 0 });// 交通罚没项目
		flagList.add(new Integer[] { 4, 1 });// 体彩购额项目
		flagList.add(new Integer[] { 4, 2 });// 其他项目

		flagList.add(new Integer[] { 5, null });// 积分业务
		flagList.add(new Integer[] { 7, null });// 固话pos收款
		flagList.add(new Integer[] { 7, 4 });// 固话pos付款
		flagList.add(new Integer[] { 6, null });// 其他业务
		
	}

	
}
