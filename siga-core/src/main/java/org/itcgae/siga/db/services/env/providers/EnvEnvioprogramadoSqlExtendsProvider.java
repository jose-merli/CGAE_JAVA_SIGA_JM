package org.itcgae.siga.db.services.env.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.EnvEnvioprogramadoSqlProvider;

import java.util.List;

public class EnvEnvioprogramadoSqlExtendsProvider extends EnvEnvioprogramadoSqlProvider {

    public String eliminarEnviosPago(Short idInstitucion, List<String> idPagos) {

        SQL subQuery = new SQL();
        subQuery.SELECT("IDINSTITUCION");
        subQuery.SELECT("IDENVIO");
        subQuery.FROM("ENV_PROGRAMPAGOS");
        subQuery.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery.WHERE("IDPAGO IN (" + String.join(",", idPagos) + ")");

        SQL query = new SQL();
        query.DELETE_FROM("ENV_ENVIOPROGRAMADO");
        query.WHERE("(IDINSTITUCION, IDENVIO) IN (" + subQuery.toString() + ")");

        return query.toString();
    }
}
