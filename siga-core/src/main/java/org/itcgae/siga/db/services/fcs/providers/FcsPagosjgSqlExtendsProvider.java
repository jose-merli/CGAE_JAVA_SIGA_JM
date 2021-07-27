package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.PagosjgItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.FcsPagosjgSqlProvider;

import java.text.SimpleDateFormat;

public class FcsPagosjgSqlExtendsProvider extends FcsPagosjgSqlProvider {

    public String comboPagosColegio(String idLenguaje, Short idInstitucion) {
        SQL sql = new SQL();

        sql.SELECT("p.idpagosjg AS id");
        sql.SELECT(
                "to_char(e.fechaestado, 'dd/mm/yy') || ' - ' || p.nombre || ' (' || to_char(p.fechadesde, 'dd/mm/yy') || '-' || to_char(p.fechahasta, 'dd/mm/yy') || ')' AS descripcion");
        sql.FROM("fcs_pagosjg p, fcs_pagos_estadospagos e");
        sql.WHERE("p.idinstitucion = '" + idInstitucion + "'");

        SQL sql1 = new SQL();
        sql1.SELECT("MAX(es.fechaestado)");
        sql1.FROM("fcs_pagos_estadospagos es");
        sql1.WHERE("es.idpagosjg = p.idpagosjg");
        sql1.WHERE("es.idinstitucion = p.idinstitucion");

        sql.WHERE("e.fechaestado = (" + sql1 + ")");

        SQL sql2 = new SQL();
        sql2.SELECT("estado.idestadopagosjg");
        sql2.FROM("fcs_pagos_estadospagos estado");
        sql2.WHERE("estado.fechaestado = e.fechaestado");
        sql2.WHERE("estado.idinstitucion = e.idinstitucion");
        sql2.WHERE("estado.idpagosjg = e.idpagosjg");

        sql.WHERE("(" + sql2 + ") >= 30");

        sql.WHERE("p.idinstitucion = e.idinstitucion");
        sql.WHERE("p.idpagosjg = e.idpagosjg");

        sql.ORDER_BY("e.fechaestado desc");
        return sql.toString();
    }

    public String buscarPagos(PagosjgItem pagosItem, String idInstitucion, String idLenguaje, Integer tamMax) {
        SQL sql = new SQL();

        sql.SELECT("IDINSTITUCION");
        sql.SELECT("ABREVIATURA");
        sql.SELECT("IDFACTURACION");
        sql.SELECT("IDPAGOSJG");
        sql.SELECT("FECHADESDE");
        sql.SELECT("FECHAHASTA");
        sql.SELECT("NOMBRE");
        sql.SELECT("DESESTADO");
        sql.SELECT("IDESTADO");
        sql.SELECT("FECHAESTADO");
        sql.SELECT("CANTIDAD");
        sql.SELECT("PORCENTAJE");

        SQL sql2 = new SQL();

        sql2.SELECT("PAGO.IDINSTITUCION");
        sql2.SELECT("PAGO.ABREVIATURA");
        sql2.SELECT("PAGO.IDFACTURACION");
        sql2.SELECT("PAGO.IDPAGOSJG");
        sql2.SELECT("PAGO.FECHADESDE");
        sql2.SELECT("PAGO.FECHAHASTA");
        sql2.SELECT("PAGO.NOMBRE");

        SQL sql3 = new SQL();

        sql3.SELECT("REC.DESCRIPCION DESCRIPCION");
        sql3.FROM("FCS_ESTADOSPAGOS ESTADOS");
        sql3.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON ESTADOS.DESCRIPCION = REC.IDRECURSO");
        sql3.WHERE("EST.IDESTADOPAGOSJG = ESTADOS.IDESTADOPAGOSJG");
        sql3.WHERE("REC.IDLENGUAJE = " + idLenguaje);

        sql2.SELECT("(" + sql3.toString() + ") AS DESESTADO");
        sql2.SELECT("EST.IDESTADOPAGOSJG AS IDESTADO");
        sql2.SELECT("EST.FECHAESTADO AS FECHAESTADO");
        sql2.SELECT("NVL(PAGO.IMPORTEPAGADO, 0) CANTIDAD");
        sql2.SELECT(
                "(CASE PAGO.IMPORTEREPARTIR WHEN 0 THEN 0 ELSE (PAGO.IMPORTEPAGADO * 100) / PAGO.IMPORTEREPARTIR END) AS PORCENTAJE");
        sql2.FROM("FCS_PAGOSJG PAGO");
        sql2.INNER_JOIN("CEN_INSTITUCION INS ON PAGO.IDINSTITUCION = INS.IDINSTITUCION");
        sql2.INNER_JOIN(
                "FCS_PAGOS_ESTADOSPAGOS EST ON PAGO.IDINSTITUCION = EST.IDINSTITUCION AND PAGO.IDPAGOSJG = EST.IDPAGOSJG");
        sql2.INNER_JOIN(
                "FCS_FACTURACIONJG FAC ON PAGO.IDFACTURACION = FAC.IDFACTURACION AND PAGO.IDINSTITUCION = FAC.IDINSTITUCION");
        sql2.WHERE("PAGO.IDINSTITUCION = '" + idInstitucion + "'");

        SQL sql4 = new SQL();
        sql4.SELECT("MAX(EST2.FECHAESTADO)");
        sql4.FROM("FCS_PAGOS_ESTADOSPAGOS EST2");
        sql4.WHERE("EST2.IDINSTITUCION = EST.IDINSTITUCION");
        sql4.WHERE("EST2.IDPAGOSJG = EST.IDPAGOSJG");

        sql2.WHERE("EST.FECHAESTADO = (" + sql4.toString() + ")");

        // FILTRO ESTADOS FACTURACIÓN
        if (!UtilidadesString.esCadenaVacia(pagosItem.getIdEstado())) {
            sql2.WHERE("EST.IDESTADOPAGOSJG IN ( " + pagosItem.getIdEstado() + " )");
        }

        // FILTRO NOMBRE
        if (!UtilidadesString.esCadenaVacia(pagosItem.getNombre())) {
            sql2.WHERE(UtilidadesString.filtroTextoBusquedas("PAGO.NOMBRE", pagosItem.getNombre().trim()));
        }

        // FILTRO PARTIDA PRESUPUESTARIA
        if (!UtilidadesString.esCadenaVacia(pagosItem.getIdPartidaPresupuestaria())) {
            sql2.WHERE("FAC.IDPARTIDAPRESUPUESTARIA IN ( " + pagosItem.getIdPartidaPresupuestaria() + " )");
        }

        // FILTRO POR CONCEPTOS DE FACTURACIÓN Y POR GRUPOS DE FACTURACIÓN
        if (!UtilidadesString.esCadenaVacia(pagosItem.getIdConcepto())
                || !UtilidadesString.esCadenaVacia(pagosItem.getIdFacturacion())) {
            SQL sql5 = new SQL();

            sql5.SELECT("1");
            sql5.FROM("FCS_FACT_GRUPOFACT_HITO HIT");
            sql5.WHERE("HIT.IDFACTURACION = PAGO.IDFACTURACION");
            sql5.WHERE("HIT.IDINSTITUCION = PAGO.IDINSTITUCION");

            // FILTRO POR CONCEPTOS DE FACTURACION
            if (!UtilidadesString.esCadenaVacia(pagosItem.getIdConcepto())) {
                sql5.WHERE("HIT.IDHITOGENERAL IN ( " + pagosItem.getIdConcepto() + " )");
            }
            // FILTRO POR GRUPO FACTURACION
            if (!UtilidadesString.esCadenaVacia(pagosItem.getIdFacturacion())) {
                sql5.WHERE("HIT.IDGRUPOFACTURACION IN ( " + pagosItem.getIdFacturacion() + " )");
            }

            sql2.WHERE("EXISTS (" + sql5.toString() + ")");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        // FILTRO FECHADESDE
        if (null != pagosItem.getFechaDesde()) {
            String fechaF = dateFormat.format(pagosItem.getFechaDesde());
            sql2.WHERE("PAGO.FECHADESDE >=TO_DATE('" + fechaF + "', 'DD/MM/YYYY hh24:mi:ss')");
        }

        // FILTRO FECHAHASTA
        if (null != pagosItem.getFechaHasta()) {
            String fechaF = dateFormat.format(pagosItem.getFechaHasta());
            sql2.WHERE("PAGO.FECHAHASTA <=TO_DATE('" + fechaF + "', 'DD/MM/YYYY hh24:mi:ss')");
        }

        sql.FROM("(" + sql2.toString() + ") busqueda");
        sql.ORDER_BY("busqueda.FECHADESDE");
        sql.ORDER_BY("busqueda.FECHAHASTA");
        sql.ORDER_BY("busqueda.FECHAESTADO DESC");

        SQL query = new SQL();
        query.SELECT("*");
        query.FROM("( " + sql.toString() + " )");

        if (tamMax != null) {
            Integer tamMaxNumber = tamMax + 1;
            query.WHERE("ROWNUM <= " + tamMaxNumber);
        }

        return query.toString();
    }

    public String datosGeneralesPagos(String idPago, String idInstitucion) {

        SQL sql = new SQL();
        sql.SELECT("PAG.IDINSTITUCION");
        sql.SELECT("PAG.IDFACTURACION");
        sql.SELECT("PAG.NOMBRE");
        sql.SELECT(
                "TO_CHAR(FAC.FECHADESDE, 'DD/MM/YYYY') || '-' || TO_CHAR(FAC.FECHAHASTA, 'DD/MM/YYYY') || ' - ' || FAC.NOMBRE AS NOMBREFAC");
        sql.SELECT("PAG.IDPAGOSJG");
        sql.SELECT("PAG.ABREVIATURA");
        sql.FROM("FCS_PAGOSJG PAG");
        sql.JOIN(
                "FCS_FACTURACIONJG FAC ON FAC.IDINSTITUCION = PAG.IDINSTITUCION AND FAC.IDFACTURACION = PAG.IDFACTURACION");
        sql.WHERE("PAG.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("PAG.IDPAGOSJG = " + idPago);

        return sql.toString();
    }

    public String historicoPagos(String idPago, String lenguaje, Short idInstitucion) {
        SQL sql = new SQL();

        sql.SELECT("EST.IDESTADOPAGOSJG");
        sql.SELECT("REC.DESCRIPCION DESCRIPCION");
        sql.SELECT("EST.FECHAESTADO");
        sql.SELECT(
                "(CASE WHEN PER.NOMBRE IS NOT NULL THEN PER.APELLIDOS2 || ' ' || PER.APELLIDOS1 || ', ' || PER.NOMBRE ELSE NULL END) AS USUARIO");

        sql.FROM("FCS_PAGOS_ESTADOSPAGOS EST "
                + "JOIN FCS_PAGOSJG PAG ON PAG.IDPAGOSJG = EST.IDPAGOSJG AND PAG.IDINSTITUCION = EST.IDINSTITUCION "
                + "LEFT JOIN ADM_USUARIOS AU ON AU.IDINSTITUCION = PAG.IDINSTITUCION AND AU.IDUSUARIO = PAG.USUMODIFICACION "
                + "LEFT JOIN CEN_PERSONA PER ON AU.NIF = PER.NIFCIF "
                + "JOIN FCS_ESTADOSPAGOS ESTADOS ON EST.IDESTADOPAGOSJG = ESTADOS.IDESTADOPAGOSJG "
                + "JOIN GEN_RECURSOS_CATALOGOS REC ON ESTADOS.DESCRIPCION = REC.IDRECURSO");

        sql.WHERE("EST.IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("EST.IDPAGOSJG = '" + idPago + "'");
        sql.WHERE("REC.IDLENGUAJE = '" + lenguaje + "'");
        sql.ORDER_BY("FECHAESTADO DESC");

        return sql.toString();
    }

    public String getNewIdPago(Short idInstitucion) {

        SQL sql = new SQL();

        sql.SELECT("(NVL(MAX(IDPAGOSJG), 0) + 1) AS IDPAGOSJG");
        sql.FROM("FCS_PAGOSJG");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

        return sql.toString();
    }

    public String getConceptosPago(String idPago, Short idInstitucion, String lenguaje) {

        SQL sql = new SQL();

        sql.SELECT("FPGH.IDINSTITUCION");
        sql.SELECT("FPGH.IDPAGOSJG");
        sql.SELECT("FP.IDFACTURACION");
        sql.SELECT("FPGH.IDHITOGENERAL");
        sql.SELECT("FPGH.IDGRUPOFACTURACION");
        sql.SELECT("F_SIGA_GETRECURSO(FH.DESCRIPCION, " + lenguaje + ") AS DESCRIPCION");
        sql.SELECT("(CASE "
                + "WHEN FPGH.IDHITOGENERAL = 10 THEN NVL(FF.IMPORTEOFICIO, 0) "
                + "WHEN FPGH.IDHITOGENERAL = 20 THEN NVL(FF.IMPORTEGUARDIA, 0) "
                + "WHEN FPGH.IDHITOGENERAL = 30 THEN NVL(FF.IMPORTESOJ, 0) "
                + "WHEN FPGH.IDHITOGENERAL = 40 THEN NVL(FF.IMPORTEEJG, 0) "
                + "END) IMPORTEFACTURADO");
        sql.SELECT("(CASE "
                + "WHEN FPGH.IDHITOGENERAL = 10 THEN NVL(FF.IMPORTEOFICIO, 0) - NVL(FP.IMPORTEOFICIO, 0) "
                + "WHEN FPGH.IDHITOGENERAL = 20 THEN NVL(FF.IMPORTEGUARDIA, 0) - NVL(FP.IMPORTEGUARDIA, 0) "
                + "WHEN FPGH.IDHITOGENERAL = 30 THEN NVL(FF.IMPORTESOJ, 0) - NVL(FP.IMPORTESOJ, 0) "
                + "WHEN FPGH.IDHITOGENERAL = 40 THEN NVL(FF.IMPORTEEJG, 0) - NVL(FP.IMPORTEEJG, 0) "
                + "END) IMPORTEPENDIENTE");
        sql.SELECT("(CASE " +
                "WHEN FPGH.IDHITOGENERAL = 10 THEN NVL(DECODE(FF.IMPORTEOFICIO, 0, 0, ROUND(((FF.IMPORTEOFICIO - FP.IMPORTEOFICIO) / FF.IMPORTEOFICIO) * 100, 2)), 0) " +
                "WHEN FPGH.IDHITOGENERAL = 20 THEN NVL(DECODE(FF.IMPORTEGUARDIA, 0, 0, ROUND(((FF.IMPORTEGUARDIA - FP.IMPORTEGUARDIA) / FF.IMPORTEGUARDIA) * 100, 2)), 0) " +
                "WHEN FPGH.IDHITOGENERAL = 30 THEN NVL(DECODE(FF.IMPORTEEJG, 0, 0, ROUND(((FF.IMPORTEEJG - FP.IMPORTEEJG) / FF.IMPORTEEJG) * 100 , 2)), 0) " +
                "WHEN FPGH.IDHITOGENERAL = 40 THEN NVL(DECODE(FF.IMPORTESOJ, 0, 0, ROUND(((FF.IMPORTESOJ - FP.IMPORTESOJ) / FF.IMPORTESOJ) * 100 , 2)), 0) " +
                "END) PORCENTAJEPENDIENTE");
        sql.SELECT("(CASE " +
                "WHEN FPGH.IDHITOGENERAL = 10 THEN NVL(FP.IMPORTEOFICIO, 0) " +
                "WHEN FPGH.IDHITOGENERAL = 20 THEN NVL(FP.IMPORTEGUARDIA, 0) " +
                "WHEN FPGH.IDHITOGENERAL = 30 THEN NVL(FP.IMPORTESOJ, 0) " +
                "WHEN FPGH.IDHITOGENERAL = 40 THEN NVL(FP.IMPORTEEJG, 0) " +
                "END) IMPORTEPAGADO");
        sql.SELECT("(CASE " +
                "WHEN FPGH.IDHITOGENERAL = 10 THEN NVL(DECODE(FF.IMPORTEOFICIO, 0, 0, ROUND((FP.IMPORTEOFICIO / FF.IMPORTEOFICIO) * 100, 2)), 0) " +
                "WHEN FPGH.IDHITOGENERAL = 20 THEN NVL(DECODE(FF.IMPORTEGUARDIA, 0, 0, ROUND((FP.IMPORTEGUARDIA / FF.IMPORTEGUARDIA) * 100 , 2)), 0) " +
                "WHEN FPGH.IDHITOGENERAL = 30 THEN NVL(DECODE(FF.IMPORTEEJG, 0, 0, ROUND((FP.IMPORTEEJG / FF.IMPORTEEJG) * 100, 2)), 0) " +
                "WHEN FPGH.IDHITOGENERAL = 40 THEN NVL(DECODE(FF.IMPORTESOJ, 0, 0, ROUND((FP.IMPORTESOJ / FF.IMPORTESOJ) * 100, 2)), 0) " +
                "END) AS PORCENTAJEPAGADO");

        sql.FROM("FCS_PAGO_GRUPOFACT_HITO FPGH");

        sql.JOIN("FCS_HITOGENERAL FH ON FH.IDHITOGENERAL = FPGH.IDHITOGENERAL");
        sql.JOIN("FCS_PAGOSJG FP ON FP.IDPAGOSJG = FPGH.IDPAGOSJG AND FP.IDINSTITUCION = FPGH.IDINSTITUCION");
        sql.JOIN("FCS_FACTURACIONJG FF ON FF.IDFACTURACION = FP.IDFACTURACION AND FF.IDINSTITUCION = FP.IDINSTITUCION");

        sql.WHERE("FPGH.IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("FPGH.IDPAGOSJG = '" + idPago + "'");

        return sql.toString();
    }

    public String comboConceptosPago(Short idInstitucion, String idFacturacion, String idPago, String idLenguaje) {

        SQL sql = new SQL();

        sql.SELECT("FFGH.IDINSTITUCION");
        sql.SELECT("FFGH.IDFACTURACION");
        sql.SELECT("FP.IDPAGOSJG");
        sql.SELECT("FFGH.IDHITOGENERAL");
        sql.SELECT("F_SIGA_GETRECURSO(FH.DESCRIPCION, " + idLenguaje + ") AS DESCRIPCION");
        sql.SELECT("FFGH.IDGRUPOFACTURACION");
        sql.SELECT("(CASE " +
                "WHEN FFGH.IDHITOGENERAL = 10 THEN NVL(FF.IMPORTEOFICIO, 0) " +
                "WHEN FFGH.IDHITOGENERAL = 20 THEN NVL(FF.IMPORTEGUARDIA, 0) " +
                "WHEN FFGH.IDHITOGENERAL = 30 THEN NVL(FF.IMPORTESOJ, 0) " +
                "WHEN FFGH.IDHITOGENERAL = 40 THEN NVL(FF.IMPORTEEJG, 0) " +
                "END) IMPORTEFACTURADO");
        sql.SELECT("(CASE " +
                "WHEN FFGH.IDHITOGENERAL = 10 THEN NVL(FF.IMPORTEOFICIO, 0) - NVL(FP.IMPORTEOFICIO, 0) " +
                "WHEN FFGH.IDHITOGENERAL = 20 THEN NVL(FF.IMPORTEGUARDIA, 0) - NVL(FP.IMPORTEGUARDIA, 0) " +
                "WHEN FFGH.IDHITOGENERAL = 30 THEN NVL(FF.IMPORTESOJ, 0) - NVL(FP.IMPORTESOJ, 0) " +
                "WHEN FFGH.IDHITOGENERAL = 40 THEN NVL(FF.IMPORTEEJG, 0) - NVL(FP.IMPORTEEJG, 0) " +
                "END) IMPORTEPENDIENTE");
        sql.SELECT("(CASE " +
                "WHEN FFGH.IDHITOGENERAL = 10 THEN NVL(DECODE(FF.IMPORTEOFICIO, 0, 0, ROUND(((FF.IMPORTEOFICIO - FP.IMPORTEOFICIO) / FF.IMPORTEOFICIO) * 100, 2)), 0) " +
                "WHEN FFGH.IDHITOGENERAL = 20 THEN NVL(DECODE(FF.IMPORTEGUARDIA, 0, 0, ROUND(((FF.IMPORTEGUARDIA - FP.IMPORTEGUARDIA) / FF.IMPORTEGUARDIA) * 100, 2)), 0) " +
                "WHEN FFGH.IDHITOGENERAL = 30 THEN NVL(DECODE(FF.IMPORTEEJG, 0, 0, ROUND(((FF.IMPORTEEJG - FP.IMPORTEEJG) / FF.IMPORTEEJG) * 100 , 2)), 0) " +
                "WHEN FFGH.IDHITOGENERAL = 40 THEN NVL(DECODE(FF.IMPORTESOJ, 0, 0, ROUND(((FF.IMPORTESOJ - FP.IMPORTESOJ) / FF.IMPORTESOJ) * 100 , 2)), 0) " +
                "END) PORCENTAJEPENDIENTE");

        sql.FROM("FCS_FACT_GRUPOFACT_HITO FFGH");

        sql.JOIN("FCS_HITOGENERAL FH ON FH.IDHITOGENERAL = FFGH.IDHITOGENERAL");
        sql.JOIN("FCS_FACTURACIONJG FF ON FF.IDINSTITUCION = FFGH.IDINSTITUCION AND FF.IDFACTURACION = FFGH.IDFACTURACION");
        sql.JOIN("FCS_PAGOSJG FP ON FP.IDFACTURACION = FF.IDFACTURACION AND FP.IDINSTITUCION = FF.IDINSTITUCION");

        sql.WHERE("FFGH.IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("FFGH.IDFACTURACION = '" + idFacturacion + "'");
        sql.WHERE("FP.IDPAGOSJG = '" + idPago + "'");

        return sql.toString();
    }

    public String hayMovimientosVariosPositivosAaplicar(Short idInstitucion, String idFacturacion) {

        SQL sql = new SQL();

        sql.SELECT("COUNT(*)");

        sql.FROM("FCS_APLICA_MOVIMIENTOSVARIOS FAM " +
                "RIGHT JOIN FCS_MOVIMIENTOSVARIOS FM ON FM.IDINSTITUCION = FAM.IDINSTITUCION AND FM.IDMOVIMIENTO = FAM.IDMOVIMIENTO");

        sql.WHERE("FM.IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("FM.IDFACTURACION = '" + idFacturacion + "'");
        sql.WHERE("FM.CANTIDAD > 0");
        sql.WHERE("FAM.IDAPLICACION IS NULL");
        sql.WHERE("FAM.IDINSTITUCION IS NULL");

        return sql.toString();
    }

    public String getConfigFichAbonos(String idPago, Short idInstitucion) {

        SQL sql = new SQL();

        sql.SELECT("IDINSTITUCION");
        sql.SELECT("IDPAGOSJG");
        sql.SELECT("IDFACTURACION");
        sql.SELECT("BANCOS_CODIGO");
        sql.SELECT("IDSUFIJO");
        sql.SELECT("IDPROPSEPA");
        sql.SELECT("IDPROPOTROS");

        sql.FROM("FCS_PAGOSJG");

        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("IDPAGOSJG = '" + idPago + "'");

        return sql.toString();
    }
}
