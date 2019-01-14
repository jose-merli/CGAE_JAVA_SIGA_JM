package org.itcgae.siga.db.services.adm.mappers;


import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.adm.RolPerfilDTO;
import org.itcgae.siga.DTOs.com.PerfilDTO;
import org.itcgae.siga.db.entities.AdmPerfil;
import org.itcgae.siga.db.entities.AdmPerfilExample;
import org.itcgae.siga.db.mappers.AdmPerfilMapper;
import org.itcgae.siga.db.services.adm.providers.AdmPerfilSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface AdmPerfilExtendsMapper extends AdmPerfilMapper {
	
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION
	 * @mbg.generated  Thu Mar 08 16:36:51 CET 2018
	 */
	@SelectProvider(type = AdmPerfilSqlProvider.class, method = "selectComboPerfilByExample")
	@Results({
		@Result(column = "IDPERFIL", property = "idperfil", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR)
	})
	List<AdmPerfil> selectComboPerfilByExample(AdmPerfilExample example);
	
	
	/***
	 * Map column of data base to java object (AdmPerfil). This is possible due to this function is associate with the result of a query SQL (selectComboPerfilDistinctByExample.class).
	 * @param example The filter used to build the  SQL query .
	 * @return List of objects mapped from data base to java object.
	 */
	@SelectProvider(type = AdmPerfilSqlProvider.class, method = "selectComboPerfilDistinctByExample")
	@Results({
		@Result(column = "IDPERFIL", property = "idperfil", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.DATE)
	})
	List<AdmPerfil> selectComboPerfilDistinctByExample(AdmPerfilExample example);
	
	
	
	/***
	 * Map column of data base to java object (AdmPerfil). This is possible due to this function is associate with the result of a query SQL (selectRolPerfilDistinctByExample.class).
	 * @param example The filter used to build the  SQL query .
	 * @return List of objects mapped from data base to java object.
	 */
	@SelectProvider(type = AdmPerfilSqlProvider.class, method = "selectRolPerfilDistinctByExample")
	@Results({
		@Result(column = "IDROL", property = "idRol", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GRUPOPORDEFECTO", property = "grupopordefecto", jdbcType = JdbcType.VARCHAR)
	})
	List<RolPerfilDTO> selectRolPerfilDistinctByExample(String idInstitucion,String idPerfil);
	
	@SelectProvider(type = AdmPerfilSqlProvider.class, method = "selectListadoPerfiles")
	@Results({ @Result(column = "IDPERFIL", property = "idPerfil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIVELPERFIL", property = "idPlantillaEnvios", jdbcType = JdbcType.NUMERIC)
		})
	List<PerfilDTO> selectListadoPerfiles(Short idInstitucion);
	
}
