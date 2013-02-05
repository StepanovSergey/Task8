package com.epam.task8.controller;

import static com.epam.task8.resource.Constant.DEFAULT_ITEMS_PER_PAGE;
import static com.epam.task8.resource.Constant.DEFAULT_PAGE;
import static com.epam.task8.resource.Constant.EMPLOYEE_COUNT_ATTRIBUTE;
import static com.epam.task8.resource.Constant.ITEMS_PER_PAGE_PARAMETER;
import static com.epam.task8.resource.Constant.PAGE_PARAMETER;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.task8.dao.EmployeeJPADao;
import com.epam.task8.dao.IEmployeeDao;
import com.epam.task8.model.Employee;
import com.epam.task8.utils.PageParameters;

/**
 * @author Siarhei_Stsiapanau
 * 
 */
final class RequestHelper {
    private static final RequestHelper requestHelper = new RequestHelper();
    private static final IEmployeeDao DAO = new EmployeeJPADao();
    //private static final IEmployeeDao DAO = new EmployeeJDBCDao();
    private static final String EMPLYEE_LIST_PARAMETER = "employeeList";
    

    private RequestHelper() {
    }

    /**
     * @return the requestHelper
     */
    public static RequestHelper getRequestHelper() {
	return requestHelper;
    }

    public void getEmployees(HttpServletRequest request,
	    HttpServletResponse response) {
	HttpSession session = request.getSession();

	if (session.getAttribute(EMPLOYEE_COUNT_ATTRIBUTE) == null) {
	    long employeeCount = DAO.getEmployeesCount();
	    session.setAttribute(EMPLOYEE_COUNT_ATTRIBUTE, employeeCount);
	}

	int page = PageParameters.getPageParameter(request, PAGE_PARAMETER,
		DEFAULT_PAGE);
	int itemsPerPage = PageParameters.getPageParameter(request,
		ITEMS_PER_PAGE_PARAMETER, DEFAULT_ITEMS_PER_PAGE);
	long startTime = System.currentTimeMillis();
	List<Employee> employeeList = DAO.getEmployees(page, itemsPerPage);
	long endTime = System.currentTimeMillis();
	double time = (endTime - startTime) / 1000D;
	System.out.println("Time for query: " + time);
	session.setAttribute(EMPLYEE_LIST_PARAMETER, employeeList);
    }
}
