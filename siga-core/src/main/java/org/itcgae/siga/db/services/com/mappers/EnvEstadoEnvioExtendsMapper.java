package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.services.com.providers.EnvEstadoEnvioExtendsSqlProvider;

public interface EnvEstadoEnvioExtendsMapper {
	
	@SelectProvider(type = EnvEstadoEnvioExtendsSqlProvider.class, method = "selectEstadoEnvios")
	@Results({@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDESTADO", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectEstadoEnvios(String idLenguaje);

}
