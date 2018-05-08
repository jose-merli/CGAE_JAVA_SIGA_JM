package org.itcgae.siga.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipalCgae implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private UserCgae userCgae;
	
	
	
	public UserCgae getUserCgae() {
		return userCgae;
	}

	public void setUserCgae(UserCgae userCgae) {
		this.userCgae = userCgae;
	}

	
	
	
	
	public UserPrincipalCgae(UserCgae userCgae) {
		
		this.userCgae = userCgae;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
