package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacLineaabonoSqlProvider;


public class FacLineaabonoExtendsSqlProvider extends FacLineaabonoSqlProvider {

    public String getLineasAbono(String idFactura, String idInstitucion) {

        SQL query = new SQL();

        query.SELECT("IDABONO,DESCRIPCIONLINEA,NUMEROLINEA,PRECIOUNITARIO,CANTIDAD,(PRECIOUNITARIO * CANTIDAD) importeNeto,"
                + "((PRECIOUNITARIO * CANTIDAD) * (IVA / 100)) importeIVA,((PRECIOUNITARIO * CANTIDAD) * (1 + IVA / 100))importeTotal");

        query.FROM("FAC_LINEAABONO fl");

        query.WHERE("IDINSTITUCION =" + idInstitucion);
        query.WHERE("IDABONO =" + idFactura);

        query.WHERE("ROWNUM < 201");

        return query.toString();
    }
}
