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
import java.util.List;
import java.util.Map;

/**
 * Title:
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
public interface ICommQueryObj {
    /**
     * Execute a query.
     * 
     * @param query
     *            a query expressed in Hibernate's query language
     * @return a distinct list of instances (or arrays of instances)
     */
    public abstract java.util.List find(String query);

    /**
     * 通过 SQL name 查询
     * 
     * @param name
     *            the name of a query defined externally
     * @return Query
     */
    public abstract java.util.List findByNamedQuery(String name);

    public abstract java.util.List findByNamedQuery(final String name, final int begin, final int count);

    /**
     * Obtain an instance of Query for a named query string defined in the
     * mapping file. Use the parameters given.
     * 
     * @param name
     *            the name of a query defined externally
     * @param params
     *            the parameter Map
     * @return Query
     */
    public abstract java.util.List findByNamedQuery(final String name, final Map params);

    public abstract java.util.List findByNamedQuery(final String name, final Map params, final int begin, final int count);

    /**
     * 通过 SQL 查询
     * 
     * @param sql
     *            SQL Statement
     * @param begin
     * @param count
     * @return
     */
    public abstract java.util.List findBySQLQuery(final String sql, final int begin, final int count);

    public abstract void excute(final String sql);

    /**
     * Obtain an instance of Query for a sql string.
     * 
     * @param countSql
     * @return
     */
    public abstract String findCountBySQLQuery(final String countSql);

    /**
     * Obtain an instance of Query for a sql string.
     * @param sql
     * @return
     */
    public abstract List findBySQLQuery(final String sql);

    public abstract List findBySQLQuery(final String sql, final Map map);

    public String findCountBySQLQuery(final String countSql, final Map map);

    public abstract java.util.List findBySQLQuery(final String sql, final int begin, final int count, final Map map);

    /**
     * Obtain an instance of Query for a named query string defined in the
     * mapping file. Use the parameters given.
     * 
     * @param name
     *            the name of a query defined externally
     * @param params
     *            the parameter array
     * @return Query
     */
    public abstract java.util.List findByNamedQuery(final String name, final Serializable[] params);

    public abstract java.util.List findByNamedQuery(final String name, final Serializable[] params, final int begin, final int count);

    public java.util.List findByHQLQuery(final String hql, final int begin, final int count);

    public List findByHQLQuery(final String hql);

}
