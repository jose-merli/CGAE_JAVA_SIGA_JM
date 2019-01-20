package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.DocumentoPlantillaItem;
import org.itcgae.siga.db.services.com.providers.ModPlantillaDocumentoExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ModPlantillaDocumentoExtendsMapper {
	
	@SelectProvider(type = ModPlantillaDocumentoExtendsSqlProvider.class, method = "selectPlantillasByInforme")
	@Results({@Result(column = "IDIOMA", property = "idIdioma", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PLANTILLA", property = "nombreDocumento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLADOCUMENTO", property = "idPlantillaDocumento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINFORME", property = "idInforme", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "idioma", jdbcType = JdbcType.VARCHAR)
	})
	List<DocumentoPlantillaItem> selectPlantillasByInforme(Long idInforme, Long idModeloComunicacion, String idLenguaje);

	
}
