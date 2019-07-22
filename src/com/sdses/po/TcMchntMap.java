package com.sdses.po;

import java.io.Serializable;

public class TcMchntMap implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // 联合主键
    private TcMchntMapPK tcMchntMapPK;
    
    // id
    private String id;
    
    // 聚合标志位
    private String mergeFlag;

    //扫码支付商户号
    private String cMchntNo;
    
    //龙支付扫码商户号
    private String payMchntNo;

    //密钥
    private String cKey;
    
    // 龙支付密钥
    private String payKey;
    
    //密码
    private String pwdId;
    
    //业务代码
    private String bussId;
    
    //柜台代码
    private String bussId2;

    // 启用标识
    private String enableFlag;

    // 有效日期开始日期
    private String startDate;

    // 有效期结束日期
    private String endDate;

    // 备注
    private String resv1;
    
    // 证书名称
    private String certpath;
    
    // 分行代码
    private String branchCode;
    
    // 银联密码
    private String cipher;
   

    public TcMchntMapPK getTcMchntMapPK() {
		return tcMchntMapPK;
	}

	public void setTcMchntMapPK(TcMchntMapPK tcMchntMapPK) {
		this.tcMchntMapPK = tcMchntMapPK;
	}

	public String getcMchntNo() {
        return cMchntNo;
    }

    public void setcMchntNo(String cMchntNo) {
        this.cMchntNo = cMchntNo;
    }

    public String getcKey() {
        return cKey;
    }

    public void setcKey(String cKey) {
        this.cKey = cKey;
    }


	public String getBussId() {
		return bussId;
	}

	public void setBussId(String bussId) {
		this.bussId = bussId;
	}

	public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

	public String getResv1() {
		return resv1;
	}

	public void setResv1(String resv1) {
		this.resv1 = resv1;
	}

	public String getPwdId() {
		return pwdId;
	}

	public void setPwdId(String pwdId) {
		this.pwdId = pwdId;
	}

	public String getCertpath() {
		return certpath;
	}

	public void setCertpath(String certpath) {
		this.certpath = certpath;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getCipher() {
		return cipher;
	}

	public void setCipher(String cipher) {
		this.cipher = cipher;
	}

	public String getBussId2() {
		return bussId2;
	}

	public void setBussId2(String bussId2) {
		this.bussId2 = bussId2;
	}

	public String getPayMchntNo() {
		return payMchntNo;
	}

	public void setPayMchntNo(String payMchntNo) {
		this.payMchntNo = payMchntNo;
	}

	public String getMergeFlag() {
		return mergeFlag;
	}

	public void setMergeFlag(String mergeFlag) {
		this.mergeFlag = mergeFlag;
	}

	public String getPayKey() {
		return payKey;
	}

	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
