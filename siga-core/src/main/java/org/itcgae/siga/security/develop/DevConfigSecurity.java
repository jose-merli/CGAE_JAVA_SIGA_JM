package org.itcgae.siga.security.develop;

import org.itcgae.siga.logger.RequestLoggingFilter;
import org.itcgae.siga.security.UserTokenUtils;
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
 * Configuracion de seguridad para entorno de desarrollo. Para activar esta
 * configuración hay que desactivar la siguiente propiedad:
 * security.basic.enabled=false
 * 
 * @author DTUser
 *
 */

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(prefix = "security.basic", value = { "enabled" }, havingValue = "false", matchIfMissing = true)
public class DevConfigSecurity extends WebSecurityConfigurerAdapter {
	
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
	private DevAuthenticationProvider devAuthenticationProvider;
	
	
	public DevConfigSecurity(SigaUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors().and()
				.csrf().disable().authorizeRequests().antMatchers("**").permitAll()
				.anyRequest().authenticated().and()
				.addFilterBefore(new DevAuthenticationFilter(authenticationManager(), loginMethod, loginUrl,
						tokenHeaderAuthKey, tokenPrefix), BasicAuthenticationFilter.class)
				.addFilter(new DevAuthorizationFilter(authenticationManager(), userDetailsService))
				.addFilterAfter(new RequestLoggingFilter(), BasicAuthenticationFilter.class);

		// Configuramos el token con los parametros de configuracion
		UserTokenUtils.configure(secretSignKey, tokenPrefix, expirationTime, tokenHeaderAuthKey);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		devAuthenticationProvider.setUserDetailsService(userDetailsService);
		auth.authenticationProvider(devAuthenticationProvider);
	}

	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
		corsConfiguration.addAllowedMethod(HttpMethod.PUT);
		corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
}
