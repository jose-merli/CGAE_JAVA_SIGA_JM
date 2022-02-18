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

        query.SELECT("IDFACTURA,IDTIPOIVA,NUMEROLINEA,DESCRIPCION, ROUND(PRECIOUNITARIO, 2) PRECIOUNITARIO, CANTIDAD, ROUND(IMPORTEANTICIPADO, 2) IMPORTEANTICIPADO, ROUND(PRECIOUNITARIO * CANTIDAD, 2) importeNeto,"
                + "ROUND((PRECIOUNITARIO * CANTIDAD) * (IVA / 100), 2) importeIVA, ROUND((PRECIOUNITARIO * CANTIDAD) * (1 + IVA / 100), 2) importeTotal,( "
                + tipoIVA.toString() + ") tipoIVA");

        query.FROM("FAC_LINEAFACTURA fl");

        query.WHERE("IDINSTITUCION =" + idInstitucion);
        query.WHERE("IDFACTURA =" + idFactura);

        query.WHERE("ROWNUM < 201");

        return query.toString();
    }

    public String getLineasImpresionInforme(String idInstitucion, String idFactura) {
        SQL sql = new SQL();
        sql.SELECT("LF.IDINSTITUCION");
        sql.SELECT("LF.IDFACTURA");
        sql.SELECT("LF.NUMEROLINEA");
        sql.SELECT("LF.CANTIDAD AS CANTIDAD_LINEA");
        sql.SELECT("LF.CTAPRODUCTOSERVICIO");
        sql.SELECT("LF.CTAIVA");
        sql.SELECT("LF.PRECIOUNITARIO AS PRECIO_LINEA");
        sql.SELECT("LF.IVA AS IVA_LINEA");
        sql.SELECT("TO_CHAR(C.FECHA, 'DD/MM/RRRR') AS FECHA_COMPRA");
        sql.SELECT("F_SIGA_DESCLINEAFACT(" + idInstitucion + ", " + idFactura + ", LF.NUMEROLINEA) AS DESCRIPCION_LINEA");
        sql.FROM("FAC_LINEAFACTURA LF, PYS_COMPRA C");
        sql.WHERE("LF.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("LF.IDFACTURA = " + idFactura);
        sql.WHERE("C.NUMEROLINEA (+)= LF.NUMEROLINEA");
        sql.WHERE("C.IDFACTURA (+)= LF.IDFACTURA");
        sql.WHERE("C.IDINSTITUCION (+)= LF.IDINSTITUCION");
        sql.ORDER_BY("LF.NUMEROORDEN ASC, LF.NUMEROLINEA ASC");
        return sql.toString();
    }
}
