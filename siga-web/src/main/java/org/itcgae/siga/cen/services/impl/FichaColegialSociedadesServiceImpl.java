package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaItem;
import org.itcgae.siga.DTOs.cen.FichaDatosColegialesDTO;
import org.itcgae.siga.DTOs.cen.FichaDatosColegialesItem;
import org.itcgae.siga.cen.services.IFichaColegialSociedadesService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenComponentesExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichaColegialSociedadesServiceImpl implements IFichaColegialSociedadesService {

	private Logger LOGGER = Logger.getLogger(FichaColegialSociedadesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenComponentesExtendsMapper cenComponentesExtendsMapper;
	
	@Override
	public BusquedaJuridicaDTO searchSocieties(int numPagina, String idPersona,
			HttpServletRequest request) {
		
		LOGGER.info("searchSocieties() -> Entrada al servicio para la búsqueda de sociedades por idPersona");
		
		List<BusquedaJuridicaItem> fichaDatosColegialesItems = new ArrayList<BusquedaJuridicaItem>();
		BusquedaJuridicaDTO fichaDatosColegiales = new BusquedaJuridicaDTO();
		String idLenguaje = null;
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchSocieties() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchSocieties() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();
				
			LOGGER.info(
						"searchSocieties() / cenComponentesExtendsMapper.searchSocieties() -> Entrada a cenComponentesExtendsMapper para obtener sociedades");
			fichaDatosColegialesItems = cenComponentesExtendsMapper.searchSocieties(idPersona, idLenguaje,  String.valueOf(idInstitucion));
				LOGGER.info(
						"searchSocieties() / cenComponentesExtendsMapper.searchSocieties() -> Salida de cenComponentesExtendsMapper para obtener sociedades");

				fichaDatosColegiales.setBusquedaJuridicaItems(fichaDatosColegialesItems);
			} 
			else {
				LOGGER.warn("searchSocieties() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchSocieties() -> idInstitucion del token nula");
		}
		
		LOGGER.info("searchSocieties() -> Salida del servicio para la búsqueda de sociedades por idPersona");
		return fichaDatosColegiales;
	}

}
