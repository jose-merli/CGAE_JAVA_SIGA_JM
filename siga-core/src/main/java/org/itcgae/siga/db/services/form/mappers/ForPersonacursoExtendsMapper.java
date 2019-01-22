package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.form.FormadorCursoItem;
import org.itcgae.siga.db.mappers.ForPersonaCursoMapper;
import org.itcgae.siga.db.services.form.providers.ForPersonacursoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ForPersonacursoExtendsMapper extends ForPersonaCursoMapper{

	@SelectProvider(type = ForPersonacursoSqlExtendsProvider.class, method = "getTrainersLabels")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBRE", property = "nombreCompleto", jdbcType = JdbcType.VARCHAR),
	})
	List<FormadorCursoItem> getTrainersLabels(String idInstitucion, String idCurso);
	

	@SelectProvider(type = ForPersonacursoSqlExtendsProvider.class, method = "getTrainersSession")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBRE", property = "nombreCompleto", jdbcType = JdbcType.VARCHAR),
	})
	List<FormadorCursoItem> getTrainersSession(String idEvento);
	
	
	@SelectProvider(type = ForPersonacursoSqlExtendsProvider.class, method = "getTrainersCourse")
	@Results({
		@Result(column = "IDFORMADOR", property = "idFormador", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDCURSO", property = "idCurso", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS", property = "apellidos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDROL", property = "idRol", jdbcType = JdbcType.NUMERIC),
		@Result(column = "ROL", property = "rol", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCOSTE", property = "idTipoCoste", jdbcType = JdbcType.NUMERIC),
		@Result(column = "TIPOCOSTE", property = "tipoCoste", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TARIFA", property = "tarifa", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "TUTOR", property = "tutor", jdbcType = JdbcType.NUMERIC),
	})
	List<FormadorCursoItem> getTrainersCourse(String idInstitucion, String idCurso, String idLenguaje);
	
}
