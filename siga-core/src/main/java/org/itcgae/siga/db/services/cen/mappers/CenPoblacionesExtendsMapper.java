package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CenPoblacionesMapper;
import org.itcgae.siga.db.services.cen.providers.CenPoblacionesSqlExtendsProvider;

public interface CenPoblacionesExtendsMapper extends CenPoblacionesMapper{

	@SelectProvider(type = CenPoblacionesSqlExtendsProvider.class, method = "getComboPoblaciones")
	@Results({
		@Result(column = "IDPOBLACION", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getComboPoblaciones (String idProvincia);
}
