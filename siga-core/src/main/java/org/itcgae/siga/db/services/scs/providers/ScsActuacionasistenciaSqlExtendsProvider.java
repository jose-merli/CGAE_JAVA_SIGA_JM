package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;

public class ScsActuacionasistenciaSqlExtendsProvider {


    public String comboCosteFijoTipoActuacion(String idTipoActuacion, Short idInstitucion, Short idTipoAsistencia, Integer idLenguaje){

        SQL SQL = new SQL();
        SQL.SELECT("tipo.IDCOSTEFIJO AS ID",
                "f_siga_getrecurso (coste.DESCRIPCION,"+idLenguaje+") AS DESCRIPCION");
        SQL.FROM("SCS_TIPOACTUACIONCOSTEFIJO tipo");
        SQL.INNER_JOIN("SCS_COSTEFIJO coste ON tipo.IDINSTITUCION = coste.IDINSTITUCION AND tipo.IDCOSTEFIJO = coste.IDCOSTEFIJO");
        SQL.WHERE("tipo.IDTIPOACTUACION = '"+idTipoActuacion+"'",
                "tipo.IDINSTITUCION = "+idInstitucion,
                "tipo.IDTIPOASISTENCIA = "+idTipoAsistencia);
        SQL.ORDER_BY("DESCRIPCION");

        return SQL.toString();
    }

    public String comboTipoActuacion(Short idInstitucion, Short idTipoAsistencia, Integer idLenguaje){

        SQL SQL = new SQL();
        SQL.SELECT("ta.idtipoactuacion as id",
                "f_siga_getrecurso(ta.descripcion , '"+idLenguaje+"') as descripcion");
        SQL.FROM("scs_tipoactuacion ta");
        SQL.WHERE("ta.idinstitucion = "+idInstitucion,
                "ta.idtipoasistencia = "+idTipoAsistencia,
                "ta.fechabaja is null");

        return SQL.toString();
    }
    
    public String getNewIdActuacion(Short idInstitucion, String anioNumero){
        SQL sql = new SQL();

        sql.SELECT("(MAX(IDACTUACION) + 1) AS IDACTUACION");
        sql.FROM("SCS_ACTUACIONASISTENCIA");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'",
                "ANIO = '"+anioNumero.split("/")[0]+"'",
                "NUMERO = '"+anioNumero.split("/")[1]+"'");

        return sql.toString();
    }
    
    public String controlCheckDiaDespues(Short idInstitucion, String idTurno, String idGuardia) {
        
    	SQL sql = new SQL();
        sql.SELECT("count(*)");
        sql.FROM("scs_hitofacturableguardia");
        sql.WHERE("idhito IN (9,25)");
        sql.WHERE("idInstitucion = " + idInstitucion);
        sql.WHERE("idturno = " + idTurno);
        sql.WHERE("idguardia = " + idGuardia);
    	
    	return sql.toString();
    }
}
