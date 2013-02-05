package com.epam.task8.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Siarhei_Stsiapanau
 * 
 */
public final class Controller extends HttpServlet {
    private static final long serialVersionUID = -5470968915401047005L;

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	process(request, response);
    }

    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	process(request, response);
    }

    private void process(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	response.setContentType("text/html");
	RequestHelper helper = RequestHelper.getRequestHelper();
	helper.getEmployees(request, response);
	RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/showEmployees.jsp");
	dispatcher.forward(request, response);
    }
}
