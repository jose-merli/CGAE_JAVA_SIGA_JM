package org.itcgae.siga.security;

import java.util.HashMap;

public class UserCgae {
	    
	 
	    private String username;
	 
	    private HashMap<String,String> permisos;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public HashMap<String, String> getPermisos() {
			return permisos;
		}

		public void setPermisos(HashMap<String, String> permisos) {
			this.permisos = permisos;
		}

	
	
}
