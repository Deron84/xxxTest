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

public final class LabelMessageTemplate
    implements Cloneable {

  private String entityId = null;

  /**
   * two lists to store all field information of tita label and tota label, respectively.
   */

  private LinkedHashMap fieldMap = new LinkedHashMap();

  public LabelMessageTemplate(){
	  
  }
  

  /**
   * Return boolean 
   * @param key
   * @return
   */
  public boolean containField(String key){
	  return this.fieldMap.containsKey(key);
  }
  
  public MessageField getField(String key){
	  return (MessageField)this.fieldMap.get(key);
  }
  /**
   * Return all tita field keys
   * @return
   */

  public Iterator getFieldKeys() {

    return fieldMap.keySet().iterator();

  }

  public Iterator getFieldValues() {

    return fieldMap.values().iterator();

  }
  
  public void putFieldMap(String fldId,MessageField messageField){
	  this.fieldMap.put(fldId, messageField);
  }

  public int getFieldSize(String key) {

    int value = 0;

    MessageField messageField = null;

    if (fieldMap.containsKey(key)) {

      messageField = (MessageField) fieldMap.get(key);

      value = Integer.parseInt(messageField.getAttribute("size"));

    }

    return value;

  }

  public void setFieldValue(String key, String value) {

    if (!fieldMap.containsKey(key)) {

      return;

    }

    MessageField messageField = (MessageField) fieldMap.get(key);

    messageField.setAttribute("value", value);

  }

  /**
   * Return a certain attribute value of a certain field
   * @param fieldKey
   * @param attributeKey
   * @return null if not find the field or the attribute
   */

  public String getFieldAttributeValue(String fieldKey, String attributeKey) {

    String returnString = null;

    if (fieldMap.containsKey(fieldKey)) {

      MessageField msgFld = (MessageField) fieldMap.get(fieldKey);

      returnString = msgFld.getAttribute(attributeKey);

    }

    return returnString;

  }

  /**
   * Another version of getFieldAttributeValue.
   * This method just return the value of the attribute 'value'.
   */

  public String getFieldAttributeValue(String fieldKey) {

    return getFieldAttributeValue(fieldKey, "value");

  }

  /**
   * Return the total length of the template fields.
   * @return integer
   */

  public int getTemplateFieldLength() {

    int length = 0;

    Iterator it = fieldMap.values().iterator();

    while (it.hasNext()) {

      MessageField field = (MessageField) it.next();

      length += Integer.parseInt(field.getAttribute("size"));

    }

    return length;

  }

  /**
   * Implements the method defined in its super interface/class
   * @return another duplicate instance of this object.
   */

  public Object clone() {

    LabelMessageTemplate obj = null;

    try {

      obj = (LabelMessageTemplate)super.clone();

      obj.fieldMap = (LinkedHashMap) fieldMap.clone();

    }
    catch (CloneNotSupportedException ex) {

    }

    return obj;

  }

public String getEntityId() {
	return entityId;
}

public void setEntityId(String entityId) {
	this.entityId = entityId;
}

public LinkedHashMap getFieldMap() {
	return fieldMap;
}

public void setFieldMap(LinkedHashMap fieldMap) {
	this.fieldMap = fieldMap;
}

}