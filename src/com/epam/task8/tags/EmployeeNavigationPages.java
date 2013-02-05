package com.epam.task8.tags;

import static com.epam.task8.resource.Constant.DEFAULT_ITEMS_PER_PAGE;
import static com.epam.task8.resource.Constant.DEFAULT_PAGE;
import static com.epam.task8.resource.Constant.EMPLOYEE_COUNT_ATTRIBUTE;
import static com.epam.task8.resource.Constant.ITEMS_PER_PAGE_PARAMETER;
import static com.epam.task8.resource.Constant.PAGE_PARAMETER;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.epam.task8.utils.PageParameters;

/**
 * @author Siarhei_Stsiapanau
 * 
 */
public final class EmployeeNavigationPages extends TagSupport {
    private static final long serialVersionUID = -3903303166792275808L;
    private static final int NUMBER_OF_PAGES_IN_CENTER = 5;

    public int doStartTag() throws JspException {
	// Defining variables
	JspWriter out = pageContext.getOut();
	HttpServletRequest request = (HttpServletRequest) pageContext
		.getRequest();
	int page = PageParameters.getPageParameter(request, PAGE_PARAMETER,
		DEFAULT_PAGE);
	int itemsPerPage = PageParameters.getPageParameter(request,
		ITEMS_PER_PAGE_PARAMETER, DEFAULT_ITEMS_PER_PAGE);

	// Creating navigation
	String navPages = doPages(page, itemsPerPage);
	// Writing to page
	try {
	    out.write(navPages);
	} catch (IOException e) {
	    throw new JspException(e.getMessage());
	}

	return SKIP_BODY;
    }

    private String doPages(int page, int itemsPerPage) {
	// Define variables
	StringBuffer buffer = new StringBuffer();
	HttpSession session = pageContext.getSession();
	final long numberOfEmployees = (long) session
		.getAttribute(EMPLOYEE_COUNT_ATTRIBUTE);
	final String uriWithParameter = "?" + PAGE_PARAMETER + "=";
	Long pageCountLong = numberOfEmployees / itemsPerPage;
	pageCountLong = numberOfEmployees % itemsPerPage == 0 ? pageCountLong
		: pageCountLong + 1;
	final int pageCount = Integer.parseInt(pageCountLong.toString());

	int startPageInCenter = page - NUMBER_OF_PAGES_IN_CENTER / 2;
	if (startPageInCenter < 2) {
	    startPageInCenter = 2;
	}
	if (startPageInCenter > (pageCount - NUMBER_OF_PAGES_IN_CENTER)) {
	    startPageInCenter = (pageCount - NUMBER_OF_PAGES_IN_CENTER);
	}
	int lastPageInCenter = startPageInCenter + NUMBER_OF_PAGES_IN_CENTER
		- 1;
	if (lastPageInCenter > pageCount) {
	    lastPageInCenter = pageCount;
	}

	// Start of nav panel
	buffer.append("<div class='nav-pages'><table><tr>");

	if (page != 1) {
	    buffer.append("<td><a href='" + uriWithParameter + (page - 1)
		    + "'>&lt;</a></td>");
	}
	if (page == 1) {
	    buffer.append("<td class='nav-active-page-td'>");
	} else {
	    buffer.append("<td>");
	}
	buffer.append("<a href='" + uriWithParameter + 1 + "'>1</a></td>");

	if (startPageInCenter > 2) {
	    buffer.append("<td>...</td>");
	}

	for (int i = 0; i < NUMBER_OF_PAGES_IN_CENTER; i++) {
	    int pageNumber = startPageInCenter + i;
	    if (pageNumber == page) {
		buffer.append("<td class='nav-active-page-td'>");
	    } else {
		buffer.append("<td>");
	    }
	    buffer.append("<a href='" + uriWithParameter + pageNumber + "'>"
		    + pageNumber + "</a></td>");
	}

	if (lastPageInCenter < pageCount - 1) {
	    buffer.append("<td>...</td>");
	}

	buffer.append("<td><a href='" + uriWithParameter + pageCount + "'>"
		+ pageCount + "</a></td>");
	buffer.append("<td><a href='" + uriWithParameter + (page + 1)
		+ "'>&gt;</a></td>");
	buffer.append("<td><form>");
	buffer.append("Page: <input name='" + PAGE_PARAMETER
		+ "'  type='text' size='1' />");
	buffer.append("&nbsp;Items Per Page: <input name='"
		+ ITEMS_PER_PAGE_PARAMETER + "' type='text' size='1' />");
	buffer.append("<input type='submit' value='Go'>");
	buffer.append("</form></td></tr></table></div>");

	return buffer.toString();
    }
}
