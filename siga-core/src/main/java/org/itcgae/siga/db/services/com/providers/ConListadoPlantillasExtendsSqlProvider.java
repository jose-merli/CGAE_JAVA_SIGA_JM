package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ConListadoPlantillasExtendsSqlProvider {

	public String selectListadoPlantillas(Short idInstitucion, String idLenguaje, String idConsulta) {

		SQL sql = new SQL();
	
		// TODO: Consulta real
//		SELECT ENVPE.IDTIPOENVIOS,
//		(SELECT rec.DESCRIPCION
//				FROM ENV_TIPOENVIOS tipo
//				INNER JOIN GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipo.NOMBRE AND rec.IDLENGUAJE = '1')
//				WHERE IDTIPOENVIOS=ENVPE.IDTIPOENVIOS) AS TIPOENVIO,
//		ENVPE.IDPLANTILLAENVIOS,ENVPE.NOMBRE,ENVPE.ACUSERECIBO 
//		FROM ENV_PLANTILLASENVIOS ENVPE, 
//		(SELECT IDPLANTILLAENVIOS,IDINSTITUCION,IDTIPOENVIOS FROM MOD_PLANTILLAENVIO_CONSULTA WHERE IDINSTITUCION='2003' AND IDCONSULTA='247' AND FECHABAJA IS NULL) MODPC 
//				WHERE ENVPE.IDPLANTILLAENVIOS=MODPC.IDPLANTILLAENVIOS
//				AND ENVPE.IDINSTITUCION=MODPC.IDINSTITUCION
//				AND ENVPE.IDTIPOENVIOS=MODPC.IDTIPOENVIOS; 
		
		sql.SELECT("ENVPE.IDTIPOENVIOS");
		sql.SELECT("(SELECT rec.DESCRIPCION FROM ENV_TIPOENVIOS tipo INNER JOIN GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipo.NOMBRE AND rec.IDLENGUAJE = '"+idLenguaje+"')WHERE IDTIPOENVIOS=ENVPE.IDTIPOENVIOS) AS TIPOENVIO");
		sql.SELECT("ENVPE.IDPLANTILLAENVIOS");
		sql.SELECT("ENVPE.NOMBRE");
		sql.SELECT("ENVPE.ACUSERECIBO");
		sql.FROM("ENV_PLANTILLASENVIOS ENVPE");
		sql.FROM("(SELECT IDPLANTILLAENVIOS,IDINSTITUCION,IDTIPOENVIOS FROM MOD_PLANTILLAENVIO_CONSULTA WHERE IDINSTITUCION='"+idInstitucion+"' AND IDCONSULTA='"+idConsulta+"' AND FECHABAJA IS NULL) MODPC");
		sql.WHERE("ENVPE.IDPLANTILLAENVIOS=MODPC.IDPLANTILLAENVIOS");
		sql.AND();
		sql.WHERE("ENVPE.IDINSTITUCION=MODPC.IDINSTITUCION");
		sql.AND();
		sql.WHERE("ENVPE.IDTIPOENVIOS=MODPC.IDTIPOENVIOS");
		
		return sql.toString();
	}

}
