package org.itcgae.siga.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.itcgae.siga.services.impl.SigaUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import io.jsonwebtoken.ExpiredJwtException;


public class AuthorizationFilter extends BasicAuthenticationFilter {
	
	private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class);

	private SigaUserDetailsService userDetailsService;
	
	private YamlPermisos yamlPermisosProperties;
	
	private String [] authorizedRequests;
	
	private Boolean security;

	public AuthorizationFilter(AuthenticationManager authManager, UserDetailsService userDetailsService, String[] authorizedRequests, YamlPermisos yamlPermisosProperties, Boolean security) {
		super(authManager);
		this.userDetailsService = (SigaUserDetailsService) userDetailsService;
		this.yamlPermisosProperties = yamlPermisosProperties;
		this.authorizedRequests = authorizedRequests;
		this.security = security;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		boolean validate =  true;
		response.setContentType("application/json");
		
		if(!security) {
			for (String pattern : authorizedRequests) {
				if (new AntPathRequestMatcher(pattern).matches(request)){
					validate =  false;
					break;
				}
			}
		}
		
		if(validate) {
		
			LOGGER.info("Se pasa filtro de desarrollo para permitir acceso");
			
			try {
			
				UserAuthenticationToken authentication = UserTokenUtils.getAuthentication(request);
				//HeaderMapRequestWrapper mutableRequest = new HeaderMapRequestWrapper(request);
				if (authentication != null) {
					
					UserCgae user = (UserCgae) authentication.getPrincipal();
					HashMap<String, String> permisosToken = user.getPermisos();
					Map<String, List<String>> permisosYaml = yamlPermisosProperties.getPermisos();
					
					if (null != permisosToken && null != permisosYaml && security) {
						
						String url = request.getServletPath();
						List<String> listaProcesos = permisosYaml.get(url);
						
						boolean tienePermiso = true;
						if (null != listaProcesos && !listaProcesos.isEmpty()) {
							tienePermiso = false;
							for (String proceso : listaProcesos) {
								if (permisosToken.containsKey(proceso) && permisosToken.get(proceso).equalsIgnoreCase("3")) {
									tienePermiso = true;
									break;
								}
							}
						}

						if (!tienePermiso) {
						    LOGGER.error("Se esta intentando realizar una operacion no autorizada:\n - Recurso -> " + url + "\n - Usuario -> " + user.getDni());
						    String error = "{\"error\": \"Access Denied\", \"message\": \"Se esta intentando realizar una operacion no autorizada: Recurso -> " + url + " Usuario -> " + user.getDni() + "\"}";
							response.getWriter().write(error);
							response.setStatus(HttpServletResponse.SC_FORBIDDEN);
						} else {
						    SecurityContextHolder.getContext().setAuthentication(authentication);
						    chain.doFilter(request, response);
						}
					} else {
						SecurityContextHolder.getContext().setAuthentication(authentication);
					    chain.doFilter(request, response);
			        }
						
				} else {
					String error = "{\"error\": \"Access Denied\", \"message\": \"Access Denied\"}";
					response.getWriter().write(error);
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				}

			} catch (ExpiredJwtException e) {
				String error = "{\"error\": \"Token Expired\", \"message\": \"Token Expired\"}";
				response.getWriter().write(error);
			    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
				
		} else {
			chain.doFilter(request, response);
		}
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = (SigaUserDetailsService) userDetailsService;
	}
	
	/*
	public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
	
		public HeaderMapRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		private Map<String, String> headerMap = new HashMap<String, String>();

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
	*/

}
