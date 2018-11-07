package org.itcgae.siga.db.services.cen.mappers;


import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenSolicitudAlterExtendsMapper {


	@SelectProvider(type = CenSolicitudAlterExtendsMapper.class, method = "getMaxIdSolicitud")
	@Results({ @Result(column = "IDSOLICITUD", property = "idMax", jdbcType = JdbcType.NUMERIC)})
	MaxIdDto getMaxIdRecurso();
	

}