package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.PlantillaDocumentoDto;
import org.itcgae.siga.DTOs.com.PlantillaModeloItem;
import org.itcgae.siga.db.services.com.providers.ModModeloPlantillaDocumentoExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ModModeloPlantillaDocumentoExtendsMapper {
	
	@SelectProvider(type = ModModeloPlantillaDocumentoExtendsSqlProvider.class, method = "selectInformes")
	@Results({@Result(column = "IDIOMA", property = "idioma", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAASOCIACION", property = "fechaAsociacion", jdbcType = JdbcType.DATE)
	})
	List<PlantillaDocumentoDto> selectInformes(Short idInstitucion, Long idModeloComunicacion);
	
	@SelectProvider(type = ModModeloPlantillaDocumentoExtendsSqlProvider.class, method = "selectPlantillasDocumento")
	@Results({@Result(column = "NOMBRE", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR)
	})
	List<PlantillaModeloItem> getPlantillasDocumento(String idModelo, String idLenguaje);
	
	@SelectProvider(type = ModModeloPlantillaDocumentoExtendsSqlProvider.class, method = "selectPlantillasDocumentoHIST")
	@Results({@Result(column = "NOMBRE", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR)
	})
	List<PlantillaModeloItem> getPlantillasDocumentoHist(String idModelo, String idLenguaje);
	
}
