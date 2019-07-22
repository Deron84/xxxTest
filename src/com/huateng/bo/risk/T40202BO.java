package com.huateng.bo.risk;

import java.io.File;
import java.util.List;

import com.huateng.common.Operator;
import com.huateng.po.TblCtlMchtInf;

public interface T40202BO {

	public String add(TblCtlMchtInf tblCtlMchtInf) throws Exception;
	public String update(TblCtlMchtInf tblCtlMchtInf) throws Exception;
	public TblCtlMchtInf get(String key);
	public String importFile(List<File> fileList,List<String> fileNameList,Operator operator) throws Exception;
	public void delete(String key) throws Exception;
}
