package com.rail.zWebSocket;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HeartData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//11 11设备类型版本  86 04设备编码  01设备组号  12 34设备密码  BC包序号  00 12数据长度
	//09数据一长度  00命令编码心跳包  7D 86最新事件号(data1) 00(主机状态1 data2) 10(主机状态2 data3)  
	//F8(手机模块状态data4) 00(手机模块信号data5) 55(分区状态1 data6) 55(分区状态2 data7)
	//07数据二长度  04命令编码  00 01报警模块版本信息  19 03 29 E4固件版本  C4xor
	
	private BaseData baseData;
	private String AData1;//最新事件号(data1)，例如7D 86
	private String AData2;//主机状态1 data2，例如00
	private String AData3;//主机状态2 data3，例如10
	private String AData4;//手机模块状态data4，例如F8
	private String AData5;//(手机模块信号data5)，例如00
	private String AData6;//(分区状态1 data6)，例如55
	private String AData7;//(分区状态2 data7)，例如55
	private String AData8;//(分区状态 防区状态)，例如30
	private String dataBLength;//数据2长度，例如04
	private String dataBOrderCode;//命令编码0x04表示设备回应查询
	private String dataBVerMsg;//命令编码报警模块版本信息 例如 00 01
	private String dataBVerEquiptMsg;//固件版本 19 03 29 E4
	private String xor;//异或值
	
	public static void main(String[] args) {
		String hexStr="00";
		
		System.out.println("hello");
	}
	/**
	 * @Description: 解析获取data2的主机状态 
	 * @param @return
	 * @return Map<String,String>
	 * @throws
	 */
	public Map<String, String> unPackData2(){
		//data2为1字节
		String data2=getAData2();
		byte[] data2ByteArray=PubUtil.parseHexStr2Byte(data2);
		byte data2Byte=data2ByteArray[0];//因为这里是1字节
		int[] data2IntArray=PubUtil.parseByteToArray(data2Byte);//获取对应的每位对应的数据值
		//下面输出对应的数据值
		Map<String, String> retMap=new HashMap<String, String>();
		retMap.put("0", data2IntArray[0]+"");//主机供电状态 1表示交流停电；0表示交流正常
		//00表示电池正常，01表示电池低压，10保留，11表示电池故障；
		retMap.put("1", data2IntArray[2]+""+data2IntArray[1]);
		//代表主机警号状态，1警号开，0警号关
		retMap.put("5", data2IntArray[5]+"");
		return retMap;
	}
	
	
	public String getAData1() {
		return AData1;
	}
	public void setAData1(String aData1) {
		AData1 = aData1;
	}
	public String getAData2() {
		return AData2;
	}
	public void setAData2(String aData2) {
		AData2 = aData2;
	}
	public String getAData3() {
		return AData3;
	}
	public void setAData3(String aData3) {
		AData3 = aData3;
	}
	public String getAData4() {
		return AData4;
	}
	public void setAData4(String aData4) {
		AData4 = aData4;
	}
	public String getAData5() {
		return AData5;
	}
	public void setAData5(String aData5) {
		AData5 = aData5;
	}
	public String getAData6() {
		return AData6;
	}
	public void setAData6(String aData6) {
		AData6 = aData6;
	}
	public String getAData7() {
		return AData7;
	}
	public void setAData7(String aData7) {
		AData7 = aData7;
	}
	public String getDataBLength() {
		return dataBLength;
	}
	public void setDataBLength(String dataBLength) {
		this.dataBLength = dataBLength;
	}
	public String getDataBOrderCode() {
		return dataBOrderCode;
	}
	public void setDataBOrderCode(String dataBOrderCode) {
		this.dataBOrderCode = dataBOrderCode;
	}
	public String getDataBVerMsg() {
		return dataBVerMsg;
	}
	public void setDataBVerMsg(String dataBVerMsg) {
		this.dataBVerMsg = dataBVerMsg;
	}
	public String getDataBVerEquiptMsg() {
		return dataBVerEquiptMsg;
	}
	public void setDataBVerEquiptMsg(String dataBVerEquiptMsg) {
		this.dataBVerEquiptMsg = dataBVerEquiptMsg;
	}
	public String getXor() {
		return xor;
	}
	public void setXor(String xor) {
		this.xor = xor;
	}

	public HeartData() {
		super();
	}
	public BaseData getBaseData() {
		return baseData;
	}
	public void setBaseData(BaseData baseData) {
		this.baseData = baseData;
	}
	public HeartData(BaseData baseData, String aData1, String aData2, String aData3, String aData4, String aData5,
			String aData6, String aData7, String dataBLength, String dataBOrderCode, String dataBVerMsg,
			String dataBVerEquiptMsg, String xor) {
		super();
		this.baseData = baseData;
		AData1 = aData1;
		AData2 = aData2;
		AData3 = aData3;
		AData4 = aData4;
		AData5 = aData5;
		AData6 = aData6;
		AData7 = aData7;
		this.dataBLength = dataBLength;
		this.dataBOrderCode = dataBOrderCode;
		this.dataBVerMsg = dataBVerMsg;
		this.dataBVerEquiptMsg = dataBVerEquiptMsg;
		this.xor = xor;
	}
	public String getAData8() {
		return AData8;
	}
	public void setAData8(String aData8) {
		AData8 = aData8;
	}
	
	
}
