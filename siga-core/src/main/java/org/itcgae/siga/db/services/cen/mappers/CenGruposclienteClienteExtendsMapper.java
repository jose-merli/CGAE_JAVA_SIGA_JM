package org.itcgae.siga.db.services.cen.mappers;

import org.apache.ibatis.annotations.InsertProvider;
import org.itcgae.siga.db.mappers.CenGruposclienteClienteMapper;
import org.itcgae.siga.db.services.cen.providers.CenGruposclienteClienteSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenGruposclienteClienteExtendsMapper extends CenGruposclienteClienteMapper{

	@InsertProvider(type = CenGruposclienteClienteSqlExtendsProvider.class, method = "insertSelectiveForCreateLegalPerson")
	int insertSelectiveForCreateLegalPerson(String idInstitucion, String grupo, String idUsuario);
	
	
	
	
}
