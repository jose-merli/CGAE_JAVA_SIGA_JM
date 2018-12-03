package org.itcgae.siga.db.services.com.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.services.com.providers.EnvHistoricoExtendsSqlProviders;

public interface EnvHistoricoEstadoExtendsMapper {
	
	@SelectProvider(type = EnvHistoricoExtendsSqlProviders.class, method = "selectMaxIDHistorico")
	@Results({
		@Result(column = "IDMAX", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO selectMaxIDHistorico();

}
