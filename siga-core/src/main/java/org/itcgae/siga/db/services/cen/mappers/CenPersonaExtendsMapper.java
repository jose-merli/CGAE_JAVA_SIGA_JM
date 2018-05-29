package org.itcgae.siga.db.services.cen.mappers;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.services.cen.providers.CenPersonaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenPersonaExtendsMapper extends CenPersonaMapper{

	@SelectProvider(type = CenPersonaSqlExtendsProvider.class, method = "loadPhotography")
	@Results({
		@Result(column = "NIFCIF", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FOTOGRAFIA", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	ComboItem loadPhotography(String idPersona);
	
		
	@InsertProvider(type = CenPersonaSqlExtendsProvider.class, method = "insertSelectiveForCreateLegalPerson")
	int insertSelectiveForCreateLegalPerson(EtiquetaUpdateDTO etiquetaUpdateDTO, AdmUsuarios usuario);
	
}
