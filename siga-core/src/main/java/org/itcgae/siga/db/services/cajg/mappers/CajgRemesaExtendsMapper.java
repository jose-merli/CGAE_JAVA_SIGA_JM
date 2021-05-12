package org.itcgae.siga.db.services.cajg.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CajgRemesaMapper;
import org.itcgae.siga.db.services.cajg.providers.CajgRemesaSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsJuzgadoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CajgRemesaExtendsMapper extends CajgRemesaMapper{

	@SelectProvider(type = CajgRemesaSqlExtendsProvider.class, method = "comboRemesa")
	@Results({ 
		@Result(column = "IDREMESA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboRemesa(Short idInstitucion);
}
