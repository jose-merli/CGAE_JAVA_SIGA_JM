package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FicherosAbonosItem;
import org.itcgae.siga.db.mappers.FacDisqueteabonosSqlProvider;

import java.text.SimpleDateFormat;


public class FacDisqueteabonosExtendsSqlProvider extends FacDisqueteabonosSqlProvider {

    public String getFicherosTransferencias(FicherosAbonosItem item, String idInstitucion, String idioma) {

        SQL principal = new SQL();
        SQL totalRemesa = new SQL();
        SQL numRecibos = new SQL();
        SQL sql = new SQL();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha;

        //sumatorio de remesas
        totalRemesa.SELECT("SUM (importeabonado)");
        totalRemesa.FROM("fac_abonoincluidoendisquete");
        totalRemesa.WHERE("idinstitucion = c.idinstitucion AND iddisqueteabono = c.iddisqueteabono");

        //sumatorio numero de recibos
        numRecibos.SELECT("COUNT (1)");
        numRecibos.FROM("fac_abonoincluidoendisquete");
        numRecibos.WHERE("idinstitucion = c.idinstitucion AND iddisqueteabono = c.iddisqueteabono");

        principal.SELECT("c.idinstitucion,c.iddisqueteabono, c.fecha, b.bancos_codigo, b.DESCRIPCION CUENTA_ENTIDAD, c.nombrefichero, c.fcs, "
                + "c.idsufijo,( s.sufijo || ' - ' || s.concepto ) sufijo, ("+totalRemesa.toString()+") AS totalimporte, ("+numRecibos.toString()+") AS numfacturas");
        principal.SELECT("(SELECT fp.CODIGO || ' ' || f_siga_getrecurso(fp.NOMBRE, " + idioma + ") FROM FAC_PROPOSITOS fp WHERE fp.IDPROPOSITO = idpropsepa) propSEPA");
        principal.SELECT("(SELECT fp.CODIGO || ' ' || f_siga_getrecurso(fp.NOMBRE, 1) FROM FAC_PROPOSITOS fp WHERE fp.IDPROPOSITO = idpropotros) propOtros");

        principal.FROM("fac_disqueteabonos c");
        principal.INNER_JOIN("fac_bancoinstitucion b ON (c.idinstitucion=b.idinstitucion AND c.bancos_codigo=b.bancos_codigo)");
        principal.LEFT_OUTER_JOIN("fac_sufijo s ON (s.idinstitucion=c.idinstitucion AND s.idsufijo=c.idsufijo)");

        principal.WHERE("c.idinstitucion="+idInstitucion);

        if (item.getFcs() != null && item.getFcs()) {
            principal.WHERE("c.fcs='1'");
        } else {
            principal.WHERE("c.fcs='0'");
        }

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
            sql.WHERE("totalimporte>=to_number("+item.getImporteTotalDesde()+",'99999999999999999.99')");
        }

        //importe total hasta
        if(item.getImporteTotalHasta()!=null && !item.getImporteTotalHasta().isEmpty()) {
            sql.WHERE("totalimporte<=to_number("+item.getImporteTotalHasta()+",'99999999999999999.99')");
        }

        //numrecibos desde
        if(item.getNumRecibosDesde()!=null && !item.getNumRecibosDesde().isEmpty()) {
            sql.WHERE("numfacturas >= "+item.getNumRecibosDesde());
        }

        //numrecibos hasta
        if(item.getNumRecibosHasta()!=null && !item.getNumRecibosHasta().isEmpty()) {
            sql.WHERE("numfacturas <= "+item.getNumRecibosHasta());
        }

        return sql.toString();
    }

    public String getFacturasIncluidas(String idFichero, String idInstitucion, String idIdioma) {

        SQL sql = new SQL();

        sql.SELECT("gr.DESCRIPCION ESTADO,"
                +"CASE WHEN ff.IDCUENTA IS NOT NULL THEN F_SIGA_GETRECURSO(540020,"+idIdioma+") WHEN ff.IDCUENTA IS NULL THEN F_SIGA_GETRECURSO(540030,"+idIdioma+") END FORMAPAGO,"
                + "COUNT(*) NUMEROFACTURAS, SUM(ff.IMPTOTAL) IMPORTETOTAL, SUM(ff.IMPPENDIENTEPORABONAR) PENDIENTETOTAL");
        sql.FROM("FAC_ABONO ff");
        sql.INNER_JOIN("FAC_ABONOINCLUIDOENDISQUETE ff2 ON (ff.IDINSTITUCION = ff2.IDINSTITUCION AND ff.IDABONO = ff2.IDABONO)");
        sql.LEFT_OUTER_JOIN("FAC_ESTADOFACTURA fe ON (ff.ESTADO = fe.IDESTADO)");
        sql.LEFT_OUTER_JOIN("GEN_RECURSOS gr ON (gr.IDLENGUAJE = "+idIdioma+" AND fe.DESCRIPCION = gr.IDRECURSO)");
        sql.WHERE("ff.IDINSTITUCION = "+idInstitucion);
        sql.WHERE("ff2.IDDISQUETEABONO ="+idFichero);
        sql.GROUP_BY("gr.DESCRIPCION,ff.IDCUENTA");

        return sql.toString();
    }

    public String getNextIdDisqueteAbono(Short idInstitucion) {
        SQL sql = new SQL();

        sql.SELECT("(NVL(MAX(da.iddisqueteabono),0) + 1)");
        sql.FROM("fac_disqueteabonos da");
        sql.WHERE("da.idinstitucion = " + idInstitucion);

        return sql.toString();
    }
}