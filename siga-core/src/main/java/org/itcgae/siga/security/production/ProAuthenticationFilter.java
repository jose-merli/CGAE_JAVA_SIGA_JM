package org.itcgae.siga.security.production;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmRol;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.itcgae.siga.security.UserCgae;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.impl.SigaUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class ProAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;

	private static String tokenHeaderAuthKey;
	
	private SigaUserDetailsService userDetailsService;


	public ProAuthenticationFilter(AuthenticationManager authenticationManager, String loginMethod, String loginUrl, String tokenHeaderAuthKey, SigaUserDetailsService userDetailsService) {
		super(new AntPathRequestMatcher(loginUrl, loginMethod));
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		ProAuthenticationFilter.tokenHeaderAuthKey = tokenHeaderAuthKey;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		LOGGER.info("Se accede por el CAS del SSO");
		
		try {
			
			String dni = (String) request.getHeader("CAS-username");
			String nombre = (String) request.getHeader("CAS-displayName");
				
			String institucion = null;
			if(request.getParameter("location")==null) {
				institucion = this.userDetailsService.getInstitucionCAS(request);
			}else {
				institucion = request.getParameter("location");	//Hemos accedido por loginMultiple
			}
			
			String grupo = null;
			AdmRol rol = null;
			if(request.getParameter("rol") == null) {
				rol = this.userDetailsService.getRolLogin(request);
				grupo = this.userDetailsService.getGrupoCAS(request);
			}else {
				rol = this.userDetailsService.getRolLoginMultiple(request.getParameter("rol")); //Hemos accedido por loginMultiple
				grupo = SigaConstants.getTipoUsuario(rol.getDescripcion()); //Hemos accedido por loginMultiple
			}
			
			UserCgae user = new UserCgae(dni, grupo, institucion, null,null,null, rol, nombre);

			return authenticationManager.authenticate(new UserAuthenticationToken(dni, user, null));
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
				response.setHeader(tokenHeaderAuthKey, UserTokenUtils.generateToken(userAuthToken));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
