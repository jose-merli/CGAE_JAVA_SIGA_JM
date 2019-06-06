package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ModPlantillaDocumentoExtendsSqlProvider {
	
	public String selectPlantillasByInforme(Long idInforme, Long idModeloComunicacion, String idLenguaje){
		
		SQL sql = new SQL();		
				
		sql.SELECT("plantillaDocumento.IDPLANTILLADOCUMENTO");
		sql.SELECT("plantillaDocumento.IDIOMA");
		sql.SELECT("plantillaDocumento.PLANTILLA");
		sql.SELECT("modeloPlantillaDocumento.IDINFORME");
		sql.SELECT("C.DESCRIPCION");
		
		sql.FROM("MOD_PLANTILLADOCUMENTO plantillaDocumento");	
		sql.INNER_JOIN("MOD_MODELO_PLANTILLADOCUMENTO modeloPlantillaDocumento ON modeloPlantillaDocumento.IDPLANTILLADOCUMENTO = plantillaDocumento.IDPLANTILLADOCUMENTO AND modeloPlantillaDocumento.IDINFORME = " + idInforme + " AND modeloPlantillaDocumento.IDMODELOCOMUNICACION = " + idModeloComunicacion);
		sql.INNER_JOIN("ADM_LENGUAJES lenguajes ON lenguajes.idlenguaje = plantillaDocumento.Idioma");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS C on lenguajes.DESCRIPCION = C.idRecurso");
		sql.WHERE("C.IDLENGUAJE = '" + idLenguaje + "' AND CODIGOEJIS is not null AND lenguajes.FECHA_BAJA is null AND modeloPlantillaDocumento.FECHABAJA IS NULL");
		
		return sql.toString();
	}
	
	public String selectPlantillasByModelo(Long idModeloComunicacion, String idLenguaje){
		
		SQL sql = new SQL();		
				
		sql.SELECT("plantillaDocumento.IDPLANTILLADOCUMENTO");
		sql.SELECT("plantillaDocumento.PLANTILLA");
		sql.SELECT("modeloPlantillaDocumento.IDINFORME");
		
		sql.FROM("MOD_PLANTILLADOCUMENTO plantillaDocumento");	
		sql.INNER_JOIN("MOD_MODELO_PLANTILLADOCUMENTO modeloPlantillaDocumento ON modeloPlantillaDocumento.IDPLANTILLADOCUMENTO = plantillaDocumento.IDPLANTILLADOCUMENTO AND modeloPlantillaDocumento.IDMODELOCOMUNICACION = " + idModeloComunicacion);
		sql.WHERE("(plantillaDocumento.idioma = '" + idLenguaje + "' or modeloPlantillaDocumento.Generacionexcel = 1) AND modeloPlantillaDocumento.FECHABAJA IS NULL");
		
		return sql.toString();
	}
	
	public String selectMaxIdPlantillaDocumento() {

		SQL sql = new SQL();

		sql.SELECT("max(idplantilladocumento) as IDPLANTILLADOCUMENTO");
		sql.FROM("MOD_PLANTILLADOCUMENTO");
		return sql.toString();
	}
}
