package org.fossa.vaadin.test.testutils;

import java.util.ArrayList;
import java.util.List;

import org.fossa.vaadin.laso.FossaLaso;

public class TestLaso extends FossaLaso {
	
	private static final long serialVersionUID = -8434347212210962273L;
	
	public static final String TEST_STRING = "teststring";
	public static final Long TEST_ID = 99999L;
	public Long testId = TEST_ID;
	public String testString = TEST_STRING;
	public Object testObject = true;
	public boolean triedToWrite = false;
	public boolean constructed = false;

	public TestLaso() {
		constructed = true;
	}

	public String getTestString() {
		return testString;
	}
	
	public void setTestString(String testString) {
		this.testString = testString;
		writeToDatabase();
	}
	
	public Long getId() {
		return testId;
	}

	public void setId(Long testId) {
		this.testId = testId;
		writeToDatabase();
	}
	
	@Override
	protected void writeToDatabase() {
		triedToWrite = true;
	}
	
	public static List<String> getAll() {
		List<String> objectList = new ArrayList<String>();
		objectList.add(TEST_STRING);
		return objectList;
	}

	@Override
	public Object getPojo() {
		return testObject;
	}

}
