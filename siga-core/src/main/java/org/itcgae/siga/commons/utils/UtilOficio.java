package org.itcgae.siga.commons.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.ParametroItem;
import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilOficio {
	
	private static Logger LOGGER = Logger.getLogger(UtilOficio.class);
	
	@Autowired
	private AdmUsuariosMapper admUsuariosMapper;
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
	public boolean validaNIG(String nig,  HttpServletRequest request) {
		boolean nigValido = false;
		String valorParametroNIG = "";
		List<ParametroItem> parametroItems = new ArrayList<ParametroItem>();
		LOGGER.info("validaNIG() / Entrada a validaNIG para comprobar si el NIG es valido o no");
		
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		LOGGER.info("updateParameter() / admUsuariosMapper.selectByExample() -> Entrada a admUsuariosMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
		LOGGER.info("updateParameter() / admUsuariosMapper.selectByExample() -> Salida de admUsuariosMapper para obtener información del usuario logeado");
		ParametroRequestDTO parametroRequestDTO = new ParametroRequestDTO();
		parametroRequestDTO.setIdInstitucion(idInstitucion.toString());
		parametroRequestDTO.setModulo("SCS");
		parametroRequestDTO.setParametrosGenerales("NIG_VALIDADOR");
		if(null != usuarios && usuarios.size() > 0) {
			AdmUsuarios usuario = usuarios.get(0);
			parametroRequestDTO.setIdInstitucion(String.valueOf(idInstitucion));
			
			if (!parametroRequestDTO.getParametrosGenerales().equalsIgnoreCase("")
					&& !parametroRequestDTO.getModulo().equalsIgnoreCase("")
					&& parametroRequestDTO.getIdInstitucion() != null) {
				parametroItems = genParametrosExtendsMapper.getParametersSearchGeneral(1, parametroRequestDTO, usuario.getIdlenguaje(), String.valueOf(idInstitucion));
				
			}
			
			if(parametroItems.size() > 0 ) {
				for(ParametroItem parametro: parametroItems) {
					if(parametro.getParametro().equals("NIG_VALIDADOR") 
							&& (parametro.getIdInstitucion().equals(parametro.getIdInstitucion()) 
									|| parametro.getIdInstitucion().equals("0"))) {
						valorParametroNIG = parametro.getValor();
						if(!UtilidadesString.esCadenaVacia(nig)) {
							nigValido = nig.matches(valorParametroNIG);
						}else {
							nigValido = true;
						}
					}
				}
			}
		}
	
		LOGGER.info("validaNIG() / Salida de validaNIG para comprobar si el NIG es valido o no");
		
		return nigValido;
	}

}
