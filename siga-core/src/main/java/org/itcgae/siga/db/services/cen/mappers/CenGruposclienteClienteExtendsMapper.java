package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CenGruposclienteClienteMapper;
import org.itcgae.siga.db.services.cen.providers.CenGruposclienteClienteSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenGruposclienteClienteExtendsMapper extends CenGruposclienteClienteMapper{

	@InsertProvider(type = CenGruposclienteClienteSqlExtendsProvider.class, method = "insertSelectiveForCreateLegalPerson")
	int insertSelectiveForCreateLegalPerson(String idInstitucion, String grupo, String idUsuario);
	
	
	
	@SelectProvider(type = CenGruposclienteClienteSqlExtendsProvider.class, method = "selectGruposPersonaJuridica")
	@Results({
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPO", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectGruposPersonaJuridica(String idPersona, String idInstitucion);
	
	
	
	@InsertProvider(type = CenGruposclienteClienteSqlExtendsProvider.class, method = "insertSelectiveForUpdateLegalPerson")
	int insertSelectiveForUpdateLegalPerson(EtiquetaUpdateDTO etiquetaUpdateDTO, String idInstitucion, String grupo, String idUsuario);
	
	
}
