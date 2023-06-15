package org.itcgae.siga.db.services.fac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.FacTiposservinclsenfactMapper;
import org.itcgae.siga.db.services.fac.providers.FacTiposservinclsenfactExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FacTiposservinclsenfactExtendsMapper extends FacTiposservinclsenfactMapper {

	@SelectProvider(type = FacTiposservinclsenfactExtendsSqlProvider.class, method = "getTiposServicios")
	@Results({
		@Result(column = "value", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "label", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getTiposServicios(String idSerieFacturacion, Short idInstitucion, String idioma);
	
}
