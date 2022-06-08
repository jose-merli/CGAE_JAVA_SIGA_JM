package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.FcsFacturacionjg;
import org.itcgae.siga.db.entities.FcsFacturacionjgKey;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosKey;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.FcsFacturacionjgMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.exception.FacturacionSJCSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtilidadesFacturacionSJCS {
    private static final String REGULARIZACION = "Regularización realizada con éxito";

	private final Logger LOGGER = Logger.getLogger(UtilidadesFacturacionSJCS.class);

    @Autowired
    private GenParametrosExtendsMapper genParametrosExtendsMapper;

    @Autowired
    private FcsFacturacionjgMapper fcsFacturacionjgMapper;

    @Autowired
    private AdmConfigMapper admConfigMapper;

    @Autowired
    private CenInstitucionMapper cenInstitucionMapper;

    @Autowired
    private EjecucionPlsPago ejecucionPlsPago;

    @Autowired
    private GenRecursosMapper genRecursosMapper;
    
    @Autowired
    private LogErroresFacturacionSJCSHelper logErroresFacHelper;

    public Hashtable getNombreFicherosFacturacion(Short idInstitucion, Integer idFacturacion) throws Exception {
        return getNombreFicherosFac(idInstitucion, idFacturacion, null, null);
    }

    public Hashtable getNombreFicherosPago(Short idInstitucion, Integer idFacturacion, Integer idPago, Long idPersona) throws Exception {
        return getNombreFicherosFac(idInstitucion, idFacturacion, idPago, idPersona);
    }

    private Hashtable getNombreFicherosFac(Short idInstitucion, Integer idFacturacion, Integer idPago, Long idPersona) throws Exception {

        try {

            Hashtable nombreFicheros = new Hashtable();

            FcsFacturacionjgKey fcsFacturacionjgKey = new FcsFacturacionjgKey();
            fcsFacturacionjgKey.setIdinstitucion(idInstitucion);
            fcsFacturacionjgKey.setIdfacturacion(idFacturacion);

            FcsFacturacionjg facturacion = fcsFacturacionjgMapper.selectByPrimaryKey(fcsFacturacionjgKey);

            if (facturacion == null) {
                return nombreFicheros;
            }

            boolean bPrevision = facturacion.getPrevision().equalsIgnoreCase("1");
            boolean bRegularizacion = facturacion.getRegularizacion().equalsIgnoreCase("1");

            String sNombreFichero = "";

            if (bPrevision && bRegularizacion) {
                return nombreFicheros;
            }

            if (idPago != null) {
                sNombreFichero = "PAGOS_";                // Pagos
            } else {
                if (!bPrevision && !bRegularizacion)    // Facturacion
                    sNombreFichero = "FACTURACION_";

                if (bPrevision)                            // Prevision
                    sNombreFichero = "PREVISION_";

                if (bRegularizacion)                    // Regularizacion
                    sNombreFichero = "REGULARIZACION_";
            }

            String extensionFichero = "_" + idInstitucion + "_" + idFacturacion;

            if (idPago != null) {
                extensionFichero += "_" + idPago;
            }

            if (idPersona != null) {
                extensionFichero += "_" + idPersona;
            }

            extensionFichero += ".XLS";

            nombreFicheros.put(SigaConstants.HITO_GENERAL_TURNO, sNombreFichero + "TURNOSOFICIO" + extensionFichero);
            nombreFicheros.put(SigaConstants.HITO_GENERAL_GUARDIA, sNombreFichero + "GUARDIAS" + extensionFichero);
            nombreFicheros.put(SigaConstants.HITO_GENERAL_EJG, sNombreFichero + "EJG" + extensionFichero);
            nombreFicheros.put(SigaConstants.HITO_GENERAL_SOJ, sNombreFichero + "SOJ" + extensionFichero);

            return nombreFicheros;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String getMensajeIdioma(String idLenguaje, String idRecurso) {

        GenRecursosKey genRecursosKey = new GenRecursosKey();
        genRecursosKey.setIdlenguaje(idLenguaje);
        genRecursosKey.setIdrecurso(idRecurso);

        GenRecursos genRecursos = genRecursosMapper.selectByPrimaryKey(genRecursosKey);

        return genRecursos.getDescripcion();
    }

    private String construirCabeceraPago(AdmUsuarios usuario) {

        return getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.importePagosRealizados") + "#" +
                getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.importeIRPFPagosRealizados") + "#" +
                getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.importePagoActual") + "#" +
                getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.importeIRPFPagoActual") + "#" +
                getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.importeTotalPagosRealizados") + "#" +
                getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.importeTotalIRPFPagosRealizados") + "#" +
                getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.importeTotalPagoActual") + "#" +
                getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.importeTotalIRPFPagoActual");
    }

    private String getCabecerasFicheros(int concepto, String tipoCabecera, AdmUsuarios usuario) {

        String cabecera = "";

        try {
            if (concepto == SigaConstants.HITO_GENERAL_SOJ) {

                cabecera = getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.tramitador") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.turno") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.guardia") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.nExpEJG") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.fecha") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.interesado") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.importe") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.total") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.totalAcumulado");

                // Si es un pago
                if (tipoCabecera.equalsIgnoreCase(SigaConstants.PAGOS_SJCS)) {
                    cabecera += "#" + construirCabeceraPago(usuario);
                }

            } else if (concepto == SigaConstants.HITO_GENERAL_EJG) {

                cabecera = getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.tramitador") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.turno") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.guardia") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.nExpEJG") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.fecha") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.ncajg") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.anioCajg") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.interesado") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.importe") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.total") + "#" +
                        getMensajeIdioma(usuario.getIdlenguaje(), "fcs.ficheroFacturacion.cabecera.totalAcumulado");

                if (tipoCabecera.equalsIgnoreCase(SigaConstants.PAGOS_SJCS)) {
                    cabecera += "#" + construirCabeceraPago(usuario);
                }

            }
        } catch (Exception e) {
            cabecera = "";
        }

        return cabecera;
    }

    public void exportarDatosPagos(Short idInstitucion, Integer idFacturacion, Integer idPago, Long idPersona, AdmUsuarios usuario) throws Exception {
        exportarDatosFac(idInstitucion, idFacturacion, idPago, idPersona, usuario);
    }

    private void exportarDatosFac(Short idInstitucion, Integer idFacturacion, Integer idPago, Long idPersona, AdmUsuarios usuario) throws Exception {

        try {

            String tipoP = SigaConstants.FACTURACION_SJCS;

            GenParametrosExample genParametrosExample = new GenParametrosExample();
            genParametrosExample.createCriteria().andModuloEqualTo(SigaConstants.MODULO_FCS).andParametroEqualTo(SigaConstants.PATH_PREVISIONES_BD)
                    .andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
            genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

            String pathFicheros = genParametrosExtendsMapper.selectByExample(genParametrosExample).get(0).getValor();

            Hashtable nombreFicheros = null;

            if (idPago == null) {
                nombreFicheros = getNombreFicherosFacturacion(idInstitucion, idFacturacion);
            } else {
                nombreFicheros = getNombreFicherosPago(idInstitucion, idFacturacion, idPago, idPersona);
                tipoP = SigaConstants.PAGOS_SJCS;
            }

            //El lenguaje de los informes es el de la institucion a la que pertenecen las
            //facturaciones, no tiene que ver con el usuario que la genera. Ademas si es un
            //proceso automatica el userBean Automatico tiene por defecto el idioma
            //español que seria un error para lo colegios con otro idioma.
            //Por lo tanto accedemos al idioma de cada institucion

            CenInstitucion beanLenguajeIntitucion = cenInstitucionMapper.selectByPrimaryKey(idInstitucion);

            // TURNOS DE OFICIO
            ejecucionPlsPago.ejecutarPLExportarTurno(idInstitucion.toString(), idFacturacion.toString(), null,
                    (idPersona == null ? "" : idPersona.toString()), pathFicheros, nombreFicheros.get(SigaConstants.HITO_GENERAL_TURNO).toString(),
                    beanLenguajeIntitucion.getIdlenguaje(), usuario);

            Object[] param_in = new Object[7];
            String[] resultado;
            param_in[0] = idInstitucion.toString();                    // IDINSTITUCION
            param_in[1] = idFacturacion.toString();                    // IDFACTURACION
            param_in[2] = (idPago == null ? "" : idPago.toString());    // IDPAGO
            param_in[3] = (idPersona == null ? "" : idPersona.toString());    // IDPERSONA
            param_in[4] = pathFicheros;                                    // PATH_FICHEROS

            // SOJ
            param_in[5] = nombreFicheros.get(SigaConstants.HITO_GENERAL_SOJ).toString();
            param_in[6] = getCabecerasFicheros(SigaConstants.HITO_GENERAL_SOJ, tipoP, usuario);
            resultado = new String[2];
            resultado = ejecucionPlsPago.callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_EXPORTAR_SOJ (?,?,?,?,?,?,?,?,?)}", 2, param_in);
            
            if (resultado != null) {
            	if (!resultado[0].equalsIgnoreCase("0")) {
                    //ClsLogging.writeFileLog("Error en PL = " + (String) resultado[1], 3);
                    LOGGER.error("Error en PL = " + (String) resultado[1]);
                }
            } else {
            	LOGGER.error("Error en PL PROC_FCS_EXPORTAR_SOJ");
            }

            // EJG
            param_in[5] = nombreFicheros.get(SigaConstants.HITO_GENERAL_EJG).toString();
            param_in[6] = getCabecerasFicheros(SigaConstants.HITO_GENERAL_EJG, tipoP, usuario);
            resultado = new String[2];
            resultado = ejecucionPlsPago.callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_EXPORTAR_EJG (?,?,?,?,?,?,?,?,?)}", 2, param_in);
            
            if (resultado != null) {
            	if (!resultado[0].equalsIgnoreCase("0")) {
                    //ClsLogging.writeFileLog("Error en PL = " + (String) resultado[1], 3);
                    LOGGER.error("Error en PL = " + (String) resultado[1]);
                }
            } else {
            	LOGGER.error("Error en PL PROC_FCS_EXPORTAR_EJG");
            }

            // GUARDIAS
            ejecucionPlsPago.ejecutarPLExportarGuardias(idInstitucion.toString(), idFacturacion.toString(), null, (idPersona == null ? "" : idPersona.toString()),
                    pathFicheros, nombreFicheros.get(SigaConstants.HITO_GENERAL_GUARDIA).toString(), beanLenguajeIntitucion.getIdlenguaje(), usuario);
        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al exportar datos", e, "messages.factSJCS.error.exportDatos");
        }
    }

    /**
     * Funcion que elimina los ficheros de una facturacion o pago
     *
     * @param idInstitucion
     * @param nombreFicheros
     */
    public void borrarFicheros(Short idInstitucion, Hashtable nombreFicheros) {

        GenParametrosExample genParametrosExample = new GenParametrosExample();
        genParametrosExample.createCriteria().andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion))
                .andModuloEqualTo(SigaConstants.MODULO_FCS)
                .andParametroEqualTo(SigaConstants.PATH_PREVISIONES);

        genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

        String path = genParametrosExtendsMapper.selectByExample(genParametrosExample).get(0).getValor();

        File fichero;

        if (nombreFicheros == null) {
            return;
        }

        fichero = new File(path + File.separator + nombreFicheros.get(SigaConstants.HITO_GENERAL_TURNO));
        if (fichero.exists()) {
            fichero.delete();
        }

        fichero = new File(path + File.separator + nombreFicheros.get(SigaConstants.HITO_GENERAL_GUARDIA));
        if (fichero.exists()) {
            fichero.delete();
        }

        fichero = new File(path + File.separator + nombreFicheros.get(SigaConstants.HITO_GENERAL_EJG));
        if (fichero.exists()) {
            fichero.delete();
        }

        fichero = new File(path + File.separator + nombreFicheros.get(SigaConstants.HITO_GENERAL_SOJ));
        if (fichero.exists()) {
            fichero.delete();
        }
    }

    //@Transactional(rollbackFor = Exception.class, timeout=24000)
    public void ejecutarFacturacionJG(FcsFacturacionjg itemFac, CenInstitucion institucion) throws Exception {
        boolean prevision = false;
    	LogErroresFacturacionSJCS logErroresFac = logErroresFacHelper.getLogErroresFacturacion(itemFac.getIdinstitucion(),itemFac.getIdfacturacion().toString());
        LOGGER.debug("ejecutarFacturacionJG() - INICIO");
        if (itemFac.getPrevision().equals("1")) {
            prevision = true;
        }

        // proceso de facturacion
        double importeTotal = 0;
        Double importeOficio = null, importeGuardia = null, importeSOJ = null, importeEJG = null;

        //////////////////////////////////
        // TURNOS DE OFICIO rgg 16-03-2005
        LOGGER.debug("AUDIT PARAMS PROC_FCS_FACTURAR_TURNOS_OFI");
        LOGGER.debug("IDINSTITUCION: " + itemFac.getIdinstitucion().toString()
        		+ " - IDFACTURACION: " + itemFac.getIdfacturacion().toString()
        		+ " - USUMODIFICACION: " + itemFac.getUsumodificacion().toString());
        
        Object[] param_in_facturacion = new Object[3];
        param_in_facturacion[0] = itemFac.getIdinstitucion().toString(); // IDINSTITUCION
        param_in_facturacion[1] = itemFac.getIdfacturacion().toString(); // IDFACTURACION
        param_in_facturacion[2] = itemFac.getUsumodificacion().toString(); // USUMODIFICACION

        String resultado[] = new String[3];
        
        try {
        LOGGER.debug("Ejecutamos PL PROC_FCS_FACTURAR_TURNOS_OFI: "+param_in_facturacion[0]+", "+param_in_facturacion[1]+", "+param_in_facturacion[2]);
        resultado = callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_FACTURAR_TURNOS_OFI(?,?,?,?,?,?)}", 3,
                param_in_facturacion);
        } catch(Exception e) {
            LOGGER.error(e.getCause());
            LOGGER.error(e.getMessage());
            LOGGER.error(e);
            LOGGER.debug("Error PL PROC_FCS_FACTURAR_TURNOS_OFI");
        	logErroresFac.logError("Error indeterminado en la ejecución de PKG_SIGA_FACTURACION_SJCS.PROC_FCS_FACTURAR_TURNOS_OFI; " + e);
        }
        LOGGER.debug("Salimos PL PROC_FCS_FACTURAR_TURNOS_OFI");
        
        if (resultado == null || resultado.length <= 1 || resultado[2] == null || !resultado[2].equalsIgnoreCase("Fin correcto ")) {
        	String sError = "";
        	if(resultado != null && resultado[2] != null) {
        		sError = "Error en PL = " + (String) resultado[2]; 
        	}else {
        		sError = "Error en PL PROC_FCS_FACTURAR_TURNOS_OFI";
        	}
            LOGGER.error(sError);
            logErroresFac.logError(sError);

        }else{
            importeOficio = new Double(resultado[0].replaceAll(",", "."));
            importeTotal += importeOficio.doubleValue();
            LOGGER.debug("PROC_FCS_FACTURAR_TURNOS_OFI Importe facturado: " + resultado[0]);
        }

        //////////////////////////////////
        // GUARDIAS rgg 22-03-2005

        param_in_facturacion = new Object[3];
        param_in_facturacion[0] = itemFac.getIdinstitucion().toString(); // IDINSTITUCION
        param_in_facturacion[1] = itemFac.getIdfacturacion().toString(); // IDFACTURACION
        param_in_facturacion[2] = itemFac.getUsumodificacion().toString(); // USUMODIFICACION

        resultado = new String[3];
        
        try {
            LOGGER.debug("Ejecutamos PL PROC_FCS_FACTURAR_TURNOS_OFI: "+param_in_facturacion[0]+", "+param_in_facturacion[1]+", "+param_in_facturacion[2]);
        resultado = callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_FACTURAR_GUARDIAS(?,?,?,?,?,?)}", 3,
                param_in_facturacion);
        } catch(Exception e) {
            LOGGER.error(e.getCause());
            LOGGER.error(e.getMessage());
            LOGGER.error(e);
            LOGGER.debug("Error PL PROC_FCS_FACTURAR_GUARDIAS");
        	logErroresFac.logError("Error indeterminado en la ejecución de PKG_SIGA_FACTURACION_SJCS.PROC_FCS_FACTURAR_GUARDIAS; " + e);
        }
        LOGGER.debug("Salimos PL PROC_FCS_FACTURAR_GUARDIAS");
        
        if (resultado == null || resultado.length <= 1 || resultado[2] == null || !resultado[2].equalsIgnoreCase("El proceso:PROC_FCS_FACTURAR_GUARDIAS ha finalizado correctamente")) {
        	String sError = "";
        	if(resultado != null && resultado[2] != null) {
        		sError = "Error en PL = " + (String) resultado[2]; 
        	}else {
        		sError = "Error en PL PROC_FCS_FACTURAR_GUARDIAS";
        	}
            LOGGER.error(sError);
            logErroresFac.logError(sError);

        }else{
            importeGuardia = new Double(resultado[0].replaceAll(",", "."));
            importeTotal += importeGuardia.doubleValue();
            LOGGER.debug("PROC_FCS_FACTURAR_GUARDIAS Importe facturado: " + resultado[0]);
        }

        //////////////////////////////////
        // EXPEDIENTES SOJ rgg 22-03-2005

        param_in_facturacion = new Object[3];
        param_in_facturacion[0] = itemFac.getIdinstitucion().toString(); // IDINSTITUCION
        param_in_facturacion[1] = itemFac.getIdfacturacion().toString(); // IDFACTURACION
        param_in_facturacion[2] = itemFac.getUsumodificacion().toString(); // USUMODIFICACION

        resultado = new String[3];
        try {
            LOGGER.debug("Ejecutamos PL PROC_FCS_FACTURAR_SOJ: "+param_in_facturacion[0]+", "+param_in_facturacion[1]+", "+param_in_facturacion[2]);
            resultado = callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_FACTURAR_SOJ(?,?,?,?,?,?)}", 3,
                    param_in_facturacion);
        } catch(Exception e) {
	        LOGGER.error(e.getCause());
	        LOGGER.error(e.getMessage());
	        LOGGER.error(e);
	        LOGGER.debug("Error PL PROC_FCS_FACTURAR_SOJ");
	        logErroresFac.logError("Error indeterminado en la ejecución de PKG_SIGA_FACTURACION_SJCS.PROC_FCS_FACTURAR_SOJ; " + e);
        }

        LOGGER.debug("Salimos PL PROC_FCS_FACTURAR_SOJ");
        if (resultado == null || resultado.length <= 1 || resultado[2] == null || !resultado[2].equalsIgnoreCase("Fin correcto")) {
            LOGGER.debug("Error PL PROC_FCS_FACTURAR_SOJ");
            String error = "";
        	if(resultado != null && resultado[2] != null) {
        		error = "Error en PL = " + (String) resultado[2]; 
        	}else {
        		error = "Error en PL PROC_FCS_FACTURAR_SOJ";
        	}
            LOGGER.error(error);
            logErroresFac.logError(error);

        }else{
            importeSOJ = new Double(resultado[0].replaceAll(",", "."));
            importeTotal += importeSOJ.doubleValue();
            LOGGER.debug("PROC_FCS_FACTURAR_SOJ Importe facturado: " + resultado[0]);
        }

        //////////////////////////////////
        // EXPEDIENTES EJG rgg 22-03-2005

        param_in_facturacion = new Object[3];
        param_in_facturacion[0] = itemFac.getIdinstitucion().toString(); // IDINSTITUCION
        param_in_facturacion[1] = itemFac.getIdfacturacion().toString(); // IDFACTURACION
        param_in_facturacion[2] = itemFac.getUsumodificacion().toString(); // USUMODIFICACION
        try{
            resultado = new String[3];
            LOGGER.debug("Ejecutamos PL PROC_FCS_FACTURAR_EJG: "+param_in_facturacion[0]+", "+param_in_facturacion[1]+", "+param_in_facturacion[2]);
            resultado = callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_FACTURAR_EJG (?,?,?,?,?,?)}", 3,
                    param_in_facturacion);
        } catch(Exception e) {
            LOGGER.error(e.getCause());
            LOGGER.error(e.getMessage());
            LOGGER.error(e);
            LOGGER.debug("Error PL PROC_FCS_FACTURAR_EJG");
            logErroresFac.logError("Error indeterminado en la ejecución de PKG_SIGA_FACTURACION_SJCS.PROC_FCS_FACTURAR_EJG; " + e);
        }

        LOGGER.debug("Salimos PL PROC_FCS_FACTURAR_EJG");
        if (resultado == null || resultado.length <= 1 || resultado[2] == null || !resultado[2].equalsIgnoreCase("Fin correcto")) {
            LOGGER.debug("Error PL PROC_FCS_FACTURAR_EJG");
            String error = "";
        	if(resultado != null && resultado[2] != null) {
        		error = "Error en PL = " + (String) resultado[2]; 
        	}else {
        		error = "Error en PL PROC_FCS_FACTURAR_EJG";
        	}
            LOGGER.error(error);
            logErroresFac.logError(error);

        }else{
            importeEJG = new Double(resultado[0].replaceAll(",", "."));
            importeTotal += importeEJG.doubleValue();
            LOGGER.debug("PROC_FCS_FACTURAR_EJG Importe facturado: " + resultado[0]);
        }

        if (prevision) {
            //////////////////////////////////////
            /// CREAMOS EL INFORME
            // ArrayList filtrosInforme = getFiltrosInforme(itemFac, institucion);
            // File fichero = getFicheroGenerado(institucion,
            ////////////////////////////////////// SigaConstants.I_INFORMEFACTSJCS,null,
            ////////////////////////////////////// filtrosInforme);
            // itemFac.setNombrefisico(fichero.getPath());

            // TODO Esta funcionalidad llamará al módulo de comunicaciones cuando esté
            // desarrollado

            String error = "Fin de la simulacion de la facturacion.";
            LOGGER.error(error);
            logErroresFac.logError(error);
            logErroresFac.writeAllErrors();
            throw new Exception("Hacemos rollback por tratarse de una simulacion");
        }

        // Exportacion de datos a EXCEL: Se ha comentado este metodo por que no se
        // quiere utilizar
        // UtilidadesFacturacionSJCS.exportarDatosFacturacion(new
        // Integer(idInstitucion), new Integer(idFacturacion), this.usrbean);
        if(logErroresFac.haveErrors()) {
        	logErroresFac.writeAllErrors();
        } else {
        	logErroresFac.writeExito();
            // ACTUALIZO LOS TOTALES
            itemFac.setImporteejg(new BigDecimal(importeEJG));
            itemFac.setImporteguardia(new BigDecimal(importeGuardia));
            itemFac.setImporteoficio(new BigDecimal(importeOficio));
            itemFac.setImportesoj(new BigDecimal(importeSOJ));
            itemFac.setImportetotal(new BigDecimal(importeTotal));

            fcsFacturacionjgMapper.updateByPrimaryKey(itemFac);
        }
        
        LOGGER.debug("ejecutarFacturacionJG() - FIN");
    }

    //@Transactional(rollbackFor = Exception.class, timeout=24000)
    public FcsFacturacionjg ejecutarRegularizacionJG(FcsFacturacionjg item, CenInstitucion institucion) throws Exception {
    	LogErroresFacturacionSJCS logErroresFac = logErroresFacHelper.getLogErroresFacturacion(item.getIdinstitucion(),item.getIdfacturacion().toString());
        // proceso de facturacion
        LOGGER.debug("Entra ejecutarRegularizacionJG");
        double importeTotal = 0;
        Double importeOficio = null, importeGuardia = null, importeSOJ = null, importeEJG = null;

        // parametros de entrada
        Object[] param_in_facturacion = new Object[4];
        param_in_facturacion[0] = item.getIdinstitucion().toString(); // IDINSTITUCION
        param_in_facturacion[1] = item.getIdfacturacion().toString(); // IDFACTURACION
        param_in_facturacion[2] = item.getUsumodificacion().toString(); // USUMODIFICACION
        param_in_facturacion[3] = institucion.getIdlenguaje();

        String resultado[] = null;

        //////////////////////////////////
        // TURNOS DE OFICIO rgg 29-03-2005
        resultado = new String[3];
        try {
            LOGGER.debug("PROC_FCS_REGULAR_TURNOS_OFI regularizacion entra");
            resultado = callPLProcedure(
                    "{call PKG_SIGA_REGULARIZACION_SJCS.PROC_FCS_REGULAR_TURNOS_OFI(?,?,?,?,?,?,?)}", 3,
                    param_in_facturacion);
            LOGGER.debug("PROC_FCS_REGULAR_TURNOS_OFI regularizacion sale");
            
            if (resultado != null) {
            	if (!resultado[1].equals("0")) {
                    LOGGER.error("PROC_FCS_REGULAR_TURNOS_OFI regularizacion error");
                	String error = "Error en PL = " + (String) resultado[2];
                    LOGGER.error(error);
                    logErroresFac.logError(error);
                    throw new Exception("Ha ocurrido un error al ejecutar la regularización de Turnos de Oficio: "
                            + (String) resultado[2]);
                }
            } else {
            	throw new Exception("Ha ocurrido un error al ejecutar la regularización de Turnos de Oficio, el resultado del PL es null");
            }
            
        } catch (IOException | NamingException | SQLException e) {
        	String error = "Error en PL al ejecutar la regularización de Turnos de Oficio = " + (String) resultado[2];
            LOGGER.error(error);
            logErroresFac.logError(error);
            throw new Exception("Ha ocurrido un error al ejecutar la regularización de Turnos de Oficio", e);
        }

        importeOficio = new Double(resultado[0].replaceAll(",", "."));
        importeTotal += importeOficio.doubleValue();

        //////////////////////////////////
        // GUARDIAS PRESENCIALES rgg 29-03-2005
        resultado = new String[3];
        try {
            resultado = callPLProcedure(
                    "{call PKG_SIGA_REGULARIZACION_SJCS.PROC_FCS_REGULAR_GUARDIAS(?,?,?,?,?,?,?)}", 3,
                    param_in_facturacion);
            
            if (resultado != null) {
            	if (!resultado[1].equals("0")) {
                	String error = "Error en PL = " + (String) resultado[2];
                    LOGGER.error(error);
                    logErroresFac.logError(error);
                    throw new Exception(
                            "Ha ocurrido un error al ejecutar la regularización de Guardias: " + (String) resultado[2]);
                }
            } else {
            	throw new Exception("Ha ocurrido un error al ejecutar la regularización de Guardias, el resultado del PL es null");
            }
            
        } catch (IOException | NamingException | SQLException e) {
        	String error = "Error en PL al ejecutar la regularización de Guardias = " + (String) resultado[2];
            LOGGER.error(error);
            logErroresFac.logError(error);
            throw new Exception("Ha ocurrido un error al ejecutar la regularización de Guardias", e);
        }

        importeGuardia = new Double((String) resultado[0].replaceAll(",", "."));
        importeTotal += importeGuardia.doubleValue();

        //////////////////////////////////
        // SOJ rgg 29-03-2005
        resultado = new String[3];
        try {
            resultado = callPLProcedure("{call PKG_SIGA_REGULARIZACION_SJCS.PROC_FCS_REGULAR_SOJ(?,?,?,?,?,?,?)}",
                    3, param_in_facturacion);
            
            if (resultado != null) {
            	if (!resultado[1].equals("0")) {
                	String error = "Error en PL al ejecutar la regularización de SOJ= " + (String) resultado[2];
                    LOGGER.error(error);
                    logErroresFac.logError(error);
                    throw new Exception(
                            "Ha ocurrido un error al ejecutar la regularización de SOJ: " + (String) resultado[2]);
                }
            } else {
            	throw new Exception("Ha ocurrido un error al ejecutar la regularización de SOJ, el resultado del PL es null");
            }
            
        } catch (IOException | NamingException | SQLException e) {
        	String error = "Error en PL al ejecutar la regularización de SOJ= " + (String) resultado[2];
            LOGGER.error(error);
            logErroresFac.logError(error);
            throw new Exception("Ha ocurrido un error al ejecutar la regularización de SOJ", e);
        }

        importeSOJ = new Double((String) resultado[0].replaceAll(",", "."));
        importeTotal += importeSOJ.doubleValue();

        //////////////////////////////////
        // EJG rgg 29-03-2005
        resultado = new String[3];
        try {
            resultado = callPLProcedure("{call PKG_SIGA_REGULARIZACION_SJCS.PROC_FCS_REGULAR_EJG(?,?,?,?,?,?,?)}",
                    3, param_in_facturacion);
            
            if (resultado != null) {
            	if (!resultado[1].equals("0")) {
                	String error = "Error en PL = " + (String) resultado[2];
                    LOGGER.error(error);
                    logErroresFac.logError(error);
                    throw new Exception(
                            "Ha ocurrido un error al ejecutar la regularización de Expedientes de Justicia Gratuita: "
                                    + (String) resultado[2]);
                }
            } else {
            	throw new Exception("Ha ocurrido un error al ejecutar la regularización de EJG, el resultado del PL es null");
            }
            
        } catch (IOException | NamingException | SQLException e) {
        	String error = "Error en PL al ejecutar la regularización de EJG= " + (String) resultado[2];
            LOGGER.error(error);
            logErroresFac.logError(error);
            throw new Exception("Ha ocurrido un error al ejecutar la regularización de EJG", e);
        }

        importeEJG = new Double(((String) resultado[0]).replaceAll(",", "."));
        importeTotal += importeEJG.doubleValue();

        // ACTUALIZO LOS TOTALES
        item.setImporteejg(new BigDecimal(importeEJG));
        item.setImporteguardia(new BigDecimal(importeGuardia));
        item.setImporteoficio(new BigDecimal(importeOficio));
        item.setImportesoj(new BigDecimal(importeSOJ));
        item.setImportetotal(new BigDecimal(importeTotal));

        fcsFacturacionjgMapper.updateByPrimaryKey(item);
        
        if(logErroresFac.haveErrors()) {
        	logErroresFac.writeAllErrors();
        } else {
        	logErroresFac.writeExito(REGULARIZACION);
        }

        return item;

    }

    /**
     * Recupera el datasource con los datos de conexión sacados del fichero de
     * configuracion
     *
     * @return
     * @throws IOException
     * @throws NamingException
     */
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

    /**
     * Calls a PL Funtion
     *
     * @param functionDefinition string that defines the function
     * @param inParameters       input parameters
     * @param outParameters      number of output parameters
     * @return error code, '0' if ok
     * @throws NamingException
     * @throws IOException
     * @throws SQLException    type Exception
     * @author CSD
     */
    private String[] callPLProcedure(String functionDefinition, int outParameters, Object[] inParameters)
            throws IOException, NamingException, SQLException {
        String result[] = null;

        if (outParameters > 0)
            result = new String[outParameters];
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
                    break;

                } catch (SQLTimeoutException tex) {
                    throw tex;

                } catch (SQLException ex) {
                    if (ex.getErrorCode() != 4068 || intento == 2) { // JPT: 4068 es un error de descompilado (la
                        // segunda vez deberia funcionar)
                        throw ex;
                    }
                }

            }

            for (int i = 0; i < outParameters; i++) {
                result[i] = cs.getString(i + size + 1);
            }
            cs.close();
            return result;

        } catch (SQLTimeoutException ex) {
            return null;
        } catch (SQLException ex) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            con.close();
            con = null;
        }
    }

}