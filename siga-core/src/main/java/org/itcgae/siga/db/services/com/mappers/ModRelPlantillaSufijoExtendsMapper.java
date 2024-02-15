package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.SufijoItem;
import org.itcgae.siga.db.services.com.providers.ModRelPlantillaSufijoExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ModRelPlantillaSufijoExtendsMapper {
	
	@SelectProvider(type = ModRelPlantillaSufijoExtendsSqlProvider.class, method = "selectSufijosPlantilla")
	@Results({@Result(column = "IDSUFIJO", property = "idSufijo", jdbcType = JdbcType.NUMERIC),
		@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBRESUFIJO", property = "nombreSufijo", jdbcType = JdbcType.VARCHAR)
	})
	List<SufijoItem> selectSufijosPlantilla(Long idModeloComunicacion, Long idInforme, Long idPlantillaDocumento, String idLenguaje);
	
}
