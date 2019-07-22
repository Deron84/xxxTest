/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-10-9       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.po.mchtSrv;

import java.io.Serializable;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-10-9
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class TblPaperSelInfPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String paperId;
	private String quesId;
	private String selOptId;
	private String selMchtId;
	/**
	 * @return the paperId
	 */
	public String getPaperId() {
		return paperId;
	}
	/**
	 * @param paperId the paperId to set
	 */
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
	/**
	 * @return the quesId
	 */
	public String getQuesId() {
		return quesId;
	}
	/**
	 * @param quesId the quesId to set
	 */
	public void setQuesId(String quesId) {
		this.quesId = quesId;
	}
	/**
	 * @return the selOptId
	 */
	public String getSelOptId() {
		return selOptId;
	}
	/**
	 * @param selOptId the selOptId to set
	 */
	public void setSelOptId(String selOptId) {
		this.selOptId = selOptId;
	}
	/**
	 * @return the selMchtId
	 */
	public String getSelMchtId() {
		return selMchtId;
	}
	/**
	 * @param selMchtId the selMchtId to set
	 */
	public void setSelMchtId(String selMchtId) {
		this.selMchtId = selMchtId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paperId == null) ? 0 : paperId.hashCode());
		result = prime * result + ((quesId == null) ? 0 : quesId.hashCode());
		result = prime * result
				+ ((selMchtId == null) ? 0 : selMchtId.hashCode());
		result = prime * result
				+ ((selOptId == null) ? 0 : selOptId.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TblPaperSelInfPK other = (TblPaperSelInfPK) obj;
		if (paperId == null) {
			if (other.paperId != null)
				return false;
		} else if (!paperId.equals(other.paperId))
			return false;
		if (quesId == null) {
			if (other.quesId != null)
				return false;
		} else if (!quesId.equals(other.quesId))
			return false;
		if (selMchtId == null) {
			if (other.selMchtId != null)
				return false;
		} else if (!selMchtId.equals(other.selMchtId))
			return false;
		if (selOptId == null) {
			if (other.selOptId != null)
				return false;
		} else if (!selOptId.equals(other.selOptId))
			return false;
		return true;
	}

	
	
}
