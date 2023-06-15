package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.PysAnticipoletradoMapper;
import org.itcgae.siga.db.services.fac.providers.PysAnticipoletradoExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysAnticipoletradoExtendsMapper extends PysAnticipoletradoMapper{

	@SelectProvider(type=PysAnticipoletradoExtendsSqlProvider.class, method="selectMaxIdAnticipo")
    @Results({
            @Result(column="ID", property="newId", jdbcType=JdbcType.VARCHAR)
    })
    NewIdDTO selectMaxIdAnticipo(Short idInstitucion, Long idPersona);
	
   
}
