package org.itcgae.siga.db.services.adm.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.adm.ParametroItem;
import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.services.adm.providers.GenParametrosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface GenParametrosExtendsMapper extends GenParametrosMapper{

	
	
	@SelectProvider(type = GenParametrosSqlExtendsProvider.class, method = "getModules")
	@Results({
		@Result(column = "MODULO", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MODULO", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getModules();
	
	@SelectProvider(type = GenParametrosSqlExtendsProvider.class, method = "getParametersSearch")
	@Results({
		@Result(column = "MODULO", property = "modulo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PARAMETRO", property = "parametro", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDRECURSO", property = "idRecurso", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALOR", property = "valor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
	})
	List<ParametroItem> getParametersSearch(int numPagina, ParametroRequestDTO parametroRequestDTO);
	
	
	
	@SelectProvider(type = GenParametrosSqlExtendsProvider.class, method = "getParametersRecord")
	@Results({
		@Result(column = "MODULO", property = "modulo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PARAMETRO", property = "parametro", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALOR", property = "valor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
	})
	List<ParametroItem> getParametersRecord(int numPagina, ParametroRequestDTO parametroRequestDTO);
}
