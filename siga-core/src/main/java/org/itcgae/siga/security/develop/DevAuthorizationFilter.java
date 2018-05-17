package org.itcgae.siga.security.develop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.commons.utils.TokenGenerationException;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.itcgae.siga.security.UserCgae;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.impl.SigaUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


public class DevAuthorizationFilter extends BasicAuthenticationFilter {

	private SigaUserDetailsService userDetailsService;
	
	private String [] authorizedRequests;

	public DevAuthorizationFilter(AuthenticationManager authManager, UserDetailsService userDetailsService, String[] authorizedRequests) {
		super(authManager);
		this.userDetailsService = (SigaUserDetailsService) userDetailsService;
		this.authorizedRequests = authorizedRequests;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		for (String pattern : authorizedRequests) {
			if (new AntPathRequestMatcher(pattern).matches(request)){
				chain.doFilter(request, response);
				return;
			}
		}
		
		
		UserAuthenticationToken authentication = UserTokenUtils.getAuthentication(request);
		HeaderMapRequestWrapper mutableRequest = new HeaderMapRequestWrapper(request);

		if (authentication == null) {
			// Usuario 2 -> Usuario de desarrollo del actual SIGA
			UserCgae userDesarrollo = (UserCgae) userDetailsService
					.loadUserByUsername(new UserCgae("44149718E", "Personal", "2000", null));
			authentication = new UserAuthenticationToken(userDesarrollo.getDni(), null, userDesarrollo, null,
					new ArrayList<>());
			String header = null;
			try {
				header = UserTokenUtils.generateToken(userDesarrollo);
			} catch (TokenGenerationException e) {
				e.printStackTrace();
			}
			mutableRequest.addHeader("Authorization", header);
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(mutableRequest, response);

	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = (SigaUserDetailsService) userDetailsService;
	}

	public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
		/**
		 * construct a wrapper for this request
		 * 
		 * @param request
		 */
		public HeaderMapRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		private Map<String, String> headerMap = new HashMap<String, String>();

		/**
		 * add a header with given name and value
		 * 
		 * @param name
		 * @param value
		 */
		public void addHeader(String name, String value) {
			headerMap.put(name, value);
		}

		@Override
		public String getHeader(String name) {
			String headerValue = super.getHeader(name);
			if (headerMap.containsKey(name)) {
				headerValue = headerMap.get(name);
			}
			return headerValue;
		}

		/**
		 * get the Header names
		 */
		@Override
		public Enumeration<String> getHeaderNames() {
			List<String> names = Collections.list(super.getHeaderNames());
			for (String name : headerMap.keySet()) {
				names.add(name);
			}
			return Collections.enumeration(names);
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			List<String> values = Collections.list(super.getHeaders(name));
			if (headerMap.containsKey(name)) {
				values.add(headerMap.get(name));
			}
			return Collections.enumeration(values);
		}

	}

}
