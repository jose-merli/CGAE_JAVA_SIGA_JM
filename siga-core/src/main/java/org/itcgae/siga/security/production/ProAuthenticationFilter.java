package org.itcgae.siga.security.production;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.NoSuchElementException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.itcgae.siga.commons.utils.InvalidClientCerticateException;
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

public class ProAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	Logger LOGGER = LoggerFactory.getLogger(ProAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;

	private static String tokenHeaderAuthKey;


	public ProAuthenticationFilter(AuthenticationManager authenticationManager, String loginMethod, String loginUrl,
			String tokenHeaderAuthKey) {
		super(new AntPathRequestMatcher(loginUrl, loginMethod));
		this.authenticationManager = authenticationManager;
		ProAuthenticationFilter.tokenHeaderAuthKey = tokenHeaderAuthKey;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
			String commonName = null;
			String organizationName = null;
			String grupo = null;
			X509Certificate cert = null;

			if (null != certs && certs.length > 0) {
				cert = certs[0];
				try {
					X500Name x500name = new JcaX509CertificateHolder(cert).getSubject();

					RDN userRdn = x500name.getRDNs(BCStyle.CN)[0];
					commonName = IETFUtils.valueToString(userRdn.getFirst().getValue());

					RDN institucionRdn = x500name.getRDNs(BCStyle.O)[0];
					organizationName = IETFUtils.valueToString(institucionRdn.getFirst().getValue());

					RDN grupoRdn = x500name.getRDNs(BCStyle.T)[0];
					grupo = IETFUtils.valueToString(grupoRdn.getFirst().getValue());

					LOGGER.debug("Common Name: " + commonName);
					LOGGER.debug("Organization Name: " + organizationName);
				} catch (NoSuchElementException e) {
					throw new InvalidClientCerticateException(e);
				}

				String dni = commonName.substring(commonName.length() - 9, commonName.length());
				String institucion = organizationName.substring(organizationName.length() - 4,
						organizationName.length());
				LOGGER.debug("DNI: " + dni);
				LOGGER.debug("INSTITUCION: " + institucion);
				LOGGER.debug("GRUPO: " + grupo);

				UserCgae user = new UserCgae(dni, grupo, institucion, null);
				LOGGER.info("Intento de autenticación en siga {}", user);
				return authenticationManager.authenticate(new UserAuthenticationToken(dni, user, cert));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		response.addHeader("Access-Control-Allow-Headers",
				"Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
						+ "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		response.addHeader("Access-Control-Expose-Headers", "Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"); 
		try {
			if (auth.getClass().equals(UserAuthenticationToken.class)) {
				response.addHeader(tokenHeaderAuthKey, UserTokenUtils.generateToken((UserAuthenticationToken) auth));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
