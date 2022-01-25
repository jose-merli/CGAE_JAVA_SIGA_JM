package org.itcgae.siga.db.mappers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.FcsHistoricoAcreditacionExample;

import java.util.List;

public class FcsHistoricoAcreditacionSqlExtendsProvider {

        public String deleteByAcreditacion(String idInstitucion, String idFacturacion, List<String> acreditaciones) {
            SQL sql = new SQL();
            sql.DELETE_FROM("FCS_HISTORICO_ACREDITACION");
            sql.WHERE(" IDACREDITACION IN (" + acreditaciones.toString() + ")");
            sql.WHERE("IDFACTURACION = " + idFacturacion );
            sql.WHERE("IDINSTITUCION = " + idInstitucion );
            return sql.toString();
        }

}
