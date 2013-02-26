package com.eds.tokenManager;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.eds.Response.EDSAPI;
import com.eds.bean.SessionToken;

public class SessionTokenManager {

    private HttpServletRequest request;
    private EDSAPI edsapi;

    public SessionTokenManager(HttpServletRequest request, EDSAPI edsapi) {
	super();
	this.request = request;
	this.edsapi = edsapi;

    }

    public SessionToken ManageSessionToken() throws IOException {

	SessionToken sessiontoken = null;
	if (request.getSession().getAttribute("sessiontoken") != null)

	{
	    sessiontoken = (SessionToken) request.getSession().getAttribute(
		    "sessiontoken");

	}

	else {

	    sessiontoken = edsapi.requestSessionToken();
	    request.getSession().setAttribute("sessiontoken", sessiontoken);

	}

	return sessiontoken;

    }

}
