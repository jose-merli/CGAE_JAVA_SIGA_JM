package org.itcgae.siga.db.services.fac.mappers;

import org.springframework.stereotype.Service;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FiltroServicioItem;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ListaServiciosItem;
import org.itcgae.siga.DTO.fac.TiposServiciosItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.PysServiciosMapper;
import org.itcgae.siga.db.services.fac.providers.PySTiposProductosSqlExtendsProvider;
import org.itcgae.siga.db.services.fac.providers.PySTiposServiciosSqlExtendsProvider;

@Service
public interface PySTiposServiciosExtendsMapper extends PysServiciosMapper{
	
	@SelectProvider(type = PySTiposServiciosSqlExtendsProvider.class, method = "searchTiposServicios")
	@Results({ 
		@Result(column = "IDTIPOSERVICIOS", property = "idtiposervicios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION_TIPO", property = "descripciontipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDSERVICIO", property = "idservicio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE)
		}) 
	List<TiposServiciosItem> searchTiposServicios(String idioma, Short idInstitucion);
	
	@SelectProvider(type = PySTiposServiciosSqlExtendsProvider.class, method = "searchTiposServiciosHistorico")
	@Results({ 
		@Result(column = "IDTIPOPRODUCTO", property = "idtipoproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION_TIPO", property = "descripciontipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPRODUCTO", property = "idproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE)
		}) 
	List<TiposServiciosItem> searchTiposServiciosHistorico(String idioma, Short idInstitucion);
	
	//Servicio que devuelve la informacion necesaria para la tabla en Facturacion --> Servicios.
	@SelectProvider(type = PySTiposServiciosSqlExtendsProvider.class, method = "searchListadoServiciosBuscador")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDSERVICIO", property = "idservicio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOSERVICIOS", property = "idtiposervicios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDSERVICIOSINSTITUCION", property = "idserviciosinstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE),
		@Result(column = "AUTOMATICO", property = "automatico", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOIVA", property = "idtipoiva", jdbcType = JdbcType.NUMERIC),
		@Result(column = "CATEGORIA", property = "categoria", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IVA", property = "iva", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PRECIO", property = "precioperiodicidad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALORMINIMO", property = "valorminimo", jdbcType = JdbcType.DOUBLE),
		@Result(column = "PERIODOMINIMO", property = "periodominimo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALORMAXIMO", property = "valormaximo", jdbcType = JdbcType.DOUBLE),
		@Result(column = "FORMA_PAGO", property = "formapago", jdbcType = JdbcType.VARCHAR),
		@Result(column = "noFacturable", property = "noFacturable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "formasPagoInternet", property = "formasPagoInternet", jdbcType = JdbcType.VARCHAR),
		@Result(column = "solicitarBaja", property = "solicitarBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechaBajaIva", property = "fechaBajaIva", jdbcType = JdbcType.DATE),
		@Result(column = "valorIva", property = "valorIva", jdbcType = JdbcType.VARCHAR)
		
		}) 
	List<ListaServiciosItem> searchListadoServiciosBuscador(String idioma, Short idInstitucion, FiltroServicioItem filtroServicioItem);	
	
	@SelectProvider(type = PySTiposServiciosSqlExtendsProvider.class, method = "comboTiposServicios")
	@Results({ 
		@Result(column = "ID", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ComboItem> comboTiposServicios(String idioma);

	@SelectProvider(type = PySTiposServiciosSqlExtendsProvider.class, method = "getIndiceMaxServicio")
	@Results({ 
		@Result(column = "IDSERVICIO", property = "newId", jdbcType = JdbcType.NUMERIC)
		}) 
	NewIdDTO getIndiceMaxServicio(List<TiposServiciosItem> listadoServicios, Short idInstitucion);
	
	@UpdateProvider(type = PySTiposServiciosSqlExtendsProvider.class, method = "activarDesactivarServicio")
	int activarDesactivarServicio(AdmUsuarios usuario, Short idInstitucion, TiposServiciosItem servicio);
	
	@SelectProvider(type = PySTiposServiciosSqlExtendsProvider.class, method = "comprobarUsoServicio")
	List<Integer> comprobarUsoServicio(ListaServiciosItem servicio, Short idInstitucion);
	
	@UpdateProvider(type = PySTiposServiciosSqlExtendsProvider.class, method = "borradoLogicoServicios")
	int borradoLogicoServicios(AdmUsuarios usuario, ListaServiciosItem servicio, Short idInstitucion);
	
	@UpdateProvider(type = PySTiposServiciosSqlExtendsProvider.class, method = "borradoFisicoServiciosRegistro")
	int borradoFisicoServiciosRegistro(ListaServiciosItem servicio, Short idInstitucion);
}
