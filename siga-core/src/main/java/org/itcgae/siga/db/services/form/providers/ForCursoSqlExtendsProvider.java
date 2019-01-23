package org.itcgae.siga.db.services.form.providers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.ForCurso;
import org.itcgae.siga.db.mappers.ForCursoSqlProvider;

public class ForCursoSqlExtendsProvider extends ForCursoSqlProvider {
	
	public String selectCursos(Short idInstitucion, CursoItem cursoItem, String idLenguaje) {

		SQL sql = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		sql.SELECT_DISTINCT("CURSO.IDCURSO");
		sql.SELECT("CURSO.CODIGOCURSO");
		sql.SELECT("CURSO.NOMBRECURSO");
		sql.SELECT("CURSO.IDINSTITUCION");
		sql.SELECT("CURSO.IDESTADOCURSO AS IDESTADO");
		sql.SELECT("CURSO.IDVISIBILIDADCURSO");
		sql.SELECT("TO_CHAR(CURSO.DESCRIPCION) as DESCRIPCION");
		sql.SELECT("CAT.DESCRIPCION AS ESTADO");
		sql.SELECT("DECODE(CURSO.IDINSTITUCION,'"+ Short.toString(idInstitucion) +"', CAT1.DESCRIPCION, INSTITUCION.ABREVIATURA) AS VISIBILIDAD");
		sql.SELECT("CONCAT(CURSO.PRECIODESDE|| ' - ', CURSO.PRECIOHASTA) AS PRECIOCURSO");
		sql.SELECT("CONCAT(TO_CHAR(CURSO.FECHAINSCRIPCIONDESDE,'DD/MM/YYYY')|| ' - ', TO_CHAR(CURSO.FECHAINSCRIPCIONHASTA,'DD/MM/YYYY')) AS FECHAINSCRIPCION");
		sql.SELECT("TO_CHAR(CURSO.FECHAINSCRIPCIONDESDE,'DD/MM/YYYY') AS FECHAINSCRIPCIONDESDE");
		sql.SELECT("TO_CHAR(CURSO.FECHAINSCRIPCIONHASTA,'DD/MM/YYYY') AS FECHAINSCRIPCIONHASTA");
		sql.SELECT("CONCAT(TO_CHAR(CURSO.FECHAIMPARTICIONDESDE,'DD/MM/YYYY')|| ' - ', TO_CHAR(CURSO.FECHAIMPARTICIONHASTA,'DD/MM/YYYY') ) AS FECHAIMPARTICION");
		sql.SELECT("TO_CHAR(CURSO.FECHAIMPARTICIONDESDE,'DD/MM/YYYY') AS FECHAIMPARTICIONDESDE");
		sql.SELECT("TO_CHAR(CURSO.FECHAIMPARTICIONHASTA,'DD/MM/YYYY') AS FECHAIMPARTICIONHASTA");
		sql.SELECT("CONCAT(PER.NOMBRE ||' ',CONCAT(PER.APELLIDOS1 || ' ',PER.APELLIDOS2)) AS NOMBREAPELLIDOSFORMADOR");
		sql.SELECT("CURSO.FLAGARCHIVADO");
		sql.SELECT("CURSO.LUGAR");
		sql.SELECT("CURSO.NUMEROPLAZAS as PLAZASDISPONIBLES");
		sql.SELECT("CURSO.AUTOVALIDACIONINSCRIPCION");
		sql.SELECT("CURSO.MINIMOASISTENCIA");
		sql.SELECT("CURSO.ENCUESTASATISFACCION");
		sql.SELECT("CURSO.INFORMACIONADICIONAL");
		sql.SELECT("CURSO.DOCUMENTACIONADJUNTA");
		sql.SELECT("CURSO.INFORMACIONADICIONAL");
		sql.SELECT("TO_CHAR(MAX.FECHAINICIO,'DD/MM/YYYY') AS FECHAFINIMPARTICION");
		sql.SELECT("TO_CHAR(MIN.FECHAINICIO,'DD/MM/YYYY') AS FECHAINICIOIMPARTICION");

		
		sql.FROM("FOR_CURSO CURSO");
		
		sql.INNER_JOIN("CEN_INSTITUCION INSTITUCION ON CURSO.IDINSTITUCION = INSTITUCION.IDINSTITUCION");
		sql.INNER_JOIN("FOR_ESTADOCURSO ESTADO ON CURSO.IDESTADOCURSO = ESTADO.IDESTADOCURSO");
		sql.INNER_JOIN("FOR_VISIBILIDADCURSO VISIBILIDAD ON CURSO.IDVISIBILIDADCURSO = VISIBILIDAD.IDVISIBILIDADCURSO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON (CAT.IDRECURSO = ESTADO.DESCRIPCION AND CAT.IDLENGUAJE = '" + idLenguaje + "' )");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT1 ON (CAT1.IDRECURSO = VISIBILIDAD.DESCRIPCION AND CAT1.IDLENGUAJE = '" + idLenguaje + "' )");
		sql.LEFT_OUTER_JOIN("FOR_TEMACURSO_CURSO TEMACURSO ON (TEMACURSO.IDCURSO = CURSO.IDCURSO AND TEMACURSO.IDINSTITUCION = CURSO.IDINSTITUCION AND TEMACURSO.FECHABAJA IS NULL)");
		sql.LEFT_OUTER_JOIN("FOR_PERSONA_CURSO PERCURSO2 ON PERCURSO2.IDCURSO = CURSO.IDCURSO AND PERCURSO2.TUTOR = '1' AND CURSO.IDINSTITUCION = PERCURSO2.IDINSTITUCION AND PERCURSO2.FECHABAJA IS NULL");
		sql.LEFT_OUTER_JOIN("CEN_PERSONA PER ON PER.IDPERSONA = PERCURSO2.IDPERSONA");

		SQL sql1 = new SQL();
		sql1.SELECT("MAX(EVE2.FECHAINICIO) FECHAINICIO");
		sql1.SELECT("EVC2.IDCURSO");
		sql1.FROM("FOR_EVENTO_CURSO EVC2");
		sql1.INNER_JOIN("AGE_EVENTO EVE2 ON (EVC2.IDEVENTO = EVE2.IDEVENTO) where EVE2.IDTIPOEVENTO = '8' GROUP BY IDCURSO");
		
		sql.LEFT_OUTER_JOIN("(" + sql1 + ") MAX ON MAX.IDCURSO = CURSO.IDCURSO");
		
		SQL sql2 = new SQL();
		sql2.SELECT("MIN(EVE2.FECHAINICIO) FECHAINICIO");
		sql2.SELECT("EVC2.IDCURSO");
		sql2.FROM("FOR_EVENTO_CURSO EVC2");
		sql2.INNER_JOIN("AGE_EVENTO EVE2 ON (EVC2.IDEVENTO = EVE2.IDEVENTO) where EVE2.IDTIPOEVENTO = '8' GROUP BY IDCURSO");
		
		sql.LEFT_OUTER_JOIN("(" + sql2 + ") MIN ON MIN.IDCURSO = CURSO.IDCURSO");
		
		if(cursoItem.getColegio() != null && cursoItem.getColegio() != "") {
			sql.WHERE("CURSO.IDINSTITUCION = '" + cursoItem.getColegio() + "'");
		}else {
			sql.WHERE("(CURSO.IDINSTITUCION = '" + idInstitucion + "' OR (CURSO.IDINSTITUCION <> '" + idInstitucion + "' AND CURSO.IDVISIBILIDADCURSO = '0'))");
		}
		
		if(cursoItem.getCodigoCurso() != null && cursoItem.getCodigoCurso() != "") {
			sql.WHERE("CURSO.CODIGOCURSO like '%" + cursoItem.getCodigoCurso() + "%'");
		}
		
		if (cursoItem.getNombreCurso() != null && cursoItem.getNombreCurso() != "") {
			sql.WHERE("UPPER(CURSO.NOMBRECURSO) LIKE UPPER('%" + cursoItem.getNombreCurso() + "%')");
		}
		
		if(cursoItem.getIdEstado() != null && cursoItem.getIdEstado() != "") {
			sql.WHERE("ESTADO.IDESTADOCURSO = '" + cursoItem.getIdEstado() + "'");
		}
		
		if(cursoItem.getIdVisibilidad() != null && cursoItem.getIdVisibilidad() != "") {
			sql.WHERE("VISIBILIDAD.IDVISIBILIDADCURSO = '" + cursoItem.getIdVisibilidad() + "'");
		}
		
		if(cursoItem.getFlagArchivado() != null) {
			sql.WHERE("CURSO.FLAGARCHIVADO = " + Integer.toString(cursoItem.getFlagArchivado()));
		}
		
		if(cursoItem.getPrecioDesde() != null) {
			sql.WHERE("CURSO.PRECIODESDE >= " + Double.toString(cursoItem.getPrecioDesde()));
		}
		
		if(cursoItem.getPrecioHasta() != null) {
			sql.WHERE("CURSO.PRECIOHASTA <= " + Double.toString(cursoItem.getPrecioHasta()));
		}
		
		if(cursoItem.getFechaInscripcionDesdeDate() != null) {
			String fechaInscripcionDesde = dateFormat.format(cursoItem.getFechaInscripcionDesdeDate());
			sql.WHERE("TO_DATE(CURSO.FECHAINSCRIPCIONDESDE,'DD/MM/RRRR') >= TO_DATE('" + fechaInscripcionDesde + "','DD/MM/YYYY')");
		}
		
		if(cursoItem.getFechaInscripcionHastaDate() != null) {
			String fechaInscripcionHasta = dateFormat.format(cursoItem.getFechaInscripcionHastaDate());
			sql.WHERE("TO_DATE(CURSO.FECHAINSCRIPCIONHASTA,'DD/MM/RRRR') <= TO_DATE('" + fechaInscripcionHasta + "','DD/MM/YYYY')");
		}
		
		if(cursoItem.getFechaImparticionDesdeDate() != null) {
			String fechaImparticionDesde = dateFormat.format(cursoItem.getFechaImparticionDesdeDate());
			sql.WHERE("TO_DATE(FECHAIMPARTICIONDESDE,'DD/MM/RRRR') >= TO_DATE('" + fechaImparticionDesde + "','DD/MM/YYYY')");
		}
		
		if(cursoItem.getFechaImparticionHastaDate() != null) {
			String fechaImparticionHasta = dateFormat.format(cursoItem.getFechaImparticionHastaDate());
			sql.WHERE("TO_DATE(FECHAIMPARTICIONHASTA,'DD/MM/RRRR') <= TO_DATE('" + fechaImparticionHasta + "','DD/MM/YYYY')");
		}
		
		if (cursoItem.getTemas() != null && cursoItem.getTemas().length > 0) {

			String temas = "";

			for (int i = 0; i < cursoItem.getTemas().length; i++) {

				if (i == cursoItem.getTemas().length - 1) {
					temas += "'" + cursoItem.getTemas()[i] + "'";
				} else {
					temas += "'" + cursoItem.getTemas()[i] + "',";
				}
			}

			sql.WHERE("TEMACURSO.IDTEMACURSO IN (" + temas + ")");
		}
		
		if(cursoItem.getPlazasDisponibles() != null && cursoItem.getPlazasDisponibles() != "") {
			
			SQL sqlPlazasDispo = new SQL();
			
			sqlPlazasDispo.SELECT("COUNT (IDINSCRIPCION)");
			sqlPlazasDispo.FROM("FOR_INSCRIPCION INSCRIPCION");
			sqlPlazasDispo.WHERE("INSCRIPCION.IDCURSO = CURSO.IDCURSO AND INSCRIPCION.IDESTADOINSCRIPCION = 1");
			
			
			if(SigaConstants.PLAZAS_DISPO_SI.equals(cursoItem.getPlazasDisponibles())) {
				sql.WHERE("CURSO.NUMEROPLAZAS > " +"("+ sqlPlazasDispo +")");
			}
			
			if(SigaConstants.PLAZAS_DISPO_NO.equals(cursoItem.getPlazasDisponibles())) {
				sql.WHERE("CURSO.NUMEROPLAZAS < " +"("+ sqlPlazasDispo +")");
			}
			
		}
		
		
		if (cursoItem.getNombreApellidosFormador() != null && cursoItem.getNombreApellidosFormador() != "") {
			sql.WHERE("TRANSLATE(UPPER( CONCAT(PER.NOMBRE ||' ',CONCAT(PER.APELLIDOS1 || ' ',PER.APELLIDOS2) )),'"+ SigaConstants.AUX_TRANS_TILDES_1 + "','" + SigaConstants.AUX_TRANS_TILDES_2 + "') LIKE TRANSLATE(UPPER('%" + cursoItem.getNombreApellidosFormador()+ "%'),'"+ SigaConstants.AUX_TRANS_TILDES_1 + "','" + SigaConstants.AUX_TRANS_TILDES_2 + "')");
		}
		
		sql.ORDER_BY("CURSO.CODIGOCURSO");
		
		return sql.toString();
	}
	
	
	public String selectCursosFechaAuto(ForCurso forCurso) {
		SQL sql = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
		
		sql.SELECT_DISTINCT("CURSO.IDCURSO");
		sql.SELECT("CURSO.IDINSTITUCION");
		sql.SELECT("TO_DATE(CURSO.FECHAIMPARTICIONDESDE,'dd/MM/YYYY') AS FECHAIMPARTICIONDESDE");
		sql.SELECT("TO_DATE(CURSO.FECHAIMPARTICIONHASTA,'dd/MM/YYYY') AS FECHAIMPARTICIONHASTA");
		
		sql.FROM("FOR_CURSO CURSO");
		
		if(forCurso.getFechaimparticiondesde() != null) {
			sql.WHERE("CURSO.FECHAIMPARTICIONDESDE = '" + dateFormat.format(forCurso.getFechaimparticiondesde()) + "'");
		}
		
		if(forCurso.getFechaimparticionhasta() != null) {
			sql.WHERE("CURSO.FECHAIMPARTICIONHASTA = '" + dateFormat.format(forCurso.getFechaimparticionhasta()) + "'");
		}
		
		return sql.toString();
	}
	
	public String searchCourseByIdcurso(String idCurso, Short idInstitucion, String idLenguaje) {

		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("CURSO.IDCURSO");
		sql.SELECT("CURSO.CODIGOCURSO");
		sql.SELECT("CURSO.NOMBRECURSO");
		sql.SELECT("CURSO.IDINSTITUCION");
		sql.SELECT("CURSO.IDESTADOCURSO AS IDESTADO");
		sql.SELECT("CURSO.IDVISIBILIDADCURSO");
		sql.SELECT("TO_CHAR(CURSO.DESCRIPCION) AS DESCRIPCION");
		sql.SELECT("CAT.DESCRIPCION AS ESTADO");
		sql.SELECT("DECODE(CURSO.IDINSTITUCION,'"+ Short.toString(idInstitucion) +"', CAT1.DESCRIPCION, INSTITUCION.ABREVIATURA) AS VISIBILIDAD");
		sql.SELECT("CONCAT(CURSO.PRECIODESDE|| ' - ', CURSO.PRECIOHASTA) AS PRECIOCURSO");
		sql.SELECT("CONCAT(CURSO.FECHAINSCRIPCIONDESDE|| ' - ', CURSO.FECHAINSCRIPCIONHASTA) AS FECHAINSCRIPCION");
		sql.SELECT("TO_CHAR(CURSO.FECHAINSCRIPCIONDESDE,'DD/MM/YYYY') AS FECHAINSCRIPCIONDESDE");
		sql.SELECT("TO_CHAR(CURSO.FECHAINSCRIPCIONHASTA,'DD/MM/YYYY') AS FECHAINSCRIPCIONHASTA");
		sql.SELECT("CONCAT(CURSO.FECHAIMPARTICIONDESDE|| ' - ', CURSO.FECHAIMPARTICIONHASTA ) AS FECHAIMPARTICION");
		sql.SELECT("TO_CHAR(CURSO.FECHAIMPARTICIONDESDE,'DD/MM/YYYY') AS FECHAIMPARTICIONDESDE");
		sql.SELECT("TO_CHAR(CURSO.FECHAIMPARTICIONHASTA,'DD/MM/YYYY') AS FECHAIMPARTICIONHASTA");
		sql.SELECT("CONCAT(PER.NOMBRE ||' ',CONCAT(PER.APELLIDOS1 || ' ',PER.APELLIDOS2)) AS NOMBREAPELLIDOSFORMADOR");
		sql.SELECT("CURSO.FLAGARCHIVADO");
		sql.SELECT("CURSO.LUGAR");
		sql.SELECT("CURSO.NUMEROPLAZAS as plazasDisponibles");
		sql.SELECT("CURSO.AUTOVALIDACIONINSCRIPCION");
		sql.SELECT("CURSO.MINIMOASISTENCIA");
		sql.SELECT("CURSO.ENCUESTASATISFACCION");
		sql.SELECT("CURSO.INFORMACIONADICIONAL");
		sql.SELECT("CURSO.DOCUMENTACIONADJUNTA");
		sql.SELECT("TO_CHAR(MAX.FECHAINICIO,'DD/MM/YYYY') AS FECHAFINIMPARTICION");
		sql.SELECT("TO_CHAR(MIN.FECHAINICIO,'DD/MM/YYYY') AS FECHAINICIOIMPARTICION");
		
		sql.FROM("FOR_CURSO CURSO");
		
		sql.INNER_JOIN("CEN_INSTITUCION INSTITUCION ON CURSO.IDINSTITUCION = INSTITUCION.IDINSTITUCION");
		sql.INNER_JOIN("FOR_ESTADOCURSO ESTADO ON CURSO.IDESTADOCURSO = ESTADO.IDESTADOCURSO");
		sql.INNER_JOIN("FOR_VISIBILIDADCURSO VISIBILIDAD ON CURSO.IDVISIBILIDADCURSO = VISIBILIDAD.IDVISIBILIDADCURSO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON (CAT.IDRECURSO = ESTADO.DESCRIPCION AND CAT.IDLENGUAJE = '" + idLenguaje + "' )");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT1 ON (CAT1.IDRECURSO = VISIBILIDAD.DESCRIPCION AND CAT1.IDLENGUAJE = '" + idLenguaje + "' )");
		sql.LEFT_OUTER_JOIN("FOR_TEMACURSO_CURSO TEMACURSO ON (TEMACURSO.IDCURSO = CURSO.IDCURSO AND TEMACURSO.IDINSTITUCION = CURSO.IDINSTITUCION AND TEMACURSO.FECHABAJA IS NULL)");
		sql.LEFT_OUTER_JOIN("FOR_PERSONA_CURSO PERCURSO2 ON PERCURSO2.IDCURSO = CURSO.IDCURSO AND PERCURSO2.TUTOR = '1' AND CURSO.IDINSTITUCION = PERCURSO2.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN("CEN_PERSONA PER ON PER.IDPERSONA = PERCURSO2.IDPERSONA");

		SQL sql1 = new SQL();
		sql1.SELECT("MAX(EVE2.FECHAINICIO) FECHAINICIO");
		sql1.SELECT("EVC2.IDCURSO");
		sql1.FROM("FOR_EVENTO_CURSO EVC2");
		sql1.INNER_JOIN("AGE_EVENTO EVE2 ON (EVC2.IDEVENTO = EVE2.IDEVENTO) where EVE2.IDTIPOEVENTO = '8' GROUP BY IDCURSO");
		
		sql.LEFT_OUTER_JOIN("(" + sql1 + ") MAX ON MAX.IDCURSO = CURSO.IDCURSO");
		
		SQL sql2 = new SQL();
		sql2.SELECT("MIN(EVE2.FECHAINICIO) FECHAINICIO");
		sql2.SELECT("EVC2.IDCURSO");
		sql2.FROM("FOR_EVENTO_CURSO EVC2");
		sql2.INNER_JOIN("AGE_EVENTO EVE2 ON (EVC2.IDEVENTO = EVE2.IDEVENTO) where EVE2.IDTIPOEVENTO = '8' GROUP BY IDCURSO");
		
		sql.LEFT_OUTER_JOIN("(" + sql2 + ") MIN ON MIN.IDCURSO = CURSO.IDCURSO");
		
		sql.WHERE("(CURSO.IDINSTITUCION = '" + idInstitucion + "' OR (CURSO.IDINSTITUCION <> '" + idInstitucion + "' AND CURSO.IDVISIBILIDADCURSO = '0'))");
		sql.WHERE("CURSO.IDCURSO = '" + idCurso + "'");

		return sql.toString();
	}
	
	public String updateCourse (CursoItem cursoItem, AdmUsuarios usuario) {
		SQL sql = new SQL();
		
 		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		sql.UPDATE("FOR_CURSO");
		
		sql.SET("FECHAMODIFICACION = TO_DATE('" + dateFormat.format(new Date()) + "','DD/MM/YYYY')");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		
 		if(null != cursoItem.getNombreCurso()) {
			sql.SET("NOMBRECURSO ='" + cursoItem.getNombreCurso() + "'");
		}
 		
 		if(null != cursoItem.getDescripcionEstado()) {
			sql.SET("DESCRIPCION ='" + cursoItem.getDescripcionEstado() + "'");
		}

 		if(null != cursoItem.getFechaImparticionDesdeDate()) {
			sql.SET("FECHAIMPARTICIONDESDE = TO_DATE('" + dateFormat.format(cursoItem.getFechaImparticionDesdeDate()) + "','DD/MM/YYYY')");
		}
 		
 		if(null != cursoItem.getFechaImparticionHastaDate()) {
			sql.SET("FECHAIMPARTICIONHASTA = TO_DATE('" + dateFormat.format(cursoItem.getFechaImparticionHastaDate()) + "','DD/MM/YYYY')");
		}
 		
 		if(null != cursoItem.getFechaInscripcionDesdeDate()) {
			sql.SET("FECHAINSCRIPCIONDESDE = TO_DATE('" + dateFormat.format(cursoItem.getFechaInscripcionDesdeDate()) + "','DD/MM/YYYY')");
		}
 		
 		if(null != cursoItem.getFechaInscripcionHastaDate()) {
			sql.SET("FECHAINSCRIPCIONHASTA = TO_DATE('" + dateFormat.format(cursoItem.getFechaInscripcionHastaDate()) + "','DD/MM/YYYY')");
		}
 		
 		if(null != cursoItem.getPlazasDisponibles()) {
			sql.SET("NUMEROPLAZAS ='" + cursoItem.getPlazasDisponibles() + "'");
		}
 		
 		if(null != cursoItem.getMinimoAsistencia()) {
			sql.SET("MINIMOASISTENCIA ='" + cursoItem.getMinimoAsistencia() + "'");
		}
 		
 		if(null != cursoItem.getAutovalidacionInscripcion()) {
			sql.SET("AUTOVALIDACIONINSCRIPCION = '" + cursoItem.getAutovalidacionInscripcion() + "'");
		}
 		
 		if(cursoItem.getEncuesta() != null) {
			sql.SET("ENCUESTASATISFACCION = '" + cursoItem.getEncuesta() + "'");
		}
		
		if(cursoItem.getAdicional() != null) {
			sql.SET("INFORMACIONADICIONAL = '" + cursoItem.getAdicional() + "'");
		}
		
		if(cursoItem.getAdjunto() != null) {
			sql.SET("DOCUMENTACIONADJUNTA = '" + cursoItem.getAdjunto() + "'");
		}
		
		if(cursoItem.getIdVisibilidad() != null) {
			sql.SET("IDVISIBILIDADCURSO = '" + cursoItem.getIdVisibilidad() + "'");
		}
		
		if(cursoItem.getLugar() != null) {
			sql.SET("LUGAR = '" + cursoItem.getLugar() + "'");
		}
 		
 		sql.WHERE("IDCURSO = '" + cursoItem.getIdCurso() + "'");
		
		return sql.toString();
	}

}
