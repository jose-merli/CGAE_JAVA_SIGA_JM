package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.SufijoItem;
import org.itcgae.siga.db.services.com.providers.ModPlantillaDocSufijoExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ModPlantillaDocSufijoExtendsMapper {
	
	@SelectProvider(type = ModPlantillaDocSufijoExtendsSqlProvider.class, method = "selectSufijos")
	@Results({@Result(column = "IDSUFIJO", property = "idSufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "nombreSufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.VARCHAR)
	})
	List<SufijoItem> selectSufijos(String idLenguaje);
	
}
