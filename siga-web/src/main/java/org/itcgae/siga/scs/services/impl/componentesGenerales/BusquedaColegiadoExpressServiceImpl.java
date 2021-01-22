package org.itcgae.siga.scs.services.impl.componentesGenerales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.ColegiadoJGDTO;
import org.itcgae.siga.DTOs.scs.ColegiadoJGItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.scs.services.componentesGenerales.IBusquedaColegiadosExpressService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaColegiadoExpressServiceImpl implements IBusquedaColegiadosExpressService{

	private Logger LOGGER = Logger.getLogger(BusquedaColegiadoExpressServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	public ColegiadoJGDTO busquedaColegiadosExpress(String colegiadoJGItem, HttpServletRequest request) {
		
		LOGGER.info("busquedaColegiadosExpress() -> Entrada al servicio para obtener el colegiado");
		
		ColegiadoJGDTO colegiadoJGDTO = new ColegiadoJGDTO();
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				// la consulta necesita idinstitucion de token y idlenguaje del usuario logueado
				usuario.setIdinstitucion(idInstitucion);
				
				LOGGER.info("busquedaColegiadosExpress() / cenPersonaExtendsMapper.busquedaColegiadoExpress() -> Entrada a cenPersonaExtendsMapper para obtener el colegiado");
				colegiadoJGDTO.setColegiadoJGItem(cenPersonaExtendsMapper.busquedaColegiadoExpress(colegiadoJGItem, idInstitucion.toString()));
				LOGGER.info("getLabel() / cenGruposclienteExtendsMapper.getLabel() -> Salida de cenGruposclienteExtendsMapper para obtener lista de tipos de colegios");
			}else {
				LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		}else {
			LOGGER.warn("getLabel() -> idInstitucion del token nula");
		}
		
		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return colegiadoJGDTO;
	}
}