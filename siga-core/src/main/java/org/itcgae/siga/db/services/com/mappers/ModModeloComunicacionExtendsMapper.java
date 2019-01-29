package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.services.com.providers.ModModeloComunicacionExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ModModeloComunicacionExtendsMapper {
	
	@SelectProvider(type = ModModeloComunicacionExtendsSqlProvider.class, method = "selectModelosComunicacion")
	@Results({@Result(column = "IDMODELOCOMUNICACION", property = "idModeloComunicacion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PRESELECCIONAR", property = "preseleccionar", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBRECLASE", property = "claseComunicacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABREVIATURA", property = "institucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE)
	})
	List<ModelosComunicacionItem> selectModelosComunicacion(DatosModelosComunicacionesSearch filtros, boolean historico);

	
	@SelectProvider(type = ModModeloComunicacionExtendsSqlProvider.class, method = "selectModelosComunicacionDialg")
	@Results({@Result(column = "IDMODELOCOMUNICACION", property = "idModeloComunicacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR)
	})
	List<ModelosComunicacionItem> selectModelosComunicacionDialogo(Short idInstitucion, String[] idsClasesComunicacion);
	
	@SelectProvider(type = ModModeloComunicacionExtendsSqlProvider.class, method = "selectPlantillaModelo")
	@Results({@Result(column = "VALUE", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "LABEL", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectPlantillasModelos(String idModelo);
	
	@SelectProvider(type = ModModeloComunicacionExtendsSqlProvider.class, method = "selectTipoEnvioPlantilla")
	@Results({@Result(column = "VALUE", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "LABEL", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectTipoEnvioPlantilla(String idLenguaje, String idPlantilla);
}
