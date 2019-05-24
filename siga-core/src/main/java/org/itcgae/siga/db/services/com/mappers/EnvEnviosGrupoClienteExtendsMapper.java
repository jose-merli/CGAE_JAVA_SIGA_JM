package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.ComboInstitucionItem;
import org.itcgae.siga.db.services.com.providers.EnvEnviosGrupoClienteExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface EnvEnviosGrupoClienteExtendsMapper {
	
	@SelectProvider(type = EnvEnviosGrupoClienteExtendsSqlProvider.class, method = "selectGruposEnvio")
	@Results({@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboInstitucionItem> getGruposEnvio(String idEnvio, String idLenguaje, Short IdInstitucion);

}
