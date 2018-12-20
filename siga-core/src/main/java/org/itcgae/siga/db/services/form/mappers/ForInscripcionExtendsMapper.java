package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ForInscripcionMapper;
import org.itcgae.siga.db.services.form.providers.ForInscripcionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ForInscripcionExtendsMapper extends ForInscripcionMapper {

	@SelectProvider(type = ForInscripcionSqlExtendsProvider.class, method = "selectInscripciones")
	@Results({ @Result(column = "IDINSCRIPCION", property = "idInscripcion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "CODIGOCURSO", property = "codigoCurso", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBRECURSO", property = "nombreCurso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PRECIOCURSO", property = "precioCurso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAIMPARTICIONDESDE", property = "fechaImparticionDesdeFormat", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAIMPARTICIONHASTA", property = "fechaImparticionHastaFormat", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHASOLICITUD", property = "fechaSolicitud", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADOINSCRIPCION", property = "estadoInscripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MINIMAASISTENCIA", property = "minimaAsistencia", jdbcType = JdbcType.NUMERIC),
			@Result(column = "CALIFICACION", property = "calificacion", jdbcType = JdbcType.NUMERIC)
	})
	List<InscripcionItem> selectInscripciones(InscripcionItem cursoItem);	
	
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
}
