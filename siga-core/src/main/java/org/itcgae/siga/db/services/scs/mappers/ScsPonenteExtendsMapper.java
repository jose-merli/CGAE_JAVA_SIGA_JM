package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsPonenteMapper;
import org.itcgae.siga.db.services.scs.providers.ScsPonenteSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
@Service
@Primary
public interface ScsPonenteExtendsMapper extends ScsPonenteMapper{
	@SelectProvider(type = ScsPonenteSqlExtendsProvider.class, method = "comboPonente")
	@Results({ 
		@Result(column = "IDPONENTE", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboPonente(String idLenguaje, String idInstitucion);
	
}
