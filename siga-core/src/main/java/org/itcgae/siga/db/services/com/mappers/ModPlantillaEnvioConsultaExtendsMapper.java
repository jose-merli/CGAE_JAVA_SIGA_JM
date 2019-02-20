package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.db.services.com.providers.ModPlantillaEnvioConsultaExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ModPlantillaEnvioConsultaExtendsMapper {
	
	@SelectProvider(type = ModPlantillaEnvioConsultaExtendsSqlProvider.class, method = "selectPlantillaEnvioConsultas")
	@Results({@Result(column = "IDCONSULTA", property = "idConsulta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SENTENCIA", property = "sentencia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDOBJETIVO", property = "idObjetivo", jdbcType = JdbcType.VARCHAR)
	})
	List<ConsultaItem> selectPlantillaEnvioConsultas(Short idInstitucion, Integer idPlantillaEnvios, Short idTipoEnvios);
	
}
