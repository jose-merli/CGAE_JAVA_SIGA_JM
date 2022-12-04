package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsConfConjuntoGuardiasSqlProvider;

public class ScsConfConjuntoGuardiasSqlExtendsProvider extends ScsConfConjuntoGuardiasSqlProvider {

    public String searchGuardiasFromLista(String idLista, Short idInstitucion, Integer tamMax) {
        SQL SQL_PADRE = new SQL();
        SQL SQL = new SQL();
        SQL.SELECT("ccg.idconjuntoguardia", "ccg.idguardia", "gt.nombre nombreguardia", "ccg.idturno", "t.nombre nombreturno", "ccg.orden", "CASE WHEN  'LMXJVSD' LIKE CONCAT('%',CONCAT(SELECCIONLABORABLES,'%')) AND LENGTH(SELECCIONLABORABLES)>1 AND SUBSTR(SELECCIONLABORABLES,1,1) = 'L' AND SUBSTR(SELECCIONLABORABLES,LENGTH(SELECCIONLABORABLES),1) IN ('V','S','D') THEN  (CONCAT(CONCAT(SUBSTR(SELECCIONLABORABLES,1,1), '-'), SUBSTR(SELECCIONLABORABLES,LENGTH(SELECCIONLABORABLES),1))) WHEN LENGTH(SELECCIONLABORABLES) = 1 THEN SELECCIONLABORABLES ELSE SELECCIONLABORABLES END AS diaslaborables", "CASE WHEN  'LMXJVSD' LIKE CONCAT('%',CONCAT(SELECCIONFESTIVOS,'%')) AND LENGTH(SELECCIONFESTIVOS)>1  AND SUBSTR(SELECCIONFESTIVOS,1,1) = 'L' AND SUBSTR(SELECCIONFESTIVOS,LENGTH(SELECCIONFESTIVOS),1) IN ('V','S','D') THEN (CONCAT(CONCAT(SUBSTR(SELECCIONFESTIVOS,1,1), '-'), SUBSTR(SELECCIONFESTIVOS,LENGTH(SELECCIONFESTIVOS),1))) WHEN LENGTH(SELECCIONFESTIVOS) = 1 THEN SELECCIONFESTIVOS ELSE SELECCIONFESTIVOS END AS diasfestivos");
        SQL.FROM("scs_conf_conjunto_guardias ccg");
        SQL.INNER_JOIN("scs_guardiasturno gt on gt.idguardia = ccg.idguardia and gt.idinstitucion = ccg.idinstitucion and gt.idturno = ccg.idturno", "scs_turno t on t.idturno = ccg.idturno and t.idinstitucion = ccg.idinstitucion");
        SQL.WHERE("ccg.idinstitucion =" + idInstitucion, "ccg.idconjuntoguardia='" + idLista + "'","gt.FECHABAJA IS NULL");
        SQL.ORDER_BY("ccg.orden desc");
        SQL_PADRE.SELECT(" *");
        SQL_PADRE.FROM("( " + SQL.toString() + " )");
        if (tamMax != null && tamMax > 0) {
            tamMax = tamMax + 1;
            SQL_PADRE.WHERE(" ROWNUM <= " + tamMax);
        }

        return SQL_PADRE.toString();
    }
}
