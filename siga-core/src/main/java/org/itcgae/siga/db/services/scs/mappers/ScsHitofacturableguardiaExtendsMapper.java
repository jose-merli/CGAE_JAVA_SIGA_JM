package org.itcgae.siga.db.services.scs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.scs.BaremosGuardiaItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.db.mappers.ScsHitofacturableguardiaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsHitofacturableguardiaSqlExtendsProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScsHitofacturableguardiaExtendsMapper extends ScsHitofacturableguardiaMapper {

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
	
	@SelectProvider(type = ScsHitofacturableguardiaSqlExtendsProvider.class, method = "getTurnoForGuardia")
	@Results({
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "value", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getTurnoForGuardia(String idInstitucion);
	
	@SelectProvider(type = ScsHitofacturableguardiaSqlExtendsProvider.class, method = "getGuardiasByConf")
	@Results({ 
		@Result(column = "DIASAPLICABLES", property = "dias", jdbcType = JdbcType.VARCHAR),
		@Result(column = "BAREMO", property = "baremo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.NUMERIC),
		@Result(column = "PRECIOHITO", property = "precioHito", jdbcType = JdbcType.VARCHAR),
		@Result(column = "POR_DIA", property = "porDia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDHITO", property = "idHito", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMTURNO", property = "nomturno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMGUARDIA", property = "nomguardia", jdbcType = JdbcType.VARCHAR),
		
	})
	List<BaremosGuardiaItem> getGuardiasByConf(String idInstitucion);
}
