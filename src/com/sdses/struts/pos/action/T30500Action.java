package com.sdses.struts.pos.action;

import java.util.ArrayList;
import java.util.List;

import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.ContextUtil;
import com.sdses.bo.pos.T30500BO;
import com.sdses.po.TblVegeCodeInfo;

/**
 * Title:机构维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-6-7
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T30500Action extends BaseAction {

    private static final long serialVersionUID = 1L;

    //	
    //	
    //菜品编码表BO
    private T30500BO t30500BO = (T30500BO) ContextUtil.getBean("T30500BO");

    //菜品编码
    private String vegCode;

    //菜品名称
    private String vegName;

    // 菜品列表
    private String vegDataList;

    @Override
    protected String subExecute() {
        try {
            if ("add".equals(getMethod())) {
                rspCode = add();
            } else if ("delete".equals(getMethod())) {
                rspCode = delete();
            } else if ("update".equals(getMethod())) {
                rspCode = update();
            }
        } catch (Exception e) {
            log("操作员编号：" + operator.getOprId() + "，对机构的维护操作" + getMethod() + "失败，失败原因为：" + e.getMessage());
        }
        return rspCode;
    }

    /**
     * 添加机构信息
     * @throws Exception
     */
    private String add() throws Exception {
        TblVegeCodeInfo vegeCodeInfo = new TblVegeCodeInfo();
        vegeCodeInfo.setVegCode(vegCode);
        vegeCodeInfo.setVegName(vegName);
        // 判断菜品编码是否存在
        if (t30500BO.get(getVegCode()) != null) {
            return ErrorCode.T30500_01;
        }
        // 判断菜品名称是否存在
        List<Object[]> lists = t30500BO.findListByName(vegName);
        if (lists != null && lists.size() > 0) {
            return ErrorCode.T30500_02;
        }
        t30500BO.add(vegeCodeInfo);
        return Constants.SUCCESS_CODE;
    }

    /**
     * 
     * //TODO 删除所选的条目信息
     *
     * @return
     * @throws Exception
     * @author hanyongqing
     */
    @SuppressWarnings("unchecked")
    private String delete() throws Exception {

        String sql2 = " select * from VEGE_CODE_FOR_MCHT t where t.Vege_Code = '" + getVegCode() + "'";
        List<Object[]> dataList2 = commQueryDAO.findBySQLQuery(sql2);

        //是否存在此条记录
        if (dataList2.size() > 0) {
            return ErrorCode.T30500_04;
        }

        String sql = "select VEGE_CODE,VEGE_NAME from vege_code_base where VEGE_CODE = '" + getVegCode() + "' ";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);

        //是否存在此条记录
        if (dataList.size() <= 0) {
            return ErrorCode.T30500_03;
        }
        TblVegeCodeInfo vegeCodeInfo = new TblVegeCodeInfo();
        for (Object[] obj : dataList) {
            vegeCodeInfo.setVegCode(obj[0].toString());
            vegeCodeInfo.setVegName(obj[1].toString());
        }
        //删除机构信息
        t30500BO.delete(vegeCodeInfo);
        //        reloadOprBrhInfo();
        return Constants.SUCCESS_CODE;
    }

    /**
     * 
     * //TODO 更新菜品信息
     *
     * @return
     * @throws Exception
     */
    private String update() throws Exception {
        jsonBean.parseJSONArrayData(getVegDataList());
        int len = jsonBean.getArray().size();
        List<TblVegeCodeInfo> vegeInfoList = new ArrayList<TblVegeCodeInfo>();
        for (int i = 0; i < len; i++) {
            jsonBean.setObject(jsonBean.getJSONDataAt(i));
            TblVegeCodeInfo vegeInfo = new TblVegeCodeInfo();
            BeanUtils.setObjectWithPropertiesValue(vegeInfo, jsonBean, true);
            String sql2 = " select * from VEGE_CODE_FOR_MCHT t where t.Vege_Code = '" + vegeInfo.getVegCode() + "'";
            List<Object[]> dataList2 = commQueryDAO.findBySQLQuery(sql2);

            //是否存在此条记录
            if (dataList2.size() > 0) {
                return "该菜品已经使用，无法修改";
            }
            //判断机构名称是否存在
            List<Object[]> lists = t30500BO.findListByName(vegeInfo.getVegName());
            if (lists != null && lists.size() > 0) {
                return ErrorCode.T30500_02;
            }

            vegeInfoList.add(vegeInfo);
        }
        t30500BO.update(vegeInfoList);
        return Constants.SUCCESS_CODE;
    }

    public String getVegCode() {
        return vegCode;
    }

    public void setVegCode(String vegCode) {
        this.vegCode = vegCode;
    }

    public String getVegName() {
        return vegName;
    }

    public void setVegName(String vegName) {
        this.vegName = vegName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public T30500BO getT30500BO() {
        return t30500BO;
    }

    public void setT30500BO(T30500BO t30500bo) {
        t30500BO = t30500bo;
    }

    public String getVegDataList() {
        return vegDataList;
    }

    public void setVegDataList(String vegDataList) {
        this.vegDataList = vegDataList;
    }
}
