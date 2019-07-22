/*
 * Copyright (C), 2012-2013, 上海华腾软件系统有限公司
 * FileName: T21100BOTarget.java
 * Author:   Feihong247
 * Date:     2013-10-16 上午9:33:27
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.bo.impl.mchnt;

import java.util.List;

import com.huateng.bo.mchnt.T21100BO;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.dao.iface.mchnt.ITblInCardManagentDAO;
import com.huateng.dao.impl.mchnt.TblInCardManagentDAO;
import com.huateng.po.mchnt.TblInCardManagent;
import com.huateng.po.mchnt.TblInCardManagentPK;

/**
 * 转入卡实现<br> 
 *  
 * @author Feihong247
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class T21100BOTarget implements T21100BO {

    
    private ITblInCardManagentDAO tblInCardManagentDAO = new TblInCardManagentDAO();
    
    /**
     * @return the tblInCardManagentDAO
     */
    public ITblInCardManagentDAO getTblInCardManagentDAO() {
        return tblInCardManagentDAO;
    }

    /**
     * @param tblInCardManagentDAO the tblInCardManagentDAO to set
     */
    public void setTblInCardManagentDAO(ITblInCardManagentDAO tblInCardManagentDAO) {
        this.tblInCardManagentDAO = tblInCardManagentDAO;
    }

    /* (non-Javadoc)
     * @see com.huateng.bo.mchnt.T21100BO#addInCardMcht(java.lang.String[], java.lang.String[])
     */
    public String addInCardMcht(TblInCardManagent tblInCardManagent) {
        // TODO Auto-generated method stub
        tblInCardManagentDAO.save(tblInCardManagent);
        return Constants.SUCCESS_CODE;
    }

    /* (non-Javadoc)
     * @see com.huateng.bo.mchnt.T21100BO#get(com.huateng.po.mchnt.TblInCardManagentPK)
     */
    public TblInCardManagent get(TblInCardManagentPK id) {
        // TODO Auto-generated method stub
        return tblInCardManagentDAO.get(id);
    }

    /* (non-Javadoc)
     * @see com.huateng.bo.mchnt.T21100BO#delete(com.huateng.po.mchnt.TblInCardManagentPK)
     */
    public void delete(TblInCardManagent tblInCardManagent) {
        // TODO Auto-generated method stub
        tblInCardManagentDAO.delete(tblInCardManagent);
    }

    /* (non-Javadoc)
     * @see com.huateng.bo.mchnt.T21100BO#update(com.huateng.po.mchnt.TblInCardManagent)
     */
    public void update(TblInCardManagent tblInCardManagent) {
        // TODO Auto-generated method stub
        tblInCardManagentDAO.saveOrUpdate(tblInCardManagent);
    }

    /* (non-Javadoc)
     * @see com.huateng.bo.mchnt.T21100BO#findAll(java.lang.String)
     */
//    public List findAll(String hql) {
//        // TODO Auto-generated method stub
//        return tblInCardManagentDAO.findAll(hql);
//    }

}
