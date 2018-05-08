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

import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.EntidadLenguajeInstitucionDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.adm.service.IGestionEntidadService;
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
import org.itcgae.siga.security.UserAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class GestionEntidadServiceImpl implements IGestionEntidadService{

	
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
		EntidadLenguajeInstitucionDTO entidadLenguajeInstitucionDTO = new EntidadLenguajeInstitucionDTO();
		
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String dni = nifInstitucion.substring(0,9);
		String idInstitucion = nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length());
		
		if(null != idInstitucion) {
			entidadLenguajeInstitucionDTO = cenInstitucionExtendsMapper.getInstitutionLenguage(idInstitucion);
		}	
		return entidadLenguajeInstitucionDTO;
	}

	@Override
	public ComboDTO getLenguages(HttpServletRequest request) {
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> combooItems = new ArrayList<ComboItem>();
		ComboItem comboItem = new ComboItem();
		
		
		combooItems = genDiccionarioExtendsMapper.getLabelLenguage();
		// primer elemento a vacio para componente de pantalla dropdown
		comboItem.setLabel("");
		comboItem.setValue("");
		combooItems.add(0,comboItem);
		comboDTO.setCombooItems(combooItems);
		
		return comboDTO;
	}

	@Override
	public CreateResponseDTO uploadFile(MultipartHttpServletRequest request) throws IOException {
		int response = 0;
		CreateResponseDTO createResponseDTO = new CreateResponseDTO();
		
		// Obtenemos datos del usuario logeado
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String dni = nifInstitucion.substring(0, 9);
		String idInstitucion = nifInstitucion.substring(nifInstitucion.length() - 4, nifInstitucion.length());
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		
		// Obtenemos path imagenes para almacenar imagen de logo
		GenPropertiesExample genPropertiesExample = new GenPropertiesExample();
		genPropertiesExample.createCriteria().andParametroEqualTo("directorios.carpeta.logos");
		List<GenProperties> properties = genPropertiesMapper.selectByExample(genPropertiesExample);
		//String pathImagenes = properties.get(0).getValor() + File.separator;
		String pathImagenes = properties.get(0).getValor() + "/";
		
		// Coger archivo del request
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
		Random aleatorio = new Random();
		fileName = idInstitucion + "_" + aleatorio.nextInt() + "_" + fileName;

		
	
		BufferedOutputStream stream = null;
		// Guardar el archivo
		try {
			File aux = new File(pathImagenes);
			// creo directorio si no existe
			aux.mkdirs();
			File serverFile = new File(pathImagenes, fileName);
			stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(file.getBytes());

		} catch (FileNotFoundException fnfe) {
			return null;
		} catch (IOException ioe) {
			return null;
		} finally {
			// close the stream
			stream.close();
		}
		
		
		// actualizar en base de datos tabla adm_gestor_interfaz
		AdmGestorinterfazExample admGestorinterfazExample = new AdmGestorinterfazExample();
		admGestorinterfazExample.createCriteria().andAdmGestorinterfazIdEqualTo(Long.valueOf(idInstitucion));
		List<AdmGestorinterfaz> listAdmGestorInterfaz = admGestorinterfazMapper.selectByExample(admGestorinterfazExample);
		
		// si vacia => insert into
		if(listAdmGestorInterfaz.isEmpty()) {
			
			AdmGestorinterfaz admGestorinterfazInsertInto = new AdmGestorinterfaz();
			admGestorinterfazInsertInto.setAdmGestorinterfazId(Long.valueOf(idInstitucion));
			admGestorinterfazInsertInto.setColor("0");
			admGestorinterfazInsertInto.setFechamodificacion(new Date());
			admGestorinterfazInsertInto.setLogo(fileName);
			admGestorinterfazInsertInto.setTipoletra("1");
			admGestorinterfazInsertInto.setUsumodificacion(usuario.getIdusuario());
			response = admGestorinterfazMapper.insertSelective(admGestorinterfazInsertInto);
		}
		// si no vacía => update
		else {
			AdmGestorinterfaz admGestorinterfaz = new AdmGestorinterfaz();
			admGestorinterfaz.setLogo(fileName);
			admGestorinterfaz.setFechamodificacion(new Date());
			admGestorinterfaz.setUsumodificacion(usuario.getIdusuario());
			response = admGestorinterfazMapper.updateByExampleSelective(admGestorinterfaz, admGestorinterfazExample);
		}
         
		if(response == 1)
			createResponseDTO.setStatus("OK");
		else 	createResponseDTO.setStatus("ERROR");
		
		return createResponseDTO;
	}

	@Override
	public CreateResponseDTO uploadLenguage(String idLenguaje, HttpServletRequest request) {
		int response = 0;
		CreateResponseDTO createResponseDTO = new CreateResponseDTO();
		
		// Como el idLenguage viene en JSON, le quitamos ""
		idLenguaje = idLenguaje.substring(1,idLenguaje.length()-1);
		// Obtenemos atributos del usuario logeado
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String dni = nifInstitucion.substring(0, 9);
		String idInstitucion = nifInstitucion.substring(nifInstitucion.length() - 4, nifInstitucion.length());
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		
		// Creamos objetos para consulta en bbdd
		if(null != idInstitucion && null != usuario) {
			CenInstitucion record = new CenInstitucion();
			record.setIdlenguaje(idLenguaje);
			record.setFechamodificacion(new Date());
			record.setUsumodificacion(usuario.getIdusuario());
			
			CenInstitucionExample example = new CenInstitucionExample();
			example.createCriteria().andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			
			// actualizamos idlenguaje, fecha modificacion y usuario de modificacion para cen_institucion
			response = cenInstitucionExtendsMapper.updateByExampleSelective(record, example);
		}
		if(response == 1)
			createResponseDTO.setStatus("OK");
		else 	createResponseDTO.setStatus("ERROR");
		
		return createResponseDTO;
	}

}
