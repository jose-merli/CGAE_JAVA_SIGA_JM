package org.itcgae.siga.db.services.com.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;

public class ConListadoModelosExtendsSqlProvider {
	public String selectListadoModelos(Short idInstitucion, String idConsulta) {

		SQL sql = new SQL();
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		// TODO: Consulta real
//		SELECT IDMODELOCOMUNICACION,NOMBRE,ORDEN,IDINSTITUCION,DESCRIPCION,PRESELECCIONAR,IDCLASECOMUNICACION
//		FROM MOD_MODELOCOMUNICACION 
//		WHERE IDMODELOCOMUNICACION=(
//				SELECT IDMODELOCOMUNICACION 
//				FROM MOD_PLANTILLADOC_CONSULTA 
//				WHERE IDINSTITUCION='2003' 
//				AND IDCONSULTA='247' 
//				AND FECHABAJA IS NULL) 
//		AND IDINSTITUCION='2003' 
//		AND FECHABAJA IS NULL;
		
		sql.SELECT("MODELO.IDMODELOCOMUNICACION");
		sql.SELECT("MODELO.NOMBRE");
		sql.SELECT("MODELO.VISIBLE");
		sql.SELECT("MODELO.ORDEN");
		sql.SELECT("MODELO.IDINSTITUCION");
		sql.SELECT("MODELO.DESCRIPCION");
		sql.SELECT("MODELO.FECHABAJA");
		sql.SELECT("MODELO.PRESELECCIONAR");
		sql.SELECT("MODELO.IDCLASECOMUNICACION");		
		sql.SELECT("CLASE.NOMBRE AS NOMBRECLASE");
		sql.SELECT("INST.ABREVIATURA");
		
		sql.FROM("MOD_MODELOCOMUNICACION MODELO");		
		
		sql.INNER_JOIN("MOD_CLASECOMUNICACIONES CLASE ON modelo.IDCLASECOMUNICACION = CLASE.IDCLASECOMUNICACION");
		sql.INNER_JOIN("CEN_INSTITUCION INST ON INST.IDINSTITUCION = MODELO.IDINSTITUCION");
		
		sql.WHERE("MODELO.IDMODELOCOMUNICACION IN (SELECT IDMODELOCOMUNICACION FROM MOD_PLANTILLADOC_CONSULTA WHERE IDINSTITUCION='"+idInstitucion+"' AND IDCONSULTA='"+idConsulta+"' AND FECHABAJA IS NULL)");
		sql.WHERE("MODELO.IDINSTITUCION='"+idInstitucion+"'");
		sql.WHERE("FECHABAJA IS NULL");
		return sql.toString();
	}


}
