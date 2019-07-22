/*
 * Copyright (C), 2012-2013, 上海华腾软件系统有限公司
 * FileName: TblInCardManagentDAO.java
 * Author:   Feihong247
 * Date:     2013-10-16 上午10:09:58
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.dao.impl.mchnt;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;

import com.huateng.dao.iface.mchnt.ITblInCardManagentDAO;
import com.huateng.po.mchnt.TblInCardManagent;
import com.huateng.po.mchnt.TblInCardManagentPK;
import com.sdses.dao._RootDAO;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 * 〈方法简述 - 方法描述〉
 *  
 * @author Feihong247
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TblInCardManagentDAO extends _RootDAO<com.huateng.po.mchnt.TblInCardManagent> implements ITblInCardManagentDAO{

    /* (non-Javadoc)
     * @see com.huateng.dao._RootDAO#getReferenceClass()
     */
    @Override
    public Class getReferenceClass() {
        // TODO Auto-generated method stub
        return com.huateng.po.mchnt.TblInCardManagent.class;
    }

    /* (non-Javadoc)
     * @see com.huateng.dao.iface.mchnt.ITblInCardManagentDAO#cast(java.lang.Object)
     */
    public TblInCardManagent cast(Object object) {
        // TODO Auto-generated method stub
        return (com.huateng.po.mchnt.TblInCardManagent) object;
    }

    /* (non-Javadoc)
     * @see com.huateng.dao.iface.mchnt.ITblInCardManagentDAO#load(java.lang.String)
     */
    public TblInCardManagent load(TblInCardManagentPK key) throws HibernateException {
        // TODO Auto-generated method stub
        return (com.huateng.po.mchnt.TblInCardManagent) load(getReferenceClass(), key);
    }

    /* (non-Javadoc)
     * @see com.huateng.dao.iface.mchnt.ITblInCardManagentDAO#get(java.lang.String)
     */
    public TblInCardManagent get(TblInCardManagentPK key) throws HibernateException {
        // TODO Auto-generated method stub
        return (com.huateng.po.mchnt.TblInCardManagent) get(getReferenceClass(), key);
    }

    /* (non-Javadoc)
     * @see com.huateng.dao.iface.mchnt.ITblInCardManagentDAO#save(com.huateng.po.mchnt.TblInCardManagent)
     */
    public void save(TblInCardManagent tblInCardManagent) throws HibernateException {
        // TODO Auto-generated method stub
        super.save(tblInCardManagent);
    }

    /* (non-Javadoc)
     * @see com.huateng.dao.iface.mchnt.ITblInCardManagentDAO#saveOrUpdate(com.huateng.po.mchnt.TblInCardManagent)
     */
    public void saveOrUpdate(TblInCardManagent tblInCardManagent) throws HibernateException {
        // TODO Auto-generated method stub
        super.saveOrUpdate(tblInCardManagent);
    }

    /* (non-Javadoc)
     * @see com.huateng.dao.iface.mchnt.ITblInCardManagentDAO#update(com.huateng.po.mchnt.TblInCardManagent)
     */
    public void update(TblInCardManagent tblInCardManagent) throws HibernateException {
        // TODO Auto-generated method stub
        super.update(tblInCardManagent);
    }

    /* (non-Javadoc)
     * @see com.huateng.dao.iface.mchnt.ITblInCardManagentDAO#delete(com.huateng.po.mchnt.TblInCardManagent)
     */
    public void delete(TblInCardManagent tblInCardManagent) throws HibernateException {
        // TODO Auto-generated method stub
        super.delete(tblInCardManagent);
    }

    /* (non-Javadoc)
     * @see com.huateng.dao.iface.mchnt.ITblInCardManagentDAO#delete(java.lang.String)
     */
    public void delete(TblInCardManagentPK id) throws HibernateException {
        // TODO Auto-generated method stub
        super.delete((Object) load(id));
    }

    /* (non-Javadoc)
     * @see com.huateng.dao.iface.mchnt.ITblInCardManagentDAO#saveBatch(java.util.List)
     */
    public void saveBatch(List<TblInCardManagent> list) {
        // TODO Auto-generated method stub
        Iterator<TblInCardManagent> it = list.iterator();
        while(it.hasNext())
        {
            this.getHibernateTemplate().saveOrUpdate(it.next());
        }
    }

    /* (non-Javadoc)
     * @see com.huateng.dao.iface.mchnt.ITblInCardManagentDAO#findAll(java.lang.String)
     */
//    public List findAll(String hql) {
//        // TODO Auto-generated method stub
//        return this.getHibernateTemplate().find(hql);
//    }

}
