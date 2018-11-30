package org.itcgae.siga.db.services.com.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;

public class EnvEnviosExtendsSqlProvider {
	
	public String selectEnvios(Short idInstitucion, EnviosMasivosSearch filtros){
		
		SQL sql = new SQL();
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				
		sql.SELECT("ENVIO.IDINSTITUCION");
		sql.SELECT("ENVIO.IDENVIO");
		sql.SELECT("ENVIO.DESCRIPCION");
		sql.SELECT("ENVIO.FECHA AS FECHACREACION");
		sql.SELECT("ENVIO.IDPLANTILLAENVIOS");
		sql.SELECT("ENVIO.IDESTADO");
		sql.SELECT("ENVIO.IDTIPOENVIOS");
		sql.SELECT("ENVIO.IDPLANTILLA");
		sql.SELECT("ENVIO.FECHAPROGRAMADA");
		sql.SELECT("ENVIO.FECHABAJA");
		sql.SELECT("PLANTILLA.ASUNTO");
		sql.SELECT("PLANTILLA.CUERPO");
		sql.SELECT("ETIQUETAS.IDGRUPO");
		
		sql.FROM("ENV_ENVIOS ENVIO");
		sql.JOIN("ENV_PLANTILLASENVIOS PLANTILLA ON PLANTILLA.IDINSTITUCION = '" + idInstitucion + "' AND PLANTILLA.IDPLANTILLAENVIOS = ENVIO.IDPLANTILLAENVIOS AND PLANTILLA.IDTIPOENVIOS = ENVIO.IDTIPOENVIOS");
		sql.LEFT_OUTER_JOIN("ENV_ENVIOSGRUPOCLIENTE ETIQUETAS ON (ETIQUETAS.IDENVIO = ENVIO.IDENVIO)");
		
		
		sql.WHERE("ENVIO.IDINSTITUCION = '" + idInstitucion +"'");
		sql.WHERE("ENVIO.FECHABAJA IS NULL");
		
		if(filtros.getAsunto() != null && !filtros.getAsunto().trim().equals("")){
			sql.WHERE(filtroTextoBusquedas("DESCRIPCION",filtros.getAsunto()));
		}
		if(filtros.getidEstado() != null && !filtros.getidEstado().trim().equals("")){
			sql.WHERE("ENVIO.IDESTADO = '" + filtros.getidEstado() +"'");
		}
		if(filtros.getFechaCreacion() != null){
			String fechaCreacion = dateFormat.format(filtros.getFechaCreacion());
			String fechaCreacion2 = dateFormat.format(filtros.getFechaCreacion());
			fechaCreacion += " 01:00:00";
			fechaCreacion2 += " 12:59:59";
			sql.WHERE("(ENVIO.FECHA >= TO_DATE('" +fechaCreacion + "', 'DD/MM/YYYY HH:MI:SS') AND ENVIO.FECHA <= TO_DATE('" +fechaCreacion2 + "', 'DD/MM/YYYY HH:MI:SS'))");
		}
		if(filtros.getFechaProgramacion() != null){
			String fechaProgramacion = dateFormat.format(filtros.getFechaProgramacion());
			String fechaProgramacion2 = dateFormat.format(filtros.getFechaProgramacion());
			fechaProgramacion += " 01:00:00";
			fechaProgramacion2 += " 12:59:59";
			sql.WHERE("(ENVIO.FECHAPROGRAMADA >= TO_DATE('" + fechaProgramacion + "', 'DD/MM/YYYY HH:MI:SS') AND ENVIO.FECHAPROGRAMADA <= TO_DATE('" + fechaProgramacion2 + "', 'DD/MM/YYYY HH:MI:SS'))");
		}
		if(filtros.getidTipoEnvio() != null && !filtros.getidTipoEnvio().trim().equals("")){
			sql.WHERE("ENVIO.IDTIPOENVIOS = '" + filtros.getidTipoEnvio() +"'");
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
