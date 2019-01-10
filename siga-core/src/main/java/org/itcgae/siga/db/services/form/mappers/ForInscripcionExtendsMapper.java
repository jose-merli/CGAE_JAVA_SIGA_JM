package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ForInscripcionMapper;
import org.itcgae.siga.db.services.form.providers.ForInscripcionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ForInscripcionExtendsMapper extends ForInscripcionMapper{

	@SelectProvider(type = ForInscripcionSqlExtendsProvider.class, method = "selectInscripciones")
	@Results({ @Result(column = "IDINSCRIPCION", property = "idInscripcion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "CODIGOCURSO", property = "codigoCurso", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBRECURSO", property = "nombreCurso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCURSO", property = "idCurso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDESTADOCURSO", property = "idEstadoCurso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDESTADOINSCRIPCION", property = "idEstadoInscripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PRECIOCURSO", property = "precioCurso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAIMPARTICIONDESDE", property = "fechaImparticionDesdeFormat", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAIMPARTICIONHASTA", property = "fechaImparticionHastaFormat", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHASOLICITUD", property = "fechaSolicitud", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADOINSCRIPCION", property = "estadoInscripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MINIMAASISTENCIA", property = "minimaAsistencia", jdbcType = JdbcType.NUMERIC),
			@Result(column = "CERTIFICADOEMITIDO", property = "certificadoEmitido", jdbcType = JdbcType.NUMERIC),
			@Result(column = "EMITIRCERTIFICADO", property = "emitirCertificado", jdbcType = JdbcType.NUMERIC),
			@Result(column = "CALIFICACION", property = "calificacion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDPETICIONSUSCRIPCION", property = "idPeticionSuscripcion", jdbcType = JdbcType.NUMERIC)

	})
	List<InscripcionItem> selectInscripciones(InscripcionItem inscripcionItem);	
	
	@SelectProvider(type = ForInscripcionSqlExtendsProvider.class, method = "getCalificacionesEmitidas")
	@Results({
		@Result(column = "IDCALIFICACION", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getCalificacionesEmitidas(String idLenguaje);
	

	@SelectProvider(type = ForInscripcionSqlExtendsProvider.class, method = "getCountIncriptions")
	@Results({ @Result(column = "PENDIENTE", property = "pendientes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "RECHAZADO", property = "rechazadas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APROBADO", property = "aprobadas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CANCELADO", property = "canceladas", jdbcType = JdbcType.VARCHAR),
	})
	InscripcionItem getCountIncriptions(String idCurso);	

	@SelectProvider(type = ForInscripcionSqlExtendsProvider.class, method = "compruebaPlazas")
	@Results({ @Result(column = "IDCURSO", property = "idCurso", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NUMEROPLAZAS", property = "numPlazas", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBRECURSO", property = "nombreCurso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INSCRIPCIONES", property = "inscripciones", jdbcType = JdbcType.NUMERIC)
	})
	CursoItem compruebaPlazas(String idCurso);	

	@SelectProvider(type = ForInscripcionSqlExtendsProvider.class, method = "searchCourseByIdcurso")
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
			@Result(column = "FLAGARCHIVADO", property = "flagArchivado", jdbcType = JdbcType.NUMERIC),
			@Result(column = "AUTOVALIDACIONINSCRIPCION", property = "autovalidacionInscripcion", jdbcType = JdbcType.NUMERIC)
	
	})
	CursoItem searchCourseByIdcurso(String idCurso, Short idInstitucion);
	
	@SelectProvider(type = ForInscripcionSqlExtendsProvider.class, method = "selectMaxIdInscripcion")
	@Results({
		@Result(column = "IDINSCRIPCION1", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSCRIPCION2", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectMaxIdInscripcion();
}
