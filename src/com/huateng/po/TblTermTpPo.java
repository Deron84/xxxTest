package com.huateng.po;

import java.io.Serializable;

public class TblTermTpPo implements Serializable {
    private static final long serialVersionUID = 1L;

    // 类型编号
    private String termTp;

    // 终端类型
    private String descr;
    
    //终端版号
    private String termNum;
    
    //终端型号
    private String termType;
    
    //产品描述
    private String proDescr;

    public String getTermTp() {
        return termTp;
    }

    public void setTermTp(String termTp) {
        this.termTp = termTp;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

	public String getTermNum() {
		return termNum;
	}

	public void setTermNum(String termNum) {
		this.termNum = termNum;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getProDescr() {
		return proDescr;
	}

	public void setProDescr(String proDescr) {
		this.proDescr = proDescr;
	}
}
