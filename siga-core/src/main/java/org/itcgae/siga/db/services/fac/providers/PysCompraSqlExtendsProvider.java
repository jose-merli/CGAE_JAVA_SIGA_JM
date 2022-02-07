package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.PysCompraSqlProvider;

public class PysCompraSqlExtendsProvider extends PysCompraSqlProvider {

    private SQL getCamposPysCompra(String alias) {

        if (alias == null) {
            alias = "";
        }

        SQL sql = new SQL();
        sql.SELECT(alias + "ACEPTADO");
        sql.SELECT(alias + "DESCRIPCION");
        sql.SELECT(alias + "FECHABAJA");
        sql.SELECT(alias + "FECHAMODIFICACION");
        sql.SELECT(alias + "IDFORMAPAGO");
        sql.SELECT(alias + "IDPETICION");
        sql.SELECT(alias + "IDPRODUCTOINSTITUCION");
        sql.SELECT(alias + "IMPORTEUNITARIO");
        sql.SELECT(alias + "NUMEROLINEA");
        sql.SELECT(alias + "IDCUENTA");
        sql.SELECT(alias + "IDCUENTADEUDOR");
        sql.SELECT(alias + "USUMODIFICACION");
        sql.SELECT(alias + "CANTIDAD");
        sql.SELECT(alias + "FECHA");
        sql.SELECT(alias + "IDFACTURA");
        sql.SELECT(alias + "IDINSTITUCION");
        sql.SELECT(alias + "IDPRODUCTO");
        sql.SELECT(alias + "IDTIPOPRODUCTO");
        sql.SELECT(alias + "IMPORTEANTICIPADO");
        sql.SELECT(alias + "IDTIPOIVA");
        sql.SELECT(alias + "IDPERSONA");
        sql.SELECT(alias + "IDPERSONADEUDOR");
        sql.SELECT(alias + "NOFACTURABLE");
        return sql;
    }

    public String obtenerComprasPeticion(String idInstitucion, String idPeticion) {
        SQL sql = getCamposPysCompra("C.");
        sql.FROM("PYS_COMPRA C, PYS_PRODUCTOSSOLICITADOS P");
        sql.WHERE("C.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("C.IDPETICION = " + idPeticion);
        sql.WHERE("P.IDINSTITUCION = C.IDINSTITUCION");
        sql.WHERE("P.IDPETICION = C.IDPETICION");
        sql.WHERE("P.IDPRODUCTO = C.IDPRODUCTO");
        sql.WHERE("P.IDTIPOPRODUCTO = C.IDTIPOPRODUCTO");
        sql.WHERE("P.IDPRODUCTOINSTITUCION = C.IDPRODUCTOINSTITUCION");
        sql.ORDER_BY("C.IDINSTITUCION");
        sql.ORDER_BY("C.IDPETICION");
        sql.ORDER_BY("P.ORDEN");
        sql.ORDER_BY("C.IDTIPOPRODUCTO");
        sql.ORDER_BY("C.IDPRODUCTO");
        sql.ORDER_BY("C.IDPRODUCTOINSTITUCION");
        return sql.toString();
    }

    public String lockTable() {
        String sql = "lock table PYS_COMPRA in exclusive mode";
        return sql;
    }

    public String obtenerCompraCertificado(String idInstitucion, String idSolicitudCertificado) {
        SQL sql = getCamposPysCompra("C.");
        sql.FROM("PYS_COMPRA C, CER_SOLICITUDCERTIFICADOS CER");
        sql.WHERE("C.IDINSTITUCION = CER.IDINSTITUCION");
        sql.WHERE("C.IDPETICION = CER.IDPETICIONPRODUCTO");
        sql.WHERE("C.IDTIPOPRODUCTO = CER.PPN_IDTIPOPRODUCTO");
        sql.WHERE("C.IDPRODUCTO = CER.PPN_IDPRODUCTO");
        sql.WHERE("C.IDPRODUCTOINSTITUCION = CER.PPN_IDPRODUCTOINSTITUCION");
        sql.WHERE("CER.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("CER.IDSOLICITUD = " + idSolicitudCertificado);
        return sql.toString();
    }

}
