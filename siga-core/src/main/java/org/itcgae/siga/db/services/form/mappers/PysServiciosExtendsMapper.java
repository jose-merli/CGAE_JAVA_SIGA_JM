package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.TiposServiciosItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.PysServiciosMapper;
import org.itcgae.siga.db.services.fac.providers.PySTiposProductosSqlExtendsProvider;
import org.itcgae.siga.db.services.form.providers.PysServiciosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysServiciosExtendsMapper extends PysServiciosMapper{

	@SelectProvider(type = PysServiciosSqlExtendsProvider.class, method = "selectMaxIdServicio")
	@Results({
		@Result(column = "IDSERVICIO", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectMaxIdServicio(Short idInstitucion);
	
	@SelectProvider(type = PysServiciosSqlExtendsProvider.class, method = "selectIdServicioByIdCurso")
	@Results({
		@Result(column = "IDSERVICIO", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectIdServicioByIdCurso(Short idInstitucion, Long idCurso);

	@SelectProvider(type = PysServiciosSqlExtendsProvider.class, method = "getServicesCourse")
	@Results({
		@Result(column = "IDTIPOSERVICIO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getServicesCourse(String idInstitucion, String idLenguaje);
	
	@SelectProvider(type = PysServiciosSqlExtendsProvider.class, method = "searchTiposServicios")
	@Results({ 
		@Result(column = "IDTIPOSERVICIOS", property = "idtiposervicios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION_TIPO", property = "descripciontipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDSERVICIO", property = "idservicio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE)
		}) 
	List<TiposServiciosItem> searchTiposServicios(String idioma, Short idInstitucion);
	
	@SelectProvider(type = PysServiciosSqlExtendsProvider.class, method = "searchTiposServiciosHistorico")
	@Results({ 
		@Result(column = "IDTIPOPRODUCTO", property = "idtipoproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION_TIPO", property = "descripciontipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPRODUCTO", property = "idproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE)
		}) 
	List<TiposServiciosItem> searchTiposServiciosHistorico(String idioma, Short idInstitucion);
	
	@SelectProvider(type = PysServiciosSqlExtendsProvider.class, method = "comboTiposServicios")
	@Results({ 
		@Result(column = "ID", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ComboItem> comboTiposServicios(String idioma);
	
	@SelectProvider(type = PysServiciosSqlExtendsProvider.class, method = "searchTiposServiciosByIdCategoria")
	@Results({ 
		@Result(column = "ID", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ComboItem> searchTiposServiciosByIdCategoria(String idioma, Short idInstitucion, String idCategoria);

	@SelectProvider(type = PysServiciosSqlExtendsProvider.class, method = "getIndiceMaxServicio")
	@Results({ 
		@Result(column = "IDSERVICIO", property = "newId", jdbcType = JdbcType.NUMERIC)
		}) 
	NewIdDTO getIndiceMaxServicio(List<TiposServiciosItem> listadoServicios, Short idInstitucion);
	
	@UpdateProvider(type = PysServiciosSqlExtendsProvider.class, method = "activarDesactivarServicio")
	int activarDesactivarServicio(AdmUsuarios usuario, Short idInstitucion, TiposServiciosItem servicio);
	
	@SelectProvider(type = PysServiciosSqlExtendsProvider.class, method = "obtenerCodigosPorColegioServicios")
	List<String> obtenerCodigosPorColegioServicios(Short idInstitucion);
}
