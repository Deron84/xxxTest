package com.huateng.dao.impl.mchnt;

import java.util.List;

import com.huateng.dao.iface.mchnt.TblMchtSignAccInfDAO;
import com.huateng.po.mchnt.TblMchtSignAccInf;
import com.huateng.po.mchnt.TblMchtSignAccInfPK;
import com.sdses.dao._RootDAO;

public class TblMchtSignAccInfTmpDAO extends _RootDAO<TblMchtSignAccInf> implements
        TblMchtSignAccInfDAO {
    public TblMchtSignAccInfTmpDAO() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.huateng.dao.iface.TblDivMchntTmpDAO#findAll()
     */
    public List<TblMchtSignAccInf> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    public Class<TblMchtSignAccInf> getReferenceClass() {
        return TblMchtSignAccInf.class;
    }

    /**
     * Cast the object as a TblMchtSignAccInf
     */
    public TblMchtSignAccInf cast(Object object) {
        return (TblMchtSignAccInf) object;
    }

    public TblMchtSignAccInf load(TblMchtSignAccInfPK key) {
        return (TblMchtSignAccInf) load(getReferenceClass(), key);
    }

    public TblMchtSignAccInf get(TblMchtSignAccInfPK key) {
        System.out.println("==="+key.getMchntId()+"=="+key.getSignAcct()+"===");
        return (TblMchtSignAccInf) get(getReferenceClass(), key);
    }

    @SuppressWarnings("unchecked")
    public java.util.List<TblMchtSignAccInf> loadAll() {
        return loadAll(getReferenceClass());
    }

    /**
     * Persist the given transient instance, first assigning a generated identifier. (Or using the current value of the
     * identifier property if the assigned generator is used.)
     * 
     * @param tblDivMchntTmp a transient instance of a persistent class
     * @return the class identifier
     */
    public TblMchtSignAccInfPK save(TblMchtSignAccInf tblDivMchntTmp) {
        return (TblMchtSignAccInfPK) super.save(tblDivMchntTmp);
    }

    /**
     * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
     * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
     * identifier property mapping.
     * 
     * @param tblDivMchntTmp a transient instance containing new or updated state
     */
    public void saveOrUpdate(TblMchtSignAccInf tblDivMchntTmp) {
        super.saveOrUpdate(tblDivMchntTmp);
    }

    /**
     * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
     * instance with the same identifier in the current session.
     * 
     * @param tblDivMchntTmp a transient instance containing updated state
     */
    public void update(TblMchtSignAccInf tblDivMchntTmp) {
        super.update(tblDivMchntTmp);
    }

    /**
     * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
     * Session or a transient instance with an identifier associated with existing persistent state.
     * 
     * @param tblDivMchntTmp the instance to be removed
     */
    public void delete(TblMchtSignAccInf tblDivMchntTmp) {
        super.delete((Object) tblDivMchntTmp);
    }

    /**
     * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
     * Session or a transient instance with an identifier associated with existing persistent state.
     * 
     * @param id the instance ID to be removed
     */
    public void delete(TblMchtSignAccInfPK id) {
        super.delete((Object) load(id));
    }

}
