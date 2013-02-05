package com.epam.task8.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Siarhei_Stsiapanau
 * 
 */
public final class PageParameters {

    private PageParameters() {
    }

    public static Integer getPageParameter(HttpServletRequest request,
	    final String PARAMETER_NAME, final Integer DEFAULT_VALUE) {
	HttpSession session = request.getSession();
	String valueString = request.getParameter(PARAMETER_NAME);
	int value;

	if (valueString == null || valueString.isEmpty()) {
	    Integer valueParameter = (Integer) session
		    .getAttribute(PARAMETER_NAME);
	    if (valueParameter == null) {
		value = DEFAULT_VALUE;
	    } else {
		value = valueParameter;
	    }
	} else {
	    value = Integer.parseInt(valueString);
	    session.setAttribute(PARAMETER_NAME, value);
	}
	return value;
    }
}
