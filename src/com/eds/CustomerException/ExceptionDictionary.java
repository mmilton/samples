package com.eds.CustomerException;

import java.util.HashMap;

public class ExceptionDictionary {

    HashMap<String, String> DictionaryMap = new HashMap<String, String>();

    public ExceptionDictionary() {

	DictionaryMap.put("100", "Unknown Parameter");
	DictionaryMap.put("101", "Incorrect Parameter Format");
	DictionaryMap.put("102", "Invalid Parameter Index");
	DictionaryMap.put("103", "Missing Parameter");
	DictionaryMap.put("104", "Auth Token Invalid");
	DictionaryMap.put("105", "Incorrect Number of Arguments");
	DictionaryMap.put("106", "Unknown Error");
	DictionaryMap.put("107", "Authentication Token Missing");
	DictionaryMap.put("108", "Session Token Missing");
	DictionaryMap.put("109", "Session Token Invalid");
	DictionaryMap.put("111", "Unknown Action");
	DictionaryMap.put("112", "Invalid Argument Value");
	DictionaryMap.put("113", "Error encountered while creating a session");
	DictionaryMap.put("114", "Required data missing");
	DictionaryMap
		.put("116",
			"Duplicate query string parameter found where only one should be specified");
	DictionaryMap.put("119", "Invalid Page Size");
	DictionaryMap.put("120", "Error saving session");
	DictionaryMap.put("121", "Error ending session");
	DictionaryMap.put("123", "Invalid Expander");
	DictionaryMap.put("124", "Invalid Search Mode");
	DictionaryMap.put("125", "Invalid Limiter");
	DictionaryMap.put("126", "Invalid Limiter Value");
	DictionaryMap.put("127", "Unsupported Profile");
	DictionaryMap.put("128", "Profile not supported for Discovery Service");
	DictionaryMap.put("129", "Invalid Content Provider");
	DictionaryMap.put("130", "Invalid Source Type");
	DictionaryMap.put("131", "XSLT Error");
	DictionaryMap.put("132", "Record not found");
	DictionaryMap.put("133", "Simultaneous User Limit Reached");
	DictionaryMap.put("134", "No Guest Access");
	DictionaryMap.put("135", "DbId Not In Profile");
	DictionaryMap.put("136", "Invalid Search View");
	DictionaryMap.put("137", "Error Retrieving Full Text");
	DictionaryMap.put("400", "Bad Request");

    }

    public String findTermFromDictionnary(String key) {

	String value = "";

	if (DictionaryMap.containsKey(key)) {
	    value = DictionaryMap.get(key);

	}

	return value;
    }

}
