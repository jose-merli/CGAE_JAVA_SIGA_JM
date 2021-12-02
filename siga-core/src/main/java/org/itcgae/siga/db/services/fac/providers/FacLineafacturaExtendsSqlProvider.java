package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacLineafacturaSqlProvider;


public class FacLineafacturaExtendsSqlProvider extends FacLineafacturaSqlProvider {

    public String getLineasFactura(String idFactura, String idInstitucion) {

        SQL tipoIVA = new SQL();
        SQL query = new SQL();

        tipoIVA.SELECT("DESCRIPCION");
        tipoIVA.FROM("PYS_TIPOIVA pt");
        tipoIVA.WHERE("IDTIPOIVA = fl.IDTIPOIVA");

        query.SELECT("IDFACTURA,IDTIPOIVA,NUMEROLINEA,DESCRIPCION,PRECIOUNITARIO,CANTIDAD,IMPORTEANTICIPADO,(PRECIOUNITARIO * CANTIDAD) importeNeto,"
                + "((PRECIOUNITARIO * CANTIDAD) * (IVA / 100)) importeIVA,((PRECIOUNITARIO * CANTIDAD) * (1 + IVA / 100))importeTotal,( "
                + tipoIVA.toString() + ")tipoIVA");

        query.FROM("FAC_LINEAFACTURA fl");

        query.WHERE("IDINSTITUCION =" + idInstitucion);
        query.WHERE("IDFACTURA =" + idFactura);

        query.WHERE("ROWNUM < 201");

        return query.toString();
    }
}
