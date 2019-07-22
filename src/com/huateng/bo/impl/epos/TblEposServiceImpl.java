package com.huateng.bo.impl.epos;

import java.util.List;

import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.epos.TblCupBcMapDAO;
import com.huateng.dao.iface.epos.TblFirstPageDAO;
import com.huateng.dao.iface.epos.TblMenuMsgDAO;
import com.huateng.dao.iface.epos.TblPptMsgDAO;
import com.huateng.dao.iface.epos.TblPrtMsgDAO;
import com.huateng.dao.iface.epos.TblRspMsgDAO;
import com.huateng.dao.iface.epos.TblTermTxnDAO;
import com.huateng.dao.iface.epos.TblVerMngDAO;
import com.huateng.po.epos.TblCupBcMap;
import com.huateng.po.epos.TblFirstPage;
import com.huateng.po.epos.TblMenuMsg;
import com.huateng.po.epos.TblMenuMsgPK;
import com.huateng.po.epos.TblPptMsg;
import com.huateng.po.epos.TblPptMsgPK;
import com.huateng.po.epos.TblPrtMsg;
import com.huateng.po.epos.TblPrtMsgPK;
import com.huateng.po.epos.TblRspMsg;
import com.huateng.po.epos.TblRspMsgPK;
import com.huateng.po.epos.TblTermTxn;
import com.huateng.po.epos.TblVerMng;
import com.huateng.po.epos.TblVerMngPK;
import com.huateng.struts.epos.EposConstants;
import com.huateng.system.util.CommonFunction;

public class TblEposServiceImpl implements TblEposService{

	public ICommQueryDAO commQueryDAO;
 	
	public TblFirstPageDAO tblFirstPageDAO;
	
	public TblPrtMsgDAO tblPrtMsgDAO;
	
	public TblMenuMsgDAO tblMenuMsgDAO;
	
	public TblRspMsgDAO tblRspMsgDAO;
	
	public TblPptMsgDAO tblPptMsgDAO;

	public TblTermTxnDAO tblTermTxnDAO;
	
	public TblVerMngDAO tblVerMngDAO;
	
	public TblCupBcMapDAO tblCupBcMapDAO;
	
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	public void setTblTermTxnDAO(TblTermTxnDAO tblTermTxnDAO) {
		this.tblTermTxnDAO = tblTermTxnDAO;
	}

	public void setTblPptMsgDAO(TblPptMsgDAO tblPptMsgDAO) {
		this.tblPptMsgDAO = tblPptMsgDAO;
	}

	public TblFirstPageDAO getTblFirstPageDAO() {
		return tblFirstPageDAO;
	}

	public void setTblFirstPageDAO(TblFirstPageDAO tblFirstPageDAO) {
		this.tblFirstPageDAO = tblFirstPageDAO;
	}

	public TblPrtMsgDAO getTblPrtMsgDAO() {
		return tblPrtMsgDAO;
	}

	public void setTblPrtMsgDAO(TblPrtMsgDAO tblPrtMsgDAO) {
		this.tblPrtMsgDAO = tblPrtMsgDAO;
	}

	public void setTblMenuMsgDAO(TblMenuMsgDAO tblMenuMsgDAO) {
		this.tblMenuMsgDAO = tblMenuMsgDAO;
	}

	public TblRspMsgDAO getTblRspMsgDAO() {
		return tblRspMsgDAO;
	}

	public void setTblRspMsgDAO(TblRspMsgDAO tblRspMsgDAO) {
		this.tblRspMsgDAO = tblRspMsgDAO;
	}

	public void setTblVerMngDAO(TblVerMngDAO tblVerMngDAO) {
		this.tblVerMngDAO = tblVerMngDAO;
	}

	public void setTblCupBcMapDAO(TblCupBcMapDAO tblCupBcMapDAO) {
		this.tblCupBcMapDAO = tblCupBcMapDAO;
	}

	/* 
	 * 保存首页提示信息
	 * @see com.huateng.bo.impl.epos.TblEposService#save(com.huateng.po.epos.TblFirstPage)
	 */
	public String save(TblFirstPage inf) throws Exception {
		tblFirstPageDAO.save(inf);
		return Constants.SUCCESS_CODE;
	}

	/* 
	 * 更新首页提示信息
	 * @see com.huateng.bo.impl.epos.TblEposService#update(com.huateng.po.epos.TblFirstPage)
	 */
	public String updateFirstPage(List<TblFirstPage> list) throws Exception {
		for (TblFirstPage inf : list) {
			tblFirstPageDAO.update(inf);
		}
		return Constants.SUCCESS_CODE;
	}

	/* 
	 * 删除首页提示信息
	 * @see com.huateng.bo.impl.epos.TblEposService#delete(com.huateng.po.epos.TblFirstPage)
	 */
	public String delete(String brhId) throws Exception {
		tblFirstPageDAO.delete(CommonFunction.fillString(brhId, ' ', 10, true));
		return Constants.SUCCESS_CODE;
	}

	/* 
	 * 首页提示信息
	 * @see com.huateng.bo.impl.epos.TblEposService#get()
	 */
	public TblFirstPage get(String brhId) throws Exception {
		
		return tblFirstPageDAO.get(CommonFunction.fillString(brhId, ' ', 10, true));
	}

	/* 
	 * 新增打印模板
	 * @see com.huateng.bo.impl.epos.TblEposService#save(com.huateng.po.epos.TblPrtMsg)
	 */
	public String save(TblPrtMsg inf) throws Exception {
		tblPrtMsgDAO.save(inf);
		return Constants.SUCCESS_CODE;
	}

	/* 
	 * 更新打印模板
	 * @see com.huateng.bo.impl.epos.TblEposService#updatePrtMsg(java.util.List)
	 */
	public String updatePrtMsg(List<TblPrtMsg> list) throws Exception {
		for (TblPrtMsg inf : list) {
			tblPrtMsgDAO.update(inf);
		}
		return Constants.SUCCESS_CODE;
	}

	/* 
	 * 删除打印模板
	 * @see com.huateng.bo.impl.epos.TblEposService#delete(com.huateng.po.epos.TblPrtMsgPK)
	 */
	public String delete(TblPrtMsgPK id) throws Exception {
		tblPrtMsgDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* 
	 * 终端打印模板
	 * @see com.huateng.bo.impl.epos.TblEposService#get(com.huateng.po.epos.TblPrtMsgPK)
	 */
	public TblPrtMsg get(TblPrtMsgPK id) throws Exception {
		return tblPrtMsgDAO.get(id);
	}

	public String save(TblMenuMsg tblMenuMsg) throws Exception {
		tblMenuMsgDAO.save(tblMenuMsg);
		return Constants.SUCCESS_CODE;
	}


	/* 
	 * 新增返回码说明
	 * @see com.huateng.bo.impl.epos.TblEposService#save(com.huateng.po.epos.TblRspMsg)
	 */
	public String save(TblRspMsg inf) throws Exception {
		tblRspMsgDAO.save(inf);
		return Constants.SUCCESS_CODE;
	}

	/* 
	 * 更新返回码说明
	 * @see com.huateng.bo.impl.epos.TblEposService#updateRspMsg(java.util.List)
	 */
	public String updateRspMsg(List<TblRspMsg> list) throws Exception {
		for (TblRspMsg inf : list) {
			tblRspMsgDAO.update(inf);
		}
		return Constants.SUCCESS_CODE;
	}

	/* 
	 * 删除返回码说明
	 * @see com.huateng.bo.impl.epos.TblEposService#delete(com.huateng.po.epos.TblRspMsgPK)
	 */
	public String delete(TblRspMsgPK id) throws Exception {
		tblRspMsgDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* 
	 * 返回码说明
	 * @see com.huateng.bo.impl.epos.TblEposService#get(com.huateng.po.epos.TblRspMsgPK)
	 */
	public TblRspMsg get(TblRspMsgPK id) throws Exception {
		return tblRspMsgDAO.get(id);
	}

	public String save(TblPptMsg tblPptMsg) throws Exception {
		tblPptMsgDAO.save(tblPptMsg);
		return Constants.SUCCESS_CODE;
	}

	public String delete(TblPptMsgPK id) throws Exception {
		tblPptMsgDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public String update(TblPptMsg tblPptMsg) throws Exception {
		tblPptMsgDAO.update(tblPptMsg);
		return Constants.SUCCESS_CODE;
	}

	public TblMenuMsg get(TblMenuMsgPK id) throws Exception {
		return tblMenuMsgDAO.get(id);
	}

	public String update(TblMenuMsg tblMenuMsg) throws Exception {
		tblMenuMsgDAO.update(tblMenuMsg);
		if(tblMenuMsg.getOprId().equals(EposConstants.DISABLE)){
			StringBuilder sql = new StringBuilder("update TBL_MENU_MSG set opr_id='")
				.append(EposConstants.DISABLE)
				.append("' where usage_key='").append(tblMenuMsg.getId().getUsageKey())
				.append("' and brh_id='").append(tblMenuMsg.getId().getBrhId())
				.append("' and ver_id='").append(tblMenuMsg.getId().getVerId())
				.append("' and ( menu_pre_id1='").append(tblMenuMsg.getId().getMenuId()).append("' or menu_pre_id2='")
				.append(tblMenuMsg.getId().getMenuId()).append("')");
			commQueryDAO.excute(sql.toString());
		}
		return Constants.SUCCESS_CODE;
	}

	public String delTermTxn(String id) throws Exception {
		tblTermTxnDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public String save(TblTermTxn tblTermTxn) throws Exception {
		tblTermTxnDAO.save(tblTermTxn);
		return Constants.SUCCESS_CODE;
	}

	public String update(TblTermTxn tblTermTxn) throws Exception {
		tblTermTxnDAO.update(tblTermTxn);
		return Constants.SUCCESS_CODE;
	}

	public TblTermTxn getTermTxn(String id) throws Exception {
		return tblTermTxnDAO.get(id);
	}

	public String getMenuId(String brhId, String menuLevel,
			String verId,String menuPreId1,String menuPreId2) {
		
		
		StringBuffer sb = new StringBuffer("select nvl(max(menu_id),0) from tbl_menu_msg where ");
		sb.append("BRH_ID = '" + brhId + "' ");
		sb.append("and VER_ID = '" + verId + "' ");
		sb.append("and MENU_LEVEL = '" + menuLevel + "' ");
		sb.append("and MENU_PRE_ID1 = '" + menuPreId1 + "' ");
		sb.append("and MENU_PRE_ID2 = '" + menuPreId2 + "' ");
		sb.append("and USAGE_KEY = '" + EposConstants.DEFAULT_USAGE_KEY + "' ");
		
		String menuId = commQueryDAO.findCountBySQLQuery(sb.toString());
		
		int max = Integer.parseInt(menuId.trim()) + 1;
		
		if((menuLevel.equals(EposConstants.MENU_LEVEL_1) || 
				menuLevel.equals(EposConstants.MENU_LEVEL_2)) && max>8){
			return EposConstants.T30505_01;
		}
		if(menuLevel.equals(EposConstants.MENU_LEVEL_3)
				&& max>6)
			return EposConstants.T30505_02;
		return String.valueOf(max);
		
		
//		StringBuffer sql = new StringBuffer("select min(t1.menu_id) from")
//			.append("(select menu_id  from tbl_menu_msg ");
//		
//		StringBuffer where = new StringBuffer("where usage_key = '")
//			.append(EposConstants.DEFAULT_USAGE_KEY).append("' and ver_id='")
//			.append(verId).append("' and brh_id='").append(brhId).append("' and menu_level='")
//			.append(menuLevel).append("' and menu_pre_id1='").append(menuPreId1)
//			.append("' and menu_pre_id2='").append(menuPreId2).append("'"); 
//		
//		sql.append(where).append(") t1 left join (select menu_id  from tbl_menu_msg ")
//			.append(where).append(" and opr_id='").append(EposConstants.OK).append("') t2")
//			.append(" on t1.menu_id=t2.menu_id where t2.menu_id is null");
//		
//		String menuId = commQueryDAO.findCountBySQLQuery(sql.toString());
//		if(menuId == null){
//			sql = new StringBuffer("select max(menu_id)+1 from tbl_menu_msg ")
//				.append(where);
//			menuId = commQueryDAO.findCountBySQLQuery(sql.toString());
//		}
//		else{
//			sql = new StringBuffer("delete from tbl_menu_msg ").append(where)
//				.append(" and opr_id='").append(EposConstants.DISABLE).append("'");
//			commQueryDAO.excute(sql.toString());
//		}
//		int max;
//		if(menuId == null)
//			max = 1;
//		else
//			max = Integer.parseInt(menuId.trim());
//		
//		if((menuLevel.equals(EposConstants.MENU_LEVEL_1) || 
//				menuLevel.equals(EposConstants.MENU_LEVEL_2)) && max>8){
//			return EposConstants.T30505_01;
//		}
//		if(menuLevel.equals(EposConstants.MENU_LEVEL_3)
//				&& max>6)
//			return EposConstants.T30505_02;
//		return String.valueOf(max);
	}

	public String delVerInfo(String brhId, String verId) throws Exception {
		StringBuffer sql = new StringBuffer("select count(1) from TBL_VER_MNG t1 left join tbl_brh_info t2 ")
			.append("on t1.bank_id = t2.cup_brh_id,TBL_TERM_INF t3 where t1.bank_id ='").append(brhId).append("'")
			.append(" and t2.brh_id = t3.term_branch ").append(" and subStr(t3.misc_2,1,4) = '").append(verId).append("'");
		String count = commQueryDAO.findCountBySQLQuery(sql.toString());
		if(count != null && Integer.parseInt(count)>0 )
			return EposConstants.T30500_01;
		//TODO 待定，是否关联的删除
		tblVerMngDAO.delete(new TblVerMngPK(brhId,verId));
		return Constants.SUCCESS_CODE;
	}

	public String save(TblVerMng tblVerMng) throws Exception {
		tblVerMngDAO.save(tblVerMng);
		return Constants.SUCCESS_CODE;
	}

	public TblVerMng getVerInfo(TblVerMngPK pk) throws Exception {
		return tblVerMngDAO.get(pk);
	}
	public int getVerInfoNew(TblVerMngPK pk) throws Exception {
		StringBuffer sql=new StringBuffer("select count(*) from tbl_ver_mng where ver_id='"+pk.getVerId()+"'");
		String count = commQueryDAO.findCountBySQLQuery(sql.toString());
		if(count == null){
			return 0;
		}
		return Integer.parseInt(count);
	}
	public int getTmpId(int pptId, String msgType, String usageKey, String verId)
			throws Exception {
		StringBuffer sql = new StringBuffer("select count(1) from tbl_ppt_msg where USAGE_KEY='")
		.append(usageKey).append("' and PPT_ID=").append(pptId).append(" and MSG_TYPE='").append(msgType)
		.append("' and VER_ID='").append(verId).append("'");
		String count = commQueryDAO.findCountBySQLQuery(sql.toString());
		if(count == null)
			return 0;
		return Integer.parseInt(count)+1;
	}

	public boolean isExist(TblPptMsgPK pk) throws Exception {
		TblPptMsg tblPptMsg = tblPptMsgDAO.get(pk);
		if(tblPptMsg == null)
			return true;
		return false;
	}

	public String save(TblCupBcMap tblCupBcMap) throws Exception {
		tblCupBcMapDAO.save(tblCupBcMap);
		return Constants.SUCCESS_CODE;
	}

	public String delCupBcMap(String cupId) throws Exception {
		TblCupBcMap tblCupBcMap = tblCupBcMapDAO.get(cupId);
		if(tblCupBcMap == null)
			return Constants.ERR_NO_DATA;
		tblCupBcMapDAO.delete(tblCupBcMap);
		return Constants.SUCCESS_CODE;
	}

	public TblCupBcMap getCupBcInfo(String cupId) throws Exception {
		TblCupBcMap tblCupBcMap = tblCupBcMapDAO.get(cupId);
		return tblCupBcMap;
	}

	public String updateCupBcMap(List<TblCupBcMap> list) throws Exception {
		for (TblCupBcMap inf : list) {
			tblCupBcMapDAO.update(inf);
		}
		return Constants.SUCCESS_CODE;
	}

	public boolean isPreMenuIdExist(String brhId, String menuLevel,
			String verId, String menuId) {
		String count = commQueryDAO.findCountBySQLQuery("select count(1) from tbl_menu_msg where opr_id = '"+EposConstants.OK
				+"' and  usage_key = '"+EposConstants.DEFAULT_USAGE_KEY+"' and ver_id='"
				+verId+"' and brh_id='"+brhId+"' and menu_id='"+menuId+"' and menu_level='"+menuLevel+"'");
		int max = Integer.parseInt(count);
		if(max >= 0)
			return true;
		return false;
	}


}
