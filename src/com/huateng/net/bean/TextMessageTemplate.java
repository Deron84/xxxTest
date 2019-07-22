package com.huateng.net.bean;

/**
 * <p>Title: TSA+ Console</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Shanghai Huateng Software System</p>
 * @author Zhang Qiang
 * @version 1.0
 */

import java.util.Iterator;
import java.util.LinkedHashMap;

import org.jdom.Element;

import com.huateng.exception.HuatengException;

/**
 * 交易报文配置Text
 * 
 * @author shen_antonio
 * 
 */
public final class TextMessageTemplate implements Cloneable {

	/**
	 * two lists to store all field information of tita label and tota label,
	 * respectively.
	 */
	private LinkedHashMap titaLabelMap = new LinkedHashMap();

	private LinkedHashMap titaTextMap = new LinkedHashMap();

	private LinkedHashMap totaLabelMap = new LinkedHashMap();

	private LinkedHashMap totaTextMap = new LinkedHashMap();

	private String bus_nm;
	
	private String entityId;

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public TextMessageTemplate(){
		
	}
	/**
	 * @param entityId
	 * @param rootElement
	 * @throws HuatengException
	 */
	public TextMessageTemplate(String entityId,Element rootElement) throws HuatengException {
		
	}

	public Iterator getTitaLabelFieldKeys() {
		return titaLabelMap.keySet().iterator();
	}

	public Iterator getTitaTextFieldKeys() {
		return titaTextMap.keySet().iterator();
	}

	public Iterator getTotaLabelFieldKeys() {
		return totaLabelMap.keySet().iterator();
	}

	public Iterator getTotaTextFieldKeys() {
		return totaTextMap.keySet().iterator();
	}

	
	public MessageField getTitaLabelFieldValue(String id) {
		return (MessageField)titaLabelMap.get(id);
	}

	public MessageField getTitaTextFieldValue(String id) {
		return (MessageField)titaTextMap.get(id);
	}

	public MessageField getTotaLabelFieldValue(String id) {
		return (MessageField)totaLabelMap.get(id);
	}

	public MessageField getTotaTextFieldValue(String id) {
		return (MessageField)totaTextMap.get(id);
	}
	
	public MessageField getTitaFieldValue(String id){
		if(containTitaLabelKey(id)){
			return (MessageField)titaLabelMap.get(id);
		}else{
			return (MessageField)titaTextMap.get(id);
		}
	}
	
	public MessageField getTotaFieldValue(String id){
		if(containTotaLabelKey(id)){
			return (MessageField)totaLabelMap.get(id);
		}else{
			return (MessageField)totaTextMap.get(id);
		}
	}
	
	

	/**
	 * Implements the method defined in its super interface/class
	 * 
	 * @return another duplicate instance of this object.
	 */

	public Object clone() {

		TextMessageTemplate obj = null;

		try {

			obj = (TextMessageTemplate) super.clone();
			obj.titaLabelMap = (LinkedHashMap) titaLabelMap.clone();
			obj.titaTextMap = (LinkedHashMap) titaTextMap.clone();
			obj.totaLabelMap = (LinkedHashMap) totaLabelMap.clone();
			obj.totaTextMap = (LinkedHashMap) totaTextMap.clone();

		} catch (CloneNotSupportedException ex) {

		}

		return obj;

	}

	public String getBus_nm() {
		return bus_nm;
	}

	public void setBus_nm(String bus_nm) {
		this.bus_nm = bus_nm;
	}

	public boolean containTitaLabelKey(String key){
		return this.titaLabelMap.containsKey(key);
	}
	public boolean containTitaTextKey(String key){
		return this.titaTextMap.containsKey(key);
	}
	public boolean containTotaLabelKey(String key){
		return this.totaLabelMap.containsKey(key);
	}
	public boolean containTotaTextKey(String key){
		return this.totaTextMap.containsKey(key);
	}
	
	public boolean containTitaKey(String key){
		if(this.titaLabelMap.containsKey(key)||this.titaTextMap.containsKey(key)){
			return true;
		}
		return false;
	}
	
	public boolean containTotaKey(String key){
		if(this.totaLabelMap.containsKey(key)||this.totaTextMap.containsKey(key)){
			return true;
		}
		return false;
	}
	
	public void putTitaLabelMap(String fldId ,MessageField msgFld){
		titaLabelMap.put(fldId, msgFld);
	}

	public void setTitaLabelMap(LinkedHashMap titaLabelMap) {
		this.titaLabelMap = titaLabelMap;
	}

	public LinkedHashMap getTitaTextMap() {
		return titaTextMap;
	}
	
	public void putTitaTextMap(String fldId ,MessageField msgFld){
		titaTextMap.put(fldId, msgFld);
	}

	public void setTitaTextMap(LinkedHashMap titaTextMap) {
		this.titaTextMap = titaTextMap;
	}

	public LinkedHashMap getTotaLabelMap() {
		return totaLabelMap;
	}
	
	public void putTotaLabelMap(String fldId ,MessageField msgFld){
		totaLabelMap.put(fldId, msgFld);
	}

	public void setTotaLabelMap(LinkedHashMap totaLabelMap) {
		this.totaLabelMap = totaLabelMap;
	}

	public LinkedHashMap getTotaTextMap() {
		return totaTextMap;
	}
	
	public void putTotaTextMap(String fldId ,MessageField msgFld){
		totaTextMap.put(fldId, msgFld);
	}

	public void setTotaTextMap(LinkedHashMap totaTextMap) {
		this.totaTextMap = totaTextMap;
	}

	public LinkedHashMap getTitaLabelMap() {
		return titaLabelMap;
	}
}