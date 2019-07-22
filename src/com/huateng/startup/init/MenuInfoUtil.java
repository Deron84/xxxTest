package com.huateng.startup.init;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.po.TblFuncInf;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.JSONBean;

/**
 * Title:系统菜单初始化
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
public class MenuInfoUtil {

    private static JSONBean allMenuBean = new JSONBean();

    /**
     * 加载全部菜单
     */
    @SuppressWarnings("unchecked")
    public static void init() {

        String hql = "from com.huateng.po.TblFuncInf t where t.FuncType in ('0','1','2') order by t.FuncId";
        ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
        List<TblFuncInf> funcInfList = commQueryDAO.findByHQLQuery(hql);

        for (int i = 0, n = funcInfList.size(); i < n; i++) {

            TblFuncInf tblFuncInf = funcInfList.get(i);
            Map<String, Object> menuBean = new LinkedHashMap<String, Object>();

            //判断餐单图标样式，如果没有或‘-’则默认样式
            if (StringUtil.isEmpty(tblFuncInf.getIconPath()) || "-".equals(tblFuncInf.getIconPath().trim())) {

                menuBean.put(Constants.TOOLBAR_ICON, Constants.TOOLBAR_ICON_MENUITEM);
            } else {

                menuBean.put(Constants.TOOLBAR_ICON, tblFuncInf.getIconPath().trim());
            }

            if (Constants.MENU_LVL_1.equals(tblFuncInf.getFuncType())) { //一级菜单

                menuBean.put(Constants.MENU_ID, tblFuncInf.getFuncId().toString().trim());
                menuBean.put(Constants.MENU_TEXT, tblFuncInf.getFuncName().trim());
                menuBean.put(Constants.MENU_CLS, Constants.MENU_FOLDER);
                allMenuBean.addJSONArrayElement(menuBean);
            } else if (Constants.MENU_LVL_2.equals(tblFuncInf.getFuncType())) { //二级菜单

                menuBean.put(Constants.MENU_ID, tblFuncInf.getFuncId().toString().trim());
                menuBean.put(Constants.MENU_TEXT, tblFuncInf.getFuncName().trim());
                menuBean.put(Constants.MENU_PARENT_ID, tblFuncInf.getFuncParentId().toString().trim());
                menuBean.put(Constants.MENU_CLS, Constants.MENU_FOLDER);
                addLvl2Menu(menuBean);
            } else if (Constants.MENU_LVL_3.equals(tblFuncInf.getFuncType())) { //三级菜单

                menuBean.put(Constants.MENU_ID, tblFuncInf.getFuncId().toString().trim());
                menuBean.put(Constants.MENU_TEXT, tblFuncInf.getFuncName().trim());
                menuBean.put(Constants.MENU_PARENT_ID, tblFuncInf.getFuncParentId().toString().trim());
                menuBean.put(Constants.MENU_LEAF, true);
                menuBean.put(Constants.MENU_URL, tblFuncInf.getPageUrl().trim());
                menuBean.put(Constants.MENU_CLS, Constants.MENU_FILE);
                addLvl3Menu(menuBean);
            }
        }

        //删除没有用的菜单
        List<Object> menuLvl1List = allMenuBean.getDataList();
        for (int i = 0; i < menuLvl1List.size(); i++) {
            Map<String, Object> menuLvl1Bean = (Map<String, Object>) menuLvl1List.get(i);
            if (!menuLvl1Bean.containsKey(Constants.MENU_CHILDREN)) {
                menuLvl1List.remove(i);
                i--;
                continue;
            }
            List<Object> menuLvl2List = (List<Object>) menuLvl1Bean.get(Constants.MENU_CHILDREN);
            for (int j = 0; j < menuLvl2List.size(); j++) {
                Map<String, Object> menuLvl2Bean = (Map<String, Object>) menuLvl2List.get(j);
                if (!menuLvl2Bean.containsKey(Constants.MENU_CHILDREN)) {
                    menuLvl2List.remove(j);
                    menuLvl1Bean.put(Constants.MENU_CHILDREN, menuLvl2List);
                    menuLvl1List.set(i, menuLvl1Bean);
                    allMenuBean.setDataList(menuLvl1List);
                    j--;
                }
            }
        }
    }

    /**
     * 将二级菜单添加到一级菜单中
     * @param menuBean
     */
    @SuppressWarnings("unchecked")
    private static void addLvl2Menu(Map<String, Object> menuBean) {

        List<Object> menuLvl1List = allMenuBean.getDataList();

        for (int i = 0, n = menuLvl1List.size(); i < n; i++) {

            Map<String, Object> tmpMenuBean = (Map<String, Object>) menuLvl1List.get(i);
            //如果二级菜单的父菜单编号和一级菜单编号一致，则加入到该一级菜单中
            if (tmpMenuBean.get(Constants.MENU_ID).toString().trim().equals(menuBean.get(Constants.MENU_PARENT_ID).toString().trim())) {
                if (!tmpMenuBean.containsKey(Constants.MENU_CHILDREN)) { //如果一级list中没有子list，则新建子list，然后把子信息放入子list
                    LinkedList<Object> menuLvl2List = new LinkedList<Object>();
                    menuLvl2List.add(menuBean);
                    tmpMenuBean.put(Constants.MENU_CHILDREN, menuLvl2List);
                } else { //如果一级list中已经有子list，就直接放（说明之前有一个该一级菜单的子菜单新建了子list）
                    LinkedList<Object> menuLvl2List = (LinkedList<Object>) tmpMenuBean.get(Constants.MENU_CHILDREN);
                    menuLvl2List.add(menuBean);
                    tmpMenuBean.put(Constants.MENU_CHILDREN, menuLvl2List);
                }
                menuLvl1List.set(i, tmpMenuBean);
            }
        }
        allMenuBean.setDataList(menuLvl1List);
    }

    /**
     * 将三级菜单添加到二级菜单中
     * @param menuBean
     */
    @SuppressWarnings("unchecked")
    private static void addLvl3Menu(Map<String, Object> menuBean) {

        List<Object> menuLvl1List = allMenuBean.getDataList();

        for (int i = 0, n = menuLvl1List.size(); i < n; i++) {

            Map<String, Object> tmpMenuBeanLvl1 = (Map<String, Object>) menuLvl1List.get(i);
            LinkedList<Object> menuLvl2List = (LinkedList<Object>) tmpMenuBeanLvl1.get(Constants.MENU_CHILDREN);

            if (menuLvl2List == null) {
                continue;
            }

            for (int j = 0, m = menuLvl2List.size(); j < m; j++) {

                Map<String, Object> tmpMenuBeanLvl2 = (Map<String, Object>) menuLvl2List.get(j);
                //如果三级菜单的父菜单编号和 二级菜单编号一致，则加入到该一级菜单中
                if (tmpMenuBeanLvl2.get(Constants.MENU_ID).toString().trim().equals(menuBean.get(Constants.MENU_PARENT_ID).toString().trim())) {
                    if (!tmpMenuBeanLvl2.containsKey(Constants.MENU_CHILDREN)) { //如果该二级菜单list中没有子list，则新建，并放入
                        LinkedList<Object> menuLvl3List = new LinkedList<Object>();
                        menuLvl3List.add(menuBean);
                        tmpMenuBeanLvl2.put(Constants.MENU_CHILDREN, menuLvl3List);
                    } else { //如果有，则直接放入
                        LinkedList<Object> menuLvl3List = (LinkedList<Object>) tmpMenuBeanLvl2.get(Constants.MENU_CHILDREN);
                        menuLvl3List.add(menuBean);
                        tmpMenuBeanLvl2.put(Constants.MENU_CHILDREN, menuLvl3List);
                    }
                    menuLvl2List.set(j, tmpMenuBeanLvl2);
                }
            }
            tmpMenuBeanLvl1.put(Constants.MENU_CHILDREN, menuLvl2List);
            menuLvl1List.set(i, tmpMenuBeanLvl1);
        }
        allMenuBean.setDataList(menuLvl1List);
    }

    /**
     * 通过级别分配交易权限
     * @param degree
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, Object> setOperatorMenuWithDegree(String degree, String contextPath) {

        String sql = "select VALUE_ID FROM TBL_ROLE_FUNC_MAP WHERE KEY_ID = " + degree;
        ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
        List<Object> funcMapList = commQueryDAO.findBySQLQuery(sql);

        List<Object> menuLvl1List = allMenuBean.getDataList();
        LinkedHashMap<String, Object> menuIndexMap = new LinkedHashMap<String, Object>();

        //遍历寻找匹配的菜单
        for (int i = 0, n = menuLvl1List.size(); i < n; i++) {

            Map<String, Object> menuLvl1Map = (Map<String, Object>) menuLvl1List.get(i);
            LinkedList<Object> menuLvl2List = (LinkedList<Object>) menuLvl1Map.get(Constants.MENU_CHILDREN);

            for (int j = 0, m = menuLvl2List.size(); j < m; j++) {

                Map<String, Object> menuLvl2Map = (Map<String, Object>) menuLvl2List.get(j);
                LinkedList<Object> menuLvl3List = (LinkedList<Object>) menuLvl2Map.get(Constants.MENU_CHILDREN);

                for (int k = 0, l = menuLvl3List.size(); k < l; k++) {

                    Map<String, Object> menuLvl3Map = (Map<String, Object>) menuLvl3List.get(k);

                    for (int a = 0, b = funcMapList.size(); a < b; a++) {

                        if (StringUtils.trim(funcMapList.get(a).toString()).equals(StringUtils.trim(menuLvl3Map.get(Constants.MENU_ID).toString()))) {

                            if (!menuIndexMap.containsKey(menuLvl1Map.get(Constants.MENU_ID).toString())) {

                                Map<String, Object> menuLvl1HashMap = new LinkedHashMap<String, Object>();
                                menuLvl1HashMap.put(Constants.MENU_ID, menuLvl1Map.get(Constants.MENU_ID));
                                menuLvl1HashMap.put(Constants.MENU_TEXT, menuLvl1Map.get(Constants.MENU_TEXT));
                                menuLvl1HashMap.put(Constants.MENU_CLS, menuLvl1Map.get(Constants.MENU_CLS));
                                menuLvl1HashMap.put(Constants.MENU_HANDLER, Constants.MENU_LVL1_FUNC);
                                menuLvl1HashMap.put(Constants.TOOL_BAR_TYPE, Constants.TOOL_BAR_BUTTON);
                                menuLvl1HashMap.put(Constants.TOOLBAR_ICON, Constants.TOOLBAR_ICON_MENU);

                                Map<String, Object> menuLvl2HashMap = new LinkedHashMap<String, Object>();
                                menuLvl2HashMap.put(Constants.MENU_ID, menuLvl2Map.get(Constants.MENU_ID));
                                menuLvl2HashMap.put(Constants.MENU_TEXT, menuLvl2Map.get(Constants.MENU_TEXT));
                                menuLvl2HashMap.put(Constants.MENU_PARENT_ID, menuLvl2Map.get(Constants.MENU_PARENT_ID));
                                menuLvl2HashMap.put(Constants.MENU_CLS, menuLvl2Map.get(Constants.MENU_CLS));
                                menuLvl2HashMap.put(Constants.TOOLBAR_ICON, menuLvl2Map.get(Constants.TOOLBAR_ICON));

                                Map<String, Object> menuLvl3HashMap = new LinkedHashMap<String, Object>();
                                menuLvl3HashMap.put(Constants.MENU_ID, menuLvl3Map.get(Constants.MENU_ID));
                                menuLvl3HashMap.put(Constants.MENU_TEXT, menuLvl3Map.get(Constants.MENU_TEXT));
                                menuLvl3HashMap.put(Constants.MENU_PARENT_ID, menuLvl3Map.get(Constants.MENU_PARENT_ID));
                                menuLvl3HashMap.put(Constants.MENU_LEAF, true);
                                menuLvl3HashMap.put(Constants.MENU_URL, contextPath + menuLvl3Map.get(Constants.MENU_URL));
                                menuLvl3HashMap.put(Constants.MENU_CLS, menuLvl3Map.get(Constants.MENU_CLS));
                                menuLvl3HashMap.put(Constants.MENU_HANDLER, Constants.MENU_LVL3_FUNC);
                                menuLvl3HashMap.put(Constants.TOOLBAR_ICON, menuLvl3Map.get(Constants.TOOLBAR_ICON));

                                LinkedList<Object> menuLvl3Child = new LinkedList<Object>();
                                menuLvl3Child.add(menuLvl3HashMap);
                                menuLvl2HashMap.put(Constants.MENU_CHILDREN, menuLvl3Child);
                                menuLvl2HashMap.put(Constants.TOOL_BAR_CHILDREN, menuLvl3Child);

                                LinkedList<Object> menuLvl2Child = new LinkedList<Object>();
                                menuLvl2Child.add(menuLvl2HashMap);
                                menuLvl1HashMap.put(Constants.MENU_CHILDREN, menuLvl2Child);
                                menuLvl1HashMap.put(Constants.TOOL_BAR_CHILDREN, menuLvl2Child);

                                menuIndexMap.put(menuLvl1Map.get(Constants.MENU_ID).toString(), menuLvl1HashMap);
                            } else {

                                Map<String, Object> menuLvl1HashMap = (Map<String, Object>) menuIndexMap
                                        .get(menuLvl1Map.get(Constants.MENU_ID).toString());
                                LinkedList<Object> menuLvl2Child = (LinkedList<Object>) menuLvl1HashMap.get(Constants.MENU_CHILDREN);
                                boolean hasMenu = false;

                                for (int c = 0, d = menuLvl2Child.size(); c < d; c++) {

                                    Map<String, Object> menuLvl2HashMap = (Map<String, Object>) menuLvl2Child.get(c);

                                    if (StringUtils.trim(menuLvl2HashMap.get(Constants.MENU_ID).toString())
                                            .equals(StringUtils.trim(menuLvl2Map.get(Constants.MENU_ID).toString()))) {

                                        LinkedList<Object> menuLvl3Child = (LinkedList<Object>) menuLvl2HashMap.get(Constants.MENU_CHILDREN);
                                        Map<String, Object> menuLvl3HashMap = new LinkedHashMap<String, Object>();
                                        menuLvl3HashMap.put(Constants.MENU_ID, menuLvl3Map.get(Constants.MENU_ID));
                                        menuLvl3HashMap.put(Constants.MENU_TEXT, menuLvl3Map.get(Constants.MENU_TEXT));
                                        menuLvl3HashMap.put(Constants.MENU_PARENT_ID, menuLvl3Map.get(Constants.MENU_PARENT_ID));
                                        menuLvl3HashMap.put(Constants.MENU_LEAF, true);
                                        menuLvl3HashMap.put(Constants.MENU_URL, contextPath + menuLvl3Map.get(Constants.MENU_URL));
                                        menuLvl3HashMap.put(Constants.MENU_CLS, menuLvl3Map.get(Constants.MENU_CLS));
                                        menuLvl3HashMap.put(Constants.MENU_HANDLER, Constants.MENU_LVL3_FUNC);
                                        menuLvl3HashMap.put(Constants.TOOLBAR_ICON, menuLvl3Map.get(Constants.TOOLBAR_ICON));
                                        menuLvl3Child.add(menuLvl3HashMap);
                                        menuLvl2HashMap.put(Constants.MENU_CHILDREN, menuLvl3Child);
                                        menuLvl2HashMap.put(Constants.TOOL_BAR_CHILDREN, menuLvl3Child);
                                        menuLvl2Child.set(c, menuLvl2HashMap);
                                        hasMenu = true;
                                        break;
                                    }
                                }
                                if (!hasMenu) {
                                    Map<String, Object> menuLvl2HashMap = new HashMap<String, Object>();
                                    menuLvl2HashMap.put(Constants.MENU_ID, menuLvl2Map.get(Constants.MENU_ID));
                                    menuLvl2HashMap.put(Constants.MENU_TEXT, menuLvl2Map.get(Constants.MENU_TEXT));
                                    menuLvl2HashMap.put(Constants.MENU_PARENT_ID, menuLvl2Map.get(Constants.MENU_PARENT_ID));
                                    menuLvl2HashMap.put(Constants.MENU_CLS, menuLvl2Map.get(Constants.MENU_CLS));
                                    menuLvl2HashMap.put(Constants.TOOLBAR_ICON, menuLvl2Map.get(Constants.TOOLBAR_ICON));

                                    LinkedList<Object> menuLvl3Child = new LinkedList<Object>();
                                    Map<String, Object> menuLvl3HashMap = new LinkedHashMap<String, Object>();
                                    menuLvl3HashMap.put(Constants.MENU_ID, menuLvl3Map.get(Constants.MENU_ID));
                                    menuLvl3HashMap.put(Constants.MENU_TEXT, menuLvl3Map.get(Constants.MENU_TEXT));
                                    menuLvl3HashMap.put(Constants.MENU_PARENT_ID, menuLvl3Map.get(Constants.MENU_PARENT_ID));
                                    menuLvl3HashMap.put(Constants.MENU_LEAF, true);
                                    menuLvl3HashMap.put(Constants.MENU_URL, contextPath + menuLvl3Map.get(Constants.MENU_URL));
                                    menuLvl3HashMap.put(Constants.MENU_CLS, menuLvl3Map.get(Constants.MENU_CLS));
                                    menuLvl3HashMap.put(Constants.MENU_HANDLER, Constants.MENU_LVL3_FUNC);
                                    menuLvl3HashMap.put(Constants.TOOLBAR_ICON, menuLvl3Map.get(Constants.TOOLBAR_ICON));

                                    menuLvl3Child.add(menuLvl3HashMap);
                                    menuLvl2HashMap.put(Constants.MENU_CHILDREN, menuLvl3Child);
                                    menuLvl2HashMap.put(Constants.TOOL_BAR_CHILDREN, menuLvl3Child);
                                    menuLvl2Child.add(menuLvl2HashMap);
                                }
                                menuLvl1HashMap.put(Constants.MENU_CHILDREN, menuLvl2Child);
                                menuLvl1HashMap.put(Constants.TOOL_BAR_CHILDREN, menuLvl2Child);
                                menuIndexMap.put(menuLvl1Map.get(Constants.MENU_ID).toString(), menuLvl1HashMap);
                            }
                        }
                    }
                }
            }
        }
        return menuIndexMap;
    }

    /**
     * 封装柜员的交易权限
     * 
     * @param degree
     * @return
     * 2011-9-16上午11:17:18
     */
    @SuppressWarnings("unchecked")
    public static HashSet<String> getAuthSet(String degree) {
        HashSet<String> set = new HashSet<String>();
        ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
        String sql = "select VALUE_ID FROM TBL_ROLE_FUNC_MAP WHERE KEY_ID = " + degree;
        List<Object> funcMapList = commQueryDAO.findBySQLQuery(sql);

        Iterator<Object> it = funcMapList.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (!StringUtil.isNull(obj)) {
                set.add(obj.toString());
            }
        }
        return set;
    }
}
