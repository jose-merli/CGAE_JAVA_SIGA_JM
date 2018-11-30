package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SoliModiDireccionesDTO;
import org.itcgae.siga.DTOs.cen.SoliModiDireccionesItem;
import org.itcgae.siga.cen.services.ISolModifDatosDireccionesDetailService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenPais;
import org.itcgae.siga.db.entities.CenPoblaciones;
import org.itcgae.siga.db.entities.CenProvincias;
import org.itcgae.siga.db.entities.CenSolimodidirecciones;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPaisExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPoblacionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenProvinciasExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolimodidireccionesExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolModifDatosDireccionesDetailServiceImpl implements ISolModifDatosDireccionesDetailService {

	private Logger LOGGER = Logger.getLogger(SolModifDatosDireccionesDetailServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;

	@Autowired
	private CenSolimodidireccionesExtendsMapper cenSoliModiDireccionesExtendsMapper;

	@Autowired
	private CenPaisExtendsMapper cenPaisExtendsMapper;

	@Autowired
	private CenProvinciasExtendsMapper cenProvinciasExtendsMapper;

	@Autowired
	private CenPoblacionesExtendsMapper cenPoblacionesExtendsMapper;

	@Autowired
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;

	@Override
	public SoliModiDireccionesItem searchSolModifDatosDireccionesDetail(int numPagina,
			SolModificacionItem solModificacionItem, HttpServletRequest request) {
		LOGGER.info(
				"searchSolModifDatosDireccionesDetail() -> Entrada al servicio para recuperar las solicitudes de modificación específica de direcciones");

		SoliModiDireccionesItem soliModiDireccionesItem = new SoliModiDireccionesItem();

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
				AdmUsuarios usuario = usuarios.get(0);

				CenSolimodidirecciones cenSolimodidirecciones = cenSoliModiDireccionesExtendsMapper
						.selectByPrimaryKey(Long.valueOf(solModificacionItem.getIdSolicitud()));

				if (cenSolimodidirecciones != null) {

					CenPais cenPais = cenPaisExtendsMapper.selectByPrimaryKey(cenSolimodidirecciones.getIdpais());

					CenProvincias cenProvincias = cenProvinciasExtendsMapper
							.selectByPrimaryKey(cenSolimodidirecciones.getIdprovincia());

					CenPoblaciones cenPoblaciones = cenPoblacionesExtendsMapper
							.selectByPrimaryKey(cenSolimodidirecciones.getIdpoblacion());

					soliModiDireccionesItem = convertItemToSolModifDatosDireccionesDetail(cenSolimodidirecciones,
							usuario, cenPais, cenProvincias, cenPoblaciones);

				}
			}
		}
		LOGGER.info(
				"searchSolModifDatosDireccionesDetail() -> Salida al servicio para recuperar las solicitudes de modificación específica de direcciones");
		return soliModiDireccionesItem;
	}

	@Override
	public SoliModiDireccionesItem searchDatosDirecciones(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request) {

		LOGGER.info("searchDatosDirecciones() -> Entrada al servicio para la búsqueda por filtros de direcciones");

		SoliModiDireccionesItem soliModiDireccionesItem = new SoliModiDireccionesItem();

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
				List<DatosDireccionesItem> datosDireccionesItem = cenDireccionesExtendsMapper.selectDireccionesSolEsp(
						solModificacionItem.getIdPersona(), solModificacionItem.getCodigo(),
						String.valueOf(idInstitucion));
				LOGGER.info(
						"searchDatosDirecciones() / cenDireccionesExtendsMapper.selectDirecciones() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de direcciones");

				if (null != datosDireccionesItem && datosDireccionesItem.size() > 0) {


						soliModiDireccionesItem = convertItemToDatosDirecciones(
								datosDireccionesItem.get(0));
				}
			} else {
				LOGGER.warn(
						"searchDatosDirecciones() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("searchDatosDirecciones() -> idInstitucion del token nula");
		}

		LOGGER.info("searchDatosDirecciones() -> Salida del servicio para la búsqueda por filtros de direcciones");
		return soliModiDireccionesItem;
	}

	private SoliModiDireccionesItem convertItemToSolModifDatosDireccionesDetail(
			CenSolimodidirecciones cenSolimodidirecciones, AdmUsuarios usuario, CenPais cenPais,
			CenProvincias cenProvincias, CenPoblaciones cenPoblaciones) {
		SoliModiDireccionesItem soliModiDireccionesItem = new SoliModiDireccionesItem();

		soliModiDireccionesItem.setIdDireccion(String.valueOf(cenSolimodidirecciones.getIddireccion()));
		soliModiDireccionesItem.setIdPersona(String.valueOf(cenSolimodidirecciones.getIdpersona()));

		soliModiDireccionesItem.setDomicilio(cenSolimodidirecciones.getDomicilio());
		if (cenPais != null) {
			GenRecursosCatalogosKey genRecursosCatalogosKey = new GenRecursosCatalogosKey();
			genRecursosCatalogosKey.setIdlenguaje(usuario.getIdlenguaje());
			genRecursosCatalogosKey.setIdrecurso(cenPais.getNombre());

			GenRecursosCatalogos genRecursosCatalogos = genRecursosCatalogosExtendsMapper
					.selectByPrimaryKey(genRecursosCatalogosKey);
			soliModiDireccionesItem.setPais(genRecursosCatalogos.getDescripcion());
		}
		soliModiDireccionesItem.setCodigoPostal(cenSolimodidirecciones.getCodigopostal());

		if (cenProvincias != null) {
			soliModiDireccionesItem.setProvincia(cenProvincias.getNombre());
		}

		if (cenPoblaciones != null) {
			soliModiDireccionesItem.setPoblacion(cenPoblaciones.getNombre());
		}

		soliModiDireccionesItem.setPoblacionExtranjera(cenSolimodidirecciones.getPoblacionextranjera());
		soliModiDireccionesItem.setTelefono(cenSolimodidirecciones.getTelefono1());
		soliModiDireccionesItem.setFax(cenSolimodidirecciones.getFax1());
		soliModiDireccionesItem.setMovil(cenSolimodidirecciones.getMovil());
		soliModiDireccionesItem.setCorreoElectronico(cenSolimodidirecciones.getCorreoelectronico());
		soliModiDireccionesItem.setPaginaWeb(cenSolimodidirecciones.getPaginaweb());

		return soliModiDireccionesItem;
	}

	private SoliModiDireccionesItem convertItemToDatosDirecciones(DatosDireccionesItem datosDireccionItem) {

		SoliModiDireccionesItem soliModiDireccionesItem = new SoliModiDireccionesItem();

		soliModiDireccionesItem.setDomicilio(datosDireccionItem.getDomicilio());
		soliModiDireccionesItem.setPais(datosDireccionItem.getNombrePais());
		soliModiDireccionesItem.setCodigoPostal(datosDireccionItem.getCodigoPostal());
		soliModiDireccionesItem.setProvincia(datosDireccionItem.getNombreProvincia());
		soliModiDireccionesItem.setPoblacion(datosDireccionItem.getNombrePoblacion());
		soliModiDireccionesItem.setPoblacionExtranjera(datosDireccionItem.getPoblacionExtranjera());
		soliModiDireccionesItem.setTelefono(datosDireccionItem.getTelefono());
		soliModiDireccionesItem.setFax(datosDireccionItem.getFax());
		soliModiDireccionesItem.setMovil(datosDireccionItem.getMovil());
		soliModiDireccionesItem.setCorreoElectronico(datosDireccionItem.getCorreoElectronico());
		soliModiDireccionesItem.setPaginaWeb(datosDireccionItem.getPaginaWeb());

		return soliModiDireccionesItem;
	}
}
