package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.*;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public final String EJG_ANIO = "EJG_ANIO";
    public final String EJG_NUMERO = "EJG_NUMERO";
    public final String DESIGNA_ANIO = "DESIGNA_ANIO";
    public final String DESIGNA_CODIGO = "DESIGNA_CODIGO";
    public final String NUMERO_ACTUACION = "NUMERO_ACTUACION";
    public final String CAB2_NUMERO_INTERCAMBIO = "CAB2_NUMERO_INTERCAMBIO";

    private Logger LOGGER = Logger.getLogger(CertificacionFacSJCSServicesCAMHelper.class);

    private static final String TXT_EXT = ".txt";
    private static final String UNDERSCORE = "_";
    private static final String CONTADOR_CAM = "INTERCAMBIOICM";
    private static final String DIRECTORIO_INCIDENCIAS = "informeIncidenciasWS";

    @Autowired
    private GenPropertiesMapper genPropertiesMapper;

    @Autowired
    private AdmInformeMapper admInfMap;

    @Autowired
    private PCAGAlcActIncidenciaMapper incMapper;

    @Autowired
    private AdmContadorMapper contadorMapper;

    @Autowired
    private VWSJE2003DesignaMapper dMapper;

    @Autowired
    FacturacionSJCSHelper colaHelper;

    Map<String, PCAJGAlcActIncidencia> mIncidencias;
    Set<String> sColumnasNoImprimir;

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
        Predicate<PCAJGAlcActIncidencia> pIncNoImprimir = inc -> inc.getIdTipoIncidencia().equals(PCAJG_ALC_ACT_TIPO_INCIDENCIA.NO_IMPRIMIR.getId());
        mIncidencias = lInc.stream().filter(pIncNoImprimir.negate()).collect(Collectors.toMap(x -> x.getCampo(), x -> x));
        sColumnasNoImprimir = lInc.stream().filter(pIncNoImprimir).map(x -> x.getCampo().toUpperCase()).collect(Collectors.toSet());
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


    private String getRutaFicheroIncidencias(Short idInstitucion, String idFacturacion) {
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

    private String getNombreFicheroCAM(Short idInstitucion, Integer userName) {
        final String PATTERN = "ddMMYYYHHMMss";


        AdmInformeKey infK = new AdmInformeKey();
        infK.setIdplantilla(1);
        infK.setIdinstitucion(idInstitucion);
        AdmInforme inf = admInfMap.selectByPrimaryKey(infK);

        String date = new SimpleDateFormat(PATTERN).format(new Date());
        String nombre = inf.getNombresalida() + UNDERSCORE + idInstitucion + UNDERSCORE + userName + UNDERSCORE + date + TXT_EXT;
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
            rutaFichero = rutaPadre.resolve(getNombreFicheroCAM(idInstitucion, idUsuario));
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

        try (FileWriter fw = new FileWriter(file); BufferedWriter bw = new BufferedWriter(fw);) {
            cabeceraLog = "AÑO EJG;NÚMERO EJG;AÑO DESIGNACIÓN;NUMERO DESIGNACIÓN;NÚMERO ACTUACIÓN;TIPO INCIDENCIA;INCIDENCIA";

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
        for (String col : mFila.keySet()) {
            try {
                hayErrores |= procesaColumna(idInstitucion, idFacturacion, bw, col, mFila.get(col));
            } catch (Exception e) {
                LOGGER.error("error al procesar la columna " + col);
            }
        }
        if (!ultima) {
            bw.newLine();
        }

        return hayErrores;
    }


    private boolean procesaColumna(Short idInstitucion, String idFacturacion, BufferedWriter bw, String columna, Object oValor) throws IOException {
        boolean hayErrores = false;
        String valor = oValor != null ? oValor.toString() : null;
        String anioEJG = "", numEJG = "", desAnio = "", desNumero = "", numeroActuacion = "";
        if (EJG_NUMERO.equalsIgnoreCase(columna)) {
            numEJG = valor;
        } else if (EJG_ANIO.equalsIgnoreCase(columna)) {
            anioEJG = valor;
        } else if (DESIGNA_CODIGO.equalsIgnoreCase(columna)) {
            desNumero = valor;
        } else if (DESIGNA_ANIO.equalsIgnoreCase(columna)) {
            desAnio = valor;
        } else if (NUMERO_ACTUACION.equalsIgnoreCase(columna)) {
            numeroActuacion = valor;
        }

        hayErrores = valida(idInstitucion, idFacturacion, numEJG, anioEJG, desNumero, desAnio, numeroActuacion, columna, valor);

        if (imprimirColumna(columna)) {
            if (valor != null && TOKEN_NL.equals(valor)) {
                bw.newLine();
            } else if (CAB2_NUMERO_INTERCAMBIO.equalsIgnoreCase(columna)) {
                bw.write(utilidadesStringFormatea(++idIntercambio, longitudContador, true) + anyoSufijo);
            } else {
                bw.write(valor != null ? valor : "");
            }
        }
        return hayErrores;
    }

    private static String utilidadesStringFormatea(Object dato, Integer longitud, boolean numerico) {
        String valor = dato != null ? dato.toString() : "";
        if (numerico) {
            valor = padLeft(valor, longitud).replace(' ', '0');
        } else {
            valor = padRight(valor, longitud);
        }
        return valor;
    }

    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    private boolean imprimirColumna(String colName) {
        return !sColumnasNoImprimir.contains(colName);
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


}
