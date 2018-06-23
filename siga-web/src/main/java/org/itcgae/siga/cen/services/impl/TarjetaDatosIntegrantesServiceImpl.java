package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesItem;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;
import org.itcgae.siga.cen.services.ITarjetaDatosIntegrantesService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenComponentesExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TarjetaDatosIntegrantesServiceImpl implements ITarjetaDatosIntegrantesService{

	private Logger LOGGER = Logger.getLogger(TarjetaDatosIntegrantesServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenComponentesExtendsMapper cenComponentesExtendsMapper;
	

	




	@Override
	public DatosIntegrantesDTO searchIntegrantesData(int numPagina, DatosIntegrantesSearchDTO datosIntegrantesSearchDTO,
			HttpServletRequest request) {
		LOGGER.info("searchIntegrantesData() -> Entrada al servicio para la búsqueda por filtros de integrantes ");
		
		List<DatosIntegrantesItem> datosIntegrantesItem = new ArrayList<DatosIntegrantesItem>();
		DatosIntegrantesDTO datosIntegrantesDTO = new DatosIntegrantesDTO();

		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchIntegrantesData() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchIntegrantesData() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"searchIntegrantesData() / cenComponentesExtendsMapper.selectIntegrantes() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de integrantes ");
				datosIntegrantesSearchDTO.setIdInstitucion(idInstitucion.toString());
				datosIntegrantesItem = cenComponentesExtendsMapper.selectIntegrantes(datosIntegrantesSearchDTO);
				LOGGER.info(
						"searchIntegrantesData() / cenComponentesExtendsMapper.selectIntegrantes() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de integrantes ");

				datosIntegrantesDTO.setDatosIntegrantesItem(datosIntegrantesItem);
			} 
			else {
				LOGGER.warn("searchIntegrantesData() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchIntegrantesData() -> idInstitucion del token nula");
		}
		
		LOGGER.info("searchIntegrantesData() -> Salida del servicio para la búsqueda por filtros de integrantes ");
		return datosIntegrantesDTO;
	}





	






}
