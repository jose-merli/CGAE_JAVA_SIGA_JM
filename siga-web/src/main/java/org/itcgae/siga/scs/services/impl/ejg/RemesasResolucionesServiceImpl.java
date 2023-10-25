package org.itcgae.siga.scs.services.impl.ejg;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.opc.internal.FileHelper;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.CheckAccionesRemesas;
import org.itcgae.siga.DTOs.scs.CheckAccionesRemesasDTO;
import org.itcgae.siga.DTOs.scs.EcomOperacionTipoaccionDTO;
import org.itcgae.siga.DTOs.scs.LogRemesaResolucionItem;
import org.itcgae.siga.DTOs.scs.RemesaResolucionDTO;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.DTOs.scs.RemesasResultadoItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.GestorContadores;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CajgProcedimientoremesaresol;
import org.itcgae.siga.db.entities.CajgProcedimientoremesaresolExample;
import org.itcgae.siga.db.entities.CajgRemesaresolucion;
import org.itcgae.siga.db.entities.CajgRemesaresolucionKey;
import org.itcgae.siga.db.entities.CajgRemesaresolucionfichero;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomOperacionTipoaccion;
import org.itcgae.siga.db.entities.EcomOperacionTipoaccionExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.db.mappers.CajgProcedimientoremesaresolMapper;
import org.itcgae.siga.db.mappers.CajgRemesaresolucionMapper;
import org.itcgae.siga.db.mappers.CajgRemesaresolucionficheroMapper;
import org.itcgae.siga.db.mappers.EcomOperacionTipoaccionMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsCargaDesignaProcuradoresExtendsMapper;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
	
	
	@Autowired
	private CajgProcedimientoremesaresolMapper cajgProcedimientoremesaresolMapper;
	
	@Autowired
	private CajgRemesaresolucionMapper cajgRemesaresolucionMapper;
	
    @Autowired
    private GestorContadores gestorContadores;
    
	@Autowired
	private CajgRemesaresolucionficheroMapper cajgRemesaresolucionficheroMapper;
	
	@Autowired
	private AdmConfigMapper admConfigMapper;
	
	@Autowired
	private ScsCargaDesignaProcuradoresExtendsMapper scsCarcaDesignaProcuradoresExtendsMapper;
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
    
	@Override
	public RemesaResolucionDTO buscarRemesasResoluciones(RemesasResolucionItem remesasResolucionItem,
			HttpServletRequest request) {
		LOGGER.info("getLabel() -> Entrada al servicio para obtener las remesas de resoluciones");
		Error error = new Error();
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RemesaResolucionDTO remesaResultadoDTO = new RemesaResolucionDTO();
		List<RemesasResolucionItem> remesasResolucionesItem = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		GenParametrosExample genParametrosExample = new GenParametrosExample();
		genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
		.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
		genParametrosExample.setOrderByClause("IDINSTITUCION DESC");


		if(idInstitucion != null) {
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = 200;
			}
			
			remesasResolucionesItem = scsRemesasResolucionesExtendsMapper.buscarRemesasResoluciones(remesasResolucionItem, idInstitucion, tamMaximo);
			if(remesasResolucionesItem != null ) {
				remesaResultadoDTO.setRemesasResolucionItem(remesasResolucionesItem);
			}
			if (remesaResultadoDTO.getRemesasResolucionItem() != null && tamMaximo != null
					&& remesaResultadoDTO.getRemesasResolucionItem().size() >= tamMaximo) {
				error.setCode(200);
				error.setDescription("La consulta devuelve más de " + tamMaximo
						+ " resultados, pero se muestran sólo los " + tamMaximo
						+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
				remesaResultadoDTO.setError(error);			
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
	                    "RemesasResolucionesServiceImpl.descargarRemesasResolucion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

	            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

	            LOGGER.info(
	                    "RemesasResolucionesServiceImpl.descargarRemesasResolucion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

	            if (usuarios != null && !usuarios.isEmpty() && !listaRemesasResolucionItem.isEmpty()) {      
	            		if(listaRemesasResolucionItem.size() == 1) {
	            			fileStream = getZipFileRemesaResoluciones(listaRemesasResolucionItem.get(0), idInstitucion);
	            		}else {
	            			fileStream = getZipFileRemesasResoluciones(listaRemesasResolucionItem, idInstitucion);
	            		}             
	                    headers.setContentType(MediaType.parseMediaType("application/zip"));
	                    headers.set("Content-Disposition", "attachment; filename=\"descargaRemesasResoluciones.zip\"");        
	                    res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,HttpStatus.OK);
	            }

	        } catch (Exception e) {
	            LOGGER.error(
	                    "RemesasResolucioneserviceImpl.descargarFicheros() -> Se ha producido un error al descargar archivos asociados a las remesas de Resoluciones",
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
                String nextEntry = "RemesasResoluciones-" + doc.getIdRemesaResolucion() + ".zip";
                InputStream fileS = getZipFileRemesaResoluciones(doc,idInstitucion);
                  zipOutputStream.putNextEntry(new ZipEntry(nextEntry));
                  IOUtils.copy(fileS, zipOutputStream);
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
    
    private InputStream getZipFileRemesaResoluciones(RemesasResolucionItem remesasResolucionItem, Short idInstitucion) {
    	  ByteArrayOutputStream byteArrayOutputStream = null;

          try {
              byteArrayOutputStream = new ByteArrayOutputStream();
              BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
              ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);
              
                  if (remesasResolucionItem.getNombreFichero() != null) {
                		
                    String nombreFichero =  remesasResolucionItem.getNombreFichero();
                    String path = getDirectorioFicheroRemesa(idInstitucion,remesasResolucionItem.getIdRemesaResolucion());
                    path += File.separator + nombreFichero;      
                    LOGGER.info("Buscando Fichero = " +nombreFichero);
                    LOGGER.info("Ruta Completa de Fichero = " + path);
                    File file = new File(path);
                    if(file.exists() && !file.isDirectory()) {
                  	  zipOutputStream.putNextEntry(new ZipEntry(nombreFichero));
  	                  FileInputStream fileInputStream = new FileInputStream(file);
  	                  IOUtils.copy(fileInputStream, zipOutputStream);
  	                  fileInputStream.close();   
                    }
                    zipOutputStream.closeEntry();
                    if(remesasResolucionItem.getLog() == 1) {
                  	  path = getDirectorioFicheroRemesa(idInstitucion,remesasResolucionItem.getIdRemesaResolucion());
                  	  path += File.separator + "log" + File.separator +nombreFichero + "_errores.txt";
                  	nombreFichero = nombreFichero + "_errores.txt";
                  	  File fileLog = new File(path);
                        if(fileLog.exists() && !fileLog.isDirectory()) {
                      	  zipOutputStream.putNextEntry(new ZipEntry(nombreFichero));
      	                  FileInputStream fileInputStream = new FileInputStream(fileLog);
      	                  IOUtils.copy(fileInputStream, zipOutputStream);
      	                  fileInputStream.close();   
      	                  
                        } 
                        zipOutputStream.closeEntry();
                    }
               
                  }
              


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
        LOGGER.info("getDirectorioFicheroRemesaResoluciones -> Path CAJG : "+path);
        GenPropertiesExample genPropertiesExamplePG = new GenPropertiesExample();
        genPropertiesExamplePG.createCriteria().andParametroEqualTo("cajg.directorioCAJGJava");
        List<GenProperties> genPropertiesPathP = genPropertiesMapper.selectByExample(genPropertiesExamplePG);
        path += genPropertiesPathP.get(0).getValor();
        LOGGER.info("getDirectorioFicheroRemesaResoluciones -> Path CAJG JAVA : "+ genPropertiesPathP.get(0).getValor());
        LOGGER.info("getDirectorioFicheroRemesaResoluciones -> Path CAJG/JAVA : "+path);
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

	@Override
	@Transactional(timeout=24000)
	public UpdateResponseDTO guardarRemesaResolucion(RemesasResolucionItem remesasResolucionItem,
			MultipartHttpServletRequest request) {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;
		boolean produceLog = false;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"guardarRemesaResoluciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarRemesaResoluciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			String nombreContador = scsRemesasResolucionesExtendsMapper.contador(idInstitucion.toString(), String.valueOf(remesasResolucionItem.getIdTipoRemesa()));
			
			LOGGER.info(
					"guardarRemesaResoluciones() / cajgProcedimientoremesaresolMapper.selectByExample() -> Entrada a cajgProcedimientoremesaresolMapper para obtener información del procedimiento");
			
			CajgProcedimientoremesaresolExample procedimientoExample = new CajgProcedimientoremesaresolExample();
			procedimientoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtiporemesaEqualTo((short)remesasResolucionItem.getIdTipoRemesa());
			List<CajgProcedimientoremesaresol> procedimientos;
			
			procedimientos = cajgProcedimientoremesaresolMapper.selectByExample(procedimientoExample);
			
			if(procedimientos == null || procedimientos.isEmpty()) {
				error.setCode(400);
				error.setDescription("NoPL");
				updateResponseDTO.setStatus(SigaConstants.KO);
				updateResponseDTO.setError(error);
				return updateResponseDTO;
			}
			
			LOGGER.info(
					"guardarRemesaResoluciones() / cajgProcedimientoremesaresolMapper.selectByExample() -> Salida de cajgProcedimientoremesaresolMapper para obtener información del procedimiento");
			Iterator<String> itr = request.getFileNames();
			MultipartFile file = request.getFile(itr.next());
			String fileName = file.getOriginalFilename();					
			RemesasResolucionItem remesaResolucionID = new RemesasResolucionItem();
			remesaResolucionID = scsRemesasResolucionesExtendsMapper.getMaxIdRemesaResolucion(idInstitucion);
			
			String path = getDirectorioFicheroRemesa(idInstitucion,remesasResolucionItem.getIdRemesaResolucion());
			if(remesasResolucionItem.getIdRemesaResolucion() == 0) {
				path = getDirectorioFicheroRemesa(idInstitucion,remesaResolucionID.getIdRemesaResolucion());
			}
			

			try {
				
				LOGGER.info(
						"guardarRemesaResoluciones() / cajgRemesaResolucionExtendsMapper.insert() -> Entrada a CajgRemesaResolucionExtendsMapper para insertar una remesa resolucion");
				
				//1- Insert en la tabla CAJG_REMESARESOLUCION
				CajgRemesaresolucion remesaResolucion = new CajgRemesaresolucion(); 
				
				AdmContador adm = gestorContadores.getContador(idInstitucion,nombreContador);
				String numeroResolucion = String.valueOf(adm.getContador()+1);
				remesaResolucion.setIdremesaresolucion(Long.valueOf(remesaResolucionID.getIdRemesaResolucion()));
				remesaResolucion.setIdinstitucion(idInstitucion);
				remesaResolucion.setPrefijo(adm.getPrefijo());
				remesaResolucion.setSufijo(adm.getSufijo());
				remesaResolucion.setNumero(arregloNumero(numeroResolucion));
				remesaResolucion.setFechacarga(new Date());
				remesaResolucion.setFecharesolucion(remesasResolucionItem.getFechaResolucion());
				remesaResolucion.setObservaciones(remesasResolucionItem.getObservaciones());
				remesaResolucion.setNombrefichero( fileName );
				remesaResolucion.setFechamodificacion(new Date());
				remesaResolucion.setUsumodificacion(usuarios.get(0).getIdusuario());
				remesaResolucion.setLoggenerado(new Short("1"));
				remesaResolucion.setIdtiporemesa((short)remesasResolucionItem.getIdTipoRemesa());
				
				response = cajgRemesaresolucionMapper.insert(remesaResolucion);
				
				//Actualizar contador.
				
				gestorContadores.setContador(adm,numeroResolucion,false);
				
				LOGGER.info(
						"guardarRemesaResoluciones() / cajgRemesaResolucionExtendsMapper.insert() -> Salida a CajgRemesaResolucionExtendsMapper para insertar una remesa resolucion");
				
				
				if(response == 1) {
					
					LOGGER.debug("guardarRemesaResoluciones() -> Guardar el archivo");	 
					 
					if(guardarFichero(path,file)) {
						
						//2- Insertar fichero en Tabla CAJG_REMESARESOLUCIONFICHERO  UN registro por LINEA.
						
						LOGGER.info(
								"guardarRemesaResoluciones() / cajgRemesaResolucionExtendsMapper.insert() -> Entrada a CajgRemesaResolucionExtendsMapper para insertar una remesa resolucion fichero");
						
						insertRemesaResolucionFichero(idInstitucion, remesaResolucionID,file,path);
						
						
						LOGGER.info(
								"guardarRemesaResoluciones() / cajgRemesaResolucionExtendsMapper.insert() -> Salida a CajgRemesaResolucionExtendsMapper para insertar una remesa resolucion fichero");
						
					
						LOGGER.info(
								"guardarRemesaResoluciones() / cajgRemesaResolucionExtendsMapper.insert() -> Entrada a la llamada al PL");
						
						//3 Llamamos al PL
							response = invocarProcedimiento(procedimientos.get(0).getConsulta(), idInstitucion, remesaResolucionID, procedimientos.get(0).getDelimitador(), fileName,usuarios.get(0).getIdusuario() );
						
						LOGGER.info(
								"guardarRemesaResoluciones() / cajgRemesaResolucionExtendsMapper.insert() -> Salida de la llamada al PL");
						
						guardarZip(path,file, fileName);
						
						produceLog = generaLog(path,fileName , idInstitucion, remesaResolucionID);
						
						if(!produceLog) {
							remesaResolucion.setLoggenerado(new Short("0"));
							 cajgRemesaresolucionMapper.updateByPrimaryKeySelective(remesaResolucion);
						}
						
					}	
				}
	
			}catch (Exception e) {
				LOGGER.error(
						"guardarRemesaResoluciones() / " + e.getMessage());
				response = 0;
				error.setCode(400);
				error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				updateResponseDTO.setStatus(SigaConstants.KO);

			}
			if (response == 0) {
				error.setCode(400);
				if (error.getDescription() == null) {
					error.setDescription("No se ha añadido la remesa resolucion");
				}
				updateResponseDTO.setStatus(SigaConstants.KO);
				updateResponseDTO.setError(error);
			} if(response == 1 ) {
				updateResponseDTO.setId(String.valueOf(remesasResolucionItem.getIdRemesaResolucion()));
				error.setCode(200);
				error.setDescription("Inserted");
				updateResponseDTO.setStatus(SigaConstants.OK);
				updateResponseDTO.setError(error);
			}
					
				
		}//Fin IF idInstitucion
		return updateResponseDTO;
	}
	
	private String nombreFichero(String nameFile) {
		String nombreFichero = nameFile.substring(0, nameFile.lastIndexOf("."));
		return nombreFichero;
	}
	
	private String arregloNumero(String num) {
		if(num.length() < 5) {
			String ceros = "";
			for(; (num.length() + ceros.length()) < 5;) {
				ceros += "0";
			}
			num = ceros + num;
		}
		return num;
	}
	private boolean generaLog(String path,String nombreFichero, Short idInstitucion,
			RemesasResolucionItem remesaResolucionID) throws IOException {
		
		
		List<LogRemesaResolucionItem> resultadoLogresultadoLog = scsRemesasResolucionesExtendsMapper.logRemesaResoluciones(String.valueOf(idInstitucion), String.valueOf(remesaResolucionID.getIdRemesaResolucion()));
		MessageFormat messageFormat;
		String [] params;
		if(!resultadoLogresultadoLog.isEmpty()) {
			File logFile = getLogFile(path, nombreFichero);
			FileWriter fileWriter = new FileWriter(logFile);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			
			  for (LogRemesaResolucionItem log : resultadoLogresultadoLog) {
				  if(log.getParametrosError() == null) log.setParametrosError("");
				  params = log.getParametrosError().split(","); 
				  messageFormat = new MessageFormat(log.getDescripcion());
				  
	            	bw.write("[Línea:" + log.getNumeroLinea() + "] " +
	            			"[" +log.getCodigo() + "] " + messageFormat.format(params));
	            	bw.newLine();
	            }
			 bw.flush();
			 bw.close();
			return true;
		}
		return false;
	}

	private void guardarZip(String path, MultipartFile file,String fileName) throws IOException {
		String pathZip = path +File.separator+ nombreFichero(fileName) +".zip";
		String filea = path + File.separator + file.getOriginalFilename();
		try {
			File fip = new File(pathZip);
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(fip));
			out.putNextEntry(new ZipEntry(file.getOriginalFilename()));
            FileInputStream fileInputStream = new FileInputStream(filea);
            IOUtils.copy(fileInputStream, out);
            fileInputStream.close(); 
            out.close();
            File aux = new File(filea);
            aux.delete();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private File getLogFile(String path, String nombreFichero) {
		String pathLog = path +File.separator+"log";
		File logDirectory = new File(pathLog);
		logDirectory.mkdirs();
		pathLog += File.separator + nombreFichero(nombreFichero) + "_errores.txt";
		File logFile = new File(pathLog);
		return logFile;
	}

	private void insertRemesaResolucionFichero(Short idInstitucion, RemesasResolucionItem remesaResolucionID, MultipartFile  fileIn,String path ) {

		CajgRemesaresolucionfichero remesaResolucionFicheroID = new CajgRemesaresolucionfichero();
		remesaResolucionFicheroID = scsRemesasResolucionesExtendsMapper.getMaxIdRemesaResolucionFichero();
		Long idRemesaResolucionFichero = remesaResolucionFicheroID.getIdremesaresolucionfichero();
		FileInputStream fis;

		try {
			File fileServer = new File(path, fileIn.getOriginalFilename());
			fis = new FileInputStream(fileServer.getPath());
			InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-15");
			BufferedReader br = new BufferedReader(isr);
			String linea="";
			int numLinea = 0;
				while(br.ready()) {  
				    linea = br.readLine();  
				    if(linea!=null){ 
				    	numLinea++;
						CajgRemesaresolucionfichero remesaResolucionFichero = new CajgRemesaresolucionfichero();
						remesaResolucionFichero.setIdremesaresolucionfichero(idRemesaResolucionFichero++);
						remesaResolucionFichero.setIdremesaresolucion(Long.valueOf(remesaResolucionID.getIdRemesaResolucion()));
						remesaResolucionFichero.setIdinstitucion(idInstitucion);
						remesaResolucionFichero.setNumerolinea(numLinea);
						remesaResolucionFichero.setLinea(linea);
						cajgRemesaresolucionficheroMapper.insert(remesaResolucionFichero);
					}
				}
			br.close();
			isr.close();
			fis.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	
	}
	
	
	
	private DataSource getOracleDataSource() throws IOException, NamingException {
		try {

			LOGGER.debug("Recuperando datasource {} provisto por el servidor (JNDI)");

			AdmConfigExample example = new AdmConfigExample();
			example.createCriteria().andClaveEqualTo("spring.datasource.jndi-name");
			List<AdmConfig> config = admConfigMapper.selectByExample(example);
			Context ctx = new InitialContext();
			return (DataSource) ctx.lookup(config.get(0).getValor());
		} catch (NamingException e) {
			throw e;
		}
	}
	

	private int  callPLProcedure(String functionDefinition, int outParameters, Object[] inParameters)
			throws IOException, NamingException, SQLException {
		int result = 0;

		DataSource ds = getOracleDataSource();
		Connection con = ds.getConnection();
		try {
			CallableStatement cs = con.prepareCall(functionDefinition);
			int size = inParameters.length;

			// input Parameters
			for (int i = 0; i < size; i++) {

				cs.setString(i + 1, (String) inParameters[i]);
			}
			// output Parameters
			for (int i = 0; i < outParameters; i++) {
				cs.registerOutParameter(i + size + 1, Types.VARCHAR);
			}

			for (int intento = 1; intento <= 2; intento++) {
				try {
					cs.execute();
					result = 1;
					break;

				} catch (SQLTimeoutException tex) {
					result = 0;
					throw tex;					
				} catch (SQLException ex) {
					if (ex.getErrorCode() != 4068 || intento == 2) { // JPT: 4068 es un error de descompilado (la
						result = 0;								// segunda vez deberia funcionar)
						throw ex;
					}
				}

			}

			cs.close();
			return result;

		} catch (SQLTimeoutException ex) {
			return 0;
		} catch (SQLException ex) {
			return 0;
		} catch (Exception e) {
			return 0;
		} finally {
			con.close();
			con = null;
		}
	}
	
    
	private int invocarProcedimiento(String nomPL, Short idInstitucion,RemesasResolucionItem remesaRemesasResolucionItem,String delimitador,String fileName,int idUsuario) throws Exception {
		Object[] param_in;
		int resultado =0;
		
		try {
	    	param_in = new Object[5];
			param_in[0] = String.valueOf(idInstitucion);
			param_in[1] = String.valueOf(remesaRemesasResolucionItem.getIdRemesaResolucion());
			param_in[2] = delimitador;
			param_in[3] = fileName;
			param_in[4] = String.valueOf(idUsuario);
			
			// Ejecucion del PL
			resultado = callPLProcedure("{call "+nomPL+" (?,?,?,?,?)}", 0, param_in);
	    	if (resultado != 1) {
	    		LOGGER.error("Error en el PL = "+nomPL);
	    	}
			
		}catch (Exception e) {
			throw new Exception ("Error al ejecutar Procedimiento: " + e.getMessage());		
		}
		return resultado;
	}
	
	private Boolean guardarFichero(String path,MultipartFile file) throws IOException {
		
		LOGGER.debug("guardarRemesaResoluciones() -> Creamos el nombre del archivo a guardar ");			
		BufferedOutputStream stream = null;
		File serverFile = null;
		
		try {
			File aux = new File(path);
			aux.mkdirs();
			serverFile = new File(path, file.getOriginalFilename());
			stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(file.getBytes());

		} catch (FileNotFoundException e) {
			LOGGER.error("uploadFile() -> Error al buscar fichero en el directorio indicado", e);
			return false;
		} catch (IOException ioe) {
			LOGGER.error("uploadFile() -> Error al guardar fichero en el directorio indicado", ioe);
			return false;
		} finally {
			// close the stream
			LOGGER.debug("uploadFile() -> Cierre del stream del fichero");
			stream.close();
		}
		
		return true ;	
		
	}

	@Override
	public UpdateResponseDTO actualizarRemesaResolucion(RemesasResolucionItem remesasResolucionItem,
			HttpServletRequest request) {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"actualizarRemesaResolucion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"actualizarRemesaResolucion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			

			LOGGER.info(
					"actualizarRemesaResolucion() / cajgProcedimientoremesaresolMapper.selectByExample() -> Entrada a cajgProcedimientoremesaresolMapper para obtener información del procedimiento");
	
			try {
				LOGGER.info(
						"actualizarRemesaResolucion() / cajgRemesaresolucionMapper.selectByPrimaryKey(example) -> Entrada a cajgRemesaresolucionMapper para buscar la remesaResolucion a actualizar");
				
				CajgRemesaresolucionKey remesaResolucionKey = new CajgRemesaresolucionKey();
				remesaResolucionKey.setIdinstitucion(idInstitucion);
				remesaResolucionKey.setIdremesaresolucion(Long.valueOf(remesasResolucionItem.getIdRemesaResolucion()));
				
				CajgRemesaresolucion cajgRemesaresolucion = cajgRemesaresolucionMapper.selectByPrimaryKey(remesaResolucionKey);
				
				LOGGER.info(
						"guardarRemesaResoluciones() / cajgRemesaresolucionMapper.selectByPrimaryKey(example) -> Entrada a cajgRemesaresolucionMapper para buscar la remesaResolucion a actualizar");
				
				if(cajgRemesaresolucion != null) {
					LOGGER.info(
							"guardarRemesaResoluciones() / cajgRemesaresolucionMapper.updateByPrimaryKeySelective(example) -> Entrada a cajgRemesaresolucionMapper para actulizar la remesaResolucion");
					
					cajgRemesaresolucion.setObservaciones(remesasResolucionItem.getObservaciones());
					cajgRemesaresolucion.setFechamodificacion(new Date());
					cajgRemesaresolucion.setFecharesolucion(remesasResolucionItem.getFechaResolucion());
					cajgRemesaresolucion.setUsumodificacion(usuarios.get(0).getIdusuario());
					
					response = cajgRemesaresolucionMapper.updateByPrimaryKeySelective(cajgRemesaresolucion);
				}
			}catch (Exception e) {
				response = 0;
				error.setCode(400);
				error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				updateResponseDTO.setStatus(SigaConstants.KO);
				updateResponseDTO.setError(error);

			}
			if (response == 0) {
				error.setCode(400);
				error.setDescription("No se ha actualizado la remesa resolucion");
				updateResponseDTO.setStatus(SigaConstants.KO);
				updateResponseDTO.setError(error);
			} if(response == 1 ) {
				updateResponseDTO.setId(String.valueOf(remesasResolucionItem.getIdRemesaResolucion()));
				error.setCode(200);
				error.setDescription("Updated");
				updateResponseDTO.setStatus(SigaConstants.OK);
				updateResponseDTO.setError(error);
			}	
				
		}//Fin IF idInstitucion
		return updateResponseDTO;
	}
    
    @Override
    public AdmContador recuperarDatosContador(HttpServletRequest request){
    	
    	String token = request.getHeader("Authorization");
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        String nombreContador = scsRemesasResolucionesExtendsMapper.contador(idInstitucion.toString(),"1");
		AdmContador adm = gestorContadores.getContador(idInstitucion,nombreContador);

		return adm;
    }
    
	

}
