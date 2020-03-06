package org.itcgae.siga.security.production;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.itcgae.siga.security.UserCgae;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.impl.SigaUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class ProAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	Logger LOGGER = LoggerFactory.getLogger(ProAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;

	private static String tokenHeaderAuthKey;
	
	private SigaUserDetailsService userDetailsService;


	public ProAuthenticationFilter(AuthenticationManager authenticationManager, String loginMethod, String loginUrl,
			String tokenHeaderAuthKey, SigaUserDetailsService userDetailsService2) {
		super(new AntPathRequestMatcher(loginUrl, loginMethod));
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService2;
		ProAuthenticationFilter.tokenHeaderAuthKey = tokenHeaderAuthKey;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			String grupo = null;
			
			String dni = (String) request.getHeader("CAS-username");
			String roles = (String) request.getHeader("CAS-roles");
			String defaultRole = null;
			String [] roleAttributes;
			String [] rolesList = roles.split("::");
			if(rolesList.length > 1) {
				defaultRole = (String) request.getHeader("CAS-defaultRole");
				roleAttributes = defaultRole.split(" ");
			}else {
				roleAttributes = roles.split(" ");
			}
				
			String institucion = null;
			institucion = this.userDetailsService.getidInstitucionByCodExterno(roleAttributes[0]).get(0).getIdinstitucion().toString();
			
			if(roleAttributes.length == 2) {
				grupo = SigaConstants.getTipoUsuario(roleAttributes[1]);
			}else {
				grupo = SigaConstants.getTipoUsuario(roleAttributes[2]);
			}
	

			LOGGER.debug("DNI: " + dni);
			LOGGER.debug("INSTITUCION: " + institucion);
			LOGGER.debug("GRUPO: " + grupo);

			UserCgae user = new UserCgae(dni, grupo, institucion, null,null,null);
			LOGGER.info("Intento de autenticación en siga {}", user);
			//return authenticationManager.authenticate(new UserAuthenticationToken(dni, user, cert));
			return authenticationManager.authenticate(new UserAuthenticationToken(dni, user, null));
		} catch (Exception e) {
			throw new BadCredentialsException(e.getMessage());
		}
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
