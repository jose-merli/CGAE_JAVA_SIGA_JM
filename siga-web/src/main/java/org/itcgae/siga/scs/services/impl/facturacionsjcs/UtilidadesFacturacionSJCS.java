package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.FcsFacturacionjgMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFacturacionJGExtendsMapper;
import org.itcgae.siga.exception.FacturacionSJCSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Hashtable;

@Service
public class UtilidadesFacturacionSJCS {
    private final Logger LOGGER = Logger.getLogger(UtilidadesFacturacionSJCS.class);

    @Autowired
    private GenParametrosExtendsMapper genParametrosExtendsMapper;

    @Autowired
    private FcsFacturacionjgMapper fcsFacturacionjgMapper;

    @Autowired
    private FcsFacturacionJGExtendsMapper fcsFacturacionJGExtendsMapper;

    @Autowired
    private AdmConfigMapper admConfigMapper;

    @Autowired
    private CenInstitucionMapper cenInstitucionMapper;

    @Autowired
    private EjecucionPlsPago ejecucionPlsPago;

    @Autowired
    private GenRecursosMapper genRecursosMapper;

    private Hashtable getNombreFicherosFacturacion(Short idInstitucion, Integer idFacturacion) throws Exception {
        return getNombreFicherosFac(idInstitucion, idFacturacion, null, null);
    }

    private Hashtable getNombreFicherosPago(Short idInstitucion, Integer idFacturacion, Integer idPago, Long idPersona) throws Exception {
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
            if (!resultado[0].equalsIgnoreCase("0")) {
                //ClsLogging.writeFileLog("Error en PL = " + (String) resultado[1], 3);
                LOGGER.error("Error en PL = " + (String) resultado[1]);
            }

            // EJG
            param_in[5] = nombreFicheros.get(SigaConstants.HITO_GENERAL_EJG).toString();
            param_in[6] = getCabecerasFicheros(SigaConstants.HITO_GENERAL_EJG, tipoP, usuario);
            resultado = new String[2];
            resultado = ejecucionPlsPago.callPLProcedure("{call PKG_SIGA_FACTURACION_SJCS.PROC_FCS_EXPORTAR_EJG (?,?,?,?,?,?,?,?,?)}", 2, param_in);
            if (!resultado[0].equalsIgnoreCase("0")) {
                //ClsLogging.writeFileLog("Error en PL = " + (String) resultado[1], 3);
                LOGGER.error("Error en PL = " + (String) resultado[1]);
            }

            // GUARDIAS
            ejecucionPlsPago.ejecutarPLExportarGuardias(idInstitucion.toString(), idFacturacion.toString(), null, (idPersona == null ? "" : idPersona.toString()),
                    pathFicheros, nombreFicheros.get(SigaConstants.HITO_GENERAL_GUARDIA).toString(), beanLenguajeIntitucion.getIdlenguaje(), usuario);
        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al exportar datos", e, getMensajeIdioma(usuario.getIdlenguaje(), "messages.factSJCS.error.exportDatos"));
        }
    }
}
