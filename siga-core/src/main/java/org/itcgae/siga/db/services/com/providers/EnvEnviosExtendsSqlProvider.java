package org.itcgae.siga.db.services.com.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;

public class EnvEnviosExtendsSqlProvider {
	
	public String selectEnvios(Short idInstitucion, String idLenguaje,EnviosMasivosSearch filtros){
		
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
		sql.SELECT("(SELECT NOMBRE FROM ENV_PLANTILLASENVIOS WHERE IDINSTITUCION = '" + idInstitucion + "' AND IDPLANTILLAENVIOS = ENVIO.IDPLANTILLAENVIOS AND IDTIPOENVIOS = ENVIO.IDTIPOENVIOS) AS NOMBREPLANTILLA");
		sql.SELECT("ENVIO.IDPLANTILLA");
		sql.SELECT("ENVIO.FECHAPROGRAMADA");
		sql.SELECT("ENVIO.FECHABAJA");
		sql.SELECT("PLANTILLA.ASUNTO");
		sql.SELECT("PLANTILLA.CUERPO");
		sql.SELECT("(SELECT CAT.DESCRIPCION FROM ENV_TIPOENVIOS LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = ENV_TIPOENVIOS.NOMBRE WHERE ENV_TIPOENVIOS.IDTIPOENVIOS = ENVIO.IDTIPOENVIOS AND CAT.IDLENGUAJE = '"+ idLenguaje +"') AS TIPOENVIO");
		sql.SELECT("(SELECT CAT.DESCRIPCION FROM ENV_ESTADOENVIO ESTADO LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = ESTADO.NOMBRE WHERE ESTADO.IDESTADO = ENVIO.IDESTADO AND CAT.IDLENGUAJE = '"+ idLenguaje +"') AS ESTADOENVIO");
		
		sql.FROM("ENV_ENVIOS ENVIO");
		sql.JOIN("ENV_PLANTILLASENVIOS PLANTILLA ON PLANTILLA.IDINSTITUCION = '" + idInstitucion + "' AND PLANTILLA.IDPLANTILLAENVIOS = ENVIO.IDPLANTILLAENVIOS AND PLANTILLA.IDTIPOENVIOS = ENVIO.IDTIPOENVIOS");
		
		
		sql.WHERE("ENVIO.IDINSTITUCION = '" + idInstitucion +"'");
		
		//con este campo controlamos que sea de envios Masivos
		sql.WHERE("ENVIO.ENVIO = 'M'");
		
		if(filtros.getDescripcion() != null && !filtros.getDescripcion().trim().equals("")){
			sql.WHERE(filtroTextoBusquedas("ENVIO.DESCRIPCION",filtros.getDescripcion()));
		}
		if(filtros.getidEstado() != null && !filtros.getidEstado().trim().equals("")){
			sql.WHERE("ENVIO.IDESTADO = '" + filtros.getidEstado() +"'");
			if(filtros.getidEstado().equals("6")){
				sql.WHERE("ENVIO.FECHABAJA IS NOT NULL");
			}else{
				sql.WHERE("ENVIO.FECHABAJA IS NULL");
			}
		}else{
			sql.WHERE("ENVIO.FECHABAJA IS NULL");
		}
		if(filtros.getFechaCreacion() != null){
			String fechaCreacion = dateFormat.format(filtros.getFechaCreacion());
//			String fechaCreacion2 = dateFormat.format(filtros.getFechaCreacion());
			fechaCreacion += " 00:00:00";
//			fechaCreacion2 += " 23:59:59";
			sql.WHERE("(ENVIO.FECHA >= TO_DATE('" +fechaCreacion + "', 'DD/MM/YYYY HH24:MI:SS'))");
		}
		if(filtros.getFechaProgramacion() != null){
			String fechaProgramacion = dateFormat.format(filtros.getFechaProgramacion());
			String fechaProgramacion2 = dateFormat.format(filtros.getFechaProgramacion());
			fechaProgramacion += " 00:00:00";
			fechaProgramacion2 += " 23:59:59";
			sql.WHERE("(ENVIO.FECHAPROGRAMADA >= TO_DATE('" + fechaProgramacion + "', 'DD/MM/YYYY HH24:MI:SS') AND ENVIO.FECHAPROGRAMADA <= TO_DATE('" + fechaProgramacion2 + "', 'DD/MM/YYYY HH24:MI:SS'))");
		}
		if(filtros.getidTipoEnvios() != null && !filtros.getidTipoEnvios().trim().equals("")){
			sql.WHERE("ENVIO.IDTIPOENVIOS = '" + filtros.getidTipoEnvios() +"'");
		}
		
		
		return sql.toString();
	}
	
	public String selectMaxIDEnvio(){
		
		SQL sql = new SQL();
		sql.SELECT("MAX(IDENVIO)+1 AS IDMAX");
		sql.FROM("ENV_ENVIOS");
		return sql.toString();
	}
	
	public String selectEnviosComunicacion(Short idInstitucion, String idLenguaje,EnviosMasivosSearch filtros){
		
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
		sql.SELECT("(SELECT NOMBRE FROM ENV_PLANTILLASENVIOS WHERE IDINSTITUCION = '" + idInstitucion + "' AND IDPLANTILLAENVIOS = ENVIO.IDPLANTILLAENVIOS AND IDTIPOENVIOS = ENVIO.IDTIPOENVIOS) AS NOMBREPLANTILLA");
		sql.SELECT("ENVIO.IDPLANTILLA");
		sql.SELECT("ENVIO.FECHAPROGRAMADA");
		sql.SELECT("ENVIO.FECHABAJA");
		sql.SELECT("PLANTILLA.ASUNTO");
		sql.SELECT("PLANTILLA.CUERPO");
		sql.SELECT("(SELECT CAT.DESCRIPCION FROM ENV_TIPOENVIOS LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = ENV_TIPOENVIOS.NOMBRE WHERE ENV_TIPOENVIOS.IDTIPOENVIOS = ENVIO.IDTIPOENVIOS AND CAT.IDLENGUAJE = '"+ idLenguaje +"') AS TIPOENVIO");
		sql.SELECT("(SELECT CAT.DESCRIPCION FROM ENV_ESTADOENVIO ESTADO LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = ESTADO.NOMBRE WHERE ESTADO.IDESTADO = ENVIO.IDESTADO AND CAT.IDLENGUAJE = '"+ idLenguaje +"') AS ESTADOENVIO");
		
		sql.FROM("ENV_ENVIOS ENVIO");
		sql.JOIN("ENV_PLANTILLASENVIOS PLANTILLA ON PLANTILLA.IDINSTITUCION = '" + idInstitucion + "' AND PLANTILLA.IDPLANTILLAENVIOS = ENVIO.IDPLANTILLAENVIOS AND PLANTILLA.IDTIPOENVIOS = ENVIO.IDTIPOENVIOS");
		
		
		sql.WHERE("ENVIO.IDINSTITUCION = '" + idInstitucion +"'");
		sql.WHERE("ENVIO.FECHABAJA IS NULL");
		//controlamos con este campo si es 'A' pertenece a comunicaciones
		sql.WHERE("ENVIO.ENVIO = 'A'");
		
		if(filtros.getDescripcion() != null && !filtros.getDescripcion().trim().equals("")){
			sql.WHERE(filtroTextoBusquedas("ENVIO.DESCRIPCION",filtros.getDescripcion()));
		}
		if(filtros.getidEstado() != null && !filtros.getidEstado().trim().equals("")){
			sql.WHERE("ENVIO.IDESTADO = '" + filtros.getidEstado() +"'");
		}
		if(filtros.getIdClaseComunicacion() != null && !filtros.getIdClaseComunicacion().trim().equals("")){
			sql.WHERE("ENVIO.IDESTADO = '" + filtros.getidEstado() +"'");
		}
		if(filtros.getFechaCreacion() != null){
			String fechaCreacion = dateFormat.format(filtros.getFechaCreacion());
//			String fechaCreacion2 = dateFormat.format(filtros.getFechaCreacion());
			fechaCreacion += " 00:00:00";
//			fechaCreacion2 += " 23:59:59";
			sql.WHERE("(ENVIO.FECHA >= TO_DATE('" +fechaCreacion + "', 'DD/MM/YYYY HH24:MI:SS'))");
		}
		if(filtros.getFechaProgramacion() != null){
			String fechaProgramacion = dateFormat.format(filtros.getFechaProgramacion());
			String fechaProgramacion2 = dateFormat.format(filtros.getFechaProgramacion());
			fechaProgramacion += " 00:00:00";
			fechaProgramacion2 += " 23:59:59";
			sql.WHERE("(ENVIO.FECHAPROGRAMADA >= TO_DATE('" + fechaProgramacion + "', 'DD/MM/YYYY HH24:MI:SS') AND ENVIO.FECHAPROGRAMADA <= TO_DATE('" + fechaProgramacion2 + "', 'DD/MM/YYYY HH24:MI:SS'))");
		}
		if(filtros.getidTipoEnvios() != null && !filtros.getidTipoEnvios().trim().equals("")){
			sql.WHERE("ENVIO.IDTIPOENVIOS = '" + filtros.getidTipoEnvios() +"'");
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
