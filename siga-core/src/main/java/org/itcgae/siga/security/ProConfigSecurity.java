package org.itcgae.siga.security;

import org.itcgae.siga.logger.RequestLoggingFilter;
import org.itcgae.siga.services.impl.SigaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuracion de seguridad para entorno de producciona. Para activar esta
 * configuración hay que activar la siguiente propiedad:
 * security.basic.enabled=true
 * 
 * @author DTUser
 *
 */

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(prefix = "security.basic", value = { "enabled" }, havingValue = "true", matchIfMissing = false)
public class ProConfigSecurity extends WebSecurityConfigurerAdapter {

	private SigaUserDetailsService userDetailsService;

	@Value("${security.login.url:/login}")
	String loginUrl;

	@Value("${security.login.method:POST}")
	String loginMethod;

	// 1 dia
	@Value("${security.token.expiration-time:86400000}")
	long expirationTime;

	@Value("${security.token.sign-key:1234}")
	String secretSignKey;

	@Value("${security.token.header-auth-key:Authorization}")
	String tokenHeaderAuthKey;

	@Value("${security.token.prefix:Bearer }")
	String tokenPrefix;

	@Autowired
	private CgaeAuthenticationProvider cgaeAuthenticationProvider;

	public ProConfigSecurity(SigaUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		/*
		 * 1. Se desactiva el uso de cookies 
		 * 2. Se activa la configuración CORS con los valores por defecto 
		 * 3. Se desactiva el filtro CSRF 
		 * 4. Se indica que el login no requiere autenticación 
		 * 5. Se indica que el resto de URLs esten securizadas
		 */
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().cors().and().csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.resolve(loginMethod), loginUrl).permitAll()
				.anyRequest().authenticated().and()
				.addFilterBefore(new CgaeAuthenticationFilter(authenticationManager(), loginMethod, loginUrl,
						tokenHeaderAuthKey, tokenPrefix), BasicAuthenticationFilter.class)
				.addFilter(new CgaeAuthorizationFilter(authenticationManager()))
				.addFilterAfter(new RequestLoggingFilter(), BasicAuthenticationFilter.class);;

		// Configuramos el token con los parametros de configuracion
		CgaeUserAuthenticationToken.configure(secretSignKey, tokenPrefix, expirationTime);
		CgaeAuthorizationFilter.configure(secretSignKey, tokenHeaderAuthKey, tokenPrefix);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Se define la clase que recupera los usuarios y el algoritmo para
		// procesar las
		// passwords
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(cgaeAuthenticationProvider);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}
