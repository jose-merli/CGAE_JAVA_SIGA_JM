package org.itcgae.siga.security.develop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.db.mappers.AdmUsuariosEfectivosPerfilMapper;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.gen.services.IMenuService;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.itcgae.siga.services.impl.SigaUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


public class DevAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	
	
	@Autowired
	private IMenuService menuService;
	
	
	Logger LOGGER = LoggerFactory.getLogger(DevAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;

	private static String tokenHeaderAuthKey;

	private static String tokenPrefix;
	
	

	public DevAuthenticationFilter(AuthenticationManager authenticationManager, String loginMethod, String loginUrl,
			String tokenHeaderAuthKey, String tokenPrefix) {
		super(new AntPathRequestMatcher(loginUrl, loginMethod));
		this.authenticationManager = authenticationManager;
		DevAuthenticationFilter.tokenHeaderAuthKey = tokenHeaderAuthKey;
		DevAuthenticationFilter.tokenPrefix = tokenPrefix;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		// Usuario 2 -> Usuario de desarrollo del actual SIGA
		String user = "44149718E";
		String institucion = "2000";
		//return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("44149718E-Personal-2000","",new ArrayList<>()));
		return authenticationManager.authenticate(new UserAuthenticationToken("44149718E-Personal-2000","",new ArrayList<>()));

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		response.addHeader("Access-Control-Allow-Headers", "Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		response.addHeader("Access-Control-Expose-Headers", "Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

		try {
			if (auth.getClass().equals(UserAuthenticationToken.class)) {
				UserAuthenticationToken userAuthToken = (UserAuthenticationToken) auth;
				HashMap<String,String> map = getPerm(auth.getPrincipal().toString());
				response.addHeader(tokenHeaderAuthKey, tokenPrefix + " " + userAuthToken.generateToken(auth.getPrincipal().toString(),map));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	private HashMap<String, String> getPerm(String string) {
		
		SigaUserDetailsService userDetailsService = new SigaUserDetailsService();
		UserDetails user = userDetailsService.loadUserByUsername(string);
		return null;
		
		
		
	}
	
	
	
}
