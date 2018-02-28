package org.itcgae.siga.services;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface ITestHeadersService {
    /**
     * 
     * @param request.
     * @return.
     */
    public String getHeadersService(HttpServletRequest request);

}
