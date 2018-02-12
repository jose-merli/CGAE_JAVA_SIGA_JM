package org.redabogacia.services.impl;

import java.util.Enumeration;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.redabogacia.services.ITestHeadersService;
import org.springframework.stereotype.Service;



@Service
public class TestHeadersServiceImpl implements ITestHeadersService {

    @Override
    public String getHeadersService(HttpServletRequest httpRequest) {

        String result = "===> HEADERS <===\n";
        Enumeration<?> headerNames = httpRequest.getHeaderNames();
        Set<String> sortedHeaders = new TreeSet<String>();
        while (headerNames.hasMoreElements()) {
            sortedHeaders.add(headerNames.nextElement().toString());
        }
        for (String headerName : sortedHeaders) {
            result = result + headerName + ": " + httpRequest.getHeader(headerName) + "\n";
        }

        result = result + "\n\n===> PARAMETERS <===\n";
        Enumeration<?> parameterNames = httpRequest.getParameterNames();
        Set<String> sortedParameters = new TreeSet<String>();
        while (parameterNames.hasMoreElements()) {
            sortedParameters.add(parameterNames.nextElement().toString());
        }
        for (String parameterName : sortedParameters) {
            result = result + parameterName + ": " + httpRequest.getParameter(parameterName) + "\n";
        }

        return result;
    }

    /**
     * Constructor vacio.
     */
    public TestHeadersServiceImpl() {
        // Constructor por defecto
    }

}
