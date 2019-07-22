package com.huateng.common.select;
/**
 * 
 * Title:SelectOptions Bean
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-12
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class SelectOptionExtractMethod {
	
	private String extractMode;
	

	private SelectStaticMode selectStaticMode;
	
	private SelectSqlMode selectSqlMode;
	
	private SelectDynamicMode selectDynamicMode;
	
	
	
	public String getExtractMode() {
		return extractMode;
	}

	public void setExtractMode(String extractMode) {
		this.extractMode = extractMode;
	}

	public SelectStaticMode getSelectStaticMode() {
		return selectStaticMode;
	}

	public void setSelectStaticMode(SelectStaticMode selectStaticMode) {
		this.selectStaticMode = selectStaticMode;
	}

	public SelectSqlMode getSelectSqlMode() {
		return selectSqlMode;
	}

	public void setSelectSqlMode(SelectSqlMode selectSqlMode) {
		this.selectSqlMode = selectSqlMode;
	}

	public SelectDynamicMode getSelectDynamicMode() {
		return selectDynamicMode;
	}

	public void setSelectDynamicMode(SelectDynamicMode selectDynamicMode) {
		this.selectDynamicMode = selectDynamicMode;
	}
	
}
