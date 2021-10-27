package org.itcgae.siga.scs.services.impl.ejg;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.CheckAccionesRemesas;
import org.itcgae.siga.DTOs.scs.CheckAccionesRemesasDTO;
import org.itcgae.siga.DTOs.scs.EcomOperacionTipoaccionDTO;
import org.itcgae.siga.DTOs.scs.RemesaResolucionDTO;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.DTOs.scs.RemesasResultadoItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomOperacionTipoaccion;
import org.itcgae.siga.db.entities.EcomOperacionTipoaccionExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.EcomOperacionTipoaccionMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsRemesasResolucionesExtendsMapper;
import org.itcgae.siga.scs.services.ejg.IRemesasResoluciones;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RemesasResolucionesServiceImpl implements IRemesasResoluciones{
	
	private Logger LOGGER = Logger.getLogger(this.getClass());
	
	
    @Autowired
    private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
    
    @Autowired
    private GenPropertiesMapper genPropertiesMapper;
    
	@Autowired
	private ScsRemesasResolucionesExtendsMapper scsRemesasResolucionesExtendsMapper;
	
	@Autowired
	private BusquedaRemesasServiceImpl busquedaRemesasServiceImpl;
	
	@Autowired
	private EcomOperacionTipoaccionMapper ecomOperacionTipoaccionMapper;

	@Override
	public RemesaResolucionDTO buscarRemesasResoluciones(RemesasResolucionItem remesasResolucionItem,
			HttpServletRequest request) {
		LOGGER.info("getLabel() -> Entrada al servicio para obtener las remesas de resoluciones");
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RemesaResolucionDTO remesaResultadoDTO = new RemesaResolucionDTO();
		List<RemesasResolucionItem> remesasResolucionesItem = null;
		
		if(idInstitucion != null) {
			remesasResolucionesItem = scsRemesasResolucionesExtendsMapper.buscarRemesasResoluciones(remesasResolucionItem, idInstitucion);
			if(remesasResolucionesItem != null ) {
				remesaResultadoDTO.setRemesasResolucionItem(remesasResolucionesItem);
			}
		}
		return remesaResultadoDTO;
	}

	@Override
	public ResponseEntity<InputStreamResource> descargarRemesasResolucion(List<RemesasResolucionItem> listaRemesasResolucionItem,
			HttpServletRequest request) {
		 String token = request.getHeader("Authorization");
	        String dni = UserTokenUtils.getDniFromJWTToken(token);
	        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
	        ResponseEntity<InputStreamResource> res = null;
	        InputStream fileStream = null;
	        HttpHeaders headers = new HttpHeaders();

	        try {

	            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
	            LOGGER.info(
	                    "RemesasResultadoServiceImpl.descargarFicheros() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

	            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

	            LOGGER.info(
	                    "RemesasResultadoServiceImpl.descargarFicheros() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

	            if (usuarios != null && !usuarios.isEmpty() && !listaRemesasResolucionItem.isEmpty()) {      
	            	
	                    fileStream = getZipFileRemesasResoluciones(listaRemesasResolucionItem, idInstitucion);
	                    headers.setContentType(MediaType.parseMediaType("application/zip"));
	                    headers.set("Content-Disposition", "attachment; filename=\"descargaRemesasResoluciones.zip\"");        
	                    res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,HttpStatus.OK);
	            }

	        } catch (Exception e) {
	            LOGGER.error(
	                    "RemesasResultadoServiceImpl.descargarFicheros() -> Se ha producido un error al descargar archivos asociados a las remesas de resultados",
	                    e);
	            res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
	                    HttpStatus.INTERNAL_SERVER_ERROR);
	        }

	        return res;
	}
	
    private InputStream getZipFileRemesasResoluciones(List<RemesasResolucionItem> listaRemesasResolucionItem, Short idInstitucion) {

        ByteArrayOutputStream byteArrayOutputStream = null;

        try {

            byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
            ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

            for (RemesasResolucionItem doc : listaRemesasResolucionItem) {
                if (doc.getNombreFichero() != null) {
                	String nextEntry;
                	if(doc.getNombreFichero().contains(".")) {
	                  nextEntry = doc.getNombreFichero().substring(0, doc.getNombreFichero().lastIndexOf("."));
	                  nextEntry += "-" + doc.getIdRemesaResolucion();
	                  String extension = doc.getNombreFichero()
	                          .substring(doc.getNombreFichero().lastIndexOf("."), doc.getNombreFichero().length())
	                          .toLowerCase();
	                  nextEntry += extension;
                	} else {
                		nextEntry = doc.getNombreFichero();
                	}
                  
                  String path = getDirectorioFicheroRemesa(idInstitucion,doc.getIdRemesaResolucion());
                  path += File.separator + doc.getNombreFichero();
                  File file = new File(path);
                  if(file.exists() && !file.isDirectory()) {
                	  zipOutputStream.putNextEntry(new ZipEntry(nextEntry));
	                  FileInputStream fileInputStream = new FileInputStream(file);
	                  IOUtils.copy(fileInputStream, zipOutputStream);
	                  fileInputStream.close();   
                  }
                  zipOutputStream.closeEntry();
                }
            }

            zipOutputStream.closeEntry();

            if (zipOutputStream != null) {
                zipOutputStream.finish();
                zipOutputStream.flush();
                IOUtils.closeQuietly(zipOutputStream);
            }

            IOUtils.closeQuietly(bufferedOutputStream);
            IOUtils.closeQuietly(byteArrayOutputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    private String getDirectorioFicheroRemesa(Short idInstitucion, int idRemesaResolucion) {

        // Extraemos el path para los ficheros
        GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
        genPropertiesExampleP.createCriteria().andParametroEqualTo("cajg.directorioFisicoCAJG");
        List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
        String path = genPropertiesPath.get(0).getValor();
        
        GenPropertiesExample genPropertiesExamplePG = new GenPropertiesExample();
        genPropertiesExamplePG.createCriteria().andParametroEqualTo("cajg.directorioCAJGJava");
        List<GenProperties> genPropertiesPathP = genPropertiesMapper.selectByExample(genPropertiesExamplePG);
        path += genPropertiesPathP.get(0).getValor();
      //Para local
        path = "C:\\Users\\kvargasnunez\\Desktop\\Server";
        path += File.separator + idInstitucion + File.separator + "remesaResoluciones";		
        path += File.separator + idRemesaResolucion;
        
        LOGGER.info("getDirectorioFicheroRemesa() -> Path del directorio de ficheros Remesa  = " + path);
        
        return path;
		
    }

	@Override
	public EcomOperacionTipoaccionDTO obtenerOperacionTipoAccion(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		GenParametros parametro = new GenParametros();
		EcomOperacionTipoaccionDTO ecomOperacionTipoaccionDTO = new EcomOperacionTipoaccionDTO();
		Error error = new Error();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"obtenerResoluciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());

			LOGGER.info(
					"obtenerResoluciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			parametro = busquedaRemesasServiceImpl.getTipoPCAJG(request);

			String tipoCajg = parametro.getValor();
			
			List<EcomOperacionTipoaccion>  ecomOperacionTipoAccion = scsRemesasResolucionesExtendsMapper.ecomOperacionTipoAccion(tipoCajg);
			
			ecomOperacionTipoaccionDTO.setEcomOperacionTipoaccion(ecomOperacionTipoAccion);	
			if(ecomOperacionTipoAccion.isEmpty()) {
				error.code(404);
				error.description("Empty");
				ecomOperacionTipoaccionDTO.setError(error);
			}else {
				error.setCode(200);
				error.setDescription("Obtención de operacion tipo accion correcta.");
				ecomOperacionTipoaccionDTO.setStatus(SigaConstants.OK);
				ecomOperacionTipoaccionDTO.setError(error);	
			}
		}
		
		return ecomOperacionTipoaccionDTO;
	}

	@Override
	public EcomOperacionTipoaccionDTO obtenerResoluciones(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		GenParametros parametro = new GenParametros();
		EcomOperacionTipoaccionDTO ecomOperacionTipoaccionDTO = new EcomOperacionTipoaccionDTO();
		int insertado = 0;
		Error error = new Error();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"obtenerResoluciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());

			LOGGER.info(
					"obtenerResoluciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			parametro = busquedaRemesasServiceImpl.getTipoPCAJG(request);

			String tipoCajg = parametro.getValor();
			
			List<EcomOperacionTipoaccion>  ecomOperacionTipoAccion = scsRemesasResolucionesExtendsMapper.ecomOperacionTipoAccion(tipoCajg);

			EcomCola ecomCola = new EcomCola();
			if(ecomOperacionTipoAccion != null && ecomOperacionTipoAccion.size() > 0) {
				ecomCola.setIdinstitucion(idInstitucion);
				ecomCola.setIdoperacion(ecomOperacionTipoAccion.get(0).getIdoperacion());
			}
				try {
					insertado = busquedaRemesasServiceImpl.insert(ecomCola);
				} catch (Exception e) {
					insertado = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en la inserción en la tabla ECOM_COLA");
					ecomOperacionTipoaccionDTO.setStatus(SigaConstants.KO);
					ecomOperacionTipoaccionDTO.setError(error);
					LOGGER.error("Se ha producido un error al insertar en ECOM_COLA " + e.getStackTrace());
					throw e;
				}
				if(insertado != 0) {
					error.setCode(200);
					error.setDescription("Obtener Resoluciones Correctamente.");
					ecomOperacionTipoaccionDTO.setStatus(SigaConstants.OK);
					ecomOperacionTipoaccionDTO.setError(error);	
				}		
			}
		
		return ecomOperacionTipoaccionDTO;
	}
	
	
	
	

}
