package org.itcgae.siga.db.services.cen.mappers;


import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.adm.EntidadLenguajeInstitucionDTO;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.services.cen.providers.CenInstitucionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenInstitucionExtendsMapper extends CenInstitucionMapper {
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION
	 * @mbg.generated  Wed Mar 07 18:59:03 CET 2018
	 */
	@Select({ "select distinct", "INSTITUCION.IDINSTITUCION, INSTITUCION.NOMBRE, INSTITUCION.BBDDCPD, INSTITUCION.IDLENGUAJE, INSTITUCION.FECHAMODIFICACION, INSTITUCION.USUMODIFICACION, ",
			"INSTITUCION.IDPERSONA, INSTITUCION.CUENTACONTABLECAJA, INSTITUCION.CEN_INST_IDINSTITUCION, INSTITUCION.ABREVIATURA, INSTITUCION.FECHAENPRODUCCION, ",
			"INSTITUCION.CODIGOEXT, INSTITUCION.IDCOMISION", "from CEN_INSTITUCION INSTITUCION inner join adm_perfil perfil on  INSTITUCION.idinstitucion = perfil.idinstitucion" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BBDDCPD", property = "bbddcpd", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDLENGUAJE", property = "idlenguaje", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CUENTACONTABLECAJA", property = "cuentacontablecaja", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CEN_INST_IDINSTITUCION", property = "cenInstIdinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAENPRODUCCION", property = "fechaenproduccion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCOMISION", property = "idcomision", jdbcType = JdbcType.VARCHAR) })
	List<CenInstitucion> selectInstitucionPerfil();
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION
	 * @mbg.generated  Thu Mar 08 16:36:51 CET 2018
	 */
	@SelectProvider(type = CenInstitucionSqlExtendsProvider.class, method = "selectComboInstitucionByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAENPRODUCCION", property = "fechaenproduccion", jdbcType = JdbcType.TIMESTAMP),
			})
	List<CenInstitucion> selectComboInstitucionByExample(CenInstitucionExample example);
	
	
	@SelectProvider(type = CenInstitucionSqlExtendsProvider.class, method = "getInstitutionLenguage")
	@Results({ @Result(column = "IDLENGUAJE", property = "idLenguaje", jdbcType = JdbcType.DECIMAL, id = true)
			})
	EntidadLenguajeInstitucionDTO getInstitutionLenguage(String idInstitucion);
	
	
}
