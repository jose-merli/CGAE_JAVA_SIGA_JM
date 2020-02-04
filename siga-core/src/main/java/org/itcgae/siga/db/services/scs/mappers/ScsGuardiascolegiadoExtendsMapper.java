package org.itcgae.siga.db.services.scs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.db.mappers.ScsGuardiascolegiadoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsGuardiascolegiadoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsGuardiascolegiadoExtendsMapper extends ScsGuardiascolegiadoMapper {

	

	
	@SelectProvider(type = ScsGuardiascolegiadoSqlExtendsProvider.class, method = "getTurnosGuardias")
	@Results({ 
		@Result(column = "COUNT", property = "valor", jdbcType = JdbcType.VARCHAR),
	})
	StringDTO getTurnosGuardias(String idPersona);
	
	
	
}
