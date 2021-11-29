package org.itcgae.siga.db.services.scs.mappers;

import org.apache.ibatis.annotations.UpdateProvider;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.db.mappers.ScsEjgActaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsEjgActaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsEjgActaExtendsMapper extends ScsEjgActaMapper{

	@UpdateProvider(type = ScsEjgActaSqlExtendsProvider.class, method = "updateResolucion")
	int updateResolucion(ActasItem actasItem, Short idInstitucion);
	
}
