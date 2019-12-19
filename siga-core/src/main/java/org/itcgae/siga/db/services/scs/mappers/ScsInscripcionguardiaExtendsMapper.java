package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsInscripcionguardiaSqlExtendsProvider;

public interface ScsInscripcionguardiaExtendsMapper extends ScsInscripcionguardiaMapper{


	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "getColaGuardias")
	@Results({
		@Result(column = "NUMEROGRUPO", property="numeroGrupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPOGUARDIACOLEGIADO", property = "idGrupoGuardiaColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAVALIDACION", property = "fechaValidacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COMPENSACIONES", property = "compensaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SALTOS", property = "saltos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "orden_cola", property = "ordenCola", jdbcType = JdbcType.VARCHAR),
	})
	List<InscripcionGuardiaItem> getColaGuardias(String idGuardia, String idTurno, String fecha,String ultimo,String ordenaciones, String idInstitucion);

	
}
