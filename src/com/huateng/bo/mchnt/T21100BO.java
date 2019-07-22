/*
 * Copyright (C), 2012-2013, 上海华腾软件系统有限公司
 * FileName: T21100BO.java
 * Author:   Feihong247
 * Date:     2013-10-16 上午9:30:49
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblInCardManagent;
import com.huateng.po.mchnt.TblInCardManagentPK;

/**
 * 转入卡接口<br> 
 *  
 * @author Feihong247
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface T21100BO {
    public String addInCardMcht(TblInCardManagent tblInCardManagent);
    
    public TblInCardManagent get(TblInCardManagentPK id);
    
    public void delete(TblInCardManagent id);
    
    public void update(TblInCardManagent tblInCardManagent);
    
//    public List findAll(String hql);
}
