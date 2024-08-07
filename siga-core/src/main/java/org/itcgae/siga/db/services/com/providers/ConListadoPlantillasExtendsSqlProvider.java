package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ConListadoPlantillasExtendsSqlProvider {

	public String selectListadoPlantillas(Short idInstitucion, String idLenguaje, String idConsulta) {

		SQL sql = new SQL();
		
		sql.SELECT("ENVPE.IDTIPOENVIOS");
		sql.SELECT("(SELECT rec.DESCRIPCION FROM ENV_TIPOENVIOS tipo INNER JOIN GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipo.NOMBRE AND rec.IDLENGUAJE = '"+idLenguaje+"')WHERE IDTIPOENVIOS=ENVPE.IDTIPOENVIOS) AS TIPOENVIO");
		sql.SELECT("ENVPE.IDPLANTILLAENVIOS");
		sql.SELECT("ENVPE.NOMBRE");
		sql.SELECT("ENVPE.ACUSERECIBO");
		sql.SELECT("ENVPE.FECHABAJA");
		sql.SELECT("ENVPE.ASUNTO");
		sql.SELECT("ENVPE.CUERPO");
		sql.SELECT("ENVPE.IDDIRECCION");
		sql.SELECT("ENVPE.IDPERSONA");
		sql.SELECT("ENVPE.DESCRIPCION");
		sql.SELECT("ENVPE.IDINSTITUCION");
		
		sql.FROM("ENV_PLANTILLASENVIOS ENVPE");
		sql.FROM("(SELECT IDPLANTILLAENVIOS,IDINSTITUCION,IDTIPOENVIOS FROM MOD_PLANTILLAENVIO_CONSULTA WHERE (IDINSTITUCION='"+idInstitucion+"' OR IDINSTITUCION = 2000) AND IDCONSULTA='"+idConsulta+"' AND FECHABAJA IS NULL) MODPC");
		sql.WHERE("ENVPE.IDPLANTILLAENVIOS=MODPC.IDPLANTILLAENVIOS");
		sql.AND();
		sql.WHERE("ENVPE.IDINSTITUCION=MODPC.IDINSTITUCION AND ENVPE.ANTIGUA = 'N'");
		sql.AND();
		sql.WHERE("ENVPE.IDTIPOENVIOS=MODPC.IDTIPOENVIOS");
		
		return sql.toString();
	}

}
