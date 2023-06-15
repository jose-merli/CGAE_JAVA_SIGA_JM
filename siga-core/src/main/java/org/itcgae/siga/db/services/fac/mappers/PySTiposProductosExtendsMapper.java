package org.itcgae.siga.db.services.fac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.TiposProductosItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.PysProductosMapper;
import org.itcgae.siga.db.services.fac.providers.PySTiposProductosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public interface PySTiposProductosExtendsMapper extends PysProductosMapper{
	
	//Datos tabla pantalla Maestros --> Tipos Productos
	@SelectProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "searchTiposProductos")
	@Results({ 
		@Result(column = "IDTIPOPRODUCTO", property = "idtipoproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION_TIPO", property = "descripciontipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPRODUCTO", property = "idproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE)
		}) 
	List<TiposProductosItem> searchTiposProductos(String idioma, Short idInstitucion);
	
	//Datos con historico (incluidos registros con fechabaja != null) tabla pantalla Maestros --> Tipos Productos
	@SelectProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "searchTiposProductosHistorico")
	@Results({ 
		@Result(column = "IDTIPOPRODUCTO", property = "idtipoproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION_TIPO", property = "descripciontipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPRODUCTO", property = "idproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE)
		}) 
	List<TiposProductosItem> searchTiposProductosHistorico(String idioma, Short idInstitucion);
	
	//Obtiene los datos del combo categoria de productos (PYS_TIPOSPRODUCTOS)
	@SelectProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "comboTiposProductos")
	@Results({ 
		@Result(column = "ID", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ComboItem> comboTiposProductos(Short institucion, String idioma);
	
	//Realiza un borrado logico (establecer fechabaja = new Date()) o lo reactiva en caso de que esta inhabilitado.
	@UpdateProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "activarDesactivarProducto")
	int activarDesactivarProducto(AdmUsuarios usuario, Short idInstitucion, TiposProductosItem producto);
	
	//Obtiene el siguiente id a establecer a la hora de crear un nuevo tipo producto (idproducto - PYS_PRODUCTOS)
	@SelectProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "getIndiceMaxTipoProducto")
	@Results({ 
		@Result(column = "IDPRODUCTO", property = "newId", jdbcType = JdbcType.NUMERIC)
		}) 
	NewIdDTO getIndiceMaxTipoProducto(int idtipoproducto, Short idInstitucion);
	
	//Obtiene el siguiente id a establecer a la hora de crear un nuevo tipo producto (idproducto - PYS_PRODUCTOS) //REVISAR EN QUE SITIOS SE USA
	@SelectProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "getIndiceMaxProducto")
	@Results({ 
		@Result(column = "IDPRODUCTO", property = "newId", jdbcType = JdbcType.NUMERIC)
		}) 
	NewIdDTO getIndiceMaxProducto(List<TiposProductosItem> listadoProductos, Short idInstitucion);
	
	//Obtiene la informacion de los productos al darle a buscar en Facturacion --> Productos para rellenar la tabla.
	@SelectProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "searchListadoProductosBuscador")
	@Results({
		@Result(column = "IDPRODUCTO", property = "idproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOPRODUCTO", property = "idtipoproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDPRODUCTOINSTITUCION", property = "idproductoinstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALOR", property = "valor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE),
		@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CATEGORIA", property = "categoria", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IVA", property = "iva", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PRECIO_IVA", property = "precioiva", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FORMAS_PAGO", property = "formapago", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOFACTURABLE", property = "noFacturable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idtipoiva", property = "idtipoiva", jdbcType = JdbcType.VARCHAR),
		@Result(column = "valorIva", property = "valorIva", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FechaBajaIva", property = "fechaBajaIva", jdbcType = JdbcType.DATE),
		@Result(column = "idformaspago", property = "idFormasPago", jdbcType = JdbcType.VARCHAR),
		@Result(column = "formaspagoInternet", property = "formasPagoInternet", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOLICITARALTA", property = "solicitarAlta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOLICITARBAJA", property = "solicitarBaja", jdbcType = JdbcType.VARCHAR),
		}) 
	List<ListaProductosItem> searchListadoProductosBuscador(String idioma, Short idInstitucion, FiltroProductoItem filtroProductoItem);
	
	//Comprueba si un producto tiene usos para saber si se ha de borrar logicamente o fisicamente
	@SelectProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "comprobarUsoProducto")
	List<Integer> comprobarUsoProducto(ListaProductosItem producto, Short idInstitucion);
	
	//Comprueba si un producto tiene solicitudes de compra para saber si se ha de borrar logicamente o fisicamente
	@SelectProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "comprobarSolicitudProducto")
	List<Integer> comprobarSolicitudProducto(ListaProductosItem producto, Short idInstitucion);
	
	//Borra logicamente un producto (establecer fecha de baja a dia de hoy)
	@UpdateProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "borradoLogicoProductos")
	int borradoLogicoProductos(AdmUsuarios usuario, ListaProductosItem producto, Short idInstitucion);
	
	//Borra fisicamente un producto (cuando no tiene ni usos ni solicitudes de compra)
	@UpdateProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "borradoFisicoProductosRegistro")
	int borradoFisicoProductosRegistro(ListaProductosItem producto, Short idInstitucion);
	
	//Borra el identificador del producto cuando se realiza un borrado fisico del producto (cuando no tiene ni usos ni solicitudes de compra).
	@UpdateProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "borradoFisicoProductosIdentificador")
	int borradoFisicoProductosIdentificador(ListaProductosItem producto, Short idInstitucion);
	
	@SelectProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "searchTiposProductosByIdCategoria")
	@Results({ 
		@Result(column = "ID", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ComboItem> searchTiposProductosByIdCategoria(String idioma, Short idInstitucion, String idCategoria);

	@SelectProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "searchTiposProductosByIdCategoriaMultiple")
	@Results({ 
		@Result(column = "ID", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ComboItem> searchTiposProductosByIdCategoriaMultiple(String idioma, Short idInstitucion, String idCategoria);
	
	//Obtiene los codigos de productos existentes en un colegio para su uso por ejemplo en validar que en ficha producto a la hora de crear/editar no se introduzca un codigo ya existente (para saber como esta formado el codigo revisar la documentacion)
	@SelectProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "obtenerCodigosPorColegio")
	List<String> obtenerCodigosPorColegio(Short idInstitucion);
	
	//Se obtienen los importes totales de las compras de los distintos productos de la ficha
	@SelectProvider(type = PySTiposProductosSqlExtendsProvider.class, method = "getImpTotalesCompra")
	List<String> getImpTotalesCompra(Short idInstitucion, Long idPeticion);
}