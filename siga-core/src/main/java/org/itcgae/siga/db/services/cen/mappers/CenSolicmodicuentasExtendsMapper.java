package org.itcgae.siga.db.services.cen.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.CenSolicmodicuentasMapper;
import org.itcgae.siga.db.services.cen.providers.CenSolicmodicuentasSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenSolicmodicuentasExtendsMapper extends CenSolicmodicuentasMapper{
	
	@SelectProvider(type = CenSolicmodicuentasSqlExtendsProvider.class, method = "getMaxIdSolicitud")
	@Results({ @Result(column = "IDSOLICITUD", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getMaxIdSolicitud(String idInstitucion, String idPersona);
	

}