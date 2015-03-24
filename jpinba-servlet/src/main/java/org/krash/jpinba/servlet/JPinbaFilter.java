package org.krash.jpinba.servlet;

import org.krash.jpinba.data.JPinbaRequest;
import org.krash.jpinba.data.Measure;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet filter, invoking filter chain, and measuring it's time
 * @author krash
 */
public class JPinbaFilter implements Filter {

    private static String LENGTH_HEADER = "Content-Length";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        handle((HttpServletRequest)servletRequest, (HttpServletResponse)servletResponse, filterChain);
    }

    private void handle(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        JPinbaRequest request = new JPinbaRequest(req.getLocalName(),req.getServerName(), req.getRequestURI(), Measure.createEmpty());
        request.setSchema(req.getScheme());

        Helper.attachRequest(req, request); // to provide usage in servlet

        long start = System.currentTimeMillis();
        chain.doFilter(req, resp);
        long end = System.currentTimeMillis();
        request.setRequestTime(((float)(end - start) / 1000));
        request.setStatus(resp.getStatus());

        if (resp.containsHeader(LENGTH_HEADER) && ! request.isDocumentSizeInstalled()) {
            try {
                int contentLength = Integer.valueOf(resp.getHeader(LENGTH_HEADER));
                if(contentLength >0) {
                    request.setDocumentSize(contentLength);
                }
            } catch(Exception e) {
                request.setDocumentSize(0);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
