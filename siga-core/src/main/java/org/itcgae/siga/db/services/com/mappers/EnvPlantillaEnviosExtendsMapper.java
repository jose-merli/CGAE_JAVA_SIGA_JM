package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.services.com.providers.EnvPlantillaEnviosExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface EnvPlantillaEnviosExtendsMapper {
	
	@SelectProvider(type = EnvPlantillaEnviosExtendsSqlProvider.class, method = "selectPlantillas")
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
