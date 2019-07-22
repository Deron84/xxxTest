package com.rail.struts.tools.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.huateng.common.Constants;
import com.huateng.common.DataUtil;
import com.huateng.common.ErrorCode;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.JSONBean;
import com.rail.bo.enums.DelStatusEnums;
import com.rail.bo.enums.InWhseEnums;
import com.rail.bo.enums.InfoSignInOutEnums;
import com.rail.bo.enums.ToolsStatusEnums;
import com.rail.bo.pub.PubSearch;
import com.rail.bo.pub.ResultUtils;
import com.rail.bo.tools.T110100BO;
import com.rail.bo.warehouse.T100100BO;
import com.rail.po.tool.RailToolInfo;
import com.rail.po.tool.RailToolProp;
import com.rail.po.warehouse.RailWhseInfo;
import com.sdses.struts.util.MD5Util;
import com.sdses.struts.util.StaticUtils;

/**
 * 
 * ClassName: ToolsAction 
 * @Description: TODO
 * @author syl
 * @date 2019年4月15日
 */
public class T110100Action extends  BaseAction {
	private Log log = LogFactory.getLog(this.getClass());
	
	private Map result;
	
	/**操作员对象*/
    protected Operator operator;
    private int begin;
    private int start;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	private static final long serialVersionUID = 5283736418947840301L;
	
	private Long id;
	private String toolCode;
	private Long toolName;
	private String modelCode;
	private String toolImg;
	private Date toolExpiration;
	
	private String whseCode;
	private String rfid;
	private String purchaseDept;
	private String purchaseUser;
	private long mfrsOrg;
	private String toolStatus;
	private String delStatus;
	private String inWhse;
	private long examPeriod;
	private Date lastExam;
	private String accessCode;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String note1;
	private String note2;
	private String note3;
	private String note4;
	private String note5;
	private String listPrice;
	private String initPrice;
	private String initPrice2;
	private String initPrice3;
	private String dept;
	
	
	private Long toolUnit;
	private String toolMaterial;
	private String stand;
	private String floor;
	private String position;
	
	private List<File> upload; 
    // 上传文件名  
    private String[] uploadFileName; 
    // 上传文件的MIME类型  
    private String[] uploadContentType;  
	
    
    private String toolExpiration1;
    
    private String transferMsg;
    
	
	private T110100BO t110100BO = (T110100BO) ContextUtil.getBean("T110100BO");
	
	@Override
	protected String subExecute() throws Exception {
		System.out.println(getMethod()+"  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		try {
            if ("add".equals(getMethod())) {
                rspCode = add();
            }
        } catch (Exception e) {
        	//e.printStackTrace();
            log("操作员：" + operator.getOprId() + "，对工具进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return SUCCESS;
	}
	/**
	 * 新增工具操作
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author syl
	 * @date 2019年4月16日
	 */
	public String add() {
		
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		if(t110100BO.checkFridPresent(rfid)){
			result =  ResultUtils.fail("工具标签值"+rfid+"已存在");
			return SUCCESS;
		}
		
//		log.info("上传图片开始");
//		String imgPath = "";
//		String format = sdf.format(new Date());
//    	String suffix = null;
//        String fileName = null;
//        String fileName2 = null;
//		if(null!=upload){
//			for (int i = 0; i < upload.size(); i++) {
//				File myfile=upload.get(i);
//				String myfileName=uploadFileName[i];
//				String myfileType=uploadContentType[i];
//				if(myfile.exists()){
//					log.info("--> File Length: " + myfile.length());
//	                log.info("--> File Tyep: " + myfileType);
//	                log.info("--> File Name: " + myfile.getName());
//	                log.info("--> File Original: " + myfileName);
//	                log.info("========================================");
//					String realPath = ServletActionContext.getServletContext().getRealPath("/upload/" + format);
//	                log.info("--> " + realPath);
//	                File dir = new File(realPath);
//	                if (dir.exists()) {
//	                	System.out.println("file exists");
//	                } else {
//	                	System.out.println("file not exists, create it ...");
//	                	try {
//	                        dir.mkdirs();
//	                    } catch (Exception e) {
//	                        e.printStackTrace();
//	                    }    
//	                } 
//	                FileInputStream inputStream=new FileInputStream(myfile);
//	                int lastIndexOf = myfileName.lastIndexOf(".");
//	                suffix = myfileName.substring(lastIndexOf + 1, myfileName.length());
//	                fileName2 = UUID.randomUUID().toString().replaceAll("-", "");
//	                fileName = fileName2 + "." + suffix;
//	                log.info("--> File Suffix Name: " + fileName + "---- old Name: " + myfileName);
//	                File file = new File(realPath, fileName);
//	                FileOutputStream fos = new FileOutputStream(file);
//	                int buffer = 1024; //定义缓冲区的大小
//	                int length = 0;
//	                byte[] b = new byte[buffer];
//	                while ((length = inputStream.read(b)) != -1) {
//	                    fos.write(b, 0, length); //向文件输出流写读取的数
//	                }
//	                fos.close();
//	                
//	                imgPath = "/upload/"+format + fileName;
//	                b = null;
//	                file = null;
//	                inputStream = null;
//	                fos = null;
//				}
//			}
//			
//			log.info("上传图片成功");
//		}
		Date date = new Date();
		log.info("------------------新增工具---------------");
		
        RailToolInfo railToolInfo = new  RailToolInfo();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        railToolInfo.setToolCode(uuid);
//        if(railToolProp!=null){
//        	railToolInfo.setToolName(railToolProp.getToolName());
//        }
//        railToolInfo.setModelCode(modelCode);
//        if(imgPath!=""){
//        	railToolInfo.setToolImg(imgPath);
//        }else{
//        	log.info("更新为上传图片");
//        }
        railToolInfo.setWhseCode(whseCode);
        railToolInfo.setRfid(rfid);
        railToolInfo.setPurchaseDept(purchaseDept);
        railToolInfo.setPurchaseUser(purchaseUser);
        railToolInfo.setMfrsOrg(mfrsOrg);
        railToolInfo.setToolUnit(toolUnit);
        railToolInfo.setToolName(toolName);
        railToolInfo.setToolStatus(String.valueOf(ToolsStatusEnums.NORMAL.getCode()));
        railToolInfo.setDelStatus(String.valueOf(DelStatusEnums.NO.getCode()));
        railToolInfo.setExamPeriod(examPeriod);
        railToolInfo.setAccessCode(accessCode);//门禁
        railToolInfo.setAddDate(date);
        railToolInfo.setAddUser(operator.getOprId());
//        railToolInfo.setInitPrice(initPrice);
//        railToolInfo.setListPrice(listPrice);
        railToolInfo.setDept(dept);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if(toolExpiration1!=null){
			try {
				railToolInfo.setToolExpiration(sdf.parse(toolExpiration1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
        railToolInfo.setStand(stand);
      
        railToolInfo.setFloor(floor);
        railToolInfo.setPosition(position);
        railToolInfo.setToolMaterial(toolMaterial);
        railToolInfo.setInWhse(String.valueOf(InWhseEnums.IN.getCode()));
        railToolInfo.setNote2(note2);
        railToolInfo.setLastExam(new Date());
        result = t110100BO.save(railToolInfo);
		log("添加工具信息成功。操作员编号：" + operator.getOprId());
		 Map<String, Object> returnmap = new HashMap<String, Object>();
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		// result =  ResultUtils.success(1);
		 return SUCCESS;
	}
	
	public String get() throws Exception {
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		RailToolInfo railToolInfo = new  RailToolInfo();
		railToolInfo.setId(id);
		RailToolInfo i = t110100BO.get(railToolInfo);
		result =  ResultUtils.success(i);
		return SUCCESS;
	}
	public String getByToolCode() throws Exception {
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		RailToolInfo railToolInfo = new  RailToolInfo();
		railToolInfo.setToolCode(toolCode);
		RailToolInfo i = t110100BO.getByToolCode(railToolInfo);
		result =  ResultUtils.success(i);
		return SUCCESS;
	}
	public String update() throws Exception {
		if(id==null){
			result = ResultUtils.fail("id 不能为空");
			return SUCCESS;
		}
		if(whseCode!=null && transferMsg==null){
			result = ResultUtils.fail("transferMsg 调库事由不能为空");
			return SUCCESS;
		}
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		RailToolInfo railToolInfo =t110100BO.getById(id);
		railToolInfo.setId(id);
		railToolInfo.setRfid(rfid);
		railToolInfo.setUpdUser(operator.getOprId());
		railToolInfo.setUpdDate(new Date());
		railToolInfo.setDelStatus(delStatus);
		railToolInfo.setTransferMsg(transferMsg);
		if(toolStatus!=null){
			if(t110100BO.checkINOrder(railToolInfo.getToolCode())){
				result = ResultUtils.fail("工具在订单中 不允许停用操作");
				return SUCCESS;
			}
			railToolInfo.setToolStatus(toolStatus);
		}
		railToolInfo.setUpdDate(new Date());
		t110100BO.update(railToolInfo);
		result =  ResultUtils.success(railToolInfo);
		return SUCCESS;
	}
	public String updateWhse() throws Exception {
		if(id==null){
			result = ResultUtils.fail("id 不能为空");
			return SUCCESS;
		}
		if(transferMsg==null){
			result = ResultUtils.fail("transferMsg 调库事由不能为空");
			return SUCCESS;
		}
		if(whseCode ==null){
			result = ResultUtils.fail("whseCode所选仓库不能为空");
			return SUCCESS;
		}
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		RailToolInfo railToolInfo = new  RailToolInfo();
		railToolInfo.setId(id);
		railToolInfo.setTransferMsg(transferMsg);
		railToolInfo.setWhseCode(whseCode);
		railToolInfo.setAddUser(operator.getOprId());
		
		
		result =  t110100BO.updateWhse(railToolInfo);
		return SUCCESS;
	}
	public String updateAll() throws Exception {
		if(id==null){
			result = ResultUtils.fail("id 不能为空");
			return SUCCESS;
		}
		
//		log.info("上传图片开始");
//		String imgPath = "";
//		String format = sdf.format(new Date());
//    	String suffix = null;
//        String fileName = null;
//        String fileName2 = null;
//		if(null!=upload){
//			for (int i = 0; i < upload.size(); i++) {
//				File myfile=upload.get(i);
//				String myfileName=uploadFileName[i];
//				String myfileType=uploadContentType[i];
//				if(myfile.exists()){
//					log.info("--> File Length: " + myfile.length());
//	                log.info("--> File Tyep: " + myfileType);
//	                log.info("--> File Name: " + myfile.getName());
//	                log.info("--> File Original: " + myfileName);
//	                log.info("========================================");
//					String realPath = ServletActionContext.getServletContext().getRealPath("/upload/" + format);
//	                log.info("--> " + realPath);
//	                File dir = new File(realPath);
//	                if (dir.exists()) {
//	                	System.out.println("file exists");
//	                } else {
//	                	System.out.println("file not exists, create it ...");
//	                	try {
//	                        dir.mkdirs();
//	                    } catch (Exception e) {
//	                        e.printStackTrace();
//	                    }    
//	                } 
//	                FileInputStream inputStream=new FileInputStream(myfile);
//	                int lastIndexOf = myfileName.lastIndexOf(".");
//	                suffix = myfileName.substring(lastIndexOf + 1, myfileName.length());
//	                fileName2 = UUID.randomUUID().toString().replaceAll("-", "");
//	                fileName = fileName2 + "." + suffix;
//	                log.info("--> File Suffix Name: " + fileName + "---- old Name: " + myfileName);
//	                File file = new File(realPath, fileName);
//	                FileOutputStream fos = new FileOutputStream(file);
//	                int buffer = 1024; //定义缓冲区的大小
//	                int length = 0;
//	                byte[] b = new byte[buffer];
//	                while ((length = inputStream.read(b)) != -1) {
//	                    fos.write(b, 0, length); //向文件输出流写读取的数
//	                }
//	                fos.close();
//	                
//	                imgPath = "/upload/"+format + fileName;
//	                b = null;
//	                file = null;
//	                inputStream = null;
//	                fos = null;
//				}
//			}
//			
//			log.info("上传图片成功");
//		}
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		RailToolInfo railToolInfo = new  RailToolInfo();
		railToolInfo.setId(id);
		railToolInfo.setRfid(rfid);
		railToolInfo.setUpdUser(operator.getOprId());
		railToolInfo.setUpdDate(new Date());
////		PubSearch pubSearch = (PubSearch) ContextUtil.getBean("PubSearch");
////		Map param = new HashMap<>();
////		param.put("toolModel", modelCode);
//		Map<String, RailToolProp>  get = pubSearch.getToolModleInfo(param);
//		RailToolProp railToolProp = get.get(modelCode);
		
        railToolInfo.setToolCode(toolCode);
        railToolInfo.setWhseCode(whseCode);
        railToolInfo.setRfid(rfid);
        railToolInfo.setPurchaseDept(purchaseDept);
        railToolInfo.setPurchaseUser(purchaseUser);
        railToolInfo.setMfrsOrg(mfrsOrg);
        railToolInfo.setToolStatus(toolStatus);
        log.info("上传图片成功----------------------------------------------");
        log.info(examPeriod);
       
        railToolInfo.setExamPeriod(examPeriod);
        railToolInfo.setAccessCode(accessCode);//门禁
        railToolInfo.setAddDate(new Date());
        railToolInfo.setAddUser(operator.getOprId());
        railToolInfo.setDept(dept);
        railToolInfo.setStand(stand);
        railToolInfo.setNote2(note2);
        railToolInfo.setFloor(floor);
        railToolInfo.setPosition(position);
        railToolInfo.setToolMaterial(toolMaterial);
//        railToolInfo.setInitPrice(initPrice);
//        railToolInfo.setListPrice(listPrice);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if(toolExpiration1!=null){
        	railToolInfo.setToolExpiration(sdf.parse(toolExpiration1));
        }
		t110100BO.updateAll(railToolInfo);
		result =  ResultUtils.success(railToolInfo);
		return SUCCESS;
	}
	/**
	 * 
	 * @Description: 工具停用
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @author syl
	 * @date 2019年4月20日
	 */
	public String stop() throws Exception {
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		RailToolInfo railToolInfo = new  RailToolInfo();
		railToolInfo.setId(id);
		railToolInfo.setToolStatus(String.valueOf(ToolsStatusEnums.STOP.getCode()));
		railToolInfo.setUpdUser(operator.getOprId());
		railToolInfo.setUpdDate(new Date());
		t110100BO.update(railToolInfo);
		result =  ResultUtils.success(railToolInfo);
		return SUCCESS;
	}
	/**
	 * 
	 * @Description: 工具删除
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @author syl
	 * @date 2019年4月20日
	 */
	public String del() throws Exception {
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		RailToolInfo railToolInfo = new  RailToolInfo();
		railToolInfo.setId(id);
		railToolInfo.setToolStatus(String.valueOf(ToolsStatusEnums.DEL.getCode()));
		railToolInfo.setUpdUser(operator.getOprId());
		railToolInfo.setUpdDate(new Date());
		t110100BO.update(railToolInfo);
		result =  ResultUtils.success(railToolInfo);
		return SUCCESS;
	}
	
	public String getToolInfoData() throws Exception{
//		long id = getId();
		RailToolInfo railToolInfo = new RailToolInfo();
		railToolInfo.setId(getId());
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		RailToolInfo railToolInfo2 = t110100BO.get(railToolInfo);
		result= ResultUtils.success(railToolInfo2);
		return SUCCESS;
	}
	
	
	public Map getResult() {
		return result;
	}
	public void setResult(Map result) {
		this.result = result;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToolCode() {
		return toolCode;
	}
	
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}

	public Long getToolName() {
		return toolName;
	}
	public void setToolName(Long toolName) {
		this.toolName = toolName;
	}
	public Long getToolUnit() {
		return toolUnit;
	}
	public void setToolUnit(Long toolUnit) {
		this.toolUnit = toolUnit;
	}
	public String getToolMaterial() {
		return toolMaterial;
	}
	public void setToolMaterial(String toolMaterial) {
		this.toolMaterial = toolMaterial;
	}
	public String getStand() {
		return stand;
	}
	public void setStand(String stand) {
		this.stand = stand;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getToolImg() {
		return toolImg;
	}
	
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public void setToolImg(String toolImg) {
		this.toolImg = toolImg;
	}
	public Date getToolExpiration() {
		return toolExpiration;
	}
	public void setToolExpiration(Date toolExpiration) {
		this.toolExpiration = toolExpiration;
	}
	public String getWhseCode() {
		return whseCode;
	}
	
	public String getToolExpiration1() {
		return toolExpiration1;
	}
	public void setToolExpiration1(String toolExpiration1) {
		this.toolExpiration1 = toolExpiration1;
	}
	public void setWhseCode(String whseCode) {
		this.whseCode = whseCode;
	}
	public String getRfid() {
		return rfid;
	}
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	public String getPurchaseDept() {
		return purchaseDept;
	}
	public void setPurchaseDept(String purchaseDept) {
		this.purchaseDept = purchaseDept;
	}
	public String getPurchaseUser() {
		return purchaseUser;
	}
	public void setPurchaseUser(String purchaseUser) {
		this.purchaseUser = purchaseUser;
	}
	public long getMfrsOrg() {
		return mfrsOrg;
	}
	
	public void setMfrsOrg(long mfrsOrg) {
		this.mfrsOrg = mfrsOrg;
	}
	public String getToolStatus() {
		return toolStatus;
	}
	public void setToolStatus(String toolStatus) {
		this.toolStatus = toolStatus;
	}
	public String getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}
	public String getInWhse() {
		return inWhse;
	}
	public void setInWhse(String inWhse) {
		this.inWhse = inWhse;
	}
	public long getExamPeriod() {
		return examPeriod;
	}
	public void setExamPeriod(long examPeriod) {
		this.examPeriod = examPeriod;
	}
	public Date getLastExam() {
		return lastExam;
	}
	public void setLastExam(Date lastExam) {
		this.lastExam = lastExam;
	}
	public String getAccessCode() {
		return accessCode;
	}
	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getUpdUser() {
		return updUser;
	}
	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}
	public Date getUpdDate() {
		return updDate;
	}
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	public String getNote1() {
		return note1;
	}
	public void setNote1(String note1) {
		this.note1 = note1;
	}
	public String getNote2() {
		return note2;
	}
	public void setNote2(String note2) {
		this.note2 = note2;
	}
	public String getNote3() {
		return note3;
	}
	public void setNote3(String note3) {
		this.note3 = note3;
	}
	public String getNote4() {
		return note4;
	}
	public void setNote4(String note4) {
		this.note4 = note4;
	}
	public String getNote5() {
		return note5;
	}
	public void setNote5(String note5) {
		this.note5 = note5;
	}
	public String getListPrice() {
		return listPrice;
	}
	public void setListPrice(String listPrice) {
		this.listPrice = listPrice;
	}
	public String getInitPrice() {
		return initPrice;
	}
	public void setInitPrice(String initPrice) {
		this.initPrice = initPrice;
	}
	public String getInitPrice2() {
		return initPrice2;
	}
	public void setInitPrice2(String initPrice2) {
		this.initPrice2 = initPrice2;
	}
	public String getInitPrice3() {
		return initPrice3;
	}
	public void setInitPrice3(String initPrice3) {
		this.initPrice3 = initPrice3;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public List<File> getUpload() {
		return upload;
	}
	public void setUpload(List<File> upload) {
		this.upload = upload;
	}
	public String[] getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String[] getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String[] uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getTransferMsg() {
		return transferMsg;
	}
	public void setTransferMsg(String transferMsg) {
		this.transferMsg = transferMsg;
	}
	
	

}

