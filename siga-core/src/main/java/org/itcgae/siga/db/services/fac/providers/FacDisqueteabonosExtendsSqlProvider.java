package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FicherosAbonosItem;
import org.itcgae.siga.db.mappers.FacDisqueteabonosSqlProvider;

import java.text.SimpleDateFormat;


public class FacDisqueteabonosExtendsSqlProvider extends FacDisqueteabonosSqlProvider {

    public String getFicherosTransferencias(FicherosAbonosItem item, String idInstitucion) {

        SQL principal = new SQL();
        SQL totalRemesa = new SQL();
        SQL numRecibos = new SQL();
        SQL sql = new SQL();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha;

        //sumatorio de remesas
        totalRemesa.SELECT("SUM (importeabonado)");
        totalRemesa.FROM("fac_abonoincluidoendisquete fi");
        totalRemesa.WHERE("fi.idinstitucion = c.idinstitucion AND fi.iddisqueteabono = c.iddisqueteabono");

        //sumatorio numero de recibos
        numRecibos.SELECT("COUNT (1)");
        numRecibos.FROM("fac_abonoincluidoendisquete fi");
        numRecibos.WHERE("fi.idinstitucion = c.idinstitucion AND fi.iddisqueteabono = c.iddisqueteabono");

        principal.SELECT("c.idinstitucion,c.iddisqueteabono, c.fecha, b.cod_banco, b.comisiondescripcion || ' (...' || SUBSTR(b.iban, -4) || ')' CUENTA_ENTIDAD, c.nombrefichero,"
                + "c.fechamodificacion, c.idsufijo,( s.sufijo || ' - ' || s.concepto ) sufijo, ("+totalRemesa.toString()+") AS totalimporte, ("+numRecibos.toString()+") AS numfacturas");


        principal.FROM("fac_disqueteabonos c");
        principal.INNER_JOIN("fac_bancoinstitucion b ON (c.idinstitucion=b.idinstitucion AND c.bancos_codigo=b.bancos_codigo)");
        principal.LEFT_OUTER_JOIN("fac_sufijo s ON (s.idinstitucion=c.idinstitucion AND s.idsufijo=c.idsufijo)");

        principal.WHERE("c.idinstitucion="+idInstitucion);
        principal.WHERE("c.fcs='0'");

        //CUENTA BANCARIA
        if(item.getBancosCodigo()!=null) {
            principal.WHERE("b.bancos_codigo="+item.getBancosCodigo());
        }

        //sufijo
        if(item.getIdSufijo()!=null) {
            principal.WHERE("c.idSufijo="+item.getIdSufijo());
        }

        //fecha creacion desde
        if(item.getFechaCreacionDesde()!=null) {
            fecha = dateFormat.format(item.getFechaCreacionDesde());
            principal.WHERE("c.FECHA >= TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
        }

        //fecha creacion hasta
        if(item.getFechaCreacionHasta()!=null) {
            fecha = dateFormat.format(item.getFechaCreacionHasta());
            principal.WHERE("c.FECHA <= TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
        }

        principal.ORDER_BY("c.IDDISQUETEABONO DESC");

        sql.SELECT("*");
        sql.FROM("("+principal.toString()+")");
        sql.WHERE("ROWNUM < 201");

        //importe total desde
        if(item.getImporteTotalDesde()!=null && !item.getImporteTotalDesde().isEmpty()) {
            sql.WHERE("total_remesa>=to_number("+item.getImporteTotalDesde()+",'99999999999999999.99')");
        }

        //importe total hasta
        if(item.getImporteTotalHasta()!=null && !item.getImporteTotalHasta().isEmpty()) {
            sql.WHERE("total_remesa<=to_number("+item.getImporteTotalHasta()+",'99999999999999999.99')");
        }

        //numrecibos desde
        if(item.getNumRecibosDesde()!=null && !item.getNumRecibosDesde().isEmpty()) {
            sql.WHERE("numRecibos >= "+item.getNumRecibosDesde());
        }

        //numrecibos hasta
        if(item.getNumRecibosHasta()!=null && !item.getNumRecibosHasta().isEmpty()) {
            sql.WHERE("numRecibos <= "+item.getNumRecibosHasta());
        }

        return sql.toString();
    }
}