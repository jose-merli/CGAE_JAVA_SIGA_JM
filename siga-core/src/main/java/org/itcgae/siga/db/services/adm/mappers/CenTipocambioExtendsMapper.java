package org.itcgae.siga.db.services.adm.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CenTipocambioMapper;
import org.itcgae.siga.db.services.adm.providers.CenTipocambioSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenTipocambioExtendsMapper extends CenTipocambioMapper{
	
	@SelectProvider(type = CenTipocambioSqlExtendsProvider.class, method = "getActionType")
	@Results({
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCAMBIO", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getActionType(String idLenguaje);
}
