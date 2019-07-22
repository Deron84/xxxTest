/*
 * Copyright (C), 2012-2013, 上海华腾软件系统有限公司
 * FileName: ITblInCardManagentDAO.java
 * Author:   Feihong247
 * Date:     2013-10-16 上午10:06:04
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.dao.iface.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblInCardManagentPK;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 * 〈方法简述 - 方法描述〉
 *  
 * @author Feihong247
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ITblInCardManagentDAO {
    public Class<com.huateng.po.mchnt.TblInCardManagent> getReferenceClass () ;
    
    public com.huateng.po.mchnt.TblInCardManagent cast (Object object) ;

    public com.huateng.po.mchnt.TblInCardManagent get(TblInCardManagentPK key)
            throws org.hibernate.HibernateException ;
    /**
     * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
     * of the identifier property if the assigned generator is used.)
     * @param TblInCardManagent a transient instance of a persistent class
     * @return the class identifier
     */
    public void save(com.huateng.po.mchnt.TblInCardManagent tblInCardManagent)
        throws org.hibernate.HibernateException;

    /**
     * Either save() or update() the given instance, depending upon the value of its identifier property. By default
     * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
     * identifier property mapping.
     * @param TblInCardManagent a transient instance containing new or updated state
     */
    public void saveOrUpdate(com.huateng.po.mchnt.TblInCardManagent tblInCardManagent)
        throws org.hibernate.HibernateException ;


    /**
     * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
     * instance with the same identifier in the current session.
     * @param TblInCardManagent a transient instance containing updated state
     */
    public void update(com.huateng.po.mchnt.TblInCardManagent tblInCardManagent)
        throws org.hibernate.HibernateException ;

    /**
     * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
     * Session or a transient instance with an identifier associated with existing persistent state.
     * @param TblInCardManagent the instance to be removed
     */
    public void delete(com.huateng.po.mchnt.TblInCardManagent tblInCardManagent)
        throws org.hibernate.HibernateException ;

    /**
     * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
     * Session or a transient instance with an identifier associated with existing persistent state.
     * @param id the instance ID to be removed
     */
    public void delete(TblInCardManagentPK key)
        throws org.hibernate.HibernateException ;
    
    public void saveBatch(List<com.huateng.po.mchnt.TblInCardManagent> list);
    
//    public List findAll(String hql);
}