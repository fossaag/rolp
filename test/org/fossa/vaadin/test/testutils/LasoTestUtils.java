package org.fossa.vaadin.test.testutils;

import java.lang.reflect.Method;

public class LasoTestUtils {

	public static boolean checkIfClassHasMatchingGetMethod(Class<?> clazz,	String columnname) {
		String neededMethodename = "get" + (Character.toUpperCase(columnname.charAt(0)) + columnname.substring(1));
		for (Method aMethod : clazz.getMethods()) {
			if (neededMethodename.equals(aMethod.getName())) {
				return true;
			}
		}
		return false;		
	}
}
