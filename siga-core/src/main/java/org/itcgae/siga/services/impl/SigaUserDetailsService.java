package org.itcgae.siga.services.impl;

import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SigaUserDetailsService implements UserDetailsService  {

	@Override
	//TODO: Añadir llamada a bbdd para recuperar el usuario
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Usuario :" + username);
		return new User(username, "password", emptyList());
	}
	
	

}
