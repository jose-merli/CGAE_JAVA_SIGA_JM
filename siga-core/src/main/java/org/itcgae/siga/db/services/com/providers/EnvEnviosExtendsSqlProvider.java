package org.itcgae.siga.db.services.com.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;

public class EnvEnviosExtendsSqlProvider {
	
	public String selectEnvios(Short idInstitucion, EnviosMasivosSearch filtros){
		
		SQL sql = new SQL();
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("IDENVIO");
		sql.SELECT("DESCRIPCION");
		sql.SELECT("FECHA");
		sql.SELECT("GENERARDOCUMENTO");
		sql.SELECT("IMPRIMIRETIQUETAS");
		sql.SELECT("IDPLANTILLAENVIOS");
		sql.SELECT("IDESTADO");
		sql.SELECT("IDTIPOENVIOS");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDPLANTILLA");
		sql.SELECT("IDIMPRESORA");
		sql.SELECT("FECHAPROGRAMADA");
		sql.SELECT("CONSULTA");
		sql.SELECT("ACUSERECIBO");
		sql.SELECT("IDTIPOINTERCAMBIOTELEMATICO");
		sql.SELECT("COMISIONAJG");
		sql.SELECT("FECHABAJA");
		sql.SELECT("CSV");
		sql.SELECT("IDSOLICITUDECOS");
		
		sql.FROM("ENV_ENVIOS");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion +"'");
		
		
		if(filtros.getAsunto() != null){
			sql.WHERE(filtroTextoBusquedas("DESCRIPCION",filtros.getAsunto()));
		}
		if(filtros.getEstado() != null){
			sql.WHERE("IDESTADO = '" + filtros.getEstado() +"'");
		}
		if(filtros.getFechaCreacion() != null){
			String fechaCreacion = dateFormat.format(filtros.getFechaCreacion());
			sql.WHERE("FECHA = TO_DATE('" +fechaCreacion + "', 'DD/MM/YYYY')");
		}
		if(filtros.getFechaProgramacion() != null){
			String fechaProgramacion = dateFormat.format(filtros.getFechaProgramacion());
			sql.WHERE("FECHA = TO_DATE('" + fechaProgramacion + "', 'DD/MM/YYYY')");
		}
		if(filtros.getTipoEnvio() != null){
			sql.WHERE("IDTIPOENVIOS = '" + filtros.getEstado() +"'");
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
