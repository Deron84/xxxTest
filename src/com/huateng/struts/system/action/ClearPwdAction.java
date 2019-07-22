package com.huateng.struts.system.action;

import com.huateng.dao.iface.base.TblOprInfoDAO;
import com.huateng.po.TblOprInfo;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.Encryption;

public class ClearPwdAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5180242699242868280L;
	/**
	 * 管理员重置其他操作员密码，将其初始为6个1
	 * @author SunnY_Wang
	 */
	//当前操作员id
	private String oprId;
	//需要重置的操作员id
	private String resetOprId;
	
	private TblOprInfoDAO oprInfoDAO;
	public String getOprId() {
		return oprId;
	}
	public void setOprId(String oprId) {
		this.oprId = oprId;
	}
	public String getResetOprId() {
		return resetOprId;
	}
	public void setResetOprId(String resetOprId) {
		this.resetOprId = resetOprId;
	}
	
	private boolean validateDegree(TblOprInfo opr1,TblOprInfo opr2){
		
		return Integer.parseInt(opr1.getOprDegree())<Integer.parseInt(opr2.getOprDegree());
	}
	@Override
	protected String subExecute() throws Exception {
		oprInfoDAO=(TblOprInfoDAO) ContextUtil.getBean("OprInfoDAO");
		oprId=(String) getSessionAttribute("oprId");
		TblOprInfo info=oprInfoDAO.get(oprId);
		//判断当前操作员是否有修改权限
		if(info!=null){
			if(resetOprId.trim().equalsIgnoreCase(oprId)){
				writeErrorMsg("你不可以重置自己的密码，请在修改密码中修改");
				return SUCCESS;
			}
			TblOprInfo resetOpr=oprInfoDAO.get(resetOprId.trim());
			if(resetOpr!=null){
				if(validateDegree(info, resetOpr) || "admin".equals(oprId)){
					resetOpr.setOprPwd(Encryption.encrypt("111111").trim());
					oprInfoDAO.update(resetOpr);
					log("操作员:"+oprId+"重置了其下级的操作员:"+resetOprId+"的密码 ");
					log("操作员"+resetOprId.trim()+"的密码已经重置成功！");
					writeSuccessMsg("操作员"+resetOprId.trim()+"的密码已经重置为“111111”！");
					return SUCCESS;
				}
				writeErrorMsg("你没有权限更改"+resetOprId+"的密码");
				return SUCCESS;
			}
			writeErrorMsg("操作员"+resetOprId+"没有发现");
			return SUCCESS;
			
			
		}
	
		writeErrorMsg("出错了，你可能需要重新登录");
		
		return SUCCESS;
	}
	
}
