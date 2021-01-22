package org.itcgae.siga.adm.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.EntidadLenguajeInstitucionDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.adm.service.IGestionEntidadService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmGestorinterfaz;
import org.itcgae.siga.db.entities.AdmGestorinterfazExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.AdmGestorinterfazMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenDiccionarioExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class GestionEntidadServiceImpl implements IGestionEntidadService{

	private Logger LOGGER = Logger.getLogger(GestionEntidadServiceImpl.class);
	
	@Autowired
	private GenDiccionarioExtendsMapper genDiccionarioExtendsMapper;
	
	@Autowired
	private CenInstitucionExtendsMapper cenInstitucionExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private GenPropertiesMapper genPropertiesMapper;
	
	@Autowired
	private AdmGestorinterfazMapper admGestorinterfazMapper;
	
	@Override
	public EntidadLenguajeInstitucionDTO getInstitutionLenguage(HttpServletRequest request) {
		LOGGER.info("getInstitutionLenguage() -> Entrada al servicio para obtener idiomas de la institución");
		EntidadLenguajeInstitucionDTO entidadLenguajeInstitucionDTO = new EntidadLenguajeInstitucionDTO();
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(null != idInstitucion) {
			LOGGER.info("getInstitutionLenguage() / cenInstitucionExtendsMapper.getInstitutionLenguage() -> Entrada a cenInstitucionExtendsMapper para obtener idiomas de la institución");
			entidadLenguajeInstitucionDTO = cenInstitucionExtendsMapper.getInstitutionLenguage(String.valueOf(idInstitucion));
			LOGGER.info("getInstitutionLenguage() / cenInstitucionExtendsMapper.getInstitutionLenguage() -> Salida de cenInstitucionExtendsMapper para obtener idiomas de la institución");
		}
		else {
			LOGGER.warn("getInstitutionLenguage() -> No existe la institución para el token");
		}
		
		LOGGER.info("getInstitutionLenguage() -> Salida del servicio para obtener idiomas de la institución");
		return entidadLenguajeInstitucionDTO;
	}

	@Override
	public ComboDTO getLenguages(HttpServletRequest request) {
		LOGGER.info("getLenguages() -> Entrada al servicio para obtener idiomas de la aplicación");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> combooItems = new ArrayList<ComboItem>();
		ComboItem comboItem = new ComboItem();
		
		LOGGER.info("getLenguages() / genDiccionarioExtendsMapper.getLabelLenguage() -> Entrada a genDiccionarioExtendsMapper para obtener lista de idiomas disponibles en la aplicación");
		combooItems = genDiccionarioExtendsMapper.getLabelLenguage();
		LOGGER.info("getLenguages() / genDiccionarioExtendsMapper.getLabelLenguage() -> Salida de genDiccionarioExtendsMapper para obtener lista de idiomas disponibles en la aplicación");
		// primer elemento a vacio para componente de pantalla dropdown
		if(null != combooItems && combooItems.size() > 0) {
//			comboItem.setLabel("");
//			comboItem.setValue("");
//			combooItems.add(0,comboItem);
			comboDTO.setCombooItems(combooItems);
		}
		else {
			LOGGER.warn("getLenguages() / genDiccionarioExtendsMapper.getLabelLenguage() -> No hay idiomas disponibles en la aplicación");
		}
		
		
		LOGGER.info("getLenguages() -> Salida del servicio para obtener idiomas de la aplicación");
		return comboDTO;
	}

	@Override
	public UpdateResponseDTO uploadFile(MultipartHttpServletRequest request) throws IOException {
		LOGGER.info("uploadFile() -> Entrada al servicio para guardar el logotipo de la institución");
		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		// Obtenemos datos del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		LOGGER.info("uploadFile() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info("uploadFile() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		AdmUsuarios usuario = usuarios.get(0);
		
		// Obtenemos path imagenes para almacenar imagen de logo
		LOGGER.debug("uploadFile() -> Obtener path para almacenar imagen de logotipo");
		GenPropertiesExample genPropertiesExample = new GenPropertiesExample();
		genPropertiesExample.createCriteria().andParametroEqualTo("directorios.carpeta.logos");
		LOGGER.info("uploadFile() / genPropertiesMapper.selectByExample() -> Entrada a genPropertiesMapper para obtener ruta para almacenar imagen de logotipo");
		List<GenProperties> properties = genPropertiesMapper.selectByExample(genPropertiesExample);
		LOGGER.info("uploadFile() / genPropertiesMapper.selectByExample() -> Salida de genPropertiesMapper para obtener ruta para almacenar imagen de logotipo");
		
		
		if(null != properties && properties.size() > 0) {
			String pathImagenes = properties.get(0).getValor() + "/";
			
			// Coger archivo del request
			LOGGER.debug("uploadFile() -> Coger imagen del request");
			Iterator<String> itr = request.getFileNames();
			MultipartFile file = request.getFile(itr.next());
			String fileName = file.getOriginalFilename();
			String extension = fileName.substring(fileName.lastIndexOf("."),fileName.length());
			
			if (extension==null || extension.trim().equals("")
	                || (!extension.trim().toUpperCase().equals(".JPG")
	                   && !extension.trim().toUpperCase().equals(".GIF")
	                   && !extension.trim().toUpperCase().equals(".PNG")
	                         && !extension.trim().toUpperCase().equals(".JPEG"))) {
	      
				// posiblemente hay que cambiarlo
				try {
					throw new SigaExceptions("messages.error.imagen.tipoNoCorrecto");
				} catch (SigaExceptions e) {
					e.printStackTrace();
				}
			}
			
			
			
			// Crear nombre del archivo a guardar
			LOGGER.debug("uploadFile() -> Crear nombre de la imagen a guardar");
			Random aleatorio = new Random();
			fileName = idInstitucion + "_" + aleatorio.nextInt() + "_" + fileName;

			
		
			BufferedOutputStream stream = null;
			// Guardar el archivo
			LOGGER.debug("uploadFile() -> Guardar el archivo");
			try {
				File aux = new File(pathImagenes);
				// creo directorio si no existe
				aux.mkdirs();
				File serverFile = new File(pathImagenes, fileName);
				stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(file.getBytes());

			} catch (FileNotFoundException e) {
				LOGGER.error("uploadFile() -> Error al buscar la imagen del logotipo en el directorio indicado", e);
			} catch (IOException ioe) {
				LOGGER.error("uploadFile() -> Error al guardar la imagen del logotipo en el directorio indicado", ioe);
			} finally {
				// close the stream
				LOGGER.debug("uploadFile() -> Cierre del stream de la imagen del logotipo");
				stream.close();
			}
			
			
			// actualizar en base de datos tabla adm_gestor_interfaz
			AdmGestorinterfazExample admGestorinterfazExample = new AdmGestorinterfazExample();
			admGestorinterfazExample.createCriteria().andAdmGestorinterfazIdEqualTo(Long.valueOf(idInstitucion));
			LOGGER.info("uploadFile() / admGestorinterfazMapper.selectByExample() -> Entrada a admGestorinterfazMapper para comprobar si existe registro del logotipo para la institución actual");
			List<AdmGestorinterfaz> listAdmGestorInterfaz = admGestorinterfazMapper.selectByExample(admGestorinterfazExample);
			LOGGER.info("uploadFile() / admGestorinterfazMapper.selectByExample() -> Salida de admGestorinterfazMapper para comprobar si existe registro del logotipo para la institución actual");
			
			// si vacia => insert into
			if(listAdmGestorInterfaz.isEmpty()) {
				
				AdmGestorinterfaz admGestorinterfazInsertInto = new AdmGestorinterfaz();
				admGestorinterfazInsertInto.setAdmGestorinterfazId(Long.valueOf(idInstitucion));
				admGestorinterfazInsertInto.setColor("0");
				admGestorinterfazInsertInto.setFechamodificacion(new Date());
				admGestorinterfazInsertInto.setLogo(fileName);
				admGestorinterfazInsertInto.setTipoletra("1");
				admGestorinterfazInsertInto.setUsumodificacion(usuario.getIdusuario());
				LOGGER.info("uploadFile() / admGestorinterfazMapper.insertSelective() -> Entrada a admGestorinterfazMapper para insertar registro para la imagen de logotipo");
				response = admGestorinterfazMapper.insertSelective(admGestorinterfazInsertInto);
				
				LOGGER.info("uploadFile() / admGestorinterfazMapper.insertSelective() -> Salida de admGestorinterfazMapper para insertar registro para la imagen de logotipo");
			}
			// si no vacía => update
			else {
				AdmGestorinterfaz admGestorinterfaz = new AdmGestorinterfaz();
				admGestorinterfaz.setLogo(fileName);
				admGestorinterfaz.setFechamodificacion(new Date());
				admGestorinterfaz.setUsumodificacion(usuario.getIdusuario());
				LOGGER.info("uploadFile() / admGestorinterfazMapper.updateByExampleSelective() -> Entrada a admGestorinterfazMapper para actualizar registro para la imagen de logotipo");
				response = admGestorinterfazMapper.updateByExampleSelective(admGestorinterfaz, admGestorinterfazExample);
				LOGGER.info("uploadFile() / admGestorinterfazMapper.updateByExampleSelective() -> Salida de admGestorinterfazMapper para actualizar registro para la imagen de logotipo");
			}
		}
		else {
			LOGGER.warn("uploadFile() / genPropertiesMapper.selectByExample() -> Ruta para almacenar imagen de logotipo NO encontrada");
		}
		
        
		
		// comprobar si ha actualizado en bd correctamente
		if(response == 1) {
			updateResponseDTO.setStatus(SigaConstants.OK);
			LOGGER.info("uploadFile() / admGestorinterfazMapper.updateByExampleSelective()-> OK. Imagen de logotipo guardada correctamente en base de datos");
		}
			
		else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("uploadFile() / admGestorinterfazMapper.updateByExampleSelective()-> KO. Imagen de logotipo NO guardada correctamente en base de datos");
		}
		
		LOGGER.info("uploadFile() -> Salida del servicio para guardar el logotipo de la institución");
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO uploadLenguage(String idLenguaje, HttpServletRequest request) {
		LOGGER.info("uploadFile() -> Entrada al servicio para actualizar el lenguaje de la entidad");
		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		// Como el idLenguage viene en JSON, le quitamos ""
		idLenguaje = idLenguaje.substring(1,idLenguaje.length()-1);
		// Obtenemos atributos del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		LOGGER.info("uploadLenguage() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info("uploadLenguage() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		
		
		// Creamos objetos para consulta en bbdd
		if(null != idInstitucion && null != usuarios && usuarios.size() > 0) {
			AdmUsuarios usuario = usuarios.get(0);
			CenInstitucion record = new CenInstitucion();
			record.setIdlenguaje(idLenguaje);
			record.setFechamodificacion(new Date());
			record.setUsumodificacion(usuario.getIdusuario());
			
			CenInstitucionExample example = new CenInstitucionExample();
			example.createCriteria().andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			
			// actualizamos idlenguaje, fecha modificacion y usuario de modificacion para cen_institucion
			LOGGER.info("uploadLenguage() / cenInstitucionExtendsMapper.updateByExampleSelective() -> Entrada a cenInstitucionExtendsMapper para actualizar cen_institucion");
			response = cenInstitucionExtendsMapper.updateByExampleSelective(record, example);
			LOGGER.info("uploadLenguage() / cenInstitucionExtendsMapper.updateByExampleSelective() -> Salida de cenInstitucionExtendsMapper para actualizar cen_institucion");
		}
		else {
			LOGGER.info("uploadLenguage() / admUsuariosExtendsMapper.selectByExample() -> No se ha podido obtener información del usuario logeado");
		}
		
		// respuesta si se actualiza correctamente
		if(response == 1) {
			updateResponseDTO.setStatus(SigaConstants.OK);
			LOGGER.info("uploadLenguage() / cenInstitucionExtendsMapper.updateByExampleSelective() -> OK. cen_institucion actualizado correctemente");
		}	
		else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.info("uploadLenguage() / cenInstitucionExtendsMapper.updateByExampleSelective() -> KO. cen_institucion NO actualizado correctemente");
		}
		
		LOGGER.info("uploadFile() -> Salida del servicio para actualizar el lenguaje de la entidad");
		return updateResponseDTO;
	}

}
