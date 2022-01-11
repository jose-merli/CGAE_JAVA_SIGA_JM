package org.itcgae.siga.db.services.fcs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.FcsFactGrupofactHitoMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsFactGrupofactHitoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FcsFactGrupofactHitoExtendsMapper extends FcsFactGrupofactHitoMapper {

	@SelectProvider(type = FcsFactGrupofactHitoSqlExtendsProvider.class, method = "comboConceptosPago")
	@Results({ @Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDHITOGENERAL", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboConceptosPago(String idFacturacion, Short idInstitucion, String idioma);

}
