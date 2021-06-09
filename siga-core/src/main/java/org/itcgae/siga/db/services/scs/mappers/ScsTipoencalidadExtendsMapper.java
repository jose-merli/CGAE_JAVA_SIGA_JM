package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsTipoencalidadMapper;
import org.itcgae.siga.db.services.scs.providers.ScsTipoencalidadSqlExtendsProvider;

public interface ScsTipoencalidadExtendsMapper extends ScsTipoencalidadMapper{

	@SelectProvider(type= ScsTipoencalidadSqlExtendsProvider.class, method= "comboTipoencalidad")
	@Results({ 
		@Result(column = "IDTIPOENCALIDAD", property = "value", jdbcType= JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboTipoencalidad(String idLenguaje, Short idInstitucion);
}
