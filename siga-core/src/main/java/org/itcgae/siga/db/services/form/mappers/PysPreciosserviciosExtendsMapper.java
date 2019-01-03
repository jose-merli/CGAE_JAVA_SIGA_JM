package org.itcgae.siga.db.services.form.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.PysPreciosserviciosMapper;
import org.itcgae.siga.db.services.form.providers.PysPreciosserviciosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysPreciosserviciosExtendsMapper extends PysPreciosserviciosMapper{

	@SelectProvider(type = PysPreciosserviciosSqlExtendsProvider.class, method = "selectMaxIdPrecioServicio")
	@Results({
		@Result(column = "IDPRECIOSERVICIO", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectMaxIdPrecioServicio(Short idInstitucion, Long idServicio, Long idServicioInstitucion, Short idPeriocidad);

}
