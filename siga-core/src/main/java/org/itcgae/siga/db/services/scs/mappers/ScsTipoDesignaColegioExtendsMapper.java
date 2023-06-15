package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsTipodesignacolegioMapper;
import org.itcgae.siga.db.services.scs.providers.ScsTipoDesignaColegioSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsTipoDesignaColegioExtendsMapper extends ScsTipodesignacolegioMapper{

	@SelectProvider(type = ScsTipoDesignaColegioSqlExtendsProvider.class, method = "comboTipoDesignacion")
	@Results({
		@Result(column = "IDTIPODESIGNACOLEGIO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboTipoDesignacion(Short idLenguaje, String idInstitucion);
	
}
