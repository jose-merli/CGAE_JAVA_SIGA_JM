package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacBancoinstitucionSqlProvider;

public class FacBancoinstitucionSqlExtendsProvider extends FacBancoinstitucionSqlProvider {

	public String getCuentasBancarias(Short idInstitucion) {
		SQL query = new SQL();
		SQL select1 = new SQL();
		SQL select2 = new SQL();
		SQL select3 = new SQL();
		SQL select4 = new SQL();
		SQL select5 = new SQL();
		
//		//Select 1
//		select1.SELECT("bi.bancos_codigo");
//		select1.SELECT(	"bi.cod_banco,bi.cod_sucursal");
//		select1.SELECT("bi.fechabaja, bi.iban");
//		select1.SELECT("bi.sjcs, su.concepto sufijo");
//		select1.SELECT("bi.comisionimporte");
//		select1.SELECT("bi.comisiondescripcion");
//		select1.SELECT("cb.nombre");
//		select1.SELECT("bi.asientocontable");
//		select1.SELECT("bi.cuentacontabletarjeta");
//		select1.SELECT("cb.bic", "bi.configficherossecuencia");
//		select1.SELECT("bi.configficherosesquema");
//		select1.SELECT("bi.configlugaresquemasecuencia");
//		select1.SELECT("bi.configconceptoampliado, ((");
//			  
//
//		select2.SELECT("COUNT(1)");
//		select2.FROM("fac_seriefacturacion_banco sfb");
//		select2.WHERE("sfb.bancos_codigo = bi.bancos_codigo");
//		select2.WHERE("sfb.idinstitucion = bi.idinstitucion) + (");
//	    
//
//		select3.SELECT("COUNT(1)");
//		select3.FROM("fcs_pagosjg p");
//		select3.WHERE("p.idinstitucion = bi.idinstitucion");
//		select3.WHERE("p.bancos_codigo = bi.bancos_codigo");
//		select3.WHERE(" p.idpagosjg IN (select distinct a.idpagosjg FROM fac_abono a WHERE a.idinstitucion = bi.idinstitucion "
//    									+ "AND a.estado = (SELECT idestado FROM fac_estadoabono WHERE UPPER(descripcion) LIKE '%PAGADO%'))), num_usos, ((");
//		
//		select4.SELECT("COUNT(1)");
//		select4.FROM("fac_disqueteabonos da");
//		select4.WHERE("da.bancos_codigo = bi.bancos_codigo");
//		select4.WHERE("da.idinstitucion = bi.idinstitucion) + (");
//		
//		select5.SELECT("COUNT(1)");
//		select5.FROM("fac_disquetecargos dc");
//		select5.WHERE("dc.bancos_codigo = bi.bancos_codigo");
//		select5.WHERE("dc.idinstitucion = bi.idinstitucion)) num_ficheros");
//		select5.FROM("fac_bancoinstitucion bi");
//		select5.INNER_JOIN("cen_bancos cb ON (cb.codigo = bi.cod_banco)");
//		select5.LEFT_OUTER_JOIN("fac_sufijo su ON (bi.idsufijosjcs = su.idsufijo and bi.idinstitucion=su.idinstitucion)");
//		select5.WHERE("bi.idinstitucion = '" + idInstitucion + "'");
//		
//	   
//	    query.SELECT(select1.toString() + select2.toString() + select3.toString() + select4.toString() + select5.toString());
	   
	    query.SELECT("bi.bancos_codigo,\r\n"
	    		+ "	bi.cod_banco,\r\n"
	    		+ "	bi.cod_sucursal,\r\n"
	    		+ "	bi.fechabaja,\r\n"
	    		+ "	bi.iban,\r\n"
	    		+ "	bi.sjcs,\r\n"
	    		+ "	su.concepto sufijo,\r\n"
	    		+ "	bi.comisionimporte,\r\n"
	    		+ "	bi.comisiondescripcion,\r\n"
	    		+ "	cb.nombre,\r\n"
	    		+ "	bi.asientocontable,\r\n"
	    		+ "	bi.cuentacontabletarjeta,\r\n"
	    		+ "	cb.bic,\r\n"
	    		+ "	bi.configficherossecuencia,\r\n"
	    		+ "	bi.configficherosesquema,\r\n"
	    		+ "	bi.configlugaresquemasecuencia,\r\n"
	    		+ "	bi.configconceptoampliado,\r\n"
	    		+ "	( (\r\n"
	    		+ "	SELECT\r\n"
	    		+ "		COUNT(1)\r\n"
	    		+ "	FROM\r\n"
	    		+ "		fac_seriefacturacion_banco sfb\r\n"
	    		+ "	WHERE\r\n"
	    		+ "		sfb.bancos_codigo = bi.bancos_codigo\r\n"
	    		+ "		AND sfb.idinstitucion = bi.idinstitucion) + ((\r\n"
	    		+ "	SELECT\r\n"
	    		+ "		COUNT(1)\r\n"
	    		+ "	FROM\r\n"
	    		+ "		fcs_pagosjg p\r\n"
	    		+ "	WHERE\r\n"
	    		+ "		p.idinstitucion = bi.idinstitucion\r\n"
	    		+ "		AND p.bancos_codigo = bi.bancos_codigo\r\n"
	    		+ "		AND (\r\n"
	    		+ "		SELECT\r\n"
	    		+ "			COUNT(idpagosjg)\r\n"
	    		+ "		FROM\r\n"
	    		+ "			fac_abono\r\n"
	    		+ "		WHERE\r\n"
	    		+ "			idpagosjg = p.idpagosjg\r\n"
	    		+ "			AND idinstitucion = p.idinstitucion) = 0 )+(\r\n"
	    		+ "	SELECT\r\n"
	    		+ "		COUNT(1)\r\n"
	    		+ "	FROM\r\n"
	    		+ "		FCS_PAGOSJG FCS\r\n"
	    		+ "	WHERE\r\n"
	    		+ "		EXISTS (\r\n"
	    		+ "		SELECT\r\n"
	    		+ "			*\r\n"
	    		+ "		FROM\r\n"
	    		+ "			FAC_ABONO FAC\r\n"
	    		+ "		WHERE\r\n"
	    		+ "			FAC.IDINSTITUCION = FCS.IDINSTITUCION\r\n"
	    		+ "			AND FAC.IDPAGOSJG = FCS.IDPAGOSJG\r\n"
	    		+ "			AND FAC.IMPPENDIENTEPORABONAR > 0)\r\n"
	    		+ "		AND FCS.IDINSTITUCION = bi.idinstitucion\r\n"
	    		+ "		AND FCS.BANCOS_CODIGO = bi.bancos_codigo ) ) ) NUM_USOS,\r\n"
	    		+ "	( (\r\n"
	    		+ "	SELECT\r\n"
	    		+ "		COUNT(1)\r\n"
	    		+ "	FROM\r\n"
	    		+ "		fac_disqueteabonos da\r\n"
	    		+ "	WHERE\r\n"
	    		+ "		da.bancos_codigo = bi.bancos_codigo\r\n"
	    		+ "		AND da.idinstitucion = bi.idinstitucion ) + (\r\n"
	    		+ "	SELECT\r\n"
	    		+ "		COUNT(1)\r\n"
	    		+ "	FROM\r\n"
	    		+ "		fac_disquetecargos dc\r\n"
	    		+ "	WHERE\r\n"
	    		+ "		dc.bancos_codigo = bi.bancos_codigo\r\n"
	    		+ "		AND dc.idinstitucion = bi.idinstitucion ) ) num_ficheros\r\n"
	    		+ "FROM\r\n"
	    		+ "	fac_bancoinstitucion bi\r\n"
	    		+ "INNER JOIN cen_bancos cb ON\r\n"
	    		+ "	(cb.codigo = bi.cod_banco)\r\n"
	    		+ "LEFT OUTER JOIN fac_sufijo su ON\r\n"
	    		+ "	(bi.idsufijosjcs = su.idsufijo\r\n"
	    		+ "		AND bi.idinstitucion = su.idinstitucion)\r\n"
	    		+ "WHERE\r\n"
	    		+ "    bi.idinstitucion = 2005\r\n"
	    		+ "");
		return query.toString();
	}

}
