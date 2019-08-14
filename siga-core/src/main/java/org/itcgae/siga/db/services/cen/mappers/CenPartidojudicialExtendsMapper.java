package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.CenPartidojudicial;
import org.itcgae.siga.db.mappers.CenPartidojudicialMapper;
import org.itcgae.siga.db.services.cen.providers.CenPartidojudicialSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenPartidojudicialExtendsMapper extends CenPartidojudicialMapper{
	
	@SelectProvider(type = CenPartidojudicialSqlExtendsProvider.class, method = "getPartidoByInstitucion")
	@Results({
		@Result(column = "IDPARTIDO", property = "idpartido", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.DATE),
		@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCIONPROPIETARIO", property = "idinstitucionpropietario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR)
	})
	List<CenPartidojudicial> getPartidoByInstitucion(Short idInstitucion);
	
	
	@SelectProvider(type = CenPartidojudicialSqlExtendsProvider.class, method = "getPartidosJudiciales")
	@Results({
		@Result(column = "IDPARTIDO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getPartidosJudiciales();
	
}
