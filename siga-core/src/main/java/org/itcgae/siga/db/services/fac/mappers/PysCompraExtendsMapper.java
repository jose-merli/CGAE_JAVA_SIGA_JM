package org.itcgae.siga.db.services.fac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.PysCompraMapper;
import org.itcgae.siga.db.services.fac.providers.PysCompraExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysCompraExtendsMapper extends PysCompraMapper {

	@SelectProvider(type = PysCompraExtendsSqlProvider.class, method = "getTiposProductos")
	@Results({ 
		@Result(column = "idtipoproducto", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "descripcion", property = "label", jdbcType = JdbcType.VARCHAR)
		})
	List<ComboItem> getTiposProductos(String idSerieFacturacion, Short idInstitucion, String idioma);
	
}
