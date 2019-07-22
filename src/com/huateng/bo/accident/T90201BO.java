package com.huateng.bo.accident;

import com.huateng.po.accident.TblEntrustTrade;;

public interface T90201BO {

	public String add(TblEntrustTrade t) ;
	public String update(TblEntrustTrade t) ;
	public String del(String id);
	public TblEntrustTrade get(String id) ;
}
