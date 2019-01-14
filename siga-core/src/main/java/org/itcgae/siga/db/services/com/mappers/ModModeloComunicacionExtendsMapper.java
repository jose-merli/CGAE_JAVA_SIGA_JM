package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.db.services.com.providers.ModModeloComunicacionExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ModModeloComunicacionExtendsMapper {
	
	@SelectProvider(type = ModModeloComunicacionExtendsSqlProvider.class, method = "selectModulosComunicacion")
	@Results({@Result(column = "IDMODELOCOMUNICACION", property = "idModeloComunicacion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PRESELECCIONAR", property = "preseleccionar", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBRECLASE", property = "clase", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABREVIATURA", property = "institucion", jdbcType = JdbcType.VARCHAR)
	})
	List<ModelosComunicacionItem> selectModulosComunicacion(DatosModelosComunicacionesSearch filtros, boolean historico);
	
}
