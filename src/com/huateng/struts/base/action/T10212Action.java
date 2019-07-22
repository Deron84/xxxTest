package com.huateng.struts.base.action;

import com.huateng.bo.base.T10212BO;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.po.TblTermTpPo;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;

/** 终端类型信息 */
public class T10212Action extends BaseAction {

    private static final long serialVersionUID = 1L;

    private String termNum;
    
    private String termType;

    private String proDescr;
    
    private String descrUpd;
    
    private String termTpUpd;

    private String termNumUpd;
    
    private String termTypeUpd;
    
    private String proDescrUpd;

    private String method;

    private T10212BO t10212BO = (T10212BO) ContextUtil.getBean("T10212BO");

    @Override
    protected String subExecute() {
        // TODO Auto-generated method stub
        try {
            if ("add".equals(method)) {
                return add();
            }
            if ("delete".equals(method)) {
                return delete();
            }
            if ("update".equals(method)) {
                return update();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorCode.T10212_04;
        }
        return "无效请求";
    }

    /**
     * @return
     */
    public String update() throws Exception {
        // TODO Auto-generated method stub
    	String descrUpd1=termTypeUpd+termNumUpd;
    	int countTerm=t10212BO.queryForTermTp(termTpUpd);
        if (countTerm != 0) {
			return "该终端类型正在使用，无法修改";
		}
        int count = t10212BO.queryForInt(descrUpd1);
        if (count != 0) {
        	String termTp=t10212BO.queryTermTp(descrUpd1);
        	if(!termTp.equals(termTpUpd)){
        		return "该终端类型已存在，请重新输入";
        	}
        }
        int updateData = t10212BO.updateData(descrUpd, descrUpd1,termNumUpd,termTypeUpd,proDescrUpd);
        if (updateData > 0) {
            return Constants.SUCCESS_CODE;
        } else {
            return ErrorCode.T10212_02;
        }
    }

    /**
     * @return
     */
    public String delete() throws Exception {
        // TODO Auto-generated method stub
    	int countTerm=t10212BO.queryForTermTp(termTpUpd);
        if (countTerm != 0) {
			return "该终端类型正在使用，无法删除";
		}
        int deleteData = t10212BO.deleteData(termTpUpd);

        if (deleteData > 0) {
            return Constants.SUCCESS_CODE;
        } else {
            return ErrorCode.T10212_03;
        }
    }

    /**
     * @return
     * 
     */
    public String add() throws Exception {
        // TODO Auto-generated method stub
        TblTermTpPo tblTermTpPo = new TblTermTpPo();
        String descr1=termType+termNum;
        tblTermTpPo.setDescr(descr1);
        tblTermTpPo.setTermNum(termNum);
        tblTermTpPo.setTermType(termType);
        tblTermTpPo.setProDescr(proDescr);
        int count = t10212BO.queryForInt(descr1);
        if (count != 0) {
            return "该终端类型已存在，请重新输入";
        }
        int findCountBySQLQuery = t10212BO.findCountBySQLQuery();
        int max = findCountBySQLQuery + 1;
        String term_Tp_string = max + "";
        tblTermTpPo.setTermTp(term_Tp_string);
        int addDate = t10212BO.addDate(tblTermTpPo);
        if (addDate > 0) {
            return Constants.SUCCESS_CODE;
        } else {
            return ErrorCode.T10212_01;
        }
    }

    public String getTermTpUpd() {
        return termTpUpd;
    }

    public void setTermTpUpd(String termTpUpd) {
        this.termTpUpd = termTpUpd;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    public T10212BO getT10212BO() {
        return t10212BO;
    }

    public void setT10212BO(T10212BO t10212bo) {
        t10212BO = t10212bo;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

	public String getTermNum() {
		return termNum;
	}

	public void setTermNum(String termNum) {
		this.termNum = termNum;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getProDescr() {
		return proDescr;
	}

	public void setProDescr(String proDescr) {
		this.proDescr = proDescr;
	}

	public String getTermNumUpd() {
		return termNumUpd;
	}

	public void setTermNumUpd(String termNumUpd) {
		this.termNumUpd = termNumUpd;
	}

	public String getTermTypeUpd() {
		return termTypeUpd;
	}

	public void setTermTypeUpd(String termTypeUpd) {
		this.termTypeUpd = termTypeUpd;
	}

	public String getProDescrUpd() {
		return proDescrUpd;
	}

	public void setProDescrUpd(String proDescrUpd) {
		this.proDescrUpd = proDescrUpd;
	}

	public String getDescrUpd() {
		return descrUpd;
	}

	public void setDescrUpd(String descrUpd) {
		this.descrUpd = descrUpd;
	}

}
