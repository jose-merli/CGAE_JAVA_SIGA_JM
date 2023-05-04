package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ComboItemFundamentosCalif;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.FundamentosCalificacionItem;
import org.itcgae.siga.db.mappers.ScsTipofundamentocalifMapper;
import org.itcgae.siga.db.services.scs.providers.ScsFundamentoscalificacionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsFundamentoscalificacionExtendsMapper extends ScsTipofundamentocalifMapper {

	

	
	@SelectProvider(type = ScsFundamentoscalificacionSqlExtendsProvider.class, method = "searchFundamentos")
	@Results({ 
		@Result(column = "IDFUNDAMENTOCALIF", property = "idFundamento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcionFundamento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEJIS", property = "codigoEjis", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TEXTOPLANTILLA", property = "textoEnPlantilla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONDICTAMEN", property = "descripcionDictamen", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPODICTAMENEJG", property = "idTipoDictamenEjg", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDRECURSO", property = "idDescripcion", jdbcType = JdbcType.VARCHAR)
	})
	List<FundamentosCalificacionItem> searchFundamentos(String idLenguaje, String idInstitucion,FundamentosCalificacionItem fundamentosCalificacionItem);
	
	@SelectProvider(type = ScsFundamentoscalificacionSqlExtendsProvider.class, method = "getIdFundamento")
	@Results({ @Result(column = "IDFUNDAMENTOCALIF", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdFundamento(Short idInstitucion,String idLenguaje);
	
	@SelectProvider(type = ScsFundamentoscalificacionSqlExtendsProvider.class, method = "comboFundamentoCalificacion")
	@Results({ 
		@Result(column = "IDFUNDAMENTOCALIF", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItemFundamentosCalif> comboFundamentoCalificacion(String idLenguaje, String idInstitucion, String[] list_dictamen);
		
}
