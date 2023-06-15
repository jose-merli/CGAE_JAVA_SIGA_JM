package org.itcgae.siga.db.services.exp.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ExpExpedienteSqlProvider;

public class ExpExpedienteSqlExtendsProvider extends ExpExpedienteSqlProvider {


    public String getExpedientesSigaColegiado(Short idInstitucion, String idPersona){
        SQL SQL = new SQL();
        SQL.SELECT("e.numeroexpediente",
                "e.anioexpediente as ANIOEXPEDIENTE",
                "t.idtipoexpediente AS IDTIPOEXPEDIENTE",
                "t.nombre                 AS TIPOEXPEDIENTE",
                "t.idinstitucion    AS IDINSTITUCIONTIPOEXPEDIENTE",
                "es.nombre as estadoexpediente",
                "TO_CHAR(e.fecha,'DD/MM/YYYY HH24:MI') as fechaapertura",
                "'Interesado' as relacion"); //Esta query es para traer expresamente los expedientes en los que el colegial es el denunciado, por lo que la relación sera siempre denunciado
        SQL.FROM("exp_denunciado      d",
                "exp_expediente      e",
                "exp_tipoexpediente  t",
                "exp_estado          es");
        SQL.WHERE("d.idinstitucion_tipoexpediente = t.idinstitucion",
                "d.idtipoexpediente = t.idtipoexpediente",
                "d.idinstitucion = e.idinstitucion",
                "d.idinstitucion_tipoexpediente = e.idinstitucion_tipoexpediente",
                "d.idtipoexpediente = e.idtipoexpediente",
                "d.numeroexpediente = e.numeroexpediente",
                "d.anioexpediente = e.anioexpediente",
                "e.idinstitucion = es.idinstitucion",
                "e.idtipoexpediente = es.idtipoexpediente",
                "e.idfase = es.idfase",
                "e.idestado = es.idestado",
                "d.idinstitucion = " + idInstitucion,
                "d.idpersona = '"+idPersona+"'",
                "e.esvisibleenficha = 'S'"
                );
        return SQL.toString();
    }
}
