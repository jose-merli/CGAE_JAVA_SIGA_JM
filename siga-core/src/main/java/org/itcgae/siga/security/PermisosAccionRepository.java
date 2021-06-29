package org.itcgae.siga.security;

import java.util.List;
import java.util.Map;


//@Service
//@Scope("singleton")
public class PermisosAccionRepository {
	
	private static Map<String, List<String>> permisosAccion;

	public static Map<String, List<String>> getPermisosAccion() {
		return permisosAccion;
	}

	public static void setPermisosAccion(Map<String, List<String>> permisosAccion) {
		PermisosAccionRepository.permisosAccion = permisosAccion;
	}
	
	

}
