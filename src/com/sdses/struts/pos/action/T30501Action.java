package com.sdses.struts.pos.action;

import java.util.ArrayList;
import java.util.List;

import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.ContextUtil;
import com.sdses.bo.pos.T30500BO;
import com.sdses.bo.pos.T30501BO;
import com.sdses.po.TblVegeMappingInfo;
import com.sdses.po.TblVegeMappingInfoPK;

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
public class T30501Action extends BaseAction {

    private static final long serialVersionUID = 1L;

    //	
    //	
    // 终端菜品映射表 BO
    private T30501BO t30501BO = (T30501BO) ContextUtil.getBean("T30501BO");

    // 商户号
    private String mchntNo;

    // 终端号
    private String termNo;

    // 按键号
    private String keyCode;

    // 菜品编码
    private String vegeCode;

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
        TblVegeMappingInfo tblVegeMappingInfo = new TblVegeMappingInfo();
        TblVegeMappingInfoPK tblVegeMappingInfoPK = new TblVegeMappingInfoPK();
        tblVegeMappingInfoPK.setMchntNo(mchntNo);
        tblVegeMappingInfoPK.setTermNo(termNo);
        tblVegeMappingInfoPK.setKeyCode(keyCode);
        tblVegeMappingInfo.setTblVegeMappingInfoPK(tblVegeMappingInfoPK);
        tblVegeMappingInfo.setVegeCode(vegeCode);
        // 判断终端菜品映射表是否有此记录
        if (t30501BO.get(tblVegeMappingInfoPK) != null) {
            return ErrorCode.T30501_01;
        }
        t30501BO.add(tblVegeMappingInfo);
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

        TblVegeMappingInfoPK tblVegeMappingInfoPK = new TblVegeMappingInfoPK();
        tblVegeMappingInfoPK.setMchntNo(mchntNo);
        tblVegeMappingInfoPK.setTermNo(termNo);
        tblVegeMappingInfoPK.setKeyCode(keyCode);

        TblVegeMappingInfo tblVegeMappingInfo = t30501BO.get(tblVegeMappingInfoPK);

        //是否存在此条记录
        if (tblVegeMappingInfo == null) {
            return ErrorCode.T30501_02;
        }
        //删除机构信息
        t30501BO.delete(tblVegeMappingInfo);
        //      reloadOprBrhInfo();
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
        List<TblVegeMappingInfo> vegeInfoList = new ArrayList<TblVegeMappingInfo>();
        for (int i = 0; i < len; i++) {
            jsonBean.setObject(jsonBean.getJSONDataAt(i));
            TblVegeMappingInfo tblVegeMappingInfo = new TblVegeMappingInfo();
            TblVegeMappingInfoPK tblVegeMappingInfoPK = new TblVegeMappingInfoPK();
            BeanUtils.setObjectWithPropertiesValue(tblVegeMappingInfo, jsonBean, false);
            BeanUtils.setObjectWithPropertiesValue(tblVegeMappingInfoPK, jsonBean, false);
            tblVegeMappingInfo.setTblVegeMappingInfoPK(tblVegeMappingInfoPK);
            //菜品编码表BO
            T30500BO t30500BO = (T30500BO) ContextUtil.getBean("T30500BO");
            if (t30500BO.get(tblVegeMappingInfo.getVegeCode()) == null) {
                return "菜品编码：" + tblVegeMappingInfo.getVegeCode() + "不存在";
            }
            //判断机构名称是否存在
            TblVegeMappingInfo vegeMappingInfo = t30501BO.get(tblVegeMappingInfoPK);
            if (vegeMappingInfo == null) {
                return ErrorCode.T30501_02;
            }
            vegeInfoList.add(tblVegeMappingInfo);
        }
        t30501BO.update(vegeInfoList);
        return Constants.SUCCESS_CODE;
    }

    public T30501BO getT30501BO() {
        return t30501BO;
    }

    public void setT30501BO(T30501BO t30501bo) {
        t30501BO = t30501bo;
    }

    public String getMchntNo() {
        return mchntNo;
    }

    public void setMchntNo(String mchntNo) {
        this.mchntNo = mchntNo;
    }

    public String getTermNo() {
        return termNo;
    }

    public void setTermNo(String termNo) {
        this.termNo = termNo;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public String getVegeCode() {
        return vegeCode;
    }

    public void setVegeCode(String vegeCode) {
        this.vegeCode = vegeCode;
    }

    public String getVegDataList() {
        return vegDataList;
    }

    public void setVegDataList(String vegDataList) {
        this.vegDataList = vegDataList;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
