package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;
import org.itcgae.siga.db.services.com.providers.EnvEnviosExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface EnvEnviosExtendsMapper {
	
	
	@SelectProvider(type = EnvEnviosExtendsSqlProvider.class, method = "selectEnvios")
	@Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDENVIO", property = "idEnvio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACREACION", property = "fechaCreacion", jdbcType = JdbcType.DATE),
		@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillasEnvio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDPLANTILLA", property = "idPlantilla", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHAPROGRAMADA", property = "fechaProgramada", jdbcType = JdbcType.DATE),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CUERPO", property = "cuerpo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPO", property = "idGrupo", jdbcType = JdbcType.VARCHAR)
	})
	List<EnviosMasivosItem> selectEnviosMasivosSearch(Short idInstitucion, EnviosMasivosSearch filtros);
	
	

}
