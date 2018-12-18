package org.itcgae.siga.db.services.ecom.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.EcomCenDatos;
import org.itcgae.siga.db.mappers.EcomCenDatosMapper;
import org.itcgae.siga.db.services.ecom.providers.EcomCenDatosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface EcomCenDatosExtendsMapper extends EcomCenDatosMapper {

	

	
	@SelectProvider(type = EcomCenDatosSqlExtendsProvider.class, method = "getInfoMantenimientoDuplicados")
	@Results({ 
		@Result(column = "ECD_IDCENSODATOS", property = "idcensodatos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ECD_IDESTADOCOLEGIADO", property = "idestadocolegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ECD_FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.DATE),
		@Result(column = "ECD_IDECOMCENSOSITUACIONEJER", property = "idecomcensosituacionejer", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ECD_FECHAMODIFRECIBIDA", property = "fechamodifrecibida", jdbcType = JdbcType.DATE)
	})
	List<EcomCenDatos> getInfoMantenimientoDuplicados(String idPersona, String idInstitucion);
	
	
	
}
