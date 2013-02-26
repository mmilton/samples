package com.eds.helper;

import java.io.InputStream;

public class BuildErrorStream {

    private InputStream error;

    public BuildErrorStream(InputStream error) {
	super();
	this.error = error;
    }

    public String getErrorStream() {

	String ErrorStrem = "";

	try {
	    int data = error.read();
	    while (data != -1) {

		ErrorStrem = ErrorStrem + (char) data;
		data = error.read();

	    }
	    error.close();
	} catch (Exception ex) {
	    try {
		if (error != null) {
		    error.close();
		}
	    } catch (Exception e) {

	    }
	}

	return ErrorStrem;
    }

}
