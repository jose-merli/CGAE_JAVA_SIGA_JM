package org.itcgae.siga.db.services.fac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.TiposIncluidosItem;
import org.itcgae.siga.db.mappers.PysCompraMapper;
import org.itcgae.siga.db.services.fac.providers.FacFacturacionsuscripcionExtendsSqlProvider;
import org.itcgae.siga.db.services.fac.providers.PysCompraExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysCompraExtendsMapper extends PysCompraMapper {

	@SelectProvider(type = PysCompraExtendsSqlProvider.class, method = "getTiposProductos")
	@Results({ 
		@Result(column = "idtipoproducto", property = "idTipoIncluido", jdbcType = JdbcType.NUMERIC),
		@Result(column = "descripcion", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idseriefacturacion", property = "idSerieFacturacion", jdbcType = JdbcType.NUMERIC)
		})
	List<TiposIncluidosItem> getTiposProductos(Short idInstitucion, String idioma);
	
}
