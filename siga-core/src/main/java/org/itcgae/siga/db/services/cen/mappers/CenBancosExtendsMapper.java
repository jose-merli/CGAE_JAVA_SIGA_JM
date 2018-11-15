package org.itcgae.siga.db.services.cen.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.CenBancosMapper;
import org.itcgae.siga.db.services.cen.providers.CenBancosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenBancosExtendsMapper extends CenBancosMapper {

	@SelectProvider(type = CenBancosSqlExtendsProvider.class, method = "getMaxCode")
	@Results({
		@Result(column = "CODIGO", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO getMaxCode();
}
