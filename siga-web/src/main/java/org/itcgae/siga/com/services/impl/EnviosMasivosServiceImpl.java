package org.itcgae.siga.com.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.EnvioProgramadoDto;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;
import org.itcgae.siga.DTOs.com.TarjetaConfiguracionDto;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.com.services.IEnviosMasivosService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.EnvCamposplantilla;
import org.itcgae.siga.db.entities.EnvDestinatarios;
import org.itcgae.siga.db.entities.EnvDestinatariosExample;
import org.itcgae.siga.db.entities.EnvEnvioprogramado;
import org.itcgae.siga.db.entities.EnvEnvioprogramadoKey;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.EnvEnviosKey;
import org.itcgae.siga.db.entities.EnvHistoricoestadoenvio;
import org.itcgae.siga.db.mappers.EnvDestinatariosMapper;
import org.itcgae.siga.db.mappers.EnvEnvioprogramadoMapper;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.mappers.EnvHistoricoestadoenvioMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEnviosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEstadoEnvioExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvHistoricoEstadoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvTipoEnvioExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EnviosMasivosServiceImpl implements IEnviosMasivosService{
	
	
	private Logger LOGGER = Logger.getLogger(EnviosMasivosServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private EnvTipoEnvioExtendsMapper _envTipoEnvioExtendsMapper;
	
	@Autowired
	private EnvEstadoEnvioExtendsMapper _envEstadoEnvioExtendsMapper;
	
	@Autowired
	private EnvEnviosExtendsMapper _envEnviosExtendsMapper;
	
	@Autowired
	private EnvEnviosMapper _envEnviosMapper;
	
	@Autowired
	private EnvEnvioprogramadoMapper _envEnvioprogramadoMapper;
	
	@Autowired
	private EnvHistoricoestadoenvioMapper _envHistoricoestadoenvioMapper;
	
	@Autowired
	private EnvDestinatariosMapper _envDestinatariosMapper;
	
	@Autowired
	private EnvHistoricoEstadoExtendsMapper _envHistoricoEstadoExtendsMapper;

	@Override
	public ComboDTO estadoEnvios(HttpServletRequest request) {
		
		LOGGER.info("estadoEnvios() -> Entrada al servicio para obtener combo estado envios");
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
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
				comboItems = _envEstadoEnvioExtendsMapper.selectEstadoEnvios(usuario.getIdlenguaje());
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
				comboDTO.setCombooItems(comboItems);
				
			}
		}
		
		
		LOGGER.info("estadoEnvios() -> Salida del servicio para obtener combo estado envios");
		
		
		return comboDTO;
	}

	@Override
	public ComboDTO tipoEnvio(HttpServletRequest request) {
		
	LOGGER.info("tipoEnvio() -> Entrada al servicio para obtener combo tipos de envio");
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
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
				comboItems = _envTipoEnvioExtendsMapper.selectTipoEnvios(usuario.getIdlenguaje());
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
				comboDTO.setCombooItems(comboItems);
				
			}
		}
		
		
		LOGGER.info("estadoEnvios() -> Salida del servicio para obtener combo tipos de envio");
		
		
		return comboDTO;
	}

	@Override
	public EnviosMasivosDTO enviosMasivosSearch(HttpServletRequest request, EnviosMasivosSearch filtros) {
		LOGGER.info("estadoEnvios() -> Entrada al servicio para obtener combo estado envios");
		
		EnviosMasivosDTO enviosMasivos = new EnviosMasivosDTO();
		List<EnviosMasivosItem> enviosItemList = new ArrayList<EnviosMasivosItem>();
		
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
				
				try{
					enviosItemList = _envEnviosExtendsMapper.selectEnviosMasivosSearch(usuario.getIdinstitucion(), usuario.getIdlenguaje(), filtros);
					if(enviosItemList.size()>0){
						enviosMasivos.setEnviosMasivosItem(enviosItemList);
					}
				}catch(Exception e){
					Error error = new Error();
					error.setCode(500);
					error.setMessage(e.getMessage());
					enviosMasivos.setError(error);
				}
				
			}
		}
		
		
		LOGGER.info("estadoEnvios() -> Salida del servicio para obtener combo estado envios");
		return enviosMasivos;
	}

	@Override
	public Error programarEnvio(HttpServletRequest request, EnvioProgramadoDto[] enviosProgramadosDto) {
		
		LOGGER.info("programarEnvio() -> Entrada al servicio para programar los envios");
		
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
				
				try{
					
					for(int i = 0; i <= enviosProgramadosDto.length;i++){
						//Solo programamos los envios si tiene estado 1(nuevo) o 4(programado)
						if(enviosProgramadosDto[i].getIdEstado().equals("1") || enviosProgramadosDto[i].getIdEstado().equals("4")){
							int update = 0;
							EnvEnvioprogramadoKey key = new EnvEnvioprogramadoKey();
							key.setIdenvio(Long.parseLong(enviosProgramadosDto[i].getIdEnvio()));
							key.setIdinstitucion(usuario.getIdinstitucion());
							EnvEnvioprogramado envioProgramado = _envEnvioprogramadoMapper.selectByPrimaryKey(key);
							envioProgramado.setFechaprogramada(enviosProgramadosDto[i].getFechaProgramada());
							envioProgramado.setFechamodificacion(new Date());
							envioProgramado.setUsumodificacion(usuario.getIdusuario());
							update = _envEnvioprogramadoMapper.updateByPrimaryKey(envioProgramado);
							if(update > 0){
								EnvEnviosKey keyEnvio = new EnvEnviosKey();
								keyEnvio.setIdenvio(Long.parseLong(enviosProgramadosDto[i].getIdEnvio()));
								keyEnvio.setIdinstitucion(usuario.getIdinstitucion());
								EnvEnvios envio = _envEnviosMapper.selectByPrimaryKey(keyEnvio);
								envio.setFechaprogramada(enviosProgramadosDto[i].getFechaProgramada());
								envio.setFechamodificacion(new Date());
								envio.setUsumodificacion(usuario.getIdusuario());
								_envEnviosMapper.updateByPrimaryKey(envio);
							}
						}
						
						
					}
					respuesta.setCode(200);
					respuesta.setDescription("Envios Masivos programados correctamente");
					respuesta.setMessage("Updates correcto");
					
				}catch(Exception e){
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
				}
				
			}
				
		}
		LOGGER.info("programarEnvio() -> Salida del servicio para programar los envios");
		return respuesta;
	}

	@Override
	public Error cancelarEnvios(HttpServletRequest request, EnvioProgramadoDto[] envios) {
		LOGGER.info("cancelarEnvios() -> Entrada al servicio para cancelar envios");
		
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
				try{
					for(int i = 0; i < envios.length;i++){
						//Solo cancelamos los envios si tiene estado 1(nuevo) o 4(programado)
						if(envios[i].getIdEstado().equals("1") || envios[i].getIdEstado().equals("4")){
							int update = 0;
							EnvEnviosKey keyEnvio = new EnvEnviosKey();
							keyEnvio.setIdenvio(Long.parseLong(envios[i].getIdEnvio()));
							keyEnvio.setIdinstitucion(usuario.getIdinstitucion());
							EnvEnvios envio = _envEnviosMapper.selectByPrimaryKey(keyEnvio);
							envio.setFechabaja(new Date());
							//Le asignamos el estado 6(archivado)
							Short idEstado = 6;
							envio.setIdestado(idEstado);
							update = _envEnviosMapper.updateByPrimaryKey(envio);
							if(update > 0){
								
								EnvHistoricoestadoenvio historico = new EnvHistoricoestadoenvio();
								NewIdDTO idDTO = _envHistoricoEstadoExtendsMapper.selectMaxIDHistorico();
								historico.setIdhistorico(Short.parseShort(idDTO.getNewId()));
								historico.setIdenvio(Long.parseLong(envios[i].getIdEnvio()));
								historico.setIdinstitucion(usuario.getIdinstitucion());
								historico.setFechamodificacion(new Date());
								historico.setFechaestado(new Date());
								historico.setUsumodificacion(usuario.getIdusuario());
								historico.setIdestado(idEstado);
								_envHistoricoestadoenvioMapper.insert(historico);
							}
						}
					}
					respuesta.setCode(200);
					respuesta.setDescription("Envios cancelados correctamente");
					respuesta.setMessage("Updates correcto");
				}catch(Exception e){
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
				}
				
				
			}
		}
		LOGGER.info("programarEnvio() -> Salida del servicio para cancelar envios");
		return respuesta;
	}

	@Override
	public Error enviar(HttpServletRequest request, List<EnvioProgramadoDto> envios) {
		
		LOGGER.info("enviar() -> Entrada al servicio para enviar");
		
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
				try{
					for(int i = 0; i <= envios.size();i++){
						EnvDestinatariosExample example = new EnvDestinatariosExample();
						example.createCriteria().andIdenvioEqualTo(Long.parseLong(envios.get(i).getIdEnvio())).andIdinstitucionEqualTo(usuario.getIdinstitucion());
						List<EnvDestinatarios> destinatarios = _envDestinatariosMapper.selectByExample(example);
						
						
					}
					respuesta.setCode(200);
					respuesta.setDescription("Envios masivos enviados correctamente");
					respuesta.setMessage("Updates correcto");
				}catch(Exception e){
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
				}
				
				
			}
		}
		LOGGER.info("enviar() -> Salida del servicio para enviar");
		return respuesta;
	}

	@Override
	public Error guardarConfiguracion(HttpServletRequest request, TarjetaConfiguracionDto datosTarjeta) {
		
		LOGGER.info("guardarConfiguracion() -> Entrada al servicio para guardar datos tarjeta configuración");
		
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
				try{
					
					if(datosTarjeta.getIdEnvio() != null){
						
						
					}else{
						Short idAsunto = 1;
						Short idCuerpo = 2;
						EnvCamposplantilla datosAsunto = new EnvCamposplantilla();
						datosAsunto.setIdcampo(idAsunto);
						datosAsunto.setIdinstitucion(idInstitucion);
						datosAsunto.setIdplantillaenvios(Short.parseShort(datosTarjeta.getIdPlantilla()));
						datosAsunto.setIdtipoenvios(Short.parseShort(datosTarjeta.getIdTipoEnvio()));
						//datosAsunto.setValor(datosTarjeta.get);
					}
					
					respuesta.setCode(200);
					respuesta.setDescription("Datos configuracion de envio guardados correctamente");
					respuesta.setMessage("Updates correcto");
				}catch(Exception e){
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
				}
				
				
			}
		}
		LOGGER.info("guardarConfiguracion() -> Salida del servicio para guardar datos tarjeta configuración");
		return respuesta;
	}
	
	

}
