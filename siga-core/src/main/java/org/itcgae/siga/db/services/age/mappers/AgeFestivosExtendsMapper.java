package org.itcgae.siga.db.services.age.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.age.FestivosItem;
import org.itcgae.siga.db.mappers.AgeFestivosMapper;
import org.itcgae.siga.db.services.age.providers.AgeFestivosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface AgeFestivosExtendsMapper extends AgeFestivosMapper{
	
	@SelectProvider(type = AgeFestivosSqlExtendsProvider.class, method = "getFestivos")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DENOMINACION", property = "denominacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOFESTIVO", property = "tipoFestivo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "LUGAR", property = "lugar", jdbcType = JdbcType.VARCHAR)
	})
	List<FestivosItem> getFestivos(Short idInstitucion);
	
	@SelectProvider(type = AgeFestivosSqlExtendsProvider.class, method = "getFechaFestivos")
	@Results({
		@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.VARCHAR)
	})
	List<String> getFechaFestivos(Short idInstitucion);
	
}
