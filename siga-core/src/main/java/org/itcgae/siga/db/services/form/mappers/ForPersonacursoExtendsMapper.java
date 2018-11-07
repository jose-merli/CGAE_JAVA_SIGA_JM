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

	@SelectProvider(type = ForPersonacursoSqlExtendsProvider.class, method = "getTrainers")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBRE", property = "nombreCompleto", jdbcType = JdbcType.VARCHAR),
	})
	List<FormadorCursoItem> getTrainers(String idInstitucion, String idCurso);
}
