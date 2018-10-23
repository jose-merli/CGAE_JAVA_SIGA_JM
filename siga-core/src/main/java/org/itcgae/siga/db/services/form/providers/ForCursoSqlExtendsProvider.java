package org.itcgae.siga.db.services.form.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.db.mappers.ForCursoSqlProvider;

public class ForCursoSqlExtendsProvider extends ForCursoSqlProvider {
	
	private static final String PLAZAS_DISPO_SI = "1";
	private static final String PLAZAS_DISPO_NO = "0";

	public String selectCursos(Short idInstitucion,CursoItem cursoItem) {

		SQL sql = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		sql.SELECT_DISTINCT("CURSO.IDCURSO");
		sql.SELECT_DISTINCT("CURSO.CODIGOCURSO");
		sql.SELECT_DISTINCT("CURSO.NOMBRECURSO");
		sql.SELECT_DISTINCT("ESTADO.IDESTADOCURSO");
		sql.SELECT_DISTINCT("CAT.DESCRIPCION AS ESTADO");
		sql.SELECT_DISTINCT("DECODE(CURSO.IDINSTITUCION,'"+ Short.toString(idInstitucion) +"', CAT1.DESCRIPCION, INSTITUCION.ABREVIATURA) AS VISIBILIDAD");
		sql.SELECT_DISTINCT("CONCAT(CURSO.PRECIODESDE|| ' - ', CURSO.PRECIOHASTA) AS PRECIOCURSO");
		sql.SELECT_DISTINCT("CONCAT(CURSO.FECHAINSCRIPCIONDESDE|| ' - ', CURSO.FECHAINSCRIPCIONHASTA) AS FECHAINSCRIPCION");
		sql.SELECT_DISTINCT("CONCAT(CURSO.FECHAIMPARTICIONDESDE|| ' - ', CURSO.FECHAIMPARTICIONHASTA ) AS FECHAIMPARTICION");
		sql.SELECT_DISTINCT("CONCAT(PER.NOMBRE ||' ',CONCAT(PER.APELLIDOS1 || ' ',PER.APELLIDOS2)) AS NOMBREAPELLIDOSFORMADOR");
		
		sql.FROM("FOR_CURSO CURSO");
		
		sql.INNER_JOIN("CEN_INSTITUCION INSTITUCION ON CURSO.IDINSTITUCION = INSTITUCION.IDINSTITUCION");
		sql.INNER_JOIN("FOR_ESTADOCURSO ESTADO ON CURSO.IDESTADOCURSO = ESTADO.IDESTADOCURSO");
		sql.INNER_JOIN("FOR_VISIBILIDADCURSO VISIBILIDAD ON CURSO.IDVISIBILIDADCURSO = VISIBILIDAD.IDVISIBILIDADCURSO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON (CAT.IDRECURSO = ESTADO.DESCRIPCION AND CAT.IDLENGUAJE = '1' )");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT1 ON (CAT1.IDRECURSO = VISIBILIDAD.DESCRIPCION AND CAT1.IDLENGUAJE = '1' )");
		sql.LEFT_OUTER_JOIN("FOR_TEMACURSO_CURSO TEMACURSO ON (TEMACURSO.IDCURSO = CURSO.IDCURSO AND TEMACURSO.IDINSTITUCION = CURSO.IDINSTITUCION AND TEMACURSO.FECHA_BAJA IS NULL)");
		sql.LEFT_OUTER_JOIN("FOR_PERSONA_CURSO PERCURSO ON PERCURSO.IDCURSO = CURSO.IDCURSO");
		sql.LEFT_OUTER_JOIN("FOR_PERSONA_CURSO PERCURSO2 ON PERCURSO2.IDCURSO = CURSO.IDCURSO AND PERCURSO2.TUTOR = '1'");
		sql.LEFT_OUTER_JOIN("CEN_PERSONA PER ON PER.IDPERSONA = PERCURSO2.IDPERSONA");
		
		
		if(cursoItem.getColegio() != null && cursoItem.getColegio() != "") {
			sql.WHERE("CURSO.IDINSTITUCION = '" + cursoItem.getColegio() + "'");
		}else {
			sql.WHERE("(CURSO.IDINSTITUCION = '" + idInstitucion + "' OR (IDINSTITUCION <> '" + idInstitucion + "' AND VISIBILIDAD.IDVISIBILIDADCURSO = '0'))");
		}
		
		if(cursoItem.getCodigoCurso() != null && cursoItem.getCodigoCurso() != "") {
			sql.WHERE("CURSO.CODIGOCURSO = '" + cursoItem.getCodigoCurso() + "'");
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
			sql.WHERE("CURSO.PRECIOHASTA >= " + Double.toString(cursoItem.getPrecioHasta()));
		}
		
		if(cursoItem.getFechaInscripcionDesde() != null) {
			String fechaInscripcionDesde = dateFormat.format(cursoItem.getFechaInscripcionDesde());
			sql.WHERE("TO_DATE(CURSO.FECHAINSCRIPCIONDESDE,'DD/MM/RRRR') >= TO_DATE('" + fechaInscripcionDesde + "','DD/MM/YYYY')");
		}
		
		if(cursoItem.getFechaInscripcionHasta() != null) {
			String fechaInscripcionHasta = dateFormat.format(cursoItem.getFechaInscripcionHasta());
			sql.WHERE("TO_DATE(CURSO.FECHAINSCRIPCIONHASTA,'DD/MM/RRRR') <= TO_DATE('" + fechaInscripcionHasta + "','DD/MM/YYYY')");
		}
		
		if(cursoItem.getFechaImparticionDesde() != null) {
			String fechaImparticionDesde = dateFormat.format(cursoItem.getFechaImparticionDesde());
			sql.WHERE("TO_DATE(FECHAIMPARTICIONDESDE,'DD/MM/RRRR') >= TO_DATE('" + fechaImparticionDesde + "','DD/MM/YYYY')");
		}
		
		if(cursoItem.getFechaImparticionHasta() != null) {
			String fechaImparticionHasta = dateFormat.format(cursoItem.getFechaImparticionHasta());
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
			
			
			if(PLAZAS_DISPO_SI.equals(cursoItem.getPlazasDisponibles())) {
				sql.WHERE("CURSO.NUMEROPLAZAS > " +"("+ sqlPlazasDispo +")");
			}
			
			if(PLAZAS_DISPO_NO.equals(cursoItem.getPlazasDisponibles())) {
				sql.WHERE("CURSO.NUMEROPLAZAS < " +"("+ sqlPlazasDispo +")");
			}
			
		}
		
		
		if (cursoItem.getNombreApellidosFormador() != null && cursoItem.getNombreApellidosFormador() != "") {
			sql.WHERE("UPPER( CONCAT(PER.NOMBRE ||' ',CONCAT(PER.APELLIDOS1 || ' ',PER.APELLIDOS2) )) LIKE UPPER('%" + cursoItem.getNombreApellidosFormador()+ "%')");
		}
		
		return sql.toString();
	}
	

}
