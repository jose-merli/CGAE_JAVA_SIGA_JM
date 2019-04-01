package org.itcgae.siga.cen.services.impl;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.cen.BancoBicItem;
import org.itcgae.siga.DTOs.cen.DatosBancariosItem;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchBancoDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.cen.SolIncorporacionDTO;
import org.itcgae.siga.DTOs.cen.SolIncorporacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudIncorporacionSearchDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.ISolicitudIncorporacionService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenBancos;
import org.itcgae.siga.db.entities.CenBancosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenColegiadoKey;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenCuentasbancariasExample;
import org.itcgae.siga.db.entities.CenCuentasbancariasKey;
import org.itcgae.siga.db.entities.CenDatoscolegialesestado;
import org.itcgae.siga.db.entities.CenDireccionTipodireccion;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenDireccionesKey;
import org.itcgae.siga.db.entities.CenPais;
import org.itcgae.siga.db.entities.CenPaisExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.CenSolicitudincorporacion;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.CenCuentasbancariasMapper;
import org.itcgae.siga.db.mappers.CenDatoscolegialesestadoMapper;
import org.itcgae.siga.db.mappers.CenDireccionTipodireccionMapper;
import org.itcgae.siga.db.mappers.CenSolicitudincorporacionMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenBancosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCuentasbancariasExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDocumentacionmodalidadExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadoSolicitudExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocivilExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPaisExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudincorporacionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTipocolegiacionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTipoidentificacionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposolicitudExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTratamientoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class SolicitudIncorporacionServiceImpl implements ISolicitudIncorporacionService{
	
	private Logger LOGGER = Logger.getLogger(SolicitudIncorporacionServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper _admUsuariosExtendsMapper;
	
	@Autowired
	private AdmConfigMapper admConfigMapper;
	
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
	private CenPersonaExtendsMapper _cenPersonaMapper;
	
	@Autowired
	private CenPersonaExtendsMapper _cenPersonaExtendsMapper;
	
	@Autowired
	private CenDireccionesExtendsMapper _cenDireccionesMapper;
	
	@Autowired
	private CenCuentasbancariasExtendsMapper _cenCuentasbancariasExtendsMapper;
	
	@Autowired
	private CenCuentasbancariasMapper _cenCuentasbancariasMapper;
	
	@Autowired
	private CenColegiadoExtendsMapper _cenColegiadoMapper;
	
	@Autowired
	private CenClienteMapper _cenClienteMapper;
	

	@Autowired
	private CenBancosExtendsMapper cenBancosExtendsMapper;

	@Autowired
	private CenPaisExtendsMapper cenPaisExtendsMapper;

	@Autowired
	private CenCuentasbancariasExtendsMapper cenCuentasbancariasExtendsMapper;

	@Autowired
	private CenDatoscolegialesestadoMapper cenDatoscolegialesestadoMapper;
	
	@Autowired
	private CenDireccionTipodireccionMapper cenDireccionTipoDireccionMapper;
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
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
//					element.setLabel("");
//					element.setValue("");
//					comboItems.add(0, element);
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
//					ComboItem element = new ComboItem();
//					element.setLabel("");
//					element.setValue("");
//					comboItems.add(0, element);
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
	public SolIncorporacionItem numColegiadoSearch(SolIncorporacionItem solIncorporacionItem,
			HttpServletRequest request) {
//		SI encuentra algún colegiado utilizando este número de colegiado devolverá dicho número, en caso de no existir ningún colegiado con ese número devolverá el string "disponible"
		LOGGER.info("numColegiadoSearch() -> Entrada al servicio para recuperar las solicitudes de incorporación");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		SolIncorporacionItem solIncorporacionResult = new SolIncorporacionItem();
		solIncorporacionResult.setNumColegiado(null);
		if(idInstitucion!= null){
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"numColegiadoSearch() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"numColegiadoSearch() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(usuarios != null && usuarios.size()>0){
				// 10 --> No Inscrito/Español
				// 20 --> Inscrito/Comunitario
				String tipoColegiacion = solIncorporacionItem.getIdTipoColegiacion();		
				CenColegiadoExample exampleColegiado = new CenColegiadoExample();
				
				if(solIncorporacionItem.getNumColegiado() != null && !solIncorporacionItem.getNumColegiado().equals("")) {
					if(tipoColegiacion.equals("10")) {
						exampleColegiado.createCriteria().andIdinstitucionEqualTo(Short.valueOf(idInstitucion)).andNcolegiadoEqualTo(solIncorporacionItem.getNumColegiado());
					}else {
						exampleColegiado.createCriteria().andIdinstitucionEqualTo(Short.valueOf(idInstitucion)).andNcomunitarioEqualTo(solIncorporacionItem.getNumColegiado());
					}
					
					List<CenColegiado> resultados = _cenColegiadoMapper.selectByExample(exampleColegiado);
					if(resultados.size() > 0) {
						solIncorporacionResult.setNumColegiado(resultados.get(0).getNcolegiado());
					}else {
						solIncorporacionResult.setNumColegiado("disponible");
					}
				}else {
					solIncorporacionResult.setNumColegiado("disponible");
				}
			}
		}
		LOGGER.info("getTipoSolicitud() -> Salida del servicio para recuperar las solicitudes de incorporación");
		return solIncorporacionResult;
	}
	
	@Override
	public SolIncorporacionItem nifExistenteSearch(SolIncorporacionItem solIncorporacionItem,
			HttpServletRequest request) {
//		SI encuentra algún colegiado utilizando este número de colegiado devolverá dicho número, en caso de no existir ningún colegiado con ese número devolverá el string "disponible"
		LOGGER.info("numColegiadoSearch() -> Entrada al servicio para recuperar las solicitudes de incorporación");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		SolIncorporacionItem solIncorporacionResult = new SolIncorporacionItem();
		solIncorporacionResult.setNumColegiado(null);
		if(idInstitucion!= null){
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"numColegiadoSearch() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"numColegiadoSearch() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(usuarios != null && usuarios.size()>0){
				CenPersonaExample examplePersona = new CenPersonaExample();
				examplePersona.createCriteria().andNifcifEqualTo(solIncorporacionItem.getNumeroIdentificacion());
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

				List<CenPersona> resultados =  _cenPersonaMapper.selectByExample(examplePersona);
				if(resultados.size() > 0) {
					solIncorporacionResult.setNombre(resultados.get(0).getNombre());
					solIncorporacionResult.setApellido1(resultados.get(0).getApellidos1());
					solIncorporacionResult.setApellido2(resultados.get(0).getApellidos2());
					solIncorporacionResult.setNumeroIdentificacion(resultados.get(0).getNifcif());
					solIncorporacionResult.setIdTipoIdentificacion(String.valueOf(resultados.get(0).getIdtipoidentificacion()));
					if(resultados.get(0).getFechanacimiento() != null) {
						solIncorporacionResult.setFechaNacimientoStr(df.format(resultados.get(0).getFechanacimiento()));
					}
					solIncorporacionResult.setIdEstadoCivil(String.valueOf(resultados.get(0).getIdestadocivil()));
					if(resultados.get(0).getNaturalde() != null) {
						solIncorporacionResult.setNaturalDe(resultados.get(0).getNaturalde());
					}
					solIncorporacionResult.setSexo(resultados.get(0).getSexo());
				}else {
					solIncorporacionResult.setNumeroIdentificacion("disponible");
				}
			}
		}
		LOGGER.info("getTipoSolicitud() -> Salida del servicio para recuperar las solicitudes de incorporación");
		return solIncorporacionResult;
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
//					element.setLabel("");
//					element.setValue("");
//					comboItems.add(0, element);
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
//					element.setLabel("");
//					element.setValue("");
//					comboItems.add(0, element);
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
//					element.setLabel("");
//					element.setValue("");
//					comboItems.add(0, element);
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
//					element.setLabel("");
//					element.setValue("");
//					comboItems.add(0, element);
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
//					element.setLabel("");
//					element.setValue("");
//					comboItems.add(0, element);
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
				
				try{
					//comprobamos si es insert o update
					if(SolIncorporacionDTO.getIdSolicitud()!=null){
						solIncorporacion = mapperDtoToEntity(SolIncorporacionDTO, usuario);
						update = _cenSolicitudincorporacionMapper.updateByPrimaryKey(solIncorporacion);
					}else{
						MaxIdDto idSolicitud = _cenSolicitudincorporacionExtendsMapper.getMaxIdRecurso();
						Long idMax = idSolicitud.getIdMax() +1;
						SolIncorporacionDTO.setIdSolicitud(Long.toString(idMax));
						
						
						//TODO esta funcionalidad se pasa a "Aprobar la solicitud"
//						if(SolIncorporacionDTO.getNumColegiado() == null) {
//							// Comprobamos si para la institucion tiene un contador unico
//							GenParametrosKey key = new GenParametrosKey();
//							key.setIdinstitucion(idInstitucion);
//							key.setModulo(SigaConstants.MODULO_CENSO);
//							key.setParametro(SigaConstants.PARAMETRO_CONTADOR_UNICO);
//							GenParametros genParametro = genParametrosExtendsMapper.selectByPrimaryKey(key);
//							
//							String contadorUnico = genParametro == null || genParametro.getValor() == null ? "0" : genParametro.getValor();
//
//							StringDTO nColegiado = new StringDTO();
//							if (contadorUnico.equals("1")) {
//								nColegiado = _cenSolicitudincorporacionExtendsMapper.getMaxNColegiadoComunitario(String.valueOf(idInstitucion));
//							} else {
//								// 10 --> No Inscrito/Español
//								// 20 --> Inscrito/Comunitario
//								String tipoColegiacion = SolIncorporacionDTO.getIdTipoColegiacion();
//								
//								if(tipoColegiacion.equals("10")) {
//									nColegiado = _cenSolicitudincorporacionExtendsMapper.getMaxNColegiado(String.valueOf(idInstitucion));
//								}else if(tipoColegiacion.equals("20")) {
//									nColegiado = _cenSolicitudincorporacionExtendsMapper.getMaxNComunitario(String.valueOf(idInstitucion));
//								}
//							}
//							
//							SolIncorporacionDTO.setNumColegiado(nColegiado.getValor());
//						}else {
//							// Comprobamos que el ncolegiado es correcto
//						}
						
						solIncorporacion = mapperDtoToEntity(SolIncorporacionDTO, usuario);
						solIncorporacion.setIdestado((short)20);
						insert = _cenSolicitudincorporacionMapper.insert(solIncorporacion);
					}
					if(insert == 1 | update ==1 ){
						response.setId(Long.toString(solIncorporacion.getIdsolicitud()));
						response.setStatus(SigaConstants.OK);
						response.setError(null);
						LOGGER.warn("guardarSolicitudIncorporacion() / cenSolicitudincorporacionMapper.insert() -> " + solIncorporacion.getIdsolicitud()
										+ " .Insertado el id correctamente en la tabla Cen_SolicitudIncorporacion");
					}
				}catch(Exception e){
					error.setCode(500);
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
		Long idDireccion2;
		Long idPersona;
		Short idBancario;
		int insertColegiado;
		int insertCliente;
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
				try{
					AdmUsuarios usuario = usuarios.get(0);
					solIncorporacion = _cenSolicitudincorporacionMapper.selectByPrimaryKey(idSolicitud);
					
					//insertamos datos personales
					idPersona = insertarDatosPersonales(solIncorporacion, usuario);
					insertCliente = insertarDatosCliente(solIncorporacion, usuario, idPersona);
					idDireccion = insertarDatosDireccion(solIncorporacion, usuario, idPersona);
					idDireccion2 = insertarDatosDireccion2(solIncorporacion, usuario, idPersona);
					insertColegiado = insertarDatosColegiado(solIncorporacion, usuario, idPersona);
					idBancario = insertarDatosBancarios(solIncorporacion, usuario, idPersona);
					solIncorporacion.setIdestado((short)50);
					solIncorporacion.setFechamodificacion(new Date());
					solIncorporacion.setUsumodificacion(usuario.getIdusuario());
					solIncorporacion.setFechaalta(new Date());
					//solIncorporacion.setFechaestado(new Date());
					updateSolicitud = _cenSolicitudincorporacionMapper.updateByPrimaryKey(solIncorporacion);
				
					if(idPersona != null && idDireccion!= null && insertCliente == 1 && idBancario != null && insertColegiado == 1 && updateSolicitud == 1){
						response.setId(Long.toString(solIncorporacion.getIdsolicitud()));
						response.setStatus(SigaConstants.OK);
						response.setError(null);
						LOGGER.warn("aprobarSolicitud() / cenSolicitudincorporacionMapper.insert() -> " + solIncorporacion.getIdsolicitud()
										+ " .Insertado el id correctamente en la tabla Cen_SolicitudIncorporacion");
					}else{
						LOGGER.error("aprobarSolicitud() --> Borramos los registros al no poder aprobar la solicitud");
						if(insertColegiado == 0) {
							CenColegiadoKey keys = new CenColegiadoKey();
							keys.setIdinstitucion(usuario.getIdinstitucion());
							keys.setIdpersona(idPersona);
							_cenColegiadoMapper.deleteByPrimaryKey(keys);
						}
						if(idPersona != null) 
							_cenPersonaMapper.deleteByPrimaryKey(idPersona);
						
						if(idDireccion != null){
							CenDireccionesKey keys = new CenDireccionesKey();
							keys.setIddireccion(idDireccion);
							keys.setIdinstitucion(usuario.getIdinstitucion());
							keys.setIdpersona(idPersona);
							_cenDireccionesMapper.deleteByPrimaryKey(keys);
						}
						if(idDireccion2 != null){
							CenDireccionesKey keys = new CenDireccionesKey();
							keys.setIddireccion(idDireccion2);
							keys.setIdinstitucion(usuario.getIdinstitucion());
							keys.setIdpersona(idPersona);
							_cenDireccionesMapper.deleteByPrimaryKey(keys);
						}
						if(idBancario != null){
							CenCuentasbancariasKey keys = new CenCuentasbancariasKey();
							keys.setIdcuenta(idBancario);
							keys.setIdinstitucion(usuario.getIdinstitucion());
							keys.setIdpersona(idPersona);
							_cenCuentasbancariasMapper.deleteByPrimaryKey(keys);
						}
						LOGGER.error("aprobarSolicitud() --> Registros borrados, fallo al aprobar la solicitud.");
					}
					
				}catch(Exception e){
					
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
	
	@Override
	public InsertResponseDTO denegarsolicitud(Long idSolicitud, HttpServletRequest request) {
		LOGGER.info("denegarsolicitud() -> Entrada al servicio para denegar una solicitud.");
		int update = 0;
		InsertResponseDTO response = new InsertResponseDTO();
		Error error = new Error();
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(idInstitucion != null){
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"denegarsolicitud() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = _admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"denegarsolicitud() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try{
					CenSolicitudincorporacion solicitud = _cenSolicitudincorporacionMapper.selectByPrimaryKey(idSolicitud);
					solicitud.setIdestado((short)30);
					solicitud.setFechamodificacion(new Date());
					solicitud.setFechaestado(new Date());
					solicitud.setUsumodificacion(usuario.getIdusuario());
					update = _cenSolicitudincorporacionMapper.updateByPrimaryKey(solicitud);
					if(update ==1){
						response.setId(Long.toString(idSolicitud));
						response.setStatus(SigaConstants.OK);
						response.setError(null);
						LOGGER.warn("denegarsolicitud() / cenSolicitudincorporacionMapper.update() -> " + idSolicitud
										+ " .Insertado el id correctamente en la tabla Cen_SolicitudIncorporacion");
					}
				}catch(Exception e){
					error.setCode(Integer.parseInt(SigaConstants.KO));
					error.setMessage(e.getMessage());
					response.setStatus(SigaConstants.KO);
					response.setError(error);
					LOGGER.warn("denegarsolicitud() / cenSolicitudincorporacionMapper.update() -> ERROR: " + e.getMessage());
				}
				
			}
		}
			LOGGER.info("denegarsolicitud() -> Salida del servicio para denegar una solicitud.");
		
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
		if(dto.getIban() != null) {
			solIncorporacion.setIban(dto.getIban());	
			solIncorporacion.setCboCodigo(dto.getIban().substring(4, 8));
			solIncorporacion.setCodigosucursal(dto.getIban().substring(8, 12));
			solIncorporacion.setDigitocontrol(dto.getIban().substring(12, 14));
			solIncorporacion.setNumerocuenta(dto.getIban().substring(14, 24));
		}
		solIncorporacion.setIdestado(Short.parseShort(dto.getIdEstado()));
		if(dto.getIdEstadoCivil() != null) {
			solIncorporacion.setIdestadocivil(Short.parseShort(dto.getIdEstadoCivil()));
		}
		solIncorporacion.setIdinstitucion(usuario.getIdinstitucion());
		solIncorporacion.setIdmodalidaddocumentacion(Short.parseShort(dto.getIdModalidadDocumentacion()));
		solIncorporacion.setIdpais(dto.getIdPais());
		solIncorporacion.setDomicilio(dto.getDomicilio());
		solIncorporacion.setIdpoblacion(dto.getIdPoblacion());
		solIncorporacion.setIdprovincia(dto.getIdProvincia());
		solIncorporacion.setPoblacionextranjera(dto.getPoblacionExtranjera());
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
		
		
		datosPersonales.setIdpersona(personaID.getIdMax());
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
		datosPersonales.setFallecido("0");
		datosPersonales.setUsumodificacion(usuario.getIdusuario());
		
		CenPersonaExample ejemplo = new CenPersonaExample();
		ejemplo.createCriteria().andNifcifEqualTo(solicitud.getNumeroidentificador());
		List <CenPersona> busqueda = _cenPersonaMapper.selectByExample(ejemplo);

		if(busqueda.isEmpty()) {
			_cenPersonaMapper.insert(datosPersonales);
			return datosPersonales.getIdpersona();
		}else {
			return busqueda.get(0).getIdpersona();
		}
		
	}
	
	private int insertarDatosCliente(CenSolicitudincorporacion solicitud, AdmUsuarios usuario, Long idPersona){
		
		CenCliente cliente = new CenCliente();
		
		cliente.setIdpersona(idPersona);
		cliente.setIdinstitucion(usuario.getIdinstitucion());
		cliente.setFechaalta(new Date());
		cliente.setCaracter("P");
		cliente.setPublicidad(SigaConstants.DB_FALSE);
		cliente.setGuiajudicial(SigaConstants.DB_FALSE);
		cliente.setComisiones(SigaConstants.DB_FALSE);
		if(solicitud.getIdtratamiento() != null) {
			cliente.setIdtratamiento(solicitud.getIdtratamiento()); // 1
		}else {
			cliente.setIdtratamiento(Short.valueOf(SigaConstants.DB_TRUE)); // 1
		} 
		cliente.setFechamodificacion(new Date());
		cliente.setUsumodificacion(usuario.getIdusuario());
		cliente.setIdlenguaje(usuario.getIdlenguaje());
		cliente.setExportarfoto(SigaConstants.DB_FALSE);
		
		CenClienteExample ejemploCliente = new CenClienteExample();
		ejemploCliente.createCriteria().andIdpersonaEqualTo(idPersona).andIdinstitucionEqualTo(usuario.getIdinstitucion());
		
		List <CenCliente> listaExistente = _cenClienteMapper.selectByExample(ejemploCliente);
		if(listaExistente.isEmpty()) {
			return _cenClienteMapper.insert(cliente);
		}else {
			return 1;
		}
	}

	private Long insertarDatosDireccion (CenSolicitudincorporacion solicitud, AdmUsuarios usuario, Long idPersona){
		
		CenDirecciones direccion = new CenDirecciones();
		
		//Creamos la direccion con los tipos de direccion oblogatorios
		 List<DatosDireccionesItem> direccionID = _cenDireccionesMapper.selectNewIdDireccion(idPersona.toString(),usuario.getIdinstitucion().toString());
		
		direccion.setIddireccion(Long.valueOf(direccionID.get(0).getIdDireccion()));
		direccion.setCodigopostal(solicitud.getCodigopostal());
		direccion.setCorreoelectronico(solicitud.getCorreoelectronico());
		direccion.setDomicilio(solicitud.getDomicilio());
		if(solicitud.getFax1() != null)direccion.setFax1(solicitud.getFax1());
		if(solicitud.getFax2() != null)direccion.setFax1(solicitud.getFax2());
		direccion.setFechamodificacion(new Date());
		direccion.setIdinstitucion(usuario.getIdinstitucion());
		direccion.setIdpais(solicitud.getIdpais());
		direccion.setIdpersona(idPersona);
		if(solicitud.getIdpoblacion()!= null)direccion.setIdpoblacion(solicitud.getIdpoblacion());
		if(solicitud.getIdprovincia()!= null)direccion.setIdprovincia(solicitud.getIdprovincia());
		if(solicitud.getTelefono1()!= null)direccion.setTelefono1(solicitud.getTelefono1());
		if(solicitud.getTelefono2()!= null)direccion.setTelefono1(solicitud.getTelefono2());
		direccion.setMovil(solicitud.getMovil());
		direccion.setUsumodificacion(usuario.getIdusuario());
		
		//Añadimos las preferencias de direccion obligatorias
		direccion.setPreferente("ECS");
		
		_cenDireccionesMapper.insert(direccion);
		
		CenDireccionTipodireccion tipoDireccion =  new CenDireccionTipodireccion();
		tipoDireccion.setFechamodificacion(new Date());
		tipoDireccion.setIddireccion(direccion.getIddireccion());
		tipoDireccion.setIdinstitucion(usuario.getIdinstitucion());
		tipoDireccion.setIdpersona(idPersona);
		tipoDireccion.setUsumodificacion(usuario.getIdusuario());
		
		//Añadimos los tipo de direcciones obligatorios
		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_TRASPASO));
		cenDireccionTipoDireccionMapper.insert(tipoDireccion );
		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_FACTURACION));
		cenDireccionTipoDireccionMapper.insert(tipoDireccion );
		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_CENSOWEB));
		cenDireccionTipoDireccionMapper.insert(tipoDireccion );
		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_GUIAJUDICIAL));
		cenDireccionTipoDireccionMapper.insert(tipoDireccion );
		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_GUARDIA));
		cenDireccionTipoDireccionMapper.insert(tipoDireccion );
		
		if(solicitud.getIdtiposolicitud() == SigaConstants.INCORPORACION_EJERCIENTE ||
				solicitud.getIdtiposolicitud() == SigaConstants.REINCORPORACION_EJERCIENTE) {
			tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_DESPACHO));
			cenDireccionTipoDireccionMapper.insert(tipoDireccion );
		}
		
		return direccion.getIddireccion();
		
	}
	
	private Long insertarDatosDireccion2 (CenSolicitudincorporacion solicitud, AdmUsuarios usuario, Long idPersona){
		
		CenDirecciones direccion = new CenDirecciones();
		
		//Creamos la direccion con los tipos de direccion oblogatorios
		 List<DatosDireccionesItem> direccionID = _cenDireccionesMapper.selectNewIdDireccion(idPersona.toString(),usuario.getIdinstitucion().toString());
		
		direccion.setIddireccion(Long.valueOf(direccionID.get(0).getIdDireccion()));
		direccion.setFechamodificacion(new Date());
		direccion.setIdinstitucion(usuario.getIdinstitucion());
		direccion.setIdpersona(idPersona);
		direccion.setUsumodificacion(usuario.getIdusuario());
		
		//Añadimos las preferencias de direccion no obligatorias
		direccion.setPreferente("F");
		
		_cenDireccionesMapper.insert(direccion);
		
		CenDireccionTipodireccion tipoDireccion =  new CenDireccionTipodireccion();
		tipoDireccion.setFechamodificacion(new Date());
		tipoDireccion.setIddireccion(direccion.getIddireccion());
		tipoDireccion.setIdinstitucion(usuario.getIdinstitucion());
		tipoDireccion.setIdpersona(idPersona);
		tipoDireccion.setUsumodificacion(usuario.getIdusuario());
		
		//Añadimos los tipo de direcciones obligatorios
		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_PUBLICA));
		cenDireccionTipoDireccionMapper.insert(tipoDireccion);
		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_RESIDENCIA));
		cenDireccionTipoDireccionMapper.insert(tipoDireccion);
		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_REVISTA));
		cenDireccionTipoDireccionMapper.insert(tipoDireccion);
		
		if(solicitud.getIdtiposolicitud() != SigaConstants.INCORPORACION_EJERCIENTE &&
				solicitud.getIdtiposolicitud() != SigaConstants.REINCORPORACION_EJERCIENTE) {
			tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_DESPACHO));
			cenDireccionTipoDireccionMapper.insert(tipoDireccion);
		}
		
		return direccion.getIddireccion();
		
	}
	
	private Short insertarDatosBancarios(CenSolicitudincorporacion solicitud, AdmUsuarios usuario, Long idPersona) throws Exception{
		
		CenCuentasbancarias cuenta = new CenCuentasbancarias();

		MaxIdDto personaID = _cenCuentasbancariasExtendsMapper.selectMaxID();
		boolean tieneCargo = false;
		boolean tieneSCSJ = false;
		boolean tieneAbono = false;
		cuenta.setIdcuenta((short)(personaID.getIdMax()+0));
		cuenta.setAbonocargo(solicitud.getAbonocargo());
		cuenta.setAbonosjcs(solicitud.getAbonosjcs());
		cuenta.setFechamodificacion(new Date());
		cuenta.setIban(solicitud.getIban());
		cuenta.setIdinstitucion(usuario.getIdinstitucion());
		cuenta.setIdpersona(idPersona);
		cuenta.setNumerocuenta(solicitud.getNumerocuenta());
		cuenta.setTitular(solicitud.getTitular());
		cuenta.setCboCodigo(solicitud.getIban().substring(4, 8));
		cuenta.setCodigosucursal(solicitud.getIban().substring(8, 12));
		cuenta.setDigitocontrol(solicitud.getIban().substring(12, 14));
		cuenta.setNumerocuenta(solicitud.getIban().substring(14, 24));
		cuenta.setUsumodificacion(usuario.getIdusuario());
		
		List<CenCuentasbancarias> cuentaBancaria = null;
		
		if(solicitud.getAbonosjcs().equals("1")){
			CenCuentasbancariasExample example = new CenCuentasbancariasExample();
			example.createCriteria().andIdpersonaEqualTo(Long.valueOf(idPersona)).andIdinstitucionEqualTo(usuario.getIdinstitucion()).andAbonosjcsEqualTo("1");
			cuentaBancaria = _cenCuentasbancariasExtendsMapper.selectByExample(example);
			if (null != cuentaBancaria && !cuentaBancaria.isEmpty()) {
				return null;
			}
		}
		
//		CenBancos banco = new CenBancos();
		
		DatosBancariosSearchDTO datosBancariosSearchDTO = new DatosBancariosSearchDTO();
		datosBancariosSearchDTO.setIdCuenta(solicitud.getIban().substring(4, 8));
		if(solicitud.getIdpersona() != null) {
			datosBancariosSearchDTO.setIdPersona(solicitud.getIdpersona().toString());
		}else {
			datosBancariosSearchDTO.setIdPersona(idPersona.toString());
		}
		datosBancariosSearchDTO.setIdInstitucion(solicitud.getIdinstitucion().toString());
//		datosBancariosSearchDTO.setIdPersona(solicitud.getIdpersona());
		
		List<DatosBancariosItem> datosBancariosItem = new ArrayList<DatosBancariosItem>();
		datosBancariosItem = cenCuentasbancariasExtendsMapper.selectCuentasBancarias(datosBancariosSearchDTO,
				solicitud.getIdinstitucion().toString());

		List<BancoBicItem> bancoBicItem = new ArrayList<BancoBicItem>();

		DatosBancariosSearchBancoDTO datosBancariosSearchBancoDTO = new DatosBancariosSearchBancoDTO();
		datosBancariosSearchBancoDTO.setiban(solicitud.getIban().substring(4, 8));
		bancoBicItem = cenCuentasbancariasExtendsMapper.selectBanks(datosBancariosSearchBancoDTO);
		
		// Comprobamos que el código está en cen_bancos, si está se pone sin más en
		// cbo_codigo, sino se coge el máx del código
		CenBancosExample cenBancosExample = new CenBancosExample();
		cenBancosExample.createCriteria().andBicEqualTo(bancoBicItem.get(0).getBic())
				.andCodigoEqualTo(solicitud.getIban().substring(4, 8));
		List<CenBancos> cenBancos = cenBancosExtendsMapper.selectByExample(cenBancosExample);

		if (null != cenBancos && !cenBancos.isEmpty()) {
//			cuentaBancaria.setCboCodigo(cenBancos.get(0).getCodigo()); // Tanto si es ext o esp tiene cod en
																		// cenBancos
		} else {
			// insertar en cen_bancos
			NewIdDTO newIdDTO = cenBancosExtendsMapper.getMaxCode();

			CenBancos record = new CenBancos();
			
//			String codigoBanco = solicitud.getCboCodigo() + solicitud.getCodigosucursal() + solicitud.getDigitocontrol() + solicitud.getDigitocontrol();
//			banco.setCodigo(solicitud.getIban().substring(4, 8));
//			banco.setFechamodificacion(new Date());
//			banco.setIdpais(solicitud.getIdpais());
//			banco.setNombre(codigoBanco);
//			banco.setUsumodificacion(usuario.getIdusuario());
//			_cenBancosMapper.insert(banco);

			if (!solicitud.getIban().substring(0, 2).equals("ES")) {
				String rdo = fill(newIdDTO.getNewId(), 5);
				record.setCodigo(rdo);
				
				record.setNombre("BANCO EXTRANJERO");
			} else {
				record.setCodigo(solicitud.getIban().substring(4, 8));
				record.setNombre(solicitud.getBanco());
			}

			record.setBic(solicitud.getBic());
			record.setFechamodificacion(new Date());

			CenPaisExample cenPaisExample = new CenPaisExample();
			cenPaisExample.createCriteria().andCodIsoEqualTo(solicitud.getIban().substring(0, 2));
			List<CenPais> cenPais = cenPaisExtendsMapper.selectByExample(cenPaisExample);

			if (null != cenPais && !cenPais.isEmpty()) {
				record.setIdpais(cenPais.get(0).getIdpais());
			}

			record.setUsumodificacion(usuario.getIdusuario());

			int res = cenBancosExtendsMapper.insert(record);

//			if (res == 0) {
//				insertResponseDTO.setStatus(SigaConstants.KO);
//				error.setMessage("Error al insertar en CEN_BANCOS");
//				insertResponseDTO.setError(error);
//			} else {
//				cuentaBancaria.setCboCodigo(record.getCodigo());
//			}
		}
		
		LOGGER.info(
				"insertarDatosBancarios() / cenNocolegiadoExtendsMapper.insertSelective() -> Entrada a cenNocolegiadoExtendsMapper para insertar cuentas bancarias");
		_cenCuentasbancariasMapper.insertSelective(cuenta);
		LOGGER.info(
				"insertarDatosBancarios() / cenNocolegiadoExtendsMapper.insertSelective() -> Salida de cenNocolegiadoExtendsMapper para insertar cuentas bancarias");
		
		if (tieneCargo) {
			Object[] paramMandatos = new Object[4];
			paramMandatos[0] = usuario.getIdinstitucion().toString();
			paramMandatos[1] = idPersona;
			paramMandatos[2] = cuenta.getIdcuenta().toString();
			paramMandatos[3] = usuario.getIdusuario().toString();
			
			String resultado[] = new String[2];
			resultado = callPLProcedure("{call PKG_SIGA_CARGOS.InsertarMandatos(?,?,?,?,?,?)}", 2, paramMandatos);
			if (resultado == null) {
				LOGGER.info("insertBanksData() -> KO. Insert para mandatos cuentas bancarias  NO realizado correctamente");
				
				//Si solo tiene tipo Cargo, se elimina la cuenta y no se crea, si tiene algún otro se elimina solo el tipo Cargo
				if (tieneAbono || tieneSCSJ) {
					if (tieneAbono) {
						cuenta.setAbonocargo("A");
					}else{
						cuenta.setAbonocargo(null);
					}
					_cenCuentasbancariasExtendsMapper.updateByPrimaryKey(cuenta);
				}else{
					_cenCuentasbancariasExtendsMapper.deleteByPrimaryKey(cuenta);
				}
				
			} else {
				if (resultado[0].equals("1")) {
					LOGGER.info("insertBanksData() -> KO. Insert para mandatos cuentas bancarias  NO realizado correctamente");
					//Si solo tiene tipo Cargo, se elimina la cuenta y no se crea, si tiene algún otro se elimina solo el tipo Cargo
					if (tieneAbono || tieneSCSJ) {
						if (tieneAbono) {
							cuenta.setAbonocargo("A");
						}else{
							cuenta.setAbonocargo(null);
						}
						_cenCuentasbancariasExtendsMapper.updateByPrimaryKey(cuenta);
					}else{
						_cenCuentasbancariasExtendsMapper.deleteByPrimaryKey(cuenta);
					}
					
				} else if (resultado[0].equals("2")) {
					LOGGER.info("insertBanksData() -> KO. Insert para mandatos cuentas bancarias  NO realizado correctamente");

					//Si solo tiene tipo Cargo, se elimina la cuenta y no se crea, si tiene algún otro se elimina solo el tipo Cargo
					if (tieneAbono || tieneSCSJ) {
						if (tieneAbono) {
							cuenta.setAbonocargo("A");
						}else{
							cuenta.setAbonocargo(null);
						}
						_cenCuentasbancariasExtendsMapper.updateByPrimaryKey(cuenta);
					}else{
						_cenCuentasbancariasExtendsMapper.deleteByPrimaryKey(cuenta);
					}
					
				} else if (!resultado[0].equals("0")) {
					LOGGER.info("insertBanksData() -> KO. Insert para mandatos cuentas bancarias  NO realizado correctamente");

					//Si solo tiene tipo Cargo, se elimina la cuenta y no se crea, si tiene algún otro se elimina solo el tipo Cargo
					if (tieneAbono || tieneSCSJ) {
						if (tieneAbono) {
							cuenta.setAbonocargo("A");
						}else{
							cuenta.setAbonocargo(null);
						}
						_cenCuentasbancariasExtendsMapper.updateByPrimaryKey(cuenta);
					}else{
						_cenCuentasbancariasExtendsMapper.deleteByPrimaryKey(cuenta);
					}
				}
			}
		}
		
		//Se comprueba si se deben revisar las cuentas y se ejecutan los scripts que se encargan de ello
		
		// Lanzamos el proceso de revision de suscripciones del letrado 
		String resultado[] = ejecutarPL_RevisionSuscripcionesLetrado(""+usuario.getIdinstitucion().toString(),
																				  ""+idPersona.toString(),
																				  "",
																				  ""+ usuario.getIdusuario().toString());
		if ((resultado == null) || (!resultado[0].equals("0"))){
			LOGGER.error("Error al ejecutar el PL PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_LETRADO"+resultado[1]);
		}
		
		// Este proceso se encarga de actualizar las cosas pendientes asociadas a la cuenta de la persona 
		String[] resultado1 = ejecutarPL_Revision_Cuenta(""+usuario.getIdinstitucion().toString(),""+idPersona.toString(),""+cuenta.getIdcuenta().toString(),
				""+ usuario.getIdusuario().toString());
		if (resultado1 == null || !resultado1[0].equals("0")) {
			LOGGER.error("Error al ejecutar el PL PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_CUENTA" + resultado[1]);

		}
		
		// Comprueba si va a lanzar el proceso que asocia las suscripciones activas con forma de pago en metalico a la nueva cuenta bancaria
		/*if (datosBancariosInsertDTO.getRevisionCuentas()) { 
			// Este proceso asocia las suscripciones activas con forma de pago en metalico a la nueva cuenta bancaria 
			resultado1 = ejecutarPL_AltaCuentaCargos(
				""+usuario.getIdinstitucion().toString(),
				  ""+cuenta.getIdpersona().toString(),
				  ""+cuenta.getIdcuenta().toString(),
				  ""+ usuario.getIdusuario().toString());
			if (resultado1 == null || !resultado1[0].equals("0")) {
				/*insertResponseDTO.setStatus(SigaConstants.KO);
				error.setMessage("Error al ejecutar el PL PKG_SERVICIOS_AUTOMATICOS.PROCESO_ALTA_CUENTA_CARGOS" + resultado[1]);
				insertResponseDTO.setError(error);
				return insertResponseDTO;
			}
		}*/
		
		return cuenta.getIdcuenta();
		
	}
	
	private String fill(String text, int size) {
		String cadena = "";

		if (text != null) {
			if (text != "") {
				int lengthCad = size - text.length();

				if (lengthCad == 0 || lengthCad < 0) {
					return text;
				} else if (lengthCad >= 1) {
					for (int i = 0; i < lengthCad; i++) {
						cadena += "0";
					}

					return cadena + text;
				}
			} else {
				return text;
			}
		} 
		
		return text;
	}
	
	private int insertarDatosColegiado(CenSolicitudincorporacion solicitud, AdmUsuarios usuario, Long idPersona){
		
		CenColegiado colegiado = new CenColegiado();
		
		colegiado.setIdpersona(idPersona);
		colegiado.setFechaincorporacion(solicitud.getFechaestadocolegial());
		colegiado.setFechamodificacion(new Date());
		colegiado.setIdinstitucion(usuario.getIdinstitucion());
		
		if(solicitud.getNcolegiado() == null) {
			// Comprobamos si para la institucion tiene un contador unico
			GenParametrosKey key = new GenParametrosKey();
			key.setIdinstitucion(solicitud.getIdinstitucion());
			key.setModulo(SigaConstants.MODULO_CENSO);
			key.setParametro(SigaConstants.PARAMETRO_CONTADOR_UNICO);
			GenParametros genParametro = genParametrosExtendsMapper.selectByPrimaryKey(key);
			
			String contadorUnico = genParametro == null || genParametro.getValor() == null ? "0" : genParametro.getValor();

			StringDTO nColegiado = new StringDTO();
			if (contadorUnico.equals("1")) {
				nColegiado = _cenSolicitudincorporacionExtendsMapper.getMaxNColegiadoComunitario(String.valueOf(solicitud.getIdinstitucion()));
			} else {
				// 10 --> No Inscrito/Español
				// 20 --> Inscrito/Comunitario
				short tipoColegiacion = solicitud.getIdtipocolegiacion();
				
				if(tipoColegiacion == 10) {
					nColegiado = _cenSolicitudincorporacionExtendsMapper.getMaxNColegiado(String.valueOf(solicitud.getIdinstitucion()));
				}else if(tipoColegiacion == 20) {
					nColegiado = _cenSolicitudincorporacionExtendsMapper.getMaxNComunitario(String.valueOf(solicitud.getIdinstitucion()));
				}
			}
			
			colegiado.setNcolegiado(nColegiado.getValor());
			solicitud.setNcolegiado(nColegiado.getValor());

		}
		
		
		colegiado.setUsumodificacion(usuario.getIdusuario());
		colegiado.setNumsolicitudcolegiacion(solicitud.getIdsolicitud().toString());
		colegiado.setFechapresentacion(new Date());
		if (solicitud.getIdtipocolegiacion() == 20) {
			colegiado.setComunitario("1");
			colegiado.setNcomunitario(solicitud.getNcolegiado());
		}else{
			colegiado.setComunitario("0");
		}
		colegiado.setJubilacioncuota("0");
		colegiado.setIndtitulacion("1");
		colegiado.setSituacionejercicio("0");
		colegiado.setSituacionempresa("0");
		if(solicitud.getResidente().equals("1")) {
			colegiado.setSituacionresidente("1");
		}else {
			colegiado.setSituacionresidente("0");
		} 
		CenColegiadoExample ejemploColegiado = new CenColegiadoExample();
		ejemploColegiado.createCriteria().andIdpersonaEqualTo(idPersona).andIdinstitucionEqualTo(solicitud.getIdinstitucion());
		
		List <CenColegiado> listaColegiados = _cenColegiadoMapper.selectByExample(ejemploColegiado);
		if(listaColegiados.isEmpty()) {
			int resultado = _cenColegiadoMapper.insert(colegiado);
			
			if (resultado == 1) {
				CenDatoscolegialesestado datosColegiales = new CenDatoscolegialesestado();
				datosColegiales.setFechaestado(solicitud.getFechaestado());
				datosColegiales.setFechamodificacion(new Date());
				if (null != solicitud.getIdtiposolicitud()) {
					if (solicitud.getIdtiposolicitud() == 10 || solicitud.getIdtiposolicitud() == 30 ) {
						datosColegiales.setIdestado(Short.valueOf("20"));
					}else{
						datosColegiales.setIdestado(Short.valueOf("10"));
					}
				}else{
					datosColegiales.setIdestado(Short.valueOf("10"));
				}
				if(solicitud.getResidente().equals("1")) {
					datosColegiales.setSituacionresidente("1");
				}else {
					datosColegiales.setSituacionresidente("0");
				} 
				datosColegiales.setIdinstitucion(usuario.getIdinstitucion());
				datosColegiales.setIdpersona(idPersona);
				datosColegiales.setUsumodificacion(usuario.getIdusuario());
				datosColegiales.setObservaciones(solicitud.getObservaciones());
				resultado = cenDatoscolegialesestadoMapper.insert(datosColegiales );
			}
			return resultado;
		} else {
			return 1;
		}
	}

	
	/**
	 * @param idInstitucion
	 * @param idPersona
	 * @param idCuenta
	 * @param usuario
	 * @return Codigo y mensaje de error
	 * @throws ClsExceptions
	 */
	private String[] ejecutarPL_AltaCuentaCargos (String idInstitucion, String idPersona, String idCuenta, String usuario) throws Exception {
		Object[] paramIn = new Object[4]; 	//Parametros de entrada del PL
		String resultado[] = new String[2]; //Parametros de salida del PL
	
		try {
	 		// Parametros de entrada del PL
	        paramIn[0] = idInstitucion;
	        paramIn[1] = idPersona;
	        paramIn[2] = idCuenta;
	        paramIn[3] = usuario;

	        // Ejecucion del PL
			resultado = callPLProcedure("{call PKG_SERVICIOS_AUTOMATICOS.PROCESO_ALTA_CUENTA_CARGOS(?,?,?,?,?,?)}", 2, paramIn);
			
		} catch (Exception e) {
			resultado[0] = "1"; 	// P_CODRETORNO
	    	resultado[1] = "ERROR"; // ERROR P_DATOSERROR        	
		}
		
	    return resultado;
	}
	
	/**
	 * Este proceso se encarga de actualizar las cosas pendientes asociadas a la cuenta de la persona 
	 * @param idInstitucion
	 * @param idPersona
	 * @param idCuenta
	 * @param usuario
	 * @return Codigo y mensaje de error
	 * @throws ClsExceptions
	 */
	private  String[] ejecutarPL_Revision_Cuenta (String idInstitucion, String idPersona, String idCuenta, String usuario) throws Exception {
		Object[] paramIn = new Object[4]; 	//Parametros de entrada del PL
		String resultado[] = new String[2]; //Parametros de salida del PL
	
		try {
	 		// Parametros de entrada del PL
	        paramIn[0] = idInstitucion;
	        paramIn[1] = idPersona;
	        paramIn[2] = idCuenta;
	        paramIn[3] = usuario;

	        // Ejecucion del PL
			resultado = callPLProcedure("{call PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_CUENTA(?,?,?,?,?,?)}", 2, paramIn);
			
		} catch (Exception e) {
			resultado[0] = "1"; 	// P_CODRETORNO
	    	resultado[1] = "ERROR"; // ERROR P_DATOSERROR        	
		}
		
	    return resultado;
	}
	
	/**
	   * Calls a PL Funtion
	   * @author CSD
	   * @param functionDefinition string that defines the function
	   * @param inParameters input parameters
	   * @param outParameters number of output parameters
	   * @return error code, '0' if ok
	 * @throws NamingException 
	 * @throws IOException 
	 * @throws SQLException 
	   * @throws ClsExceptions  type Exception
	   */
	  private  String[] callPLProcedure(String functionDefinition, int outParameters, Object[] inParameters) throws IOException, NamingException, SQLException  {
	    String result[] = null;
	    
	    if (outParameters>0) result= new String[outParameters];
	    DataSource ds = getOracleDataSource();
	    Connection con=ds.getConnection();
	    try{
	      CallableStatement cs=con.prepareCall(functionDefinition);
	      int size=inParameters.length;
	      
	      //input Parameters
	      for(int i=0;i<size;i++){
	    	  

	        cs.setString(i+1,(String)inParameters[i]);
	      }
	      //output Parameters
	      for(int i=0;i<outParameters;i++){
	        cs.registerOutParameter(i+size+1,Types.VARCHAR);
	      }
	      
			for (int intento = 1; intento <= 2; intento++) {
				try {
					cs.execute();
					break;
					
				} catch (SQLTimeoutException tex) {
					throw tex;
		
				} catch (SQLException ex) {
					if (ex.getErrorCode() != 4068 || intento == 2) { // JPT: 4068 es un error de descompilado (la segunda vez deberia funcionar)
						throw ex;
					}
				}

			}      

	      for(int i=0;i<outParameters;i++){
	        result[i]=cs.getString(i+size+1);
	      }
	      cs.close();
	      return result;
	      
	    }catch(SQLTimeoutException ex){
	        return null;
	    }catch(SQLException ex){
	    	return null;
	    }catch(Exception e){
	    	return null;
	    }finally{
	      con.close();
	      con = null;
	    }
	  }
	  
	/**
	 * Recupera el datasource con los datos de conexión sacados del fichero de
	 * configuracion
	 * 
	 * @return
	 * @throws IOException
	 * @throws NamingException
	 */
	private  DataSource getOracleDataSource() throws IOException, NamingException {
		try {
			
			LOGGER.debug("Recuperando datasource {} provisto por el servidor (JNDI)");
			
			AdmConfigExample example = new AdmConfigExample();
			example.createCriteria().andClaveEqualTo("spring.datasource.jndi-name");
			List<AdmConfig> config = admConfigMapper.selectByExample(example );
			Context ctx = new InitialContext();
			return (DataSource) ctx.lookup(config.get(0).getValor());
		} catch (NamingException e) {
			throw e;
		}
	}
		
	/**
	 * PL que realiza una revision de letrado
	 * @param idInstitucion
	 * @param idPersona
	 * @param usuario
	 * @return
	 * @throws ClsExceptions
	 */
	private  String[] ejecutarPL_RevisionSuscripcionesLetrado (String idInstitucion, String idPersona, String fecha, String usuario) throws Exception {

		Object[] paramIn = new Object[4]; //Parametros de entrada del PL
		String resultado[] = new String[2]; //Parametros de salida del PL
	
		try {
	 		// Parametros de entrada del PL
	        paramIn[0] = idInstitucion;
	        paramIn[1] = idPersona;
	        paramIn[2] = fecha;
	        paramIn[3] = usuario;

	        // Ejecucion del PL
			resultado = callPLProcedure("{call PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_LETRADO (?,?,?,?,?,?)}", 2, paramIn);
			
		} catch (Exception e) {
			resultado[0] = "1"; 	// P_NUMREGISTRO
	    	resultado[1] = "ERROR"; // ERROR P_DATOSERROR        	
		}
		
	    return resultado;
	}


}
