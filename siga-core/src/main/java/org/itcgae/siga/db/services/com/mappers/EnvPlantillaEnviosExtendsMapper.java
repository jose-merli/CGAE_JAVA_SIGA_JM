package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.DTOs.com.PlantillaEnvioSearchItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.services.com.providers.EnvPlantillaEnviosExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface EnvPlantillaEnviosExtendsMapper {
	
	@SelectProvider(type = EnvPlantillaEnviosExtendsSqlProvider.class, method = "selectPlantillas")
	@Results({@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ACUSERECIBO", property = "acuseRecibo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CUERPO", property = "cuerpo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDDIRECCION", property = "idDireccion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR)
		})
	List<PlantillaEnvioItem> selectPlantillasEnvios(Short idInstitucion, PlantillaEnvioSearchItem filtros);
	
	@SelectProvider(type = EnvPlantillaEnviosExtendsSqlProvider.class, method = "getPlantillas")
	@Results({@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLAENVIOS", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getPlantillas(Short idInstitucion, String idTipoEnvio);
	
	@SelectProvider(type = EnvPlantillaEnviosExtendsSqlProvider.class, method = "selectMaxIDPlantillaEnvio")
	@Results({
		@Result(column = "IDMAX", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO selectMaxIDPlantillas();

}
