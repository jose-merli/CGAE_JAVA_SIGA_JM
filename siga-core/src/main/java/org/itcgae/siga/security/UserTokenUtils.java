package org.itcgae.siga.security;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.itcgae.siga.commons.utils.TokenGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class UserTokenUtils {
	
	static Logger LOGGER = LoggerFactory.getLogger(UserTokenUtils.class);

	private static long expirationTime;

	private static String secretSignKey;

	private static String tokenPrefix;

	private static String tokenHeaderAuthKey;

	public static void configure(String secretSignKey, String tokenPrefix, long expirationTime,
			String tokenHeaderAuthKey) {
		UserTokenUtils.expirationTime = expirationTime;
		UserTokenUtils.secretSignKey = secretSignKey;
		UserTokenUtils.tokenPrefix = tokenPrefix;
		UserTokenUtils.tokenHeaderAuthKey = tokenHeaderAuthKey;
	}

	public static UserAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(tokenHeaderAuthKey);
		if (token == null || !token.startsWith(tokenPrefix)) {
			return null;
		}
		UserCgae user = UserTokenUtils.gerUserFromJWTToken(token);
		if (user != null) {
			return new UserAuthenticationToken(user, null, user, null, new ArrayList<>());
		} else {
			return null;
		}
	}

	public static String generateToken(UserAuthenticationToken auth) throws TokenGenerationException {
		try {
			return tokenPrefix + Jwts.builder().setIssuedAt(new Date()).setIssuer("CONSEJO GENERAL DE LA ABOGACIA")
					.setSubject(auth.getUser().getDni()).claim("permisos", auth.getUser().getPermisos())
					.claim("institucion", auth.getUser().getInstitucion()).claim("grupo", auth.getUser().getGrupo())
					.claim("perfiles", auth.getUser().getPerfiles()).claim("letrado", auth.getUser().getLetrado())
					.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
					.signWith(SignatureAlgorithm.HS512, secretSignKey).compact();

		} catch (Exception e) {
			throw new TokenGenerationException(e);
		}
	}

	public static String generateToken(UserCgae user) throws TokenGenerationException {
		try {

			return tokenPrefix + Jwts.builder().setIssuedAt(new Date()).setIssuer("CONSEJO GENERAL DE LA ABOGACIA")
					.setSubject(user.getDni()).claim("permisos", user.getPermisos())
					.claim("institucion", user.getInstitucion()).claim("grupo", user.getGrupo()).claim("perfiles", user.getPerfiles())
					.claim("letrado",user.getLetrado())
					.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
					.signWith(SignatureAlgorithm.HS512, secretSignKey).compact();

		} catch (Exception e) {
			throw new TokenGenerationException(e);
		}
	}

	public static UserCgae gerUserFromJWTToken(String token) {
		String dni = Jwts.parser().setSigningKey(secretSignKey).parseClaimsJws(token.replace(tokenPrefix, "")).getBody()
				.getSubject();
		String institucion = (String) Jwts.parser().setSigningKey(secretSignKey)
				.parseClaimsJws(token.replace(tokenPrefix, "")).getBody().get("institucion");
		String grupo = (String) Jwts.parser().setSigningKey(secretSignKey)
				.parseClaimsJws(token.replace(tokenPrefix, "")).getBody().get("grupo");
		HashMap<String, String> permisos = getPermisosFromJWTToken(token);
		
		List<String> perfiles = (List<String>) Jwts.parser().setSigningKey(secretSignKey)
				.parseClaimsJws(token.replace(tokenPrefix, "")).getBody().get("perfiles");

		String letrado = (String) Jwts.parser().setSigningKey(secretSignKey)
				.parseClaimsJws(token.replace(tokenPrefix, "")).getBody().get("grupo");

		return new UserCgae(dni, grupo, institucion, permisos,perfiles,letrado, null, null);
	}

	public static String getDniFromJWTToken(String token) {
		return Jwts.parser().setSigningKey(secretSignKey).parseClaimsJws(token.replace(tokenPrefix, "")).getBody()
				.getSubject();
	}

	public static Short getInstitucionFromJWTToken(String token) {
		return Short.valueOf(UserTokenUtils.getInstitucionFromJWTTokenAsString(token));
	}

	public static String getInstitucionFromJWTTokenAsString(String token) {
		return (String) Jwts.parser().setSigningKey(secretSignKey).parseClaimsJws(token.replace(tokenPrefix, ""))
				.getBody().get("institucion");
	}

	public static String getGrupoFromJWTToken(String token) {
		return (String) Jwts.parser().setSigningKey(secretSignKey).parseClaimsJws(token.replace(tokenPrefix, ""))
				.getBody().get("grupo");
	}
	public static String getLetradoFromJWTToken(String token) {
		return (String) Jwts.parser().setSigningKey(secretSignKey).parseClaimsJws(token.replace(tokenPrefix, ""))
				.getBody().get("letrado");
	}

	public static List<String> getPerfilesFromJWTToken(String token) {
		return (List<String>) Jwts.parser().setSigningKey(secretSignKey).parseClaimsJws(token.replace(tokenPrefix, ""))
				.getBody().get("perfiles");
	}
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> getPermisosFromJWTToken(String token) {
		// Claims claims =
		// Jwts.parser().setSigningKey(secretSignKey).parseClaimsJws(token.replace(tokenPrefix,
		// "")).getBody();
		//

		return (HashMap<String, String>) Jwts.parser().setSigningKey(secretSignKey)
				.parseClaimsJws(token.replace(tokenPrefix, "")).getBody().get("permisos");
	}

	public static UserCgae getUserFromCertificate(HttpServletRequest request){
		UserCgae response = null;
		
		X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
		String commonName = null;

		String organizationName = null;
		String grupo = null;
		X509Certificate cert = null;

		if (null != certs && certs.length > 0) {
			response = new UserCgae();
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
				} catch (Exception e) {
					LOGGER.error("No se ha encontrado un certificado correcto", e);
					return null;
				}

				String dni = commonName.substring(commonName.length() - 9, commonName.length());
				String institucion = organizationName.substring(organizationName.length() - 4,
						organizationName.length());
				LOGGER.debug("DNI: " + dni);
				LOGGER.debug("INSTITUCION: " + institucion);
				LOGGER.debug("GRUPO: " + grupo);
				
				response.setDni(dni);
				response.setGrupo(grupo);
				response.setInstitucion(institucion);
		}
		
		return response;
	}
	
}
