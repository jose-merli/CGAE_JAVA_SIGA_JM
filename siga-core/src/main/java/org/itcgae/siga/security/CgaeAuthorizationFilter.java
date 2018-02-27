package org.itcgae.siga.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class CgaeAuthorizationFilter extends BasicAuthenticationFilter {
	
	private static String secretSignKey;
	
	private static String tokenHeaderAuthKey;

	private static String tokenPrefix;

	protected static void configure(String secretSignKey, String tokenHeaderAuthKey, String tokenPrefix){
		CgaeAuthorizationFilter.secretSignKey = secretSignKey;
		CgaeAuthorizationFilter.tokenHeaderAuthKey = tokenHeaderAuthKey;
		CgaeAuthorizationFilter.tokenPrefix = tokenPrefix;
	}
	
	public CgaeAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(tokenHeaderAuthKey);
		if (header == null || !header.startsWith(tokenPrefix)) {
			chain.doFilter(req, res);
			return;
		}
		CgaeUserAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private CgaeUserAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(tokenHeaderAuthKey);
		if (token != null) {
			// Se procesa el token y se recupera el usuario.
			String user = Jwts.parser()
						.setSigningKey(secretSignKey)
						.parseClaimsJws(token.replace(tokenPrefix, ""))
						.getBody()
						.getSubject();

			if (user != null) {
				return new CgaeUserAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
}
