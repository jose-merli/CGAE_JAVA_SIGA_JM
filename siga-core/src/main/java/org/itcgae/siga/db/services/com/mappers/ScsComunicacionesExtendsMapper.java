package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsComunicacionesMapper;
import org.itcgae.siga.db.services.com.providers.ScsComunicacionesExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsComunicacionesExtendsMapper extends ScsComunicacionesMapper{
	
	 @SelectProvider(type=ScsComunicacionesExtendsSqlProvider.class, method="nextIdComunicacion")
	    @Results({
	            @Result(column="ID", property="newId", jdbcType=JdbcType.VARCHAR)
	    })
	    NewIdDTO selectMaxIdLinea();
	

}
