package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.TblBrhInfo;

/**
 * Title: 机构信息BO
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-9
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public interface T10101BO {
    /**
     * 查询机构信息
     * @param brhId    机构编号
     * @return
     */
    public TblBrhInfo get(String brhId);

    /**
     * 添加机构信息
     * @param tblBrhInfo    机构信息
     * @return
     */
    public String add(TblBrhInfo tblBrhInfo);

    /**
     * 更新机构信息
     * @param tblBrhInfoList    机构信息列表
     * @return
     */
    public String update(List<TblBrhInfo> tblBrhInfoList);

    /**
     * 删除机构信息
     * @param tblBrhInfo    机构信息
     * @return
     */
    public String delete(TblBrhInfo tblBrhInfo);

    /**
     * 删除机构信息
     * @param brhId    机构编号
     * @return
     */
    public String delete(String brhId);

    public List<Object[]> findListByName(String name);

    public List<Object[]> findListByUpBrhId(String upBrhId);

    public List<Object> getBelowBrh(String upBrhID);
}
