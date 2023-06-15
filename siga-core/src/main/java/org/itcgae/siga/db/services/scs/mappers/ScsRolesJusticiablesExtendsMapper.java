package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsRolesjusticiablesMapper;
import org.itcgae.siga.db.services.scs.providers.ScsRolesJusticiablesSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsRolesJusticiablesExtendsMapper extends ScsRolesjusticiablesMapper{

	@SelectProvider(type = ScsRolesJusticiablesSqlExtendsProvider.class, method = "getComboRoles")
	@Results({
		@Result(column = "IDROL", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getComboRoles(String idLenguaje);
	
}
