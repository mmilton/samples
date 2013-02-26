package com.eds.helper;

public class StringEscape {

    public static String unEscapeString(String s) {

	s = s.replace("\"", "");
	/*
	 * Here you could escape more special characters
	 */
	return s;
    }
}
