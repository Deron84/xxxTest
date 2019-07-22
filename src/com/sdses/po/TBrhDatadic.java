package com.sdses.po;

import java.io.Serializable;

public class TBrhDatadic implements Serializable{

	private static final long serialVersionUID = 1069407131224293284L;
	
	TBrhDatadicPK tBrhDatadicPK;
	
	private String codeBrhId;
	
	public TBrhDatadicPK gettBrhDatadicPK() {
		return tBrhDatadicPK;
	}
	public void settBrhDatadicPK(TBrhDatadicPK tBrhDatadicPK) {
		this.tBrhDatadicPK = tBrhDatadicPK;
	}
	public String getCodeBrhId() {
		return codeBrhId;
	}
	public void setCodeBrhId(String codeBrhId) {
		this.codeBrhId = codeBrhId;
	}
}
