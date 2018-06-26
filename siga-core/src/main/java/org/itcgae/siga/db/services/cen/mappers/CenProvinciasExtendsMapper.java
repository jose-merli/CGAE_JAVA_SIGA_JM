package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CenProvinciasMapper;
import org.itcgae.siga.db.services.cen.providers.CenProvinciasSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenProvinciasExtendsMapper extends CenProvinciasMapper{
	
	@SelectProvider(type = CenProvinciasSqlExtendsProvider.class, method = "selectDistinctProvinces")
	@Results({
		@Result(column = "IDPROVINCIA", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "value", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> selectDistinctProvinces();
}
