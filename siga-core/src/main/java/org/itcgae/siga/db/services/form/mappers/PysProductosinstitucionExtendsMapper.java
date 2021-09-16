package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTOs.form.CertificadoCursoItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.PysProductosinstitucionMapper;
import org.itcgae.siga.db.services.fac.providers.PySTiposProductosSqlExtendsProvider;
import org.itcgae.siga.db.services.form.providers.PysProductosinstitucionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;



@Service
@Primary
public interface PysProductosinstitucionExtendsMapper extends PysProductosinstitucionMapper{

	@SelectProvider(type = PysProductosinstitucionSqlExtendsProvider.class, method = "selectTypesCertificatesCourse")
	@Results({
		@Result(column = "IDTIPOPRODUCTO", property = "idTipoProducto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPRODUCTO", property = "idProducto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALUE", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPRODUCTOINSTITUCION", property = "idProductoInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALOR", property = "precio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CLAVE", property = "clave", jdbcType = JdbcType.VARCHAR),
		@Result(column = "LABEL", property = "label", jdbcType = JdbcType.VARCHAR)

	})
	List<CertificadoCursoItem> selectTypesCertificatesCourse(Short idInstitucion);

	@SelectProvider(type = PysProductosinstitucionSqlExtendsProvider.class, method = "detalleProducto")
	@Results({
		@Result(column = "IDTIPOPRODUCTO", property = "idtipoproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDPRODUCTO", property = "idproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDPRODUCTOINSTITUCION", property = "idproductoinstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALOR", property = "valor", jdbcType = JdbcType.FLOAT),
		@Result(column = "MOMENTOCARGO", property = "momentocargo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOLICITARBAJA", property = "solicitarbaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOLICITARALTA", property = "solicitaralta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CUENTACONTABLE", property = "cuentacontable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDIMPRESORA", property = "idimpresora", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDPLANTILLA", property = "idplantilla", jdbcType = JdbcType.NUMERIC),
		@Result(column = "TIPOCERTIFICADO", property = "tipocertificado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE),
		@Result(column = "IDCONTADOR", property = "idcontador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOFACTURABLE", property = "nofacturable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOIVA", property = "idtipoiva", jdbcType = JdbcType.NUMERIC),
		@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGO_TRASPASONAV", property = "codigo_traspasonav", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.NUMERIC),
		@Result(column = "CATEGORIA", property = "categoria", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALORIVA", property = "valoriva", jdbcType = JdbcType.NUMERIC),
		@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR)
		}) 
	ProductoDetalleDTO detalleProducto(int idTipoProducto, int idProducto, int idProductoInstitucion, Short idInstitucion);
	
	@SelectProvider(type = PysProductosinstitucionSqlExtendsProvider.class, method = "obtenerFormasDePagoInternetByProducto")
	List<Integer> obtenerFormasDePagoInternetByProducto(int idTipoProducto, int idProducto, int idProductoInstitucion, Short idInstitucion);
	
	@SelectProvider(type = PysProductosinstitucionSqlExtendsProvider.class, method = "obtenerFormasDePagoSecretariaByProducto")
	List<Integer> obtenerFormasDePagoSecretariaByProducto(int idTipoProducto, int idProducto, int idProductoInstitucion, Short idInstitucion);

	@SelectProvider(type = PysProductosinstitucionSqlExtendsProvider.class, method = "getIndiceMaxProducto")
	@Results({ 
		@Result(column = "IDPRODUCTOINSTITUCION", property = "newId", jdbcType = JdbcType.NUMERIC)
		}) 
	NewIdDTO getIndiceMaxProducto(ProductoDetalleDTO producto, Short idInstitucion);
	
	@SelectProvider(type = PysProductosinstitucionSqlExtendsProvider.class, method = "getIdProductoInstitucion")
	@Results({ 
		@Result(column = "IDPRODUCTOINSTITUCION", property = "newId", jdbcType = JdbcType.NUMERIC)
		}) 
	NewIdDTO getIdProductoInstitucion(ProductoDetalleDTO producto, Short idInstitucion);

}
