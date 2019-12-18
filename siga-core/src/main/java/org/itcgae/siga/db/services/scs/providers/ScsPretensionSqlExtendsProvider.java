package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.PretensionItem;
import org.itcgae.siga.db.mappers.ScsPretensionSqlProvider;

public class ScsPretensionSqlExtendsProvider extends ScsPretensionSqlProvider{


	public String searchPretensiones(String idLenguaje, PretensionItem pretensionItem) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("PRETENSION.IDINSTITUCION");
		sql.SELECT("PRETENSION.IDPRETENSION");
		sql.SELECT("CATPRETENSION.DESCRIPCION AS DESCRIPCIONPRETENSION");
		sql.SELECT("PRETENSION.CODIGOEXT");
		sql.SELECT("PRETENSION.IDJURISDICCION");
		sql.SELECT("PRETENSION.FECHABAJA");
		sql.SELECT("CATJURISDICCION.DESCRIPCION AS  DESCRIPCIONJURISDICCION");
		
		sql.FROM("SCS_PRETENSION PRETENSION");
		
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS CATPRETENSION ON CATPRETENSION.IDRECURSO = PRETENSION.DESCRIPCION AND CATPRETENSION.IDLENGUAJE = "+idLenguaje);
		sql.LEFT_OUTER_JOIN("SCS_PRETENSIONESPROCED PRETENSIONESPROC ON PRETENSIONESPROC.IDPRETENSION = PRETENSION.IDPRETENSION AND PRETENSION.IDINSTITUCION = PRETENSIONESPROC.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN("SCS_JURISDICCION JURISDICCION ON JURISDICCION.IDJURISDICCION = PRETENSION.IDJURISDICCION");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS CATJURISDICCION ON CATJURISDICCION.IDRECURSO = JURISDICCION.DESCRIPCION AND CATJURISDICCION.IDLENGUAJE = "+idLenguaje);

		sql.WHERE("pretension.idinstitucion = '" + pretensionItem.getIdInstitucion() + "'");
		
		
		if(pretensionItem.getIdJurisdiccion() != null && pretensionItem.getIdJurisdiccion() != "") {
			sql.WHERE("PRETENSION.IDJURISDICCION = "+pretensionItem.getIdJurisdiccion());
		}
		if(pretensionItem.getIdPretension() != null && pretensionItem.getIdPretension() != "") {
			sql.WHERE("PRETENSIONESPROC.IDPROCEDIMIENTO = '" + pretensionItem.getIdPretension() + "'");
		}
		if(pretensionItem.getDescripcion() != null && pretensionItem.getDescripcion() != "") {
			sql.WHERE("UPPER(CATPRETENSION.DESCRIPCION) LIKE UPPER('%" + pretensionItem.getDescripcion() + "%')");
		}
	
		
		if(!pretensionItem.getHistorico())
			sql.WHERE("fechabaja is null");
		
		sql.ORDER_BY("CATPRETENSION.DESCRIPCION"); 
	
		return sql.toString();
	}
	

	public String getIdPretension(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDPRETENSION) AS IDPRETENSION");
		sql.FROM("SCS_PRETENSION");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

}
