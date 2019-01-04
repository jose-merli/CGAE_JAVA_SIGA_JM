package org.itcgae.siga.db.services.form.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.PysPeticioncomprasuscripcionMapper;
import org.itcgae.siga.db.services.form.providers.PysPeticioncomprasuscripcionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysPeticioncomprasuscripcionExtendsMapper extends PysPeticioncomprasuscripcionMapper{

	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "selectMaxIdPeticion")
	@Results({
		@Result(column = "IDPETICION", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectMaxIdPeticion(Short idInstitucion);

	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "selectMaxNumOperacion")
	@Results({
		@Result(column = "NUM_OPERACION", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectMaxNumOperacion(Short idInstitucion);

	
}
