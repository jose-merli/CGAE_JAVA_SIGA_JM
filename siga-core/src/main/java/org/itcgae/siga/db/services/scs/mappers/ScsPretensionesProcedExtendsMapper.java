package org.itcgae.siga.db.services.scs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsPretensionesprocedMapper;
import org.itcgae.siga.db.services.scs.providers.ScsPretensionesProcedSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsPretensionesProcedExtendsMapper extends ScsPretensionesprocedMapper{

	@SelectProvider(type = ScsPretensionesProcedSqlExtendsProvider.class, method = "getIdPretension")
	@Results({ @Result(column = "IDPRETENSION", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdPretension(Short idInstitucion);
	
}
