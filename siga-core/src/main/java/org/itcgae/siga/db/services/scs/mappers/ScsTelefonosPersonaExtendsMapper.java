package org.itcgae.siga.db.services.scs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.JusticiableItem;
import org.itcgae.siga.db.mappers.ScsTelefonospersonaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsTelefonosPersonaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsTelefonosPersonaExtendsMapper extends ScsTelefonospersonaMapper{

	@SelectProvider(type = ScsTelefonosPersonaSqlExtendsProvider.class, method = "getIdTelefono")
	@Results({ @Result(column = "IDTELEFONO", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdTelefono(JusticiableItem justiciableItem, Short idInstitucion);
	
}
