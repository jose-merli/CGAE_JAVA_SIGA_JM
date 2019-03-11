package org.itcgae.siga.db.services.age.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.age.AsistenciaEventoItem;
import org.itcgae.siga.db.mappers.AgeAsistenciaEventoMapper;
import org.itcgae.siga.db.services.age.providers.AgeAsistenciaeventoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface AgeAsistenciaeventoExtendsMapper extends AgeAsistenciaEventoMapper{
		
	
	@SelectProvider(type = AgeAsistenciaeventoSqlExtendsProvider.class, method = "getEntryListCourse")
	@Results({
		@Result(column = "IDASISTENCIAEVENTO", property = "idAsistenciaEvento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDEVENTO", property = "idEvento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSCRIPCION", property = "idInscripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ASISTENCIA", property = "asistencia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCURSO", property = "idCurso", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPERSONA", property = "nombrePersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR)

	})
	List<AsistenciaEventoItem> getEntryListCourse(String idLenguaje);

	
}
