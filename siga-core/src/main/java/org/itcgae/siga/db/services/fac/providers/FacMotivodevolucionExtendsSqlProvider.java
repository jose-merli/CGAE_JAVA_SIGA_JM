package org.itcgae.siga.db.services.fac.providers;


import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacMotivodevolucionSqlProvider;

public class FacMotivodevolucionExtendsSqlProvider extends FacMotivodevolucionSqlProvider {

    public String comboMotivosDevolucion(String idLenguaje) {
        SQL sql = new SQL();

        // Select
        sql.SELECT("CODIGO id,F_SIGA_GETRECURSO(NOMBRE,"+idLenguaje+") descripcion");

        // From
        sql.FROM("FAC_MOTIVODEVOLUCION fm");

        return sql.toString();
    }
}
