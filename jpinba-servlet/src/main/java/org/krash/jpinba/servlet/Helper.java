package org.krash.jpinba.servlet;

import org.krash.jpinba.data.JPinbaRequest;

import javax.servlet.ServletRequest;

/**
 * Helper, attaching and extracting JPinbaRequest from Servlet, via custom attribute
 */
abstract public class Helper {

    public static final String ATTRIBUTE_NAME = "org.krash.jpinba.servlet.REQUEST";


    public static void attachRequest(ServletRequest request, JPinbaRequest jpRequest)
    {
        request.setAttribute(ATTRIBUTE_NAME, jpRequest);
    }

    public static JPinbaRequest extract(ServletRequest request)
    {
        return (JPinbaRequest) request.getAttribute(ATTRIBUTE_NAME);
    }
}
