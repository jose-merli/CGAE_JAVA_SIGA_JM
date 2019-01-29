package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.KeyItem;
import org.itcgae.siga.db.services.com.providers.ModKeyclasecomunicacionExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ModKeyclasecomunicacionExtendsMapper {
	
	@SelectProvider(type = ModKeyclasecomunicacionExtendsSqlProvider.class, method = "selectKeyClase")
	@Results({@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDKEY", property = "idKey", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TABLA", property = "tabla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR)
	})
	List<KeyItem> selectKeyClase(Long idClaseComunicacion);
	
}
