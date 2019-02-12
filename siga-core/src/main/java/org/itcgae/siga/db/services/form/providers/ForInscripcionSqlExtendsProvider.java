package org.itcgae.siga.db.services.form.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.db.mappers.ForInscripcionSqlProvider;

public class ForInscripcionSqlExtendsProvider extends ForInscripcionSqlProvider {

	public String selectInscripciones(InscripcionItem inscripcionItem, String idLenguaje) {

		SQL sql = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		sql.SELECT_DISTINCT("INSC.IDINSCRIPCION");
		sql.SELECT("INSC.IDESTADOINSCRIPCION");
		sql.SELECT("INSC.IDPERSONA");
		sql.SELECT("INSC.EMITIRCERTIFICADO");
		sql.SELECT("INSC.CERTIFICADOEMITIDO");
		sql.SELECT("CURSO.IDESTADOCURSO");
		sql.SELECT("CURSO.IDCURSO");
		sql.SELECT("INSC.IDINSTITUCION");
		sql.SELECT("CURSO.CODIGOCURSO");
		sql.SELECT("CURSO.NOMBRECURSO");
		sql.SELECT("CAT.DESCRIPCION AS ESTADOCURSO");
		sql.SELECT("CONCAT(CURSO.PRECIODESDE|| ' - ', CURSO.PRECIOHASTA) AS PRECIOCURSO");
		sql.SELECT("TO_CHAR(CURSO.FECHAIMPARTICIONDESDE,'DD/MM/YYYY') AS FECHAIMPARTICIONDESDE");
		sql.SELECT("TO_CHAR(CURSO.FECHAIMPARTICIONHASTA,'DD/MM/YYYY') AS FECHAIMPARTICIONHASTA");
		sql.SELECT("TO_CHAR(INSC.FECHASOLICITUD,'DD/MM/YYYY') AS FECHASOLICITUD");
		sql.SELECT("case when NVL(CURSO.MINIMOASISTENCIA,0) > DECODE(count (idasistenciaevento),0,-1, count (idasistenciaevento)) then 'NO' else 'SI' end MINIMAASISTENCIA");
		sql.SELECT("CAT2.DESCRIPCION AS ESTADOINSCRIPCION");
		sql.SELECT("INSC.CALIFICACION");
		sql.SELECT("INSC.IDCALIFICACION");
		sql.SELECT("INSC.IDPETICIONSUSCRIPCION");

		sql.FROM("FOR_INSCRIPCION INSC");

		sql.INNER_JOIN("FOR_CURSO CURSO ON INSC.IDCURSO = CURSO.IDCURSO");
		sql.LEFT_OUTER_JOIN("for_evento_curso eventocurso on eventocurso.idcurso = CURSO.idcurso");
		sql.LEFT_OUTER_JOIN("age_asistencia_evento asis on (asis.idevento = eventocurso.idevento and asis.idinscripcion = insc.idinscripcion)");
		sql.INNER_JOIN("FOR_ESTADOCURSO ESTADOCURSO ON CURSO.IDESTADOCURSO = ESTADOCURSO.IDESTADOCURSO");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS CAT ON (CAT.IDRECURSO = ESTADOCURSO.DESCRIPCION AND CAT.IDLENGUAJE = '" + idLenguaje + "' )");
		sql.INNER_JOIN("FOR_ESTADOINSCRIPCION ESTADOINSC ON INSC.IDESTADOINSCRIPCION = ESTADOINSC.IDESTADOINSCRIPCION");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS CAT2 ON (CAT2.IDRECURSO = ESTADOINSC.DESCRIPCION AND CAT2.IDLENGUAJE = '" + idLenguaje + "' )");
		sql.INNER_JOIN("FOR_VISIBILIDADCURSO VISIBILIDAD ON CURSO.IDVISIBILIDADCURSO = VISIBILIDAD.IDVISIBILIDADCURSO");
		sql.LEFT_OUTER_JOIN("FOR_TEMACURSO_CURSO TEMACURSO ON (TEMACURSO.IDCURSO = CURSO.IDCURSO AND TEMACURSO.IDINSTITUCION = CURSO.IDINSTITUCION AND TEMACURSO.FECHABAJA IS NULL)");
		sql.LEFT_OUTER_JOIN(
				"FOR_PERSONA_CURSO PERCURSO2 ON PERCURSO2.IDCURSO = CURSO.IDCURSO AND PERCURSO2.TUTOR = '1' AND CURSO.IDINSTITUCION = PERCURSO2.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN(
				"CEN_COLEGIADO COLEGIADO ON (COLEGIADO.IDPERSONA = INSC.IDPERSONA  )");

		if (inscripcionItem.getIdInstitucion() != null && inscripcionItem.getIdInstitucion() != "") {
			sql.WHERE("(INSC.idinstitucion = '" + inscripcionItem.getIdInstitucion() + "' OR COLEGIADO.IDINSTITUCION = '" + inscripcionItem.getIdInstitucion() + "')");
		}
		sql.ORDER_BY("FECHASOLICITUD DESC");
		
		if (inscripcionItem.getIdCurso() != null && inscripcionItem.getIdCurso() != "") {
			sql.WHERE("INSC.IDCURSO = '" + inscripcionItem.getIdCurso() + "'");
		}
		
		if (inscripcionItem.getColegio() != null && inscripcionItem.getColegio() != "") {
			sql.WHERE("INSC.IDINSTITUCION = '" + inscripcionItem.getColegio() + "'");
		}
		
		if (inscripcionItem.getIdPersona() != null) {
			sql.WHERE("INSC.IDPERSONA = '" + inscripcionItem.getIdPersona() + "'");
		}

		if (inscripcionItem.getCodigoCurso() != null && inscripcionItem.getCodigoCurso() != "") {
			sql.WHERE("CURSO.CODIGOCURSO like '%" + inscripcionItem.getCodigoCurso() + "%'");
		}

		if (inscripcionItem.getNombreCurso() != null && inscripcionItem.getNombreCurso() != "") {
			sql.WHERE("UPPER(CURSO.NOMBRECURSO) LIKE UPPER('%" + inscripcionItem.getNombreCurso() + "%')");
		}

		if (inscripcionItem.getIdEstadoCurso() != null && inscripcionItem.getIdEstadoCurso() != "") {
			sql.WHERE("ESTADOCURSO.IDESTADOCURSO = '" + inscripcionItem.getIdEstadoCurso() + "'");
		}
		
		if(inscripcionItem.getIdEstadoInscripcion() != null && inscripcionItem.getIdEstadoInscripcion() != "") {
			sql.WHERE("ESTADOINSC.IDESTADOINSCRIPCION = '" + inscripcionItem.getIdEstadoInscripcion() + "'");

		}

		if (inscripcionItem.getIdVisibilidad() != null && inscripcionItem.getIdVisibilidad() != "") {
			sql.WHERE("VISIBILIDAD.IDVISIBILIDADCURSO = '" + inscripcionItem.getIdVisibilidad() + "'");
		}

		if (inscripcionItem.getFechaInscripcionDesde() != null) {
			String fechaInscripcionDesde = dateFormat.format(inscripcionItem.getFechaInscripcionDesde());
			sql.WHERE("TO_DATE(CURSO.FECHAINSCRIPCIONDESDE,'DD/MM/RRRR') >= TO_DATE('" + fechaInscripcionDesde
					+ "','DD/MM/YYYY')");
		}

		if (inscripcionItem.getFechaInscripcionHasta() != null) {
			String fechaInscripcionHasta = dateFormat.format(inscripcionItem.getFechaInscripcionHasta());
			sql.WHERE("TO_DATE(CURSO.FECHAINSCRIPCIONHASTA,'DD/MM/RRRR') <= TO_DATE('" + fechaInscripcionHasta
					+ "','DD/MM/YYYY')");
		}

		if (inscripcionItem.getFechaImparticionDesde() != null) {
			String fechaImparticionDesde = dateFormat.format(inscripcionItem.getFechaImparticionDesde());
			sql.WHERE("TO_DATE(FECHAIMPARTICIONDESDE,'DD/MM/RRRR') >= TO_DATE('" + fechaImparticionDesde
					+ "','DD/MM/YYYY')");
		}

		if (inscripcionItem.getFechaImparticionHasta() != null) {
			String fechaImparticionHasta = dateFormat.format(inscripcionItem.getFechaImparticionHasta());
			sql.WHERE("TO_DATE(FECHAIMPARTICIONHASTA,'DD/MM/RRRR') <= TO_DATE('" + fechaImparticionHasta
					+ "','DD/MM/YYYY')");
		}

		if (inscripcionItem.getTemas() != null && inscripcionItem.getTemas().length > 0) {

			String temas = "";

			for (int i = 0; i < inscripcionItem.getTemas().length; i++) {

				if (i == inscripcionItem.getTemas().length - 1) {
					temas += "'" + inscripcionItem.getTemas()[i] + "'";
				} else {
					temas += "'" + inscripcionItem.getTemas()[i] + "',";
				}
			}

			sql.WHERE("TEMACURSO.IDTEMACURSO IN (" + temas + ")");
		}

		if (inscripcionItem.getIdFormador() != null) {
			sql.WHERE("PERCURSO2.IDPERSONA ='" + inscripcionItem.getIdFormador() + "'");
		}

		if(inscripcionItem.getCertificadoEmitido() != null) {
			// 0 --> TODOS
			// 1 --> Si tiene certificadoEmitido
			// 2 --> No tiene certificadoEmitido
		
			if(inscripcionItem.getCertificadoEmitido() == 1) {
				sql.WHERE("INSC.CERTIFICADOEMITIDO = '1'");
			}else if(inscripcionItem.getCertificadoEmitido() == 2) {
				sql.WHERE("INSC.CERTIFICADOEMITIDO = '0' OR INSC.CERTIFICADOEMITIDO IS NULL");
			}
		}
		
		if (inscripcionItem.getPagada() != null) {
			// 0 --> TODOS
			// 1 --> Si esta pagada la inscripcion
			// 2 --> No esta pagada la inscripcion
			if (inscripcionItem.getPagada() == 1)
				sql.WHERE("INSC.PAGADA ='1'");
			else if(inscripcionItem.getPagada() == 2)
				sql.WHERE("INSC.PAGADA ='0' OR INSC.PAGADA IS NULL");
		}
		
		if(inscripcionItem.getIdCalificacion() != null) {
			if (inscripcionItem.getIdCalificacion() >= 0) {
				sql.WHERE("INSC.IDCALIFICACION = '" + inscripcionItem.getIdCalificacion() + "'");
			}else {
				if(inscripcionItem.getIdCalificacion() == -2) // Sin calificacion
					sql.WHERE("INSC.IDCALIFICACION IS NULL");
			}

		}
		
		sql.GROUP_BY("INSC.IDINSCRIPCION, INSC.IDESTADOINSCRIPCION, INSC.IDPERSONA, INSC.EMITIRCERTIFICADO, INSC.CERTIFICADOEMITIDO,CURSO.IDESTADOCURSO, CURSO.IDCURSO, INSC.IDINSTITUCION, CURSO.CODIGOCURSO, CURSO.NOMBRECURSO, CAT.DESCRIPCION, CONCAT(CURSO.PRECIODESDE|| ' - ', CURSO.PRECIOHASTA), TO_CHAR(CURSO.FECHAIMPARTICIONDESDE,'DD/MM/YYYY'), TO_CHAR(CURSO.FECHAIMPARTICIONHASTA,'DD/MM/YYYY'), TO_CHAR(INSC.FECHASOLICITUD,'DD/MM/YYYY'), CAT2.DESCRIPCION, INSC.CALIFICACION,INSC.IDCALIFICACION, INSC.IDPETICIONSUSCRIPCION, CURSO.MINIMOASISTENCIA");

		return sql.toString();
	}
	
	public String selectInscripcionByPrimaryKey(InscripcionItem inscripcionItem) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("INSC.IDINSCRIPCION");
		sql.SELECT("INSC.IDESTADOINSCRIPCION");
		sql.SELECT("INSC.IDPERSONA");
		sql.SELECT("INSC.EMITIRCERTIFICADO");
		sql.SELECT("INSC.CERTIFICADOEMITIDO");
		sql.SELECT("CURSO.IDESTADOCURSO");
		sql.SELECT("CURSO.IDCURSO");
		sql.SELECT("INSC.IDINSTITUCION");
		sql.SELECT("CURSO.CODIGOCURSO");
		sql.SELECT("CURSO.NOMBRECURSO");
		sql.SELECT("CAT.DESCRIPCION AS ESTADOCURSO");
		sql.SELECT("CONCAT(CURSO.PRECIODESDE|| ' - ', CURSO.PRECIOHASTA) AS PRECIOCURSO");
		sql.SELECT("TO_CHAR(CURSO.FECHAIMPARTICIONDESDE,'DD/MM/YYYY') AS FECHAIMPARTICIONDESDE");
		sql.SELECT("TO_CHAR(CURSO.FECHAIMPARTICIONHASTA,'DD/MM/YYYY') AS FECHAIMPARTICIONHASTA");
		sql.SELECT("TO_CHAR(INSC.FECHASOLICITUD,'DD/MM/YYYY') AS FECHASOLICITUD");
		sql.SELECT("case when NVL(CURSO.MINIMOASISTENCIA,0) > DECODE(count (idasistenciaevento),0,-1) then 'NO' else 'SI' end MINIMAASISTENCIA");
		sql.SELECT("CAT2.DESCRIPCION AS ESTADOINSCRIPCION");
		sql.SELECT("INSC.CALIFICACION");
		sql.SELECT("INSC.IDPETICIONSUSCRIPCION");

		sql.FROM("FOR_INSCRIPCION INSC");

		sql.INNER_JOIN("FOR_CURSO CURSO ON INSC.IDCURSO = CURSO.IDCURSO");
		sql.LEFT_OUTER_JOIN("for_evento_curso eventocurso on eventocurso.idcurso = CURSO.idcurso");
		sql.LEFT_OUTER_JOIN("age_asistencia_evento asis on asis.idevento = eventocurso.idevento");
		sql.INNER_JOIN("FOR_ESTADOCURSO ESTADOCURSO ON CURSO.IDESTADOCURSO = ESTADOCURSO.IDESTADOCURSO");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS CAT ON (CAT.IDRECURSO = ESTADOCURSO.DESCRIPCION AND CAT.IDLENGUAJE = '1' )");
		sql.INNER_JOIN("FOR_ESTADOINSCRIPCION ESTADOINSC ON INSC.IDESTADOINSCRIPCION = ESTADOINSC.IDESTADOINSCRIPCION");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS CAT2 ON (CAT2.IDRECURSO = ESTADOINSC.DESCRIPCION AND CAT2.IDLENGUAJE = '1' )");
		sql.INNER_JOIN("FOR_VISIBILIDADCURSO VISIBILIDAD ON CURSO.IDVISIBILIDADCURSO = VISIBILIDAD.IDVISIBILIDADCURSO");
		sql.LEFT_OUTER_JOIN("FOR_TEMACURSO_CURSO TEMACURSO ON (TEMACURSO.IDCURSO = CURSO.IDCURSO AND TEMACURSO.IDINSTITUCION = CURSO.IDINSTITUCION AND TEMACURSO.FECHABAJA IS NULL)");
		sql.LEFT_OUTER_JOIN(
				"FOR_PERSONA_CURSO PERCURSO2 ON PERCURSO2.IDCURSO = CURSO.IDCURSO AND PERCURSO2.TUTOR = '1' AND CURSO.IDINSTITUCION = PERCURSO2.IDINSTITUCION");
		
		sql.WHERE("INSC.IDINSCRIPCION = '" + inscripcionItem.getIdInscripcion() + "'");
		sql.ORDER_BY("FECHASOLICITUD DESC");
		
		sql.GROUP_BY("INSC.IDINSCRIPCION, INSC.IDESTADOINSCRIPCION, INSC.IDPERSONA, INSC.EMITIRCERTIFICADO, INSC.CERTIFICADOEMITIDO,CURSO.IDESTADOCURSO, CURSO.IDCURSO, INSC.IDINSTITUCION, CURSO.CODIGOCURSO, CURSO.NOMBRECURSO, CAT.DESCRIPCION, CONCAT(CURSO.PRECIODESDE|| ' - ', CURSO.PRECIOHASTA), TO_CHAR(CURSO.FECHAIMPARTICIONDESDE,'DD/MM/YYYY'), TO_CHAR(CURSO.FECHAIMPARTICIONHASTA,'DD/MM/YYYY'), TO_CHAR(INSC.FECHASOLICITUD,'DD/MM/YYYY'), CAT2.DESCRIPCION, INSC.CALIFICACION, INSC.IDPETICIONSUSCRIPCION, CURSO.MINIMOASISTENCIA");

		return sql.toString();
	}

	public String getCalificacionesEmitidas(String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("CAL.IDCALIFICACION");
		sql.SELECT("CAT.DESCRIPCION");

		sql.FROM("FOR_CALIFICACIONES CAL");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON CAL.DESCRIPCION = CAT.IDRECURSO");

		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("CAL.FECHABAJA IS NULL");
		sql.WHERE("CAT.NOMBRETABLA = 'FOR_CALIFICACIONES'");

		sql.ORDER_BY("CAT.DESCRIPCION");

		return sql.toString();
	}

	public String getCountIncriptions(String idCurso) {

		SQL sql = new SQL();
		SQL sql1 = new SQL();

		sql.SELECT("NVL(SUM(DECODE(IDESTADOINSCRIPCION,1,CUANTAS)),0) AS PENDIENTE");
		sql.SELECT("NVL(SUM(DECODE(IDESTADOINSCRIPCION,2,CUANTAS)),0) AS RECHAZADO");
		sql.SELECT("NVL(SUM(DECODE(IDESTADOINSCRIPCION,3,CUANTAS)),0) AS APROBADO");
		sql.SELECT("NVL(SUM(DECODE(IDESTADOINSCRIPCION,4,CUANTAS)),0) AS CANCELADO");

		sql1.SELECT("COUNT(IDINSCRIPCION) AS CUANTAS");
		sql1.SELECT("ins.IDESTADOINSCRIPCION");
		sql1.SELECT("IDCURSO");
		sql1.FROM("FOR_INSCRIPCION ins");
		sql1.LEFT_OUTER_JOIN("FOR_ESTADOINSCRIPCION est ON ins.IDESTADOINSCRIPCION = EST.IDESTADOINSCRIPCION");
		
		sql1.WHERE("ins.IDCURSO = '" + idCurso + "'");
		sql1.GROUP_BY("ins.IDESTADOINSCRIPCION, IDCURSO");
		
		sql.FROM("(" + sql1 + ")");
		
		return sql.toString();
	}

	public String compruebaPlazas(String idCurso) {
		
		SQL sql = new SQL();
		
		sql.SELECT("FC.IDCURSO");
		sql.SELECT("FC.NUMEROPLAZAS");
		sql.SELECT("FC.NOMBRECURSO");
		sql.SELECT("COUNT(IDINSCRIPCION) AS INSCRIPCIONES");
		
		sql.FROM("FOR_CURSO FC");
		sql.LEFT_OUTER_JOIN("FOR_INSCRIPCION FI ON FC.IDCURSO = FI.IDCURSO");
		
		sql.WHERE("FC.IDCURSO = '" + idCurso + "'");
		
		sql.GROUP_BY("FC.IDCURSO, FC.NOMBRECURSO, NUMEROPLAZAS");

		return sql.toString();
	}
	
	
	public String searchCourseByIdcurso(String idCurso, Short idInstitucion) {

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
		sql.SELECT("CURSO.FECHAINSCRIPCIONDESDE AS FECHAINSCRIPCIONDESDEDATE");
		sql.SELECT("CURSO.FECHAINSCRIPCIONHASTA AS FECHAINSCRIPCIONHASTADATE");
		sql.SELECT("CONCAT(CURSO.FECHAIMPARTICIONDESDE|| ' - ', CURSO.FECHAIMPARTICIONHASTA ) AS FECHAIMPARTICION");
		sql.SELECT("TO_CHAR(CURSO.FECHAIMPARTICIONDESDE,'DD/MM/YYYY') AS FECHAIMPARTICIONDESDE");
		sql.SELECT("TO_CHAR(CURSO.FECHAIMPARTICIONHASTA,'DD/MM/YYYY') AS FECHAIMPARTICIONHASTA");
		sql.SELECT("CURSO.FLAGARCHIVADO");
		sql.SELECT("CURSO.LUGAR");
		sql.SELECT("CURSO.NUMEROPLAZAS as plazasDisponibles");
		sql.SELECT("CURSO.AUTOVALIDACIONINSCRIPCION");
		sql.SELECT("CURSO.MINIMOASISTENCIA");
		sql.SELECT("count(TEMACURSO.idtemacurso) as NUMTEMAS");
		
		sql.FROM("FOR_CURSO CURSO");
		
		sql.INNER_JOIN("CEN_INSTITUCION INSTITUCION ON CURSO.IDINSTITUCION = INSTITUCION.IDINSTITUCION");
		sql.INNER_JOIN("FOR_ESTADOCURSO ESTADO ON CURSO.IDESTADOCURSO = ESTADO.IDESTADOCURSO");
		sql.INNER_JOIN("FOR_VISIBILIDADCURSO VISIBILIDAD ON CURSO.IDVISIBILIDADCURSO = VISIBILIDAD.IDVISIBILIDADCURSO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON (CAT.IDRECURSO = ESTADO.DESCRIPCION AND CAT.IDLENGUAJE = '1' )");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT1 ON (CAT1.IDRECURSO = VISIBILIDAD.DESCRIPCION AND CAT1.IDLENGUAJE = '1' )");
		sql.LEFT_OUTER_JOIN("FOR_TEMACURSO_CURSO TEMACURSO ON (TEMACURSO.IDCURSO = CURSO.IDCURSO AND TEMACURSO.IDINSTITUCION = CURSO.IDINSTITUCION AND TEMACURSO.FECHABAJA IS NULL)");
		sql.LEFT_OUTER_JOIN("FOR_PERSONA_CURSO PERCURSO2 ON PERCURSO2.IDCURSO = CURSO.IDCURSO AND PERCURSO2.TUTOR = '1' AND CURSO.IDINSTITUCION = PERCURSO2.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN("CEN_PERSONA PER ON PER.IDPERSONA = PERCURSO2.IDPERSONA");
		sql.WHERE("(CURSO.IDINSTITUCION = '" + idInstitucion + "' OR (CURSO.IDINSTITUCION <> '" + idInstitucion + "' AND CURSO.IDVISIBILIDADCURSO = '0'))");
		sql.WHERE("CURSO.IDCURSO = '" + idCurso + "'");
		sql.GROUP_BY("CURSO.IDCURSO, CURSO.CODIGOCURSO, CURSO.NOMBRECURSO, CURSO.IDINSTITUCION, CURSO.IDESTADOCURSO , CURSO.IDVISIBILIDADCURSO,\r\n" + 
				"TO_CHAR(CURSO.DESCRIPCION), CAT.DESCRIPCION, DECODE(CURSO.IDINSTITUCION,'2005', CAT1.DESCRIPCION, INSTITUCION.ABREVIATURA), \r\n" + 
				"CONCAT(CURSO.PRECIODESDE|| ' - ', CURSO.PRECIOHASTA), CONCAT(CURSO.FECHAINSCRIPCIONDESDE|| ' - ', CURSO.FECHAINSCRIPCIONHASTA), \r\n" + 
				"CURSO.FECHAINSCRIPCIONDESDE, CURSO.FECHAINSCRIPCIONHASTA, CONCAT(CURSO.FECHAIMPARTICIONDESDE|| ' - ', \r\n" + 
				"CURSO.FECHAIMPARTICIONHASTA ), TO_CHAR(CURSO.FECHAIMPARTICIONDESDE,'DD/MM/YYYY'), \r\n" + 
				"TO_CHAR(CURSO.FECHAIMPARTICIONHASTA,'DD/MM/YYYY'), CURSO.FLAGARCHIVADO, CURSO.LUGAR, CURSO.NUMEROPLAZAS, \r\n" + 
				"CURSO.AUTOVALIDACIONINSCRIPCION, CURSO.MINIMOASISTENCIA");

		return sql.toString();
	}
	
	public String selectMaxIdInscripcion() {

		SQL sql = new SQL();

		sql.SELECT("max(idinscripcion) as IDINSCRIPCION1");
		sql.SELECT("max(idinscripcion) as IDINSCRIPCION2");
		sql.FROM("for_inscripcion");
		return sql.toString();
	}
	
	public String checkMinimaAsistencia(Short idInstitucion, Long idInscripcion) {

		SQL sql = new SQL();

		sql.SELECT(
				"case when fc.MINIMOASISTENCIA > count (idasistenciaevento) then 0 else 1 end asistenciaminima");
		sql.FROM("for_inscripcion insc");
		sql.INNER_JOIN("for_curso fc on insc.idcurso = fc.idcurso");
		sql.INNER_JOIN("for_evento_curso eventocurso on eventocurso.idcurso = fc.idcurso");
		sql.INNER_JOIN("age_asistencia_evento asis on asis.idevento = eventocurso.idevento");
		sql.WHERE("insc.idinscripcion = '" + idInscripcion + "'");
		sql.WHERE("insc.idinstitucion = '" + idInstitucion + "'");
		sql.GROUP_BY("fc.minimoasistencia");

		return sql.toString();
	}
	
	public String compruebaPlazasAprobadas(String idCurso) {
		
		SQL sql = new SQL();
		
		sql.SELECT("FC.IDCURSO");
		sql.SELECT("FC.NUMEROPLAZAS");
		sql.SELECT("FC.NOMBRECURSO");
		sql.SELECT("NVL(COUNT(IDINSCRIPCION),0) AS INSCRIPCIONES");
		
		sql.FROM("FOR_CURSO FC");
		sql.LEFT_OUTER_JOIN("FOR_INSCRIPCION FI ON FC.IDCURSO = FI.IDCURSO");
		
		sql.WHERE("FC.IDCURSO = '" + idCurso + "'");
		sql.WHERE("FI.IDESTADOINSCRIPCION = 3");
		
		sql.GROUP_BY("FC.IDCURSO, FC.NOMBRECURSO, NUMEROPLAZAS");

		return sql.toString();
	}

}

