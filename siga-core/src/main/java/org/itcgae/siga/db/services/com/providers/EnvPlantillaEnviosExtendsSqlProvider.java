package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.com.PlantillaEnvioSearchItem;
import org.itcgae.siga.commons.utils.UtilidadesString;

public class EnvPlantillaEnviosExtendsSqlProvider {
	
	
	public String selectPlantillas(Short idInstitucion, String idLenguaje, PlantillaEnvioSearchItem filtros){
		SQL sql = new SQL();
		
		sql.SELECT("PLANTILLA.idPlantillaEnvios, PLANTILLA.idTipoEnvios, PLANTILLA.nombre, PLANTILLA.idInstitucion, PLANTILLA.acuseRecibo,PLANTILLA.fechaBaja, PLANTILLA.asunto, PLANTILLA.cuerpo");
		sql.SELECT("PLANTILLA.idDireccion, PLANTILLA.idPersona, PLANTILLA.DESCRIPCION");
		sql.SELECT("(SELECT CAT.DESCRIPCION FROM ENV_TIPOENVIOS LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = ENV_TIPOENVIOS.NOMBRE WHERE ENV_TIPOENVIOS.IDTIPOENVIOS = "
				+ "PLANTILLA.IDTIPOENVIOS AND CAT.IDLENGUAJE = '" + idLenguaje + "') AS TIPOENVIO");
		sql.FROM("ENV_PLANTILLASENVIOS PLANTILLA");
		sql.WHERE("PLANTILLA.FECHABAJA is null");
		sql.WHERE("PLANTILLA.IDINSTITUCION = '"+ idInstitucion +"' AND ANTIGUA = 'N'");
		

		if(filtros.getIdTipoEnvios() != null && !filtros.getIdTipoEnvios().trim().equals("")){
			sql.WHERE("IDTIPOENVIOS = '" + filtros.getIdTipoEnvios() +"'");
		}
		if(filtros.getNombre() != null && !filtros.getNombre().trim().equals("") ){
			sql.WHERE(filtroTextoBusquedas("NOMBRE", filtros.getNombre()));
		}	
		
		return sql.toString();
	}
	
	
	
	public String getPlantillas(Short idInstitucion, String idTipoEnvio){
		
		SQL sql = new SQL();
		
		sql.SELECT("TO_CHAR(IDPLANTILLAENVIOS) AS IDPLANTILLAENVIOS");
		sql.SELECT("NOMBRE");
		
		sql.FROM("ENV_PLANTILLASENVIOS");
		
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "' AND ANTIGUA = 'N' AND FECHABAJA is NULL");
		sql.WHERE("IDTIPOENVIOS = '" + idTipoEnvio + "'");
		sql.ORDER_BY("NOMBRE");
		return sql.toString();
	}
	
	public String getPlantillasComunicacion(Short idInstitucion){
		
		SQL sql = new SQL();
		sql.SELECT("IDPLANTILLAENVIOS AS VALUE");
		sql.SELECT("INITCAP(NOMBRE) AS LABEL");		
		sql.FROM("ENV_PLANTILLASENVIOS");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "' AND ANTIGUA = 'N' AND FECHABAJA is NULL");
		sql.ORDER_BY("LABEL");
		
		return sql.toString();
	}
	
	public String getTipoEnvioPlantilla (Short idInstitucion, String idPlantilla, String idLenguaje){
		
		SQL sql = new SQL();
		
		sql.SELECT("cat.DESCRIPCION, plantilla.idtipoEnvios");
		sql.FROM("ENV_PLANTILLASENVIOS plantilla");
		sql.JOIN("env_tipoenvios tipo on tipo.IDTIPOENVIOS = plantilla.IDTIPOENVIOS");
		sql.JOIN("GEN_RECURSOS_CATALOGOS cat on cat.IDRECURSO = tipo.NOMBRE and cat.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("plantilla.idinstitucion ='"+ idInstitucion +"' and plantilla.IDPLANTILLAENVIOS = '"+ idPlantilla +"'");
		
		return sql.toString();
	}
	
	
	public String selectMaxIDPlantillaEnvio(){
		
		SQL sql = new SQL();
		sql.SELECT("MAX(IDPLANTILLAENVIOS)+1 AS IDMAX");
		sql.FROM("ENV_PLANTILLASENVIOS");
		return sql.toString();
	}
	
	public static String filtroTextoBusquedas(String columna, String cadena) {

		StringBuilder cadenaWhere = new StringBuilder();
		cadenaWhere.append(" (TRANSLATE(LOWER( " + columna + "),'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž','AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz') ");
		cadenaWhere.append(" LIKE");
		cadenaWhere.append(" TRANSLATE(LOWER('%" + cadena + "%'),'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž','AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz')) ");
		return cadenaWhere.toString();
		
	} 
	
	public String selectTipoEnvioPlantilla(Short idInstitucion, String idLenguaje, String idPlantilla){
		
		SQL sql = new SQL();
		
		sql.SELECT("CAT.DESCRIPCION");
		sql.SELECT("PLANTILLA.Idtipoenvios");
		
		sql.FROM("ENV_PLANTILLASENVIOS PLANTILLA");
		sql.INNER_JOIN("ENV_TIPOENVIOS TIPO ON PLANTILLA.Idtipoenvios = TIPO.Idtipoenvios");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = TIPO.NOMBRE AND CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("PLANTILLA.ANTIGUA = 'N' AND PLANTILLA.Idplantillaenvios = " + idPlantilla +" AND PLANTILLA.idinstitucion = "+ idInstitucion);
		
		return sql.toString();
	}
	
	public String selectPlantillaIdPlantilla(Short idInstitucion,  PlantillaEnvioSearchItem filtros,String idPlantilla){
		SQL sql = new SQL();
		
		sql.SELECT("PLANTILLA.idPlantillaEnvios, PLANTILLA.idTipoEnvios, PLANTILLA.nombre, PLANTILLA.idInstitucion, PLANTILLA.acuseRecibo,PLANTILLA.fechaBaja, PLANTILLA.asunto, PLANTILLA.cuerpo");
		sql.SELECT("PLANTILLA.idDireccion, PLANTILLA.idPersona, PLANTILLA.DESCRIPCION");
		sql.FROM("ENV_PLANTILLASENVIOS PLANTILLA");
		sql.WHERE("PLANTILLA.FECHABAJA is null");
		sql.WHERE("PLANTILLA.IDINSTITUCION = '"+ idInstitucion +"'");
		

		if(filtros.getIdTipoEnvios() != null && !filtros.getIdTipoEnvios().trim().equals("")){
			sql.WHERE("IDTIPOENVIOS = '" + filtros.getIdTipoEnvios() +"'");
		}
		if(filtros.getNombre() != null && !filtros.getNombre().trim().equals("") ){
			sql.WHERE(filtroTextoBusquedas("NOMBRE", filtros.getNombre()));
		}
		if(!UtilidadesString.esCadenaVacia(idPlantilla)){
			sql.WHERE("idPlantillaEnvios = '" + idPlantilla +"'");
		}
		
		return sql.toString();
	}

}
