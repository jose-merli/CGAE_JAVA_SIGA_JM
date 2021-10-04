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
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.RemesaResultadoDTO;
import org.itcgae.siga.DTOs.scs.RemesasItem;
import org.itcgae.siga.DTOs.scs.RemesasResultadoItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmContadorExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsRemesasResultadoExtendsMapper;
import org.itcgae.siga.scs.services.ejg.IRemesasResultados;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RemesasResultadoServiceImpl implements IRemesasResultados{
	
	private Logger LOGGER = Logger.getLogger(RemesasResultadoServiceImpl.class);
	
	@Autowired
	private ScsRemesasResultadoExtendsMapper scsRemesasResultadoExtendsMapper;
	
    @Autowired
    private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
    
    @Autowired
    private GenPropertiesMapper genPropertiesMapper;
    
	@Autowired
	private AdmContadorExtendsMapper admContadorExtendsMapper;
	
	@Override
	public RemesaResultadoDTO buscarRemesasResultado(RemesasResultadoItem remesasResultadoItem, HttpServletRequest request) {
		LOGGER.info("getLabel() -> Entrada del servicio para obtener las remesas de resultados");
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RemesaResultadoDTO remesaResultadoDTO = new RemesaResultadoDTO();
		List<RemesasResultadoItem> remesasResultadoItems = null;
		
		if (idInstitucion != null) {
			remesasResultadoItems = scsRemesasResultadoExtendsMapper.buscarRemesasResultado(remesasResultadoItem, idInstitucion);
			
			if (remesasResultadoItems != null) {
				remesaResultadoDTO.setRemesasResultadosItem(remesasResultadoItems);
			}
		}
		
		
		LOGGER.info("getLabel() -> Salida del servicio para obtener las remesas de resultados");
		return remesaResultadoDTO;
	}
	
	@Override
    public ResponseEntity<InputStreamResource> descargarFicheros(List<RemesasResultadoItem> listaRemesasResultadoItem, HttpServletRequest request) {

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

            if (usuarios != null && !usuarios.isEmpty() && !listaRemesasResultadoItem.isEmpty()) {               
                    fileStream = getZipFileRemesasResultados(listaRemesasResultadoItem, idInstitucion);
                    headers.setContentType(MediaType.parseMediaType("application/zip"));
                    headers.set("Content-Disposition", "attachment; filename=\"descargaRemesasResultados.zip\"");        
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
	
    private InputStream getZipFileRemesasResultados(List<RemesasResultadoItem> listaRemesasResultadoItem, Short idInstitucion) {

        ByteArrayOutputStream byteArrayOutputStream = null;

        try {

            byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
            ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

            for (RemesasResultadoItem doc : listaRemesasResultadoItem) {
                if (doc.getNombreFichero() != null) {
                	String nextEntry;
                	if(doc.getNombreFichero().contains(".")) {
	                  nextEntry = doc.getNombreFichero().substring(0, doc.getNombreFichero().lastIndexOf("."));
	                  nextEntry += "-" + doc.getIdRemesaResultado();
	                  String extension = doc.getNombreFichero()
	                          .substring(doc.getNombreFichero().lastIndexOf("."), doc.getNombreFichero().length())
	                          .toLowerCase();
	                  nextEntry += extension;
                	} else {
                		nextEntry = doc.getNombreFichero();
                	}
                  
                  String path = getDirectorioFicheroRemesa(idInstitucion,doc.getIdRemesaResultado());
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

        path += File.separator + idInstitucion + File.separator + "remesaResoluciones";		
        path += File.separator + idRemesaResolucion;

        return path;
		
    }
    
    @Override
    public AdmContador recuperarDatosContador(HttpServletRequest request){
    	
    	String token = request.getHeader("Authorization");
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		AdmContadorExample admContadorExample = new AdmContadorExample();
		admContadorExample.createCriteria().andIdcontadorEqualTo(SigaConstants.CONTADOR_REMESAS_RESULTADOS)
				.andIdinstitucionEqualTo(idInstitucion);

		List<AdmContador> counterList = admContadorExtendsMapper.selectByExample(admContadorExample);
		AdmContador counter = new AdmContador();

		if (null != counterList && counterList.size() > 0) {
			counter = counterList.get(0);
		}

		return counter;
    }
    
    
    @Override
	public UpdateResponseDTO guardarRemesaResultado(RemesasResultadoItem remesasResultadoItem, HttpServletRequest request) {
    	
    	UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		Integer idRemesa = 0;
		int response = 0;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"guardarRemesaResultado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarRemesaResultado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(usuarios.size() > 0) {
				response = scsRemesasResultadoExtendsMapper.insertSelective(remesasResultadoItem, idInstitucion, usuarios.get(0).getIdusuario());
			}
		}
		
		
    	
    	return new UpdateResponseDTO();
    }
    
}
