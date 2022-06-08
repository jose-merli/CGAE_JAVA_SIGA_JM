package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.services.scs.providers.ScsImpugnacionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.itcgae.siga.db.mappers.ScsTiporesolautoMapper;


@Service
@Primary
public interface ScsImpugnacionExtendsMapper extends ScsTiporesolautoMapper {

	@SelectProvider(type = ScsImpugnacionSqlExtendsProvider.class, method = "comboImpugnacion")
	@Results({ 
		@Result(column = "IDIMPUGNACION", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboImpugnacion(String idLenguaje, String idInstitucion);
	}



