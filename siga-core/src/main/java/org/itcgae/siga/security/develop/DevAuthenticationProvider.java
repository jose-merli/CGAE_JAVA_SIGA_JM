package org.itcgae.siga.security.develop;

import org.apache.log4j.Logger;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.itcgae.siga.security.UserCgae;
import org.itcgae.siga.services.impl.SigaUserDetailsService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Configurable
public class DevAuthenticationProvider implements AuthenticationProvider {

	Logger LOGGER = Logger.getLogger(DevAuthenticationProvider.class);

	private SigaUserDetailsService userDetailsService;

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = (SigaUserDetailsService) userDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UserAuthenticationToken cgaeAuthenticaton = (UserAuthenticationToken) authentication;
		UserCgae user = (UserCgae) this.userDetailsService.loadUserByUsername(cgaeAuthenticaton.getUser());
		return new UserAuthenticationToken(user.getDni(), null, user, null,
				cgaeAuthenticaton.getAuthorities());

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UserAuthenticationToken.class);
	}

//	@Override
//	protected void additionalAuthenticationChecks(UserDetails userDetails,
//			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//
//		if (authentication.getCredentials() == null) {
//			logger.debug("Authentication failed: no credentials provided");
//
//			throw new BadCredentialsException(
//					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
//		}
//	}
//
//	@Override
//	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
//			throws AuthenticationException {
//
//		UserDetails loadedUser;
//
//		try {
//			loadedUser = this.getUserDetailsService().loadUserByUsername(username);
//		} catch (Exception repositoryProblem) {
//			throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
//		}
//
//		if (loadedUser == null) {
//			throw new InternalAuthenticationServiceException(
//					"UserDetailsService returned null, which is an interface contract violation");
//		}
//		return loadedUser;
//	}

}
