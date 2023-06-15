package org.itcgae.siga.security.develop;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.db.entities.AdmRol;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.itcgae.siga.security.UserCgae;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.impl.SigaUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Primary
public class DevAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	
	Logger LOGGER = LoggerFactory.getLogger(DevAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;
	
	@SuppressWarnings("unused")
	private SigaUserDetailsService userDetailsService;

	private static String tokenHeaderAuthKey;
	
	@Autowired
	private static ApplicationContext context;
	
	public DevAuthenticationFilter(AuthenticationManager authenticationManager, String loginMethod, String loginUrl,
			String tokenHeaderAuthKey, SigaUserDetailsService userDetailsService2) {
		super(new AntPathRequestMatcher(loginUrl, loginMethod));
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService2;
		DevAuthenticationFilter.tokenHeaderAuthKey = tokenHeaderAuthKey;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try{
			LOGGER.info("Se accede por los combos");
//			String dni = "44149718E"; // Habilitar este para trabajar en local y comentar las dos líneas de CAS
//			String nombre = "Jesus"; // Habilitar este para trabajar en local y comentar las dos líneas de CAS
			String dni = (String) request.getHeader("CAS-username");
			String nombre = (String) request.getHeader("CAS-displayName");

			String grupo = "";
			String institucion = request.getParameter("location");
			String letrado = "";
			AdmRol rol = null;
			grupo = request.getParameter("profile");
			letrado = request.getParameter("letrado");
			
			UserCgae user = new UserCgae(dni, grupo, institucion, null,null,letrado, rol, nombre);
			return authenticationManager.authenticate(new UserAuthenticationToken(dni, user,null));
		} catch (Exception e) {
			throw new BadCredentialsException(e.getMessage(),e);
		}
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