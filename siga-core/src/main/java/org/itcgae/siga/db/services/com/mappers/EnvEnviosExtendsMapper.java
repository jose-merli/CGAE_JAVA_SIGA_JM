package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
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
		@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBREPLANTILLA", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLA", property = "idPlantilla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAPROGRAMADA", property = "fechaProgramada", jdbcType = JdbcType.DATE),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CUERPO", property = "cuerpo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADOENVIO", property = "estadoEnvio", jdbcType = JdbcType.VARCHAR)
	})
	List<EnviosMasivosItem> selectEnviosMasivosSearch(Short idInstitucion, String idLenguaje, EnviosMasivosSearch filtros);
	
	@SelectProvider(type = EnvEnviosExtendsSqlProvider.class, method = "selectMaxIDEnvio")
	@Results({
		@Result(column = "IDMAX", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO selectMaxIDEnvio();
	
	@SelectProvider(type = EnvEnviosExtendsSqlProvider.class, method = "selectEnviosComunicacion")
	@Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDENVIO", property = "idEnvio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACREACION", property = "fechaCreacion", jdbcType = JdbcType.DATE),
		@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDMODELOCOMUNICACION", property = "idModeloComunicacion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRECLASE", property = "idClaseComunicacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREMODELO", property = "idClaseComunicacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPLANTILLA", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLA", property = "idPlantilla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAPROGRAMADA", property = "fechaProgramada", jdbcType = JdbcType.DATE),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CUERPO", property = "cuerpo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPO", property = "idGrupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADOENVIO", property = "estadoEnvio", jdbcType = JdbcType.VARCHAR)
	})
	List<EnviosMasivosItem> selectEnviosComunicacionSearch(Short idInstitucion, String idLenguaje, EnviosMasivosSearch filtros);

}
