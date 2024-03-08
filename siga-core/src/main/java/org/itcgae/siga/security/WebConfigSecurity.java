package org.itcgae.siga.security;

import org.itcgae.siga.logger.RequestLoggingFilter;
import org.itcgae.siga.security.develop.DevAuthenticationFilter;
import org.itcgae.siga.security.develop.DevAuthorizationFilter;
import org.itcgae.siga.security.production.ProAuthenticationFilter;
import org.itcgae.siga.security.production.ProAuthorizationFilter;
import org.itcgae.siga.services.impl.SigaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
//@ConditionalOnProperty(prefix = "security.basic", value = { "enabled" }, havingValue = "false", matchIfMissing = false)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

	private SigaUserDetailsService userDetailsService;

	@Value("${security.basic.enabled:true}")
	Boolean security;
	
	@Value("${security.login.url:/login}")
	String loginUrl;

	@Value("${security.login.method:POST}")
	String loginMethod;

	// 12 horas
	@Value("${security.token.expiration-time:43200000}")
	long expirationTime;

	@Value("${security.token.sign-key:1234}")
	String secretSignKey;

	@Value("${security.token.header-auth-key:Authorization}")
	String tokenHeaderAuthKey;

	@Value("${security.token.prefix:Bearer }")
	String tokenPrefix;

	@Autowired
	private CgaeAuthenticationProvider proAuthenticationProvider;
	
	@Autowired
	private YamlPermisos yamlPermisosProperties;

	public WebConfigSecurity(SigaUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		String [] authorizedRequests = {
				loginUrl, 
				"/loginDevelop",
				"/validaInstitucion",
				"/instituciones", 
				"/perfilespost", 
				"/perfiles",
				"/getEntorno",
				"/getEnvParams",
				"/ws/**",
				"/institucionesUsuario",
				"/rolesColegioUsuario",
				"/perfilesColegioRol"
		};
		/*
		 * 1. Se desactiva el uso de cookies 
		 * 2. Se activa la configuración CORS con los valores por defecto 
		 * 3. Se desactiva el filtro CSRF 
		 * 4. Se indica que el login no requiere autenticación 
		 * 5. Se indica que el resto de URLs esten securizadas
		 */ 
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().cors().and().csrf().disable().authorizeRequests()
				.antMatchers(authorizedRequests).permitAll()
				.anyRequest().authenticated().and()
				.addFilterBefore(new ProAuthenticationFilter(authenticationManager(), loginMethod, loginUrl,
						tokenHeaderAuthKey, userDetailsService), BasicAuthenticationFilter.class)
				.addFilter(new ProAuthorizationFilter(authenticationManager(), yamlPermisosProperties))
				.addFilterBefore(new DevAuthenticationFilter(authenticationManager(), "GET", "/loginDevelop",
						tokenHeaderAuthKey, userDetailsService), BasicAuthenticationFilter.class)
				.addFilterAfter(new RequestLoggingFilter(), BasicAuthenticationFilter.class);
		
		if (!security){
			httpSecurity.addFilter(new DevAuthorizationFilter(authenticationManager(), userDetailsService, authorizedRequests));
		}
		
		// Configuramos el token con los parametros de configuracion
		UserTokenUtils.configure(secretSignKey, tokenPrefix, expirationTime, tokenHeaderAuthKey);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		proAuthenticationProvider.setUserDetailsService(userDetailsService);
		auth.authenticationProvider(proAuthenticationProvider);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}
