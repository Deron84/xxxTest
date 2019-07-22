package com.huateng.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;

/**
 * project JSBConsole date 2013-3-13
 * 
 * @author 樊东东
 */
public class DataUtil {

	/** 将request的参数封装到一个map<String,String>中 多项选择的属性不考虑 */
	public static Map<String, String> getParamMapFromRequest(
			HttpServletRequest request) {

		Map<String, String> paramMap = new HashMap<String, String>();
		Enumeration en = request.getParameterNames();
		while (en.hasMoreElements()) {
			String key = en.nextElement().toString();
			paramMap.put(key, StringUtils.trim(request.getParameter(key).toString()));//已trim
		}
		return paramMap;
	}

	/** javabean的属性的copy */
	public static void beanCopy(Object target, Object source) throws Exception {
		Class targetClass = target.getClass();
		Class sourceClass = source.getClass();
		Field[] fields = targetClass.getDeclaredFields();
		Set<String> targetMethodNames = getClassMethodSet(targetClass);
		Set<String> sourceMethodNames = getClassMethodSet(sourceClass);
		for (Field field : fields) {
			String fullFieldName = getFieldName(field);
			String targetSetMethodName = getSetMethodName(fullFieldName);
			String sourceGetMethodName = getGetMethodName(fullFieldName);
			if (!targetMethodNames.contains(targetSetMethodName)
					|| !sourceMethodNames.contains(sourceGetMethodName)) {
				continue;
			}

			try {
				Class fieldClass = field.getType();
				Method targetFieldSetMethod = targetClass.getMethod(
						targetSetMethodName, fieldClass);
				Method sourceFieldGetMethod = sourceClass
						.getMethod(sourceGetMethodName);

				Object fieldValue;
				try {
					fieldValue = sourceFieldGetMethod.invoke(source);
					targetFieldSetMethod.invoke(target, fieldValue);
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

		}

	}

	/** 将类中方法名封到一个set中 */
	public static Set<String> getClassMethodSet(Class clazz) {
		Set<String> methodNameSet = new HashSet<String>();
		Method[] methods = clazz.getMethods();
		for (Method m : methods) {
			// System.out.println(m.getName());
			methodNameSet.add(m.getName());
		}
		return methodNameSet;
	}

	/** 获取属性名 */
	public static String getFieldName(Field field) {
		String fullFieldName = field.toString();
		int index = fullFieldName.lastIndexOf(".");
		return fullFieldName.substring(index + 1);
	}

	/** 根据属性名获得set方法名称 */
	public static String getSetMethodName(String paramName) {
		return "set" + paramName.substring(0, 1).toUpperCase()
				+ paramName.substring(1);
	}

	/** 根据属性名获得get方法名称 */
	public static String getGetMethodName(String paramName) {
		return "get" + paramName.substring(0, 1).toUpperCase()
				+ paramName.substring(1);
	}

}
