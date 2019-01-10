package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.com.PlantillaEnvioSearchItem;

public class PlantillasEnvioExtendsSqlProvider {
	
	public String selectPlantillas(Short idInstitucion, PlantillaEnvioSearchItem filtros){
		SQL sql = new SQL();
		
		sql.SELECT("idPlantillaEnvios, idTipoEnvios, nombre, idInstitucion, acuseRecibo, fechaBaja,"
				+ "asunto, cuerpo, idDireccion, idPersona");
		sql.FROM("env_plantillasenvios");
		sql.WHERE("FECHABAJA is not null");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		
		if(!filtros.getTipoEnvio().trim().equals("") && filtros.getTipoEnvio() != null){
			sql.WHERE("IDTIPOENVIOS = '" + filtros.getTipoEnvio() +"'");
		}
		if(filtros.getNombre().trim().equals("") && filtros.getNombre() != null){
			sql.WHERE(filtroTextoBusquedas("NOMBRE", filtros.getNombre()));
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
