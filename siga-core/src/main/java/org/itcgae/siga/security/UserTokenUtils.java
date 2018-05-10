package org.itcgae.siga.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.commons.utils.TokenGenerationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class UserTokenUtils {

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
					.setSubject(auth.getPrincipal().toString()).claim("permisos", auth.getUser().getPermisos())
					.claim("institucion", auth.getUser().getInstitucion()).claim("grupo", auth.getUser().getGrupo())
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
					.claim("institucion", user.getInstitucion()).claim("grupo", user.getGrupo())
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

		return new UserCgae(dni, grupo, institucion, permisos);
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

	@SuppressWarnings("unchecked")
	public static HashMap<String, String> getPermisosFromJWTToken(String token) {
		// Claims claims =
		// Jwts.parser().setSigningKey(secretSignKey).parseClaimsJws(token.replace(tokenPrefix,
		// "")).getBody();
		//

		return (HashMap<String, String>) Jwts.parser().setSigningKey(secretSignKey)
				.parseClaimsJws(token.replace(tokenPrefix, "")).getBody().get("permisos");
	}

	// private static Map<String, String> getMapFromIoJsonwebtokenClaims(Claims
	// claims) {
	// Map<String, String> expectedMap = new HashMap<String, String>();
	// for (Entry<String, Object> entry : claims.entrySet()) {
	// expectedMap.put(entry.getKey(), (String) entry.getValue());
	// }
	// return expectedMap;
	// }
}
