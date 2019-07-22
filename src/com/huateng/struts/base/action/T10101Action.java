package com.huateng.struts.base.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.huateng.bo.base.T10101BO;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.po.TblBrhInfo;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

import net.sf.json.JSONObject;

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
public class T10101Action extends BaseAction {

    private static final long serialVersionUID = 1L;

    //机构编号
    private String brhId;

    //机构级别
    private String brhLvl;

    //上级机构编号
    private String upBrhId;

    //机构名称
    private String brhName;

    //机构地址
    private String brhAddr;

    //机构联系电话
    private String brhTelNo;

    //机构邮编
    private String postCode;

    //机构联系人
    private String brhContName;

    //机构银联编号
    private String cupBrhId;

    //机构修改列表
    private String brhDataList;

    private String resv1;

    //机构信息BO
    private T10101BO t10101BO = (T10101BO) ContextUtil.getBean("T10101BO");

    /* (non-Javadoc)
     * @see com.huateng.struts.system.action.BaseAction#subExecute()
     */
    @Override
    protected String subExecute() {
        try {
            if ("add".equals(getMethod())) {
                rspCode = add();
            } else if ("delete".equals(getMethod())) {
                rspCode = delete();
            } else if ("update".equals(getMethod())) {
                rspCode = update();
            } else if("getBrhLevel".equals(getMethod())){
            	rspCode = getBrhLevel();
            }
        } catch (Exception e) {
            log("操作员编号：" + operator.getOprId() + "，对机构的维护操作" + getMethod() + "失败，失败原因为：" + e.getMessage());
        }
        return rspCode;
    }
    
    private String getBrhLevel(){
    	try {
    		Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("brhLevel", operator.getOprBrhLvl());
            String str = JSONObject.fromObject(midMap).toString();
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 添加机构信息
     * @throws Exception
     */
    private String add() throws Exception {
        TblBrhInfo tblBrhInfo = new TblBrhInfo();
        //判断机构是否存在
        if (t10101BO.get(getBrhId()) != null) {
            return ErrorCode.T10101_01;
        }
        //判断机构名称是否存在
        List<Object[]> lists = t10101BO.findListByName(brhName);
        if (lists != null && lists.size() > 0) {
            return ErrorCode.T10101_05;
        }

        //机构编号
        tblBrhInfo.setId(getBrhId());
        //银联机构编号
        tblBrhInfo.setCupBrhId(getCupBrhId());
        //机构状态，暂不启用
        tblBrhInfo.setBrhSta("0");
        tblBrhInfo.setResv1(resv1);
        //上级机构编号
        tblBrhInfo.setUpBrhId(getUpBrhId());
        //机构注册时间
        tblBrhInfo.setRegDt(CommonFunction.getCurrentDate());
        //机构所在地邮政编号
        tblBrhInfo.setPostCd(getPostCode());
        //机构所在地址
        tblBrhInfo.setBrhAddr(getBrhAddr());
        //机构名称
        tblBrhInfo.setBrhName(getBrhName());
        //机构联系电话
        tblBrhInfo.setBrhTelNo(getBrhTelNo());
        //机构联系人
        tblBrhInfo.setBrhContName(getBrhContName());
        //最后更新操作员
        tblBrhInfo.setLastUpdOprId(operator.getOprId());
        //最后更新时间
        tblBrhInfo.setLastUpdTs(CommonFunction.getCurrentDateTime());
        //最后更新交易码
        tblBrhInfo.setLastUpdTxnId(getTxnId());
        
        //获取上级机构信息
        TblBrhInfo tblBrhInfo1 = t10101BO.get(tblBrhInfo.getUpBrhId());
        int brhLevel = Integer.parseInt(tblBrhInfo1.getBrhLevel());
        //机构级别
        tblBrhInfo.setBrhLevel(brhLevel + 1 + "");
        tblBrhInfo.setLevelOne(tblBrhInfo1.getLevelOne());
        tblBrhInfo.setLevelTwo(tblBrhInfo1.getLevelTwo());
        tblBrhInfo.setLevelThr(tblBrhInfo1.getLevelThr());
        tblBrhInfo.setLevelFou(tblBrhInfo1.getLevelFou());
        switch(brhLevel){
            case 0 :
                tblBrhInfo.setLevelOne(tblBrhInfo.getUpBrhId());
                tblBrhInfo.setLevelTwo(tblBrhInfo.getId());
                break;
            case 1 :
                tblBrhInfo.setLevelTwo(tblBrhInfo.getUpBrhId());
                tblBrhInfo.setLevelThr(tblBrhInfo.getId());
                break;
            case 2 :
                tblBrhInfo.setLevelThr(tblBrhInfo.getUpBrhId());
                tblBrhInfo.setLevelFou(tblBrhInfo.getId());
                break;
        }
        t10101BO.add(tblBrhInfo);

        log("添加机构信息成功。操作员编号：" + operator.getOprId() + "，机构编号：" + getBrhId());
        //重新加载机构信息
        reloadOprBrhInfo();

        return Constants.SUCCESS_CODE;
    }

    /**
     * 删除机构信息
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private String delete() throws Exception {

        String sql = "select * from tbl_opr_info where brh_id = '" + getBrhId() + "' ";
        List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
        //是否有子机构
        if (StringUtils.isNotBlank(getBrhId())) {
            List<Object[]> lists = t10101BO.findListByUpBrhId(getBrhId());

            if (lists != null && lists.size() > 0) {
                return ErrorCode.T10101_04;
            }

        }
        //判断是否有操作员在此机构下
        if (dataList.size() > 0) {
            return ErrorCode.T10101_02;
        }
//        //分行行号    签约网点
//        sql = "select * from TBL_MCHT_BASE_INF_TMP where bank_no = '" + getBrhId() + "' or agr_br='" + getBrhId() + "'";
//        dataList = commQueryDAO.findBySQLQuery(sql);
//        //判断是否有商户在此机构下
//        if (dataList.size() > 0) {
//            return ErrorCode.T10101_03;
//        }
        //删除机构信息
        t10101BO.delete(getBrhId());
        log("删除机构信息成功。操作员编号：" + operator.getOprId() + "，机构编号：" + getBrhId());
        //加载操作员所在机构信息
        reloadOprBrhInfo();
        return Constants.SUCCESS_CODE;
    }

    public T10101BO getT10101BO() {
        return t10101BO;
    }

    public void setT10101BO(T10101BO t10101bo) {
        t10101BO = t10101bo;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * 
     * //TODO 添加方法功能描述
     *
     * @return
     */
    public String getData() {
        try {
            TblBrhInfo tblBrhInfo = t10101BO.get(getBrhId());
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", tblBrhInfo);
            String str = JSONObject.fromObject(midMap).toString();
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 更新机构信息
     * @return
     */
    private String update() throws Exception {
        List<TblBrhInfo> brhInfoList = new ArrayList<TblBrhInfo>();

        TblBrhInfo tblBrhInfo2 = t10101BO.get(getBrhId());
        //判断机构是否存在
        if (tblBrhInfo2 == null) {
            return "此条记录不存在";
        }
        try {
            List<Object> belowBrhList = t10101BO.getBelowBrh(getUpBrhId());
            for (Object obj : belowBrhList) {
                if (obj.equals(getBrhId())) {
                    return "机构信息级联出错";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "机构信息级联出错";
        }
        TblBrhInfo tblBrhInfo = new TblBrhInfo();
        //机构编号
        tblBrhInfo.setId(getBrhId());
        //银联机构编号
        tblBrhInfo.setCupBrhId(getCupBrhId());
        //机构状态，暂不启用
        tblBrhInfo.setBrhSta("0");
        tblBrhInfo.setResv1(resv1);
        //上级机构编号
        tblBrhInfo.setUpBrhId(getUpBrhId());
        //机构注册时间
        tblBrhInfo.setRegDt(tblBrhInfo2.getRegDt());
        //机构所在地邮政编号
        tblBrhInfo.setPostCd(getPostCode());
        //机构所在地址
        tblBrhInfo.setBrhAddr(getBrhAddr());
        //机构名称
        tblBrhInfo.setBrhName(getBrhName());
        //机构联系电话
        tblBrhInfo.setBrhTelNo(getBrhTelNo());
        //机构联系人
        tblBrhInfo.setBrhContName(getBrhContName());
        //最后更新操作员
        tblBrhInfo.setLastUpdOprId(operator.getOprId());
        //最后更新时间
        tblBrhInfo.setLastUpdTs(CommonFunction.getCurrentDateTime());
        //最后更新交易码
        tblBrhInfo.setLastUpdTxnId(getTxnId());

//        System.out.println(tblBrhInfo.getUpBrhId()+">>>>>>>>>>>>>>>>>>>>>");
        int brhLevel = 0;
        if(!"-".equals(tblBrhInfo.getUpBrhId())){
        //获取上级机构信息
	        TblBrhInfo tblBrhInfo1 = t10101BO.get(tblBrhInfo.getUpBrhId());
	        brhLevel = Integer.parseInt(tblBrhInfo1.getBrhLevel());
	        //机构级别
	        tblBrhInfo.setBrhLevel(brhLevel + 1 + "");
	        tblBrhInfo.setLevelOne(tblBrhInfo1.getLevelOne());
	        tblBrhInfo.setLevelTwo(tblBrhInfo1.getLevelTwo());
	        tblBrhInfo.setLevelThr(tblBrhInfo1.getLevelThr());
	        tblBrhInfo.setLevelFou(tblBrhInfo1.getLevelFou());
        }else{
        	tblBrhInfo.setBrhLevel(brhLevel + "");
        }
        switch(brhLevel){
            case 0 :
                tblBrhInfo.setLevelOne(tblBrhInfo.getUpBrhId());
                tblBrhInfo.setLevelTwo(tblBrhInfo.getId());
                break;
            case 1 :
                tblBrhInfo.setLevelTwo(tblBrhInfo.getUpBrhId());
                tblBrhInfo.setLevelThr(tblBrhInfo.getId());
                break;
            case 2 :
                tblBrhInfo.setLevelThr(tblBrhInfo.getUpBrhId());
                tblBrhInfo.setLevelFou(tblBrhInfo.getId());
                break;
        }

        List<Object[]> lists = t10101BO.findListByName(getBrhName());
        if (lists != null && lists.size() > 0) {
        	if (!getBrhId().equals(lists.get(0)[0])) {
        		return ErrorCode.T10101_05;
			}
        }
        brhInfoList.add(tblBrhInfo);

        t10101BO.update(brhInfoList);

        log("更新机构信息成功。操作员编号：" + operator.getOprId());
        //加载操作员所在机构信息
        reloadOprBrhInfo();

        return Constants.SUCCESS_CODE;
    }

    /**
     * 重新加载操作员相关的机构信息
     */
    private void reloadOprBrhInfo() {
        Map<String, String> brhMap = new LinkedHashMap<String, String>();
        brhMap.put(operator.getOprBrhId(), operator.getOprBrhName());
        operator.setBrhBelowMap(CommonFunction.getBelowBrhMap(brhMap));
        operator.setBrhBelowId(CommonFunction.getBelowBrhInfo(operator.getBrhBelowMap()));
    }

    public String getBrhId() {
        return brhId;
    }

    public void setBrhId(String brhId) {
        this.brhId = brhId;
    }

    public String getBrhLvl() {
        return brhLvl;
    }

    public void setBrhLvl(String brhLvl) {
        this.brhLvl = brhLvl;
    }

    public String getUpBrhId() {
        return upBrhId;
    }

    public void setUpBrhId(String upBrhId) {
        this.upBrhId = upBrhId;
    }

    public String getBrhName() {
        return brhName;
    }

    public void setBrhName(String brhName) {
        this.brhName = brhName;
    }

    public String getBrhAddr() {
        return brhAddr;
    }

    public void setBrhAddr(String brhAddr) {
        this.brhAddr = brhAddr;
    }

    public String getBrhTelNo() {
        return brhTelNo;
    }

    public void setBrhTelNo(String brhTelNo) {
        this.brhTelNo = brhTelNo;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getBrhContName() {
        return brhContName;
    }

    public void setBrhContName(String brhContName) {
        this.brhContName = brhContName;
    }

    public String getCupBrhId() {
        return cupBrhId;
    }

    public void setCupBrhId(String cupBrhId) {
        this.cupBrhId = cupBrhId;
    }

    public String getBrhDataList() {
        return brhDataList;
    }

    public void setBrhDataList(String brhDataList) {
        this.brhDataList = brhDataList;
    }

    public String getResv1() {
        return resv1;
    }

    public void setResv1(String resv1) {
        this.resv1 = resv1;
    }

}
