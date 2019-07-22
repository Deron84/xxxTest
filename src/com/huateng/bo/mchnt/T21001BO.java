package com.huateng.bo.mchnt;

import com.huateng.po.mchnt.TblUnitPersonBind;
import com.huateng.po.mchnt.TblUnitPersonBindPK;


/**
 * project JSBConsole
 * date 2013-3-11
 * @author 樊东东
 */
public interface T21001BO {

	/**
	 * @param id
	 * @return
	 */
	TblUnitPersonBind get(TblUnitPersonBindPK id);

	/**
	 * @param obj
	 * @return
	 */
	String save(TblUnitPersonBind obj);

	/**
	 * @param existObj
	 * @return
	 */
	String delete(TblUnitPersonBind existObj);

}
