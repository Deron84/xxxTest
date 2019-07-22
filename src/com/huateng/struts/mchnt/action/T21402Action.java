package com.huateng.struts.mchnt.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.huateng.bo.mchnt.T21402BO;
import com.huateng.bo.mchnt.T21403BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.po.mchnt.TblMchntDiscountRule;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.CommonStatus;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.InformationUtil;

/**
 * 优惠规则
 * @author Administrator
 *
 */
public class T21402Action extends BaseAction{
	 private static Logger logger = Logger.getLogger(T21402Action.class);
	T21402BO t21402BO = (T21402BO) ContextUtil.getBean("T21402BO");
	private String discountId;
	private String discountType;
	private String discountCard;		
	private String discountm;
	private String discountj;
	private String discountzk;
	private String discountAny;
	private Date startTimeEff;
	private Date endTimeEff;
	private String discountCode;
	private String openType;//开启方式
	private String acquirersType;//折扣类型(下发的01折扣 02满减 03联机)
	private String openLian="01";//开启联机优惠
	private String isDownload="00";//是否已下载
	private String remark;//备注
	private String acquirersId;
	//
	private String sumcount0;//脱机按卡限次
	private String sumcount1;//脱机按终端限次
	private String sumcount0l;//联机按卡限次
	private String sumcount1l;//联机按终端限次
	
	@Override
	protected String subExecute() throws Exception {
        if ("add".equals(method)) {
            rspCode = add();
        }else if ("delete".equals(method)) {
            rspCode = delete();
        } else if ("update".equals(method)) {
            rspCode = update();
        }
        return rspCode;
	}

	private String update() {
		//
		T21403BO t21403BO = (T21403BO) ContextUtil.getBean("T21403BO");
		List<Object[]> binds = t21403BO.findBindListByDiscountId(discountId);
		if (binds != null && binds.size() > 0) {
			return "此优惠规则已经使用，不能修改！";
		}
		TblMchntDiscountRule bean = t21402BO.get(discountId);
		if (StringUtils.isNotBlank(discountCard)&&"02".equals(openType)) {
			bean.setDiscountCard(discountCard);
		} else {
			bean.setDiscountCard("");
		}
		//
		bean.setSumcount0(fixString(sumcount0));
		bean.setSumcount1(fixString(sumcount1));
		//联机限次处理
		bean.setSumcount0l(fixString(sumcount0l));
		bean.setSumcount1l(fixString(sumcount1l));
		
		String discountValue = "";
		String discountValueInfo = "";
		//满减
		if (StringUtils.equals(discountType, "0")) {
			discountValue = StringUtils.leftPad(discountm, 10, '0') + "-"
					+ StringUtils.leftPad(discountj, 10, '0');
			discountValueInfo = "MJ" + StringUtils.leftPad(discountm, 10, '0')
					+ "-" + StringUtils.leftPad(discountj, 10, '0');
			acquirersType="02";
		}
		//折扣
		if (StringUtils.equals(discountType, "1")) {
			if(discountzk.length()<2){
				discountzk="0"+discountzk;
			}
			discountValue = StringUtils.leftPad(discountm, 10, '0') + "-"
					+ discountzk;
			discountValueInfo = "ZK" + StringUtils.leftPad(discountm, 10, '0')
					+ "-" + discountzk;
			acquirersType="01";
		}
		//任意
		if (StringUtils.equals(discountType, "2")) {
			discountValue = discountAny;
			discountValueInfo = discountAny;
			acquirersType="";
		}

		
		
		bean.setDiscountValue(discountValue);
		bean.setDiscountValueInfo(discountValueInfo);
		bean.setDiscountCode(discountCode);
		bean.setStartTimeEff(CommonFunction.getDateStartStr(startTimeEff));
		bean.setEndTimeEff(CommonFunction.getDateEndStr(endTimeEff));

		if (StringUtils.equals(discountType, "0")
				|| StringUtils.equals(discountType, "1")) {
			bean.setAcquirersId(acquirersId);
			bean.setIsDownload(isDownload);
			bean.setOpenLian(openLian);
			bean.setOpenType(openType);
		}

		bean.setRemark(remark);
		bean.setAcquirersType(acquirersType);
		//仅联机优惠
		if(openLian.equals("02")){   //00 脱机，01脱机+联机  02联机
			acquirersType="03";//03 62.1仅开启联机优惠
			//
			//openLian="01";
		}
		// 下发内容
//		if(!StringUtils.equals(openLian, "02")){
		if(true){
//		if (!StringUtils.equals(discountType, "3")) {
			StringBuffer buffer = new StringBuffer();
			if (bean.getDiscountType().equals("2")) {
				buffer = new StringBuffer(bean.getDiscountValue());
			} else {
				buffer.append(acquirersType);
				if (StringUtils.isNotBlank(openType)) {
					buffer.append(openType);
				}
				if ("01".equals(acquirersType) || "02".equals(acquirersType)|| "03".equals(acquirersType)) {
					buffer.append(bean.getDiscountValue().split("-")[0]);

					if (StringUtils.isNotBlank(openType)) {
						String s = "";
						if ("01".equals(openType)) {
							s = bean.getDiscountValue().split("-")[1];
						} else if ("02".equals(openType)) {
							if (StringUtils.isNotBlank(bean.getDiscountCard())) {
								// 组装card
								String cardStr = bean.getDiscountCard();
								String[] cards = StringUtils.split(cardStr, ",");
								s += bean.getDiscountValue().split("-")[1];
								
								for (int i = 0; i < cards.length; i++) {
									if(org.apache.commons.lang.StringUtils.isBlank(cards[i])){
										continue;
									}
									
									s += StringUtils.leftPad(cards[i], 6, '0');
									s += ",";
								}
								if(s.endsWith(",")){
									s = s.substring(0, s.length() -1);
								}
								s += "|";
							} else {
								return "此优惠规则的cardbin为空，与开启方式不符！";
							}

						}
						
						int length = s.length();
						buffer.append(StringUtils.leftPad(length + "", 3, '0'));
						buffer.append(s);
					}
				}
				buffer.append(StringUtils.rightPad(bean.getDiscountCode(), 20, '0'));
				//
				buffer.append(bean.getSumcount0());
				buffer.append(bean.getSumcount1());
				//
				buffer.append(isDownload);
				buffer.append(acquirersId);
				if(openLian.equals("02")){
					buffer.append("01");
				}else{
					buffer.append(openLian);
				}
			}

			bean.setIssuedContent(buffer.toString());
		}
	/*	else{
			bean.setIssuedContent(acquirersType);
		}*/

		t21402BO.update(bean);
		return Constants.SUCCESS_CODE;

	}

	private String delete() {
		//判断此规则是否被使用
		T21403BO t21403BO = (T21403BO) ContextUtil.getBean("T21403BO");
		List<Object[]> binds = t21403BO.findBindListByDiscountId(discountId);
		if(binds!=null&&binds.size()>0){
			return "此优惠规则已经使用，不能删除！";
		}
		TblMchntDiscountRule bean = t21402BO.get(discountId);
		bean.setStatus(CommonStatus.UNACTIVE.getCode()+"");
		t21402BO.update(bean);
		return Constants.SUCCESS_CODE;
	}

	private String add() {
		//判断：营销编码唯一
		T21403BO t21403BO = (T21403BO) ContextUtil.getBean("T21403BO");
		List<Object[]> binds = t21403BO.findBindListByDiscountCode(discountCode);
		if (binds != null && binds.size() > 0) {
			//重新生成代码，如果还存在则返回错误
			 discountCode=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+discountCode.substring(17, 20);
			 binds = t21403BO.findBindListByDiscountCode(discountCode);
			 if (binds != null && binds.size() > 0) {
				 return "营销代码已经存在";
			 }
		}
		discountId = InformationUtil.getDiscountRuleId();
		TblMchntDiscountRule bean = new TblMchntDiscountRule();
		bean.setDiscountId(discountId);
		bean.setDiscountCode(discountCode);
		bean.setDiscountType(discountType);
		bean.setStatus(CommonStatus.ACTIVE.getCode()+"");
		//脱机限次处理
		bean.setSumcount0(fixString(sumcount0));
		bean.setSumcount1(fixString(sumcount1));
		//联机限次处理
		bean.setSumcount0l(fixString(sumcount0l));
		bean.setSumcount1l(fixString(sumcount1l));
		
		if (StringUtils.isNotBlank(discountCard)&&"02".equals(openType)) {
			bean.setDiscountCard(discountCard);
		} else {
			bean.setDiscountCard("");
		}
		String discountValue = "";
		String discountValueInfo = "";
		//满减
		if (StringUtils.equals(discountType, "0")) {
			discountValue = StringUtils.leftPad(discountm, 10, '0') + "-"
					+ StringUtils.leftPad(discountj, 10, '0');
			discountValueInfo = "MJ" + StringUtils.leftPad(discountm, 10, '0')
					+ "-" + StringUtils.leftPad(discountj, 10, '0');
			
			acquirersType="02";
			
		}
		//折扣
		if (StringUtils.equals(discountType, "1")) {
			if(discountzk.length()<2){
				discountzk="0"+discountzk;
			}
			discountValue = StringUtils.leftPad(discountm, 10, '0') + "-"	+ discountzk;
			discountValueInfo = "ZK" + StringUtils.leftPad(discountm, 10, '0')
					+ "-" + discountzk;
			acquirersType="01";
			
		}
		//任意
		if (StringUtils.equals(discountType, "2")) {
			discountValue = discountAny;
			discountValueInfo = discountAny;
			acquirersType="";
		}
		
		//仅联机优惠
		if(openLian.equals("02")){   //00 脱机，01脱机+联机  02联机
			acquirersType="03";//03 62.1仅开启联机优惠
			//
			//openLian="01";
		}
		
		/*if (StringUtils.equals(discountType, "3")) {
			openLian="01";			
			acquirersType="03";
		}*/
		

		bean.setDiscountValue(discountValue);
		bean.setDiscountValueInfo(discountValueInfo);
		// 更新时间和柜员
		bean.setCreateTime(CommonFunction.getCurrentDateTime());
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);
		bean.setCreatePerson(opr.getOprId());

		bean.setStartTimeEff(CommonFunction.getDateStartStr(startTimeEff));
		bean.setEndTimeEff(CommonFunction.getDateEndStr(endTimeEff));
		bean.setIsDownload(isDownload);
		bean.setOpenLian(openLian);
		bean.setOpenType(openType);		
		
		bean.setAcquirersType(acquirersType);
		bean.setRemark(remark);
		//下发内容
		if(true){
			StringBuffer buffer = new StringBuffer();		
			if(bean.getDiscountType().equals("2")){     //任意   处理
				buffer = new StringBuffer(bean.getDiscountValue());
			}else{
				buffer.append(acquirersType);    //62.1开启何种优惠
				if(StringUtils.isNotBlank(openType)){  //62.2开启方式
					buffer.append(openType);
				}else{
				}
				
				//62.3
				if("01".equals(acquirersType) || "02".equals(acquirersType)|| "03".equals(acquirersType)){
					buffer.append(bean.getDiscountValue().split("-")[0]);
					//62.4
					if(StringUtils.isNotBlank(openType)){
						String s = "";
						if ("01".equals(openType)) {
							s = bean.getDiscountValue().split("-")[1];
						} else if ("02".equals(openType)) {
							if (StringUtils.isNotBlank(bean.getDiscountCard())) {
								// 组装card
								String cardStr = bean.getDiscountCard();

								String[] cards = StringUtils.split(cardStr, ",");
								s += bean.getDiscountValue().split("-")[1];
								for (int i = 0; i < cards.length; i++) {
									if(org.apache.commons.lang.StringUtils.isBlank(cards[i])){
										continue;
									}
									s += StringUtils.leftPad(cards[i], 6, '0');
									s += ",";
								}
								if(s.endsWith(",")){
									s = s.substring(0, s.length() -1);
								}
								s += "|";
							} else {
								return "此优惠规则的cardbin为空，与开启方式不符！";
							}

						}
						
						
						int length = s.length();
						buffer.append(StringUtils.leftPad(length + "", 3, '0'));
						buffer.append(s);
					}
				}
				buffer.append(StringUtils.rightPad(bean.getDiscountCode(), 20, '0'));
				//脱机限次处理
				buffer.append(bean.getSumcount0());
				buffer.append(bean.getSumcount1());
				//
				buffer.append(isDownload);
				buffer.append(acquirersId);
				if(openLian.equals("02")){
					buffer.append("01");
				}else{
					buffer.append(openLian);
				}
			}
		
			bean.setIssuedContent(buffer.toString());
		}
/*		if(!StringUtils.equals(openLian, "02")){
			StringBuffer buffer = new StringBuffer();		
			if(bean.getDiscountType().equals("2")){
				buffer = new StringBuffer(bean.getDiscountValue());
			}else{
				buffer.append(acquirersType);
				if(StringUtils.isNotBlank(openType)){
					buffer.append(openType);
				}else{
				}
				
				if("01".equals(acquirersType) || "02".equals(acquirersType)|| "03".equals(acquirersType)){
					buffer.append(bean.getDiscountValue().split("-")[0]);
					
					if(StringUtils.isNotBlank(openType)){
						String s = "";
						if ("01".equals(openType)) {
							s = bean.getDiscountValue().split("-")[1];
						} else if ("02".equals(openType)) {
							if (StringUtils.isNotBlank(bean.getDiscountCard())) {
								// 组装card
								String cardStr = bean.getDiscountCard();

								String[] cards = StringUtils.split(cardStr, ",");
								s += bean.getDiscountValue().split("-")[1];
								for (int i = 0; i < cards.length; i++) {
									if(org.apache.commons.lang.StringUtils.isBlank(cards[i])){
										continue;
									}
									s += StringUtils.leftPad(cards[i], 6, '0');
									s += ",";
								}
								if(s.endsWith(",")){
									s = s.substring(0, s.length() -1);
								}
								s += "|";
							} else {
								return "此优惠规则的cardbin为空，与开启方式不符！";
							}

						}
						
						
						int length = s.length();
						buffer.append(StringUtils.leftPad(length + "", 3, '0'));
						buffer.append(s);
					}
				}
				buffer.append(StringUtils.rightPad(bean.getDiscountCode(), 20, '0'));
				//
				buffer.append(bean.getSumcount0());
				buffer.append(bean.getSumcount1());
				//
				buffer.append(isDownload);
				buffer.append(acquirersId);
				buffer.append(openLian);
			}
		
			bean.setIssuedContent(buffer.toString());
		}else{
			bean.setIssuedContent(acquirersType);
		}*/
		bean.setAcquirersId(acquirersId);
		
		
		t21402BO.add(bean);
		return Constants.SUCCESS_CODE;

	}
	
	
	private String fixString(String temp){
		if(temp==null||temp.equals("")){
			temp="0";
		}
		int oLength=temp.length();
		for(int i=oLength;i<4;i++){
			temp="0"+temp;
		}
		return temp;
	}
	

	public T21402BO getT21402BO() {
		return t21402BO;
	}

	public void setT21402BO(T21402BO t21402bo) {
		t21402BO = t21402bo;
	}

	public String getDiscountId() {
		return discountId;
	}

	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getDiscountCard() {
		return discountCard;
	}

	public void setDiscountCard(String discountCard) {
		this.discountCard = discountCard;
	}
	

	public String getDiscountm() {
		return discountm;
	}

	public void setDiscountm(String discountm) {
		this.discountm = discountm;
	}

	public String getDiscountj() {
		return discountj;
	}

	public void setDiscountj(String discountj) {
		this.discountj = discountj;
	}

	public String getDiscountzk() {
		return discountzk;
	}

	public void setDiscountzk(String discountzk) {
		this.discountzk = discountzk;
	}

	public String getDiscountAny() {
		return discountAny;
	}

	public void setDiscountAny(String discountAny) {
		this.discountAny = discountAny;
	}

	public Date getStartTimeEff() {
		return startTimeEff;
	}

	public void setStartTimeEff(Date startTimeEff) {
		this.startTimeEff = startTimeEff;
	}

	public Date getEndTimeEff() {
		return endTimeEff;
	}

	public void setEndTimeEff(Date endTimeEff) {
		this.endTimeEff = endTimeEff;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getAcquirersType() {
		return acquirersType;
	}

	public void setAcquirersType(String acquirersType) {
		this.acquirersType = acquirersType;
	}

	public String getOpenLian() {
		return openLian;
	}

	public void setOpenLian(String openLian) {
		this.openLian = openLian;
	}

	public String getIsDownload() {
		return isDownload;
	}

	public void setIsDownload(String isDownload) {
		this.isDownload = isDownload;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAcquirersId() {
		return acquirersId;
	}

	public void setAcquirersId(String acquirersId) {
		this.acquirersId = acquirersId;
	}

	public String getSumcount0() {
		return sumcount0;
	}

	public void setSumcount0(String sumcount0) {
		this.sumcount0 = sumcount0;
	}

	public String getSumcount1() {
		return sumcount1;
	}

	public void setSumcount1(String sumcount1) {
		this.sumcount1 = sumcount1;
	}

	public String getSumcount0l() {
		return sumcount0l;
	}

	public void setSumcount0l(String sumcount0l) {
		this.sumcount0l = sumcount0l;
	}

	public String getSumcount1l() {
		return sumcount1l;
	}

	public void setSumcount1l(String sumcount1l) {
		this.sumcount1l = sumcount1l;
	}

}
