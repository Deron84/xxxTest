package com.huateng.bo.mchnt;

import com.huateng.po.TblDivMchnt;
import com.huateng.po.TblDivMchntPK;


/**
 * project JSBConsole
 * date 2013-3-10
 * @author 樊东东
 */
public interface T20703BO {

	/**
	 * @param id
	 * @return
	 */
	TblDivMchnt get(TblDivMchntPK id);


	/**
	 * @param divMcht
	 * @return
	 */
	String save(TblDivMchnt divMcht);


	/**
	 * @param divMcht
	 * @return
	 */
	String delete(TblDivMchnt divMcht);


	/**
	 * @param divMcht
	 * @return
	 */
	String update(TblDivMchnt divMcht);

}
