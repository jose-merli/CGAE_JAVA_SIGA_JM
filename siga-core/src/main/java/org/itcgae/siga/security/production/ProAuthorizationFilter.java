package org.itcgae.siga.security.production;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.security.UserAuthenticationToken;
import org.itcgae.siga.security.UserCgae;
import org.itcgae.siga.security.UserPrincipalCgae;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class ProAuthorizationFilter extends BasicAuthenticationFilter {
	
	private static String secretSignKey;
	
	private static String tokenHeaderAuthKey;

	private static String tokenPrefix;

	protected static void configure(String secretSignKey, String tokenHeaderAuthKey, String tokenPrefix){
		ProAuthorizationFilter.secretSignKey = secretSignKey;
		ProAuthorizationFilter.tokenHeaderAuthKey = tokenHeaderAuthKey;
		ProAuthorizationFilter.tokenPrefix = tokenPrefix;
	}
	
	public ProAuthorizationFilter(AuthenticationManager authManager) {
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
		UserAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
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

			Claims claims = Jwts.parser().setSigningKey(secretSignKey).parseClaimsJws("claim").getBody();
			
			UserCgae userRecuperado = new UserCgae();
			userRecuperado.setUsername(user);
			userRecuperado.setPermisos((HashMap<String, String>) getMapFromIoJsonwebtokenClaims(claims));
			UserPrincipalCgae userPrincipal = new UserPrincipalCgae(userRecuperado);
			if (user != null) {
				return new UserAuthenticationToken(user, null, userPrincipal, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
	
	public Map<String, String>  getMapFromIoJsonwebtokenClaims(Claims claims){
	    Map<String, String> expectedMap = new HashMap<String, String>();
	    for(Entry<String, Object> entry : claims.entrySet()) {
	        expectedMap.put(entry.getKey() , (String) entry.getValue());
	    }
	    return expectedMap;
	}
}
