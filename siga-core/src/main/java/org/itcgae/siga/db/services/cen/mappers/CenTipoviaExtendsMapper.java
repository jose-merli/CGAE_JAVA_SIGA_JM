package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CenTipoviaMapper;
import org.itcgae.siga.db.services.cen.providers.CenTipoviaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenTipoviaExtendsMapper extends CenTipoviaMapper{

	
	@SelectProvider(type = CenTipoviaSqlExtendsProvider.class, method = "getTipoVias")
	@Results({
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOVIA", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getTipoVias(Short idInstitucion, String idLenguaje, String idTipoViaJusticiable);
}
