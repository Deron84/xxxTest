package com.rail.zWebSocket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.java_websocket.WebSocketImpl;

import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;
import com.rail.bo.access.T120101BO;
import com.rail.bo.tools.TSendMsgSocketBO;
import com.rail.bo.warehouse.T100101BO;
import com.rail.dao.iface.access.RailAccessWarnDao;
import com.rail.dao.iface.work.RailWorkInfoDao;
import com.rail.dao.impl.access.RailAccessWarnDaoTarget;
import com.rail.dao.impl.work.RailWorkInfoDaoTarget;
import com.rail.po.access.RailAccessOptlog;
import com.rail.po.access.RailAccessWarn;
import com.rail.po.work.RailWorkInfo;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xpath.internal.operations.And;

public class PubUtil {
	
	/**
	 * 这个全局变量用来保存对应的设备连接信息，key值为设备编码，通过这个来获取对应的ws以及门禁的开关信息
	 */
	public static HashMap<String, EquipConInfo> EQUIP_IN_CON=new HashMap<String, EquipConInfo>();
	
	/**
	 * 这个全局变量用来保存对应的设备的状态、故障信息等，key值为设备编码 开关门状态，目前暂先不用
	 */
	public static HashMap<String, EquipStateData> EQUIP_STATE=new HashMap<String, EquipStateData>();

	/**
	 * 这个全局变量用来保存用户发送的开关门指令信息
	 * 对应的设备编码：开启时间点
	 * 查询针对开启指令时间点是否有工单信息
	 * 1、首先向设备发送开门指令时要保存
	 * 2、发送心跳时将时间过长的开门指令去除 ===2.1这里要判断时间过长了没有关闭的要进行门锁长时间未关闭的报警，然后清空
	 * 3、接收到门锁开启信息时要插入一条开启信息====3.1这里要判断是否有上一次的开门信息，没有或者时间超过3分钟进行报警，意外打开，然后报警
	 * 4、当收到门锁复合指令时清空long信息
	 */
	public static HashMap<String, Long> ACCESS_OPEN=new HashMap<String, Long>();
	
	
	/**
	 * 这个针对全局的红外信息获取应用
	 * 对应的设备编码：List保存对应的开门时间点
	 * 1、查询当前设备是否对应时间有工单信息
	 * 2、发送心跳时将时间过长的开门指令去除 ===2.1这里要判断时间过长的，超过3分钟这种红外指令，然后清空
	 * 								  2.2判断List的长度，超过2配置的时候进行报警处理，然后清空list
	 * 3、查询当前设备一段时间内是否有开门指令信息==如果没有进行报警；如果有不报警，清空list
	 * 
	 * 在心跳里面进行处理报警队列，报警处理
	 */
	public static HashMap<String, List<Long>> LIGHT_OPEN=new HashMap<String, List<Long>>();
	
	public static int ACCESSOPENWARN=Integer.parseInt(SysParamUtil.getParam("ACCESSOPENWARN"));
	public static int LIGHTNUMWARN=Integer.parseInt(SysParamUtil.getParam("LIGHTNUMWARN"));
	/**
	 * @Description: 对接收到的数据流进行解析存放到全局map里面，
	 * 后面定时任务中对已经在的全局map进行预警处理
	 * @param @param reqBuffer
	 * @param @param ws
	 * @return void
	 * @throws
	 */
	public static void parReqWsData(ByteBuffer reqBuffer,WebSocketImpl ws){
		//这里获取到对应的二进制码
		byte[] reqByte=parseByteBuffer2Byte(reqBuffer);
		//获取对应的接收到的串
		String reqHex=parseByte2HexStr(reqByte);
		//然后获取对应的返回的16进制数组做后面的拆分工作
		String[] reqHexArray=parseStrToArray(reqHex);
		//判断是否是心跳包数据
		String equipTypeVersion=reqHexArray[0]+reqHexArray[1];//设备类型版本，例如  1111 2位组成
		String equipCode=reqHexArray[2]+reqHexArray[3];//设备编码，例如8604 2位组成 通过页面终端等设定
		String equipGrop=reqHexArray[4];//设备组号，例如01
		String equipPwd=reqHexArray[5]+reqHexArray[6];//设备密码，例如1234
		String packSerial=reqHexArray[7];//包序号，例如BC
		//以上信息为发送时特定的数据值
		String dataLength=reqHexArray[8]+reqHexArray[9];//数据长度，例如00 12
		String dataALength=reqHexArray[10];//数据1长度，例如09
		String dataAOrderCode=reqHexArray[11];//命令编码00表示心跳包 检测主要检测这个值的
		//开始封装对应的BaseData数据
		BaseData baseData=new BaseData(equipTypeVersion, equipCode, equipGrop, equipPwd, packSerial,dataLength,
				dataALength, dataAOrderCode);
		
		//查询对应的门禁信息
		//基于报警的equipCode得到门禁编码
		TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
		if(tSendMsgSocketBO.getAccessCodeFromEquipCode(equipCode)==null)
			return ;
		String accessCode=tSendMsgSocketBO.getAccessCodeFromEquipCode(equipCode).getAccessCode();
		
		//命令编码00表示心跳包 检测主要检测这个值的
		if (dataAOrderCode.equalsIgnoreCase("00")) {
			String aData8=reqHexArray[20];//这里要检测各个防区的故障状态
			byte[] defenceByte=parseHexStr2Byte(aData8);
			int[] defenceInt=reverIntArray(getIntArray(defenceByte[0]));
			EquipConInfo equipConInfo=new EquipConInfo(ws, defenceInt, baseData, (new Date().getTime()));
			EQUIP_IN_CON.put(equipCode, equipConInfo);//通过这个查看各个设备的信息
			//如果当前门磁没有加入，进行初始化操作
			if (EQUIP_STATE.get(equipCode)==null) {
				EquipStateData equipStateData=new EquipStateData(equipCode, 0);
				EQUIP_STATE.put(equipCode, equipStateData);
			}
			//门锁相关信息处理
			if (ACCESS_OPEN.get(equipCode)!=null) {
				long lastOpenTime=ACCESS_OPEN.get(equipCode);
				//标识长时间未关闭进行预警处理
				if ((new Date().getTime()/1000-lastOpenTime/1000)>ACCESSOPENWARN) {
					
					RailAccessWarn railAccessWarn=new RailAccessWarn(accessCode, "2", "长时间未关闭", "0", new Date());
					RailAccessWarnDao railAccessWarnDao = (RailAccessWarnDao) ContextUtil.getBean("RailAccessWarnDao");
					railAccessWarnDao.add(railAccessWarn);
					
					//长时间未关闭进行报警
					//首先先判断当前设备是否连接的上
					if (PubUtil.wsChannelIsAvailable(equipCode)) {
						String accessOrder=PubUtil.wsGetElectricOrder(equipCode, 0);
						int wirteNum= PubUtil.wsWriteDataFromChannel(equipCode, accessOrder);
						if (wirteNum>0) {
							System.out.println("equipCode:"+equipCode+",发送警报成功，缘由长时间未关闭！");
						}
					}
					
					//然后清空对应的值
					ACCESS_OPEN.remove(equipCode);
				}
			}
			
			
		}else if (dataAOrderCode.equalsIgnoreCase("01")) {//这里监控的是警情
			//5CC11BA7
			String timeStamp=reqHexArray[12]+reqHexArray[13]+reqHexArray[14]+reqHexArray[15];//时间戳
			//004A
			String eventSerial=reqHexArray[16]+reqHexArray[17];//事件号
			//01
			String defenceArea=reqHexArray[18];//防区
			//1110
			String cid=reqHexArray[19]+reqHexArray[20];;//对应的CID码
			//73
			String xor=reqHexArray[21];//xor异或码
			WarnData warnData=new WarnData(baseData, timeStamp, eventSerial, defenceArea, cid, xor);
			//这里开始对warnData做数据处理了
			if (defenceArea.equalsIgnoreCase("06")) {
				if (cid.equalsIgnoreCase("1134")) {//开门
					EquipStateData equipStateData=new EquipStateData(equipCode, 1);
					EQUIP_STATE.put(equipCode, equipStateData);
					//判断是否是工单期间的开门信息
//					RailWorkInfoDao railWorkInfoDao=(RailWorkInfoDao)ContextUtil.getBean("RailWorkInfoDao");
//					List<RailWorkInfo> railWorkInfos=railWorkInfoDao.getByParam(equipCode);
					
//					if (railWorkInfos.size()==0) {//没有工单信息
					//门锁相关信息处理
					
					if (ACCESS_OPEN.get(equipCode)==null) {
						RailAccessWarn railAccessWarn = new RailAccessWarn(accessCode, "1", "门禁意外打开", "0",new Date());
						RailAccessWarnDao railAccessWarnDao = (RailAccessWarnDao) ContextUtil.getBean("RailAccessWarnDao");
						railAccessWarnDao.add(railAccessWarn);
						//TODO snedsocket(railAccessWarn.getId,4)
						//开门记录
						RailAccessOptlog railAccessOptlog=new RailAccessOptlog();
						railAccessOptlog.setAccessCode(accessCode);
						railAccessOptlog.setEquipCode(equipCode);
						railAccessOptlog.setOpenSign("3");
						railAccessOptlog.setAddDate(new Date());
						T120101BO t120101BO=(T120101BO) ContextUtil.getBean("T120101BO");
						t120101BO.saveRailAccessOptlog(railAccessOptlog);	
						
						//首先先判断当前设备是否连接的上
						if (PubUtil.wsChannelIsAvailable(equipCode)) {
							String accessOrder=PubUtil.wsGetElectricOrder(equipCode, 0);
							int wirteNum= PubUtil.wsWriteDataFromChannel(equipCode, accessOrder);
							if (wirteNum>0) {
								System.out.println("equipCode:"+equipCode+",发送警报成功，缘由门禁意外打开！");
							}
						}
						
					}else {
						long lastOpenTime=ACCESS_OPEN.get(equipCode);
						if((new Date().getTime()/1000-lastOpenTime/1000)>=ACCESSOPENWARN){
							RailAccessWarn railAccessWarn = new RailAccessWarn(accessCode, "1", "门禁意外打开", "0",new Date());
							RailAccessWarnDao railAccessWarnDao = (RailAccessWarnDao) ContextUtil.getBean("RailAccessWarnDao");
							railAccessWarnDao.add(railAccessWarn);
							//TODO snedsocket(railAccessWarn.getId,4)
							//开门记录
							RailAccessOptlog railAccessOptlog=new RailAccessOptlog();
							railAccessOptlog.setAccessCode(accessCode);
							railAccessOptlog.setEquipCode(equipCode);
							railAccessOptlog.setOpenSign("3");
							railAccessOptlog.setAddDate(new Date());
							T120101BO t120101BO=(T120101BO) ContextUtil.getBean("T120101BO");
							t120101BO.saveRailAccessOptlog(railAccessOptlog);	
							
							//首先先判断当前设备是否连接的上
							if (PubUtil.wsChannelIsAvailable(equipCode)) {
								String accessOrder=PubUtil.wsGetElectricOrder(equipCode, 0);
								int wirteNum= PubUtil.wsWriteDataFromChannel(equipCode, accessOrder);
								if (wirteNum>0) {
									System.out.println("equipCode:"+equipCode+",发送警报成功，缘由门禁意外打开！");
								}
							}
						}
					}
//					}
				}else if (cid.equalsIgnoreCase("3134")) {//关门
					EquipStateData equipStateData=new EquipStateData(equipCode, 0);
					EQUIP_STATE.put(equipCode, equipStateData);
					//已经关门了就清空对应的开门记录信息
					//然后清空对应的值
					ACCESS_OPEN.remove(equipCode);
				}
			}
			//下面判断其他警情情况
			//这里判断是否是火警报警
			//8604主机防区1烟感，防区2烟感，防区3红外，防区4红外，防区5防拆，防区6门磁
			if (defenceArea.equalsIgnoreCase("01")||defenceArea.equalsIgnoreCase("02")) {
				if (cid.equalsIgnoreCase("1110")) {//火警报警
					
					RailAccessWarn railAccessWarn = new RailAccessWarn(accessCode, "3", "火警警报", "0",new Date());
					RailAccessWarnDao railAccessWarnDao = (RailAccessWarnDao) ContextUtil.getBean("RailAccessWarnDao");
					railAccessWarnDao.add(railAccessWarn);
					
					//首先先判断当前设备是否连接的上
					if (PubUtil.wsChannelIsAvailable(equipCode)) {
						String accessOrder=PubUtil.wsGetElectricOrder(equipCode, 0);
						int wirteNum= PubUtil.wsWriteDataFromChannel(equipCode, accessOrder);
						if (wirteNum>0) {
							System.out.println("equipCode:"+equipCode+",发送警报成功，缘由火警警报！");
						}
					}
					
				}else if (cid.equalsIgnoreCase("3110")) {//火警报警 解除
				}
				
			}
			//8604主机防区1烟感，防区2烟感，防区3红外，防区4红外，防区5防拆，防区6门磁
			//防拆预警
			if (defenceArea.equalsIgnoreCase("05")||defenceArea.equalsIgnoreCase("05")) {
				if (cid.equalsIgnoreCase("1144")) {//防拆报警
					
					RailAccessWarn railAccessWarn = new RailAccessWarn(accessCode, "4", "强拆警报", "0",new Date());
					RailAccessWarnDao railAccessWarnDao = (RailAccessWarnDao) ContextUtil.getBean("RailAccessWarnDao");
					railAccessWarnDao.add(railAccessWarn);
					
					//首先先判断当前设备是否连接的上
					if (PubUtil.wsChannelIsAvailable(equipCode)) {
						String accessOrder=PubUtil.wsGetElectricOrder(equipCode, 0);
						int wirteNum= PubUtil.wsWriteDataFromChannel(equipCode, accessOrder);
						if (wirteNum>0) {
							System.out.println("equipCode:"+equipCode+",发送警报成功，缘由强拆警报！");
						}
					}
					
				}else if (cid.equalsIgnoreCase("3144")) {//防拆报警 解除 解除时是否也插入
				}
			}
			
			//8604主机防区1烟感，防区2烟感，防区3红外，防区4红外，防区5防拆，防区6门磁
			//红外报警
			if (defenceArea.equalsIgnoreCase("03")||defenceArea.equalsIgnoreCase("04")) {
				if (cid.equalsIgnoreCase("1131")) {//红外报警
					//首先插入相关信息
					if (LIGHT_OPEN.get(equipCode)!=null) {
						List<Long> lightOpenList=LIGHT_OPEN.get(equipCode);
						lightOpenList.add(new Date().getTime());
						LIGHT_OPEN.put(equipCode, lightOpenList);
					}else {
						List<Long> lightOpenList=new ArrayList<Long>();
						lightOpenList.add(new Date().getTime());
						LIGHT_OPEN.put(equipCode, lightOpenList);
					}
					//判断是否是工单期间的开门信息
					RailWorkInfoDao railWorkInfoDao=(RailWorkInfoDao)ContextUtil.getBean("RailWorkInfoDao");
					List<RailWorkInfo> railWorkInfos=railWorkInfoDao.getByParam(accessCode);
					
					if (railWorkInfos.size()==0) {//没有工单信息
						if (LIGHT_OPEN.get(equipCode) == null) {//这个过程不执行
							List<Long> lightOpenList=new ArrayList<Long>();
							lightOpenList.add(new Date().getTime());
							LIGHT_OPEN.put(equipCode, lightOpenList);
						}else {//判断是否是在一段时间内的开门
							//判断打开的次数
							int lightOpenTimes=LIGHT_OPEN.get(equipCode).size();
							if (lightOpenTimes>=LIGHTNUMWARN) {
								//这里还要判断特定时间内是否出现了开门操作
								if (ACCESS_OPEN.get(equipCode)!=null) {
									long accessOpenTime=ACCESS_OPEN.get(equipCode);
									if ((new Date().getTime()-accessOpenTime)/1000>=ACCESSOPENWARN) {
										RailAccessWarn railAccessWarn = new RailAccessWarn(accessCode, "5", "红外报警", "0",new Date());
										RailAccessWarnDao railAccessWarnDao = (RailAccessWarnDao) ContextUtil.getBean("RailAccessWarnDao");
										railAccessWarnDao.add(railAccessWarn);
										LIGHT_OPEN.remove(equipCode);
										
										//首先先判断当前设备是否连接的上
										if (PubUtil.wsChannelIsAvailable(equipCode)) {
											String accessOrder=PubUtil.wsGetElectricOrder(equipCode, 0);
											int wirteNum= PubUtil.wsWriteDataFromChannel(equipCode, accessOrder);
											if (wirteNum>0) {
												System.out.println("equipCode:"+equipCode+",发送警报成功，缘由红外报警！");
											}
										}
										
									}else {
										LIGHT_OPEN.remove(equipCode);
									}
								}else {//特定时间内没有开门操作，没有开门操作直接报警
									
									RailAccessWarn railAccessWarn = new RailAccessWarn(accessCode, "5", "红外报警", "0",new Date());
									RailAccessWarnDao railAccessWarnDao = (RailAccessWarnDao) ContextUtil.getBean("RailAccessWarnDao");
									railAccessWarnDao.add(railAccessWarn);
									LIGHT_OPEN.remove(equipCode);
									
									//首先先判断当前设备是否连接的上
									if (PubUtil.wsChannelIsAvailable(equipCode)) {
										String accessOrder=PubUtil.wsGetElectricOrder(equipCode, 0);
										int wirteNum= PubUtil.wsWriteDataFromChannel(equipCode, accessOrder);
										if (wirteNum>0) {
											System.out.println("equipCode:"+equipCode+",发送警报成功，缘由红外报警！");
										}
									}
								}
							}else {
								//这里删除对应的红外在三分钟前的所有打开
								List<Long> tmpLightList=LIGHT_OPEN.get(equipCode);
								for (int i = 0; i < tmpLightList.size(); i++) {
									long openTime=tmpLightList.get(i);
									if ((new Date().getTime()-openTime)/1000>=ACCESSOPENWARN) {
										LIGHT_OPEN.get(equipCode).remove(openTime);
									}
								}
								//如果不超过特定时间不预警只增加次数
								List<Long> lightOpenList=LIGHT_OPEN.get(equipCode);
								lightOpenList.add(new Date().getTime());
								LIGHT_OPEN.put(equipCode, lightOpenList);
							}
						}
						
						
					}else{//有工单信息时直接清空
						ACCESS_OPEN.remove(equipCode);
					}
						
					
				}else if (cid.equalsIgnoreCase("3131")) {//红外报警 解除 解除时是否也插入
				}
			}
		}
		
	}
	

	/**
	 * @Description: 从一条指令里面获取对应的机器码 
	 * @param @param order
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String parOderEquipCode(String order){
		//然后获取对应的返回的16进制数组做后面的拆分工作
		String[] reqHexArray=parseStrToArray(order);
		String equipCode=reqHexArray[2]+reqHexArray[3];
		return equipCode;
	}

	/**
	 * @Description: 返回两位组成以为的数组 
	 * @param @param input
	 * @param @return
	 * @return String[]
	 * @throws
	 */
	public static String[] parseStrToArray(String input){
		if (input==null || input.length()<1) 
			return null;
		String[] retStr=new String[input.length()/2];
		for (int i = 0; i < retStr.length; i++) {
			retStr[i]=input.charAt(i*2)+""+input.charAt(i*2+1);
		}
		return retStr;
	}
	/**
	 * @Description: 返回两位组成以为的数组 
	 * @param @param input
	 * @param @return
	 * @return String[]
	 * @throws
	 */
	public static int[] parseHexStrToArray(String inputHex){
		if (inputHex==null ||inputHex.length()<1) 
			return null;
		String[] retStr=parseStrToArray(inputHex);
		int[] retInt=new int[retStr.length];
		for (int i = 0; i < retStr.length; i++) {
			retInt[i]=parseHex2Dec(retStr[i]);
		}
		return retInt;
	}
	/**
	 * @Description: 返回数据异或值 
	 * @param @param inputArray
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String xorArray(int[] inputArray){
		int temp = 0;
		if (inputArray!=null && inputArray.length>0) {
			for (int i = 0; i < inputArray.length; i++) {
				temp ^= inputArray[i];
			}
		}
		return Integer.toHexString(temp).toUpperCase();//返回大写字母
	}
	
	/**
	 * @description 将16进制转换为二进制
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	
	/**
	 * @description 将byteBuffer转换为二进制
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseByteBuffer2Byte(ByteBuffer byteBuffer) {
		byte[] tmpBufferArray=byteBuffer.array();
		byte[] retBytes=new byte[byteBuffer.remaining()];
		for (int i = 0; i < byteBuffer.remaining(); i++) {
			retBytes[i]=tmpBufferArray[i];
		}
		return retBytes;
	}
	/**
	 * @Description: 将一个byte转换成对应的二进制的每一位的数组 
	 * @param @param input
	 * @param @return
	 * @return int[]
	 * @throws
	 */
	public static int[] parseByteToArray(byte input){
		int[] ret=new int[8];
		for (int i = 0; i < 8; i++) {
			ret[i]=((input >> i) & 0x01);
			if (((input >> i) & 0x01) == 1)
				System.out.println("第" + i + "位:" + 1);
			else
				System.out.println("第" + i + "位:" + 0);
		}
		return ret;
	}
	
	/**
	 * @description 将二进制转换成16进制
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	
	/**
	 * 字符串转换成为16进制(无需Unicode编码)
	 * @param str
	 * @return
	 */
	public static String str2HexStr(String str) {
	    char[] chars = "0123456789ABCDEF".toCharArray();
	    StringBuilder sb = new StringBuilder("");
	    byte[] bs = str.getBytes();
	    int bit;
	    for (int i = 0; i < bs.length; i++) {
	        bit = (bs[i] & 0x0f0) >> 4;
	        sb.append(chars[bit]);
	        bit = bs[i] & 0x0f;
	        sb.append(chars[bit]);
	        // sb.append(' ');
	    }
	    return sb.toString().trim();
	}
	
	/**
	 * @Description: 将16进制数转换成10进制 
	 * @param @param hex
	 * @param @return
	 * @return int
	 * @throws
	 */
	public static int parseHex2Dec(String valueHex){
		return Integer.parseInt(valueHex,16);
	}
	/**
	 * @Description: 将10进制数转换成16进制 
	 * @param @param valueTen
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String parseDec2Hex(int valueTen){
		return Integer.toHexString(valueTen);
	}
	//-----------------------组装对应的控制板指令----------------------
	/**
	 * @Description: 通过channel向对应的client连接端发送数据请求 
	 * @param @param hexData
	 * @param @param ws
	 * @param @return
	 * @return int
	 * @throws
	 */
	
	public static int wsWriteDataFromChannel(String hexData,WebSocketImpl ws){
//		hexData="11118607ff12341700050486030001CA";//开门禁
//		hexData="11118607FF12341700050486030000CB";//关门禁
		int retWrite=0;
		byte[] hexArray= parseHexStr2Byte(hexData);
		ByteBuffer tmpBuffer=ByteBuffer.wrap(hexArray);
		try {
			retWrite=ws.getChannel().write(tmpBuffer);
			System.out.println("The number of bytes written, possibly zero:"+retWrite);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retWrite;
	}
	
	/**
	 * @Description: 发送特殊指令 
	 * @param @param equipCode 设备号
	 * @param @param hexData 要发送的hexData
	 * @param @return
	 * @return int
	 * @throws
	 */
	public static int wsWriteDataFromChannel(String equipCode,String hexData){
		int retWrite=0;
		if (wsChannelIsAvailable(equipCode)) {
			EquipConInfo equipConInfo=EQUIP_IN_CON.get(equipCode);
			WebSocketImpl wSocketImpl=equipConInfo.getWebSocketImpl();
			byte[] hexArray= parseHexStr2Byte(hexData);
			ByteBuffer tmpBuffer=ByteBuffer.wrap(hexArray);
			try {
				retWrite=wSocketImpl.getChannel().write(tmpBuffer);
				System.out.println("The number of bytes written, possibly zero:"+retWrite);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return retWrite;
	}
	
	/**
	 * @Description: 发送指令之前判定当前指令是否可执行 
	 * @param @param equipCode
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean wsChannelIsAvailable(String equipCode){
		boolean flag=false;
		EquipConInfo equipConInfo=EQUIP_IN_CON.get(equipCode);
		if (equipConInfo!=null) {
			WebSocketImpl wSocketImpl=equipConInfo.getWebSocketImpl();
			if (wSocketImpl.getChannel().isOpen()) {
				flag=true;
			}
//			flag=true;
		}
		return flag;
	}
	/**
	 * @Description: 得到开关门禁的指令 在调用这个方法之前要先判定设备的可用性
	 * @param @param equipCode
	 * @param @param openType 0标识开；1标识关
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String wsGetAccessOrder(String equipCode,int openType){
		EquipConInfo equipConInfo=EQUIP_IN_CON.get(equipCode);
		BaseData baseData=equipConInfo.getBaseData();
		String equipOrder=baseData.getBaseHeaderData()+"000504860300";
		if (openType==0) {
			equipOrder+="01";
		}else if (openType==1) {
			equipOrder+="00";
		}
		int[] equipOrderInt=parseHexStrToArray(equipOrder);
		String xorData=xorArray(equipOrderInt);
		return equipOrder+xorData;
	}
	/**
	 * @Description: 得到开关继电器的指令 在调用这个方法之前要先判定设备的可用性
	 * @param @param equipCode
	 * @param @param openType 0标识开；1标识关
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String wsGetElectricOrder(String equipCode,int openType){
		EquipConInfo equipConInfo=EQUIP_IN_CON.get(equipCode);
		BaseData baseData=equipConInfo.getBaseData();
		String equipOrder=baseData.getBaseHeaderData()+"000504860200";
		if (openType==0) {
			equipOrder+="21";
		}else if (openType==1) {
			equipOrder+="20";
		}
		int[] equipOrderInt=parseHexStrToArray(equipOrder);
		String xorData=xorArray(equipOrderInt);
		return equipOrder+xorData;
	}
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
//		int tmp[]=new int[]{0x11,0x11,0x86,0x07,0xFF,0x12,0x34,0x17,0x00,0x05,0x04,0x86,0x03,0x00,0x01};
//		System.out.println(xorArray(tmp));
//		byte[] tmp=parseHexStr2Byte("02");
//		byte tmpByte=tmp[0];
//		String tmpStr="";
//		for (int i = 0; i < 8; i++) {
//			if (((tmpByte >> i) & 0x01) == 1)
//				System.out.println("第" + i + "位:" + 1);
//			else
//				System.out.println("第" + i + "位:" + 0);
//		}
		
//		int[] output=parseByteToArray(tmpByte);
//		System.out.println(output.toString());
//		
//		System.out.println(Integer.toHexString(11));
//		System.out.println(0x11);
//		System.out.println(str2HexStr("FF"));
//		
//		char str="12345".charAt(3);
//		System.out.println("12345");
		
//		int[] tmp=new int[]{0x11,0x11,0x86,0x07,0xFF,0x12,0x34,0x17,0x00,0x05,0x04,0x86,0x02,0x00,0x21};
//		System.out.println(xorArray(tmp));
//		int[] tmp1=new int[]{0x11,0x11,0x86,0x07,0xFF,0x12,0x34,0x17,0x00,0x05,0x04,0x86,0x02,0x00,0x20};
//		System.out.println(xorArray(tmp1));
//		int[] tmp2=new int[]{0x11,0x11,0x86,0x07,0xFF,0x12,0x34,0x23,0x00,0x05,0x04,0x81,0x00,0x29,0x0A};
//		System.out.println(xorArray(tmp2));
//		System.out.println(new Date().getTime());
//		System.out.println(new Date(1556068055393l));
//		System.out.println(new Date(parseHex2Dec("5CBFB2E9")*1000l));
//		int[] tmp3=new int[]{0x11,0x11,0x86,0x07,0xFF,0x12,0x34,0x23,0x00,0x02,0x01,0x82};
//		System.out.println(xorArray(tmp3));
//		int[] tmp4=new int[]{0x11,0x11,0x86,0x07,0xFF,0x12,0x34,0x23,0x00,0x05,0x04,0x86,0x01,0x00,0x3F};
//		System.out.println(xorArray(tmp4));
//		int[] tmp5=new int[]{0x11,0x11,0x86,0x07,0xFF,0x12,0x34,0x23,0x00,0x05,0x04,0x86,0x01,0x00,0x00};
//		System.out.println(xorArray(tmp5));

//		String tmpString="3A";
//		byte[] defenceByte=parseHexStr2Byte(tmpString);
//		int[] tmp=getIntArray(defenceByte[0]);
//		int[] defenceInt=reverIntArray(tmp);
//		System.out.println(defenceInt[6]);
		
//		String tmpString="11118607FF12341700050486030001";
//		int tmp[]=parseHexStrToArray(tmpString);
//		System.out.println(xorArray(tmp));
		System.out.println(new Date(new Date().getTime()-180000l));
	}

 
    /**
     * @Description: 将byte转换为一个长度为8的byte数组，数组每个值代表bit  
     * @param @param b
     * @param @return
     * @return byte[]
     * @throws
     */
    public static byte[] getBitArray(byte b) {  
        byte[] array = new byte[8];  
        for (int i = 7; i >= 0; i--) {  
            array[i] = (byte)(b & 1);  
            b = (byte) (b >> 1);  
        }  
        return array;  
    }  
    /**
     * @Description: 将byte转换为一个长度为8的byte数组，数组每个值代表bit  
     * @param @param b
     * @param @return
     * @return byte[]
     * @throws
     */
    public static int[] getIntArray(byte b) {  
    	int[] array = new int[8];  
    	for (int i = 7; i >= 0; i--) {  
    		array[i] = (byte)(b & 1);  
    		b = (byte) (b >> 1);  
    	}
    	return array;  
    }  
    /**
     * @Description: 反向输出一个数组 
     * @param @param input
     * @param @return
     * @return int[]
     * @throws
     */
    public static int[] reverIntArray(int[] input){
    	int[] outPut=new int[input.length];
    	for (int i = 0; i < outPut.length; i++) {
    		outPut[i]=input[input.length-1-i];
		}
    	return outPut;
    }
    /**
     * @Description: TODO 
     * @param @param b
     * @param @return
     * @return String
     * @throws
     */
    public static String parseByteToBit(byte b) {  
        return ""  
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)  
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)  
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)  
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);  
    }   
    /**
     * @Description: 将二进制值转换成对应的byte值 
     * @param @param byteStr
     * @param @return
     * @return byte
     * @throws
     */
    public static byte parseStrBitToByte(String byteStr) {  
        int re, len;  
        if (null == byteStr) {  
            return 0;  
        }  
        len = byteStr.length();  
        if (len != 4 && len != 8) {  
            return 0;  
        }  
        if (len == 8) {// 8 bit处理  
            if (byteStr.charAt(0) == '0') {// 正数  
                re = Integer.parseInt(byteStr, 2);  
            } else {// 负数  
                re = Integer.parseInt(byteStr, 2) - 256;  
            }  
        } else {// 4 bit处理  
            re = Integer.parseInt(byteStr, 2);  
        }  
        return (byte) re;  
    } 
}
