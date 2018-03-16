package org.itcgae.siga.security.production;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.NoSuchElementException;

import javax.naming.ldap.LdapName;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.commons.utils.InvalidClientCerticateException;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class ProAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	Logger LOGGER = LoggerFactory.getLogger(ProAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;

	private static String tokenHeaderAuthKey;

	private static String tokenPrefix;

	public ProAuthenticationFilter(AuthenticationManager authenticationManager, String loginMethod, String loginUrl,
			String tokenHeaderAuthKey, String tokenPrefix) {
		super(new AntPathRequestMatcher(loginUrl, loginMethod));
		this.authenticationManager = authenticationManager;
		ProAuthenticationFilter.tokenHeaderAuthKey = tokenHeaderAuthKey;
		ProAuthenticationFilter.tokenPrefix = tokenPrefix;
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

				return authenticationManager.authenticate(new UserAuthenticationToken(user, certs[0]));
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
			if (auth.getClass().equals(UserAuthenticationToken.class)) {
				UserAuthenticationToken userAuthToken = (UserAuthenticationToken) auth;
				response.addHeader(tokenHeaderAuthKey, tokenPrefix + " " + userAuthToken.generateToken(auth));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
