package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.CenPoblacionesSqlProvider;

public class CenPoblacionesSqlExtendsProvider extends CenPoblacionesSqlProvider{

	public String getComboPoblaciones(String idProvincia) {
		
		SQL sql = new SQL();

		sql.SELECT("IDPOBLACION");
		sql.SELECT("NOMBRE");
		sql.FROM("CEN_POBLACIONES");
		sql.WHERE("IDPROVINCIA ='" + idProvincia + "'");
		sql.ORDER_BY("NOMBRE");

		return sql.toString();
		
	}
	
	public String selectByFilter(String filtro, String idProvincia) {
		
		SQL sql = new SQL();

		sql.SELECT("IDPOBLACION");
		sql.SELECT("NOMBRE");
		sql.SELECT("IDPROVINCIA");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDPARTIDO");
		sql.SELECT("CODIGOEXT");
		sql.SELECT("INE");
		sql.SELECT("IDPOBLACIONMUNICIPIO");
		sql.SELECT("PRIORIDAD");	
		
		sql.FROM("CEN_POBLACIONES");
		if(!UtilidadesString.esCadenaVacia(idProvincia)) {
			sql.WHERE("IDPROVINCIA ='" + idProvincia + "'");
		}
		sql.WHERE(filtroTextoBusquedas("NOMBRE", filtro));
		sql.ORDER_BY("PRIORIDAD, NOMBRE");

		return sql.toString();
		
	}
	
		public static String filtroTextoBusquedas(String columna, String cadena) {
				StringBuilder cadenaWhere = new StringBuilder();
				cadenaWhere.append(" (TRANSLATE(LOWER( " + columna + "),'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž','AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz') ");
				cadenaWhere.append(" LIKE");
				cadenaWhere.append(" TRANSLATE(LOWER('%" + cadena + "%'),'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž','AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz')) ");
				return cadenaWhere.toString();
				
			} 
		 

}
