package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacEstadofacturaSqlProvider;

public class FactEstadosfacturaExtendsSqlProvider extends FacEstadofacturaSqlProvider {

    public String comboEstadosFacturas(String idioma) {
        SQL sql = new SQL();

        // Select
        sql.SELECT("ef.idestado, r.descripcion descripcion, 'FACTURA' AS tipo");

        // From
        sql.FROM("fac_estadofactura ef");

        //Inner join
        sql.INNER_JOIN("gen_recursos r ON (ef.descripcion = r.idrecurso AND r.idlenguaje = '" + idioma + "')");

        // Order by
        sql.ORDER_BY("r.descripcion");

        return sql.toString();
    }
}
