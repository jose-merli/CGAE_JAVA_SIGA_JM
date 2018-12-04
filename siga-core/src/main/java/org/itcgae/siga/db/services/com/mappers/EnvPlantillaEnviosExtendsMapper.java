package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.services.com.providers.EnvPlantillaEnviosExtendsSqlProvider;

public interface EnvPlantillaEnviosExtendsMapper {
	
	@SelectProvider(type = EnvPlantillaEnviosExtendsSqlProvider.class, method = "selectPlantillas")
	@Results({@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLAENVIOS", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectPlantillas(Short idInstitucion, String idTipoEnvio);

}
