package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.DestinatarioItem;
import org.itcgae.siga.db.services.com.providers.EnvDestinatariosExtendsSqlProvider;

public interface EnvDestinatariosExtendsMapper {

	
	@SelectProvider(type = EnvDestinatariosExtendsSqlProvider.class, method = "selectDestinatarios")
	@Results({@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOENVIOS", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<DestinatarioItem> selectDestinatarios(Short idInstitucion, String idEnvio);
}
