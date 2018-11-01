package org.itcgae.siga.db.services.cen.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.mappers.CenDatoscvSqlProvider;

public class CenDatoscvSqlExtendsProvider extends CenDatoscvSqlProvider{
	
	public String searchDatosCurriculares(String idPersona, String idInstitucion) {
		SQL sql = new SQL();
		SQL sql1 = new SQL();
		
		sql.SELECT("TO_CHAR(FECHAINICIO,'DD/MM/YYYY') AS FECHADESDE");
		sql.SELECT("TO_CHAR(FECHAFIN,'DD/MM/YYYY') AS FECHAHASTA");
		sql.SELECT("TO_CHAR(FECHAMOVIMIENTO,'DD/MM/YYYY') AS FECHAMOVIMIENTO");
		sql1.SELECT("F_SIGA_GETRECURSO(DESCRIPCION,1) AS LABEL FROM CEN_TIPOSCV E WHERE IDTIPOCV = DATOS.IDTIPOCV");
		sql.SELECT("DATOS.DESCRIPCION, (" + sql1 + ")  AS CATEGORIACURRICULAR");		
		sql.SELECT("CONCAT((SELECT F_SIGA_GETRECURSO(DESCRIPCION,1) AS LABEL FROM CEN_TIPOSCVSUBTIPO1 E WHERE IDTIPOCVSUBTIPO1 = DATOS.IDTIPOCVSUBTIPO1 AND TIPOS.IDTIPOCV = IDTIPOCV AND DATOS.IDINSTITUCION = IDINSTITUCION) || ' - ' , ((SELECT F_SIGA_GETRECURSO(DESCRIPCION,1) AS LABEL FROM CEN_TIPOSCVSUBTIPO2 E WHERE IDTIPOCVSUBTIPO2 = DATOS.IDTIPOCVSUBTIPO2 AND TIPOS.IDTIPOCV = IDTIPOCV AND DATOS.IDINSTITUCION = IDINSTITUCION) )) AS TIPOSUBTIPO");
		sql.SELECT("DATOS.IDTIPOCV");
		sql.SELECT("DATOS.IDCV");
		sql.SELECT("DATOS.IDTIPOCVSUBTIPO1");
		sql.SELECT("DATOS.IDTIPOCVSUBTIPO2");
		sql.SELECT("DATOS.IDINSTITUCION");
		sql.SELECT("DATOS.IDPERSONA");
		sql.FROM("CEN_DATOSCV DATOS");
		sql.INNER_JOIN("CEN_TIPOSCV TIPOS ON DATOS.IDTIPOCV = TIPOS.IDTIPOCV ");
		sql.LEFT_OUTER_JOIN("CEN_TIPOSCVSUBTIPO1 SUB1 ON (SUB1.IDTIPOCVSUBTIPO1 = DATOS.IDTIPOCVSUBTIPO1 AND TIPOS.IDTIPOCV = SUB1.IDTIPOCV AND DATOS.IDINSTITUCION = SUB1.IDINSTITUCION) ");
		sql.LEFT_OUTER_JOIN("CEN_TIPOSCVSUBTIPO2 SUB2 ON (SUB2.IDTIPOCVSUBTIPO2 = DATOS.IDTIPOCVSUBTIPO2 AND TIPOS.IDTIPOCV = SUB2.IDTIPOCV AND DATOS.IDINSTITUCION = SUB2.IDINSTITUCION) ");
		sql.WHERE("DATOS.IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("DATOS.IDPERSONA = '"+idPersona+"'");
		
		return sql.toString();
	}
	
	
	public String updateCurriculo(CenDatoscv record) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			SQL sql = new SQL();
			sql.UPDATE("CEN_DATOSCV CEN");
//			if (record.getIdcv() != null) {
//				sql.SET("IDCV ='"+ record.getIdcv() +"'");
//			}
//			if (record.getIdinstitucion() != null) {
//				sql.SET("IDINSTITUCION ="+ record.getIdinstitucion()+"");
//			}
//			if (null != perJuridicaDatosRegistralesUpdateDTO.getFechaFin()) {
//				String fechaF = dateFormat.format(perJuridicaDatosRegistralesUpdateDTO.getFechaFin());
//				sql.SET("FECHAFIN = TO_DATE('" + fechaF + "','DD/MM/YYYY')");
//			if (record.getIdpersona() != null) {
//				sql.SET("IPERNA ="+record.getIdpersona()+"");
//			}			
			if (record.getFechainicio() != null) {
				sql.SET("FECHAINICIO = '"+record.getFechainicio() + "'");
			}
			if (record.getFechafin() != null) {
				sql.SET("FECHAFIN = '"+record.getFechainicio()+ "'");
			}
			if (record.getDescripcion() != null) {
				sql.SET("DESCRIPCION = '"+record.getDescripcion() +"'");
			}
			if (record.getIdtipocv() != null) {
				sql.SET("IDTIPOCV = '"+record.getIdtipocv() +"'");
			}
			if (record.getFechamodificacion() != null) {
//				sql.SET("FECHAMODIFICACION = '"+ record.getFechamodificacion()+"'");
				
				String fechaF = dateFormat.format(record.getFechamodificacion());
				sql.SET("FECHAMODIFICACION = '" + fechaF + "'");
				
			}
			
			
				
				
				
				
				
			if (record.getUsumodificacion() != null) {
				sql.SET("USUMODIFICACION =  '"+record.getUsumodificacion() +"'");
			}
			if (record.getCertificado() != null) {
				sql.SET("CERTIFICADO = '"+record.getCertificado()  + "'");
			}
			if (record.getCreditos() != null) {
				sql.SET("CREDITOS = '"+record.getCreditos() + "'");
			}
			if (record.getFechamovimiento() != null) {
				sql.SET("FECHAMOVIMIENTO = '"+record.getFechamovimiento() + "'");
			}
			if (record.getFechabaja() != null) {
				sql.SET("FECHABAJA = '"+record.getFechabaja() + "'");
			}
			if (record.getIdtipocvsubtipo1() != null) {
				sql.SET("IDTIPOCVSUBTIPO1 = '"+record.getIdtipocvsubtipo1() + "'");
			}
			if (record.getIdtipocvsubtipo2() != null) {
				sql.SET("IDTIPOCVSUBTIPO2 = '"+ record.getIdtipocvsubtipo2() + "'");
			}
			if (record.getIdinstitucionSubt1() != null) {
				sql.SET("IDINSTITUCION_SUBT1 = '"+record.getIdinstitucionSubt1() + "'");
			}
			if (record.getIdinstitucionSubt2() != null) {
				sql.SET("IDINSTITUCION_SUBT2 = '"+record.getIdinstitucionSubt1() + "'");
			}
			if (record.getIdinstitucioncargo() != null) {
				sql.SET("IDINSTITUCIONCARGO = '"+record.getIdinstitucioncargo() + "'");
			}
			sql.WHERE("CEN.IDINSTITUCION = '"+record.getIdinstitucion()+"'");
			sql.WHERE("CEN.IDPERSONA = '" + record.getIdpersona() +"'");
			sql.WHERE("IDCV = '" + record.getIdcv() +"'");
//			sql.SET("IDCV ='"+ record.getIdcv() +"'");

			
//			applyWhere(sql, example, true);
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
