package org.itcgae.siga.db.services.scs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.cen.FichaPersonaItem;
import org.itcgae.siga.db.mappers.ScsDesignasletradoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsDesignasLetradoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsDesignasLetradoExtendsMapper extends ScsDesignasletradoMapper{

	@SelectProvider(type = ScsDesignasLetradoSqlExtendsProvider.class, method = "getDesignaLetrado")
	@Results({ 
		@Result(column = "NCOLEGIADO", property = "numeroColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDO1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDO2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COLEGIADO", property = "colegiado", jdbcType = JdbcType.VARCHAR),
	})
	FichaPersonaItem getDesignaLetrado(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje);
}
