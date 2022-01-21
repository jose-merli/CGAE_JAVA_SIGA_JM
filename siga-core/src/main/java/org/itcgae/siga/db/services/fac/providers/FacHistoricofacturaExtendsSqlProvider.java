package org.itcgae.siga.db.services.fac.providers;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacHistoricofacturaSqlProvider;

public class FacHistoricofacturaExtendsSqlProvider extends FacHistoricofacturaSqlProvider {

	public String getEstadosPagos(String idFactura, String idInstitucion, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("FH.FECHAMODIFICACION, F_SIGA_GETRECURSO(FT.NOMBRE," + idLenguaje + ") ACCION,"
				+ "FT.IDTIPOACCION, FH.ESTADO IDESTADO,"
				+ "GR.DESCRIPCION ESTADO, CC.IBAN, FH.IMPTOTALPAGADO, FH.IMPTOTALPORPAGAR,"
				+ "CASE WHEN FT.IDTIPOACCION = 10 THEN TO_CHAR(FA.IDPAGOSJG) END IDSJCS,"
				+ "CASE WHEN FT.IDTIPOACCION = 2 THEN '1' END ENLACEFACTURA," + "FF.NUMEROFACTURA,"
				+ "CASE WHEN FT.IDTIPOACCION = 2 THEN FF.IDFACTURA END IDFACTURA,"
				+ "CASE WHEN FT.IDTIPOACCION = 5 THEN TO_CHAR(FH.IDDISQUETECARGOS) END IDCARGOS,"
				+ "CASE WHEN FT.IDTIPOACCION = 6 THEN TO_CHAR(IDDISQUETEDEVOLUCIONES) END IDDEVOLUCIONES,"
				+ "CASE WHEN FT.IDTIPOACCION = 8 THEN '1' WHEN FT.IDTIPOACCION = 9 THEN '1' END ENLACEABONO,"
				+ "FA.NUMEROABONO,"
				+ "CASE WHEN FT.IDTIPOACCION = 8 THEN FA.IDABONO WHEN FT.IDTIPOACCION = 9 THEN FA.IDABONO END IDABONO");

		sql.FROM("FAC_HISTORICOFACTURA FH");
		sql.INNER_JOIN("FAC_TIPOSACCIONFACTURA FT ON (FT.IDTIPOACCION = FH.IDTIPOACCION)");
		sql.INNER_JOIN("FAC_ESTADOFACTURA FE ON (FE.IDESTADO = FH.ESTADO)");
		sql.INNER_JOIN("GEN_RECURSOS GR ON (GR.IDRECURSO = FE.DESCRIPCION AND GR.IDLENGUAJE = " + idLenguaje + ")");
		sql.LEFT_OUTER_JOIN("FAC_FACTURA FF ON (FF.IDINSTITUCION = FH.IDINSTITUCION AND FF.IDFACTURA = FH.IDFACTURA)");
		sql.LEFT_OUTER_JOIN("FAC_ABONO FA ON (FA.IDINSTITUCION = FH.IDINSTITUCION AND FA.IDABONO = FH.IDABONO)");
		sql.LEFT_OUTER_JOIN(
				"CEN_CUENTASBANCARIAS CC ON (CC.IDCUENTA = FH.IDCUENTA AND CC.IDINSTITUCION = FH.IDINSTITUCION AND CC.IDPERSONA = FH.IDPERSONA)");

		sql.WHERE("FH.IDINSTITUCION =" + idInstitucion);
		sql.WHERE("FH.IDFACTURA =" + idFactura);

		sql.ORDER_BY("FH.IDHISTORICO ASC");

		return sql.toString();
	}

	public String getFacturacionLog(String idFactura, String idInstitucion, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("fh.FECHAMODIFICACION AS FECHA, F_SIGA_GETRECURSO(ft.NOMBRE ," + idLenguaje
				+ ") AS ACCION, gr.DESCRIPCION AS ESTADO");

		sql.FROM("FAC_HISTORICOFACTURA fh");
		sql.LEFT_OUTER_JOIN("FAC_TIPOSACCIONFACTURA ft ON(fh.IDTIPOACCION = ft.IDTIPOACCION)");
		sql.LEFT_OUTER_JOIN("FAC_ESTADOFACTURA fe ON(fh.ESTADO = fe.IDESTADO)");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS gr ON(fe.DESCRIPCION = gr.IDRECURSO AND gr.IDLENGUAJE = " + idLenguaje + ")");
		sql.LEFT_OUTER_JOIN("FAC_TIPOSACCIONFACTURA ft ON(fh.IDTIPOACCION = ft.IDTIPOACCION)");

		sql.WHERE("fh.IDINSTITUCION =" + idInstitucion);
		sql.WHERE("fh.IDFACTURA =" + idFactura);

		sql.ORDER_BY("fh.FECHAMODIFICACION ASC");

		return sql.toString();
	}

	public String insertarHistoricoFacParametros(String idInstitucion, String idFactura, Integer idTipoAccion,
			Integer idPagoPorCaja, Integer idDisqueteCargos, Integer idFacturaIncluidaEnDisquete,
			Integer idDisqueteDevoluciones, String idRecibo, Integer idRenegociacion, Integer idAbono,
			String comisionIdFactura) {

		String cadena = "";

		if (null != idPagoPorCaja && idPagoPorCaja > 0) {
			cadena += ", IDPAGOPORCAJA";
		}

		if (null != idDisqueteCargos && idDisqueteCargos > 0) {
			cadena += ", IDDISQUETECARGOS";
		}

		if (null != idFacturaIncluidaEnDisquete && idFacturaIncluidaEnDisquete > 0) {
			cadena += ", IDFACTURAINCLUIDAENDISQUETE";
		}

		if (null != idDisqueteDevoluciones && idDisqueteDevoluciones > 0) {
			cadena += ", IDDISQUETEDEVOLUCIONES";
		}

		if (null != idRecibo && !"".equalsIgnoreCase(idRecibo)) {
			cadena += ", IDRECIBO";
		}

		if (null != idRenegociacion && idRenegociacion > 0) {
			cadena += ", IDRENEGOCIACION";
		}

		if (null != idAbono && idAbono > 0) {
			cadena += ", IDABONO";
		}

		if (null != comisionIdFactura && !"".equalsIgnoreCase(comisionIdFactura)) {
			cadena += ", COMISIONIDFACTURA";
		}

		SQL sql = new SQL();
		sql.INSERT_INTO("FAC_HISTORICOFACTURA"
				+ " (IDINSTITUCION,IDFACTURA, IDHISTORICO,FECHAMODIFICACION, USUMODIFICACION, IDTIPOACCION,"
				+ " IDFORMAPAGO, IDPERSONA, IDCUENTA, IDPERSONADEUDOR, IDCUENTADEUDOR, IMPTOTALANTICIPADO,"
				+ " IMPTOTALPAGADOPORCAJA, IMPTOTALPAGADOSOLOCAJA, IMPTOTALPAGADOSOLOTARJETA, IMPTOTALPAGADOPORBANCO,"
				+ " IMPTOTALPAGADO, IMPTOTALPORPAGAR, IMPTOTALCOMPENSADO, ESTADO" + cadena + ")");

		SQL sql2 = new SQL();
		sql2.SELECT("IDINSTITUCION");
		sql2.SELECT("IDFACTURA");
		SQL subQuery = new SQL();
		subQuery.SELECT("MAX(HIS2.IDHISTORICO)");
		subQuery.FROM("FAC_HISTORICOFACTURA HIS2");
		subQuery.WHERE("HIS2.IDINSTITUCION = FAC_FACTURA.IDINSTITUCION");
		subQuery.WHERE("HIS2.IDFACTURA = FAC_FACTURA.IDFACTURA");
		sql2.SELECT("NVL(" + subQuery.toString() + ", 0) + 1");
		sql2.SELECT("SYSDATE");
		sql2.SELECT("USUMODIFICACION");
		sql2.SELECT(idTipoAccion.toString());
		sql2.SELECT("IDFORMAPAGO");
		sql2.SELECT("IDPERSONA");
		sql2.SELECT("IDCUENTA");
		sql2.SELECT("IDPERSONADEUDOR");
		sql2.SELECT("IDCUENTADEUDOR");
		sql2.SELECT("IMPTOTALANTICIPADO");
		sql2.SELECT("IMPTOTALPAGADOPORCAJA");
		sql2.SELECT("IMPTOTALPAGADOSOLOCAJA");
		sql2.SELECT("IMPTOTALPAGADOSOLOTARJETA");
		sql2.SELECT("IMPTOTALPAGADOPORBANCO");
		sql2.SELECT("IMPTOTALPAGADO");
		sql2.SELECT("IMPTOTALPORPAGAR");
		sql2.SELECT("IMPTOTALCOMPENSADO");
		sql2.SELECT("ESTADO");

		if (null != idPagoPorCaja && idPagoPorCaja > 0) {
			sql2.SELECT("IDPAGOPORCAJA");
		}

		if (null != idDisqueteCargos && idDisqueteCargos > 0) {
			sql2.SELECT("IDDISQUETECARGOS");
		}

		if (null != idFacturaIncluidaEnDisquete && idFacturaIncluidaEnDisquete > 0) {
			sql2.SELECT("IDFACTURAINCLUIDAENDISQUETE");
		}

		if (null != idDisqueteDevoluciones && idDisqueteDevoluciones > 0) {
			sql2.SELECT("IDDISQUETEDEVOLUCIONES");
		}

		if (null != idRecibo && !"".equalsIgnoreCase(idRecibo)) {
			sql2.SELECT("IDRECIBO");
		}

		if (null != idRenegociacion && idRenegociacion > 0) {
			sql2.SELECT("IDRENEGOCIACION");
		}

		if (null != idAbono && idAbono > 0) {
			sql2.SELECT("IDABONO");
		}

		if (null != comisionIdFactura && !"".equalsIgnoreCase(comisionIdFactura)) {
			sql2.SELECT("COMISIONIDFACTURA");
		}

		sql2.FROM("FAC_FACTURA");
		sql2.WHERE("IDINSTITUCION = " + idInstitucion);
		sql2.WHERE("IDFACTURA = " + idFactura);

		return sql.toString().concat(sql2.toString());
	}

	public String deleteDeshacerCierre(Short idInstitucion, List<Integer> idPagos) {

		SQL subQuery2 = new SQL();
		subQuery2.SELECT("IDABONO");
		subQuery2.FROM("FAC_ABONO");
		subQuery2.WHERE("IDINSTITUCION = " + idInstitucion);
		subQuery2.WHERE(
				"IDPAGOSJG IN (" + idPagos.stream().map(a -> a.toString()).collect(Collectors.joining(",")) + ")");

		SQL subQuery = new SQL();
		subQuery.SELECT("IDINSTITUCION");
		subQuery.SELECT("IDFACTURA");
		subQuery.SELECT("IDPAGOPORCAJA");
		subQuery.FROM("FAC_PAGOSPORCAJA");
		subQuery.WHERE("IDINSTITUCION = " + idInstitucion);
		subQuery.WHERE("IDABONO IN ( " + subQuery2.toString() + " )");

		SQL query = new SQL();
		query.DELETE_FROM("FAC_HISTORICOFACTURA");
		query.WHERE("(IDINSTITUCION, IDFACTURA, IDPAGOPORCAJA) IN ( " + subQuery.toString() + " )");

		return query.toString();
	}

	public String facturasDevueltasEnDisquete(String idDisquetecargos, String idInstitucion) {
		SQL query = new SQL();
		SQL subquery = new SQL();

		subquery.SELECT("MAX(fh2.IDHISTORICO)");

		subquery.FROM("FAC_HISTORICOFACTURA fh2");

		subquery.WHERE("fh2.IDFACTURA = fh.IDFACTURA");
		subquery.WHERE("fh2.IDDISQUETECARGOS = fh.IDDISQUETECARGOS");
		subquery.WHERE("fh2.IDINSTITUCION = fh.IDINSTITUCION");

		query.SELECT("ff.*");

		query.FROM("FAC_HISTORICOFACTURA fh");
		query.INNER_JOIN("INNER JOIN FAC_FACTURA ff ON(ff.IDFACTURA = fh.IDFACTURA AND ff.IDINSTITUCION = fh.IDINSTITUCION)");

		query.WHERE("fh.IDDISQUETECARGOS = " + idDisquetecargos);
		query.WHERE("fh.IDINSTITUCION = " + idInstitucion);
		query.WHERE("fh.ESTADO = 1");
		query.WHERE("fh.IDHISTORICO != (" + subquery.toString() + ")");

		return query.toString();
	}
}