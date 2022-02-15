package org.itcgae.siga.db.services.fac.providers;

import org.apache.commons.beanutils.converters.SqlTimeConverter;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FacEstadoConfirmacionFact;
import org.itcgae.siga.DTO.fac.FacEstadosFacturacion;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.FacFacturacionprogramadaSqlProvider;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;

public class FacFacturacionprogramadaExtendsSqlProvider extends FacFacturacionprogramadaSqlProvider {



    public String getFacturacionesProgramadas(FacFacturacionprogramadaItem facturacionProgramada, Short idInstitucion, String idioma, Integer rownum) {
        SQL sql = new SQL();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Select
        sql.SELECT("facprog.idprogramacion");
        sql.SELECT("facprog.idseriefacturacion");
        sql.SELECT("facprog.descripcion");
        sql.SELECT("seriefac.nombreabreviado");
        sql.SELECT("facprog.fechainicioproductos");
        sql.SELECT("facprog.fechafinproductos");
        sql.SELECT("facprog.fechainicioservicios");
        sql.SELECT("facprog.fechafinservicios");
        sql.SELECT("facprog.fechaprogramacion");
        sql.SELECT("facprog.fechaprevistageneracion");
        sql.SELECT("facprog.fechaprevistaconfirm");
        sql.SELECT("facprog.fechaconfirmacion");
        sql.SELECT("facprog.fecharealgeneracion");
        sql.SELECT("facprog.idestadoconfirmacion");
        sql.SELECT("( SELECT r.descripcion FROM fac_estadoconfirmfact f, gen_recursos r WHERE f.descripcion = r.idrecurso AND r.idlenguaje = '" + idioma + "' AND f.idestado = facprog.idestadoconfirmacion ) estadoconfirmacion");
        sql.SELECT("facprog.idestadopdf");
        sql.SELECT("( SELECT r.descripcion FROM fac_estadoconfirmfact f, gen_recursos r WHERE f.descripcion = r.idrecurso AND r.idlenguaje = '" + idioma + "' AND f.idestado = facprog.idestadopdf ) estadopdf");
        sql.SELECT("facprog.idestadoenvio");
        sql.SELECT("( SELECT r.descripcion FROM fac_estadoconfirmfact f, gen_recursos r WHERE f.descripcion = r.idrecurso AND r.idlenguaje = '" + idioma + "' AND f.idestado = facprog.idestadoenvio ) estadoenvio");
        sql.SELECT("facprog.idestadotraspaso");
        sql.SELECT("( SELECT r.descripcion FROM fac_estadoconfirmfact f, gen_recursos r WHERE f.descripcion = r.idrecurso AND r.idlenguaje = '" + idioma + "' AND f.idestado = facprog.idestadotraspaso ) estadotraspaso");
        sql.SELECT("facprog.archivarfact");
        sql.SELECT("facprog.usumodificacion");
        sql.SELECT("facprog.nombrefichero");
        sql.SELECT("facprog.logerror");
        sql.SELECT("facprog.logtraspaso");
        sql.SELECT("facprog.traspasofacturas");
        sql.SELECT("facprog.generapdf");
        sql.SELECT("facprog.envio");
        sql.SELECT("facprog.idtipoplantillamail");
        sql.SELECT("( SELECT c.nombre FROM env_plantillasenvios c WHERE c.idinstitucion = facprog.idinstitucion AND c.idplantillaenvios = facprog.idtipoplantillamail AND c.idtipoenvios = facprog.idtipoenvios ) tipoplantillamail");
        sql.SELECT("facprog.traspaso_plantilla");
        sql.SELECT("facprog.traspaso_codauditoria_def");
        sql.SELECT("SUM(f.imptotal) AS importe");
        sql.SELECT("facprog.fechapresentacion");
        sql.SELECT("facprog.fecharecibosprimeros");
        sql.SELECT("facprog.fecharecibosrecurrentes");
        sql.SELECT("facprog.fechareciboscor1");
        sql.SELECT("facprog.fecharecibosb2b");
        sql.SELECT("facprog.fechamodificacion");
        sql.SELECT("seriefac.idmodelofactura");
        sql.SELECT("seriefac.idmodelorectificativa");
        sql.SELECT("( SELECT c.nombre FROM mod_modelocomunicacion c WHERE c.idmodelocomunicacion = seriefac.idmodelofactura ) modelofactura");
        sql.SELECT("( SELECT c.nombre FROM mod_modelocomunicacion c WHERE c.idmodelocomunicacion = seriefac.idmodelorectificativa ) modelorectificativa");

        // From
        sql.FROM("fac_facturacionprogramada facprog");

        // Joins
        sql.LEFT_OUTER_JOIN("fac_seriefacturacion seriefac ON ( facprog.idinstitucion = seriefac.idinstitucion AND facprog.idseriefacturacion = seriefac.idseriefacturacion )");
        sql.LEFT_OUTER_JOIN("fac_factura f ON ( f.idinstitucion = facprog.idinstitucion AND f.idseriefacturacion = facprog.idseriefacturacion AND f.idprogramacion = facprog.idprogramacion )");

        //Where
        sql.WHERE("facprog.idinstitucion = seriefac.idinstitucion");
        sql.WHERE("facprog.idseriefacturacion = seriefac.idseriefacturacion");
        sql.WHERE("nvl(facprog.visible,'N') = 'S'");
        sql.WHERE("facprog.idinstitucion = " + idInstitucion);

        // Buscar por idprogramacion
        if (!UtilidadesString.esCadenaVacia(facturacionProgramada.getIdProgramacion()))
            sql.WHERE("facprog.idprogramacion = " + facturacionProgramada.getIdProgramacion());

        // Filtros

        if (!UtilidadesString.esCadenaVacia(facturacionProgramada.getIdSerieFacturacion()))
            sql.WHERE("seriefac.idseriefacturacion = " + facturacionProgramada.getIdSerieFacturacion());

        if (!UtilidadesString.esCadenaVacia(facturacionProgramada.getCompraSuscripcion())) {
            switch (facturacionProgramada.getCompraSuscripcion()) {
                case "0":
                    sql.WHERE("((facprog.fechainicioproductos IS NOT NULL OR facprog.fechafinproductos IS NOT NULL) AND (facprog.fechainicioservicios IS NOT NULL OR facprog.fechafinservicios IS NOT NULL))");
                    break;
                case "1":
                    sql.WHERE("((facprog.fechainicioproductos IS NOT NULL OR facprog.fechafinproductos IS NOT NULL) AND (facprog.fechainicioservicios IS NULL AND facprog.fechafinservicios IS NULL))");
                    break;
                case "2":
                    sql.WHERE("((facprog.fechainicioproductos IS NULL AND facprog.fechafinproductos IS NULL) AND (facprog.fechainicioservicios IS NOT NULL OR facprog.fechafinservicios IS NOT NULL))");
                    break;
            }
        }

        if (facturacionProgramada.getFechaCompraSuscripcionDesde() != null)
            sql.WHERE("(facprog.fechainicioproductos >= TO_DATE('" + dateFormat.format(facturacionProgramada.getFechaCompraSuscripcionDesde()) + "','DD/MM/YYYY hh24:mi:ss') " +
                    "OR facprog.fechainicioservicios >= TO_DATE('" + dateFormat.format(facturacionProgramada.getFechaCompraSuscripcionDesde()) + "','DD/MM/YYYY hh24:mi:ss'))");
        if (facturacionProgramada.getFechaCompraSuscripcionHasta() != null)
            sql.WHERE("(facprog.fechafinproductos <= TO_DATE('" + dateFormat.format(facturacionProgramada.getFechaCompraSuscripcionHasta()) + "','DD/MM/YYYY hh24:mi:ss') " +
                    "OR facprog.fechafinservicios <= TO_DATE('" + dateFormat.format(facturacionProgramada.getFechaCompraSuscripcionHasta()) + "','DD/MM/YYYY hh24:mi:ss'))");

        if (!UtilidadesString.esCadenaVacia(facturacionProgramada.getIdEstadoConfirmacion()))
            sql.WHERE("facprog.idestadoconfirmacion = " + facturacionProgramada.getIdEstadoConfirmacion());
        if (!UtilidadesString.esCadenaVacia(facturacionProgramada.getIdEstadoPDF()))
            sql.WHERE("facprog.idestadopdf = " + facturacionProgramada.getIdEstadoPDF());
        if (!UtilidadesString.esCadenaVacia(facturacionProgramada.getIdEstadoEnvio()))
            sql.WHERE("facprog.idestadoenvio = " + facturacionProgramada.getIdEstadoEnvio());
        if (!UtilidadesString.esCadenaVacia(facturacionProgramada.getIdEstadoTraspaso()))
            sql.WHERE("facprog.idestadotraspaso = " + facturacionProgramada.getIdEstadoTraspaso());

        if (facturacionProgramada.getFechaPrevistaGeneracionDesde() != null)
            sql.WHERE("facprog.fechaprevistageneracion >= TO_DATE('"+ dateFormat.format(facturacionProgramada.getFechaPrevistaGeneracionDesde()) + "','DD/MM/YYYY hh24:mi:ss')");
        if (facturacionProgramada.getFechaPrevistaGeneracionHasta() != null)
            sql.WHERE("facprog.fechaprevistageneracion <= TO_DATE('"+ dateFormat.format(facturacionProgramada.getFechaPrevistaGeneracionHasta()) + "','DD/MM/YYYY hh24:mi:ss')");

        if (facturacionProgramada.getFechaPrevistaConfirmDesde() != null)
            sql.WHERE("facprog.fechaprevistaconfirm >= TO_DATE('"+ dateFormat.format(facturacionProgramada.getFechaPrevistaConfirmDesde()) + "','DD/MM/YYYY hh24:mi:ss')");
        if (facturacionProgramada.getFechaPrevistaConfirmHasta() != null)
            sql.WHERE("facprog.fechaprevistaconfirm <= TO_DATE('"+ dateFormat.format(facturacionProgramada.getFechaPrevistaConfirmHasta()) + "','DD/MM/YYYY hh24:mi:ss')");

        if (facturacionProgramada.getFechaRealGeneracionDesde() != null)
            sql.WHERE("facprog.fecharealgeneracion >= TO_DATE('"+ dateFormat.format(facturacionProgramada.getFechaRealGeneracionDesde()) + "','DD/MM/YYYY hh24:mi:ss')");
        if (facturacionProgramada.getFechaRealGeneracionHasta() != null)
            sql.WHERE("facprog.fecharealgeneracion <= TO_DATE('"+ dateFormat.format(facturacionProgramada.getFechaRealGeneracionHasta()) + "','DD/MM/YYYY hh24:mi:ss')");

        if (facturacionProgramada.getFechaConfirmacionDesde() != null)
            sql.WHERE("facprog.fechaconfirmacion >= TO_DATE('"+ dateFormat.format(facturacionProgramada.getFechaConfirmacionDesde()) + "','DD/MM/YYYY hh24:mi:ss')");
        if (facturacionProgramada.getFechaConfirmacionHasta() != null)
            sql.WHERE("facprog.fechaconfirmacion <= TO_DATE('"+ dateFormat.format(facturacionProgramada.getFechaConfirmacionHasta()) + "','DD/MM/YYYY hh24:mi:ss')");

        // Group by
        sql.GROUP_BY("facprog.idinstitucion");
        sql.GROUP_BY("facprog.idprogramacion");
        sql.GROUP_BY("facprog.idseriefacturacion");
        sql.GROUP_BY("facprog.descripcion");
        sql.GROUP_BY("seriefac.nombreabreviado");
        sql.GROUP_BY("facprog.fechainicioproductos");
        sql.GROUP_BY("facprog.fechafinproductos");
        sql.GROUP_BY("facprog.fechainicioservicios");
        sql.GROUP_BY("facprog.fechafinservicios");
        sql.GROUP_BY("facprog.fechaprogramacion");
        sql.GROUP_BY("facprog.fechaprevistageneracion");
        sql.GROUP_BY("facprog.fechaprevistaconfirm");
        sql.GROUP_BY("facprog.fechaconfirmacion");
        sql.GROUP_BY("facprog.fecharealgeneracion");
        sql.GROUP_BY("facprog.idestadoconfirmacion");
        sql.GROUP_BY("facprog.idestadopdf");
        sql.GROUP_BY("facprog.idestadoenvio");
        sql.GROUP_BY("facprog.idestadotraspaso");
        sql.GROUP_BY("facprog.archivarfact");
        sql.GROUP_BY("facprog.idprogramacion");
        sql.GROUP_BY("facprog.usumodificacion");
        sql.GROUP_BY("facprog.nombrefichero");
        sql.GROUP_BY("facprog.logerror");
        sql.GROUP_BY("facprog.logtraspaso");
        sql.GROUP_BY("facprog.traspasofacturas");
        sql.GROUP_BY("facprog.generapdf");
        sql.GROUP_BY("facprog.envio");
        sql.GROUP_BY("facprog.idtipoenvios");
        sql.GROUP_BY("facprog.idtipoplantillamail");
        sql.GROUP_BY("facprog.traspaso_plantilla");
        sql.GROUP_BY("facprog.traspaso_codauditoria_def");
        sql.GROUP_BY("facprog.fechapresentacion");
        sql.GROUP_BY("facprog.fecharecibosprimeros");
        sql.GROUP_BY("facprog.fecharecibosrecurrentes");
        sql.GROUP_BY("facprog.fechareciboscor1");
        sql.GROUP_BY("facprog.fecharecibosb2b");
        sql.GROUP_BY("facprog.fechamodificacion");
        sql.GROUP_BY("seriefac.idmodelofactura");
        sql.GROUP_BY("seriefac.idmodelorectificativa");

        // Order by
        sql.ORDER_BY("fecharealgeneracion DESC");

        // Limitamos el número de resultados
        SQL sqlGlobal = new SQL();
        sqlGlobal.SELECT("*");
        sqlGlobal.FROM("(" + sql.toString() + ")");
        sqlGlobal.WHERE("ROWNUM < " + rownum);

        if (!UtilidadesString.esCadenaVacia(facturacionProgramada.getImporteDesde()))
            sqlGlobal.WHERE("importe >= to_number('" + facturacionProgramada.getImporteDesde() + "', '99999999999999999.99')");
        if (!UtilidadesString.esCadenaVacia(facturacionProgramada.getImporteHasta()))
            sqlGlobal.WHERE("importe <= to_number('" + facturacionProgramada.getImporteHasta() + "', '99999999999999999.99')");

        return sqlGlobal.toString();
    }

    public String getNextIdFacturacionProgramada(Short idInstitucion, Long idSerieFacturacion) {
        SQL sql = new SQL();

        sql.SELECT("(NVL(MAX(fp.idprogramacion),0) + 1) as idprogramacion");
        sql.FROM("fac_facturacionprogramada fp");
        sql.WHERE("fp.idinstitucion = " + idInstitucion);
        sql.WHERE("fp.idseriefacturacion = " + idSerieFacturacion);

        return sql.toString();
    }

    public String comboFacturaciones(Short idInstitucion) {
        SQL sql = new SQL();

        // Select
        sql.SELECT("idprogramacion || '-' || idseriefacturacion id, descripcion");

        // From
        sql.FROM("fac_facturacionprogramada f");

        // Where
        sql.WHERE("idinstitucion = '" + idInstitucion + "'");
        sql.WHERE("f.visible = 'S'");

        // Order by
        sql.ORDER_BY("idprogramacion, idseriefacturacion");

        return sql.toString();
    }

    public String isSerieFacturacionActiva(Short idInstitucion, Long idSerieFacturacion, Long idProgramacion) {
        SQL sql = new SQL();

        sql.SELECT("CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END EXISTE");
        sql.FROM("FAC_FACTURACIONPROGRAMADA FP");
        sql.WHERE("FP.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("FP.IDSERIEFACTURACION = " + idSerieFacturacion);
        sql.WHERE("FP.IDPROGRAMACION = " + idProgramacion);
        sql.WHERE("FP.TRASPASOFACTURAS = '1'");

        return sql.toString();
    }

    private SQL selectDatosFacturacion() {
        SQL sql = new SQL();
        sql.SELECT("FP.IDINSTITUCION");
        sql.SELECT("FP.IDSERIEFACTURACION");
        sql.SELECT("FP.IDPROGRAMACION");
        sql.SELECT("FP.FECHAINICIOPRODUCTOS");
        sql.SELECT("FP.FECHAFINPRODUCTOS");
        sql.SELECT("FP.FECHAINICIOSERVICIOS");
        sql.SELECT("FP.FECHAFINSERVICIOS");
        sql.SELECT("FP.FECHAPROGRAMACION");
        sql.SELECT("FP.FECHAPREVISTAGENERACION");
        sql.SELECT("FP.FECHAMODIFICACION");
        sql.SELECT("FP.USUMODIFICACION");
        sql.SELECT("FP.IDPREVISION");
        sql.SELECT("FP.FECHAREALGENERACION");
        sql.SELECT("FP.FECHACONFIRMACION");
        sql.SELECT("FP.IDESTADOCONFIRMACION");
        sql.SELECT("FP.IDESTADOPDF");
        sql.SELECT("FP.IDESTADOENVIO");
        sql.SELECT("FP.FECHAPREVISTACONFIRM");
        sql.SELECT("FP.GENERAPDF");
        sql.SELECT("FP.ENVIO");
        sql.SELECT("FP.ARCHIVARFACT");
        sql.SELECT("FP.FECHACARGO");
        sql.SELECT("FP.CONFDEUDOR");
        sql.SELECT("FP.CONFINGRESOS");
        sql.SELECT("FP.CTAINGRESOS");
        sql.SELECT("FP.CTACLIENTES");
        sql.SELECT("FP.VISIBLE");
        sql.SELECT("FP.DESCRIPCION");
        sql.SELECT("FP.IDTIPOPLANTILLAMAIL");
        sql.SELECT("FP.IDTIPOENVIOS");
        sql.SELECT("FP.FECHAPRESENTACION");
        sql.SELECT("FP.FECHARECIBOSPRIMEROS");
        sql.SELECT("FP.FECHARECIBOSRECURRENTES");
        sql.SELECT("FP.FECHARECIBOSCOR1");
        sql.SELECT("FP.FECHARECIBOSB2B");
        sql.SELECT("FP.NOMBREFICHERO");
        sql.SELECT("FP.LOGERROR");
        sql.SELECT("FP.TRASPASO_PLANTILLA");
        sql.SELECT("FP.TRASPASO_CODAUDITORIA_DEF");
        sql.SELECT("FP.TRASPASOFACTURAS");
        sql.SELECT("FP.IDESTADOTRASPASO");
        sql.SELECT("FP.LOGTRASPASO");
        sql.SELECT("FP.IDMODELOFACTURA");
        sql.SELECT("FP.IDMODELORECTIFICATIVA");
        sql.SELECT("SF.NOMBREABREVIADO");
        sql.FROM("FAC_FACTURACIONPROGRAMADA FP");
        sql.JOIN("FAC_SERIEFACTURACION SF ON FP.IDINSTITUCION = SF.IDINSTITUCION AND FP.IDSERIEFACTURACION = SF.IDSERIEFACTURACION");
        return sql;
    }

    public String getFacturacionesProTratarFacturacion(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada) {
        SQL sql = selectDatosFacturacion();
        sql.WHERE("FP.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("FP.FECHAREALGENERACION IS NULL");
        sql.WHERE("FP.FECHAPREVISTAGENERACION IS NOT NULL");
        sql.WHERE("FP.FECHAPREVISTAGENERACION <= SYSDATE");
        sql.WHERE(MessageFormat.format("(FP.IDESTADOCONFIRMACION = {0} OR (FP.IDESTADOCONFIRMACION = {1} AND SYSDATE - {2} > FP.FECHAMODIFICACION))",
                FacEstadoConfirmacionFact.GENERACION_PROGRAMADA.getId(), FacEstadoConfirmacionFact.EJECUTANDO_GENERACION.getId(), tiempoMaximoEjecucionBloqueada));
        sql.ORDER_BY("FP.FECHAPREVISTAGENERACION");
        return sql.toString();
    }

    public String getFacturacionesProTratarConfirmacion(Short idInstitucion) {
        SQL subQuery = new SQL();
        subQuery.SELECT("1");
        subQuery.FROM("FAC_FACTURACIONPROGRAMADA PREVIA");
        subQuery.WHERE("PREVIA.IDINSTITUCION = " + idInstitucion);
        subQuery.WHERE("PREVIA.IDSERIEFACTURACION = SF.IDSERIEFACTURACIONPREVIA");
        subQuery.WHERE("PREVIA.VISIBLE = 'S'");
        subQuery.WHERE(MessageFormat.format("PREVIA.IDESTADOCONFIRMACION <> {0}", FacEstadoConfirmacionFact.CONFIRM_FINALIZADA.toString()));

        SQL sql = selectDatosFacturacion();
        sql.WHERE("FP.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("FP.FECHAPREVISTACONFIRM IS NOT NULL");
        sql.WHERE("FP.FECHAPREVISTACONFIRM <= SYSDATE");
        sql.WHERE("FP.FECHAREALGENERACION IS NOT NULL");
        sql.WHERE("FP.VISIBLE = 'S'");
        sql.WHERE(MessageFormat.format("FP.IDESTADOCONFIRMACION IN({0})", String.join(FacEstadoConfirmacionFact.GENERADA.toString(), FacEstadoConfirmacionFact.CONFIRM_PROGRAMADA.toString())));
        sql.WHERE(MessageFormat.format("NOT EXISTS ({0})", subQuery.toString()));
        sql.ORDER_BY("FP.FECHAPREVISTACONFIRM");
        return sql.toString();
    }

    public String getFacturacionesProGenerarPDFsYenviarFacturasProgramacion(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada) {
        SQL sql = selectDatosFacturacion();
        sql.WHERE("FP.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("FP.FECHAPREVISTACONFIRM IS NOT NULL");
        sql.WHERE("FP.FECHAPREVISTACONFIRM <= SYSDATE");
        sql.WHERE("FP.FECHAREALGENERACION IS NOT NULL");
        sql.WHERE(MessageFormat.format("FP.IDESTADOCONFIRMACION = {0}", FacEstadoConfirmacionFact.CONFIRM_FINALIZADA.toString()));
        sql.WHERE(MessageFormat.format("(FP.IDESTADOPDF = {0} OR (FP.IDESTADOPDF = {1} AND SYSDATE - {2} > FP.FECHAMODIFICACION))",
                FacEstadoConfirmacionFact.PDF_PROGRAMADA.toString(), FacEstadoConfirmacionFact.PDF_PROCESANDO.toString(), tiempoMaximoEjecucionBloqueada));
        sql.ORDER_BY("FP.FECHAPREVISTAGENERACION");
        return sql.toString();
    }

    public String getFacturacionesProGenerarEnviosFacturasPendientes(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada) {
        SQL sql = selectDatosFacturacion();
        sql.WHERE("FP.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("FP.FECHAPREVISTACONFIRM IS NOT NULL");
        sql.WHERE("FP.FECHAPREVISTACONFIRM <= SYSDATE");
        sql.WHERE("FP.FECHAREALGENERACION IS NOT NULL");
        sql.WHERE(MessageFormat.format("FP.IDESTADOCONFIRMACION = {0}", FacEstadoConfirmacionFact.CONFIRM_FINALIZADA.toString()));
        sql.WHERE(MessageFormat.format("FP.IDESTADOPDF = {0}", FacEstadoConfirmacionFact.PDF_FINALIZADA.toString()));
        sql.WHERE(MessageFormat.format("(FP.IDESTADOENVIO = {0} OR (FP.IDESTADOENVIO = {1} AND SYSDATE - {2} > FP.FECHAMODIFICACION))",
                FacEstadoConfirmacionFact.ENVIO_PROGRAMADA.toString(), FacEstadoConfirmacionFact.ENVIO_PROCESANDO.toString(), tiempoMaximoEjecucionBloqueada));
        sql.ORDER_BY("FP.FECHAPREVISTAGENERACION");
        return sql.toString();
    }

    public String getFacturacionesProComprobacionTraspasoFacturas(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada) {
        SQL subQuery = new SQL();
        subQuery.SELECT("1");
        subQuery.FROM("FAC_FACTURA F");
        subQuery.WHERE("F.IDINSTITUCION = FP.IDINSTITUCION");
        subQuery.WHERE("F.IDSERIEFACTURACION = FP.IDSERIEFACTURACION");
        subQuery.WHERE("F.IDPROGRAMACION = FP.IDPROGRAMACION");
        subQuery.WHERE("F.TRASPASADA IS NULL");

        SQL sql = selectDatosFacturacion();
        sql.WHERE("FP.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("FP.FECHACONFIRMACION IS NOT NULL");
        sql.WHERE(MessageFormat.format("FP.IDESTADOCONFIRMACION = {0}", FacEstadoConfirmacionFact.CONFIRM_FINALIZADA.toString()));
        sql.WHERE(MessageFormat.format("FP.IDESTADOTRASPASO = {0}", FacEstadoConfirmacionFact.TRASPASO_PROCESANDO.toString()));
        sql.WHERE(MessageFormat.format("(NOT EXISTS ({0}) OR SYSDATE - {1} > FP.FECHAMODIFICACION)", subQuery.toString(), tiempoMaximoEjecucionBloqueada));
        sql.ORDER_BY("FP.FECHAPREVISTAGENERACION");
        return sql.toString();
    }

    public String getNumTotalFacturacionesProTratarFacturacion(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada) {
        SQL sql = new SQL();
        sql.SELECT("COUNT(1) TOTAL");
        sql.FROM("FAC_FACTURACIONPROGRAMADA FP JOIN FAC_SERIEFACTURACION SF ON FP.IDINSTITUCION = SF.IDINSTITUCION AND FP.IDSERIEFACTURACION = SF.IDSERIEFACTURACION");
        sql.WHERE("FP.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("FP.FECHAREALGENERACION IS NULL");
        sql.WHERE("FP.FECHAPREVISTAGENERACION IS NOT NULL");
        sql.WHERE("FP.FECHAPREVISTAGENERACION <= SYSDATE");
        sql.WHERE(MessageFormat.format("(FP.IDESTADOCONFIRMACION = {0} OR (FP.IDESTADOCONFIRMACION = {1} AND SYSDATE - {2} > FP.FECHAMODIFICACION))",
                FacEstadoConfirmacionFact.GENERACION_PROGRAMADA.getId(), FacEstadoConfirmacionFact.EJECUTANDO_GENERACION.getId(), tiempoMaximoEjecucionBloqueada));
        sql.ORDER_BY("FP.FECHAPREVISTAGENERACION");
        return sql.toString();
    }

    public String getPosicionFacturacionProTratarFacturacion(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada,
                                                             String idSerieFacturacion, String idProgramacion) {
        SQL sql = new SQL();
        sql.SELECT("FP.*");
        sql.FROM("FAC_FACTURACIONPROGRAMADA FP JOIN FAC_SERIEFACTURACION SF ON FP.IDINSTITUCION = SF.IDINSTITUCION AND FP.IDSERIEFACTURACION = SF.IDSERIEFACTURACION");
        sql.WHERE("FP.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("FP.FECHAREALGENERACION IS NULL");
        sql.WHERE("FP.FECHAPREVISTAGENERACION IS NOT NULL");
        sql.WHERE("FP.FECHAPREVISTAGENERACION <= SYSDATE");
        sql.WHERE(MessageFormat.format("(FP.IDESTADOCONFIRMACION = {0} OR (FP.IDESTADOCONFIRMACION = {1} AND SYSDATE - {2} > FP.FECHAMODIFICACION))",
                FacEstadoConfirmacionFact.GENERACION_PROGRAMADA.getId(), FacEstadoConfirmacionFact.EJECUTANDO_GENERACION.getId(), tiempoMaximoEjecucionBloqueada));
        sql.ORDER_BY("FP.FECHAPREVISTAGENERACION");

        SQL a = new SQL();
        a.SELECT("ROWNUM POSICION");
        a.SELECT("A.*");
        a.FROM("( " + sql + " ) A");

        SQL b = new SQL();
        b.SELECT("B.POSICION");
        b.FROM("(" + a + " ) B");
        b.WHERE("B.IDINSTITUCION = " + idInstitucion);
        b.WHERE("B.IDSERIEFACTURACION = " + idSerieFacturacion);
        b.WHERE("B.IDPROGRAMACION = " + idProgramacion);

        return b.toString();
    }

    public String getNumTotalFacturacionesProTratarConfirmacion(Short idInstitucion) {
        SQL subQuery = new SQL();
        subQuery.SELECT("1");
        subQuery.FROM("FAC_FACTURACIONPROGRAMADA PREVIA");
        subQuery.WHERE("PREVIA.IDINSTITUCION = " + idInstitucion);
        subQuery.WHERE("PREVIA.IDSERIEFACTURACION = SF.IDSERIEFACTURACIONPREVIA");
        subQuery.WHERE("PREVIA.VISIBLE = 'S'");
        subQuery.WHERE(MessageFormat.format("PREVIA.IDESTADOCONFIRMACION <> {0}", FacEstadoConfirmacionFact.CONFIRM_FINALIZADA.toString()));

        SQL sql = new SQL();
        sql.SELECT("COUNT(1) TOTAL");
        sql.FROM("FAC_FACTURACIONPROGRAMADA FP JOIN FAC_SERIEFACTURACION SF ON FP.IDINSTITUCION = SF.IDINSTITUCION AND FP.IDSERIEFACTURACION = SF.IDSERIEFACTURACION");
        sql.WHERE("FP.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("FP.FECHAPREVISTACONFIRM IS NOT NULL");
        sql.WHERE("FP.FECHAPREVISTACONFIRM <= SYSDATE");
        sql.WHERE("FP.FECHAREALGENERACION IS NOT NULL");
        sql.WHERE("FP.VISIBLE = 'S'");
        sql.WHERE(MessageFormat.format("FP.IDESTADOCONFIRMACION IN({0})", String.join(FacEstadoConfirmacionFact.GENERADA.toString(), FacEstadoConfirmacionFact.CONFIRM_PROGRAMADA.toString())));
        sql.WHERE(MessageFormat.format("NOT EXISTS ({0})", subQuery.toString()));
        sql.ORDER_BY("FP.FECHAPREVISTACONFIRM");
        return sql.toString();
    }

    public String getPosicionFacturacionProTratarConfirmacion(Short idInstitucion, String idSerieFacturacion, String idProgramacion) {
        SQL subQuery = new SQL();
        subQuery.SELECT("1");
        subQuery.FROM("FAC_FACTURACIONPROGRAMADA PREVIA");
        subQuery.WHERE("PREVIA.IDINSTITUCION = " + idInstitucion);
        subQuery.WHERE("PREVIA.IDSERIEFACTURACION = SF.IDSERIEFACTURACIONPREVIA");
        subQuery.WHERE("PREVIA.VISIBLE = 'S'");
        subQuery.WHERE(MessageFormat.format("PREVIA.IDESTADOCONFIRMACION <> {0}", FacEstadoConfirmacionFact.CONFIRM_FINALIZADA.toString()));

        SQL sql = new SQL();
        sql.SELECT("FP.*");
        sql.FROM("FAC_FACTURACIONPROGRAMADA FP JOIN FAC_SERIEFACTURACION SF ON FP.IDINSTITUCION = SF.IDINSTITUCION AND FP.IDSERIEFACTURACION = SF.IDSERIEFACTURACION");
        sql.WHERE("FP.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("FP.FECHAPREVISTACONFIRM IS NOT NULL");
        sql.WHERE("FP.FECHAPREVISTACONFIRM <= SYSDATE");
        sql.WHERE("FP.FECHAREALGENERACION IS NOT NULL");
        sql.WHERE("FP.VISIBLE = 'S'");
        sql.WHERE(MessageFormat.format("FP.IDESTADOCONFIRMACION IN({0})", String.join(FacEstadoConfirmacionFact.GENERADA.toString(), FacEstadoConfirmacionFact.CONFIRM_PROGRAMADA.toString())));
        sql.WHERE(MessageFormat.format("NOT EXISTS ({0})", subQuery.toString()));
        sql.ORDER_BY("FP.FECHAPREVISTACONFIRM");

        SQL a = new SQL();
        a.SELECT("ROWNUM POSICION");
        a.SELECT("A.*");
        a.FROM("( " + sql + " ) A");

        SQL b = new SQL();
        b.SELECT("B.POSICION");
        b.FROM("(" + a + " ) B");
        b.WHERE("B.IDINSTITUCION = " + idInstitucion);
        b.WHERE("B.IDSERIEFACTURACION = " + idSerieFacturacion);
        b.WHERE("B.IDPROGRAMACION = " + idProgramacion);

        return b.toString();
    }

    public String getNumTotalFacturacionesProGenerarPDFsYenviarFacturasProgramacion(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada) {
        SQL sql = new SQL();
        sql.SELECT("COUNT(1) TOTAL");
        sql.FROM("FAC_FACTURACIONPROGRAMADA FP JOIN FAC_SERIEFACTURACION SF ON FP.IDINSTITUCION = SF.IDINSTITUCION AND FP.IDSERIEFACTURACION = SF.IDSERIEFACTURACION");
        sql.WHERE("FP.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("FP.FECHAPREVISTACONFIRM IS NOT NULL");
        sql.WHERE("FP.FECHAPREVISTACONFIRM <= SYSDATE");
        sql.WHERE("FP.FECHAREALGENERACION IS NOT NULL");
        sql.WHERE(MessageFormat.format("FP.IDESTADOCONFIRMACION = {0}", FacEstadoConfirmacionFact.CONFIRM_FINALIZADA.toString()));
        sql.WHERE(MessageFormat.format("(FP.IDESTADOPDF = {0} OR (FP.IDESTADOPDF = {1} AND SYSDATE - {2} > FP.FECHAMODIFICACION))",
                FacEstadoConfirmacionFact.PDF_PROGRAMADA.toString(), FacEstadoConfirmacionFact.PDF_PROCESANDO.toString(), tiempoMaximoEjecucionBloqueada));
        sql.ORDER_BY("FP.FECHAPREVISTAGENERACION");
        return sql.toString();
    }

    public String getPosicionFacturacionProGenerarPDFsYenviarFacturasProgramacion(Short idInstitucion, String idSerieFacturacion, String idProgramacion, Double tiempoMaximoEjecucionBloqueada) {
        SQL sql = new SQL();
        sql.SELECT("FP.*");
        sql.FROM("FAC_FACTURACIONPROGRAMADA FP JOIN FAC_SERIEFACTURACION SF ON FP.IDINSTITUCION = SF.IDINSTITUCION AND FP.IDSERIEFACTURACION = SF.IDSERIEFACTURACION");
        sql.WHERE("FP.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("FP.FECHAPREVISTACONFIRM IS NOT NULL");
        sql.WHERE("FP.FECHAPREVISTACONFIRM <= SYSDATE");
        sql.WHERE("FP.FECHAREALGENERACION IS NOT NULL");
        sql.WHERE(MessageFormat.format("FP.IDESTADOCONFIRMACION = {0}", FacEstadoConfirmacionFact.CONFIRM_FINALIZADA.toString()));
        sql.WHERE(MessageFormat.format("(FP.IDESTADOPDF = {0} OR (FP.IDESTADOPDF = {1} AND SYSDATE - {2} > FP.FECHAMODIFICACION))",
                FacEstadoConfirmacionFact.PDF_PROGRAMADA.toString(), FacEstadoConfirmacionFact.PDF_PROCESANDO.toString(), tiempoMaximoEjecucionBloqueada));
        sql.ORDER_BY("FP.FECHAPREVISTAGENERACION");

        SQL a = new SQL();
        a.SELECT("ROWNUM POSICION");
        a.SELECT("A.*");
        a.FROM("( " + sql + " ) A");

        SQL b = new SQL();
        b.SELECT("B.POSICION");
        b.FROM("(" + a + " ) B");
        b.WHERE("B.IDINSTITUCION = " + idInstitucion);
        b.WHERE("B.IDSERIEFACTURACION = " + idSerieFacturacion);
        b.WHERE("B.IDPROGRAMACION = " + idProgramacion);

        return b.toString();
    }

    public String getNumTotalFacturacionesProGenerarEnviosFacturasPendientes(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada) {
        SQL sql = new SQL();
        sql.SELECT("COUNT(1) TOTAL");
        sql.FROM("FAC_FACTURACIONPROGRAMADA FP JOIN FAC_SERIEFACTURACION SF ON FP.IDINSTITUCION = SF.IDINSTITUCION AND FP.IDSERIEFACTURACION = SF.IDSERIEFACTURACION");
        sql.WHERE("FP.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("FP.FECHAPREVISTACONFIRM IS NOT NULL");
        sql.WHERE("FP.FECHAPREVISTACONFIRM <= SYSDATE");
        sql.WHERE("FP.FECHAREALGENERACION IS NOT NULL");
        sql.WHERE(MessageFormat.format("FP.IDESTADOCONFIRMACION = {0}", FacEstadoConfirmacionFact.CONFIRM_FINALIZADA.toString()));
        sql.WHERE(MessageFormat.format("FP.IDESTADOPDF = {0}", FacEstadoConfirmacionFact.PDF_FINALIZADA.toString()));
        sql.WHERE(MessageFormat.format("(FP.IDESTADOENVIO = {0} OR (FP.IDESTADOENVIO = {1} AND SYSDATE - {2} > FP.FECHAMODIFICACION))",
                FacEstadoConfirmacionFact.ENVIO_PROGRAMADA.toString(), FacEstadoConfirmacionFact.ENVIO_PROCESANDO.toString(), tiempoMaximoEjecucionBloqueada));
        sql.ORDER_BY("FP.FECHAPREVISTAGENERACION");
        return sql.toString();
    }

    public String getPosicionFacturacionProGenerarEnviosFacturasPendientes(Short idInstitucion, String idSerieFacturacion, String idProgramacion, Double tiempoMaximoEjecucionBloqueada) {
        SQL sql = new SQL();
        sql.SELECT("FP.*");
        sql.FROM("FAC_FACTURACIONPROGRAMADA FP JOIN FAC_SERIEFACTURACION SF ON FP.IDINSTITUCION = SF.IDINSTITUCION AND FP.IDSERIEFACTURACION = SF.IDSERIEFACTURACION");
        sql.WHERE("FP.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("FP.FECHAPREVISTACONFIRM IS NOT NULL");
        sql.WHERE("FP.FECHAPREVISTACONFIRM <= SYSDATE");
        sql.WHERE("FP.FECHAREALGENERACION IS NOT NULL");
        sql.WHERE(MessageFormat.format("FP.IDESTADOCONFIRMACION = {0}", FacEstadoConfirmacionFact.CONFIRM_FINALIZADA.toString()));
        sql.WHERE(MessageFormat.format("FP.IDESTADOPDF = {0}", FacEstadoConfirmacionFact.PDF_FINALIZADA.toString()));
        sql.WHERE(MessageFormat.format("(FP.IDESTADOENVIO = {0} OR (FP.IDESTADOENVIO = {1} AND SYSDATE - {2} > FP.FECHAMODIFICACION))",
                FacEstadoConfirmacionFact.ENVIO_PROGRAMADA.toString(), FacEstadoConfirmacionFact.ENVIO_PROCESANDO.toString(), tiempoMaximoEjecucionBloqueada));
        sql.ORDER_BY("FP.FECHAPREVISTAGENERACION");

        SQL a = new SQL();
        a.SELECT("ROWNUM POSICION");
        a.SELECT("A.*");
        a.FROM("( " + sql + " ) A");

        SQL b = new SQL();
        b.SELECT("B.POSICION");
        b.FROM("(" + a + " ) B");
        b.WHERE("B.IDINSTITUCION = " + idInstitucion);
        b.WHERE("B.IDSERIEFACTURACION = " + idSerieFacturacion);
        b.WHERE("B.IDPROGRAMACION = " + idProgramacion);

        return b.toString();
    }

    public String getNumTotalFacturacionesProComprobacionTraspasoFacturas(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada) {
        SQL subQuery = new SQL();
        subQuery.SELECT("1");
        subQuery.FROM("FAC_FACTURA F");
        subQuery.WHERE("F.IDINSTITUCION = FP.IDINSTITUCION");
        subQuery.WHERE("F.IDSERIEFACTURACION = FP.IDSERIEFACTURACION");
        subQuery.WHERE("F.IDPROGRAMACION = FP.IDPROGRAMACION");
        subQuery.WHERE("F.TRASPASADA IS NULL");

        SQL sql = new SQL();
        sql.SELECT("COUNT(1) TOTAL");
        sql.FROM("FAC_FACTURACIONPROGRAMADA FP JOIN FAC_SERIEFACTURACION SF ON FP.IDINSTITUCION = SF.IDINSTITUCION AND FP.IDSERIEFACTURACION = SF.IDSERIEFACTURACION");
        sql.WHERE("FP.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("FP.FECHACONFIRMACION IS NOT NULL");
        sql.WHERE(MessageFormat.format("FP.IDESTADOCONFIRMACION = {0}", FacEstadoConfirmacionFact.CONFIRM_FINALIZADA.toString()));
        sql.WHERE(MessageFormat.format("FP.IDESTADOTRASPASO = {0}", FacEstadoConfirmacionFact.TRASPASO_PROCESANDO.toString()));
        sql.WHERE(MessageFormat.format("(NOT EXISTS ({0}) OR SYSDATE - {1} > FP.FECHAMODIFICACION)", subQuery.toString(), tiempoMaximoEjecucionBloqueada));
        sql.ORDER_BY("FP.FECHAPREVISTAGENERACION");
        return sql.toString();
    }

    public String getPosicionFacturacionProComprobacionTraspasoFacturas(Short idInstitucion, String idSerieFacturacion, String idProgramacion, Double tiempoMaximoEjecucionBloqueada) {
        SQL subQuery = new SQL();
        subQuery.SELECT("1");
        subQuery.FROM("FAC_FACTURA F");
        subQuery.WHERE("F.IDINSTITUCION = FP.IDINSTITUCION");
        subQuery.WHERE("F.IDSERIEFACTURACION = FP.IDSERIEFACTURACION");
        subQuery.WHERE("F.IDPROGRAMACION = FP.IDPROGRAMACION");
        subQuery.WHERE("F.TRASPASADA IS NULL");

        SQL sql = new SQL();
        sql.SELECT("FP.*");
        sql.FROM("FAC_FACTURACIONPROGRAMADA FP JOIN FAC_SERIEFACTURACION SF ON FP.IDINSTITUCION = SF.IDINSTITUCION AND FP.IDSERIEFACTURACION = SF.IDSERIEFACTURACION");
        sql.WHERE("FP.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("FP.FECHACONFIRMACION IS NOT NULL");
        sql.WHERE(MessageFormat.format("FP.IDESTADOCONFIRMACION = {0}", FacEstadoConfirmacionFact.CONFIRM_FINALIZADA.toString()));
        sql.WHERE(MessageFormat.format("FP.IDESTADOTRASPASO = {0}", FacEstadoConfirmacionFact.TRASPASO_PROCESANDO.toString()));
        sql.WHERE(MessageFormat.format("(NOT EXISTS ({0}) OR SYSDATE - {1} > FP.FECHAMODIFICACION)", subQuery.toString(), tiempoMaximoEjecucionBloqueada));
        sql.ORDER_BY("FP.FECHAPREVISTAGENERACION");

        SQL a = new SQL();
        a.SELECT("ROWNUM POSICION");
        a.SELECT("A.*");
        a.FROM("( " + sql + " ) A");

        SQL b = new SQL();
        b.SELECT("B.POSICION");
        b.FROM("(" + a + " ) B");
        b.WHERE("B.IDINSTITUCION = " + idInstitucion);
        b.WHERE("B.IDSERIEFACTURACION = " + idSerieFacturacion);
        b.WHERE("B.IDPROGRAMACION = " + idProgramacion);

        return b.toString();
    }

}
