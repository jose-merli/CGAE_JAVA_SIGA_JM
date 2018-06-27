package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.GenFicheroMapper;
import org.itcgae.siga.db.services.cen.providers.GenFicheroSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface GenFicheroExtendsMapper extends GenFicheroMapper{
	
	@SelectProvider(type = GenFicheroSqlExtendsProvider.class, method = "selectMaxIdFichero")
	@Results({
		@Result(column = "IDFICHERO1", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDFICHERO2", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectMaxIdFichero();
}
