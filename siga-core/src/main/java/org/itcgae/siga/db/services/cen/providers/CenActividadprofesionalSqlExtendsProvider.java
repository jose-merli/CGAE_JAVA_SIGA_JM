package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.db.mappers.CenActividadprofesionalSqlProvider;

public class CenActividadprofesionalSqlExtendsProvider extends CenActividadprofesionalSqlProvider{

	
	public String selectProfesionalActivities(String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("ACTIVIDAD.IDACTIVIDADPROFESIONAL");
		sql.SELECT("REC.DESCRIPCION");
		sql.FROM("CEN_ACTIVIDADPROFESIONAL ACTIVIDAD");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON (REC.IDRECURSO = ACTIVIDAD.DESCRIPCION " + 
				"		AND REC.IDLENGUAJE = '"+idLenguaje+"')");
		sql.WHERE("ACTIVIDAD.FECHA_BAJA IS NULL");
		sql.ORDER_BY("REC.DESCRIPCION");
		return sql.toString();
	}
	
	public String searchRegistryDataLegalPerson(PersonaJuridicaSearchDTO personaJuridicaSearchDTO) {
		SQL sql = new SQL();
		
		sql.SELECT("RESENA");
		sql.SELECT("OBJETOSOCIAL");
		sql.SELECT("NOPOLIZA");
		sql.SELECT("COMPANIASEG");
		sql.SELECT("NOCOL.ID_DATOS_REG");
		sql.SELECT("TO_CHAR(FECHANACIMIENTO,'DD/MM/YYYY') AS FECHANACIMIENTO");
		sql.SELECT("TO_CHAR(NOCOL.FECHAFIN,'DD/MM/YYYY') AS FECHAFIN");
		sql.SELECT("TO_CHAR(NOCOL.FECHA_BAJA,'DD/MM/YYYY') AS FECHABAJA");
		sql.SELECT("REG_MERC.NUM_REGISTRO"); 
		sql.SELECT("REG_MERC.IDENTIFICACION_REG");
		sql.SELECT("REG_MERC.FECHA_INSCRIPCION");
		sql.SELECT("REG_MERC.FECHA_CANCELACION");
		
		sql.FROM("CEN_NOCOLEGIADO  NOCOL");
		
		sql.INNER_JOIN("CEN_PERSONA PER ON PER.IDPERSONA = NOCOL.IDPERSONA");
		sql.INNER_JOIN("CEN_REG_MERCANTIL REG_MERC ON REG_MERC.ID_DATOS_REG = NOCOL.ID_DATOS_REG");
		
		sql.WHERE("NOCOL.IDPERSONA = '"+personaJuridicaSearchDTO.getIdPersona()+"'");
		sql.WHERE("NOCOL.IDINSTITUCION = '"+personaJuridicaSearchDTO.getIdInstitucion()+"'");
		
		return sql.toString();
	}
}
