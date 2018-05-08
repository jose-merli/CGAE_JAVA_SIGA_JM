package org.itcgae.siga.security;

import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.naming.ldap.LdapName;

import org.itcgae.siga.commons.utils.TokenGenerationException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

	private static long expirationTime;

	private static String secretSignKey;

	private static String tokenPrefix;

	public static void configure(String secretSignKey, String tokenPrefix, long expirationTime) {
		UserAuthenticationToken.expirationTime = expirationTime;
		UserAuthenticationToken.secretSignKey = secretSignKey;
		UserAuthenticationToken.tokenPrefix = tokenPrefix;
	}

	private static final long serialVersionUID = 1L;

	private final Object principal;
	private Object credentials;
	private X509Certificate certificate;
	private UserPrincipalCgae user;

	/**
	 * This constructor can be safely used by any code that wishes to create a
	 * <code>UserAuthenticationToken</code>, as the {@link #isAuthenticated()}
	 * will return <code>false</code>.
	 *
	 */
	public UserAuthenticationToken(Object principal) {
		super(null);
		this.principal = principal;
		setAuthenticated(false);
	}

	
	/**
	 * This constructor should only be used by
	 * <code>AuthenticationManager</code> or <code>AuthenticationProvider</code>
	 * implementations that are satisfied with producing a trusted (i.e.
	 * {@link #isAuthenticated()} = <code>true</code>) authentication token.
	 *
	 * @param principal
	 * @param credentials
	 * @param authorities
	 */
	public UserAuthenticationToken(Object principal, Object credentials, UserPrincipalCgae user, X509Certificate certificate,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.certificate = certificate;
		this.principal = principal;
		this.credentials = credentials;
		this.user = user;
		super.setAuthenticated(true); // must use super, as we override
	}

	// ~ Methods
	// ========================================================================================================

	public Object getCredentials() {
		return this.credentials;
	}

	public Object getPrincipal() {
		return this.principal;
	}
	
	private UserPrincipalCgae getUser() {
		return this.user;
	}

	public X509Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}

	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		credentials = null;
	}

	public String generateToken(Authentication auth) throws TokenGenerationException {
		try {
			String dn = this.getCertificate().getSubjectX500Principal().getName();
			LdapName ldapDN;
			ldapDN = new LdapName(dn);
			String issuer;
			issuer = ldapDN.getRdns().stream().filter(a -> a.getType().equals("O")).findFirst().get().getValue()
					.toString();

			return Jwts.builder().setIssuedAt(new Date()).setIssuer(issuer).setSubject(auth.getPrincipal().toString())
					.claim("claim", this.getUser().getUserCgae().getPermisos())
					.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
					.signWith(SignatureAlgorithm.HS512, secretSignKey).compact();

		} catch (Exception e) {
			throw new TokenGenerationException(e);
		}
	}

	public String generateToken(String user) throws TokenGenerationException {
		try {

			return Jwts.builder().setIssuedAt(new Date()).setSubject(user.toString())
					.claim("claim", this.getUser().getUserCgae().getPermisos())
					.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
					.signWith(SignatureAlgorithm.HS512, secretSignKey).compact();

		} catch (Exception e) {
			throw new TokenGenerationException(e);
		}
	}

	public static String getUserFromJWTToken(String token) {
		return Jwts.parser().setSigningKey(secretSignKey).parseClaimsJws(token.replace(tokenPrefix, "")).getBody()
				.getSubject();
	}

	public static Map<String, String> getPermisosFromJWTToken(String token) {
		@SuppressWarnings("unchecked")
		Map<String, String> claims = (Map<String, String>) Jwts.parser().setSigningKey(secretSignKey)
				.parseClaimsJws(token.replace(tokenPrefix, "")).getBody().get("claim");

		return claims;
	}

}
