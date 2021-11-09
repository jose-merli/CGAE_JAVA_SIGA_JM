package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacBancoinstitucionSqlProvider;

public class FacBancoinstitucionSqlExtendsProvider extends FacBancoinstitucionSqlProvider {

	public String getCuentasBancarias(Short idInstitucion) {
		SQL query = new SQL();
		
		//Consulta principal
		query.SELECT("bi.bancos_codigo");
		query.SELECT("bi.cod_banco");
		query.SELECT("bi.cod_sucursal");
		query.SELECT("bi.fechabaja");

		query.SELECT("bi.iban");

		query.SELECT("cb.nombre");
		// query.SELECT("bi.descripcion");
		query.SELECT("bi.asientocontable");
		query.SELECT("bi.cuentacontabletarjeta");
		query.SELECT("cb.bic");

		//Subconsulta 1
		query.SELECT(
				"((" +
						"SELECT COUNT(1) " +
						"FROM  fac_seriefacturacion_banco sfb " +
						"WHERE sfb.bancos_codigo = bi.bancos_codigo " +
						"AND sfb.idinstitucion = bi.idinstitucion " +
						") +"

						+

						"((" +
						"SELECT COUNT(1) " +
						"FROM fcs_pagosjg p " +
						"WHERE p.idinstitucion = bi.idinstitucion " +
						"AND p.bancos_codigo = bi.bancos_codigo " +
						"AND ( " +
						"	SELECT COUNT(idpagosjg) " +
						"	FROM fac_abono f" +
						"	WHERE f.idpagosjg = p.idpagosjg " +
						"	AND f.idinstitucion = p.idinstitucion) = 0" +
						") +"

						+

						"(" +
						"SELECT COUNT(1) " +
						"FROM fcs_pagosjg fcs " +
						"WHERE EXISTS ( " +
						"	SELECT 1 " +
						"	FROM fac_abono fac " +
						"	WHERE fac.idinstitucion = fcs.idinstitucion " +
						"	AND fac.idpagosjg = fcs.idpagosjg " +
						"	AND fac.imppendienteporabonar > 0) " +
						"AND fcs.idinstitucion = bi.idinstitucion " +
						"AND fcs.bancos_codigo = bi.bancos_codigo " +
						")) " +
						") num_usos "
		);

		//Subconsulta 2
		query.SELECT(
				"((" +
						"SELECT COUNT(1) " +
						"FROM fac_disqueteabonos da " +
						"WHERE da.bancos_codigo = bi.bancos_codigo " +
						"AND da.idinstitucion = bi.idinstitucion) + "

						+

						"(" +
						"SELECT COUNT(1) " +
						"FROM fac_disquetecargos dc " +
						"WHERE dc.bancos_codigo = bi.bancos_codigo " +
						"AND dc.idinstitucion = bi.idinstitucion " +
						")) num_ficheros"
		);

		query.SELECT("bi.comisionimporte");
		query.SELECT("bi.comisiondescripcion");
		query.SELECT("bi.idtipoiva");

		query.SELECT("bi.configficherossecuencia");
		query.SELECT("bi.configficherosesquema");
		query.SELECT("bi.configlugaresquemasecuencia");
		query.SELECT("bi.configconceptoampliado");

		query.SELECT("bi.idsufijosjcs");
		query.SELECT("su.concepto");
		query.SELECT("bi.sjcs");


							
		//Consulta principal
		query.FROM("fac_bancoinstitucion bi");
		query.INNER_JOIN("cen_bancos cb ON (cb.codigo = bi.cod_banco)");
		query.LEFT_OUTER_JOIN("fac_sufijo su ON (bi.idsufijosjcs = su.idsufijo and bi.idinstitucion=su.idinstitucion)");
		query.WHERE("bi.idinstitucion = " + idInstitucion);
		
		return query.toString();
	   
	}
	
	public String comboCuentasBancarias(Short idInstitucion) {
		SQL query = new SQL();
		
		query.SELECT("bi.bancos_codigo");
		query.SELECT("comisiondescripcion || ' (...' || SUBSTR(IBAN, -4) || ')' CUENTA");
		query.FROM("FAC_BANCOINSTITUCION bi");
		query.WHERE("bi.idinstitucion=" + idInstitucion);
		query.ORDER_BY("bi.bancos_codigo");
		
		return query.toString();
	}
	
}
