package org.itcgae.siga.security.develop;

import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
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
		X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
		String commonName = null;

		String dni =  null;
		X509Certificate cert = null;

		if (null != certs && certs.length > 0) {
			cert = certs[0];
				X500Name x500name;
				try {
					x500name = new JcaX509CertificateHolder(cert).getSubject();
					RDN userRdn = x500name.getRDNs(BCStyle.CN)[0];
					commonName = IETFUtils.valueToString(userRdn.getFirst().getValue());
					dni = commonName.substring(commonName.length() - 9, commonName.length());
				} catch (CertificateEncodingException e) {

					e.printStackTrace();
				}
		}else {
			dni = "44149718E";
		}
		
		String grupo = request.getParameter("profile");
		String institucion = request.getParameter("location");
		
		UserCgae user = new UserCgae(dni, grupo, institucion, null);
		return authenticationManager.authenticate(new UserAuthenticationToken(dni, user, null));
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
				response.addHeader(tokenHeaderAuthKey, UserTokenUtils.generateToken(userAuthToken));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}	
	
}
