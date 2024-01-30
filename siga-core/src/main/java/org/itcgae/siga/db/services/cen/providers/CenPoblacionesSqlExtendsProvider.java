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

		sql.SELECT_DISTINCT("poblacion.IDPOBLACION");
		sql.SELECT("poblacion.NOMBRE");
		sql.SELECT("poblacion.IDPROVINCIA");
		sql.SELECT("poblacion.FECHAMODIFICACION");
		sql.SELECT("poblacion.USUMODIFICACION");
		sql.SELECT("poblacion.IDPARTIDO");
		sql.SELECT("poblacion.CODIGOEXT");
		sql.SELECT("poblacion.INE");
		sql.SELECT("poblacion.IDPOBLACIONMUNICIPIO");
		sql.SELECT("poblacion.PRIORIDAD");	
		
		sql.FROM("CEN_POBLACIONES poblacion");
		//sql.FROM("scs_juzgado juzgado");
		
		if(!UtilidadesString.esCadenaVacia(idProvincia)) {
			sql.WHERE("poblacion.IDPROVINCIA ='" + idProvincia + "'");
		}
		if(!filtro.equals("-1")) {
			sql.WHERE(filtroTextoBusquedas("poblacion.NOMBRE", filtro));
		}
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

	public String recuperaPoblacionPorDescripcion(String descripcion, String provincia) {
		SQL sql = new SQL();
		
		sql.SELECT("*");
		sql.FROM("CEN_POBLACIONES");
		sql.WHERE("UPPER(NOMBRE) LIKE UPPER('%" + descripcion +"%')");
		if(provincia != null && !provincia.isEmpty()) {
			sql.WHERE("IDPROVINCIA = " + provincia);
		}
		sql.ORDER_BY("IDPOBLACION ASC");

		return sql.toString();
	}

}
