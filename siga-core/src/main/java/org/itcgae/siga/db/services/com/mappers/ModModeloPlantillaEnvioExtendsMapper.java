package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.PlantillaModeloItem;
import org.itcgae.siga.db.services.com.providers.ModModeloPlantillaEnviosExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ModModeloPlantillaEnvioExtendsMapper {

	@SelectProvider(type = ModModeloPlantillaEnviosExtendsSqlProvider.class, method = "selectPlantillasModelo")
	@Results({@Result(column = "NOMBRE", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PORDEFECTO", property = "porDefecto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR)
	})
	List<PlantillaModeloItem> getPlantillasModelo(String idModelo, Short idInstitucion, String idLenguaje);
	
	@SelectProvider(type = ModModeloPlantillaEnviosExtendsSqlProvider.class, method = "selectPlantillasModeloHIST")
	@Results({@Result(column = "NOMBRE", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PORDEFECTO", property = "porDefecto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR)
	})
	List<PlantillaModeloItem> getPlantillasModeloHist(String idModelo, Short idInstitucion, String idLenguaje);
}
