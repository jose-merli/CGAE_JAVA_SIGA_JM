package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsTipoejgcolegioMapper;
import org.itcgae.siga.db.services.scs.providers.ScsTipoejgSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoejgcolegioSqlExtendsProvider;

public interface ScsTipoejgcolegioExtendsMapper extends ScsTipoejgcolegioMapper {
	
	@SelectProvider(type = ScsTipoejgcolegioSqlExtendsProvider.class, method = "comboTipoColegioEjg")
	@Results({ 
		@Result(column = "IDTIPOEJG", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboTipoColegioEjg(String idlenguaje, String idInstitucion);

}
