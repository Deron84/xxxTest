package com.sdses.struts.mchnt.action;

import java.util.HashMap;
import java.util.ResourceBundle;

import com.alibaba.fastjson.JSON;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.BaseAction;
import com.sdses.struts.util.APIHttpClient;
import com.sdses.struts.util.ResponseBean;
import com.sdses.struts.util.StaticUtils;

public class mchntMSAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	ResourceBundle bundle = ResourceBundle.getBundle(SysParamConstants.CKEY);
	String MSURl = bundle.getString("MSURl");
	String mchntAddUrl = bundle.getString("mchntAddUrl");
	String mchntModifyUrl = bundle.getString("mchntModifyUrl");
	String mchntQueryUrl = bundle.getString("mchntQueryUrl");

	private String operId;        //拓展人编号
	private String outMchntId;    //外部商户号
	private String cmbcMchntId;    //民生商户号
	private String mchntName;     //商户简称
	private String mchntFullName; //商户全称
	private String devType;       //拓展模式
	private String acdCode;       //地区编码
	private String province;      //省份
	private String city;          //城市
	private String address;       //地址
	private String isCert;        //是否持证
	private String licId;         //营业执证
	private String idTCard;       //省份证件号
	private String corpName;      //联系人
	private String telephone;     //联系电话
	private String autoSettle;
	/**
     * 
     * //TODO 商户添加
     *
     * @param args
     * @throws Exception
     */
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		String postStr = null;
		try {
            if ("add".equals(getMethod())) {
            	postStr = add();
            } else if ("update".equals(getMethod())) {
            	postStr=update();	
			} else if ("query".equals(getMethod())){
				postStr = query();
			}
        } catch (Exception e) {
            log("操作员编号：" + operator.getOprId() + "，对机构的维护操作" + getMethod() + "失败，失败原因为：" + e.getMessage());
        }
        return postStr;
	}
	/**
     * 添加民生商户信息
     * @throws Exception
     */
    private String add() throws Exception {
    	HashMap<String, Object> hashMap = new HashMap<String, Object>();
    	String responseMsg=null;
    	hashMap.put("operId",operId);
        hashMap.put("outMchntId",outMchntId);
        hashMap.put("mchntName", mchntName);
        hashMap.put("mchntFullName",mchntFullName);
        hashMap.put("devType", devType);
        hashMap.put("acdCode", acdCode);
        hashMap.put("province", province);
        hashMap.put("city", city);
        hashMap.put("address", address);
        hashMap.put("isCert", isCert); //为0-licID可空，为1-licID不可空
        hashMap.put("licId", licId);
        hashMap.put("idTCard", idTCard); //可空
        hashMap.put("corpName",corpName);
        hashMap.put("telephone", telephone);
        hashMap.put("autoSettle", autoSettle);
        String paramStr = StaticUtils.mapToStr(hashMap);
        System.out.println(paramStr);
        String postStr = APIHttpClient.httpsRequest(MSURl+mchntAddUrl, "POST", paramStr.toString());
        System.out.println("-------"+postStr);
        ResponseBean bean = JSON.parseObject(postStr, ResponseBean.class);
        responseMsg=StaticUtils.ListToSt(bean);
        System.out.println("-------"+responseMsg);
        return responseMsg;
    }
    /**
     * 
     * //TODO 商户修改
     *
     * @param args
     * @throws Exception
     */
    private String update() throws Exception{
    	HashMap<String, Object> hashMap = new HashMap<String, Object>();
    	String responseMsg=null;
        hashMap.put("operId",operId);
        hashMap.put("outMchntId",outMchntId);
        hashMap.put("cmbcMchntId", cmbcMchntId);
        hashMap.put("mchntName", mchntName);
        hashMap.put("mchntFullName",mchntFullName);
        hashMap.put("address", address);
        hashMap.put("isCert",isCert); //为0-licID可空，为1-licID不可空
        hashMap.put("licId",licId);
        hashMap.put("idtCard", idTCard); //可空
        hashMap.put("corpName",corpName);
        hashMap.put("telephone",telephone);
        hashMap.put("autoSettle",autoSettle);
        String paramStr = StaticUtils.mapToStr(hashMap);
        System.out.println(paramStr);
        String postStr = APIHttpClient.httpsRequest(MSURl+mchntModifyUrl, "POST", paramStr.toString());
        System.out.println("-------"+postStr);
        ResponseBean bean = JSON.parseObject(postStr, ResponseBean.class);
        responseMsg=StaticUtils.ListToSt(bean);
        System.out.println("-------"+responseMsg);
        return responseMsg;
    }
    /**
     * 
     * //TODO 商户查询
     *
     * @param args
     * @throws Exception
     */
    private String query() throws Exception{
    	HashMap<String, Object> hashMap = new HashMap<String, Object>();
    	String responseMsg=null;
        hashMap.put("outMchntId",outMchntId);
        String paramStr = StaticUtils.mapToStr(hashMap);
        String postStr = APIHttpClient.httpsRequest(MSURl+mchntQueryUrl, "POST", paramStr.toString());
        System.out.println("-------"+postStr);
        ResponseBean bean = JSON.parseObject(postStr, ResponseBean.class);
        responseMsg=StaticUtils.ListToSt(bean);
        System.out.println("-------"+responseMsg);
        return responseMsg;
    }
    
    
    
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public String getOutMchntId() {
		return outMchntId;
	}
	public void setOutMchntId(String outMchntId) {
		this.outMchntId = outMchntId;
	}
	public String getCmbcMchntId() {
		return cmbcMchntId;
	}
	public void setCmbcMchntId(String cmbcMchntId) {
		this.cmbcMchntId = cmbcMchntId;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public String getMchntFullName() {
		return mchntFullName;
	}
	public void setMchntFullName(String mchntFullName) {
		this.mchntFullName = mchntFullName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIsCert() {
		return isCert;
	}
	public void setIsCert(String isCert) {
		this.isCert = isCert;
	}
	public String getLicId() {
		return licId;
	}
	public void setLicId(String licId) {
		this.licId = licId;
	}
	public String getIdTCard() {
		return idTCard;
	}
	public void setIdTCard(String idTCard) {
		this.idTCard = idTCard;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAutoSettle() {
		return autoSettle;
	}
	public void setAutoSettle(String autoSettle) {
		this.autoSettle = autoSettle;
	}
	public String getDevType() {
		return devType;
	}
	public void setDevType(String devType) {
		this.devType = devType;
	}
	public String getAcdCode() {
		return acdCode;
	}
	public void setAcdCode(String acdCode) {
		this.acdCode = acdCode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}