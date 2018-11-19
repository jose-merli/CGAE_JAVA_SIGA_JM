package org.itcgae.siga.db.services.age.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.AgeDiassemanaMapper;
import org.itcgae.siga.db.services.age.providers.AgeDiassemanaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface AgeDiassemanaExtendsMapper extends AgeDiassemanaMapper{
		
	
	@SelectProvider(type = AgeDiassemanaSqlExtendsProvider.class, method = "getDaysWeek")
	@Results({
		@Result(column = "IDDIASSEMANA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getDaysWeek(String idLenguaje);
	
}
