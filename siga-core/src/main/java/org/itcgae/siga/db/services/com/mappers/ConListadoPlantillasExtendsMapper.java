package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.db.services.com.providers.ConListadoPlantillasExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ConListadoPlantillasExtendsMapper {

	@SelectProvider(type = ConListadoPlantillasExtendsSqlProvider.class, method = "selectListadoPlantillas")
	@Results({ @Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ACUSERECIBO", property = "acuseRecibo", jdbcType = JdbcType.VARCHAR)
		})
	List<PlantillaEnvioItem> selectListadoPlantillas(Short idInstitucion,String idLenguaje,ConsultaItem consulta);
}
