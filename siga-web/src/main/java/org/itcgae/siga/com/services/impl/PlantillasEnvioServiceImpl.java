package org.itcgae.siga.com.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.DTOs.com.PlantillaEnvioSearchItem;
import org.itcgae.siga.DTOs.com.PlantillasEnvioDTO;
import org.itcgae.siga.DTOs.com.RemitenteDTO;
import org.itcgae.siga.DTOs.com.TarjetaConfiguracionDto;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.cen.services.impl.TarjetaDatosDireccionesServiceImpl;
import org.itcgae.siga.com.services.IPlantillasEnvioService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.EnvPlantillasenvios;
import org.itcgae.siga.db.entities.EnvPlantillasenviosKey;
import org.itcgae.siga.db.entities.ModPlantilladocConsulta;
import org.itcgae.siga.db.entities.ModPlantilladocConsultaExample;
import org.itcgae.siga.db.entities.ModPlantillaenvioConsulta;
import org.itcgae.siga.db.entities.ModPlantillaenvioConsultaExample;
import org.itcgae.siga.db.mappers.ConConsultaMapper;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantillasEnvioServiceImpl implements IPlantillasEnvioService{

	private Logger LOGGER = Logger.getLogger(PlantillasEnvioServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private EnvPlantillasenviosMapper _envPlantillasenviosMapper;
	
	
	@Override
	public ComboDTO getComboTipoEnvio(HttpServletRequest request) {
		
		LOGGER.info("getComboTipoEnvio() -> Entrada al servicio para obtener los tipos de envio");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				//comboItems = cenTipoDireccionExtendsMapper.selectTipoDireccion(usuario.getIdlenguaje());
				
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
				comboDTO.setCombooItems(comboItems);
				
			}
			
		}
		LOGGER.info("getComboTipoEnvio() -> Salida del servicio para obtener los tipos de envio");
		
		return comboDTO;
	}

	@Override
	public ComboDTO getComboConsultas(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlantillasEnvioDTO PlantillasEnvioSearch(HttpServletRequest request, PlantillaEnvioSearchItem filtros) {
		
		LOGGER.info("getComboTipoEnvio() -> Entrada al servicio para obtener los tipos de envio");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

			}
			
		}
		LOGGER.info("getComboTipoEnvio() -> Salida del servicio para obtener los tipos de envio");
		return null;
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
LOGGER.info("guardarDatosGenerales() -> Entrada al servicio para guardar los datos generales de la plantilla");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

			}
			
		}
		
		LOGGER.info("guardarDatosGenerales() -> Salida del servicio para guardar los datos generales de la plantilla");
		return null;
	}

	@Override
	public ConsultasDTO detalleConsultas(HttpServletRequest request, ConsultasDTO consultas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error asociarConsulta(HttpServletRequest request, ConsultaItem consulta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error borrarConsulta(HttpServletRequest request, ConsultaItem consulta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemitenteDTO detalleRemitente(HttpServletRequest request, String idConsulta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error guardarREmitente(HttpServletRequest request, RemitenteDTO remitente) {
		// TODO Auto-generated method stub
		return null;
	}



}
