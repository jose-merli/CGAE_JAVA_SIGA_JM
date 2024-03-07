package org.itcgae.siga.security.develop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Primary
public class DevAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DevAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;
	
	private SigaUserDetailsService userDetailsService;

	private static String tokenHeaderAuthKey;
	
	public DevAuthenticationFilter(AuthenticationManager authenticationManager, String loginMethod, String loginUrl, String tokenHeaderAuthKey, SigaUserDetailsService userDetailsService) {
		super(new AntPathRequestMatcher(loginUrl, loginMethod));
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		DevAuthenticationFilter.tokenHeaderAuthKey = tokenHeaderAuthKey;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try{
			
			LOGGER.info("Se accede por el formulario de deserrallo");
			
			List<String> perfiles = new ArrayList<String>();
			perfiles.add("ADG");
			String dni = "44149718E"; // Habilitar este para trabajar en local y comentar las dos líneas de CAS
			String nombre = "Jesus"; // Habilitar este para trabajar en local y comentar las dos líneas de CAS
			//String dni = (String) request.getHeader("CAS-username");
			//String nombre = (String) request.getHeader("CAS-displayName");

			String grupo = request.getParameter("profile");
			String institucion = request.getParameter("location");
			String letrado = request.getParameter("letrado");
			AdmRol rol = null;
			
			UserCgae user = new UserCgae(dni, grupo, institucion, null, perfiles, letrado, rol, nombre);
			UserCgae userDesarrollo = (UserCgae) userDetailsService.loadUserByUsername(user);
						
			return authenticationManager.authenticate(new UserAuthenticationToken(dni, userDesarrollo, null));
		} catch (Exception e) {
			throw new BadCredentialsException(e.getMessage(),e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
		
		response.addHeader("Access-Control-Allow-Headers", "Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		response.addHeader("Access-Control-Expose-Headers", "Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"); 
		
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