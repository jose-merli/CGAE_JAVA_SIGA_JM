package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.db.mappers.ScsHitofacturableguardiaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsHitofacturableguardiaSqlExtendsProvider;

public interface ScsHitofacturableguardiaExtendsMapper extends ScsHitofacturableguardiaMapper{

	@SelectProvider(type = ScsHitofacturableguardiaSqlExtendsProvider.class, method = "getBaremos")
	@Results({ 
//		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PRECIO", property = "value", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getBaremos(String idGuardia, String idLenguaje);
	
	@SelectProvider(type = ScsHitofacturableguardiaSqlExtendsProvider.class, method = "getCheckSeparacionGuardias")
	@Results({ 
		@Result(column = "DIASAPLICABLES", property = "separarGuardia", jdbcType = JdbcType.VARCHAR),
	})
	List<GuardiasItem> getCheckSeparacionGuardias(String idGuardia, String idTurno, String idInstitucion);
}
