package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsPreceptivoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsPreceptivoSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTiporesolucionSqlExtendsProvider;

public interface ScsPreceptivoExtendsMapper extends ScsPreceptivoMapper {

	
	@SelectProvider(type = ScsPreceptivoSqlExtendsProvider.class, method = "comboPreceptivo")
	@Results({
		@Result(column = "IDPRECEPTIVO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})	
	List<ComboItem> comboPreceptivo(String idLenguaje, String idInstitucion);

}
