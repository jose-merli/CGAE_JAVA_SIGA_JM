package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsTiposguardiasMapper;
import org.itcgae.siga.db.services.scs.providers.ScsTiposGuardiasSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsTiposGuardiasExtendsMapper extends ScsTiposguardiasMapper{

	@SelectProvider(type = ScsTiposGuardiasSqlExtendsProvider.class, method = "comboTiposGuardia")
	@Results({
		@Result(column = "IDTIPOGUARDIA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboTiposGuardia(String idLenguaje);
	
}
