package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.ClaseComunicacionItem;
import org.itcgae.siga.db.services.com.providers.ModClasecomunicacionesExtendsSqlProvider;

public interface ModClasecomunicacionesExtendsMapper {
	
	
	@SelectProvider(type = ModClasecomunicacionesExtendsSqlProvider.class, method = "selectClaseComunicacionModulo")
	@Results({@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "RUTAPLANTILLA", property = "rutaPlantilla", jdbcType = JdbcType.VARCHAR)
	})
	List<ClaseComunicacionItem> selectClaseComunicacionModulo(String idModeloComunicacion);
    
}