package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.PlantillaDocumentoDTO;
import org.itcgae.siga.db.services.com.providers.ModModeloPlantillaDocumentoExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ModModeloPlantillaDocumentoExtendsMapper {
	
	@SelectProvider(type = ModModeloPlantillaDocumentoExtendsSqlProvider.class, method = "selectInformes")
	@Results({@Result(column = "IDIOMA", property = "idioma", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFICHEROSALIDA", property = "ficheroSalida", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SUFIJO", property = "sufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FORMATOSALIDA", property = "formatoSalida", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAASOCIACION", property = "fechaAsociacion", jdbcType = JdbcType.DATE)
	})
	List<PlantillaDocumentoDTO> selectInformes(Short idInstitucion, Long idModeloComunicacion);

	
}
