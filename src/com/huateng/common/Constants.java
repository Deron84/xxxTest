/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-3-9       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.common;

/**
 * Title:静态全局变量宏定义
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-9
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class Constants {
	
	/**系统一级菜单*/
	public static final String MENU_LVL_1 = "1";
	/**系统二级菜单*/
	public static final String MENU_LVL_2 = "2";
	/**系统三级菜单*/
	public static final String MENU_LVL_3 = "0";
	/**菜单编号*/
	public static final String MENU_ID = "id";
	/**父菜单编号*/
	public static final String MENU_PARENT_ID = "parent_id";
	/**菜单名称*/
	public static final String MENU_TEXT = "text";
	/**菜单链接*/
	public static final String MENU_URL = "url";
	/**菜单叶子节点标识*/
	public static final String MENU_LEAF = "leaf";
	/**树节点样式*/
	public static final String MENU_CLS = "cls";
	/**叶子节点样式*/
	public static final String MENU_FILE = "file";
	/**父节点样式*/
	public static final String MENU_FOLDER = "folder";
	/**子菜单标识*/
	public static final String MENU_CHILDREN = "children";
	/**系统成功提示*/
	public static final String SUCCESS_HEADER = "success";
	/**系统成功提示*/
	public static final String PROMPT_MSG = "msg";
	/**系统提示代码*/
	public static final String PROMPT_CODE = "code";
	/**生产模式标识*/
	public static final String PRODUCTION_MODE_FLG = "PRODUCTION_MODE";
	/**操作员信息*/
	public static final String OPERATOR_INFO = "operator";
	/**操作员id信息*/
	public static final String OPERATOR_ID = "oprId";
	/**菜单事件*/
	public static final String MENU_HANDLER = "handler";
	/**方法名称*/
	public static final String MENU_LVL1_FUNC = "changeTree";
	/**JSON格式的方法名称*/
	public static final String MENU_LVL1_JSON_FUNC = "\"changeTree\"";
	/**方法名称*/
	public static final String MENU_LVL3_FUNC = "changeTxn";
	/**JSON格式的方法名称*/
	public static final String MENU_LVL3_JSON_FUNC = "\"changeTxn\"";
	/**树形菜单SESSION变量*/
	public static final String TREE_MENU_MAP = "tree_menu_map";
	/**交易权限*/
	public static final String USER_AUTH_SET = "user_auth_set";
	/**工具栏SESSION变量*/
	public static final String TOOL_BAR_STR = "tool_bar_str";
	/**字菜单标识*/
	public static final String TOOL_BAR_CHILDREN = "menu";
	/**工具栏类型*/
	public static final String TOOL_BAR_TYPE = "xtype";
	/**按钮类型*/
	public static final String TOOL_BAR_BUTTON = "splitbutton";
	/**菜单图标*/
	public static final String TOOLBAR_ICON = "iconCls";
	/**一级菜单图标样式*/
	public static final String TOOLBAR_ICON_MENU = "splitMenu";
	/**菜单项图标样式*/
	public static final String TOOLBAR_ICON_MENUITEM = "splitButtonItem";
	/**菜单数初始化变量*/
	public static final String TREE_INIT_FLG = "init";
	/**系统日志编号识别类型*/
	public static final String NEXT_ID_LOG_FLG = "TXN_LOG";
	/**角色编号识别类型*/
	public static final String NEXT_ID_ROLE_FLG = "ROLE_ID";
	/**存于SESSION中的日志信息*/
	public static final String TXN_LOG_SESSION = "TXN_LOG_SESSION";
	
	/**总行机构号*/
	public static final String HEAD_OFFICE = "0000";
	
    // SysDic 表
	public final static String TABLE_ROOT = "results";
    public final static String TABLE_ROW = "row";
    public final static String SYS_DIC_TBL_NM = "TBL_NM";
    public final static String SYS_DIC_FLD_NM = "FLD_NM";
    public final static String SYS_DIC_FLD_VAL = "FLD_VAL";
    public final static String SYS_DIC_FLD_DESC = "FLD_DESC";
    
    //SelectOptions
    public final static String SLT_TXN_ID = "TXN_ID";
    public final static String SLT_XML_ID = "id";
    public final static String SLT_EXTRACT_TYPE = "EXTRACT_TYPE";
    public final static String SLT_ATTR_VALUE = "value";
    public final static String SLT_MODE_STATIC = "STATIC_MODE";
    public final static String SLT_MODE_SQL = "SQL_MODE";
    public final static String SLT_MODE_SQL_SQL = "SQL";
    public final static String SLT_MODE_QUERYDAO = "QUERY_DAO";
    public final static String SLT_MODE_DYNAMIC = "DYNAMIC_MODE";
    public final static String SLT_MODE_DYNAMIC_METHOD = "METHOD";
    public final static String SLT_MODE_DYNAMIC_ATTR_NAME = "name";
    public final static String SLT_MODE_SQL_VALUE = "SQL";
    public final static String SLT_MODE_STATIC_VALUE = "STATIC";
    public final static String SLT_MODE_DYNAMIC_VALUE = "DYNAMIC";
    
    /**SysDic路径*/
    public final static String SYSDIC_CONTEXTPATH = "/WEB-INF/xml/SysDic.xml";
    /**SelectionOptions路径*/
    public final static String SELECTOPTION_CONTEXTPATH = "/WEB-INF/xml/SelectOptions.xml";
    /**信息列表路径*/
    public static final String GRID_CONFIG_CONTEXTPATH = "/WEB-INF/xml/GridConfig.xml";
    /**JSON数据头信息*/
    public static final String JSON_HEANDER_DATA = "data";
    /**JSON数据总数*/
    public static final String JSON_HEANDER_TOTALCOUNT = "totalCount";
    /**查询显示的记录数*/
    public static int QUERY_RECORD_COUNT = 15;
    /**下拉列表记录数*/
    public static int QUERY_SELECT_COUNT = 50;
    /**下拉列表隐藏值*/
    public static final String SELECT_VALUE_FIELD = "valueField";
    /**下拉列表显示值*/
    public static final String SELECT_DISPLAY_FIELD = "displayField";
    /**上下文路径*/
    public static String CONTEXTPATH = null;
    /**成功代码*/
    public static final String SUCCESS_CODE = "00";
    /**自定义代码*/
    public static final String SUCCESS_CODE_CUSTOMIZE = "$C";
    /**数据存储失败*/
    public static final String DATA_OPR_FAIL = "数据存储失败，请重试";
    /**失败代码*/
    public static final String FAILURE_CODE = "-1";
    /**PDF格式报表*/
    public static final String REPORT_PDFMODE = "PDF";
    /**RTF格式报表*/
    public static final String REPORT_RTFMODE = "RTF";
    /**HTML格式报表*/
    public static final String REPORT_HTMLMODE = "HTML";
    /**TXT格式报表*/
    public static final String REPORT_TXTMODE = "TXT";
    /**XLS格式报表*/
    public static final String REPORT_EXCELMODE = "EXCEL";
    
    public final static String ADD = "A";
    public final static String MODIFY = "M";
    public final static String DELETE = "D";
    public final static String VALID = "1";
    public final static String INVALID = "0";
    
    public final static String DEFAULT = "-";
    
    public final static String DEFAULT_ERROR = "999999";
    
    public final static String ERR_ATTRIBUTE = "没有正确获得页面传递参数，请重试！";
    
    public final static String INIT_PAPER_ID = "INIT_PAPER_ID";
    
    
    /***********二期添加内容 by feihong247*************/
    public final static String ERR_EXIST = "您所录入的记录已存在，请重试！";
    
    public final static String ERR_NO_DATA = "您所请求的记录不存在，请刷新纪录后重试！";
    
    /***********新增标示：00代表一卡通、01代表银联*************/
    public final static String TYPE_ONE = "00";
    public final static String TYPE_TWO = "01";
}
