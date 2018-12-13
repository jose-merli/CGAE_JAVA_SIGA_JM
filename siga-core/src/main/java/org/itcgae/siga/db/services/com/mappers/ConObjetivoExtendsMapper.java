package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.services.com.providers.ConObjetivosExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ConObjetivoExtendsMapper {

	@SelectProvider(type = ConObjetivosExtendsSqlProvider.class, method = "selectObjetivos")
	@Results({@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDOBJETIVO", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectObjetivos(String idLenguaje);
}
