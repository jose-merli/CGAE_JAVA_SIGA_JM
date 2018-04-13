package org.itcgae.siga.security.develop;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.security.UserAuthenticationToken;
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
		String user = "03862002A";
		String institucion = "2003";
		return authenticationManager.authenticate(new UserAuthenticationToken(user.concat(institucion)));

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		response.addHeader("Access-Control-Allow-Headers", "Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		response.addHeader("Access-Control-Expose-Headers", "Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		//response.addHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
		//response.addHeader("Access-Control-Allow-Origin", "*");
		try {
			if (auth.getClass().equals(UserAuthenticationToken.class)) {
				UserAuthenticationToken userAuthToken = (UserAuthenticationToken) auth;
				response.addHeader(tokenHeaderAuthKey, tokenPrefix + " " + userAuthToken.generateToken(auth.getPrincipal().toString()));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
