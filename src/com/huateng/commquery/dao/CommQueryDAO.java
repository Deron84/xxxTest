/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-3-8       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.commquery.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Title:通用查询
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-8
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class CommQueryDAO extends HibernateDaoSupport implements ICommQueryDAO {

    @Override
    public Session getCurrentSession() {
        return this.getSessionFactory().getCurrentSession();
    }

    @Override
    public void excute(final String sql) {
        getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.getTransaction().begin();
                Query query = session.createSQLQuery(sql);
                int executeColum = query.executeUpdate();
                session.getTransaction().commit();
                return executeColum;
            }
        });
    }

    @Override
    public List find(String query) {
        return getHibernateTemplate().find(query);
    }

    @Override
    public List findByHQLQuery(final String hql, final int begin, final int count) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                if (begin >= 0) {
                    query.setFirstResult(begin);
                    query.setMaxResults(count);
                }
                return query.list();
            }
        });
    }

    @Override
    public List findByHQLQuery(final String hql) {
        List data = getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                return query.list();
            }
        });
        return data;
    }

    @Override
    public List findByNamedQuery(String name) {
        return getHibernateTemplate().findByNamedQuery(name);
    }

    @Override
    public List findByNamedQuery(final String name, final int begin, final int count) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.getNamedQuery(name);
                if (begin >= 0) {
                    query.setFirstResult(begin);
                    query.setMaxResults(count);
                }
                return query.list();
            }
        });
    }

    @Override
    public List findByNamedQuery(String name, Map params) {
        return findByNamedQuery(name, params, -1, -1);
    }

    @Override
    public List findByNamedQuery(final String name, final Map params, final int begin, final int count) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.getNamedQuery(name);
                if (null != params) {
                    for (Iterator i = params.entrySet().iterator(); i.hasNext();) {
                        Map.Entry entry = (Map.Entry) i.next();
                        query.setParameter((String) entry.getKey(), entry.getValue());
                    }
                }
                if (begin >= 0) {
                    query.setFirstResult(begin);
                    query.setMaxResults(count);
                }
                return query.list();
            }
        });
    }

    @Override
    public List findByNamedQuery(String name, Serializable[] params) {
        return findByNamedQuery(name, params, -1, -1);
    }

    @Override
    public List findByNamedQuery(final String name, final Serializable[] params, final int begin, final int count) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.getNamedQuery(name);
                if (null != params) {
                    for (int i = 0; i < params.length; i++) {
                        query.setParameter(i, params[i]);
                    }
                }
                if (begin >= 0) {
                    query.setFirstResult(begin);
                    query.setMaxResults(count);
                }
                return query.list();
            }
        });
    }

    @Override
    public List findBySQLQuery(final String sql, final int begin, final int count) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql);
                if (begin >= 0) {
                    query.setFirstResult(begin);
                    query.setMaxResults(count);
                }
                return query.list();
            }
        });
    }

    @Override
    public List findBySQLQuery(final String sql) {
        List data = getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql);
                return query.list();
            }
        });
        return data;
    }

    @Override
    public List findBySQLQuery(final String sql, final Map map) {
        List data = getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql);
                //          session.close();
                Iterator iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    String key = iter.next().toString();
                    Object obj = map.get(key);
                    String[] keys = query.getNamedParameters();
                    for (int i = 0; i < keys.length; i++) {
                        if (key != null && key.equals(keys[i])) {
                            if (obj instanceof String) {
                                query.setString(key, obj.toString());
                            }
                            if (obj instanceof Number) {
                                query.setInteger(key, Integer.parseInt(obj.toString()));
                            }
                            if (obj instanceof BigDecimal) {
                                query.setBigDecimal(key, (BigDecimal) obj);
                            }
                            if (obj instanceof List) {
                                query.setParameterList(key, (List) obj);
                            }
                        }
                    }
                }
                return query.list();
            }
        });
        return data;
    }

    @Override
    public List findBySQLQuery(final String sql, final int begin, final int count, final Map map) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql);
                Iterator iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    String key = iter.next().toString();
                    Object obj = map.get(key);
                    String[] keys = query.getNamedParameters();
                    for (int i = 0; i < keys.length; i++) {
                        if (key != null && key.equals(keys[i])) {
                            if (obj instanceof String) {
                                query.setString(key, obj.toString());
                            }
                            if (obj instanceof Number) {
                                query.setInteger(key, Integer.parseInt(obj.toString()));
                            }
                            if (obj instanceof BigDecimal) {
                                query.setBigDecimal(key, (BigDecimal) obj);
                            }
                            if (obj instanceof List) {
                                query.setParameterList(key, (List) obj);
                            }
                        }
                    }
                }
                if (begin >= 0) {
                    query.setFirstResult(begin);
                    query.setMaxResults(count);
                }
                return query.list();
            }
        });
    }

    @Override
    public String findCountBySQLQuery(final String countSql) {
        List data = getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(countSql);
                return query.list();
            }
        });
        return data.get(0).toString();
    }

    @Override
    public String findCountBySQLQuery(final String countSql, final Map map) {
        List data = getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(countSql);
                Iterator iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    String key = iter.next().toString();
                    Object obj = map.get(key);
                    String[] keys = query.getNamedParameters();
                    for (int i = 0; i < keys.length; i++) {
                        if (key != null && key.equals(keys[i])) {
                            if (obj instanceof String) {
                                query.setString(key, obj.toString());
                            }
                            if (obj instanceof Number) {
                                query.setInteger(key, Integer.parseInt(obj.toString()));
                            }
                            if (obj instanceof BigDecimal) {
                                query.setBigDecimal(key, (BigDecimal) obj);
                            }
                            if (obj instanceof List) {
                                query.setParameterList(key, (List) obj);
                            }
                        }
                    }
                }
                return query.list();
            }
        });
        return data.get(0).toString();
    }

    @Override
    public void flush() {
        getHibernateTemplate().flush();
    }
}
