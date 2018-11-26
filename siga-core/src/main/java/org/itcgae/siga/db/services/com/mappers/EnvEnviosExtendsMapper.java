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
	
	
	@SelectProvider(type = EnvEnviosExtendsSqlProvider.class, method = "selectTipoEnvios")
	@Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDENVIO", property = "idEnvio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.DATE),
		@Result(column = "GENERARDOCUMENTO", property = "generarDocumento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPRIMIRETIQUETAS", property = "imprimirEtiquetas", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillasEnvios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOENVIOS", property = "idEstado", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHAMODIFICACION", property = "value", jdbcType = JdbcType.DATE),
		@Result(column = "USUMODIFICACION", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDPLANTILLA", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDIMPRESORA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAPROGRAMADA", property = "value", jdbcType = JdbcType.DATE),
		@Result(column = "CONSULTA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ACUSERECIBO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOINTERCAMBIOTELEMATICO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COMISIONAJG", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHABAJA", property = "value", jdbcType = JdbcType.DATE),
		@Result(column = "CSV", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDSOLICITUDECOS", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<EnviosMasivosItem> selectEnviosMasivosSearch(Short idInstitucion, EnviosMasivosSearch filtros);
	
	

}
