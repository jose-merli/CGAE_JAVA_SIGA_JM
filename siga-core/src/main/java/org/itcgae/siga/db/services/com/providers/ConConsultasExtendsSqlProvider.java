package org.itcgae.siga.db.services.com.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.com.ConsultasSearch;

public class ConConsultasExtendsSqlProvider {
	
public String selectConsultas(Short idInstitucion, String idLenguaje,ConsultasSearch filtros){
		
		SQL sql = new SQL();
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				
		sql.SELECT("CONSULTA.IDINSTITUCION");
		sql.SELECT("CONSULTA.IDCONSULTA");
		sql.SELECT("CONSULTA.DESCRIPCION");
		sql.SELECT("DECODE(CONSULTA.GENERAL,'1','Si','S','Si','0','No','N','No',CONSULTA.GENERAL) AS GENERAL");
		sql.SELECT("CONSULTA.TIPOCONSULTA");
		sql.SELECT("CONSULTA.IDMODULO");
		sql.SELECT("(SELECT MODULO.NOMBRE FROM CON_MODULO MODULO WHERE MODULO.IDMODULO=CONSULTA.IDMODULO) AS NOMBREMODULO");
		sql.SELECT("CONSULTA.FECHAMODIFICACION");
		sql.SELECT("CONSULTA.USUMODIFICACION");
		sql.SELECT("CONSULTA.BASES");
		sql.SELECT("CONSULTA.SENTENCIA");
		sql.SELECT("CONSULTA.IDTABLA");
		sql.SELECT("CONSULTA.ESEXPERTA");
		sql.SELECT("CONSULTA.OBSERVACIONES");
		sql.SELECT("CONSULTA.FECHABAJA");
		sql.SELECT("CONSULTA.IDOBJETIVO");
		sql.SELECT("(SELECT DISTINCT rec.DESCRIPCION FROM CON_OBJETIVO objetivo INNER JOIN GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = objetivo.NOMBRE AND rec.IDLENGUAJE = '1') WHERE objetivo.IDOBJETIVO=CONSULTA.IDOBJETIVO) AS NOMBREOBJETIVO");
		sql.SELECT("CONSULTA.IDCLASE");
		sql.SELECT("CONSULTA.IDCLASECOMUNICACION");
		sql.SELECT("(SELECT CLASE.NOMBRE  FROM MOD_CLASECOMUNICACIONES CLASE WHERE CLASE.IDCLASECOMUNICACION=CONSULTA.IDCLASECOMUNICACION) AS NOMBRECLASE");
		sql.FROM("CON_CONSULTA CONSULTA");
		
	
		if(filtros.getIdClaseComunicacion() != null && !filtros.getIdClaseComunicacion().trim().equals("")){
			sql.WHERE("CONSULTA.IDCLASECOMUNICACION = '" + filtros.getIdClaseComunicacion() +"'");
		}
		if(filtros.getIdObjetivo() != null && !filtros.getIdObjetivo().trim().equals("")){
			sql.WHERE("CONSULTA.IDOBJETIVO = '" + filtros.getIdObjetivo() +"'");
		}
		sql.WHERE("CONSULTA.FECHABAJA IS NULL");
		
		if(filtros.getNombre() != null && !filtros.getNombre().trim().equals("")){
			sql.WHERE(filtroTextoBusquedas("CONSULTA.DESCRIPCION",filtros.getNombre()));
		}
		if(filtros.getDescripcion() != null && !filtros.getDescripcion().trim().equals("")){
			sql.WHERE(filtroTextoBusquedas("CONSULTA.OBSERVACIONES", filtros.getDescripcion()));
		}
		
		if(filtros.getIdModulo() != null && !filtros.getIdModulo().trim().equals("")){
			sql.WHERE("CONSULTA.IDMODULO = '" + filtros.getIdModulo() +"'");
		}
		
		if(filtros.getGenerica() != null && !filtros.getGenerica().trim().equals("")){
			if(filtros.getGenerica().equals("0")){
				sql.WHERE("(CONSULTA.GENERAL = 'N' OR CONSULTA.GENERAL = 'n'OR CONSULTA.GENERAL = '0' AND CONSULTA.IDINSTITUCION = '" + idInstitucion +"')");
			}else{
				sql.WHERE("(CONSULTA.GENERAL = 'S' OR CONSULTA.GENERAL = 's' OR  CONSULTA.GENERAL = '1' AND CONSULTA.IDINSTITUCION = '2000' OR CONSULTA.IDINSTITUCION = '" + idInstitucion +"')");
			}
		}
		
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
