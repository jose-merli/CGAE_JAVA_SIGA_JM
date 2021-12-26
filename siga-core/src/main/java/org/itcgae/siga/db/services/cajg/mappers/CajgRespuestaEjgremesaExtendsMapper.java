package org.itcgae.siga.db.services.cajg.mappers;

import org.apache.ibatis.annotations.DeleteProvider;
import org.itcgae.siga.db.mappers.CajgRespuestaEjgremesaMapper;
import org.itcgae.siga.db.services.cajg.providers.CajgRespuestaEjgremesaExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CajgRespuestaEjgremesaExtendsMapper extends CajgRespuestaEjgremesaMapper{

	@DeleteProvider(type = CajgRespuestaEjgremesaExtendsProvider.class, method = "deleteConSelect")
	int deleteConSelect(Long idremesa, Short idInstitucion);
	
}
