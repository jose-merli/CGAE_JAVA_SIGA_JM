package org.itcgae.siga.db.services.fac.providers;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacLineaabonoSqlProvider;


public class FacLineaabonoExtendsSqlProvider extends FacLineaabonoSqlProvider {

    public String getLineasAbono(String idFactura, String idInstitucion, Integer tamMaximo) {

        SQL query = new SQL();
        SQL sqlFinal = new SQL();

        query.SELECT("IDABONO,DESCRIPCIONLINEA,NUMEROLINEA,PRECIOUNITARIO,CANTIDAD,(PRECIOUNITARIO * CANTIDAD) importeNeto,"
                + "((PRECIOUNITARIO * CANTIDAD) * (IVA / 100)) importeIVA,((PRECIOUNITARIO * CANTIDAD) * (1 + IVA / 100))importeTotal");

        query.FROM("FAC_LINEAABONO fl");

        query.WHERE("IDINSTITUCION =" + idInstitucion);
        query.WHERE("IDABONO =" + idFactura);
        
        sqlFinal.SELECT("*");
        sqlFinal.FROM("(" + query.toString() + ")");
        if(tamMaximo!=null) {
        	sqlFinal.WHERE("ROWNUM <= " + tamMaximo);
        }
        return sqlFinal.toString();
    }
    
    public String getNuevoID(String idInstitucion, String idAbono) {

        SQL sql = new SQL();
        sql.SELECT("(NVL(MAX(NUMEROLINEA) + 1 , 1)) AS NUMEROLINEA");
        sql.FROM("FAC_LINEAABONO");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("IDABONO = '" + idAbono + "'");

        return sql.toString();
    }

    public String deleteDeshacerCierre(Short idInstitucion, List<Integer> idPagos) {

        SQL subQuery = new SQL();
        subQuery.SELECT("IDABONO");
        subQuery.FROM("FAC_ABONO");
        subQuery.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery.WHERE("IDPAGOSJG IN (" + idPagos.stream().map(a -> a.toString()).collect(Collectors.joining(",")) + ")");

        SQL query = new SQL();
        query.DELETE_FROM("FAC_LINEAABONO");
        query.WHERE("IDINSTITUCION = " + idInstitucion);
        query.WHERE("IDABONO IN ( " + subQuery.toString() + " )");

        return query.toString();
    }
}
