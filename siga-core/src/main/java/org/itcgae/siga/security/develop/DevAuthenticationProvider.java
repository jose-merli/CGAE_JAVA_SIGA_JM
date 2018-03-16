package org.itcgae.siga.security.develop;

import org.apache.log4j.Logger;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@Configurable
public class DevAuthenticationProvider implements AuthenticationProvider {

	Logger LOGGER = Logger.getLogger(DevAuthenticationProvider.class);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UserAuthenticationToken cgaeAuthenticaton = (UserAuthenticationToken) authentication;

		return new UserAuthenticationToken(cgaeAuthenticaton.getPrincipal(), null, null,
				cgaeAuthenticaton.getAuthorities());

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UserAuthenticationToken.class);
	}


}
