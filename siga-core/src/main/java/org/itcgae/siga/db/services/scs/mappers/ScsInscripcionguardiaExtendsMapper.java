package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsInscripcionguardiaSqlExtendsProvider;

public interface ScsInscripcionguardiaExtendsMapper extends ScsInscripcionguardiaMapper{


	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "getColaGuardias")
	@Results({
		@Result(column = "NUMEROGRUPO", property="value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPOGUARDIACOLEGIADO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDEN", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROCOLEGIADO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAVALIDACION", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COMPENSACIONES", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SALTOS", property = "value", jdbcType = JdbcType.VARCHAR),
	})
	List<InscripcionItem> getColaGuardias(String idGuardia, String idTurno, String fecha,String ultimo,String ordenaciones, String idInstitucion);

	
}
