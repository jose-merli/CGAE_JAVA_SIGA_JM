package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsMinusvaliaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsMinusvaliaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsMinusvaliaExtendsMapper extends ScsMinusvaliaMapper{

	@SelectProvider(type = ScsMinusvaliaSqlExtendsProvider.class, method = "getMinusvalias")
	@Results({
		@Result(column = "IDMINUSVALIA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getMinusvalias(Short idInstitucion, String lenguaje);

	
}
