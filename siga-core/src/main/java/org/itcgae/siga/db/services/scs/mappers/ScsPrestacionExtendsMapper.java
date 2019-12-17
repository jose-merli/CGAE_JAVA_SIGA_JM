package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsPrestacionMapper;
import org.itcgae.siga.db.services.scs.providers.ScsPrestacionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
@Service
@Primary

public interface ScsPrestacionExtendsMapper extends ScsPrestacionMapper {
	
	@SelectProvider(type = ScsPrestacionSqlExtendsProvider.class, method = "comboPrestaciones")
	@Results({ @Result(column = "IDPRESTACION", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
			 })
	List<ComboItem> comboPrestaciones(String idLenguaje, String idInstitucion);
}
