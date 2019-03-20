package org.itcgae.siga.db.services.age.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.age.NotificacionEventoItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosMapper;
import org.itcgae.siga.db.services.age.providers.AgeUnidadMedidaSqlExtendsProvider;
import org.itcgae.siga.db.services.age.providers.EnvPlantillasenviosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface EnvPlantillasenviosExtendsMapper extends EnvPlantillasenviosMapper {
		
		
	@SelectProvider(type = EnvPlantillasenviosSqlExtendsProvider.class, method = "getTemplates")
	@Results({
		@Result(column = "IDPLANTILLAENVIOS", property = "idPlantilla", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBRE", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR)
	})
	List<NotificacionEventoItem> getTemplates(String idInstitucion);
	
	@SelectProvider(type = EnvPlantillasenviosSqlExtendsProvider.class, method = "getPlantillas")
	@Results({
		@Result(column = "IDPLANTILLAENVIOS", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOENVIOS", property = "subValue", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getPlantillas(String idInstitucion);
	
}
