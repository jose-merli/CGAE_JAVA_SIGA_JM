package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenDatoscvSqlProvider;

public class CenDatoscvSqlExtendsProvider extends CenDatoscvSqlProvider{
	
	public String searchDatosCurriculares(String idPersona, String idInstitucion) {
		SQL sql = new SQL();
		SQL sql1 = new SQL();
		
		sql.SELECT("TO_CHAR(FECHAINICIO,'DD/MM/YYYY') AS FECHAINICIO");
		sql.SELECT("TO_CHAR(FECHAFIN,'DD/MM/YYYY') AS FECHAFIN");
		
		sql1.SELECT("F_SIGA_GETRECURSO(DESCRIPCION,1) AS LABEL FROM CEN_TIPOSCV E WHERE IDTIPOCV = DATOS.IDTIPOCV");
		
		sql.SELECT("DATOS.DESCRIPCION, (" + sql1 + ")  AS CATEGORIACURRICULAR");
		
		
		sql.SELECT("CONCAT((SELECT F_SIGA_GETRECURSO(DESCRIPCION,1) AS LABEL FROM CEN_TIPOSCVSUBTIPO1 E WHERE IDTIPOCVSUBTIPO1 = DATOS.IDTIPOCVSUBTIPO1 AND TIPOS.IDTIPOCV = IDTIPOCV AND DATOS.IDINSTITUCION = IDINSTITUCION) || ' - ' , ((SELECT F_SIGA_GETRECURSO(DESCRIPCION,1) AS LABEL FROM CEN_TIPOSCVSUBTIPO2 E WHERE IDTIPOCVSUBTIPO2 = DATOS.IDTIPOCVSUBTIPO2 AND TIPOS.IDTIPOCV = IDTIPOCV AND DATOS.IDINSTITUCION = IDINSTITUCION) )) AS TIPOSUBTIPO");
		sql.SELECT("DATOS.IDTIPOCV");
		sql.SELECT("DATOS.IDTIPOCVSUBTIPO1");
		sql.SELECT("DATOS.IDTIPOCVSUBTIPO2");
		sql.SELECT("DATOS.IDINSTITUCION");
		sql.SELECT("DATOS.IDPERSONA");
		sql.FROM("CEN_DATOSCV DATOS");
		sql.INNER_JOIN("CEN_TIPOSCV TIPOS ON DATOS.IDTIPOCV = TIPOS.IDTIPOCV ");
		sql.LEFT_OUTER_JOIN("CEN_TIPOSCVSUBTIPO1 SUB1 ON (SUB1.IDTIPOCVSUBTIPO1 = DATOS.IDTIPOCVSUBTIPO1 AND TIPOS.IDTIPOCV = SUB1.IDTIPOCV AND DATOS.IDINSTITUCION = SUB1.IDINSTITUCION) ");
		sql.LEFT_OUTER_JOIN("CEN_TIPOSCVSUBTIPO2 SUB2 ON (SUB2.IDTIPOCVSUBTIPO2 = DATOS.IDTIPOCVSUBTIPO2 AND TIPOS.IDTIPOCV = SUB2.IDTIPOCV AND DATOS.IDINSTITUCION = SUB2.IDINSTITUCION) ");
		sql.WHERE(" DATOS.IDINSTITUCION = '"+idInstitucion+"'  ");
		sql.WHERE("DATOS.IDPERSONA = '"+idPersona+"'");
		
		return sql.toString();
	}

}
