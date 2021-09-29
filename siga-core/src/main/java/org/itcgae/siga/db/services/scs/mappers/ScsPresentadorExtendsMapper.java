package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsPresentadorMapper;
import org.itcgae.siga.db.services.scs.providers.ScsPresentadorSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsPresentadorExtendsMapper extends ScsPresentadorMapper{
	
	@SelectProvider(type = ScsPresentadorSqlExtendsProvider.class, method = "comboPresentadores")
	@Results({ 
		@Result(column = "IDPRESENTADOR", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboPresentadores(String idLenguaje, Short idInstitucion);

}
