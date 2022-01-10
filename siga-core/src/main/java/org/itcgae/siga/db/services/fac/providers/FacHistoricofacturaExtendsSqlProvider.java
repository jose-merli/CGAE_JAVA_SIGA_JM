package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacHistoricofacturaSqlProvider;


public class FacHistoricofacturaExtendsSqlProvider extends FacHistoricofacturaSqlProvider {

    public String getEstadosPagos(String idFactura, String idInstitucion, String idLenguaje) {

        SQL sql = new SQL();

        sql.SELECT("FH.FECHAMODIFICACION, F_SIGA_GETRECURSO(FT.NOMBRE,"+idLenguaje+") ACCION,"
                +"FT.IDTIPOACCION, FH.ESTADO IDESTADO,"
                +"GR.DESCRIPCION ESTADO, CC.IBAN, FH.IMPTOTALPAGADO, FH.IMPTOTALPORPAGAR,"
                +"CASE WHEN FT.IDTIPOACCION = 10 THEN TO_CHAR(FA.IDPAGOSJG) END IDSJCS,"
                +"CASE WHEN FT.IDTIPOACCION = 2 THEN '1' END ENLACEFACTURA,"
                +"FF.NUMEROFACTURA,"
                +"CASE WHEN FT.IDTIPOACCION = 2 THEN FF.IDFACTURA END IDFACTURA,"
                +"CASE WHEN FT.IDTIPOACCION = 5 THEN TO_CHAR(FH.IDDISQUETECARGOS) END IDCARGOS,"
                +"CASE WHEN FT.IDTIPOACCION = 6 THEN TO_CHAR(IDDISQUETEDEVOLUCIONES) END IDDEVOLUCIONES,"
                +"CASE WHEN FT.IDTIPOACCION = 8 THEN '1' WHEN FT.IDTIPOACCION = 9 THEN '1' END ENLACEABONO,"
                +"FA.NUMEROABONO,"
                +"CASE WHEN FT.IDTIPOACCION = 8 THEN FA.IDABONO WHEN FT.IDTIPOACCION = 9 THEN FA.IDABONO END IDABONO");

        sql.FROM("FAC_HISTORICOFACTURA FH");
        sql.INNER_JOIN("FAC_TIPOSACCIONFACTURA FT ON (FT.IDTIPOACCION = FH.IDTIPOACCION)");
        sql.INNER_JOIN("FAC_ESTADOFACTURA FE ON (FE.IDESTADO = FH.ESTADO)");
        sql.INNER_JOIN("GEN_RECURSOS GR ON (GR.IDRECURSO = FE.DESCRIPCION AND GR.IDLENGUAJE = "+idLenguaje+")");
        sql.LEFT_OUTER_JOIN("FAC_FACTURA FF ON (FF.IDINSTITUCION = FH.IDINSTITUCION AND FF.IDFACTURA = FH.IDFACTURA)");
        sql.LEFT_OUTER_JOIN("FAC_ABONO FA ON (FA.IDINSTITUCION = FH.IDINSTITUCION AND FA.IDABONO = FH.IDABONO)");
        sql.LEFT_OUTER_JOIN("CEN_CUENTASBANCARIAS CC ON (CC.IDCUENTA = FH.IDCUENTA AND CC.IDINSTITUCION = FH.IDINSTITUCION AND CC.IDPERSONA = FH.IDPERSONA)");

        sql.WHERE("FH.IDINSTITUCION ="+idInstitucion);
        sql.WHERE("FH.IDFACTURA ="+idFactura);

        sql.ORDER_BY("FH.IDHISTORICO ASC");

        return sql.toString();
    }

    public String getFacturacionLog(String idFactura, String idInstitucion, String idLenguaje) {

        SQL sql = new SQL();

        sql.SELECT("fh.FECHAMODIFICACION AS FECHA, F_SIGA_GETRECURSO(ft.NOMBRE ,"+idLenguaje+") AS ACCION, gr.DESCRIPCION AS ESTADO");

        sql.FROM("FAC_HISTORICOFACTURA fh");
        sql.LEFT_OUTER_JOIN("FAC_TIPOSACCIONFACTURA ft ON(fh.IDTIPOACCION = ft.IDTIPOACCION)");
        sql.LEFT_OUTER_JOIN("FAC_ESTADOFACTURA fe ON(fh.ESTADO = fe.IDESTADO)");
        sql.LEFT_OUTER_JOIN("GEN_RECURSOS gr ON(fe.DESCRIPCION = gr.IDRECURSO AND gr.IDLENGUAJE = "+idLenguaje+")");
        sql.LEFT_OUTER_JOIN("FAC_TIPOSACCIONFACTURA ft ON(fh.IDTIPOACCION = ft.IDTIPOACCION)");

        sql.WHERE("fh.IDINSTITUCION ="+idInstitucion);
        sql.WHERE("fh.IDFACTURA ="+idFactura);

        sql.ORDER_BY("fh.FECHAMODIFICACION ASC");

        return sql.toString();
    }
}