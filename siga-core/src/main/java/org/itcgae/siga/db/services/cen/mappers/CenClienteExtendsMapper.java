package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.services.cen.providers.CenClienteSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenClienteExtendsMapper extends CenClienteMapper{


	@SelectProvider(type = CenClienteSqlExtendsProvider.class, method = "getIdPersonaWithNif")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.NUMERIC),
	})
	Long getIdPersonaWithNif(String personaNif, Short idInstitucion);
	
	@SelectProvider(type = CenClienteSqlExtendsProvider.class, method = "getIdPersona")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.NUMERIC),
	})
	Long getIdPersona (String colegiadoNumero, String personaNif, Short idInstitucion);

	
	
	@SelectProvider(type = CenClienteSqlExtendsProvider.class, method = "getEsLetrado")
	@Results({
		@Result(column = "LETRADO", property = "valor", jdbcType = JdbcType.NUMERIC),
	})
	StringDTO getEsLetrado(String idPersona, String idInstitucion);

	@SelectProvider(type = CenClienteSqlExtendsProvider.class, method = "getInstitucionesEjerciente")
	@Results({
		@Result(column = "IDINSTITUCION", property = "valor", jdbcType = JdbcType.NUMERIC),
	})
	
	List<StringDTO> getInstitucionesEjerciente(String idPersona, String idInstitucion);
	
	@SelectProvider(type = CenClienteSqlExtendsProvider.class, method = "getTratamiento")
	@Results({
		@Result(column = "TRATAMIENTO", property = "valor", jdbcType = JdbcType.NUMERIC),
	})
	
	List<StringDTO> getTratamiento(String idInstitucion, String idPersona, int idIdioma);

}
