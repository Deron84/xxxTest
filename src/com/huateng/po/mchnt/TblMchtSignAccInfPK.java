package com.huateng.po.mchnt;

import com.huateng.po.mchnt.base.BaseTblHisDiscAlgoPK;

public class TblMchtSignAccInfPK  extends BaseTblHisDiscAlgoPK{
    /**
     */
//   
    private static final long serialVersionUID = 4476624115912477275L;
    private String mchntId;
    private String signAcct;
    
    public TblMchtSignAccInfPK(){}
    public TblMchtSignAccInfPK(String mchntId, String signAcct) {
        this.mchntId = mchntId;
        this.signAcct = signAcct;
    }

    public String getMchntId() {
        return mchntId;
    }

    public void setMchntId(String mchntId) {
        this.mchntId = mchntId;
    }

    public String getSignAcct() {
        return signAcct;
    }

    public void setSignAcct(String signAcct) {
        this.signAcct = signAcct;
    }

}
