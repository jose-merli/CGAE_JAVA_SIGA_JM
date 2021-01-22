package org.itcgae.siga.db.services.cen.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.mappers.CenDatoscvSqlProvider;

public class CenDatoscvSqlExtendsProvider extends CenDatoscvSqlProvider{
	
	public String searchDatosCurriculares(String idPersona, boolean historico, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();
		SQL sql1 = new SQL();
		
		sql.SELECT("TO_CHAR(FECHAINICIO,'DD/MM/YYYY') AS FECHADESDE");
		sql.SELECT("TO_CHAR(FECHAFIN,'DD/MM/YYYY') AS FECHAHASTA");
		sql.SELECT("FECHAINICIO AS DATEFECHAINICIO");
		sql.SELECT("FECHAFIN AS DATEFECHAFIN");
		sql.SELECT("TO_CHAR(FECHAMOVIMIENTO,'DD/MM/YYYY') AS FECHAMOVIMIENTO");
		sql.SELECT("TO_CHAR(FECHABAJA,'DD/MM/YYYY') AS FECHABAJA");
		sql1.SELECT("F_SIGA_GETRECURSO(DESCRIPCION," + idLenguaje + ") AS LABEL FROM CEN_TIPOSCV E WHERE IDTIPOCV = DATOS.IDTIPOCV");
		sql.SELECT("DATOS.DESCRIPCION, (" + sql1 + ")  AS CATEGORIACURRICULAR");		
		sql.SELECT("DATOS.DESCRIPCION, (" + sql1 + ")  AS DESCRIPCION");		
		sql.SELECT("CONCAT((SELECT F_SIGA_GETRECURSO(DESCRIPCION," + idLenguaje + ") AS LABEL FROM CEN_TIPOSCVSUBTIPO1 E WHERE IDTIPOCVSUBTIPO1 = DATOS.IDTIPOCVSUBTIPO1 AND TIPOS.IDTIPOCV = IDTIPOCV AND DATOS.IDINSTITUCION_SUBT1 = IDINSTITUCION) || ' - ' , ((SELECT F_SIGA_GETRECURSO(DESCRIPCION," + idLenguaje + ") AS LABEL FROM CEN_TIPOSCVSUBTIPO2 E WHERE IDTIPOCVSUBTIPO2 = DATOS.IDTIPOCVSUBTIPO2 AND TIPOS.IDTIPOCV = IDTIPOCV AND DATOS.IDINSTITUCION_SUBT2 = IDINSTITUCION) )) AS TIPOSUBTIPO");
		sql.SELECT("DATOS.IDTIPOCV");
		sql.SELECT("DATOS.IDCV");
		sql.SELECT("DATOS.IDTIPOCVSUBTIPO1");
		sql.SELECT("DATOS.IDTIPOCVSUBTIPO2");
		sql.SELECT("DATOS.IDINSTITUCION_SUBT1");
		sql.SELECT("DATOS.IDINSTITUCION_SUBT2");
		sql.SELECT("DATOS.IDINSTITUCION");
		sql.SELECT("DATOS.IDPERSONA");
		sql.SELECT("DATOS.CREDITOS");
		sql.SELECT("DATOS.CERTIFICADO");
		sql.FROM("CEN_DATOSCV DATOS");
		sql.INNER_JOIN("CEN_TIPOSCV TIPOS ON DATOS.IDTIPOCV = TIPOS.IDTIPOCV ");
		sql.LEFT_OUTER_JOIN("CEN_TIPOSCVSUBTIPO1 SUB1 ON (SUB1.IDTIPOCVSUBTIPO1 = DATOS.IDTIPOCVSUBTIPO1 AND TIPOS.IDTIPOCV = SUB1.IDTIPOCV AND DATOS.IDINSTITUCION_SUBT1 = SUB1.IDINSTITUCION) ");
		sql.LEFT_OUTER_JOIN("CEN_TIPOSCVSUBTIPO2 SUB2 ON (SUB2.IDTIPOCVSUBTIPO2 = DATOS.IDTIPOCVSUBTIPO2 AND TIPOS.IDTIPOCV = SUB2.IDTIPOCV AND DATOS.IDINSTITUCION_SUBT2 = SUB2.IDINSTITUCION) ");
		if(!idInstitucion.equals("2000")) {
			sql.WHERE("DATOS.IDINSTITUCION = '"+idInstitucion+"'");
		}
		sql.WHERE("DATOS.IDPERSONA = '"+idPersona+"'");
		if (!historico) {
			sql.WHERE("DATOS.FECHABAJA is null");
		}
		sql.ORDER_BY("FECHAINICIO desc");
		return sql.toString();
	}
	
	public String updateCurriculo(CenDatoscv record) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			SQL sql = new SQL();
			SQL sql1 = new SQL();
			SQL sql2 = new SQL();

			sql.UPDATE("CEN_DATOSCV CEN");
			sql1.SELECT("IDINSTITUCION");
			sql2.SELECT("IDINSTITUCION");
	
			if (record.getFechainicio() != null) {				
				String fechaF = dateFormat.format(record.getFechainicio());
				sql.SET("FECHAINICIO = TO_DATE('" + fechaF + "','DD/MM/YYYY hh24:mi:ss')");
			}
			if (record.getFechafin() != null) {
				String fechaF = dateFormat.format(record.getFechafin());
				sql.SET("FECHAFIN = TO_DATE('" + fechaF + "','DD/MM/YYYY hh24:mi:ss')");
			}else {
				sql.SET("FECHAFIN = null");
			}
			if (record.getDescripcion() != null) {
				sql.SET("DESCRIPCION = '"+record.getDescripcion() +"'");
			}else {
				sql.SET("DESCRIPCION = null");
			}
			if (record.getIdtipocv() != null) {
				sql.SET("IDTIPOCV = '"+record.getIdtipocv() +"'");
			}
			if (record.getFechamodificacion() != null) {
				String fechaF = dateFormat.format(record.getFechamodificacion());
				sql.SET("FECHAMODIFICACION = TO_DATE('" + fechaF + "','DD/MM/YYYY hh24:mi:ss')");
			}							
			if (record.getUsumodificacion() != null) {
				sql.SET("USUMODIFICACION =  '"+record.getUsumodificacion() +"'");
			}
			if (record.getCertificado() != null) {
				sql.SET("CERTIFICADO = '"+record.getCertificado()  + "'");
			}
			if (record.getCreditos() != null) {
				sql.SET("CREDITOS = '"+record.getCreditos() + "'");
			}else {
				sql.SET("CREDITOS = null");
			}
			if (record.getFechamovimiento() != null) {
				String fechaF = dateFormat.format(record.getFechamovimiento());
				sql.SET("FECHAMOVIMIENTO = TO_DATE('" + fechaF + "','DD/MM/YYYY hh24:mi:ss')");
			}else {
				sql.SET("FECHAMOVIMIENTO = null");

			}
			if (record.getFechabaja() != null) {
				String fechaF = dateFormat.format(record.getFechabaja());
				sql.SET("FECHABAJA = TO_DATE('" + fechaF + "','DD/MM/YYYY hh24:mi:ss')");
			}else {
				sql.SET("FECHABAJA = null");
			}
			if (record.getIdtipocvsubtipo1() != null) {
				sql.SET("IDTIPOCVSUBTIPO1 = '"+record.getIdtipocvsubtipo1() + "'");
				sql1.FROM("CEN_TIPOSCVSUBTIPO1");
				sql1.WHERE("IDTIPOCVSUBTIPO1 ='"+ record.getIdtipocvsubtipo1() +"'");
				sql1.WHERE("IDTIPOCV ='"+ record.getIdtipocv() +"'");
				sql1.WHERE("ROWNUM = 1");
				sql.SET("IDINSTITUCION_SUBT1 = ("+ record.getIdinstitucionSubt1() + ")");
			}else{
				sql.SET("IDTIPOCVSUBTIPO1 = "+record.getIdtipocvsubtipo1() + "");
				sql.SET("IDINSTITUCION_SUBT1 = "+record.getIdinstitucionSubt1() + "");

			}  
			
			if (record.getIdtipocvsubtipo2() != null) {
				sql.SET("IDTIPOCVSUBTIPO2 = '"+ record.getIdtipocvsubtipo2() + "'");
				sql2.FROM("CEN_TIPOSCVSUBTIPO2");
				sql2.WHERE("IDTIPOCVSUBTIPO2 ='"+ record.getIdtipocvsubtipo2() +"'");
				sql2.WHERE("IDTIPOCV ='"+ record.getIdtipocv() +"'");
				sql2.WHERE("ROWNUM = 1");
				sql.SET("IDINSTITUCION_SUBT2 = ("+ record.getIdinstitucionSubt2() + ")");
			}else{
				sql.SET("IDTIPOCVSUBTIPO2 = "+ record.getIdtipocvsubtipo2() + "");
				sql.SET("IDINSTITUCION_SUBT2 = "+record.getIdinstitucionSubt2() + "");

		}
			
//				sql.SET("IDTIPOCVSUBTIPO1 = '"+record.getIdtipocvsubtipo1() + "'");
//				sql1.FROM("CEN_TIPOSCVSUBTIPO1");
//				sql1.WHERE("IDTIPOCVSUBTIPO1 ='"+ record.getIdtipocvsubtipo1() +"'");
//				sql1.WHERE("IDTIPOCV ='"+ record.getIdtipocv() +"'");
//				sql1.WHERE("ROWNUM = 1");
//				sql.SET("IDINSTITUCION_SUBT1 = ("+sql1 + ")");
//				
//				sql.SET("IDTIPOCVSUBTIPO2 = '"+ record.getIdtipocvsubtipo2() + "'");
//				sql2.FROM("CEN_TIPOSCVSUBTIPO2");
//				sql2.WHERE("IDTIPOCVSUBTIPO2 ='"+ record.getIdtipocvsubtipo2() +"'");
//				sql2.WHERE("IDTIPOCV ='"+ record.getIdtipocv() +"'");
//				sql2.WHERE("ROWNUM = 1");
//				sql.SET("IDINSTITUCION_SUBT2 = ("+sql2 + ")");
				
				
			if (record.getIdinstitucioncargo() != null) {
				sql.SET("IDINSTITUCIONCARGO = '"+record.getIdinstitucioncargo() + "'");
			}
			sql.WHERE("CEN.IDINSTITUCION = '"+record.getIdinstitucion()+"'");
			sql.WHERE("CEN.IDPERSONA = '" + record.getIdpersona() +"'");
			sql.WHERE("IDCV = '" + record.getIdcv() +"'");
			return sql.toString();
	}
	
	
	public String getMaxIdCv(String idInstitucion, String idPersona) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDCV) AS IDCV");
		sql.FROM("CEN_DATOSCV");
		sql.WHERE("IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("IDPERSONA = '"+ idPersona +"'");
		
		return sql.toString();
	}

}
