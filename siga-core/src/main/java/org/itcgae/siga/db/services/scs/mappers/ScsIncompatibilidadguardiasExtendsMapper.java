package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.db.mappers.ScsIncompatibilidadguardiasMapper;
import org.itcgae.siga.db.services.scs.providers.ScsIncompatibilidadguardiasSqlExtendsProvider;

public interface ScsIncompatibilidadguardiasExtendsMapper extends ScsIncompatibilidadguardiasMapper{
	

	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "tarjetaIncompatibilidades")
	@Results({ 
		@Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GUARDIA", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SELECCIONLABORABLES", property = "seleccionLaborables", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SELECCIONFESTIVOS", property = "seleccionFestivos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOTIVOS", property = "descripcion", jdbcType = JdbcType.VARCHAR)
	})
	List<GuardiasItem> tarjetaIncompatibilidades(String idGuardia, String idInstitucion);
	
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "resumenIncompatibilidades")
	@Results({ 
		@Result(column = "TOTAL_INCOMPATIBILIDADES", property = "incompatibilidades", jdbcType = JdbcType.VARCHAR),
	})
	List<GuardiasItem> resumenIncompatibilidades(GuardiasItem guardia, String idInstitucion);
	

}
