package org.itcgae.siga.db.services.fac.providers;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacHistoricofacturaSqlProvider;

public class FacHistoricofacturaExtendsSqlProvider extends FacHistoricofacturaSqlProvider {

	public String getEstadosPagos(String idFactura, String idInstitucion, String idLenguaje) {

		SQL cuentaBancaria = new SQL();

		cuentaBancaria.SELECT("Case When Instr(banco.NOMBRE, '~', 2) > 1 Then Substr(banco.NOMBRE, 1, Instr(banco.NOMBRE, '~', 2) - 2) " +
				"When Instr(banco.NOMBRE, '(') > 0 Then Substr(banco.NOMBRE, 1, Instr(banco.NOMBRE, '(') - 2) " +
				"Else banco.NOMBRE End || ' (...' || Substr(cb.IBAN, -4) || ')'");
		cuentaBancaria.FROM("CEN_CUENTASBANCARIAS cb");
		cuentaBancaria.FROM("CEN_BANCOS banco");
		cuentaBancaria.WHERE("cb.CBO_CODIGO = banco.CODIGO");
		cuentaBancaria.WHERE("cb.IDINSTITUCION = FH.IDINSTITUCION AND cb.IDPERSONA = FH.IDPERSONA AND cb.IDCUENTA = FH.IDCUENTA");

		SQL forward = new SQL();

		forward.SELECT_DISTINCT("level as LVL");
		forward.SELECT_DISTINCT("FH.IDHISTORICO");

		String fechaEmision = "WHEN (FH.IDTIPOACCION = 1 OR FH.IDTIPOACCION = 2) THEN FF.FECHAEMISION ";
		String fechaPagoCaja = "WHEN FH.IDTIPOACCION = 4 THEN PAGO.FECHA ";
		String fechaPagoBanco = "WHEN FH.IDTIPOACCION = 5 THEN FH.FECHAMODIFICACION ";
		String fechaDevolucion = "WHEN FH.IDTIPOACCION = 6 THEN (LAG(FINC.FECHADEVOLUCION, 1) OVER (ORDER BY LEVEL, FH.IDHISTORICO)) "; // Se almacena en el pago por banco
		String fechaRenegociacion = "WHEN FH.IDTIPOACCION = 7 THEN RENEGOCIACION.FECHARENEGOCIACION ";
		String fechaAnulacion = "WHEN ((FH.IDTIPOACCION = 8 OR FH.IDTIPOACCION = 9) AND FA.FECHA IS NOT NULL) THEN FA.FECHA ";
		String fechaDevolucionAnulacion = "WHEN FH.IDTIPOACCION = 9 THEN (LAG(FINC.FECHADEVOLUCION, 2) OVER (ORDER BY LEVEL, FH.IDHISTORICO)) "; // Se almacena en el pago por banco
		String fechaCompensacion = "WHEN FH.IDTIPOACCION = 10 THEN PAGO.FECHA";

		forward.SELECT_DISTINCT("(CASE " + fechaEmision + fechaPagoCaja + fechaPagoBanco + fechaDevolucion
				+ fechaRenegociacion + fechaAnulacion + fechaDevolucionAnulacion + fechaCompensacion + " ELSE FH.FECHAMODIFICACION END) FECHAMODIFICACION");

		forward.SELECT_DISTINCT("INITCAP(F_SIGA_GETRECURSO(FT.NOMBRE," + idLenguaje + ")) ACCION");
		forward.SELECT_DISTINCT("FT.IDTIPOACCION, FH.ESTADO IDESTADO");
		forward.SELECT_DISTINCT("INITCAP(GR.DESCRIPCION) ESTADO");
		forward.SELECT_DISTINCT("( " + cuentaBancaria.toString() + " ) IBAN");
		forward.SELECT_DISTINCT("(CASE WHEN FH.IDPAGOPORCAJA IS NOT NULL THEN PAGO.IMPORTE WHEN FH.IDFACTURAINCLUIDAENDISQUETE IS NOT NULL THEN FINC.IMPORTE ELSE 0 END) IMPTOTALPAGADO");
		forward.SELECT_DISTINCT("FH.IMPTOTALPORPAGAR");
		forward.SELECT_DISTINCT("CASE WHEN FT.IDTIPOACCION = 10 THEN TO_CHAR(FA.IDPAGOSJG) END IDSJCS");
		forward.SELECT_DISTINCT("FF.NUMEROFACTURA");
		forward.SELECT_DISTINCT("CASE WHEN FT.IDTIPOACCION = 2 OR ((FT.IDTIPOACCION = 8 OR FT.IDTIPOACCION = 9) AND FH.COMISIONIDFACTURA IS NOT NULL) THEN '1' END ENLACEFACTURA");
		forward.SELECT_DISTINCT("FF.IDFACTURA");
		forward.SELECT_DISTINCT("cASE WHEN FT.IDTIPOACCION = 5 THEN TO_CHAR(FH.IDDISQUETECARGOS) END IDCARGOS");
		forward.SELECT_DISTINCT("CASE WHEN FT.IDTIPOACCION = 6 THEN TO_CHAR(IDDISQUETEDEVOLUCIONES) END IDDEVOLUCIONES");
		forward.SELECT_DISTINCT("CASE WHEN (FT.IDTIPOACCION = 8 OR FT.IDTIPOACCION = 9) AND FH.COMISIONIDFACTURA IS NULL THEN '1' END ENLACEABONO");
		forward.SELECT_DISTINCT("FA.NUMEROABONO");
		forward.SELECT_DISTINCT("CASE WHEN FT.IDTIPOACCION = 8 THEN FA.IDABONO WHEN FT.IDTIPOACCION = 9 THEN FA.IDABONO END IDABONO");
		forward.SELECT_DISTINCT("CASE WHEN FT.IDTIPOACCION = 8 OR FT.IDTIPOACCION = 9 THEN FH.COMISIONIDFACTURA ELSE NULL END COMISIONIDFACTURA");
		forward.SELECT_DISTINCT("CASE WHEN FT.IDTIPOACCION = 8 OR FT.IDTIPOACCION = 9 THEN (SELECT CF.NUMEROFACTURA FROM FAC_FACTURA CF WHERE CF.IDINSTITUCION = FH.IDINSTITUCION AND CF.COMISIONIDFACTURA = FH.IDFACTURA) ELSE NULL END COMISIONFACTURA");
		forward.SELECT_DISTINCT("(CASE WHEN FH.IDTIPOACCION = 7 THEN RENEGOCIACION.COMENTARIO WHEN FH.IDTIPOACCION = 4 THEN PAGO.OBSERVACIONES ELSE NULL END) COMENTARIO");

		forward.FROM("FAC_FACTURA FF");
		forward.LEFT_OUTER_JOIN("FAC_ABONO FA ON ( FA.IDINSTITUCION = FF.IDINSTITUCION AND FA.IDFACTURA = FF.IDFACTURA )");
		forward.LEFT_OUTER_JOIN("FAC_HISTORICOFACTURA FH ON (FF.IDINSTITUCION = FH.IDINSTITUCION AND FF.IDFACTURA = FH.IDFACTURA)");

		forward.LEFT_OUTER_JOIN("FAC_FACTURAINCLUIDAENDISQUETE FINC ON (FINC.IDINSTITUCION = FH.IDINSTITUCION AND FINC.IDDISQUETECARGOS = FH.IDDISQUETECARGOS AND FINC.IDFACTURAINCLUIDAENDISQUETE = FH.IDFACTURAINCLUIDAENDISQUETE)");
		forward.LEFT_OUTER_JOIN("FAC_PAGOSPORCAJA PAGO ON (PAGO.IDINSTITUCION = FH.IDINSTITUCION AND PAGO.IDFACTURA = FH.IDFACTURA AND PAGO.IDPAGOPORCAJA = FH.IDPAGOPORCAJA)");
		forward.LEFT_OUTER_JOIN("FAC_RENEGOCIACION RENEGOCIACION ON (RENEGOCIACION.IDINSTITUCION = FH.IDINSTITUCION AND RENEGOCIACION.IDFACTURA = FH.IDFACTURA AND RENEGOCIACION.IDRENEGOCIACION = FH.IDRENEGOCIACION)");

		forward.LEFT_OUTER_JOIN("FAC_TIPOSACCIONFACTURA FT ON (FT.IDTIPOACCION = FH.IDTIPOACCION)");
		forward.LEFT_OUTER_JOIN("FAC_ESTADOFACTURA FE ON (FE.IDESTADO = FH.ESTADO)");
		forward.LEFT_OUTER_JOIN("GEN_RECURSOS GR ON (GR.IDRECURSO = FE.DESCRIPCION AND GR.IDLENGUAJE = " + idLenguaje + ")");


		String backwardString = forward.toString()
				+ " START WITH FF.IDFACTURA = " + idFactura + " AND FF.IDINSTITUCION = " + idInstitucion + " "
				+ "CONNECT BY PRIOR FF.IDFACTURA = FF.COMISIONIDFACTURA AND FF.IDINSTITUCION = " + idInstitucion;

		SQL principal = new SQL();
		principal.SELECT("*");
		principal.FROM("((" + backwardString + "))");
		principal.ORDER_BY("LVL, IDHISTORICO");

		return principal.toString();
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
		sql2.SELECT("NVL((" + subQuery.toString() + "), 0) + 1");
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
			sql2.SELECT(idPagoPorCaja.toString());
		}

		if (null != idDisqueteCargos && idDisqueteCargos > 0) {
			sql2.SELECT(idDisqueteCargos.toString());
		}

		if (null != idFacturaIncluidaEnDisquete && idFacturaIncluidaEnDisquete > 0) {
			sql2.SELECT(idFacturaIncluidaEnDisquete.toString());
		}

		if (null != idDisqueteDevoluciones && idDisqueteDevoluciones > 0) {
			sql2.SELECT(idDisqueteDevoluciones.toString());
		}

		if (null != idRecibo && !"".equalsIgnoreCase(idRecibo)) {
			sql2.SELECT(idRecibo);
		}

		if (null != idRenegociacion && idRenegociacion > 0) {
			sql2.SELECT(idRenegociacion.toString());
		}

		if (null != idAbono && idAbono > 0) {
			sql2.SELECT(idAbono.toString());
		}

		if (null != comisionIdFactura && !"".equalsIgnoreCase(comisionIdFactura)) {
			sql2.SELECT(comisionIdFactura);
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
		SQL join = new SQL();

		join.SELECT("MAX(fh2.IDHISTORICO) IDHISTORICO");
		join.SELECT("ff2.IDFACTURA");
		join.FROM("FAC_FACTURAINCLUIDAENDISQUETE ff2");
		join.INNER_JOIN("FAC_HISTORICOFACTURA fh2 ON (fh2.IDINSTITUCION = ff2.IDINSTITUCION AND fh2.IDFACTURA = ff2.IDFACTURA)");
		join.WHERE("ff2.IDINSTITUCION = " + idInstitucion + " AND ff2.IDDISQUETECARGOS = " + idDisquetecargos);
		join.GROUP_BY("ff2.IDFACTURA");


		query.SELECT_DISTINCT("ff.*");

		query.FROM("FAC_HISTORICOFACTURA fh");
		query.INNER_JOIN("( " + join.toString() + " ) maxhistorico ON (fh.IDFACTURA = maxhistorico.IDFACTURA AND fh.IDHISTORICO = maxhistorico.IDHISTORICO)");
		query.INNER_JOIN("FAC_FACTURA ff ON(ff.IDFACTURA = fh.IDFACTURA AND ff.IDINSTITUCION = fh.IDINSTITUCION)");
		query.LEFT_OUTER_JOIN("FAC_FACTURAINCLUIDAENDISQUETE ff3 ON (fh.IDINSTITUCION = ff3.IDINSTITUCION AND fh.IDFACTURA = ff3.IDFACTURA AND ff3.IDDISQUETECARGOS = " + idDisquetecargos + ")");

		query.WHERE("fh.IDINSTITUCION = " + idInstitucion);

		// Se produce error al eliminar en las siguietnes situaciones:
		// - Facturas con acción distinta de Pago por Banco
		// - Facturas Pendientes de Pago por Banco
		// - Facturas Pagadas por Banco que han sido Devueltas
		query.WHERE("(fh.IDTIPOACCION != 5 OR fh.IDTIPOACCION = 5 AND (fh.ESTADO != 1 OR fh.ESTADO = 1 AND ff3.DEVUELTA = 'S'))");

		return query.toString();
	}

	public String getNextIdHstorico(Short idInstitucion, String idFactura) {
		SQL sql = new SQL();

		sql.SELECT("(NVL(MAX(HIS.IDHISTORICO),0) + 1)");
		sql.FROM("FAC_HISTORICOFACTURA HIS");
		sql.WHERE("HIS.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("HIS.IDFACTURA = " + idFactura);

		return sql.toString();
	}

	public String updateImportesHistoricoEmisionFactura(String idFactura, Short idHistorico, Short idInstitucion, Integer idUsuario) {
		SQL sql = new SQL();

		sql.UPDATE("fac_historicofactura");

		sql.SET("imptotalanticipado = pkg_siga_totalesfactura.totalanticipado(idinstitucion, idfactura)");
		sql.SET("imptotalpagadoporcaja = pkg_siga_totalesfactura.totalpagadoporcaja(idinstitucion, idfactura)");
		sql.SET("imptotalpagadosolocaja = pkg_siga_totalesfactura.totalpagadosolocaja(idinstitucion, idfactura)");
		sql.SET("imptotalpagadosolotarjeta = pkg_siga_totalesfactura.totalpagadosolotarjeta(idinstitucion, idfactura)");
		sql.SET("imptotalpagadoporbanco = pkg_siga_totalesfactura.totalpagadoporbanco(idinstitucion, idfactura)");
		sql.SET("imptotalpagado = pkg_siga_totalesfactura.totalpagado(idinstitucion, idfactura)");
		sql.SET("imptotalporpagar = pkg_siga_totalesfactura.pendienteporpagar(idinstitucion, idfactura)");
		sql.SET("imptotalcompensado = pkg_siga_totalesfactura.totalcompensado(idinstitucion, idfactura)");
		sql.SET("fechamodificacion = sysdate");
		sql.SET("usumodificacion = " + idUsuario);

		sql.WHERE("idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("idfactura = '" + idFactura + "'");
		sql.WHERE("idhistorico = '" + idHistorico + "'");

		return sql.toString();
	}

}