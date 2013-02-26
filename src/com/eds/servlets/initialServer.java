package com.eds.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class initialServer extends HttpServlet {

    private static final long serialVersionUID = -14380037979438659L;

    private String end_point;
    private String messageFormat;

    public void init(ServletConfig config) throws ServletException {
	super.init(config);
	end_point = getServletConfig().getInitParameter("end_point");
	messageFormat = getServletConfig().getInitParameter("message_format");
    }

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {

	ServletContext application = getServletContext();
	application.setAttribute("end_point", end_point);
	application.setAttribute("messageFormat", messageFormat);
	RequestDispatcher dispatcher = request
		.getRequestDispatcher("index.jsp");
	dispatcher.forward(request, response);

    }

    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
    }

}
