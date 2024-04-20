package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.PlantillaModeloDocumentoDTO;
import org.itcgae.siga.db.services.com.providers.ModModeloPlantillaDocumentoExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ModModeloPlantillaDocumentoExtendsMapper {
	
	@SelectProvider(type = ModModeloPlantillaDocumentoExtendsSqlProvider.class, method = "selectInformes")
	@Results({@Result(column = "IDIOMA", property = "idioma", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFICHEROSALIDA", property = "nombreFicheroSalida", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFORMATO", property = "formatoSalida", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FORMATOSALIDA", property = "idFormatoSalida", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAASOCIACION", property = "fechaAsociacion", jdbcType = JdbcType.DATE),
		@Result(column = "IDINFORME", property = "idInforme", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLAS", property = "idPlantillas")
	})
	List<PlantillaModeloDocumentoDTO> selectInformes(Short idInstitucion, Long idModeloComunicacion, String idLenguaje);

	@SelectProvider(type = ModModeloPlantillaDocumentoExtendsSqlProvider.class, method = "selectMaxInforme")
	Long selectMaxInforme(Short idInstitucion, Long idModeloComunicacion);
	
	@SelectProvider(type = ModModeloPlantillaDocumentoExtendsSqlProvider.class, method = "selectInformesGenerar")
	@Results({
		@Result(column = "IDINFORME", property = "idInforme", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLAS", property = "idPlantillas", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFICHEROSALIDA", property = "nombreFicheroSalida", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FORMATOSALIDA", property = "formatoSalida", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idPlantillaDocumento", property = "idPlantillaDocumento", jdbcType = JdbcType.VARCHAR),
	})
	List<PlantillaModeloDocumentoDTO> selectInformesGenerar(Long idModeloComunicacion, String idLenguaje);
	
	@SelectProvider(type = ModModeloPlantillaDocumentoExtendsSqlProvider.class, method = "selectInformesGenerarConDest")
	@Results({
		@Result(column = "IDINFORME", property = "idInforme", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLAS", property = "idPlantillas", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFICHEROSALIDA", property = "nombreFicheroSalida", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FORMATOSALIDA", property = "formatoSalida", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idPlantillaDocumento", property = "idPlantillaDocumento", jdbcType = JdbcType.VARCHAR),
	})
	List<PlantillaModeloDocumentoDTO> selectInformesGenerarConDest(Long idModeloComunicacion, String idLenguaje);
	
	@SelectProvider(type = ModModeloPlantillaDocumentoExtendsSqlProvider.class, method = "selectPlantillaGenerar")
	@Results({
		@Result(column = "IDINFORME", property = "idInforme", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLAS", property = "idPlantillas", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFICHEROSALIDA", property = "nombreFicheroSalida", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FORMATOSALIDA", property = "formatoSalida", jdbcType = JdbcType.VARCHAR)	
	})
	List<PlantillaModeloDocumentoDTO> selectPlantillaGenerar(Long idModeloComunicacion, Long idPlantillaDocumento);
	
}
