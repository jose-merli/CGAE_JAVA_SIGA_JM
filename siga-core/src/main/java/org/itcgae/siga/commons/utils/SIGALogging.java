package org.itcgae.siga.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SIGALogging implements ISIGALogging {

    private final Logger LOGGER = LoggerFactory.getLogger(SIGALogging.class);

    private final SimpleDateFormat sdfLong = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private final String sError = "[ERROR]";
    private final String sInfo = "[INFO]";
    private final String separador = File.separator;
    private File f;
    private boolean iniciado;
    // log level 0.- todo 1.- Errores 2.- Errores e info
    private int logLevel = 2;
    private final int ERROR = 1;

    /**
     * Crea el fichero de log y el path en el que se encuentra
     *
     * @param nombreFichero con el path completo.
     * @param logLevel      con el nivel de log
     */
    public SIGALogging(String nombreFichero, Integer logLevel) {

        f = new File(nombreFichero);
        SIGAHelper.mkdirs(f.getParent());
        iniciado = false;

        if (logLevel != null) {
            this.logLevel = logLevel.intValue();
        }
    }

    /**
     * Escribe una traza en el fichero creado en el constructor con la hora de la traza
     *
     * @param s texto a escribir
     */
    @Override
    public void write(String s) {

        try (PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(f, true)), true)) {

            Date dat = Calendar.getInstance().getTime();
            printer.print(sdfLong.format(dat) + separador + s + "\r\n");

        } catch (Exception e) {
            LOGGER.error("Error Escribiendo SIGALogging: " + e);
        }
    }

    @Override
    public void writeLimpio(String s) {

        try (PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(f, true)), true)) {

            printer.print(s + "\r\n");

        } catch (Exception e) {
            LOGGER.error("Error Escribiendo SIGALogging: " + e);
        }
    }

    @Override
    public void writeLimpioError(Exception e, String idInstitucion, String idUsuario) {

        try (PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(f, true)), true)) {

            printer.print(getCompleteMessageParaLogger(e, idInstitucion, idUsuario));

        } catch (Exception ex) {
            LOGGER.error("Error Escribiendo SIGALogging: " + ex);
        }
    }

    /**
     * Escribe una traza en el fichero creado en el constructor con formato para el log de facturas
     * <br>El formato es: ERROR, FECHA, TIPO, IDPERSONA, NUMEROFACTURA, DESCRIPCION
     *
     * @param tipo          Tipo de traza: CONFIRMACION, PDF o ENVIO
     * @param idPersona     Identificador de la persona
     * @param numeroFactura Numero de factura
     * @param descripcion   Descripcion de la traza
     */
    @Override
    public void writeLogFactura(String tipo, String idPersona, String numeroFactura, String descripcion) {

        try (PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(f, true)), true)) {

            Date dat = Calendar.getInstance().getTime();
            // formato: ERROR, FECHA, TIPO, IDPERSONA, NUMEROFACTURA, DESCRIPCION
            if (!iniciado) {
                printer.println("TRAZA" + separador + "FECHA" + separador + "TIPO" + separador + "IDPERSONA" + separador + "NUM. FACTURA" + separador + "DESCRIPCION");
                iniciado = true;
            }

            printer.print(sError + separador + sdfLong.format(dat) + separador + tipo + separador + idPersona + separador + numeroFactura + separador + descripcion + "\r\n");

        } catch (Exception e) {
            LOGGER.error("Error Escribiendo SIGALogging para log de facturacion: " + e);
        }
    }

    @Override
    public void writeLogTraspasoFactura(String numFactura, String traspasada, String descripcionError) {

        try (PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(f, true)), true)) {

            if (!iniciado) {
                printer.println("NUM. FACTURA" + separador + "TRASPASADA" + separador + "DESCRIPCION ERROR");
                iniciado = true;
            }

            printer.print(numFactura + separador + traspasada + separador + "\"" + descripcionError + "\"\r\n");

        } catch (Exception e) {
            LOGGER.error("Error Escribiendo SIGALogging para log de facturacion: " + e);
        }
    }

    @Override
    public void writeLogGestorColaSincronizarDatos(int level, Integer idInstitucionOrigen, Long idPersona, String nombreCliente, String descripcion) {

        try (PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(f, true)), true)) {

            if (level <= this.logLevel) {

                Date dat = Calendar.getInstance().getTime();

                // formato: ERROR, FECHA, TIPO, IDPERSONA, NUMEROFACTURA, DESCRIPCION
                if (!iniciado && f.length() < 1) {
                    printer.println("TIPO" + separador +
                            "FECHA" + separador +
                            "INSTITUCION ORIGEN" + separador +
                            "IDPERSONA" + separador +
                            "NOMBRE CLIENTE" + separador +
                            "DESCRIPCION");
                    iniciado = true;
                }

                printer.print(((level == ERROR) ? sError : sInfo) + separador +
                        sdfLong.format(dat) + separador +
                        idInstitucionOrigen + separador +
                        idPersona + separador +
                        nombreCliente + separador +
                        descripcion + "\r\n");

            }

        } catch (Exception e) {
            LOGGER.error("Error Escribiendo SIGALogging para log de facturacion: " + e);
        }
    }

    private String getCompleteMessageParaLogger(Exception e, String idInstitucion, String idUsuario) {

        StringBuffer b = new StringBuffer();
        Date d = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");

        String codigoErrorExterno = "SIGAE" + "-" + idInstitucion + "-" + idUsuario + "-" + sdf2.format(d);

        b.append("<----------------  Excepcion en tiempo de ejecucion: ---------------->\n");
        b.append("CODIGO EXTERNO     --> " + codigoErrorExterno + "\n");
        b.append("<-------------------------------------------------------------------->\n");
        b.append("CODIGO USUARIO     --> " + idUsuario + "\n");
        b.append("INSTITUCION        --> " + idInstitucion + "\n");
        b.append("FECHA ERROR        --> " + sdf.format(d) + "\n\n");

        if (e != null) {

            b.append("EXCEPCIONES ANIDADAS (STACK TRACE)\n");
            Exception last = e;
            b.append("\t\t<---------------------------------------------------------------->\n");
            b.append("\t\tEXCEPCION          --> " + e.getMessage() + "\n");
            b.append("\t\t<---------------------------------------------------------------->\n");

            if (last != null) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                last.printStackTrace(pw);
                b.append("  PILA DE EJECUCION DE LA EXCEPCION ORIGINARIA\n");
                b.append(sw);
            }

        }

        b.append("<-------------------------------------------------------------------->\n");
        return b.toString();
    }

}
