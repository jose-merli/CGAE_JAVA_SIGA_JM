package org.itcgae.siga.db.services.com.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.services.com.providers.EnvTipoEnvioExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface EnvTipoEnvioExtendsMapper {
	
	
	@SelectProvider(type = EnvTipoEnvioExtendsSqlProvider.class, method = "selectTipoEnvios")
	@Results({@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOENVIOS", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectTipoEnvios(String idLenguaje);
	
	@SelectProvider(type = EnvTipoEnvioExtendsSqlProvider.class, method = "selectTipoEnviosConsultas")
	@ResultType(value = List.class)
	List<Map<String, Object>> selectTipoEnviosConsultas(String idLenguaje);
}
