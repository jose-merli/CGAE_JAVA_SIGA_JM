package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ForCalificacionesMapper;
import org.itcgae.siga.db.services.form.providers.ForCalificacionesSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ForCalificacionesExtendsMapper extends ForCalificacionesMapper{

	@SelectProvider(type = ForCalificacionesSqlExtendsProvider.class, method = "getQualificationsCourse")
	@Results({
		@Result(column = "IDCALIFICACION", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getQualificationsCourse(String idLenguaje);
}
