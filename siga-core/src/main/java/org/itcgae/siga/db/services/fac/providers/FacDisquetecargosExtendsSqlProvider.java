package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FicherosAdeudosItem;
import org.itcgae.siga.db.mappers.FacDisquetecargosSqlProvider;


public class FacDisquetecargosExtendsSqlProvider extends FacDisquetecargosSqlProvider {
	
	public String getFicherosAdeudos(FicherosAdeudosItem item, String idInstitucion) {
		SQL sql = new SQL();
		SQL totalRemesa = new SQL();
		SQL numRecibos = new SQL();
		
		//sumatorio de remesas
		totalRemesa.SELECT("SUM (fi.importe)");
		totalRemesa.FROM("fac_facturaincluidaendisquete fi");
		totalRemesa.WHERE("fi.idinstitucion = c.idinstitucion AND fi.iddisquetecargos = c.iddisquetecargos");
		
		//sumatorio numero de recibos
		numRecibos.SELECT("COUNT (1)");
		numRecibos.FROM("fac_facturaincluidaendisquete fi");
		numRecibos.WHERE("fi.idinstitucion = c.idinstitucion AND fi.iddisquetecargos = c.iddisquetecargos");
		
		//query principal
		sql.SELECT("c.idinstitucion, c.iddisquetecargos, c.nombrefichero, c.bancos_codigo, b.comisiondescripcion, b.iban, "
				+ "c.fechacreacion, c.idseriefacturacion, sf.nombreabreviado,c .idprogramacion, p.descripcion , c.fechacargo, "
				+ "c.numerolineas, c.idsufijo, s.sufijo, ("+totalRemesa.toString()+") total_remesa, ("+numRecibos.toString()+") AS NUMRECIBOS");
	
		sql.FROM("fac_disquetecargos c");
		sql.INNER_JOIN("fac_bancoinstitucion b ON (c.idinstitucion=b.idinstitucion AND c.bancos_codigo=b.bancos_codigo)");
		sql.LEFT_OUTER_JOIN("fac_seriefacturacion sf ON (sf.idinstitucion=c.idinstitucion AND sf.idseriefacturacion=c.idseriefacturacion)");
		sql.LEFT_OUTER_JOIN("fac_facturacionprogramada p ON (p.idinstitucion=c.idinstitucion AND p.idseriefacturacion=c.idseriefacturacion "
				+ "AND p.idprogramacion=c.idprogramacion)");
		sql.LEFT_OUTER_JOIN("fac_sufijo s ON (s.idinstitucion=c.idinstitucion AND s.idsufijo=c.idsufijo)");
		
		sql.WHERE("c.idinstitucion="+idInstitucion);
		
		return sql.toString();
	}
}