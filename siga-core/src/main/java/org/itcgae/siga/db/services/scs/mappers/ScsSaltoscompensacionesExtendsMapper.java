package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.db.mappers.ScsSaltoscompensacionesMapper;
import org.itcgae.siga.db.services.scs.providers.ScsSaltoscompensacionesSqlExtendsProvider;

public interface ScsSaltoscompensacionesExtendsMapper extends ScsSaltoscompensacionesMapper{
	

	@SelectProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "searchSaltosCompensaciones")
	@Results({ @Result(column = "GRUPO", property = "grupo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBRE_TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREGUARDIA", property = "guardia", jdbcType = JdbcType.VARCHAR), 
			@Result(column = "IDSALTOSTURNO", property = "idSaltosTurno", jdbcType = JdbcType.VARCHAR), 
			@Result(column = "COLEGIADO_GRUPO", property = "colegiadoGrupo", jdbcType = JdbcType.VARCHAR), 
			@Result(column = "LETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR), 
			@Result(column = "SALTOOCOMPENSACION", property = "saltoCompensacion", jdbcType = JdbcType.VARCHAR), 
			@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.VARCHAR), 
			@Result(column = "MOTIVO", property = "motivo", jdbcType = JdbcType.VARCHAR), 
			@Result(column = "FECHA_USO", property = "fechaUso", jdbcType = JdbcType.VARCHAR), 
	})
	List<SaltoCompGuardiaItem> searchSaltosCompensaciones(SaltoCompGuardiaItem saltoItem,String idInstitucion);
	

}
