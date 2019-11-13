package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsEjgSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoejgSqlExtendsProvider;

public interface ScsEjgExtendsMapper extends ScsEjgMapper {

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "comboCreadoDesde")
	@Results({ 
		@Result(column = "ORIGENAPERTURA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboCreadoDesde(String idlenguaje, String string);

}
