package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.FundamentoResolucionItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsTipofundamentosMapper;
import org.itcgae.siga.db.services.scs.providers.ScsEjgSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsFundamentoscalificacionSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipofundamentosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsTipofundamentosExtendsMapper extends ScsTipofundamentosMapper{

	@SelectProvider(type = ScsTipofundamentosSqlExtendsProvider.class, method = "searchFundamentosResolucion")
	@Results({
		@Result(column = "IDFUNDAMENTO", property = "idFundamento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGO", property = "codigoExt", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEJIS", property = "codigoEjis", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcionFundamento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TEXTOPLANTILLA", property = "textoPlantilla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TEXTOPLANTILLA2", property = "textoPlantilla2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TEXTOPLANTILLA3", property = "textoPlantilla3", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TEXTOPLANTILLA4", property = "textoPlantilla4", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcionFundamento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONRESOLUCION", property = "descripcionResolucion", jdbcType = JdbcType.VARCHAR),

	})
	List<FundamentoResolucionItem> searchFundamentosResolucion(FundamentoResolucionItem fundamentoResolucionItem, String idLenguaje, Short idInstitucion);

	@SelectProvider(type = ScsTipofundamentosSqlExtendsProvider.class, method = "getIdFundamentoResolucion")
	@Results({ @Result(column = "IDFUNDAMENTO", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getIdFundamentoResolucion(Short idInstitucion);

	
	@SelectProvider(type = ScsTipofundamentosSqlExtendsProvider.class, method = "comboFundamentoJurid")
	@Results({ 
		@Result(column = "IDFUNDAMENTO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),

	})
	List<ComboItem> comboFundamentoJurid(String idLenguaje, String idInstitucion, String resolucion);
	
}
