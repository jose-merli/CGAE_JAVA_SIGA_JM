package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacAbonoincluidoendisqueteSqlProvider;

import java.util.List;
import java.util.stream.Collectors;

public class FacAbonoincluidoendisqueteSqlExtendsProvider extends FacAbonoincluidoendisqueteSqlProvider {

    public String getDisquetesPorAbonos(Short idInstitucion, List<Long> idAbonos) {

        SQL sql = new SQL();
        sql.SELECT("IDDISQUETEABONO");
        sql.FROM("FAC_ABONOINCLUIDOENDISQUETE");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDABONO IN (" + idAbonos.stream().map(a -> a.toString()).collect(Collectors.joining(",")) + ")");
        sql.GROUP_BY("IDDISQUETEABONO");

        return sql.toString();
    }

    public String getRestoPagosDisquete(Short idInstitucion, Long idDisqueteAbono, Integer idPago) {

        SQL subQuery = new SQL();
        subQuery.SELECT("IDABONO");
        subQuery.FROM("FAC_ABONO");
        subQuery.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery.WHERE("IDPAGOSJG = " + idPago);

        SQL query = new SQL();
        query.SELECT("FA2.IDPAGOSJG");
        query.FROM("FAC_ABONOINCLUIDOENDISQUETE FA");
        query.JOIN("FAC_ABONO FA2 ON FA.IDINSTITUCION = FA2.IDINSTITUCION AND FA.IDABONO = FA2.IDABONO");
        query.WHERE("FA.IDINSTITUCION = " + idInstitucion);
        query.WHERE("FA.IDDISQUETEABONO = " + idDisqueteAbono);
        query.WHERE("FA.IDABONO NOT IN ( " + subQuery.toString() + " )");
        query.GROUP_BY("FA2.IDPAGOSJG");


        return query.toString();
    }

}
