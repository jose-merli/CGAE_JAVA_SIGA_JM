package org.itcgae.siga.com.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.PlantillaDatosConsultaDTO;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.DTOs.com.PlantillaEnvioSearchItem;
import org.itcgae.siga.DTOs.com.PlantillasEnvioDTO;
import org.itcgae.siga.DTOs.com.RemitenteDTO;
import org.itcgae.siga.DTOs.com.TarjetaConfiguracionDto;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.com.services.IPlantillasEnvioService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.EnvPlantillasenvios;
import org.itcgae.siga.db.entities.EnvPlantillasenviosKey;
import org.itcgae.siga.db.entities.ModPlantillaenvioConsulta;
import org.itcgae.siga.db.entities.ModPlantillaenvioConsultaKey;
import org.itcgae.siga.db.entities.EnvPlantillasenviosWithBLOBs;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosMapper;
import org.itcgae.siga.db.mappers.ModPlantillaenvioConsultaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.PlantillasEnvioExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantillasEnvioServiceImpl implements IPlantillasEnvioService{

	private Logger LOGGER = Logger.getLogger(PlantillasEnvioServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	
	@Autowired
	private PlantillasEnvioExtendsMapper _plantillasEnvioExtendsMapper;
	
	@Autowired
	private EnvPlantillasenviosMapper _envPlantillasenviosMapper;
	
	@Autowired
	private ModPlantillaenvioConsultaMapper _modPlantillaenvioConsultaMapper;
	
	@Autowired
	private ConConsultasExtendsMapper _conConsultasExtendsMapper;


	@Override
	public ComboDTO getComboConsultas(HttpServletRequest request) {
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

				AdmUsuarios usuario = usuarios.get(0);
				comboItems = _conConsultasExtendsMapper.selectConsultasDisponibles(idInstitucion);
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
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try{
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					plantillasItem = _plantillasEnvioExtendsMapper.selectPlantillasEnvios(idInstitucion, filtros);
					if(plantillasItem != null && plantillasItem.size()> 0){
						respuesta.setPlantillasItem(plantillasItem);
					}
				}
			}catch(Exception e){
				Error error = new Error();
				error.setCode(500);
				error.setMessage(e.getMessage());
				e.printStackTrace();
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
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					boolean noBorrada = false;
					for (int i = 0; i < plantillasEnvio.length; i++) {
						EnvPlantillasenviosKey plantillaEnvioKey = new EnvPlantillasenviosKey();
						plantillaEnvioKey.setIdplantillaenvios(Short.valueOf(plantillasEnvio[i].getIdPlantillaEnvios()));
						plantillaEnvioKey.setIdinstitucion(Short.valueOf(plantillasEnvio[i].getIdInstitucion()));
						EnvPlantillasenvios plantillaEnvios = _envPlantillasenviosMapper.selectByPrimaryKey(plantillaEnvioKey);
		
						
						if( idInstitucion == plantillaEnvios.getIdinstitucion()){
								plantillaEnvios.setFechabaja(new Date());
								plantillaEnvios.setFechamodificacion(new Date());
								plantillaEnvios.setUsumodificacion(usuario.getIdusuario());
								_envPlantillasenviosMapper.updateByPrimaryKey(plantillaEnvios);
						}else{
							noBorrada = true;
						}
					}
					respuesta.setCode(200);
					//Si ha habido alguna consulta no borrada se le indica mediante un mensaje al front para indicarselo al usuario
					if(noBorrada){
						respuesta.setMessage("noBorrar");
					}
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
		LOGGER.info("PlantillasEnvioSearch() -> Entrada al servicio para la busqueda de plantillas de envio");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		Error respuesta = new Error();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try{
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					if(datosTarjeta.getIdPlantillasEnvio() != null){
						EnvPlantillasenviosKey key = new EnvPlantillasenviosKey();
						key.setIdplantillaenvios(Short.parseShort(datosTarjeta.getIdPlantillasEnvio()));
						key.setIdtipoenvios(Short.parseShort(datosTarjeta.getIdTipoEnvio()));
						key.setIdinstitucion(idInstitucion);
						EnvPlantillasenviosWithBLOBs plantilla = _envPlantillasenviosMapper.selectByPrimaryKey(key);
						plantilla.setAsunto(datosTarjeta.getAsunto());
						plantilla.setCuerpo(datosTarjeta.getCuerpo());
						plantilla.setIdtipoenvios(Short.valueOf(datosTarjeta.getIdTipoEnvio()));
						plantilla.setNombre(datosTarjeta.getNombre());
						plantilla.setDescripcion(datosTarjeta.getDescripcion());
						plantilla.setFechamodificacion(new Date());
						plantilla.setUsumodificacion(usuario.getIdusuario());
						_envPlantillasenviosMapper.updateByPrimaryKeyWithBLOBs(plantilla);
					}else{
						EnvPlantillasenviosWithBLOBs plantilla = new EnvPlantillasenviosWithBLOBs();
						plantilla.setIdinstitucion(idInstitucion);
						plantilla.setIdtipoenvios(Short.parseShort(datosTarjeta.getIdPlantillasEnvio()));
						plantilla.setNombre(datosTarjeta.getDescripcion());
						if(datosTarjeta.getIdTipoEnvio().equals("1") || datosTarjeta.getIdTipoEnvio().equals("2")){
							plantilla.setAsunto(datosTarjeta.getAsunto());
							plantilla.setCuerpo(datosTarjeta.getCuerpo());
						}
						plantilla.setDescripcion(datosTarjeta.getDescripcion());
						plantilla.setFechamodificacion(new Date());
						plantilla.setUsumodificacion(usuario.getIdusuario());
						_envPlantillasenviosMapper.insert(plantilla);
					}
					respuesta.setCode(200);
					respuesta.setMessage("Plantilla guardada correctamente");

				}
			}catch(Exception e){
				respuesta.setCode(500);
				respuesta.setDescription("Error al guardar datos generales");
				respuesta.setMessage(e.getMessage());
			}
		
		}
		LOGGER.info("PlantillasEnvioSearch() -> Salida del servicio para la busqueda de plantillas de envio");
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
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try{
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					ModPlantillaenvioConsulta consultaAsoc = new ModPlantillaenvioConsulta();
					consultaAsoc.setIdconsulta(Long.valueOf(consulta.getIdConsulta()));
					consultaAsoc.setIdplantillaenvios(Short.valueOf(consulta.getIdPlantilla()));
					consultaAsoc.setIdinstitucion(idInstitucion);
					consultaAsoc.setIdtipoenvios(Short.valueOf(consulta.getIdTipoEnvio()));
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
	public Error borrarConsulta(HttpServletRequest request, PlantillaDatosConsultaDTO consulta) {
		LOGGER.info("PlantillasEnvioSearch() -> Salida del servicio para asociar una consulta a la plantilla de envio");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		Error respuesta = new Error();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try{
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					ModPlantillaenvioConsultaKey key = new ModPlantillaenvioConsultaKey();
					key.setIdconsulta(Long.valueOf(consulta.getIdConsulta()));
					key.setIdplantillaenvios(Short.valueOf(consulta.getIdPlantilla()));
					ModPlantillaenvioConsulta con = _modPlantillaenvioConsultaMapper.selectByPrimaryKey(key);
					con.setFechabaja(new Date());
					con.setUsumodificacion(usuario.getIdusuario());
					con.setFechamodificacion(new Date());
					_modPlantillaenvioConsultaMapper.updateByPrimaryKey(con);
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
		
		
		LOGGER.info("PlantillasEnvioSearch() -> Salida del servicio para asociar una consulta a la plantilla de envio");
		return respuesta;
	}


	@Override
	public Error guardarRemitente(HttpServletRequest request, RemitenteDTO remitente) {
		// TODO Auto-generated method stub
		return null;
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
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try{
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					consultaItems = _conConsultasExtendsMapper.selectConsultasPlantillas(idInstitucion, consulta.getIdPlantillasEnvio(), consulta.getIdTipoEnvio());
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
	public RemitenteDTO detalleRemitente(HttpServletRequest request, String idConsulta) {
		// TODO Auto-generated method stub
		return null;
	}



}
