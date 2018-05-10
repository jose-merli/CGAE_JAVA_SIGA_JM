package org.itcgae.siga.security;

import java.util.Collection;
import java.util.HashMap;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserCgae implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String dni;
	private String grupo;
	private String institucion;
    private HashMap<String,String> permisos;
	
    public UserCgae() {
	}
    
	public UserCgae(String dni) {
		this.dni = dni;
	}
	
	public UserCgae(String dni, String grupo, String institucion, HashMap<String,String> permisos) {
		this.dni = dni;
		this.grupo = grupo;
		this.institucion = institucion;
		this.permisos = permisos;
	}
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public HashMap<String, String> getPermisos() {
		return permisos;
	}

	public void setPermisos(HashMap<String, String> permisos) {
		this.permisos = permisos;
	}

	@Override
	public String getUsername() {
		return dni;
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
