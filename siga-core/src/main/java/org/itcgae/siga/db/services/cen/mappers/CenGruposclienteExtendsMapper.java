package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenGruposcliente;
import org.itcgae.siga.db.mappers.CenGruposclienteMapper;
import org.itcgae.siga.db.services.cen.providers.CenGruposclienteSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenGruposclienteExtendsMapper extends CenGruposclienteMapper{


	@SelectProvider(type = CenGruposclienteSqlExtendsProvider.class, method = "getLabel")
	@Results({
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPO", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getLabel(AdmUsuarios usuario);
	
	

	@SelectProvider(type = CenGruposclienteSqlExtendsProvider.class, method = "createLegalPerson")
	@Results({
		 @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDGRUPO", property = "idgrupo", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR)
	})
	List<CenGruposcliente> createLegalPerson(String idInstitucion,String idLenguaje, String descripcion);
	
	
	@InsertProvider(type = CenGruposclienteSqlExtendsProvider.class, method = "insertSelectiveForCreateLegalPerson")
	int insertSelectiveForCreateLegalPerson(String idInstitucion, AdmUsuarios usuario);
	
}
