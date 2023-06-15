package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.com.PlantillaEnvioSearchItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosSqlProvider;

public class EnvPlantillaEnviosExtendsSqlProvider extends EnvPlantillasenviosSqlProvider {
	
	
	public String selectPlantillas(Short idInstitucion, String idLenguaje, PlantillaEnvioSearchItem filtros){
		SQL sql = new SQL();
		
		sql.SELECT("PLANTILLA.idPlantillaEnvios, PLANTILLA.idTipoEnvios, PLANTILLA.nombre, PLANTILLA.idInstitucion, PLANTILLA.acuseRecibo,PLANTILLA.fechaBaja, PLANTILLA.asunto, PLANTILLA.cuerpo");
		sql.SELECT("PLANTILLA.idDireccion, PLANTILLA.idPersona, PLANTILLA.DESCRIPCION");
		sql.SELECT("(SELECT CAT.DESCRIPCION FROM ENV_TIPOENVIOS LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = ENV_TIPOENVIOS.NOMBRE WHERE ENV_TIPOENVIOS.IDTIPOENVIOS = "
				+ "PLANTILLA.IDTIPOENVIOS AND CAT.IDLENGUAJE = '" + idLenguaje + "') AS TIPOENVIO");
		sql.SELECT("clase.nombre as clasecomunicacion");
		sql.FROM("ENV_PLANTILLASENVIOS PLANTILLA");
		sql.LEFT_OUTER_JOIN("mod_clasecomunicaciones clase on plantilla.idclasecomunicacion = clase.idclasecomunicacion");
		sql.WHERE("PLANTILLA.FECHABAJA is null");
		sql.WHERE("PLANTILLA.IDINSTITUCION = '"+ idInstitucion +"' AND PLANTILLA.ANTIGUA = 'N'");
		

		if(filtros.getIdTipoEnvios() != null && !filtros.getIdTipoEnvios().trim().equals("")){
			sql.WHERE("PLANTILLA.IDTIPOENVIOS = '" + filtros.getIdTipoEnvios() +"'");
		}
		if(filtros.getNombre() != null && !filtros.getNombre().trim().equals("") ){
			sql.WHERE(filtroTextoBusquedas("PLANTILLA.NOMBRE", filtros.getNombre()));
		}	
		
		return sql.toString();
	}
	
	
	
	public String getPlantillasByIdTipoEnvio(Short idInstitucion, String idTipoEnvio){
		
		SQL sql = new SQL();
		
		sql.SELECT("TO_CHAR(IDPLANTILLAENVIOS) AS IDPLANTILLAENVIOS");
		sql.SELECT("NOMBRE");
		
		sql.FROM("ENV_PLANTILLASENVIOS");
		
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "' AND ANTIGUA = 'N' AND FECHABAJA is NULL");
		sql.WHERE("IDTIPOENVIOS = '" + idTipoEnvio + "'");
		sql.WHERE("IDCLASECOMUNICACION is null");
		sql.ORDER_BY("NOMBRE");
		return sql.toString();
	}
	
	public String getPlantillasComunicacion(Short idInstitucion, String idClaseComunicacion){
		
		SQL sql = new SQL();
		sql.SELECT("IDPLANTILLAENVIOS AS VALUE");
		sql.SELECT("INITCAP(NOMBRE) AS LABEL");		
		sql.FROM("ENV_PLANTILLASENVIOS");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "' AND ANTIGUA = 'N' AND FECHABAJA is NULL");
		
		if(!UtilidadesString.esCadenaVacia(idClaseComunicacion)) {
			sql.WHERE("(IDCLASECOMUNICACION = '" + idClaseComunicacion  + "' or IDCLASECOMUNICACION is null)");
		}
		
		sql.ORDER_BY("LABEL");
		
		return sql.toString();
	}
	
	public String getTipoEnvioPlantilla (Short idInstitucion, String idPlantilla, String idLenguaje){
		
		SQL sql = new SQL();
		
		sql.SELECT("cat.DESCRIPCION, plantilla.idtipoEnvios, plantilla.IDPERSONA");
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
		sql.WHERE("PLANTILLA.fechabaja is null");
		
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

	public String getTemplates(String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("IDPLANTILLAENVIOS");
		sql.SELECT("INITCAP(NOMBRE) AS NOMBRE");
		sql.SELECT("IDTIPOENVIOS");
		sql.FROM("ENV_PLANTILLASENVIOS");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.ORDER_BY("NOMBRE");
		
		return sql.toString();
	}
	
	public String getPlantillasByIdInstitucion(String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("IDPLANTILLAENVIOS");
		sql.SELECT("NOMBRE");
		sql.SELECT("IDTIPOENVIOS");
		sql.FROM("ENV_PLANTILLASENVIOS");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.ORDER_BY("NOMBRE");
		
		return sql.toString();
	}

	public String comboPlantillasEnvio(Short idInstitucion) {
		SQL sql = new SQL();

		// Select
		sql.SELECT_DISTINCT("e.idplantillaenvios");
		sql.SELECT_DISTINCT("e.nombre");

		// From
		sql.FROM("env_plantillasenvios e");

		// Where
		sql.WHERE("e.idinstitucion = " + idInstitucion);
		sql.WHERE("e.antigua = 'N'");
		sql.WHERE("e.idtipoenvios = ( SELECT  idtipoenvios FROM env_tipoenvios WHERE upper(f_siga_getrecurso(nombre, 1)) LIKE '%ELEC%NICO%' )");
		sql.WHERE("fechabaja IS NULL");

		// Order by
		sql.ORDER_BY("e.nombre");


		return sql.toString();
	}
}
