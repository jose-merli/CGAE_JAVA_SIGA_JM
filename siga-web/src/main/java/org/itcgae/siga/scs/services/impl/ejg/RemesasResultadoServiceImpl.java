package org.itcgae.siga.scs.services.impl.ejg;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Types;
import java.text.MessageFormat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.io.FileOutputStream;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.LogRemesaResolucionItem;
import org.itcgae.siga.DTOs.scs.RemesaResultadoDTO;
import org.itcgae.siga.DTOs.scs.RemesasItem;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.DTOs.scs.RemesasResultadoItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.GestorContadores;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorExample;
import org.itcgae.siga.db.entities.AdmContadorKey;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CajgProcedimientoremesaresol;
import org.itcgae.siga.db.entities.CajgProcedimientoremesaresolExample;
import org.itcgae.siga.db.entities.CajgRemesaresolucion;
import org.itcgae.siga.db.entities.CajgRemesaresolucionKey;
import org.itcgae.siga.db.entities.CajgRemesaresolucionfichero;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.db.mappers.CajgProcedimientoremesaresolMapper;
import org.itcgae.siga.db.mappers.CajgRemesaresolucionMapper;
import org.itcgae.siga.db.mappers.CajgRemesaresolucionficheroMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.ScsProcedimientosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmContadorExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsRemesasResolucionesExtendsMapper;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
@Transactional
public class RemesasResultadoServiceImpl implements IRemesasResultados{
	
	private Logger LOGGER = Logger.getLogger(RemesasResultadoServiceImpl.class);
	
	@Autowired
	private ScsRemesasResultadoExtendsMapper scsRemesasResultadoExtendsMapper;
	
    @Autowired
    private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
    
    @Autowired
    private GenPropertiesMapper genPropertiesMapper;
    
    @Autowired
    private GestorContadores gestorContadores;
	
	@Autowired
	private ScsRemesasResolucionesExtendsMapper remesasResolucionesExtendsMapper;
	
	@Autowired
	private CajgRemesaresolucionMapper cajgRemesaresolucionMapper;
	
	@Autowired
	private CajgRemesaresolucionficheroMapper cajgRemesaresolucionficheroMapper;
	
	@Autowired
	private AdmConfigMapper admConfigMapper;
	
	@Autowired
	private CajgProcedimientoremesaresolMapper cajgProcedimientoremesaresolMapper;
	
	@Autowired
	private ScsRemesasResolucionesExtendsMapper scsRemesasResolucionesExtendsMapper;
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
	@Override
	public RemesaResultadoDTO buscarRemesasResultado(RemesasResultadoItem remesasResultadoItem, HttpServletRequest request) {
		LOGGER.info("getLabel() -> Entrada del servicio para obtener las remesas de resultados");
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RemesaResultadoDTO remesaResultadoDTO = new RemesaResultadoDTO();
		List<RemesasResultadoItem> remesasResultadoItems = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		GenParametrosExample genParametrosExample = new GenParametrosExample();
		genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
		.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
		genParametrosExample.setOrderByClause("IDINSTITUCION DESC");


		if (idInstitucion != null) {
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = 200;
			}
			
			remesasResultadoItems = scsRemesasResultadoExtendsMapper.buscarRemesasResultado(remesasResultadoItem, idInstitucion, tamMaximo);
			
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
        LOGGER.info("getDirectorioFicheroRemesaResultado -> Path CAJG : "+path);
        GenPropertiesExample genPropertiesExamplePG = new GenPropertiesExample();
        genPropertiesExamplePG.createCriteria().andParametroEqualTo("cajg.directorioCAJGJava");
        List<GenProperties> genPropertiesPathP = genPropertiesMapper.selectByExample(genPropertiesExamplePG);
        path += genPropertiesPathP.get(0).getValor();
        LOGGER.info("getDirectorioFicheroRemesaResultado -> Path CAJG JAVA : "+ genPropertiesPathP.get(0).getValor());
        LOGGER.info("getDirectorioFicheroRemesaResultado -> Path CAJG/JAVA : "+path);
        path += File.separator + idInstitucion + File.separator + "remesaResoluciones";		
        path += File.separator + idRemesaResolucion;
        
        LOGGER.info("getDirectorioFicheroRemesa() -> Path del directorio de ficheros Remesa  = " + path);
        
        return path;
		
    }
    
    @Override
    public AdmContador recuperarDatosContador(HttpServletRequest request){
    	
    	String token = request.getHeader("Authorization");
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        String nombreContador = scsRemesasResolucionesExtendsMapper.contador(idInstitucion.toString(),"3");
		AdmContador adm = gestorContadores.getContador(idInstitucion,nombreContador);

		return adm;
    }
    
 
    @Override
    @Transactional(timeout=24000)
	public UpdateResponseDTO guardarRemesaResultado(RemesasResolucionItem remesasResolucionItem, MultipartHttpServletRequest request )  {
    	
    	UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		boolean produceLog = false;
		
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"guardarRemesaResultado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarRemesaResultado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			
			LOGGER.info(
					"guardarRemesaResultado() / cajgProcedimientoremesaresolMapper.selectByExample() -> Entrada a cajgProcedimientoremesaresolMapper para obtener información del procedimiento");

			CajgProcedimientoremesaresolExample procedimientoExample = new CajgProcedimientoremesaresolExample();
			procedimientoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtiporemesaEqualTo(new Short("3"));
			List<CajgProcedimientoremesaresol> procedimientos;
			
			procedimientos = cajgProcedimientoremesaresolMapper.selectByExample(procedimientoExample);
			
			LOGGER.info(
					"guardarRemesaResultado() / cajgProcedimientoremesaresolMapper.selectByExample() -> Salida de cajgProcedimientoremesaresolMapper para obtener información del procedimiento");
			Iterator<String> itr = request.getFileNames();
			MultipartFile file = request.getFile(itr.next());
			String fileName = file.getOriginalFilename();				
			
			RemesasResolucionItem remesaResolucionID = new RemesasResolucionItem();
			remesaResolucionID = remesasResolucionesExtendsMapper.getMaxIdRemesaResolucion(idInstitucion);
			
			String path = getDirectorioFicheroRemesa(idInstitucion,remesasResolucionItem.getIdRemesaResolucion());
			if(remesasResolucionItem.getIdRemesaResolucion() == 0) {
				path = getDirectorioFicheroRemesa(idInstitucion,remesaResolucionID.getIdRemesaResolucion());
			}
			
				try {
					
					// Dejado por Ruben
					//response = scsRemesasResultadoExtendsMapper.insertSelective(remesasResultadoItem, idInstitucion, usuarios.get(0).getIdusuario());		
				
					LOGGER.info(
							"guardarRemesaResultado() / cajgRemesaResolucionExtendsMapper.insert() -> Entrada a CajgRemesaResolucionExtendsMapper para insertar una remesa resolucion");
					
					//1- Insert en la tabla CAJG_REMESARESOLUCION
					CajgRemesaresolucion remesaResolucion = new CajgRemesaresolucion(); 
					
					AdmContador adm = gestorContadores.getContador(idInstitucion,SigaConstants.CONTADOR_REMESAS_RESULTADOS);
					String numeroResolucion = String.valueOf(adm.getContador()+1);
					remesaResolucion.setIdremesaresolucion(Long.valueOf(remesaResolucionID.getIdRemesaResolucion()));
					remesaResolucion.setIdinstitucion(idInstitucion);
					remesaResolucion.setPrefijo(adm.getPrefijo());
					remesaResolucion.setSufijo(adm.getSufijo());
					remesaResolucion.setNumero(arregloNumero(numeroResolucion));
					remesaResolucion.setFechacarga(remesasResolucionItem.getFechaCarga());
					remesaResolucion.setFecharesolucion(remesasResolucionItem.getFechaResolucion());
					remesaResolucion.setObservaciones(remesasResolucionItem.getObservaciones());
					remesaResolucion.setNombrefichero(fileName);
					remesaResolucion.setFechamodificacion(new Date());
					remesaResolucion.setUsumodificacion(usuarios.get(0).getIdusuario());
					remesaResolucion.setLoggenerado(new Short("1"));
					remesaResolucion.setIdtiporemesa(new Short("3"));
					
					response = cajgRemesaresolucionMapper.insert(remesaResolucion);
					
					//Actualizar contador.
					
					gestorContadores.setContador(adm,numeroResolucion,false);
					
					LOGGER.info(
							"guardarRemesaResultado() / cajgRemesaResolucionExtendsMapper.insert() -> Salida a CajgRemesaResolucionExtendsMapper para insertar una remesa resolucion");
					
					
					if(response == 1) {
						
						LOGGER.debug("guardarRemesaResultado() -> Guardar el archivo");	 
						 
						if(guardarFichero(path,file)) {
							
							//2- Insertar fichero en Tabla CAJG_REMESARESOLUCIONFICHERO  UN registro por LINEA.
							
							LOGGER.info(
									"guardarRemesaResultado() / cajgRemesaResolucionExtendsMapper.insert() -> Entrada a CajgRemesaResolucionExtendsMapper para insertar una remesa resolucion fichero");
							
								insertRemesaResolucionFichero(idInstitucion, remesaResolucionID,file,path);
							
							
							LOGGER.info(
									"guardarRemesaResultado() / cajgRemesaResolucionExtendsMapper.insert() -> Salida a CajgRemesaResolucionExtendsMapper para insertar una remesa resolucion fichero");
							
						
							LOGGER.info(
									"guardarRemesaResultado() / cajgRemesaResolucionExtendsMapper.insert() -> Entrada a la llamada al PL");
							
							//3 Llamamos al PL
								response = invocarProcedimiento(procedimientos.get(0).getConsulta(), idInstitucion, remesaResolucionID, procedimientos.get(0).getDelimitador(), fileName,usuarios.get(0).getIdusuario() );
							
							LOGGER.info(
									"guardarRemesaResultado() / cajgRemesaResolucionExtendsMapper.insert() -> Salida de la llamada al PL");
							produceLog = generaLog(path,fileName , idInstitucion, remesaResolucionID);
							
							if(!produceLog) {
								remesaResolucion.setLoggenerado(new Short("0"));
								 cajgRemesaresolucionMapper.updateByPrimaryKeySelective(remesaResolucion);
							}
							
						}	
					}
		
				}catch (Exception e) {
					LOGGER.error(
							"guardarRemesaResultado() / " + e.getMessage());
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);

				}if(response==1) {
					updateResponseDTO.setId(String.valueOf(remesasResolucionItem.getIdRemesaResolucion()));
					error.setCode(200);
					error.setDescription("Inserted");
					updateResponseDTO.setStatus(SigaConstants.OK);
					updateResponseDTO.setError(error);
				}
				if (response == 0) {
					error.setCode(400);
					error.setDescription("No se ha añadido la remesa resolucion");	
					updateResponseDTO.setStatus(SigaConstants.KO);
					updateResponseDTO.setError(error);
				} 
				
		}//Fin IF idInstitucion
    	
    	return updateResponseDTO;
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
		if(resultadoLogresultadoLog != null && !resultadoLogresultadoLog.isEmpty()) {
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
	
	private File getLogFile(String path, String nombreFichero) {
		String pathLog = path +"log";
		File logDirectory = new File(pathLog);
		logDirectory.mkdirs();
		pathLog += File.separator + nombreFichero + "_errores.txt";
		File logFile = new File(pathLog);
		return logFile;
	}
	
	private void insertRemesaResolucionFichero(Short idInstitucion, RemesasResolucionItem remesaResolucionID, MultipartFile  fileIn,String path ) {

		CajgRemesaresolucionfichero remesaResolucionFicheroID = new CajgRemesaresolucionfichero();
		remesaResolucionFicheroID = remesasResolucionesExtendsMapper.getMaxIdRemesaResolucionFichero();
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
				    if(linea!=null){ //Ejecucion por LINEA
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
		
		LOGGER.debug("guardarRemesaResultado() -> Creamos el nombre del archivo a guardar ");			
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
	
}
