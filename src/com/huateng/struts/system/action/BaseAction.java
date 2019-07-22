package com.huateng.struts.system.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alibaba.fastjson.JSON;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.TblTxnInfoConstants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;
import com.huateng.system.util.JSONBean;
import com.huateng.system.util.SysCodeUtil;
import com.huateng.system.util.TxnInfoUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.sdses.dao.iface.TblTxnInfoDAO2;
import com.sdses.po.TblTxnInfo;
import com.sdses.po.TblTxnInfoPK;

/**
 * Title:所有action的基类
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-5-21
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public abstract class BaseAction extends ActionSupport {

    private static final long serialVersionUID = -5921599244769853409L;

    private static Logger log = Logger.getLogger(BaseAction.class);

    /**交易代码*/
    private String txnId;

    /**交易子码*/
    private String subTxnId;

    /**操作员对象*/
    protected Operator operator;

    /**交易返回码*/
    protected String rspCode = "运行时错误";

    /**通用查询DAO*/
    protected ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");

    /**客户端调用方法名称*/
    protected String method;

    /**JSON数据对象*/
    protected JSONBean jsonBean = new JSONBean();

    /**request*/
    protected HttpServletRequest request;

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String execute() {
        //操作日志
        TblTxnInfo tblTxnInfo = null;
        TblTxnInfoPK tblTxnInfoPK = null;
        TblTxnInfoDAO2 txnInfoDAO = null;
        request = ServletActionContext.getRequest();
        try {
            operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
            if (getTxnId() != null && getSubTxnId() != null) {
                txnInfoDAO = (TblTxnInfoDAO2) ContextUtil.getBean("TxnInfoDAO");
                tblTxnInfo = new TblTxnInfo();
                tblTxnInfoPK = new TblTxnInfoPK();
                String acctTxnDate = CommonFunction.getCurrentDate();
                tblTxnInfoPK.setAcctDate(acctTxnDate);
                tblTxnInfoPK.setTxnSeqNo(GenerateNextId.getTxnSeq());
                tblTxnInfo.setId(tblTxnInfoPK);

                String currentTime = CommonFunction.getCurrentDateTime();
                tblTxnInfo.setTxnDate(currentTime.substring(0, 8));
                tblTxnInfo.setTxnTime(currentTime.substring(8, 14));
                tblTxnInfo.setTxnCode(txnId);
                tblTxnInfo.setSubTxnCode(subTxnId);
                tblTxnInfo.setOprId(operator.getOprId());
                tblTxnInfo.setOrgCode(operator.getOprBrhId());
                tblTxnInfo.setIpAddr(getRequest().getRemoteHost());
                tblTxnInfo.setTxnName(TxnInfoUtil.getTxnInfo(getTxnId() + "." + getSubTxnId()));
                tblTxnInfo.setTxnSta(TblTxnInfoConstants.TXN_STA_FAILURE);
                tblTxnInfo.setErrMsg("-");
            }

            rspCode = subExecute();

            if (getTxnId() != null && getSubTxnId() != null) {
                if (Constants.SUCCESS_CODE.equals(rspCode)) {
                    tblTxnInfo.setTxnSta(TblTxnInfoConstants.TXN_STA_SUCCESS);
                    txnInfoDAO.save(tblTxnInfo);
                    writeSuccessMsg(TxnInfoUtil.getTxnInfo(getTxnId() + "." + getSubTxnId()) + "成功");
                } else if (rspCode.length() > 2 && Constants.SUCCESS_CODE_CUSTOMIZE.equalsIgnoreCase(rspCode.substring(0, 2))) {
                    //自定义返回信息，信息格式：Constants.SUCCESS_CODE_CUSTOMIZE + 信息
                    tblTxnInfo.setTxnSta(TblTxnInfoConstants.TXN_STA_SUCCESS);
                    txnInfoDAO.save(tblTxnInfo);
                    writeSuccessMsg(rspCode.substring(2));
                } else {

                    if (rspCode.length() == 8 && rspCode.contains(".")) {
                        writeErrorMsg(SysCodeUtil.getErrCode(rspCode));
                    } else {
                        if (Constants.FAILURE_CODE.equals(rspCode)) {
                            writeErrorMsg("运行时错误");
                        } else {
                            writeErrorMsg(rspCode);
                        }

                    }

                    tblTxnInfo.setTxnSta(TblTxnInfoConstants.TXN_STA_FAILURE);
                    if (rspCode.length() > 512) {
                        rspCode = rspCode.substring(0, 512);
                    }
                    tblTxnInfo.setErrMsg(rspCode);
                    txnInfoDAO.save(tblTxnInfo);
                }
                log(TxnInfoUtil.getTxnInfo(getTxnId() + "." + getSubTxnId()) + ":" + rspCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (getTxnId() != null) {
                tblTxnInfo.setTxnSta(TblTxnInfoConstants.TXN_STA_FAILURE);
                if (e.getMessage().length() > 512) {
                    tblTxnInfo.setErrMsg(e.getMessage().substring(0, 1024));
                } else {
                    tblTxnInfo.setErrMsg(e.getMessage());
                }
                txnInfoDAO.save(tblTxnInfo);
            }

            log("交易失败，交易码：[ " + getTxnId() + "." + getSubTxnId() + " ]，异常信息：[ " + e.getMessage() + " ]");
            try {
                writeErrorMsg("对不起，本次操作失败！");
            } catch (IOException e1) {
                e1.printStackTrace();
                log("交易失败，交易码：[ " + getTxnId() + "." + getSubTxnId() + " ]，异常信息：[ " + e.getMessage() + " ]");
            }
        }
        return SUCCESS;
    }

    /**
     * 所有子类实现该方法实现业务逻辑
     * @return
     * @throws Exception
     */
    protected abstract String subExecute() throws Exception;

    /**
     * 将JSON信息返回到客户端
     * @param message
     * @throws IOException
     */
    protected void writeMessage(String message) throws IOException {
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write(message);
        printWriter.flush();
        printWriter.close();
    }

    /**
     * 记录系统日志
     * @param info
     */
    protected void log(String info) {
        log.info(info);
    }

    /**
     * 设置request的attribute
     * @param name
     * @param value
     */
    protected void setRequestAttribute(String name, Object value) {
        ServletActionContext.getRequest().setAttribute(name, value);
    }

    /**
     * 设置session的attribute
     * @param name
     * @param value
     */
    protected void setSessionAttribute(String name, Object value) {
        ServletActionContext.getRequest().getSession().setAttribute(name, value);
    }

    /**
     * 删除session内attribute
     * @param name
     * 2010-8-31下午10:11:37
     */
    protected void removeSessionAttribute(String name) {
        ServletActionContext.getRequest().getSession().removeAttribute(name);
    }

    /**
     * 获得request的attribute
     * @param name
     */
    protected Object getRequestAttribute(String name) {
        return ServletActionContext.getRequest().getAttribute(name);
    }

    /**
     * 获得session的attribute
     * @param name
     */
    protected Object getSessionAttribute(String name) {
        return ServletActionContext.getRequest().getSession().getAttribute(name);
    }

    /**
     * 输出成功信息
     * @param msg
     * @throws IOException 
     */
    protected void writeSuccessMsg(String msg) throws IOException {
        JSONBean jsonBean = new JSONBean();
        jsonBean.addJSONElement(Constants.SUCCESS_HEADER, true);
        jsonBean.addJSONElement(Constants.PROMPT_MSG, msg);
        writeMessage(jsonBean.toString());
    }

    /**
     * 输出失败信息
     * @param msg
     * @throws IOException
     */
    protected void writeErrorMsg(String msg) throws IOException {
        JSONBean jsonBean = new JSONBean();
        jsonBean.addJSONElement(Constants.SUCCESS_HEADER, false);
        jsonBean.addJSONElement(Constants.PROMPT_MSG, msg);
        writeMessage(jsonBean.toString());
    }

    /**
     * 系统提示信息
     * @param msg
     * @param code
     * @throws IOException
     * 2010-8-31下午06:20:16
     */
    protected void writeAlertMsg(String msg, String code) throws IOException {
        JSONBean jsonBean = new JSONBean();
        jsonBean.addJSONElement(Constants.SUCCESS_HEADER, false);
        jsonBean.addJSONElement(Constants.PROMPT_MSG, msg);
        jsonBean.addJSONElement(Constants.PROMPT_CODE, code);
        writeMessage(jsonBean.toString());
    }

    /**
     * 判断信息是否为空
     * @param str
     * @return
     */
    protected boolean isNotEmpty(String str) {
        if (str != null && !"".equals(str.trim()))
            return true;
        else
            return false;
    }

    /**
     * @return the subTxnId
     */
    public String getSubTxnId() {
        return subTxnId;
    }

    /**
     * @param subTxnId the subTxnId to set
     */
    public void setSubTxnId(String subTxnId) {
        this.subTxnId = subTxnId;
    }

    /**
     * 获得request对象
     * @return
     * 2010-12-9 上午10:24:32
     * Shuang.Pan
     */
    protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * @param name
     * @param obj
     * @param request
     */
    protected void storeTmpInfoToSession(String name, Object obj) {
        getRequest().getSession().setAttribute(name, obj);
    }

    /**
     * @param name
     * @param request
     * @return
     */
    protected Object getTmpInfoFromSession(String name) {
        return getRequest().getSession().getAttribute(name);
    }
    


    /**
     * httpServletRequest,httpServletResponse 线程安全直接引入。
     */
    @ExceptionHandler
    public void exp( Exception ex) throws IOException {
        log.error("异常：",ex);
        PrintWriter pw = ServletActionContext.getResponse().getWriter();
        String msg  = "服务器异常";
        if(ex instanceof RuntimeException){
        	RuntimeException exception = (RuntimeException) ex;
            msg =  exception.getMessage();
            pw.print(JSON.toJSONString(com.rail.bo.pub.ResultUtils.errorCode(500,msg)));
        }else{
            pw.print(JSON.toJSONString(com.rail.bo.pub.ResultUtils.errorCode(500,msg)));
        }
        pw.flush();
        pw.close();
    }
    
}
