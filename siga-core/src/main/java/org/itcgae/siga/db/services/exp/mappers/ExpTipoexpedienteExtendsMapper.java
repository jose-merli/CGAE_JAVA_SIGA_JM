package org.itcgae.siga.db.services.exp.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ExpTipoexpedienteMapper;
import org.itcgae.siga.db.services.exp.providers.ExpTipoexpedienteSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ExpTipoexpedienteExtendsMapper extends ExpTipoexpedienteMapper{
	@SelectProvider(type = ExpTipoexpedienteSqlExtendsProvider.class, method = "comboTipoExpediente")
	@Results({ 
		@Result(column = "IDTIPOEXPEDIENTE", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboTipoExpediente(String idlenguaje, String idInstitucion);

}
