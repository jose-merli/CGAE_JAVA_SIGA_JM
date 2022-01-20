package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacEstadoconfirmfactSqlProvider;

public class FacEstadoconfirmfactExtendsSqlProvider extends FacEstadoconfirmfactSqlProvider {

    public String comboEstados(String tipo, String idioma) {
        SQL sql = new SQL();

        // Select
        sql.SELECT("f.idestado");
        sql.SELECT("r.descripcion");

        // From
        sql.FROM("fac_estadoconfirmfact f");
        sql.FROM("gen_recursos r");

        // Where
        sql.WHERE("f.descripcion = r.idrecurso");
        sql.WHERE("r.idlenguaje = '" + idioma + "'");
        sql.WHERE("f.tipo = '" + tipo + "'");

        // Order by
        sql.ORDER_BY("r.descripcion");

        return sql.toString();
    }

}
