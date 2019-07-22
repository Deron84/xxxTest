package com.huateng.struts.base.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.base.T10205BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.TblBankBinInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;

/**
 * Title:银联卡表维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-17
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T10205Action extends BaseAction {
	
	
	T10205BO t10205BO = (T10205BO) ContextUtil.getBean("T10205BO");
	

	private String ind;
	private String CardTp;
	
	private String insIdCd;
	//一磁偏移量
	private String acc1Offset;	
    //一磁长度
	private String acc1Len;
	//一磁所在磁道号
	private String acc1Tnum;	
	//二磁偏移量
	private String acc2Offset;	
	//二磁长度
	private String acc2Len;	
	//二磁所在磁道号
	private String acc2Tnum;
	//卡bin偏移量
	private String binOffSet;
	//卡bin长度
	private String binLen;
	//卡bin起始值
	private String binStaNo;	
	//卡bin结束值
	private String binEndNo;	
	//卡bin所在磁道号
	private String binTnum;	
	//描述
	private String cardDis;	
	
	private String tblBankBinInfList;

	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
			if("add".equals(method)) {					
				 rspCode = add();				
			} else if("delete".equals(method)) {				
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			} else if("upload".equals(method)) {
				rspCode = upload();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，银联卡表维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	/**
	 * add city code
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String add() throws IllegalAccessException, InvocationTargetException{
		TblBankBinInf tblBankBinInf = new TblBankBinInf();
		String bankBinId = GenerateNextId.getBankBinId();
		BeanUtils.copyProperties(tblBankBinInf, this);	
		tblBankBinInf.setId(Integer.parseInt(bankBinId));
		tblBankBinInf.setRecOprId(operator.getOprId());
		tblBankBinInf.setRecUpdOpr(operator.getOprId());
		tblBankBinInf.setRecCrtTs(CommonFunction.getCurrentDateTime());		
		tblBankBinInf.setRecUpdTs(CommonFunction.getCurrentDateTime());	
		t10205BO.createTblBankBinInf(tblBankBinInf);
		log("新增新联卡表信息成功。操作员编号：" + operator.getOprId());		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * delete city code
	 * @return
	 */
	private String delete() {
			t10205BO.delete(Integer.parseInt(ind));
		log("删除新联卡表信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * update city code
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws ASException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		jsonBean.parseJSONArrayData(getTblBankBinInfList());
		
		int len = jsonBean.getArray().size();
		
		TblBankBinInf tblBankBinInf = null;
	
		List<TblBankBinInf> tblBankBinInfList = new ArrayList<TblBankBinInf>(len);
		try {
			for (int i = 0; i < len; i++) {
				tblBankBinInf = t10205BO.get(new Integer(jsonBean
						.getJSONDataAt(i).getString("ind")));
				BeanUtils.setObjectWithPropertiesValue(tblBankBinInf, jsonBean,
						false);
				tblBankBinInf.setCardDis(tblBankBinInf.getCardDis().trim());
				tblBankBinInfList.add(tblBankBinInf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		t10205BO.update(tblBankBinInfList);

		log("更新银联卡表信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	
	private String upload(){
		
		List<TblBankBinInf> tblBankBinInfList = new ArrayList<TblBankBinInf>();
//		Map<String, String> map = new HashMap<String, String>();
		try {
			TblBankBinInf tblBankBinInf = null;
			for(File file : files) {
				BufferedReader reader = 
					new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
				while(reader.ready()){
					String tmp = reader.readLine();
					if (!StringUtil.isNull(tmp)) {
						
						String[] data = tmp.split(",");
						tblBankBinInf = new TblBankBinInf();
						
						tblBankBinInf.setCardDis(data[0]); // 发卡行名称 card_dis CHAR(40),
						tblBankBinInf.setInsIdCd(data[1]); // 发卡行编号 ins_id_cd CHAR(11) NOT NULL,
						tblBankBinInf.setAcc1Offset(data[10]); // 卡号偏移量 acc1_offset CHAR(2),
						tblBankBinInf.setAcc1Len(data[11]); // 卡号长度 acc1_len CHAR(2),
						tblBankBinInf.setAcc1Tnum(data[12]); // 卡号磁道号 acc1_tnum CHAR(1),
						
						tblBankBinInf.setBinOffSet(data[13]); // 卡BIN偏移量  bin_offset CHAR(2) NOT NULL,
						tblBankBinInf.setBinLen(data[14]); // 卡BIN长度 bin_len CHAR(2) NOT NULL,
						tblBankBinInf.setBinStaNo(data[15]); // 卡BIN起值 bin_sta_no CHAR(30) NOT NULL,
						tblBankBinInf.setBinEndNo(data[15]); // 卡BIN终值 bin_end_no CHAR(30) NOT NULL,
						tblBankBinInf.setBinTnum(data[16]); // 卡BIN磁道号 bin_tnum CHAR(1) NOT NULL,
						if (!StringUtil.isNull(data[17])) { // 交易卡种 card_tp CHAR(2) NOT NULL,
							if ("借记卡".equals(data[17].trim())) {
								tblBankBinInf.setCardTp("01");
							} else {
								tblBankBinInf.setCardTp("00");
							}
						}
				
						tblBankBinInf.setRecCrtTs(CommonFunction.getCurrentDateTime());		
						tblBankBinInf.setRecUpdTs(CommonFunction.getCurrentDateTime());	
						
						tblBankBinInfList.add(tblBankBinInf);
						
//						map.put(data[15] + data[16], tmp);
					}
				}
				reader.close();
			}
			
			int success = 0;
			int fail = 0;
			
			for(TblBankBinInf inf:tblBankBinInfList){
				
				String sql = "select count(1) from TBL_BANK_BIN_INF where " +
							"BIN_STA_NO = '" + inf.getBinStaNo() + 
							"' and ACC1_LEN = '" + inf.getAcc1Len() + 
							"' and BIN_TNUM ='"+inf.getBinTnum()+
							"' and INS_ID_CD = '"+CommonFunction.fillString(inf.getInsIdCd(), ' ', 11, true) +"'";
				BigDecimal count = (BigDecimal) CommonFunction.getCommQueryDAO().findBySQLQuery(sql).get(0);
				if (count.intValue() != 0) {
					fail++;
					continue;
				}
				String bankBinId = GenerateNextId.getBankBinId();
				inf.setId(Integer.parseInt(bankBinId));
				t10205BO.createTblBankBinInf(inf);
				success++;
			}
			return Constants.SUCCESS_CODE_CUSTOMIZE + 
				"成功录入条目：" + String.valueOf(success) + ",已存在的条目：" + String.valueOf(fail);
		} catch (Exception e) {
			e.printStackTrace();
			return "解析文件失败";
		}
	}

	public T10205BO getT10205BO() {
		return t10205BO;
	}

	public void setT10205BO(T10205BO t10205bo) {
		t10205BO = t10205bo;
	}

	public String getCardTp() {
		return CardTp;
	}

	public void setCardTp(String cardTp) {
		CardTp = cardTp;
	}

	public String getInsIdCd() {
		return insIdCd;
	}

	public void setInsIdCd(String insIdCd) {
		this.insIdCd = insIdCd;
	}

	public String getAcc1Offset() {
		return acc1Offset;
	}

	public void setAcc1Offset(String acc1Offset) {
		this.acc1Offset = acc1Offset;
	}

	public String getAcc1Len() {
		return acc1Len;
	}

	public void setAcc1Len(String acc1Len) {
		this.acc1Len = acc1Len;
	}

	public String getAcc1Tnum() {
		return acc1Tnum;
	}

	public void setAcc1Tnum(String acc1Tnum) {
		this.acc1Tnum = acc1Tnum;
	}

	public String getAcc2Offset() {
		return acc2Offset;
	}

	public void setAcc2Offset(String acc2Offset) {
		this.acc2Offset = acc2Offset;
	}

	public String getAcc2Len() {
		return acc2Len;
	}

	public void setAcc2Len(String acc2Len) {
		this.acc2Len = acc2Len;
	}

	public String getAcc2Tnum() {
		return acc2Tnum;
	}

	public void setAcc2Tnum(String acc2Tnum) {
		this.acc2Tnum = acc2Tnum;
	}
	
	public String getBinStaNo() {
		return binStaNo;
	}

	public void setBinStaNo(String binStaNo) {
		this.binStaNo = binStaNo;
	}

	public String getBinEndNo() {
		return binEndNo;
	}

	public void setBinEndNo(String binEndNo) {
		this.binEndNo = binEndNo;
	}

	public String getBinTnum() {
		return binTnum;
	}

	public void setBinTnum(String binTnum) {
		this.binTnum = binTnum;
	}

	public String getCardDis() {
		return cardDis;
	}

	public void setCardDis(String cardDis) {
		this.cardDis = cardDis;
	}

	public String getInd() {
		return ind;
	}

	public void setInd(String ind) {
		this.ind = ind;
	}

	public String getTblBankBinInfList() {
		return tblBankBinInfList;
	}

	public void setTblBankBinInfList(String tblBankBinInfList) {
		this.tblBankBinInfList = tblBankBinInfList;
	}

	public String getBinOffSet() {
		return binOffSet;
	}

	public void setBinOffSet(String binOffSet) {
		this.binOffSet = binOffSet;
	}

	public String getBinLen() {
		return binLen;
	}

	public void setBinLen(String binLen) {
		this.binLen = binLen;
	}
	

	// 文件集合
	private List<File> files;
	// 文件名称集合
	private List<String> filesFileName;


	/**
	 * @return the files
	 */
	public List<File> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(List<File> files) {
		this.files = files;
	}

	/**
	 * @return the filesFileName
	 */
	public List<String> getFilesFileName() {
		return filesFileName;
	}

	/**
	 * @param filesFileName the filesFileName to set
	 */
	public void setFilesFileName(List<String> filesFileName) {
		this.filesFileName = filesFileName;
	}
	
	
	
}
