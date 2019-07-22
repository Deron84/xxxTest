package com.huateng.po.base;

import java.io.Serializable;


public abstract class BaseTblMenuMsgPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String usageKey;
	private java.lang.String brhId;
	private java.lang.String menuId;
	private java.lang.String menuLevel;
	private java.lang.String menuPreId1;
	private java.lang.String menuPreId2;
	private java.lang.String verId;


	public BaseTblMenuMsgPK () {}
	
	public BaseTblMenuMsgPK (
		java.lang.String usageKey,
		java.lang.String brhId,
		java.lang.String menuId,
		java.lang.String menuLevel,
		java.lang.String menuPreId1,
		java.lang.String menuPreId2,
		java.lang.String verId) {

		this.setUsageKey(usageKey);
		this.setBrhId(brhId);
		this.setMenuId(menuId);
		this.setMenuLevel(menuLevel);
		this.setMenuPreId1(menuPreId1);
		this.setMenuPreId2(menuPreId2);
		this.setVerId(verId);
	}


	/**
	 * Return the value associated with the column: USAGE_KEY
	 */
	public java.lang.String getUsageKey () {
		return usageKey;
	}

	/**
	 * Set the value related to the column: USAGE_KEY
	 * @param usageKey the USAGE_KEY value
	 */
	public void setUsageKey (java.lang.String usageKey) {
		this.usageKey = usageKey;
	}



	/**
	 * Return the value associated with the column: BRH_ID
	 */
	public java.lang.String getBrhId () {
		return brhId;
	}

	/**
	 * Set the value related to the column: BRH_ID
	 * @param brhId the BRH_ID value
	 */
	public void setBrhId (java.lang.String brhId) {
		this.brhId = brhId;
	}



	/**
	 * Return the value associated with the column: MENU_ID
	 */
	public java.lang.String getMenuId () {
		return menuId;
	}

	/**
	 * Set the value related to the column: MENU_ID
	 * @param menuId the MENU_ID value
	 */
	public void setMenuId (java.lang.String menuId) {
		this.menuId = menuId;
	}



	/**
	 * Return the value associated with the column: MENU_LEVEL
	 */
	public java.lang.String getMenuLevel () {
		return menuLevel;
	}

	/**
	 * Set the value related to the column: MENU_LEVEL
	 * @param menuLevel the MENU_LEVEL value
	 */
	public void setMenuLevel (java.lang.String menuLevel) {
		this.menuLevel = menuLevel;
	}



	/**
	 * Return the value associated with the column: MENU_PRE_ID1
	 */
	public java.lang.String getMenuPreId1 () {
		return menuPreId1;
	}

	/**
	 * Set the value related to the column: MENU_PRE_ID1
	 * @param menuPreId1 the MENU_PRE_ID1 value
	 */
	public void setMenuPreId1 (java.lang.String menuPreId1) {
		this.menuPreId1 = menuPreId1;
	}



	/**
	 * Return the value associated with the column: MENU_PRE_ID2
	 */
	public java.lang.String getMenuPreId2 () {
		return menuPreId2;
	}

	/**
	 * Set the value related to the column: MENU_PRE_ID2
	 * @param menuPreId2 the MENU_PRE_ID2 value
	 */
	public void setMenuPreId2 (java.lang.String menuPreId2) {
		this.menuPreId2 = menuPreId2;
	}



	/**
	 * Return the value associated with the column: VER_ID
	 */
	public java.lang.String getVerId () {
		return verId;
	}

	/**
	 * Set the value related to the column: VER_ID
	 * @param verId the VER_ID value
	 */
	public void setVerId (java.lang.String verId) {
		this.verId = verId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.epos.TblMenuMsgPK)) return false;
		else {
			com.huateng.po.epos.TblMenuMsgPK mObj = (com.huateng.po.epos.TblMenuMsgPK) obj;
			if (null != this.getUsageKey() && null != mObj.getUsageKey()) {
				if (!this.getUsageKey().equals(mObj.getUsageKey())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getBrhId() && null != mObj.getBrhId()) {
				if (!this.getBrhId().equals(mObj.getBrhId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getMenuId() && null != mObj.getMenuId()) {
				if (!this.getMenuId().equals(mObj.getMenuId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getMenuLevel() && null != mObj.getMenuLevel()) {
				if (!this.getMenuLevel().equals(mObj.getMenuLevel())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getMenuPreId1() && null != mObj.getMenuPreId1()) {
				if (!this.getMenuPreId1().equals(mObj.getMenuPreId1())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getMenuPreId2() && null != mObj.getMenuPreId2()) {
				if (!this.getMenuPreId2().equals(mObj.getMenuPreId2())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getVerId() && null != mObj.getVerId()) {
				if (!this.getVerId().equals(mObj.getVerId())) {
					return false;
				}
			}
			else {
				return false;
			}
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuilder sb = new StringBuilder();
			if (null != this.getUsageKey()) {
				sb.append(this.getUsageKey().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getBrhId()) {
				sb.append(this.getBrhId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getMenuId()) {
				sb.append(this.getMenuId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getMenuLevel()) {
				sb.append(this.getMenuLevel().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getMenuPreId1()) {
				sb.append(this.getMenuPreId1().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getMenuPreId2()) {
				sb.append(this.getMenuPreId2().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getVerId()) {
				sb.append(this.getVerId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}


}