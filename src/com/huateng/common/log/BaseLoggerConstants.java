package com.huateng.common.log;

/**
 * Log4j logger name define
 * @author shen_antonio
 *
 */
public class BaseLoggerConstants {
	/** System Init Log. */
	public static final String SYSTEM_STRAT_UP_LOG = "SystemStartup";
	/**交易日志*/
	public static final String TXN_LOG = "TxnInfoLog";
	
//	log.info("----------"+request.getRemoteAddr()+"--"+"["+CommonFunction.getLocalDateTime14()+" -"+"400001-权限集增加"+"]"+" begin----------");
	public static final String TXN_PREFEX = "----------";
	public static final String TXN_BEGIN_TAIL = "begin----------";
	public static final String TXN_END_TAIL = "end----------";
	public static final String TXN_BETWEEN = "--";
	public static final String TXN_LEFT = "[";
	public static final String TXN_RIGHT = "]";
	public static final String TXN_EQ="=";
	
	public static final String SERVICE_BEGIN_PREFEX="########################## ";
	public static final String SERVICE_BEGIN_TAIL=" Begin ##########################";
	public static final String SERVICE_END_PREFEX="########################## ";
	public static final String SERVICE_END_TAIL=" End ##########################";
	
	public static final String SERVICE_COMP_BEGIN_PREFEX="-------------------------- Service Component:";
	public static final String SERVICE_COMP_BEGIN_TAIL=" Begin --------------------------";
	public static final String SERVICE_COMP_END_PREFEX="-------------------------- Service Component:";
	public static final String SERVICE_COMP_END_TAIL=" End ---------------------";
	
	public static final String TXN_COMP_BEGIN_PREFEX="<<<<<<<<<<<<<<<<<<<<<<<<<< Txn Component:";
	public static final String TXN_COMP_BEGIN_TAIL=" Begin >>>>>>>>>>>>>>>>>>>>>>>>>>";
	public static final String TXN_COMP_END_PREFEX="<<<<<<<<<<<<<<<<<<<<<<<<<< Txn Component:";
	public static final String TXN_COMP_END_TAIL=" End >>>>>>>>>>>>>>>>>>>>>>>>>>";
	
	public static final String COMM_COMP_BEGIN_PREFEX="<<<<<<<<<<<<<<<<<<<<<<<<<< Comm Component:";
	public static final String COMM_COMP_BEGIN_TAIL=" Begin >>>>>>>>>>>>>>>>>>>>>>>>>>";
	public static final String COMM_COMP_END_PREFEX="<<<<<<<<<<<<<<<<<<<<<<<<<< Comm Component:";
	public static final String COMM_COMP_END_TAIL=" End >>>>>>>>>>>>>>>>>>>>>>>>>>";
	
	public static final String TRANCE_INFO_PREFEX = "********************";
	public static final String TRANCE_INFO_TAIL = "********************";
	
	public static String TxnBegin(String addr,String time,String txn){
		return TXN_PREFEX + addr + TXN_BETWEEN + TXN_LEFT+ time + TXN_BETWEEN + txn + TXN_BETWEEN + TXN_RIGHT + TXN_BEGIN_TAIL ;
	}
	public static String TxnEnd(String addr,String time,String txn){
		return TXN_PREFEX + addr + TXN_BETWEEN + TXN_LEFT+ time + TXN_BETWEEN + txn + TXN_BETWEEN + TXN_RIGHT + TXN_END_TAIL ;
	}
	
	public static String TxnDetail(String misc,String value){
		misc= "".equals(misc)?"":misc+":";
		return TXN_PREFEX+misc+TXN_LEFT+value+TXN_RIGHT;
	}
	
	public static String TraceServiceBegin(String serviceId){
		return SERVICE_BEGIN_PREFEX + serviceId + SERVICE_BEGIN_TAIL;
	}	
	public static String TraceServiceEnd(String serviceId){
		return SERVICE_END_PREFEX + serviceId + SERVICE_END_TAIL;
	}	
	public static String TraceServiceCompBegin(String serviceCompId){
		return SERVICE_COMP_BEGIN_PREFEX + serviceCompId + SERVICE_COMP_BEGIN_TAIL;
	}
	public static String TraceServiceCompEnd(String serviceCompId){
		return SERVICE_COMP_END_PREFEX + serviceCompId + SERVICE_COMP_END_TAIL;
	}
	public static String TraceTxnCompBegin(String txnCompId,String phase){
		return TXN_COMP_BEGIN_PREFEX + txnCompId + " Phase:" + phase + TXN_COMP_BEGIN_TAIL;
	}
	public static String TraceTxnCompEnd(String txnCompId,String phase){
		return TXN_COMP_END_PREFEX + txnCompId + " Phase:" + phase + TXN_COMP_END_TAIL;
	}
	public static String TraceCommCompBegin(String commCompId,String phase){
		return TXN_COMP_BEGIN_PREFEX + commCompId + " Phase:" + phase + TXN_COMP_BEGIN_TAIL;
	}
	public static String TraceCommCompEnd(String commCompId,String phase){
		return TXN_COMP_END_PREFEX + commCompId + " Phase:" + phase + TXN_COMP_END_TAIL;
	}
	
	public static String traceInfo(String info){
		return TRANCE_INFO_PREFEX + info + TRANCE_INFO_TAIL;
	}
	
	public static String errorInfo(String info){
		return TRANCE_INFO_PREFEX + info + TRANCE_INFO_TAIL;
	}
	public static String warnInfo(String info){
		return TRANCE_INFO_PREFEX + info + TRANCE_INFO_TAIL;
	}
}
