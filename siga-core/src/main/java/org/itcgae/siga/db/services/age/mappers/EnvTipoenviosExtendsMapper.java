package org.itcgae.siga.db.services.age.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.EnvTipoenviosMapper;
import org.itcgae.siga.db.services.age.providers.EnvTipoenviosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface EnvTipoenviosExtendsMapper extends EnvTipoenviosMapper {
		
		
	@SelectProvider(type = EnvTipoenviosSqlExtendsProvider.class, method = "getTypeSend")
	@Results({
		@Result(column = "IDTIPOENVIOS", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getTypeSend(String idPlantillaEnvio, String idTipoEnvio, String idInstitucion, String idLenguaje);
	
}
