package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.TipoCurricularItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.CenTiposcvsubtipo1Mapper;
import org.itcgae.siga.db.services.cen.providers.CenTiposCVSubtipo1SqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenTiposCVSubtipo1ExtendsMapper extends CenTiposcvsubtipo1Mapper{

	@SelectProvider(type = CenTiposCVSubtipo1SqlExtendsProvider.class, method = "searchTipoCurricular")
	@Results({
		@Result(column = "IDTIPOCV", property = "idTipoCV", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCVSUBTIPO1", property = "idTipoCvSubtipo1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEXTERNO", property = "codigoExterno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR)
	})
	List<TipoCurricularItem> searchTipoCurricular(TipoCurricularItem tipoCurricularItem, String idLenguaje, String idInstitucion);
		
	@SelectProvider(type = CenTiposCVSubtipo1SqlExtendsProvider.class, method = "searchCurricularTypeCombo")
	@Results({
		@Result(column = "IDTIPOCVSUBTIPO1", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> searchCurricularTypeCombo(String idTipoCv, String idLenguaje, String idInstitucion);
	
	
	@SelectProvider(type = CenTiposCVSubtipo1SqlExtendsProvider.class, method = "getMaxIdCvSubtipo1")
	@Results({ @Result(column = "IDTIPOCVSUBTIPO1", property = "newId", jdbcType = JdbcType.VARCHAR)})
	NewIdDTO getMaxIdCvSubtipo1(String idInstitucion, String idTipoCv);
	
	@SelectProvider(type = CenTiposCVSubtipo1SqlExtendsProvider.class, method = "getHistory")
	@Results({
		@Result(column = "IDTIPOCV", property = "idTipoCV", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCVSUBTIPO1", property = "idTipoCvSubtipo1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEXTERNO", property = "codigoExterno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
	})
	List<TipoCurricularItem> getHistory(TipoCurricularItem tipoCurricularItem, String idInstitucion, String idLenguaje);
	
}
