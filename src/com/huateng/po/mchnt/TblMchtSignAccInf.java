package com.huateng.po.mchnt;

import java.io.Serializable;
/**
 * 
 * 商户签约账户信息表<br> 
 * 〈功能详细描述〉
 * 〈方法简述 - 方法描述〉
 *  
 * @author Administrator
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TblMchtSignAccInf implements Serializable {
    /**
     */
    private static final long serialVersionUID = 5576000655757885058L;
    //主键
    private TblMchtSignAccInfPK id;
    // 签约类型 0商户收款帐户 1付款转入帐户
    private String signType;
    // 签约状态 0正常 1未启用
    private String signStatus;
    // 签约卡所属行行号
    private String rcvBankId;
    // 签约卡所属行名称
    private String rcvBankNo;
    // 创建记录操作员
    private String crtOprId;
    // 创建记录操作员
    private String updOprId;
    // 记录创建时间
    private String recCrtTs;
    // 记录修改时间S
    private String recUpdTs;
    // 记录操作员行号
    private String bankNo;
    // 记录操作员行名称
    private String bankName;
    // 记录签约账户名称
    private String acctName;
    // 记录操作员银行地址
    private String bankAddr;
    public String getCrtOprId() {
        return crtOprId;
    }
    public void setCrtOprId(String crtOprId) {
        this.crtOprId = crtOprId;
    }
    public String getRcvBankId() {
        return rcvBankId;
    }
    public void setRcvBankId(String rcvBankId) {
        this.rcvBankId = rcvBankId;
    }
    public String getRcvBankNo() {
        return rcvBankNo;
    }
    public void setRcvBankNo(String rcvBankNo) {
        this.rcvBankNo = rcvBankNo;
    }
    public String getRecCrtTs() {
        return recCrtTs;
    }
    public void setRecCrtTs(String recCrtTs) {
        this.recCrtTs = recCrtTs;
    }
    public String getRecUpdTs() {
        return recUpdTs;
    }
    public void setRecUpdTs(String recUpdTs) {
        this.recUpdTs = recUpdTs;
    }
    public String getSignStatus() {
        return signStatus;
    }
    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }
    public String getSignType() {
        return signType;
    }
    public void setSignType(String signType) {
        this.signType = signType;
    }

    public TblMchtSignAccInfPK getId() {
        return id;
    }
    public void setId(TblMchtSignAccInfPK id) {
        this.id = id;
    }
    public String getUpdOprId() {
        return updOprId;
    }
    public void setUpdOprId(String updOprId) {
        this.updOprId = updOprId;
    }
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getBankAddr() {
		return bankAddr;
	}
	public void setBankAddr(String bankAddr) {
		this.bankAddr = bankAddr;
	}
	

}
