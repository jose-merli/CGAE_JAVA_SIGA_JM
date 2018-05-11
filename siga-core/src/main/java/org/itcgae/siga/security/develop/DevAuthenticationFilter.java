package org.itcgae.siga.security.develop;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.security.UserAuthenticationToken;
import org.itcgae.siga.security.UserCgae;
import org.itcgae.siga.security.UserTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


public class DevAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	
	Logger LOGGER = LoggerFactory.getLogger(DevAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;

	private static String tokenHeaderAuthKey;

	public DevAuthenticationFilter(AuthenticationManager authenticationManager, String loginMethod, String loginUrl,
			String tokenHeaderAuthKey) {
		super(new AntPathRequestMatcher(loginUrl, loginMethod));
		this.authenticationManager = authenticationManager;
		DevAuthenticationFilter.tokenHeaderAuthKey = tokenHeaderAuthKey;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		String dni = "44149718E";
		String grupo = "Personal";
		String institucion = "2000";
		
		UserCgae user = new UserCgae(dni, grupo, institucion, null);
		return authenticationManager.authenticate(new UserAuthenticationToken(dni, user, null));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		response.addHeader("Access-Control-Allow-Headers", "Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

		try {
			if (auth.getClass().equals(UserAuthenticationToken.class)) {
				UserAuthenticationToken userAuthToken = (UserAuthenticationToken) auth;
				response.addHeader(tokenHeaderAuthKey, UserTokenUtils.generateToken(userAuthToken));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}	
	
}
