package org.itcgae.siga.fac.services.impl;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FacFicherosDescargaBean;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.FoUtils;
import org.itcgae.siga.commons.utils.SIGAHelper;
import org.itcgae.siga.db.entities.AdmLenguajes;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoKey;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacFacturaKey;
import org.itcgae.siga.db.entities.FacPlantillafacturacion;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.AdmLenguajesMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacPlantillafacturacionExtendsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.channels.FileChannel;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
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

    public File generarPdfFacturaFirmada(String idFactura, String idInstitucion) throws Exception {
        return generarPdfFacturaFirmada(idFactura, idInstitucion, false);
    }

    public File generarPdfFacturaFirmada(String idFactura, String idInstitucion, boolean bRegenerar) throws Exception {

        File ficheroPDF;

        FacFacturaKey facFacturaKey = new FacFacturaKey();
        facFacturaKey.setIdfactura(idFactura);
        facFacturaKey.setIdinstitucion(Short.valueOf(idInstitucion));

        FacFactura facFactura = facFacturaExtendsMapper.selectByPrimaryKey(facFacturaKey);

        try {

            // Generamos el fichero pdf de la factura
            ficheroPDF = generarPdfFacturaFirmada(facFactura, bRegenerar);

        } catch (Exception e) {
            throw e;
        }

        return ficheroPDF;
    }

    private File generarPdfFacturaFirmada(FacFactura facFactura, boolean bRegenerar) throws Exception {

        File fFicheroFirmado;

        try {

            File filePDF = generarPdfFacturaSinFirmar(facFactura, bRegenerar);

            if (filePDF == null) {
                throw new Exception("Error al generar la factura. Fichero devuelto es nulo");
            } else {
                LOGGER.info("DESPUES DE LA GENERACION DE LA FACTURA: " + filePDF.getAbsolutePath());
                LOGGER.info("Existe el fichero: " + (filePDF.exists() ? "SI" : "NO"));
            }

            // Genero una carpeta con las firmas
            String sRutaFirmas = filePDF.getParentFile().getPath() + File.separator + "firmas";
            File fRutaFirmas = new File(sRutaFirmas);
            SIGAHelper.mkdirs(sRutaFirmas);

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

    private File generarPdfFacturaSinFirmar(FacFactura facFactura, boolean bRegenerar) throws Exception {

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
            SIGAHelper.mkdirs(rutaAlmacen);

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
                fPdf = generarInforme(rutaServidorTmp, contenidoPlantilla, rutaAlmacen, nombrePDF, idFacturaParametro);
                LOGGER.info("DESPUES DE GENERAR EL INFORME EN  " + rutaAlmacen);

            }

        } catch (Exception e) {
            throw e;
        }

        return fPdf;
    }

    public File generarInforme(String rutaServidorTmp, String contenidoPlantilla, String rutaServidorDescargas, String nombreFicheroPDF, String idFactura) throws Exception {

        File ficheroFOP = null;
        File ficheroPDF = null;
        File rutaTmp = null;

        try {

            nombreFicheroPDF = validarNombreFichero(nombreFicheroPDF);

            //Crea la ruta temporal
            rutaTmp = new File(rutaServidorTmp);
            SIGAHelper.mkdirs(rutaServidorTmp);
            ficheroFOP = new File(rutaTmp + File.separator + nombreFicheroPDF + System.currentTimeMillis() + ".fo");

            // Generacion del fichero .FOP para este usuario a partir de la plantilla .FO
            LOGGER.info("ANTES DE REEMPLAZAR LOS DATOS DE LA PLANTILLA.");
            String content = reemplazarDatos(contenidoPlantilla, idFactura);
            LOGGER.info("DESPUES DE REEMPLAZAR LOS DATOS DE LA PLANTILLA.");
            setFileContent(ficheroFOP, content);

            //Crea el fichero. Si no existe el directorio de la institucion para la descarga lo crea.
            ficheroPDF = new File(rutaServidorDescargas + File.separator + nombreFicheroPDF + ".pdf");
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

    private String reemplazarDatos(String plantillaFO, String idFactura) {
        return plantillaFO;
    }

    // OBTENCION DE LA PLANTILLA FO:
    public String obtenerContenidoPlantilla(String rutaServidorPlantillas, String nombrePlantilla) throws Exception {

        LOGGER.info("*************** PLANTILLA : " + rutaServidorPlantillas + File.separator + nombrePlantilla);
        String barraPlantilla = "";

        if (rutaServidorPlantillas.indexOf("/") > -1) {
            barraPlantilla = "/";
        }

        if (rutaServidorPlantillas.indexOf("\\") > -1) {
            barraPlantilla = "\\";
        }

        if (barraPlantilla.equals("")) {
            barraPlantilla = File.separator;
        }

        File plantillaFO = new File(rutaServidorPlantillas + barraPlantilla + nombrePlantilla);

        if (!plantillaFO.exists()) {
            throw new Exception("El directorio de plantillas no existe o no esta bien configurado");
        } else if (!plantillaFO.canRead()) {
            throw new Exception("Error dfe lectura del fichero FOP: " + plantillaFO.getAbsolutePath());
        }

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

    private String getProperty(final String parametro) {
        return getProperty(parametro, null);
    }

    private String getProperty(final String parametro, final String fichero) {

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

}
