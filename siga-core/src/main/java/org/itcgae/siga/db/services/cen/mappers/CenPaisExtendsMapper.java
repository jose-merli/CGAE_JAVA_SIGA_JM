package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CenPaisMapper;
import org.itcgae.siga.db.services.cen.providers.CenPaisSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenPaisExtendsMapper extends CenPaisMapper{
	
	@SelectProvider(type = CenPaisSqlExtendsProvider.class, method = "selectPais")
	@Results({
		@Result(column = "IDPAIS", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectPais(String idLenguaje);
	

	
	
}
