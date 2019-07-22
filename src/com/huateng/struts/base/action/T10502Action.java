package com.huateng.struts.base.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import com.huateng.bo.base.T10502BO;
import com.huateng.common.StringUtil;
import com.huateng.po.base.TblComCardInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;

public class T10502Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private T10502BO t10502BO = (T10502BO) ContextUtil.getBean("T10502BO");

	@Override
	protected String subExecute(){
		try {
			if("add".equals(getMethod())) {				//新增
				rspCode = add();			
			} else if("delete".equals(getMethod())) {	//删除，直接从数据库删除这条记录，只用到卡号
				rspCode = delete();
			} else if("update".equals(getMethod())) {	//修改，前台传来JOSN类型的字符串，在BO中处理
				rspCode = update();
			} else if("batch".equals(getMethod())) {	//修改，前台传来JOSN类型的字符串，在BO中处理
				rspCode = batch();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对财务POS账户的维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}

	private String add() throws Exception {
		
		//这里只把数据封装成TblComCardInf，传给BO，数据监测会在BO中做
		TblComCardInf tblComCardInf = new TblComCardInf();
		tblComCardInf.setId(cardId);
		tblComCardInf.setMchtNo(mchtId);
		tblComCardInf.setTermId(posId);
		tblComCardInf.setHolderName(holderName);
		tblComCardInf.setHolderId(holderId);
		tblComCardInf.setHolderTel(holderTel);
		tblComCardInf.setSmsQuota(smsQuota);
		tblComCardInf.setStartTime(startTime);
		tblComCardInf.setStopTime(stopTime);
		return t10502BO.add(tblComCardInf);
	}

	private String delete() throws Exception {
		
		return t10502BO.delete(cardId);
	}

	private String update() throws Exception {
		
		//数据在BO中解析、处理
		return t10502BO.update(infoList);
	}

	private String batch() throws Exception {
	
		List<TblComCardInf> infoList = new LinkedList<TblComCardInf>();	//存放账户信息的list，发给BO，BO做进一步处理后存上
		
		for(File file:files) {	//files为前台传过来的多个文件，每个文件中包含若干条信息，信息中的字段以逗号分割，一条信息占一行
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
			while(reader.ready()) {
				
				String tmp = reader.readLine();
				if(!StringUtil.isNull(tmp)) {
					
					String[] data = tmp.split(",");
					TblComCardInf tblComCardInf = new TblComCardInf();
					tblComCardInf.setId(data[0]);			//卡号
					tblComCardInf.setMchtNo(data[1]);		//商户号
					tblComCardInf.setTermId(data[2]);		//终端号
					tblComCardInf.setHolderName(data[3]);	//姓名
					tblComCardInf.setHolderId(data[4]);		//身份证
					tblComCardInf.setHolderTel(data[5]);	//电话
					tblComCardInf.setSmsQuota(data[6]);
					tblComCardInf.setStartTime(data[7]);	//起始时间
					tblComCardInf.setStopTime(data[8]);		//结束时间
					infoList.add(tblComCardInf);
				}
			}
		}
		
		return t10502BO.addBatch(infoList);
	}
	
	private String cardId;		//卡号、账户号
	private String mchtId;		//商户号
	private String posId;  		//终端号
	private String holderName;	//持卡人姓名
	private String holderId;	//持卡人身份证号
	private String holderTel;	//持卡人电话
	private String smsQuota;	//短信通知限额
	private String startTime;	//交易开始时间
	private String stopTime;	//交易结束时间
	
	private String infoList;	//修改的信息集合
	
	private List<File> files;	//多个批量导入文件
	
	public void setInfoList(String infoList) {
		this.infoList = infoList;
	}
	
	public void setSmsQuota(String smsQuota) {
		this.smsQuota = smsQuota;
	}
	
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}
	
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public void setHolderId(String holderId) {
		this.holderId = holderId;
	}

	public void setHolderTel(String holderTel) {
		this.holderTel = holderTel;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
	
	public void setFiles(List<File> files) {
		this.files = files;
	}
}
