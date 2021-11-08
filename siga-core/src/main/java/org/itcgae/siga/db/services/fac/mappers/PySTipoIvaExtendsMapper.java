package org.itcgae.siga.db.services.fac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.PysTipoivaMapper;
import org.itcgae.siga.db.services.fac.providers.PySTipoIvaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public interface PySTipoIvaExtendsMapper extends PysTipoivaMapper{
	
	@SelectProvider(type = PySTipoIvaSqlExtendsProvider.class, method = "comboIva")
	@Results({ 
		@Result(column = "ID", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ComboItem> comboIva(String idioma);
	
	@SelectProvider(type = PySTipoIvaSqlExtendsProvider.class, method = "comboIvaNoDerogados")
	@Results({ 
		@Result(column = "ID", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ComboItem> comboIvaNoDerogados(String idioma);

	@SelectProvider(type = PySTipoIvaSqlExtendsProvider.class, method = "comboIva")
	@Results({
			@Result(column = "idtipoiva", property = "value", jdbcType = JdbcType.NUMERIC),
			@Result(column = "descripcion", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboTiposIVA(String idioma);

}
