package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacPagoabonoefectivoSqlProvider;

import java.util.List;
import java.util.stream.Collectors;

public class FacPagoabonoefectivoSqlExtendsProvider extends FacPagoabonoefectivoSqlProvider {

    public String getNuevoID(String institucion, String abono) {

        SQL sql = new SQL();
        sql.SELECT("(NVL((MAX(IDPAGOABONO) + 1), 1 )) AS IDPAGOABONO");
        sql.FROM("FAC_PAGOABONOEFECTIVO");
        sql.WHERE("IDINSTITUCION = '" + institucion + "'");
        sql.WHERE("IDABONO = '" + abono + "'");

        return sql.toString();
    }

    public String deleteDeshacerCierre(Short idInstitucion, List<Integer> idPagos) {

        SQL subQuery = new SQL();
        subQuery.SELECT("IDABONO");
        subQuery.FROM("FAC_ABONO");
        subQuery.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery.WHERE("IDPAGOSJG IN (" + idPagos.stream().map(a -> a.toString()).collect(Collectors.joining(",")) + ")");

        SQL query = new SQL();
        query.DELETE_FROM("FAC_PAGOABONOEFECTIVO");
        query.WHERE("IDINSTITUCION = " + idInstitucion);
        query.WHERE("IDABONO IN ( " + subQuery.toString() + " )");

        return query.toString();
    }

    public String getEstadosAbonosSJCS(String idAbono, Short idInstitucion, String idioma) {

        String pagosCaja = getEstadosPagosCaja(idAbono, idInstitucion, idioma);
        String compensacion = getEstadosCompensacion(idAbono, idInstitucion, idioma);
        String abonoBanco = getEstadosAbonoBanco(idAbono, idInstitucion, idioma);
        String pendienteBanco = getEstadosPendienteBanco(idAbono, idInstitucion, idioma);
        String pendienteCaja = getEstadosPendienteCaja(idAbono, idInstitucion, idioma);
        String emisionPago = getEstadosEmisionPago(idAbono, idInstitucion, idioma);

        SQL principal = new SQL();
        principal.SELECT("*");
        principal.FROM("((" + pagosCaja
                + ") UNION (" + compensacion
                + ") UNION (" + abonoBanco
                + ") UNION (" + pendienteBanco
                + ") UNION (" + pendienteCaja
                + ") UNION (" + emisionPago + "))");
        principal.ORDER_BY("IDTABLA ASC", "FECHA ASC", "FECHAMODIFICACION ASC", "IDENTIFICADOR ASC");

        return principal.toString();
    }

    private String getEstadosPagosCaja(String idAbono, Short idInstitucion, String idioma) {
        SQL pagosCaja = new SQL();

        pagosCaja.SELECT("2 AS IDTABLA");
        pagosCaja.SELECT("fp.IDINSTITUCION");
        pagosCaja.SELECT("fp.IDPAGOABONO AS IDENTIFICADOR");
        pagosCaja.SELECT("NULL AS IDFACTURA");
        pagosCaja.SELECT("NULL AS NUMEROFACTURA");
        pagosCaja.SELECT("fp.IDABONO");
        pagosCaja.SELECT("NULL AS NUMEROABONO");
        pagosCaja.SELECT("fp.FECHA");
        pagosCaja.SELECT("fp.FECHAMODIFICACION");
        pagosCaja.SELECT("F_SIGA_GETRECURSO_ETIQUETA('facturacion.abonosPagos.datosPagoAbono.abonoCaja', " + idioma + ") AS MODO");
        pagosCaja.SELECT("F_SIGA_GETRECURSO_ETIQUETA('general.literal.pagado', " + idioma + ") AS ESTADO");
        pagosCaja.SELECT("fp.IMPORTE");
        pagosCaja.SELECT("NULL AS IMPORTE_PENDIENTE");
        pagosCaja.SELECT("'' AS BANCO");
        pagosCaja.SELECT("NULL AS IDPAGOSJG");

        pagosCaja.FROM("FAC_PAGOABONOEFECTIVO fp");
        pagosCaja.WHERE("fp.IDINSTITUCION = " + idInstitucion + " AND fp.IDABONO = " + idAbono);

        SQL pagosCajaMinus = new SQL();

        pagosCajaMinus.SELECT("2 AS IDTABLA");
        pagosCajaMinus.SELECT("fp.IDINSTITUCION");
        pagosCajaMinus.SELECT("fp.IDPAGOABONO AS IDENTIFICADOR");
        pagosCajaMinus.SELECT("NULL AS IDFACTURA");
        pagosCajaMinus.SELECT("NULL AS NUMEROFACTURA");
        pagosCajaMinus.SELECT("fp.IDABONO");
        pagosCajaMinus.SELECT("NULL AS NUMEROABONO");
        pagosCajaMinus.SELECT("fp.FECHA");
        pagosCajaMinus.SELECT("fp.FECHAMODIFICACION");
        pagosCajaMinus.SELECT("F_SIGA_GETRECURSO_ETIQUETA('facturacion.abonosPagos.datosPagoAbono.abonoCaja', " + idioma + ") AS MODO");
        pagosCajaMinus.SELECT("F_SIGA_GETRECURSO_ETIQUETA('general.literal.pagado', 1) AS ESTADO");
        pagosCajaMinus.SELECT("fp.IMPORTE");
        pagosCajaMinus.SELECT("NULL AS IMPORTE_PENDIENTE");
        pagosCajaMinus.SELECT("'' AS BANCO");
        pagosCajaMinus.SELECT("NULL AS IDPAGOSJG");

        pagosCajaMinus.FROM("FAC_PAGOABONOEFECTIVO fp");
        pagosCajaMinus.FROM("FAC_PAGOSPORCAJA fpc");
        pagosCajaMinus.WHERE("fp.IDINSTITUCION = " + idInstitucion + " AND fp.IDABONO = " + idAbono);
        pagosCajaMinus.WHERE("fp.IDINSTITUCION = fpc.IDINSTITUCION AND fp.IDABONO = fpc.IDABONO AND fp.IDPAGOABONO = fpc.IDPAGOABONO");

        return pagosCaja.toString() + " MINUS (" + pagosCajaMinus.toString() + ")";
    }

    private String getEstadosCompensacion(String idAbono, Short idInstitucion, String idioma) {
        SQL compensacion = new SQL();

        compensacion.SELECT("2 AS IDTABLA");
        compensacion.SELECT("fp.IDINSTITUCION");
        compensacion.SELECT("fp.IDPAGOABONO AS IDENTIFICADOR");
        compensacion.SELECT("ff.IDFACTURA");
        compensacion.SELECT("ff.NUMEROFACTURA");
        compensacion.SELECT("fp.IDABONO");
        compensacion.SELECT("NULL AS NUMEROABONO");
        compensacion.SELECT("fp.FECHA");
        compensacion.SELECT("fp.FECHAMODIFICACION");
        compensacion.SELECT("INITCAP(F_SIGA_GETRECURSO_ETIQUETA('facturacion.pagosFactura.accion.compensacion', " + idioma + ")) AS MODO");
        compensacion.SELECT("F_SIGA_GETRECURSO_ETIQUETA('general.literal.pendienteabonobanco', " + idioma + ") AS ESTADO");
        compensacion.SELECT("fp.IMPORTE");
        compensacion.SELECT("NULL AS IMPORTE_PENDIENTE");
        compensacion.SELECT("'' AS BANCO");
        compensacion.SELECT("NULL AS IDPAGOSJG");

        compensacion.FROM("FAC_PAGOABONOEFECTIVO fp");
        compensacion.FROM("FAC_PAGOSPORCAJA fpc");
        compensacion.FROM("FAC_FACTURA ff");
        compensacion.WHERE("fp.IDINSTITUCION = " + idInstitucion + " AND fp.IDABONO = " + idAbono);
        compensacion.WHERE("fp.IDINSTITUCION = fpc.IDINSTITUCION AND fp.IDABONO = fpc.IDABONO AND fp.IDPAGOABONO = fpc.IDPAGOABONO");
        compensacion.WHERE("fp.IDINSTITUCION = ff.IDINSTITUCION AND fpc.IDFACTURA = ff.IDFACTURA ");

        return compensacion.toString();
    }

    private String getEstadosAbonoBanco(String idAbono, Short idInstitucion, String idioma) {
        SQL cuentaBancaria = new SQL();

        cuentaBancaria.SELECT("Case When Instr(banco.NOMBRE, '~', 2) > 1 Then Substr(banco.NOMBRE, 1, Instr(banco.NOMBRE, '~', 2) - 2) " +
                "When Instr(banco.NOMBRE, '(') > 0 Then Substr(banco.NOMBRE, 1, Instr(banco.NOMBRE, '(') - 2) " +
                "Else banco.NOMBRE End || ' (...' || Substr(cb.IBAN, -4) || ')'");
        cuentaBancaria.FROM("CEN_CUENTASBANCARIAS cb");
        cuentaBancaria.FROM("CEN_BANCOS banco");
        cuentaBancaria.WHERE("cb.CBO_CODIGO = banco.CODIGO");
        cuentaBancaria.WHERE("cb.IDINSTITUCION = fa.IDINSTITUCION AND cb.IDPERSONA = fa.IDPERSONA AND cb.IDCUENTA = fa.IDCUENTA");


        SQL abonoBanco = new SQL();

        abonoBanco.SELECT("2 AS IDTABLA");
        abonoBanco.SELECT("ad.IDINSTITUCION");
        abonoBanco.SELECT("ad.IDDISQUETEABONO AS IDENTIFICADOR");
        abonoBanco.SELECT("NULL AS IDFACTURA");
        abonoBanco.SELECT("NULL AS NUMEROFACTURA");
        abonoBanco.SELECT("ad.IDABONO");
        abonoBanco.SELECT("NULL AS NUMEROABONO");
        abonoBanco.SELECT("ad.FECHAMODIFICACION AS FECHA");
        abonoBanco.SELECT("ad.FECHAMODIFICACION");
        abonoBanco.SELECT("F_SIGA_GETRECURSO_ETIQUETA('facturacion.abonosPagos.datosPagoAbono.abonoBanco', " + idioma + ") AS MODO");
        abonoBanco.SELECT("F_SIGA_GETRECURSO_ETIQUETA('general.literal.pagado', " + idioma + ") AS ESTADO");
        abonoBanco.SELECT("ad.IMPORTEABONADO AS IMPORTE");
        abonoBanco.SELECT("NULL AS IMPORTE_PENDIENTE");
        abonoBanco.SELECT("(" + cuentaBancaria.toString() + ") AS BANCO");
        abonoBanco.SELECT("NULL AS IDPAGOSJG");

        abonoBanco.FROM("FAC_ABONOINCLUIDOENDISQUETE ad");
        abonoBanco.FROM("FAC_ABONO fa");
        abonoBanco.WHERE("ad.IDINSTITUCION = " + idInstitucion + " AND ad.IDABONO = " + idAbono);
        abonoBanco.WHERE("ad.IDINSTITUCION = fa.IDINSTITUCION AND ad.IDABONO = fa.IDABONO");

        return abonoBanco.toString();
    }

    private String getEstadosPendienteBanco(String idAbono, Short idInstitucion, String idioma) {
        SQL cuentaBancaria = new SQL();

        cuentaBancaria.SELECT("Case When Instr(banco.NOMBRE, '~', 2) > 1 Then Substr(banco.NOMBRE, 1, Instr(banco.NOMBRE, '~', 2) - 2) " +
                    "When Instr(banco.NOMBRE, '(') > 0 Then Substr(banco.NOMBRE, 1, Instr(banco.NOMBRE, '(') - 2) " +
                    "Else banco.NOMBRE End || ' (...' || Substr(cb.IBAN, -4) || ')'");
        cuentaBancaria.FROM("CEN_CUENTASBANCARIAS cb");
        cuentaBancaria.FROM("CEN_BANCOS banco");
        cuentaBancaria.WHERE("cb.CBO_CODIGO = banco.CODIGO");
        cuentaBancaria.WHERE("cb.IDINSTITUCION = fa.IDINSTITUCION AND cb.IDPERSONA = fa.IDPERSONA AND cb.IDCUENTA = fa.IDCUENTA");


        SQL pendienteBanco = new SQL();

        pendienteBanco.SELECT("3 AS IDTABLA");
        pendienteBanco.SELECT("fa.IDINSTITUCION");
        pendienteBanco.SELECT("fa.IDPERSONA AS IDENTIFICADOR");
        pendienteBanco.SELECT("NULL AS IDFACTURA");
        pendienteBanco.SELECT("NULL AS NUMEROFACTURA");
        pendienteBanco.SELECT("fa.IDABONO");
        pendienteBanco.SELECT("NULL AS NUMEROABONO");
        pendienteBanco.SELECT("fa.FECHA");
        pendienteBanco.SELECT("fa.FECHAMODIFICACION");
        pendienteBanco.SELECT("F_SIGA_GETRECURSO_ETIQUETA('facturacion.abonosPagos.datosPagoAbono.abonoBanco', " + idioma + ") AS MODO");
        pendienteBanco.SELECT("F_SIGA_GETRECURSO_ETIQUETA('general.literal.pendienteabonobanco', " + idioma + ") AS ESTADO");
        pendienteBanco.SELECT("0 AS IMPORTE");
        pendienteBanco.SELECT("fa.IMPPENDIENTEPORABONAR AS IMPORTE_PENDIENTE");
        pendienteBanco.SELECT("(" + cuentaBancaria.toString() + ") AS BANCO");
        pendienteBanco.SELECT("fa.IDPAGOSJG");

        pendienteBanco.FROM("FAC_ABONO fa");
        pendienteBanco.WHERE("fa.IDINSTITUCION = " + idInstitucion + " AND fa.IDABONO = " + idAbono);
        pendienteBanco.WHERE("fa.IDCUENTA IS NOT NULL AND fa.IMPPENDIENTEPORABONAR > 0");

        return pendienteBanco.toString();
    }

    private String getEstadosPendienteCaja(String idAbono, Short idInstitucion, String idioma) {
        SQL pendienteCaja = new SQL();

        pendienteCaja.SELECT("3 AS IDTABLA");
        pendienteCaja.SELECT("fa.IDINSTITUCION");
        pendienteCaja.SELECT("fa.IDPERSONA AS IDENTIFICADOR");
        pendienteCaja.SELECT("NULL AS IDFACTURA");
        pendienteCaja.SELECT("NULL AS NUMEROFACTURA");
        pendienteCaja.SELECT("fa.IDABONO");
        pendienteCaja.SELECT("NULL AS NUMEROABONO");
        pendienteCaja.SELECT("fa.FECHA");
        pendienteCaja.SELECT("fa.FECHAMODIFICACION");
        pendienteCaja.SELECT("F_SIGA_GETRECURSO_ETIQUETA('facturacion.abonosPagos.datosPagoAbono.abonoCaja', " + idioma + ") AS MODO");
        pendienteCaja.SELECT("F_SIGA_GETRECURSO_ETIQUETA('general.literal.pendienteabonocaja', " + idioma + ") AS ESTADO");
        pendienteCaja.SELECT("0 AS IMPORTE");
        pendienteCaja.SELECT("fa.IMPPENDIENTEPORABONAR AS IMPORTE_PENDIENTE");
        pendienteCaja.SELECT("'' AS BANCO");
        pendienteCaja.SELECT("fa.IDPAGOSJG");

        pendienteCaja.FROM("FAC_ABONO fa");
        pendienteCaja.WHERE("fa.IDINSTITUCION = " + idInstitucion + " AND fa.IDABONO = " + idAbono);
        pendienteCaja.WHERE("fa.IDCUENTA IS NULL AND fa.IMPPENDIENTEPORABONAR > 0");

        return pendienteCaja.toString();
    }

    private String getEstadosEmisionPago(String idAbono, Short idInstitucion, String idioma) {
        SQL emisionPago = new SQL();

        emisionPago.SELECT("1 AS IDTABLA");
        emisionPago.SELECT("fa.IDINSTITUCION");
        emisionPago.SELECT("fa.IDPERSONA AS IDENTIFICADOR");
        emisionPago.SELECT("NULL AS IDFACTURA");
        emisionPago.SELECT("NULL AS NUMEROFACTURA");
        emisionPago.SELECT("fa.IDABONO");
        emisionPago.SELECT("fa.NUMEROABONO");
        emisionPago.SELECT("fa.FECHA");
        emisionPago.SELECT("fa.FECHAMODIFICACION");
        emisionPago.SELECT("INITCAP(F_SIGA_GETRECURSO_ETIQUETA('facturacion.pagosAbonos.accion.emisionPago', " + idioma + ")) AS MODO");
        emisionPago.SELECT("F_SIGA_GETRECURSO_ETIQUETA('facturacion.pagosAbonos.estado.revision', " + idioma + ") AS ESTADO");
        emisionPago.SELECT("0 AS IMPORTE");
        emisionPago.SELECT("fa.IMPTOTAL AS IMPORTE_PENDIENTE");
        emisionPago.SELECT("'' AS BANCO");
        emisionPago.SELECT("NULL AS IDPAGOSJG");

        emisionPago.FROM("FAC_ABONO fa");
        emisionPago.WHERE("fa.IDINSTITUCION = " + idInstitucion + " AND fa.IDABONO = " + idAbono);

        return emisionPago.toString();
    }

}