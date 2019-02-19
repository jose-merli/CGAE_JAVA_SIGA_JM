package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ForTemacursoPersonaMapper;
import org.itcgae.siga.db.services.form.providers.ForTemacursoPersonaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ForTemacursoPersonaExtendsMapper extends ForTemacursoPersonaMapper{
	
	@SelectProvider(type = ForTemacursoPersonaSqlExtendsProvider.class, method = "getTopicsSpecificPerson")
	@Results({
		@Result(column = "IDTEMACURSO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getTopicsSpecificPerson(String idInstitucion, String idCurso);
}
