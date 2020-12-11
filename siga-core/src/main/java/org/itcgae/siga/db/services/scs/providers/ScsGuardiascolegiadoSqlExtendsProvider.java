package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsCabeceraguardiasSqlProvider;

public class ScsGuardiascolegiadoSqlExtendsProvider extends ScsCabeceraguardiasSqlProvider{

    public String getTurnosGuardias(String idPersona, Short idInstitucion) {
        SQL sql = new SQL();
        
        sql.SELECT("COUNT(1) AS COUNT");
        sql.FROM("scs_inscripcionturno");
        sql.WHERE("idinstitucion = " + idInstitucion);
        sql.WHERE("idpersona = " + idPersona);
        sql.WHERE("fechabaja IS NULL");
        sql.WHERE("fechavalidacion IS NOT NULL");
        
        return sql.toString();
    }
}

