package com.eds.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.eds.bean.Response;
import com.eds.bean.SessionToken;
import com.eds.helper.BuildErrorStream;

public class EDSAPIConnector {

    private IMessageProcessor _messageProcessor = null;

    public EDSAPIConnector(IMessageProcessor messageProcessor) {
	this._messageProcessor = messageProcessor;
    }

    public BufferedReader sendCreateSessionRequest(String end_point)
	    throws IOException {

	String url = end_point + "/createsession";
	String params = "profile=edsapi&org=&guest=n";
	url = url + "?" + params;
	URL geturl = new URL(url);
	HttpURLConnection connection = (HttpURLConnection) geturl
		.openConnection();
	connection.setRequestProperty("Accept",
		this._messageProcessor.GetContentType());
	BufferedReader reader = new BufferedReader(new InputStreamReader(
		connection.getInputStream()));
	return reader;
    }

    public Response sendSearchRequest(String end_point, String params,
	    String sessionToken) {

	String url = end_point + "/Search";
	url = url + "?" + params;
	URLConnection connection = null;
	BufferedReader reader = null;
	String errorStream = "";
	Response response = new Response();
	String errorNumber = "";

	try {

	    URL geturl = new URL(url);
	    connection = (URLConnection) geturl.openConnection();
	    connection.setRequestProperty("x-sessionToken", sessionToken);
	    connection.setRequestProperty("Accept",
		    this._messageProcessor.GetContentType());
	    reader = new BufferedReader(new InputStreamReader(
		    connection.getInputStream()));

	} catch (IOException ioe) {

	    InputStream error = ((HttpURLConnection) connection)
		    .getErrorStream();

	    try {
		errorNumber = ((HttpURLConnection) connection)
			.getResponseCode() + "";
	    } catch (IOException e) {

		e.printStackTrace();
	    }

	    BuildErrorStream bes = new BuildErrorStream(error);
	    errorStream = bes.getErrorStream();

	}

	response.setErrorStream(errorStream);
	response.setRead(reader);
	response.setErrorNumber(errorNumber);

	return response;

    }

    public Response sendRetrieveRequest(String end_point, String params,
	    SessionToken sessionToken) throws IOException {

	String url = end_point + "/Retrieve";
	url = url + "?" + params;
	URLConnection connection = null;
	BufferedReader reader = null;
	String errorStream = "";
	Response response = new Response();
	String errorNumber = "";

	try {

	    URL geturl = new URL(url);
	    connection = (URLConnection) geturl.openConnection();
	    connection.setRequestProperty("x-sessionToken",
		    sessionToken.getSessionToken());
	    connection.setRequestProperty("Accept",
		    this._messageProcessor.GetContentType());
	    reader = new BufferedReader(new InputStreamReader(
		    connection.getInputStream()));

	} catch (IOException ioe) {

	    InputStream error = ((HttpURLConnection) connection)
		    .getErrorStream();

	    try {
		errorNumber = ((HttpURLConnection) connection)
			.getResponseCode() + "";
	    } catch (IOException e) {

		e.printStackTrace();
	    }

	    BuildErrorStream bes = new BuildErrorStream(error);
	    errorStream = bes.getErrorStream();

	}

	response.setErrorStream(errorStream);
	response.setRead(reader);
	response.setErrorNumber(errorNumber);

	return response;

    }

}
