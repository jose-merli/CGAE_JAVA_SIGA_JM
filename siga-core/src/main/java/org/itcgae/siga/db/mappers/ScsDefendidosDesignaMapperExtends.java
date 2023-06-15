package org.itcgae.siga.db.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;

public interface ScsDefendidosDesignaMapperExtends extends ScsDefendidosdesignaMapper {

	@SelectProvider(type = ScsDefendidosDesignaProviderExtends.class, method = "seleccionarDesigna")
	@Results({ @Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.NUMERIC, id = true),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.NUMERIC, id = true) })
	List<ScsDefendidosdesigna> seleccionarDesigna(ScsDefendidosdesigna designa);

}
