package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.CosteFijoItem;
import org.itcgae.siga.db.mappers.ScsCostefijoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsCostefijoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsCostefijoExtendsMapper extends ScsCostefijoMapper{

	@SelectProvider(type = ScsCostefijoSqlExtendsProvider.class, method = "getComboCosteFijos")
	@Results({
		@Result(column = "IDCOSTEFIJO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getComboCosteFijos(String idLenguaje, Short idInstitucion);
	
	@SelectProvider(type = ScsCostefijoSqlExtendsProvider.class, method = "searchCosteFijos")
	@Results({
		@Result(column = "IDCOSTEFIJO", property = "idCosteFijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTE", property = "importe", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOASISTENCIA", property = "idTipoAsistencia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOACTUACION", property = "idTipoActuacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOASISTENCIA", property = "tipoAsistencia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOACTUACION", property = "tipoActuacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCOSTEFIJO", property = "idCosteFijoOld", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOASISTENCIA", property = "idTipoAsistenciaOld", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOACTUACION", property = "idTipoActuacionOld", jdbcType = JdbcType.VARCHAR),
	})
	List<CosteFijoItem> searchCosteFijos(boolean historico, String idLenguaje, Short idInstitucion);
	
}
