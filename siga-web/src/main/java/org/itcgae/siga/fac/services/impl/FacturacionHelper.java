package org.itcgae.siga.fac.services.impl;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.itcgae.siga.DTO.fac.*;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.FoUtils;
import org.itcgae.siga.commons.utils.SIGAHelper;
import org.itcgae.siga.commons.utils.UtilidadesNumeros;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmLenguajes;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoKey;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenMandatosCuentasbancarias;
import org.itcgae.siga.db.entities.CenMandatosCuentasbancariasExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacFacturaKey;
import org.itcgae.siga.db.entities.FacFacturacionprogramada;
import org.itcgae.siga.db.entities.FacPlantillafacturacion;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosKey;
import org.itcgae.siga.db.mappers.AdmLenguajesMapper;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.CenMandatosCuentasbancariasMapper;
import org.itcgae.siga.db.mappers.CenPaisMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacLineafacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacPlantillafacturacionExtendsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.core.io.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FacturacionHelper {

    private final Logger LOGGER = Logger.getLogger(FacturacionHelper.class);

    @Autowired
    private FacFacturaExtendsMapper facFacturaExtendsMapper;

    @Autowired
    private GenParametrosExtendsMapper genParametrosExtendsMapper;

    @Autowired
    private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

    @Autowired
    private GenPropertiesMapper genPropertiesMapper;

    @Autowired
    private FacPlantillafacturacionExtendsMapper facPlantillafacturacionExtendsMapper;

    @Autowired
    private CenClienteExtendsMapper cenClienteExtendsMapper;

    @Autowired
    private AdmLenguajesMapper admLenguajesMapper;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private GenRecursosMapper genRecursosMapper;

    @Autowired
    private CenInstitucionMapper cenInstitucionMapper;

    @Autowired
    private CenPersonaMapper cenPersonaMapper;

    @Autowired
    private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;

    @Autowired
    private CenPaisMapper cenPaisMapper;

    @Autowired
    private CenMandatosCuentasbancariasMapper cenMandatosCuentasbancariasMapper;

    @Autowired
    private FacLineafacturaExtendsMapper facLineafacturaExtendsMapper;

    private final String CTR = "%%";
    
	private static final int EXCEL_ROW_FLUSH = 1000;
	
    protected static final String LOG_FAC_PREFIX = "LOG_FAC";
    
    protected static final String FACTURACION_DIRECTORIO_FISICO_LOG_PROGRAMACION = "facturacion.directorioFisicoLogProgramacion";

	

    public File generarPdfFacturaFirmada(String idFactura, String idInstitucion, AdmUsuarios usuario) throws Exception {
        return generarPdfFacturaFirmada(idFactura, idInstitucion, false, usuario);
    }

    public File generarPdfFacturaFirmada(String idFactura, String idInstitucion, boolean bRegenerar, AdmUsuarios usuario) throws Exception {

        File ficheroPDF;

        FacFacturaKey facFacturaKey = new FacFacturaKey();
        facFacturaKey.setIdfactura(idFactura);
        facFacturaKey.setIdinstitucion(Short.valueOf(idInstitucion));

        FacFactura facFactura = facFacturaExtendsMapper.selectByPrimaryKey(facFacturaKey);

        try {

            // Generamos el fichero pdf de la factura
            ficheroPDF = generarPdfFacturaFirmada(facFactura, bRegenerar, usuario);

        } catch (Exception e) {
            throw e;
        }

        return ficheroPDF;
    }

    private File generarPdfFacturaFirmada(FacFactura facFactura, boolean bRegenerar, AdmUsuarios usuario) throws Exception {

        File fFicheroFirmado;

        try {

            File filePDF = generarPdfFacturaSinFirmar(facFactura, bRegenerar, usuario);

            if (filePDF == null) {
                throw new Exception("Error al generar la factura. Fichero devuelto es nulo");
            } else {
                LOGGER.info("DESPUES DE LA GENERACION DE LA FACTURA: " + filePDF.getAbsolutePath());
                LOGGER.info("Existe el fichero: " + (filePDF.exists() ? "SI" : "NO"));
            }

            // Genero una carpeta con las firmas
            String sRutaFirmas = filePDF.getParentFile().getPath() + File.separator + "firmas";

            File fRutaFirmas = new File(sRutaFirmas);

            fRutaFirmas.mkdirs();
            SIGAHelper.addPerm777(fRutaFirmas);

            if (!fRutaFirmas.canWrite()) {
                throw new Exception("El path donde almacenar las facturas no tiene los permisos adecuados.");
            }

            // Genero una copia del pdf que se va a firmar
            fFicheroFirmado = new File(sRutaFirmas + File.separator + filePDF.getName());
            copyFile(filePDF, fFicheroFirmado);

            // Firmo el pdf
            boolean isFirmadoOk = firmarPDF(fFicheroFirmado, facFactura.getIdinstitucion().toString());
            LOGGER.info("PDF FIRMADO:: " + isFirmadoOk);

            // Hay que borrar el pdf sin firma si no tiene numero de factura
            if (facFactura.getNumerofactura() == null || facFactura.getNumerofactura().equals("")) {
                filePDF.delete();
            }

        } catch (Exception e) {
            throw e;
        }

        return fFicheroFirmado;
    }

    private File generarPdfFacturaSinFirmar(FacFactura facFactura, boolean bRegenerar, AdmUsuarios usuario) throws Exception {

        File fPdf;

        try {

            String idFacturaParametro = "-1";

            if (facFactura != null && facFactura.getIdfactura() != null) {
                idFacturaParametro = facFactura.getIdfactura();
            }

            // Obtenemos el numero de colegiado
            CenColegiadoKey cenColegiadoKey = new CenColegiadoKey();
            cenColegiadoKey.setIdinstitucion(facFactura.getIdinstitucion());
            cenColegiadoKey.setIdpersona(facFactura.getIdpersona());
            CenColegiado cenColegiado = cenColegiadoExtendsMapper.selectByPrimaryKey(cenColegiadoKey);

            String nColegiado = "";
            if (cenColegiado != null) {
                nColegiado = cenColegiado.getNcolegiado();
            }

            // obtener ruta almacen
            String idSerieIdProgramacion = facFactura.getIdseriefacturacion() + "_" + facFactura.getIdprogramacion();

            String rutaAlmacen = getProperty(SigaConstants.PARAMETRO_DIRECTORIO_FISICO_FACTURA_PDF) + getProperty(SigaConstants.PARAMETRO_DIRECTORIO_FACTURA_PDF)
                    + File.separator + facFactura.getIdinstitucion() + File.separator + idSerieIdProgramacion;

            File rutaPDF = new File(rutaAlmacen);

            rutaPDF.mkdirs();
            SIGAHelper.addPerm777(rutaPDF);

            if (!rutaPDF.canWrite()) {
                throw new Exception("El path donde almacenar las facturas no tiene los permisos adecuados.");
            }

            //para las facturas que estan generadas pero no confirmadas por lo que no tienen numero se le pondra de nombre del idfactura y se borrara una vez descargada
            String nombrePDF = "";
            if (facFactura.getNumerofactura() == null || facFactura.getNumerofactura().equals("")) {
                nombrePDF = nColegiado + "-" + facFactura.getIdfactura();
            } else { // Contiene numero de factura, por lo tanto, esta confirmada
                nombrePDF = nColegiado + "-" + validarNombreFichero(facFactura.getNumerofactura());
            }

            // Buscamos si el fichero existe
            fPdf = new File(rutaAlmacen + File.separator + nombrePDF + ".pdf");

            if (bRegenerar || !fPdf.exists()) { // Lo regeneramos en caso de que no exista

                List<FacPlantillafacturacion> facPlantillafacturacionList = facPlantillafacturacionExtendsMapper.getPlantillaSerieFacturacion(facFactura.getIdinstitucion().toString(), facFactura.getIdseriefacturacion().toString());

                String modelo = "";
                if (facPlantillafacturacionList != null && facPlantillafacturacionList.size() > 0) {
                    modelo = facPlantillafacturacionList.get(0).getPlantillapdf();
                }

                // Obtenemos el lenguaje del cliente
                CenClienteKey cenClienteKey = new CenClienteKey();
                cenClienteKey.setIdinstitucion(facFactura.getIdinstitucion());
                cenClienteKey.setIdpersona(facFactura.getIdpersona());
                CenCliente cenCliente = cenClienteExtendsMapper.selectByPrimaryKey(cenClienteKey);

                String idioma = cenCliente.getIdlenguaje();

                // RGG 26/02/2007 cambio en los codigos de lenguajes
                AdmLenguajes admLenguajes = admLenguajesMapper.selectByPrimaryKey(idioma);
                String idiomaExt = admLenguajes.getCodigoext();

                String rutaPlantilla = getProperty(SigaConstants.PARAMETRO_FACTURACION_DIRECTORIO_FISICO_PLANTILLA_FACTURA_JAVA) + getProperty(SigaConstants.PARAMETRO_FACTURACION_DIRECTORIO_PLANTILLA_FACTURA_JAVA) +
                        File.separator + facFactura.getIdinstitucion() + File.separator + modelo;
                String nombrePlantilla = "factura_" + idiomaExt + ".fo";

                // Utilizamos la ruta de la plantilla para el temporal
                String rutaServidorTmp = rutaPlantilla + File.separator + "tmp_factura_" + System.currentTimeMillis();

                String contenidoPlantilla = obtenerContenidoPlantilla(rutaPlantilla, nombrePlantilla);

                LOGGER.info("ANTES DE GENERAR EL INFORME.");
                fPdf = generarInforme(rutaServidorTmp, contenidoPlantilla, rutaAlmacen, nombrePDF, idFacturaParametro, facFactura.getIdinstitucion(), usuario);
                LOGGER.info("DESPUES DE GENERAR EL INFORME EN  " + rutaAlmacen);

            }

        } catch (Exception e) {
            throw e;
        }

        return fPdf;
    }

    public File generarInforme(String rutaServidorTmp, String contenidoPlantilla, String rutaServidorDescargas, String nombreFicheroPDF, String idFactura, Short idInstitucion, AdmUsuarios usuario) throws Exception {

        File ficheroFOP = null;
        File ficheroPDF = null;
        File rutaTmp = null;

        try {

            nombreFicheroPDF = validarNombreFichero(nombreFicheroPDF);

            //Crea la ruta temporal
            rutaTmp = new File(rutaServidorTmp);
            ficheroFOP = new File(rutaTmp + File.separator + nombreFicheroPDF + System.currentTimeMillis() + ".fo");

            try {
                ficheroFOP.getParentFile().mkdirs();
                ficheroFOP.createNewFile();
                SIGAHelper.addPerm777(ficheroFOP);
            } catch (IOException ioe) {
                throw new Exception("Error al crear el fichero de firmas");
            }

            // Generacion del fichero .FOP para este usuario a partir de la plantilla .FO
            LOGGER.info("ANTES DE REEMPLAZAR LOS DATOS DE LA PLANTILLA.");
            String content = reemplazarDatos(contenidoPlantilla, idFactura, idInstitucion.toString(), usuario);
            LOGGER.info("DESPUES DE REEMPLAZAR LOS DATOS DE LA PLANTILLA.");
            setFileContent(ficheroFOP, content);

            //Crea el fichero. Si no existe el directorio de la institucion para la descarga lo crea.
            ficheroPDF = new File(rutaServidorDescargas + File.separator + nombreFicheroPDF + ".pdf");

            try {
                ficheroPDF.getParentFile().mkdirs();
                ficheroPDF.createNewFile();
                SIGAHelper.addPerm777(ficheroPDF);
            } catch (IOException ioe) {
                throw new Exception("Error al crear el fichero de firmas");
            }

            LOGGER.info("RUTA DONDE SE UBICAR EL INFORME. " + ficheroPDF);
            LOGGER.info("ANTES DE GENERAR EL PDF.");
            FoUtils.convertFO2PDF(ficheroFOP, ficheroPDF, rutaTmp.getParent());
            LOGGER.info("PDF GENERADO.");

        } catch (Exception e) {
            throw new Exception("Error al generar el informe: " + e.getLocalizedMessage());
        } finally {

            if (ficheroFOP != null && ficheroFOP.exists()) {
                ficheroFOP.delete();
            }
            if (rutaTmp != null && rutaTmp.exists()) {
                rutaTmp.delete();
            }
        }

        return ficheroPDF;
    }

    private void setFileContent(File file, String content) throws Exception {

        try {

            StringReader is = new StringReader(content);
            OutputStream os = new FileOutputStream(file);
            int car;

            while ((car = is.read()) != -1) {
                os.write(car);
            }

            os.flush();
            os.close();
            is.close();

        } catch (IOException e) {
            throw new Exception("La operación de inserción de un nuevo fichero no se ha realizado con éxito.", e);
        }
    }

    private String reemplazarDatos(String plantillaFO, String idFacturaDemonio, String idInstitucion, AdmUsuarios usuario) throws Exception {
        Hashtable htDatos = null;

        String plantilla = plantillaFO;
        String idFactura = idFacturaDemonio;
        String institucion = idInstitucion;

        //Cargar datos fijos
        htDatos = cargarDatosFijos(institucion, idFactura, usuario);

        //Cargar listado de letrados en cola
        List<LineaImpresionInformeDTO> vLineasFactura = getLineasImpresionInforme(institucion, idFactura);
        plantilla = reemplazaRegistros(plantilla, vLineasFactura, null, "LINEAFACTURA");


        {    // Comprobamos si tenemos que mostar el texto de "exento de iva" (si hay algun registro con iva = 0.0%)
            htDatos.put("EXENTO_IVA", "");
            if (vLineasFactura != null) {
                for (int i = 0; i < vLineasFactura.size(); i++) {
                    LineaImpresionInformeDTO h = vLineasFactura.get(i);
                    Float f = Float.valueOf(String.valueOf(h.getIva_linea_aux()));
                    if (f != null) {
                        if (f.doubleValue() == 0.0f) {
                            String idioma = usuario.getIdlenguaje();
                            htDatos.put("EXENTO_IVA", getMensajeIdioma(idioma, "messages.factura.LIVA"));
                            break;
                        }
                    }
                }
            }
        }

        plantilla = reemplazaVariables(htDatos, plantilla);
        return plantilla;
    }

    public List<LineaImpresionInformeDTO> getLineasImpresionInforme(String institucion, String idFactura) {
        List<LineaImpresionInformeDTO> vLineasFactura = facLineafacturaExtendsMapper.getLineasImpresionInforme(institucion, idFactura);

        for (LineaImpresionInformeDTO linea : vLineasFactura) {
            double importeNeto = UtilidadesNumeros.redondea(new Double(linea.getCantidad_linea()).doubleValue() * new Double(linea.getPrecio_linea()).doubleValue(), 2);
            double importeIva = UtilidadesNumeros.redondea(new Double(linea.getCantidad_linea()).doubleValue() * new Double(linea.getPrecio_linea()).doubleValue() * new Double(linea.getIva_linea()).doubleValue() / 100, 2);
            double importeTotal = importeNeto + importeIva;

            linea.setNeto_linea(UtilidadesNumeros.formato(new Double(importeNeto).toString()));
            linea.setImporte_iva_linea(UtilidadesNumeros.formato(new Double(importeIva).toString()));
            linea.setTotal_linea(UtilidadesNumeros.formato(new Double(importeTotal).toString()));

            linea.setIva_linea_aux(Float.valueOf(String.valueOf(linea.getIva_linea())));
            linea.setIva_linea(UtilidadesNumeros.formato(Float.valueOf(String.valueOf(linea.getIva_linea())).floatValue()));
            linea.setPrecio_linea(UtilidadesNumeros.formato(Float.valueOf(String.valueOf(linea.getPrecio_linea())).floatValue()));
        }

        return vLineasFactura;
    }

    public String reemplazaVariables(Hashtable htDatos, String plantillaFO) {
        return UtilidadesString.reemplazaParametros(plantillaFO, CTR, htDatos);
    }

    public String reemplazaRegistros(String plantillaFO, List<LineaImpresionInformeDTO> registros, Hashtable htDatos, String delim) throws Exception {
        String plantilla = plantillaFO;
        String delimIni = CTR + "INI_" + delim + CTR;
        String delimFin = CTR + "FIN_" + delim + CTR;
        String sAux = "";

        // RGG 09/07/2008
        if (registros == null || registros.isEmpty()) {
            LOGGER.info("MASTERREPORT: no hay registros");
            // cuando no existen datos se busca la marca INI_TODO_xxx para sustituirla por NADA.
            // Estas marcas deben estas fuera de las que pretendemos cambiar con datos.
            delimIni = CTR + "INI_TODO_" + delim + CTR;
            delimFin = CTR + "FIN_TODO_" + delim + CTR;
            sAux = "";
        } else {

            String delimTodoIni = CTR + "INI_TODO_" + delim + CTR;
            String delimTodoFin = CTR + "FIN_TODO_" + delim + CTR;

            String auxAux = UtilidadesString.encuentraEntreMarcas(plantilla, delimTodoIni, delimTodoFin);

            plantilla = UtilidadesString.reemplazaEntreMarcasCon(plantilla, delimTodoIni, delimTodoFin, auxAux);

            if (registros != null && !registros.isEmpty()) {

                String plantillaRegistro = UtilidadesString.encuentraEntreMarcas(plantilla, delimIni, delimFin);
                int size = registros.size();
                LOGGER.info("MASTERREPORT: TAMANIO DEL VECTOR PARA REEMPLAZAR DATOS: " + size);
                for (int i = 0; i < size; i++) {
                    LineaImpresionInformeDTO rObj = registros.get(i);
                    Hashtable row = new Hashtable();
                    //ClsLogging.writeFileLog("MASTERREPORT: REGISTRO: "+i,10);

                    // CONVERTIMOS EL OBJETO A HASHTABLE
                    Field[] fields = rObj.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        String name = field.getName().toUpperCase();
                        field.setAccessible(true);
                        Object resultValue = field.get(rObj);
                        if (resultValue != null) {
                            row.put(name, resultValue);
                        }
                    }

                    sAux += UtilidadesString.reemplazaParametros(plantillaRegistro, CTR, row);

                }
            }
        }

        plantilla = UtilidadesString.reemplazaEntreMarcasCon(plantilla, delimIni, delimFin, sAux);

        return plantilla;
    }

    public Hashtable cargarDatosFijos(String institucion, String idFactura, AdmUsuarios usuario) throws Exception {
        Hashtable ht = getDatosImpresionInformeFactura(institucion, idFactura, usuario);
        return ht;
    }

    public Hashtable getDatosImpresionInformeFactura(String idInstitucion, String idFactura, AdmUsuarios usuario) throws Exception {
        Hashtable nuevo = new Hashtable();

        try {

            DatoImpresionInformeFacturaDTO factura = facFacturaExtendsMapper.getDatosImpresionInformeFactura(idInstitucion, idFactura);

            if (factura != null) {

                // RGG 15/02/2007 OBTENEMOS LOS VALORES, CALCULAMOS OTROS Y CAMBIAMOS LAS ETIQUETAS
                String sIdInstitucion = factura.getIdinstitucion();
                CenInstitucion cenInstitucion = cenInstitucionMapper.selectByPrimaryKey(Short.valueOf(sIdInstitucion));
                nuevo.put("NOMBRE_COLEGIO", cenInstitucion.getNombre());

                Long idPersona = cenInstitucion.getIdpersona();
                CenPersona cenPersona = cenPersonaMapper.selectByPrimaryKey(idPersona);
                nuevo.put("CIF_COLEGIO", cenPersona.getNifcif());

                // direccion institucion
                EntradaDireccionEspecificaDTO direccion = cenDireccionesExtendsMapper.getEntradaDireccionEspecifica(idPersona.toString(), sIdInstitucion, SigaConstants.TIPO_DIR_CENSOWEB).get(0);

                nuevo.put("DIRECCION_COLEGIO", direccion.getDomicilio());

                String sPais = direccion.getIdPais();

                if (sPais != null) {

                    String sCodigoPostal = direccion.getCodigoPostal();
                    String sPoblacion = direccion.getPoblacion();
                    String sProvincia = direccion.getProvincia();

                    if ((sPais.equals("") || sPais.equals(SigaConstants.ID_PAIS_ESPANA)) && sCodigoPostal != null && sPoblacion != null && sProvincia != null) {
                        nuevo.put("CODIGO_POSTAL_POBLACION_PROVINCIA_COLEGIO", sCodigoPostal + ", " + sPoblacion + ", " + sProvincia);
                    } else {
                        String sPoblacionExtranjera = direccion.getPoblacionExtranjera();
                        String pais = cenPaisMapper.selectByPrimaryKey(sPais).getNombre();
                        nuevo.put("CODIGO_POSTAL_POBLACION_PROVINCIA_COLEGIO", sCodigoPostal + ", " + sPoblacionExtranjera + ", " + pais);
                    }

                } else {
                    nuevo.put("CODIGO_POSTAL_POBLACION_PROVINCIA_COLEGIO", "-");
                }

                StringBuilder resultado = new StringBuilder();
                direccion = cenDireccionesExtendsMapper.getEntradaDireccionEspecifica(idPersona.toString(), sIdInstitucion, SigaConstants.TIPO_DIR_CENSOWEB).get(0);

                String sTelefono1 = direccion.getTelefono1();
                if (sTelefono1 != null && !sTelefono1.equals("")) {
                    resultado.append(sTelefono1);
                    nuevo.put("TELEFONO1", sTelefono1);
                }

                String sTelefono2 = direccion.getTelefono2();
                if (sTelefono2 != null && !sTelefono2.equals("")) {
                    if (resultado.length() > 0) {
                        resultado.append(", ");
                    }
                    resultado.append(sTelefono2);
                }

                String sMovil = direccion.getMovil();
                if (sMovil != null && !sMovil.equals("")) {
                    if (resultado.length() > 0) {
                        resultado.append(", ");
                    }
                    resultado.append(sMovil);
                }

                String sCorreoElectronico = direccion.getCorreoElectronico();
                if (sCorreoElectronico != null && !sCorreoElectronico.equals("")) {
                    if (resultado.length() > 0) {
                        resultado.append(" ");
                    }
                    resultado.append(sCorreoElectronico);
                }
                nuevo.put("TELEFONOS_EMAIL_COLEGIO", resultado.toString());

                // TRATAMIENTO PARA LA PERSONA DE LA FACTURA
                idPersona = Long.valueOf(factura.getIdpersona());

                String nombre = obtenerNombreApellidos(idPersona.toString());
                if (nombre != null && !nombre.equals("")) {
                    nuevo.put("NOMBRE_CLIENTE", nombre);
                } else {
                    nuevo.put("NOMBRE_CLIENTE", "-");
                }

                direccion = cenDireccionesExtendsMapper.getEntradaDireccionEspecifica(idPersona.toString(), sIdInstitucion, SigaConstants.TIPO_DIR_FACTURACION).get(0);
                if (direccion == null) {
                    throw new Exception("messages.censo.direcciones.facturacion");
                }

                String sDomicilio = direccion.getDomicilio();
                if (sDomicilio != null && !sDomicilio.equals("")) {
                    nuevo.put("DIRECCION_CLIENTE", sDomicilio);
                } else {
                    nuevo.put("DIRECCION_CLIENTE", "-");
                }

                sPais = direccion.getIdPais();
                if (sPais != null) {
                    String sCodigoPostal = direccion.getCodigoPostal();
                    String sPoblacion = direccion.getPoblacion();
                    String sProvincia = direccion.getProvincia();
                    if ((sPais.equals("") || sPais.equals(SigaConstants.ID_PAIS_ESPANA)) && sCodigoPostal != null && sPoblacion != null && sProvincia != null) {
                        nuevo.put("CODIGO_POSTAL_POBLACION_PROVINCIA_CLIENTE", sCodigoPostal + ", " + sPoblacion + ", " + sProvincia);
                    } else {
                        String sPoblacionExtranjera = direccion.getPoblacionExtranjera();
                        String pais = cenPaisMapper.selectByPrimaryKey(sPais).getNombre();
                        nuevo.put("CODIGO_POSTAL_POBLACION_PROVINCIA_CLIENTE", sCodigoPostal + ", " + sPoblacionExtranjera + ", " + pais);
                    }
                } else {
                    nuevo.put("CODIGO_POSTAL_POBLACION_PROVINCIA_CLIENTE", "-");
                }

                resultado = new StringBuilder();
                sTelefono1 = direccion.getTelefono1();
                if (sTelefono1 != null && !sTelefono1.equals("")) {
                    resultado.append(sTelefono1);
                } else {
                    resultado.append("-");
                }

                sTelefono2 = direccion.getTelefono2();
                if (sTelefono2 != null && !sTelefono2.equals("")) {
                    if (resultado.length() > 0) {
                        resultado.append(", ");
                    }
                    resultado.append(sTelefono2);
                }

                sMovil = direccion.getMovil();
                if (sMovil != null && !sMovil.equals("")) {
                    if (resultado.length() > 0) {
                        resultado.append(", ");
                    }
                    resultado.append(sMovil);
                }
                nuevo.put("TELEFONOS_CLIENTE", resultado.toString());

                sCorreoElectronico = direccion.getCorreoElectronico();
                if (sCorreoElectronico != null && !sCorreoElectronico.equals("")) {
                    nuevo.put("EMAIL_CLIENTE", sCorreoElectronico);
                } else {
                    nuevo.put("EMAIL_CLIENTE", "-");
                }

                String sNif = cenPersonaMapper.selectByPrimaryKey(idPersona).getNifcif();
                if (sNif != null && !sNif.equals("")) {
                    nuevo.put("NIFCIF_CLIENTE", sNif);
                } else {
                    nuevo.put("NIFCIF_CLIENTE", "-");
                }

                String sNumeroFactura = factura.getNumerofactura();
                if (sNumeroFactura != null && !sNumeroFactura.equals("")) {
                    nuevo.put("NUMERO_FACTURA", sNumeroFactura);
                } else {
                    nuevo.put("NUMERO_FACTURA", "-");
                }

                SimpleDateFormat sdf = new SimpleDateFormat(SigaConstants.TIMESTAMP_BBDD);
                Date d = sdf.parse(factura.getFechaemision());
                sdf.applyPattern(SigaConstants.DATEST_FORMAT_ONLYDATE);
                String sFechaFactura = sdf.format(d);
                if (sFechaFactura != null && !sFechaFactura.equals("")) {
                    nuevo.put("FECHA_FACTURA", sFechaFactura);
                } else {
                    nuevo.put("FECHA_FACTURA", "-");
                }

                CenColegiadoKey cenColegiadoKey = new CenColegiadoKey();
                cenColegiadoKey.setIdpersona(idPersona);
                cenColegiadoKey.setIdinstitucion(Short.valueOf(sIdInstitucion));
                CenColegiado cenColegiado = cenColegiadoExtendsMapper.selectByPrimaryKey(cenColegiadoKey);

                String sNumeroColegiado = "";

                if (cenColegiado != null) {
                    sNumeroColegiado = cenColegiado.getComunitario().equalsIgnoreCase(SigaConstants.DB_TRUE) ? cenColegiado.getNcomunitario() : cenColegiado.getNcolegiado();
                }

                if (sNumeroColegiado != null && !sNumeroColegiado.equals("")) {
                    nuevo.put("NUMERO_COLEGIADO_FACTURA", sNumeroColegiado);
                } else {
                    nuevo.put("NUMERO_COLEGIADO_FACTURA", "-");
                }

                String sIdCuentaDeudor = factura.getIdcuentadeudor();
                String sFormaPago;
                if (sIdCuentaDeudor != null && !sIdCuentaDeudor.equals("")) {
                    sFormaPago = getMensajeIdioma(usuario.getIdlenguaje(), "facturacion.abonosPagos.boton.pagoDomiciliacionBanco");
                } else {
                    String sIdCuenta = factura.getIdcuenta();
                    if (sIdCuenta != null && !sIdCuenta.equals("")) {
                        String sIBAN = factura.getIban();
                        String sTitular = factura.getTitular();
                        sFormaPago = UtilidadesString.mostrarIBANConAsteriscos(sIBAN);
                        nuevo.put("TITULARCUENTA", sTitular);

                        CenMandatosCuentasbancariasExample hMandatosCuentasBancarias = new CenMandatosCuentasbancariasExample();
                        CenMandatosCuentasbancariasExample.Criteria criteria = hMandatosCuentasBancarias.createCriteria();
                        criteria.andIdinstitucionEqualTo(Short.valueOf(sIdInstitucion))
                                .andIdpersonaEqualTo(idPersona)
                                .andIdcuentaEqualTo(Short.valueOf(sIdCuenta));


                        List<CenMandatosCuentasbancarias> vMandatosCuentasBancarias = null;
                        String sIdMandato = factura.getIdmandato();
                        String sRefMandatoSepa = factura.getRefmandatosepa();
                        if (sIdMandato != null && !"".equals(sIdMandato)) {
                            criteria.andIdmandatoEqualTo(Short.valueOf(sIdMandato));
                            vMandatosCuentasBancarias = cenMandatosCuentasbancariasMapper.selectByExample(hMandatosCuentasBancarias);
                        } else if (sRefMandatoSepa != null && !"".equals(sRefMandatoSepa)) {
                            criteria.andRefmandatosepaEqualTo(sRefMandatoSepa);
                            vMandatosCuentasBancarias = cenMandatosCuentasbancariasMapper.selectByExample(hMandatosCuentasBancarias);
                        }
                        if (vMandatosCuentasBancarias != null && vMandatosCuentasBancarias.size() > 0) {
                            CenMandatosCuentasbancarias bMandatosCuentasBancarias = vMandatosCuentasBancarias.get(0);
                            String sNifCifDeudor = bMandatosCuentasBancarias.getDeudorId();
                            nuevo.put("NIFCIF_TITULAR", sNifCifDeudor);
                        }
                    } else {
                        sFormaPago = getMensajeIdioma(usuario.getIdlenguaje(), "facturacion.abonosPagos.boton.pagoCaja");
                    }
                }
                nuevo.put("FORMA_PAGO_FACTURA", sFormaPago);

                String sObservaciones = factura.getObservaciones() == null ? "" : factura.getObservaciones();
                nuevo.put("OBSERVACIONES", sObservaciones);

                String sObservacionesInforme = factura.getObservinforme() == null ? "" : factura.getObservinforme();
                nuevo.put("OBSERVINFORME", sObservacionesInforme);

                String sDescripcionEstado = factura.getDescripcion_estado();
                sDescripcionEstado = getMensajeIdioma(usuario.getIdlenguaje(), sDescripcionEstado);
                nuevo.put("ESTADO", sDescripcionEstado);

                // formateo de valores
                nuevo.put("ANTICIPADO", UtilidadesNumeros.formato(factura.getAnticipado()));
                nuevo.put("COMPENSADO", UtilidadesNumeros.formato(factura.getCompensado()));
                nuevo.put("POR_CAJA", UtilidadesNumeros.formato(factura.getPor_caja()));
                nuevo.put("POR_SOLOCAJA", UtilidadesNumeros.formato(factura.getPor_solocaja()));
                nuevo.put("POR_SOLOTARJETA", UtilidadesNumeros.formato(factura.getPor_solotarjeta()));
                nuevo.put("POR_BANCO", UtilidadesNumeros.formato(factura.getPor_banco()));
                nuevo.put("TOTAL_FACTURA", UtilidadesNumeros.formato(factura.getTotal_factura()));
                nuevo.put("TOTAL_PAGOS", UtilidadesNumeros.formato(factura.getTotal_pagos()));
                nuevo.put("PENDIENTE_PAGAR", UtilidadesNumeros.formato(factura.getPendiente_pagar()));
                nuevo.put("IMPORTE_NETO", UtilidadesNumeros.formato(factura.getImporte_neto()));
                nuevo.put("IMPORTE_IVA", UtilidadesNumeros.formato(factura.getImporte_iva()));

            }

        } catch (Exception e) {
            throw new Exception("Error al obtener los datos para el informe de una factura.", e);
        }

        return nuevo;
    }

    public String obtenerNombreApellidos(String idPersona) throws Exception {

        String nombre = "";

        try {

            CenPersona cenPersona = cenPersonaMapper.selectByPrimaryKey(Long.valueOf(idPersona));

            if (cenPersona != null) {

                nombre = cenPersona.getNombre();

                if (cenPersona.getApellidos1() != null && !cenPersona.getApellidos1().equals("#NA")) {
                    nombre += " " + cenPersona.getApellidos1();
                }

                if (cenPersona.getApellidos2() != null && !cenPersona.getApellidos2().equals("#NA")) {
                    nombre += " " + cenPersona.getApellidos2();
                }

            }

        } catch (Exception e) {
            throw new Exception("Error al obtener el nombre y apellidos", e);
        }

        return nombre;
    }

    public String getMensajeIdioma(String idLenguaje, String idRecurso) {

        GenRecursosKey genRecursosKey = new GenRecursosKey();
        genRecursosKey.setIdlenguaje(idLenguaje);
        genRecursosKey.setIdrecurso(idRecurso);

        GenRecursos genRecursos = genRecursosMapper.selectByPrimaryKey(genRecursosKey);

        return genRecursos.getDescripcion();
    }

    // OBTENCION DE LA PLANTILLA FO:
    public String obtenerContenidoPlantilla(String rutaServidorPlantillas, String nombrePlantilla) throws Exception {

        LOGGER.info("*************** PLANTILLA ENTRADA: " + rutaServidorPlantillas + File.separator + nombrePlantilla);
        String barraPlantilla = "";
        File plantillaFO = null;
        
        if (rutaServidorPlantillas.indexOf("/") > -1) {
            barraPlantilla = "/";
        }

        if (rutaServidorPlantillas.indexOf("\\") > -1) {
            barraPlantilla = "\\";
        }

        if (barraPlantilla.equals("")) {
            barraPlantilla = File.separator;
        }
        
        try {
            plantillaFO = new File(rutaServidorPlantillas + barraPlantilla + nombrePlantilla);
            if (!plantillaFO.exists()) {
            	LOGGER.warn("El directorio de plantillas no existe o no esta bien configurado: " + plantillaFO.getAbsolutePath());
                throw new Exception("El directorio de plantillas no existe o no esta bien configurado");
            } else if (!plantillaFO.canRead()) {
            	LOGGER.error("Error de lectura del fichero FOP: " + plantillaFO.getAbsolutePath());
                throw new Exception("Error de lectura del fichero FOP: " + plantillaFO.getAbsolutePath());
            }
		} catch (Exception e) {
			throw new Exception("ObtenerContenidoPlantilla ERROR: " + e.getMessage());
		}
        
        LOGGER.info("*************** PLANTILLA SALIDA : " + rutaServidorPlantillas + File.separator + nombrePlantilla);
        return getFileContent(plantillaFO);
    }

    private String getFileContent(File file) throws Exception {
        String content;

        if (!file.exists()) {
            throw new Exception("El fichero " + file.getName() + " no existe");
        }
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            int nBytes = is.available();
            byte buffer[] = new byte[nBytes];
            is.read(buffer, 0, nBytes);
            content = new String(buffer);
            is.close();
            return content;
        } catch (IOException e) {
            try {
                is.close();
            } catch (Exception eee) {
            }
            throw new Exception("No es posible leer el fichero.", e);
        }
    }

    private String validarNombreFichero(String nombreFichero) {

        char caracter = '_';
        nombreFichero = nombreFichero.replace('\\', caracter);
        nombreFichero = nombreFichero.replace('/', caracter);
        nombreFichero = nombreFichero.replace(':', caracter);
        nombreFichero = nombreFichero.replace('*', caracter);
        nombreFichero = nombreFichero.replace('?', caracter);
        nombreFichero = nombreFichero.replace('\"', caracter);
        nombreFichero = nombreFichero.replace('<', caracter);
        nombreFichero = nombreFichero.replace('>', caracter);
        nombreFichero = nombreFichero.replace('|', caracter);
        return nombreFichero;
    }

    public String getProperty(final String parametro) {
        return getProperty(parametro, null);
    }

    public String getProperty(final String parametro, final String fichero) {

        String valor = null;

        GenPropertiesExample genPropertiesExample = new GenPropertiesExample();
        GenPropertiesExample.Criteria criteria = genPropertiesExample.createCriteria().andParametroEqualTo(parametro);

        if (fichero != null && fichero.trim().length() > 0) {
            criteria.andFicheroEqualTo(fichero);
        }

        List<GenProperties> genPropertiesList = genPropertiesMapper.selectByExample(genPropertiesExample);

        if (genPropertiesList != null && !genPropertiesList.isEmpty()) {
            valor = genPropertiesList.get(0).getValor();
        }

        return valor;
    }

    private void copyFile(File s, File t) {

        try {

            FileChannel in = (new FileInputStream(s)).getChannel();
            FileChannel out = (new FileOutputStream(t)).getChannel();
            in.transferTo(0, s.length(), out);
            in.close();
            out.close();

        } catch (Exception e) {
            LOGGER.error("No se pudo copiar el fichero");
        }
    }

    public boolean firmarPDF(File fIn, String idInstitucion) {

        boolean respuesta = false;

        try {
            LOGGER.info("VOY A FIRMAR EL PDF: " + fIn.getAbsolutePath());
            respuesta = firmarPDF(new Short(idInstitucion), fIn.getAbsolutePath());
        } catch (Exception e) {
            LOGGER.error("***************** ERROR DE FIRMA DIGITAL EN DOCUMENTO *************************");
            LOGGER.error("Error al FIRMAR el PDF de la institucion: " + idInstitucion);
            LOGGER.error("*******************************************************************************");
            e.printStackTrace();
            LOGGER.error("*******************************************************************************");
        }

        return respuesta;
    }

    private boolean firmarPDF(Short idInstitucion, String nombreFicheroEntrada) {

        boolean respuesta = false;
        FileInputStream fisID = null;
        FileOutputStream fos = null;

        try {

            GregorianCalendar gcFecha = new GregorianCalendar();

            GenParametrosKey genParametrosKey = new GenParametrosKey();
            genParametrosKey.setModulo(SigaConstants.MODULO_CER);
            genParametrosKey.setParametro(SigaConstants.PARAMETRO_PATH_CERTIFICADOS_DIGITALES);
            genParametrosKey.setIdinstitucion(idInstitucion);
            GenParametros certificadoDigitalDirectorio = genParametrosExtendsMapper.selectByPrimaryKey(genParametrosKey);

            genParametrosKey.setParametro(SigaConstants.PARAMETRO_NOMBRE_CERTIFICADOS_DIGITALES);
            GenParametros certificadoDigitalNombre = genParametrosExtendsMapper.selectByPrimaryKey(genParametrosKey);

            genParametrosKey.setParametro(SigaConstants.PARAMETRO_CLAVE_CERTIFICADOS_DIGITALES);
            GenParametros pwdCertificadoDigital = genParametrosExtendsMapper.selectByPrimaryKey(genParametrosKey);

            StringBuilder certificadoDigitalPath = new StringBuilder();
            certificadoDigitalPath.append(certificadoDigitalDirectorio.getValor());
            certificadoDigitalPath.append(File.separator);
            certificadoDigitalPath.append(certificadoDigitalNombre.getValor());

            KeyStore keyStore = getKeyStore("PKCS12", certificadoDigitalPath.toString(), pwdCertificadoDigital.getValor());

            String sNombreFicheroSalida = nombreFicheroEntrada + ".tmp";
            File fOut = new File(sNombreFicheroSalida);
            File fIn = new File(nombreFicheroEntrada);

            String sAlias = keyStore.aliases().nextElement();
            PrivateKey pKey = (PrivateKey) keyStore.getKey(sAlias, pwdCertificadoDigital.getValor().toCharArray());
            Certificate[] aCertificados = keyStore.getCertificateChain(sAlias);

            PdfReader reader = new PdfReader(nombreFicheroEntrada);

            fos = new FileOutputStream(sNombreFicheroSalida);

            PdfStamper stamper = PdfStamper.createSignature(reader, fos, '\0');
            PdfSignatureAppearance psa = stamper.getSignatureAppearance();

            psa.setCrypto(pKey, aCertificados, null, PdfSignatureAppearance.WINCER_SIGNED);
            psa.setSignDate(gcFecha);

            stamper.close();
            fos.close();
            fIn.delete();
            fOut.renameTo(fIn);

            respuesta = true;

        } catch (Exception e) {
            LOGGER.error("***************** ERROR DE FIRMA DIGITAL EN DOCUMENTO *************************");
            LOGGER.error("Error al FIRMAR el PDF: " + nombreFicheroEntrada + " IdInstitucion: " + idInstitucion);
            LOGGER.error("Error al FIRMAR el PDF: " + e.getMessage());
            LOGGER.error("*******************************************************************************");
            LOGGER.error("*******************************************************************************");
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (fisID != null) fisID.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return respuesta;
    }

    private KeyStore getKeyStore(String keyStoreType, String certificadoDigitalPath, String pwdCertificadoDigital) throws Exception {

        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        InputStream inputStream = new FileInputStream(certificadoDigitalPath);
        keyStore.load(inputStream, pwdCertificadoDigital.toCharArray());

        if (inputStream != null) {
            inputStream.close();
        }

        return keyStore;
    }

    public void doZipGeneracionRapida(String rutaServidorDescargasZip, String nombreFichero, ArrayList<FacFicherosDescargaBean> ficherosPDF) throws Exception {
        // Generar Zip
        File ficZip = null;
        byte[] buffer = new byte[8192];
        int leidos;
        ZipOutputStream outTemp = null;

        try {
            LOGGER.info("DESCARGA DE FACTURAS: numero de facturas = " + ficherosPDF.size());

            if ((ficherosPDF != null) && (ficherosPDF.size() > 0)) {

                ficZip = new File(rutaServidorDescargasZip + nombreFichero + ".zip");

                // RGG
                if (ficZip.exists()) {
                    ficZip.delete();
                    LOGGER.info("DESCARGA DE FACTURAS: el fichero zip ya existia. Se elimina");
                }

                outTemp = new ZipOutputStream(new FileOutputStream(ficZip));

                for (int i = 0; i < ficherosPDF.size(); i++) {

                    File auxFile = ficherosPDF.get(i).getFichero();
                    LOGGER.info("DESCARGA DE FACTURAS: fichero numero " + i + " longitud=" + auxFile.length());
                    if (auxFile.exists() && !auxFile.getAbsolutePath().equalsIgnoreCase(ficZip.getAbsolutePath())) {
                        ZipEntry ze = null;
                        String[] nombreFicherosarrays;

                        switch (ficherosPDF.get(i).getFormatoDescarga()) {
                            case 1:
                                nombreFicherosarrays = auxFile.getName().split("-", 2);
                                ze = new ZipEntry(nombreFicherosarrays[1]);
                                break;
                            case 2:
                                //Quitamos la extension y anadimos el nombre mas la extension
                                String[] separacionExtensionDelFichero = auxFile.getName().split(Pattern.quote("."));
                                String[] separacionNombreColegiado = ficherosPDF.get(i).getNombreFacturaFichero().split("-");
                                nombreFicherosarrays = separacionExtensionDelFichero[0].split("-", 2);

                                ze = new ZipEntry(nombreFicherosarrays[1] + "-" + separacionNombreColegiado[0] + "." + separacionExtensionDelFichero[1]);
                                break;
                            case 3:
                                nombreFicherosarrays = auxFile.getName().split("-", 2);
                                ze = new ZipEntry(ficherosPDF.get(i).getNombreFacturaFichero() + nombreFicherosarrays[1]);
                                break;
                            case -1: //Tipos de ficheros especiales cuyo nombre no se ha de modificar
                                ze = new ZipEntry(auxFile.getName());
                                break;

                            default:
                                nombreFicherosarrays = auxFile.getName().split("-", 2);
                                ze = new ZipEntry(ficherosPDF.get(i).getNombreFacturaFichero() + nombreFicherosarrays[1]);
                                break;
                        }
                        outTemp.putNextEntry(ze);
                        FileInputStream fis = new FileInputStream(auxFile);

                        buffer = new byte[8192];

                        while ((leidos = fis.read(buffer, 0, buffer.length)) > 0) {
                            outTemp.write(buffer, 0, leidos);
                        }

                        fis.close();
                        outTemp.closeEntry();
                    }
                }
                LOGGER.info("DESCARGA DE FACTURAS: ok ");

                outTemp.close();

            }
        } catch (FileNotFoundException e) {
            throw new Exception("Error al crear fichero zip", e);
        } catch (IOException e) {
            throw new Exception("Error al crear fichero zip", e);
        } finally {
            try {
                outTemp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public TransactionStatus getNewLongTransaction(int timeout) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setTimeout(timeout);
        def.setName("transGenFac");
        return transactionManager.getTransaction(def);
    }

    public TransactionStatus getNeTransaction() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setTimeout(Integer.parseInt("30"));
        def.setName("transGenFac");
        return transactionManager.getTransaction(def);
    }

    public Resource obtenerFicheros(List<FacturaItem> facturas) throws Exception {

        try {
            if (facturas.size() == 1) {

                File filePDF = obtenerFicheroServer(facturas.get(0));
                Resource resource = null;

                if (filePDF != null) {
                    resource = new ByteArrayResource(Files.readAllBytes(filePDF.toPath())) {
                        public String getFilename() {
                            return filePDF.getName();
                        }
                    };
                }

                return resource;

            } else {

                String rutaZip = getProperty(SigaConstants.PARAMETRO_DIRECTORIO_FISICO_FACTURA_PDF) + getProperty(SigaConstants.PARAMETRO_DIRECTORIO_FACTURA_PDF) +
                        File.separator + facturas.get(0).getIdInstitucion() + File.separator + "Facturas.zip";
                ZipOutputStream outTemp = null;
                Resource resource = null;

                try {

                    File ficheroZip = new File(rutaZip);
                    ZipEntry zipEntry = null;
                    File fileFactura = null;
                    byte[] buffer = new byte[8192];
                    int leidos;

                    if (ficheroZip.exists()) {
                        ficheroZip.delete();
                    }

                    outTemp = new ZipOutputStream(new FileOutputStream(ficheroZip));

                    for (FacturaItem factura : facturas) {

                        fileFactura = obtenerFicheroServer(factura);
                        zipEntry = new ZipEntry(fileFactura.getName());
                        outTemp.putNextEntry(zipEntry);

                        FileInputStream fis = new FileInputStream(fileFactura);

                        buffer = new byte[8192];

                        while ((leidos = fis.read(buffer, 0, buffer.length)) > 0) {
                            outTemp.write(buffer, 0, leidos);
                        }

                        fis.close();
                        outTemp.closeEntry();
                    }

                    outTemp.close();

                    if (ficheroZip != null) {
                        resource = new ByteArrayResource(Files.readAllBytes(ficheroZip.toPath())) {
                            public String getFilename() {
                                return ficheroZip.getName();
                            }
                        };
                    }
                    ficheroZip.delete();
                    return resource;

                } catch(FileNotFoundException fnfe) {
                    throw new Exception("No se ha encontrado la factura");
                } catch(IOException ioe) {
                    throw new Exception("Ha ocurrido un error al descargar el conjunto de ficheros");
                }

            }

        } catch (IOException ioe) {
            throw new Exception("Ha ocurrido un error al recoger los ficheros del servidor");
        }

    }

    private File obtenerFicheroServer(FacturaItem factura) {

        String idSerieIdProgramacion = factura.getIdSerieFacturacion() + "_" + factura.getIdProgramacion();
        String rutaAlmacen = getProperty(SigaConstants.PARAMETRO_DIRECTORIO_FISICO_FACTURA_PDF) + getProperty(SigaConstants.PARAMETRO_DIRECTORIO_FACTURA_PDF) +
                File.separator + factura.getIdInstitucion() + File.separator + idSerieIdProgramacion;
        String nombrePDF = "";
        String nColegiado = "";
        File ficheroPDF;

        // Obtenemos el numero de colegiado
        CenColegiadoKey cenColegiadoKey = new CenColegiadoKey();
        cenColegiadoKey.setIdinstitucion( Short.valueOf(factura.getIdInstitucion()) );
        cenColegiadoKey.setIdpersona( Long.valueOf(factura.getIdPersona()) );
        CenColegiado cenColegiado = cenColegiadoExtendsMapper.selectByPrimaryKey(cenColegiadoKey);

        if (cenColegiado != null) {
            nColegiado = cenColegiado.getNcolegiado();
        }

        if (factura.getNumeroFactura() == null || factura.getNumeroFactura().equals("")) {
            nombrePDF = nColegiado + "-" + factura.getIdFactura();
        } else { // Contiene numero de factura, por lo tanto, esta confirmada
            nombrePDF = nColegiado + "-" + validarNombreFichero(factura.getNumeroFactura());
        }

        ficheroPDF = new File(rutaAlmacen + File.separator + nombrePDF + ".pdf");

        if (ficheroPDF.exists()) {

            return ficheroPDF;

        }

        return null;

    }
    
    //Facturacion Programada.
    public boolean actualizarLogExcel(FacFacturacionprogramada facP,
    		FacEstadosFacturacion estado,String msg) {
    	String estadoAux = "";
    	
    	try {
    		switch (estado) {
    		case EJECUTANDO_GENERACION:
    			estadoAux = "GENERACIÓN";
    			editarLog(facP,estadoAux,"EJECUTANDO GENERACIÓN",msg);
    			break;
    		case GENERADA:
    			estadoAux = "GENERACIÓN";
    			editarLog(facP,estadoAux,"GENERADA",msg);
    			break;
    			
    		case ERROR_GENERACION:
    			estadoAux = "GENERACIÓN";
    			editarLog(facP,estadoAux,"ERROR GENERACIÓN",msg);
    			break;
    			
    		case EJECUTANDO_CONFIRMACION:
    			estadoAux = "CONFIRMACIÓN";
	    		editarLog(facP,estadoAux,"EJECUTANDO CONFIRMACIÓN",msg);
				break;
    		case CONFIRM_FINALIZADA:
    			estadoAux = "CONFIRMACIÓN";
    			editarLog(facP,estadoAux,"CONFIRMACIÓN FINALIZADA",msg);
				break;
    		case ERROR_CONFIRMACION:
    			estadoAux = "CONFIRMACIÓN";
    			editarLog(facP,estadoAux,"ERROR CONFIRMACIÓN",msg);
				break;
    		case TRASPASO_PROGRAMADA:
    			estadoAux = "TRASPASO";
    			editarLog(facP,estadoAux,"TRASPASO PROGRAMADO",msg);
    			break;
    		case PDF_PROCESANDO:
    			estadoAux = "GENERAR PDF";
    			editarLog(facP,estadoAux,"PDF PROCESANDO",msg);
    			break;
    		case PDF_FINALIZADA:
    			estadoAux = "GENERAR PDF-ENVIO";
    			editarLog(facP,estadoAux,"PDF FINALIZADO",msg);
				break;
				
    		case PDF_FINALIZADAERRORES:
    			estadoAux = "GENERAR PDF-ENVIO";
    			editarLog(facP,estadoAux,"PDF FINALIZADO CON ERRORES",msg);
				break;
    		case ENVIO_FINALIZADA:
    			estadoAux = "GENERAR PDF-ENVIO";
    			editarLog(facP,estadoAux,"ENVIO FINALIZADO",msg);
				break;
    		case ENVIO_FINALIZADAERRORES:
    			estadoAux = "GENERAR PDF-ENVIO";
    			editarLog(facP,estadoAux,"ENVIO FINALIZADO CON ERRORES",msg);
				break;
    		default:
    			break;
    		}
    		return true;
		} catch (Exception e) {
			return false;
		}
		
    }
    
	private void editarLog(FacFacturacionprogramada facPro,String auxEstado, String estado,String msg) {
		try {
			LOGGER.info("FacturacionProgramada()- Se va a editar el excel informando");
			
			String pathFichero = getPathFacPro(facPro);
			String nombreFichero =  nombreFac(facPro);
			
			File file = new File(pathFichero,nombreFichero);
			if(file.exists()) {
				Workbook workBook = editarExcel(file,facPro,auxEstado,estado,msg);
				FileOutputStream fileOut;
				fileOut = new FileOutputStream(file);
				workBook.write(fileOut);
				fileOut.close();
				workBook.close();
				LOGGER.info(" FacturacionProgramada()- editar fichero:  " + nombreFichero);
			}
			
		} catch (Exception e) {
			LOGGER.error("FacturacionProgramada() - Error a la hora de editar Excel",e);

		}
	}

private Workbook editarExcel(File file,FacFacturacionprogramada facPro, String auxEstado,String estado ,String msg) {
		
		try {
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(0);
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			
			int rowCount = sheet.getLastRowNum();
			sheet.shiftRows(1,	 rowCount, 1);
			Date date = new Date();
			String fecha = dateFormat.format(date);
			//Fecha
			Row Accion = sheet.createRow(1);
			Accion.createCell(0).setCellValue(fecha);
			Accion.createCell(1).setCellValue(auxEstado);
			Accion.createCell(2).setCellValue(estado);
			Accion.createCell(3).setCellValue(msg);
	
			sheet.setColumnWidth(0, 6500);
			sheet.setColumnWidth(1, 4000);
			sheet.setColumnWidth(2, 8000);
			sheet.setColumnWidth(3, 10000);
			fis.close();
			return workbook;
			
		} catch (Exception e) {
			throw new RuntimeException("Error al editar el archivo Excel: " + e.getMessage());
		}
	}
    
	private String nombreFac(FacFacturacionprogramada item) {
		
		return LOG_FAC_PREFIX +"_"+item.getIdseriefacturacion() +"_" +item.getIdprogramacion()+".xlsx";
	}


	private String getPathFacPro(FacFacturacionprogramada facPro) {
		 String pathFichero = getProperty(FACTURACION_DIRECTORIO_FISICO_LOG_PROGRAMACION);
	     Path pLog = Paths.get(pathFichero).resolve(facPro.getIdinstitucion().toString());
		return pLog.toString();
	}

}
