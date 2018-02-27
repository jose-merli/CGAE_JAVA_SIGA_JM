package org.itcgae.siga.security;

import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;

import javax.naming.ldap.LdapName;

import org.itcgae.siga.security.utils.TokenGenerationException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class CgaeUserAuthenticationToken extends AbstractAuthenticationToken {

	private static long expirationTime;
	
	private static String secretSignKey;
	
	protected static void configure(String secretSignKey, long expirationTime){
		CgaeUserAuthenticationToken.expirationTime = expirationTime;
		CgaeUserAuthenticationToken.secretSignKey = secretSignKey;
	}
	
	private static final long serialVersionUID = 1L;

	private final Object principal;
	private Object credentials;
	private X509Certificate certificate;

	/**
	 * This constructor can be safely used by any code that wishes to create a
	 * <code>UsernamePasswordAuthenticationToken</code>, as the
	 * {@link #isAuthenticated()} will return <code>false</code>.
	 *
	 */
	public CgaeUserAuthenticationToken(Object principal) {
		super(null);
		this.principal = principal;
		setAuthenticated(false);
	}

	/**
	 * This constructor can be safely used by any code that wishes to create a
	 * <code>UsernamePasswordAuthenticationToken</code>, as the
	 * {@link #isAuthenticated()} will return <code>false</code>.
	 *
	 */
	public CgaeUserAuthenticationToken(Object principal, Object credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		setAuthenticated(false);
	}

	/**
	 * This constructor can be safely used by any code that wishes to create a
	 * <code>UsernamePasswordAuthenticationToken</code>, as the
	 * {@link #isAuthenticated()} will return <code>false</code>.
	 *
	 */
	public CgaeUserAuthenticationToken(Object principal, X509Certificate certificate) {
		super(null);
		this.principal = principal;
		this.setCertificate(certificate);
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
	public CgaeUserAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true); // must use super, as we override
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
	public CgaeUserAuthenticationToken(Object principal, Object credentials, X509Certificate certificate,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.certificate = certificate;
		this.principal = principal;
		this.credentials = credentials;
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
			
			return Jwts.builder().setIssuedAt(new Date()).setIssuer(issuer)
					.setSubject(auth.getPrincipal().toString())
					.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
					.signWith(SignatureAlgorithm.HS512, secretSignKey).compact();
			
		} catch (Exception e) {
			throw new TokenGenerationException(e);
		}
	}

}
