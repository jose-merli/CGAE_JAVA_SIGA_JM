package org.itcgae.siga.cen.services.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.SolicitudIncorporacionSearchDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.cen.SolIncorporacionDTO;
import org.itcgae.siga.DTOs.cen.SolIncorporacionItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.cen.services.ISolicitudIncorporacionService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenSolicitudincorporacion;
import org.itcgae.siga.db.mappers.CenCuentasbancariasMapper;
import org.itcgae.siga.db.mappers.CenDireccionesMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.CenSolicitudincorporacionMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCuentasbancariasExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDocumentacionmodalidadExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadoSolicitudExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocivilExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudincorporacionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTipocolegiacionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTipoidentificacionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposolicitudExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTratamientoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import scala.annotation.meta.setter;


@Service
public class SolicitudIncorporacionServiceImpl implements ISolicitudIncorporacionService{
	
	private Logger LOGGER = Logger.getLogger(SolicitudIncorporacionServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper _admUsuariosExtendsMapper;
	
	@Autowired
	private CenTiposolicitudExtendsMapper _cenTiposolicitudSqlExtendsMapper;
	
	@Autowired
	private CenEstadoSolicitudExtendsMapper _cenEstadoSolicitudExtendsMapper;
	
	@Autowired
	private CenSolicitudincorporacionExtendsMapper _cenSolicitudincorporacionExtendsMapper;
	
	@Autowired
	private CenEstadocivilExtendsMapper _cenEstadocivilExtendsMapper;
	
	@Autowired
	private CenTratamientoExtendsMapper _cenTratamientoExtendsMapper;
	
	@Autowired
	private CenTipoidentificacionExtendsMapper _cenTipoidentificacionExtendsMapper;
	
	@Autowired
	private CenTipocolegiacionExtendsMapper _cenTipocolegiacionExtendsMapper;
	
	@Autowired
	private CenDocumentacionmodalidadExtendsMapper _cenDocumentacionmodalidadExtendsMapper;
	
	@Autowired
	private CenSolicitudincorporacionMapper _cenSolicitudincorporacionMapper;
	
	@Autowired
	private CenPersonaMapper _cenPersonaMapper;
	
	@Autowired
	private CenPersonaExtendsMapper _cenPersonaExtendsMapper;
	
	@Autowired
	private CenDireccionesExtendsMapper _cenDireccionesExtendsMapper;
	
	@Autowired
	private CenDireccionesMapper _cenDireccionesMapper;
	
	@Autowired
	private CenCuentasbancariasExtendsMapper _cenCuentasbancariasExtendsMapper;
	
	@Autowired
	private CenCuentasbancariasMapper _cenCuentasbancariasMapper;
	
	@Override
	public ComboDTO getTipoSolicitud(HttpServletRequest request) {
		LOGGER.info("getTipoSolicitud() -> Entrada al servicio para cargar el tipo de solicitud");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();
		
		if(idInstitucion!= null){
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getTipoSolicitud() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTipoSolicitud() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(usuarios != null && usuarios.size()>0){
				
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getTipoSolicitud() / cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los tipos de solicitud");

				List<ComboItem> comboItems = _cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud(usuario.getIdlenguaje());
				
				if(comboItems != null && comboItems.size() >0){
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
					combo.setCombooItems(comboItems);
				}
			}
			
		}
		LOGGER.info("getTipoSolicitud() -> Salida del servicio para obtener los tipos de solicitud");
		return combo;
	}

	@Override
	public ComboDTO getEstadoSolicitud(HttpServletRequest request) {
		LOGGER.info("getTipoSolicitud() -> Entrada al servicio para cargar los estados de solicitud");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();
		
		if(idInstitucion!= null){
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getEstadoSolicitud() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEstadoSolicitud() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(usuarios != null && usuarios.size()>0){
				
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getEstadoSolicitud() / cenEstadoSolicitudExtendsMapper.selectTipoSolicitud() -> Entrada a cenEstadoSolicitudExtendsMapper para obtener los estados de solicitud");

				List<ComboItem> comboItems = _cenEstadoSolicitudExtendsMapper.selectEstadoSolicitud(usuario.getIdlenguaje());
				
				if(comboItems != null && comboItems.size() >0){
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
					combo.setCombooItems(comboItems);
				}
			}
			
		}
		LOGGER.info("getEstadoSolicitud() -> Salida del servicio para obtener los estados de solicitud");
		return combo;
	}

	@Override
	public SolIncorporacionDTO datosSolicitudSearch(int numPagina, SolicitudIncorporacionSearchDTO solicitudIncorporacionSearchDTO,
			HttpServletRequest request) {
		LOGGER.info("getTipoSolicitud() -> Entrada al servicio para recuperar las solicitudes de incorporación");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		SolIncorporacionDTO solIncorporacionDTO = new SolIncorporacionDTO();
		
		if(idInstitucion!= null){
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosSolicitudSearch() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosSolicitudSearch() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(usuarios != null && usuarios.size()>0){
				
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info("datosSolicitudSearch() / cenEstadoSolicitudExtendsMapper.selectTipoSolicitud() -> Entrada a cenEstadoSolicitudExtendsMapper para obtener los estados de solicitud");
				solicitudIncorporacionSearchDTO.setIdInstitucion(idInstitucion.toString());
				List<SolIncorporacionItem> SolIncorporacionItemList = _cenSolicitudincorporacionExtendsMapper.getSolicitudes(solicitudIncorporacionSearchDTO, usuario.getIdlenguaje());
				solIncorporacionDTO.setSolIncorporacionItems(SolIncorporacionItemList);
				
			}
		}
		
		LOGGER.info("getTipoSolicitud() -> Salida del servicio para recuperar las solicitudes de incorporación");
		
		return solIncorporacionDTO;
	}

	@Override
	public ComboDTO getTratamiento(HttpServletRequest request) {
		LOGGER.info("getTratamiento() -> Entrada al servicio para cargar los tipos de tratamiento");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();
		
		if(idInstitucion!= null){
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getTratamiento() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTratamiento() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(usuarios != null && usuarios.size()>0){
				
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getTratamiento() / cenEstadoSolicitudExtendsMapper.selectTipoSolicitud() -> Entrada a cenEstadoSolicitudExtendsMapper para obtener los estados de solicitud");

				List<ComboItem> comboItems = _cenTratamientoExtendsMapper.selectTratamiento(usuario.getIdlenguaje());
				
				if(comboItems != null && comboItems.size() >0){
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
					combo.setCombooItems(comboItems);
				}
			}
			
		}
		LOGGER.info("getTratamiento() -> Salida del servicio para cargar los tipos de tratamiento");
		return combo;
	}

	@Override
	public ComboDTO getEstadoCivil(HttpServletRequest request) {
		LOGGER.info("getEstadoCivil() -> Entrada al servicio para cargar los estados civiles");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();
		
		if(idInstitucion!= null){
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getEstadoCivil() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEstadoCivil() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(usuarios != null && usuarios.size()>0){
				
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getEstadoCivil() / _cenEstadocivilExtendsMapper.distinctCivilStatus() -> Entrada a cenEstadoSolicitudExtendsMapper para obtener los estados civiles");

				List<ComboItem> comboItems = _cenEstadocivilExtendsMapper.distinctCivilStatus(usuario.getIdlenguaje());
				
				if(comboItems != null && comboItems.size() >0){
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
					combo.setCombooItems(comboItems);
				}
			}
			
		}
		LOGGER.info("getEstadoCivil() -> Salida del servicio para cargar los estados civiles");
		return combo;
	}

	@Override
	public ComboDTO getTipoIdentificacion(HttpServletRequest request) {
		
		LOGGER.info("getTipoIdentificacion() -> Entrada al servicio para cargar los tipos de identificacion");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();
		
		if(idInstitucion!= null){
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getTipoIdentificacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTipoIdentificacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(usuarios != null && usuarios.size()>0){
				
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getTipoIdentificacion() / _cenTipoidentificacionExtendsMapper.getIdentificationTypes -> Entrada a cenTipoidentificacionExtendsMapper para obtener los tipos de identificacion");

				List<ComboItem> comboItems = _cenTipoidentificacionExtendsMapper.getIdentificationTypes(usuario.getIdlenguaje());
				
				if(comboItems != null && comboItems.size() >0){
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
					combo.setCombooItems(comboItems);
				}
			}
			
		}
		LOGGER.info("getTipoIdentificacion() -> Salida del servicio para cargar los tipos de identificacion");
		return combo;
	}

	@Override
	public ComboDTO getTipoColegiacion(HttpServletRequest request) {
		
		LOGGER.info("getTipoColegiacion() -> Entrada al servicio para cargar los tipos de colegiación");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();
		
		if(idInstitucion!= null){
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getEstadoCivil() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEstadoCivil() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(usuarios != null && usuarios.size()>0){
				
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getTipoColegiacion() / _cenTipocolegiacionExtendsMapper.selectTipoColegiacion -> Entrada a cenTipocolegiacionExtendsMapper para obtener los tipos de colegiación");

				List<ComboItem> comboItems = _cenTipocolegiacionExtendsMapper.selectTipoColegiacion(usuario.getIdlenguaje());
				
				if(comboItems != null && comboItems.size() >0){
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
					combo.setCombooItems(comboItems);
				}
			}
			
		}
		LOGGER.info("getTipoColegiacion() -> Salida del servicio para cargar los tipos de colegiación");
		return combo;
	}

	@Override
	public ComboDTO getModalidadDocumentacion(HttpServletRequest request) {
		
		LOGGER.info("getModalidadDocumentacion() -> Entrada al servicio para cargar las modalidades de documentación");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();
		
		if(idInstitucion!= null){
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getModalidadDocumentacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getModalidadDocumentacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(usuarios != null && usuarios.size()>0){
				
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getModalidadDocumentacion() / _cenDocumentacionmodalidadExtendsMapper.selectModalidadDocumentacion() -> Entrada a cenDocumentacionmodalidadExtendsMapper para obtener los tipos de colegiación");

				List<ComboItem> comboItems = _cenDocumentacionmodalidadExtendsMapper.selectModalidadDocumentacion(usuario);
				
				if(comboItems != null && comboItems.size() >0){
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
					combo.setCombooItems(comboItems);
				}
			}
			
		}
		LOGGER.info("getModalidadDocumentacion() -> Salida del servicio para cargar las modalidades de documentación");
		return combo;
	}

	@Override
	public InsertResponseDTO guardarSolicitudIncorporacion(SolIncorporacionItem SolIncorporacionDTO, HttpServletRequest request) {
		
		LOGGER.info("guardarSolicitudIncorporacion() -> Entrada al servicio para insertar una solicitud de incorporacion");
		int insert = 0;
		int update = 0;
		InsertResponseDTO response = new InsertResponseDTO();
		Error error = new Error();
		CenSolicitudincorporacion solIncorporacion;
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(idInstitucion != null){
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"guardarSolicitudIncorporacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"guardarSolicitudIncorporacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				//comprobamos si es insert o update
				if(SolIncorporacionDTO.getIdSolicitud()!=null){
					solIncorporacion = mapperDtoToEntity(SolIncorporacionDTO, usuario);
					update = _cenSolicitudincorporacionMapper.updateByPrimaryKey(solIncorporacion);
				}else{
					MaxIdDto idSolicitud = _cenSolicitudincorporacionExtendsMapper.getMaxIdRecurso();
					Long idMax = idSolicitud.getIdMax() +1;
					SolIncorporacionDTO.setIdSolicitud(Long.toString(idMax));
					solIncorporacion = mapperDtoToEntity(SolIncorporacionDTO, usuario);
					insert = _cenSolicitudincorporacionMapper.insert(solIncorporacion);
				}
				try{
					if(insert == 1 | update ==1 ){
						response.setId(Long.toString(solIncorporacion.getIdsolicitud()));
						response.setStatus(SigaConstants.OK);
						response.setError(null);
						LOGGER.warn("guardarSolicitudIncorporacion() / cenSolicitudincorporacionMapper.insert() -> " + solIncorporacion.getIdsolicitud()
										+ " .Insertado el id correctamente en la tabla Cen_SolicitudIncorporacion");
					}
				}catch(Exception e){
					error.setCode(Integer.parseInt(SigaConstants.KO));
					error.setMessage(e.getMessage());
					response.setStatus(SigaConstants.KO);
					response.setError(error);
					LOGGER.warn("guardarSolicitudIncorporacion() / cenSolicitudincorporacionMapper.insert() -> ERROR: " + e.getMessage());
				}
				
			}
		}
			LOGGER.info("guardarSolicitudIncorporacion() -> Salida del servicio para insertar una solicitud de incorporacion");
		
		return response;
	}
	
	@Override
	public InsertResponseDTO aprobarSolicitud(Long idSolicitud, HttpServletRequest request) {
		
		
		LOGGER.info("aprobarSolicitud() -> Entrada al servicio para aprobar una solicitud");
		
		Long idDireccion;
		Long idPersonal;
		Short idBancario;
		int updateSolicitud = 0;
		InsertResponseDTO response = new InsertResponseDTO();
		Error error = new Error();
		CenSolicitudincorporacion solIncorporacion;
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(idInstitucion != null){
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"aprobarSolicitud() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"aprobarSolicitud() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				solIncorporacion = _cenSolicitudincorporacionMapper.selectByPrimaryKey(idSolicitud);
				
				//insertamos datos personales
				idPersonal = insertarDatosPersonales(solIncorporacion, usuario);
				idDireccion = insertarDatosDireccion(solIncorporacion, usuario, idPersonal);
				idBancario = insertarDatosBancarios(solIncorporacion, usuario, idPersonal);
				solIncorporacion.setIdestado((short)50);
				solIncorporacion.setFechamodificacion(new Date());
				solIncorporacion.setUsumodificacion(usuario.getIdusuario());
				solIncorporacion.setFechaalta(new Date());
				
				updateSolicitud = _cenSolicitudincorporacionMapper.updateByPrimaryKey(solIncorporacion);
				
				
					
				try{
					if(idPersonal != null && idDireccion!= null && idBancario != null && updateSolicitud == 1){
						response.setId(Long.toString(solIncorporacion.getIdsolicitud()));
						response.setStatus(SigaConstants.OK);
						response.setError(null);
						LOGGER.warn("aprobarSolicitud() / cenSolicitudincorporacionMapper.insert() -> " + solIncorporacion.getIdsolicitud()
										+ " .Insertado el id correctamente en la tabla Cen_SolicitudIncorporacion");
					}
				}catch(Exception e){
					error.setCode(Integer.parseInt(SigaConstants.KO));
					error.setMessage(e.getMessage());
					response.setStatus(SigaConstants.KO);
					response.setError(error);
					LOGGER.warn("aprobarSolicitud() / cenSolicitudincorporacionMapper.insert() -> ERROR: " + e.getMessage());
				}
				
			}
		}
		LOGGER.info("aprobarSolicitud() -> Salida del servicio para aprobar una solicitud");
		return response;
	}
	
	
	private CenSolicitudincorporacion mapperDtoToEntity(SolIncorporacionItem dto, AdmUsuarios usuario){
		
		CenSolicitudincorporacion solIncorporacion = new CenSolicitudincorporacion();
		

		solIncorporacion.setIdsolicitud(Long.parseLong(dto.getIdSolicitud()));
		solIncorporacion.setAbonocargo(dto.getAbonoCargo());
		solIncorporacion.setAbonosjcs(dto.getAbonoJCS());
		solIncorporacion.setApellido1(dto.getApellido1());
		solIncorporacion.setApellido2(dto.getApellido2());
		solIncorporacion.setCboCodigo(dto.getCboCodigo());
		solIncorporacion.setCodigopostal(dto.getCodigoPostal());
		solIncorporacion.setCodigosucursal(dto.getCodigoSucursal());
		solIncorporacion.setCorreoelectronico(dto.getCorreoElectronico());
		solIncorporacion.setDigitocontrol(dto.getDigitoControl());
		solIncorporacion.setFax1(dto.getFax1());
		solIncorporacion.setFax2(dto.getFax2());
		////solIncorporacion.setFechaalta(new Date());
		solIncorporacion.setFechaestado(dto.getFechaEstado());
		solIncorporacion.setFechaestadocolegial(dto.getFechaIncorporacion());
		solIncorporacion.setFechamodificacion(new Date());
		solIncorporacion.setFechanacimiento(dto.getFechaNacimiento());
		solIncorporacion.setFechasolicitud(dto.getFechaSolicitud());
		solIncorporacion.setIban(dto.getIban());
		solIncorporacion.setIdestado(Short.parseShort(dto.getIdEstado()));
		solIncorporacion.setIdestadocivil(Short.parseShort(dto.getIdEstadoCivil()));
		solIncorporacion.setIdinstitucion(usuario.getIdinstitucion());
		solIncorporacion.setIdmodalidaddocumentacion(Short.parseShort(dto.getIdModalidadDocumentacion()));
		solIncorporacion.setIdpais(dto.getIdPais());
		solIncorporacion.setDomicilio(dto.getDomicilio());
		solIncorporacion.setIdpoblacion(dto.getIdPoblacion());
		solIncorporacion.setIdprovincia(dto.getIdProvincia());
		solIncorporacion.setIdtipocolegiacion(Short.parseShort(dto.getTipoColegiacion()));
		solIncorporacion.setIdtipoidentificacion(Short.parseShort(dto.getIdTipoIdentificacion()));
		solIncorporacion.setIdtiposolicitud(Short.parseShort(dto.getIdTipo()));
		solIncorporacion.setIdtratamiento(Short.parseShort(dto.getTratamiento()));
		solIncorporacion.setMovil(dto.getMovil());
		solIncorporacion.setNaturalde(dto.getNaturalDe());
		solIncorporacion.setNcolegiado(dto.getNumColegiado());
		solIncorporacion.setNombre(dto.getNombre());
		solIncorporacion.setNumerocuenta(dto.getNumeroCuenta());
		solIncorporacion.setNumeroidentificador(dto.getNumeroIdentificacion());
		solIncorporacion.setObservaciones(dto.getObservaciones());
		solIncorporacion.setResidente(dto.getResidente());
		solIncorporacion.setSexo(dto.getSexo());
		solIncorporacion.setTelefono1(dto.getTelefono1());
		solIncorporacion.setTelefono2(dto.getTelefono2());
		solIncorporacion.setTitular(dto.getTitular());
		solIncorporacion.setUsumodificacion(usuario.getIdusuario());
		
		return solIncorporacion;
		
	}
	
	private Long insertarDatosPersonales (CenSolicitudincorporacion solicitud, AdmUsuarios usuario){
		
		CenPersona datosPersonales = new CenPersona();
		
		MaxIdDto personaID = _cenPersonaExtendsMapper.selectMaxIdPersona2();
		
		
		datosPersonales.setIdpersona(personaID.getIdMax()+1);
		datosPersonales.setNombre(solicitud.getNombre());
		datosPersonales.setApellidos1(solicitud.getApellido1());
		datosPersonales.setApellidos2(solicitud.getApellido2());
		datosPersonales.setFechamodificacion(new Date());
		datosPersonales.setFechanacimiento(solicitud.getFechanacimiento());
		datosPersonales.setIdestadocivil(solicitud.getIdestadocivil());
		datosPersonales.setIdtipoidentificacion(solicitud.getIdtipoidentificacion());
		datosPersonales.setNaturalde(solicitud.getNaturalde());
		datosPersonales.setNifcif(solicitud.getNumeroidentificador());
		datosPersonales.setSexo(solicitud.getSexo());
		datosPersonales.setUsumodificacion(usuario.getIdusuario());
		
		return datosPersonales.getIdpersona();
	}

	private Long insertarDatosDireccion (CenSolicitudincorporacion solicitud, AdmUsuarios usuario, Long idPersona){
		
		CenDirecciones direccion = new CenDirecciones();
		
		MaxIdDto personaID = _cenDireccionesExtendsMapper.selectMaxID();
		
		
		direccion.setIddireccion(personaID.getIdMax()+1);
		direccion.setCodigopostal(solicitud.getCodigopostal());
		direccion.setCorreoelectronico(solicitud.getCorreoelectronico());
		direccion.setDomicilio(solicitud.getDomicilio());
		if(solicitud.getFax1() != null)direccion.setFax1(solicitud.getFax1());
		if(solicitud.getFax2() != null)direccion.setFax1(solicitud.getFax2());
		direccion.setFechamodificacion(new Date());
		direccion.setIdinstitucion(usuario.getIdinstitucion());
		direccion.setIdpais(solicitud.getIdpais());
		direccion.setIdpersona(idPersona);
		direccion.setIdpoblacion(solicitud.getIdpoblacion());
		direccion.setIdprovincia(solicitud.getIdprovincia());
		direccion.setMovil(solicitud.getMovil());
		if(solicitud.getTelefono1()!= null)direccion.setTelefono1(solicitud.getTelefono1());
		if(solicitud.getTelefono2()!= null)direccion.setTelefono1(solicitud.getTelefono2());
		direccion.setUsumodificacion(usuario.getIdusuario());
		
		return direccion.getIddireccion();
		
	}
	
	private Short insertarDatosBancarios(CenSolicitudincorporacion solicitud, AdmUsuarios usuario, Long idPersona){
		
		CenCuentasbancarias cuenta = new CenCuentasbancarias();
		
		MaxIdDto personaID = _cenCuentasbancariasExtendsMapper.selectMaxID();
		
		cuenta.setIdcuenta((short)(personaID.getIdMax()+1));
		cuenta.setAbonocargo(solicitud.getAbonocargo());
		cuenta.setAbonosjcs(solicitud.getAbonosjcs());
		// TODO: cuenta.setCuentacontable(cuentacontable);
		cuenta.setFechamodificacion(new Date());
		cuenta.setIban(solicitud.getIban());
		cuenta.setIdinstitucion(usuario.getIdinstitucion());
		cuenta.setIdpersona(idPersona);
		cuenta.setNumerocuenta(solicitud.getNumerocuenta());
		cuenta.setTitular(solicitud.getTitular());
		cuenta.setUsumodificacion(usuario.getIdusuario());
		
		return cuenta.getIdcuenta();
		
	}

}
