package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.CenDireccionTipodireccion;
import org.itcgae.siga.db.mappers.CenDireccionTipodireccionMapper;
import org.itcgae.siga.db.services.cen.providers.CenDireccionTipodireccionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenDireccionTipodireccionExtendsMapper extends CenDireccionTipodireccionMapper {

	@SelectProvider(type = CenDireccionTipodireccionSqlExtendsProvider.class, method = "select")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDDIRECCION", property = "iddireccion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPODIRECCION", property = "idtipodireccion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	List<CenDireccionTipodireccion> select(String idPersona, String idInstitucion, String[] idTipoDireccion, String idDireccion);
}
