package org.itcgae.siga.com.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesDTO;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;
import org.itcgae.siga.DTOs.com.DocumentoPlantillaItem;
import org.itcgae.siga.DTOs.com.DocumentosPlantillaDTO;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.com.PlantillaDocumentoDTO;
import org.itcgae.siga.DTOs.com.PlantillaModeloBorrarDTO;
import org.itcgae.siga.DTOs.com.PlantillaModeloDocumentoDTO;
import org.itcgae.siga.DTOs.com.PlantillaModeloItem;
import org.itcgae.siga.DTOs.com.PlantillasDocumentosDTO;
import org.itcgae.siga.DTOs.com.PlantillasModeloDTO;
import org.itcgae.siga.DTOs.com.ResponseDocumentoDTO;
import org.itcgae.siga.DTOs.com.TarjetaModeloConfiguracionDTO;
import org.itcgae.siga.DTOs.com.TarjetaPerfilesDTO;
import org.itcgae.siga.DTOs.com.TarjetaPlantillaDocumentoDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.com.services.IModelosYcomunicacionesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ConConsulta;
import org.itcgae.siga.db.entities.ConConsultaKey;
import org.itcgae.siga.db.entities.EnvDocumentos;
import org.itcgae.siga.db.entities.ModModeloPerfiles;
import org.itcgae.siga.db.entities.ModModeloPerfilesExample;
import org.itcgae.siga.db.entities.ModModeloPlantilladocumento;
import org.itcgae.siga.db.entities.ModModeloPlantilladocumentoExample;
import org.itcgae.siga.db.entities.ModModeloPlantilladocumentoKey;
import org.itcgae.siga.db.entities.ModModeloPlantillaenvio;
import org.itcgae.siga.db.entities.ModModeloPlantillaenvioExample;
import org.itcgae.siga.db.entities.ModModeloPlantillaenvioKey;
import org.itcgae.siga.db.entities.ModModelocomunicacion;
import org.itcgae.siga.db.entities.ModPlantilladocConsulta;
import org.itcgae.siga.db.entities.ModPlantilladocConsultaExample;
import org.itcgae.siga.db.entities.ModPlantilladocConsultaKey;
import org.itcgae.siga.db.entities.ModPlantilladocumento;
import org.itcgae.siga.db.entities.ModPlantilladocumentoExample;
import org.itcgae.siga.db.mappers.ConConsultaMapper;
import org.itcgae.siga.db.mappers.ModModeloPerfilesMapper;
import org.itcgae.siga.db.mappers.ModModeloPlantilladocumentoMapper;
import org.itcgae.siga.db.mappers.ModModeloPlantillaenvioMapper;
import org.itcgae.siga.db.mappers.ModModelocomunicacionMapper;
import org.itcgae.siga.db.mappers.ModPlantilladocConsultaMapper;
import org.itcgae.siga.db.mappers.ModPlantilladocumentoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloComunicacionExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloPerfilesExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloPlantillaDocumentoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloPlantillaEnvioExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaDocFormatoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaDocSufijoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaDocumentoConsultaExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaDocumentoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class ModelosYcomunicacionesServiceImpl implements IModelosYcomunicacionesService{

	private Logger LOGGER = Logger.getLogger(ModelosYcomunicacionesServiceImpl.class);
	
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
	private ModPlantilladocumentoMapper modPlantilladocumentoMapper;
	
	@Autowired
	private ModModeloPlantillaenvioMapper modModeloPlantillaenvioMapper;
	
	@Autowired
	private ModPlantilladocConsultaMapper modPlantilladocConsultaMapper;
	
	@Autowired
	private ModPlantillaDocumentoConsultaExtendsMapper modPlantillaDocumentoConsultaExtendsMapper;
	
	@Autowired
	private ModModeloPerfilesExtendsMapper modModeloPerfilesExtendsMapper;
	
	@Autowired
	private ModModeloPlantillaDocumentoExtendsMapper modModeloPlantillaDocumentoExtendsMapper;
	
	@Autowired
	private ModPlantillaDocSufijoExtendsMapper modPlantillaDocSufijoExtendsMapper;
	
	@Autowired
	private ModPlantillaDocFormatoExtendsMapper modPlantillaDocFormatoExtendsMapper;
	
	@Autowired
	private ConConsultasExtendsMapper _conConsultasExtendsMapper;

	@Autowired
	private ModModeloPlantillaEnvioExtendsMapper _modModeloPlantillaEnvioExtendsMapper;
	
	@Autowired
	private ModPlantillaDocumentoExtendsMapper modPlantillaDocumentoExtendsMapper;
	
	@Autowired
	private ConConsultaMapper conConsultaMapper;
	
	@Override
	public DatosModelosComunicacionesDTO modeloYComunicacionesSearch(HttpServletRequest request, DatosModelosComunicacionesSearch filtros, boolean historico) {
		
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
					modelosItem = modModeloComunicacionExtendsMapper.selectModelosComunicacion(filtros, historico);	
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
					//Tabla mod_modelocomunicacion
					ModModelocomunicacion modelo = modModelocomunicacionMapper.selectByPrimaryKey(Long.valueOf(modeloComunicacion.getIdModeloComunicacion()));
									
					modelo.setIdmodelocomunicacion(null);
					String nuevoNombre = modelo.getNombre() + SigaConstants.SUFIJO_MODULO_COM_DUPLICADO;
					if(nuevoNombre.length() < SigaConstants.NOMBRE_MAXLENGTH){
						modelo.setNombre(nuevoNombre);
					}else{
						nuevoNombre = nuevoNombre.substring(0,SigaConstants.NOMBRE_MAXLENGTH);
						modelo.setNombre(nuevoNombre);
					}
					
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
							modPlantilla.setFechaasociacion(new Date());
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
	public Error borrarModeloComunicaciones(HttpServletRequest request, ModelosComunicacionItem[] modeloComunicacion) {
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
						
					
					for(ModelosComunicacionItem modeloCom : modeloComunicacion){
						//Tabla mod_modelocomunicacion
						ModModelocomunicacion modelo = new ModModelocomunicacion();
						modelo.setIdmodelocomunicacion(Long.parseLong(modeloCom.getIdModeloComunicacion()));
						modelo.setFechabaja(new Date());
						modelo.setFechamodificacion(new Date());
						
						modModelocomunicacionMapper.updateByPrimaryKeySelective(modelo);
					}
							
					
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

	@Override
	public ComboDTO obtenerPerfilesModelo(HttpServletRequest request, String idInstitucion, String idModeloComunicacion) {
		LOGGER.info("obtenerPerfilesModelo() -> Entrada al servicio para obtener los perfiles asignados al modelo");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucionUser = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		if (null != idInstitucionUser) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucionUser));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);	
			
			if (null != usuarios && usuarios.size() > 0) {
				try{
					comboItems = modModeloPerfilesExtendsMapper.selectPerfilesModelo(Short.parseShort(idInstitucion), Long.parseLong(idModeloComunicacion));
					if(null != comboItems && comboItems.size() > 0) {
						ComboItem element = new ComboItem();
						element.setLabel("");
						element.setValue("");
						comboItems.add(0, element);
					}		
					
					comboDTO.setCombooItems(comboItems);
				}catch(Exception e){
					Error error = new Error();
					error.setCode(500);
					error.setMessage("Error al obtener los perfiles");
					error.description(e.getMessage());
					e.printStackTrace();
				}
			}		
		}		
		
		LOGGER.info("obtenerPerfilesModelo() -> Salida del servicio para obtener los perfiles asignados al modelo");
		return comboDTO;
	}

	@Override
	public PlantillasDocumentosDTO obtenerInformes(HttpServletRequest request, String idInstitucion, String idModeloComuncacion) {
		LOGGER.info("obtenerInformes() -> Entrada al servicio para obtener los informes de un modelo de comunicación");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucionUser = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		PlantillasDocumentosDTO respuesta = new PlantillasDocumentosDTO();
		List<PlantillaModeloDocumentoDTO> plantillasItem = new ArrayList<PlantillaModeloDocumentoDTO>();
		
		if (null != idInstitucionUser) {
			
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucionUser));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);	
			if (null != usuarios && usuarios.size() > 0) {
				try{
					AdmUsuarios usuario = usuarios.get(0);
					plantillasItem = modModeloPlantillaDocumentoExtendsMapper.selectInformes(Short.parseShort(idInstitucion), Long.parseLong(idModeloComuncacion), usuario.getIdlenguaje());			
					if(plantillasItem != null && plantillasItem.size()> 0){
						respuesta.setPlantillasModeloDocumentos(plantillasItem);
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
		
		
		LOGGER.info("obtenerInformes() -> Salida del servicio para obtener los informes de un modelo de comunicación");
		return respuesta;
	}

	@Override
	public Error guardarDatosGenerales(HttpServletRequest request, TarjetaModeloConfiguracionDTO datosTarjeta) {
		LOGGER.info("guardarDatosGenerales() -> Entrada al servicio para guardar los datos generales del modelo de comunicación");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		Error respuesta = new Error();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			try{
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					if(datosTarjeta.getIdModeloComunicacion() != null){
						ModModelocomunicacion modeloCom = modModelocomunicacionMapper.selectByPrimaryKey(Long.parseLong(datosTarjeta.getIdModeloComunicacion()));
						modeloCom.setDescripcion(datosTarjeta.getDescripcion());
						modeloCom.setFechabaja(null);
						modeloCom.setFechamodificacion(new Date());
						modeloCom.setIdclasecomunicacion(Short.parseShort(datosTarjeta.getIdClaseComunicacion()));
						modeloCom.setIdinstitucion(Short.parseShort(datosTarjeta.getIdInstitucion()));
						modeloCom.setNombre(datosTarjeta.getNombre());
						modeloCom.setOrden(Short.parseShort(datosTarjeta.getOrden()));
						modeloCom.setPreseleccionar(datosTarjeta.getPreseleccionar());
						modeloCom.setUsumodificacion(usuario.getIdusuario());
						//modeloCom.setVisible(visible);
						
						modModelocomunicacionMapper.updateByPrimaryKey(modeloCom);
					}else{
						ModModelocomunicacion modeloCom = new ModModelocomunicacion();
						modeloCom.setDescripcion(datosTarjeta.getDescripcion());
						modeloCom.setFechabaja(null);
						modeloCom.setFechamodificacion(new Date());
						modeloCom.setIdclasecomunicacion(Short.parseShort(datosTarjeta.getIdClaseComunicacion()));
						modeloCom.setIdinstitucion(Short.parseShort(datosTarjeta.getIdInstitucion()));
						modeloCom.setNombre(datosTarjeta.getNombre());
						modeloCom.setOrden(Short.parseShort(datosTarjeta.getOrden()));
						modeloCom.setPreseleccionar(datosTarjeta.getPreseleccionar());
						modeloCom.setUsumodificacion(usuario.getIdusuario());
						//modeloCom.setVisible(visible);
						
						modModelocomunicacionMapper.insert(modeloCom);
					}
					respuesta.setCode(200);
					respuesta.setMessage("Datos generales guardados correctamente");

				}
			}catch(Exception e){
				respuesta.setCode(500);
				respuesta.setDescription("Error al guardar datos generales");
				respuesta.setMessage(e.getMessage());
			}
		
		}
		LOGGER.info("guardarDatosGenerales() -> Salida del servicio para guardar los datos generales del modelo de comunicación");
		return respuesta;
	}
	
	@Override
	public Error guardarPerfilesModelo(HttpServletRequest request, TarjetaPerfilesDTO perfilesDTO) {
		
		LOGGER.info("guardarPerfilesModelo() -> Entrada al servicio para guardar datos perfiles");
		
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
					
					//borramos las etiquetas no seleccionadas
					for (int i = 0; i < perfilesDTO.getPerfilesNoSeleccionados().length; i++) {
						ModModeloPerfilesExample example = new ModModeloPerfilesExample();
						example.createCriteria().andIdmodelocomunicacionEqualTo(Long.valueOf(perfilesDTO.getIdModeloComunicacion())).andIdperfilEqualTo(perfilesDTO.getPerfilesNoSeleccionados()[i]);
						modModeloPerfilesMapper.deleteByExample(example);
					}
					
					//añadimos las etiquetas seleccionadas
					for (int i = 0; i < perfilesDTO.getPerfilesSeleccionados().length; i++) {
						ModModeloPerfiles perfil = new ModModeloPerfiles();
						perfil.setIdmodelocomunicacion(Long.valueOf(perfilesDTO.getIdModeloComunicacion()));
						perfil.setFechamodificacion(new Date());
						perfil.setIdperfil(perfilesDTO.getPerfilesSeleccionados()[i]);
						perfil.setUsumodificacion(usuario.getIdusuario());
						perfil.setIdinstitucion(idInstitucion);
						modModeloPerfilesMapper.insert(perfil);
					}
					
					
					respuesta.setCode(200);
					respuesta.setDescription("Datos perfiles del modelo guardados correctamente");
					respuesta.setMessage("Updates correcto");
				}catch(Exception e){
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
				}
				
				
			}
		}
		LOGGER.info("guardarPerfilesModelo() -> Salida del servicio para guardar datos perfiles");
		return respuesta;
	}

	@Override
	public Error guardarModPlantillaDocumento(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc) {
		LOGGER.info("guardarModPlantillaDocumento() -> Entrada al servicio para guardar los datos de la plantilla de documento");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		Error respuesta = new Error();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			try{
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					
					if(plantillaDoc.getIdModeloComunicacion() != null){
						if(plantillaDoc.getPlantillas() != null && plantillaDoc.getPlantillas().size() > 0){
							Long idInforme = (long) 0;
							if(plantillaDoc.getIdInforme()!=null && !"".equals(plantillaDoc.getIdInforme())){
								LOGGER.debug("El informe ya está asociado");
							}else{
								idInforme = modModeloPlantillaDocumentoExtendsMapper.selectMaxInforme(Short.parseShort(plantillaDoc.getIdInstitucion()), Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
								idInforme = idInforme + (long)1;
							}	
							
							for(DocumentoPlantillaItem idPlantillaDoc : plantillaDoc.getPlantillas()){
								
								ModPlantilladocumento modPlantillaDoc = modPlantilladocumentoMapper.selectByPrimaryKey(Long.parseLong(idPlantillaDoc.getIdPlantillaDocumento()));
								
								if(modPlantillaDoc != null){
									ModModeloPlantilladocumentoKey modModeloPlantillaDocKey = new ModModeloPlantilladocumentoKey();
									modModeloPlantillaDocKey.setIdmodelocomunicacion(Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
									modModeloPlantillaDocKey.setIdplantilladocumento(modPlantillaDoc.getIdplantilladocumento());
									
									ModModeloPlantilladocumento modModeloPlantillaDoc = modModeloPlantilladocumentoMapper.selectByPrimaryKey(modModeloPlantillaDocKey);
									
									if(modModeloPlantillaDoc != null){
										modModeloPlantillaDoc.setFechamodificacion(new Date());
										modModeloPlantillaDoc.setFormatosalida(plantillaDoc.getFormatoSalida());
										modModeloPlantillaDoc.setNombreficherosalida(plantillaDoc.getFicheroSalida());
										modModeloPlantillaDoc.setSufijo(plantillaDoc.getSufijo());
										modModeloPlantillaDoc.setUsumodificacion(usuario.getIdusuario());
										modModeloPlantillaDoc.setIdplantilladocumento(modPlantillaDoc.getIdplantilladocumento());		
										modModeloPlantillaDoc.setIdinforme(idInforme);
										modModeloPlantilladocumentoMapper.updateByPrimaryKey(modModeloPlantillaDoc);
									}else{
										modModeloPlantillaDoc = new ModModeloPlantilladocumento();
										modModeloPlantillaDoc.setFechamodificacion(new Date());
										modModeloPlantillaDoc.setFormatosalida(plantillaDoc.getFormatoSalida());
										modModeloPlantillaDoc.setNombreficherosalida(plantillaDoc.getFicheroSalida());
										modModeloPlantillaDoc.setSufijo(plantillaDoc.getSufijo());
										modModeloPlantillaDoc.setUsumodificacion(usuario.getIdusuario());
										modModeloPlantillaDoc.setIdplantilladocumento(modPlantillaDoc.getIdplantilladocumento());
										modModeloPlantillaDoc.setIdmodelocomunicacion(Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
										modModeloPlantillaDoc.setFechaasociacion(new Date());
										modModeloPlantillaDoc.setIdinforme(idInforme);
										
										modModeloPlantilladocumentoMapper.insert(modModeloPlantillaDoc);
									}
								}		
								
							}
						}
					}else{
						respuesta.setCode(500);
						respuesta.setDescription("Error al guardar la plantilla de documento");
						respuesta.setMessage("Error al guardar la plantilla de documento");
					}
					
					respuesta.setCode(200);
					respuesta.setMessage("Datos plantilla de documento guardados correctamente");

				}
			}catch(Exception e){
				respuesta.setCode(500);
				respuesta.setDescription("Error al guardar datos generales");
				respuesta.setMessage(e.getMessage());
			}
		
		}
		LOGGER.info("guardarModPlantillaDocumento() -> Salida del servicio para guardar los datos de la plantilla de documento");
		return respuesta;
	}	

	@Override
	public ComboDTO obtenerFormatoSalida(HttpServletRequest request) {
		LOGGER.info("obtenerFormatoSalida() -> Entrada al servicio para obtener combo formatos de salida");
		
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
				comboItems = modPlantillaDocFormatoExtendsMapper.selectFormatos(usuario.getIdlenguaje());
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
				comboDTO.setCombooItems(comboItems);
				
			}
		}
		
		
		LOGGER.info("obtenerFormatoSalida() -> Salida del servicio para obtener combo formatos de salida");
		
		
		return comboDTO;
	}

	@Override
	public ComboDTO obtenerSufijos(HttpServletRequest request) {
		LOGGER.info("obtenerSufijos() -> Entrada al servicio para obtener combo sufijos");
		
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
				comboItems = modPlantillaDocSufijoExtendsMapper.selectSufijos(usuario.getIdlenguaje());
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
				comboDTO.setCombooItems(comboItems);
				
			}
		}
		
		
		LOGGER.info("obtenerSufijos() -> Salida del servicio para obtener combo sufijos");
		
		
		return comboDTO;
	}
	
	@Override
	public ConsultasDTO obtenerConsultasPlantilla(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc, boolean historico) {
		LOGGER.info("obtenerConsultasPlantilla() -> Entrada al servicio para obtener las consultas de una plantilla de documento");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucionUser = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		ConsultasDTO respuesta = new ConsultasDTO();
		List<ConsultaItem> listaConsultaItem = new ArrayList<ConsultaItem>();
		
		if (null != idInstitucionUser) {
			
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucionUser));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);	
			if (null != usuarios && usuarios.size() > 0) {
				try{
					listaConsultaItem = modPlantillaDocumentoConsultaExtendsMapper.selectPlantillaDocConsultas(Short.parseShort(plantillaDoc.getIdInstitucion()), Long.parseLong(plantillaDoc.getIdModeloComunicacion()), Long.parseLong(plantillaDoc.getIdPlantillaDocumento()), historico);			
					if(listaConsultaItem != null && listaConsultaItem.size()> 0){
						respuesta.setConsultaItem(listaConsultaItem);
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
		
		
		LOGGER.info("obtenerConsultasPlantilla() -> Salida del servicio para obtener las consultas de una plantilla de documento");
		return respuesta;
	}

	@Override
	public ComboDTO obtenerConsultasDisponibles(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc) {
		LOGGER.info("obtenerConsultasDisponibles() -> Entrada al servicio para obtener las disponibles para la clase y la institucion");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucionUser = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		if (null != idInstitucionUser) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucionUser));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);	
			
			if (null != usuarios && usuarios.size() > 0) {
				try{
					Long idClaseComunicacion = null;
					if(plantillaDoc.getIdClaseComunicacion() != null){
						idClaseComunicacion = Long.parseLong(plantillaDoc.getIdClaseComunicacion());
					}
					comboItems = _conConsultasExtendsMapper.selectConsultasDisponibles(Short.parseShort(plantillaDoc.getIdInstitucion()), idClaseComunicacion);
					if(null != comboItems && comboItems.size() > 0) {
						ComboItem element = new ComboItem();
						element.setLabel("");
						element.setValue("");
						comboItems.add(0, element);
					}		
					
					comboDTO.setCombooItems(comboItems);
				}catch(Exception e){
					Error error = new Error();
					error.setCode(500);
					error.setMessage("Error al obtener los perfiles");
					error.description(e.getMessage());
					e.printStackTrace();
				}
			}		
		}		
		
		LOGGER.info("obtenerConsultasDisponibles() -> Salida del servicio para obtener las disponibles para la clase y la institucion");
		return comboDTO;
	}
	@Override
	public PlantillasModeloDTO obtenerPlantillasEnviosModeloSearch(HttpServletRequest request, String idModelo) {
		LOGGER.info("obtenerPlantillasModelo() -> Entrada al servicio para obtener plantillas del modelo");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		PlantillasModeloDTO respuesta = new PlantillasModeloDTO();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try{
					List<PlantillaModeloItem> plantillas = _modModeloPlantillaEnvioExtendsMapper.getPlantillasModelo(idModelo, idInstitucion, usuario.getIdlenguaje());
					if(plantillas != null && plantillas.size() > 0){
						respuesta.setPlantillas(plantillas);
					}
					
				}catch(Exception e){
					Error error = new Error();
					error.setCode(500);
					error.setDescription(e.getMessage());
					error.setMessage("Error");
					respuesta.setError(error);
				}
			}
		}
		
		LOGGER.info("obtenerPlantillasModelo() -> Salida del servicio para obtener plantillas del modelo");
		return respuesta;
	}
	
	@Override
	public PlantillasModeloDTO obtenerPlantillasEnviosModeloSearchHist(HttpServletRequest request, String idModelo) {
		LOGGER.info("obtenerPlantillasModeloHist() -> Entrada al servicio para obtener plantillas del modelo HIST");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		PlantillasModeloDTO respuesta = new PlantillasModeloDTO();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try{
					List<PlantillaModeloItem> plantillas = _modModeloPlantillaEnvioExtendsMapper.getPlantillasModeloHist(idModelo, idInstitucion, usuario.getIdlenguaje());
					if(plantillas != null && plantillas.size() > 0){
						respuesta.setPlantillas(plantillas);
					}
					
				}catch(Exception e){
					Error error = new Error();
					error.setCode(500);
					error.setDescription(e.getMessage());
					error.setMessage("Error");
					respuesta.setError(error);
				}
			}
		}
		
		LOGGER.info("obtenerPlantillasModeloHist() -> Salida del servicio para obtener plantillas del modelo HIST");
		return respuesta;
	}

	@Override
	public Error borrarPlantillaEnviosModelo(HttpServletRequest request, PlantillaModeloBorrarDTO[] plantillas) {
		LOGGER.info("borrarPlantillaModelo() -> Entrada al servicio para obtener plantillas del modelo");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		

		Error respuesta = new Error();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try{
					for (int i = 0; i < plantillas.length; i++) {
						ModModeloPlantillaenvioKey key = new ModModeloPlantillaenvioKey();
						key.setIdmodelocomunicacion(Long.valueOf(plantillas[i].getIdModelo()));
						key.setIdplantillaenvios(Short.valueOf(plantillas[i].getIdPlantillaEnvios()));
						ModModeloPlantillaenvio plantilla = modModeloPlantillaenvioMapper.selectByPrimaryKey(key);
						plantilla.setFechabaja(new Date());
						plantilla.setFechamodificacion(new Date());
						plantilla.setUsumodificacion(usuario.getIdusuario());
						modModeloPlantillaenvioMapper.updateByPrimaryKey(plantilla);
					}
					respuesta.setCode(200);
					respuesta.setMessage("Plantillas borradas");
				}catch(Exception e){
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error al borrar plantillas");
					e.printStackTrace();
				}
			}
		}
		LOGGER.info("borrarPlantillaModelo() -> Salida del servicio para obtener plantillas del modelo");
		return respuesta;
	}

	@Override
	public Error guardarPlantillaEnviosModelo(HttpServletRequest request, PlantillaModeloBorrarDTO datosPlantilla) {
		LOGGER.info("guardarPlantillaModelo() -> Entrada al servicio para guardar la plantilla del modelo");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		Error respuesta = new Error();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try{
					
					if(datosPlantilla.getPorDefecto().equals("Si") || datosPlantilla.getPorDefecto().equals("SI")){
						ModModeloPlantillaenvioExample example = new ModModeloPlantillaenvioExample();
						example.createCriteria().andIdmodelocomunicacionEqualTo(Long.valueOf(datosPlantilla.getIdModelo()));
						List<ModModeloPlantillaenvio> plantillas = modModeloPlantillaenvioMapper.selectByExample(example);
						for (int i = 0; i < plantillas.size() ; i++) {
							ModModeloPlantillaenvioKey key = new ModModeloPlantillaenvioKey();
							key.setIdmodelocomunicacion(Long.valueOf(plantillas.get(0).getIdmodelocomunicacion()));
							key.setIdplantillaenvios(Short.valueOf(plantillas.get(0).getIdplantillaenvios()));
							key.setIdinstitucion(idInstitucion);
							ModModeloPlantillaenvio plantillaMod = new ModModeloPlantillaenvio();
							plantillaMod.setPordefecto("No");
							modModeloPlantillaenvioMapper.updateByPrimaryKey(plantillaMod);
						}
					}
					ModModeloPlantillaenvio plantilla = new ModModeloPlantillaenvio();
					plantilla.setIdmodelocomunicacion(Long.valueOf(datosPlantilla.getIdModelo()));
					plantilla.setIdplantillaenvios(Short.valueOf(datosPlantilla.getIdPlantillaEnvios()));
					plantilla.setIdinstitucion(Short.valueOf(datosPlantilla.getIdInstitucion()));
					plantilla.setPordefecto(datosPlantilla.getPorDefecto());
					plantilla.setUsumodificacion(usuario.getIdusuario());
					plantilla.setFechamodificacion(new Date());
					modModeloPlantillaenvioMapper.insert(plantilla);
					respuesta.setCode(200);
					respuesta.setDescription("Plantilla guardada");
				}catch(Exception e){
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error al guardar plantilla");
				}
			}
		}
		
		LOGGER.info("guardarPlantillaModelo() -> Salida del servicio para guardar la plantilla del modelo");
		return respuesta;
	}


	@Override
	public Error guardarConsultasPlantilla(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc) {
		LOGGER.info("guardarConsultasPlantilla() -> Entrada al servicio para guardar las consultas de la plantilla");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		Error respuesta = new Error();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				boolean consultasValidas = false;
				try{		
					
					//Obtenemos las consultas asignadas a la plantilla del documento
					// Comprobamos las consultas
					List<ConsultaItem> listaItems = plantillaDoc.getConsultas();
					if(listaItems != null){						
						
						int consultaDatos = 0;
						int consultaDestinatario = 0;
						int consultaMultidocumento = 0;
						int consultaCondicion = 0;

						for(ConsultaItem consultaItem : listaItems){
							if(Long.parseLong(consultaItem.getIdObjetivo()) == SigaConstants.OBJETIVO.DESTINATARIOS.getCodigo()){
								consultaDestinatario++;
							}else if(Long.parseLong(consultaItem.getIdObjetivo()) == SigaConstants.OBJETIVO.MULTIDOCUMENTO.getCodigo()){
								consultaMultidocumento++;
							}else if(Long.parseLong(consultaItem.getIdObjetivo()) == SigaConstants.OBJETIVO.CONDICIONAL.getCodigo()){
								consultaCondicion++;
							}else if(Long.parseLong(consultaItem.getIdObjetivo()) == SigaConstants.OBJETIVO.DATOS.getCodigo()){
								consultaDatos++;
							}
						}
						
						consultasValidas = true;
						
						if(consultaDestinatario != 1){
							consultasValidas = false;
						}
						if(consultaMultidocumento > 1){
							consultasValidas = false;
						}
						if(consultaCondicion !=1){
							consultasValidas = false;
						}
					}
					
					if(listaItems != null && consultasValidas && plantillaDoc.getIdInforme() != null){
						// Por cada plantilla asociada hay que guardar sus consultas
						ModModeloPlantilladocumentoExample modModeloPlantillaExample = new ModModeloPlantilladocumentoExample();
						modModeloPlantillaExample.createCriteria().andIdinformeEqualTo(Long.parseLong(plantillaDoc.getIdInforme()));
						List<ModModeloPlantilladocumento> listaPlantillaDoc = modModeloPlantilladocumentoMapper.selectByExample(modModeloPlantillaExample);
						
						for(ModModeloPlantilladocumento modPlantilla : listaPlantillaDoc){
							for(ConsultaItem consultaItem : listaItems){								
								// Comprobamos si la consulta ya está asignada a la plantilla
								ModPlantilladocConsultaExample consultaPlantillaExample = new ModPlantilladocConsultaExample();
								consultaPlantillaExample.createCriteria().andIdconsultaEqualTo(Long.parseLong(consultaItem.getIdConsultaAnterior())).andIdmodelocomunicacionEqualTo(Long.parseLong(plantillaDoc.getIdModeloComunicacion()))
													.andIdinstitucionEqualTo(Short.parseShort(plantillaDoc.getIdInstitucion())).andIdplantilladocumentoEqualTo(modPlantilla.getIdplantilladocumento());
								
								List<ModPlantilladocConsulta> listaPlantillaModificar = modPlantilladocConsultaMapper.selectByExample(consultaPlantillaExample);
								ModPlantilladocConsulta consultaPlantillaModificar = null;
								if(listaPlantillaModificar != null && listaPlantillaModificar.size() == 1){
									consultaPlantillaModificar = listaPlantillaModificar.get(0);
								}
								
								if(consultaPlantillaModificar != null){
									consultaPlantillaModificar.setFechabaja(new Date());										
									modPlantilladocConsultaMapper.updateByPrimaryKey(consultaPlantillaModificar);
								}

								consultaPlantillaModificar = new ModPlantilladocConsulta();
								consultaPlantillaModificar.setIdinstitucion(Short.parseShort(consultaItem.getIdInstitucion()));
								consultaPlantillaModificar.setIdmodelocomunicacion(Long.parseLong(consultaItem.getIdModeloComunicacion()));
								consultaPlantillaModificar.setIdplantilladocumento(Long.parseLong(consultaItem.getIdPlantillaDocumento()));
								consultaPlantillaModificar.setIdconsulta(Long.parseLong(consultaItem.getIdConsulta()));
								consultaPlantillaModificar.setFechabaja(null);
								consultaPlantillaModificar.setUsumodificacion(usuario.getIdusuario());
								consultaPlantillaModificar.setFechamodificacion(new Date());
								
								modPlantilladocConsultaMapper.insert(consultaPlantillaModificar);

							}
						}
						
						respuesta.setCode(200);
						respuesta.setDescription("Consultas guardadas");
						
					}else{
						respuesta.setCode(500);
						respuesta.setDescription("Consultas no válidas");
						respuesta.setMessage("Error al guardar las consultas");
					}					
				}catch(Exception e){
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error al guardar la consulta");
				}
			}
		}
		
		LOGGER.info("guardarConsultasPlantilla() -> Entrada al servicio para guardar las consultas de la plantilla");
		return respuesta;
	}


	@Override
	public PlantillasDocumentosDTO guardarInformes(HttpServletRequest request,
			TarjetaPlantillaDocumentoDTO plantillaDoc) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseDocumentoDTO uploadFile(MultipartHttpServletRequest request) {
		LOGGER.info("uploadFile() -> Entrada al servicio para subir una plantilla de documento");
		
		ResponseDocumentoDTO response = new ResponseDocumentoDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			if (null != usuarios && usuarios.size() > 0) {
				
				
				// crear path para almacenar el fichero
				String pathFichero = "/FILERMSA1000/SIGA/ficheros/archivo/" + String.valueOf(idInstitucion) + "/plantillaDocumentos/";
				
				// 1. Coger archivo del request
				LOGGER.debug("uploadFile() -> Coger documento de cuenta bancaria del request");
				Iterator<String> itr = request.getFileNames();
				MultipartFile file = request.getFile(itr.next());
				String fileName = file.getOriginalFilename();
				String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
				//BufferedOutputStream stream = null;
				try {
					File aux = new File(pathFichero);
					// creo directorio si no existe
					aux.mkdirs();
					File serverFile = new File(pathFichero, fileName);
					//stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					//stream.write(file.getBytes());
					FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
					response.setNombreDocumento(fileName);
					response.setRutaDocumento(pathFichero + fileName);
				} catch (FileNotFoundException e) {
					Error error = new Error();
					error.setCode(500);
					error.setDescription(e.getMessage());
					response.setError(error);
					e.printStackTrace();
					LOGGER.error("uploadFile() -> Error al buscar la plantilla de documento en el directorio indicado",e);
				} catch (IOException ioe) {
					Error error = new Error();
					error.setCode(500);
					error.setDescription(ioe.getMessage());
					response.setError(error);
					ioe.printStackTrace();
					LOGGER.error("uploadFile() -> Error al guardar la plantilla de documento en el directorio indicado",ioe);
				} finally {
					// close the stream
					LOGGER.debug("uploadFile() -> Cierre del stream del documento");
					//stream.close();
				}
			}
		}

		
		LOGGER.info("uploadFile() -> Salida del servicio para subir una plantilla de documento");
		return response;
	}
	
	
	@Override
	public ResponseDocumentoDTO guardarPlantillaDocumento(HttpServletRequest request, DocumentoPlantillaItem documento) {
		LOGGER.info("guardarPlantillaDocumento() -> Entrada al servicio para guardar la plantilla de documento");

		Error error = new Error();
		ResponseDocumentoDTO response = new ResponseDocumentoDTO();
		
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
					ModPlantilladocumento modPlantillaDoc = new ModPlantilladocumento();
					
					modPlantillaDoc.setFechamodificacion(new Date());
					modPlantillaDoc.setIdioma(documento.getIdioma());
					modPlantillaDoc.setUsumodificacion(usuario.getIdusuario());
					modPlantillaDoc.setPlantilla(documento.getNombreDocumento());
					modPlantilladocumentoMapper.insert(modPlantillaDoc);
					
					response.setIdioma(documento.getIdioma());
					response.setNombreDocumento(documento.getNombreDocumento());
					response.setRutaDocumento(documento.getNombreDocumento());
					response.setIdPlantillaDocumento(String.valueOf(modPlantillaDoc.getIdplantilladocumento()));
				}catch(Exception e){
					error.setCode(500);
					error.setDescription(e.getMessage());
					error.setMessage("Error al insertar documento");
					documento.setError(error);
				}
				
			}
		}
		LOGGER.info("guardarPlantillaDocumento() -> Salida del servicio para guardar la plantilla de documento");
		return response;
	}


	@Override
	public DocumentosPlantillaDTO obtenerPlantillasInforme(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc) {
		LOGGER.info("obtenerPlantilla() -> Entrada al servicio para obtener las plantillas asociadas a un informe");

		Error error = new Error();
		List<DocumentoPlantillaItem> items = new ArrayList<DocumentoPlantillaItem>();
		DocumentosPlantillaDTO response = new DocumentosPlantillaDTO();
		
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
					items = modPlantillaDocumentoExtendsMapper.selectPlantillasByInforme(Long.parseLong(plantillaDoc.getIdInforme()), Long.parseLong(plantillaDoc.getIdModeloComunicacion()));
					
					response.setDocumentoPlantillaItem(items);
				}catch(Exception e){
					error.setCode(500);
					error.setDescription(e.getMessage());
					error.setMessage("Error al obtener las plantillas");
				}
				
			}
		}
		LOGGER.info("obtenerPlantillasInforme() -> Salida del servicio las plantillas asociadas a un informe");
		return response;
	}	
}
