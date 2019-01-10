package org.itcgae.siga.db.services.com.mappers;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasSearch;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.services.com.providers.ConConsultasExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ConConsultasExtendsMapper {

	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "selectConsultas")
	@Results({ @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDCONSULTA", property = "idConsulta", jdbcType = JdbcType.NUMERIC),
			@Result(column = "DESCRIPCION", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GENERAL", property = "generica", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOCONSULTA", property = "tipoConsulta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDMODULO", property = "idModulo", jdbcType = JdbcType.NUMERIC),
			@Result(column = "BASES", property = "bases", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTABLA", property = "idTabla", jdbcType = JdbcType.NUMERIC),
			@Result(column = "ESEXPERTA", property = "experta", jdbcType = JdbcType.VARCHAR),
			// CLOB
			@Result(column = "SENTENCIA", property = "sentencia", jdbcType =JdbcType.CLOB),
			@Result(column = "OBSERVACIONES", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
			@Result(column = "IDOBJETIVO", property = "idObjetivo", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDCLASE", property = "idClase", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBREOBJETIVO", property = "objetivo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREMODULO", property = "modulo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRECLASE", property = "claseComunicacion", jdbcType = JdbcType.VARCHAR) })
	List<ConsultaItem> selectConsultasSearch(Short idInstitucion, String idLenguaje, ConsultasSearch filtros);

	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "selectMaxIdConsulta")
	@Results({
		@Result(column = "IDMAX", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO selectMaxIDConsulta();
	


	@ResultType(value = List.class)
	public List<Map<String, Object>> ejecutarConsulta(@Param(value = "select") Map<String,String> querys);
}	
