package org.itcgae.siga.com.services.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.FinalidadConsultaDTO;
import org.itcgae.siga.DTOs.com.PlantillaDatosConsultaDTO;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.DTOs.com.PlantillaEnvioSearchItem;
import org.itcgae.siga.DTOs.com.PlantillasEnvioDTO;
import org.itcgae.siga.DTOs.com.RemitenteDTO;
import org.itcgae.siga.DTOs.com.TarjetaConfiguracionDto;
import org.itcgae.siga.DTOs.com.TarjetaRemitenteDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.com.services.IPlantillasEnvioService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenDireccionesExample;
import org.itcgae.siga.db.entities.CenDireccionesKey;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.ConConsulta;
import org.itcgae.siga.db.entities.ConConsultaKey;
import org.itcgae.siga.db.entities.EnvPlantillasenvios;
import org.itcgae.siga.db.entities.EnvPlantillasenviosKey;
import org.itcgae.siga.db.entities.EnvPlantillasenviosWithBLOBs;
import org.itcgae.siga.db.entities.ModPlantillaenvioConsulta;
import org.itcgae.siga.db.entities.ModPlantillaenvioConsultaKey;
import org.itcgae.siga.db.mappers.CenDireccionesMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.ConConsultaMapper;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosMapper;
import org.itcgae.siga.db.mappers.ModPlantillaenvioConsultaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvPlantillaEnviosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlantillasEnvioServiceImpl implements IPlantillasEnvioService{

	private Logger LOGGER = Logger.getLogger(PlantillasEnvioServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	EnvPlantillaEnviosExtendsMapper _envPlantillaEnviosExtendsMapper;
	
	@Autowired
	private EnvPlantillasenviosMapper _envPlantillasenviosMapper;
	
	@Autowired
	private ModPlantillaenvioConsultaMapper _modPlantillaenvioConsultaMapper;
	
	@Autowired
	private ConConsultasExtendsMapper _conConsultasExtendsMapper;
	
	@Autowired
	private CenPersonaMapper _cenPersonaMapper;

	@Autowired 
	private CenDireccionesMapper _cenDireccionesMapper;
	
	@Autowired
	private ConConsultaMapper _conConsultaMapper;

	@Override
	public ComboDTO getComboConsultas(HttpServletRequest request, String filtro) {
		LOGGER.info("getComboConsultas() -> Entrada al servicio para obtener las consultas disponibles");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {

				comboItems = _conConsultasExtendsMapper.selectConsultasDisponiblesFiltro(idInstitucion, null, null, filtro);
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
				comboDTO.setCombooItems(comboItems);
				
			}
		}
		
		LOGGER.info("getComboConsultas() -> Salida del servicio para obtener las consultas disponibles");
		return comboDTO;
	}

	@Override
	public PlantillasEnvioDTO PlantillasEnvioSearch(HttpServletRequest request, PlantillaEnvioSearchItem filtros) {
		
		LOGGER.info("PlantillasEnvioSearch() -> Entrada al servicio para la busqueda de plantillas de envio");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		PlantillasEnvioDTO respuesta = new PlantillasEnvioDTO();
		List<PlantillaEnvioItem> plantillasItem = new ArrayList<PlantillaEnvioItem>();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("PlantillasEnvioSearch() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("PlantillasEnvioSearch() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try{
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					plantillasItem = _envPlantillaEnviosExtendsMapper.selectPlantillasEnvios(idInstitucion, usuario.getIdlenguaje(), filtros);
					if(plantillasItem != null && plantillasItem.size()> 0){
						respuesta.setPlantillasItem(plantillasItem);
					}
				}
			}catch(Exception e){
				Error error = new Error();
				error.setCode(500);
				error.setMessage(e.getMessage());
				e.printStackTrace();
				respuesta.setError(error);
			}
		
		}
		LOGGER.info("PlantillasEnvioSearch() -> Salida del servicio para la busqueda de plantillas de envio");
		return respuesta;
	}

	@Override
	public Error borrarPlantillasEnvio(HttpServletRequest request, PlantillaEnvioItem[] plantillasEnvio) {
		LOGGER.info("borrarPlantillasEnvio() -> Entrada al servicio de borrar plantilla envio");

		Error respuesta = new Error();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("PlantillasEnvioSearch() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("PlantillasEnvioSearch() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					for (int i = 0; i < plantillasEnvio.length; i++) {
						EnvPlantillasenviosKey plantillaEnvioKey = new EnvPlantillasenviosKey();
						plantillaEnvioKey.setIdplantillaenvios(Integer.parseInt(plantillasEnvio[i].getIdPlantillaEnvios()));
						plantillaEnvioKey.setIdinstitucion(Short.valueOf(plantillasEnvio[i].getIdInstitucion()));
						plantillaEnvioKey.setIdtipoenvios(Short.valueOf(plantillasEnvio[i].getIdTipoEnvios()));
						EnvPlantillasenvios plantillaEnvios = _envPlantillasenviosMapper.selectByPrimaryKey(plantillaEnvioKey);
						plantillaEnvios.setFechabaja(new Date());
						plantillaEnvios.setFechamodificacion(new Date());
						plantillaEnvios.setUsumodificacion(usuario.getIdusuario());
						_envPlantillasenviosMapper.updateByPrimaryKey(plantillaEnvios);
					}
					respuesta.setCode(200);
					respuesta.setDescription("Plantillas de envío borradas");
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setMessage("Error al borrar consulta");
					respuesta.setDescription(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		LOGGER.info("borrarPlantillasEnvio() -> Salida del servicio de borrar plantilla envio");
		return respuesta;
	}

	@Override
	public Error guardarDatosGenerales(HttpServletRequest request, TarjetaConfiguracionDto datosTarjeta) {
		LOGGER.info("guardarDatosGenerales() -> Entrada al servicio para la busqueda de plantillas de envio");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		Error respuesta = new Error();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("guardarDatosGenerales() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("guardarDatosGenerales() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try{
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					if(datosTarjeta.getIdPlantillaEnvios() != null){
						EnvPlantillasenviosKey key = new EnvPlantillasenviosKey();
						key.setIdplantillaenvios(Integer.parseInt(datosTarjeta.getIdPlantillaEnvios()));
						key.setIdtipoenvios(Short.parseShort(datosTarjeta.getIdTipoEnvios()));
						key.setIdinstitucion(idInstitucion);
						EnvPlantillasenviosWithBLOBs plantilla = _envPlantillasenviosMapper.selectByPrimaryKey(key);
						plantilla.setAsunto(datosTarjeta.getAsunto());
						plantilla.setCuerpo(datosTarjeta.getCuerpo());
						plantilla.setIdtipoenvios(Short.valueOf(datosTarjeta.getIdTipoEnvios()));
						plantilla.setNombre(datosTarjeta.getNombre());
						plantilla.setDescripcion(datosTarjeta.getDescripcion());
						plantilla.setFechamodificacion(new Date());
						plantilla.setUsumodificacion(usuario.getIdusuario());
						_envPlantillasenviosMapper.updateByPrimaryKeyWithBLOBs(plantilla);
					}else{
						EnvPlantillasenviosWithBLOBs plantilla = new EnvPlantillasenviosWithBLOBs();
						NewIdDTO id = _envPlantillaEnviosExtendsMapper.selectMaxIDPlantillas();
						plantilla.setIdplantillaenvios(Integer.parseInt(id.getNewId()));
						plantilla.setIdinstitucion(idInstitucion);
						plantilla.setIdtipoenvios(Short.parseShort(datosTarjeta.getIdTipoEnvios()));
						plantilla.setNombre(datosTarjeta.getNombre());
						plantilla.setAsunto(datosTarjeta.getAsunto());
						plantilla.setCuerpo(datosTarjeta.getCuerpo());
						plantilla.setDescripcion(datosTarjeta.getDescripcion());
						plantilla.setFechamodificacion(new Date());
						plantilla.setUsumodificacion(usuario.getIdusuario());
						plantilla.setAntigua("N");
						_envPlantillasenviosMapper.insert(plantilla);
						respuesta.setMessage(plantilla.getIdplantillaenvios().toString());
					}
					respuesta.setCode(200);
					
				}
			}catch(Exception e){
				respuesta.setCode(500);
				respuesta.setDescription("Error al guardar datos generales");
				respuesta.setMessage(e.getMessage());
			}
		
		}
		LOGGER.info("guardarDatosGenerales() -> Salida del servicio para la busqueda de plantillas de envio");
		return respuesta;
	}

	@Override
	public Error asociarConsulta(HttpServletRequest request, PlantillaDatosConsultaDTO consulta) {
		LOGGER.info("asociarConsulta() -> Entrada al servicio para asociar una consulta a la plantilla de envio");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		Error respuesta = new Error();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("asociarConsulta() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("asociarConsulta() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try{
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					ModPlantillaenvioConsulta consultaAsoc = new ModPlantillaenvioConsulta();
					consultaAsoc.setIdconsulta(Long.valueOf(consulta.getIdConsulta()));
					consultaAsoc.setIdplantillaenvios(Integer.parseInt(consulta.getIdPlantillaEnvios()));
					consultaAsoc.setIdinstitucion(idInstitucion);
					consultaAsoc.setIdtipoenvios(Short.valueOf(consulta.getIdTipoEnvios()));
					consultaAsoc.setUsumodificacion(usuario.getIdusuario());
					consultaAsoc.setFechamodificacion(new Date());
					_modPlantillaenvioConsultaMapper.insert(consultaAsoc);
					respuesta.setCode(200);
					respuesta.setMessage("Consulta Asociada correctamente");
				}
			}catch(Exception e){
				respuesta.setCode(500);
				respuesta.setDescription("Error al asociar consulta a la plantilla");
				respuesta.setMessage(e.getMessage());
				e.printStackTrace();
			}
		}
		
		
		LOGGER.info("asociarConsulta() -> Salida del servicio para asociar una consulta a la plantilla de envio");
		return respuesta;
	}

	@Override
	public Error desAsociarConsulta(HttpServletRequest request, PlantillaDatosConsultaDTO[] consulta) {
		LOGGER.info("desAsociarConsulta() -> Salida del servicio para borrar una consulta a la plantilla de envio");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		Error respuesta = new Error();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("desAsociarConsulta() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("desAsociarConsulta() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try{
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					for (int i = 0; i < consulta.length; i++) {
						ModPlantillaenvioConsultaKey key = new ModPlantillaenvioConsultaKey();
						key.setIdconsulta(Long.valueOf(consulta[i].getIdConsulta()));
						key.setIdplantillaenvios(Integer.parseInt(consulta[i].getIdPlantillaEnvios()));
						ModPlantillaenvioConsulta con = _modPlantillaenvioConsultaMapper.selectByPrimaryKey(key);
						con.setFechabaja(new Date());
						con.setUsumodificacion(usuario.getIdusuario());
						con.setFechamodificacion(new Date());
						_modPlantillaenvioConsultaMapper.updateByPrimaryKey(con);
					}
					respuesta.setCode(200);
					respuesta.setMessage("Consulta desAsocidada correctamente");
				}
			}catch(Exception e){
				respuesta.setCode(500);
				respuesta.setDescription("Error al asociar consulta a la plantilla");
				respuesta.setMessage(e.getMessage());
				e.printStackTrace();
			}
		}
		
		
		LOGGER.info("desAsociarConsulta() -> Salida del servicio para borrar una consulta a la plantilla de envio");
		return respuesta;
	}

	@Override
	public Error guardarRemitente(HttpServletRequest request, TarjetaRemitenteDTO remitente) {
		LOGGER.info("guardarRemitente() -> Entrada al servicio para añadir un remitente");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		Error respuesta = new Error();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("guardarRemitente() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("guardarRemitente() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try{
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					EnvPlantillasenviosKey key = new EnvPlantillasenviosKey();
					key.setIdinstitucion(idInstitucion);
					key.setIdplantillaenvios(Integer.parseInt(remitente.getIdPlantillaEnvios()));
					key.setIdtipoenvios(Short.valueOf(remitente.getIdTipoEnvios()));
					
					EnvPlantillasenvios plantilla = _envPlantillasenviosMapper.selectByPrimaryKey(key);
					plantilla.setIddireccion(Long.valueOf(remitente.getIdDireccion()));
					plantilla.setIdpersona(Long.valueOf(remitente.getIdPersona()));
					plantilla.setFechamodificacion(new Date());
					plantilla.setUsumodificacion(usuario.getIdusuario());
					_envPlantillasenviosMapper.updateByPrimaryKey(plantilla);
					respuesta.setCode(200);
					respuesta.setMessage("Remitente guardado correctamente");
				}
			}catch(Exception e){
				respuesta.setCode(500);
				respuesta.setDescription("Error al guardar un remitente");
				respuesta.setMessage(e.getMessage());
				e.printStackTrace();
			}
		}
		
		LOGGER.info("guardarRemitente() -> Salida del servicio para servicio para añadir un remitente");
		return respuesta;
	}

	@Override
	public ConsultasDTO detalleConsultas(HttpServletRequest request, TarjetaConfiguracionDto consulta) {
	LOGGER.info("detalleConsultas() -> Entrada al servicio para obtener las consultas asociadas a la plantilla");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		ConsultasDTO respuesta = new ConsultasDTO();
		List<ConsultaItem> consultaItems = new ArrayList<ConsultaItem>();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("detalleConsultas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("detalleConsultas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try{
				if (null != usuarios && usuarios.size() > 0) {
					consultaItems = _conConsultasExtendsMapper.selectConsultasPlantillas(idInstitucion, consulta.getIdPlantillaEnvios(), consulta.getIdTipoEnvios());
					if(consultaItems != null && consultaItems.size()>0){
						respuesta.setConsultaItem(consultaItems);
					}
				}
			}catch(Exception e){
				Error error = new Error();
				error.setCode(500);
				error.setMessage(e.getMessage());
				respuesta.setError(error);
				e.printStackTrace();
			}
		
		}
		LOGGER.info("detalleConsultas() -> Salida del servicio para obtener las consultas asociadas a la plantilla");
		return respuesta;
	}

	@Override
	public RemitenteDTO detalleRemitente(HttpServletRequest request, PlantillaDatosConsultaDTO datosPlantilla) {
		LOGGER.info("detalleRemitente() -> Entrada al servicio para obtener detalles del remitente");
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RemitenteDTO remitente = new RemitenteDTO();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("detalleRemitente() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("detalleRemitente() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try{
				if (null != usuarios && usuarios.size() > 0) {
					
					EnvPlantillasenviosKey key = new EnvPlantillasenviosKey();
					key.setIdinstitucion(idInstitucion);
					key.setIdplantillaenvios(Integer.parseInt(datosPlantilla.getIdPlantillaEnvios()));
					key.setIdtipoenvios(Short.valueOf(datosPlantilla.getIdTipoEnvios()));
					EnvPlantillasenvios plantilla = _envPlantillasenviosMapper.selectByPrimaryKey(key);

					if (null != plantilla && null != plantilla.getIdpersona()) {
						CenPersona persona = _cenPersonaMapper.selectByPrimaryKey(plantilla.getIdpersona());
						remitente.setIdPersona(persona.getIdpersona().toString());
						remitente.setNombre(persona.getNombre());
						remitente.setApellido1(persona.getApellidos1());
						remitente.setApellido2(persona.getApellidos2());
						
						CenDireccionesKey keyDirecciones = new CenDireccionesKey();
						keyDirecciones.setIddireccion(plantilla.getIddireccion());
						keyDirecciones.setIdinstitucion(plantilla.getIdinstitucion());
						keyDirecciones.setIdpersona(plantilla.getIdpersona());
						CenDirecciones direccion = _cenDireccionesMapper.selectByPrimaryKey(keyDirecciones);
						List<DatosDireccionesItem> direccionList = new ArrayList<DatosDireccionesItem>();
						DatosDireccionesItem direccionItem = new DatosDireccionesItem();
						direccionItem.setIdDireccion(direccion.getIddireccion().toString());
						direccionItem.setDomicilio(direccion.getDomicilio());
						direccionItem.setIdPoblacion(direccion.getIdpoblacion().toString());
						direccionItem.setIdProvincia(direccion.getIdprovincia());
						direccionItem.setIdPais(direccion.getIdpais());
						direccionItem.setCodigoPostal(direccion.getCodigopostal());
						if(direccion.getTelefono1()!=null)
						direccionItem.setTelefono(direccion.getTelefono1());
						if(direccion.getTelefono2() != null)
							direccionItem.setTelefono2(direccion.getTelefono2());
						if(direccion.getMovil()!= null)
							direccionItem.setMovil(direccion.getMovil());
						if(direccion.getFax1() != null)
							direccionItem.setFax(direccion.getFax1());
						if(direccion.getFax2() != null)
							direccionItem.setFax2(direccion.getFax2());
						if(direccion.getCorreoelectronico() != null)
							direccionItem.setCorreoElectronico(direccion.getCorreoelectronico());
						if(direccion.getPaginaweb() != null)
							direccionItem.setPaginaWeb(direccion.getPaginaweb());
						direccionList.add(direccionItem);
						remitente.setDireccion(direccionList);	
					}

				}
			}catch(Exception e){
				Error error = new Error();
				error.setCode(500);
				error.setDescription("Error al obtener detalles remitente");
				error.setMessage(e.getMessage());
				remitente.setError(error);
				e.printStackTrace();
			}
		}
		
		LOGGER.info("detalleRemitente() -> Salida del servicio para obtener detalles del remitente");
		return remitente;
	}

	@Override
	public FinalidadConsultaDTO obtenerFinalidad(HttpServletRequest request, String idConsulta) {
		LOGGER.info("obtenerFinalidad() -> Entrada al servicio para obtener la finalidad de una consulta");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		FinalidadConsultaDTO finalidadDTO = new FinalidadConsultaDTO();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("obtenerFinalidad() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("obtenerFinalidad() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try{
				if (null != usuarios && usuarios.size() > 0) {
					String finalidad = obtenerFinalidadByIdConsulta(idInstitucion, Long.valueOf(idConsulta));
					finalidadDTO.setFinalidad(finalidad);
				}
			}catch(Exception e){
				Error error = new Error();
				error.setCode(500);
				error.setDescription("Error al obtener detalles remitente");
				error.setMessage(e.getMessage());
				finalidadDTO.setError(error);
				e.printStackTrace();
			}
		}
		
		LOGGER.info("obtenerFinalidad() -> Salida del servicio para obtener la finalidad de una consulta");
		return finalidadDTO;
	}

	@Override
	public RemitenteDTO obtenerPersonaYdireccion(HttpServletRequest request, String idPersona) {
		LOGGER.info("obtenerPersonaYdireccion() -> Entrada al servicio para obtener detalles del remitente");
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RemitenteDTO remitente = new RemitenteDTO();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("obtenerPersonaYdireccion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("obtenerPersonaYdireccion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try{
				if (null != usuarios && usuarios.size() > 0) {
					CenPersona persona = _cenPersonaMapper.selectByPrimaryKey(Long.valueOf(idPersona));
					remitente.setIdPersona(persona.getIdpersona().toString());
					remitente.setNombre(persona.getNombre());
					remitente.setApellido1(persona.getApellidos1());
					remitente.setApellido2(persona.getApellidos2());
					
					CenDireccionesExample example = new CenDireccionesExample();
					example.createCriteria().andIdpersonaEqualTo(Long.valueOf(idPersona)).andIdinstitucionEqualTo(idInstitucion);
					List<CenDirecciones> direcciones = _cenDireccionesMapper.selectByExample(example);
					if(direcciones != null && direcciones.size() > 0){
						List<DatosDireccionesItem> direccionesList = new ArrayList<DatosDireccionesItem>();
						for (CenDirecciones item : direcciones) {
							DatosDireccionesItem direccion = new DatosDireccionesItem();
							if(item.getIdinstitucion() != null){
								direccion.setIdInstitucion(item.getIdinstitucion().toString());
							}
							if(item.getIddireccion() != null){
								direccion.setIdDireccion(item.getIddireccion().toString());	
							}
							direccion.setDomicilio(item.getDomicilio());
							direccion.setIdPoblacion(item.getIdpoblacion());
							direccion.setIdProvincia(item.getIdprovincia());
							direccion.setIdPais(item.getIdpais());
							direccion.setCodigoPostal(item.getCodigopostal());
							direccion.setTelefono(item.getTelefono1());
							direccion.setTelefono2(item.getTelefono2());
							direccion.setMovil(item.getMovil());
							direccion.setFax(item.getFax1());
							direccion.setFax2(item.getFax2());
							direccion.setCorreoElectronico(item.getCorreoelectronico());
							direccion.setPaginaWeb(item.getPaginaweb());
							direccionesList.add(direccion);
						}
						remitente.setDireccion(direccionesList);
					}

				}
			}catch(Exception e){
				Error error = new Error();
				error.setCode(500);
				error.setDescription("Error al obtener persona y direccion");
				error.setMessage(e.getMessage());
				remitente.setError(error);
				e.printStackTrace();
			}
		}
		
		LOGGER.info("obtenerPersonaYdireccion() -> Salida del servicio para obtener detalles del remitente");
		return remitente;
	}
	
	@Override
	public String obtenerFinalidadByIdConsulta(Short idInstitucion, Long idConsulta){
		ConConsultaKey key = new ConConsultaKey();
		key.setIdconsulta(Long.valueOf(idConsulta));
		key.setIdinstitucion(idInstitucion);
		ConConsulta consulta = _conConsultaMapper.selectByPrimaryKey(key);
		
		if(consulta == null) {
			key.setIdinstitucion(Short.valueOf("2000"));
			consulta = _conConsultaMapper.selectByPrimaryKey(key);
		}
		
		int inicioSelect = consulta.getSentencia().indexOf("<SELECT>")+8;
		int finSelect = consulta.getSentencia().indexOf("</SELECT>");
		String finalidad = consulta.getSentencia().substring(inicioSelect, finSelect);
		finalidad = finalidad.replace("select", "");
		finalidad = finalidad.replace("distinct", "");
		return finalidad;
	}

}
