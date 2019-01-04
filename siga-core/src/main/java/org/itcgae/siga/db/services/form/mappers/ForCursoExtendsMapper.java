package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
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
			@Result(column = "DOCUMENTACIONADJUNTA", property = "adjunto", jdbcType = JdbcType.VARCHAR)
	
	})
	List<CursoItem> selectCursos(Short idInstitucion, CursoItem cursoItem);
	
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
			@Result(column = "DOCUMENTACIONADJUNTA", property = "adjunto", jdbcType = JdbcType.VARCHAR)
	
	})
	CursoItem searchCourseByIdcurso(String idCurso, Short idInstitucion);
	
	@UpdateProvider(type = ForCursoSqlExtendsProvider.class, method = "updateCourse")
	int updateCourse (CursoItem cursoItem, AdmUsuarios usuario);
	
	
}
