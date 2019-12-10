package org.itcgae.siga.db.services.scs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.EstadoEjgItem;
import org.itcgae.siga.db.mappers.ScsEstadoejgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsEstadoejgSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsEstadoejgExtendsMapper extends ScsEstadoejgMapper{

	@SelectProvider(type = ScsEstadoejgSqlExtendsProvider.class, method = "getEstadoEjg")
	@Results({ 
		@Result(column = "IDESTADOEJG", property = "idEstadoejg", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
	})
	EstadoEjgItem getEstadoEjg(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje);
}
