package org.itcgae.siga.db.services.form.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.PysProductossolicitadosMapper;
import org.itcgae.siga.db.services.form.providers.PysProductossolicitadosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysProductossolicitadosExtendsMapper extends PysProductossolicitadosMapper{

	@SelectProvider(type = PysProductossolicitadosSqlExtendsProvider.class, method = "selectMaxIdPeticion")
	@Results({
		@Result(column = "IDPETICION1", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectMaxIdPeticion(Short idInstitucion, Short idTipoProducto, Long idProducto, Long idProductoInstitucion);
}
