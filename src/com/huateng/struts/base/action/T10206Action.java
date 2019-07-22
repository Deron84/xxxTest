package com.huateng.struts.base.action;

import java.lang.reflect.InvocationTargetException;

import com.huateng.bo.base.T10206BO;
import com.huateng.common.Constants;
import com.huateng.po.base.TblEmvPara;
import com.huateng.po.base.TblEmvParaPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;

public class T10206Action extends BaseAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1547067057901723700L;

	private T10206BO t10206BO = (T10206BO) ContextUtil.getBean("T10206BO");
	
	private java.lang.String paraOrg;
	private java.lang.String a9F06;
	private java.lang.String a9F22;
	private java.lang.String dF05;
	private java.lang.String dF06;
	private java.lang.String dF07;
	private java.lang.String dF04;
	private java.lang.String dF02;
	private java.lang.String dF03;

	@Override
	protected String subExecute(){
		try {
			if("add".equals(method)) { // 新增CA银联公钥信息
				rspCode = add();
			} else if("delete".equals(method)) { //删除CA银联公钥信息
				rspCode = delete();
			} else if("update".equals(method)) { //同步CA银联公钥信息
				rspCode = update();
			} else if("edit".equals(method)) {  // 更新权限信息
				rspCode = edit();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，CA银联公钥信息维护" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	/**
	 * 添加CA银联公钥信息
	 * @return
	 */
	private String add() {
				
		//9F06 与认证中心公钥索引一起标识认证中心的公钥(注册应用提供商标识) 长度 5(bit型)
		//9F06 + 05 + A000000333
		String string9F06 = "9F06" + "05" + a9F06;
		
		//9F22 与RID一起标识认证中心的公钥(根CA公钥索引) 长度 1(bit型)
		//9F22 + 01 + 08
		String string9F22 = "9F22" + "01" + CommonFunction.fillString(a9F22, '0', 2, false);
		
		//DF05 认证中心规定的有效期限(证书失效日期) 长度 4(n 数字型)
		//DF05 + 08 + 3230333031323031          (2030-12-1)
		char[] dF05CharArray = dF05.toCharArray();
		StringBuffer dF05StringBuffer = new StringBuffer();
		for(int i=0; i<dF05.length(); i++) dF05StringBuffer.append("3").append(dF05CharArray[i]); //在每个数字前加3
		String stringDF05 = "DF05" + "08" + dF05StringBuffer.toString();
		
		//DF06 标识用于在数字签名方案中产生哈什结果的哈希算法  长度 1(bit型)
		//DF06 + 01 + 01    (SHA-1)
		String stringDF06 = "DF06" + "01" + CommonFunction.fillString(dF06, '0', 2, false);
		
		//DF07 标识使用在认证中心公钥上的数字签名算法 长度 1(bit型)
		//DF06 + 01 + 01    (RSA)
		String stringDF07 = "DF07" + "01" + CommonFunction.fillString(dF07, '0', 2, false);
		
		//DF04 公钥指数 长度 1(bit型)
		//DF04 + 01 + 03
		String stringDF04 = "DF04" + "01" + CommonFunction.fillString(dF04, '0', 2, false);
		
		//DF02 公钥模值 长度 变长，最大为248(bit型)
		//DF02 + 90(HEX = DEC 144) + (144个字节扩展)
		String dF02Deal = dF02.replace(" ", ""); //去掉dF02的空格
		int dF02DealLength = dF02Deal.length()/2; //dF02Deal的长度，由于前面用2位表示一个字符，所以这里除2
		
		String dF02DealLengthOX;
		//当L字段最左边字节的最左bit位（即bit8）为0，表示该L字段占一个字节
		//它的后续7个bit位（即bit7～bit1）表示子域取值的长度，采用二进制数表示子域取值长度的十进制数。
		//当L字段最左边字节的最左bit位（即bit8）为1，表示该L字段不止占一个字节，
		//那么它到底占几个字节由该最左字节的后续7个bit位（即bit7～bit1）的十进制取值表示
		if(dF02DealLength <128) { //00 ~ 7F(0000 0000 ~ 01111 1111) 
			
			dF02DealLengthOX = Integer.toHexString(dF02DealLength);
			if(dF02DealLengthOX.length() == 1) dF02DealLengthOX = "0" + dF02DealLengthOX; //不足两位，前补0
		} else if(dF02DealLength<256) { //80 ~ FF(1000 0000 ~ 1111 1111)
			
			dF02DealLengthOX = Integer.toHexString(dF02DealLength);
			dF02DealLengthOX = "81" + dF02DealLengthOX;
		} else if(dF02DealLength<4096) { //100 ~ FFF(0001 0000 0000 ~ 1111 1111 1111)
			
			dF02DealLengthOX = Integer.toHexString(dF02DealLength);
			dF02DealLengthOX = "820" + dF02DealLengthOX;
		} else {
			
			dF02DealLengthOX = "null";
		}
		
		String stringDF02 = "DF02" + dF02DealLengthOX.toUpperCase() + dF02Deal;
		
		//DF03 验证认证中心公钥用 长度 变长(bit型)
		//DF03 + 14(HEX = DEC 20) + (20个字节扩展)
		String dF03Deal = dF03.replace(" ", ""); //去掉dF02的空格
		String stringDF03 = "DF03" + Integer.toHexString(20) + dF03Deal;
		
		String paraValString = string9F06 + string9F22 + stringDF05 + stringDF06 + stringDF07 + stringDF02 + stringDF04 + stringDF03;
		
		String paraIdx = GenerateNextId.getParaIdx("1");
		String paraId = GenerateNextId.getParaId();
		
		TblEmvParaPK pk = new TblEmvParaPK("1", paraIdx);
		
		TblEmvPara tblEmvPara = new TblEmvPara(pk);
		tblEmvPara.setParaId(paraId);
		tblEmvPara.setGenFlag("0");
		tblEmvPara.setParaLen(String.valueOf(paraValString.length()));
		tblEmvPara.setParaOrg(paraOrg);
		tblEmvPara.setParaSta("0");		
		tblEmvPara.setParaVal(paraValString);
		tblEmvPara.setRecOprId("0");
		tblEmvPara.setRecUpdOpr(operator.getOprId());
		tblEmvPara.setRecCrtTs(CommonFunction.getCurrentDateTime());
		tblEmvPara.setRecUpdTs(CommonFunction.getCurrentDateTime());		
		
		t10206BO.createTblEmvPara(tblEmvPara);
		
		return Constants.SUCCESS_CODE;
	}

	private String paraIdxPK;
	private String usageKeyPK;
	
	/**
	 * @return the paraIdxPK
	 */
	public String getParaIdxPK() {
		return paraIdxPK;
	}

	/**
	 * @param paraIdxPK the paraIdxPK to set
	 */
	public void setParaIdxPK(String paraIdxPK) {
		this.paraIdxPK = paraIdxPK;
	}

	/**
	 * @return the usageKeyPK
	 */
	public String getUsageKeyPK() {
		return usageKeyPK;
	}

	/**
	 * @param usageKeyPK the usageKeyPK to set
	 */
	public void setUsageKeyPK(String usageKeyPK) {
		this.usageKeyPK = usageKeyPK;
	}

	private String delete() {

		TblEmvParaPK pk = new TblEmvParaPK();
		pk.setParaIdx(CommonFunction.fillString(paraIdxPK, ' ', 4, true));
		pk.setUsageKey(CommonFunction.fillString("1", ' ', 8, true));
		t10206BO.delete(pk);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 更新CA银联公钥信息
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
//		jsonBean.parseJSONArrayData(getRoleInfoList());
//		
//		int len = jsonBean.getArray().size();
//		
//		TblRoleInf tblRoleInf = null;
//		
//		List<TblRoleInf> tblRoleInfList = new ArrayList<TblRoleInf>(len);
//		
//		for(int i = 0; i < len; i++) {
//			jsonBean.setObject(jsonBean.getJSONDataAt(i));
//			
//			tblRoleInf = new TblRoleInf();
//			
//			tblRoleInf.setRecUpdOpr(operator.getOprId());
//			
//			tblRoleInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
//			
//			BeanUtils.setObjectWithPropertiesValue(tblRoleInf, jsonBean, true);
//			
//			tblRoleInfList.add(tblRoleInf);
//		}
//		t10301BO.update(tblRoleInfList);
//		log("更新CA银联公钥信息维护成功。操作员编号：" + operator.getOprId()+ "，被添加CA银联公钥编号：" + roleId);	
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 更新CA银联公钥的权限信息
	 * @return
	 */
	public String edit() {
		
//		jsonBean.parseJSONArrayData(getMenuList());
//		
//		int len = jsonBean.getArray().size();
//		
//		List<TblRoleFuncMap> roleFuncMapList = new ArrayList<TblRoleFuncMap>(len);
//		
//		TblRoleFuncMapPK tblRoleFuncMapPK = null;
//		
//		TblRoleFuncMap tblRoleFuncMap = null;
//		
//		for(int i = 0; i < len; i++) {
//			
//			tblRoleFuncMap = new TblRoleFuncMap();
//			
//			tblRoleFuncMapPK = new TblRoleFuncMapPK();
//			
//			tblRoleFuncMapPK.setKeyId(Integer.parseInt(roleId));
//			
//			tblRoleFuncMapPK.setValueId(Integer.parseInt(jsonBean.getJSONDataAt(i).getString("valueId")));
//			
//			tblRoleFuncMap.setId(tblRoleFuncMapPK);
//			
//			tblRoleFuncMap.setRecCrtTs(CommonFunction.getCurrentDateTime());
//			
//			tblRoleFuncMap.setRecUpdOpr(operator.getOprId());
//			
//			tblRoleFuncMap.setRecUpdTs(CommonFunction.getCurrentDateTime());
//			
//			roleFuncMapList.add(tblRoleFuncMap);
//		}
//		
//		t10301BO.updateRoleMenu(roleFuncMapList,t10301BO.getMenuList(Integer.parseInt(roleId)));	
//		log("更新CA银联公钥的权限信息成功添加CA银联公钥信息维护成功。操作员编号：" + operator.getOprId()+ "，被添加CA银联公钥编号：" + roleId);			
		return Constants.SUCCESS_CODE;
	}



	public java.lang.String getParaOrg() {
		return paraOrg;
	}

	public void setParaOrg(java.lang.String paraOrg) {
		this.paraOrg = paraOrg;
	}

	public java.lang.String getA9F06() {
		return a9F06;
	}

	public void setA9F06(java.lang.String a9f06) {
		a9F06 = a9f06;
	}

	public java.lang.String getA9F22() {
		return a9F22;
	}

	public void setA9F22(java.lang.String a9f22) {
		a9F22 = a9f22;
	}

	public java.lang.String getDF05() {
		return dF05;
	}

	public void setDF05(java.lang.String df05) {
		dF05 = df05;
	}

	public java.lang.String getDF06() {
		return dF06;
	}

	public void setDF06(java.lang.String df06) {
		dF06 = df06;
	}

	public java.lang.String getDF07() {
		return dF07;
	}

	public void setDF07(java.lang.String df07) {
		dF07 = df07;
	}

	public java.lang.String getDF04() {
		return dF04;
	}

	public void setDF04(java.lang.String df04) {
		dF04 = df04;
	}

	public java.lang.String getDF02() {
		return dF02;
	}

	public void setDF02(java.lang.String df02) {
		dF02 = df02;
	}

	public java.lang.String getDF03() {
		return dF03;
	}

	public void setDF03(java.lang.String df03) {
		dF03 = df03;
	}	
}
