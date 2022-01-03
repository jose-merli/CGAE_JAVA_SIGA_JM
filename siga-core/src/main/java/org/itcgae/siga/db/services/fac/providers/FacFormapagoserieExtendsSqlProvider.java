package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacFormapagoserieSqlProvider;

public class FacFormapagoserieExtendsSqlProvider extends FacFormapagoserieSqlProvider {

    public String getFormasPagosSerie(String idSerieFacturacion, Short idInstitucion, String idioma) {
        SQL sql = new SQL();

        // Select
        sql.SELECT_DISTINCT("fp.idformapago");
        sql.SELECT_DISTINCT("f_siga_getrecurso( fp.descripcion, '" + idioma + "' ) descripcion");

        // From
        sql.FROM("fac_formapagoserie fps");

        // Joins
        sql.INNER_JOIN("pys_formapago fp ON ( fps.idformapago = fp.idformapago )");

        // Where
        sql.WHERE("fps.idinstitucion = " + idInstitucion);
        sql.WHERE("fps.idseriefacturacion = '" + idSerieFacturacion + "'");

        // Order by
        sql.ORDER_BY("fp.idformapago");

        return sql.toString();
    }

}
