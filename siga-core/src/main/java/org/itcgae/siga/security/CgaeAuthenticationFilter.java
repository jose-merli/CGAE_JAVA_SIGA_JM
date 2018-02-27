package org.itcgae.siga.security;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.NoSuchElementException;

import javax.naming.ldap.LdapName;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.security.utils.InvalidClientCerticateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class CgaeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	Logger LOGGER = LoggerFactory.getLogger(CgaeAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;

	private static String tokenHeaderAuthKey;

	private static String tokenPrefix;

	public CgaeAuthenticationFilter(AuthenticationManager authenticationManager, String loginMethod, String loginUrl,
			String tokenHeaderAuthKey, String tokenPrefix) {
		super(new AntPathRequestMatcher(loginUrl, loginMethod));
		this.authenticationManager = authenticationManager;
		CgaeAuthenticationFilter.tokenHeaderAuthKey = tokenHeaderAuthKey;
		CgaeAuthenticationFilter.tokenPrefix = tokenPrefix;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
			String user = null;
			if (null != certs && certs.length > 0) {
				String dn = certs[0].getSubjectX500Principal().getName();
				LdapName ldapDN = new LdapName(dn);
				try {
					user = ldapDN.getRdns().stream().filter(a -> a.getType().equals("CN")).findFirst().get().getValue()
							.toString();
				} catch (NoSuchElementException e) {
					throw new InvalidClientCerticateException(e);
				}

				return authenticationManager.authenticate(new CgaeUserAuthenticationToken(user, certs[0]));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		try {
			if (auth.getClass().equals(CgaeUserAuthenticationToken.class)) {
				CgaeUserAuthenticationToken userAuthToken = (CgaeUserAuthenticationToken) auth;
				response.addHeader(tokenHeaderAuthKey, tokenPrefix + " " + userAuthToken.generateToken(auth));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
