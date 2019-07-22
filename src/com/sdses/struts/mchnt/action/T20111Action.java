package com.sdses.struts.mchnt.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.ContextUtil;
import com.sdses.bo.mchnt.T20111BO;
import com.sdses.po.TAirpayMap;
import com.sdses.po.TAirpayMapPK;

public class T20111Action extends BaseAction{
	
	private static Log log = LogFactory.getLog(T20111Action.class);
	
	private T20111BO t20111bo = (T20111BO) ContextUtil.getBean("T20111BO");

	private static final long serialVersionUID = 1L;
	
	// 银联商户号
	private String ylMchntNo;
	
	// 银联终端号
	private String ylTermNo;
	
	// 应用ID
	private String appId;
	
	// 支付类型
	private String payFlag;
	
	// 商户号
	private String mchId;
	
	// 支付宝公钥/微信密钥
	private String publicKey;
	
	// 支付宝私钥
	private String privateKey;
	
	// 签名类型
	private String signType;
	
	// 状态标识
	private String flag;
	
	// 授权标识
	private String authorityFlag;
	
	// 保存数据字符串
    private String mchntDataStr;

	@Override
	protected String subExecute() throws Exception {
		try {
			if ("add".equals(getMethod())) {
	             rspCode = add();
	         } else if ("update".equals(getMethod())) {
	             rspCode = update();
	         } else if ("delete".equals(getMethod())) {
	             rspCode = delete();
	         }
		} catch (Exception e) {
			log("操作员编号:" + operator.getOprId() + "，对机构的维护操作" + getMethod() + "失败，失败原因为：" + e.getMessage());
		}		 
		return rspCode;
	}

	private String add() throws Exception {
		TAirpayMapPK tAirpayMapPK=new TAirpayMapPK();
		tAirpayMapPK.setYlMchntNo(ylMchntNo.trim());
		tAirpayMapPK.setYlTermNo(ylTermNo.trim());
		tAirpayMapPK.setPayFlag(payFlag.trim());
		TAirpayMap tAirpayMap=new TAirpayMap();
		tAirpayMap.settAirpayMapPK(tAirpayMapPK);
		tAirpayMap.setAppId(appId.trim());
		tAirpayMap.setcKey1(publicKey.trim());
		tAirpayMap.setFlag(flag);
		tAirpayMap.setAuthorityFlag(authorityFlag);
		int isExit = t20111bo.isExitRecord(tAirpayMapPK);
        if (isExit != 0) {
        	return "该条记录已存在，请重新输入！";
		}
		if ("0".equals(payFlag.trim())) {
			tAirpayMap.setMchId(mchId.trim());
			List<Object[]> isExitDate = t20111bo.isExitWeDate(appId.trim(), mchId.trim());
			if(isExitDate != null && isExitDate.size() > 0){
				return "此应用ID下该商户号已存在，请重新输入！";
			}
		} else if ("1".equals(payFlag.trim())) {
			tAirpayMap.setMchId(ylMchntNo.trim());
			tAirpayMap.setcKey2(privateKey.trim());
			if ("0".equals(signType.trim())) {
				tAirpayMap.setSignType("RSA2");
			} else {
				tAirpayMap.setSignType("RSA");
			}
//			List<Object[]> isExitDate = t20111bo.isExitAliDate(appId);
//			if(isExitDate != null && isExitDate.size() > 0){
//				return "该应用ID已存在，请重新输入！";
//			}
		}
		int addData = t20111bo.addDate(tAirpayMap);
		if (addData > 0) {
            return Constants.SUCCESS_CODE;
        } else {
            return ErrorCode.T20111_01;
        }
	}

	private String update() throws Exception{
		jsonBean.parseJSONArrayData(getMchntDataStr());
        int len = jsonBean.getArray().size();
        List<TAirpayMap> tAirpayMapList=new ArrayList<TAirpayMap>();
        for (int i = 0; i < len; i++) {
            jsonBean.setObject(jsonBean.getJSONDataAt(i));
            TAirpayMap tAirpayMap=new TAirpayMap();
            TAirpayMapPK tAirpayMapPK=new TAirpayMapPK();
            BeanUtils.setObjectWithPropertiesValue(tAirpayMap,jsonBean,false);
            BeanUtils.setObjectWithPropertiesValue(tAirpayMapPK,jsonBean,false);
            if ("微信".equals(tAirpayMapPK.getPayFlag())) {
				tAirpayMapPK.setPayFlag("0");
			} else {
				tAirpayMapPK.setPayFlag("1");
			}
            if("0".equals(tAirpayMap.getSignType())){
            	tAirpayMap.setSignType("RSA2");
            } else if ("1".equals(tAirpayMap.getSignType())) {
				tAirpayMap.setSignType("RSA");
			}
            tAirpayMap.settAirpayMapPK(tAirpayMapPK);
            if ("0".equals(tAirpayMapPK.getPayFlag())) {
            	List<Object[]> isExitDate = t20111bo.isExitWeDate(tAirpayMap.getAppId().trim(), tAirpayMap.getMchId().trim());
    			if(isExitDate != null && isExitDate.size() > 0){
    				Object[] date=isExitDate.get(0);
    				if(!(tAirpayMapPK.getYlMchntNo().trim().equals(date[0]) && tAirpayMapPK.getYlTermNo().trim().equals(date[1]) && tAirpayMapPK.getPayFlag().trim().equals(date[4]))){
    					return "此应用ID下该商户号已存在，请重新输入！";
					}	
    			}
			} else {
//				List<Object[]> isExitDate = t20111bo.isExitAliDate(tAirpayMap.getAppId().trim());
//				if(isExitDate != null && isExitDate.size() > 0){
//					Object[] date=isExitDate.get(0);
//					if(!(tAirpayMapPK.getYlMchntNo().trim().equals(date[0]) && tAirpayMapPK.getYlTermNo().trim().equals(date[1]) && tAirpayMapPK.getPayFlag().trim().equals(date[4]))){
//						return "该应用ID已存在，请重新输入！";
//					}
//				}
			}
            int updateData = t20111bo.updateData(tAirpayMap);
            if (updateData <= 0) {
				return ErrorCode.T20111_02;
			} else {
				return Constants.SUCCESS_CODE;
			}
        }
        return Constants.SUCCESS_CODE;
	}

	private String delete() throws Exception {
		TAirpayMapPK tAirpayMapPK=new TAirpayMapPK();
		tAirpayMapPK.setYlMchntNo(ylMchntNo.trim());
		tAirpayMapPK.setYlTermNo(ylTermNo.trim());
		if("微信".equals(payFlag.trim())){
			tAirpayMapPK.setPayFlag("0");
		} else {
			tAirpayMapPK.setPayFlag("1");
		}
		
		int deleteData = t20111bo.deleteDate(tAirpayMapPK);
		if (deleteData > 0) {
			return Constants.SUCCESS_CODE;
		} else {
			return ErrorCode.T20111_03;
		}
	}

	public T20111BO getT20111bo() {
		return t20111bo;
	}

	public void setT20111bo(T20111BO t20111bo) {
		this.t20111bo = t20111bo;
	}
	
	public String getYlMchntNo() {
		return ylMchntNo;
	}

	public void setYlMchntNo(String ylMchntNo) {
		this.ylMchntNo = ylMchntNo;
	}

	public String getYlTermNo() {
		return ylTermNo;
	}

	public void setYlTermNo(String ylTermNo) {
		this.ylTermNo = ylTermNo;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getMchntDataStr() {
		return mchntDataStr;
	}

	public void setMchntDataStr(String mchntDataStr) {
		this.mchntDataStr = mchntDataStr;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getAuthorityFlag() {
		return authorityFlag;
	}

	public void setAuthorityFlag(String authorityFlag) {
		this.authorityFlag = authorityFlag;
	}

}
