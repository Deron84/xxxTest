package com.huateng.bo.accident;

import java.util.Map;


/**
 * project JSBConsole
 * date 2013-4-12
 * @author 樊东东
 */
public interface T90102BO {

	/**审核通过
	 * @param params
	 * @return
	 */
	String accept(Map<String, String> params)throws Exception;

	/**审核拒绝
	 * @param params
	 * @return
	 */
	String refuse(Map<String, String> params)throws Exception;

}
