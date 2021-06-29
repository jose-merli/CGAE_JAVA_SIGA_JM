package org.itcgae.siga.security.production;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.security.PermisosAccionRepository;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.itcgae.siga.security.UserCgae;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;

@Service
public class ProAuthorizationFilter extends BasicAuthenticationFilter {

	
	public ProAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		UserAuthenticationToken authentication = UserTokenUtils.getAuthentication(request);
		
		Map<String, List<String>> permisosAccionBD = PermisosAccionRepository.getPermisosAccion();
		if(authentication != null) {
			UserCgae user = (UserCgae)authentication.getPrincipal();
			HashMap<String, String> permisosToken = user.getPermisos();
			if(permisosAccionBD != null){
				String url = request.getServletPath();
				List<String> idProcesoPermisoBD = permisosAccionBD.get(url);
				if(idProcesoPermisoBD != null && permisosToken != null) {
					String permisoToken = permisosToken.get(idProcesoPermisoBD.get(0));
					if(!idProcesoPermisoBD.contains(permisoToken)){
						response.sendError(HttpServletResponse.SC_FORBIDDEN);
						return;
					}
				}
			}
		}

	
		if (authentication == null)  {
			chain.doFilter(request, response);
			return;
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}


}
