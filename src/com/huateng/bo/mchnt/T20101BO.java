package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblMchtBaseInfTmp;

public interface T20101BO {

	public TblMchtBaseInfTmp get(String mchntId);
	List<Object[]> findListByMappingMchntcdOne(String mappingMchntcdOne);
	List<Object[]> findListByMappingMchntcdTwo(String mappingMchntcdTwo);
}
