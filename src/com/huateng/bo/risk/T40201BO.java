package com.huateng.bo.risk;

import java.io.File;
import java.util.List;

import com.huateng.common.Operator;
import com.huateng.po.TblCtlCardInf;

public interface T40201BO {

	public String add(TblCtlCardInf tblCtlCardInf) throws Exception;
	public String update(TblCtlCardInf tblCtlCardInf) throws Exception;
	public String importFile(List<File> fileList,List<String> fileNameList,Operator operator) throws Exception;
	public TblCtlCardInf get(String key);
	public void delete(String key) throws Exception;
}
