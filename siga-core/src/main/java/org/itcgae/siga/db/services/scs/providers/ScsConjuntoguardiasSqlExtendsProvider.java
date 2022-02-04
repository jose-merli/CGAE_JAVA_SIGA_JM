package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.ListaGuardiasItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsConjuntoguardiasSqlProvider;

public class ScsConjuntoguardiasSqlExtendsProvider extends ScsConjuntoguardiasSqlProvider {

    public String searchListaGuardias(ListaGuardiasItem filtro, Short idInstitucion, Integer tamMax) {
        SQL SQL = new SQL();
        SQL SQL_PADRE = new SQL();
            SQL.SELECT_DISTINCT("cg.idconjuntoguardia idlista", "cg.descripcion nombre", "cg.lugar lugar", "cg.tipo idTipo", "CASE WHEN cg.tipo is null THEN 'Para ambos' WHEN cg.tipo = 1 THEN 'Para comunicación o descarga' WHEN cg.tipo = 2 THEN 'Para generación' END tipo", "cg.observaciones");
            SQL.FROM("scs_conjuntoguardias cg");
            SQL.WHERE("cg.idinstitucion=" + idInstitucion);
            if (!UtilidadesString.esCadenaVacia(filtro.getIdTipo())) {
            SQL.WHERE("cg.tipo='" + filtro.getIdTipo() + "'");
        }

            if (!UtilidadesString.esCadenaVacia(filtro.getLugar())) {
            SQL.WHERE("UPPER(cg.lugar) LIKE UPPER('%" + filtro.getLugar() + "%')");
        }

            if (!UtilidadesString.esCadenaVacia(filtro.getNombre())) {
            SQL.WHERE("UPPER(cg.descripcion) LIKE UPPER('%" + filtro.getNombre() + "%')");
        }

            if (!UtilidadesString.esCadenaVacia(filtro.getIdGrupoZona())) {
            SQL.INNER_JOIN("scs_conf_conjunto_guardias ccg on cg.idconjuntoguardia = ccg.idconjuntoguardia and cg.idinstitucion = ccg.idinstitucion", "scs_guardiasturno g on g.idguardia = ccg.idguardia and g.idinstitucion = ccg.idinstitucion", "scs_turno t on t.idturno = g.idturno and t.idinstitucion = g.idinstitucion");
            SQL.WHERE("t.idzona IN (" + filtro.getIdGrupoZona() + ")");
            if (!UtilidadesString.esCadenaVacia(filtro.getIdZona())) {
                SQL.WHERE("t.idsubzona IN (" + filtro.getIdZona() + ")");
            }
        }

            SQL_PADRE.SELECT(" *");
            SQL_PADRE.FROM("( " + SQL.toString() + " )");
            if (tamMax != null && tamMax > 0) {
            tamMax = tamMax + 1;
            SQL_PADRE.WHERE(" ROWNUM <= " + tamMax);
        }

            return SQL_PADRE.toString();
    }

    public String getNextIdLista(Short idInstitucion) {
        SQL sql = new SQL();
        sql.SELECT("nvl(MAX(idconjuntoguardia)+1,1) ID");
        sql.FROM("scs_conjuntoguardias");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
        return sql.toString();
    }
}
