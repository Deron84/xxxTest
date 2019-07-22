package com.huateng.struts.base.action;

import com.huateng.common.Constants;
import com.huateng.dao.common.SqlDao;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/** 城市地区码信息 */
public class T10209Action extends BaseAction {

	private SqlDao dao = (SqlDao) ContextUtil.getBean("sqlDao");

	/**
	 * @return
	 */
	public String update() {
		// TODO Auto-generated method stub

		String nowDate = CommonFunction.getCurrentDateTime();
		String oprId = this.operator.getOprId();
		String findsql1 = "SELECT COUNT(1) FROM TBL_MCHT_BASE_INF WHERE trim(area_no)='" + cityCodeUpd + "'";
		int countArea=dao.queryForInt(findsql1);
		if (countArea != 0) {
			return "该地区"+cityCodeUpd+"正在使用，无法修改";
		}
		String findsql = "SELECT COUNT(1) FROM CST_CITY_CODE WHERE  CITY_NAME='"
				+ cityNameUpd + "' and CITY_CODE_NEW != '" + cityCodeUpd + "'";
		int count = dao.queryForInt(findsql);
		if (count != 0) {
			return "名称" + cityNameUpd + "已存在，请重新输入";
		}

		StringBuffer sb = new StringBuffer("");
		sb.append(
				"UPDATE CST_CITY_CODE SET CITY_NAME="
						+ CommonFunction.getStringWithDYHTwoSide(cityNameUpd)
						+ "MODI_OPR_ID="
						+ CommonFunction.getStringWithDYHTwoSide(oprId)
						+ "MODI_TIME='" + nowDate + "' "
						+ "WHERE TRIM(CITY_CODE_NEW)="
						+ CommonFunction.getStringWithDYHTwoSide(cityCodeUpd))
				.deleteCharAt(sb.lastIndexOf(","));
		dao.execute(sb.toString());

		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return
	 */
	public String delete() {
		// TODO Auto-generated method stub
		String findsql1 = "SELECT COUNT(1) FROM TBL_MCHT_BASE_INF WHERE trim(area_no)='" + cityCodeUpd + "'";
		int countArea=dao.queryForInt(findsql1);
		if (countArea != 0) {
			return "该地区"+cityCodeUpd+"正在使用，无法删除";
		}
		String sql = "DELETE CST_CITY_CODE WHERE TRIM(CITY_CODE_NEW)='"
				+ cityCodeUpd + "'";
		dao.execute(sql);

		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return
	 * 
	 */
	public String add() {
		// TODO Auto-generated method stub

		String nowDate = CommonFunction.getCurrentDateTime();
		String oprId = this.operator.getOprId();
		String findsql = "SELECT COUNT(1) FROM CST_CITY_CODE WHERE CITY_CODE_NEW='"
				+ cityCode + "' or CITY_NAME='" + cityName + "'";
		int count = dao.queryForInt(findsql);
		if (count != 0) {
			return "编号:" + cityCode + "或名称" + cityName + "已存在，请重新输入";
		}
		StringBuffer sb = new StringBuffer("");
		sb.append("INSERT INTO CST_CITY_CODE(CITY_CODE_NEW,CITY_CODE_OLD,MCHT_ADDR,CITY_FLAG,CITY_NAME,INIT_OPR_ID,MODI_OPR_ID,INIT_TIME,MODI_TIME) ");
		sb.append(" VALUES(").append(
				CommonFunction.getStringWithDYHTwoSide(cityCode));
		sb.append(CommonFunction.getStringWithDYHTwoSide("-"));
		sb.append(CommonFunction.getStringWithDYHTwoSide("-"));
		sb.append(CommonFunction.getStringWithDYHTwoSide("0"));
		sb.append(CommonFunction.getStringWithDYHTwoSide(cityName));
		sb.append(CommonFunction.getStringWithDYHTwoSide(oprId));
		sb.append(CommonFunction.getStringWithDYHTwoSide(oprId));
		sb.append(CommonFunction.getStringWithDYHTwoSide(nowDate));
		sb.append(CommonFunction.getStringWithDYHTwoSide(nowDate));
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append(")");
		dao.execute(sb.toString());

		return Constants.SUCCESS_CODE;
	}

	private String cityCodeUpd;

	private String cityNameUpd;

	private String method;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCityCodeUpd() {
		return cityCodeUpd;
	}

	public void setCityCodeUpd(String cityCodeUpd) {
		this.cityCodeUpd = cityCodeUpd;
	}

	public String getCityNameUpd() {
		return cityNameUpd;
	}

	public void setCityNameUpd(String cityNameUpd) {
		this.cityNameUpd = cityNameUpd;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	private String cityCode;

	private String cityName;

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() {
		// TODO Auto-generated method stub
		if ("add".equals(method)) {
			return add();
		}
		if ("delete".equals(method)) {
			return delete();
		}
		if ("update".equals(method)) {
			return update();
		}
		return "无效请求";
	}

}
