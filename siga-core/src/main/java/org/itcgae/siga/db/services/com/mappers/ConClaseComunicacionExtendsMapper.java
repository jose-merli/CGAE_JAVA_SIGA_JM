package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.services.com.providers.ConClaseComunicacionesExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ConClaseComunicacionExtendsMapper {

	@SelectProvider(type = ConClaseComunicacionesExtendsSqlProvider.class, method = "selectClaseComunicaciones")
	@Results({@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCLASECOMUNICACION", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectTipoClaseComunicacion(String idLenguaje, Short idInstitucion);
}
