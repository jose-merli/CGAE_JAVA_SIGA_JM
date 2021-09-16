package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacHistoricofacturaSqlProvider;

public class FacHistoricofacturaSqlExtendsProvider extends FacHistoricofacturaSqlProvider {

    public String insertarHistoricoFacParametros(String idInstitucion, String idFactura, Integer idTipoAccion,
                                                 Integer idPagoPorCaja, Integer idDisqueteCargos, Integer idFacturaIncluidaEnDisquete,
                                                 Integer idDisqueteDevoluciones, String idRecibo, Integer idRenegociacion, Integer idAbono, String comisionIdFactura) {

        String cadena = "";

        if (null != idPagoPorCaja && idPagoPorCaja > 0) {
            cadena += ", IDPAGOPORCAJA";
        }

        if (null != idDisqueteCargos && idDisqueteCargos > 0) {
            cadena += ", IDDISQUETECARGOS";
        }

        if (null != idFacturaIncluidaEnDisquete && idFacturaIncluidaEnDisquete > 0) {
            cadena += ", IDFACTURAINCLUIDAENDISQUETE";
        }

        if (null != idDisqueteDevoluciones && idDisqueteDevoluciones > 0) {
            cadena += ", IDDISQUETEDEVOLUCIONES";
        }

        if (null != idRecibo && !"".equalsIgnoreCase(idRecibo)) {
            cadena += ", IDRECIBO";
        }

        if (null != idRenegociacion && idRenegociacion > 0) {
            cadena += ", IDRENEGOCIACION";
        }

        if (null != idAbono && idAbono > 0) {
            cadena += ", IDABONO";
        }

        if (null != comisionIdFactura && !"".equalsIgnoreCase(comisionIdFactura)) {
            cadena += ", COMISIONIDFACTURA";
        }

        SQL sql = new SQL();
        sql.INSERT_INTO("FAC_HISTORICOFACTURA" +
                " (IDINSTITUCION,IDFACTURA, IDHISTORICO,FECHAMODIFICACION, USUMODIFICACION, IDTIPOACCION," +
                " IDFORMAPAGO, IDPERSONA, IDCUENTA, IDPERSONADEUDOR, IDCUENTADEUDOR, IMPTOTALANTICIPADO," +
                " IMPTOTALPAGADOPORCAJA, IMPTOTALPAGADOSOLOCAJA, IMPTOTALPAGADOSOLOTARJETA, IMPTOTALPAGADOPORBANCO," +
                " IMPTOTALPAGADO, IMPTOTALPORPAGAR, IMPTOTALCOMPENSADO, ESTADO" + cadena + ")");


        SQL sql2 = new SQL();
        sql2.SELECT("IDINSTITUCION");
        sql2.SELECT("IDFACTURA");
        SQL subQuery = new SQL();
        subQuery.SELECT("MAX(HIS2.IDHISTORICO)");
        subQuery.FROM("FAC_HISTORICOFACTURA HIS2");
        subQuery.WHERE("HIS2.IDINSTITUCION = FAC_FACTURA.IDINSTITUCION");
        subQuery.WHERE("HIS2.IDFACTURA = FAC_FACTURA.IDFACTURA");
        sql2.SELECT("NVL(" + subQuery.toString() + ", 0) + 1");
        sql2.SELECT("SYSDATE");
        sql2.SELECT("USUMODIFICACION");
        sql2.SELECT(idTipoAccion.toString());
        sql2.SELECT("IDFORMAPAGO");
        sql2.SELECT("IDPERSONA");
        sql2.SELECT("IDCUENTA");
        sql2.SELECT("IDPERSONADEUDOR");
        sql2.SELECT("IDCUENTADEUDOR");
        sql2.SELECT("IMPTOTALANTICIPADO");
        sql2.SELECT("IMPTOTALPAGADOPORCAJA");
        sql2.SELECT("IMPTOTALPAGADOSOLOCAJA");
        sql2.SELECT("IMPTOTALPAGADOSOLOTARJETA");
        sql2.SELECT("IMPTOTALPAGADOPORBANCO");
        sql2.SELECT("IMPTOTALPAGADO");
        sql2.SELECT("IMPTOTALPORPAGAR");
        sql2.SELECT("IMPTOTALCOMPENSADO");
        sql2.SELECT("ESTADO");

        if (null != idPagoPorCaja && idPagoPorCaja > 0) {
            sql2.SELECT("IDPAGOPORCAJA");
        }

        if (null != idDisqueteCargos && idDisqueteCargos > 0) {
            sql2.SELECT("IDDISQUETECARGOS");
        }

        if (null != idFacturaIncluidaEnDisquete && idFacturaIncluidaEnDisquete > 0) {
            sql2.SELECT("IDFACTURAINCLUIDAENDISQUETE");
        }

        if (null != idDisqueteDevoluciones && idDisqueteDevoluciones > 0) {
            sql2.SELECT("IDDISQUETEDEVOLUCIONES");
        }

        if (null != idRecibo && !"".equalsIgnoreCase(idRecibo)) {
            sql2.SELECT("IDRECIBO");
        }

        if (null != idRenegociacion && idRenegociacion > 0) {
            sql2.SELECT("IDRENEGOCIACION");
        }

        if (null != idAbono && idAbono > 0) {
            sql2.SELECT("IDABONO");
        }

        if (null != comisionIdFactura && !"".equalsIgnoreCase(comisionIdFactura)) {
            sql2.SELECT("COMISIONIDFACTURA");
        }

        sql2.FROM("FAC_FACTURA");
        sql2.WHERE("IDINSTITUCION = " + idInstitucion);
        sql2.WHERE("IDFACTURA = " + idFactura);

        return sql.toString().concat(sql2.toString());
    }

}
