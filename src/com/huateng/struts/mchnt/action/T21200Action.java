package com.huateng.struts.mchnt.action;

import com.huateng.bo.mchnt.T21200BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.mchnt.TblInfMchntTp;
import com.huateng.po.mchnt.TblInfMchntTpPK;
import com.huateng.po.mchnt.TblMchtSignAccInf;
import com.huateng.po.mchnt.TblMchtSignAccInfPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.groovy.tools.shell.commands.SetCommand;

/**
 * 
 * 商户签约账户维护<br>
 * 〈功能详细描述〉 〈方法简述 - 方法描述〉
 * 
 * @author Administrator
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class T21200Action extends BaseAction {
    
    private static Logger logger = Logger.getLogger(T21200Action.class);
    /**
     */
    private static final long serialVersionUID = 1429482710278393736L;
    T21200BO T21200BO = (T21200BO) ContextUtil.getBean("T21200BO");
    private String mchntId;
    private String signAcct;
    private String rcvBankId;
    private String rcvBankNo;
    private String signStatus;
    private String signType;
    private String acctName;
    // 商户签约账户集合
    private String TblMchtSignAccInfList;

    protected String subExecute() throws Exception {

        // add method
        if ("add".equals(method)) {
            rspCode = add();
        } else if ("delete".equals(method)) {
            rspCode = delete();
        } else if ("update".equals(method)) {
            rspCode = update();
        }
        return rspCode;
    }

    public String add() {
        if (!StringUtil.isNull(mchntId) && !StringUtil.isNull(signAcct)) {
            TblMchtSignAccInf bean = new TblMchtSignAccInf();
            TblMchtSignAccInfPK tblMchtSignAccInfPK = new TblMchtSignAccInfPK(mchntId, signAcct);
            int countNum=T21200BO.countNum(tblMchtSignAccInfPK); 
            if(countNum!=0){
            	return "该条数据已存在！";
        	}
            bean.setId(tblMchtSignAccInfPK);
            bean.setRcvBankNo(rcvBankNo);
            bean.setSignStatus(signStatus);
            bean.setSignType(signType);
            bean.setRcvBankId(rcvBankId);
            bean.setCrtOprId(operator.getOprId());
            bean.setUpdOprId(operator.getOprId());
            bean.setBankNo(rcvBankId);
            bean.setAcctName(acctName);
            bean.setBankName(rcvBankNo);
            bean.setBankAddr("-");
            bean.setRecCrtTs(CommonFunction.getCurrentDateTime());
            bean.setRecUpdTs(CommonFunction.getCurrentDateTime());
            T21200BO.add(bean);
            return Constants.SUCCESS_CODE;
        } else {
            return "传入后台系统参数丢失，请联系管理员!";
        }
    }

    public String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{

        jsonBean.parseJSONArrayData(TblMchtSignAccInfList);
//
//        int len = jsonBean.getArray().size();
//
//        TblMchtSignAccInf tblMchntTp = null;
//        TblMchtSignAccInfPK tblMchtSignAccInfPK=null;
//
//        List<TblMchtSignAccInf> tblMchntTpList = new ArrayList<TblMchtSignAccInf>(len);
//        for (int i = 0; i < len; i++) {
//            
//             tblMchtSignAccInfPK = new TblMchtSignAccInfPK();
//    jsonBean.setObject(jsonBean.getJSONDataAt(i));
//            
//            BeanUtils.setObjectWithPropertiesValue(tblMchtSignAccInfPK, jsonBean,false);
            
//            this.mchntId = jsonBean.getJSONDataAt(i).getString("mchntId");
//            this.signAcct = jsonBean.getJSONDataAt(i).getString("signAcct");
//            this.rcvBankNo = jsonBean.getJSONDataAt(i).getString("rcvBankNo");
//            this.signStatus = jsonBean.getJSONDataAt(i).getString("signStatus");
//            this.rcvBankId = jsonBean.getJSONDataAt(i).getString("rcvBankId");
//            System.out.println(mchntId + "==" + signAcct + "==" + rcvBankNo + "===" + signStatus
//                    + "==" + rcvBankId);
            
//            tblMchntTp=new TblMchtSignAccInf();
//            tblMchntTp.setId(tblMchtSignAccInfPK);
//            tblMchntTp.setRcvBankNo(rcvBankNo);
//            tblMchntTp.setSignStatus(signStatus);
//            tblMchntTp.setRcvBankId(rcvBankId);
            
//            BeanUtils.setObjectWithPropertiesValue(tblMchntTp, jsonBean,false);
//            
//            tblMchntTp.setUpdOprId(operator.getOprId());
//            tblMchntTp.setRecUpdTs(CommonFunction.getCurrentDateTime());
//
//            tblMchntTpList.add(tblMchntTp);
//        }
        return T21200BO.update(jsonBean, operator);
//        logger.info("修改商户签约账户成功");
//        return Constants.SUCCESS_CODE;
    }

    public String delete() {
      
       
        return  T21200BO.delete(mchntId,signAcct);
    }

    public String getMchntId() {
        return mchntId;
    }

    public void setMchntId(String mchntId) {
        this.mchntId = mchntId;
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

    public String getSignAcct() {
        return signAcct;
    }

    public void setSignAcct(String signAcct) {
        this.signAcct = signAcct;
    }
    

    public T21200BO getT21200BO() {
		return T21200BO;
	}

	public void setT21200BO(T21200BO t21200bo) {
		T21200BO = t21200bo;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getTblMchtSignAccInfList() {
        return TblMchtSignAccInfList;
    }

    public void setTblMchtSignAccInfList(String tblMchtSignAccInfList) {
        TblMchtSignAccInfList = tblMchtSignAccInfList;
    }

}
