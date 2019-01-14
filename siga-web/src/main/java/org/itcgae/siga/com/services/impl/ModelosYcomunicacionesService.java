package org.itcgae.siga.com.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesDTO;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.com.services.IModelosYcomunicacionesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ModModeloPerfiles;
import org.itcgae.siga.db.entities.ModModeloPerfilesExample;
import org.itcgae.siga.db.entities.ModModeloPlantilladocumento;
import org.itcgae.siga.db.entities.ModModeloPlantilladocumentoExample;
import org.itcgae.siga.db.entities.ModModeloPlantillaenvio;
import org.itcgae.siga.db.entities.ModModeloPlantillaenvioExample;
import org.itcgae.siga.db.entities.ModModelocomunicacion;
import org.itcgae.siga.db.entities.ModPlantilladocConsulta;
import org.itcgae.siga.db.entities.ModPlantilladocConsultaExample;
import org.itcgae.siga.db.mappers.ModModeloPerfilesMapper;
import org.itcgae.siga.db.mappers.ModModeloPlantilladocumentoMapper;
import org.itcgae.siga.db.mappers.ModModeloPlantillaenvioMapper;
import org.itcgae.siga.db.mappers.ModModelocomunicacionMapper;
import org.itcgae.siga.db.mappers.ModPlantilladocConsultaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloComunicacionExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelosYcomunicacionesService implements IModelosYcomunicacionesService{

	private Logger LOGGER = Logger.getLogger(ModelosYcomunicacionesService.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	
	@Autowired
	private ModModeloComunicacionExtendsMapper modModeloComunicacionExtendsMapper;
	
	@Autowired
	private ModModelocomunicacionMapper modModelocomunicacionMapper;
	
	@Autowired
	private ModModeloPerfilesMapper modModeloPerfilesMapper;
	
	@Autowired
	private ModModeloPlantilladocumentoMapper modModeloPlantilladocumentoMapper;
	
	@Autowired
	private ModModeloPlantillaenvioMapper modModeloPlantillaenvioMapper;
	
	@Autowired
	private ModPlantilladocConsultaMapper modPlantilladocConsultaMapper;

	
	@Override
	public DatosModelosComunicacionesDTO modeloYComunicacionesSearch(HttpServletRequest request, DatosModelosComunicacionesSearch filtros) {
		
		LOGGER.info("modeloYComunicacionesSearch() -> Entrada al servicio para busqueda de modelos de comunicación");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		DatosModelosComunicacionesDTO respuesta = new DatosModelosComunicacionesDTO();
		List<ModelosComunicacionItem> modelosItem = new ArrayList<ModelosComunicacionItem>();
		
		if (null != idInstitucion) {
			
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);	
			if (null != usuarios && usuarios.size() > 0) {
				try{
					modelosItem = modModeloComunicacionExtendsMapper.selectModulosComunicacion(filtros, false);	
					if(modelosItem != null && modelosItem.size()> 0){
						respuesta.setModelosComunicacionItem(modelosItem);
					}
				}catch(Exception e){
					Error error = new Error();
					error.setCode(500);
					error.setMessage("Error al obtener los perfiles");
					error.description(e.getMessage());
					e.printStackTrace();
				}				
			}
		}
		
		
		LOGGER.info("modeloYComunicacionesSearch() -> Salida del servicio para busqueda de modelos de comunicación");
		return respuesta;
	}

	@Override
	public DatosModelosComunicacionesDTO modeloYComunicacionesHistoricoSearch(HttpServletRequest request,
			DatosModelosComunicacionesSearch filtros) {
		
		LOGGER.info("modeloYComunicacionesHistoricoSearch() -> Entrada al servicio para busqueda de modelos de comunicación con histórico");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		DatosModelosComunicacionesDTO respuesta = new DatosModelosComunicacionesDTO();
		List<ModelosComunicacionItem> modelosItem = new ArrayList<ModelosComunicacionItem>();
		
		if (null != idInstitucion) {
			
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);	
			if (null != usuarios && usuarios.size() > 0) {
				try{
					modelosItem = modModeloComunicacionExtendsMapper.selectModulosComunicacion(filtros, true);			
					if(modelosItem != null && modelosItem.size()> 0){
						respuesta.setModelosComunicacionItem(modelosItem);
					}
				}catch(Exception e){
					Error error = new Error();
					error.setCode(500);
					error.setMessage("Error al obtener los perfiles");
					error.description(e.getMessage());
					e.printStackTrace();
				}				
			}
		}
		
		
		LOGGER.info("modeloYComunicacionesHistoricoSearch() -> Salida del servicio para busqueda de modelos de comunicación con histórico");
		return respuesta;
	}


	@Override
	public Error duplicarModeloComunicaciones(HttpServletRequest request, ModelosComunicacionItem modeloComunicacion) {
		
		LOGGER.info("duplicarModeloComunicaciones() -> Entrada al servicio para duplicar un modelos de comunicación");
		
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
				
				try{
					AdmUsuarios usuario = usuarios.get(0);
					
					//Tabla mod_modelocomunicacion
					ModModelocomunicacion modelo = modModelocomunicacionMapper.selectByPrimaryKey(Long.valueOf(modeloComunicacion.getIdModeloComunicacion()));
									
					modelo.setIdmodelocomunicacion(null);
					modelo.setNombre(modelo.getNombre() + SigaConstants.SUFIJO_MODULO_COM_DUPLICADO);
					modelo.setOrden(null);
					modelo.setFechabaja(null);
					modelo.setFechamodificacion(new Date());
					modelo.setIdinstitucion(idInstitucion);
					
					modModelocomunicacionMapper.insert(modelo);
					
					//Tabla mod_modelo_perfiles
					ModModeloPerfilesExample example = new ModModeloPerfilesExample();
					example.createCriteria().andIdinstitucionEqualTo(Short.parseShort(modeloComunicacion.getIdInstitucion())).andIdmodelocomunicacionEqualTo(Long.valueOf(modeloComunicacion.getIdModeloComunicacion()));
					
					List<ModModeloPerfiles> listaModPerfiles = modModeloPerfilesMapper.selectByExample(example);
					
					if(listaModPerfiles != null){
						for(ModModeloPerfiles modPerfil : listaModPerfiles){
							modPerfil.setFechamodificacion(new Date());
							modPerfil.setIdinstitucion(idInstitucion);
							modPerfil.setIdmodelocomunicacion(modelo.getIdmodelocomunicacion());
							modModeloPerfilesMapper.insert(modPerfil);
						}
					}
					
					//Tabla mod_modelo_plantilladocumento
					ModModeloPlantilladocumentoExample examplePlantillaDoc = new ModModeloPlantilladocumentoExample();
					examplePlantillaDoc.createCriteria().andIdmodelocomunicacionEqualTo(Long.valueOf(modeloComunicacion.getIdModeloComunicacion()));
					
					List<ModModeloPlantilladocumento> listaModPlantilla = modModeloPlantilladocumentoMapper.selectByExample(examplePlantillaDoc);
					
					if(listaModPlantilla != null){
						for(ModModeloPlantilladocumento modPlantilla : listaModPlantilla){
							modPlantilla.setFechamodificacion(new Date());
							modPlantilla.setIdmodelocomunicacion(modelo.getIdmodelocomunicacion());
							modPlantilla.setIdplantilladocumento(null);
							modModeloPlantilladocumentoMapper.insert(modPlantilla);
						}
					}
					
					//Tabla mod_modelo_plantillaenvio
					ModModeloPlantillaenvioExample examplePlantillaEnvio = new ModModeloPlantillaenvioExample();
					examplePlantillaEnvio.createCriteria().andIdmodelocomunicacionEqualTo(Long.valueOf(modeloComunicacion.getIdModeloComunicacion()));
					
					List<ModModeloPlantillaenvio> listaModPlantillaEnvio = modModeloPlantillaenvioMapper.selectByExample(examplePlantillaEnvio);
					
					if(listaModPlantillaEnvio != null){
						for(ModModeloPlantillaenvio modPlantillaEnvio : listaModPlantillaEnvio){
							modPlantillaEnvio.setFechamodificacion(new Date());
							modPlantillaEnvio.setIdmodelocomunicacion(modelo.getIdmodelocomunicacion());
							modModeloPlantillaenvioMapper.insert(modPlantillaEnvio);
						}
					}
					
					//Tabla mod_plantilladoc_consulta
					ModPlantilladocConsultaExample examplePlantillaConsulta = new ModPlantilladocConsultaExample();
					examplePlantillaConsulta.createCriteria().andIdinstitucionEqualTo(Short.parseShort(modeloComunicacion.getIdInstitucion())).andIdmodelocomunicacionEqualTo(Long.valueOf(modeloComunicacion.getIdModeloComunicacion()));
					
					List<ModPlantilladocConsulta> listaModPlantillaConsulta = modPlantilladocConsultaMapper.selectByExample(examplePlantillaConsulta);
					
					if(listaModPlantillaEnvio != null){
						for(ModPlantilladocConsulta modPlantillaConsulta : listaModPlantillaConsulta){
							modPlantillaConsulta.setFechamodificacion(new Date());
							modPlantillaConsulta.setIdmodelocomunicacion(modelo.getIdmodelocomunicacion());
							modPlantillaConsulta.setIdinstitucion(idInstitucion);
							modPlantilladocConsultaMapper.insert(modPlantillaConsulta);
						}
					}
					
					respuesta.setCode(200);
					respuesta.setDescription("Datos duplicados correctamente");
					respuesta.setMessage("Updates correcto");
				}catch(Exception e){
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
				}				
			}
		}
		LOGGER.info("duplicarModeloComunicaciones() -> Salida del servicio para duplicar el modelo de comunicación");
		return respuesta;
	}


	@Override
	public Error borrarModeloComunicaciones(HttpServletRequest request, ModelosComunicacionItem modeloComunicacion) {
		LOGGER.info("borrarModeloComunicaciones() -> Entrada al servicio para borrar un módulo de comunicación");
		
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
				
				try{
					AdmUsuarios usuario = usuarios.get(0);				
					
					//Tabla mod_modelocomunicacion
					ModModelocomunicacion modelo = new ModModelocomunicacion();
					modelo.setIdmodelocomunicacion(Long.parseLong(modeloComunicacion.getIdModeloComunicacion()));
					modelo.setFechabaja(new Date());
					modelo.setFechamodificacion(new Date());
					
					modModelocomunicacionMapper.updateByPrimaryKeySelective(modelo);				
					
					respuesta.setCode(200);
					respuesta.setDescription("Datos borrados correctamente");
					respuesta.setMessage("Updates correcto");
				}catch(Exception e){
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
				}				
			}
		}
		LOGGER.info("borrarModeloComunicaciones() -> Salida del servicio para borrar un modelo de comunicación");
		return respuesta;
	}

}
