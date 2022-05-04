package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.PagosjgItem;
import org.itcgae.siga.commons.constants.SigaConstants;
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

    public String getNumApuntesPago(String idPago, Short idInstitucion, String idLenguaje) {

        // NOMBREBANCO
        SQL sql = new SQL();
        sql.SELECT("B.NOMBRE");
        sql.FROM("CEN_CUENTASBANCARIAS CB");
        sql.INNER_JOIN("CEN_BANCOS B ON CB.CBO_CODIGO = B.CODIGO");
        sql.WHERE("PC.IDPERDESTINO = CB.IDPERSONA");
        sql.WHERE("PC.IDINSTITUCION = CB.IDINSTITUCION");
        sql.WHERE("PC.IDCUENTA = CB.IDCUENTA");

        // NUMEROCUENTA
        SQL sql1 = new SQL();
        sql1.SELECT("F_SIGA_FORMATOIBAN(CB.IBAN) AS CUENTA");
        sql1.FROM("CEN_CUENTASBANCARIAS CB");
        sql1.WHERE("PC.IDPERDESTINO = CB.IDPERSONA");
        sql1.WHERE("PC.IDINSTITUCION = CB.IDINSTITUCION");
        sql1.WHERE("PC.IDCUENTA = CB.IDCUENTA");

        SQL sql2 = new SQL();
        sql2.SELECT("PC.IDINSTITUCION");
        sql2.SELECT("PC.IDPAGOSJG AS IDPAGOS");
        sql2.SELECT("SUM(PC.IMPOFICIO + PC.IMPASISTENCIA + PC.IMPEJG + PC.IMPSOJ) AS TOTALIMPORTESJCS");
        sql2.SELECT("SUM(PC.IMPRET) AS IMPORTETOTALRETENCIONES");
        sql2.SELECT("SUM(PC.IMPMOVVAR) AS IMPORTETOTALMOVIMIENTOS");
        sql2.SELECT("SUM(PC.IMPIRPF) AS TOTALIMPORTEIRPF");
        sql2.SELECT("SUM(PC.IMPOFICIO + PC.IMPASISTENCIA + PC.IMPEJG + PC.IMPSOJ) + SUM(PC.IMPRET) + SUM(PC.IMPMOVVAR) + SUM(PC.IMPIRPF) AS TOTALFINAL");
        sql2.SELECT("SUM(PC.IMPIRPF) AS TOTALIMPORTEIVA");
        sql2.SELECT("PC.IDCUENTA");
        sql2.SELECT("PC.IDPERDESTINO");
        sql2.SELECT("PC.PORCENTAJEIRPF AS TIPOIRPF");
        sql2.SELECT("SUM(PC.IMPOFICIO) AS IMPORTETOTALOFICIO");
        sql2.SELECT("SUM(PC.IMPASISTENCIA) AS IMPORTETOTALASISTENCIA");
        sql2.SELECT("SUM(PC.IMPEJG) AS IMPORTETOTALEJG");
        sql2.SELECT("SUM(PC.IMPSOJ) AS IMPORTETOTALSOJ");
        sql2.SELECT("DECODE(PC.IDPERDESTINO, PC.IDPERORIGEN, 'COLEGIADO', 'SOCIEDAD') AS DESTINATARIO");
        sql2.SELECT("F_SIGA_GETRECURSO_ETIQUETA(DECODE(PC.IDCUENTA, NULL, 'GRATUITA.PAGOS.PORCAJA', 'GRATUITA.PAGOS.PORBANCO'), " + idLenguaje + ") AS FORMADEPAGO");
        sql2.SELECT("( " + sql.toString() + " ) AS NOMBREBANCO");
        sql2.SELECT("( " + sql1.toString() + " ) AS NUMEROCUENTA");
        sql2.SELECT("PC.IDPERORIGEN AS IDPERSONASJCS");
        sql2.FROM("FCS_PAGO_COLEGIADO PC");
        sql2.WHERE("PC.IDINSTITUCION = '" + idInstitucion + "'");
        sql2.WHERE("PC.IDPAGOSJG = NVL(" + idPago + ", PC.IDPAGOSJG)");
        sql2.GROUP_BY("PC.IDPERORIGEN");
        sql2.GROUP_BY("PC.IDPERDESTINO");
        sql2.GROUP_BY("PC.IDPAGOSJG");
        sql2.GROUP_BY("PC.IDINSTITUCION");
        sql2.GROUP_BY("PC.PORCENTAJEIRPF");
        sql2.GROUP_BY("PC.IDCUENTA");

        SQL sql3 = new SQL();
        sql3.SELECT("PAGO.*");
        sql3.SELECT("CEN.APELLIDOS1 || ' ' || CEN.APELLIDOS2 || ', ' || CEN.NOMBRE AS NOMBREPERSONA");
        sql3.SELECT("DECODE(COL.COMUNITARIO, 1, COL.NCOMUNITARIO, COL.NCOLEGIADO) AS NCOLEGIADO");
        sql3.FROM("( " + sql2.toString() + " ) PAGO");
        sql3.JOIN("CEN_PERSONA CEN ON PAGO.IDPERSONASJCS = CEN.IDPERSONA");
        sql3.JOIN("CEN_COLEGIADO COL ON COL.IDPERSONA = CEN.IDPERSONA");
        sql3.WHERE(" COL.IDINSTITUCION = '" + idInstitucion + "'");


        SQL query = new SQL();
        query.SELECT("COUNT(1) AS NUMAPUNTES");
        query.FROM("( " + sql3.toString() + " )");

        return query.toString();
    }

    public String getEstadoPago(String idPago, Short idInstitucion) {

        SQL subQuery = new SQL();
        subQuery.SELECT("MAX(FECHAESTADO)");
        subQuery.FROM("FCS_PAGOS_ESTADOSPAGOS");
        subQuery.WHERE("IDPAGOSJG = '" + idPago + "'");
        subQuery.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

        SQL query = new SQL();
        query.SELECT("IDESTADOPAGOSJG");
        query.FROM("FCS_PAGOS_ESTADOSPAGOS");
        query.WHERE("IDPAGOSJG = '" + idPago + "'");
        query.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
        query.WHERE("FECHAESTADO = ( " + subQuery.toString() + " )");

        return query.toString();
    }

    public String getPagosSJCSBloqueadosEnEjecucion(Short idInstitucion, Long tiempoMaximoMinutos) {

        SQL subQuery = new SQL();
        subQuery.SELECT("MAX(FECHAESTADO)");
        subQuery.FROM("FCS_PAGOS_ESTADOSPAGOS");
        subQuery.WHERE("IDPAGOSJG = fp.IDPAGOSJG");
        subQuery.WHERE("IDINSTITUCION = fp.IDINSTITUCION");

        SQL query = new SQL();
        query.SELECT("fp.*");
        query.FROM("FCS_PAGOSJG fp");
        query.INNER_JOIN("FCS_PAGOS_ESTADOSPAGOS fpe ON ( fpe.IDINSTITUCION = fp.IDINSTITUCION AND fpe.IDPAGOSJG = fp.IDPAGOSJG AND fpe.FECHAESTADO = (" + subQuery + ") )");
        query.WHERE("fp.IDINSTITUCION = " + idInstitucion);
        query.WHERE("fpe.IDESTADOPAGOSJG = " + SigaConstants.ESTADO_PAGO_EJECUTANDO);
        query.WHERE("fpe.FECHAESTADO < SYSDATE - " + tiempoMaximoMinutos + "/1440");

        return query.toString();
    }

    public String getCompensacionFacturas(String idPago, Short idInstitucion) {

        SQL sql = new SQL();
        sql.SELECT("1");
        sql.FROM("FAC_FACTURA F");
        sql.WHERE("F.IDPERSONA = PC.IDPERORIGEN");
        sql.WHERE("F.IDINSTITUCION = PC.IDINSTITUCION");
        sql.WHERE("F.NUMEROFACTURA > '0'");
        sql.WHERE("F.IMPTOTALPORPAGAR > 0");
        sql.WHERE("F.ESTADO IN (2, 4, 5)");

        SQL sql1 = new SQL();
        sql1.SELECT("IDPERORIGEN");
        sql1.FROM("FCS_PAGO_COLEGIADO PC");
        sql1.WHERE("PC.IDINSTITUCION = '" + idInstitucion + "'");
        sql1.WHERE("PC.IDPAGOSJG = '" + idPago + "'");
        sql1.WHERE("EXISTS ( " + sql.toString() + " )");

        SQL query = new SQL();
        query.SELECT("FACTURA.IDPERSONA");
        query.SELECT("DECODE(COLEGIADO.COMUNITARIO, 0, COLEGIADO.NCOLEGIADO, COLEGIADO.NCOMUNITARIO) AS NCOLEGIADO");
        query.SELECT("(PERSONA.APELLIDOS2 || ' ' || PERSONA.APELLIDOS1 || ', ' || PERSONA.NOMBRE) AS NOMBRE");
        query.SELECT("FACTURA.IDFACTURA");
        query.SELECT("FACTURA.NUMEROFACTURA");
        query.SELECT("FACTURA.FECHAEMISION AS FECHAFACTURA");
        query.SELECT("FACTURA.IMPTOTAL AS IMPORTETOTALFACTURA");
        query.SELECT("FACTURA.IMPTOTALPORPAGAR AS IMPORTEPENDIENTEFACTURA");
        query.SELECT("FACTURA.IMPTOTALCOMPENSADO AS IMPORTECOMPENSADO");
        query.SELECT("FACTURA.IMPTOTALPAGADO AS IMPORTEPAGO");
        query.FROM("FAC_FACTURA FACTURA");
        query.JOIN("CEN_COLEGIADO COLEGIADO ON COLEGIADO.IDPERSONA = FACTURA.IDPERSONA AND COLEGIADO.IDINSTITUCION = FACTURA.IDINSTITUCION");
        query.JOIN("CEN_PERSONA PERSONA ON PERSONA.IDPERSONA = COLEGIADO.IDPERSONA");
        query.WHERE("FACTURA.IDINSTITUCION = '" + idInstitucion + "'");
        query.WHERE("FACTURA.IDPERSONA IN ( " + sql1.toString() + " )");
        query.WHERE("FACTURA.ESTADO NOT IN (1, 7, 8)");
        query.WHERE("FACTURA.IMPTOTALPORPAGAR > 0");
        query.ORDER_BY("FACTURA.FECHAEMISION ASC");

        return query.toString();
    }

    public String getColegiadosApagar(Short idInstitucion, String idPago, int caseMorosos) {

        SQL sql1 = new SQL();
        sql1.SELECT("1");
        sql1.FROM("FAC_FACTURA F");
        sql1.WHERE("F.IDPERSONA = PC.IDPERORIGEN");
        sql1.WHERE("F.IDINSTITUCION = PC.IDINSTITUCION");
        sql1.WHERE("F.NUMEROFACTURA > '0'");
        sql1.WHERE("F.IMPTOTALPORPAGAR > 0");
        sql1.WHERE("F.ESTADO IN (2,4,5)");

        SQL sql = new SQL();
        sql.SELECT("PC.IDINSTITUCION");
        sql.SELECT("PC.IDPAGOSJG");
        sql.SELECT("PC.IDPERORIGEN AS IDPERSONA_SJCS");
        sql.SELECT("PC.IDPERDESTINO");
        sql.SELECT("PC.IMPOFICIO");
        sql.SELECT("PC.IMPASISTENCIA");
        sql.SELECT("PC.IMPSOJ");
        sql.SELECT("PC.IMPEJG");
        sql.SELECT("PC.IMPMOVVAR");
        sql.SELECT("PC.IMPIRPF");
        sql.SELECT("PC.IMPRET");
        sql.SELECT("PC.IDCUENTA");
        sql.SELECT("PC.FECHAMODIFICACION");
        sql.SELECT("PC.USUMODIFICACION");
        sql.SELECT("PC.PORCENTAJEIRPF");
        sql.SELECT("PC.IDRETENCION");
        sql.FROM("FCS_PAGO_COLEGIADO PC");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("IDPAGOSJG = '" + idPago + "'");

        switch (caseMorosos) {
            case 0:
                sql.WHERE("EXISTS ( " + sql1.toString() + " )");
                break;
            case 1:
                sql.WHERE("NOT EXISTS ( " + sql1.toString() + " )");
                break;
            default:
                break;
        }

        return sql.toString();
    }

    public String perteneceAunaSociedad(Short idInstitucion, String idPersona) {

        SQL sql = new SQL();
        sql.SELECT("IDPERSONA");
        sql.SELECT("IDCUENTA");
        sql.FROM("CEN_COMPONENTES");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("CEN_CLIENTE_IDPERSONA = '" + idPersona + "'");
        sql.WHERE("IDINSTITUCION = CEN_CLIENTE_IDINSTITUCION");
        sql.WHERE("SOCIEDAD = '1'");
        sql.WHERE("FECHABAJA IS NULL");

        return sql.toString();
    }

    public String tieneCuentaAbonoAsociada(Short idInstitucion, String idPersonaSociedad, String idCuenta) {

        SQL sql = new SQL();
        sql.SELECT("IDCUENTA");
        sql.FROM("CEN_CUENTASBANCARIAS");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("IDPERSONA = '" + idPersonaSociedad + "'");
        sql.WHERE("FECHABAJA IS NULL");
        sql.WHERE("ABONOSJCS = '1'");
        sql.WHERE("IDCUENTA = '" + idCuenta + "'");

        return sql.toString();
    }

    public String getCuentaBancariaActiva(Short idInstitucion, String idPersona) {

        SQL sql = new SQL();
        sql.SELECT("IDCUENTA");
        sql.FROM("CEN_CUENTASBANCARIAS");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("IDPERSONA = '" + idPersona + "'");
        sql.WHERE("FECHABAJA IS NULL");
        sql.WHERE("ABONOSJCS = '1'");
        sql.ORDER_BY("FECHAMODIFICACION DESC");

        return sql.toString();
    }

    public String getMovimientosRW(Short idInstitucion, String idPersona, String idFacturacion, String fDesde, String idGrupoFacturacion, int caso) {

        SQL sql = new SQL();
        sql.SELECT("M.MOTIVO");
        sql.SELECT("M.DESCRIPCION");
        sql.SELECT("M.IDINSTITUCION");
        sql.SELECT("M.IDMOVIMIENTO");
        sql.SELECT("M.CANTIDAD");
        sql.SELECT("M.IDFACTURACION");
        sql.SELECT("M.IDGRUPOFACTURACION");
        sql.SELECT("SUM(NVL(A.IMPORTEAPLICADO, 0)) AS IMPORTEAPLICADO");
        sql.SELECT("M.FECHAMODIFICACION");
        sql.SELECT("M.FECHAALTA");
        sql.SELECT("M.USUMODIFICACION");
        sql.FROM("FCS_MOVIMIENTOSVARIOS M " +
                "LEFT JOIN FCS_APLICA_MOVIMIENTOSVARIOS A ON M.IDINSTITUCION = A.IDINSTITUCION " +
                "AND M.IDMOVIMIENTO = A.IDMOVIMIENTO");
        sql.WHERE("M.IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("M.IDPERSONA = '" + idPersona + "'");

        SQL consultaPorFacturacion = new SQL();
        consultaPorFacturacion.SELECT("1");
        consultaPorFacturacion.FROM("FCS_FACTURACIONJG FACJG");
        consultaPorFacturacion.WHERE("TRUNC(FECHADESDE) <= TO_DATE('" + fDesde + "', 'YYYY/MM/DD HH24:MI:SS')");
        consultaPorFacturacion.WHERE("M.IDINSTITUCION = FACJG.IDINSTITUCION");
        consultaPorFacturacion.WHERE("M.IDFACTURACION = FACJG.IDFACTURACION");

        switch (caso) {
            case SigaConstants.CASO_MVNOASOCIADO:
                sql.WHERE("M.IDFACTURACION IS NULL");
                sql.WHERE("M.IDGRUPOFACTURACION IS NULL");
                break;
            case SigaConstants.CASO_MVASOCIADOAFACTURACION:
                sql.WHERE("M.IDGRUPOFACTURACION IS NULL");
                sql.WHERE("M.IDFACTURACION IS NOT NULL");
                sql.WHERE("(M.IDFACTURACION = " + idFacturacion + " OR EXISTS ( " + consultaPorFacturacion.toString() + " ))");
                break;
            case SigaConstants.CASO_MVASOCIADOAGRUPOFACT:
                sql.WHERE("M.IDGRUPOFACTURACION = " + idGrupoFacturacion);
                sql.WHERE("(M.IDFACTURACION IS NULL OR EXISTS ( " + consultaPorFacturacion.toString() + " ))");
                break;
        }

        sql.GROUP_BY("M.MOTIVO");
        sql.GROUP_BY("M.DESCRIPCION");
        sql.GROUP_BY("M.IDINSTITUCION");
        sql.GROUP_BY("M.IDMOVIMIENTO");
        sql.GROUP_BY("M.CANTIDAD");
        sql.GROUP_BY("M.FECHAMODIFICACION");
        sql.GROUP_BY("M.USUMODIFICACION");
        sql.GROUP_BY("M.FECHAALTA");
        sql.GROUP_BY("M.IDFACTURACION");
        sql.GROUP_BY("M.IDGRUPOFACTURACION");

        sql.HAVING("ABS(M.CANTIDAD) > ABS(SUM(NVL(A.IMPORTEAPLICADO, 0)))");

        sql.ORDER_BY("CASE WHEN (M.CANTIDAD > 0) THEN '1' ELSE '2' END ASC");
        sql.ORDER_BY("M.FECHAALTA ASC");

        return sql.toString();
    }

    public String getFacturacionesGruposPagos(String idPago, String idInstitucion) {

        SQL sql = new SQL();
        sql.SELECT_DISTINCT("FCS_PAGOSJG.IDFACTURACION");
        sql.SELECT_DISTINCT("FCS_FACT_GRUPOFACT_HITO.IDGRUPOFACTURACION");
        sql.FROM("FCS_PAGOSJG");
        sql.JOIN("FCS_FACT_GRUPOFACT_HITO ON FCS_FACT_GRUPOFACT_HITO.IDINSTITUCION(+) = FCS_PAGOSJG.IDINSTITUCION" +
                " AND FCS_FACT_GRUPOFACT_HITO.IDFACTURACION(+) = FCS_PAGOSJG.IDFACTURACION");
        sql.WHERE("FCS_PAGOSJG.IDPAGOSJG = " + idPago);
        sql.WHERE("FCS_PAGOSJG.IDINSTITUCION = " + idInstitucion);

        return sql.toString();
    }

    public String getSumaMovimientosAplicados(String idInstitucion, String idMovimiento, String idPersona) {

        SQL sql = new SQL();
        sql.SELECT("NVL(SUM(IMPORTEAPLICADO), 0) AS IMPORTE");
        sql.FROM("FCS_APLICA_MOVIMIENTOSVARIOS");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDPERSONA = " + idPersona);
        sql.WHERE("IDMOVIMIENTO = " + idMovimiento);

        return sql.toString();
    }

    public String getSumaRetenciones(String idInstitucion, String idPago, String idPersona) {

        SQL sql = new SQL();
        sql.SELECT("NVL(SUM(IMPORTERETENIDO), 0) AS IMPORTE");
        sql.FROM("FCS_COBROS_RETENCIONJUDICIAL");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDPERSONA = " + idPersona);
        sql.WHERE("IDPAGOSJG = " + idPago);

        return sql.toString();
    }

    public String getNuevoIdAplicaMovimientosVarios() {

        SQL sql = new SQL();
        sql.SELECT("(NVL(MAX(IDAPLICACION), 0) + 1) AS IDAPLICACION");
        sql.FROM("FCS_APLICA_MOVIMIENTOSVARIOS");

        return sql.toString();
    }

    public String getDetallePagoColegiado(String idInstitucion, String idPagosJg, String idPersona, boolean irpf, String idioma) {

        SQL sql = new SQL();
        sql.SELECT("PC.IDINSTITUCION");
        sql.SELECT("PC.IDPAGOSJG as IDPAGOS");
        sql.SELECT("SUM(PC.IMPOFICIO + PC.IMPASISTENCIA + PC.IMPEJG + PC.IMPSOJ) AS TOTALIMPORTESJCS");
        sql.SELECT("SUM(PC.IMPRET) AS IMPORTETOTALRETENCIONES");
        sql.SELECT("SUM(PC.IMPMOVVAR) AS IMPORTETOTALMOVIMIENTOS");
        sql.SELECT("SUM(PC.IMPIRPF) AS TOTALIMPORTEIRPF");
        sql.SELECT("SUM(PC.IMPOFICIO + PC.IMPASISTENCIA + PC.IMPEJG + PC.IMPSOJ) + SUM(PC.IMPRET) + SUM(PC.IMPMOVVAR) + SUM(PC.IMPIRPF) AS TOTALFINAL");
        sql.SELECT("SUM(PC.IMPIRPF) AS TOTALIMPORTEIVA");

        if (irpf) {
            sql.SELECT("PC.IDPERDESTINO as IDPERSONASJCS");
        } else {
            sql.SELECT("PC.IDCUENTA");
            sql.SELECT("PC.IDPERDESTINO");
            sql.SELECT("PC.PORCENTAJEIRPF AS TIPOIRPF");
            sql.SELECT("SUM(PC.IMPOFICIO) AS IMPORTETOTALOFICIO");
            sql.SELECT("SUM(PC.IMPASISTENCIA) AS IMPORTETOTALASISTENCIA");
            sql.SELECT("SUM(PC.IMPEJG) AS IMPORTETOTALEJG");
            sql.SELECT("SUM(PC.IMPSOJ) AS IMPORTETOTALSOJ");
            sql.SELECT("DECODE(PC.IDPERDESTINO, PC.IDPERORIGEN, 'Colegiado', 'Sociedad') AS DESTINATARIO");
            sql.SELECT("F_SIGA_GETRECURSO_ETIQUETA(DECODE(PC.IDCUENTA, NULL, 'gratuita.pagos.porCaja', 'gratuita.pagos.porBanco'), " + idioma + ") AS FORMADEPAGO");

            SQL subQuery = new SQL();
            subQuery.SELECT("B.NOMBRE");
            subQuery.FROM("CEN_CUENTASBANCARIAS CB");
            subQuery.INNER_JOIN("CEN_BANCOS B ON CB.CBO_CODIGO = B.CODIGO");
            subQuery.WHERE("PC.IDPERDESTINO = CB.IDPERSONA");
            subQuery.WHERE("PC.IDINSTITUCION = CB.IDINSTITUCION");
            subQuery.WHERE("PC.IDCUENTA = CB.IDCUENTA");
            sql.SELECT("( " + subQuery.toString() + " ) AS NOMBREBANCO");

            SQL subQuery2 = new SQL();
            subQuery2.SELECT("F_SIGA_FORMATOIBAN(CB.IBAN) AS CUENTA");
            subQuery2.FROM("CEN_CUENTASBANCARIAS CB");
            subQuery2.WHERE("PC.IDPERDESTINO = CB.IDPERSONA");
            subQuery2.WHERE("PC.IDINSTITUCION = CB.IDINSTITUCION");
            subQuery2.WHERE("PC.IDCUENTA = CB.IDCUENTA");
            sql.SELECT("( " + subQuery2.toString() + " ) AS NUMEROCUENTA");

            sql.SELECT("PC.IDPERORIGEN as IDPERSONASJCS");
        }

        sql.FROM("FCS_PAGO_COLEGIADO PC");
        sql.WHERE("PC.IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("PC.IDPAGOSJG = NVL(" + idPagosJg + ", PC.IDPAGOSJG)");

        if (irpf) {
            sql.WHERE("PC.IDPERDESTINO = '" + idPersona + "'");
        } else {
            sql.WHERE("PC.IDPERORIGEN = '" + idPersona + "'");
        }

        if (irpf) {
            sql.GROUP_BY("PC.IDPERDESTINO, PC.IDPAGOSJG, PC.IDINSTITUCION, PC.PORCENTAJEIRPF, PC.IDCUENTA");
        } else {
            sql.GROUP_BY("PC.IDPERORIGEN, PC.IDPERDESTINO, PC.IDPAGOSJG, PC.IDINSTITUCION, PC.PORCENTAJEIRPF, PC.IDCUENTA");
        }

        return sql.toString();
    }

    public String getPago(String idPago, Short idInstitucion, String idLenguaje) {

        SQL subQuery = new SQL();

        subQuery.SELECT("REC.DESCRIPCION DESCRIPCION");
        subQuery.FROM("FCS_ESTADOSPAGOS ESTADOS");
        subQuery.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON ESTADOS.DESCRIPCION = REC.IDRECURSO");
        subQuery.WHERE("EST.IDESTADOPAGOSJG = ESTADOS.IDESTADOPAGOSJG");
        subQuery.WHERE("REC.IDLENGUAJE = " + idLenguaje);

        SQL subQueryEstadoPago = new SQL();
        subQueryEstadoPago.SELECT("MAX(FECHAESTADO)");
        subQueryEstadoPago.FROM("FCS_PAGOS_ESTADOSPAGOS");
        subQueryEstadoPago.WHERE("IDPAGOSJG = '" + idPago + "'");
        subQueryEstadoPago.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

        SQL query = new SQL();
        query.SELECT("PG.IDINSTITUCION");
        query.SELECT("PG.IDPAGOSJG");
        query.SELECT("PG.NOMBRE");
        query.SELECT("EST.IDESTADOPAGOSJG AS IDESTADO");
        query.SELECT("(" + subQuery.toString() + ") AS DESESTADO");
        query.SELECT("PG.IDFACTURACION");
        query.SELECT("TO_CHAR(FAC.FECHADESDE, 'DD/MM/YYYY') || '-' || TO_CHAR(FAC.FECHAHASTA, 'DD/MM/YYYY') || ' - ' || FAC.NOMBRE AS NOMBREFAC");
        query.FROM("FCS_PAGOSJG PG");
        query.JOIN(
                "FCS_FACTURACIONJG FAC ON FAC.IDINSTITUCION = PG.IDINSTITUCION AND FAC.IDFACTURACION = PG.IDFACTURACION");
        query.INNER_JOIN(
                "FCS_PAGOS_ESTADOSPAGOS EST ON PG.IDINSTITUCION = EST.IDINSTITUCION AND PG.IDPAGOSJG = EST.IDPAGOSJG");
        query.WHERE("PG.IDINSTITUCION = '" + idInstitucion + "'");
        query.WHERE("PG.IDPAGOSJG = '" + idPago + "'");
        query.WHERE("EST.FECHAESTADO = (" + subQueryEstadoPago.toString() + ")");

        return query.toString();
    }

    public String getFechaEstadoPago(Short idInstitucion, Integer idPago) {

        SQL subQuery = new SQL();
        subQuery.SELECT("MAX(FECHAESTADO)");
        subQuery.FROM("FCS_PAGOS_ESTADOSPAGOS");
        subQuery.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery.WHERE("IDPAGOSJG = " + idPago);

        SQL query = new SQL();
        query.SELECT("FPE.FECHAESTADO");
        query.FROM("FCS_PAGOSJG FP");
        query.JOIN("FCS_PAGOS_ESTADOSPAGOS FPE ON FP.IDINSTITUCION = FPE.IDINSTITUCION AND FP.IDPAGOSJG = FPE.IDPAGOSJG");
        query.WHERE("FPE.IDINSTITUCION = " + idInstitucion);
        query.WHERE("FPE.IDPAGOSJG = " + idPago);
        query.WHERE("FPE.FECHAESTADO = ( " + subQuery.toString() + " )");


        return query.toString();
    }

    public String comboAplicadoEnPago (String idinstitucion) {
    	
    	SQL sql = new SQL();
    	
    	sql.SELECT("FCS_PAGOSJG.IDPAGOSJG AS ID");
    	sql.SELECT("substr(to_char(fcs_pagosjg.fechadesde,'dd/mm/yyyy') || '-' ||  to_char(fcs_pagosjg.fechahasta,'dd/mm/yyyy') || ' - ' || fcs_pagosjg.NOMBRE,0,80) AS DESCRIPCION");
    	sql.FROM("FCS_PAGOSJG");
    	sql.WHERE("FCS_PAGOSJG.IDINSTITUCION = "+idinstitucion);
    	sql.ORDER_BY("FCS_PAGOSJG.FECHADESDE DESC");
    	return sql.toString();
    }
}
