package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.commons.utils.SIGAHelper;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.*;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsPcajgAlcActErrorCamExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CertificacionFacSJCSServicesCAMHelper {

    private static final String TOKEN_NL = "$$";

    static enum PCAJG_ALC_ACT_TIPO_INCIDENCIA {

        NO_IMPRIMIR((short) 0),
        OBLIGATORIO((short) 1),
        BOOLEANO((short) 2);

        private Short id = 0;

        PCAJG_ALC_ACT_TIPO_INCIDENCIA(Short id) {
            this.id = id;
        }

        public Short getId() {
            return this.id;
        }
    }


    public static enum PCAJG_ALC_ACT_NIVEL {
        WARINING,
        ERROR
    }
    
    public enum TIPO_FICHERO_CAM{
    	TODOS,
    	NINGUNO
    }

    public final String EJG_ANIO = "EJG_ANIO";
    public final String EJG_NUMERO = "EJG_NUMERO";
    public final String DESIGNA_ANIO = "DESIGNA_ANIO";
    public final String DESIGNA_CODIGO = "DESIGNA_CODIGO";
    public final String NUMERO_ACTUACION = "NUMERO_ACTUACION";
    public final String CAB2_NUMERO_INTERCAMBIO = "CAB2_NUMERO_INTERCAMBIO";

    private Logger LOGGER = Logger.getLogger(CertificacionFacSJCSServicesCAMHelper.class);

    private static final String TXT_EXT = ".txt";
    private static final String XLSX_EXT = ".xlsx";
    private static final String UNDERSCORE = "_";
    private static final String CONTADOR_CAM = "INTERCAMBIOICM";
    private static final String DIRECTORIO_INCIDENCIAS = "informeIncidenciasWS";

	private static final int EXCEL_ROW_FLUSH = 1000;
    
    @Autowired
    private GenPropertiesMapper genPropertiesMapper;

    @Autowired
    private PCAGAlcActIncidenciaMapper incMapper;

    @Autowired
    private AdmContadorMapper contadorMapper;

    @Autowired
    private VWSJE2003DesignaMapper dMapper;

    @Autowired
    private FacturacionSJCSHelper colaHelper;
    
    @Autowired
	private ConConsultasExtendsMapper conConsultasExtendsMapper;
    
    @Autowired
    private FcsPcajgAlcActErrorCamExtendsMapper fcsPcajgAlcActErrorCamExtendsMapper;
    
    
    Map<String, PCAJGAlcActIncidencia> mIncidencias = new HashMap<String,PCAJGAlcActIncidencia >();
    List<String> sColumnasNoImprimir = new ArrayList<String>() ;

    private AdmContador contadorOriginal;
    private String anyoSufijo;
    private Long idIntercambio;
    private Integer longitudContador;
    private Integer idUsuario;
    private BufferedWriter bwError;
    private File fError;
    private String cabeceraLog = null;

    private void initInforme() {
        List<PCAJGAlcActIncidencia> lInc = incMapper.selectAll();
      
        if(lInc !=null) {
        	for (PCAJGAlcActIncidencia pcajgAlcActIncidencia : lInc) {
				if (PCAJG_ALC_ACT_TIPO_INCIDENCIA.NO_IMPRIMIR.getId() == pcajgAlcActIncidencia.getIdTipoIncidencia()) {
					this.sColumnasNoImprimir.add(pcajgAlcActIncidencia.getCampo());
				} else {
					this.mIncidencias.put(pcajgAlcActIncidencia.getCampo(), pcajgAlcActIncidencia);
				}
			}
        }     
        //Predicate<PCAJGAlcActIncidencia> pIncNoImprimir = inc -> inc.getIdTipoIncidencia().equals(PCAJG_ALC_ACT_TIPO_INCIDENCIA.NO_IMPRIMIR.getId());
        //mIncidencias = lInc.stream().filter(pIncNoImprimir.negate()).collect(Collectors.toMap(x -> x.getCampo(), x -> x));
        //sColumnasNoImprimir = lInc.stream().filter(pIncNoImprimir).map(x -> x.getCampo().toUpperCase()).collect(Collectors.toSet());
    }

    private void finalizaInforme() {
        if (bwError != null) {
            try {
                bwError.close();
                bwError = null;
                fError = null;
            } catch (Exception e) {
                LOGGER.error("Error al cerrar bwError:" + e);
            }
            ;
        }
    }

    private boolean closeLogFile() {
    	boolean abierto = false;
    	if(bwError != null) {
    		try {
    			bwError.flush();
    			bwError.close();
    			bwError = null;
    		}catch(IOException e) {
    			
    		}
    	}
    	return abierto;
    }

    private Path getRutaFicheroSalida(Short idInstitucion, String idFacturacion) {
        Path rutaTmp = getRutaFicherosSiga().resolve(String.valueOf(idInstitucion)).resolve(idFacturacion);
        return rutaTmp;
    }

    private Path getRutaFicherosSiga() {
        GenPropertiesKey key = new GenPropertiesKey();
        key.setFichero(SigaConstants.FICHERO_SIGA);
        key.setParametro(SigaConstants.parametroRutaSalidaInformes);
        GenProperties rutaFicherosSalida = genPropertiesMapper.selectByPrimaryKey(key);
        return Paths.get(rutaFicherosSalida.getValor());
    }


    private Path getRutaAlmacenFichero(Short idInstitucion, String idFacturacion) {
        GenPropertiesKey key = new GenPropertiesKey();
        key.setFichero(SigaConstants.FICHERO_SIGA);
        key.setParametro(SigaConstants.parametroRutaAlmacenFicheros);
        GenProperties rutaFicherosSalida = genPropertiesMapper.selectByPrimaryKey(key);
        String rutaRaiz = rutaFicherosSalida.getValor();
        return Paths.get(rutaRaiz, String.valueOf(idInstitucion), idFacturacion);
    }


    public String getRutaFicheroIncidencias(Short idInstitucion, String idFacturacion) {
        String ruta = getRutaFicherosSiga() + SigaConstants.pathSeparator + DIRECTORIO_INCIDENCIAS + SigaConstants.pathSeparator + idInstitucion + SigaConstants.pathSeparator + idFacturacion + SigaConstants.pathSeparator;
       
        return ruta;
    }

    private File getFileInformeIncidencias(Short idInstitucion, String idFacturacion) throws IOException {
        File f = null;
        Path pFile = null;
        pFile = Paths.get(getRutaFicheroIncidencias(idInstitucion, idFacturacion));

        if (!Files.exists(pFile)) {
            Files.createDirectories(pFile);
        }

        f = pFile.resolve("InformeIncididencias.csv").toFile();

        return f;
    }

    public String getNombreFicheroCAM() {
        //final String PATTERN = "ddMMYYYHHMMss";


        /*AdmInformeKey infK = new AdmInformeKey();
        infK.setIdplantilla(1);
        infK.setIdinstitucion(idInstitucion);
        AdmInforme inf = admInfMap.selectByPrimaryKey(infK);*/

        //String date = new SimpleDateFormat(PATTERN).format(new Date());
        String nombre = "InformeCAM" + TXT_EXT;
        return nombre;
    }

    public String getNombreErroresCAM() {
        //final String PATTERN = "ddMMYYYHHMMss";


        /*AdmInformeKey infK = new AdmInformeKey();
        infK.setIdplantilla(1);
        infK.setIdinstitucion(idInstitucion);
        AdmInforme inf = admInfMap.selectByPrimaryKey(infK);*/

        //String date = new SimpleDateFormat(PATTERN).format(new Date());
        String nombre = "ErroresCAM" + XLSX_EXT;
        return nombre;
    }
    
    public String getNombreResumenCAM() {
        //final String PATTERN = "ddMMYYYHHMMss";


        /*AdmInformeKey infK = new AdmInformeKey();
        infK.setIdplantilla(1);
        infK.setIdinstitucion(idInstitucion);
        AdmInforme inf = admInfMap.selectByPrimaryKey(infK);*/

        //String date = new SimpleDateFormat(PATTERN).format(new Date());
        String nombre = "Resumen" + XLSX_EXT;
        return nombre;
    }
    
    public File getInformeCAM(Short idInstitucion, String idFacturacion, String tipoFichero, HttpServletRequest request) {
        boolean hayErrores = false;
        Path rutaPadre;
        Path rutaFichero;
        File fichero = null;

        initInforme();

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        idUsuario = colaHelper.getUsuario(idInstitucion, dni).getIdusuario();

        rutaPadre = getRutaFicheroSalida(idInstitucion, idFacturacion);

        try {
            deletePathWithContents(rutaPadre);
            Files.createDirectories(rutaPadre);
            rutaFichero = rutaPadre.resolve(getNombreFicheroCAM());
            fichero = rutaFichero.toFile();
            hayErrores = rellenaFichero(fichero, idInstitucion, idFacturacion, tipoFichero);
        } catch (IOException e) {
            LOGGER.error("error al obtener informe CAM:" + e);
            hayErrores = true;
        }

        if (hayErrores) {
            deleteFileSafeMode(fichero);
            fichero = fError;
        }

        finalizaInforme();

        return fichero;
    }

    private void deleteFileSafeMode(File fichero) {
        if (fichero != null) {
            try {
                Files.deleteIfExists(fichero.toPath());
            } catch (IOException e) {
                LOGGER.error("error al borrar " + fichero.getName() + ": " + e);
            }
        }
    }

    private void deletePathWithContents(Path pPadre) throws IOException {
        Consumer<Path> deleteIfExists = p -> {
            try {
                Files.deleteIfExists(p);
            } catch (IOException e) {
                LOGGER.error("error al borrar:" + e);
            }
        };
        if (Files.exists(pPadre)) {
            Files.list(pPadre).forEach(deleteIfExists);
            Files.deleteIfExists(pPadre);
        }
    }

    private void escribeLog(Short idInstitucion, String idFacturacion, String texto) throws IOException {
        if (bwError == null) {
            fError = getFileInformeIncidencias(idInstitucion, idFacturacion);
            deleteFileSafeMode(fError);
            FileWriter fileWriter = new FileWriter(fError);
            bwError = new BufferedWriter(fileWriter);
            if (cabeceraLog != null) {
                bwError.write(cabeceraLog);
                bwError.newLine();
            }
        }

        bwError.write(texto);
        bwError.newLine();
        bwError.flush();
    }

    private String getTexto(String... valores) {
        return String.join(";", valores);
    }


    private boolean rellenaFichero(File file, Short idInstitucion, String idFacturacion, String idTipoFichero) {
        boolean hayErrores = false;

        inicializarContador(idInstitucion);

        List<Map<String, Object>> listaFilas = dMapper.selectByIdInstitucionAndIdFacturacionAndIdTipoFichero(idInstitucion, idFacturacion, idTipoFichero);

        try (
        		FileWriter fw = new FileWriter(file); 
        		BufferedWriter bw = new BufferedWriter(fw);) {
            //cabeceraLog = "AÑO EJG;NÚMERO EJG;AÑO DESIGNACIÓN;NUMERO DESIGNACIÓN;NÚMERO ACTUACIÓN;TIPO INCIDENCIA;INCIDENCIA";

            if (listaFilas.size() > 0) {
                int numFila = 1;
                for (Map<String, Object> mFila : listaFilas) {
                	hayErrores |= procesaFila(idInstitucion, idFacturacion, bw, mFila, numFila == listaFilas.size(),
                            hayErrores);
                
                }
                actualizarContador(idInstitucion);
            } else {
                escribeLog(idInstitucion, idFacturacion, "No se ha recuperado ningún registro. Revise que todas las designaciones están asociadas a un EJG.");
            }
            bw.flush();
        } catch (Exception e) {
            LOGGER.error("error en rellenaFichero: " + e);
            hayErrores = true;
        }
        return hayErrores;
    }

    private void inicializarContador(Short idInstitucion) {
        AdmContadorKey cKey = new AdmContadorKey();
        cKey.setIdcontador(CONTADOR_CAM);
        cKey.setIdinstitucion(idInstitucion);
        contadorOriginal = contadorMapper.selectByPrimaryKey(cKey);
        anyoSufijo = contadorOriginal.getIdcamposufijo();
        idIntercambio = contadorOriginal.getContador();
        longitudContador = contadorOriginal.getLongitudcontador();
    }

    private void actualizarContador(Short idInstitucion) {
        AdmContadorKey cKey = new AdmContadorKey();
        cKey.setIdcontador(CONTADOR_CAM);
        cKey.setIdinstitucion(idInstitucion);
        AdmContador contadorNuevo = contadorMapper.selectByPrimaryKey(cKey);
        if (longitudContador > contadorNuevo.getLongitudcontador()) {
            if (contadorNuevo.getPrefijo().equals(contadorOriginal.getPrefijo()) &&
                    contadorNuevo.getSufijo().equals(contadorOriginal.getSufijo())) {
                contadorNuevo.setContador(Long.valueOf(longitudContador));
                contadorNuevo.setUsumodificacion(idUsuario);
                contadorNuevo.setFechamodificacion(new Date());
                contadorMapper.updateByPrimaryKey(contadorNuevo);
            }
        }


    }


    private boolean procesaFila(Short idInstitucion, String idFacturacion, BufferedWriter bw, Map<String, Object> mFila, boolean ultima, boolean hayErrores) throws IOException {
    	String anioEJG = "", numEJG = "", desAnio = "", desNumero = "", numeroActuacion = "";
    	for (String col : mFila.keySet()) {
            try {
                //hayErrores = procesaColumna(idInstitucion, idFacturacion, bw, col, mFila.get(col));
            	  String valor = mFila.get(col) != null ? mFila.get(col).toString() : null;
                  
                  if (EJG_NUMERO.equalsIgnoreCase(col)) {
                      numEJG = valor;
                  } else if (EJG_ANIO.equalsIgnoreCase(col)) {
                      anioEJG = valor;
                  } else if (DESIGNA_CODIGO.equalsIgnoreCase(col)) {
                      desNumero = valor;
                  } else if (DESIGNA_ANIO.equalsIgnoreCase(col)) {
                      desAnio = valor;
                  } else if (NUMERO_ACTUACION.equalsIgnoreCase(col)) {
                      numeroActuacion = valor;
                  }

                  hayErrores = valida(idInstitucion, idFacturacion, numEJG, anioEJG, desNumero, desAnio, numeroActuacion, col, valor) || hayErrores;

                  if (imprimirColumna(col)) {
                  	String aux = "";
                  	if(valor != null) {
                  		aux = valor.replaceAll("\\s+", "");
                  	}
                      if (valor != null && TOKEN_NL.equals(valor)) {
                          bw.newLine();
                      } else if (CAB2_NUMERO_INTERCAMBIO.equalsIgnoreCase(col)) {
                          bw.write(utilidadesStringFormatea(++idIntercambio, longitudContador, true) + anyoSufijo);
                      } else {
                          bw.write(valor != null && aux.length() != 0  ? valor : "");
                      }
                  }
            } catch (Exception e) {
                LOGGER.error("error al procesar la columna " + col);
            }
        }
        if (!ultima) {
            bw.newLine();
        }

        return hayErrores;
    }



    private  String utilidadesStringFormatea(Object dato, int longitud, boolean numerico) {
        String salida = "";
        if(dato == null) {
        	if(numerico) {
        		salida = relleno("0",longitud);
        	}else {
        		salida = relleno(" ",longitud);
        	}
        }else {
        	String datoAux = dato.toString();
        	if(datoAux.length() == 0) {
        		if(numerico) {
            		salida = relleno("0",longitud);
            	}else {
            		salida = relleno(" ",longitud);
            	}
        	}else if(datoAux.length() > longitud) {
        		if(numerico) {
            		salida = datoAux.substring(datoAux.length() - longitud, datoAux.length());
            	}else {
            		salida = datoAux.substring(0, longitud);
            	}
        	}else if(datoAux.length() < longitud) {
        		if(numerico) {
        			salida = relleno("0",longitud - datoAux.length())+ datoAux;
        		}else {
        			salida = datoAux + relleno(" ",longitud - datoAux.length());
        		}
        	}else {
        		salida = datoAux;
        	}
        }
        return salida;
    }

    private String relleno(String caracter, int longitud) {
    	String salida = "";
    	for (int i = 0; i < longitud; i++) {
			salida += caracter;
		}
    	return salida;
    }

    private boolean imprimirColumna(String colName) {
    	for( String s : this.sColumnasNoImprimir) {
    	if(s.equalsIgnoreCase(colName)) {
    		return false;
    	}
    	}
    	return true;
        //return !sColumnasNoImprimir.contains(colName);
    }

    private boolean valida(Short idInstitucion, String idFacturacion, String numEJG, String anioEJG, String desNumero, String desAnio, String numeroActuacion, String columna, String valor) throws IOException {
        boolean hayError = false;
        PCAJGAlcActIncidencia pcajgAlcActIncidencia = mIncidencias.get(columna);

        if (pcajgAlcActIncidencia != null) {
            boolean muestraMensaje = false;
            if (pcajgAlcActIncidencia.getIdTipoIncidencia().equals(PCAJG_ALC_ACT_TIPO_INCIDENCIA.OBLIGATORIO.getId()) && (valor == null || valor.trim().equals(""))) {
                muestraMensaje = true;
            } else if (pcajgAlcActIncidencia.getIdTipoIncidencia().equals(PCAJG_ALC_ACT_TIPO_INCIDENCIA.BOOLEANO.getId()) && "1".equals(valor)) {
                muestraMensaje = true;
            }

            if (muestraMensaje) {
                String texto = getTexto(anioEJG, numEJG, desAnio, desNumero, numeroActuacion, pcajgAlcActIncidencia.getNivel(), pcajgAlcActIncidencia.getMensaje());
                escribeLog(idInstitucion, idFacturacion, texto);
                hayError = PCAJG_ALC_ACT_NIVEL.ERROR.name().equals(pcajgAlcActIncidencia.getNivel());
            }
        }

        return hayError;
    }

    public Path subirFicheroCAM(Short idInstitucion, String idFacturacion, MultipartFile fichero) throws Exception {
        Path pRuta = getRutaAlmacenFichero(idInstitucion, idFacturacion);
        Files.createDirectories(pRuta);
        Path pFile = pRuta.resolve(fichero.getOriginalFilename());
        Files.copy(fichero.getInputStream(), pFile, StandardCopyOption.REPLACE_EXISTING);
        return pFile;
    }
    
    private void creaRuta(String filePath) {
    	if(filePath == null || filePath.trim().equalsIgnoreCase(""))
    		return;
    	File fileDir = new File(filePath.toString());
    	if(fileDir != null && !fileDir.exists()) {
    		fileDir.mkdirs();
    		SIGAHelper.addPerm777(fileDir);
    	}
    	
    }
	public int execute(String directorio, String tipoFichero, Short idInstitucion, String idFacturacion, String tipoFicheroCAM ,AdmUsuarios usrBean) throws Exception {
		tipoFicheroCAM = tipoFichero;
		List<File> listaFicheros = new ArrayList<File>();
		int erroresGeneral = 0;
		try {
			initInforme();	
			cabeceraLog = "AÑO EJG;NÚMERO EJG;AÑO DESIGNACIÓN;NÚMERO DESIGNACIÓN;NÚMERO ACTUACIÓN;TIPO INCIDENCIA;INCIDENCIA";
			
			String rutaAlm = getRutaFicheroIncidencias( idInstitucion, idFacturacion);			
			File parentFile = new File(rutaAlm);
			parentFile.delete();
			creaRuta(rutaAlm);
		
			
			for (File f : parentFile.listFiles()) {
				  LOGGER.error("Fichero eliminado (" + f.delete() + ") " + f.getAbsolutePath());
			}
			String nombreFichero = getNombreFicheroCAM();
	
			File file = new File(parentFile, nombreFichero);				
			boolean hayErrores = rellenaFichero(file, idInstitucion,idFacturacion,tipoFicheroCAM);	
			if(hayErrores) erroresGeneral = 1;
			LOGGER.info("Generando fichero txt en: " + file.getAbsolutePath());
			
			if (tipoFicheroCAM != null && !tipoFicheroCAM.trim().equals("") && !tipoFicheroCAM.trim().equals(TIPO_FICHERO_CAM.NINGUNO.name())) {
//				actualizar todos o el tipo seleccionado
				PcajgAlcActErrorCam pcajgAlcActErrorCam = new PcajgAlcActErrorCam();
				pcajgAlcActErrorCam.setBorrado((short)1);//borrado
				pcajgAlcActErrorCam.setUsumodificacion(usrBean.getIdusuario());
				pcajgAlcActErrorCam.setFechamodificacion(new Date());
				
				PcajgAlcActErrorCamExample pcajgAlcActErrorCamExample = new PcajgAlcActErrorCamExample();
				PcajgAlcActErrorCamExample.Criteria criteria = pcajgAlcActErrorCamExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdfacturacionEqualTo(Integer.parseInt(idFacturacion));
				if (!tipoFicheroCAM.trim().equals(TIPO_FICHERO_CAM.TODOS.name())) {
					criteria.andCodigoErrorEqualTo(tipoFicheroCAM);
				}
				
				fcsPcajgAlcActErrorCamExtendsMapper.updateByExampleSelective(pcajgAlcActErrorCam, pcajgAlcActErrorCamExample);
				
			}
			
			if (!hayErrores && (tipoFicheroCAM == null || tipoFicheroCAM.trim().equals("") || !tipoFicheroCAM.trim().equals(TIPO_FICHERO_CAM.NINGUNO.name()))) {
				listaFicheros.add(file);
			} else {
				file.delete();
			}
			
			if (closeLogFile()) {
				listaFicheros.add(getFileInformeIncidencias(idInstitucion, idFacturacion));
			}
			
			crearInformeResumenErroresCAM(parentFile,idInstitucion, idFacturacion);
			//File fileErroresCAM = crearInformeErroresCAM(parentFile,idInstitucion, idFacturacion);
			crearInformeErroresCAM(parentFile,idInstitucion, idFacturacion); // Habilitar para generar un fichero de errores mas extenso.
			//if (fileErroresCAM != null && fileErroresCAM.exists()) {
			//	listaFicheros.add(fileErroresCAM);
			//}			

			//return listaFicheros; sustituimos 
			
		} finally {
			closeLogFile();
			
		}
		return erroresGeneral;
		
	}//FIN Execute

	private void crearInformeResumenErroresCAM(File parentFile,Short idInstitucion, String idFacturacion) throws Exception{
		String sentencia = "SELECT * FROM V_PCAJG_ALC_ACT_ERROR_CAM WHERE IDINSTITUCION = " + idInstitucion + " AND IDFACTURACION = " + idFacturacion;	
		
		 List<Map<String, Object>> result = conConsultasExtendsMapper.ejecutarConsultaString(sentencia);
		 
		 crearPestana(parentFile, result, "Resumen");
	}
	
	private void crearInformeErroresCAM(File parentFile, Short idInstitucion, String idFacturacion) throws Exception{
		String sentencia = "SELECT E.IDINSTITUCION, E.IDFACTURACION, E.REGISTRO_ERROR_CAM, E.CODIGO_ERROR, TE.ERROR_DESCRIPCION, E.CODIGO_CAMPO_ERROR" +
				", TC.CAMPO_DESCRIPCION, E.OBSERVACIONES_ERROR, E.EJG_ANIO AS ANIO_EJG, E.EJG_NUMEJG AS NUMBERO_EJG, DECODE(E.BORRADO, 1, 'SI', 'NO') AS HISTORICO" +
		" FROM PCAJG_ALC_ACT_ERROR_CAM E, PCAJG_ALC_TIPOERRORINTERCAMBIO TE, PCAJG_ALC_TIPOCAMPOCARGA TC" +
		" WHERE E.CODIGO_ERROR = TE.ERROR_CODIGO(+)" +
		" AND E.CODIGO_CAMPO_ERROR = TC.CAMPO_CODIGO(+)" +
		" AND E.IDINSTITUCION = " + idInstitucion + 
		" AND E.IDFACTURACION = " + idFacturacion +
		" ORDER BY E.IDENTIFICADOR";
		
		 List<Map<String, Object>> result = conConsultasExtendsMapper.ejecutarConsultaString(sentencia);
		 
		 crearPestana(parentFile, result, "ErroresCAM");
	}
	
	private void crearPestana(File parentFile, List<Map<String, Object>> result, String sheetName) {
		try {
			Workbook workBook = crearExcel(result,sheetName);
			FileOutputStream fileOut;
			String nombreFichero = sheetName + ".xlsx";
			File file = new File(parentFile,nombreFichero);
			fileOut = new FileOutputStream(file);
			workBook.write(fileOut);
			fileOut.close();
			workBook.close();
		} catch (Exception e) {
			LOGGER.info("Error a la hora de crear Pestana",e);

		}
	
		
	}

	private Workbook crearExcel(List<Map<String, Object>> result, String sheetName) {

		LOGGER.info("crearExcel() -> Entrada del servicio para crear el excel con los datos de CAM");

		// Creamos el libro de excel
		Workbook workbook = new SXSSFWorkbook(EXCEL_ROW_FLUSH);
		Sheet sheet = workbook.createSheet(sheetName);

		// Le aplicamos estilos a las cabeceras
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		// headerFont.setItalic(true);
		headerFont.setFontHeightInPoints((short) 14);
		//headerFont.setColor(IndexedColors.BLUE.getIndex());
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Row headerRow = sheet.createRow(0);

		// Recorremos el map y vamos metiendo celdas
		List<String> columnsKey = new ArrayList<String>();
		int rowNum = 1;
		int index = 0;
		Row row = null;

		Map<Integer, CellStyle> mapaEstilos = new HashMap<Integer, CellStyle>();

		CellStyle cellStyleNum = workbook.createCellStyle();
		cellStyleNum.setAlignment(CellStyle.ALIGN_RIGHT);
		
		CellStyle cellStyleString = workbook.createCellStyle();
		cellStyleString.setAlignment(CellStyle.ALIGN_LEFT);
		
		Object campo = null;
		XSSFRichTextString textCell = null;
		
		if (result.size() > 0) {
			for (String value : result.get(0).keySet()) {
				Cell cell = headerRow.createCell(index);
				cell.setCellValue(value);
				cell.setCellStyle(headerCellStyle);
				columnsKey.add(value);
				index++;
			}

			for (Map<String, Object> map : result) {
				
				if (map != null) {
	
					row = sheet.createRow(rowNum++);
					int cell = 0;
	
					
					for (int j = 0; j < columnsKey.size(); j++) {
						campo = map.get(columnsKey.get(j).trim());
						
						if (campo == null || campo.toString().trim() == "") {
							row.createCell(cell).setCellValue("");
						} else {
							Cell celda = row.createCell(cell);
							if (campo instanceof Number) {
								if (!mapaEstilos.containsKey(cell)) {
									mapaEstilos.put(cell, cellStyleNum);
								}
								celda.setCellType(Cell.CELL_TYPE_NUMERIC);
								celda.setCellValue(Double.parseDouble(campo.toString()));
								
							} else if (campo instanceof Date) {
								if (!mapaEstilos.containsKey(cell)) {
									mapaEstilos.put(cell, cellStyleString);
								}
								
								CreationHelper creationHelper = workbook.getCreationHelper();
								
								celda.setCellValue((Date) campo);
								
								CellStyle style1 = workbook.createCellStyle();
								style1.setDataFormat(creationHelper.createDataFormat().getFormat(
										"dd/mm/yyyy hh:mm"));
								celda.setCellStyle(style1);
								
							} else {
								if (!mapaEstilos.containsKey(cell)) {
									mapaEstilos.put(cell, cellStyleString);
								}
								
								celda.setCellType(Cell.CELL_TYPE_STRING);
								textCell = new XSSFRichTextString(campo.toString());
								celda.setCellValue(textCell);
							}
						}
						cell++;
						
					}
				}
			}

			for (int i = 0; i < index; i++) {
				//sheet.autoSizeColumn(j);
				if (mapaEstilos.containsKey(i)) {
					sheet.setDefaultColumnStyle(i, mapaEstilos.get(i));
				}
			}
		}

		LOGGER.info("crearExcel() -> Salida del servicio para crear el excel con los datos de CAM");

		return workbook;

	}
	

}
