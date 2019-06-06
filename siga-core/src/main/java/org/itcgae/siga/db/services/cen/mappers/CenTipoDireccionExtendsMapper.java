package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.CenTipodireccion;
import org.itcgae.siga.db.mappers.CenTipodireccionMapper;
import org.itcgae.siga.db.services.cen.providers.CenTipodireccionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenTipoDireccionExtendsMapper extends CenTipodireccionMapper{
	
	@SelectProvider(type = CenTipodireccionSqlExtendsProvider.class, method = "selectTipoDireccion")
	@Results({
		@Result(column = "IDTIPODIRECCION", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectTipoDireccion(String idLenguaje);
	
}
