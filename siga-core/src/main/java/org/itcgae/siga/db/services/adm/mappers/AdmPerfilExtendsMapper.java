package org.itcgae.siga.db.services.adm.mappers;


import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
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
	
	
	
	
	
	
	
}
