package org.itcgae.siga.db.services.scs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosEjgItem;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsEjgSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsEjgExtendsMapper extends ScsEjgMapper{

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getAsuntoTipoEjg")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),	
		@Result(column = "TURNOGUARDIA", property = "turnoGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONALETRADO", property = "idPersonaLetrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONAJG", property = "idPersonajg", jdbcType = JdbcType.VARCHAR),
		@Result(column = "EXPEDIENTEINSOSTENIBILIDAD", property = "expedienteInsostenibilidad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DICTAMEN", property = "dictamen", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FUNDAMENTOCALIFICACION", property = "fundamentoCalificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPORESOLUCION", property = "tipoResolucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOFUNDAMENTO", property = "tipoFundamento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPORESOLUCIONAUTO", property = "tipoResolucionAuto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOSENTIDOAUTO", property = "tipoSentidoAuto", jdbcType = JdbcType.VARCHAR),
	})
	AsuntosEjgItem getAsuntoTipoEjg(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje);
}
