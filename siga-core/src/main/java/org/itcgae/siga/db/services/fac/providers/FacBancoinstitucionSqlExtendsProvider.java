package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.FacBancoinstitucionSqlProvider;

public class FacBancoinstitucionSqlExtendsProvider extends FacBancoinstitucionSqlProvider {

	public String getCuentasBancarias(String idCuenta, Short idInstitucion) {
		SQL query = new SQL();
		
		//Consulta principal
		query.SELECT("bi.bancos_codigo");
		query.SELECT("bi.cod_banco");
		query.SELECT("bi.cod_sucursal");
		query.SELECT("bi.fechabaja");

		query.SELECT("bi.iban");

		query.SELECT("cb.nombre");
		query.SELECT("bi.descripcion");
		query.SELECT("bi.asientocontable");
		query.SELECT("bi.cuentacontabletarjeta");
		query.SELECT("cb.bic");

		//Subconsulta 1

		// Num. pagos
		SQL pagos = new SQL();
		SQL series = new SQL();
		pagos.SELECT("count(*)");
		pagos.FROM("fcs_pagosjg p");
		pagos.LEFT_OUTER_JOIN("fac_sufijo s ON (p.idinstitucion = s.idinstitucion " +
				"AND p.idsufijo = s.idsufijo)");
		pagos.WHERE("p.idinstitucion = " + idInstitucion + " AND p.bancos_codigo = bi.bancos_codigo");

		// Num. series
		series.SELECT("count(*)");
		series.FROM("fac_seriefacturacion sf");
		series.INNER_JOIN("fac_seriefacturacion_banco sfb ON (sf.idinstitucion = sfb.idinstitucion " +
				"AND sf.idseriefacturacion = sfb.idseriefacturacion)");
		series.LEFT_OUTER_JOIN("fac_sufijo s ON (sfb.idinstitucion = s.idinstitucion " +
				"AND sfb.idsufijo = s.idsufijo)");
		series.WHERE("sf.idinstitucion = " + idInstitucion + " AND sfb.bancos_codigo = bi.bancos_codigo");

		query.SELECT( "( (" + pagos.toString() + ") + (" + series.toString() + ") )" + "num_usos");

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
		query.SELECT("(SELECT pysTipoIVA.descripcion FROM pys_tipoiva pysTipoIVA WHERE pysTipoIVA.idtipoiva = bi.idtipoiva) tipoiva");
		query.SELECT("bi.comisioncuentacontable");

		query.SELECT("bi.configficherossecuencia");
		query.SELECT("bi.configficherosesquema");
		query.SELECT("bi.configlugaresquemasecuencia");
		query.SELECT("bi.configconceptoampliado");

		query.SELECT("bi.idsufijosjcs");
		query.SELECT("(SELECT fs.sufijo || ' - ' || fs.concepto FROM fac_sufijo fs WHERE fs.idinstitucion = bi.idinstitucion AND fs.idsufijo = bi.idsufijosjcs) sufijosjcs");
		query.SELECT("su.concepto");
		query.SELECT("bi.sjcs");


							
		//Consulta principal
		query.FROM("fac_bancoinstitucion bi");
		query.INNER_JOIN("cen_bancos cb ON (cb.codigo = bi.cod_banco)");
		query.LEFT_OUTER_JOIN("fac_sufijo su ON (bi.idsufijosjcs = su.idsufijo and bi.idinstitucion=su.idinstitucion)");
		query.WHERE("bi.idinstitucion = " + idInstitucion);
		
		if(!UtilidadesString.esCadenaVacia(idCuenta)) {
			query.WHERE("bi.bancos_codigo="+idCuenta);
		}
			
		return query.toString();
	   
	}

	public String getNextIdCuentaBancaria(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("(NVL(MAX(TO_NUMBER(bi.bancos_codigo)),0) + 1) as bancos_codigo");
		sql.FROM("fac_bancoinstitucion bi");
		sql.WHERE("bi.idinstitucion = " + idInstitucion);

		return sql.toString();
	}

	public String getBancosCodigo(String idInstitucion, String idFactura) {
		SQL sql = new SQL();

		sql.SELECT("fb.BANCOS_CODIGO");
		sql.FROM("FAC_BANCOINSTITUCION fb");
		sql.INNER_JOIN("FAC_SERIEFACTURACION_BANCO fsb ON(fsb.IDINSTITUCION = fb.IDINSTITUCION AND fb.BANCOS_CODIGO = fsb.BANCOS_CODIGO)");
		sql.INNER_JOIN("FAC_FACTURA ff ON(ff.IDFACTURA = "+idFactura+" AND ff.IDINSTITUCION = fb.IDINSTITUCION AND fsb.IDSERIEFACTURACION = ff.IDSERIEFACTURACION)");
		sql.WHERE("fb.idinstitucion = " + idInstitucion);

		return sql.toString();
	}

	public String getPorcentajeIva(String idInstitucion, String bancoCodigo) {
		SQL sql = new SQL();

		sql.SELECT("pt.VALOR");
		sql.FROM("PYS_TIPOIVA pt");
		sql.INNER_JOIN("FAC_BANCOINSTITUCION fb ON(fb.IDTIPOIVA = pt.IDTIPOIVA)");
		sql.WHERE("fb.idinstitucion = " + idInstitucion);
		sql.WHERE("fb.BANCOS_CODIGO = " + bancoCodigo);

		return sql.toString();
	}
	
	 public String comboPropTranferencia(Short idInstitucion) {

	        SQL sql = new SQL();

	        sql.SELECT("BANCOS_CODIGO");
	        sql.SELECT("IBAN");

	        sql.FROM("FAC_BANCOINSTITUCION");

	        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

	        return sql.toString();
	    }
	
}
