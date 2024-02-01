package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.CenPoblaciones;
import org.itcgae.siga.db.mappers.CenPoblacionesMapper;
import org.itcgae.siga.db.services.cen.providers.CenPoblacionesSqlExtendsProvider;

public interface CenPoblacionesExtendsMapper extends CenPoblacionesMapper{

	@SelectProvider(type = CenPoblacionesSqlExtendsProvider.class, method = "getComboPoblaciones")
	@Results({
		@Result(column = "IDPOBLACION", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getComboPoblaciones (String idProvincia);
	
	@SelectProvider(type = CenPoblacionesSqlExtendsProvider.class, method = "selectByFilter")
	@Results({ @Result(column = "IDPOBLACION", property = "idpoblacion", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "IDPROVINCIA", property = "idprovincia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPARTIDO", property = "idpartido", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INE", property = "ine", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPOBLACIONMUNICIPIO", property = "idpoblacionmunicipio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PRIORIDAD", property = "prioridad", jdbcType = JdbcType.DECIMAL) })
	List<CenPoblaciones> selectByFilter(String filtro, String idProvincia);
	
	@SelectProvider(type = CenPoblacionesSqlExtendsProvider.class, method = "recuperaPoblacionPorDescripcion")
	@Results({ @Result(column = "IDPOBLACION", property = "idpoblacion", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "IDPROVINCIA", property = "idprovincia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPARTIDO", property = "idpartido", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INE", property = "ine", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPOBLACIONMUNICIPIO", property = "idpoblacionmunicipio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PRIORIDAD", property = "prioridad", jdbcType = JdbcType.DECIMAL) })
	List<CenPoblaciones> recuperaPoblacionPorDescripcion(String descripcion, String provincia);
}
