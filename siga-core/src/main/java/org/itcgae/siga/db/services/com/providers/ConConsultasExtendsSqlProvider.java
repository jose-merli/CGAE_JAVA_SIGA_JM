package org.itcgae.siga.db.services.com.providers;


import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.com.ConsultasSearch;

public class ConConsultasExtendsSqlProvider {
	
	public String selectConsultas(Short idInstitucion, String idLenguaje,ConsultasSearch filtros){
			
		SQL sql = new SQL();
				
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
			if(filtros.getGenerica().equals("N")){
				sql.WHERE("((CONSULTA.GENERAL = 'N' OR CONSULTA.GENERAL = 'n' OR CONSULTA.GENERAL = '0') AND CONSULTA.IDINSTITUCION = '" + idInstitucion +"')");
			}else if(filtros.getGenerica().equals("S")){
				sql.WHERE("((CONSULTA.GENERAL = 'S' OR CONSULTA.GENERAL = 's' OR  CONSULTA.GENERAL = '1') AND CONSULTA.IDINSTITUCION = '2000')");
			}
		}else {
			sql.WHERE("((CONSULTA.IDINSTITUCION = '2000' AND UPPER(CONSULTA.GENERAL) = 'S') OR (CONSULTA.IDINSTITUCION = '" + idInstitucion +"' AND UPPER(CONSULTA.GENERAL) = 'N'))");
		}
		
		return sql.toString();
	}

	
	public String selectMaxIdConsulta(){
		SQL sql = new SQL();
		sql.SELECT("max(idconsulta)+1 as IDMAX");
		sql.FROM("CON_CONSULTA");
		return sql.toString();
	}

	public String selectConsultasPlantilla(Short idInstitucion, String idPlantillaEnvios, String idtipoEnvio, String idlenguaje) {
		SQL sql = new SQL();
		sql.SELECT("cc.IDINSTITUCION");
		sql.SELECT("cc.IDCONSULTA");
		sql.SELECT("cc.GENERAL");
		sql.SELECT("cc.DESCRIPCION");
		sql.SELECT("cc.OBSERVACIONES");
		sql.SELECT("cc.TIPOCONSULTA");
		sql.SELECT("CM.IDMODULO");
		sql.SELECT("cc.IDCLASECOMUNICACION");
		sql.SELECT("cc.IDOBJETIVO");
		sql.SELECT("cc.SENTENCIA");
		sql.SELECT("(SELECT REC.DESCRIPCION FROM CON_OBJETIVO OBJETIVO JOIN GEN_RECURSOS_CATALOGOS REC ON REC.IDRECURSO = OBJETIVO.NOMBRE AND REC.IDLENGUAJE = '"+idlenguaje+""
					+ "' WHERE OBJETIVO.IDOBJETIVO = cc.IDOBJETIVO) AS OBJETIVO");

		sql.FROM("con_consulta cc");
		sql.LEFT_OUTER_JOIN("con_modulo cm on cc.idmodulo = cm.idmodulo");
		sql.INNER_JOIN("mod_plantillaenvio_consulta mpc on cc.idconsulta = mpc.idconsulta");

		sql.WHERE("mpc.idplantillaenvios='" + idPlantillaEnvios + "' AND mpc.idtipoenvios ='" + idtipoEnvio + "' AND mpc.FECHABAJA is null and cc.IDINSTITUCION = '" + idInstitucion + "'");
		return sql.toString();
	}
	
	public String selectConsultasDisponibles(Short idInstitucion, Long idClaseComunicacion, Long idObjetivo){
		
		SQL sql = new SQL();
		sql.SELECT_DISTINCT("IDCONSULTA, DESCRIPCION");
		sql.FROM("CON_CONSULTA");
		sql.WHERE("(IDINSTITUCION = "+ idInstitucion + " OR (IDINSTITUCION = '2000' AND (UPPER(GENERAL) = 'S' OR GENERAL = '1'))) AND FECHABAJA IS NULL");
		
		if(idClaseComunicacion != null){
			sql.WHERE("IDCLASECOMUNICACION = "+ idClaseComunicacion);
		}
		
		if(idObjetivo != null){
			sql.WHERE("IDOBJETIVO = " + idObjetivo);
		}
		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
	}
	
	public String selectConsultasDisponiblesPlantillasEnvio(Short idInstitucion){
		
		SQL sql = new SQL();
		sql.SELECT_DISTINCT("IDCONSULTA, DESCRIPCION");
		sql.FROM("CON_CONSULTA");
		sql.WHERE("(IDINSTITUCION = "+ idInstitucion + " OR (IDINSTITUCION = '2000' AND (UPPER(GENERAL) = 'S' OR GENERAL = '1'))) AND FECHABAJA IS NULL");
		
		sql.WHERE("IDOBJETIVO IN (1,4)");

		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
	}
	public String selectObjetivo(String idObjetivo, String idLenguaje){
		
		SQL sql = new SQL();
		sql.SELECT("REC.DESCRIPCION");
		sql.FROM("CON_OBJETIVO OBJETIVO");
		sql.JOIN("GEN_RECURSOS_CATALOGOS REC ON REC.IDRECURSO = OBJETIVO.NOMBRE AND REC.IDLENGUAJE = '"+idLenguaje+"'");
		sql.WHERE("OBJETIVO.IDOBJETIVO = '"+idObjetivo+"'");
		
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
