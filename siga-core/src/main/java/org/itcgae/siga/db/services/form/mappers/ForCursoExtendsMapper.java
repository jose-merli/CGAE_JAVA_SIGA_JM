package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.ForCurso;
import org.itcgae.siga.db.mappers.ForCursoMapper;
import org.itcgae.siga.db.services.form.providers.ForCursoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ForCursoExtendsMapper extends ForCursoMapper {

	@SelectProvider(type = ForCursoSqlExtendsProvider.class, method = "selectCursos")
	@Results({ @Result(column = "IDCURSO", property = "idCurso", jdbcType = JdbcType.NUMERIC),
			@Result(column = "CODIGOCURSO", property = "codigoCurso", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBRECURSO", property = "nombreCurso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "colegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "descripcionEstado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LUGAR", property = "lugar", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "descripcionEstado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MINIMOASISTENCIA", property = "minimoAsistencia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PLAZASDISPONIBLES", property = "plazasDisponibles", jdbcType = JdbcType.NUMERIC),
			@Result(column = "VISIBILIDAD", property = "visibilidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDVISIBILIDADCURSO", property = "idVisibilidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PRECIOCURSO", property = "precioCurso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAINSCRIPCION", property = "fechaInscripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAINSCRIPCIONDESDE", property = "fechaInscripcionDesde", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAINSCRIPCIONHASTA", property = "fechaInscripcionHasta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAIMPARTICION", property = "fechaImparticion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAIMPARTICIONDESDE", property = "fechaImparticionDesde", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAIMPARTICIONHASTA", property = "fechaImparticionHasta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREAPELLIDOSFORMADOR", property = "nombreApellidosFormador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FLAGARCHIVADO", property = "flagArchivado", jdbcType = JdbcType.NUMERIC),
			@Result(column = "AUTOVALIDACIONINSCRIPCION", property = "autovalidacionInscripcion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "ENCUESTASATISFACCION", property = "encuesta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INFORMACIONADICIONAL", property = "adicional", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DOCUMENTACIONADJUNTA", property = "adjunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAFINIMPARTICION", property = "fechaImparticionHasta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAINICIOIMPARTICION", property = "fechaImparticionDesde", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROSESIONES", property = "numeroSesiones", jdbcType = JdbcType.NUMERIC)
	
	})
	List<CursoItem> selectCursos(Short idInstitucion, CursoItem cursoItem, String idLenguaje);
	
	@SelectProvider(type = ForCursoSqlExtendsProvider.class, method = "selectCursosFechaAuto")
	@Results({ @Result(column = "IDCURSO", property = "idcurso", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAIMPARTICIONDESDE", property = "fechaimparticiondesde", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAIMPARTICIONHASTA", property = "fechaimparticionhasta", jdbcType = JdbcType.VARCHAR)
	})
	List<ForCurso> selectCursosFechaAuto(ForCurso forCurso);
	
	@SelectProvider(type = ForCursoSqlExtendsProvider.class, method = "searchCourseByIdcurso")
	@Results({ @Result(column = "IDCURSO", property = "idCurso", jdbcType = JdbcType.NUMERIC),
			@Result(column = "CODIGOCURSO", property = "codigoCurso", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBRECURSO", property = "nombreCurso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "colegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "descripcionEstado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LUGAR", property = "lugar", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "descripcionEstado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MINIMOASISTENCIA", property = "minimoAsistencia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PLAZASDISPONIBLES", property = "plazasDisponibles", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VISIBILIDAD", property = "visibilidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDVISIBILIDADCURSO", property = "idVisibilidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PRECIOCURSO", property = "precioCurso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAINSCRIPCION", property = "fechaInscripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAINSCRIPCIONDESDE", property = "fechaInscripcionDesde", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAINSCRIPCIONHASTA", property = "fechaInscripcionHasta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAIMPARTICION", property = "fechaImparticion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAIMPARTICIONDESDE", property = "fechaImparticionDesde", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAIMPARTICIONHASTA", property = "fechaImparticionHasta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREAPELLIDOSFORMADOR", property = "nombreApellidosFormador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FLAGARCHIVADO", property = "flagArchivado", jdbcType = JdbcType.NUMERIC),
			@Result(column = "AUTOVALIDACIONINSCRIPCION", property = "autovalidacionInscripcion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "ENCUESTASATISFACCION", property = "encuesta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INFORMACIONADICIONAL", property = "adicional", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DOCUMENTACIONADJUNTA", property = "adjunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAFINIMPARTICION", property = "fechaImparticionHasta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAINICIOIMPARTICION", property = "fechaImparticionDesde", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROSESIONES", property = "numeroSesiones", jdbcType = JdbcType.NUMERIC)

	})
	CursoItem searchCourseByIdcurso(String idCurso, Short idInstitucion, String idLenguaje);
	
	@UpdateProvider(type = ForCursoSqlExtendsProvider.class, method = "updateCourse")
	int updateCourse (CursoItem cursoItem, AdmUsuarios usuario);
	
	
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_CURSO
	 * @mbg.generated  Wed Jan 02 18:01:32 CET 2019
	 */
	@Select({ "select", "IDCURSO, IDINSTITUCION, CODIGOCURSO, NOMBRECURSO, PRECIODESDE, PRECIOHASTA, ",
			"FECHAINSCRIPCIONDESDE, FECHAINSCRIPCIONHASTA, FECHAIMPARTICIONDESDE, FECHAIMPARTICIONHASTA, ",
			"NUMEROPLAZAS, IDVISIBILIDADCURSO, IDESTADOCURSO, USUMODIFICACION, FECHAMODIFICACION, ",
			"IDTIPOSERVICIO, LUGAR, MINIMOASISTENCIA, AUTOVALIDACIONINSCRIPCION, DOCUMENTACIONADJUNTA, ",
			"ENCUESTASATISFACCION, INFORMACIONADICIONAL, FLAGARCHIVADO, IDSERVICIO, DESCRIPCION", "from FOR_CURSO",
			"where IDCURSO = #{idcurso,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDCURSO", property = "idcurso", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOCURSO", property = "codigocurso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRECURSO", property = "nombrecurso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PRECIODESDE", property = "preciodesde", jdbcType = JdbcType.DECIMAL),
			@Result(column = "PRECIOHASTA", property = "preciohasta", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAINSCRIPCIONDESDE", property = "fechainscripciondesde", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAINSCRIPCIONHASTA", property = "fechainscripcionhasta", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAIMPARTICIONDESDE", property = "fechaimparticiondesde", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAIMPARTICIONHASTA", property = "fechaimparticionhasta", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "NUMEROPLAZAS", property = "numeroplazas", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDVISIBILIDADCURSO", property = "idvisibilidadcurso", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDESTADOCURSO", property = "idestadocurso", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDTIPOSERVICIO", property = "idtiposervicio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "LUGAR", property = "lugar", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MINIMOASISTENCIA", property = "minimoasistencia", jdbcType = JdbcType.DECIMAL),
			@Result(column = "AUTOVALIDACIONINSCRIPCION", property = "autovalidacioninscripcion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DOCUMENTACIONADJUNTA", property = "documentacionadjunta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ENCUESTASATISFACCION", property = "encuestasatisfaccion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INFORMACIONADICIONAL", property = "informacionadicional", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FLAGARCHIVADO", property = "flagarchivado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDSERVICIO", property = "idservicio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR) })
	ForCurso selectByPrimaryKeyExtends(Long idcurso);
}
