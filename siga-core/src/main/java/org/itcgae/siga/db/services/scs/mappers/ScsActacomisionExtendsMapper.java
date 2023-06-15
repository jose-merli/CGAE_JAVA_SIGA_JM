package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.db.mappers.ScsActacomisionMapper;
import org.itcgae.siga.db.services.scs.providers.ScsActacomisionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
@Service
@Primary
public interface ScsActacomisionExtendsMapper extends ScsActacomisionMapper {

	@SelectProvider(type = ScsActacomisionSqlExtendsProvider.class, method = "getActaAnnio")
		@Results({ 
			@Result(column = "IDACTANNIO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),

	})
	List<ComboItem> getActaAnnio(String idInstitucion, String anioacta, String idacta);
}
