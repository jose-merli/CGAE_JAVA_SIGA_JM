package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.com.ModelosComunicacionesItemConNombreConsultaDestinatarios;
import org.itcgae.siga.DTOs.com.PlantillaModeloItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ModModelocomunicacionMapper;
import org.itcgae.siga.db.services.com.providers.ModModeloComunicacionExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ModModeloComunicacionExtendsMapper extends ModModelocomunicacionMapper {

	@SelectProvider(type = ModModeloComunicacionExtendsSqlProvider.class, method = "selectModelosComunicacion")
	@Results({ @Result(column = "IDMODELOCOMUNICACION", property = "idModeloComunicacion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PRESELECCIONAR", property = "preseleccionar", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBRECLASE", property = "claseComunicacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABREVIATURA", property = "institucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
			@Result(column = "PORDEFECTO", property = "porDefecto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOENVIOS", property = "idTipoEnvio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INFORMEUNICO", property = "informeUnico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR) })
	List<ModelosComunicacionItem> selectModelosComunicacion(String idInstitucion,
			DatosModelosComunicacionesSearch filtros, String idLenguaje, boolean historico);

	@SelectProvider(type = ModModeloComunicacionExtendsSqlProvider.class, method = "selectModelo")
	@Results({ @Result(column = "IDMODELOCOMUNICACION", property = "idModeloComunicacion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PRESELECCIONAR", property = "preseleccionar", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBRECLASE", property = "claseComunicacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABREVIATURA", property = "institucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
			@Result(column = "PORDEFECTO", property = "porDefecto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOENVIOS", property = "idTipoEnvio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INFORMEUNICO", property = "informeUnico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR) })
	ModelosComunicacionItem selectModelo(String idModelo, String idLenguaje);

	@SelectProvider(type = ModModeloComunicacionExtendsSqlProvider.class, method = "selectModelosComunicacionDialg")
	@Results({ @Result(column = "IDMODELOCOMUNICACION", property = "idModeloComunicacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOENVIOS", property = "idTipoEnvio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PRESELECCIONAR", property = "preseleccionar", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INFORMEUNICO", property = "informeUnico", jdbcType = JdbcType.VARCHAR)

	})
	List<ModelosComunicacionItem> selectModelosComunicacionDialogo(String idInstitucionLogueada, String idInstitucion, String idClaseComunicacion,
			String idModulo, String idLenguaje, String idConsulta, List<String> perfiles);
	
	@SelectProvider(type = ModModeloComunicacionExtendsSqlProvider.class, method = "selectModelosComunicacionDialgConConsultaDestinatarios")
	@Results({ @Result(column = "IDMODELOCOMUNICACION", property = "idModeloComunicacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOENVIOS", property = "idTipoEnvio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PRESELECCIONAR", property = "preseleccionar", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INFORMEUNICO", property = "informeUnico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRECONSULTADESTINATARIOS", property = "nombreConsultaDestinatarios", jdbcType = JdbcType.VARCHAR)

	})
	List<ModelosComunicacionesItemConNombreConsultaDestinatarios> selectModelosComunicacionDialogoConConsultaDestinatarios(String idInstitucionLogueada, String idInstitucion, String idClaseComunicacion,
			String idModulo, String idLenguaje, String idConsulta, List<String> perfiles);

	@SelectProvider(type = ModModeloComunicacionExtendsSqlProvider.class, method = "selectModelosClasesComunicacion")
	@Results({ @Result(column = "VALUE", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LABEL", property = "label", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> selectModelosClasesComunicacion(String idClasesComunicacion);

	@SelectProvider(type = ModModeloComunicacionExtendsSqlProvider.class, method = "selectPlantillaModelo")
	@Results({ @Result(column = "VALUE", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LABEL", property = "label", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> selectPlantillasModelos(String idModelo, Short idInstitucion);

	@SelectProvider(type = ModModeloComunicacionExtendsSqlProvider.class, method = "selectTipoEnvioPlantilla")
	@Results({ @Result(column = "VALUE", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LABEL", property = "label", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> selectTipoEnvioPlantilla(String idLenguaje, String idPlantilla);

	@SelectProvider(type = ModModeloComunicacionExtendsSqlProvider.class, method = "comprobarNombreDuplicado")
	@Results({ @Result(column = "NOMBRE", property = "valor", jdbcType = JdbcType.VARCHAR) })
	StringDTO comprobarNombreDuplicado(String nombreModelo);

	@SelectProvider(type = ModModeloComunicacionExtendsSqlProvider.class, method = "selectPlantillaPorDefecto")
	@Results({ @Result(column = "NOMBRE", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR) })
	List<PlantillaModeloItem> selectPlantillaPorDefecto(String idModelo, Short idInstitucion, String idLenguaje);
}
