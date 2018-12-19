package org.itcgae.siga.db.services.form.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.db.mappers.ForInscripcionSqlProvider;

public class ForInscripcionSqlExtendsProvider extends ForInscripcionSqlProvider {
	
	public String selectInscripciones(InscripcionItem inscripcionItem) {

		SQL sql = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		sql.SELECT("INSC.IDINSCRIPCION");
		sql.SELECT("INSC.IDESTADOINSCRIPCION");
		sql.SELECT("CURSO.IDESTADOCURSO");
		sql.SELECT("INSC.IDINSTITUCION");
		sql.SELECT("CURSO.CODIGOCURSO");
		sql.SELECT("CURSO.NOMBRECURSO");
		sql.SELECT("CAT.DESCRIPCION AS ESTADOCURSO");
		sql.SELECT("CONCAT(CURSO.PRECIODESDE|| ' - ', CURSO.PRECIOHASTA) AS PRECIOCURSO");
		sql.SELECT("TO_CHAR(CURSO.FECHAIMPARTICIONDESDE,'DD/MM/YYYY') AS FECHAIMPARTICIONDESDE");
		sql.SELECT("TO_CHAR(CURSO.FECHAIMPARTICIONHASTA,'DD/MM/YYYY') AS FECHAIMPARTICIONHASTA");
		sql.SELECT("TO_CHAR(INSC.FECHASOLICITUD,'DD/MM/YYYY') AS FECHASOLICITUD");
		sql.SELECT("CURSO.MINIMOASISTENCIA AS MINIMAASISTENCIA");
		sql.SELECT("CAT2.DESCRIPCION AS ESTADOINSCRIPCION");
		sql.SELECT("INSC.CALIFICACION");
		
		sql.FROM("FOR_INSCRIPCION INSC");
		
		sql.INNER_JOIN("FOR_CURSO CURSO ON INSC.IDCURSO = CURSO.IDCURSO");
		sql.INNER_JOIN("FOR_ESTADOCURSO ESTADOCURSO ON CURSO.IDESTADOCURSO = ESTADOCURSO.IDESTADOCURSO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON (CAT.IDRECURSO = ESTADOCURSO.DESCRIPCION AND CAT.IDLENGUAJE = '1' )");
		sql.INNER_JOIN("FOR_ESTADOINSCRIPCION ESTADOINSC ON INSC.IDESTADOINSCRIPCION = ESTADOINSC.IDESTADOINSCRIPCION");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT2 ON (CAT2.IDRECURSO = ESTADOINSC.DESCRIPCION AND CAT2.IDLENGUAJE = '1' )");
		sql.INNER_JOIN("FOR_VISIBILIDADCURSO VISIBILIDAD ON CURSO.IDVISIBILIDADCURSO = VISIBILIDAD.IDVISIBILIDADCURSO");
		sql.LEFT_OUTER_JOIN("FOR_PERSONA_CURSO PERCURSO2 ON PERCURSO2.IDCURSO = CURSO.IDCURSO AND PERCURSO2.TUTOR = '1' AND CURSO.IDINSTITUCION = PERCURSO2.IDINSTITUCION");
		
		
		if(inscripcionItem.getColegio() != null && inscripcionItem.getColegio() != "") {
			sql.WHERE("INSC.IDINSTITUCION = '" + inscripcionItem.getColegio() + "'");
		}
		
		if(inscripcionItem.getCodigoCurso() != null && inscripcionItem.getCodigoCurso() != "") {
			sql.WHERE("CURSO.CODIGOCURSO = '" + inscripcionItem.getCodigoCurso() + "'");
		}
		
		if (inscripcionItem.getNombreCurso() != null && inscripcionItem.getNombreCurso() != "") {
			sql.WHERE("UPPER(CURSO.NOMBRECURSO) LIKE UPPER('%" + inscripcionItem.getNombreCurso() + "%')");
		}
		
		if(inscripcionItem.getIdEstadoCurso() != null && inscripcionItem.getIdEstadoCurso() != "") {
			sql.WHERE("ESTADOCURSO.IDESTADOCURSO = '" + inscripcionItem.getIdEstadoCurso() + "'");
		}
		
		if(inscripcionItem.getIdEstadoInscripcion() != null && inscripcionItem.getIdEstadoInscripcion() != "") {
			sql.WHERE("ESTADOINSC.IDESTADOINSCRIPCION = '" + inscripcionItem.getIdEstadoInscripcion() + "'");
		}
		
		if(inscripcionItem.getIdVisibilidad() != null && inscripcionItem.getIdVisibilidad() != "") {
			sql.WHERE("VISIBILIDAD.IDVISIBILIDADCURSO = '" + inscripcionItem.getIdVisibilidad() + "'");
		}
		
		if(inscripcionItem.getFechaInscripcionDesde() != null) {
			String fechaInscripcionDesde = dateFormat.format(inscripcionItem.getFechaInscripcionDesde());
			sql.WHERE("TO_DATE(CURSO.FECHAINSCRIPCIONDESDE,'DD/MM/RRRR') >= TO_DATE('" + fechaInscripcionDesde + "','DD/MM/YYYY')");
		}
		
		if(inscripcionItem.getFechaInscripcionHasta() != null) {
			String fechaInscripcionHasta = dateFormat.format(inscripcionItem.getFechaInscripcionHasta());
			sql.WHERE("TO_DATE(CURSO.FECHAINSCRIPCIONHASTA,'DD/MM/RRRR') <= TO_DATE('" + fechaInscripcionHasta + "','DD/MM/YYYY')");
		}
		
		if(inscripcionItem.getFechaImparticionDesde() != null) {
			String fechaImparticionDesde = dateFormat.format(inscripcionItem.getFechaImparticionDesde());
			sql.WHERE("TO_DATE(FECHAIMPARTICIONDESDE,'DD/MM/RRRR') >= TO_DATE('" + fechaImparticionDesde + "','DD/MM/YYYY')");
		}
		
		if(inscripcionItem.getFechaImparticionHasta() != null) {
			String fechaImparticionHasta = dateFormat.format(inscripcionItem.getFechaImparticionHasta());
			sql.WHERE("TO_DATE(FECHAIMPARTICIONHASTA,'DD/MM/RRRR') <= TO_DATE('" + fechaImparticionHasta + "','DD/MM/YYYY')");
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
		
		// TODO Falta filtro de "Certificado emitido -> Si, No, Todos"
		
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
				else if(inscripcionItem.getIdCalificacion() == -1) // Todas las calificadas
					sql.WHERE("INSC.IDCALIFICACION IS NOT NULL");
			}
				
		}
				
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

}
