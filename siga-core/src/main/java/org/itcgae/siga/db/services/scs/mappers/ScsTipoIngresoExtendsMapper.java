package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsTipoingresoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsTipoGrupoLaboralSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoIngresoSqlExtendsProvider;

public interface ScsTipoIngresoExtendsMapper extends ScsTipoingresoMapper {
	
	@SelectProvider(type = ScsTipoIngresoSqlExtendsProvider.class, method = "getTiposIngresos")
	@Results({
		@Result(column = "IDGRUPO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getTiposIngresos(String idLenguaje);

}
