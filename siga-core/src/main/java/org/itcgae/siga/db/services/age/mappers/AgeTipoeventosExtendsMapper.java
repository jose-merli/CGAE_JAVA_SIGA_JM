package org.itcgae.siga.db.services.age.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.AgeTipoeventosMapper;
import org.itcgae.siga.db.services.age.providers.AgeTipoeventoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface AgeTipoeventosExtendsMapper extends AgeTipoeventosMapper{
		
		
	@SelectProvider(type = AgeTipoeventoSqlExtendsProvider.class, method = "getTypeEvent")
	@Results({
		@Result(column = "IDTIPOEVENTO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getTypeEvent(String idLenguaje);
	
}
