package org.itcgae.siga.db.services.com.mappers;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FichaTarjetaPreciosItem;
import org.itcgae.siga.DTO.fac.FiltroServicioItem;
import org.itcgae.siga.DTO.fac.ListaServiciosItem;
import org.itcgae.siga.DTO.fac.ServicioDetalleDTO;
import org.itcgae.siga.DTOs.com.ConfigColumnasQueryBuilderItem;
import org.itcgae.siga.DTOs.com.ConstructorConsultasItem;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasSearch;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ComboItemConsulta;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ConConsultaMapper;
import org.itcgae.siga.db.services.com.providers.ConClaseComunicacionesExtendsSqlProvider;
import org.itcgae.siga.db.services.com.providers.ConConsultasExtendsSqlProvider;
import org.itcgae.siga.db.services.fac.providers.PySTiposServiciosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ConConsultasExtendsMapper extends ConConsultaMapper{

	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "selectConsultas")
	@Results({ @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDCONSULTA", property = "idConsulta", jdbcType = JdbcType.NUMERIC),
			@Result(column = "DESCRIPCION", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GENERAL", property = "generica", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOCONSULTA", property = "tipoConsulta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDMODULO", property = "idModulo", jdbcType = JdbcType.NUMERIC),
			@Result(column = "BASES", property = "bases", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTABLA", property = "idTabla", jdbcType = JdbcType.NUMERIC),
			@Result(column = "ESEXPERTA", property = "experta", jdbcType = JdbcType.VARCHAR),
			// CLOB
			@Result(column = "SENTENCIA", property = "sentencia", jdbcType =JdbcType.CLOB),
			@Result(column = "OBSERVACIONES", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
			@Result(column = "IDOBJETIVO", property = "idObjetivo", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBREOBJETIVO", property = "objetivo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREMODULO", property = "modulo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRECLASE", property = "claseComunicacion", jdbcType = JdbcType.VARCHAR) })
	List<ConsultaItem> selectConsultasSearch(Short idInstitucion, String idLenguaje, List<String> perfiles, ConsultasSearch filtros);
	
	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "selectServiciosByConsulta")
	@Results({ @Result(column = "IDSERVICIOSINSTITUCION", property = "idserviciosinstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDSERVICIO", property = "idservicio", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDTIPOSERVICIOS", property = "idtiposervicios", jdbcType = JdbcType.NUMERIC)})
	List<ServicioDetalleDTO> selectServiciosByConsulta(Short idInstitucion, String idConsulta);
	
	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "selectPreciosByConsulta")
	@Results({ @Result(column = "idtiposervicios", property = "idtiposervicios", jdbcType = JdbcType.NUMERIC),
			@Result(column = "idservicio", property = "idservicio", jdbcType = JdbcType.NUMERIC),
			@Result(column = "idserviciosinstitucion", property = "idserviciosinstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "idperiodicidad", property = "idperiodicidad", jdbcType = JdbcType.NUMERIC),
			@Result(column = "idpreciosservicios", property = "idpreciosservicios", jdbcType = JdbcType.NUMERIC)})
	List<FichaTarjetaPreciosItem> selectPreciosByConsulta(Short idInstitucion, String idConsulta);
	
	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "selectMaxIdConsulta")
	@Results({
		@Result(column = "IDMAX", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO selectMaxIDConsulta();
	
	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "selectConsultasPlantilla")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCONSULTA", property = "idConsulta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOCONSULTA", property = "tipoConsulta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONES", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDMODULO", property = "idModulo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MODULO", property = "modulo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDOBJETIVO", property = "idObjetivo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SENTENCIA", property = "sentencia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GENERAL", property = "generica", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBJETIVO", property = "objetivo", jdbcType = JdbcType.VARCHAR)
	})
	List<ConsultaItem> selectConsultasPlantillas(Short idInstitucion, String idPlantillaEnvios, String idtipoEnvio, String idLenguaje);
	
	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "selectConsultasById")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCONSULTA", property = "idConsulta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOCONSULTA", property = "tipoConsulta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONES", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDMODULO", property = "idModulo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MODULO", property = "modulo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDOBJETIVO", property = "idObjetivo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SENTENCIA", property = "sentencia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GENERAL", property = "generica", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBJETIVO", property = "objetivo", jdbcType = JdbcType.VARCHAR)
	})
	List<ConsultaItem> selectConsultasById(Short idInstitucion, String idLenguaje, String idConsulta);

	@ResultType(value = List.class)
	public List<Map<String, Object>> ejecutarConsulta(@Param(value = "query") Map<String,String> querys);
	
	@ResultType(value = List.class)
	public List<Map<String, Object>> ejecutarConsultaString(@Param(value = "query") String query);
	
	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "selectConsultasDisponibles")
	@Results({@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCONSULTA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItemConsulta> selectConsultasDisponibles(Short IdInstitucion, Long idClaseComunicacion, Long idObjetivo);
	
	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "selectConsultasDisponiblesPlantillasEnvio")
	@Results({@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCONSULTA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItemConsulta> selectConsultasDisPlantilla(Short IdInstitucion);
	
	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "selectObjetivo")
	@Results({@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	String SelectObjetivo(String idObjetivo, String idLenguaje);

	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "selectConsultasDisponiblesFiltro")
	@Results({@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCONSULTA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CLASECOMUNICACION", property = "claseComunicacion", jdbcType = JdbcType.VARCHAR)

	})
	List<ComboItemConsulta> selectConsultasDisponiblesFiltro(Short IdInstitucion, Long idClaseComunicacion, Long idObjetivo, String filtro);

	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "obtenerDatosConsulta")
	@Results({
		@Result(column = "IDCONSULTA", property = "idconsulta", jdbcType = JdbcType.NUMERIC),
		@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.NUMERIC),
		@Result(column = "CONECTOR", property = "conector", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABRIRPAR", property = "abrirparentesis", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCAMPO", property = "idcampo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTABLA", property = "idtabla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripciontabla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREREAL", property = "nombrereal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREENCONSULTA", property = "campo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OPERADOR", property = "operador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SIMBOLO", property = "simbolo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALOR", property = "valor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CERRARPAR", property = "cerrarparentesis", jdbcType = JdbcType.VARCHAR)
		
		}) 
	List<ConstructorConsultasItem> obtenerDatosConsulta(String idioma, Short idInstitucion, String idConsulta);
	
	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "obtenerConsulta")
	String obtenerConsulta(Short idInstitucion, String idConsulta);
	
	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "obtenerConfigColumnasQueryBuilder")
	@Results({
		@Result(column = "TIPOCAMPO", property = "tipocampo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCAMPO", property = "idcampo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREENCONSULTA", property = "nombreenconsulta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SELECTAYUDA", property = "selectayuda", jdbcType = JdbcType.VARCHAR)
		
		}) 
	List<ConfigColumnasQueryBuilderItem> obtenerConfigColumnasQueryBuilder();
	
	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "obtenerCombosQueryBuilder")
	@Results({@Result(column = "ID", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> obtenerCombosQueryBuilder(ConfigColumnasQueryBuilderItem configColumnasQueryBuilderItem, String idioma, Short idInstitucion);
	
	@SelectProvider(type = ConConsultasExtendsSqlProvider.class, method = "getIdOperacion")
	int getIdOperacion(String idCampo, String simbolo);
	
}	
