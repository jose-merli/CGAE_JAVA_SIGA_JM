package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsTipodesignacolegioMapper;
import org.itcgae.siga.db.services.scs.providers.ScsTipoSOJSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsTipoSOJExtendsMapper extends ScsTipodesignacolegioMapper{

	@SelectProvider(type = ScsTipoSOJSqlExtendsProvider.class, method = "comboTipoSOJ")
	@Results({
		@Result(column = "IDTIPOSOJ", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboTipoSOJ(Short idLenguaje);
	
	@SelectProvider(type = ScsTipoSOJSqlExtendsProvider.class, method = "comboTipoSOJColegio")
	@Results({
		@Result(column = "IDTIPOSOJCOLEGIO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboTipoSOJColegio(Short idLenguaje,Short idInstitucion);
	
	@SelectProvider(type = ScsTipoSOJSqlExtendsProvider.class, method = "comboTipoConsulta")
	@Results({
		@Result(column = "IDTIPOCONSULTA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboTipoConsulta(Short idLenguaje,Short idInstitucion);
	
	@SelectProvider(type = ScsTipoSOJSqlExtendsProvider.class, method = "comboTipoRespuesta")
	@Results({
		@Result(column = "IDTIPORESPUESTA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboTipoRespuesta(Short idLenguaje,Short idInstitucion);
	
}
