package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SoliModiDireccionesItem;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.cen.services.ISolModifDatosDireccionesDetailService;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSoliModiDireccionesExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolModifDatosDireccionesDetailServiceImpl implements ISolModifDatosDireccionesDetailService {

	private Logger LOGGER = Logger.getLogger(SolModifDatosDireccionesDetailServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenSoliModiDireccionesExtendsMapper cenSoliModiDireccionesExtendsMapper;

	@Override
	public SoliModiDireccionesItem searchSolModifDatosDireccionesDetail(int numPagina, StringDTO idSolicitud,
			HttpServletRequest request) {
		LOGGER.info(
				"searchSolModifDatosDireccionesDetail() -> Entrada al servicio para recuperar las solicitudes de modificación específica de direcciones");

		SoliModiDireccionesItem soliModiDireccionesItem = new SoliModiDireccionesItem();
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchSolModifDatosDireccionesDetail() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchSolModifDatosDireccionesDetail() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

//				soliModiDireccionesItem = cenSoliModiDireccionesExtendsMapper
//						.selectSoliModiDirecciones(idSolicitud.getValor(), usuario.getIdlenguaje());
			}
		}

		LOGGER.info(
				"searchSolModifDatosDireccionesDetail() -> Salida al servicio para recuperar las solicitudes de modificación específica de direcciones");
		return soliModiDireccionesItem;
	}

	
	@Autowired
	private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;
	
	@Override
	public DatosDireccionesDTO searchDatosDirecciones(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		
		LOGGER.info("searchDatosDirecciones() -> Entrada al servicio para la búsqueda por filtros de direcciones");

		List<DatosDireccionesItem> datosDireccionesItem = new ArrayList<DatosDireccionesItem>();
		DatosDireccionesDTO datosDireccionesDTO = new DatosDireccionesDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchDatosDirecciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchDatosDirecciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"searchDatosDirecciones() / cenDireccionesExtendsMapper.selectDirecciones() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de direcciones");
				datosDireccionesItem = cenDireccionesExtendsMapper.selectDireccionesSolEsp(solModificacionItem.getIdPersona(), solModificacionItem.getCodigo(), String.valueOf(idInstitucion));
				LOGGER.info(
						"searchDatosDirecciones() / cenDireccionesExtendsMapper.selectDirecciones() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de direcciones");

				if (null != datosDireccionesItem && datosDireccionesItem.size() > 0) {
					for (DatosDireccionesItem datosDireccionItem : datosDireccionesItem) {
						if (!UtilidadesString.esCadenaVacia(datosDireccionItem.getIdTipoDireccionList())) {
							datosDireccionItem
									.setIdTipoDireccion(datosDireccionItem.getIdTipoDireccionList().split(";"));
						}
					}
				}
				datosDireccionesDTO.setDatosDireccionesItem(datosDireccionesItem);
			} else {
				LOGGER.warn(
						"searchDatosDirecciones() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("searchDatosDirecciones() -> idInstitucion del token nula");
		}

		LOGGER.info("searchDatosDirecciones() -> Salida del servicio para la búsqueda por filtros de direcciones");
		return datosDireccionesDTO;
	}
}
