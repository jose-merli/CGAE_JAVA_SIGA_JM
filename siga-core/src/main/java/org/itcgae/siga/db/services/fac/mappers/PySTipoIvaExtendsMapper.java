package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ComboItem2;
import org.itcgae.siga.db.mappers.PysTipoivaMapper;
import org.itcgae.siga.db.services.fac.providers.PySTipoIvaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


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

	@SelectProvider(type = PySTipoIvaSqlExtendsProvider.class, method = "comboTiposIVA")
	@Results({
			@Result(column = "idtipoiva", property = "value", jdbcType = JdbcType.NUMERIC),
			@Result(column = "descripcion", property = "label1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "valor", property = "label2", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem2> comboTiposIVA(String idioma);
	
	@SelectProvider(type = PySTipoIvaSqlExtendsProvider.class, method = "comboIVACuentasBancariasEntidad")
	@Results({
			@Result(column = "IDTIPOIVA", property = "value", jdbcType = JdbcType.NUMERIC),
			@Result(column = "DESCRIPCION", property = "label1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALOR", property = "label2", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem2> comboIVACuentasBancariasEntidad(String tipoIva,String idioma);

	@SelectProvider(type = PySTipoIvaSqlExtendsProvider.class, method = "getC_CTAIVA")
	@Results({
			@Result(column = "C_CTAIVA", property = "value", jdbcType = JdbcType.NUMERIC),
	})
	List<ComboItem> getC_CTAIVA(String idInstitucion, String idTipoIVA);
}
