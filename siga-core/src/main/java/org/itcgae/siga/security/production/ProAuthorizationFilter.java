package org.itcgae.siga.security.production;

import org.apache.log4j.Logger;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.itcgae.siga.security.UserCgae;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.security.YamlPermisos;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProAuthorizationFilter extends BasicAuthenticationFilter {

    private final Logger LOGGER = Logger.getLogger(ProAuthorizationFilter.class);

    private YamlPermisos yamlPermisosProperties;

    public ProAuthorizationFilter(AuthenticationManager authManager, YamlPermisos yamlPermisosProperties) {
        super(authManager);
        this.yamlPermisosProperties = yamlPermisosProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        UserAuthenticationToken authentication = UserTokenUtils.getAuthentication(request);

        if (null != authentication) {

            UserCgae user = (UserCgae) authentication.getPrincipal();
            HashMap<String, String> permisosToken = user.getPermisos();
            Map<String, List<String>> permisosYaml = yamlPermisosProperties.getPermisos();

            if (null != permisosToken && null != permisosYaml) {

                String url = request.getServletPath();
                List<String> listaProcesos = permisosYaml.get(url);

                if (null != listaProcesos && !listaProcesos.isEmpty()) {

                    boolean tienePermiso = false;

                    for (String proceso : listaProcesos) {
                        if (permisosToken.containsKey(proceso) && permisosToken.get(proceso).equalsIgnoreCase("3")) {
                            tienePermiso = true;
                            break;
                        }
                    }

                    if (!tienePermiso) {
                        LOGGER.error("Se esta intentando realizar una operacion no autorizada:\n" +
                                "- Recurso -> " + url + "\n" +
                                "- Usuario -> " + user.getDni());
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }

                }

            }

        } else {
            chain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}
