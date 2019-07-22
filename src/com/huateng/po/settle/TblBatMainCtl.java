package com.huateng.po.settle;




public class TblBatMainCtl {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblBatMainCtl";
	public static String PROP_BAT_PROC_NAME = "BatProcName";
	public static String PROP_RUN_PROC_QT = "RunProcQt";
	public static String PROP_CHD_COUNT = "ChdCount";
	public static String PROP_TASK_START_FLG = "TaskStartFlg";
	public static String PROP_REC_OPR_ID = "RecOprId";
	public static String PROP_BAT_CLS = "BatCls";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_BAT_STATE = "BatState";
	public static String PROP_TASK_ASSIGN_STATE = "TaskAssignState";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_BAT_NOTE = "BatNote";
	public static String PROP_REC_UPD_OPR = "RecUpdOpr";
	public static String PROP_BAT_DSP = "BatDsp";
	public static String PROP_BAT_ID = "BatId";
	public static String PROP_COMMIT_QT = "CommitQt";


	// constructors
	public TblBatMainCtl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public TblBatMainCtl (java.lang.String batId) {
		this.setBatId(batId);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public TblBatMainCtl (
		java.lang.String batId,
		java.lang.String batProcName,
		java.lang.String batCls,
		java.lang.String batState,
		java.lang.String runProcQt,
		java.lang.String commitQt,
		java.lang.Integer chdCount,
		java.lang.String taskAssignState) {

		this.setBatId(batId);
		this.setBatProcName(batProcName);
		this.setBatCls(batCls);
		this.setBatState(batState);
		this.setRunProcQt(runProcQt);
		this.setCommitQt(commitQt);
		this.setChdCount(chdCount);
		this.setTaskAssignState(taskAssignState);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String batId;

	// fields
	private java.lang.String batProcName;
	private java.lang.String batCls;
	private java.lang.String batState;
	private java.lang.String runProcQt;
	private java.lang.String commitQt;
	private java.lang.Integer chdCount;
	private java.lang.String taskAssignState;
	private java.lang.String taskStartFlg;
	private java.lang.String batDsp;
	private java.lang.String batNote;
	private java.lang.String recOprId;
	private java.lang.String recUpdOpr;
	private java.lang.String recCrtTs;
	private java.lang.String recUpdTs;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="BAT_ID"
     */
	public java.lang.String getBatId () {
		return batId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param batId the new ID
	 */
	public void setBatId (java.lang.String batId) {
		this.batId = batId;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: BAT_PROC_NAME
	 */
	public java.lang.String getBatProcName () {
		return batProcName;
	}

	/**
	 * Set the value related to the column: BAT_PROC_NAME
	 * @param batProcName the BAT_PROC_NAME value
	 */
	public void setBatProcName (java.lang.String batProcName) {
		this.batProcName = batProcName;
	}



	/**
	 * Return the value associated with the column: BAT_CLS
	 */
	public java.lang.String getBatCls () {
		return batCls;
	}

	/**
	 * Set the value related to the column: BAT_CLS
	 * @param batCls the BAT_CLS value
	 */
	public void setBatCls (java.lang.String batCls) {
		this.batCls = batCls;
	}



	/**
	 * Return the value associated with the column: BAT_STATE
	 */
	public java.lang.String getBatState () {
		return batState;
	}

	/**
	 * Set the value related to the column: BAT_STATE
	 * @param batState the BAT_STATE value
	 */
	public void setBatState (java.lang.String batState) {
		this.batState = batState;
	}



	/**
	 * Return the value associated with the column: RUN_PROC_QT
	 */
	public java.lang.String getRunProcQt () {
		return runProcQt;
	}

	/**
	 * Set the value related to the column: RUN_PROC_QT
	 * @param runProcQt the RUN_PROC_QT value
	 */
	public void setRunProcQt (java.lang.String runProcQt) {
		this.runProcQt = runProcQt;
	}



	/**
	 * Return the value associated with the column: COMMIT_QT
	 */
	public java.lang.String getCommitQt () {
		return commitQt;
	}

	/**
	 * Set the value related to the column: COMMIT_QT
	 * @param commitQt the COMMIT_QT value
	 */
	public void setCommitQt (java.lang.String commitQt) {
		this.commitQt = commitQt;
	}



	/**
	 * Return the value associated with the column: CHD_COUNT
	 */
	public java.lang.Integer getChdCount () {
		return chdCount;
	}

	/**
	 * Set the value related to the column: CHD_COUNT
	 * @param chdCount the CHD_COUNT value
	 */
	public void setChdCount (java.lang.Integer chdCount) {
		this.chdCount = chdCount;
	}



	/**
	 * Return the value associated with the column: TASK_ASSIGN_STATE
	 */
	public java.lang.String getTaskAssignState () {
		return taskAssignState;
	}

	/**
	 * Set the value related to the column: TASK_ASSIGN_STATE
	 * @param taskAssignState the TASK_ASSIGN_STATE value
	 */
	public void setTaskAssignState (java.lang.String taskAssignState) {
		this.taskAssignState = taskAssignState;
	}



	/**
	 * Return the value associated with the column: TASK_START_FLG
	 */
	public java.lang.String getTaskStartFlg () {
		return taskStartFlg;
	}

	/**
	 * Set the value related to the column: TASK_START_FLG
	 * @param taskStartFlg the TASK_START_FLG value
	 */
	public void setTaskStartFlg (java.lang.String taskStartFlg) {
		this.taskStartFlg = taskStartFlg;
	}



	/**
	 * Return the value associated with the column: BAT_DSP
	 */
	public java.lang.String getBatDsp () {
		return batDsp;
	}

	/**
	 * Set the value related to the column: BAT_DSP
	 * @param batDsp the BAT_DSP value
	 */
	public void setBatDsp (java.lang.String batDsp) {
		this.batDsp = batDsp;
	}



	/**
	 * Return the value associated with the column: BAT_NOTE
	 */
	public java.lang.String getBatNote () {
		return batNote;
	}

	/**
	 * Set the value related to the column: BAT_NOTE
	 * @param batNote the BAT_NOTE value
	 */
	public void setBatNote (java.lang.String batNote) {
		this.batNote = batNote;
	}



	/**
	 * Return the value associated with the column: REC_OPR_ID
	 */
	public java.lang.String getRecOprId () {
		return recOprId;
	}

	/**
	 * Set the value related to the column: REC_OPR_ID
	 * @param recOprId the REC_OPR_ID value
	 */
	public void setRecOprId (java.lang.String recOprId) {
		this.recOprId = recOprId;
	}



	/**
	 * Return the value associated with the column: REC_UPD_OPR
	 */
	public java.lang.String getRecUpdOpr () {
		return recUpdOpr;
	}

	/**
	 * Set the value related to the column: REC_UPD_OPR
	 * @param recUpdOpr the REC_UPD_OPR value
	 */
	public void setRecUpdOpr (java.lang.String recUpdOpr) {
		this.recUpdOpr = recUpdOpr;
	}



	/**
	 * Return the value associated with the column: REC_CRT_TS
	 */
	public java.lang.String getRecCrtTs () {
		return recCrtTs;
	}

	/**
	 * Set the value related to the column: REC_CRT_TS
	 * @param recCrtTs the REC_CRT_TS value
	 */
	public void setRecCrtTs (java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}



	/**
	 * Return the value associated with the column: REC_UPD_TS
	 */
	public java.lang.String getRecUpdTs () {
		return recUpdTs;
	}

	/**
	 * Set the value related to the column: REC_UPD_TS
	 * @param recUpdTs the REC_UPD_TS value
	 */
	public void setRecUpdTs (java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.settle.TblBatMainCtl)) return false;
		else {
			com.huateng.po.settle.TblBatMainCtl tblBatMainCtl = (com.huateng.po.settle.TblBatMainCtl) obj;
			if (null == this.getBatId() || null == tblBatMainCtl.getBatId()) return false;
			else return (this.getBatId().equals(tblBatMainCtl.getBatId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getBatId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getBatId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}