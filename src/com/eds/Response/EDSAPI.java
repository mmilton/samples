package com.eds.Response;

import java.io.BufferedReader;
import java.io.IOException;

import com.eds.bean.Record;
import com.eds.bean.Response;
import com.eds.bean.ResultsList;
import com.eds.bean.SessionToken;

public class EDSAPI {

    private String end_point;

    public EDSAPI(String end_point, String messagingFormat) {

	this.end_point = end_point;
	this.messageProcessor = this.getResponseFormat(messagingFormat);
	this.edsApiConnector = new EDSAPIConnector(messageProcessor);
    }

    private EDSAPIConnector edsApiConnector = null;
    private IMessageProcessor messageProcessor = null;

    public SessionToken requestSessionToken() throws IOException {

	BufferedReader reader = edsApiConnector
		.sendCreateSessionRequest(end_point);
	SessionToken sessionToken = messageProcessor.buildSessionToken(reader);

	return sessionToken;
    }

    public ResultsList requestSearch(SessionToken sessionToken, String query) {

	String params = "query=" + query;
	Response response = null;
	ResultsList resultsList = null;

	response = edsApiConnector.sendSearchRequest(end_point, params,
		sessionToken.getSessionToken());
	resultsList = messageProcessor.buildResultsList(response);

	return resultsList;
    }

    public Record requestRetrieve(SessionToken sessionToken, String query)
	    throws IOException {

	String params = query;
	Response response = null;

	response = edsApiConnector.sendRetrieveRequest(end_point, params,
		sessionToken);

	Record record = messageProcessor.buildRecord(response);

	return record;
    }

    private IMessageProcessor getResponseFormat(String format) {
	if (null == format)
	    format = "XML";
	if (0 == format.compareToIgnoreCase("JSON"))
	    return new JSONProcessor();
	else
	    return new XMLProcessor();
    }

}
