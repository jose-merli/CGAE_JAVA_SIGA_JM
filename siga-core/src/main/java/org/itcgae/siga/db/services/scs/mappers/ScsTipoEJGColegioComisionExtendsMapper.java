package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsTipoejgcolegioMapper;
import org.itcgae.siga.db.services.scs.providers.ScsTipoEJGColegioComisionSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoEJGColegioSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsTipoEJGColegioComisionExtendsMapper extends ScsTipoejgcolegioMapper{

	@SelectProvider(type = ScsTipoEJGColegioComisionSqlExtendsProvider.class, method = "comboTipoEjgColegio")
	@Results({
		@Result(column = "IDINSTITUCION", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NAME", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboColegioEjgComision(Short idLenguaje, String idinstitucion);

	
}
