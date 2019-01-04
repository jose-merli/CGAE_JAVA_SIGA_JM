package org.itcgae.siga.db.services.form.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.PysSuscripcionMapper;
import org.itcgae.siga.db.services.form.providers.PysSuscripcionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysSuscripcionExtendsMapper extends PysSuscripcionMapper{

	@SelectProvider(type =PysSuscripcionSqlExtendsProvider.class, method = "selectMaxIdSuscripcion")
	@Results({
		@Result(column = "IDSUSCRIPCION", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectMaxIdSuscripcion(Short idInstitucion, Long idServicio, Long idServicioInstitucion);

}
