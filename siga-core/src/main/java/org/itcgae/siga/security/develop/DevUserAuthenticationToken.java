//package org.itcgae.siga.security.develop;
//
//import java.security.cert.X509Certificate;
//import java.util.Collection;
//import java.util.Date;
//
//import org.itcgae.siga.commons.utils.TokenGenerationException;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//public class DevUserAuthenticationToken extends AbstractAuthenticationToken {
//
//	private static long expirationTime;
//
//	private static String secretSignKey;
//
//	private static String tokenPrefix;
//
//	protected static void configure(String secretSignKey, String tokenPrefix, long expirationTime) {
//		DevUserAuthenticationToken.expirationTime = expirationTime;
//		DevUserAuthenticationToken.secretSignKey = secretSignKey;
//		DevUserAuthenticationToken.tokenPrefix = tokenPrefix;
//	}
//
//	private static final long serialVersionUID = 1L;
//
//	private final Object principal;
//	private Object credentials;
//	private X509Certificate certificate;
//
//	/**
//	 * This constructor can be safely used by any code that wishes to create a
//	 * <code>UsernamePasswordAuthenticationToken</code>, as the
//	 * {@link #isAuthenticated()} will return <code>false</code>.
//	 *
//	 */
//	public DevUserAuthenticationToken(Object principal) {
//		super(null);
//		this.principal = principal;
//		setAuthenticated(false);
//	}
//
//	/**
//	 * This constructor can be safely used by any code that wishes to create a
//	 * <code>UsernamePasswordAuthenticationToken</code>, as the
//	 * {@link #isAuthenticated()} will return <code>false</code>.
//	 *
//	 */
//	public DevUserAuthenticationToken(Object principal, Object credentials) {
//		super(null);
//		this.principal = principal;
//		this.credentials = credentials;
//		setAuthenticated(false);
//	}
//
//	/**
//	 * This constructor can be safely used by any code that wishes to create a
//	 * <code>UsernamePasswordAuthenticationToken</code>, as the
//	 * {@link #isAuthenticated()} will return <code>false</code>.
//	 *
//	 */
//	public DevUserAuthenticationToken(Object principal, X509Certificate certificate) {
//		super(null);
//		this.principal = principal;
//		this.setCertificate(certificate);
//		setAuthenticated(false);
//	}
//
//	/**
//	 * This constructor should only be used by
//	 * <code>AuthenticationManager</code> or <code>AuthenticationProvider</code>
//	 * implementations that are satisfied with producing a trusted (i.e.
//	 * {@link #isAuthenticated()} = <code>true</code>) authentication token.
//	 *
//	 * @param principal
//	 * @param credentials
//	 * @param authorities
//	 */
//	public DevUserAuthenticationToken(Object principal, Object credentials,
//			Collection<? extends GrantedAuthority> authorities) {
//		super(authorities);
//		this.principal = principal;
//		this.credentials = credentials;
//		super.setAuthenticated(true); // must use super, as we override
//	}
//
//	/**
//	 * This constructor should only be used by
//	 * <code>AuthenticationManager</code> or <code>AuthenticationProvider</code>
//	 * implementations that are satisfied with producing a trusted (i.e.
//	 * {@link #isAuthenticated()} = <code>true</code>) authentication token.
//	 *
//	 * @param principal
//	 * @param credentials
//	 * @param authorities
//	 */
//	public DevUserAuthenticationToken(Object principal, Object credentials, X509Certificate certificate,
//			Collection<? extends GrantedAuthority> authorities) {
//		super(authorities);
//		this.certificate = certificate;
//		this.principal = principal;
//		this.credentials = credentials;
//		super.setAuthenticated(true); // must use super, as we override
//	}
//
//	// ~ Methods
//	// ========================================================================================================
//
//	public Object getCredentials() {
//		return this.credentials;
//	}
//
//	public Object getPrincipal() {
//		return this.principal;
//	}
//
//	public X509Certificate getCertificate() {
//		return certificate;
//	}
//
//	public void setCertificate(X509Certificate certificate) {
//		this.certificate = certificate;
//	}
//
//	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//		if (isAuthenticated) {
//			throw new IllegalArgumentException(
//					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
//		}
//
//		super.setAuthenticated(false);
//	}
//
//	@Override
//	public void eraseCredentials() {
//		super.eraseCredentials();
//		credentials = null;
//	}
//
//	public String generateToken(String auth) throws TokenGenerationException {
//		try {
//			
//			return Jwts.builder().setIssuedAt(new Date()).setSubject(auth.toString())
//					.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//					.signWith(SignatureAlgorithm.HS512, secretSignKey).compact();
//
//		} catch (Exception e) {
//			throw new TokenGenerationException(e);
//		}
//	}
//
//	public static String getUserFromJWTToken(String token) {
//		return Jwts.parser().setSigningKey(secretSignKey).parseClaimsJws(token.replace(tokenPrefix, "")).getBody()
//				.getSubject();
//	}
//
//}
