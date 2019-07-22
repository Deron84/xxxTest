package com.huateng.struts.mchnt.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.xwork.StringUtils;

import com.huateng.bo.impl.mchnt.TblMchntService;
import com.huateng.bo.mchnt.T20101BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.common.TblMchntInfoConstants;
import com.huateng.po.TblMchtCupInfoTmp;
import com.huateng.po.mchnt.TblMchntUser;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtLicenceInfTmp;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.FileFilter;
import com.huateng.system.util.GenerateNextId;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

public class T20100Action extends BaseSupport {

	private static final long serialVersionUID = 1L;

	public String add() {
		try {

			String idStr = areaNo + mcc; // areaNo所在地区 商户mcc
			mchtNo = GenerateNextId.getMchntId(idStr);

			TblMchtBaseInfTmp tblMchtBaseInfTmp = buildTmpMchtBaseInfo();

			TblMchtSettleInfTmp tblMchtSettleInfTmp = buildTmpMchtSettleInfo();
			
			TblMchtCupInfoTmp tblMchtCupInfoTmp = buildTblMchtCupInfo();
			
			rspCode = service.saveTmp(tblMchtBaseInfTmp, tblMchtSettleInfTmp, tblMchtCupInfoTmp);

			if (Constants.SUCCESS_CODE.equals(rspCode)) {
				/*//添加商户平台信息
				T20101BO t20101BO = (T20101BO) ContextUtil.getBean("T20101BO");
				TblMchtBaseInfTmp tblMchtBase=t20101BO.get(mchtNo);
				TblMchntUser tblMchtUser=new TblMchntUser();

				tblMchtUser.setId(InformationUtil.getMchtUserId());
				tblMchtUser.setMchntId(mchtNo);
				//加密
				tblMchtUser.setLoginPwd(T21400Action.MD5("000000"));
				tblMchtUser.setLoginName(tblMchtBase.getMchtNo());
				tblMchtUser.setMchntName(tblMchtBase.getMchtNm());
				tblMchtUser.setMchntPhone(tblMchtBase.getCommMobil());
				tblMchtUser.setStatus("1");
				
				
				tblMchtUser.setCreateTime(CommonFunction.getCurrentDateTime());
				
				service.saveMchtUser(tblMchtUser);*/
				
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE
						+ "新增商户信息成功，商户编号[" + mchtNo + "]");
			} else {
				return returnService(rspCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}

	private TblMchtCupInfoTmp buildTblMchtCupInfo() 
			throws IllegalAccessException, InvocationTargetException {
		TblMchtCupInfoTmp tmp =new TblMchtCupInfoTmp();
		BeanUtils.copyProperties(tmp, this);
		
		tmp.setMcht_no(mchtNo);
		tmp.setMcht_nm(mchtNm);
		tmp.setMcht_short_cn(mchtCnAbbr);
		tmp.setMcht_e_nm(engName);
		tmp.setConn_type("S");
		tmp.setAcq_inst_id_code("08"+acqInstId);
		tmp.setMcht_status("9");
		tmp.setManager(manager1);
		tmp.setMana_cred_tp(artifCertifTp);
		tmp.setMana_cred_no(identityNo);
		tmp.setSettle_flg("1");//参与
		tmp.setCrt_opr_id(getOperator().getOprId());
		tmp.setCrt_ts(CommonFunction.getCurrentDateTime());
		
		/** 以下四个取值重复 */
		tmp.setLicence_no(licenceNo);
		tmp.setLicence_add(addr);
		tmp.setPrincipal(manager1);
		if(commTel.isEmpty() || "".equalsIgnoreCase(commTel)){
			tmp.setComm_tel("");
		}else{
			tmp.setComm_tel(commTel);
		}
		
		if (!StringUtil.isNull(deposit_flag)
				&& "on".equalsIgnoreCase(deposit_flag)) {
			tmp.setDeposit_flag("1");
		} else {
			tmp.setDeposit_flag("0");
		}
		
		if (!StringUtil.isNull(res_pan_flag)
				&& "on".equalsIgnoreCase(res_pan_flag)) {
			tmp.setRes_pan_flag("1");
		} else {
			tmp.setRes_pan_flag("0");
		}
		
		if (!StringUtil.isNull(res_track_flag)
				&& "on".equalsIgnoreCase(res_track_flag)) {
			tmp.setRes_track_flag("1");
		} else {
			tmp.setRes_track_flag("0");
		}
		
		if (!StringUtil.isNull(process_flag)
				&& "on".equalsIgnoreCase(process_flag)) {
			tmp.setProcess_flag("1");
		} else {
			tmp.setProcess_flag("0");
		}
		
		if(StringUtils.isNotEmpty(mchtCup40)){
			tmp.setMcht_acq_rebate(mchtCup40);
		}else if(StringUtils.isNotEmpty(mchtCup401)){
			tmp.setMcht_acq_rebate(mchtCup401);
		}
		tmp.setRebate_stlm_cd(rebate_stlm_cd);
		
		//分段计费
		if (!StringUtils.isNotEmpty(mchtCup54)) {
			mchtCup54 = "0";
		} 
		if (!StringUtils.isNotEmpty(mchtCup541)) {
			mchtCup541 = "0";
		} 
		if (!StringUtils.isNotEmpty(mchtCup542)) {
			mchtCup542 = "0";
		} 
		if (!StringUtils.isNotEmpty(mchtCup543)) {
			mchtCup543 = "0";
		} 
		String feeDivMode = mchtCup54 + mchtCup541 + mchtCup542 + mchtCup543;
		tmp.setFee_div_mode(feeDivMode);
		
		//分润角色
		if (!StringUtils.isNotEmpty(mchtCup781)) {
			mchtCup781 = "0";
		}else if("on".equalsIgnoreCase(mchtCup781)){
			mchtCup781 = "1";
		}
		if (!StringUtils.isNotEmpty(mchtCup782)) {
			mchtCup782 = "0";
		}else if("on".equalsIgnoreCase(mchtCup782)){
			mchtCup782 = "1";
		}
		if (!StringUtils.isNotEmpty(mchtCup783)) {
			mchtCup783 = "0";
		}else if("on".equalsIgnoreCase(mchtCup783)){
			mchtCup783 = "1";
		}
		if (!StringUtils.isNotEmpty(mchtCup784)) {
			mchtCup784 = "0";
		}else if("on".equalsIgnoreCase(mchtCup784)){
			mchtCup784 = "1";
		}
		if (!StringUtils.isNotEmpty(mchtCup785)) {
			mchtCup785 = "0";
		}else if("on".equalsIgnoreCase(mchtCup785)){
			mchtCup785 = "1";
		}
		if (!StringUtils.isNotEmpty(mchtCup786)) {
			mchtCup786 = "0";
		}else if("on".equalsIgnoreCase(mchtCup786)){
			mchtCup786 = "1";
		}
		if (!StringUtils.isNotEmpty(mchtCup787)) {
			mchtCup787 = "0";
		}else if("on".equalsIgnoreCase(mchtCup787)){
			mchtCup787 = "1";
		}
		String rateRole = mchtCup781 + mchtCup782 + mchtCup783 + mchtCup784 + mchtCup785 + mchtCup786 + mchtCup787 + "000";
		tmp.setRate_role(rateRole);
		
		//商户属性位图，填充到10位
		if (!StringUtils.isNotEmpty(attr_bmp)) {
			attr_bmp = "0";
		}else if("on".equalsIgnoreCase(attr_bmp)){
			attr_bmp = "1";
		}
		String attrBmp = attr_bmp + "000000000";
		tmp.setAttr_bmp(attrBmp);
		
		//报表生成位图，3位组成一个字段，填充到10位
		if (!StringUtils.isNotEmpty(mchtCup82)) {
			mchtCup82 = "0";
		}else if("on".equalsIgnoreCase(mchtCup82)){
			mchtCup82 = "1";
		}
		if (!StringUtils.isNotEmpty(mchtCup821)) {
			mchtCup821 = "0";
		}else if("on".equalsIgnoreCase(mchtCup821)){
			mchtCup821 = "1";
		}
		if (!StringUtils.isNotEmpty(mchtCup822)) {
			mchtCup822 = "0";
		}else if("on".equalsIgnoreCase(mchtCup822)){
			mchtCup822 = "1";
		}
		String reprotBmp = mchtCup82 + mchtCup821 + mchtCup822 + "0000000";
		tmp.setReport_bmp(reprotBmp);
		
		return tmp;
	}

	private TblMchtSettleInfTmp buildTmpMchtSettleInfo()
			throws IllegalAccessException, InvocationTargetException {

		TblMchtSettleInfTmp tblMchtSettleInfTmp = new TblMchtSettleInfTmp();
		BeanUtils.copyProperties(tblMchtSettleInfTmp, this);

		tblMchtSettleInfTmp.setSettleAcct(clearType + settleAcct);// 前面加上账户结算类型标志位
//		if(!"1".equals(acctSettleType) && StringUtil.isNotEmpty(acctSettleType)){
//			if(StringUtil.isNotEmpty(clearType2) && StringUtil.isNotEmpty(settleAcctSnd)){
//				tblMchtSettleInfTmp.setSettleAcctSnd(clearType2 + settleAcctSnd);// 前面加上账户结算类型标志位
//			}
//		}
		tblMchtSettleInfTmp.setFeeBackFlg("0");
		tblMchtSettleInfTmp.setRateFlag("-");
		tblMchtSettleInfTmp.setFeeType("3"); // 分段收费
		tblMchtSettleInfTmp.setFeeFixed("-");
		tblMchtSettleInfTmp.setFeeMaxAmt("0");
		tblMchtSettleInfTmp.setFeeMinAmt("0");
		tblMchtSettleInfTmp.setFeeRate(discCode);
		tblMchtSettleInfTmp.setFeeDiv1("1");
		tblMchtSettleInfTmp.setFeeDiv2("2");
		tblMchtSettleInfTmp.setFeeDiv3("3");
		tblMchtSettleInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tblMchtSettleInfTmp.setRecCrtTs(CommonFunction.getCurrentDateTime());
		
		tblMchtSettleInfTmp.setSettleBankNmSnd(settleBankNmSnd);//商户分润金额/比例(%)
		tblMchtSettleInfTmp.setSettleBankNoSnd(settleBankNoSnd);//1级机构分润金额/比例(%)
		tblMchtSettleInfTmp.setSettleAcctNmSnd(settleAcctNmSnd);//2级机构分润金额/比例(%)
		tblMchtSettleInfTmp.setSettleAcctSnd(settleAcctSnd);//3级机构分润金额/比例(%)
		tblMchtSettleInfTmp.setAcctSettleLimit(acctSettleLimit);//手续费比例 
		tblMchtSettleInfTmp.setSettleAcctNm(clearType2);//分润方式(存到【商户账户1户名】settleAcctNm字段中)
		tblMchtSettleInfTmp.setAcctSettleRate(acctSettleRate);//支付宝账号

		// 是否自动清算
		if (!StringUtil.isNull(autoStlFlg)
				&& TblMchntInfoConstants.EXTJS_CHECKED
						.equalsIgnoreCase(autoStlFlg)) {
			tblMchtSettleInfTmp.setAutoStlFlg("1");
		} else {
			tblMchtSettleInfTmp.setAutoStlFlg("0");
		}

		if (StringUtil.isNull(openStlno)) {
			tblMchtSettleInfTmp.setOpenStlno(" ");
		}
		if (StringUtil.isNull(settleBankNo)) {
			tblMchtSettleInfTmp.setSettleBankNo(" ");
		}
		if (StringUtil.isNull(changeStlno)) {
			tblMchtSettleInfTmp.setChangeStlno(" ");
		}

		// 白名单标志
		if (!StringUtil.isNull(whiteListFlag)) {
			tblMchtSettleInfTmp.setWhiteListFlag("1");
		} else
			tblMchtSettleInfTmp.setWhiteListFlag("0");

		// 个人结算账户标志
		if (!StringUtil.isNull(personSettleFlg)) {
			tblMchtSettleInfTmp.setPersonSettleFlg("1");
		} else
			tblMchtSettleInfTmp.setPersonSettleFlg("0");

		return tblMchtSettleInfTmp;
	}

	private TblMchtBaseInfTmp buildTmpMchtBaseInfo()
			throws IllegalAccessException, InvocationTargetException {

		TblMchtBaseInfTmp tblMchntInfoTmp = new TblMchtBaseInfTmp();
		BeanUtils.copyProperties(tblMchntInfoTmp, this);
		tblMchntInfoTmp.setMchtGroupFlag("-");// 这个字段没用了
		tblMchntInfoTmp.setPassFlag("-");// 是否支持无磁无密，现在没用，当成pos终端支持的交易，在终端控制
		tblMchntInfoTmp.setManuAuthFlag("-");// 是否支持人工授权，没用
		tblMchntInfoTmp.setEngName(engName.length() > 40 ? "" : engName);

		tblMchntInfoTmp.setBankNo(signInstId);
		tblMchntInfoTmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK);
		tblMchntInfoTmp.setSettleAreaNo(areaNo);
		tblMchntInfoTmp.setUpdOprId(getOperator().getOprId());

		tblMchntInfoTmp.setRislLvl("0");
		tblMchntInfoTmp.setMchtLvl("0");
		tblMchntInfoTmp.setSaAction("0");
		if(StringUtils.isEmpty(contact)){
			tblMchntInfoTmp.setContact("-");
		}
		
		if (StringUtils.isNotEmpty(cupMchtFlg)) {
			tblMchntInfoTmp.setCupMchtFlg("1");
		} else {
			tblMchntInfoTmp.setCupMchtFlg("0");
		}

		if (StringUtils.isNotEmpty(debMchtFlg)) {
			tblMchntInfoTmp.setDebMchtFlg("1");
		} else {
			tblMchntInfoTmp.setDebMchtFlg("0");
		}

		if (StringUtils.isNotEmpty(creMchtFlg)) {
			tblMchntInfoTmp.setCreMchtFlg("1");
		} else {
			tblMchntInfoTmp.setCreMchtFlg("0");
		}

		if (StringUtils.isNotEmpty(cdcMchtFlg)) {
			tblMchntInfoTmp.setCdcMchtFlg("1");
		} else {
			tblMchntInfoTmp.setCdcMchtFlg("0");
		}

		tblMchntInfoTmp.setManagerTel("-");
		tblMchntInfoTmp.setManager(manager1);

		tblMchntInfoTmp.setApplyDate(CommonFunction.getCurrentDate());
		tblMchntInfoTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tblMchntInfoTmp.setRecCrtTs(CommonFunction.getCurrentDateTime());
		tblMchntInfoTmp.setCrtOprId(getOperator().getOprId());

		tblMchntInfoTmp.setDiscConsFlg("-");

		if (StringUtil.isNull(openTime) || "请选择".equals(openTime)) {
			tblMchntInfoTmp.setOpenTime("00:00");
		}

		if (StringUtil.isNull(closeTime) || "请选择".equals(closeTime)) {
			tblMchntInfoTmp.setCloseTime("23:59");
		}
		// 巡检频率
		if (StringUtil.isNull(checkFrqc))
			tblMchntInfoTmp.setCheckFrqc("180");// 巡检频率为空时设置为默认的180天

		//新增的字段赋值
		tblMchntInfoTmp.setMappingMchntTypeOne(Constants.TYPE_ONE);
		tblMchntInfoTmp.setMappingMchntcdOne(mappingMchntcdOne);
		tblMchntInfoTmp.setMappingMchntTypeTwo(Constants.TYPE_TWO);
		tblMchntInfoTmp.setMappingMchntcdTwo(mappingMchntcdTwo);
		
		return tblMchntInfoTmp;
	}

	public String upload() {

		FileInputStream is = null;
		DataOutputStream out = null;
		DataInputStream dis = null;

		try {
			String fileName = "";
			int fileNameIndex = 0;

			String basePath = SysParamUtil
					.getParam(SysParamConstants.FILE_UPLOAD_DISK);

			basePath = basePath.replace("\\", "/");

			Random random = new Random();

			for (File file : imgFile) {
				is = new FileInputStream(file);
				fileName = imgFileFileName.get(fileNameIndex);
				String fileType = "";
				if (fileName.indexOf(".") != -1) {
					fileType = fileName.substring(fileName.lastIndexOf("."));
				}

				File writeFile = new File(basePath + imagesId
						+ random.nextInt(999999) + fileType);

				if (!writeFile.getParentFile().exists()) {
					writeFile.getParentFile().mkdirs();
				}
				if (writeFile.exists()) {
					writeFile.delete();
				}
				dis = new DataInputStream(is);
				out = new DataOutputStream(new FileOutputStream(writeFile));

				int temp;
				while ((temp = dis.read()) != -1) {
					out.write(temp);
				}

				out.flush();
				out.close();
				dis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("上传文件失败！", e);
		} finally {
			try {
				if (null != out) {
					out.close();
				}
				if (null != is) {
					is.close();
				}
				if (null != dis) {
					dis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnService(Constants.SUCCESS_CODE);
	}
	
	/**
	 * @return the imgFile
	 */
	public List<File> getImgFile() {
		return imgFile;
	}

	/**
	 * @param imgFile
	 *            the imgFile to set
	 */
	public void setImgFile(List<File> imgFile) {
		this.imgFile = imgFile;
	}

	/**
	 * @return the imgFileFileName
	 */
	public List<String> getImgFileFileName() {
		return imgFileFileName;
	}

	/**
	 * @param imgFileFileName
	 *            the imgFileFileName to set
	 */
	public void setImgFileFileName(List<String> imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	// 文件集合
	private List<File> imgFile;

	// 文件名称集合
	private List<String> imgFileFileName;

	private String imagesId;

	private String hasUpload;

	/**
	 * @return the hasUpload
	 */
	public String getHasUpload() {
		return hasUpload;
	}

	/**
	 * @param hasUpload
	 *            the hasUpload to set
	 */
	public void setHasUpload(String hasUpload) {
		this.hasUpload = hasUpload;
	}

	/**
	 * @return the imagesId
	 */
	public String getImagesId() {
		return imagesId;
	}

	/**
	 * @param imagesId
	 *            the imagesId to set
	 */
	public void setImagesId(String imagesId) {
		this.imagesId = imagesId;
	}

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

	// primary key
	private java.lang.String mchtNo;

	// fields
	private java.lang.String mchtNm;

	private java.lang.String rislLvl;

	private java.lang.String mchtLvl;

	private java.lang.String mchtStatus;

	private java.lang.String manuAuthFlag;

	private java.lang.String partNum;

	private java.lang.String discConsFlg;

	private java.lang.String discConsRebate;

	private java.lang.String passFlag;

	private java.lang.String openDays;

	private java.lang.String sleepDays;

	private java.lang.String mchtCnAbbr;

	private java.lang.String spellName;

	private java.lang.String engName;

	private java.lang.String mchtEnAbbr;

	private java.lang.String areaNo;

	private java.lang.String settleAreaNo;

	private java.lang.String addr;

	private java.lang.String homePage;

	private java.lang.String mcc;

	private java.lang.String tcc;

	private String licenceNo;

	private java.lang.String mngMchtId;

	private java.lang.String mchtGrp;

	private java.lang.String mchtAttr;

	private java.lang.String mchtGroupFlag;

	private java.lang.String mchtGroupId;

	private java.lang.String mchtEngNm;

	private java.lang.String mchtEngAddr;

	private java.lang.String mchtEngCityName;

	private java.lang.String saLimitAmt;

	private java.lang.String saAction;

	private java.lang.String psamNum;

	private java.lang.String cdMacNum;

	private java.lang.String posNum;

	private java.lang.String connType;

	private java.lang.String mchtMngMode;

	private java.lang.String mchtFunction;

	private java.lang.String busType;

	private java.lang.String contact;

	private java.lang.String postCode;

	private java.lang.String commEmail;

	private java.lang.String commMobil;

	private java.lang.String commTel;

	private java.lang.String electrofax;

	private java.lang.String applyDate;

	private java.lang.String enableDate;

	private java.lang.String preAudNm;

	private java.lang.String confirmNm;

	private java.lang.String protocalId;

	private java.lang.String signInstId;

	private java.lang.String netNm;

	private java.lang.String agrBr;

	private java.lang.String netTel;

	private java.lang.String prolDate;

	private java.lang.String prolTlr;

	private java.lang.String closeDate;

	private java.lang.String closeTlr;

	private java.lang.String mainTlr;

	private java.lang.String checkTlr;

	private java.lang.String operNo;

	private java.lang.String operNm;

	private java.lang.String procFlag;

	private java.lang.String setCur;

	private java.lang.String printInstId;

	private java.lang.String acqInstId;

	private java.lang.String acqBkName;

	private java.lang.String bankNo;

	private java.lang.String orgnNo;

	private java.lang.String subbrhNo;

	private java.lang.String subbrhNm;

	private java.lang.String openTime;

	private java.lang.String closeTime;

	private java.lang.String visActFlg;

	private java.lang.String visMchtId;

	private java.lang.String mstActFlg;

	private java.lang.String mstMchtId;

	private java.lang.String amxActFlg;

	private java.lang.String amxMchtId;

	private java.lang.String dnrActFlg;

	private java.lang.String dnrMchtId;

	private java.lang.String jcbActFlg;

	private java.lang.String jcbMchtId;

	private java.lang.String cupMchtFlg;

	private java.lang.String debMchtFlg;

	private java.lang.String creMchtFlg;

	private java.lang.String cdcMchtFlg;

	private java.lang.String settleType;

	private java.lang.String rateFlag;

	private java.lang.String settleChn;

	private java.lang.String batTime;

	private java.lang.String autoStlFlg;

	private java.lang.String feeType;

	private java.lang.String feeFixed;

	private java.lang.String feeMaxAmt;

	private java.lang.String feeMinAmt;

	private java.lang.String feeRate;

	private java.lang.String feeDiv1;

	private java.lang.String feeDiv2;

	private java.lang.String feeDiv3;

	// new add
	private String otscName;

	private String otscDivRate;

	private String checkFrqc;

	private String marketerName;

	private String marketerContact;

	private java.lang.String contactSnd;

	private java.lang.String commEmailSnd;

	private java.lang.String commMobilSnd;

	private java.lang.String commTelSnd;

	private String mchtPostEmail;
	
	private java.lang.String manager1;
	private java.lang.String artifCertifTp;
	private java.lang.String identityNo;

	// 白名单标志
	private String whiteListFlag;

	// 账户清算方式
	private String acctSettleType;

	// 个人结算账户标志
	private String personSettleFlg;

	// 第二账户信息

	private String acctSettleRate;

	private String acctSettleLimit;

	private java.lang.String settleBankNoSnd;

	private java.lang.String settleBankNmSnd;

	private java.lang.String settleAcctNmSnd;

	private java.lang.String settleAcctSnd;

	// 商户性质1
	private String mchtFlag1;

	// 商户性质2
	private String mchtFlag2;
	
	private String mappingMchntcdOne;
	private String mappingMchntcdTwo;
	private String routingFlag;
	
	//直联商户部分
	private String mcht_no;
	private String inner_acq_inst_id;
	private String acq_inst_id_code;
	private String inst_id;
	private String mcht_nm;
	private String mcht_short_cn ;
	private String area_no;
	private String inner_stlm_ins_id;
	private String mcht_type;
	private String mcht_status;
	private String acq_area_cd;
	private String settle_flg;
	private String conn_inst_cd;
	private String mchnt_tp_grp;
	private String mchnt_srv_tp;
	private String real_mcht_tp;
	private String settle_area_cd;
	private String mcht_acq_curr;
	private String conn_type;
	private String currcy_cd;
	private String deposit_flag;
	private String res_pan_flag;
	private String res_track_flag;
	private String process_flag;
	private String cntry_code;
	private String ch_store_flag;
	private String mcht_tp_reason;
	private String mcc_md;
	private String rebate_flag;
	private String mcht_acq_rebate;
	private String rebate_stlm_cd;
	private String reason_code;
	private String rate_type;
	private String fee_cycle;
	private String fee_type;
	private String limit_flag;
	private String fee_rebate;
	private String settle_amt;
	private String amt_top;
	private String amt_bottom;
	private String disc_cd;
	private String fee_type_m;
	private String limit_flag_m;
	private String settle_amt_m;
	private String amt_top_m;
	private String amt_bottom_m;
	private String disc_cd_m;
	private String fee_rate_type;
	private String settle_bank_no;
	private String settle_bank_type;
	private String advanced_flag;
	private String prior_flag;
	private String open_stlno;
	private String feerate_index;
	private String rate_role;
	private String rate_disc;
	private String cycle_mcht;
	private String mac_chk_flag;
	private String fee_div_mode;
	private String settle_mode;
	private String attr_bmp;
	private String cycle_settle_type;
	private String report_bmp;
	private String day_stlm_flag;
	private String cup_stlm_flag;
	private String adv_ret_flag;
	private String mcht_file_flag;
	private String fee_act;
	private String licence_no;
	private String licence_add;
	private String  principal;
	private String comm_tel;
	private String manager;
	private String mana_cred_tp;
	private String mana_cred_no;
	private String capital_sett_cycle;
	private String card_in_start_date;
	private String settle_acct;
	private String mcht_e_nm;
	private String settle_bank;
	private String rate_no;
	private String card_in_settle_bank;
	private String fee_spe_type;
	private String fee_spe_gra;
	private String reserved1;
	private String reserved2;
	private String upd_opr_id;
	private String upd_ts;
	private String crt_opr_id;
	private String crt_ts;
	private String mchtCup40;
	private String mchtCup401;
	private String mchtCup54;
	private String mchtCup541;
	private String mchtCup542;
	private String mchtCup543;
	private String mchtCup781;
	private String mchtCup782;
	private String mchtCup783;
	private String mchtCup784;
	private String mchtCup785;
	private String mchtCup786;
	private String mchtCup787;
	private String mchtCup82;
	private String mchtCup821;
	private String mchtCup822;
	
	
	
	
	

	public String getMcht_no() {
		return mcht_no;
	}

	public void setMcht_no(String mcht_no) {
		this.mcht_no = mcht_no;
	}

	public String getInner_acq_inst_id() {
		return inner_acq_inst_id;
	}

	public void setInner_acq_inst_id(String inner_acq_inst_id) {
		this.inner_acq_inst_id = inner_acq_inst_id;
	}

	public String getAcq_inst_id_code() {
		return acq_inst_id_code;
	}

	public void setAcq_inst_id_code(String acq_inst_id_code) {
		this.acq_inst_id_code = acq_inst_id_code;
	}

	public String getInst_id() {
		return inst_id;
	}

	public void setInst_id(String inst_id) {
		this.inst_id = inst_id;
	}

	public String getMcht_nm() {
		return mcht_nm;
	}

	public void setMcht_nm(String mcht_nm) {
		this.mcht_nm = mcht_nm;
	}

	public String getMcht_short_cn() {
		return mcht_short_cn;
	}

	public void setMcht_short_cn(String mcht_short_cn) {
		this.mcht_short_cn = mcht_short_cn;
	}

	public String getArea_no() {
		return area_no;
	}

	public void setArea_no(String area_no) {
		this.area_no = area_no;
	}

	public String getInner_stlm_ins_id() {
		return inner_stlm_ins_id;
	}

	public void setInner_stlm_ins_id(String inner_stlm_ins_id) {
		this.inner_stlm_ins_id = inner_stlm_ins_id;
	}

	public String getMcht_type() {
		return mcht_type;
	}

	public void setMcht_type(String mcht_type) {
		this.mcht_type = mcht_type;
	}

	public String getMcht_status() {
		return mcht_status;
	}

	public void setMcht_status(String mcht_status) {
		this.mcht_status = mcht_status;
	}

	public String getAcq_area_cd() {
		return acq_area_cd;
	}

	public void setAcq_area_cd(String acq_area_cd) {
		this.acq_area_cd = acq_area_cd;
	}

	public String getSettle_flg() {
		return settle_flg;
	}

	public void setSettle_flg(String settle_flg) {
		this.settle_flg = settle_flg;
	}

	public String getConn_inst_cd() {
		return conn_inst_cd;
	}

	public void setConn_inst_cd(String conn_inst_cd) {
		this.conn_inst_cd = conn_inst_cd;
	}

	public String getMchnt_tp_grp() {
		return mchnt_tp_grp;
	}

	public void setMchnt_tp_grp(String mchnt_tp_grp) {
		this.mchnt_tp_grp = mchnt_tp_grp;
	}

	public String getMchnt_srv_tp() {
		return mchnt_srv_tp;
	}

	public void setMchnt_srv_tp(String mchnt_srv_tp) {
		this.mchnt_srv_tp = mchnt_srv_tp;
	}

	public String getReal_mcht_tp() {
		return real_mcht_tp;
	}

	public void setReal_mcht_tp(String real_mcht_tp) {
		this.real_mcht_tp = real_mcht_tp;
	}

	public String getSettle_area_cd() {
		return settle_area_cd;
	}

	public void setSettle_area_cd(String settle_area_cd) {
		this.settle_area_cd = settle_area_cd;
	}

	public String getMcht_acq_curr() {
		return mcht_acq_curr;
	}

	public void setMcht_acq_curr(String mcht_acq_curr) {
		this.mcht_acq_curr = mcht_acq_curr;
	}

	public String getConn_type() {
		return conn_type;
	}

	public void setConn_type(String conn_type) {
		this.conn_type = conn_type;
	}

	public String getCurrcy_cd() {
		return currcy_cd;
	}

	public void setCurrcy_cd(String currcy_cd) {
		this.currcy_cd = currcy_cd;
	}

	public String getDeposit_flag() {
		return deposit_flag;
	}

	public void setDeposit_flag(String deposit_flag) {
		this.deposit_flag = deposit_flag;
	}

	public String getRes_pan_flag() {
		return res_pan_flag;
	}

	public void setRes_pan_flag(String res_pan_flag) {
		this.res_pan_flag = res_pan_flag;
	}

	public String getRes_track_flag() {
		return res_track_flag;
	}

	public void setRes_track_flag(String res_track_flag) {
		this.res_track_flag = res_track_flag;
	}

	public String getProcess_flag() {
		return process_flag;
	}

	public void setProcess_flag(String process_flag) {
		this.process_flag = process_flag;
	}

	public String getCntry_code() {
		return cntry_code;
	}

	public void setCntry_code(String cntry_code) {
		this.cntry_code = cntry_code;
	}

	public String getCh_store_flag() {
		return ch_store_flag;
	}

	public void setCh_store_flag(String ch_store_flag) {
		this.ch_store_flag = ch_store_flag;
	}
	
	public java.lang.String getManager1() {
		return manager1;
	}

	public void setManager1(java.lang.String manager1) {
		this.manager1 = manager1;
	}

	public java.lang.String getArtifCertifTp() {
		return artifCertifTp;
	}

	public void setArtifCertifTp(java.lang.String artifCertifTp) {
		this.artifCertifTp = artifCertifTp;
	}

	public java.lang.String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(java.lang.String identityNo) {
		this.identityNo = identityNo;
	}

	public String getMcht_tp_reason() {
		return mcht_tp_reason;
	}

	public void setMcht_tp_reason(String mcht_tp_reason) {
		this.mcht_tp_reason = mcht_tp_reason;
	}

	public String getMcc_md() {
		return mcc_md;
	}

	public void setMcc_md(String mcc_md) {
		this.mcc_md = mcc_md;
	}

	public String getRebate_flag() {
		return rebate_flag;
	}

	public void setRebate_flag(String rebate_flag) {
		this.rebate_flag = rebate_flag;
	}

	public String getMcht_acq_rebate() {
		return mcht_acq_rebate;
	}

	public void setMcht_acq_rebate(String mcht_acq_rebate) {
		this.mcht_acq_rebate = mcht_acq_rebate;
	}

	public String getRebate_stlm_cd() {
		return rebate_stlm_cd;
	}

	public void setRebate_stlm_cd(String rebate_stlm_cd) {
		this.rebate_stlm_cd = rebate_stlm_cd;
	}

	public String getReason_code() {
		return reason_code;
	}

	public void setReason_code(String reason_code) {
		this.reason_code = reason_code;
	}

	public String getRate_type() {
		return rate_type;
	}

	public void setRate_type(String rate_type) {
		this.rate_type = rate_type;
	}

	public String getFee_cycle() {
		return fee_cycle;
	}

	public void setFee_cycle(String fee_cycle) {
		this.fee_cycle = fee_cycle;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getLimit_flag() {
		return limit_flag;
	}

	public void setLimit_flag(String limit_flag) {
		this.limit_flag = limit_flag;
	}

	public String getFee_rebate() {
		return fee_rebate;
	}

	public void setFee_rebate(String fee_rebate) {
		this.fee_rebate = fee_rebate;
	}

	public String getSettle_amt() {
		return settle_amt;
	}

	public void setSettle_amt(String settle_amt) {
		this.settle_amt = settle_amt;
	}

	public String getAmt_top() {
		return amt_top;
	}

	public void setAmt_top(String amt_top) {
		this.amt_top = amt_top;
	}

	public String getAmt_bottom() {
		return amt_bottom;
	}

	public void setAmt_bottom(String amt_bottom) {
		this.amt_bottom = amt_bottom;
	}

	public String getDisc_cd() {
		return disc_cd;
	}

	public void setDisc_cd(String disc_cd) {
		this.disc_cd = disc_cd;
	}

	public String getFee_type_m() {
		return fee_type_m;
	}

	public void setFee_type_m(String fee_type_m) {
		this.fee_type_m = fee_type_m;
	}

	public String getLimit_flag_m() {
		return limit_flag_m;
	}

	public void setLimit_flag_m(String limit_flag_m) {
		this.limit_flag_m = limit_flag_m;
	}

	public String getSettle_amt_m() {
		return settle_amt_m;
	}

	public void setSettle_amt_m(String settle_amt_m) {
		this.settle_amt_m = settle_amt_m;
	}

	public String getAmt_top_m() {
		return amt_top_m;
	}

	public void setAmt_top_m(String amt_top_m) {
		this.amt_top_m = amt_top_m;
	}

	public String getAmt_bottom_m() {
		return amt_bottom_m;
	}

	public void setAmt_bottom_m(String amt_bottom_m) {
		this.amt_bottom_m = amt_bottom_m;
	}

	public String getDisc_cd_m() {
		return disc_cd_m;
	}

	public void setDisc_cd_m(String disc_cd_m) {
		this.disc_cd_m = disc_cd_m;
	}

	public String getFee_rate_type() {
		return fee_rate_type;
	}

	public void setFee_rate_type(String fee_rate_type) {
		this.fee_rate_type = fee_rate_type;
	}

	public String getSettle_bank_no() {
		return settle_bank_no;
	}

	public void setSettle_bank_no(String settle_bank_no) {
		this.settle_bank_no = settle_bank_no;
	}

	public String getSettle_bank_type() {
		return settle_bank_type;
	}

	public void setSettle_bank_type(String settle_bank_type) {
		this.settle_bank_type = settle_bank_type;
	}

	public String getAdvanced_flag() {
		return advanced_flag;
	}

	public void setAdvanced_flag(String advanced_flag) {
		this.advanced_flag = advanced_flag;
	}

	public String getPrior_flag() {
		return prior_flag;
	}

	public void setPrior_flag(String prior_flag) {
		this.prior_flag = prior_flag;
	}

	public String getOpen_stlno() {
		return open_stlno;
	}

	public void setOpen_stlno(String open_stlno) {
		this.open_stlno = open_stlno;
	}

	public String getFeerate_index() {
		return feerate_index;
	}

	public void setFeerate_index(String feerate_index) {
		this.feerate_index = feerate_index;
	}

	public String getRate_role() {
		return rate_role;
	}

	public void setRate_role(String rate_role) {
		this.rate_role = rate_role;
	}

	public String getRate_disc() {
		return rate_disc;
	}

	public void setRate_disc(String rate_disc) {
		this.rate_disc = rate_disc;
	}

	public String getCycle_mcht() {
		return cycle_mcht;
	}

	public void setCycle_mcht(String cycle_mcht) {
		this.cycle_mcht = cycle_mcht;
	}

	public String getMac_chk_flag() {
		return mac_chk_flag;
	}

	public void setMac_chk_flag(String mac_chk_flag) {
		this.mac_chk_flag = mac_chk_flag;
	}

	public String getFee_div_mode() {
		return fee_div_mode;
	}

	public void setFee_div_mode(String fee_div_mode) {
		this.fee_div_mode = fee_div_mode;
	}

	public String getSettle_mode() {
		return settle_mode;
	}

	public void setSettle_mode(String settle_mode) {
		this.settle_mode = settle_mode;
	}

	public String getAttr_bmp() {
		return attr_bmp;
	}

	public void setAttr_bmp(String attr_bmp) {
		this.attr_bmp = attr_bmp;
	}

	public String getCycle_settle_type() {
		return cycle_settle_type;
	}

	public void setCycle_settle_type(String cycle_settle_type) {
		this.cycle_settle_type = cycle_settle_type;
	}

	public String getReport_bmp() {
		return report_bmp;
	}

	public void setReport_bmp(String report_bmp) {
		this.report_bmp = report_bmp;
	}

	public String getDay_stlm_flag() {
		return day_stlm_flag;
	}

	public void setDay_stlm_flag(String day_stlm_flag) {
		this.day_stlm_flag = day_stlm_flag;
	}

	public String getCup_stlm_flag() {
		return cup_stlm_flag;
	}

	public void setCup_stlm_flag(String cup_stlm_flag) {
		this.cup_stlm_flag = cup_stlm_flag;
	}

	public String getAdv_ret_flag() {
		return adv_ret_flag;
	}

	public void setAdv_ret_flag(String adv_ret_flag) {
		this.adv_ret_flag = adv_ret_flag;
	}

	public String getMcht_file_flag() {
		return mcht_file_flag;
	}

	public void setMcht_file_flag(String mcht_file_flag) {
		this.mcht_file_flag = mcht_file_flag;
	}

	public String getFee_act() {
		return fee_act;
	}

	public void setFee_act(String fee_act) {
		this.fee_act = fee_act;
	}

	public String getLicence_no() {
		return licence_no;
	}

	public void setLicence_no(String licence_no) {
		this.licence_no = licence_no;
	}

	public String getLicence_add() {
		return licence_add;
	}

	public void setLicence_add(String licence_add) {
		this.licence_add = licence_add;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getComm_tel() {
		return comm_tel;
	}

	public void setComm_tel(String comm_tel) {
		this.comm_tel = comm_tel;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getMana_cred_tp() {
		return mana_cred_tp;
	}

	public void setMana_cred_tp(String mana_cred_tp) {
		this.mana_cred_tp = mana_cred_tp;
	}

	public String getMana_cred_no() {
		return mana_cred_no;
	}

	public void setMana_cred_no(String mana_cred_no) {
		this.mana_cred_no = mana_cred_no;
	}

	public String getCapital_sett_cycle() {
		return capital_sett_cycle;
	}

	public void setCapital_sett_cycle(String capital_sett_cycle) {
		this.capital_sett_cycle = capital_sett_cycle;
	}

	public String getCard_in_start_date() {
		return card_in_start_date;
	}

	public void setCard_in_start_date(String card_in_start_date) {
		this.card_in_start_date = card_in_start_date;
	}

	public String getSettle_acct() {
		return settle_acct;
	}

	public void setSettle_acct(String settle_acct) {
		this.settle_acct = settle_acct;
	}

	public String getMcht_e_nm() {
		return mcht_e_nm;
	}

	public void setMcht_e_nm(String mcht_e_nm) {
		this.mcht_e_nm = mcht_e_nm;
	}

	public String getSettle_bank() {
		return settle_bank;
	}

	public void setSettle_bank(String settle_bank) {
		this.settle_bank = settle_bank;
	}

	public String getRate_no() {
		return rate_no;
	}

	public void setRate_no(String rate_no) {
		this.rate_no = rate_no;
	}

	public String getCard_in_settle_bank() {
		return card_in_settle_bank;
	}

	public void setCard_in_settle_bank(String card_in_settle_bank) {
		this.card_in_settle_bank = card_in_settle_bank;
	}

	public String getFee_spe_type() {
		return fee_spe_type;
	}

	public void setFee_spe_type(String fee_spe_type) {
		this.fee_spe_type = fee_spe_type;
	}

	public String getFee_spe_gra() {
		return fee_spe_gra;
	}

	public void setFee_spe_gra(String fee_spe_gra) {
		this.fee_spe_gra = fee_spe_gra;
	}

	public String getReserved1() {
		return reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public String getUpd_opr_id() {
		return upd_opr_id;
	}

	public void setUpd_opr_id(String upd_opr_id) {
		this.upd_opr_id = upd_opr_id;
	}

	public String getUpd_ts() {
		return upd_ts;
	}

	public void setUpd_ts(String upd_ts) {
		this.upd_ts = upd_ts;
	}

	public String getCrt_opr_id() {
		return crt_opr_id;
	}

	public void setCrt_opr_id(String crt_opr_id) {
		this.crt_opr_id = crt_opr_id;
	}

	public String getCrt_ts() {
		return crt_ts;
	}

	public void setCrt_ts(String crt_ts) {
		this.crt_ts = crt_ts;
	}

	public String getMchtCup40() {
		return mchtCup40;
	}

	public void setMchtCup40(String mchtCup40) {
		this.mchtCup40 = mchtCup40;
	}

	public String getMchtCup401() {
		return mchtCup401;
	}

	public void setMchtCup401(String mchtCup401) {
		this.mchtCup401 = mchtCup401;
	}

	public String getMchtCup54() {
		return mchtCup54;
	}

	public void setMchtCup54(String mchtCup54) {
		this.mchtCup54 = mchtCup54;
	}

	public String getMchtCup541() {
		return mchtCup541;
	}

	public void setMchtCup541(String mchtCup541) {
		this.mchtCup541 = mchtCup541;
	}

	public String getMchtCup542() {
		return mchtCup542;
	}

	public void setMchtCup542(String mchtCup542) {
		this.mchtCup542 = mchtCup542;
	}

	public String getMchtCup543() {
		return mchtCup543;
	}

	public void setMchtCup543(String mchtCup543) {
		this.mchtCup543 = mchtCup543;
	}

	public String getMchtCup781() {
		return mchtCup781;
	}

	public void setMchtCup781(String mchtCup781) {
		this.mchtCup781 = mchtCup781;
	}

	public String getMchtCup782() {
		return mchtCup782;
	}

	public void setMchtCup782(String mchtCup782) {
		this.mchtCup782 = mchtCup782;
	}

	public String getMchtCup783() {
		return mchtCup783;
	}

	public void setMchtCup783(String mchtCup783) {
		this.mchtCup783 = mchtCup783;
	}

	public String getMchtCup784() {
		return mchtCup784;
	}

	public void setMchtCup784(String mchtCup784) {
		this.mchtCup784 = mchtCup784;
	}

	public String getMchtCup785() {
		return mchtCup785;
	}

	public void setMchtCup785(String mchtCup785) {
		this.mchtCup785 = mchtCup785;
	}

	public String getMchtCup786() {
		return mchtCup786;
	}

	public void setMchtCup786(String mchtCup786) {
		this.mchtCup786 = mchtCup786;
	}

	public String getMchtCup787() {
		return mchtCup787;
	}

	public void setMchtCup787(String mchtCup787) {
		this.mchtCup787 = mchtCup787;
	}

	public String getMchtCup82() {
		return mchtCup82;
	}

	public void setMchtCup82(String mchtCup82) {
		this.mchtCup82 = mchtCup82;
	}

	public String getMchtCup821() {
		return mchtCup821;
	}

	public void setMchtCup821(String mchtCup821) {
		this.mchtCup821 = mchtCup821;
	}

	public String getMchtCup822() {
		return mchtCup822;
	}

	public void setMchtCup822(String mchtCup822) {
		this.mchtCup822 = mchtCup822;
	}

	public String getMchtFlag1() {
		return mchtFlag1;
	}

	public void setMchtFlag1(String mchtFlag1) {
		this.mchtFlag1 = mchtFlag1;
	}

	public String getMchtFlag2() {
		return mchtFlag2;
	}

	public void setMchtFlag2(String mchtFlag2) {
		this.mchtFlag2 = mchtFlag2;
	}

	public String getPersonSettleFlg() {
		return personSettleFlg;
	}

	public void setPersonSettleFlg(String personSettleFlg) {
		this.personSettleFlg = personSettleFlg;
	}

	public String getAcctSettleRate() {
		return acctSettleRate;
	}

	public void setAcctSettleRate(String acctSettleRate) {
		this.acctSettleRate = acctSettleRate;
	}

	public String getAcctSettleLimit() {
		return acctSettleLimit;
	}

	public void setAcctSettleLimit(String acctSettleLimit) {
		this.acctSettleLimit = acctSettleLimit;
	}

	public String getAcctSettleType() {
		return acctSettleType;
	}

	public void setAcctSettleType(String acctSettleType) {
		this.acctSettleType = acctSettleType;
	}

	public java.lang.String getSettleBankNoSnd() {
		return settleBankNoSnd;
	}

	public void setSettleBankNoSnd(java.lang.String settleBankNoSnd) {
		this.settleBankNoSnd = settleBankNoSnd;
	}

	public java.lang.String getSettleBankNmSnd() {
		return settleBankNmSnd;
	}

	public void setSettleBankNmSnd(java.lang.String settleBankNmSnd) {
		this.settleBankNmSnd = settleBankNmSnd;
	}

	public java.lang.String getSettleAcctNmSnd() {
		return settleAcctNmSnd;
	}

	public void setSettleAcctNmSnd(java.lang.String settleAcctNmSnd) {
		this.settleAcctNmSnd = settleAcctNmSnd;
	}

	public java.lang.String getSettleAcctSnd() {
		return settleAcctSnd;
	}

	public void setSettleAcctSnd(java.lang.String settleAcctSnd) {
		this.settleAcctSnd = settleAcctSnd;
	}

	public TblMchntService getService() {
		return service;
	}

	public void setService(TblMchntService service) {
		this.service = service;
	}

	public java.lang.String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(java.lang.String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public java.lang.String getMchtNm() {
		return mchtNm;
	}

	public void setMchtNm(java.lang.String mchtNm) {
		this.mchtNm = mchtNm;
	}

	public java.lang.String getRislLvl() {
		return rislLvl;
	}

	public void setRislLvl(java.lang.String rislLvl) {
		this.rislLvl = rislLvl;
	}

	public java.lang.String getMchtLvl() {
		return mchtLvl;
	}

	public void setMchtLvl(java.lang.String mchtLvl) {
		this.mchtLvl = mchtLvl;
	}

	public java.lang.String getMchtStatus() {
		return mchtStatus;
	}

	public void setMchtStatus(java.lang.String mchtStatus) {
		this.mchtStatus = mchtStatus;
	}

	public java.lang.String getManuAuthFlag() {
		return manuAuthFlag;
	}

	public void setManuAuthFlag(java.lang.String manuAuthFlag) {
		this.manuAuthFlag = manuAuthFlag;
	}

	public java.lang.String getDiscConsFlg() {
		return discConsFlg;
	}

	public void setDiscConsFlg(java.lang.String discConsFlg) {
		this.discConsFlg = discConsFlg;
	}

	public java.lang.String getDiscConsRebate() {
		return discConsRebate;
	}

	public void setDiscConsRebate(java.lang.String discConsRebate) {
		this.discConsRebate = discConsRebate;
	}

	public java.lang.String getPassFlag() {
		return passFlag;
	}

	public void setPassFlag(java.lang.String passFlag) {
		this.passFlag = passFlag;
	}

	public java.lang.String getOpenDays() {
		return openDays;
	}

	public void setOpenDays(java.lang.String openDays) {
		this.openDays = openDays;
	}

	public java.lang.String getSleepDays() {
		return sleepDays;
	}

	public void setSleepDays(java.lang.String sleepDays) {
		this.sleepDays = sleepDays;
	}

	public java.lang.String getMchtCnAbbr() {
		return mchtCnAbbr;
	}

	public void setMchtCnAbbr(java.lang.String mchtCnAbbr) {
		this.mchtCnAbbr = mchtCnAbbr;
	}

	public java.lang.String getSpellName() {
		return spellName;
	}

	public void setSpellName(java.lang.String spellName) {
		this.spellName = spellName;
	}

	public java.lang.String getEngName() {
		return engName;
	}

	public void setEngName(java.lang.String engName) {
		this.engName = engName;
	}

	public java.lang.String getMchtEnAbbr() {
		return mchtEnAbbr;
	}

	public void setMchtEnAbbr(java.lang.String mchtEnAbbr) {
		this.mchtEnAbbr = mchtEnAbbr;
	}

	public java.lang.String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(java.lang.String areaNo) {
		this.areaNo = areaNo;
	}

	public java.lang.String getSettleAreaNo() {
		return settleAreaNo;
	}

	public void setSettleAreaNo(java.lang.String settleAreaNo) {
		this.settleAreaNo = settleAreaNo;
	}

	public java.lang.String getAddr() {
		return addr;
	}

	public void setAddr(java.lang.String addr) {
		this.addr = addr;
	}

	public java.lang.String getHomePage() {
		return homePage;
	}

	public void setHomePage(java.lang.String homePage) {
		this.homePage = homePage;
	}

	public java.lang.String getMcc() {
		return mcc;
	}

	public void setMcc(java.lang.String mcc) {
		this.mcc = mcc;
	}

	public java.lang.String getTcc() {
		return tcc;
	}

	public void setTcc(java.lang.String tcc) {
		this.tcc = tcc;
	}

	// public java.lang.String getEtpsAttr() {
	// return etpsAttr;
	// }
	//
	// public void setEtpsAttr(java.lang.String etpsAttr) {
	// this.etpsAttr = etpsAttr;
	// }

	public java.lang.String getMngMchtId() {
		return mngMchtId;
	}

	public void setMngMchtId(java.lang.String mngMchtId) {
		this.mngMchtId = mngMchtId;
	}

	public java.lang.String getMchtGrp() {
		return mchtGrp;
	}

	public void setMchtGrp(java.lang.String mchtGrp) {
		this.mchtGrp = mchtGrp;
	}

	public java.lang.String getMchtAttr() {
		return mchtAttr;
	}

	public void setMchtAttr(java.lang.String mchtAttr) {
		this.mchtAttr = mchtAttr;
	}

	public java.lang.String getMchtGroupFlag() {
		return mchtGroupFlag;
	}

	public void setMchtGroupFlag(java.lang.String mchtGroupFlag) {
		this.mchtGroupFlag = mchtGroupFlag;
	}

	public java.lang.String getMchtGroupId() {
		return mchtGroupId;
	}

	public void setMchtGroupId(java.lang.String mchtGroupId) {
		this.mchtGroupId = mchtGroupId;
	}

	public java.lang.String getMchtEngNm() {
		return mchtEngNm;
	}

	public void setMchtEngNm(java.lang.String mchtEngNm) {
		this.mchtEngNm = mchtEngNm;
	}

	public java.lang.String getMchtEngAddr() {
		return mchtEngAddr;
	}

	public void setMchtEngAddr(java.lang.String mchtEngAddr) {
		this.mchtEngAddr = mchtEngAddr;
	}

	public java.lang.String getMchtEngCityName() {
		return mchtEngCityName;
	}

	public void setMchtEngCityName(java.lang.String mchtEngCityName) {
		this.mchtEngCityName = mchtEngCityName;
	}

	public java.lang.String getSaLimitAmt() {
		return saLimitAmt;
	}

	public void setSaLimitAmt(java.lang.String saLimitAmt) {
		this.saLimitAmt = saLimitAmt;
	}

	public java.lang.String getSaAction() {
		return saAction;
	}

	public void setSaAction(java.lang.String saAction) {
		this.saAction = saAction;
	}

	public java.lang.String getPsamNum() {
		return psamNum;
	}

	public void setPsamNum(java.lang.String psamNum) {
		this.psamNum = psamNum;
	}

	public java.lang.String getCdMacNum() {
		return cdMacNum;
	}

	public void setCdMacNum(java.lang.String cdMacNum) {
		this.cdMacNum = cdMacNum;
	}

	public java.lang.String getPosNum() {
		return posNum;
	}

	public void setPosNum(java.lang.String posNum) {
		this.posNum = posNum;
	}

	public java.lang.String getConnType() {
		return connType;
	}

	public void setConnType(java.lang.String connType) {
		this.connType = connType;
	}

	public java.lang.String getMchtMngMode() {
		return mchtMngMode;
	}

	public void setMchtMngMode(java.lang.String mchtMngMode) {
		this.mchtMngMode = mchtMngMode;
	}

	public java.lang.String getMchtFunction() {
		return mchtFunction;
	}

	public void setMchtFunction(java.lang.String mchtFunction) {
		this.mchtFunction = mchtFunction;
	}

	public java.lang.String getLicenceNo() {
		return licenceNo;
	}

	public void setLicenceNo(java.lang.String licenceNo) {
		this.licenceNo = licenceNo;
	}

	public java.lang.String getBusType() {
		return busType;
	}

	public void setBusType(java.lang.String busType) {
		this.busType = busType;
	}

	public java.lang.String getContact() {
		return contact;
	}

	public void setContact(java.lang.String contact) {
		this.contact = contact;
	}

	public java.lang.String getPostCode() {
		return postCode;
	}

	public void setPostCode(java.lang.String postCode) {
		this.postCode = postCode;
	}

	public java.lang.String getCommEmail() {
		return commEmail;
	}

	public void setCommEmail(java.lang.String commEmail) {
		this.commEmail = commEmail;
	}

	public java.lang.String getCommMobil() {
		return commMobil;
	}

	public void setCommMobil(java.lang.String commMobil) {
		this.commMobil = commMobil;
	}

	public java.lang.String getCommTel() {
		return commTel;
	}

	public void setCommTel(java.lang.String commTel) {
		this.commTel = commTel;
	}

	public java.lang.String getElectrofax() {
		return electrofax;
	}

	public void setElectrofax(java.lang.String electrofax) {
		this.electrofax = electrofax;
	}

	public java.lang.String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(java.lang.String applyDate) {
		this.applyDate = applyDate;
	}

	public java.lang.String getEnableDate() {
		return enableDate;
	}

	public void setEnableDate(java.lang.String enableDate) {
		this.enableDate = enableDate;
	}

	public java.lang.String getPreAudNm() {
		return preAudNm;
	}

	public void setPreAudNm(java.lang.String preAudNm) {
		this.preAudNm = preAudNm;
	}

	public java.lang.String getConfirmNm() {
		return confirmNm;
	}

	public void setConfirmNm(java.lang.String confirmNm) {
		this.confirmNm = confirmNm;
	}

	public java.lang.String getProtocalId() {
		return protocalId;
	}

	public void setProtocalId(java.lang.String protocalId) {
		this.protocalId = protocalId;
	}

	public java.lang.String getSignInstId() {
		return signInstId;
	}

	public void setSignInstId(java.lang.String signInstId) {
		this.signInstId = signInstId;
	}

	public java.lang.String getNetNm() {
		return netNm;
	}

	public void setNetNm(java.lang.String netNm) {
		this.netNm = netNm;
	}

	public java.lang.String getAgrBr() {
		return agrBr;
	}

	public void setAgrBr(java.lang.String agrBr) {
		this.agrBr = agrBr;
	}

	public java.lang.String getNetTel() {
		return netTel;
	}

	public void setNetTel(java.lang.String netTel) {
		this.netTel = netTel;
	}

	public java.lang.String getProlDate() {
		return prolDate;
	}

	public void setProlDate(java.lang.String prolDate) {
		this.prolDate = prolDate;
	}

	public java.lang.String getProlTlr() {
		return prolTlr;
	}

	public void setProlTlr(java.lang.String prolTlr) {
		this.prolTlr = prolTlr;
	}

	public java.lang.String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(java.lang.String closeDate) {
		this.closeDate = closeDate;
	}

	public java.lang.String getCloseTlr() {
		return closeTlr;
	}

	public void setCloseTlr(java.lang.String closeTlr) {
		this.closeTlr = closeTlr;
	}

	public java.lang.String getMainTlr() {
		return mainTlr;
	}

	public void setMainTlr(java.lang.String mainTlr) {
		this.mainTlr = mainTlr;
	}

	public java.lang.String getCheckTlr() {
		return checkTlr;
	}

	public void setCheckTlr(java.lang.String checkTlr) {
		this.checkTlr = checkTlr;
	}

	public java.lang.String getOperNo() {
		return operNo;
	}

	public void setOperNo(java.lang.String operNo) {
		this.operNo = operNo;
	}

	public java.lang.String getOperNm() {
		return operNm;
	}

	public void setOperNm(java.lang.String operNm) {
		this.operNm = operNm;
	}

	public java.lang.String getProcFlag() {
		return procFlag;
	}

	public void setProcFlag(java.lang.String procFlag) {
		this.procFlag = procFlag;
	}

	public java.lang.String getSetCur() {
		return setCur;
	}

	public void setSetCur(java.lang.String setCur) {
		this.setCur = setCur;
	}

	public java.lang.String getPrintInstId() {
		return printInstId;
	}

	public void setPrintInstId(java.lang.String printInstId) {
		this.printInstId = printInstId;
	}

	public java.lang.String getAcqInstId() {
		return acqInstId;
	}

	public void setAcqInstId(java.lang.String acqInstId) {
		this.acqInstId = acqInstId;
	}

	public java.lang.String getAcqBkName() {
		return acqBkName;
	}

	public void setAcqBkName(java.lang.String acqBkName) {
		this.acqBkName = acqBkName;
	}

	public java.lang.String getBankNo() {
		return bankNo;
	}

	public void setBankNo(java.lang.String bankNo) {
		this.bankNo = bankNo;
	}

	public java.lang.String getOrgnNo() {
		return orgnNo;
	}

	public void setOrgnNo(java.lang.String orgnNo) {
		this.orgnNo = orgnNo;
	}

	public java.lang.String getSubbrhNo() {
		return subbrhNo;
	}

	public void setSubbrhNo(java.lang.String subbrhNo) {
		this.subbrhNo = subbrhNo;
	}

	public java.lang.String getSubbrhNm() {
		return subbrhNm;
	}

	public void setSubbrhNm(java.lang.String subbrhNm) {
		this.subbrhNm = subbrhNm;
	}

	public java.lang.String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(java.lang.String openTime) {
		this.openTime = openTime;
	}

	public java.lang.String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(java.lang.String closeTime) {
		this.closeTime = closeTime;
	}

	public java.lang.String getVisActFlg() {
		return visActFlg;
	}

	public void setVisActFlg(java.lang.String visActFlg) {
		this.visActFlg = visActFlg;
	}

	public java.lang.String getVisMchtId() {
		return visMchtId;
	}

	public void setVisMchtId(java.lang.String visMchtId) {
		this.visMchtId = visMchtId;
	}

	public java.lang.String getMstActFlg() {
		return mstActFlg;
	}

	public void setMstActFlg(java.lang.String mstActFlg) {
		this.mstActFlg = mstActFlg;
	}

	public java.lang.String getMstMchtId() {
		return mstMchtId;
	}

	public void setMstMchtId(java.lang.String mstMchtId) {
		this.mstMchtId = mstMchtId;
	}

	public java.lang.String getAmxActFlg() {
		return amxActFlg;
	}

	public void setAmxActFlg(java.lang.String amxActFlg) {
		this.amxActFlg = amxActFlg;
	}

	public java.lang.String getAmxMchtId() {
		return amxMchtId;
	}

	public void setAmxMchtId(java.lang.String amxMchtId) {
		this.amxMchtId = amxMchtId;
	}

	public java.lang.String getDnrActFlg() {
		return dnrActFlg;
	}

	public void setDnrActFlg(java.lang.String dnrActFlg) {
		this.dnrActFlg = dnrActFlg;
	}

	public java.lang.String getDnrMchtId() {
		return dnrMchtId;
	}

	public void setDnrMchtId(java.lang.String dnrMchtId) {
		this.dnrMchtId = dnrMchtId;
	}

	public java.lang.String getJcbActFlg() {
		return jcbActFlg;
	}

	public void setJcbActFlg(java.lang.String jcbActFlg) {
		this.jcbActFlg = jcbActFlg;
	}

	public java.lang.String getJcbMchtId() {
		return jcbMchtId;
	}

	public void setJcbMchtId(java.lang.String jcbMchtId) {
		this.jcbMchtId = jcbMchtId;
	}

	public java.lang.String getCupMchtFlg() {
		return cupMchtFlg;
	}

	public void setCupMchtFlg(java.lang.String cupMchtFlg) {
		this.cupMchtFlg = cupMchtFlg;
	}

	public java.lang.String getDebMchtFlg() {
		return debMchtFlg;
	}

	public void setDebMchtFlg(java.lang.String debMchtFlg) {
		this.debMchtFlg = debMchtFlg;
	}

	public java.lang.String getCreMchtFlg() {
		return creMchtFlg;
	}

	public void setCreMchtFlg(java.lang.String creMchtFlg) {
		this.creMchtFlg = creMchtFlg;
	}

	public java.lang.String getCdcMchtFlg() {
		return cdcMchtFlg;
	}

	public void setCdcMchtFlg(java.lang.String cdcMchtFlg) {
		this.cdcMchtFlg = cdcMchtFlg;
	}

	public java.lang.String getSettleType() {
		return settleType;
	}

	public void setSettleType(java.lang.String settleType) {
		this.settleType = settleType;
	}

	public java.lang.String getRateFlag() {
		return rateFlag;
	}

	public void setRateFlag(java.lang.String rateFlag) {
		this.rateFlag = rateFlag;
	}

	public java.lang.String getSettleChn() {
		return settleChn;
	}

	public void setSettleChn(java.lang.String settleChn) {
		this.settleChn = settleChn;
	}

	public java.lang.String getBatTime() {
		return batTime;
	}

	public void setBatTime(java.lang.String batTime) {
		this.batTime = batTime;
	}

	public java.lang.String getAutoStlFlg() {
		return autoStlFlg;
	}

	public void setAutoStlFlg(java.lang.String autoStlFlg) {
		this.autoStlFlg = autoStlFlg;
	}

	public java.lang.String getPartNum() {
		return partNum;
	}

	public void setPartNum(java.lang.String partNum) {
		this.partNum = partNum;
	}

	public java.lang.String getFeeType() {
		return feeType;
	}

	public void setFeeType(java.lang.String feeType) {
		this.feeType = feeType;
	}

	public java.lang.String getFeeFixed() {
		return feeFixed;
	}

	public void setFeeFixed(java.lang.String feeFixed) {
		this.feeFixed = feeFixed;
	}

	public java.lang.String getFeeMaxAmt() {
		return feeMaxAmt;
	}

	public void setFeeMaxAmt(java.lang.String feeMaxAmt) {
		this.feeMaxAmt = feeMaxAmt;
	}

	public java.lang.String getFeeMinAmt() {
		return feeMinAmt;
	}

	public void setFeeMinAmt(java.lang.String feeMinAmt) {
		this.feeMinAmt = feeMinAmt;
	}

	public java.lang.String getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(java.lang.String feeRate) {
		this.feeRate = feeRate;
	}

	public java.lang.String getFeeDiv1() {
		return feeDiv1;
	}

	public void setFeeDiv1(java.lang.String feeDiv1) {
		this.feeDiv1 = feeDiv1;
	}

	public java.lang.String getFeeDiv2() {
		return feeDiv2;
	}

	public void setFeeDiv2(java.lang.String feeDiv2) {
		this.feeDiv2 = feeDiv2;
	}

	public java.lang.String getFeeDiv3() {
		return feeDiv3;
	}

	public void setFeeDiv3(java.lang.String feeDiv3) {
		this.feeDiv3 = feeDiv3;
	}

	public java.lang.String getSettleMode() {
		return settleMode;
	}

	public void setSettleMode(java.lang.String settleMode) {
		this.settleMode = settleMode;
	}

	public java.lang.String getFeeCycle() {
		return feeCycle;
	}

	public void setFeeCycle(java.lang.String feeCycle) {
		this.feeCycle = feeCycle;
	}

	public java.lang.String getSettleRpt() {
		return settleRpt;
	}

	public void setSettleRpt(java.lang.String settleRpt) {
		this.settleRpt = settleRpt;
	}

	public java.lang.String getSettleBankNo() {
		return settleBankNo;
	}

	public void setSettleBankNo(java.lang.String settleBankNo) {
		this.settleBankNo = settleBankNo;
	}

	public java.lang.String getSettleBankNm() {
		return settleBankNm;
	}

	public void setSettleBankNm(java.lang.String settleBankNm) {
		this.settleBankNm = settleBankNm;
	}

	public java.lang.String getSettleAcctNm() {
		return settleAcctNm;
	}

	public void setSettleAcctNm(java.lang.String settleAcctNm) {
		this.settleAcctNm = settleAcctNm;
	}

	public java.lang.String getSettleAcct() {
		return settleAcct;
	}

	public void setSettleAcct(java.lang.String settleAcct) {
		this.settleAcct = settleAcct;
	}

	public java.lang.String getFeeAcctNm() {
		return feeAcctNm;
	}

	public void setFeeAcctNm(java.lang.String feeAcctNm) {
		this.feeAcctNm = feeAcctNm;
	}

	public java.lang.String getFeeAcct() {
		return feeAcct;
	}

	public void setFeeAcct(java.lang.String feeAcct) {
		this.feeAcct = feeAcct;
	}

	public java.lang.String getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(java.lang.String groupFlag) {
		this.groupFlag = groupFlag;
	}

	public java.lang.String getOpenStlno() {
		return openStlno;
	}

	public void setOpenStlno(java.lang.String openStlno) {
		this.openStlno = openStlno;
	}

	public java.lang.String getChangeStlno() {
		return changeStlno;
	}

	public void setChangeStlno(java.lang.String changeStlno) {
		this.changeStlno = changeStlno;
	}

	public java.lang.String getReserved() {
		return reserved;
	}

	public void setReserved(java.lang.String reserved) {
		this.reserved = reserved;
	}

	/**
	 * @return the settleAcctNm2
	 */
	public java.lang.String getSettleAcctNm2() {
		return settleAcctNm2;
	}

	/**
	 * @param settleAcctNm2
	 *            the settleAcctNm2 to set
	 */
	public void setSettleAcctNm2(java.lang.String settleAcctNm2) {
		this.settleAcctNm2 = settleAcctNm2;
	}

	/**
	 * @return the settleAcct2
	 */
	public java.lang.String getSettleAcct2() {
		return settleAcct2;
	}

	/**
	 * @param settleAcct2
	 *            the settleAcct2 to set
	 */
	public void setSettleAcct2(java.lang.String settleAcct2) {
		this.settleAcct2 = settleAcct2;
	}

	private java.lang.String settleMode;

	private java.lang.String feeCycle;

	private java.lang.String settleRpt;

	private java.lang.String settleBankNo;

	private java.lang.String settleBankNm;

	private java.lang.String settleAcctNm;

	private java.lang.String settleAcct;

	private java.lang.String settleAcctNm2;

	private java.lang.String settleAcct2;

	private java.lang.String feeAcctNm;

	private java.lang.String feeAcct;

	private java.lang.String groupFlag;

	private java.lang.String openStlno;

	private java.lang.String changeStlno;

	private java.lang.String reserved;

	// 计费代码
	private String discCode;

	public String getDiscCode() {
		return discCode;
	}

	public void setDiscCode(String discCode) {
		this.discCode = discCode;
	}

	private String mchtNoBySelf;

	private String idOtherNo;

	private String feeTypeFlag;

	private String feeSelfGDName;

	private String feeSelfBLName;

	private String feeMost;

	private String feeLeast;

	// 账户一结算类型
	private String clearType;

	// 第二账户结算类型
	private String clearType2;

	public String getClearType2() {
		return clearType2;
	}

	public void setClearType2(String clearType2) {
		this.clearType2 = clearType2;
	}

	public String getClearType() {
		return clearType;
	}

	public void setClearType(String clearType) {
		this.clearType = clearType;
	}

	public String getFeeSelfBLName() {
		return feeSelfBLName;
	}

	public void setFeeSelfBLName(String feeSelfBLName) {
		this.feeSelfBLName = feeSelfBLName;
	}

	public String getFeeMost() {
		return feeMost;
	}

	public void setFeeMost(String feeMost) {
		this.feeMost = feeMost;
	}

	public String getFeeLeast() {
		return feeLeast;
	}

	public void setFeeLeast(String feeLeast) {
		this.feeLeast = feeLeast;
	}

	public String getFeeSelfGDName() {
		return feeSelfGDName;
	}

	public void setFeeSelfGDName(String feeSelfGDName) {
		this.feeSelfGDName = feeSelfGDName;
	}

	public String getFeeTypeFlag() {
		return feeTypeFlag;
	}

	public void setFeeTypeFlag(String feeTypeFlag) {
		this.feeTypeFlag = feeTypeFlag;
	}

	public void setIdOtherNo(String idOtherNo) {
		this.idOtherNo = idOtherNo;
	}

	public String getIdOtherNo() {
		return idOtherNo;
	}

	public String getMchtNoBySelf() {
		return mchtNoBySelf;
	}

	public void setMchtNoBySelf(String mchtNoBySelf) {
		this.mchtNoBySelf = mchtNoBySelf;
	}

	public String checkIds;

	/**
	 * @return the checkIds
	 */
	public String getCheckIds() {
		return checkIds;
	}

	/**
	 * @param checkIds
	 *            the checkIds to set
	 */
	public void setCheckIds(String checkIds) {
		this.checkIds = checkIds;
	}

	/**
	 * @return the otscName
	 */
	public String getOtscName() {
		return otscName;
	}

	/**
	 * @param otscName
	 *            the otscName to set
	 */
	public void setOtscName(String otscName) {
		this.otscName = otscName;
	}

	/**
	 * @return the otscDivRate
	 */
	public String getOtscDivRate() {
		return otscDivRate;
	}

	/**
	 * @param otscDivRate
	 *            the otscDivRate to set
	 */
	public void setOtscDivRate(String otscDivRate) {
		this.otscDivRate = otscDivRate;
	}

	/**
	 * @return the checkFrqc
	 */
	public String getCheckFrqc() {
		return checkFrqc;
	}

	/**
	 * @param checkFrqc
	 *            the checkFrqc to set
	 */
	public void setCheckFrqc(String checkFrqc) {
		this.checkFrqc = checkFrqc;
	}

	/**
	 * @return the marketerName
	 */
	public String getMarketerName() {
		return marketerName;
	}

	/**
	 * @param marketerName
	 *            the marketerName to set
	 */
	public void setMarketerName(String marketerName) {
		this.marketerName = marketerName;
	}

	/**
	 * @return the marketerContact
	 */
	public String getMarketerContact() {
		return marketerContact;
	}

	/**
	 * @param marketerContact
	 *            the marketerContact to set
	 */
	public void setMarketerContact(String marketerContact) {
		this.marketerContact = marketerContact;
	}

	/**
	 * @return the contactSnd
	 */
	public java.lang.String getContactSnd() {
		return contactSnd;
	}

	/**
	 * @param contactSnd
	 *            the contactSnd to set
	 */
	public void setContactSnd(java.lang.String contactSnd) {
		this.contactSnd = contactSnd;
	}

	/**
	 * @return the commEmailSnd
	 */
	public java.lang.String getCommEmailSnd() {
		return commEmailSnd;
	}

	/**
	 * @param commEmailSnd
	 *            the commEmailSnd to set
	 */
	public void setCommEmailSnd(java.lang.String commEmailSnd) {
		this.commEmailSnd = commEmailSnd;
	}

	/**
	 * @return the commMobilSnd
	 */
	public java.lang.String getCommMobilSnd() {
		return commMobilSnd;
	}

	/**
	 * @param commMobilSnd
	 *            the commMobilSnd to set
	 */
	public void setCommMobilSnd(java.lang.String commMobilSnd) {
		this.commMobilSnd = commMobilSnd;
	}

	/**
	 * @return the commTelSnd
	 */
	public java.lang.String getCommTelSnd() {
		return commTelSnd;
	}

	/**
	 * @param commTelSnd
	 *            the commTelSnd to set
	 */
	public void setCommTelSnd(java.lang.String commTelSnd) {
		this.commTelSnd = commTelSnd;
	}

	/**
	 * @return the whiteListFlag
	 */
	public String getWhiteListFlag() {
		return whiteListFlag;
	}

	/**
	 * @param whiteListFlag
	 *            the whiteListFlag to set
	 */
	public void setWhiteListFlag(String whiteListFlag) {
		this.whiteListFlag = whiteListFlag;
	}

	/**
	 * @return the mchtPostEmail
	 */
	public String getMchtPostEmail() {
		return mchtPostEmail;
	}

	/**
	 * @param mchtPostEmail
	 *            the mchtPostEmail to set
	 */
	public void setMchtPostEmail(String mchtPostEmail) {
		this.mchtPostEmail = mchtPostEmail;
	}

	public String getMappingMchntcdOne() {
		return mappingMchntcdOne;
	}

	public void setMappingMchntcdOne(String mappingMchntcdOne) {
		this.mappingMchntcdOne = mappingMchntcdOne;
	}

	public String getMappingMchntcdTwo() {
		return mappingMchntcdTwo;
	}

	public void setMappingMchntcdTwo(String mappingMchntcdTwo) {
		this.mappingMchntcdTwo = mappingMchntcdTwo;
	}

	public String getRoutingFlag() {
		return routingFlag;
	}

	public void setRoutingFlag(String routingFlag) {
		this.routingFlag = routingFlag;
	}



}
