package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.CargaMasivaDatosITItem;
import org.itcgae.siga.DTOs.scs.ModulosItem;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsInscripcionguardiaSqlExtendsProvider;

public interface ScsInscripcionguardiaExtendsMapper extends ScsInscripcionguardiaMapper{
	
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "searchGrupo")
	@Results({
		@Result(column = "NUMEROGRUPO", property = "grupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR)
	})
	List<CargaMasivaDatosITItem> searchGrupo(String grupo, Short idInstitucion);
	
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "searchGrupo")
	@Results({
		@Result(column = "NUMEROGRUPO", property = "grupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR)
	})
	List<CargaMasivaDatosITItem> searchGrupoGuardia(Short idInstitucion, String idGuardia);

}
