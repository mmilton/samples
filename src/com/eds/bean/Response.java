package com.eds.bean;

import java.io.BufferedReader;

public class Response {

    private BufferedReader read;
    private String errorStream;
    private String errorNumber;

    public String getErrorNumber() {
	return errorNumber;
    }

    public void setErrorNumber(String errorNumber) {
	this.errorNumber = errorNumber;
    }

    public BufferedReader getRead() {
	return read;
    }

    public void setRead(BufferedReader read) {
	this.read = read;
    }

    public String getErrorStream() {
	return errorStream;
    }

    public void setErrorStream(String errorStream) {
	this.errorStream = errorStream;
    }

}
