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
import org.itcgae.siga.db.mappers.AdmUsuariosEfectivosPerfilMapper;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.gen.services.IMenuService;
import org.itcgae.siga.gen.services.impl.MenuServiceImpl;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.itcgae.siga.services.impl.SigaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;



public class DevAuthorizationFilter extends BasicAuthenticationFilter {
	

	
	private static String secretSignKey;
	
	private static String tokenHeaderAuthKey;

	private static String tokenPrefix;

	protected static void configure(String secretSignKey, String tokenHeaderAuthKey, String tokenPrefix){
		DevAuthorizationFilter.secretSignKey = secretSignKey;
		DevAuthorizationFilter.tokenHeaderAuthKey = tokenHeaderAuthKey;
		DevAuthorizationFilter.tokenPrefix = tokenPrefix;
	}
	
	public DevAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
		//this.admUsuariosExtendsMapper = admUsuariosExtendsMapper;
		//this.admUsuariosEfectivoMapper = admUsuariosEfectivoMapper;
		//this.usuarioMapper = usuarioMapper;	
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = null;
		try {
			
			HashMap<String,String> map = getPerm("44149718E-Personal-2000");
			header = tokenPrefix + " " + new UserAuthenticationToken("44149718E-Personal-2000").generateToken("44149718E-Personal-2000",map);
		} catch (TokenGenerationException e) {
			e.printStackTrace();
		}
		HeaderMapRequestWrapper mutableRequest = new HeaderMapRequestWrapper(req);
		
		mutableRequest.addHeader("Authorization", header);
		
		UserAuthenticationToken authentication = getAuthentication(req);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(mutableRequest, res);
	}

	private UserAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(tokenHeaderAuthKey);
		if (token != null) {
			// Se procesa el token y se recupera el usuario.
			String user = Jwts.parser()
						.setSigningKey(secretSignKey)
						.parseClaimsJws(token.replace(tokenPrefix, ""))
						.getBody()
						.getSubject();

			if (user != null) {
				return new UserAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
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
	
	private HashMap<String, String> getPerm(String string) {
		
		SigaUserDetailsService userDetailsService = new SigaUserDetailsService();
		UserDetails user = userDetailsService.loadUserByUsername(string);
		return null;
		
		
		
	}
}
