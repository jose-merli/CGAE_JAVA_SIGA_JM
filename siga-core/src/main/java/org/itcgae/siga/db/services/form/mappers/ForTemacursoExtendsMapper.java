package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ForTemacursoMapper;
import org.itcgae.siga.db.services.form.providers.ForTemacursoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ForTemacursoExtendsMapper extends ForTemacursoMapper{

	@SelectProvider(type = ForTemacursoSqlExtendsProvider.class, method = "distinctTemaCurso")
	@Results({
		@Result(column = "IDTEMACURSO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> distinctTemaCurso(String idLenguaje);
}
