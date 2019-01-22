package org.itcgae.siga.db.services.age.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.age.PermisosPerfilesCalendarItem;
import org.itcgae.siga.db.mappers.AgePermisoscalendarioMapper;
import org.itcgae.siga.db.services.age.providers.AgePermisosCalendarioSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface AgePermisosCalendarioExtendsMapper extends AgePermisoscalendarioMapper{
			
	@SelectProvider(type = AgePermisosCalendarioSqlExtendsProvider.class, method = "getProfilesPermissions")
	@Results({
		@Result(column = "IDPERFIL", property = "idPerfil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "nombrePerfil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOACCESO", property = "derechoacceso", jdbcType = JdbcType.NUMERIC)
	})
	List<PermisosPerfilesCalendarItem> getProfilesPermissions(String idCalendario, String idInstitucion);
}
