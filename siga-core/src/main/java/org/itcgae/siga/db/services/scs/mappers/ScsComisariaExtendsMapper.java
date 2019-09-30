package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.scs.ComisariaItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsComisariaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsComisariaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsComisariaExtendsMapper extends ScsComisariaMapper{

	@SelectProvider(type = ScsComisariaSqlExtendsProvider.class, method = "searchComisarias")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOPOSTAL", property = "codigoPostal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPOBLACION", property = "idPoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROVINCIA", property = "idProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO1", property = "telefono1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO2", property = "telefono2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEXT", property = "codigoExt", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VISIBLEMOVIL", property = "visibleMovil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPOBLACION", property = "nombrePoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPROVINCIA", property = "nombreProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR),

	})
	List<ComisariaItem> searchComisarias(ComisariaItem comisariaItem, Short idInstitucion);

	@SelectProvider(type = ScsComisariaSqlExtendsProvider.class, method = "getIdComisaria")
	@Results({ @Result(column = "IDCOMISARIA", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getIdComisaria(Short idInstitucion);
	
}
