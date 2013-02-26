package com.eds.servlets;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.eds.Response.EDSAPI;
import com.eds.bean.ApiErrorMessage;
import com.eds.bean.ResultsList;
import com.eds.bean.SessionToken;
import com.eds.tokenManager.SessionTokenManager;

public class Search extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String lookfor;
    private String type;
    private String end_point;
    private String messageFormat = "";

    @SuppressWarnings("deprecation")
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {

	HttpSession session = request.getSession();
	lookfor = request.getParameter("lookfor");
	type = request.getParameter("type");
	lookfor = URLDecoder.decode(lookfor);
	ResultsList resultsList = null;

	String query = "";
	lookfor = lookfor.trim();
	String term = lookfor;
	if (term != null && !term.equals("")) {

	    term = term.replace(",", "\\,");
	    term = term.replace(":", "\\:");
	    term = term.replace("(", "\\(");
	    term = term.replace(")", "\\)");

	    String fieldCode = fieldCodeSelect(type);

	    if (fieldCode.equals("")) {
		query = term;
	    } else {
		query = fieldCode + ":" + term;

	    }

	}
	query = URLEncoder.encode(query);

	if (term.equals("")) {
	    RequestDispatcher dispatcher = request
		    .getRequestDispatcher("index.jsp");
	    dispatcher.forward(request, response);
	}

	else {

	    end_point = (String) getServletContext().getAttribute("end_point");
	    messageFormat = (String) getServletContext().getAttribute(
		    "messageFormat");
	    EDSAPI api = new EDSAPI(end_point, messageFormat);
	    SessionTokenManager stm = new SessionTokenManager(request, api);
	    SessionToken sessionToken = stm.ManageSessionToken();
	    try {

		resultsList = api.requestSearch(sessionToken, query);
		if (resultsList.getApierrormessage() != null) {
		    ApiErrorMessage aem = resultsList.getApierrormessage();
		    String errorcode = "";
		    errorcode = aem.getErrorNumber();
		    if (errorcode.equals("109")) {
			sessionToken = api.requestSessionToken();
			resultsList = api.requestSearch(sessionToken, query);
		    }
		}

	    } catch (Exception e) {

		e.printStackTrace();
	    }
	    resultsList.setLookfor(lookfor);
	    resultsList.setType(type);
	    session.setAttribute("resultsList", resultsList);
	    RequestDispatcher dispatcher = request
		    .getRequestDispatcher("resultsList.jsp");
	    dispatcher.forward(request, response);

	}

    }

    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
    }

    protected String fieldCodeSelect(String type) {
	if (type.equals("Author")) {
	    return "AU";
	} else if (type.equals("title")) {
	    return "TI";
	} else if (type.equals("keyword")) {
	    return "";
	} else {
	    return type;
	}
    }
}
