package com.huateng.net.bean;



import java.util.Iterator;

import java.util.LinkedHashMap;

import org.jdom.Element;

import java.util.List;

import org.jdom.Attribute;


/**

 * <p>Title: TSA+ Console</p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: Shanghai Huateng Software System</p>

 * @author Zhang Qiang

 * @version 1.0

 */



public class MessageField implements Cloneable {

  private LinkedHashMap attributes = new LinkedHashMap();

  public MessageField() {

  }



  public MessageField(Element e) {

    List attrList = e.getAttributes();

    Attribute attribute = null;

    for (int j = 0; j < attrList.size(); j++) {

      attribute = (Attribute) attrList.get(j);

      setAttribute(attribute.getName().toLowerCase(), attribute.getValue());

    }

  }



  public void setAttribute(String key,String value) {

    attributes.put(key,value);

  }



  public String getAttribute(String key) {

    if(attributes.containsKey(key))

      return attributes.get(key).toString();

    else

      return null;

  }



  public Iterator getKeys() {

    return attributes.keySet().iterator();

  }



  public Iterator getValues() {

    return attributes.values().iterator();

  }



  public Object clone() {

    MessageField obj = null;

    try {

      obj = (MessageField)super.clone();

      obj.attributes = (LinkedHashMap)attributes.clone();

    } catch (CloneNotSupportedException ex) {



    }

    return obj;

  }

}