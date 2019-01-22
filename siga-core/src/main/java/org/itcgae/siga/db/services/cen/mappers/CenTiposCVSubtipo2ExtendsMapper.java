package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.SubtipoCurricularItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.CenTiposcvsubtipo2Mapper;
import org.itcgae.siga.db.services.cen.providers.CenTiposCVSubtipo2SqlExtendsProvider;

public interface CenTiposCVSubtipo2ExtendsMapper extends CenTiposcvsubtipo2Mapper{
	
	@SelectProvider(type = CenTiposCVSubtipo2SqlExtendsProvider.class, method = "searchSubtipoCurricular")
	@Results({
		@Result(column = "IDTIPOCV", property = "idTipoCV", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCVSUBTIPO2", property = "idTipoCvSubtipo2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEXTERNO", property = "codigoExterno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
	})
	List<SubtipoCurricularItem> searchSubtipoCurricular(SubtipoCurricularItem subtipoCurricularItem, String idLenguaje, String idInstitucion);

	
	@SelectProvider(type = CenTiposCVSubtipo2SqlExtendsProvider.class, method = "searchComboSubtipoCurricular")
	@Results({
		@Result(column = "IDTIPOCVSUBTIPO2", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> searchComboSubtipoCurricular(SubtipoCurricularItem subtipoCurricularItem, String idLenguaje, String idInstitucion);

	@SelectProvider(type = CenTiposCVSubtipo2SqlExtendsProvider.class, method = "searchCurricularSubtypeCombo")
	@Results({
		@Result(column = "IDTIPOCVSUBTIPO2", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> searchCurricularSubtypeCombo(String idTipoCV, String idLenguaje, String idInstitucion);

	
	
	@SelectProvider(type = CenTiposCVSubtipo2SqlExtendsProvider.class, method = "getMaxIdCvSubtipo2")
	@Results({ @Result(column = "IDTIPOCVSUBTIPO2", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getMaxIdCvSubtipo2(String idInstitucion, String idTipoCv);
	

	@SelectProvider(type = CenTiposCVSubtipo2SqlExtendsProvider.class, method = "getHistory")
	@Results({
		@Result(column = "IDTIPOCV", property = "idTipoCV", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCVSUBTIPO2", property = "idTipoCvSubtipo2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEXTERNO", property = "codigoExterno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
	})
	List<SubtipoCurricularItem> getHistory(SubtipoCurricularItem subtipoCurricularItem, String idInstitucion, String idLenguaje);
}
