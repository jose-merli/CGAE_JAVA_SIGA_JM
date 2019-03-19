package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.db.services.com.providers.EnvDestConsultaEnvioExtendsSqlProviders;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public interface EnvDestConsultaEnvioExtendsMapper {
	
	@SelectProvider(type = EnvDestConsultaEnvioExtendsSqlProviders.class, method = "consultasDestAsociadasEnvio")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCONSULTA", property = "idConsulta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOCONSULTA", property = "tipoConsulta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONES", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDMODULO", property = "idModulo", jdbcType = JdbcType.VARCHAR),
		//@Result(column = "MODULO", property = "modulo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDOBJETIVO", property = "idObjetivo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SENTENCIA", property = "sentencia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GENERAL", property = "generica", jdbcType = JdbcType.VARCHAR),
		//@Result(column = "OBJETIVO", property = "objetivo", jdbcType = JdbcType.VARCHAR)
	})
	List<ConsultaItem> selectConsultasDestEnvio(Short idInstitucion, String idEnvio);
}
