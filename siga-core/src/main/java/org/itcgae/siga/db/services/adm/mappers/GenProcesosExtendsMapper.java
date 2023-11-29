package org.itcgae.siga.db.services.adm.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.PermisoEntity;
import org.itcgae.siga.DTOs.gen.PermisoRequestItem;
import org.itcgae.siga.db.mappers.GenProcesosMapper;
import org.itcgae.siga.db.services.adm.providers.GenProcesosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface GenProcesosExtendsMapper extends GenProcesosMapper{

	
	
	@SelectProvider(type = GenProcesosSqlExtendsProvider.class, method = "getProcesosPermisos")
	@Results({
		@Result(column = "TEXT", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ID", property = "data", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DERECHOACCESO", property = "derechoacceso", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PARENT", property = "parent", jdbcType = JdbcType.VARCHAR)
	})
	List<PermisoEntity> getProcesosPermisos(PermisoRequestItem request,String idInstitucionCert, String idLenguaje);
	  

	
}
