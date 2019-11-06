package org.redabogacia.ecom.db.services.ecom.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomColaExample;
import org.itcgae.siga.db.mappers.EcomColaMapper;
import org.redabogacia.ecom.db.services.ecom.providers.EcomColaExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface EcomColaExtendsMapper extends EcomColaMapper {

	@SelectProvider(type = EcomColaExtendsSqlProvider.class, method = "getPendientes")
	@Results({ @Result(column = "IDECOMCOLA", property = "idecomcola", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDESTADOCOLA", property = "idestadocola", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDOPERACION", property = "idoperacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "REINTENTO", property = "reintento", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHACREACION", property = "fechacreacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAEJECUCION", property = "fechaejecucion", jdbcType = JdbcType.TIMESTAMP) })
	List<EcomCola> getPendientes(EcomColaExample example, int horasEnEjecucion);
}
