package org.itcgae.siga.db.services.fac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.TiposProductosItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.PysProductosMapper;
import org.itcgae.siga.db.services.fac.providers.PySTiposProductosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public interface PySTiposProductosExtendsMapper extends PysProductosMapper{
	
	@SelectProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "searchTiposProductos")
	@Results({ 
		@Result(column = "IDTIPOPRODUCTO", property = "idtipoproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION_TIPO", property = "descripciontipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPRODUCTO", property = "idproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE)
		}) 
	List<TiposProductosItem> searchTiposProductos(String idioma, Short idInstitucion);
	
	@SelectProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "searchTiposProductosHistorico")
	@Results({ 
		@Result(column = "IDTIPOPRODUCTO", property = "idtipoproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION_TIPO", property = "descripciontipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPRODUCTO", property = "idproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE)
		}) 
	List<TiposProductosItem> searchTiposProductosHistorico(String idioma, Short idInstitucion);
	
	@SelectProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "getIndiceMaxProducto")
	@Results({ 
		@Result(column = "IDPRODUCTO", property = "newId", jdbcType = JdbcType.NUMERIC)
		}) 
	NewIdDTO getIndiceMaxProducto(List<TiposProductosItem> listadoProductos, Short idInstitucion);
	
	@SelectProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "comboTiposProductos")
	@Results({ 
		@Result(column = "ID", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ComboItem> comboTiposProductos(String idioma);
	
	@UpdateProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "activarDesactivarProducto")
	int activarDesactivarProducto(AdmUsuarios usuario, Short idInstitucion, List<TiposProductosItem> listadoProductos);
	
	
}
