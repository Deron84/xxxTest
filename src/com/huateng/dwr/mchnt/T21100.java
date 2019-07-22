/*
 * Copyright (C), 2012-2013, 上海华腾软件系统有限公司
 * FileName: T21100.java
 * Author:   Feihong247
 * Date:     2013-10-18 上午11:26:31
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.dwr.mchnt;

import com.huateng.bo.mchnt.T21100BO;
import com.huateng.po.mchnt.TblInCardManagent;
import com.huateng.po.mchnt.TblInCardManagentPK;
import com.huateng.system.util.ContextUtil;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 * 〈方法简述 - 方法描述〉
 *  
 * @author Feihong247
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class T21100 {
    
    private T21100BO t21100BO = (T21100BO) ContextUtil.getBean("T21100BOTarget");
    
    public TblInCardManagent getTblInCardManagent(String mchntCd,String inCardNum,String inOutFlag){
        TblInCardManagent tblInCardManagent = null ;
        TblInCardManagentPK tblInCardManagentPK = new TblInCardManagentPK(mchntCd,inCardNum,inOutFlag);
        try{
            tblInCardManagent = t21100BO.get(tblInCardManagentPK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return tblInCardManagent;
    }
}
