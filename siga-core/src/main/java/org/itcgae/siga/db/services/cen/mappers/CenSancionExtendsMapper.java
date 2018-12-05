package org.itcgae.siga.db.services.cen.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.CenSancionMapper;
import org.itcgae.siga.db.services.cen.providers.CenSancionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenSancionExtendsMapper extends CenSancionMapper{

	@SelectProvider(type = CenSancionSqlExtendsProvider.class, method = "getMaxIdSancion")
	@Results({ @Result(column = "IDSANCION", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getMaxIdSancion(String idTipoSancion, String idPersona);
}
