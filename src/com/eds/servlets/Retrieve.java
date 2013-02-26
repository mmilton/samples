package com.eds.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.eds.Response.EDSAPI;
import com.eds.bean.Record;
import com.eds.bean.SessionToken;
import com.eds.tokenManager.SessionTokenManager;

public class Retrieve extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {

	String an = request.getParameter("an");
	String dbid = request.getParameter("dbid");
	HttpSession session = request.getSession();
	Record record = null;

	String query = "an=" + an + "&dbid=" + dbid;
	// Get end_point and messageFormat from java servlet application scope
	String end_point = (String) getServletContext().getAttribute(
		"end_point");
	String messageFormat = (String) getServletContext().getAttribute(
		"messageFormat");

	EDSAPI api = new EDSAPI(end_point, messageFormat);

	SessionTokenManager stm = new SessionTokenManager(request, api);
	SessionToken sessionToken = stm.ManageSessionToken();

	try {

	    record = api.requestRetrieve(sessionToken, query);

	}

	catch (Exception e) {

	    e.printStackTrace();
	}

	session.setAttribute("record", record);
	RequestDispatcher dispatcher = request
		.getRequestDispatcher("record.jsp");
	dispatcher.forward(request, response);

    }

    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
    }

}
