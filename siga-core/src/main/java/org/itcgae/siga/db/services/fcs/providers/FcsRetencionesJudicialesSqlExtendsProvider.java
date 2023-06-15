package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AplicacionRetencionRequestDTO;
import org.itcgae.siga.DTOs.scs.RetencionesRequestDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.FcsRetencionesJudicialesSqlProvider;

import java.text.SimpleDateFormat;

public class FcsRetencionesJudicialesSqlExtendsProvider extends FcsRetencionesJudicialesSqlProvider {

    public String searchRetenciones(Short idInstitucion, RetencionesRequestDTO retencionesRequestDTO, String idLenguaje, Integer tamMaximo) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        SQL subQuery = new SQL();
        subQuery.SELECT("DECODE(COL.COMUNITARIO, 1, COL.NCOMUNITARIO, COL.NCOLEGIADO)");
        subQuery.FROM("CEN_COLEGIADO COL");
        subQuery.WHERE("COL.IDPERSONA = RETENCIONES.IDPERSONA");
        subQuery.WHERE("COL.IDINSTITUCION = RETENCIONES.IDINSTITUCION");

        SQL subQuery2 = new SQL();
        subQuery2.SELECT("(PER.APELLIDOS2 || ' ' || PER.APELLIDOS1 || ', ' || PER.NOMBRE)");
        subQuery2.FROM("CEN_PERSONA PER");
        subQuery2.WHERE("PER.IDPERSONA = RETENCIONES.IDPERSONA");

        SQL subQuery3 = new SQL();
        subQuery3.SELECT("NOMBRE");
        subQuery3.FROM("FCS_DESTINATARIOS_RETENCIONES DES");
        subQuery3.WHERE("DES.IDINSTITUCION = RETENCIONES.IDINSTITUCION");
        subQuery3.WHERE("DES.IDDESTINATARIO = RETENCIONES.IDDESTINATARIO");

        SQL subQuery4 = new SQL();
        subQuery4.SELECT("SUM(C.IMPORTERETENIDO)");
        subQuery4.FROM("FCS_COBROS_RETENCIONJUDICIAL C");
        subQuery4.WHERE("C.IDINSTITUCION = RETENCIONES.IDINSTITUCION");
        subQuery4.WHERE("C.IDPERSONA = RETENCIONES.IDPERSONA");
        subQuery4.WHERE("C.IDRETENCION = RETENCIONES.IDRETENCION");

        SQL subQuery5 = new SQL();
        subQuery5.SELECT("LTRIM(DECODE(COL.COMUNITARIO, 1, COL.NCOMUNITARIO, COL.NCOLEGIADO), '0')");
        subQuery5.FROM("CEN_COLEGIADO COL");
        subQuery5.WHERE("COL.IDPERSONA = RETENCIONES.IDPERSONA");
        subQuery5.WHERE("COL.IDINSTITUCION = RETENCIONES.IDINSTITUCION");

        SQL subQuery6 = new SQL();
        subQuery6.SELECT("SUM(C.IMPORTERETENIDO)");
        subQuery6.FROM("FCS_COBROS_RETENCIONJUDICIAL C");
        subQuery6.WHERE("C.IDINSTITUCION = RETENCIONES.IDINSTITUCION");
        subQuery6.WHERE("C.IDPERSONA = RETENCIONES.IDPERSONA");
        subQuery6.WHERE("C.IDRETENCION = RETENCIONES.IDRETENCION");

        SQL subQuery7 = new SQL();
        subQuery7.SELECT("IDDESTINATARIO");
        subQuery7.FROM("FCS_DESTINATARIOS_RETENCIONES DES");
        subQuery7.WHERE("DES.IDINSTITUCION = RETENCIONES.IDINSTITUCION");
        subQuery7.WHERE("DES.IDDESTINATARIO = RETENCIONES.IDDESTINATARIO");

        SQL subQuery8 = new SQL();
        subQuery8.SELECT(" MAX(FECHAESTADO)");
        subQuery8.FROM("FCS_PAGOS_ESTADOSPAGOS");
        subQuery8.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery8.GROUP_BY("IDPAGOSJG");

        SQL subQuery9 = new SQL();
        subQuery9.SELECT("EST.IDPAGOSJG");
        subQuery9.FROM("FCS_PAGOSJG PAGO");
        subQuery9.JOIN("FCS_PAGOS_ESTADOSPAGOS EST ON EST.IDINSTITUCION = PAGO.IDINSTITUCION AND EST.IDPAGOSJG = PAGO.IDPAGOSJG");
        subQuery9.WHERE("EST.IDINSTITUCION = " + idInstitucion);
        subQuery9.WHERE("IDESTADOPAGOSJG = 30");
        subQuery9.WHERE("EST.FECHAESTADO IN (" + subQuery8.toString() + ")");
        if (null != retencionesRequestDTO.getFechaAplicacionDesde()) {
            String fecha = simpleDateFormat.format(retencionesRequestDTO.getFechaAplicacionDesde());
            subQuery9.WHERE("TRUNC(EST.FECHAESTADO) >= TRUNC(TO_DATE('" + fecha + "', 'YYYY/MM/DD HH24:MI:SS'))");
        }
        if (null != retencionesRequestDTO.getFechaAplicacionHasta()) {
            String fecha = simpleDateFormat.format(retencionesRequestDTO.getFechaAplicacionHasta());
            subQuery9.WHERE("TRUNC(EST.FECHAESTADO) <= TRUNC(TO_DATE('" + fecha + "', 'YYYY/MM/DD HH24:MI:SS'))");
        }

        SQL query = new SQL();
        if (!UtilidadesString.esCadenaVacia(retencionesRequestDTO.getIdPagos()) || null != retencionesRequestDTO.getFechaAplicacionDesde()
                || null != retencionesRequestDTO.getFechaAplicacionHasta()) {
            query.SELECT("C.IDPAGOSJG");
        }
        query.SELECT("O.ORDEN");
        query.SELECT("FECHAALTA");
        query.SELECT("RETENCIONES.IDINSTITUCION");
        query.SELECT("RETENCIONES.IDPERSONA");
        query.SELECT("RETENCIONES.IDRETENCION");
        query.SELECT("(" + subQuery.toString() + ") AS NCOLEGIADO");
        query.SELECT("DECODE(RETENCIONES.TIPORETENCION, 'P', F_SIGA_GETRECURSO_ETIQUETA('FACTSJCS.MANTRETENCIONESJ.LITERAL.PORCENTUAL', " + idLenguaje + "), 'F', F_SIGA_GETRECURSO_ETIQUETA('FACTSJCS.MANTRETENCIONESJ.LITERAL.IMPORTEFIJO', " + idLenguaje + "), 'L', F_SIGA_GETRECURSO_ETIQUETA('FACTSJCS.MANTRETENCIONESJ.LITERAL.TRAMOSLEC', " + idLenguaje + ")) AS TIPORETENCION");
        query.SELECT("(" + subQuery2.toString() + ") AS NOMBRE");
        query.SELECT("(" + subQuery7.toString() + ") AS IDDESTINATARIO");
        query.SELECT("(" + subQuery3.toString() + ") AS NOMBREDESTINATARIO");
        query.SELECT("RETENCIONES.FECHAINICIO");
        query.SELECT("RETENCIONES.FECHAFIN");
        query.SELECT("RETENCIONES.IMPORTE");
        query.SELECT("F_SIGA_APLICADARETENCION(RETENCIONES.IDINSTITUCION, RETENCIONES.IDRETENCION) RETENCIONAPLICADA");
        query.SELECT("DECODE(TIPORETENCION, 'P', '-', DECODE(IMPORTE, NULL, '-', (IMPORTE + NVL((" + subQuery4.toString() + "), 0)))) RESTANTE");
        query.FROM("FCS_RETENCIONES_JUDICIALES RETENCIONES");
        query.FROM("FCS_DESTINATARIOS_RETENCIONES O");
        if (!UtilidadesString.esCadenaVacia(retencionesRequestDTO.getIdPagos()) || null != retencionesRequestDTO.getFechaAplicacionDesde()
                || null != retencionesRequestDTO.getFechaAplicacionHasta()) {
            query.FROM("FCS_COBROS_RETENCIONJUDICIAL C");
            query.WHERE("C.IDINSTITUCION = RETENCIONES.IDINSTITUCION");
            query.WHERE("C.IDRETENCION = RETENCIONES.IDRETENCION");
        }
        query.WHERE("RETENCIONES.IDINSTITUCION = " + idInstitucion);
        query.WHERE("RETENCIONES.IDINSTITUCION = O.IDINSTITUCION");
        query.WHERE("RETENCIONES.IDDESTINATARIO = O.IDDESTINATARIO");
        if (!UtilidadesString.esCadenaVacia(retencionesRequestDTO.getNColegiado())) {
            query.WHERE("(" + subQuery5.toString() + ") = LTRIM('" + retencionesRequestDTO.getNColegiado() + "', '0')");
        }
        if (!UtilidadesString.esCadenaVacia(retencionesRequestDTO.getTiposRetencion())) {
            query.WHERE("RETENCIONES.TIPORETENCION IN (" + retencionesRequestDTO.getTiposRetencion() + ")");
        }
        if (!UtilidadesString.esCadenaVacia(retencionesRequestDTO.getIdDestinatarios())) {
            query.WHERE("RETENCIONES.IDDESTINATARIO IN (" + retencionesRequestDTO.getIdDestinatarios() + " )");
        }
        if (null != retencionesRequestDTO.getFechainicio()) {
            String fecha = simpleDateFormat.format(retencionesRequestDTO.getFechainicio());
            query.WHERE("TRUNC(FECHAINICIO) >= TRUNC(TO_DATE('" + fecha + "', 'YYYY/MM/DD HH24:MI:SS'))");
        }
        if (null != retencionesRequestDTO.getFechaFin()) {
            String fecha = simpleDateFormat.format(retencionesRequestDTO.getFechaFin());
            query.WHERE("TRUNC(FECHAINICIO) <= TRUNC(TO_DATE('" + fecha + "', 'YYYY/MM/DD HH24:MI:SS'))");
        }
        if (!retencionesRequestDTO.isHistorico()) {
            query.WHERE("(FECHAFIN IS NULL OR TRUNC(FECHAFIN) >= TRUNC(SYSDATE))");
        }
        query.WHERE("(( IMPORTE + NVL ((" + subQuery6.toString() + "), 0) ) > 0 OR TIPORETENCION = 'P' OR (TIPORETENCION = 'L' AND IMPORTE IS NULL))");
        if (!UtilidadesString.esCadenaVacia(retencionesRequestDTO.getIdPagos())) {
            query.WHERE("C.IDPAGOSJG IN (" + retencionesRequestDTO.getIdPagos() + ")");
        }
        if (null != retencionesRequestDTO.getFechaAplicacionDesde() || null != retencionesRequestDTO.getFechaAplicacionHasta()) {
            query.WHERE("C.IDPAGOSJG IN (" + subQuery9.toString() + ")");
        }

        SQL superQuery = new SQL();
        superQuery.SELECT("IDINSTITUCION");
        superQuery.SELECT("IDPERSONA");
        superQuery.SELECT("IDRETENCION");
        superQuery.SELECT("NCOLEGIADO");
        superQuery.SELECT("TIPORETENCION");
        superQuery.SELECT("NOMBRE");
        superQuery.SELECT("NOMBREDESTINATARIO");
        superQuery.SELECT("FECHAINICIO");
        superQuery.SELECT("FECHAFIN");
        superQuery.SELECT("IMPORTE");
        superQuery.SELECT("RETENCIONAPLICADA");
        superQuery.SELECT("RESTANTE");
        superQuery.FROM("(" + query.toString() + ")");

        if (tamMaximo != null) {
            Integer tamMaxNumber = tamMaximo + 1;
            superQuery.WHERE("ROWNUM <= " + tamMaxNumber);
        }

        if (!UtilidadesString.esCadenaVacia(retencionesRequestDTO.getIdPagos()) || null != retencionesRequestDTO.getFechaAplicacionDesde()
                || null != retencionesRequestDTO.getFechaAplicacionHasta()) {
            superQuery.GROUP_BY("IDPAGOSJG");
        }
        superQuery.GROUP_BY("FECHAALTA");
        superQuery.GROUP_BY("ORDEN");
        superQuery.GROUP_BY("IDINSTITUCION");
        superQuery.GROUP_BY("IDPERSONA");
        superQuery.GROUP_BY("IDRETENCION");
        superQuery.GROUP_BY("NCOLEGIADO");
        superQuery.GROUP_BY("TIPORETENCION");
        superQuery.GROUP_BY("NOMBRE");
        superQuery.GROUP_BY("NOMBREDESTINATARIO");
        superQuery.GROUP_BY("FECHAINICIO");
        superQuery.GROUP_BY("FECHAFIN");
        superQuery.GROUP_BY("IMPORTE");
        superQuery.GROUP_BY("RETENCIONAPLICADA");
        superQuery.GROUP_BY("RESTANTE");

        superQuery.ORDER_BY("ORDEN");
        superQuery.ORDER_BY("FECHAINICIO");
        superQuery.ORDER_BY("FECHAALTA");

        return superQuery.toString();
    }

    public String searchRetencionConRestante(Short idInstitucion, String idRetencion) {
        SQL sql = new SQL();
        SQL sql1 = new SQL();

        sql1.SELECT("SUM(C.IMPORTERETENIDO)");
        sql1.FROM("FCS_COBROS_RETENCIONJUDICIAL C");
        sql1.WHERE("C.IDINSTITUCION = frj.IDINSTITUCION");
        sql1.WHERE("C.IDPERSONA = frj.IDPERSONA");
        sql1.WHERE("C.IDRETENCION = frj.IDRETENCION");

        sql.SELECT("IDINSTITUCION, IDRETENCION, IDPERSONA, IDDESTINATARIO, FECHAALTA, FECHAINICIO, FECHAFIN, TIPORETENCION, IMPORTE, OBSERVACIONES, FECHAMODIFICACION, USUMODIFICACION, CONTABILIZADO, ESDETURNO, DESCDESTINATARIO");
        sql.SELECT("(IMPORTE + NVL((" + sql1 + "), 0)) RESTANTE");
        sql.FROM("FCS_RETENCIONES_JUDICIALES frj");
        sql.WHERE("frj.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("frj.IDRETENCION = " + idRetencion);

        return sql.toString();
    }

    public String searchRetencionesAplicadas(Short idInstitucion, RetencionesRequestDTO retencionesRequestDTO, Integer tamMaximo) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        SQL sql = new SQL();

        sql.SELECT("RET.TIPORETENCION");
        sql.SELECT("F_SIGA_CALCULONCOLEGIADO(COB.IDINSTITUCION,COB.IDPERSONA) NCOLEGIADO");
        sql.SELECT("(PER.APELLIDOS2 || ' ' || PER.APELLIDOS1 || ', ' || PER.NOMBRE) NOMBRE");
        sql.SELECT("RET.IDDESTINATARIO");
        sql.SELECT("DEST.NOMBRE NOMBREDESTINATARIO");
        sql.SELECT("RET.DESCDESTINATARIO");
        sql.SELECT("RET.FECHAINICIO");
        sql.SELECT("RET.FECHAFIN");
        sql.SELECT("COB.FECHARETENCION");
        sql.SELECT("COB.IMPORTERETENIDO");
        sql.SELECT("COB.IDPAGOSJG");
        sql.SELECT("COB.ANIO || '/' || LPAD(COB.MES, 2, '0') ANIOMES");
        sql.SELECT("PAGO.NOMBRE PAGORELACIONADO");
        sql.SELECT("ABONO.NUMEROABONO ABONORELACIONADO");
        sql.SELECT("COB.IDCOBRO");
        sql.SELECT("COB.IDRETENCION");
        sql.SELECT("COB.IDINSTITUCION");
        sql.SELECT("COB.IDPERSONA");
        sql.SELECT("TO_CHAR(PAGO.FECHADESDE,'dd/mm/yyyy') FECHADESDE");
        sql.SELECT("TO_CHAR(PAGO.FECHAHASTA,'dd/mm/yyyy') FECHAHASTA");
        sql.SELECT("(PCOL.IMPOFICIO + PCOL.IMPASISTENCIA + PCOL.IMPSOJ + PCOL.IMPEJG) IMPORTEPAGO");

        sql.FROM("FCS_COBROS_RETENCIONJUDICIAL COB");
        sql.FROM("FCS_RETENCIONES_JUDICIALES RET");
        sql.FROM("FCS_DESTINATARIOS_RETENCIONES DEST");
        sql.FROM("FCS_PAGO_COLEGIADO PCOL");
        sql.FROM("FAC_ABONO ABONO");
        sql.FROM("FCS_PAGOSJG PAGO");
        sql.FROM("CEN_PERSONA PER");

        sql.WHERE("COB.IDINSTITUCION = RET.IDINSTITUCION");
        sql.WHERE("COB.IDRETENCION = RET.IDRETENCION");
        sql.WHERE("RET.IDINSTITUCION = DEST.IDINSTITUCION");
        sql.WHERE("RET.IDDESTINATARIO = DEST.IDDESTINATARIO");
        sql.WHERE("COB.IDINSTITUCION = PCOL.IDINSTITUCION");
        sql.WHERE("COB.IDPAGOSJG = PCOL.IDPAGOSJG");
        sql.WHERE("COB.IDPERSONA = PCOL.IDPERORIGEN");
        sql.WHERE("PCOL.IDPERDESTINO = ABONO.IDPERSONA(+)");
        sql.WHERE("PCOL.IDINSTITUCION = ABONO.IDINSTITUCION(+)");
        sql.WHERE("PCOL.IDPAGOSJG = ABONO.IDPAGOSJG(+)");
        sql.WHERE("PCOL.IDINSTITUCION = PAGO.IDINSTITUCION");
        sql.WHERE("PCOL.IDPAGOSJG = PAGO.IDPAGOSJG");
        sql.WHERE("PCOL.IDPERORIGEN = PER.IDPERSONA");
        sql.WHERE("COB.IDINSTITUCION = " + idInstitucion);
        if (!UtilidadesString.esCadenaVacia(retencionesRequestDTO.getIdPersona())) {
            sql.WHERE("COB.IDPERSONA = " + retencionesRequestDTO.getIdPersona());
        }
        if (!UtilidadesString.esCadenaVacia(retencionesRequestDTO.getIdRetenciones())) {
            sql.WHERE("RET.IDRETENCION = " + retencionesRequestDTO.getIdRetenciones());
        }
        if (null != retencionesRequestDTO.getFechainicio()) {
            String fecha = simpleDateFormat.format(retencionesRequestDTO.getFechainicio());
            sql.WHERE("RET.FECHAINICIO >= TO_DATE('" + fecha + "', 'YYYY/MM/DD HH24:MI:SS')");
        }
        if (null != retencionesRequestDTO.getFechaFin()) {
            String fecha = simpleDateFormat.format(retencionesRequestDTO.getFechaFin());
            sql.WHERE("RET.FECHAFIN <= TO_DATE('" + fecha + "', 'YYYY/MM/DD HH24:MI:SS')");
        }
        if (!UtilidadesString.esCadenaVacia(retencionesRequestDTO.getTiposRetencion())) {
            sql.WHERE("RET.TIPORETENCION IN (" + retencionesRequestDTO.getTiposRetencion() + ")");
        }
        if (!UtilidadesString.esCadenaVacia(retencionesRequestDTO.getIdDestinatarios())) {
            sql.WHERE("RET.IDDESTINATARIO IN (" + retencionesRequestDTO.getIdDestinatarios() + ")");
        }
        if (!UtilidadesString.esCadenaVacia(retencionesRequestDTO.getIdPagos())) {
            sql.WHERE("PAGO.IDPAGOSJG IN (" + retencionesRequestDTO.getIdPagos() + ")");
        }
        if (!UtilidadesString.esCadenaVacia(retencionesRequestDTO.getNumeroAbono())) {
            sql.WHERE("ABONO.NUMEROABONO = '" + retencionesRequestDTO.getNumeroAbono() + "'");
        }

        if (!retencionesRequestDTO.isHistorico()) {
            sql.WHERE("(RET.FECHAFIN IS NULL OR TRUNC(RET.FECHAFIN) >= TRUNC(SYSDATE))");
        }

        sql.ORDER_BY("PER.APELLIDOS1");
        sql.ORDER_BY("PER.APELLIDOS2");
        sql.ORDER_BY("PER.NOMBRE");
        sql.ORDER_BY("RET.DESCDESTINATARIO");
        sql.ORDER_BY("ANIO DESC");
        sql.ORDER_BY("MES DESC");
        sql.ORDER_BY("COB.IDPAGOSJG DESC");
        sql.ORDER_BY("RET.FECHAINICIO DESC");

        SQL superQuery = new SQL();
        superQuery.SELECT("*");
        superQuery.FROM("(" + sql.toString() + ")");

        if (tamMaximo != null) {
            Integer tamMaxNumber = tamMaximo + 1;
            superQuery.WHERE("ROWNUM <= " + tamMaxNumber);
        }

        return superQuery.toString();
    }

    public String getAplicacionesRetenciones(Short idInstitucion, AplicacionRetencionRequestDTO aplicacionRetencionRequestDTO) {

        SQL importeAntAplicaRetencion = new SQL();
        importeAntAplicaRetencion.SELECT("NVL(SUM(COB2.IMPORTEAPLICARETENCION), 0)");
        importeAntAplicaRetencion.FROM("FCS_COBROS_RETENCIONJUDICIAL COB2");
        importeAntAplicaRetencion.FROM("FCS_RETENCIONES_JUDICIALES RET2");
        importeAntAplicaRetencion.WHERE("COB2.IDINSTITUCION = COB.IDINSTITUCION");
        importeAntAplicaRetencion.WHERE("COB2.IDPERSONA = COB.IDPERSONA");
        importeAntAplicaRetencion.WHERE("COB2.MES = COB.MES");
        importeAntAplicaRetencion.WHERE("COB2.ANIO = COB.ANIO");
        importeAntAplicaRetencion.WHERE("COB2.IDINSTITUCION = RET2.IDINSTITUCION");
        importeAntAplicaRetencion.WHERE("COB2.IDRETENCION = RET2.IDRETENCION");
        importeAntAplicaRetencion.WHERE("RET2.TIPORETENCION = 'L'");
        importeAntAplicaRetencion.WHERE("COB2.IDPAGOSJG < COB.IDPAGOSJG");

        SQL importeAntRetenido = new SQL();
        importeAntRetenido.SELECT("NVL(SUM(COB2.IMPORTERETENIDO), 0)");
        importeAntRetenido.FROM("FCS_COBROS_RETENCIONJUDICIAL COB2");
        importeAntRetenido.FROM("FCS_RETENCIONES_JUDICIALES RET2");
        importeAntRetenido.WHERE("COB2.IDINSTITUCION = COB.IDINSTITUCION");
        importeAntRetenido.WHERE("COB2.IDPERSONA = COB.IDPERSONA");
        importeAntRetenido.WHERE("COB2.MES = COB.MES");
        importeAntRetenido.WHERE("COB2.ANIO = COB.ANIO");
        importeAntRetenido.WHERE("COB2.IDINSTITUCION = RET2.IDINSTITUCION");
        importeAntRetenido.WHERE("COB2.IDRETENCION = RET2.IDRETENCION");
        importeAntRetenido.WHERE("RET2.TIPORETENCION = 'L'");
        importeAntRetenido.WHERE("COB2.IDPAGOSJG < COB.IDPAGOSJG");

        SQL importeTotAplicaRetencion = new SQL();
        importeTotAplicaRetencion.SELECT("NVL(SUM(COB2.IMPORTEAPLICARETENCION), 0)");
        importeTotAplicaRetencion.FROM("FCS_COBROS_RETENCIONJUDICIAL COB2");
        importeTotAplicaRetencion.FROM("FCS_RETENCIONES_JUDICIALES RET2");
        importeTotAplicaRetencion.WHERE("COB2.IDINSTITUCION = COB.IDINSTITUCION");
        importeTotAplicaRetencion.WHERE("COB2.IDPERSONA = COB.IDPERSONA");
        importeTotAplicaRetencion.WHERE("COB2.MES = COB.MES");
        importeTotAplicaRetencion.WHERE("COB2.ANIO = COB.ANIO");
        importeTotAplicaRetencion.WHERE("COB2.IDINSTITUCION = RET2.IDINSTITUCION");
        importeTotAplicaRetencion.WHERE("COB2.IDRETENCION = RET2.IDRETENCION");
        importeTotAplicaRetencion.WHERE("RET2.TIPORETENCION = 'L'");
        importeTotAplicaRetencion.WHERE("COB2.IDPAGOSJG < COB.IDPAGOSJG");

        SQL importeTotRetenido = new SQL();
        importeTotRetenido.SELECT("NVL(SUM(COB2.IMPORTERETENIDO), 0)");
        importeTotRetenido.FROM("FCS_COBROS_RETENCIONJUDICIAL COB2");
        importeTotRetenido.FROM("FCS_RETENCIONES_JUDICIALES RET2");
        importeTotRetenido.WHERE("COB2.IDINSTITUCION = COB.IDINSTITUCION");
        importeTotRetenido.WHERE("COB2.IDPERSONA = COB.IDPERSONA");
        importeTotRetenido.WHERE("COB2.MES = COB.MES");
        importeTotRetenido.WHERE("COB2.ANIO = COB.ANIO");
        importeTotRetenido.WHERE("COB2.IDINSTITUCION = RET2.IDINSTITUCION");
        importeTotRetenido.WHERE("COB2.IDRETENCION = RET2.IDRETENCION");
        importeTotRetenido.WHERE("RET2.TIPORETENCION = 'L'");
        importeTotRetenido.WHERE("COB2.IDPAGOSJG <= COB.IDPAGOSJG");

        SQL importeSmi = new SQL();
        importeSmi.SELECT("SMI.VALOR");
        importeSmi.FROM("FCS_SMI SMI");
        importeSmi.WHERE("SMI.ANIO = COB.ANIO");

        SQL colegiado = new SQL();
        colegiado.SELECT("C.NCOLEGIADO || ' ' || P.NOMBRE || ' ' || P.APELLIDOS1 || ' ' || NVL(P.APELLIDOS2, '')");
        colegiado.FROM("CEN_PERSONA P");
        colegiado.FROM("CEN_COLEGIADO C");
        colegiado.WHERE("COB.IDINSTITUCION = C.IDINSTITUCION");
        colegiado.WHERE("COB.IDPERSONA = C.IDPERSONA");
        colegiado.WHERE("C.IDPERSONA = P.IDPERSONA");


        SQL queryPrincipal = new SQL();
        queryPrincipal.SELECT("COB.ANIO || '/' || LPAD(COB.MES, 2, '0') ANIOMES");
        queryPrincipal.SELECT("RET.TIPORETENCION AS TIPORETENCION");
        queryPrincipal.SELECT("(F_SIGA_FORMATONUMERO((" + importeAntAplicaRetencion.toString() + "), 2)) AS IMPORTEANTAPLICARETENCION");
        queryPrincipal.SELECT("(F_SIGA_FORMATONUMERO((" + importeAntRetenido.toString() + "), 2)) AS IMPORTEANTRETENIDO");
        queryPrincipal.SELECT("(F_SIGA_FORMATONUMERO(COB.IMPORTEAPLICARETENCION, 2)) AS IMPORTEAPLICARETENCION");
        queryPrincipal.SELECT("(F_SIGA_FORMATONUMERO(COB.IMPORTERETENIDO, 2)) AS IMPORTERETENIDO");
        queryPrincipal.SELECT("(F_SIGA_FORMATONUMERO((" + importeTotAplicaRetencion.toString() + ") + COB.IMPORTEAPLICARETENCION, 2)) AS IMPORTETOTAPLICARETENCION");
        queryPrincipal.SELECT("(F_SIGA_FORMATONUMERO((" + importeTotRetenido.toString() + "), 2)) AS IMPORTETOTRETENIDO");
        queryPrincipal.SELECT("(F_SIGA_FORMATONUMERO((" + importeSmi.toString() + "), 2)) AS IMPORTESMI");
        queryPrincipal.SELECT("(" + colegiado.toString() + ") AS COLEGIADO");
        queryPrincipal.SELECT("(PG.NOMBRE || ' (' || TO_CHAR(PG.FECHADESDE, 'DD/MM/YY') || '-' || TO_CHAR(PG.FECHAHASTA, 'DD/MM/YY') || ')') AS NOMBREPAGO");
        queryPrincipal.FROM("FCS_COBROS_RETENCIONJUDICIAL COB");
        queryPrincipal.FROM("FCS_RETENCIONES_JUDICIALES RET");
        queryPrincipal.FROM("FCS_PAGO_COLEGIADO PAGCOL");
        queryPrincipal.FROM("FCS_PAGOSJG PG");
        queryPrincipal.WHERE("COB.IDINSTITUCION = PAGCOL.IDINSTITUCION");
        queryPrincipal.WHERE("COB.IDPAGOSJG = PAGCOL.IDPAGOSJG");
        queryPrincipal.WHERE("COB.IDPERSONA = PAGCOL.IDPERORIGEN");
        queryPrincipal.WHERE("PAGCOL.IDINSTITUCION = PG.IDINSTITUCION");
        queryPrincipal.WHERE("PAGCOL.IDPAGOSJG = PG.IDPAGOSJG");
        queryPrincipal.WHERE("COB.IDINSTITUCION = RET.IDINSTITUCION");
        queryPrincipal.WHERE("COB.IDRETENCION = RET.IDRETENCION");
        queryPrincipal.WHERE("COB.IDINSTITUCION = " + idInstitucion);
        queryPrincipal.WHERE("COB.IDPERSONA = " + aplicacionRetencionRequestDTO.getIdPersona());
        queryPrincipal.WHERE("RET.TIPORETENCION = 'L'");
        queryPrincipal.WHERE("TO_DATE('01' || LPAD(COB.MES, 2, '0') || COB.ANIO, 'DDMMYYYY') BETWEEN TO_DATE('01' || TO_CHAR(TO_DATE('" + aplicacionRetencionRequestDTO.getFechaPagoDesde() + "', 'DD/MM/YYYY'), 'MMYYYY'), 'DDMMYYYY')");
        queryPrincipal.WHERE("TO_DATE('01' || TO_CHAR(TO_DATE('" + aplicacionRetencionRequestDTO.getFechaPagoHasta() + "', 'DD/MM/YYYY'), 'MMYYYY'), 'DDMMYYYY')");
        queryPrincipal.ORDER_BY("COB.ANIO");
        queryPrincipal.ORDER_BY("COB.MES");
        queryPrincipal.ORDER_BY("COB.IDPAGOSJG");
        queryPrincipal.ORDER_BY("COB.IDCOBRO");

        return queryPrincipal.toString();
    }

    public String getNewId(Short idInstitucion) {

        SQL sql = new SQL();
        sql.SELECT("(NVL(MAX(IDRETENCION), 0) + 1) IDRETENCION");
        sql.FROM("FCS_RETENCIONES_JUDICIALES");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);

        return sql.toString();
    }

}
