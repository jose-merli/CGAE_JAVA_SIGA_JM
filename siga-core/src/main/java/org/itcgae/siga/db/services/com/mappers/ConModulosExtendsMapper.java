package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.services.com.providers.ConModulosExtendsSqlProvider;

public interface ConModulosExtendsMapper {
	
	@SelectProvider(type = ConModulosExtendsSqlProvider.class, method = "selectModulos")
	@Results({@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDMODULO", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	
	List<ComboItem> selectModulos();
}
