package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaItem;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.TipoCurricularItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CenTiposcvMapper;
import org.itcgae.siga.db.services.cen.providers.CenNocolegiadoSqlExtendsProvider;
import org.itcgae.siga.db.services.cen.providers.CenTiposcvSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenTiposcvExtendsMapper extends CenTiposcvMapper{

	
	@SelectProvider(type = CenTiposcvSqlExtendsProvider.class, method = "selectCategoriaCV")
	@Results({
		@Result(column = "IDTIPOCV", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> selectCategoriaCV(String idLenguaje);
	
	
	@SelectProvider(type = CenTiposcvSqlExtendsProvider.class, method = "getHistory")
	@Results({
		@Result(column = "IDTIPOCV", property = "idTipoCV", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCVSUBTIPO1", property = "idTipoCvSubtipo1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEXTERNO", property = "codigoExterno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
	})
	List<TipoCurricularItem> getHistory(TipoCurricularItem tipoCurricularItem, String idInstitucion, String idLenguaje);
	
}
