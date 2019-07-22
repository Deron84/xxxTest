package com.huateng.struts.mchnt.action;

import com.huateng.common.Constants;
import com.huateng.dao.common.HqlDao;
import com.huateng.po.mchnt.TblUnitPersonBind;
import com.huateng.po.mchnt.TblUnitPersonBindPK;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * project JSBConsole date 2013-3-11
 * 
 * @author 樊东东
 */
public class T21001Action extends BaseSupport {
	
	public String add() {
		try {
			TblUnitPersonBindPK id = new TblUnitPersonBindPK(mchtNo, cardNo,
					cardFlag);
			String oprId = getOperator().getOprId();
			String nowTime = CommonFunction.getCurrentDateTime();
			TblUnitPersonBind existObj = (TblUnitPersonBind) dao.get(
					TblUnitPersonBind.class, id);
			if (existObj != null) {
				return returnService("已存在相同商户号、卡号、卡标志的记录，请重新输入");
			}
			TblUnitPersonBind obj = new TblUnitPersonBind();
			obj.setId(id);

			obj.setInitOprId(oprId);
			obj.setModiOprId(oprId);
			obj.setInitTime(nowTime);
			obj.setModiTime(nowTime);
			dao.save(obj);
			rspCode = Constants.SUCCESS_CODE;
			return returnService(rspCode);

		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}

	public String delete() {
		try {
			TblUnitPersonBindPK id = new TblUnitPersonBindPK(mchtNo, cardNo,
					cardFlag);
			TblUnitPersonBind existObj = (TblUnitPersonBind) dao.get(
					TblUnitPersonBind.class, id);
			if (existObj == null) {
				return returnService("没有找到指定商户号、单位卡账号、个人卡账号的记录，请重新选择");
			}
			dao.delete(existObj);
			rspCode = Constants.SUCCESS_CODE;

			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}

	}

	private HqlDao dao = (HqlDao)ContextUtil.getBean("hqlDao");

	/** 商户号 mcht_no */
	private String mchtNo;

	/** 卡号 card_no */
	private String cardNo;

	/** 卡标志card_flag 0-单位卡 1-个人卡 */
	private String cardFlag;

	

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardFlag() {
		return cardFlag;
	}

	public void setCardFlag(String cardFlag) {
		this.cardFlag = cardFlag;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.system.action.BaseSupport#getMsg()
	 */
	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return msg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.system.action.BaseSupport#isSuccess()
	 */
	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return success;
	}

}
