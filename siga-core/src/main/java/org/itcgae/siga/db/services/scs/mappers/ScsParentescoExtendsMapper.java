package org.itcgae.siga.db.services.scs.mappers;

import org.itcgae.siga.db.mappers.ScsParentescoMapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.services.scs.providers.ScsParentescoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsParentescoExtendsMapper extends ScsParentescoMapper{
	
	@SelectProvider(type = ScsParentescoSqlExtendsProvider.class, method = "getParentesco")
	@Results({
		@Result(column = "IDGRUPO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getParentesco(Short idInstitucion, String idLenguaje);

}
