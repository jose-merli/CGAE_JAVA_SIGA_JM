package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsDelitoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsDelitoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsDelitoExtendsMapper extends ScsDelitoMapper {

	@SelectProvider(type = ScsDelitoSqlExtendsProvider.class, method = "comboDelitos")
	@Results({ 
		@Result(column = "IDDELITO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboDelitos(String idLenguaje, Short idInstitucion);
}
