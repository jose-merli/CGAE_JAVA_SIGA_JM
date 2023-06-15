package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsJurisdiccionMapper;
import org.itcgae.siga.db.services.cen.providers.ScsJurisdiccionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsJurisdiccionExtendsMapper extends ScsJurisdiccionMapper{
	
	@SelectProvider(type = ScsJurisdiccionSqlExtendsProvider.class, method = "getJurisdicciones")
	@Results({
		@Result(column = "IDJURISDICCION", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getJurisdicciones(String idInstitucion, String nif);
	
	@SelectProvider(type = ScsJurisdiccionSqlExtendsProvider.class, method = "getComboJurisdiccion")
	@Results({
		@Result(column = "IDJURISDICCION", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getComboJurisdiccion(String idLenguaje);
	
}
