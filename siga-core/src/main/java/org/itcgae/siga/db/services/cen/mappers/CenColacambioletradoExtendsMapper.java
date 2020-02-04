package org.itcgae.siga.db.services.cen.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.db.mappers.CenColacambioletradoMapper;
import org.itcgae.siga.db.services.cen.providers.CenColacambioletradoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenColacambioletradoExtendsMapper extends CenColacambioletradoMapper{
	
	@SelectProvider(type = CenColacambioletradoSqlExtendsProvider.class, method = "selectNuevoId")
	@Results({
		@Result(column = "ID", property = "idMax", jdbcType = JdbcType.VARCHAR),
	})
	MaxIdDto selectNuevoId(Integer idInstitucion, Long idPersona);
	

}