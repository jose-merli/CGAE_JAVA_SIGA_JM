package org.itcgae.siga.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.itcgae.siga.db.entities.AdmRol;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserCgae implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String dni;
	private String nombre;
	private String grupo;
	private AdmRol rol;
	private String institucion;
	private String letrado;
    public String getLetrado() {
		return letrado;
	}

	public void setLetrado(String letrado) {
		this.letrado = letrado;
	}

	private HashMap<String,String> permisos;
    private List<String> perfiles;
	
    public List<String> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<String> perfiles) {
		this.perfiles = perfiles;
	}

	public UserCgae() {
	}
    
	public UserCgae(String dni) {
		this.dni = dni;
	}
	
	public UserCgae(String dni, String grupo, String institucion, HashMap<String,String> permisos,List<String> perfiles,String letrado, AdmRol rol2, String nombre2) {
		this.dni = dni;
		this.grupo = grupo;
		this.rol = rol2;
		this.institucion = institucion;
		this.permisos = permisos;
		this.perfiles = perfiles;
		this.letrado = letrado;
		this.nombre = nombre2;
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
	
	public AdmRol getRol() {
		return rol;
	}

	public void setRol(AdmRol rol) {
		this.rol = rol;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	@Override
	public String toString() {
		return "UserCgae [dni=" + dni + ", nombre=" + nombre + ", grupo=" + grupo + ", rol=" + rol.getCodigoext() + ", institucion=" + institucion + ", permisos=" + permisos + ", perfiles=" + perfiles
				+ ", letrado=" + letrado + "]";
	}
	
	

}
