package org.itcgae.siga.db.services.fac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ListaServiciosItem;
import org.itcgae.siga.DTO.fac.PrecioServicioItem;
import org.itcgae.siga.DTO.fac.TiposProductosItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.PysFormapagoMapper;
import org.itcgae.siga.db.services.fac.providers.PySTipoFormaPagoSqlExtendsProvider;
import org.itcgae.siga.db.services.fac.providers.PySTiposProductosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PySTipoFormaPagoExtendsMapper extends PysFormapagoMapper{
	
	@SelectProvider(type = PySTipoFormaPagoSqlExtendsProvider.class, method = "comboTipoFormaPago")
	@Results({ 
		@Result(column = "ID", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ComboItem> comboTipoFormaPago(String idioma);
	
	@SelectProvider(type = PySTipoFormaPagoSqlExtendsProvider.class, method = "comboTipoFormaPagoInternet")
	@Results({ 
		@Result(column = "ID", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ComboItem> comboTipoFormaPagoInternet(String idioma);
	
	@SelectProvider(type = PySTipoFormaPagoSqlExtendsProvider.class, method = "comboTipoFormaPagoSecretaria")
	@Results({ 
		@Result(column = "ID", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ComboItem> comboTipoFormaPagoSecretaria(String idioma);
	
	@SelectProvider(type = PySTipoFormaPagoSqlExtendsProvider.class, method = "comboPreciosServicio")
	@Results({ 
		@Result(column = "IDPreciosServicios", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idPeriodicidad", property = "idPeriodicidad", jdbcType = JdbcType.VARCHAR)
		}) 
	List<PrecioServicioItem> comboPreciosServicio(String idioma);
	
	//Realiza un borrado fisico de las formas de pago de un producto
	@UpdateProvider(type = PySTipoFormaPagoSqlExtendsProvider.class, method = "borradoFisicoFormasPagoByProducto")
	int borradoFisicoFormasPagoByProducto(ListaProductosItem producto,Short idInstitucion);
	
	//Realiza un borrado fisico de las formas de pago de un servicio
	@UpdateProvider(type = PySTipoFormaPagoSqlExtendsProvider.class, method = "borradoFisicoFormasPagoByServicio")
	int borradoFisicoFormasPagoByServicio(ListaServiciosItem servicio,Short idInstitucion);
	
	
}
