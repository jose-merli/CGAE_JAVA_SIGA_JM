package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CenEstadocolegialMapper;
import org.itcgae.siga.db.services.cen.providers.CenEstadocolegialSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenEstadocolegialExtendsMapper extends CenEstadocolegialMapper{

	
	@SelectProvider(type = CenEstadocolegialSqlExtendsProvider.class, method = "distinctSituacionColegial")
	@Results({
		@Result(column = "IDESTADO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> distinctSituacionColegial(String idLenguaje);
	
	@SelectProvider(type = CenEstadocolegialSqlExtendsProvider.class, method = "getSituacionGlobalColegiado")
	@Results({
		@Result(column = "IDESTADO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getSituacionGlobalColegiado(String idPersona);
}
